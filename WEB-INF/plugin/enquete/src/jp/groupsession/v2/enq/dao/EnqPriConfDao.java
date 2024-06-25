package jp.groupsession.v2.enq.dao;

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
import jp.groupsession.v2.enq.model.EnqPriConfModel;

/**
 * <p>ENQ_PRI_CONF Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class EnqPriConfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(EnqPriConfDao.class);

    /**
     * <p>Default Constructor
     */
    public EnqPriConfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public EnqPriConfDao(Connection con) {
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
            sql.addSql("drop table ENQ_PRI_CONF");

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
            sql.addSql(" create table ENQ_PRI_CONF (");
            sql.addSql("   USR_SID integer not null,");
            sql.addSql("   EPC_MAIN_DSP integer not null,");
            sql.addSql("   EPC_LIST_CNT integer not null,");
            sql.addSql("   EPC_FOLDER_SELECT integer not null,");
            sql.addSql("   EPC_CAN_ANSWER integer not null,");
            sql.addSql("   EPC_AUID integer not null,");
            sql.addSql("   EPC_ADATE timestamp not null,");
            sql.addSql("   EPC_EUID integer not null,");
            sql.addSql("   EPC_EDATE timestamp not null,");
            sql.addSql("   EPC_DSPCNT_MAIN integer not null,");
            sql.addSql("   EPC_DSP_CHECKED integer not null,");
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
     * <p>Insert ENQ_PRI_CONF Data Binding JavaBean
     * @param bean ENQ_PRI_CONF Data Binding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(EnqPriConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" ENQ_PRI_CONF(");
            sql.addSql("   USR_SID,");
            sql.addSql("   EPC_MAIN_DSP,");
            sql.addSql("   EPC_LIST_CNT,");
            sql.addSql("   EPC_FOLDER_SELECT,");
            sql.addSql("   EPC_CAN_ANSWER,");
            sql.addSql("   EPC_AUID,");
            sql.addSql("   EPC_ADATE,");
            sql.addSql("   EPC_EUID,");
            sql.addSql("   EPC_EDATE,");
            sql.addSql("   EPC_DSPCNT_MAIN,");
            sql.addSql("   EPC_DSP_CHECKED");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getEpcMainDsp());
            sql.addIntValue(bean.getEpcListCnt());
            sql.addIntValue(bean.getEpcListFolder());
            sql.addIntValue(bean.getEpcCanAnswer());
            sql.addIntValue(bean.getEpcAuid());
            sql.addDateValue(bean.getEpcAdate());
            sql.addIntValue(bean.getEpcEuid());
            sql.addDateValue(bean.getEpcEdate());
            sql.addIntValue(bean.getEpcDspcntMain());
            sql.addIntValue(bean.getEpcDspChecked());
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
     * <p>Update ENQ_PRI_CONF Data Binding JavaBean
     * @param bean ENQ_PRI_CONF Data Binding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(EnqPriConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ENQ_PRI_CONF");
            sql.addSql(" set ");
            sql.addSql("   EPC_MAIN_DSP=?,");
            sql.addSql("   EPC_LIST_CNT=?,");
            sql.addSql("   EPC_FOLDER_SELECT=?,");
            sql.addSql("   EPC_CAN_ANSWER=?,");
            sql.addSql("   EPC_AUID=?,");
            sql.addSql("   EPC_ADATE=?,");
            sql.addSql("   EPC_EUID=?,");
            sql.addSql("   EPC_EDATE=?,");
            sql.addSql("   EPC_DSPCNT_MAIN=?,");
            sql.addSql("   EPC_DSP_CHECKED=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getEpcMainDsp());
            sql.addIntValue(bean.getEpcListCnt());
            sql.addIntValue(bean.getEpcAuid());
            sql.addDateValue(bean.getEpcAdate());
            sql.addIntValue(bean.getEpcEuid());
            sql.addDateValue(bean.getEpcEdate());
            sql.addIntValue(bean.getEpcDspcntMain());
            sql.addIntValue(bean.getEpcDspChecked());
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
     * <br>[機  能] 初期表示フォルダを更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param bean ENQ_PRI_CONF Data Binding JavaBean
     * @param usrSid ユーザSID
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateListFolder(EnqPriConfModel bean, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ENQ_PRI_CONF");
            sql.addSql(" set ");
            sql.addSql("   EPC_FOLDER_SELECT=?,");
            sql.addSql("   EPC_CAN_ANSWER=?,");
            sql.addSql("   EPC_EUID=?,");
            sql.addSql("   EPC_EDATE=?");
            sql.addSql(" where");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getEpcListFolder());
            sql.addIntValue(bean.getEpcCanAnswer());
            sql.addIntValue(bean.getEpcEuid());
            sql.addDateValue(bean.getEpcEdate());
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
     * <br>[機  能] 一覧表示件数を更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param bean ENQ_PRI_CONF Data Binding JavaBean
     * @param usrSid ユーザSID
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateListCnt(EnqPriConfModel bean, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ENQ_PRI_CONF");
            sql.addSql(" set ");
            sql.addSql("   EPC_LIST_CNT=?,");
            sql.addSql("   EPC_EUID=?,");
            sql.addSql("   EPC_EDATE=?");
            sql.addSql(" where");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getEpcListCnt());
            sql.addIntValue(bean.getEpcEuid());
            sql.addDateValue(bean.getEpcEdate());
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
     * <br>[機  能] メイン表示フラグを更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param bean ENQ_PRI_CONF Data Binding JavaBean
     * @param usrSid ユーザSID
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateMainDsp(EnqPriConfModel bean, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ENQ_PRI_CONF");
            sql.addSql(" set");
            sql.addSql("   EPC_MAIN_DSP=?,");
            sql.addSql("   EPC_DSPCNT_MAIN=?,");
            sql.addSql("   EPC_DSP_CHECKED=?,");
            sql.addSql("   EPC_EUID=?,");
            sql.addSql("   EPC_EDATE=?");
            sql.addSql(" where");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getEpcMainDsp());
            sql.addIntValue(bean.getEpcDspcntMain());
            sql.addIntValue(bean.getEpcDspChecked());
            sql.addIntValue(bean.getEpcEuid());
            sql.addDateValue(bean.getEpcEdate());
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
     * <p>Select ENQ_PRI_CONF All Data
     * @return List in ENQ_PRI_CONFModel
     * @throws SQLException SQL実行例外
     */
    public List<EnqPriConfModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<EnqPriConfModel> ret = new ArrayList<EnqPriConfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   EPC_MAIN_DSP,");
            sql.addSql("   EPC_LIST_CNT,");
            sql.addSql("   EPC_FOLDER_SELECT,");
            sql.addSql("   EPC_CAN_ANSWER,");
            sql.addSql("   EPC_AUID,");
            sql.addSql("   EPC_ADATE,");
            sql.addSql("   EPC_EUID,");
            sql.addSql("   EPC_EDATE,");
            sql.addSql("   EPC_DSPCNT_MAIN,");
            sql.addSql("   EPC_DSP_CHECKED");
            sql.addSql(" from ");
            sql.addSql("   ENQ_PRI_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getEnqPriConfFromRs(rs));
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
     * <p>Select ENQ_PRI_CONF
     * @param usrSid USR_SID
     * @return ENQ_PRI_CONFModel
     * @throws SQLException SQL実行例外
     */
    public EnqPriConfModel select(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        EnqPriConfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   EPC_MAIN_DSP,");
            sql.addSql("   EPC_LIST_CNT,");
            sql.addSql("   EPC_FOLDER_SELECT,");
            sql.addSql("   EPC_CAN_ANSWER,");
            sql.addSql("   EPC_AUID,");
            sql.addSql("   EPC_ADATE,");
            sql.addSql("   EPC_EUID,");
            sql.addSql("   EPC_EDATE,");
            sql.addSql("   EPC_DSPCNT_MAIN,");
            sql.addSql("   EPC_DSP_CHECKED");
            sql.addSql(" from");
            sql.addSql("   ENQ_PRI_CONF");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getEnqPriConfFromRs(rs);
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
     * <p>Delete ENQ_PRI_CONF
     * @param usrSid USR_SID
     * @return 削除件数
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
            sql.addSql("   ENQ_PRI_CONF");
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
     * <p>Create ENQ_PRI_CONF Data Binding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created EnqPriConfModel
     * @throws SQLException SQL実行例外
     */
    private EnqPriConfModel __getEnqPriConfFromRs(ResultSet rs) throws SQLException {
        EnqPriConfModel bean = new EnqPriConfModel();
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setEpcMainDsp(rs.getInt("EPC_MAIN_DSP"));
        bean.setEpcListCnt(rs.getInt("EPC_LIST_CNT"));
        bean.setEpcListFolder(rs.getInt("EPC_FOLDER_SELECT"));
        bean.setEpcCanAnswer(rs.getInt("EPC_CAN_ANSWER"));
        bean.setEpcAuid(rs.getInt("EPC_AUID"));
        bean.setEpcAdate(UDate.getInstanceTimestamp(rs.getTimestamp("EPC_ADATE")));
        bean.setEpcEuid(rs.getInt("EPC_EUID"));
        bean.setEpcEdate(UDate.getInstanceTimestamp(rs.getTimestamp("EPC_EDATE")));
        bean.setEpcDspcntMain(rs.getInt("EPC_DSPCNT_MAIN"));
        bean.setEpcDspChecked(rs.getInt("EPC_DSP_CHECKED"));
        return bean;
    }
}
