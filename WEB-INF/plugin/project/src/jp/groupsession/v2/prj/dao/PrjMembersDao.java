package jp.groupsession.v2.prj.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.model.PrjMemberEditModel;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.model.PrjMembersModel;
import jp.groupsession.v2.prj.model.UserModel;
import jp.groupsession.v2.prj.prj150.Prj150MemberForm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>PRJ_MEMBERS Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class PrjMembersDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(PrjMembersDao.class);

    /**
     * <p>Default Constructor
     */
    public PrjMembersDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public PrjMembersDao(Connection con) {
        super(con);
    }

    /**
     * <p>Insert PRJ_MEMBERS Data Bindding JavaBean
     * @param bean PRJ_MEMBERS Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(PrjMembersModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" PRJ_MEMBERS(");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   PRM_EMPLOYEE_KBN,");
            sql.addSql("   PRM_ADMIN_KBN,");
            sql.addSql("   PRM_AUID,");
            sql.addSql("   PRM_ADATE,");
            sql.addSql("   PRM_EUID,");
            sql.addSql("   PRM_EDATE,");
            sql.addSql("   PRM_MEM_KEY,");
            sql.addSql("   PRM_SORT");
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
            sql.addIntValue(bean.getPrjSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getPrmEmployeeKbn());
            sql.addIntValue(bean.getPrmAdminKbn());
            sql.addIntValue(bean.getPrmAuid());
            sql.addDateValue(bean.getPrmAdate());
            sql.addIntValue(bean.getPrmEuid());
            sql.addDateValue(bean.getPrmEdate());
            sql.addStrValue(bean.getPrmMemKey());
            sql.addIntValue(bean.getPrmSort());
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
     * <p>Update PRJ_MEMBERS Data Bindding JavaBean
     * @param bean PRJ_MEMBERS Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(PrjMembersModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   PRJ_MEMBERS");
            sql.addSql(" set ");
            sql.addSql("   PRM_ADMIN_KBN=?,");
            sql.addSql("   PRM_AUID=?,");
            sql.addSql("   PRM_ADATE=?,");
            sql.addSql("   PRM_EUID=?,");
            sql.addSql("   PRM_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   PRM_EMPLOYEE_KBN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getPrmAdminKbn());
            sql.addIntValue(bean.getPrmAuid());
            sql.addDateValue(bean.getPrmAdate());
            sql.addIntValue(bean.getPrmEuid());
            sql.addDateValue(bean.getPrmEdate());
            //where
            sql.addIntValue(bean.getPrjSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getPrmEmployeeKbn());

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
     * <br>[機  能] プロジェクトメンバーの件数をカウント
     * <br>[解  説]
     * <br>[備  考]
     * @param projectSid プロジェクトSID
     * @param userSid ユーザSID
     * @param adminFlg 管理者取得フラグ true=管理者のみ取得する、false=管理者以外も取得する
     * @return int 件数
     * @throws SQLException SQL実行例外
     */
    public int getMemberCount(int projectSid, int userSid, boolean adminFlg)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    count(*) as CNT");
            sql.addSql("  from");
            sql.addSql("    PRJ_MEMBERS");
            sql.addSql("  where");
            sql.addSql("    PRJ_SID = ?");
            sql.addSql("  and");
            sql.addSql("    USR_SID = ?");

            sql.addIntValue(projectSid);
            sql.addIntValue(userSid);

            if (adminFlg) {
                sql.addSql("  and");
                sql.addSql("    PRM_ADMIN_KBN = ?");
                sql.addIntValue(GSConstProject.KBN_POWER_ADMIN);
            }

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = rs.getInt("CNT");
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
     * <br>[機  能] プロジェクトメンバーの件数をカウント
     * <br>[解  説]
     * <br>[備  考]
     * @param projectSid プロジェクトSID
     * @param userSid ユーザSID
     * @return int 件数
     * @throws SQLException SQL実行例外
     */
    public int getMemberCount(int projectSid, String[] userSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        if (userSid == null) {
            return ret;
        }
        if (userSid.length < 1) {
            return ret;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    count(*) as CNT");
            sql.addSql("  from");
            sql.addSql("    PRJ_MEMBERS");
            sql.addSql("  where");
            sql.addSql("    PRJ_SID = ?");
            sql.addIntValue(projectSid);

            sql.addSql("  and");
            sql.addSql("   USR_SID in ( ");

            for (int i = 0; i < userSid.length; i++) {
                if (i > 0) {
                    sql.addSql("     , ");
                }
                sql.addSql("     ?");
                sql.addIntValue(Integer.parseInt(userSid[i]));
            }
            sql.addSql("        )");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = rs.getInt("CNT");
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
     * <br>[機  能] プロジェクトメンバー情報を取得する
     * <br>[解  説] メンバーの氏名、状態区分、管理者区分、役職等も取得
     * <br>[備  考]
     * @param projectSid プロジェクトSID
     * @param adminFlg 管理者取得フラグ true=管理者のみ取得する、false=管理者以外も取得する
     * @return List in UserModel
     * @throws SQLException SQL実行例外
     */
    public List<UserModel> getMemberList(int projectSid, boolean adminFlg)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<UserModel> ret = new ArrayList<UserModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    CMN_USRM.USR_SID,");
            sql.addSql("    CMN_USRM.USR_JKBN,");
            sql.addSql("    CMN_USRM.USR_UKO_FLG,");
            sql.addSql("    CMN_USRM_INF.USI_SEI,");
            sql.addSql("    CMN_USRM_INF.USI_MEI,");
            sql.addSql("    PRJ_MEMBERS.PRM_ADMIN_KBN,");
            sql.addSql("    PRJ_MEMBERS.PRM_MEM_KEY,");
            sql.addSql("    PRJ_MEMBERS.PRM_SORT,");
            sql.addSql("    CMN_USR_INOUT.UIO_STATUS,");
            sql.addSql("    CMN_USR_INOUT.UIO_BIKO,");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID > 0 "
                           + "and PRJ_MEMBERS.PRM_ADMIN_KBN = ? then 0");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 "
                           + "and PRJ_MEMBERS.PRM_ADMIN_KBN = ? then 1");
            sql.addSql("      when CMN_USRM_INF.POS_SID > 0 then 2");
            sql.addSql("      else 3");
            sql.addSql("    end) as YAKUSYOKU_EXIST,");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then ''");
            sql.addSql("      else CMN_POSITION.POS_NAME");
            sql.addSql("    end) as YAKUSYOKU_NAME,");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then 0");
            sql.addSql("      else (select");
            sql.addSql("              POS_SORT");
            sql.addSql("            from");
            sql.addSql("              CMN_POSITION");
            sql.addSql("            where CMN_USRM_INF.POS_SID = CMN_POSITION.POS_SID)");
            sql.addSql("    end) as YAKUSYOKU_SORT");
            sql.addSql("  from");
            sql.addSql("    PRJ_MEMBERS,");
            sql.addSql("    CMN_USRM,");
            sql.addSql("    CMN_USRM_INF,");
            sql.addSql("    CMN_POSITION,");
            sql.addSql("    CMN_USR_INOUT");
            sql.addSql("  where");
            sql.addSql("    PRJ_MEMBERS.USR_SID = CMN_USRM_INF.USR_SID");
            sql.addSql("  and");
            sql.addSql("    CMN_USRM_INF.USR_SID = CMN_USRM.USR_SID");
            sql.addSql("  and");
            sql.addSql("    CMN_USRM_INF.POS_SID = CMN_POSITION.POS_SID");
            sql.addSql("  and");
            sql.addSql("    PRJ_MEMBERS.PRJ_SID = ?");
            sql.addSql("  and");
            sql.addSql("    CMN_USRM_INF.USR_SID = CMN_USR_INOUT.UIO_SID");

            sql.addIntValue(GSConstProject.KBN_POWER_ADMIN);
            sql.addIntValue(GSConstProject.KBN_POWER_ADMIN);
            sql.addIntValue(projectSid);

            if (adminFlg) {
                sql.addSql("  and");
                sql.addSql("    PRJ_MEMBERS.PRM_ADMIN_KBN = ?");
                sql.addIntValue(GSConstProject.KBN_POWER_ADMIN);
            }

            sql.addSql(" order by ");
            sql.addSql("   PRJ_MEMBERS.PRM_SORT asc,");
            sql.addSql("   YAKUSYOKU_EXIST asc,");
            sql.addSql("   YAKUSYOKU_SORT asc,");
            sql.addSql("   USI_SEI_KN asc,");
            sql.addSql("   USI_MEI_KN asc");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                UserModel bean = new UserModel();
                bean.setUserSid(rs.getInt("USR_SID"));
                bean.setStatus(rs.getInt("USR_JKBN"));
                bean.setSei(rs.getString("USI_SEI"));
                bean.setMei(rs.getString("USI_MEI"));
                bean.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
                bean.setAdminKbn(rs.getInt("PRM_ADMIN_KBN"));
                bean.setPosition(rs.getString("YAKUSYOKU_NAME"));
                bean.setMemberKey(rs.getString("PRM_MEM_KEY"));
                bean.setZaisekiKbn(rs.getInt("UIO_STATUS"));
                bean.setZaisekiMsg(rs.getString("UIO_BIKO"));
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
     * <br>[機  能] プロジェクトメンバー情報を取得する
     * <br>[解  説] メンバーの氏名、状態区分、管理者区分、役職等も取得
     * <br>[備  考]
     * @param projectSid プロジェクトSID
     * @return List in UserModel
     * @throws SQLException SQL実行例外
     */
    public List<PrjMemberEditModel> getMemberList(int projectSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<PrjMemberEditModel> ret = new ArrayList<PrjMemberEditModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    CMN_USRM.USR_SID,");
            sql.addSql("    CMN_USRM.USR_JKBN,");
            sql.addSql("    CMN_USRM.USR_UKO_FLG,");
            sql.addSql("    CMN_USRM_INF.USI_SEI,");
            sql.addSql("    CMN_USRM_INF.USI_MEI,");
            sql.addSql("    PRJ_MEMBERS.PRM_MEM_KEY,");
            sql.addSql("    PRJ_MEMBERS.PRM_ADMIN_KBN,");
            sql.addSql("    PRJ_MEMBERS.PRM_SORT,");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID > 0 "
                           + "and PRJ_MEMBERS.PRM_ADMIN_KBN = ? then 0");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 "
                           + "and PRJ_MEMBERS.PRM_ADMIN_KBN = ? then 1");
            sql.addSql("      when CMN_USRM_INF.POS_SID > 0 then 2");
            sql.addSql("      else 3");
            sql.addSql("    end) as YAKUSYOKU_EXIST,");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then ''");
            sql.addSql("      else CMN_POSITION.POS_NAME");
            sql.addSql("    end) as YAKUSYOKU_NAME");
            sql.addSql("  from");
            sql.addSql("    PRJ_MEMBERS,");
            sql.addSql("    CMN_USRM,");
            sql.addSql("    CMN_USRM_INF,");
            sql.addSql("    CMN_POSITION");
            sql.addSql("  where");
            sql.addSql("    PRJ_MEMBERS.USR_SID = CMN_USRM_INF.USR_SID");
            sql.addSql("  and");
            sql.addSql("    CMN_USRM_INF.USR_SID = CMN_USRM.USR_SID");
            sql.addSql("  and");
            sql.addSql("    CMN_USRM_INF.POS_SID = CMN_POSITION.POS_SID");
            sql.addSql("  and");
            sql.addSql("    PRJ_MEMBERS.PRJ_SID = ?");

            sql.addIntValue(GSConstProject.KBN_POWER_ADMIN);
            sql.addIntValue(GSConstProject.KBN_POWER_ADMIN);
            sql.addIntValue(projectSid);

            sql.addSql(" order by");
            sql.addSql("  PRJ_MEMBERS.PRM_SORT asc,");
            sql.addSql("  YAKUSYOKU_EXIST asc,");
            sql.addSql("  CMN_POSITION.POS_SORT asc");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                PrjMemberEditModel bean = new PrjMemberEditModel();
                bean.setUsrSid(rs.getInt("USR_SID"));
                bean.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
                bean.setUsiSei(rs.getString("USI_SEI"));
                bean.setUsiMei(rs.getString("USI_MEI"));
                bean.setMemberKey(rs.getString("PRM_MEM_KEY"));
                bean.setPrmAdminKbn(rs.getInt("PRM_ADMIN_KBN"));
                bean.setSort(rs.getInt("PRM_SORT"));
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
     * <br>[機  能] プロジェクトメンバー情報を取得する
     * <br>[解  説] メンバーの氏名、状態区分、管理者区分、役職等も取得
     * <br>[備  考]
     * @param projectSid プロジェクトSID
     * @param usrSid セッションユーザSID(自分自身)
     * @return List in UserModel
     * @throws SQLException SQL実行例外
     */
    public List<UserModel> getMemberList(int projectSid, int usrSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<UserModel> ret = new ArrayList<UserModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    CMN_USRM.USR_SID,");
            sql.addSql("    CMN_USRM.USR_JKBN,");
            sql.addSql("    CMN_USRM.USR_UKO_FLG,");
            sql.addSql("    CMN_USRM_INF.USI_SEI,");
            sql.addSql("    CMN_USRM_INF.USI_MEI,");
            sql.addSql("    PRJ_MEMBERS.PRM_ADMIN_KBN,");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then 1");
            sql.addSql("      else 0");
            sql.addSql("    end) as YAKUSYOKU_EXIST,");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then ''");
            sql.addSql("      else CMN_POSITION.POS_NAME");
            sql.addSql("    end) as YAKUSYOKU_NAME");
            sql.addSql("  from");
            sql.addSql("    PRJ_MEMBERS,");
            sql.addSql("    CMN_USRM,");
            sql.addSql("    CMN_USRM_INF,");
            sql.addSql("    CMN_POSITION");
            sql.addSql("  where");
            sql.addSql("    PRJ_MEMBERS.USR_SID = CMN_USRM_INF.USR_SID");
            sql.addSql("  and");
            sql.addSql("    CMN_USRM_INF.USR_SID = CMN_USRM.USR_SID");
            sql.addSql("  and");
            sql.addSql("    CMN_USRM_INF.POS_SID = CMN_POSITION.POS_SID");
            sql.addSql("  and");
            sql.addSql("    PRJ_MEMBERS.PRJ_SID = ?");
            sql.addSql("  and");
            sql.addSql("    PRJ_MEMBERS.USR_SID <> ?");
            sql.addSql(" order by");
            sql.addSql("  PRJ_MEMBERS.PRM_SORT asc,");
            sql.addSql("  YAKUSYOKU_EXIST asc,");
            sql.addSql("  CMN_POSITION.POS_SORT asc");

            sql.addIntValue(projectSid);
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                UserModel bean = new UserModel();
                bean.setUserSid(rs.getInt("USR_SID"));
                bean.setStatus(rs.getInt("USR_JKBN"));
                bean.setSei(rs.getString("USI_SEI"));
                bean.setMei(rs.getString("USI_MEI"));
                bean.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
                bean.setAdminKbn(rs.getInt("PRM_ADMIN_KBN"));
                bean.setPosition(rs.getString("YAKUSYOKU_NAME"));
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
     * <br>[機  能] プロジェクトメンバー情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param projectSid プロジェクトSID
     * @param userSid 除外するユーザSID
     * @param jkbn 状態区分 -1:すべて
     * @return List in UserModel
     * @throws SQLException SQL実行例外
     */
    public List<UserModel> getMemberList(int projectSid,
            String[] userSid, int jkbn) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<UserModel> ret = new ArrayList<UserModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("    CMN_USRM.USR_JKBN,");
            sql.addSql("    CMN_USRM.USR_UKO_FLG,");
            sql.addSql("   CMN_USRM_INF.USR_SID,");
            sql.addSql("   CMN_USRM_INF.USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI,");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then 1");
            sql.addSql("      else 0");
            sql.addSql("    end) as YAKUSYOKU_EXIST");
            sql.addSql(" from");
            sql.addSql("   PRJ_MEMBERS,");
            sql.addSql("   CMN_USRM,");
            sql.addSql("   CMN_USRM_INF,");
            sql.addSql("   CMN_POSITION");
            sql.addSql(" where");
            sql.addSql("   PRJ_MEMBERS.USR_SID = CMN_USRM.USR_SID");
            sql.addSql(" and");
            sql.addSql("   PRJ_MEMBERS.USR_SID = CMN_USRM_INF.USR_SID");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM_INF.POS_SID = CMN_POSITION.POS_SID");
            sql.addSql(" and");
            sql.addSql("   PRJ_MEMBERS.PRJ_SID = ?");
            sql.addIntValue(projectSid);
            if (jkbn >= 0) {
                sql.addSql(" and");
                sql.addSql("   CMN_USRM.USR_JKBN = ?");
                sql.addIntValue(jkbn);
            }
            if (userSid != null && userSid.length > 0) {
                sql.addSql(" and");
                sql.addSql("   CMN_USRM_INF.USR_SID not in (");
                for (int i = 0; i < userSid.length; i++) {
                    if (i > 0) {
                        sql.addSql("     , ");
                    }
                    sql.addSql("     ?");
                    sql.addIntValue(Integer.parseInt(userSid[i]));
                }
                sql.addSql("        )");
            }

            sql.addSql(" order by");
            sql.addSql("  PRJ_MEMBERS.PRM_SORT asc,");
            sql.addSql("  YAKUSYOKU_EXIST asc,");
            sql.addSql("  CMN_POSITION.POS_SORT asc");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                UserModel bean = new UserModel();
                bean.setUserSid(rs.getInt("USR_SID"));
                bean.setSei(rs.getString("USI_SEI"));
                bean.setMei(rs.getString("USI_MEI"));
                bean.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
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
     * <br>[機  能] プロジェクト管理者メンバー情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param prjSid PRJ_SID
     * @return PRJ_MEMBERSModel
     * @throws SQLException SQL実行例外
     */
    public List<PrjMembersModel> getPrjKanriMembers(int prjSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<PrjMembersModel> ret = new ArrayList<PrjMembersModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   PRM_EMPLOYEE_KBN,");
            sql.addSql("   PRM_ADMIN_KBN,");
            sql.addSql("   PRM_AUID,");
            sql.addSql("   PRM_ADATE,");
            sql.addSql("   PRM_EUID,");
            sql.addSql("   PRM_EDATE,");
            sql.addSql("   PRM_MEM_KEY,");
            sql.addSql("   PRM_SORT");
            sql.addSql(" from");
            sql.addSql("   PRJ_MEMBERS");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");
            sql.addSql(" and ");
            sql.addSql("   PRM_ADMIN_KBN=1");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(prjSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getPrjMembersFromRs(rs));

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
     * <br>[機  能] プロジェクトメンバー情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param prjSid PRJ_SID
     * @return PRJ_MEMBERSModel
     * @throws SQLException SQL実行例外
     */
    public List<PrjMembersModel> getPrjMembers(int prjSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<PrjMembersModel> ret = new ArrayList<PrjMembersModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   PRM_EMPLOYEE_KBN,");
            sql.addSql("   PRM_ADMIN_KBN,");
            sql.addSql("   PRM_AUID,");
            sql.addSql("   PRM_ADATE,");
            sql.addSql("   PRM_EUID,");
            sql.addSql("   PRM_EDATE,");
            sql.addSql("   PRM_MEM_KEY,");
            sql.addSql("   PRM_SORT");
            sql.addSql(" from");
            sql.addSql("   PRJ_MEMBERS");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(prjSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getPrjMembersFromRs(rs));

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
     * <p>Select PRJ_MEMBERS
     * @return PRJ_MEMBERSModel
     * @throws SQLException SQL実行例外
     */
    public List<PrjMembersModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<PrjMembersModel> ret = new ArrayList<PrjMembersModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   PRM_EMPLOYEE_KBN,");
            sql.addSql("   PRM_ADMIN_KBN,");
            sql.addSql("   PRM_AUID,");
            sql.addSql("   PRM_ADATE,");
            sql.addSql("   PRM_EUID,");
            sql.addSql("   PRM_EDATE,");
            sql.addSql("   PRM_MEM_KEY,");
            sql.addSql("   PRM_SORT");
            sql.addSql(" from");
            sql.addSql("   PRJ_MEMBERS");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getPrjMembersFromRs(rs));
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
     * <br>[機  能] プロジェクトメンバー情報マッピングを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param prjSid PRJ_SID
     * @return HashMap in PRJ_MEMBERSModel
     * @throws SQLException SQL実行例外
     */
    public HashMap<String, PrjMembersModel> selectProjectsMap(int prjSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        HashMap<String, PrjMembersModel> ret =
            new HashMap<String, PrjMembersModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   PRM_EMPLOYEE_KBN,");
            sql.addSql("   PRM_ADMIN_KBN,");
            sql.addSql("   PRM_AUID,");
            sql.addSql("   PRM_ADATE,");
            sql.addSql("   PRM_EUID,");
            sql.addSql("   PRM_EDATE,");
            sql.addSql("   PRM_MEM_KEY,");
            sql.addSql("   PRM_SORT");
            sql.addSql(" from");
            sql.addSql("   PRJ_MEMBERS");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(prjSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                if (!StringUtil.isNullZeroString(rs.getString("PRM_MEM_KEY"))) {
                    PrjMembersModel mdl = __getPrjMembersFromRs(rs);
                    ret.put(mdl.getPrmMemKey(), mdl);
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
     * <p>Select PRJ_MEMBERS
     * @param prjSid PRJ_SID
     * @param usrSid USR_SID
     * @param prmEmployeeKbn PRM_EMPLOYEE_KBN
     * @return PRJ_MEMBERSModel
     * @throws SQLException SQL実行例外
     */
    public PrjMembersModel select(int prjSid, int usrSid, int prmEmployeeKbn) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        PrjMembersModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   PRM_EMPLOYEE_KBN,");
            sql.addSql("   PRM_ADMIN_KBN,");
            sql.addSql("   PRM_AUID,");
            sql.addSql("   PRM_ADATE,");
            sql.addSql("   PRM_EUID,");
            sql.addSql("   PRM_EDATE,");
            sql.addSql("   PRM_MEM_KEY,");
            sql.addSql("   PRM_SORT");
            sql.addSql(" from");
            sql.addSql("   PRJ_MEMBERS");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   PRM_EMPLOYEE_KBN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(prjSid);
            sql.addIntValue(usrSid);
            sql.addIntValue(prmEmployeeKbn);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getPrjMembersFromRs(rs);
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
     * <p>指定したユーザがプロジェクトのメンバーか判定する
     * @param prjSid プロジェクトSID
     * @param usrSid ユーザSID
     * @return true:可  false:不可
     * @throws SQLException SQL実行例外
     */
    public boolean isCheckPrjMember(int prjSid, int usrSid)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int cnt = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from");
            sql.addSql("   PRJ_MEMBERS");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   PRM_EMPLOYEE_KBN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(prjSid);
            sql.addIntValue(usrSid);
            sql.addIntValue(GSConstProject.KBN_PROJECT_MEMBER_INNER);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                cnt = rs.getInt("CNT");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return cnt > 0;
    }

    /**
     * <p>Delete PRJ_MEMBERS
     * @param prjSid PRJ_SID
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int prjSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   PRJ_MEMBERS");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");
            sql.addIntValue(prjSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] プロジェクトメンバーIDを登録する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param formArray 更新フォームリスト
     * @param prjSid プロジェクトSID
     * @param usrSid セッションユーザSID
     * @param now システム日付
     * @param prjAdminList プロジェクト管理者リスト
     * @throws SQLException 例外
     */
    public void addUserKey(ArrayList<Prj150MemberForm> formArray,
                               int prjSid,
                               int usrSid,
                               UDate now,
                               List<PrjMembersModel> prjAdminList)
        throws SQLException {

        int count = 1;
        for (Prj150MemberForm mdl : formArray) {

            String memberKey =
                NullDefault.getString(
                        mdl.getProjectMemberKey(), "");


                PrjMembersModel bean = new PrjMembersModel();
                bean.setPrjSid(prjSid);
                bean.setUsrSid(mdl.getUsrSid());
                bean.setPrmEmployeeKbn(GSConstProject.KBN_PROJECT_MEMBER_INNER);
                bean.setPrmAdminKbn(GSConstProject.KBN_POWER_NORMAL);

                //プロジェクトの管理者かどうか
                for (int i = 0; i < prjAdminList.size(); i++) {
                    if (prjAdminList.get(i).getUsrSid() == mdl.getUsrSid()) {
                        bean.setPrmAdminKbn(GSConstProject.KBN_POWER_ADMIN);
                    }
                }
                bean.setPrmAuid(usrSid);
                bean.setPrmAdate(now);
                bean.setPrmEuid(usrSid);
                bean.setPrmEdate(now);
                bean.setPrmMemKey(memberKey);
                bean.setPrmSort(count);
                insert(bean);
                count++;
        }
    }

    /**
     * <br>[機  能] プロジェクトメンバーIDを登録する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param formArray 更新フォームリスト
     * @param prjSid プロジェクトSID
     * @param usrSid セッションユーザSID
     * @param now システム日付
     * @throws SQLException 例外
     */
    public void updateUserKey(ArrayList<Prj150MemberForm> formArray,
                               int prjSid,
                               int usrSid,
                               UDate now)
        throws SQLException {

        if (formArray == null || formArray.isEmpty()) {
            return;
        }

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   PRJ_MEMBERS");
            sql.addSql(" set");
            sql.addSql("   PRM_MEM_KEY = ?,");
            sql.addSql("   PRM_EUID = ?,");
            sql.addSql("   PRM_EDATE = ?");
            sql.addSql(" where");
            sql.addSql("   PRJ_SID = ?");
            sql.addSql(" and");
            sql.addSql("   USR_SID = ?");
            sql.addSql(" and");
            sql.addSql("   PRM_EMPLOYEE_KBN = ?");

            pstmt = con.prepareStatement(sql.toSqlString());

            for (Prj150MemberForm mdl : formArray) {

                String memberKey =
                    NullDefault.getString(
                            mdl.getProjectMemberKey(), "");

                pstmt.setString(1, memberKey);
                pstmt.setInt(2, usrSid);
                pstmt.setTimestamp(3, now.toSQLTimestamp());
                pstmt.setInt(4, prjSid);
                pstmt.setInt(5, mdl.getUsrSid());
                pstmt.setInt(6, GSConstProject.KBN_PROJECT_MEMBER_INNER);

                //ログ出力
                sql.addStrValue(memberKey);
                sql.addIntValue(usrSid);
                sql.addDateValue(now);
                sql.addIntValue(prjSid);
                sql.addIntValue(mdl.getUsrSid());
                sql.addIntValue(GSConstProject.KBN_PROJECT_MEMBER_INNER);

                log__.info(sql.toLogString());
                sql.clearValue();

                if (pstmt.executeUpdate() == 0) {
                    PrjMembersModel bean = new PrjMembersModel();
                    bean.setPrjSid(prjSid);
                    bean.setUsrSid(mdl.getUsrSid());
                    bean.setPrmEmployeeKbn(GSConstProject.KBN_PROJECT_MEMBER_INNER);
                    bean.setPrmAdminKbn(GSConstProject.KBN_POWER_NORMAL);
                    bean.setPrmAuid(usrSid);
                    bean.setPrmAdate(now);
                    bean.setPrmEuid(usrSid);
                    bean.setPrmEdate(now);
                    bean.setPrmMemKey(memberKey);
                    insert(bean);
                }
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] プロジェクトメンバーIDを削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param formArray 更新フォームリスト
     * @param prjSid プロジェクトSID
     * @throws SQLException 例外
     */
    public void deleteUserKey(ArrayList<Prj150MemberForm> formArray, int prjSid)
        throws SQLException {

        if (formArray == null || formArray.isEmpty()) {
            return;
        }

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete from PRJ_MEMBERS");
            sql.addSql(" where");
            sql.addSql("   PRJ_SID = ?");
            sql.addIntValue(prjSid);

            sql.addSql(" and");
            if (formArray.size() == 1) {
                sql.addSql("   USR_SID != ?");
                sql.addIntValue(formArray.get(0).getUsrSid());
            } else {
                sql.addSql("   USR_SID not in ( ?");
                sql.addIntValue(formArray.get(0).getUsrSid());

                for (int idx = 1; idx < formArray.size(); idx++) {
                    sql.addSql("     ,?");
                    sql.addIntValue(formArray.get(idx).getUsrSid());
                }
                sql.addSql("   )");
            }

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Create PRJ_MEMBERS Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created PrjMembersModel
     * @throws SQLException SQL実行例外
     */
    private PrjMembersModel __getPrjMembersFromRs(ResultSet rs) throws SQLException {
        PrjMembersModel bean = new PrjMembersModel();
        bean.setPrjSid(rs.getInt("PRJ_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setPrmEmployeeKbn(rs.getInt("PRM_EMPLOYEE_KBN"));
        bean.setPrmAdminKbn(rs.getInt("PRM_ADMIN_KBN"));
        bean.setPrmAuid(rs.getInt("PRM_AUID"));
        bean.setPrmAdate(UDate.getInstanceTimestamp(rs.getTimestamp("PRM_ADATE")));
        bean.setPrmEuid(rs.getInt("PRM_EUID"));
        bean.setPrmEdate(UDate.getInstanceTimestamp(rs.getTimestamp("PRM_EDATE")));
        bean.setPrmMemKey(rs.getString("PRM_MEM_KEY"));
        bean.setPrmSort(rs.getInt("PRM_SORT"));
        return bean;
    }
}