package jp.groupsession.v2.rsv.dao;

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
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.rsv.ReserveCommonModel;
import jp.groupsession.v2.rsv.model.RsvSisGrpModel;
import jp.groupsession.v2.rsv.rsv080.Rsv080Model;
import jp.groupsession.v2.rsv.rsv240.Rsv240DspModel;

/**
 * <p>RSV_SIS_GRP Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RsvSisGrpDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RsvSisGrpDao.class);

    /**
     * <p>Default Constructor
     */
    public RsvSisGrpDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public RsvSisGrpDao(Connection con) {
        super(con);
    }

    /**
     * <p>Insert RSV_SIS_GRP Data Bindding JavaBean
     * @param bean RSV_SIS_GRP Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(RsvSisGrpModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" RSV_SIS_GRP(");
            sql.addSql("   RSG_SID,");
            sql.addSql("   RSK_SID,");
            sql.addSql("   RSG_ID,");
            sql.addSql("   RSG_NAME,");
            sql.addSql("   RSG_ADM_KBN,");
            sql.addSql("   RSG_SORT,");
            sql.addSql("   RSG_ACS_LIMIT_KBN,");
            sql.addSql("   RSG_APPR_KBN,");
            sql.addSql("   RSG_AUID,");
            sql.addSql("   RSG_ADATE,");
            sql.addSql("   RSG_EUID,");
            sql.addSql("   RSG_EDATE");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRsgSid());
            sql.addIntValue(bean.getRskSid());
            sql.addStrValue(bean.getRsgId());
            sql.addStrValue(bean.getRsgName());
            sql.addIntValue(bean.getRsgAdmKbn());
            sql.addIntValue(bean.getRsgSort());
            sql.addIntValue(bean.getRsgAcsLimitKbn());
            sql.addIntValue(bean.getRsgApprKbn());
            sql.addIntValue(bean.getRsgAuid());
            sql.addDateValue(bean.getRsgAdate());
            sql.addIntValue(bean.getRsgEuid());
            sql.addDateValue(bean.getRsgEdate());
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
     * <br>[機  能] 指定された施設グループの管理者ユーザを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param bean 登録データモデル
     * @throws SQLException SQL実行例外
     */
    public void insertNewGrp(RsvSisGrpModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert");
            sql.addSql(" into");
            sql.addSql("   RSV_SIS_GRP");
            sql.addSql("   (");
            sql.addSql("     RSG_SID,");
            sql.addSql("     RSK_SID,");
            sql.addSql("     RSG_ID,");
            sql.addSql("     RSG_NAME,");
            sql.addSql("     RSG_ADM_KBN,");
            sql.addSql("     RSG_SORT,");
            sql.addSql("     RSG_ACS_LIMIT_KBN,");
            sql.addSql("     RSG_APPR_KBN,");
            sql.addSql("     RSG_AUID,");
            sql.addSql("     RSG_ADATE,");
            sql.addSql("     RSG_EUID,");
            sql.addSql("     RSG_EDATE");
            sql.addSql("   )");
            sql.addSql("   select");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     case when max(RSG_SORT) is null then 1 "
                          + "else max(RSG_SORT) + 1 end,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?");
            sql.addSql("   from");
            sql.addSql("     RSV_SIS_GRP");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRsgSid());
            sql.addIntValue(bean.getRskSid());
            sql.addStrValue(bean.getRsgId());
            sql.addStrValue(bean.getRsgName());
            sql.addIntValue(bean.getRsgAdmKbn());
            sql.addIntValue(bean.getRsgAcsLimitKbn());
            sql.addIntValue(bean.getRsgApprKbn());
            sql.addIntValue(bean.getRsgAuid());
            sql.addDateValue(bean.getRsgAdate());
            sql.addIntValue(bean.getRsgEuid());
            sql.addDateValue(bean.getRsgEdate());
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
     * <p>Update RSV_SIS_GRP Data Bindding JavaBean
     * @param bean RSV_SIS_GRP Data Bindding JavaBean
     * @return count update count
     * @throws SQLException SQL実行例外
     */
    public int update(RsvSisGrpModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RSV_SIS_GRP");
            sql.addSql(" set ");
            sql.addSql("   RSK_SID=?,");
            sql.addSql("   RSG_ID=?,");
            sql.addSql("   RSG_NAME=?,");
            sql.addSql("   RSG_ADM_KBN=?,");
            sql.addSql("   RSG_SORT=?,");
            sql.addSql("   RSG_ACS_LIMIT_KBN=?,");
            sql.addSql("   RSG_APPR_KBN=?,");
            sql.addSql("   RSG_AUID=?,");
            sql.addSql("   RSG_ADATE=?,");
            sql.addSql("   RSG_EUID=?,");
            sql.addSql("   RSG_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   RSG_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRskSid());
            sql.addStrValue(bean.getRsgId());
            sql.addStrValue(bean.getRsgName());
            sql.addIntValue(bean.getRsgAdmKbn());
            sql.addIntValue(bean.getRsgSort());
            sql.addIntValue(bean.getRsgAcsLimitKbn());
            sql.addIntValue(bean.getRsgApprKbn());
            sql.addIntValue(bean.getRsgAuid());
            sql.addDateValue(bean.getRsgAdate());
            sql.addIntValue(bean.getRsgEuid());
            sql.addDateValue(bean.getRsgEdate());
            //where
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
     * <br>[機  能] 指定された施設グループを更新
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param bean 登録データモデル
     * @return count update count
     * @throws SQLException SQL実行例外
     */
    public int updateGrp(RsvSisGrpModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RSV_SIS_GRP");
            sql.addSql(" set ");
            sql.addSql("   RSK_SID = ?,");
            sql.addSql("   RSG_ID = ?,");
            sql.addSql("   RSG_NAME = ?,");
            sql.addSql("   RSG_ADM_KBN = ?,");
            sql.addSql("   RSG_ACS_LIMIT_KBN = ?,");
            sql.addSql("   RSG_APPR_KBN=?,");
            sql.addSql("   RSG_EUID = ?,");
            sql.addSql("   RSG_EDATE = ?");
            sql.addSql(" where ");
            sql.addSql("   RSG_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRskSid());
            sql.addStrValue(bean.getRsgId());
            sql.addStrValue(bean.getRsgName());
            sql.addIntValue(bean.getRsgAdmKbn());
            sql.addIntValue(bean.getRsgAcsLimitKbn());
            sql.addIntValue(bean.getRsgApprKbn());
            sql.addIntValue(bean.getRsgEuid());
            sql.addDateValue(bean.getRsgEdate());
            //where
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
     * <p>Select RSV_SIS_GRP All Data
     * @return List in RSV_SIS_GRPModel
     * @throws SQLException SQL実行例外
     */
    public List<RsvSisGrpModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RsvSisGrpModel> ret = new ArrayList<RsvSisGrpModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RSG_SID,");
            sql.addSql("   RSK_SID,");
            sql.addSql("   RSG_ID,");
            sql.addSql("   RSG_NAME,");
            sql.addSql("   RSG_ADM_KBN,");
            sql.addSql("   RSG_SORT,");
            sql.addSql("   RSG_ACS_LIMIT_KBN,");
            sql.addSql("   RSG_APPR_KBN,");
            sql.addSql("   RSG_AUID,");
            sql.addSql("   RSG_ADATE,");
            sql.addSql("   RSG_EUID,");
            sql.addSql("   RSG_EDATE");
            sql.addSql(" from ");
            sql.addSql("   RSV_SIS_GRP");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRsvSisGrpFromRs(rs));
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
     * <br>[機  能] 施設グループIDを全て取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return ret ArrayList in RsvSisGrpModel
     * @throws SQLException SQL実行例外
     */
    public List<String> getAllRsvGrpId() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<String> ret = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RSG_ID");
            sql.addSql(" from ");
            sql.addSql("   RSV_SIS_GRP");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getString("RSG_ID"));
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
     * <br>[機  能] 施設グループ全レコードを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return ret ArrayList in RsvSisGrpModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<RsvSisGrpModel> selectAllGroupData() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RsvSisGrpModel> ret = new ArrayList<RsvSisGrpModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RSG_SID,");
            sql.addSql("   RSK_SID,");
            sql.addSql("   RSG_ID,");
            sql.addSql("   RSG_NAME,");
            sql.addSql("   RSG_ADM_KBN,");
            sql.addSql("   RSG_SORT,");
            sql.addSql("   RSG_ACS_LIMIT_KBN,");
            sql.addSql("   RSG_APPR_KBN,");
            sql.addSql("   RSG_AUID,");
            sql.addSql("   RSG_ADATE,");
            sql.addSql("   RSG_EUID,");
            sql.addSql("   RSG_EDATE");
            sql.addSql(" from ");
            sql.addSql("   RSV_SIS_GRP");
            sql.addSql(" order by");
            sql.addSql("   RSG_SORT asc");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRsvSisGrpFromRs(rs));
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
     * <br>[機  能] 施設グループ全レコードを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rskSid 施設区分
     * @return ret ArrayList in RsvSisGrpModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<RsvSisGrpModel> selectAllGroupData(int rskSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RsvSisGrpModel> ret = new ArrayList<RsvSisGrpModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RSG_SID,");
            sql.addSql("   RSK_SID,");
            sql.addSql("   RSG_ID,");
            sql.addSql("   RSG_NAME,");
            sql.addSql("   RSG_ADM_KBN,");
            sql.addSql("   RSG_SORT,");
            sql.addSql("   RSG_ACS_LIMIT_KBN,");
            sql.addSql("   RSG_APPR_KBN,");
            sql.addSql("   RSG_AUID,");
            sql.addSql("   RSG_ADATE,");
            sql.addSql("   RSG_EUID,");
            sql.addSql("   RSG_EDATE");
            sql.addSql(" from ");
            sql.addSql("   RSV_SIS_GRP");
            if (rskSid != 0) {
                sql.addSql(" where ");
                sql.addSql(" RSK_SID = ? ");
                sql.addIntValue(rskSid);
            }
            sql.addSql(" order by");
            sql.addSql("   RSG_SORT asc");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRsvSisGrpFromRs(rs));
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
     * <br>[機  能] 閲覧可能な施設グループ情報を取得する。
     * <br>[解  説] 権限設定を行わない施設グループも取得します。
     * <br>[備  考]
     *
     * @param usrSid ユーザSID
     * @return ret ArrayList in RsvSisGrpModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<RsvSisGrpModel> getCanReadData(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RsvSisGrpModel> ret = new ArrayList<RsvSisGrpModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RSV_SIS_GRP.RSG_SID,");
            sql.addSql("   RSV_SIS_GRP.RSK_SID,");
            sql.addSql("   RSV_SIS_GRP.RSG_ID,");
            sql.addSql("   RSV_SIS_GRP.RSG_NAME,");
            sql.addSql("   RSV_SIS_GRP.RSG_ADM_KBN,");
            sql.addSql("   RSV_SIS_GRP.RSG_SORT,");
            sql.addSql("   RSV_SIS_GRP.RSG_ACS_LIMIT_KBN,");
            sql.addSql("   RSV_SIS_GRP.RSG_APPR_KBN,");
            sql.addSql("   RSV_SIS_GRP.RSG_AUID,");
            sql.addSql("   RSV_SIS_GRP.RSG_ADATE,");
            sql.addSql("   RSV_SIS_GRP.RSG_EUID,");
            sql.addSql("   RSV_SIS_GRP.RSG_EDATE");
            sql.addSql(" from ");
            sql.addSql("   RSV_SIS_GRP");
            sql.addSql(" where ");

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

            sql.addSql(" order by");
            sql.addSql("   RSG_SORT asc");

            pstmt = con.prepareStatement(sql.toSqlString());
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

            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRsvSisGrpFromRs(rs));
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
     * <br>[機  能] 閲覧可能な施設グループ情報を取得する。
     * <br>[解  説] 権限設定を行わない施設グループも取得します。
     * <br>[備  考]
     *
     * @param usrSid ユーザSID
     * @param rsgSid 施設グループSID
     * @return ret ArrayList in RsvSisGrpModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<RsvSisGrpModel> getCanReadData(int usrSid, int rsgSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RsvSisGrpModel> ret = new ArrayList<RsvSisGrpModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RSV_SIS_GRP.RSG_SID,");
            sql.addSql("   RSV_SIS_GRP.RSK_SID,");
            sql.addSql("   RSV_SIS_GRP.RSG_ID,");
            sql.addSql("   RSV_SIS_GRP.RSG_NAME,");
            sql.addSql("   RSV_SIS_GRP.RSG_ADM_KBN,");
            sql.addSql("   RSV_SIS_GRP.RSG_SORT,");
            sql.addSql("   RSV_SIS_GRP.RSG_ACS_LIMIT_KBN,");
            sql.addSql("   RSV_SIS_GRP.RSG_APPR_KBN,");
            sql.addSql("   RSV_SIS_GRP.RSG_AUID,");
            sql.addSql("   RSV_SIS_GRP.RSG_ADATE,");
            sql.addSql("   RSV_SIS_GRP.RSG_EUID,");
            sql.addSql("   RSV_SIS_GRP.RSG_EDATE");
            sql.addSql(" from ");
            sql.addSql("   RSV_SIS_GRP");
            sql.addSql(" where ");
            sql.addSql("   RSV_SIS_GRP.RSG_SID=?");
            sql.addSql(" and ");
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

            sql.addSql(" order by");
            sql.addSql("   RSG_SORT asc");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rsgSid);
            sql.addIntValue(GSConstReserve.RSV_ACCESS_MODE_FREE);
            sql.addIntValue(GSConstReserve.RSV_ACCESS_MODE_LIMIT);
            sql.addIntValue(usrSid);
            sql.addIntValue(usrSid);
            sql.addIntValue(GSConstReserve.RSV_ACCESS_MODE_PERMIT);
            sql.addIntValue(usrSid);
            sql.addIntValue(usrSid);
            sql.addIntValue(GSConstReserve.RSV_ACCESS_KBN_READ);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());

            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRsvSisGrpFromRs(rs));
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
     * <br>[機  能] 閲覧可能な施設グループ情報を取得する。
     * <br>[解  説] 権限設定を行わない施設グループも取得します。
     * <br>[備  考]
     *
     * @param usrSid ユーザSID
     * @param rskSid 施設区分
     * @return ret ArrayList in RsvSisGrpModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<RsvSisGrpModel> getCanReadDataSisetuKbn(int usrSid, int rskSid)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RsvSisGrpModel> ret = new ArrayList<RsvSisGrpModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RSV_SIS_GRP.RSG_SID,");
            sql.addSql("   RSV_SIS_GRP.RSK_SID,");
            sql.addSql("   RSV_SIS_GRP.RSG_ID,");
            sql.addSql("   RSV_SIS_GRP.RSG_NAME,");
            sql.addSql("   RSV_SIS_GRP.RSG_ADM_KBN,");
            sql.addSql("   RSV_SIS_GRP.RSG_SORT,");
            sql.addSql("   RSV_SIS_GRP.RSG_ACS_LIMIT_KBN,");
            sql.addSql("   RSV_SIS_GRP.RSG_APPR_KBN,");
            sql.addSql("   RSV_SIS_GRP.RSG_AUID,");
            sql.addSql("   RSV_SIS_GRP.RSG_ADATE,");
            sql.addSql("   RSV_SIS_GRP.RSG_EUID,");
            sql.addSql("   RSV_SIS_GRP.RSG_EDATE");
            sql.addSql(" from ");
            sql.addSql("   RSV_SIS_GRP");
            sql.addSql(" where ");

            if (rskSid != 0) {
                sql.addSql("   RSV_SIS_GRP.RSK_SID = ?");
                sql.addIntValue(rskSid);
                sql.addSql("   and (");
            }

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
            if (rskSid != 0) {
                sql.addSql("  )");
            }

            sql.addSql(" order by");
            sql.addSql("   RSG_SORT asc");

            pstmt = con.prepareStatement(sql.toSqlString());
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

            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRsvSisGrpFromRs(rs));
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
     * <br>[機  能] 編集可能な施設グループ情報を取得する。
     * <br>[解  説] 権限設定を行わない施設グループも取得します。
     * <br>[備  考]
     *
     * @param usrSid ユーザSID
     * @return ret ArrayList in RsvSisGrpModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<RsvSisGrpModel> getCanEditData(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RsvSisGrpModel> ret = new ArrayList<RsvSisGrpModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RSV_SIS_GRP.RSG_SID,");
            sql.addSql("   RSV_SIS_GRP.RSK_SID,");
            sql.addSql("   RSV_SIS_GRP.RSG_ID,");
            sql.addSql("   RSV_SIS_GRP.RSG_NAME,");
            sql.addSql("   RSV_SIS_GRP.RSG_ADM_KBN,");
            sql.addSql("   RSV_SIS_GRP.RSG_SORT,");
            sql.addSql("   RSV_SIS_GRP.RSG_ACS_LIMIT_KBN,");
            sql.addSql("   RSV_SIS_GRP.RSG_APPR_KBN,");
            sql.addSql("   RSV_SIS_GRP.RSG_AUID,");
            sql.addSql("   RSV_SIS_GRP.RSG_ADATE,");
            sql.addSql("   RSV_SIS_GRP.RSG_EUID,");
            sql.addSql("   RSV_SIS_GRP.RSG_EDATE");
            sql.addSql(" from ");
            sql.addSql("   RSV_SIS_GRP");
            sql.addSql(" where ");

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
            sql.addSql("      and");
            sql.addSql("        RSV_ACCESS_CONF.RAC_AUTH = ?");
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

            sql.addSql("    )");
            sql.addSql("  )");

            sql.addSql(" order by");
            sql.addSql("   RSG_SORT asc");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(GSConstReserve.RSV_ACCESS_MODE_FREE);
            sql.addIntValue(GSConstReserve.RSV_ACCESS_MODE_LIMIT);
            sql.addIntValue(usrSid);
            sql.addIntValue(usrSid);
            sql.addIntValue(GSConstReserve.RSV_ACCESS_KBN_WRITE);
            sql.addIntValue(GSConstReserve.RSV_ACCESS_MODE_PERMIT);
            sql.addIntValue(usrSid);
            sql.addIntValue(usrSid);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRsvSisGrpFromRs(rs));
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
     * <br>[機  能] 編集可能な施設グループ情報を取得する。
     * <br>[解  説] 権限設定を行わない施設グループも取得します。
     * <br>[備  考]
     *
     * @param usrSid ユーザSID
     * @param rsgSid 施設グループSID
     * @return ret ArrayList in RsvSisGrpModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<RsvSisGrpModel> getCanEditData(int usrSid, int rsgSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RsvSisGrpModel> ret = new ArrayList<RsvSisGrpModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RSV_SIS_GRP.RSG_SID,");
            sql.addSql("   RSV_SIS_GRP.RSK_SID,");
            sql.addSql("   RSV_SIS_GRP.RSG_ID,");
            sql.addSql("   RSV_SIS_GRP.RSG_NAME,");
            sql.addSql("   RSV_SIS_GRP.RSG_ADM_KBN,");
            sql.addSql("   RSV_SIS_GRP.RSG_SORT,");
            sql.addSql("   RSV_SIS_GRP.RSG_ACS_LIMIT_KBN,");
            sql.addSql("   RSV_SIS_GRP.RSG_APPR_KBN,");
            sql.addSql("   RSV_SIS_GRP.RSG_AUID,");
            sql.addSql("   RSV_SIS_GRP.RSG_ADATE,");
            sql.addSql("   RSV_SIS_GRP.RSG_EUID,");
            sql.addSql("   RSV_SIS_GRP.RSG_EDATE");
            sql.addSql(" from ");
            sql.addSql("   RSV_SIS_GRP");
            sql.addSql(" where ");
            sql.addSql("   RSV_SIS_GRP.RSG_SID=?");
            sql.addSql(" and");

            sql.addSql("  (");
            sql.addSql("   RSV_SIS_GRP.RSG_ACS_LIMIT_KBN=?");

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
            sql.addSql("      and");
            sql.addSql("        RSV_ACCESS_CONF.RAC_AUTH = ?");
            sql.addSql("      and");
            sql.addSql("        RSV_ACCESS_CONF.RSG_SID=?");
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
            sql.addSql("        RSV_ACCESS_CONF.RSG_SID=?");
            sql.addSql("    )");
            sql.addSql("  )");
            sql.addSql("  )");

            sql.addSql(" order by");
            sql.addSql("   RSG_SORT asc");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rsgSid);
            sql.addIntValue(GSConstReserve.RSV_ACCESS_MODE_FREE);
            sql.addIntValue(GSConstReserve.RSV_ACCESS_MODE_LIMIT);
            sql.addIntValue(usrSid);
            sql.addIntValue(usrSid);
            sql.addIntValue(GSConstReserve.RSV_ACCESS_KBN_WRITE);
            sql.addIntValue(rsgSid);
            sql.addIntValue(GSConstReserve.RSV_ACCESS_MODE_PERMIT);
            sql.addIntValue(usrSid);
            sql.addIntValue(usrSid);
            sql.addIntValue(rsgSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRsvSisGrpFromRs(rs));
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
     * <br>[機  能] 施設グループの管理者に設定されているデータと、
     * <br>         権限設定をしていないデータを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param usrSid ユーザSID
     * @param grpSids ユーザが所属しているグループSIDリスト
     * @return ret ArrayList in RsvSisGrpModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<RsvSisGrpModel> selectGrpListNotAdmin(int usrSid, ArrayList<Integer> grpSids)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RsvSisGrpModel> ret = new ArrayList<RsvSisGrpModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   unionVw.RSG_SID,");
            sql.addSql("   unionVw.RSG_NAME,");
            sql.addSql("   unionVw.RSG_SORT");
            sql.addSql(" from (");
            sql.addSql("   select");
            sql.addSql("     RSV_SIS_GRP.RSG_SID,");
            sql.addSql("     RSV_SIS_GRP.RSG_NAME,");
            sql.addSql("     RSV_SIS_GRP.RSG_SORT");
            sql.addSql("   from");
            sql.addSql("     RSV_SIS_GRP,");
            sql.addSql("     RSV_SIS_ADM");
            sql.addSql("   where");
            sql.addSql("     RSV_SIS_GRP.RSG_SID = RSV_SIS_ADM.RSG_SID");
            sql.addSql("   and");
            sql.addSql("     RSV_SIS_GRP.RSG_ADM_KBN = ?");
            sql.addSql("   and");
            sql.addSql("     RSV_SIS_ADM.USR_SID = ?");
            sql.addSql("   and");
            sql.addSql("     RSV_SIS_ADM.GRP_SID = -1");

            sql.addIntValue(GSConstReserve.RSG_ADM_KBN_OK);
            sql.addIntValue(usrSid);

            if (grpSids != null && grpSids.size() > 0) {
                sql.addSql("   union");
                sql.addSql("   select");
                sql.addSql("     RSV_SIS_GRP.RSG_SID,");
                sql.addSql("     RSV_SIS_GRP.RSG_NAME,");
                sql.addSql("     RSV_SIS_GRP.RSG_SORT");
                sql.addSql("   from");
                sql.addSql("     RSV_SIS_GRP,");
                sql.addSql("     RSV_SIS_ADM");
                sql.addSql("   where");
                sql.addSql("     RSV_SIS_GRP.RSG_SID = RSV_SIS_ADM.RSG_SID");
                sql.addSql("   and");
                sql.addSql("     RSV_SIS_GRP.RSG_ADM_KBN = ?");
                sql.addSql("   and");
                sql.addSql("     RSV_SIS_ADM.USR_SID = -1");
                sql.addSql("   and");
                sql.addSql("     RSV_SIS_ADM.GRP_SID in (");

              sql.addIntValue(GSConstReserve.RSG_ADM_KBN_OK);

                for (int i = 0; i < grpSids.size(); i++) {

                    sql.addSql("?");
                    sql.addIntValue(grpSids.get(i));

                    if (i != grpSids.size() - 1) {
                        sql.addSql(", ");
                    }
                }

                sql.addSql("   )");

            }

            sql.addSql("   union");
            sql.addSql("   select");
            sql.addSql("     RSV_SIS_GRP.RSG_SID,");
            sql.addSql("     RSV_SIS_GRP.RSG_NAME,");
            sql.addSql("     RSV_SIS_GRP.RSG_SORT");
            sql.addSql("   from");
            sql.addSql("     RSV_SIS_GRP");
            sql.addSql("   where");
            sql.addSql("     RSV_SIS_GRP.RSG_ADM_KBN = ?");
            sql.addSql("   ) unionVw");
            sql.addSql(" order by");
            sql.addSql("   unionVw.RSG_SORT asc");

            sql.addIntValue(GSConstReserve.RSG_ADM_KBN_NO);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                RsvSisGrpModel mdl = new RsvSisGrpModel();
                mdl.setRsgSid(rs.getInt("RSG_SID"));
                mdl.setRsgName(rs.getString("RSG_NAME"));
                mdl.setRsgSort(rs.getInt("RSG_SORT"));
                ret.add(mdl);
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
     * <p>Select RSV_SIS_GRP
     * @param grpSid GroupSid
     * @return RSV_SIS_GRPModel
     * @throws SQLException SQL実行例外
     */
    public RsvSisGrpModel select(int grpSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        RsvSisGrpModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSG_SID,");
            sql.addSql("   RSK_SID,");
            sql.addSql("   RSG_ID,");
            sql.addSql("   RSG_NAME,");
            sql.addSql("   RSG_ADM_KBN,");
            sql.addSql("   RSG_SORT,");
            sql.addSql("   RSG_ACS_LIMIT_KBN,");
            sql.addSql("   RSG_APPR_KBN,");
            sql.addSql("   RSG_AUID,");
            sql.addSql("   RSG_ADATE,");
            sql.addSql("   RSG_EUID,");
            sql.addSql("   RSG_EDATE");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_GRP");
            sql.addSql(" where ");
            sql.addSql("   RSG_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(grpSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getRsvSisGrpFromRs(rs);
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
     * <br>[機  能] 指定したグループIDのグループ情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param grpId グループID
     * @return RsvSisGrpModel グループ情報
     * @throws SQLException SQL実行例外
     */
    public RsvSisGrpModel getExGrpIdData(String grpId) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        RsvSisGrpModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSG_SID,");
            sql.addSql("   RSK_SID,");
            sql.addSql("   RSG_ID,");
            sql.addSql("   RSG_NAME,");
            sql.addSql("   RSG_ADM_KBN,");
            sql.addSql("   RSG_SORT,");
            sql.addSql("   RSG_ACS_LIMIT_KBN,");
            sql.addSql("   RSG_APPR_KBN,");
            sql.addSql("   RSG_AUID,");
            sql.addSql("   RSG_ADATE,");
            sql.addSql("   RSG_EUID,");
            sql.addSql("   RSG_EDATE");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_GRP");
            sql.addSql(" where ");
            sql.addSql("   RSG_ID=?");
            sql.addStrValue(grpId);

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = new RsvSisGrpModel();
                ret = __getRsvSisGrpFromRs(rs);
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
     * <br>[機  能] 指定したグループIDのグループ情報を取得する
     * <br>[解  説]
     * <br>[備  考] 指定したグループSIDは除く
     * @param grpId グループID
     * @param grpSid 排除するグループSID
     * @return RsvSisGrpModel グループ情報
     * @throws SQLException SQL実行例外
     */
    public RsvSisGrpModel getGrpIdData(String grpId, int grpSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        RsvSisGrpModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSG_SID,");
            sql.addSql("   RSK_SID,");
            sql.addSql("   RSG_ID,");
            sql.addSql("   RSG_NAME,");
            sql.addSql("   RSG_ADM_KBN,");
            sql.addSql("   RSG_SORT,");
            sql.addSql("   RSG_ACS_LIMIT_KBN,");
            sql.addSql("   RSG_APPR_KBN,");
            sql.addSql("   RSG_AUID,");
            sql.addSql("   RSG_ADATE,");
            sql.addSql("   RSG_EUID,");
            sql.addSql("   RSG_EDATE");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_GRP");
            sql.addSql(" where ");
            sql.addSql("   RSG_ID=?");
            sql.addSql(" and ");
            sql.addSql("   RSG_SID<>?");
            sql.addStrValue(grpId);
            sql.addIntValue(grpSid);

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = new RsvSisGrpModel();
                ret = __getRsvSisGrpFromRs(rs);
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
     * <br>[機  能] メイン表示設定されている施設グループ情報を取得する。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param usrSid ユーザSID
     * @return ret ArrayList in Rsv240DspModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Rsv240DspModel> mainDspGrpData(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Rsv240DspModel> ret = new ArrayList<Rsv240DspModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RSV_SIS_GRP.RSG_SID as RSG_SID,");
            sql.addSql("   RSV_SIS_GRP.RSK_SID as RSK_SID,");
            sql.addSql("   RSV_SIS_GRP.RSG_ID as RSG_ID,");
            sql.addSql("   RSV_SIS_GRP.RSG_NAME as RSG_NAME,");
            sql.addSql("   RSV_SIS_GRP.RSG_ADM_KBN as RSG_ADM_KBN,");
            sql.addSql("   RSV_SIS_GRP.RSG_SORT as RSG_SORT,");
            sql.addSql("   RSV_SIS_GRP.RSG_ACS_LIMIT_KBN as RSG_ACS_LIMIT_KBN,");
            sql.addSql("   RSV_SIS_GRP.RSG_APPR_KBN as RSG_APPR_KBN,");
            sql.addSql("   RSV_SIS_GRP.RSG_AUID as RSG_AUID,");
            sql.addSql("   RSV_SIS_GRP.RSG_ADATE as RSG_ADATE,");
            sql.addSql("   RSV_SIS_GRP.RSG_EUID as RSG_EUID,");
            sql.addSql("   RSV_SIS_GRP.RSG_EDATE as RSG_EDATE,");
            sql.addSql("   RSV_SIS_MAIN.RSM_DSP_KBN as RSM_DSP_KBN");
            sql.addSql(" from ");
            sql.addSql("   RSV_SIS_GRP,");
            sql.addSql("   RSV_SIS_MAIN");
            sql.addSql(" where ");
            sql.addSql("   RSV_SIS_MAIN.USR_SID = ?");
            sql.addSql(" and ");
            sql.addSql("   RSV_SIS_GRP.RSG_SID = RSV_SIS_MAIN.RSG_SID ");
            sql.addSql(" order by ");
            sql.addSql("   RSV_SIS_GRP.RSG_SORT asc ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                Rsv240DspModel bean = new Rsv240DspModel();
                bean.setRsgSid(rs.getInt("RSG_SID"));
                bean.setRskSid(rs.getInt("RSK_SID"));
                bean.setRsgId(rs.getString("RSG_ID"));
                bean.setRsgName(rs.getString("RSG_NAME"));
                bean.setRsgAdmKbn(rs.getInt("RSG_ADM_KBN"));
                bean.setRsgSort(rs.getInt("RSG_SORT"));
                bean.setRsgAcsLimitKbn(rs.getInt("RSG_ACS_LIMIT_KBN"));
                bean.setRsgApprKbn(rs.getInt("RSG_APPR_KBN"));
                bean.setRsgAuid(rs.getInt("RSG_AUID"));
                bean.setRsgAdate(UDate.getInstanceTimestamp(rs.getTimestamp("RSG_ADATE")));
                bean.setRsgEuid(rs.getInt("RSG_EUID"));
                bean.setRsgEdate(UDate.getInstanceTimestamp(rs.getTimestamp("RSG_EDATE")));
                bean.setRsgEuid(rs.getInt("RSG_EUID"));
                bean.setRsmDspKbn(rs.getInt("RSM_DSP_KBN"));
                ret.add(bean);
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
     * <p>指定した施設グループ情報を取得する。
     * @param rsgSids GroupSids
     * @return RSV_SIS_GRPModel
     * @throws SQLException SQL実行例外
     */
    public List<RsvSisGrpModel> select(String[] rsgSids) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<RsvSisGrpModel> ret = new ArrayList<RsvSisGrpModel>();
        if (rsgSids == null || rsgSids.length < 1) {
            return ret;
        }
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSG_SID,");
            sql.addSql("   RSK_SID,");
            sql.addSql("   RSG_ID,");
            sql.addSql("   RSG_NAME,");
            sql.addSql("   RSG_ADM_KBN,");
            sql.addSql("   RSG_SORT,");
            sql.addSql("   RSG_ACS_LIMIT_KBN,");
            sql.addSql("   RSG_APPR_KBN,");
            sql.addSql("   RSG_AUID,");
            sql.addSql("   RSG_ADATE,");
            sql.addSql("   RSG_EUID,");
            sql.addSql("   RSG_EDATE");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_GRP");
            sql.addSql(" where ");
            int i = 0;
            for (String rsgSid : rsgSids) {
                if (i > 0) {
                    sql.addSql(" or ");
                }
                sql.addSql("   RSG_SID=?");
                sql.addIntValue(Integer.parseInt(rsgSid));
                i++;
           }

            sql.addSql(" order by ");
            sql.addSql("   RSG_SORT ASC");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRsvSisGrpFromRs(rs));
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
     * <p>Delete RSV_SIS_GRP
     * @param bean RSV_SIS_GRP Model
     * @return count delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(RsvSisGrpModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_GRP");
            sql.addSql(" where ");
            sql.addSql("   RSG_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] 施設グループの状態に応じ件数を取得する
     * <br>[解  説]
     * <br>[備  考] 全ての施設グループを対象とし、
     *              管理者の設定されている施設グループ数と
     *              権限設定をしない施設グループ数を取得する
     *
     * @param userSid ユーザSID
     * @return ret ReserveCommonModel
     * @throws SQLException SQL実行例外
     */
    public ReserveCommonModel getGroupCnt(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        ReserveCommonModel ret = new ReserveCommonModel();

        try {
            //「全ユーザが変更可能な」施設グループの件数を取得
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select count(*) as CNT from RSV_SIS_GRP");
            sql.addSql(" where RSG_ADM_KBN = ?");
            sql.addIntValue(GSConstReserve.RSG_ADM_KBN_NO);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret.setGroupAdmNotSetCnt(rs.getInt("CNT"));
            }
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);


            //「管理者に設定されている」施設グループの件数を取得
            sql = new SqlBuffer();
            sql.addSql(" select count(distinct RSV_SIS_GRP.RSG_SID) as CNT");
            sql.addSql(" from RSV_SIS_GRP, RSV_SIS_ADM");
            sql.addSql(" where");
            sql.addSql("   RSV_SIS_GRP.RSG_ADM_KBN = ?");
            sql.addSql(" and");
            sql.addSql("   RSV_SIS_GRP.RSG_SID = RSV_SIS_ADM.RSG_SID");
            sql.addSql(" and");
            sql.addSql("   (");
            sql.addSql("     RSV_SIS_ADM.USR_SID = ?");
            sql.addSql("   or");
            sql.addSql("     RSV_SIS_ADM.GRP_SID in (");
            sql.addSql("       select GRP_SID from CMN_BELONGM");
            sql.addSql("       where USR_SID = ?");
            sql.addSql("     )");
            sql.addSql("   )");
            sql.addIntValue(GSConstReserve.RSG_ADM_KBN_OK);
            sql.addIntValue(userSid);
            sql.addIntValue(userSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret.setGroupAdmCnt(rs.getInt("CNT"));
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
     * <br>[機  能] 指定された施設グループの管理者かカウントする
     * <br>[解  説] ユーザSID＋所属グループSIDで判定
     * <br>[備  考]
     *
     * @param grpSid 施設グループSID
     * @param userSid ユーザSID
     * @return cnt 該当件数
     * @throws SQLException SQL実行例外
     */
    public int getSelectGroupAdmCnt(int grpSid, int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        int cnt = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("   select");
            sql.addSql("      count(RSV_SIS_ADM.USR_SID) as cnt");
            sql.addSql("   from");
            sql.addSql("      RSV_SIS_GRP");
            sql.addSql("      ,RSV_SIS_ADM");
            sql.addSql("   where");
            sql.addSql("      RSV_SIS_GRP.RSG_SID = ?");
            sql.addSql("      and RSV_SIS_GRP.RSG_SID = RSV_SIS_ADM.RSG_SID");
            sql.addSql("      and RSV_SIS_GRP.RSG_ADM_KBN = ?");
            sql.addSql("      and (");
            sql.addSql("          (");
            sql.addSql("              RSV_SIS_ADM.USR_SID = ?");
            sql.addSql("              and RSV_SIS_ADM.GRP_SID = -1");
            sql.addSql("          )");
            sql.addSql("          or (");
            sql.addSql("              RSV_SIS_ADM.USR_SID = -1");
            sql.addSql("              and RSV_SIS_ADM.GRP_SID in (");
            sql.addSql("                  select");
            sql.addSql("                     GRP_SID");
            sql.addSql("                  from");
            sql.addSql("                     CMN_BELONGM");
            sql.addSql("                  where");
            sql.addSql("                     USR_SID = ?");
            sql.addSql("              )");
            sql.addSql("          )");
            sql.addSql("      )");

            sql.addIntValue(grpSid);
            sql.addIntValue(GSConstReserve.RSG_ADM_KBN_OK);
            sql.addIntValue(userSid);
            sql.addIntValue(userSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                cnt = rs.getInt("cnt");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return cnt;
    }

    /**
     * <br>[機  能] 指定された施設グループの管理者ユーザ・グループを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param grpSid 施設グループSID
     * @return ret in CmnUsrmModel
     * @throws SQLException SQL実行例外
     */
  public ArrayList<String> getDefaultAdmUser(int grpSid) throws SQLException {
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      Connection con = getCon();
      ArrayList<String> ret = new ArrayList<String>();

      try {
          //SQL文
          SqlBuffer sql = new SqlBuffer();
          sql.addSql(" select");
          sql.addSql("   RSV_SIS_ADM.USR_SID,");
          sql.addSql("   RSV_SIS_ADM.GRP_SID");
          sql.addSql(" from");
          sql.addSql("   RSV_SIS_GRP,");
          sql.addSql("   RSV_SIS_ADM,");
          sql.addSql("   CMN_USRM");
          sql.addSql(" where");
          sql.addSql("   RSV_SIS_GRP.RSG_SID = ?");
          sql.addSql(" and");
          sql.addSql("   RSV_SIS_GRP.RSG_SID = RSV_SIS_ADM.RSG_SID");
          sql.addSql(" and");
          sql.addSql("   RSV_SIS_ADM.USR_SID = CMN_USRM.USR_SID");
          sql.addSql(" and");
          sql.addSql("   CMN_USRM.USR_JKBN = ?");

          sql.addSql(" union all");

          sql.addSql(" select");
          sql.addSql("   RSV_SIS_ADM.USR_SID,");
          sql.addSql("   RSV_SIS_ADM.GRP_SID");
          sql.addSql(" from");
          sql.addSql("   RSV_SIS_GRP,");
          sql.addSql("   RSV_SIS_ADM,");
          sql.addSql("   CMN_GROUPM");
          sql.addSql(" where");
          sql.addSql("   RSV_SIS_GRP.RSG_SID = ?");
          sql.addSql(" and");
          sql.addSql("   RSV_SIS_GRP.RSG_SID = RSV_SIS_ADM.RSG_SID");
          sql.addSql(" and");
          sql.addSql("   RSV_SIS_ADM.GRP_SID = CMN_GROUPM.GRP_SID");
          sql.addSql(" and");
          sql.addSql("   CMN_GROUPM.GRP_JKBN = ?");

          sql.addSql(" order by  USR_SID");

          sql.addIntValue(grpSid);
          sql.addIntValue(GSConst.JTKBN_TOROKU);
          sql.addIntValue(grpSid);
          sql.addIntValue(GSConst.JTKBN_TOROKU);


          log__.info(sql.toLogString());

          pstmt = con.prepareStatement(sql.toSqlString());
          sql.setParameter(pstmt);
          rs = pstmt.executeQuery();

          while (rs.next()) {
              if (rs.getInt("USR_SID") == -1) {
                  ret.add(GSConstReserve.GROUP_HEADSTR + String.valueOf(rs.getInt("GRP_SID")));
              } else {
                  ret.add(String.valueOf(rs.getInt("USR_SID")));
              }
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
     * <br>[機  能] 指定された施設グループの情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param grpSid 施設グループSID
     * @return ret 取得値
     * @throws SQLException SQL実行例外
     */
    public Rsv080Model getGrpBaseData(int grpSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        Rsv080Model ret = new Rsv080Model();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSV_SIS_GRP.RSG_NAME,");
            sql.addSql("   RSV_SIS_KBN.RSK_SID,");
            sql.addSql("   RSV_SIS_KBN.RSK_NAME");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_GRP,");
            sql.addSql("   RSV_SIS_KBN");
            sql.addSql(" where");
            sql.addSql("   RSV_SIS_GRP.RSG_SID = ?");
            sql.addSql(" and");
            sql.addSql("   RSV_SIS_GRP.RSK_SID = RSV_SIS_KBN.RSK_SID");

            sql.addIntValue(grpSid);
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = new Rsv080Model();
                ret.setRsgName(rs.getString("RSG_NAME"));
                ret.setRskSid(rs.getInt("RSK_SID"));
                ret.setRskName(rs.getString("RSK_NAME"));
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
     * <br>[機  能] 指定された施設が属する施設グループの管理者かカウントする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param rsdSid 施設SID
     * @param userSid ユーザSID
     * @return true:施設グループ管理者
     * @throws SQLException SQL実行例外
     */
    public boolean isGroupAdmin(int rsdSid, int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        boolean result = false;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("   select");
            sql.addSql("     count(RSV_SIS_ADM.USR_SID) as cnt");
            sql.addSql("   from");
            sql.addSql("     RSV_SIS_DATA,");
            sql.addSql("     RSV_SIS_GRP,");
            sql.addSql("     RSV_SIS_ADM");
            sql.addSql("   where");
            sql.addSql("     RSV_SIS_DATA.RSD_SID = ?");
            sql.addSql("   and");
            sql.addSql("     RSV_SIS_GRP.RSG_ADM_KBN = ?");
            sql.addSql("   and");
            sql.addSql("     RSV_SIS_GRP.RSG_SID = RSV_SIS_DATA.RSG_SID");
            sql.addSql("   and");
            sql.addSql("     RSV_SIS_GRP.RSG_SID = RSV_SIS_ADM.RSG_SID");
            sql.addSql("   and (");
            sql.addSql("         (");
            sql.addSql("           RSV_SIS_ADM.USR_SID = ?");
            sql.addSql("           and RSV_SIS_ADM.GRP_SID = -1");
            sql.addSql("         )");
            sql.addSql("         or (");
            sql.addSql("           RSV_SIS_ADM.USR_SID = -1");
            sql.addSql("           and RSV_SIS_ADM.GRP_SID in (");
            sql.addSql("             select");
            sql.addSql("               GRP_SID");
            sql.addSql("             from");
            sql.addSql("               CMN_BELONGM");
            sql.addSql("             where");
            sql.addSql("               USR_SID = ?");
            sql.addSql("           )");
            sql.addSql("         )");
            sql.addSql("   )");

            sql.addIntValue(rsdSid);
            sql.addIntValue(GSConstReserve.RSG_ADM_KBN_OK);
            sql.addIntValue(userSid);
            sql.addIntValue(userSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                result = rs.getInt("cnt") > 0;
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return result;
    }

    /**
     * <br>[機  能] [施設情報の承認]を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param grpSid 施設グループSID
     * @return ret [施設情報の承認]
     * @throws SQLException SQL実行例外
     */
    public int getGrpApprKbn(int grpSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        int apprKbn = GSConstReserve.RSG_APPR_KBN_SISETSU;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSG_APPR_KBN");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_GRP");
            sql.addSql(" where");
            sql.addSql("   RSG_SID = ?");

            sql.addIntValue(grpSid);
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                apprKbn = rs.getInt("RSG_APPR_KBN");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return apprKbn;
    }

    /**
     * <p>Delete NTP_GYOMU_SORT
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete() throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_GRP");

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
     * <p>Create RSV_SIS_GRP Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created RsvSisGrpModel
     * @throws SQLException SQL実行例外
     */
    private RsvSisGrpModel __getRsvSisGrpFromRs(ResultSet rs) throws SQLException {
        RsvSisGrpModel bean = new RsvSisGrpModel();
        bean.setRsgSid(rs.getInt("RSG_SID"));
        bean.setRskSid(rs.getInt("RSK_SID"));
        bean.setRsgId(rs.getString("RSG_ID"));
        bean.setRsgName(rs.getString("RSG_NAME"));
        bean.setRsgAdmKbn(rs.getInt("RSG_ADM_KBN"));
        bean.setRsgSort(rs.getInt("RSG_SORT"));
        bean.setRsgAcsLimitKbn(rs.getInt("RSG_ACS_LIMIT_KBN"));
        bean.setRsgApprKbn(rs.getInt("RSG_APPR_KBN"));
        bean.setRsgAuid(rs.getInt("RSG_AUID"));
        bean.setRsgAdate(UDate.getInstanceTimestamp(rs.getTimestamp("RSG_ADATE")));
        bean.setRsgEuid(rs.getInt("RSG_EUID"));
        bean.setRsgEdate(UDate.getInstanceTimestamp(rs.getTimestamp("RSG_EDATE")));
        return bean;
    }
}