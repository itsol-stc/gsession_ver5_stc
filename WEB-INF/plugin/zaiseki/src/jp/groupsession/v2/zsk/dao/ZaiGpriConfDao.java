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
import jp.groupsession.v2.zsk.model.ZaiGpriConfModel;

/**
 * <p>ZAI_GPRI_CONF Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class ZaiGpriConfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ZaiGpriConfDao.class);

    /**
     * <p>Default Constructor
     */
    public ZaiGpriConfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public ZaiGpriConfDao(Connection con) {
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
            sql.addSql("drop table ZAI_GPRI_CONF");

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
            sql.addSql(" create table ZAI_GPRI_CONF (");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   ZGC_GRP NUMBER(10,0) not null,");
            sql.addSql("   ZGC_GKBN NUMBER(10,0) not null,");
            sql.addSql("   ZGC_VIEW_KBN NUMBER(10,0) not null,");
            sql.addSql("   ZGC_SORT_KEY1 NUMBER(10,0) not null,");
            sql.addSql("   ZGC_SORT_ORDER1 NUMBER(10,0) not null,");
            sql.addSql("   ZGC_SORT_KEY2 NUMBER(10,0) not null,");
            sql.addSql("   ZGC_SORT_ORDER2 NUMBER(10,0) not null,");
            sql.addSql("   ZGC_SCH_VIEW_DF NUMBER(10,0) not null,");
            sql.addSql("   ZGC_AUID NUMBER(10,0) not null,");
            sql.addSql("   ZGC_ADATE varchar(23) not null,");
            sql.addSql("   ZGC_EUID NUMBER(10,0) not null,");
            sql.addSql("   ZGC_EDATE varchar(23) not null,");
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
     * <p>Insert ZAI_GPRI_CONF Data Bindding JavaBean
     * @param bean ZAI_GPRI_CONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(ZaiGpriConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" ZAI_GPRI_CONF(");
            sql.addSql("   USR_SID,");
            sql.addSql("   ZGC_GRP,");
            sql.addSql("   ZGC_GKBN,");
            sql.addSql("   ZGC_VIEW_KBN,");
            sql.addSql("   ZGC_SORT_KEY1,");
            sql.addSql("   ZGC_SORT_ORDER1,");
            sql.addSql("   ZGC_SORT_KEY2,");
            sql.addSql("   ZGC_SORT_ORDER2,");
            sql.addSql("   ZGC_SCH_VIEW_DF,");
            sql.addSql("   ZGC_AUID,");
            sql.addSql("   ZGC_ADATE,");
            sql.addSql("   ZGC_EUID,");
            sql.addSql("   ZGC_EDATE");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getZgcGrp());
            sql.addIntValue(bean.getZgcGkbn());
            sql.addIntValue(bean.getZgcViewKbn());
            sql.addIntValue(bean.getZgcSortKey1());
            sql.addIntValue(bean.getZgcSortOrder1());
            sql.addIntValue(bean.getZgcSortKey2());
            sql.addIntValue(bean.getZgcSortOrder2());
            sql.addIntValue(bean.getZgcSchViewDf());
            sql.addIntValue(bean.getZgcAuid());
            sql.addDateValue(bean.getZgcAdate());
            sql.addIntValue(bean.getZgcEuid());
            sql.addDateValue(bean.getZgcEdate());
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
     * <p>Update ZAI_GPRI_CONF Data Bindding JavaBean
     * @param bean ZAI_GPRI_CONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(ZaiGpriConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ZAI_GPRI_CONF");
            sql.addSql(" set ");
            sql.addSql("   ZGC_GRP=?,");
            sql.addSql("   ZGC_GKBN=?,");
            sql.addSql("   ZGC_VIEW_KBN=?,");
            sql.addSql("   ZGC_SORT_KEY1=?,");
            sql.addSql("   ZGC_SORT_ORDER1=?,");
            sql.addSql("   ZGC_SORT_KEY2=?,");
            sql.addSql("   ZGC_SORT_ORDER2=?,");
            sql.addSql("   ZGC_SCH_VIEW_DF=?,");
            sql.addSql("   ZGC_EUID=?,");
            sql.addSql("   ZGC_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getZgcGrp());
            sql.addIntValue(bean.getZgcGkbn());
            sql.addIntValue(bean.getZgcViewKbn());
            sql.addIntValue(bean.getZgcSortKey1());
            sql.addIntValue(bean.getZgcSortOrder1());
            sql.addIntValue(bean.getZgcSortKey2());
            sql.addIntValue(bean.getZgcSortOrder2());
            sql.addIntValue(bean.getZgcSchViewDf());
            sql.addIntValue(bean.getZgcEuid());
            sql.addDateValue(bean.getZgcEdate());
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
     * <p>Select ZAI_GPRI_CONF All Data
     * @return List in ZAI_GPRI_CONFModel
     * @throws SQLException SQL実行例外
     */
    public List<ZaiGpriConfModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<ZaiGpriConfModel> ret = new ArrayList<ZaiGpriConfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   ZGC_GRP,");
            sql.addSql("   ZGC_GKBN,");
            sql.addSql("   ZGC_VIEW_KBN,");
            sql.addSql("   ZGC_SORT_KEY1,");
            sql.addSql("   ZGC_SORT_ORDER1,");
            sql.addSql("   ZGC_SORT_KEY2,");
            sql.addSql("   ZGC_SORT_ORDER2,");
            sql.addSql("   ZGC_SCH_VIEW_DF,");
            sql.addSql("   ZGC_AUID,");
            sql.addSql("   ZGC_ADATE,");
            sql.addSql("   ZGC_EUID,");
            sql.addSql("   ZGC_EDATE");
            sql.addSql(" from ");
            sql.addSql("   ZAI_GPRI_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getZaiGpriConfFromRs(rs));
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
     * <p>Select ZAI_GPRI_CONF
     * @param usrSid USR_SID
     * @return ZAI_GPRI_CONFModel
     * @throws SQLException SQL実行例外
     */
    public ZaiGpriConfModel select(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ZaiGpriConfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   ZGC_GRP,");
            sql.addSql("   ZGC_GKBN,");
            sql.addSql("   ZGC_VIEW_KBN,");
            sql.addSql("   ZGC_SORT_KEY1,");
            sql.addSql("   ZGC_SORT_ORDER1,");
            sql.addSql("   ZGC_SORT_KEY2,");
            sql.addSql("   ZGC_SORT_ORDER2,");
            sql.addSql("   ZGC_SCH_VIEW_DF,");
            sql.addSql("   ZGC_AUID,");
            sql.addSql("   ZGC_ADATE,");
            sql.addSql("   ZGC_EUID,");
            sql.addSql("   ZGC_EDATE");
            sql.addSql(" from");
            sql.addSql("   ZAI_GPRI_CONF");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getZaiGpriConfFromRs(rs);
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
     * <p>Delete ZAI_GPRI_CONF
     * @param usrSid USR_SID
     * @return count
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
            sql.addSql("   ZAI_GPRI_CONF");
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
     * <p>Create ZAI_GPRI_CONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created ZaiGpriConfModel
     * @throws SQLException SQL実行例外
     */
    private ZaiGpriConfModel __getZaiGpriConfFromRs(ResultSet rs) throws SQLException {
        ZaiGpriConfModel bean = new ZaiGpriConfModel();
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setZgcGrp(rs.getInt("ZGC_GRP"));
        bean.setZgcGkbn(rs.getInt("ZGC_GKBN"));
        bean.setZgcViewKbn(rs.getInt("ZGC_VIEW_KBN"));
        bean.setZgcSortKey1(rs.getInt("ZGC_SORT_KEY1"));
        bean.setZgcSortOrder1(rs.getInt("ZGC_SORT_ORDER1"));
        bean.setZgcSortKey2(rs.getInt("ZGC_SORT_KEY2"));
        bean.setZgcSortOrder2(rs.getInt("ZGC_SORT_ORDER2"));
        bean.setZgcSchViewDf(rs.getInt("ZGC_SCH_VIEW_DF"));
        bean.setZgcAuid(rs.getInt("ZGC_AUID"));
        bean.setZgcAdate(UDate.getInstanceTimestamp(rs.getTimestamp("ZGC_ADATE")));
        bean.setZgcEuid(rs.getInt("ZGC_EUID"));
        bean.setZgcEdate(UDate.getInstanceTimestamp(rs.getTimestamp("ZGC_EDATE")));
        return bean;
    }
}