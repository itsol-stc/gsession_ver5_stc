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
import jp.groupsession.v2.cht.model.ChtAdmConfModel;

/**
 * <p>CHT_ADM_CONF Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class ChtAdmConfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ChtAdmConfDao.class);

    /**
     * <p>Default Constructor
     */
    public ChtAdmConfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public ChtAdmConfDao(Connection con) {
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
            sql.addSql("drop table CHT_ADM_CONF");

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
            sql.addSql(" create table CHT_ADM_CONF (");
            sql.addSql("   CAC_CHAT_FLG integer not null,");
            sql.addSql("   CAC_GROUP_FLG integer not null,");
            sql.addSql("   CAC_GROUP_KBN integer not null,");
            sql.addSql("   CAC_READ_FLG integer not null,");
            sql.addSql("   CAC_ATDEL_FLG integer not null,");
            sql.addSql("   CAC_ATDEL_Y integer,");
            sql.addSql("   CAC_ATDEL_M integer,");
            sql.addSql("   CAC_AUID integer not null,");
            sql.addSql("   CAC_ADATE timestamp not null,");
            sql.addSql("   CAC_EUID integer not null,");
            sql.addSql("   CAC_EDATE timestamp not null");
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
     * <p>Insert CHT_ADM_CONF Data Bindding JavaBean
     * @param bean CHT_ADM_CONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(ChtAdmConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CHT_ADM_CONF(");
            sql.addSql("   CAC_CHAT_FLG,");
            sql.addSql("   CAC_GROUP_FLG,");
            sql.addSql("   CAC_GROUP_KBN,");
            sql.addSql("   CAC_READ_FLG,");
            sql.addSql("   CAC_ATDEL_FLG,");
            sql.addSql("   CAC_ATDEL_Y,");
            sql.addSql("   CAC_ATDEL_M,");
            sql.addSql("   CAC_AUID,");
            sql.addSql("   CAC_ADATE,");
            sql.addSql("   CAC_EUID,");
            sql.addSql("   CAC_EDATE");
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
            sql.addIntValue(bean.getCacChatFlg());
            sql.addIntValue(bean.getCacGroupFlg());
            sql.addIntValue(bean.getCacGroupKbn());
            sql.addIntValue(bean.getCacReadFlg());
            sql.addIntValue(bean.getCacAtdelFlg());
            sql.addIntValue(bean.getCacAtdelY());
            sql.addIntValue(bean.getCacAtdelM());
            sql.addIntValue(bean.getCacAuid());
            sql.addDateValue(bean.getCacAdate());
            sql.addIntValue(bean.getCacEuid());
            sql.addDateValue(bean.getCacEdate());
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
     * <p>Update CHT_ADM_CONF Data Bindding JavaBean
     * @param bean CHT_ADM_CONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(ChtAdmConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CHT_ADM_CONF");
            sql.addSql(" set ");
            sql.addSql("   CAC_CHAT_FLG=?,");
            sql.addSql("   CAC_GROUP_FLG=?,");
            sql.addSql("   CAC_GROUP_KBN=?,");
            sql.addSql("   CAC_READ_FLG=?,");
            sql.addSql("   CAC_ATDEL_FLG=?,");
            sql.addSql("   CAC_ATDEL_Y=?,");
            sql.addSql("   CAC_ATDEL_M=?,");
            sql.addSql("   CAC_EUID=?,");
            sql.addSql("   CAC_EDATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCacChatFlg());
            sql.addIntValue(bean.getCacGroupFlg());
            sql.addIntValue(bean.getCacGroupKbn());
            sql.addIntValue(bean.getCacReadFlg());
            sql.addIntValue(bean.getCacAtdelFlg());
            sql.addIntValue(bean.getCacAtdelY());
            sql.addIntValue(bean.getCacAtdelM());
            sql.addIntValue(bean.getCacEuid());
            sql.addDateValue(bean.getCacEdate());

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
     * <p>Update CHT_ADM_CONF Data Bindding JavaBean
     * @param bean CHT_ADM_CONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateAutoDel(ChtAdmConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CHT_ADM_CONF");
            sql.addSql(" set ");
            sql.addSql("   CAC_ATDEL_FLG=?,");
            sql.addSql("   CAC_ATDEL_Y=?,");
            sql.addSql("   CAC_ATDEL_M=?,");
            sql.addSql("   CAC_EUID=?,");
            sql.addSql("   CAC_EDATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCacAtdelFlg());
            sql.addIntValue(bean.getCacAtdelY());
            sql.addIntValue(bean.getCacAtdelM());
            sql.addIntValue(bean.getCacEuid());
            sql.addDateValue(bean.getCacEdate());

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
     * <p>Select CHT_ADM_CONF All Data
     * @return List in CHT_ADM_CONFModel
     * @throws SQLException SQL実行例外
     */
    public List<ChtAdmConfModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ChtAdmConfModel> ret = new ArrayList<ChtAdmConfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CAC_CHAT_FLG,");
            sql.addSql("   CAC_GROUP_FLG,");
            sql.addSql("   CAC_GROUP_KBN,");
            sql.addSql("   CAC_READ_FLG,");
            sql.addSql("   CAC_ATDEL_FLG,");
            sql.addSql("   CAC_ATDEL_Y,");
            sql.addSql("   CAC_ATDEL_M,");
            sql.addSql("   CAC_AUID,");
            sql.addSql("   CAC_ADATE,");
            sql.addSql("   CAC_EUID,");
            sql.addSql("   CAC_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CHT_ADM_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getChtAdmConfFromRs(rs));
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
     * <p>Create CHT_ADM_CONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created ChtAdmConfModel
     * @throws SQLException SQL実行例外
     */
    private ChtAdmConfModel __getChtAdmConfFromRs(ResultSet rs) throws SQLException {
        ChtAdmConfModel bean = new ChtAdmConfModel();
        bean.setCacChatFlg(rs.getInt("CAC_CHAT_FLG"));
        bean.setCacGroupFlg(rs.getInt("CAC_GROUP_FLG"));
        bean.setCacGroupKbn(rs.getInt("CAC_GROUP_KBN"));
        bean.setCacReadFlg(rs.getInt("CAC_READ_FLG"));
        bean.setCacAtdelFlg(rs.getInt("CAC_ATDEL_FLG"));
        bean.setCacAtdelY(rs.getInt("CAC_ATDEL_Y"));
        bean.setCacAtdelM(rs.getInt("CAC_ATDEL_M"));
        bean.setCacAuid(rs.getInt("CAC_AUID"));
        bean.setCacAdate(UDate.getInstanceTimestamp(rs.getTimestamp("CAC_ADATE")));
        bean.setCacEuid(rs.getInt("CAC_EUID"));
        bean.setCacEdate(UDate.getInstanceTimestamp(rs.getTimestamp("CAC_EDATE")));
        return bean;
    }
}
