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
import jp.groupsession.v2.ntp.model.NtpSpaccessPermitModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>NTP_SPACCESS_PERMIT Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class NtpSpaccessPermitDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(NtpSpaccessPermitDao.class);

    /**
     * <p>Default Constructor
     */
    public NtpSpaccessPermitDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public NtpSpaccessPermitDao(Connection con) {
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
            sql.addSql("drop table NTP_SPACCESS_PERMIT");

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
            sql.addSql(" create table NTP_SPACCESS_PERMIT (");
            sql.addSql("   NSA_SID NUMBER(10,0) not null,");
            sql.addSql("   NSP_TYPE NUMBER(10,0) not null,");
            sql.addSql("   NSP_PSID NUMBER(10,0) not null,");
            sql.addSql("   NSP_AUTH NUMBER(10,0) not null");
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
     * <p>Insert NTP_SPACCESS_PERMIT Data Bindding JavaBean
     * @param bean NTP_SPACCESS_PERMIT Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(NtpSpaccessPermitModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" NTP_SPACCESS_PERMIT(");
            sql.addSql("   NSA_SID,");
            sql.addSql("   NSP_TYPE,");
            sql.addSql("   NSP_PSID,");
            sql.addSql("   NSP_AUTH");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getNsaSid());
            sql.addIntValue(bean.getNspType());
            sql.addIntValue(bean.getNspPsid());
            sql.addIntValue(bean.getNspAuth());
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
     * <p>Update NTP_SPACCESS_PERMIT Data Bindding JavaBean
     * @param bean NTP_SPACCESS_PERMIT Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(NtpSpaccessPermitModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   NTP_SPACCESS_PERMIT");
            sql.addSql(" set ");
            sql.addSql("   NSA_SID=?,");
            sql.addSql("   NSP_TYPE=?,");
            sql.addSql("   NSP_PSID=?,");
            sql.addSql("   NSP_AUTH=?");
            sql.addSql(" where");
            sql.addSql("   NSA_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getNsaSid());
            sql.addIntValue(bean.getNspType());
            sql.addIntValue(bean.getNspPsid());
            sql.addIntValue(bean.getNspAuth());
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
     * <p>Update NTP_SPACCESS_PERMIT Data Bindding JavaBean
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
            sql.addSql("   NTP_SPACCESS_PERMIT");
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
     * <p>Select NTP_SPACCESS_PERMIT All Data
     * @return List in NTP_SPACCESS_PERMITModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<NtpSpaccessPermitModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<NtpSpaccessPermitModel> ret = new ArrayList<NtpSpaccessPermitModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NSA_SID,");
            sql.addSql("   NSP_TYPE,");
            sql.addSql("   NSP_PSID,");
            sql.addSql("   NSP_AUTH");
            sql.addSql(" from ");
            sql.addSql("   NTP_SPACCESS_PERMIT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getNtpSpaccessPermitFromRs(rs));
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
     * <br>[機  能] 指定された特例アクセスの許可対象を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param nsaSid 特例アクセスSID
     * @return 許可対象
     * @throws SQLException SQL実行時例外
     */
    public List<NtpSpaccessPermitModel> getPermitList(int nsaSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<NtpSpaccessPermitModel> ret = new ArrayList<NtpSpaccessPermitModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NSA_SID,");
            sql.addSql("   NSP_TYPE,");
            sql.addSql("   NSP_PSID,");
            sql.addSql("   NSP_AUTH");
            sql.addSql(" from ");
            sql.addSql("   NTP_SPACCESS_PERMIT");
            sql.addSql(" where ");
            sql.addSql("   NSA_SID = ?");
            sql.addIntValue(nsaSid);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getNtpSpaccessPermitFromRs(rs));
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
     * <p>Create NTP_SPACCESS_PERMIT Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created NtpSpaccessPermitModel
     * @throws SQLException SQL実行例外
     */
    private NtpSpaccessPermitModel __getNtpSpaccessPermitFromRs(ResultSet rs) throws SQLException {
        NtpSpaccessPermitModel bean = new NtpSpaccessPermitModel();
        bean.setNsaSid(rs.getInt("NSA_SID"));
        bean.setNspType(rs.getInt("NSP_TYPE"));
        bean.setNspPsid(rs.getInt("NSP_PSID"));
        bean.setNspAuth(rs.getInt("NSP_AUTH"));
        return bean;
    }

    /**
     *
     * <br>[機  能] 指定したユーザSIDへの閲覧を許可されたユーザのSIDを返す
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid 指定ユーザSID
     * @throws SQLException SQL実行時例外
     * @return ユーザSID
     */
    public List<Integer> selectUsrSids(int usrSid) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        List<Integer> ret = new ArrayList<Integer>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select distinct USR_SID");
            sql.addSql(" from (");

            //ユーザ指定許可
            sql.addSql(" select ");
            sql.addSql("   NTP_SPACCESS_PERMIT.NSP_PSID as USR_SID");
            sql.addSql(" from ");
            sql.addSql("   NTP_SPACCESS_PERMIT");
            sql.addSql(" where ");
            sql.addSql("   NTP_SPACCESS_PERMIT.NSA_SID in ( ");
            sql.addSql("     select distinct NSA_SID");
            sql.addSql("     from (");
            sql.addSql("       select NSA_SID from ");
            sql.addSql("         NTP_SPACCESS_TARGET");
            sql.addSql("       where NST_TSID = ? ");
            sql.addSql("       and NST_TYPE = ? ");
            sql.addIntValue(usrSid);
            sql.addIntValue(GSConst.ST_TYPE_USER);
            sql.addSql("       union all ");
            sql.addSql("       select NSA_SID from ");
            sql.addSql("         NTP_SPACCESS_TARGET");
            sql.addSql("       where NST_TSID in ( ");
            sql.addSql("         select GRP_SID from");
            sql.addSql("           CMN_BELONGM");
            sql.addSql("         where USR_SID =? ");
            sql.addSql("       ) ");
            sql.addSql("       and NST_TYPE = ? ");
            sql.addIntValue(usrSid);
            sql.addIntValue(GSConst.ST_TYPE_GROUP);
            sql.addSql("     ) TARGET_NSA_SID");
            sql.addSql("  ) ");
            sql.addSql("  and NTP_SPACCESS_PERMIT.NSP_TYPE = ?");
            sql.addIntValue(GSConst.SP_TYPE_USER);
            //グループ指定許可
            sql.addSql(" union all ");
            sql.addSql(" select ");
            sql.addSql("   CMN_BELONGM.USR_SID");
            sql.addSql(" from ");
            sql.addSql("   NTP_SPACCESS_PERMIT");
            sql.addSql("   ,CMN_BELONGM");
            sql.addSql(" where ");
            sql.addSql("   NTP_SPACCESS_PERMIT.NSA_SID in ( ");
            sql.addSql("     select distinct NSA_SID");
            sql.addSql("     from (");
            sql.addSql("       select NSA_SID from ");
            sql.addSql("         NTP_SPACCESS_TARGET");
            sql.addSql("       where NST_TSID = ? ");
            sql.addSql("       and NST_TYPE = ? ");
            sql.addIntValue(usrSid);
            sql.addIntValue(GSConst.ST_TYPE_USER);
            sql.addSql("       union all ");
            sql.addSql("       select NSA_SID from ");
            sql.addSql("         NTP_SPACCESS_TARGET");
            sql.addSql("       where NST_TSID in ( ");
            sql.addSql("         select GRP_SID from");
            sql.addSql("           CMN_BELONGM");
            sql.addSql("         where USR_SID =? ");
            sql.addSql("       ) ");
            sql.addSql("       and NST_TYPE = ? ");
            sql.addIntValue(usrSid);
            sql.addIntValue(GSConst.ST_TYPE_GROUP);
            sql.addSql("     ) TARGET_NSA_SID");
            sql.addSql("  ) ");
            sql.addSql("  and NTP_SPACCESS_PERMIT.NSP_TYPE = ?");
            sql.addIntValue(GSConst.SP_TYPE_GROUP);
            sql.addSql("  and NTP_SPACCESS_PERMIT.NSP_PSID = CMN_BELONGM.GRP_SID");
            //役職指定許可
            sql.addSql(" union all ");
            sql.addSql(" select ");
            sql.addSql("   CMN_USRM_INF.USR_SID");
            sql.addSql(" from ");
            sql.addSql("   CMN_POSITION");
            sql.addSql("   ,CMN_USRM_INF");
            sql.addSql(" where ");
            sql.addSql(" CMN_POSITION.POS_SORT < (");
            sql.addSql("   select ");
            sql.addSql("     min(CMN_POSITION.POS_SORT) as SORT");
            sql.addSql("   from ");
            sql.addSql("     NTP_SPACCESS_PERMIT");
            sql.addSql("     ,CMN_POSITION");
            sql.addSql("   where ");
            sql.addSql("     NTP_SPACCESS_PERMIT.NSA_SID in ( ");
            sql.addSql("       select distinct NSA_SID");
            sql.addSql("       from (");
            sql.addSql("         select NSA_SID from ");
            sql.addSql("           NTP_SPACCESS_TARGET");
            sql.addSql("         where NST_TSID = ? ");
            sql.addSql("         and NST_TYPE = ? ");
            sql.addIntValue(usrSid);
            sql.addIntValue(GSConst.ST_TYPE_USER);
            sql.addSql("         union all ");
            sql.addSql("         select NSA_SID from ");
            sql.addSql("           NTP_SPACCESS_TARGET");
            sql.addSql("         where NST_TSID in ( ");
            sql.addSql("           select GRP_SID from");
            sql.addSql("             CMN_BELONGM");
            sql.addSql("           where USR_SID =? ");
            sql.addSql("         ) ");
            sql.addSql("         and NST_TYPE = ? ");
            sql.addIntValue(usrSid);
            sql.addIntValue(GSConst.ST_TYPE_GROUP);
            sql.addSql("       ) TARGET_NSA_SID");
            sql.addSql("    ) ");
            sql.addSql("    and NTP_SPACCESS_PERMIT.NSP_TYPE = ?");
            sql.addIntValue(GSConst.SP_TYPE_POSITION);
            sql.addSql("    and NTP_SPACCESS_PERMIT.NSP_PSID = CMN_POSITION.POS_SID");
            sql.addSql("  ) ");
            sql.addSql("   and CMN_POSITION.POS_SID = CMN_USRM_INF.POS_SID");
            sql.addSql(" ) SPACCESS_USR_SID");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);

            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getInt("USR_SID"));
            }
            return ret;
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
    }
}
