package jp.groupsession.v2.ntp.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.ntp.model.NtpSpaccessTargetModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>NTP_SPACCESS_TARGET Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class NtpSpaccessTargetDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(NtpSpaccessTargetDao.class);

    /**
     * <p>Default Constructor
     */
    public NtpSpaccessTargetDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public NtpSpaccessTargetDao(Connection con) {
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
            sql.addSql("drop table NTP_SPACCESS_TARGET");

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
            sql.addSql(" create table NTP_SPACCESS_TARGET (");
            sql.addSql("   NSA_SID NUMBER(10,0) not null,");
            sql.addSql("   NST_TYPE NUMBER(10,0) not null,");
            sql.addSql("   NST_TSID NUMBER(10,0) not null");
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
     * <p>Insert NTP_SPACCESS_TARGET Data Bindding JavaBean
     * @param bean NTP_SPACCESS_TARGET Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(NtpSpaccessTargetModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" NTP_SPACCESS_TARGET(");
            sql.addSql("   NSA_SID,");
            sql.addSql("   NST_TYPE,");
            sql.addSql("   NST_TSID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getNsaSid());
            sql.addIntValue(bean.getNstType());
            sql.addIntValue(bean.getNstTsid());
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
     * <p>Update NTP_SPACCESS_TARGET Data Bindding JavaBean
     * @param bean NTP_SPACCESS_TARGET Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(NtpSpaccessTargetModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   NTP_SPACCESS_TARGET");
            sql.addSql(" set ");
            sql.addSql("   NSA_SID=?,");
            sql.addSql("   NST_TYPE=?,");
            sql.addSql("   NST_TSID=?");
            sql.addSql(" where");
            sql.addSql("   NSA_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getNsaSid());
            sql.addIntValue(bean.getNstType());
            sql.addIntValue(bean.getNstTsid());
            sql.addIntValue(bean.getNsaSid());

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
     * <p>Update NTP_SPACCESS_TARGET Data Bindding JavaBean
     * @param nsaSid 日報特例アクセスSID
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int nsaSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete from");
            sql.addSql("   NTP_SPACCESS_TARGET");
            sql.addSql(" where ");
            sql.addSql("   NSA_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(nsaSid);

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
     * <p>Select NTP_SPACCESS_TARGET All Data
     * @return List in NTP_SPACCESS_TARGETModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<NtpSpaccessTargetModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<NtpSpaccessTargetModel> ret = new ArrayList<NtpSpaccessTargetModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NSA_SID,");
            sql.addSql("   NST_TYPE,");
            sql.addSql("   NST_TSID");
            sql.addSql(" from ");
            sql.addSql("   NTP_SPACCESS_TARGET");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getNtpSpaccessTargetFromRs(rs));
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
     * <br>[機  能] 指定した特例アクセスの制限対象を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param nsaSid 特例アクセスSID
     * @return 制限対象
     * @throws SQLException SQL実行時例外
     */
    public List<NtpSpaccessTargetModel> getTargetList(int nsaSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<NtpSpaccessTargetModel> ret = new ArrayList<NtpSpaccessTargetModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NSA_SID,");
            sql.addSql("   NST_TYPE,");
            sql.addSql("   NST_TSID");
            sql.addSql(" from ");
            sql.addSql("   NTP_SPACCESS_TARGET");
            sql.addSql(" where ");
            sql.addSql("   NSA_SID = ?");
            sql.addIntValue(nsaSid);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getNtpSpaccessTargetFromRs(rs));
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
     * <p>Create NTP_SPACCESS_TARGET Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created NtpSpaccessTargetModel
     * @throws SQLException SQL実行例外
     */
    private NtpSpaccessTargetModel __getNtpSpaccessTargetFromRs(ResultSet rs) throws SQLException {
        NtpSpaccessTargetModel bean = new NtpSpaccessTargetModel();
        bean.setNsaSid(rs.getInt("NSA_SID"));
        bean.setNstType(rs.getInt("NST_TYPE"));
        bean.setNstTsid(rs.getInt("NST_TSID"));
        return bean;
    }
    /**
     *
     * <br>[機  能] 指定ユーザを対象とする制限設定数を返す
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid 指定ユーザSID
     * @return 制限設定数
     * @throws SQLException SQL実行時例外
     */
    public int countNSA(int usrSid) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select count(distinct NSA_SID) as CNT");
            sql.addSql(" from (");
            sql.addSql("   select NSA_SID from ");
            sql.addSql("     NTP_SPACCESS_TARGET");
            sql.addSql("   where NST_TSID = ? ");
            sql.addSql("   and NST_TYPE = ? ");
            sql.addIntValue(usrSid);
            sql.addIntValue(GSConst.ST_TYPE_USER);
            sql.addSql("   union all ");
            sql.addSql("   select NSA_SID from ");
            sql.addSql("     NTP_SPACCESS_TARGET");
            sql.addSql("   where NST_TSID in ( ");
            sql.addSql("     select GRP_SID from");
            sql.addSql("       CMN_BELONGM");
            sql.addSql("     where USR_SID =? ");
            sql.addSql("   ) ");
            sql.addSql("   and NST_TYPE = ? ");
            sql.addIntValue(usrSid);
            sql.addIntValue(GSConst.ST_TYPE_GROUP);
            sql.addSql(" ) NSA_CNT ");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("CNT");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return 0;
    }
}
