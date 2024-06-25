package jp.groupsession.v2.ntp.dao;

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
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.ntp.model.NtpAnkenPermitModel;

/**
 * <p>NTP_ANKEN_PERMIT Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class NtpAnkenPermitDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(NtpAnkenPermitDao.class);

    /**
     * <p>Default Constructor
     */
    public NtpAnkenPermitDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public NtpAnkenPermitDao(Connection con) {
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
            sql.addSql("drop table NTP_ANKEN_PERMIT");

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
            sql.addSql(" create table NTP_ANKEN_PERMIT (");
            sql.addSql("   NAN_SID NUMBER(10,0) not null,");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   GRP_SID NUMBER(10,0) not null,");
            sql.addSql("   NAP_KBN NUMBER(10,0) not null");
            sql.addSql("   primary key (NAN_SID,USR_SID,GRP_SID,NAP_KBN)");
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
     * <p>Insert NTP_ANKEN_PERMIT Data Bindding JavaBean
     * @param bean NTP_ANKEN_PERMIT Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(NtpAnkenPermitModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" NTP_ANKEN_PERMIT(");
            sql.addSql("   NAN_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   NAP_KBN");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getNanSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getGrpSid());
            sql.addIntValue(bean.getNapKbn());
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
     * <p>Update NTP_ANKEN_PERMIT Data Bindding JavaBean
     * @param bean NTP_ANKEN_PERMIT Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(NtpAnkenPermitModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   NTP_ANKEN_PERMIT");
            sql.addSql(" set ");
            sql.addSql("   NAN_SID=?,");
            sql.addSql("   USR_SID=?,");
            sql.addSql("   GRP_SID=?,");
            sql.addSql("   NAP_KBN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getNanSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getGrpSid());
            sql.addIntValue(bean.getNapKbn());

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
     * <p>Select NTP_ANKEN_PERMIT All Data
     * @return List in NTP_ANKEN_PERMITModel
     * @throws SQLException SQL実行例外
     */
    public List<NtpAnkenPermitModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<NtpAnkenPermitModel> ret = new ArrayList<NtpAnkenPermitModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NAN_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   NAP_KBN");
            sql.addSql(" from ");
            sql.addSql("   NTP_ANKEN_PERMIT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getNtpAnkenPermitFromRs(rs));
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
     *
     * <br>[機  能] 案件に紐づく権限ユーザリストを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param nanSid 案件SID
     * @param napKbn 権限区分 0:閲覧可能 1：編集可能
     * @return List in NTP_ANKEN_PERMITModel
     * @throws SQLException SQL実行例外
     */
    public List<NtpAnkenPermitModel> select(int nanSid, int napKbn) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<NtpAnkenPermitModel> ret = new ArrayList<NtpAnkenPermitModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NAN_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   NAP_KBN");
            sql.addSql(" from ");
            sql.addSql("   NTP_ANKEN_PERMIT");
            sql.addSql(" where ");
            sql.addSql("   NAN_SID = ?");
            sql.addIntValue(nanSid);
            if (napKbn == GSConst.SP_AUTH_EDIT) {
                sql.addSql(" and ");
                sql.addSql("   NAP_KBN = ?");
                sql.addIntValue(napKbn);
            }
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getNtpAnkenPermitFromRs(rs));
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
     * <p>Create NTP_ANKEN_PERMIT Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created NtpAnkenPermitModel
     * @throws SQLException SQL実行例外
     */
    private NtpAnkenPermitModel __getNtpAnkenPermitFromRs(ResultSet rs) throws SQLException {
        NtpAnkenPermitModel bean = new NtpAnkenPermitModel();
        bean.setNanSid(rs.getInt("NAN_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setGrpSid(rs.getInt("GRP_SID"));
        bean.setNapKbn(rs.getInt("NAP_KBN"));
        return bean;
    }
    /**
     * <p>Delete NTP_ANKEN
     * @param nanSid NAN_SID
     * @throws SQLException SQL実行例外
     * @return 削除件数
     */
    public int delete(int nanSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   NTP_ANKEN_PERMIT");
            sql.addSql(" where ");
            sql.addSql("   NAN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nanSid);

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
    *
    * <br>[機  能] 日報管理者権限をもつユーザSIDを一覧取得する
    * <br>[解  説]
    * <br>[備  考]
     * @param nanSid 案件SID
     * @param napKbn 権限区分 0:閲覧可能 1：編集可能
    * @return  ユーザSID一覧
    * @throws SQLException SQL実行時例外
    */
   public ArrayList<Integer> selectUsrSids(int nanSid, int napKbn) throws SQLException {
       PreparedStatement pstmt = null;
       ResultSet rs = null;
       Connection con = null;
       ArrayList<Integer> ret = new ArrayList<Integer>();
       con = getCon();

       try {
           //SQL文
           SqlBuffer sql = new SqlBuffer();
           sql.addSql(" select distinct");
           sql.addSql("   USR_SID");
           sql.addSql(" from (");
           sql.addSql("   select ");
           sql.addSql("     USR_SID");
           sql.addSql("   from");
           sql.addSql("     NTP_ANKEN_PERMIT");
           sql.addSql("   where ");
           sql.addSql("     NAN_SID=? and USR_SID > 0");
           sql.addIntValue(nanSid);
           if (napKbn == GSConst.SP_AUTH_EDIT) {
               sql.addSql(" and NAP_KBN=?");
               sql.addIntValue(napKbn);
           }
           sql.addSql("   union all ");
           sql.addSql("   select ");
           sql.addSql("     CMN_BELONGM.USR_SID as USR_SID");
           sql.addSql("   from");
           sql.addSql("     NTP_ANKEN_PERMIT");
           sql.addSql("     ,CMN_BELONGM");
           sql.addSql("   where ");
           sql.addSql("     NTP_ANKEN_PERMIT.NAN_SID=?");
           sql.addIntValue(nanSid);
           sql.addSql("     and NTP_ANKEN_PERMIT.GRP_SID=CMN_BELONGM.GRP_SID");
           if (napKbn == GSConst.SP_AUTH_EDIT) {
               sql.addSql(" and NTP_ANKEN_PERMIT.NAP_KBN=?");
               sql.addIntValue(napKbn);
           }
           sql.addSql(" ) NAP_CNT");

           pstmt = con.prepareStatement(sql.toSqlString());

           log__.info(sql.toLogString());
           sql.setParameter(pstmt);
           rs = pstmt.executeQuery();
           while (rs.next()) {
               ret.add(rs.getInt("USR_SID"));
           }
       } catch (SQLException e) {
           throw e;
       } finally {
           JDBCUtil.closeResultSet(rs);
           JDBCUtil.closeStatement(pstmt);
       }
       return ret;

   }

}
