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
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] 日報情報検索用のDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class NippouDao extends AbstractDao {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(NippouDao.class);
    /** 役職 表示順 未設定 */
    public static final int POS_SORT_NONE = -1;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     */
    public NippouDao(Connection con) {
        super(con);
    }

    /**
     * <p>期首月を取得する
     * @return List in CMN_ENTER_INFModel
     * @throws SQLException SQL実行例外
     */
    public int getKishuMonth() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   ENI_KISYU");
            sql.addSql(" from ");
            sql.addSql("   CMN_ENTER_INF");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getInt("ENI_KISYU");
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
     * <br>[機  能] 指定したユーザがアクセスを許可されているユーザの所属グループを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return グループ一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getGroupBelongSpAccessUser(int userSid) throws SQLException {
        return getGroupBelongSpAccessUser(userSid, -1);
    }

    /**
     * <br>[機  能] 指定したユーザがアクセスを許可されているユーザの所属グループを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param nspAuth 権限区分
     * @return グループ一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getGroupBelongSpAccessUser(int userSid, int nspAuth) throws SQLException {

        //ユーザの「役職 並び順」を取得
        int userPosSort = getUserPosSort(userSid);

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Integer> grpList = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("    CMN_BELONGM.GRP_SID as GRP_SID");
            sql.addSql(" from");
            sql.addSql("   CMN_BELONGM,");
            sql.addSql("   NTP_SPACCESS_TARGET");
            sql.addSql(" where");
            sql.addSql("   NTP_SPACCESS_TARGET.NST_TYPE = ?");
            sql.addSql(" and");
            sql.addSql("   NTP_SPACCESS_TARGET.NSA_SID in (");
            sql.addSql("     select NSA_SID from NTP_SPACCESS_PERMIT");
            sql.addSql("     where");
            sql.addSql("      (");
            sql.addSql("       (");
            sql.addSql("         NSP_TYPE = ?");
            sql.addSql("       and");
            sql.addSql("         NSP_PSID = ?");
            sql.addSql("       )");
            sql.addSql("     or");
            sql.addSql("       (");
            sql.addSql("         NSP_TYPE = ?");
            sql.addSql("       and");
            sql.addSql("         NSP_PSID in (");
            sql.addSql("           select GRP_SID from CMN_BELONGM");
            sql.addSql("           where USR_SID = ?");
            sql.addSql("         )");
            sql.addSql("       )");

            sql.addIntValue(GSConst.ST_TYPE_USER);
            sql.addIntValue(GSConst.SP_TYPE_USER);
            sql.addIntValue(userSid);
            sql.addIntValue(GSConst.SP_TYPE_GROUP);
            sql.addIntValue(userSid);

            if (userPosSort != POS_SORT_NONE) {
                sql.addSql("     or");
                sql.addSql("       (");
                sql.addSql("         NSP_TYPE = ?");
                sql.addSql("       and");
                sql.addSql("         NSP_PSID in (");
                sql.addSql("           select POS_SID from CMN_POSITION");
                sql.addSql("           where POS_SID > 0");
                sql.addSql("           and POS_SORT >= ?");
                sql.addSql("         )");
                sql.addSql("       )");
                sql.addIntValue(GSConst.SP_TYPE_POSITION);
                sql.addIntValue(userPosSort);
            }
            sql.addSql("      )");

            if (nspAuth == GSConst.SP_AUTH_EDIT
                || nspAuth == GSConst.SP_AUTH_VIEWONLY) {
                        sql.addSql("     and");
                        sql.addSql("       NSP_AUTH = ?");
                        sql.addIntValue(nspAuth);
            }

            sql.addSql("   )");

            sql.addSql(" and");
            sql.addSql("   CMN_BELONGM.USR_SID = NTP_SPACCESS_TARGET.NST_TSID");

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                grpList.add(rs.getInt("GRP_SID"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return grpList;
    }

    /**
     * <br>[機  能] 指定したユーザに設定された役職の「並び順」を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return 役職の「並び順」
     * @throws SQLException SQL実行例外
     */
    public int getUserPosSort(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int posSort = POS_SORT_NONE;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CMN_POSITION.POS_SORT as POS_SORT");
            sql.addSql(" from ");
            sql.addSql("   CMN_POSITION,");
            sql.addSql("   CMN_USRM_INF");
            sql.addSql(" where ");
            sql.addSql("   CMN_USRM_INF.USR_SID = ?");
            sql.addSql(" and ");
            sql.addSql("   CMN_POSITION.POS_SID > 0");
            sql.addSql(" and ");
            sql.addSql("   CMN_POSITION.POS_SID = CMN_USRM_INF.POS_SID");
            sql.addIntValue(userSid);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                posSort = rs.getInt("POS_SORT");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return posSort;
    }

    /**
     * <br>[機  能] ユーザがアクセス可能なグループの一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param nspAuth 権限区分
     * @return グループ一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getAccessGrpList(int userSid, int nspAuth)
        throws SQLException {
        return getSpAccessGrpList(userSid, true, nspAuth);
    }

    /**
     * <br>[機  能] ユーザがアクセス可能なグループの一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return グループ一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getAccessGrpList(int userSid)
        throws SQLException {
        return getSpAccessGrpList(userSid, true, -1);
    }

    /**
     * <br>[機  能] ユーザがアクセス不可能なグループの一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return グループ一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getNotAccessGrpList(int userSid)
        throws SQLException {
        return getSpAccessGrpList(userSid, false, -1);
    }

    /**
     * <br>[機  能] 指定したユーザの特例アクセスグループを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param type 閲覧区分 true:閲覧可能 false:閲覧不可
     * @param nspAuth 権限区分 0:編集可 1:閲覧可
     * @return グループ一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getSpAccessGrpList(int userSid, boolean type, int nspAuth)
    throws SQLException {

        //ユーザの「役職 並び順」を取得
        int userPosSort = getUserPosSort(userSid);

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Integer> grpList = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            __setSpAccessGrpListSql(sql, userSid, type, userPosSort, nspAuth);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                grpList.add(rs.getInt("NST_TSID"));
            }

            //アクセス不可グループ
            if (!type) {
                //所属グループ内のみ共有可の場合、所属グループ以外のグループは除外する
                int sadCrange = getNadCrange();
                if (sadCrange == GSConstNippou.CRANGE_SHARE_GROUP) {
                    JDBCUtil.closeResultSet(rs);
                    JDBCUtil.closePreparedStatement(pstmt);

                    sql = new SqlBuffer();
                    sql.addSql(" select GRP_SID from CMN_GROUPM");
                    sql.addSql(" where GRP_JKBN = ?");
                    sql.addSql(" and");
                    sql.addSql("   GRP_SID not in (");
                    sql.addSql("     select GRP_SID from CMN_BELONGM");
                    sql.addSql("     where USR_SID = ?");
                    sql.addSql("   );");
                    sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);
                    sql.addIntValue(userSid);

                    log__.info(sql.toLogString());
                    pstmt = con.prepareStatement(sql.toSqlString());
                    sql.setParameter(pstmt);
                    rs = pstmt.executeQuery();

                    int notBelongGrpSid = 0;
                    while (rs.next()) {
                        notBelongGrpSid = rs.getInt("GRP_SID");
                        if (grpList.indexOf(notBelongGrpSid) < 0) {
                            grpList.add(notBelongGrpSid);
                        }
                    }
                }

                //アクセス不可グループを取得する場合、可能に設定されているグループを除外する
                List<Integer> accessGrpList = getAccessGrpList(userSid, nspAuth);
                List<Integer> notAccessGrpList = new ArrayList<Integer>();
                for (int grpSid : grpList) {
                    if (accessGrpList.indexOf(grpSid) < 0) {
                        notAccessGrpList.add(grpSid);
                    }
                }
                grpList.clear();
                grpList.addAll(notAccessGrpList);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return grpList;
    }

    /**
     * <br>[機  能] 指定したユーザの特例アクセスグループを取得するSQLを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SqlBuffer
     * @param userSid ユーザSID
     * @param type 閲覧区分 true:閲覧可能 false:閲覧不可
     * @param userPosSort ユーザの「役職 並び順」を取得
     * @param nspAuth 権限区分 0:編集可 1:閲覧可
     * @throws SQLException SQL実行時例外
     */
    private void __setSpAccessGrpListSql(SqlBuffer sql, int userSid, boolean type,
                                                            int userPosSort, int nspAuth)
    throws SQLException {

        sql.addSql("      select NST_TSID from NTP_SPACCESS_TARGET");
        sql.addSql("      where");
        sql.addSql("        NST_TYPE = ?");
        sql.addSql("      and");
        if (type) {
            sql.addSql("        NSA_SID in (");
        } else {
            sql.addSql("        NSA_SID not in (");
        }
        sql.addSql("          select NSA_SID from NTP_SPACCESS_PERMIT");
        sql.addSql("          where");
        sql.addSql("            (");
        sql.addSql("              (");
        sql.addSql("                NSP_TYPE = ?");
        sql.addSql("              and");
        sql.addSql("                NSP_PSID = ?");
        sql.addSql("              )");
        sql.addSql("            or");
        sql.addSql("              (");
        sql.addSql("                NSP_TYPE = ?");
        sql.addSql("              and");
        sql.addSql("                NSP_PSID in (");
        sql.addSql("                  select GRP_SID from CMN_BELONGM");
        sql.addSql("                  where USR_SID = ?");
        sql.addSql("                )");
        sql.addSql("              )");
        sql.addIntValue(GSConst.ST_TYPE_GROUP);
        sql.addIntValue(GSConst.SP_TYPE_USER);
        sql.addIntValue(userSid);
        sql.addIntValue(GSConst.SP_TYPE_GROUP);
        sql.addIntValue(userSid);

        if (userPosSort != POS_SORT_NONE) {
            sql.addSql("            or");
            sql.addSql("              (");
            sql.addSql("                NSP_TYPE = ?");
            sql.addSql("              and");
            sql.addSql("                NSP_PSID in (");
            sql.addSql("                  select POS_SID from CMN_POSITION");
            sql.addSql("                  where POS_SID > 0");
            sql.addSql("                  and POS_SORT >= ?");
            sql.addSql("                )");
            sql.addSql("              )");
            sql.addIntValue(GSConst.SP_TYPE_POSITION);
            sql.addIntValue(userPosSort);
        }
        sql.addSql("            )");

        if (nspAuth == GSConst.SP_AUTH_EDIT
            ||  nspAuth == GSConst.SP_AUTH_VIEWONLY) {
            sql.addSql("          and");
            sql.addSql("            NSP_AUTH = ?");
            sql.addIntValue(nspAuth);
        }

        sql.addSql("        )");
    }

    /**
     * <br>[機  能] 日報管理者設定 共有範囲を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return 日報管理者設定 共有範囲
     * @throws SQLException SQL実行時例外
     */
    public int getNadCrange() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int sadCrange = GSConstNippou.CRANGE_SHARE_ALL;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NAC_CRANGE");
            sql.addSql(" from ");
            sql.addSql("   NTP_ADM_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                sadCrange = rs.getInt("NAC_CRANGE");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return sadCrange;
    }

    /**
     * <br>[機  能] 特例アクセスユーザの一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param grpSid グループSID
     * @param userSid ユーザSID
     * @param type 閲覧区分 true:閲覧可能 false:閲覧不可
     * @param nspAuth 権限区分
     * @return 特例アクセスユーザの一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getSpAccessUserList(int grpSid, int userSid, boolean type, int nspAuth)
    throws SQLException {
        //ユーザの「役職 並び順」を取得
        int userPosSort = getUserPosSort(userSid);

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Integer> userList = new ArrayList<Integer>();
        con = getCon();

        try {
            //特例アクセスユーザを取得
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NTP_SPACCESS_TARGET.NST_TSID as USR_SID");
            sql.addSql(" from");
            sql.addSql("   NTP_SPACCESS_TARGET");
            sql.addSql(" where");
            sql.addSql("   NTP_SPACCESS_TARGET.NST_TYPE = ?");
            sql.addIntValue(GSConst.ST_TYPE_USER);
            __setSpAccessUserListSql(sql, grpSid, userSid, type, userPosSort, nspAuth);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                userList.add(rs.getInt("USR_SID"));
            }
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closePreparedStatement(pstmt);

            //特例アクセス グループに所属するユーザを取得
            sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CMN_BELONGM.USR_SID as USR_SID");
            sql.addSql(" from");
            sql.addSql("   NTP_SPACCESS_TARGET,");
            sql.addSql("   CMN_BELONGM");
            sql.addSql(" where");
            sql.addSql("   NTP_SPACCESS_TARGET.NST_TYPE = ?");
            sql.addSql(" and");
            sql.addSql("   CMN_BELONGM.GRP_SID = NTP_SPACCESS_TARGET.NST_TSID");
            sql.addIntValue(GSConst.ST_TYPE_GROUP);
            __setSpAccessUserListSql(sql, grpSid, userSid, type, userPosSort, nspAuth);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            int belongUsrSid = 0;
            while (rs.next()) {
                belongUsrSid = rs.getInt("USR_SID");
                if (userList.indexOf(belongUsrSid) < 0) {
                    userList.add(belongUsrSid);
                }
            }
            if (!type) {
                //所属グループ内のみ共有可の場合、所属グループ以外のユーザは除外する
                int sadCrange = getNadCrange();
                if (sadCrange == GSConstNippou.CRANGE_SHARE_GROUP) {
                    JDBCUtil.closeResultSet(rs);
                    JDBCUtil.closePreparedStatement(pstmt);

                    sql = new SqlBuffer();
                    sql.addSql(" select USR_SID from CMN_USRM");
                    sql.addSql(" where USR_JKBN = ?");
                    sql.addSql(" and");
                    sql.addSql("   USR_SID not in (");
                    sql.addSql("   select");
                    sql.addSql("     CMN_BELONGM.USR_SID");
                    sql.addSql("   from");
                    sql.addSql("     CMN_BELONGM,");
                    sql.addSql("     CMN_BELONGM BELONGM2");
                    sql.addSql("   where");
                    sql.addSql("     BELONGM2.USR_SID = ?");
                    sql.addSql("   and");
                    sql.addSql("     CMN_BELONGM.GRP_SID = BELONGM2.GRP_SID");
                    sql.addSql("   );");
                    sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);
                    sql.addIntValue(userSid);

                    log__.info(sql.toLogString());
                    pstmt = con.prepareStatement(sql.toSqlString());
                    sql.setParameter(pstmt);
                    rs = pstmt.executeQuery();

                    int notBelongUsrSid = 0;
                    while (rs.next()) {
                        notBelongUsrSid = rs.getInt("USR_SID");
                        if (userList.indexOf(notBelongUsrSid) < 0) {
                            userList.add(notBelongUsrSid);
                        }
                    }
                }
                //アクセス不可ユーザを取得する場合、アクセス可能に設定されているユーザを除外する
                List<Integer> accessUserList = getSpAccessUserList(grpSid, userSid, true, nspAuth);
                List<Integer> notAccessUserList = new ArrayList<Integer>();
                for (int notAccessUserSid : userList) {
                    if (accessUserList.indexOf(notAccessUserSid) < 0) {
                        notAccessUserList.add(notAccessUserSid);
                    }
                }
                userList.clear();
                userList.addAll(notAccessUserList);
                //判定対象ユーザを閲覧不可ユーザから除外する
                int userIndex = userList.indexOf(userSid);
                if (userIndex >= 0) {
                    userList.remove(userIndex);
                }

            } else {
                //判定対象ユーザを閲覧可能ユーザに含める
                if (userList.indexOf(userSid) < 0) {
                    userList.add(userSid);
                }
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return userList;
    }


    /**
     * <br>[機  能] 特例アクセスユーザ取得時のSQLを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SqlBuffer
     * @param grpSid グループSID
     * @param userSid ユーザSID
     * @param type 閲覧区分 true:閲覧可能 false:閲覧不可
     * @param userPosSort ユーザの「役職 並び順」
     * @param nspAuth 権限区分
     * @throws SQLException SQL実行時例外
     */
    public void __setSpAccessUserListSql(SqlBuffer sql, int grpSid, int userSid,
                                                            boolean type, int userPosSort,
                                                            int nspAuth)
    throws SQLException {
        sql.addSql(" and");
        if (type) {
            sql.addSql("   NTP_SPACCESS_TARGET.NSA_SID in (");
        } else {
            sql.addSql("   NTP_SPACCESS_TARGET.NSA_SID not in (");
        }
        sql.addSql("     select NSA_SID from NTP_SPACCESS_PERMIT");
        sql.addSql("     where");
        sql.addSql("       (");
        sql.addSql("         (");
        sql.addSql("           NSP_TYPE = ?");
        sql.addSql("         and");
        sql.addSql("           NSP_PSID = ?");
        sql.addSql("         )");
        sql.addSql("       or");
        sql.addSql("         (");
        sql.addSql("           NSP_TYPE = ?");
        sql.addSql("         and");
        sql.addSql("           NSP_PSID in (");
        sql.addSql("             select GRP_SID from CMN_BELONGM");
        sql.addSql("             where USR_SID = ?");
        sql.addSql("           )");
        sql.addSql("         )");
        sql.addIntValue(GSConst.SP_TYPE_USER);
        sql.addIntValue(userSid);
        sql.addIntValue(GSConst.SP_TYPE_GROUP);
        sql.addIntValue(userSid);

        if (userPosSort != POS_SORT_NONE) {
            sql.addSql("       or");
            sql.addSql("         (");
            sql.addSql("           NSP_TYPE = ?");
            sql.addSql("         and");
            sql.addSql("           NSP_PSID in (");
            sql.addSql("             select POS_SID from CMN_POSITION");
            sql.addSql("             where POS_SID > 0");
            sql.addSql("             and POS_SORT >= ?");
            sql.addSql("           )");
            sql.addSql("         )");
            sql.addIntValue(GSConst.SP_TYPE_POSITION);
            sql.addIntValue(userPosSort);
        }

        sql.addSql("       )");

        if (nspAuth == GSConst.SP_AUTH_EDIT
        || nspAuth == GSConst.SP_AUTH_VIEWONLY) {
            sql.addSql("     and");
            sql.addSql("       NSP_AUTH = ?");
            sql.addIntValue(nspAuth);
        }

        sql.addSql("   )");
    }

    /**
     * <br>[機  能] 日報の閲覧が不可能なユーザの一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return ユーザ一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getNotAccessUserList(int userSid) throws SQLException {
        return getSpAccessUserList(-1, userSid, false, -1);
    }

    /**
     * <br>[機  能] 日報の閲覧が不可能なユーザの一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param grpSid グループSID
     * @param userSid ユーザSID
     * @return ユーザ一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getNotAccessUserList(int grpSid, int userSid) throws SQLException {
        return getSpAccessUserList(grpSid, userSid, false, -1);
    }

    /**
     * <br>[機  能] 指定したユーザの日報を登録可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param sessionUserSid セッションユーザSID
     * @return true: 閲覧可能 false: 閲覧不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canRegistUserNippou(int userSid, int sessionUserSid)
    throws SQLException {
        List<Integer> notRegistUserList
            = getNotRegistUserList(sessionUserSid);

        return notRegistUserList.indexOf(userSid) < 0;
    }

    /**
     * <br>[機  能] 日報の登録・編集・削除が不可能なユーザの一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return ユーザ一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getNotRegistUserList(int userSid) throws SQLException {
        return getSpAccessUserList(-1, userSid, false, GSConst.SP_AUTH_VIEWONLY);
    }
    /**
     *
     * <br>[機  能] 日報管理者権限をもつユーザSIDを一覧取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return  日報管理者SID一覧
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<Integer> getNippouAdminUsrSids() throws SQLException {
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
            sql.addSql("     CMN_PLUGIN_ADMIN");
            sql.addSql("   where ");
            sql.addSql("     PCT_PID=? and USR_SID > 0");
            sql.addStrValue(GSConstNippou.PLUGIN_ID_NIPPOU);
            sql.addSql("   union all ");
            sql.addSql("   select ");
            sql.addSql("     CMN_BELONGM.USR_SID as USR_SID");
            sql.addSql("   from");
            sql.addSql("     CMN_PLUGIN_ADMIN");
            sql.addSql("     ,CMN_BELONGM");
            sql.addSql("   where ");
            sql.addSql("     CMN_PLUGIN_ADMIN.PCT_PID=? ");
            sql.addStrValue(GSConstNippou.PLUGIN_ID_NIPPOU);
            sql.addSql("     and CMN_PLUGIN_ADMIN.GRP_SID = CMN_BELONGM.GRP_SID");
            sql.addSql("   union all ");
            sql.addSql("   select ");
            sql.addSql("     CMN_BELONGM.USR_SID as USR_SID");
            sql.addSql("   from");
            sql.addSql("     CMN_BELONGM");
            sql.addSql("   where ");
            sql.addSql("     CMN_BELONGM.GRP_SID = 0");
            sql.addSql(" ) ");

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
