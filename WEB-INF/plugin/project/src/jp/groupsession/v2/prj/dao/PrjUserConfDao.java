package jp.groupsession.v2.prj.dao;

import jp.co.sjts.util.date.UDate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.prj.model.PrjUserConfModel;

/**
 * <p>PRJ_USER_CONF Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class PrjUserConfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(PrjUserConfDao.class);

    /**
     * <p>Default Constructor
     */
    public PrjUserConfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public PrjUserConfDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] プロジェクト個人設定情報を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param bean PRJ_USER_CONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(PrjUserConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" PRJ_USER_CONF(");
            sql.addSql("   USR_SID,");
            sql.addSql("   PUC_PRJ_CNT,");
            sql.addSql("   PUC_TODO_CNT,");
            sql.addSql("   PUC_AUID,");
            sql.addSql("   PUC_ADATE,");
            sql.addSql("   PUC_EUID,");
            sql.addSql("   PUC_EDATE,");
            sql.addSql("   PUC_TODO_DATE,");
            sql.addSql("   PUC_TODO_PROJECT,");
            sql.addSql("   PUC_TODO_STATUS,");
            sql.addSql("   PUC_PRJ_PROJECT,");
            sql.addSql("   PUC_MAIN_DATE,");
            sql.addSql("   PUC_MAIN_STATUS,");
            sql.addSql("   PUC_MAIN_MENBER,");
            sql.addSql("   PUC_DEF_DSP,");
            sql.addSql("   PUC_SCH_KBN,");
            sql.addSql("   PUC_TODO_DSP");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getPucPrjCnt());
            sql.addIntValue(bean.getPucTodoCnt());
            sql.addIntValue(bean.getPucAuid());
            sql.addDateValue(bean.getPucAdate());
            sql.addIntValue(bean.getPucEuid());
            sql.addDateValue(bean.getPucEdate());
            sql.addIntValue(bean.getPucTodoDate());
            sql.addIntValue(bean.getPucTodoProject());
            sql.addIntValue(bean.getPucTodoStatus());
            sql.addIntValue(bean.getPucPrjProject());
            sql.addIntValue(bean.getPucMainDate());
            sql.addIntValue(bean.getPucMainStatus());
            sql.addIntValue(bean.getPucMainMember());
            sql.addIntValue(bean.getPucDefDsp());
            sql.addIntValue(bean.getPucSchKbn());
            sql.addIntValue(bean.getPucTodoDsp());

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
     * <br>[機  能] プロジェクト個人設定情報を更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param bean PRJ_USER_CONF Data Bindding JavaBean
     * @return int 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(PrjUserConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   PRJ_USER_CONF");
            sql.addSql(" set ");
            sql.addSql("   PUC_PRJ_CNT=?,");
            sql.addSql("   PUC_TODO_CNT=?,");
            sql.addSql("   PUC_EUID=?,");
            sql.addSql("   PUC_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getPucPrjCnt());
            sql.addIntValue(bean.getPucTodoCnt());
            sql.addIntValue(bean.getPucEuid());
            sql.addDateValue(bean.getPucEdate());
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
     * <br>[機  能] プロジェクト個人設定情報を更新する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param bean PRJ_USER_CONF Data Bindding JavaBean
     * @return int 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateDashBoard(PrjUserConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   PRJ_USER_CONF");
            sql.addSql(" set ");
            sql.addSql("   PUC_TODO_DATE = ?,");
            sql.addSql("   PUC_TODO_PROJECT = ?,");
            sql.addSql("   PUC_TODO_STATUS = ?,");
            sql.addSql("   PUC_PRJ_PROJECT = ?,");
            sql.addSql("   PUC_DEF_DSP = ?,");
            sql.addSql("   PUC_EUID = ?,");
            sql.addSql("   PUC_EDATE = ?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getPucTodoDate());
            sql.addIntValue(bean.getPucTodoProject());
            sql.addIntValue(bean.getPucTodoStatus());
            sql.addIntValue(bean.getPucPrjProject());
            sql.addIntValue(bean.getPucDefDsp());
            sql.addIntValue(bean.getPucEuid());
            sql.addDateValue(bean.getPucEdate());
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
     * <br>[機  能] プロジェクト個人設定情報を更新する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param bean PRJ_USER_CONF Data Bindding JavaBean
     * @return int 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updatePrjMain(PrjUserConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   PRJ_USER_CONF");
            sql.addSql(" set ");
            sql.addSql("   PUC_MAIN_DATE = ?,");
            sql.addSql("   PUC_MAIN_STATUS = ?,");
            sql.addSql("   PUC_MAIN_MENBER = ?,");
            sql.addSql("   PUC_EUID = ?,");
            sql.addSql("   PUC_EDATE = ?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getPucMainDate());
            sql.addIntValue(bean.getPucMainStatus());
            sql.addIntValue(bean.getPucMainMember());
            sql.addIntValue(bean.getPucEuid());
            sql.addDateValue(bean.getPucEdate());
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
     * <br>[機  能] プロジェクト個人設定情報を更新する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param bean PRJ_USER_CONF Data Bindding JavaBean
     * @return int 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updatePrjSch(PrjUserConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   PRJ_USER_CONF");
            sql.addSql(" set ");
            sql.addSql("   PUC_SCH_KBN = ?,");
            sql.addSql("   PUC_EUID = ?,");
            sql.addSql("   PUC_EDATE = ?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getPucSchKbn());
            sql.addIntValue(bean.getPucEuid());
            sql.addDateValue(bean.getPucEdate());
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
     * <br>[機  能] プロジェクト個人設定情報を更新する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param bean PRJ_USER_CONF Data Bindding JavaBean
     * @return int 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updatePrjTodoDsp(PrjUserConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   PRJ_USER_CONF");
            sql.addSql(" set ");
            sql.addSql("   PUC_TODO_DSP = ?,");
            sql.addSql("   PUC_EUID = ?,");
            sql.addSql("   PUC_EDATE = ?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getPucTodoDsp());
            sql.addIntValue(bean.getPucEuid());
            sql.addDateValue(bean.getPucEdate());
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
     * <p>Select PRJ_USER_CONF All Data
     * @return List in PRJ_USER_CONFModel
     * @throws SQLException SQL実行例外
     */
    public List<PrjUserConfModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<PrjUserConfModel> ret = new ArrayList<PrjUserConfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   PUC_PRJ_CNT,");
            sql.addSql("   PUC_TODO_CNT,");
            sql.addSql("   PUC_AUID,");
            sql.addSql("   PUC_ADATE,");
            sql.addSql("   PUC_EUID,");
            sql.addSql("   PUC_EDATE,");
            sql.addSql("   PUC_TODO_DATE,");
            sql.addSql("   PUC_TODO_PROJECT,");
            sql.addSql("   PUC_TODO_STATUS,");
            sql.addSql("   PUC_PRJ_PROJECT,");
            sql.addSql("   PUC_MAIN_DATE,");
            sql.addSql("   PUC_MAIN_STATUS,");
            sql.addSql("   PUC_MAIN_MENBER,");
            sql.addSql("   PUC_DEF_DSP,");
            sql.addSql("   PUC_SCH_KBN,");
            sql.addSql("   PUC_TODO_DSP");
            sql.addSql(" from ");
            sql.addSql("   PRJ_USER_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getPrjUserConfFromRs(rs));
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
     * <br>[機  能] プロジェクト個人設定を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return PRJ_USER_CONFModel
     * @throws SQLException SQL実行例外
     */
    public PrjUserConfModel select(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        PrjUserConfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   PUC_PRJ_CNT,");
            sql.addSql("   PUC_TODO_CNT,");
            sql.addSql("   PUC_AUID,");
            sql.addSql("   PUC_ADATE,");
            sql.addSql("   PUC_EUID,");
            sql.addSql("   PUC_EDATE,");
            sql.addSql("   PUC_TODO_DATE,");
            sql.addSql("   PUC_TODO_PROJECT,");
            sql.addSql("   PUC_TODO_STATUS,");
            sql.addSql("   PUC_PRJ_PROJECT,");
            sql.addSql("   PUC_MAIN_DATE,");
            sql.addSql("   PUC_MAIN_STATUS,");
            sql.addSql("   PUC_MAIN_MENBER,");
            sql.addSql("   PUC_DEF_DSP,");
            sql.addSql("   PUC_SCH_KBN,");
            sql.addSql("   PUC_TODO_DSP");
            sql.addSql(" from");
            sql.addSql("   PRJ_USER_CONF");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            sql.addIntValue(userSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = __getPrjUserConfFromRs(rs);
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
     * <p>Delete PRJ_USER_CONF
     * @param userSid 削除するユーザSID
     * @return int 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   PRJ_USER_CONF");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            sql.addIntValue(userSid);

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
     * <p>Create PRJ_USER_CONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created PrjUserConfModel
     * @throws SQLException SQL実行例外
     */
    private PrjUserConfModel __getPrjUserConfFromRs(ResultSet rs) throws SQLException {
        PrjUserConfModel bean = new PrjUserConfModel();
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setPucPrjCnt(rs.getInt("PUC_PRJ_CNT"));
        bean.setPucTodoCnt(rs.getInt("PUC_TODO_CNT"));
        bean.setPucAuid(rs.getInt("PUC_AUID"));
        bean.setPucAdate(UDate.getInstanceTimestamp(rs.getTimestamp("PUC_ADATE")));
        bean.setPucEuid(rs.getInt("PUC_EUID"));
        bean.setPucEdate(UDate.getInstanceTimestamp(rs.getTimestamp("PUC_EDATE")));
        bean.setPucTodoDate(rs.getInt("PUC_TODO_DATE"));
        bean.setPucTodoProject(rs.getInt("PUC_TODO_PROJECT"));
        bean.setPucTodoStatus(rs.getInt("PUC_TODO_STATUS"));
        bean.setPucPrjProject(rs.getInt("PUC_PRJ_PROJECT"));
        bean.setPucMainDate(rs.getInt("PUC_MAIN_DATE"));
        bean.setPucMainStatus(rs.getInt("PUC_MAIN_STATUS"));
        bean.setPucMainMember(rs.getInt("PUC_MAIN_MENBER"));
        bean.setPucDefDsp(rs.getInt("PUC_DEF_DSP"));
        bean.setPucSchKbn(rs.getInt("PUC_SCH_KBN"));
        bean.setPucTodoDsp(rs.getInt("PUC_TODO_DSP"));

        return bean;
    }
}