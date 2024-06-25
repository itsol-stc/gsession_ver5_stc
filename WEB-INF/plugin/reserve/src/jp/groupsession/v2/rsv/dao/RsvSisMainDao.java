package jp.groupsession.v2.rsv.dao;

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
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.rsv.model.RsvSisMainModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>RSV_SIS_MAIN Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class RsvSisMainDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RsvSisMainDao.class);

    /**
     * <p>Default Constructor
     */
    public RsvSisMainDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public RsvSisMainDao(Connection con) {
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
            sql.addSql("drop table RSV_SIS_MAIN");

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
            sql.addSql(" create table RSV_SIS_MAIN (");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   RSG_SID NUMBER(10,0) not null,");
            sql.addSql("   RSM_DSP_KBN NUMBER(10,0) not null,");
            sql.addSql("   RSM_AUID NUMBER(10,0) not null,");
            sql.addSql("   RSM_ADATE varchar(23) not null,");
            sql.addSql("   RSM_EUID NUMBER(10,0) not null,");
            sql.addSql("   RSM_EDATE varchar(23) not null,");
            sql.addSql("   primary key (USR_SID,RSG_SID)");
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
     * <p>Insert RSV_SIS_MAIN Data Bindding JavaBean
     * @param bean RSV_SIS_MAIN Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(RsvSisMainModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" RSV_SIS_MAIN(");
            sql.addSql("   USR_SID,");
            sql.addSql("   RSG_SID,");
            sql.addSql("   RSM_DSP_KBN,");
            sql.addSql("   RSM_AUID,");
            sql.addSql("   RSM_ADATE,");
            sql.addSql("   RSM_EUID,");
            sql.addSql("   RSM_EDATE");
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
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getRsgSid());
            sql.addIntValue(bean.getRsmDspKbn());
            sql.addIntValue(bean.getRsmAuid());
            sql.addDateValue(bean.getRsmAdate());
            sql.addIntValue(bean.getRsmEuid());
            sql.addDateValue(bean.getRsmEdate());
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
     * <p>Update RSV_SIS_MAIN Data Bindding JavaBean
     * @param bean RSV_SIS_MAIN Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(RsvSisMainModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RSV_SIS_MAIN");
            sql.addSql(" set ");
            sql.addSql("   RSM_EUID=?,");
            sql.addSql("   RSM_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   RSG_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRsmEuid());
            sql.addDateValue(bean.getRsmEdate());
            //where
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getRsgSid());

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
     * <p>Select RSV_SIS_MAIN All Data
     * @return List in RSV_SIS_MAINModel
     * @throws SQLException SQL実行例外
     */
    public List<RsvSisMainModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RsvSisMainModel> ret = new ArrayList<RsvSisMainModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   RSG_SID,");
            sql.addSql("   RSM_DSP_KBN,");
            sql.addSql("   RSM_AUID,");
            sql.addSql("   RSM_ADATE,");
            sql.addSql("   RSM_EUID,");
            sql.addSql("   RSM_EDATE");
            sql.addSql(" from ");
            sql.addSql("   RSV_SIS_MAIN");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRsvSisMainFromRs(rs));
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
     * <p>Select RSV_SIS_MAIN
     * @param usrSid USR_SID
     * @return List in RSV_SIS_MAINModel
     * @throws SQLException SQL実行例外
     */
    public List<RsvSisMainModel> select(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<RsvSisMainModel> ret = new ArrayList<RsvSisMainModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   RSG_SID,");
            sql.addSql("   RSM_DSP_KBN,");
            sql.addSql("   RSM_AUID,");
            sql.addSql("   RSM_ADATE,");
            sql.addSql("   RSM_EUID,");
            sql.addSql("   RSM_EDATE");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_MAIN");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRsvSisMainFromRs(rs));
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
     * <p>Select RSV_SIS_MAIN
     * @param usrSid USR_SID
     * @return List in RSV_SIS_MAINModel
     * @throws SQLException SQL実行例外
     */
    public List<RsvSisMainModel> getCanReadConf(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<RsvSisMainModel> ret = new ArrayList<RsvSisMainModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSV_SIS_MAIN.USR_SID,");
            sql.addSql("   RSV_SIS_MAIN.RSG_SID,");
            sql.addSql("   RSV_SIS_MAIN.RSM_DSP_KBN,");
            sql.addSql("   RSV_SIS_MAIN.RSM_AUID,");
            sql.addSql("   RSV_SIS_MAIN.RSM_ADATE,");
            sql.addSql("   RSV_SIS_MAIN.RSM_EUID,");
            sql.addSql("   RSV_SIS_MAIN.RSM_EDATE");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_MAIN");
            sql.addSql("     left join");
            sql.addSql("     RSV_SIS_GRP");
            sql.addSql("     on");
            sql.addSql("     RSV_SIS_MAIN.RSG_SID=RSV_SIS_GRP.RSG_SID");
            sql.addSql(" where ");

            sql.addSql("   USR_SID=?");
            sql.addSql(" and ");

            sql.addSql("(");
            sql.addSql("  (");
            sql.addSql("   RSV_SIS_GRP.RSG_ACS_LIMIT_KBN=?");
            sql.addSql("  )");

            sql.addSql(" or");

            sql.addSql("   ( ");
            sql.addSql("    RSV_SIS_GRP.RSG_ACS_LIMIT_KBN=?");
            sql.addSql("  and ");
            sql.addSql("    exists");
            sql.addSql("    (");
            sql.addSql("      select");
            sql.addSql("        RSV_ACCESS_CONF.RSG_SID");
            sql.addSql("      from");
            sql.addSql("        RSV_ACCESS_CONF");
            sql.addSql("        left join");
            sql.addSql("          CMN_BELONGM");
            sql.addSql("        on");
            sql.addSql("          RSV_ACCESS_CONF.GRP_SID = CMN_BELONGM.GRP_SID");
            sql.addSql("      where");
            sql.addSql("        RSV_ACCESS_CONF.RSG_SID = RSV_SIS_GRP.RSG_SID");
            sql.addSql("      and");
            sql.addSql("        (");
            sql.addSql("          RSV_ACCESS_CONF.USR_SID = ?");
            sql.addSql("        or");
            sql.addSql("          CMN_BELONGM.USR_SID = ?");
            sql.addSql("        )");
            sql.addSql("    )");
            sql.addSql("  )");
            sql.addSql(" or");

            sql.addSql("  (");
            sql.addSql("    RSV_SIS_GRP.RSG_ACS_LIMIT_KBN=?");
            sql.addSql("  and ");
            sql.addSql("    not exists");
            sql.addSql("    (");
            sql.addSql("      select");
            sql.addSql("        RSV_ACCESS_CONF.RSG_SID");
            sql.addSql("      from");
            sql.addSql("        RSV_ACCESS_CONF");
            sql.addSql("        left join");
            sql.addSql("          CMN_BELONGM");
            sql.addSql("        on");
            sql.addSql("          RSV_ACCESS_CONF.GRP_SID = CMN_BELONGM.GRP_SID");
            sql.addSql("      where");
            sql.addSql("        RSV_ACCESS_CONF.RSG_SID = RSV_SIS_GRP.RSG_SID");
            sql.addSql("      and");
            sql.addSql("        (");
            sql.addSql("          RSV_ACCESS_CONF.USR_SID = ?");
            sql.addSql("        or");
            sql.addSql("          CMN_BELONGM.USR_SID = ?");
            sql.addSql("        )");
            sql.addSql("      and");
            sql.addSql("        RSV_ACCESS_CONF.RAC_AUTH=?");
            sql.addSql("    )");
            sql.addSql("  )");
            sql.addSql(")");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);

            sql.addIntValue(GSConstReserve.RSV_ACCESS_MODE_FREE);
            sql.addIntValue(GSConstReserve.RSV_ACCESS_MODE_LIMIT);
            sql.addIntValue(usrSid);
            sql.addIntValue(usrSid);
            sql.addIntValue(GSConstReserve.RSV_ACCESS_MODE_PERMIT);
            sql.addIntValue(usrSid);
            sql.addIntValue(usrSid);
            sql.addIntValue(GSConstReserve.RSV_ACCESS_KBN_READ);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);


            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRsvSisMainFromRs(rs));
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
     * <p>Delete RSV_SIS_MAIN
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
            sql.addSql("   RSV_SIS_MAIN");
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
     * <p>Create RSV_SIS_MAIN Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created RsvSisMainModel
     * @throws SQLException SQL実行例外
     */
    private RsvSisMainModel __getRsvSisMainFromRs(ResultSet rs) throws SQLException {
        RsvSisMainModel bean = new RsvSisMainModel();
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setRsgSid(rs.getInt("RSG_SID"));
        bean.setRsmDspKbn(rs.getInt("RSM_DSP_KBN"));
        bean.setRsmAuid(rs.getInt("RSM_AUID"));
        bean.setRsmAdate(UDate.getInstanceTimestamp(rs.getTimestamp("RSM_ADATE")));
        bean.setRsmEuid(rs.getInt("RSM_EUID"));
        bean.setRsmEdate(UDate.getInstanceTimestamp(rs.getTimestamp("RSM_EDATE")));
        return bean;
    }
}
