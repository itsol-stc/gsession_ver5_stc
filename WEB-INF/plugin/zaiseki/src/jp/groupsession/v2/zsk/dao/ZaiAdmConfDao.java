package jp.groupsession.v2.zsk.dao;

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
import jp.groupsession.v2.zsk.model.ZaiAdmConfModel;

/**
 * <p>ZAI_ADM_CONF Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class ZaiAdmConfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ZaiAdmConfDao.class);

    /**
     * <p>Default Constructor
     */
    public ZaiAdmConfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public ZaiAdmConfDao(Connection con) {
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
            sql.addSql("drop table ZAI_ADM_CONF");

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
            sql.addSql(" create table ZAI_ADM_CONF (");
            sql.addSql("   ZAC_NAISEN_KBN NUMBER(10,0) not null,");
            sql.addSql("   ZAC_AID NUMBER(10,0) not null,");
            sql.addSql("   ZAC_ADATE varchar(23) not null,");
            sql.addSql("   ZAC_EID NUMBER(10,0) not null,");
            sql.addSql("   ZAC_EDATE varchar(23) not null,");
            sql.addSql("   ZAC_SORT_KBN NUMBER(10,0) not null,");
            sql.addSql("   ZAC_SORT_KEY1 NUMBER(10,0) not null,");
            sql.addSql("   ZAC_SORT_ORDER1 NUMBER(10,0) not null,");
            sql.addSql("   ZAC_SORT_KEY2 NUMBER(10,0) not null,");
            sql.addSql("   ZAC_SORT_ORDER2 NUMBER(10,0) not null");
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
     * <p>Insert ZAI_ADM_CONF Data Bindding JavaBean
     * @param bean ZAI_ADM_CONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(ZaiAdmConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" ZAI_ADM_CONF(");
            sql.addSql("   ZAC_NAISEN_KBN,");
            sql.addSql("   ZAC_AID,");
            sql.addSql("   ZAC_ADATE,");
            sql.addSql("   ZAC_EID,");
            sql.addSql("   ZAC_EDATE,");
            sql.addSql("   ZAC_SORT_KBN,");
            sql.addSql("   ZAC_SORT_KEY1,");
            sql.addSql("   ZAC_SORT_ORDER1,");
            sql.addSql("   ZAC_SORT_KEY2,");
            sql.addSql("   ZAC_SORT_ORDER2");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getZacNaisenKbn());
            sql.addIntValue(bean.getZacAid());
            sql.addDateValue(bean.getZacAdate());
            sql.addIntValue(bean.getZacEid());
            sql.addDateValue(bean.getZacEdate());
            sql.addIntValue(bean.getZacSortKbn());
            sql.addIntValue(bean.getZacSortKey1());
            sql.addIntValue(bean.getZacSortOrder1());
            sql.addIntValue(bean.getZacSortKey2());
            sql.addIntValue(bean.getZacSortOrder2());
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
     * <p>Update ZAI_ADM_CONF Data Bindding JavaBean
     * @param bean ZAI_ADM_CONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     * @return int 更新件数
     */
    public int update(ZaiAdmConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ZAI_ADM_CONF");
            sql.addSql(" set ");
            sql.addSql("   ZAC_NAISEN_KBN=?,");
            sql.addSql("   ZAC_AID=?,");
            sql.addSql("   ZAC_ADATE=?,");
            sql.addSql("   ZAC_EID=?,");
            sql.addSql("   ZAC_EDATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getZacNaisenKbn());
            sql.addIntValue(bean.getZacAid());
            sql.addDateValue(bean.getZacAdate());
            sql.addIntValue(bean.getZacEid());
            sql.addDateValue(bean.getZacEdate());

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
     * <p>Update ZAI_ADM_CONF Data Bindding JavaBean
     * @param bean ZAI_ADM_CONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateSort(ZaiAdmConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ZAI_ADM_CONF");
            sql.addSql(" set ");
            sql.addSql("   ZAC_EID=?,");
            sql.addSql("   ZAC_EDATE=?,");
            sql.addSql("   ZAC_SORT_KBN=?,");
            sql.addSql("   ZAC_SORT_KEY1=?,");
            sql.addSql("   ZAC_SORT_ORDER1=?,");
            sql.addSql("   ZAC_SORT_KEY2=?,");
            sql.addSql("   ZAC_SORT_ORDER2=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getZacEid());
            sql.addDateValue(bean.getZacEdate());
            sql.addIntValue(bean.getZacSortKbn());
            sql.addIntValue(bean.getZacSortKey1());
            sql.addIntValue(bean.getZacSortOrder1());
            sql.addIntValue(bean.getZacSortKey2());
            sql.addIntValue(bean.getZacSortOrder2());

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
     * <p>Select ZAI_ADM_CONF All Data
     * @return List in ZAI_ADM_CONFModel
     * @throws SQLException SQL実行例外
     */
    public List<ZaiAdmConfModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ZaiAdmConfModel> ret = new ArrayList<ZaiAdmConfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   ZAC_NAISEN_KBN,");
            sql.addSql("   ZAC_AID,");
            sql.addSql("   ZAC_ADATE,");
            sql.addSql("   ZAC_EID,");
            sql.addSql("   ZAC_EDATE,");
            sql.addSql("   ZAC_SORT_KBN,");
            sql.addSql("   ZAC_SORT_KEY1,");
            sql.addSql("   ZAC_SORT_ORDER1,");
            sql.addSql("   ZAC_SORT_KEY2,");
            sql.addSql("   ZAC_SORT_ORDER2");
            sql.addSql(" from ");
            sql.addSql("   ZAI_ADM_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getZaiAdmConfFromRs(rs));
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
     * <p>Select ZAI_ADM_CONF
     * @return ZAI_ADM_CONFModel
     * @throws SQLException SQL実行例外
     */
    public ZaiAdmConfModel getData() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ZaiAdmConfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   ZAC_NAISEN_KBN,");
            sql.addSql("   ZAC_AID,");
            sql.addSql("   ZAC_ADATE,");
            sql.addSql("   ZAC_EID,");
            sql.addSql("   ZAC_EDATE,");
            sql.addSql("   ZAC_SORT_KBN,");
            sql.addSql("   ZAC_SORT_KEY1,");
            sql.addSql("   ZAC_SORT_ORDER1,");
            sql.addSql("   ZAC_SORT_KEY2,");
            sql.addSql("   ZAC_SORT_ORDER2");
            sql.addSql(" from ");
            sql.addSql("   ZAI_ADM_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getZaiAdmConfFromRs(rs);
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
     * <p>Delete ZAI_ADM_CONF
     * @param bean ZAI_ADM_CONF Model
     * @throws SQLException SQL実行例外
     * @return int 削除件数
     */
    public int delete(ZaiAdmConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   ZAI_ADM_CONF");

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
     * <p>Create ZAI_ADM_CONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created ZaiAdmConfModel
     * @throws SQLException SQL実行例外
     */
    private ZaiAdmConfModel __getZaiAdmConfFromRs(ResultSet rs) throws SQLException {
        ZaiAdmConfModel bean = new ZaiAdmConfModel();
        bean.setZacNaisenKbn(rs.getInt("ZAC_NAISEN_KBN"));
        bean.setZacAid(rs.getInt("ZAC_AID"));
        bean.setZacAdate(UDate.getInstanceTimestamp(rs.getTimestamp("ZAC_ADATE")));
        bean.setZacEid(rs.getInt("ZAC_EID"));
        bean.setZacEdate(UDate.getInstanceTimestamp(rs.getTimestamp("ZAC_EDATE")));
        bean.setZacSortKbn(rs.getInt("ZAC_SORT_KBN"));
        bean.setZacSortKey1(rs.getInt("ZAC_SORT_KEY1"));
        bean.setZacSortOrder1(rs.getInt("ZAC_SORT_ORDER1"));
        bean.setZacSortKey2(rs.getInt("ZAC_SORT_KEY2"));
        bean.setZacSortOrder2(rs.getInt("ZAC_SORT_ORDER2"));
        return bean;
    }
}
