package jp.groupsession.v2.ntp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.ntp.model.NtpSmlMemberModel;

/**
 * <p>NTP_SML_MEMBER Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class NtpSmlMemberDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(NtpSmlMemberDao.class);

    /**
     * <p>Default Constructor
     */
    public NtpSmlMemberDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public NtpSmlMemberDao(Connection con) {
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
            sql.addSql("drop table NTP_SML_MEMBER");

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
            sql.addSql(" create table NTP_SML_MEMBER (");
            sql.addSql("   NSM_USR_SID NUMBER(10,0) not null,");
            sql.addSql("   GRP_SID     NUMBER(10,0) not null,");
            sql.addSql("   USR_SID     NUMBER(10,0) not null,");
            sql.addSql("   NSM_GRP     NUMBER(10,0) not null default 0,");
            sql.addSql("   primary key (NSM_USR_SID,GRP_SID,USR_SID,NSM_GRP)");
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
     * <p>Insert NTP_SML_MEMBER Data Bindding JavaBean
     * @param bean NTP_SML_MEMBER Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(NtpSmlMemberModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" NTP_SML_MEMBER(");
            sql.addSql("   NSM_USR_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   NSM_GRP");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getNsmUsrSid());
            sql.addIntValue(bean.getGrpSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getNsmGrpKbn());

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
     * <p>Update NTP_SML_MEMBER Data Bindding JavaBean
     * @param bean NTP_SML_MEMBER Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(NtpSmlMemberModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   NTP_SML_MEMBER");
            sql.addSql(" set ");
            sql.addSql(" where ");
            sql.addSql("   NSM_USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   GRP_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   NSM_GRP=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            //where
            sql.addIntValue(bean.getNsmUsrSid());
            sql.addIntValue(bean.getGrpSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getNsmGrpKbn());

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
     * <p>Select NTP_SML_MEMBER All Data
     * @return List in NTP_SML_MEMBERModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<NtpSmlMemberModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<NtpSmlMemberModel> ret = new ArrayList<NtpSmlMemberModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NSM_USR_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   NSM_GRP");
            sql.addSql(" from ");
            sql.addSql("   NTP_SML_MEMBER");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getNtpSmlMemberFromRs(rs));
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
     * <p>Select NTP_SML_MEMBER All Data
     * @param usrSid NSM_USR_SID
     * @return List in NTP_SML_MEMBERModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<NtpSmlMemberModel> select(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<NtpSmlMemberModel> ret = new ArrayList<NtpSmlMemberModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NSM_USR_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   NSM_GRP");
            sql.addSql(" from ");
            sql.addSql("   NTP_SML_MEMBER");
            sql.addSql(" where ");
            sql.addSql("   NSM_USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getNtpSmlMemberFromRs(rs));
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
     * 指定したユーザSIDを登録しているユーザを取得する
     * @param usrSid NSM_USR_SID
     * @param usrSids 除外するユーザSID
     * @return List in NTP_SML_MEMBERModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Integer> getSelUsrSid(
            int usrSid, ArrayList<Integer> usrSids) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NSM_USR_SID");
            sql.addSql(" from ");
            sql.addSql("   NTP_SML_MEMBER");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addIntValue(usrSid);
            if (usrSids != null && usrSids.size() > 0) {
                sql.addSql(" and ");
                sql.addSql("   NSM_USR_SID not in( ");
                for (int i = 0; i < usrSids.size(); i++) {
                    if (i == 0) {
                        sql.addSql("  ? ");
                    } else {
                        sql.addSql("  ,? ");
                    }
                    sql.addIntValue(usrSids.get(i).intValue());
                }
                sql.addSql("  ) ");
            }
            sql.addSql(" group by NSM_USR_SID ");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(rs.getInt("NSM_USR_SID"));
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
     * 指定したグループSIDを登録しているユーザを取得する
     * @param grpSids グループSID
     * @param usrSids 除外するユーザSID
     * @param myGpFlg マイグループフラグ false:通常 true:マイグループ
     * @return List in NTP_SML_MEMBERModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Integer> getSelGrpSid(ArrayList<Integer> grpSids,
                                           ArrayList<Integer> usrSids,
                                           boolean myGpFlg) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NSM_USR_SID");
            sql.addSql(" from ");
            sql.addSql("   NTP_SML_MEMBER");
            sql.addSql(" where ");
            sql.addSql("   GRP_SID in ( ");
            for (int i = 0; i < grpSids.size(); i++) {
                if (i == 0) {
                    sql.addSql("  ? ");
                } else {
                    sql.addSql("  ,? ");
                }
                sql.addIntValue(grpSids.get(i).intValue());
            }
            sql.addSql("  ) ");
            if (usrSids != null && usrSids.size() > 0) {
                sql.addSql(" and ");
                sql.addSql("   NSM_USR_SID not in( ");
                for (int i = 0; i < usrSids.size(); i++) {
                    if (i == 0) {
                        sql.addSql("  ? ");
                    } else {
                        sql.addSql("  ,? ");
                    }
                    sql.addIntValue(usrSids.get(i).intValue());
                }
                sql.addSql("  ) ");
            }
            sql.addSql(" and ");
            sql.addSql(" NSM_GRP =?");

            if (myGpFlg) {
                sql.addIntValue(1);
            } else {
                sql.addIntValue(0);
            }

            sql.addSql(" group by NSM_USR_SID ");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(rs.getInt("NSM_USR_SID"));
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
     * <p>Select NTP_SML_MEMBER
     * @param pctPid NSM_USR_SID
     * @param grpSid GRP_SID
     * @param usrSid USR_SID
     * @return NTP_SML_MEMBERModel
     * @throws SQLException SQL実行例外
     */
    public NtpSmlMemberModel select(int pctPid, int grpSid, int usrSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        NtpSmlMemberModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NSM_USR_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   USR_SID");
            sql.addSql("   NSM_GRP");
            sql.addSql(" from");
            sql.addSql("   NTP_SML_MEMBER");
            sql.addSql(" where ");
            sql.addSql("   NSM_USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   GRP_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(pctPid);
            sql.addIntValue(grpSid);
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getNtpSmlMemberFromRs(rs);
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
     * <br>[機  能] 指定したユーザがアクセス権設定されているプラグインのプラグインIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param pluginIdList プラグインID
     * @param userSid ユーザSID
     * @return プラグインSID
     * @throws SQLException SQL実行例外
     */
    public List<String> getCantUsePluginList(List<String> pluginIdList, int userSid)
    throws SQLException {

        List<String> ret = new ArrayList<String>();
        if (pluginIdList == null || pluginIdList.isEmpty()) {
            return ret;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NSM_USR_SID");
            sql.addSql(" from ");
            sql.addSql("   NTP_SML_MEMBER");

            sql.addSql(" where ");
            if (pluginIdList.size() == 1) {
                sql.addSql("   NSM_USR_SID = ?");
                sql.addStrValue(pluginIdList.get(0));
            } else {
                sql.addSql("   NSM_USR_SID in (");
                sql.addSql("     ?");
                sql.addStrValue(pluginIdList.get(0));

                for (int idx = 1; idx < pluginIdList.size(); idx++) {
                    sql.addSql("     ,?");
                    sql.addStrValue(pluginIdList.get(idx));
                }
                sql.addSql("   )");
            }

            sql.addSql(" and");
            sql.addSql("   (");
            sql.addSql("      USR_SID = ?");
            sql.addSql("    or");
            sql.addSql("      GRP_SID in (");
            sql.addSql("        select GRP_SID from CMN_BELONGM");
            sql.addSql("        where USR_SID = ?");
            sql.addSql("      )");
            sql.addSql("   )");
            sql.addIntValue(userSid);
            sql.addIntValue(userSid);

            sql.addSql(" group by");
            sql.addSql("   NSM_USR_SID");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getString("NSM_USR_SID"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }

//    /**
//     * <br>[機  能] 指定したプラグインを使用可能なユーザを取得します。
//     * <br>[解  説]
//     * <br>[備  考]
//     * @param pluginId プラグインID
//     * @param userSidList ユーザSID
//     * @return ユーザSID
//     * @throws SQLException SQL実行例外
//     */
//    public List<Integer> getCantUseUserList(String pluginId, List<Integer> userSidList)
//    throws SQLException {
//
//        List<Integer> ret = new ArrayList<Integer>();
//        if (userSidList == null || userSidList.isEmpty()) {
//            return ret;
//        }
//
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        Connection con = null;
//        con = getCon();
//
//        try {
//            //SQL文
//            SqlBuffer sql = new SqlBuffer();
//
//            sql.addSql(" select");
//            sql.addSql("   CMN_BELONGM.USR_SID as BELONGM_USR_SID,");
//            sql.addSql("   NTP_SML_MEMBER.USR_SID as USR_SID");
//            sql.addSql(" from");
//            sql.addSql("   NTP_SML_MEMBER");
//            sql.addSql("   left join");
//            sql.addSql("     CMN_BELONGM");
//            sql.addSql("   on");
//            sql.addSql("     NTP_SML_MEMBER.GRP_SID >= 0");
//            sql.addSql("   and");
//            sql.addSql("     NTP_SML_MEMBER.GRP_SID = CMN_BELONGM.GRP_SID");
//            sql.addSql(" where");
//            sql.addSql("   NTP_SML_MEMBER.NSM_USR_SID = ?");
//            sql.addStrValue(pluginId);
//
//            String usrSidSql = "";
//            if (userSidList.size() == 1) {
//                usrSidSql += " = " + userSidList.get(0).toString();
//            } else {
//                usrSidSql += " in ( " + userSidList.get(0).toString();
//                for (int idx = 1; idx < userSidList.size(); idx++) {
//                    usrSidSql += ", " + userSidList.get(idx);
//                }
//                usrSidSql += ")";
//            }
//
//            sql.addSql(" and");
//            sql.addSql("   (");
//            sql.addSql("     (");
//            sql.addSql("       NTP_SML_MEMBER.GRP_SID >= 0");
//            sql.addSql("     and");
//            sql.addSql("       CMN_BELONGM.USR_SID" + usrSidSql);
//            sql.addSql("     )");
//            sql.addSql("    or");
//            sql.addSql("     (");
//            sql.addSql("       NTP_SML_MEMBER.GRP_SID < 0");
//            sql.addSql("     and");
//            sql.addSql("       NTP_SML_MEMBER.USR_SID" + usrSidSql);
//            sql.addSql("     )");
//            sql.addSql("   )");
//            sql.addSql(" group by");
//            sql.addSql("   CMN_BELONGM.USR_SID,");
//            sql.addSql("   NTP_SML_MEMBER.USR_SID");
//
//            log__.info(sql.toLogString());
//
//            pstmt = con.prepareStatement(sql.toSqlString());
//            sql.setParameter(pstmt);
//            rs = pstmt.executeQuery();
//            while (rs.next()) {
//                Integer userSid = rs.getInt("BELONGM_USR_SID");
//                if (userSid > 0 && ret.indexOf(userSid) < 0) {
//                    ret.add(userSid);
//                } else {
//                    userSid = rs.getInt("USR_SID");
//                    if (userSid > 0 && ret.indexOf(userSid) < 0) {
//                        ret.add(userSid);
//                    }
//                }
//
//            }
//        } catch (SQLException e) {
//            throw e;
//        } finally {
//            JDBCUtil.closeResultSet(rs);
//            JDBCUtil.closeStatement(pstmt);
//        }
//        return ret;
//    }
//
//    /**
//     * <br>[機  能] 指定したプラグインを使用可能なグループを取得します。
//     * <br>[解  説]
//     * <br>[備  考]
//     * @param pluginId プラグインID
//     * @param grpSidList グループSID
//     * @return グループSID
//     * @throws SQLException SQL実行例外
//     */
//    public List<Integer> getCantUseGroupList(String pluginId, List<Integer> grpSidList)
//    throws SQLException {
//
//        List<Integer> ret = new ArrayList<Integer>();
//        if (grpSidList == null || grpSidList.isEmpty()) {
//            return ret;
//        }
//
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        Connection con = null;
//        con = getCon();
//
//        try {
//            //SQL文
//            SqlBuffer sql = new SqlBuffer();
//
//            sql.addSql(" select");
//            sql.addSql("   GRP_SID");
//            sql.addSql(" from");
//            sql.addSql("   NTP_SML_MEMBER");
//            sql.addSql(" where");
//            sql.addSql("   NSM_USR_SID = ?");
//            sql.addStrValue(pluginId);
//
//            sql.addSql(" and");
//            if (grpSidList.size() == 1) {
//                sql.addSql("   GRP_SID = ?");
//                sql.addIntValue(grpSidList.get(0));
//            } else {
//                sql.addSql("   GRP_SID in (?");
//                sql.addIntValue(grpSidList.get(0));
//                for (int idx = 1; idx < grpSidList.size(); idx++) {
//                    sql.addSql("      ,?");
//                    sql.addIntValue(grpSidList.get(idx));
//                }
//                sql.addSql("   )");
//            }
//
//            log__.info(sql.toLogString());
//
//            pstmt = con.prepareStatement(sql.toSqlString());
//            sql.setParameter(pstmt);
//            rs = pstmt.executeQuery();
//            while (rs.next()) {
//                ret.add(rs.getInt("GRP_SID"));
//            }
//        } catch (SQLException e) {
//            throw e;
//        } finally {
//            JDBCUtil.closeResultSet(rs);
//            JDBCUtil.closeStatement(pstmt);
//        }
//        return ret;
//    }

    /**
     * <p>Delete NTP_SML_MEMBER
     * @param usrSid NSM_USR_SID
     * @return delete count
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
            sql.addSql("   NTP_SML_MEMBER");
            sql.addSql(" where ");
            sql.addSql("   NSM_USR_SID=?");

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
     * <p>Delete NTP_SML_MEMBER
     * @param pctPid NSM_USR_SID
     * @param grpSid GRP_SID
     * @param usrSid USR_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(String pctPid, int grpSid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   NTP_SML_MEMBER");
            sql.addSql(" where ");
            sql.addSql("   NSM_USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   GRP_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(pctPid);
            sql.addIntValue(grpSid);
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
     * <p>Delete NTP_SML_MEMBER
     * @param grpSid GRP_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int deleteForGroup(int grpSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   NTP_SML_MEMBER");
            sql.addSql(" where ");
            sql.addSql("   GRP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(grpSid);

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
     * <p>Delete NTP_SML_MEMBER
     * @param usrSid USR_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int deleteForUser(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   NTP_SML_MEMBER");
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
     * <p>Create NTP_SML_MEMBER Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created NtpSmlMemberModel
     * @throws SQLException SQL実行例外
     */
    private NtpSmlMemberModel __getNtpSmlMemberFromRs(ResultSet rs)
    throws SQLException {
        NtpSmlMemberModel bean = new NtpSmlMemberModel();
        bean.setNsmUsrSid(rs.getInt("NSM_USR_SID"));
        bean.setGrpSid(rs.getInt("GRP_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setNsmGrpKbn(rs.getInt("NSM_GRP"));
        return bean;
    }
}
