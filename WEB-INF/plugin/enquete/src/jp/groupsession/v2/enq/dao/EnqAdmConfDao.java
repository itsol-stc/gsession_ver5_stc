package jp.groupsession.v2.enq.dao;

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
import jp.groupsession.v2.enq.GSConstEnquete;
import jp.groupsession.v2.enq.model.EnqAdmConfModel;

/**
 * <p>ENQ_ADM_CONF Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class EnqAdmConfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(EnqAdmConfDao.class);

    /**
     * <p>Default Constructor
     */
    public EnqAdmConfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public EnqAdmConfDao(Connection con) {
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
            sql.addSql("drop table ENQ_ADM_CONF");

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
            sql.addSql(" create table ENQ_ADM_CONF (");
            sql.addSql("   EAC_KBN_TAISYO integer,");
            sql.addSql("   EAC_MAIN_DSP_USE integer not null,");
            sql.addSql("   EAC_MAIN_DSP integer not null,");
            sql.addSql("   EAC_LIST_CNT_USE integer not null,");
            sql.addSql("   EAC_LIST_CNT integer not null,");
            sql.addSql("   EAC_FOLDER_USE integer not null,");
            sql.addSql("   EAC_FOLDER_SELECT integer not null,");
            sql.addSql("   EAC_CAN_ANSWER  integer not null,");
            sql.addSql("   EAC_AUID integer not null,");
            sql.addSql("   EAC_ADATE timestamp not null,");
            sql.addSql("   EAC_EUID integer not null,");
            sql.addSql("   EAC_EDATE timestamp not null,");
            sql.addSql("   EAC_DSPCNT_MAIN integer not null,");
            sql.addSql("   EAC_DSP_CHECKED integer not null");
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
     * <p>Insert ENQ_ADM_CONF Data Binding JavaBean
     * @param bean ENQ_ADM_CONF Data Binding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(EnqAdmConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" ENQ_ADM_CONF(");
            sql.addSql("   EAC_KBN_TAISYO,");
            sql.addSql("   EAC_MAIN_DSP_USE,");
            sql.addSql("   EAC_MAIN_DSP,");
            sql.addSql("   EAC_LIST_CNT_USE,");
            sql.addSql("   EAC_LIST_CNT,");
            sql.addSql("   EAC_FOLDER_USE,");
            sql.addSql("   EAC_FOLDER_SELECT,");
            sql.addSql("   EAC_CAN_ANSWER,");
            sql.addSql("   EAC_AUID,");
            sql.addSql("   EAC_ADATE,");
            sql.addSql("   EAC_EUID,");
            sql.addSql("   EAC_EDATE,");
            sql.addSql("   EAC_DSPCNT_MAIN,");
            sql.addSql("   EAC_DSP_CHECKED");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getEacKbnTaisyo());
            sql.addIntValue(bean.getEacMainDspUse());
            sql.addIntValue(bean.getEacMainDsp());
            sql.addIntValue(bean.getEacListCntUse());
            sql.addIntValue(bean.getEacListCnt());
            sql.addIntValue(bean.getEacFolderUse());
            sql.addIntValue(bean.getEacFolderSelect());
            sql.addIntValue(bean.getEacCanAnswer());
            sql.addIntValue(bean.getEacAuid());
            sql.addDateValue(bean.getEacAdate());
            sql.addIntValue(bean.getEacEuid());
            sql.addDateValue(bean.getEacEdate());
            sql.addIntValue(bean.getEacDspcntMain());
            sql.addIntValue(bean.getEacDspChecked());
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
     * <p>Update ENQ_ADM_CONF Data Binding JavaBean
     * @param bean ENQ_ADM_CONF Data Binding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(EnqAdmConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ENQ_ADM_CONF");
            sql.addSql(" set ");
            sql.addSql("   EAC_KBN_TAISYO=?,");
            sql.addSql("   EAC_MAIN_DSP_USE=?,");
            sql.addSql("   EAC_MAIN_DSP=?,");
            sql.addSql("   EAC_LIST_CNT_USE=?,");
            sql.addSql("   EAC_LIST_CNT=?,");
            sql.addSql("   EAC_FOLDER_USE=?,");
            sql.addSql("   EAC_FOLDER_SELECT=?,");
            sql.addSql("   EAC_CAN_ANSWER=?,");
            sql.addSql("   EAC_AUID=?,");
            sql.addSql("   EAC_ADATE=?,");
            sql.addSql("   EAC_EUID=?,");
            sql.addSql("   EAC_EDATE=?,");
            sql.addSql("   EAC_DSPCNT_MAIN=?,");
            sql.addSql("   EAC_DSP_CHECKED=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getEacKbnTaisyo());
            sql.addIntValue(bean.getEacMainDspUse());
            sql.addIntValue(bean.getEacMainDsp());
            sql.addIntValue(bean.getEacListCntUse());
            sql.addIntValue(bean.getEacListCnt());
            sql.addIntValue(bean.getEacFolderUse());
            sql.addIntValue(bean.getEacFolderSelect());
            sql.addIntValue(bean.getEacCanAnswer());
            sql.addIntValue(bean.getEacAuid());
            sql.addDateValue(bean.getEacAdate());
            sql.addIntValue(bean.getEacEuid());
            sql.addDateValue(bean.getEacEdate());
            sql.addIntValue(bean.getEacDspcntMain());
            sql.addIntValue(bean.getEacDspChecked());

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
     * <br>[機  能] アンケート作成対象者区分更新処理
     * <br>[解  説]
     * <br>[備  考]
     * @param bean ENQ_ADM_CONF Data Binding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateKbnTaisyo(EnqAdmConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ENQ_ADM_CONF");
            sql.addSql(" set ");
            sql.addSql("   EAC_KBN_TAISYO=?,");
            sql.addSql("   EAC_EUID=?,");
            sql.addSql("   EAC_EDATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getEacKbnTaisyo());
            sql.addIntValue(bean.getEacEuid());
            sql.addDateValue(bean.getEacEdate());

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
     * <br>[機  能] 一覧表示件数/初期表示フォルダ更新処理
     * <br>[解  説]
     * <br>[備  考] 一覧表示件数区分 = 「管理者が設定する」の時、一覧表示件数のフィールドを更新する
     * @param bean ENQ_ADM_CONF Data Binding JavaBean
     * @param cntKbn 一覧表示件数区分
     * @param mainKbn メイン表示区分
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateListDsp(EnqAdmConfModel bean, int cntKbn, int mainKbn) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ENQ_ADM_CONF");
            sql.addSql(" set ");
            sql.addSql("   EAC_LIST_CNT_USE=?,");
            if (cntKbn == GSConstEnquete.CONF_LIST_USE_LIMIT) {
                sql.addSql("   EAC_LIST_CNT=?,");
            }
            sql.addSql("   EAC_FOLDER_USE=?,");
            if (cntKbn == GSConstEnquete.CONF_LIST_USE_LIMIT) {
                sql.addSql("   EAC_FOLDER_SELECT=?,");
                sql.addSql("   EAC_CAN_ANSWER=?,");
            }

            sql.addSql("   EAC_MAIN_DSP_USE=?,");
            if (mainKbn == GSConstEnquete.CONF_MAIN_DSP_USE_LIMIT) {
                sql.addSql("   EAC_MAIN_DSP=?,");
                sql.addSql("   EAC_DSPCNT_MAIN=?,");
                sql.addSql("   EAC_DSP_CHECKED=?,");
            }

            sql.addSql("   EAC_EUID=?,");
            sql.addSql("   EAC_EDATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getEacListCntUse());
            if (cntKbn == GSConstEnquete.CONF_LIST_USE_LIMIT) {
                sql.addIntValue(bean.getEacListCnt());
            }
            sql.addIntValue(bean.getEacFolderUse());
            if (cntKbn == GSConstEnquete.CONF_LIST_USE_LIMIT) {
                sql.addIntValue(bean.getEacFolderSelect());
                sql.addIntValue(bean.getEacCanAnswer());
            }
            sql.addIntValue(bean.getEacMainDspUse());
            if (mainKbn == GSConstEnquete.CONF_MAIN_DSP_USE_LIMIT) {
                sql.addIntValue(bean.getEacMainDsp());
                sql.addIntValue(bean.getEacDspcntMain());
                sql.addIntValue(bean.getEacDspChecked());
            }
            sql.addIntValue(bean.getEacEuid());
            sql.addDateValue(bean.getEacEdate());

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
     * <p>Select ENQ_ADM_CONF Count Data
     * @return List in ENQ_ADM_CONFModel
     * @throws SQLException SQL実行例外
     */
    public int count() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int count = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(*) CNT");
            sql.addSql(" from ");
            sql.addSql("   ENQ_ADM_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("CNT");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <p>Select ENQ_ADM_CONF All Data
     * @return List in ENQ_ADM_CONFModel
     * @throws SQLException SQL実行例外
     */
    public EnqAdmConfModel select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        EnqAdmConfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   EAC_KBN_TAISYO,");
            sql.addSql("   EAC_MAIN_DSP_USE,");
            sql.addSql("   EAC_MAIN_DSP,");
            sql.addSql("   EAC_LIST_CNT_USE,");
            sql.addSql("   EAC_LIST_CNT,");
            sql.addSql("   EAC_FOLDER_USE,");
            sql.addSql("   EAC_FOLDER_SELECT,");
            sql.addSql("   EAC_CAN_ANSWER,");
            sql.addSql("   EAC_AUID,");
            sql.addSql("   EAC_ADATE,");
            sql.addSql("   EAC_EUID,");
            sql.addSql("   EAC_EDATE,");
            sql.addSql("   EAC_DSPCNT_MAIN,");
            sql.addSql("   EAC_DSP_CHECKED");
            sql.addSql(" from ");
            sql.addSql("   ENQ_ADM_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getEnqAdmConfFromRs(rs);
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
     * <p>Create ENQ_ADM_CONF Data Binding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created EnqAdmConfModel
     * @throws SQLException SQL実行例外
     */
    private EnqAdmConfModel __getEnqAdmConfFromRs(ResultSet rs) throws SQLException {
        EnqAdmConfModel bean = new EnqAdmConfModel();
        bean.setEacKbnTaisyo(rs.getInt("EAC_KBN_TAISYO"));
        bean.setEacMainDspUse(rs.getInt("EAC_MAIN_DSP_USE"));
        bean.setEacMainDsp(rs.getInt("EAC_MAIN_DSP"));
        bean.setEacListCntUse(rs.getInt("EAC_LIST_CNT_USE"));
        bean.setEacListCnt(rs.getInt("EAC_LIST_CNT"));
        bean.setEacFolderUse(rs.getInt("EAC_FOLDER_USE"));
        bean.setEacFolderSelect(rs.getInt("EAC_FOLDER_SELECT"));
        bean.setEacCanAnswer(rs.getInt("EAC_CAN_ANSWER"));
        bean.setEacAuid(rs.getInt("EAC_AUID"));
        bean.setEacAdate(UDate.getInstanceTimestamp(rs.getTimestamp("EAC_ADATE")));
        bean.setEacEuid(rs.getInt("EAC_EUID"));
        bean.setEacEdate(UDate.getInstanceTimestamp(rs.getTimestamp("EAC_EDATE")));
        bean.setEacDspcntMain(rs.getInt("EAC_DSPCNT_MAIN"));
        bean.setEacDspChecked(rs.getInt("EAC_DSP_CHECKED"));
        return bean;
    }
}
