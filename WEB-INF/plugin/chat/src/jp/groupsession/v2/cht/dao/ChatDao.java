package jp.groupsession.v2.cht.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cht.GSConstChat;
import jp.groupsession.v2.cht.model.ChatDeleteModel;
import jp.groupsession.v2.cht.model.ChatGroupInfModel;
import jp.groupsession.v2.cht.model.ChatInformationModel;
import jp.groupsession.v2.cht.model.ChatMessageModel;
import jp.groupsession.v2.cht.model.ChatMidokuModel;
import jp.groupsession.v2.cht.model.ChatSearchModel;
import jp.groupsession.v2.cht.model.ChatUserInfModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.usr.UserUtil;

/**
 *
 * <br>[機  能]チャットDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ChatDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ChatDao.class);

    /**
     * <p>Default Constructor
     */
    public ChatDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public ChatDao(Connection con) {
        super(con);
    }

    /**
     * お気に入り登録されているユーザSIDを取得する
     * @param usrSid ユーザSID
     * @return List in CHT_FAVORITEModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<ChatUserInfModel> favoriteUserSelect(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ChatUserInfModel> ret = new ArrayList<ChatUserInfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select ");
            sql.addSql("    CMN_USRM_INF.USR_SID,");
            sql.addSql("    CMN_USRM_INF.USI_SEI,");
            sql.addSql("    CMN_USRM_INF.USI_MEI,");
            sql.addSql("    CMN_USRM.USR_JKBN,");
            sql.addSql("    CMN_USRM.USR_UKO_FLG,");
            sql.addSql("    VIEW_DATA.CNT");
            sql.addSql("  from ");
            sql.addSql("    CMN_USRM_INF");
            sql.addSql("  left join ");
            sql.addSql("    CMN_USRM");
            sql.addSql("  on ");
            sql.addSql("    CMN_USRM_INF.USR_SID = CMN_USRM.USR_SID");
            sql.addSql("  left join");
            sql.addSql("    (");
            sql.addSql("     select");
            sql.addSql("       SID_DATA.CUP_SID,");
            sql.addSql("       SID_DATA.USR_SID,");
            sql.addSql("       CNT_DATA.CNT as CNT");
            sql.addSql("    from");
            sql.addSql("      (");
            sql.addSql("        select");
            sql.addSql("          CHT_USER_PAIR.CUP_SID,");
            sql.addSql("          case");
            sql.addSql("            when CHT_USER_PAIR.CUP_UID_F = ? then CHT_USER_PAIR.CUP_UID_S");
            sql.addSql("            else CHT_USER_PAIR.CUP_UID_F");
            sql.addSql("           end as USR_SID");
            sql.addSql("         from");
            sql.addSql("           CHT_USER_PAIR");
            sql.addSql("         where");
            sql.addSql("           (");
            sql.addSql("              CUP_UID_F IN (");
            sql.addSql("                select");
            sql.addSql("                  CHF_SID");
            sql.addSql("                from");
            sql.addSql("                  CHT_FAVORITE");
            sql.addSql("                where");
            sql.addSql("                  CHF_UID = ?");
            sql.addSql("                and");
            sql.addSql("                  CHF_CHAT_KBN = ?");
            sql.addSql("              )");
            sql.addSql("            and");
            sql.addSql("              CUP_UID_S = ?");
            sql.addSql("           )");
            sql.addSql("         or");
            sql.addSql("           ( ");
            sql.addSql("              CUP_UID_S IN (");
            sql.addSql("                select");
            sql.addSql("                  CHF_SID");
            sql.addSql("                from");
            sql.addSql("                  CHT_FAVORITE");
            sql.addSql("                where");
            sql.addSql("                  CHF_UID = ?");
            sql.addSql("                and");
            sql.addSql("                  CHF_CHAT_KBN = ?");
            sql.addSql("              )");
            sql.addSql("            and");
            sql.addSql("              CUP_UID_F = ?");
            sql.addSql("           )");
            sql.addSql("       )SID_DATA");
            sql.addSql("     left join");
            sql.addSql("       (");
            sql.addIntValue(usrSid);
            sql.addIntValue(usrSid);
            sql.addIntValue(GSConstChat.CHAT_KBN_USER);
            sql.addIntValue(usrSid);
            sql.addIntValue(usrSid);
            sql.addIntValue(GSConstChat.CHAT_KBN_USER);
            sql.addIntValue(usrSid);

            writeQueryUserTimeline(sql, usrSid);

            sql.addSql("       ) CNT_DATA");
            sql.addSql("     on");
            sql.addSql("       SID_DATA.CUP_SID = CNT_DATA.PAIR_SID");
            sql.addSql("    ) VIEW_DATA");
            sql.addSql("  on");
            sql.addSql("    CMN_USRM_INF.USR_SID = VIEW_DATA.USR_SID");
            sql.addSql("  where ");
            sql.addSql("    CMN_USRM_INF.USR_SID IN (");
            sql.addSql("      select");
            sql.addSql("        CHF_SID");
            sql.addSql("      from");
            sql.addSql("        CHT_FAVORITE");
            sql.addSql("      where");
            sql.addSql("        CHF_UID = ?");
            sql.addSql("      and");
            sql.addSql("        CHF_CHAT_KBN = ?");
            sql.addSql("     )");



            sql.addIntValue(usrSid);
            sql.addIntValue(GSConstChat.CHAT_KBN_USER);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ChatUserInfModel bean = new ChatUserInfModel();
                bean.setUsrSid(rs.getInt("USR_SID"));
                bean.setUsiSei(rs.getString("USI_SEI"));
                bean.setUsiMei(rs.getString("USI_MEI"));
                bean.setUsrJkbn(rs.getInt("USR_JKBN"));
                bean.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
                bean.setChtUserCount(rs.getInt("CNT"));
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
     * <p>お気に入り登録されているグループ情報の取得
     * @param usrSid ユーザSID
     * @return List in CHT_FAVORITEModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<ChatGroupInfModel> favoriteGroupSelect(int usrSid)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ChatGroupInfModel> ret = new ArrayList<ChatGroupInfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CHT_GROUP_INF.CGI_SID,");
            sql.addSql("   CHT_GROUP_INF.CGI_NAME,");
            sql.addSql("   COUNT_DATA.CNT");
            sql.addSql(" from ");
            sql.addSql("   CHT_GROUP_INF");
            sql.addSql(" left join (");
            writeQueryGroupTimeline(sql, usrSid);
            sql.addSql(" )COUNT_DATA");
            sql.addSql(" on");
            sql.addSql("   CHT_GROUP_INF.CGI_SID = COUNT_DATA.GROUP_SID");
            sql.addSql(" where ");
            sql.addSql("   CHT_GROUP_INF.CGI_SID IN (");
            sql.addSql("     select");
            sql.addSql("       CHF_SID");
            sql.addSql("     from");
            sql.addSql("       CHT_FAVORITE");
            sql.addSql("     where");
            sql.addSql("       CHF_UID = ?");
            sql.addSql("     and");
            sql.addSql("       CHF_CHAT_KBN = ?");
            sql.addSql("    )");
            sql.addSql(" and ");
            sql.addSql("   CGI_DEL_FLG = ? ");
            sql.addSql(" order by ");
            sql.addSql("   CGI_SID ");


            sql.addIntValue(usrSid);
            sql.addIntValue(GSConstChat.CHAT_KBN_GROUP);
            sql.addIntValue(GSConstChat.CHAT_GROUP_LOGIC_NOT_DELETE);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ChatGroupInfModel bean = new ChatGroupInfModel();
                bean.setCgiSid(rs.getInt("CGI_SID"));
                bean.setCgiName(rs.getString("CGI_NAME"));
                bean.setChtGroupCount(rs.getInt("CNT"));
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
     * 削除済みユーザを取得する
     * @return List Integer
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Integer> getDelUser() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CMN_USRM.USR_SID");
            sql.addSql(" from ");
            sql.addSql("   CMN_USRM");
            sql.addSql(" where ");
            sql.addSql("   CMN_USRM.USR_JKBN = ?");

            sql.addIntValue(GSConst.JTKBN_DELETE);

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

    /**
     * <br>[機  能] 削除対象のグループチャットSID一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param delMdl 手動削除設定モデル
     * @return チャットSID一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getDelGroupChatSidList(ChatDeleteModel delMdl) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Integer> chtGrpSidList = new ArrayList<Integer>();
        SqlBuffer sql = null;

        try {
            UDate delDate = new UDate();
            delDate.setMaxHhMmSs();
            delDate.addYear((delMdl.getDelYear() * -1));
            delDate.addMonth((delMdl.getDelMonth() * -1));

            sql = new SqlBuffer();
            sql.addSql(" select CGI_SID from CHT_GROUP_INF");

            sql.addSql(" where");
            sql.addSql(" and CGI_ADATE <= ? ");
            sql.addDateValue(delDate);


            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                chtGrpSidList.add(rs.getInt("CGI_SID"));
            }
        } catch (SQLException e) {
            if (sql != null) {
                log__.error("Error SQL: " + sql.toLogString());
            }
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closePreparedStatement(pstmt);
        }

        return chtGrpSidList;
    }

    /**
     * <br>[機  能] 削除対象のユーザチャットSID一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param delMdl 手動削除設定モデル
     * @return チャットSID一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getDelUserChatSidList(ChatDeleteModel delMdl) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Integer> chtUsrSidList = new ArrayList<Integer>();
        SqlBuffer sql = null;

        try {
            UDate delDate = new UDate();
            delDate.setMaxHhMmSs();
            delDate.addYear((delMdl.getDelYear() * -1));
            delDate.addMonth((delMdl.getDelMonth() * -1));

            sql = new SqlBuffer();
            sql.addSql(" select CGI_SID from CHT_GROUP_INF");

            sql.addSql(" where");
            sql.addSql(" and CGI_ADATE <= ? ");
            sql.addDateValue(delDate);


            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                chtUsrSidList.add(rs.getInt("CGI_SID"));
            }
        } catch (SQLException e) {
            if (sql != null) {
                log__.error("Error SQL: " + sql.toLogString());
            }
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closePreparedStatement(pstmt);
        }

        return chtUsrSidList;
    }

    /**
     * <p>ユーザ間メッセージ情報の取得
     * @param cupSid CUP_SID
     * @param userSid ユーザSID
     * @param partnerViewSid 閲覧SID
     * @param kidokuFlg 既読表示フラグ
     * @param scrollFlg スクロールによる読み込み
     * @param readFlg 上か下か
     * @param endIdx スクロール最終SID
     * @return CHT_USER_DATAModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<ChatMessageModel> getUserMessage(int cupSid, int userSid,
            int partnerViewSid, boolean kidokuFlg,
            boolean scrollFlg, int readFlg, Long endIdx) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ChatMessageModel> ret = new ArrayList<ChatMessageModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   COALESCE(VIEW.VIEW_SID, 0) VIEW_SID,");
            sql.addSql("   COALESCE(VIEW.VIEW_CNT, 0) VIEW_CNT,");
            sql.addSql("   DATA.DATA_SID,");
            sql.addSql("   DATA.DATA_CNT");
            sql.addSql(" from");
            sql.addSql("   (");
            sql.addSql("    select");
            sql.addSql("      CHT_USER_DATA.CUP_SID,");
            sql.addSql("      MAX(CUD_SID) as DATA_SID,");
            sql.addSql("      COUNT(CUD_SID) as DATA_CNT");
            sql.addSql("    from");
            sql.addSql("      CHT_USER_DATA");
            sql.addSql("    where");
            sql.addSql("      CUP_SID = ?");
            sql.addSql("    group by");
            sql.addSql("      CHT_USER_DATA.CUP_SID");
            sql.addSql("   )DATA");
            sql.addSql("   left join");
            sql.addSql("   (");
            sql.addSql("    select");
            sql.addSql("      CHT_USER_VIEW.CUP_SID,");
            sql.addSql("      CHT_USER_VIEW.CUD_SID as VIEW_SID,");
            sql.addSql("      COUNT(CHT_USER_DATA.CUD_SID) as VIEW_CNT");
            sql.addSql("    from");
            sql.addSql("      CHT_USER_VIEW");
            sql.addSql("    left join");
            sql.addSql("      CHT_USER_DATA");
            sql.addSql("    on");
            sql.addSql("      CHT_USER_VIEW.CUP_SID = CHT_USER_DATA.CUP_SID");
            sql.addSql("    where");
            sql.addSql("      CHT_USER_VIEW.CUD_SID >= CHT_USER_DATA.CUD_SID");
            sql.addSql("    and");
            sql.addSql("      CHT_USER_VIEW.CUP_SID = ?");
            sql.addSql("    and");
            sql.addSql("      CHT_USER_VIEW.CUV_UID = ?");
            sql.addSql("    group by");
            sql.addSql("      CHT_USER_VIEW.CUD_SID,");
            sql.addSql("      CHT_USER_VIEW.CUP_SID");
            sql.addSql("   )VIEW");
            sql.addSql("   on");
            sql.addSql("     DATA.CUP_SID = VIEW.CUP_SID");

            sql.addIntValue(cupSid);
            sql.addIntValue(cupSid);
            sql.addIntValue(userSid);
            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            Long viewSid = (long) 0;
            int viewCnt = 0;
            int dataCnt = 0;
            if (rs.next()) {
                viewSid = rs.getLong("VIEW_SID");
                viewCnt = rs.getInt("VIEW_CNT");
                dataCnt = rs.getInt("DATA_CNT");
            }
            if (scrollFlg) {
                if (readFlg == GSConstChat.CHAT_TOP_SCROLL) {
                    ret = getUserMessageDesc(cupSid, endIdx, viewSid, partnerViewSid, kidokuFlg);
                } else {
                    ret = getUserMessage(cupSid, endIdx, viewSid, partnerViewSid, kidokuFlg);
                }
            } else if (dataCnt > viewCnt + GSConstChat.CHAT_GET_MESSAGE_NUMBER) {
                ret = getUserMessage(cupSid, viewSid, viewSid, partnerViewSid, kidokuFlg);
            } else {
                ret = getUserMessageDesc(cupSid, (long) -1, viewSid, partnerViewSid, kidokuFlg);
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
     * <br>[機  能]メッセージ情報select(ユーザ)
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SqlBuffer
     * @return sql SqlBuffer
     */
    public SqlBuffer getUserMessage(SqlBuffer sql) {
        sql.addSql(" select");
        sql.addSql("   CMN_BINF.BIN_FILE_NAME,");
        sql.addSql("   CMN_BINF.BIN_FILE_PATH,");
        sql.addSql("   CMN_BINF.BIN_FILE_SIZE,");
        sql.addSql("   CMN_USRM_INF.USI_SEI,");
        sql.addSql("   CMN_USRM_INF.USI_MEI,");
        sql.addSql("   CMN_USRM_INF.BIN_SID as USR_BIN,");
        sql.addSql("   CMN_USRM_INF.USI_PICT_KF,");
        sql.addSql("   CMN_USRM.USR_JKBN,");
        sql.addSql("   CMN_USRM.USR_UKO_FLG,");
        sql.addSql("   CHT_USER_DATA.CUD_SID,");
        sql.addSql("   CHT_USER_DATA.CUP_SID,");
        sql.addSql("   CHT_USER_DATA.CUD_TEXT,");
        sql.addSql("   CHT_USER_DATA.BIN_SID,");
        sql.addSql("   CHT_USER_DATA.CUD_SEND_UID,");
        sql.addSql("   CHT_USER_DATA.CUD_STATE_KBN,");
        sql.addSql("   CHT_USER_DATA.CUD_AUID,");
        sql.addSql("   CHT_USER_DATA.CUD_ADATE,");
        sql.addSql("   CHT_USER_DATA.CUD_EUID,");
        sql.addSql("   CHT_USER_DATA.CUD_EDATE");
        sql.addSql(" from");
        sql.addSql("   CHT_USER_DATA");
        sql.addSql(" left join");
        sql.addSql("   CMN_BINF");
        sql.addSql(" on");
        sql.addSql("   CMN_BINF.BIN_SID = CHT_USER_DATA.BIN_SID");
        sql.addSql(" left join");
        sql.addSql("   CMN_USRM_INF");
        sql.addSql(" on");
        sql.addSql("   CHT_USER_DATA.CUD_SEND_UID = CMN_USRM_INF.USR_SID");
        sql.addSql(" left join");
        sql.addSql("   CMN_USRM");
        sql.addSql(" on");
        sql.addSql("   CMN_USRM_INF.USR_SID = CMN_USRM.USR_SID");
        return sql;
    }

    /**
     * <p>ユーザ間メッセージ情報の取得
     * @param cupSid CUP_SID
     * @param startIdx 検索開始位置
     * @param viewSid 閲覧SID
     * @param partnerViewSid 閲覧SID
     * @param kidokuFlg 既読表示フラグ
     * @return CHT_USER_DATAModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<ChatMessageModel> getUserMessage(int cupSid, Long startIdx,
            Long viewSid, int partnerViewSid, boolean kidokuFlg) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ChatMessageModel> ret = new ArrayList<ChatMessageModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            getUserMessage(sql);
            sql.addSql(" where ");
            sql.addSql("   CHT_USER_DATA.CUP_SID=?");
            sql.addSql(" and ");
            sql.addSql("   CHT_USER_DATA.CUD_SID>?");
            sql.addSql(" order by ");
            sql.addSql("   CUD_SID ");
            sql.addSql(" limit ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(cupSid);
            sql.addLongValue(startIdx);
            sql.addIntValue(GSConstChat.CHAT_GET_MESSAGE_NUMBER);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getChatUsrMessageModel(rs, viewSid,
                        partnerViewSid, kidokuFlg));
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
     * <p>ユーザメッセージ情報の取得
     * @param cupSid CUP_SID
     * @param endIdx スクロール確認SID
     * @param viewSid 閲覧SID
     * @param partnerViewSid 相手の確認SID
     * @param kidokuFlg kidokuFlg
     * @return CHT_USER_DATAModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<ChatMessageModel> getUserMessageDesc(int cupSid, Long endIdx,
            Long viewSid, int partnerViewSid, boolean kidokuFlg) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ChatMessageModel> ret = new ArrayList<ChatMessageModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            getUserMessage(sql);
            sql.addSql(" where ");
            sql.addSql("   CHT_USER_DATA.CUP_SID=?");
            sql.addLongValue(cupSid);
            if (endIdx != -1) {
                sql.addSql(" and ");
                sql.addSql("   CHT_USER_DATA.CUD_SID<? ");
                sql.addLongValue(endIdx);
            }
            sql.addSql(" order by ");
            sql.addSql("   CHT_USER_DATA.CUD_SID desc ");
            sql.addSql(" limit ?");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(GSConstChat.CHAT_GET_MESSAGE_NUMBER);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            ArrayList<ChatMessageModel> save = new ArrayList<ChatMessageModel>();
            while (rs.next()) {
                save.add(__getChatUsrMessageModel(rs, viewSid,
                        partnerViewSid, kidokuFlg));
            }
            for (int idx = save.size() - 1; idx > -1; idx--) {
                ChatMessageModel cmMdl = save.get(idx);
                ret.add(cmMdl);
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
     * <p>直近2件のユーザ間チャット送信者と送信日付を取得する
     * 送信用、日付フラグを確認するために2件取得する
     * @param selectSid 選択グループSID
     * @param messageSid 投稿SID
     * @param userSid ユーザSID
     * @return CHT_USER_DATAModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<ChatMessageModel> getUserMessage(int selectSid, long messageSid, int userSid)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ChatMessageModel> ret = new ArrayList<ChatMessageModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            getUserMessage(sql);
            sql.addSql(" where ");
            sql.addSql("   CHT_USER_DATA.CUP_SID = (");
            sql.addSql("      select");
            sql.addSql("        CUP_SID");
            sql.addSql("      from");
            sql.addSql("        CHT_USER_PAIR");
            sql.addSql("      where");
            sql.addSql("        (");
            sql.addSql("           CUP_UID_F = ?");
            sql.addSql("         and");
            sql.addSql("           CUP_UID_S = ?");
            sql.addSql("        )");
            sql.addSql("        or");
            sql.addSql("        (");
            sql.addSql("           CUP_UID_F = ?");
            sql.addSql("         and");
            sql.addSql("           CUP_UID_S = ?");
            sql.addSql("        )");
            sql.addSql("     )");
            sql.addSql(" and ");
            sql.addSql("   CUD_SID <= ? ");
            sql.addSql(" order by ");
            sql.addSql("   CUD_SID desc ");
            sql.addSql(" limit ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(selectSid);
            sql.addIntValue(userSid);
            sql.addIntValue(userSid);
            sql.addIntValue(selectSid);
            sql.addLongValue(messageSid);
            sql.addIntValue(2);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getChatUsrMessageModel(rs, messageSid,
                        0, false));
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
     * <p>Create CHT_USER_DATA Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @param viewSid viewSid
     * @param partnerViewSid partnerViewSid
     * @param kidokuFlg kidokuFlg
     * @return created ChtGroupDataModel
     * @throws SQLException SQL実行例外
     */
    private ChatMessageModel __getChatUsrMessageModel(ResultSet rs,
            Long viewSid, int partnerViewSid, boolean kidokuFlg) throws SQLException {

        CmnBinfModel binMdl = new CmnBinfModel();
        CommonBiz cmnBiz = new CommonBiz();
        binMdl.setBinSid(rs.getLong("BIN_SID"));
        binMdl.setBinFileName(rs.getString("BIN_FILE_NAME"));
        binMdl.setBinFilePath(rs.getString("BIN_FILE_PATH"));
        //添付ファイルサイズ設定(表示用)
        long size = rs.getInt("BIN_FILE_SIZE");
        String strSize = cmnBiz.getByteSizeString(size);
        binMdl.setBinFileSizeDsp(strSize);

        ChatMessageModel cmMdl = new ChatMessageModel();
        cmMdl.setMessageSid(rs.getLong("CUD_SID"));
        cmMdl.setSelectSid(rs.getInt("CUP_SID"));
        cmMdl.setMessageText(rs.getString("CUD_TEXT"));
        cmMdl.setBinSid(rs.getLong("BIN_SID"));
        cmMdl.setInsertUid(rs.getInt("CUD_AUID"));
        cmMdl.setInsertDate(UDate.getInstanceTimestamp(rs.getTimestamp("CUD_ADATE")));
        cmMdl.setUpdateUid(rs.getInt("CUD_EUID"));
        cmMdl.setUpdateDate(UDate.getInstanceTimestamp(rs.getTimestamp("CUD_EDATE")));
        cmMdl.setTmpFile(binMdl);
        if (partnerViewSid >= rs.getInt("CUD_SID") && kidokuFlg) {
            //相手の既読情報登録
            cmMdl.setPartnerKidoku(GSConstChat.KIDOKU_DISP);
        }
        if (viewSid < rs.getInt("CUD_SID")) {
            //自身の既読情報登録
            cmMdl.setOwnKidoku(GSConstChat.KIDOKU_DISP);
        }
        UDate date = UDate.getInstanceTimestamp(rs.getTimestamp("CUD_ADATE"));
        cmMdl.setEntryDay(date.getStrYear() + "/"
        + date.getStrMonth() + "/" + date.getStrDay());
        cmMdl.setEntryTime(date.getStrHour() + ":" + date.getStrMinute());
        cmMdl.setUsrSid(rs.getInt("CUD_SEND_UID"));
        cmMdl.setUsrName(UserUtil.makeName(rs.getString("USI_SEI"), rs.getString("USI_MEI")));
        cmMdl.setUsrJkbn(rs.getInt("USR_JKBN"));
        cmMdl.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
        cmMdl.setUsrBinSid(rs.getInt("USR_BIN"));
        cmMdl.setUsrPictKf(rs.getInt("USI_PICT_KF"));
        UDate uDate = UDate.getInstanceTimestamp(rs.getTimestamp("CUD_EDATE"));
        cmMdl.setUpdateDay(uDate.getDateStringForSql());
        cmMdl.setUpdateTime(uDate.getStrHour() + ":" + uDate.getStrMinute());
        cmMdl.setMessageKbn(rs.getInt("CUD_STATE_KBN"));

        return cmMdl;
    }

    /**
     * <br>[機  能]グループメッセージ情報の取得
     * <br>[解  説]
     * <br>[備  考]
     * @param groupSid グループSID
     * @param userSid ユーザSID
     * @param scrollFlg スクロールフラグ
     * @param readFlg 上から下からか
     * @param scrollSid インデックス用SID
     * @return ChatMessageModelList
     * @throws SQLException SQLException
     */
    public ArrayList<ChatMessageModel> getGroupMessage(int groupSid, int userSid, boolean scrollFlg,
            int readFlg, Long scrollSid)
            throws SQLException {

        //自身の投稿SIDを取得する
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ChatMessageModel> ret = new ArrayList<ChatMessageModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   COALESCE(VIEW.VIEW_SID, 0) VIEW_SID,");
            sql.addSql("   COALESCE(VIEW.VIEW_CNT, 0) VIEW_CNT,");
            sql.addSql("   DATA.DATA_SID,");
            sql.addSql("   DATA.DATA_CNT");
            sql.addSql(" from");
            sql.addSql("   (");
            sql.addSql("    select");
            sql.addSql("      CHT_GROUP_DATA.CGI_SID,");
            sql.addSql("      MAX(CGD_SID) as DATA_SID,");
            sql.addSql("      COUNT(CGD_SID) as DATA_CNT");
            sql.addSql("    from");
            sql.addSql("      CHT_GROUP_DATA");
            sql.addSql("    where");
            sql.addSql("      CGI_SID = ?");
            sql.addSql("    group by");
            sql.addSql("      CHT_GROUP_DATA.CGI_SID");
            sql.addSql("   )DATA");
            sql.addSql("   left join");
            sql.addSql("   (");
            sql.addSql("    select");
            sql.addSql("      CHT_GROUP_VIEW.CGI_SID,");
            sql.addSql("      CHT_GROUP_VIEW.CGD_SID as VIEW_SID,");
            sql.addSql("      COUNT(CHT_GROUP_DATA.CGD_SID) as VIEW_CNT");
            sql.addSql("    from");
            sql.addSql("      CHT_GROUP_VIEW");
            sql.addSql("    left join");
            sql.addSql("      CHT_GROUP_DATA");
            sql.addSql("    on");
            sql.addSql("      CHT_GROUP_VIEW.CGI_SID = CHT_GROUP_DATA.CGI_SID");
            sql.addSql("    where");
            sql.addSql("      CHT_GROUP_VIEW.CGD_SID >= CHT_GROUP_DATA.CGD_SID");
            sql.addSql("    and");
            sql.addSql("      CHT_GROUP_VIEW.CGI_SID = ?");
            sql.addSql("    and");
            sql.addSql("      CHT_GROUP_VIEW.CGV_UID = ?");
            sql.addSql("    group by");
            sql.addSql("      CHT_GROUP_VIEW.CGD_SID,");
            sql.addSql("      CHT_GROUP_VIEW.CGI_SID");
            sql.addSql("   )VIEW");
            sql.addSql("   on");
            sql.addSql("     DATA.CGI_SID = VIEW.CGI_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(groupSid);
            sql.addIntValue(groupSid);
            sql.addIntValue(userSid);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            Long viewSid = (long) 0;
            int viewCnt = 0;
            int dataCnt = 0;
            if (rs.next()) {
                viewSid = rs.getLong("VIEW_SID");
                viewCnt = rs.getInt("VIEW_CNT");
                dataCnt = rs.getInt("DATA_CNT");
            }
            if (scrollFlg) {
                if (readFlg == GSConstChat.CHAT_TOP_SCROLL) {
                    ret = getGroupMessageDesc(groupSid, scrollSid, viewSid);
                } else {
                    ret = getGroupMessage(groupSid, scrollSid, viewSid);
                }
            } else if (dataCnt > viewCnt + GSConstChat.CHAT_GET_MESSAGE_NUMBER) {
                ret = getGroupMessage(groupSid, viewSid, viewSid);
            } else {
                ret = getGroupMessageDesc(groupSid, (long) -1, viewSid);
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
     * <br>[機  能]メッセージ情報select(グループ)
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SqlBuffer
     * @return sql SqlBuffer
     */
    public SqlBuffer getGroupMessage(SqlBuffer sql) {
        sql.addSql(" select");
        sql.addSql("   CMN_BINF.BIN_FILE_NAME,");
        sql.addSql("   CMN_BINF.BIN_FILE_PATH,");
        sql.addSql("   CMN_BINF.BIN_FILE_SIZE,");
        sql.addSql("   CMN_USRM_INF.USI_SEI,");
        sql.addSql("   CMN_USRM_INF.USI_MEI,");
        sql.addSql("   CMN_USRM_INF.BIN_SID as USR_BIN,");
        sql.addSql("   CMN_USRM_INF.USI_PICT_KF,");
        sql.addSql("   CMN_USRM.USR_JKBN,");
        sql.addSql("   CMN_USRM.USR_UKO_FLG,");
        sql.addSql("   CHT_GROUP_DATA.CGD_SID,");
        sql.addSql("   CHT_GROUP_DATA.CGI_SID,");
        sql.addSql("   CHT_GROUP_DATA.CGD_TEXT,");
        sql.addSql("   CHT_GROUP_DATA.BIN_SID,");
        sql.addSql("   CHT_GROUP_DATA.CGD_SEND_UID,");
        sql.addSql("   CHT_GROUP_DATA.CGD_STATE_KBN,");
        sql.addSql("   CHT_GROUP_DATA.CGD_AUID,");
        sql.addSql("   CHT_GROUP_DATA.CGD_ADATE,");
        sql.addSql("   CHT_GROUP_DATA.CGD_EUID,");
        sql.addSql("   CHT_GROUP_DATA.CGD_EDATE");
        sql.addSql(" from");
        sql.addSql("   CHT_GROUP_DATA");
        sql.addSql(" left join");
        sql.addSql("   CMN_BINF");
        sql.addSql(" on");
        sql.addSql("   CMN_BINF.BIN_SID = CHT_GROUP_DATA.BIN_SID");
        sql.addSql(" left join");
        sql.addSql("   CMN_USRM_INF");
        sql.addSql(" on");
        sql.addSql("   CHT_GROUP_DATA.CGD_SEND_UID = CMN_USRM_INF.USR_SID");
        sql.addSql(" left join");
        sql.addSql("   CMN_USRM");
        sql.addSql(" on");
        sql.addSql("   CMN_USRM_INF.USR_SID = CMN_USRM.USR_SID");

        return sql;
    }

    /**
     * <p>グループメッセージ情報の取得
     * @param groupSid CGI_SID
     * @param endIdx インデックス
     * @param viewSid 閲覧SID
     * @return CHT_USER_DATAModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<ChatMessageModel> getGroupMessageDesc(int groupSid,
            Long endIdx, Long viewSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ChatMessageModel> ret = new ArrayList<ChatMessageModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            getGroupMessage(sql);

            sql.addSql(" where ");
            sql.addSql("   CHT_GROUP_DATA.CGI_SID=?");
            sql.addLongValue(groupSid);
            if (endIdx != -1) {
                sql.addSql(" and");
                sql.addSql("   CHT_GROUP_DATA.CGD_SID<?");
                sql.addLongValue(endIdx);
            }
            sql.addSql(" order by ");
            sql.addSql("   CHT_GROUP_DATA.CGD_SID desc ");
            sql.addSql(" limit ?");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(GSConstChat.CHAT_GET_MESSAGE_NUMBER);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            ArrayList<ChatMessageModel> save = new ArrayList<ChatMessageModel>();
            while (rs.next()) {
                save.add(__getChatGrpMessageModel(rs, viewSid));
            }
            for (int idx = save.size() - 1; idx > -1; idx--) {
                ChatMessageModel cmMdl = save.get(idx);
                ret.add(cmMdl);
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
     * <p>グループメッセージ情報の取得
     * @param groupSid CGI_SID
     * @param startIdx 検索開始位置
     * @param viewSid 閲覧SID
     * @return CHT_USER_DATAModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<ChatMessageModel> getGroupMessage(int groupSid, Long startIdx,
            Long viewSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ChatMessageModel> ret = new ArrayList<ChatMessageModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            getGroupMessage(sql);
            sql.addSql(" where ");
            sql.addSql("   CHT_GROUP_DATA.CGI_SID=?");
            sql.addSql(" and ");
            sql.addSql("   CHT_GROUP_DATA.CGD_SID>?");
            sql.addSql(" order by ");
            sql.addSql("   CGD_SID ");
            sql.addSql(" limit ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(groupSid);
            sql.addLongValue(startIdx);
            sql.addIntValue(GSConstChat.CHAT_GET_MESSAGE_NUMBER);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getChatGrpMessageModel(rs, viewSid));
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
     * <p>直近2件のグループチャット送信者と送信日付を取得する
     * @param selectSid 選択グループSID
     * @param messageSid 投稿SID
     * @return CHT_USER_DATAModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<ChatMessageModel> getGroupMessage(int selectSid, long messageSid)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ChatMessageModel> ret = new ArrayList<ChatMessageModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            getGroupMessage(sql);
            sql.addSql(" where ");
            sql.addSql("   CHT_GROUP_DATA.CGI_SID = ?");
            sql.addSql(" and ");
            sql.addSql("   CGD_SID <= ? ");
            sql.addSql(" order by ");
            sql.addSql("   CGD_SID desc ");
            sql.addSql(" limit ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(selectSid);
            sql.addLongValue(messageSid);
            sql.addIntValue(2);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getChatGrpMessageModel(rs, messageSid));
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
     * <p>Create CHT_GROUP_DATA Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @param viewSid viewSid
     * @return created ChtGroupDataModel
     * @throws SQLException SQL実行例外
     */
    private ChatMessageModel __getChatGrpMessageModel(ResultSet rs,
            Long viewSid) throws SQLException {

        CmnBinfModel binMdl = new CmnBinfModel();
        CommonBiz cmnBiz = new CommonBiz();
        binMdl.setBinSid(rs.getLong("BIN_SID"));
        binMdl.setBinFileName(rs.getString("BIN_FILE_NAME"));
        binMdl.setBinFilePath(rs.getString("BIN_FILE_PATH"));
        //添付ファイルサイズ設定(表示用)
        long size = rs.getInt("BIN_FILE_SIZE");
        String strSize = cmnBiz.getByteSizeString(size);
        binMdl.setBinFileSizeDsp(strSize);

        ChatMessageModel cmMdl = new ChatMessageModel();
        cmMdl.setMessageSid(rs.getLong("CGD_SID"));
        cmMdl.setSelectSid(rs.getInt("CGI_SID"));
        cmMdl.setMessageText(rs.getString("CGD_TEXT"));
        cmMdl.setBinSid(rs.getLong("BIN_SID"));
        cmMdl.setInsertUid(rs.getInt("CGD_AUID"));
        cmMdl.setInsertDate(UDate.getInstanceTimestamp(rs.getTimestamp("CGD_ADATE")));
        cmMdl.setUpdateUid(rs.getInt("CGD_EUID"));
        cmMdl.setUpdateDate(UDate.getInstanceTimestamp(rs.getTimestamp("CGD_EDATE")));
        cmMdl.setTmpFile(binMdl);
        if (viewSid < rs.getInt("CGD_SID")) {
            //自身の既読情報登録
            cmMdl.setOwnKidoku(GSConstChat.KIDOKU_DISP);
        }
        UDate date = UDate.getInstanceTimestamp(rs.getTimestamp("CGD_ADATE"));
        cmMdl.setEntryDay(date.getStrYear() + "/"
        + date.getStrMonth() + "/" + date.getStrDay());
        cmMdl.setEntryTime(date.getStrHour() + ":" + date.getStrMinute());
        cmMdl.setUsrSid(rs.getInt("CGD_SEND_UID"));
        cmMdl.setUsrName(rs.getString("USI_SEI") + " " + rs.getString("USI_MEI"));
        cmMdl.setUsrJkbn(rs.getInt("USR_JKBN"));
        cmMdl.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
        cmMdl.setUsrBinSid(rs.getInt("USR_BIN"));
        cmMdl.setUsrPictKf(rs.getInt("USI_PICT_KF"));
        UDate uDate = UDate.getInstanceTimestamp(rs.getTimestamp("CGD_EDATE"));
        cmMdl.setUpdateDay(uDate.getDateStringForSql());
        cmMdl.setUpdateTime(uDate.getStrHour() + ":" + uDate.getStrMinute());
        cmMdl.setMessageKbn(rs.getInt("CGD_STATE_KBN"));
        return cmMdl;
    }

    /**
     * ユーザ情報を取得する
     * @param usrSid ペアSID
     * @return CmnUsrmInfModel　情報クラス
     * @throws SQLException SQL実行例外
     */
    public CmnUsrmInfModel getUser(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnUsrmInfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CMN_USRM_INF.USR_SID,");
            sql.addSql("   CMN_USRM_INF.USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI,");
            sql.addSql("   CMN_USRM.USR_JKBN");
            sql.addSql(" from ");
            sql.addSql("   CMN_USRM_INF");
            sql.addSql(" left join ");
            sql.addSql("   CMN_USRM");
            sql.addSql(" on ");
            sql.addSql("   CMN_USRM_INF.USR_SID = CMN_USRM.USR_SID");
            sql.addSql(" where ");
            sql.addSql("   CMN_USRM_INF.USR_SID = ?");

            sql.addIntValue(usrSid);
            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = new CmnUsrmInfModel();
                ret.setUsrSid(rs.getInt("USR_SID"));
                ret.setUsiSei(rs.getString("USI_SEI"));
                ret.setUsiMei(rs.getString("USI_MEI"));
                ret.setUsrJkbn(rs.getInt("USR_JKBN"));
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
     * ユーザ情報を取得する
     * @param usrList ユーザリスよ
     * @return CmnUsrmInfModel　情報クラス
     * @throws SQLException SQL実行例外
     */
    public ArrayList<CmnUsrmInfModel> getUser(ArrayList<Integer> usrList) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnUsrmInfModel> ret = new ArrayList<CmnUsrmInfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CMN_USRM_INF.USR_SID,");
            sql.addSql("   CMN_USRM_INF.USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI,");
            sql.addSql("   CMN_USRM.USR_JKBN");
            sql.addSql(" from ");
            sql.addSql("   CMN_USRM_INF");
            sql.addSql(" left join ");
            sql.addSql("   CMN_USRM");
            sql.addSql(" on ");
            sql.addSql("   CMN_USRM_INF.USR_SID = CMN_USRM.USR_SID");
            sql.addSql(" where ");
            sql.addSql("   CMN_USRM_INF.USR_SID IN (");
            for (int idx = 0; idx < usrList.size(); idx++) {
                if (idx != 0) {
                    sql.addSql(" , ");
                }
                sql.addSql(" ? ");
                sql.addIntValue(usrList.get(idx));
            }
            sql.addSql("   )");
            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                CmnUsrmInfModel cuiMdl = new CmnUsrmInfModel();
                cuiMdl.setUsrSid(rs.getInt("USR_SID"));
                cuiMdl.setUsiSei(rs.getString("USI_SEI"));
                cuiMdl.setUsiMei(rs.getString("USI_MEI"));
                cuiMdl.setUsrJkbn(rs.getInt("USR_JKBN"));
                ret.add(cuiMdl);
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
     * グループ情報情報を取得する
     * @param groupSid グループSID
     * @return ChatInformationModel　情報クラス
     * @throws SQLException SQL実行例外
     */
    public ChatInformationModel getGroupInfoData(int groupSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ChatInformationModel ret = new ChatInformationModel();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CHT_GROUP_INF.CGI_SID, ");
            sql.addSql("   CHT_GROUP_INF.CGI_ID, ");
            sql.addSql("   CHT_GROUP_INF.CGI_NAME, ");
            sql.addSql("   CHT_GROUP_INF.CGI_CONTENT, ");
            sql.addSql("   CHT_GROUP_INF.CGI_COMP_FLG, ");
            sql.addSql("   CHT_GROUP_INF.CHC_SID, ");
            sql.addSql("   CHT_GROUP_INF.CGI_ADATE, ");
            sql.addSql("   CHT_CATEGORY.CHC_NAME ");
            sql.addSql(" from ");
            sql.addSql("   CHT_GROUP_INF");
            sql.addSql(" left join ");
            sql.addSql("   CHT_CATEGORY");
            sql.addSql(" on ");
            sql.addSql("   CHT_GROUP_INF.CHC_SID = CHT_CATEGORY.CHC_SID");
            sql.addSql(" where ");
            sql.addSql("   CHT_GROUP_INF.CGI_SID = ?");

            sql.addIntValue(groupSid);
            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret.setChatSid(rs.getInt("CGI_SID"));
                ret.setChatName(rs.getString("CGI_NAME"));
                ret.setChatArchive(rs.getInt("CGI_COMP_FLG"));
                ret.setCategorySid(rs.getInt("CHC_SID"));
                ret.setCategoryName(rs.getString("CHC_NAME"));
                ret.setChatId(rs.getString("CGI_ID"));
                ret.setChatBiko(
                        StringUtilHtml.transToHTmlPlusAmparsant(rs.getString("CGI_CONTENT")));
                ret.setInsertDate(UDate.getInstanceTimestamp(rs.getTimestamp("CGI_ADATE")));
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
     * <p>Select CHT_GROUP_USER
     * @param groupSid CGI_SID
     * @param adminFlg 0:一般ユーザ 1:管理者ユーザ
     * @return CHT_GROUP_USERModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<CmnUsrmInfModel> getGroupUser(int groupSid, int adminFlg) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnUsrmInfModel> ret = new ArrayList<CmnUsrmInfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CMN_USRM_INF.USR_SID,");
            sql.addSql("   CMN_USRM_INF.USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI,");
            sql.addSql("   CMN_USRM.USR_JKBN,");
            sql.addSql("   CMN_USRM.USR_UKO_FLG");
            sql.addSql(" from");
            sql.addSql("   CMN_USRM_INF");
            sql.addSql(" left join");
            sql.addSql("   CMN_USRM");
            sql.addSql(" on");
            sql.addSql("   CMN_USRM_INF.USR_SID = CMN_USRM.USR_SID");
            sql.addSql(" where ");
            sql.addSql("   CMN_USRM_INF.USR_SID IN (");
            sql.addSql("     select");
            sql.addSql("       CGU_SELECT_SID");
            sql.addSql("     from");
            sql.addSql("       CHT_GROUP_USER");
            sql.addSql("     where");
            sql.addSql("       CGI_SID = ?");
            sql.addSql("     and");
            sql.addSql("       CGU_ADMIN_FLG = ?");
            sql.addSql("     and");
            sql.addSql("       CGU_KBN = ?");
            sql.addSql("     and");
            sql.addSql("       CMN_USRM.USR_JKBN = ?");
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(groupSid);
            sql.addIntValue(adminFlg);
            sql.addIntValue(GSConstChat.CHAT_KBN_USER);
            sql.addIntValue(GSConst.JTKBN_TOROKU);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                CmnUsrmInfModel usrMdl = new CmnUsrmInfModel();
                usrMdl.setUsrSid(rs.getInt("USR_SID"));
                usrMdl.setUsiSei(rs.getString("USI_SEI"));
                usrMdl.setUsiMei(rs.getString("USI_MEI"));
                usrMdl.setUsrJkbn(rs.getInt("USR_JKBN"));
                usrMdl.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
                ret.add(usrMdl);
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
     * <p>Select CHT_GROUP_USER
     * @param groupSid CGI_SID
     * @param adminFlg 0:一般ユーザ 1:管理者ユーザ
     * @return CHT_GROUP_USERModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<CmnGroupmModel> getGroup(int groupSid, int adminFlg) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnGroupmModel> ret = new ArrayList<CmnGroupmModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CMN_GROUPM.GRP_SID,");
            sql.addSql("   CMN_GROUPM.GRP_ID,");
            sql.addSql("   CMN_GROUPM.GRP_NAME,");
            sql.addSql("   CMN_GROUPM.GRP_JKBN");
            sql.addSql(" from");
            sql.addSql("   CMN_GROUPM");
            sql.addSql(" where ");
            sql.addSql("   CMN_GROUPM.GRP_SID IN (");
            sql.addSql("     select");
            sql.addSql("       CGU_SELECT_SID");
            sql.addSql("     from");
            sql.addSql("       CHT_GROUP_USER");
            sql.addSql("     where");
            sql.addSql("       CGI_SID = ?");
            sql.addSql("     and");
            sql.addSql("       CGU_ADMIN_FLG = ?");
            sql.addSql("     and");
            sql.addSql("       CGU_KBN = ?");
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(groupSid);
            sql.addIntValue(adminFlg);
            sql.addIntValue(GSConstChat.CHAT_KBN_GROUP);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                CmnGroupmModel grpMdl = new CmnGroupmModel();
                grpMdl.setGrpSid(rs.getInt("GRP_SID"));
                grpMdl.setGrpId(rs.getString("GRP_ID"));
                grpMdl.setGrpName(rs.getString("GRP_NAME"));
                grpMdl.setGrpJkbn(rs.getInt("GRP_JKBN"));
                ret.add(grpMdl);
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
     * <p>Select deleteUserSid
     * @param userSid ユーザSID
     * @return CHT_GROUP_USERModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<CmnUsrmInfModel> getDeleteUser(int userSid) throws SQLException {

        ArrayList<CmnUsrmInfModel> ret = new ArrayList<CmnUsrmInfModel>();
        ArrayList<Integer> sidList = deleteUserCount(userSid);
        if (sidList.size() != 0) {
            ret = getUser(sidList);
        }
        return ret;
    }

    /**
     * <p>Select deleteUserSid
     * @param userSid ユーザSID
     * @return CHT_GROUP_USERModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Integer> deleteUserCount(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    CUP_TABLE.USR_SID");
            sql.addSql("  from");
            sql.addSql("    (select");
            sql.addSql("       case ");
            sql.addSql("         when CHT_USER_PAIR.CUP_UID_F = ? then CHT_USER_PAIR.CUP_UID_S");
            sql.addSql("         else CHT_USER_PAIR.CUP_UID_F");
            sql.addSql("       end as USR_SID");
            sql.addSql("     from");
            sql.addSql("       CHT_USER_PAIR");
            sql.addSql("     where");
            sql.addSql("       (");
            sql.addSql("          CHT_USER_PAIR.CUP_UID_F = ?");
            sql.addSql("        or");
            sql.addSql("          CHT_USER_PAIR.CUP_UID_S = ?");
            sql.addSql("       )");
            sql.addSql("    ) CUP_TABLE");
            sql.addSql("  left join");
            sql.addSql("    CMN_USRM");
            sql.addSql("  on");
            sql.addSql("    CUP_TABLE.USR_SID = CMN_USRM.USR_SID");
            sql.addSql("  where");
            sql.addSql("    CMN_USRM.USR_JKBN = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(userSid);
            sql.addIntValue(userSid);
            sql.addIntValue(userSid);
            sql.addIntValue(GSConst.JTKBN_DELETE);
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

    /**
     *
     * <br>[機  能]未読件数を返す
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @return ret カウント
     * @throws SQLException SQLException
     */
    public int getMidokuCount(int usrSid) throws SQLException {

        int ret = 0;
        int groupCount = getGroupMidokuCount(usrSid);
        int userCount = getUserMidokuCount(usrSid);
        ret = groupCount + userCount;
        return ret;
    }

    /**
     *
     * <br>[機  能] タイムライン一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @param startIdx 検索開始位置
     * @param onlyNoRead 未読のみ表示
     * @return ret ChatMidokuModelList
     * @throws SQLException SQLException
     */
    public ArrayList<ChatMidokuModel> getTimelineList(
            int usrSid, UDate startIdx, boolean onlyNoRead)
            throws SQLException {

        ArrayList<ChatMidokuModel> ret = new ArrayList<ChatMidokuModel>();
        ArrayList<ChatMidokuModel> groupList = getGroupTimelineList(usrSid, startIdx, onlyNoRead);
        ArrayList<ChatMidokuModel> userList = getUserTimelineList(usrSid, startIdx, onlyNoRead);

        //startIdxから最新10件取得する
        boolean check = true;
        UDate chkDate = null;

        Iterator<ChatMidokuModel> itGroup = groupList.iterator();
        Iterator<ChatMidokuModel> itUser = userList.iterator();

        ChatMidokuModel stkUser = null;
        ChatMidokuModel stkGroup = null;

        while (check) {
            //ストックもイテレータもなければ走査終了
            if (!itGroup.hasNext() && !itUser.hasNext()
                    && stkUser == null && stkGroup == null) {
                check = false;
                break;
            }

            ChatMidokuModel chkUser = null;
            ChatMidokuModel chkGroup = null;
            //ストックがなければイテレータから取り出す
            if (stkGroup != null) {
                chkGroup = stkGroup;
            } else if (itGroup.hasNext()) {
                chkGroup = itGroup.next();
            }
            if (stkUser != null) {
                chkUser = stkUser;
            } else if (itUser.hasNext()) {
                chkUser = itUser.next();
            }
            stkGroup = chkGroup;
            stkUser = chkUser;
            //基準日がない場合基準日を設定
            if (chkDate == null) {
                if (chkUser == null) {
                    chkDate = chkGroup.getMidokuDate();
                } else if (chkGroup == null) {
                    chkDate = chkUser.getMidokuDate();
                } else if (chkGroup.getMidokuDate().compare(
                        chkGroup.getMidokuDate(), chkUser.getMidokuDate()) >= 0) {
                    chkDate = chkUser.getMidokuDate();
                } else {
                    chkDate = chkGroup.getMidokuDate();
                }
            }

            //タイムラインが基準日と一致する限り連続して同じイテレータから取り出す
            if (chkUser != null
                    && chkDate.equalsTimeStamp(chkUser.getMidokuDate())) {
                ret.add(chkUser);
                stkUser = null;
            } else if (chkGroup != null
                    && chkDate.equalsTimeStamp(chkGroup.getMidokuDate())) {
                ret.add(chkGroup);
                stkGroup = null;
            //基準日と一致するタイムラインがなければ次の基準日へ進めるためchkDateを空に
            } else {
                chkDate = null;
                //タイムラインのリミットを超えていればループ終了
                if (ret.size() > GSConstChat.MIDOKU_COUNT) {
                    check = false;
                }
            }

        }
        return ret;
    }

    /**
    *
    * <br>[機  能] 相手別タイムライン件数を取得する
    * <br>[解  説]
    * <br>[備  考]
    * @param usrSid ユーザSID
    * @param startIdx 検索開始位置
    * @param onlyNoRead 未読のみ表示
    * @return ret ChatMidokuModelList
    * @throws SQLException SQLException
    */
   public int getTimelineListCount(int usrSid, UDate startIdx, boolean onlyNoRead)
           throws SQLException {

       int ret = 0;
       int groupCnt = getGroupTimelineListCount(usrSid, startIdx, onlyNoRead);
       int userCnt = getUserTimelineListCount(usrSid, startIdx, onlyNoRead);
       ret = groupCnt + userCnt;
       return ret;
   }

    /**
     * <br>[機  能] 未読グループ一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @param startIdx 検索開始位置
     * @param onlyNoRead 未読のみ表示
     * @return グループ情報一覧の件数
     * @throws SQLException SQL実行例外
     */
    public ArrayList<ChatMidokuModel> getGroupTimelineList(
            int usrSid, UDate startIdx, boolean onlyNoRead)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ChatMidokuModel> ret = new ArrayList<ChatMidokuModel>();
        con = getCon();

        try {
            // SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   GROUP_DATA.GROUP_SID, ");
            sql.addSql("   CHT_GROUP_INF.CGI_NAME, ");
            sql.addSql("   CHT_GROUP_INF.CGI_COMP_FLG, ");
            sql.addSql("   GROUP_DATA.CNT, ");
            sql.addSql("   GROUP_DATA.LAST_DATE ");
            sql.addSql(" from");
            sql.addSql("   (");
            writeQueryGroupTimeline(sql, usrSid);
            sql.addSql("   ) GROUP_DATA");
            sql.addSql(" left join ");
            sql.addSql("   CHT_GROUP_INF");
            sql.addSql(" on ");
            sql.addSql("   GROUP_DATA.GROUP_SID = CHT_GROUP_INF.CGI_SID");
            sql.addSql(" where");
            sql.addSql("   GROUP_DATA.LAST_DATE < ?");
            if (onlyNoRead) {
                sql.addSql(" and");
                sql.addSql("   GROUP_DATA.CNT > 0");
            }
            sql.addSql(" and");
            sql.addSql("   CHT_GROUP_INF.CGI_DEL_FLG = ?");
            sql.addSql(" order by");
            sql.addSql("   GROUP_DATA.LAST_DATE desc");

            // 検索条件
            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addDateValue(startIdx);
            sql.addIntValue(GSConstChat.CHAT_MODE_ADD);
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            UDate chkDate = null;
            while (rs.next()) {
                ChatMidokuModel cmMdl = new ChatMidokuModel();
                cmMdl.setMidokuCount(rs.getInt("CNT"));
                cmMdl.setMidokuDate(UDate.getInstanceTimestamp(rs.getTimestamp("LAST_DATE")));
                cmMdl.setMidokuKbn(GSConstChat.CHAT_KBN_GROUP);
                cmMdl.setMidokuName(rs.getString("CGI_NAME"));
                cmMdl.setArchiveFlg(rs.getInt("CGI_COMP_FLG"));
                cmMdl.setUsrUkoFlg(0);
                cmMdl.setMidokuJkbn(0);
                cmMdl.setMidokuSid(rs.getInt("GROUP_SID"));
                UDate now = new UDate();
                UDate lastDate = UDate.getInstanceTimestamp(rs.getTimestamp("LAST_DATE"));
                if (now.compareDateYMD(lastDate) == 0) {
                    cmMdl.setMidokuDispDate(lastDate.getStrHour() + ":" + lastDate.getStrMinute());
                } else {
                    cmMdl.setMidokuDispDate(lastDate.getStrMonth() + "/" + lastDate.getStrDay()
                    + " " + lastDate.getStrHour() + ":" + lastDate.getStrMinute());
                }
                if (ret.size() > GSConstChat.MIDOKU_COUNT) {
                    if (chkDate != null
                            && !chkDate.equalsTimeStamp(cmMdl.getMidokuDate())) {
                        break;
                    }
                }

                ret.add(cmMdl);

                chkDate = cmMdl.getMidokuDate();
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
     * <br>[機  能] タイムライン件数を取得する（グループ別）
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @param startIdx 検索開始位置
     * @param onlyNoRead 未読のみ表示
     * @return グループ情報一覧の件数
     * @throws SQLException SQL実行例外
     */
    public int getGroupTimelineListCount(
            int usrSid, UDate startIdx, boolean onlyNoRead)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            // SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   COUNT(GROUP_DATA.GROUP_SID) as CNT ");
            sql.addSql(" from");
            sql.addSql("   (");
            writeQueryGroupTimeline(sql, usrSid);
            sql.addSql("   ) GROUP_DATA");
            sql.addSql(" where");
            sql.addSql("   GROUP_DATA.LAST_DATE < ?");
            if (onlyNoRead) {
                sql.addSql(" and");
                sql.addSql("   GROUP_DATA.CNT > 0");
            }
            // 検索条件
            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addDateValue(startIdx);
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
     * <br>[機  能] 未読ユーザ一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @param startIdx 検索開始位置
     * @param onlyNoRead 未読のみ表示
     * @return グループ情報一覧の件数
     * @throws SQLException SQL実行例外
     */
    public ArrayList<ChatMidokuModel> getUserTimelineList(
            int usrSid, UDate startIdx, boolean onlyNoRead)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ChatMidokuModel> ret = new ArrayList<ChatMidokuModel>();
        con = getCon();

        try {
            // SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select");
            sql.addSql("   USER_INFO.USID, ");
            sql.addSql("   USER_INFO.CNT, ");
            sql.addSql("   USER_INFO.LAST_DATE, ");
            sql.addSql("   CMN_USRM.USR_JKBN, ");
            sql.addSql("   CMN_USRM.USR_UKO_FLG, ");
            sql.addSql("   CMN_USRM_INF.USI_SEI, ");
            sql.addSql("   CMN_USRM_INF.USI_MEI ");
            sql.addSql(" from");
            sql.addSql("   (");
            sql.addSql("     select");
            sql.addSql("       case ");
            sql.addSql("         when CHT_USER_PAIR.CUP_UID_F = ? then CHT_USER_PAIR.CUP_UID_S");
            sql.addIntValue(usrSid);
            sql.addSql("         else CHT_USER_PAIR.CUP_UID_F ");
            sql.addSql("       end as USID, ");
            sql.addSql("       USER_DATA.CNT, ");
            sql.addSql("       USER_DATA.LAST_DATE ");
            sql.addSql("     from");
            sql.addSql("       (");
            writeQueryUserTimeline(sql, usrSid);
            sql.addSql("       ) USER_DATA");
            sql.addSql("     inner join ");
            sql.addSql("       CHT_USER_PAIR");
            sql.addSql("     on");
            sql.addSql("       USER_DATA.PAIR_SID = CHT_USER_PAIR.CUP_SID");
            sql.addSql("     where");
            sql.addSql("       USER_DATA.LAST_DATE < ?");
            sql.addSql("   ) USER_INFO");
            sql.addSql(" left join");
            sql.addSql("   CMN_USRM_INF");
            sql.addSql(" on");
            sql.addSql("   USER_INFO.USID = CMN_USRM_INF.USR_SID");
            sql.addSql(" left join");
            sql.addSql("   CMN_USRM");
            sql.addSql(" on");
            sql.addSql("   USER_INFO.USID = CMN_USRM.USR_SID");
            if (onlyNoRead) {
                sql.addSql(" where");
                sql.addSql("   USER_INFO.CNT > 0");
            }
            sql.addSql("     order by");
            sql.addSql("       USER_INFO.LAST_DATE desc");


            // 検索条件
            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addDateValue(startIdx);
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            UDate chkDate = null;

            while (rs.next()) {
                ChatMidokuModel cmMdl = new ChatMidokuModel();
                cmMdl.setMidokuCount(rs.getInt("CNT"));
                cmMdl.setMidokuDate(UDate.getInstanceTimestamp(rs.getTimestamp("LAST_DATE")));
                cmMdl.setMidokuKbn(GSConstChat.CHAT_KBN_USER);
                cmMdl.setMidokuName(
                        UserUtil.makeName(rs.getString("USI_SEI"), rs.getString("USI_MEI")));
                cmMdl.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
                cmMdl.setMidokuJkbn(rs.getInt("USR_JKBN"));
                cmMdl.setMidokuSid(rs.getInt("USID"));
                UDate now = new UDate();
                UDate lastDate = UDate.getInstanceTimestamp(rs.getTimestamp("LAST_DATE"));
                if (now.compareDateYMD(lastDate) == 0) {
                    cmMdl.setMidokuDispDate(lastDate.getStrHour() + ":" + lastDate.getStrMinute());
                } else {
                    cmMdl.setMidokuDispDate(lastDate.getStrMonth() + "/" + lastDate.getStrDay()
                    + " " + lastDate.getStrHour() + ":" + lastDate.getStrMinute());
                }
                if (ret.size() > GSConstChat.MIDOKU_COUNT) {
                    if (chkDate != null
                            && !chkDate.equalsTimeStamp(cmMdl.getMidokuDate())) {
                        break;
                    }
                }

                ret.add(cmMdl);

                chkDate = cmMdl.getMidokuDate();
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
     * <br>[機  能] タイムライン件数を取得する（ユーザ別）
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @param startIdx 検索開始位置
     * @param onlyNoRead 未読のみ表示
     * @return グループ情報一覧の件数
     * @throws SQLException SQL実行例外
     */
    public int getUserTimelineListCount(int usrSid, UDate startIdx, boolean onlyNoRead)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();
        try {
            // SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   COUNT(USER_INFO.USID) as CNT ");
            sql.addSql(" from");
            sql.addSql("   (");
            sql.addSql("     select");
            sql.addSql("       case ");
            sql.addSql("         when CHT_USER_PAIR.CUP_UID_F = ? then CHT_USER_PAIR.CUP_UID_S");
            sql.addIntValue(usrSid);
            sql.addSql("         else CHT_USER_PAIR.CUP_UID_F ");
            sql.addSql("       end as USID, ");
            sql.addSql("       USER_DATA.CNT, ");
            sql.addSql("       USER_DATA.LAST_DATE ");
            sql.addSql("     from");
            sql.addSql("       (");
            writeQueryUserTimeline(sql, usrSid);
            sql.addSql("       ) USER_DATA");
            sql.addSql("     left join ");
            sql.addSql("       CHT_USER_PAIR");
            sql.addSql("     on");
            sql.addSql("       USER_DATA.PAIR_SID = CHT_USER_PAIR.CUP_SID");
            sql.addSql("     where");
            sql.addSql("       USER_DATA.LAST_DATE < ?");
            sql.addDateValue(startIdx);
            sql.addSql("   ) USER_INFO");
            sql.addSql(" left join");
            sql.addSql("   CMN_USRM_INF");
            sql.addSql(" on");
            sql.addSql("   USER_INFO.USID = CMN_USRM_INF.USR_SID");
            if (onlyNoRead) {
                sql.addSql(" where");
                sql.addSql("   USER_INFO.CNT > 0");
            }
            // 検索条件
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
     *
     * <br>[機  能] グループチャット先一覧取得用のクエリを書き込む
     * <br>[解  説] 未読件数込で取得できるクエリを書き込む
     * <br>[備  考] 処理内ではSQLパラメータの追加がある
     * @param sql SqlBuffer
     * @param usrSid ユーザSID
     * @return sql SqlBuffer
     * @throws SQLException SQLException
     */
    public SqlBuffer writeQueryGroupTimeline(SqlBuffer sql, int usrSid) throws SQLException {
        sql.addSql(" select");
        sql.addSql("      CGU.CGI_SID AS GROUP_SID,");
        sql.addSql("        case when DATA.LASTDATE is null then CHT_GROUP_DATA_SUM.CGS_LASTDATE");
        sql.addSql("          else DATA.LASTDATE end as LAST_DATE,");
        sql.addSql("        coalesce(CHT_GROUP_DATA_SUM.CGS_CNT, 0) +");
        sql.addSql("           coalesce(DATA.CNT, 0) -");
        sql.addSql("           coalesce(VIEW_EXIST.CGV_VIEWCNT, 0) as CNT");
        sql.addSql("    from");
        sql.addSql("      (");
        sql.addSql("        select distinct");
        sql.addSql("          CHT_GROUP_USER.CGI_SID");
        sql.addSql("        from");
        sql.addSql("          CHT_GROUP_USER");
        sql.addSql("           inner join CHT_GROUP_INF");
        sql.addSql("             on CHT_GROUP_USER.CGI_SID = CHT_GROUP_INF.CGI_SID");
        sql.addSql("        where");
        sql.addSql("          (");
        sql.addSql("              (");
        sql.addSql("                CGU_SELECT_SID = ?");
        sql.addSql("              and");
        sql.addSql("                CGU_KBN = ?");
        sql.addSql("              )");
        sql.addSql("              or");
        sql.addSql("              (");
        sql.addSql("                CGU_SELECT_SID in");
        sql.addSql("                 (");
        sql.addSql("                  select");
        sql.addSql("                    GRP_SID");
        sql.addSql("                  from");
        sql.addSql("                    CMN_BELONGM");
        sql.addSql("                  where");
        sql.addSql("                    CMN_BELONGM.USR_SID = ?");
        sql.addSql("                 )");
        sql.addSql("               and");
        sql.addSql("                 CGU_KBN = ?");
        sql.addSql("              )");
        sql.addSql("          )");
        sql.addSql("          and CHT_GROUP_INF.CGI_DEL_FLG <> ?");
        sql.addSql("      ) CGU");
        sql.addSql("            left join");
        sql.addSql("         (");
        writeQueryGroupDataSumMisyukei(sql);
        sql.addSql("         ) DATA");
        sql.addSql("             on CGU.CGI_SID = DATA.CGI_SID");
        sql.addSql("         inner join CHT_GROUP_DATA_SUM");
        sql.addSql("             on CGU.CGI_SID = CHT_GROUP_DATA_SUM.CGI_SID");
        sql.addSql("         left join");
        sql.addSql("         (");
        sql.addSql("            select ");
        sql.addSql("                CHT_GROUP_VIEW.CGI_SID as CGI_SID,");
        sql.addSql("                CHT_GROUP_VIEW.CGD_SID as CGD_SID,");
        sql.addSql("                CHT_GROUP_VIEW.CGV_VIEWCNT as CGV_VIEWCNT");
        sql.addSql("            from CHT_GROUP_VIEW");
        sql.addSql("            where");
        sql.addSql("                CHT_GROUP_VIEW.CGV_UID = ?");
        sql.addSql("         ) VIEW_EXIST");
        sql.addSql("            on CGU.CGI_SID = VIEW_EXIST.CGI_SID");
        sql.addSql("    where");
        sql.addSql("      CHT_GROUP_DATA_SUM.CGS_LASTDATE is not null");
        sql.addSql("      or DATA.CGI_SID is not null");


        sql.addIntValue(usrSid);
        sql.addIntValue(GSConstChat.CHAT_KBN_USER);
        sql.addIntValue(usrSid);
        sql.addIntValue(GSConstChat.CHAT_KBN_GROUP);
        sql.addIntValue(GSConstChat.CHAT_MODE_DELETE);
        sql.addIntValue(usrSid);


        return sql;
    }
    /**
    *
    * <br>[機  能] 投稿集計情報の未集計範囲の集計を取得するクエリを書き込む
    * <br>[解  説]
    * <br>[備  考]
    * @param sql SqlBuffer
    * @return sql
    */
    public SqlBuffer writeQueryGroupDataSumMisyukei(SqlBuffer sql) {
        sql.addSql("             select");
        sql.addSql("                 CGI_SID,");
        sql.addSql("                 count(1) as CNT,");
        sql.addSql("                 max(CGD_ADATE) as LASTDATE,");
        sql.addSql("                 max(CGD_SID) as LASTSID");
        sql.addSql("             from");
        sql.addSql("              CHT_GROUP_DATA");
        sql.addSql("             where CGD_SID > coalesce((");
        sql.addSql("                 select max(CGS_LASTSID) LAST_SID from CHT_GROUP_DATA_SUM");
        sql.addSql("               ), 0)");
        sql.addSql("             group by CGI_SID");
        return sql;
    }

    /**
     * <p>Select count
     * @param usrSid ユーザSID
     * @return count
     * @throws SQLException SQL実行例外
     */
    public int getGroupMidokuCount(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            writeQueryGroupTimeline(sql, usrSid);
            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret += rs.getInt("CNT");
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
     * <br>[機  能] ユーザチャット先一覧取得用のクエリを書き込む
     * <br>[解  説] 未読件数込のユーザチャット先一覧が取得できる
     * <br>[備  考] 処理内ではSQLパラメータの追加がある
     * @param sql SqlBuffer
     * @param usrSid ユーザSID
     * @return sql SqlBuffer
     * @throws SQLException SQLException
     */
    public SqlBuffer writeQueryUserTimeline(SqlBuffer sql, int usrSid) throws SQLException {

        sql.addSql(" select");
        sql.addSql("        PAIR.CUP_SID as PAIR_SID,");
        sql.addSql("        case when DATA.LASTDATE is null then CHT_USER_DATA_SUM.CUS_LASTDATE");
        sql.addSql("          else DATA.LASTDATE end as LAST_DATE,");
        sql.addSql("        coalesce(CHT_USER_DATA_SUM.CUS_CNT, 0) +");
        sql.addSql("           coalesce(DATA.CNT, 0) -");
        sql.addSql("           coalesce( VIEW_EXIST.CUV_VIEWCNT, 0) as CNT");
        sql.addSql("    from");
        sql.addSql("        (");
        sql.addSql("            select");
        sql.addSql("                    CUP_SID");
        sql.addSql("                from");
        sql.addSql("                    CHT_USER_PAIR");
        sql.addSql("                WHERE");
        sql.addSql("                    CUP_UID_F = ?");
        sql.addSql("                    OR CUP_UID_S = ?");
        sql.addSql("        ) PAIR");
        sql.addSql("            left join");
        sql.addSql("         (");
        writeQueryUserDataSumMisyukei(sql);
        sql.addSql("         ) DATA");
        sql.addSql("             on PAIR.CUP_SID = DATA.CUP_SID");
        sql.addSql("            inner join CHT_USER_DATA_SUM");
        sql.addSql("                on PAIR.CUP_SID = CHT_USER_DATA_SUM.CUP_SID");
        sql.addSql("            left join");
        sql.addSql("            (");
        sql.addSql("                select ");
        sql.addSql("                 CHT_USER_VIEW.CUP_SID as CUP_SID,");
        sql.addSql("                 CHT_USER_VIEW.CUD_SID as CUD_SID,");
        sql.addSql("                 CHT_USER_VIEW.CUV_VIEWCNT as CUV_VIEWCNT");
        sql.addSql("             from CHT_USER_VIEW");
        sql.addSql("             where");
        sql.addSql("                    CHT_USER_VIEW.CUV_UID = ?");
        sql.addSql("            ) VIEW_EXIST");
        sql.addSql("                on PAIR.CUP_SID = VIEW_EXIST.CUP_SID");
        sql.addSql("    where");
        sql.addSql("      CHT_USER_DATA_SUM.CUS_LASTDATE is not null");
        sql.addSql("      or DATA.CUP_SID is not null");


        sql.addIntValue(usrSid);
        sql.addIntValue(usrSid);
        sql.addIntValue(usrSid);

        return sql;
    }
    /**
     *
     * <br>[機  能] 投稿集計情報の未集計範囲の集計を取得するクエリを書き込む
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SqlBuffer
     * @return sql
     */
    public SqlBuffer writeQueryUserDataSumMisyukei(SqlBuffer sql) {
        sql.addSql("             select");
        sql.addSql("                 CUP_SID,");
        sql.addSql("                 count(1) as CNT,");
        sql.addSql("                 max(CUD_ADATE) as LASTDATE,");
        sql.addSql("                 max(CUD_SID) as LASTSID");
        sql.addSql("             from");
        sql.addSql("              CHT_USER_DATA");
        sql.addSql("             where CUD_SID > coalesce((");
        sql.addSql("                     select max(CUS_LASTSID) LAST_SID from CHT_USER_DATA_SUM");
        sql.addSql("                   ),0)");
        sql.addSql("             group by CUP_SID");
        return sql;
    }

    /**
     * <p>Select count
     * @param usrSid ユーザSID
     * @return count
     * @throws SQLException SQL実行例外
     */
    public int getUserMidokuCount(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql = writeQueryUserTimeline(sql, usrSid);
            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret += rs.getInt("CNT");
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
     * <p>ユーザ間チャットにおいて各それぞれでの未読件数を取得する
     * @param usrSid ユーザSID
     * @param uList 相手ユーザSID
     * @return ret ユーザリスト
     * @throws SQLException SQL実行例外
     */
    public ArrayList<ChatUserInfModel> getUserCntList(int usrSid,
            List<CmnUsrmInfModel> uList) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ChatUserInfModel> ret = new ArrayList<ChatUserInfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   case");
            sql.addSql("      when CHT_USER_PAIR.CUP_UID_F = ?");
            sql.addIntValue(usrSid);
            sql.addSql("      then CHT_USER_PAIR.CUP_UID_S");
            sql.addSql("      else CHT_USER_PAIR.CUP_UID_F");
            sql.addSql("   end as USR_SID,");
            sql.addSql("   CNT_DATA.CNT");
            sql.addSql(" from");
            sql.addSql("   CHT_USER_PAIR");
            sql.addSql(" left join");
            sql.addSql("   (");
            writeQueryUserTimeline(sql, usrSid);
            sql.addSql("   ) CNT_DATA");
            sql.addSql(" on");
            sql.addSql("   CHT_USER_PAIR.CUP_SID = CNT_DATA.PAIR_SID");
            sql.addSql(" where");
            sql.addSql("   (");
            sql.addSql("      CHT_USER_PAIR.CUP_UID_F = ?");
            sql.addIntValue(usrSid);
            sql.addSql("    and");
            sql.addSql("      CHT_USER_PAIR.CUP_UID_S IN (");
            for (int idx = 0; idx < uList.size(); idx++) {
                if (idx != 0) {
                    sql.addSql("    ,");
                }
                sql.addSql("    ?");
                sql.addIntValue(uList.get(idx).getUsrSid());
            }
            sql.addSql("      )");
            sql.addSql("   )");
            sql.addSql(" or");
            sql.addSql("   (");
            sql.addSql("      CHT_USER_PAIR.CUP_UID_S = ?");
            sql.addIntValue(usrSid);
            sql.addSql("    and");
            sql.addSql("      CHT_USER_PAIR.CUP_UID_F IN (");
            for (int idx = 0; idx < uList.size(); idx++) {
                if (idx != 0) {
                    sql.addSql("    ,");
                }
                sql.addSql("    ?");
                sql.addIntValue(uList.get(idx).getUsrSid());
            }
            sql.addSql("      )");
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            ArrayList<Integer> usrSidList = new ArrayList<Integer>();
            ArrayList<Integer> cntList = new ArrayList<Integer>();
            while (rs.next()) {
                usrSidList.add(rs.getInt("USR_SID"));
                cntList.add(rs.getInt("CNT"));
            }
            for (CmnUsrmInfModel cuiMdl : uList) {
                ChatUserInfModel bean = new ChatUserInfModel();
                boolean bCheck = false;
                for (int idx = 0; idx < usrSidList.size(); idx++) {
                    if (usrSidList.get(idx) == cuiMdl.getUsrSid()) {
                        bean.setBinSid(cuiMdl.getBinSid());
                        bean.setUsiPictKf(cuiMdl.getUsiPictKf());
                        bean.setUsrSid(cuiMdl.getUsrSid());
                        bean.setUsrJkbn(cuiMdl.getUsrJkbn());
                        bean.setUsrUkoFlg(cuiMdl.getUsrUkoFlg());
                        bean.setUsiSei(cuiMdl.getUsiSei());
                        bean.setUsiMei(cuiMdl.getUsiMei());
                        bean.setChtUserCount(cntList.get(idx));
                        bCheck = true;
                        break;
                    }
                }
                if (!bCheck) {
                    bean.setBinSid(cuiMdl.getBinSid());
                    bean.setUsiPictKf(cuiMdl.getUsiPictKf());
                    bean.setUsrSid(cuiMdl.getUsrSid());
                    bean.setUsrJkbn(cuiMdl.getUsrJkbn());
                    bean.setUsrUkoFlg(cuiMdl.getUsrUkoFlg());
                    bean.setUsiSei(cuiMdl.getUsiSei());
                    bean.setUsiMei(cuiMdl.getUsiMei());
                    bean.setChtUserCount(0);
                }
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
     * 指定したグループチャットにおける未読件数を取得
     * ただしメッセージ受信ユーザ側から見た未読件数を取得する
     * @param usrSid 受信者SID
     * @param cgiSid ペアSID
     * @return 未読件数
     * @throws SQLException SQL実行例外
     * */
    public int getMidokuCountGroup(int usrSid, int cgiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   DATA.GROUP_SID, ");
            sql.addSql("   DATA.CNT, ");
            sql.addSql("   DATA.LAST_DATE ");
            sql.addSql(" from");
            sql.addSql("   (");
            sql = writeQueryGroupTimeline(sql, usrSid);
            sql.addSql("   ) DATA");
            sql.addSql(" where");
            sql.addSql("   DATA.GROUP_SID = ? ");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(cgiSid);

            log__.info(sql.toLogString());
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
     * 指定したユーザ間チャットにおける未読件数を取得
     * ただしメッセージ受信ユーザ側から見た未読件数を取得する
     * @param usrSid 受信者SID
     * @param pairSid ペアSID
     * @return 未読件数
     * @throws SQLException SQL実行例外
     * */
    public int getMidokuCountPair(int usrSid, int pairSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("     select");
            sql.addSql("       USER_DATA.CNT, ");
            sql.addSql("       USER_DATA.LAST_DATE ");
            sql.addSql("     from");
            sql.addSql("       (");
            writeQueryUserTimeline(sql, usrSid);
            sql.addSql("       ) USER_DATA");
            sql.addSql(" where ");
            sql.addSql("   USER_DATA.PAIR_SID = ? ");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(pairSid);

            log__.info(sql.toLogString());
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
     * <br>[機  能] チャットグループ情報一覧の件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param searchMdl 検索情報
     * @return グループ情報一覧の件数
     * @throws SQLException SQL実行例外
     */
    public int getChatGroupDataCount(ChatSearchModel searchMdl) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int count = 0;
        con = getCon();

        try {
            // SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(CHT_GROUP_INF.CGI_SID) as COUNT");
            sql.addSql(" from");
            sql.addSql("   CHT_GROUP_INF");
            sql.addSql(" left join ");
            sql.addSql("   (");
            sql.addSql("   select");
            sql.addSql("     CGI_SID,");
            sql.addSql("     MAX(CGD_ADATE) as CGD_ADATE");
            sql.addSql("   from");
            sql.addSql("     CHT_GROUP_DATA");
            sql.addSql("   group by");
            sql.addSql("     CGI_SID");
            sql.addSql("   )LAST_DAY");
            sql.addSql(" on ");
            sql.addSql("    CHT_GROUP_INF.CGI_SID = LAST_DAY.CGI_SID");
            // 検索条件
            __searchWhere(searchMdl, sql);
            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt("COUNT");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return count;
    }

    /**
     * <br>[機  能] チャットグループ情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param searchMdl 検索情報
     * @return グループ情報一覧
     * @throws SQLException SQL実行例外
     */
    public List <ChatInformationModel> getChtGrpDataList(
            ChatSearchModel searchMdl) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        List<ChatInformationModel> ret = new ArrayList<ChatInformationModel>();
        try {
            // SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CHT_GROUP_INF.CGI_SID, ");
            sql.addSql("   CHT_GROUP_INF.CGI_ID, ");
            sql.addSql("   CHT_GROUP_INF.CGI_NAME, ");
            sql.addSql("   CHT_GROUP_INF.CGI_CONTENT, ");
            sql.addSql("   CHT_GROUP_INF.CGI_COMP_FLG, ");
            sql.addSql("   CHT_GROUP_INF.CHC_SID, ");
            sql.addSql("   CHT_GROUP_INF.CGI_ADATE, ");
            sql.addSql("   CHT_GROUP_INF.CGI_EDATE,");
            sql.addSql("   LAST_DAY.CGD_ADATE");
            sql.addSql(" from ");
            sql.addSql("   CHT_GROUP_INF");
            sql.addSql(" left join ");
            sql.addSql("   (");
            sql.addSql("   select");
            sql.addSql("     CGI_SID,");
            sql.addSql("     MAX(CGD_ADATE) as CGD_ADATE");
            sql.addSql("   from");
            sql.addSql("     CHT_GROUP_DATA");
            sql.addSql("   group by");
            sql.addSql("     CGI_SID");
            sql.addSql("   ) as LAST_DAY");
            sql.addSql(" on ");
            sql.addSql("    CHT_GROUP_INF.CGI_SID = LAST_DAY.CGI_SID");
            // 検索条件
            __searchWhere(searchMdl, sql);
            __searchSort(searchMdl, sql);
            __page(searchMdl, sql);
            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ChatInformationModel mdl = new ChatInformationModel();
                mdl.setChatSid(rs.getInt("CGI_SID"));
                mdl.setChatName(rs.getString("CGI_NAME"));
                mdl.setChatArchive(rs.getInt("CGI_COMP_FLG"));
                mdl.setCategorySid(rs.getInt("CHC_SID"));
                mdl.setChatId(rs.getString("CGI_ID"));
                mdl.setChatBiko(rs.getString("CGI_CONTENT"));
                mdl.setInsertDate(UDate.getInstanceTimestamp(rs.getTimestamp("CGI_ADATE")));
                mdl.setStrInsertDate(__createDateStr(mdl.getInsertDate()));
                mdl.setLastSendDate(UDate.getInstanceTimestamp(rs.getTimestamp("CGD_ADATE")));
                mdl.setStrLastSendDate(__createDateStr(mdl.getLastSendDate()));
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
     * <br>[機  能] 検索where
     * <br>[解  説]
     * <br>[備  考]
     * @param searchMdl 検索情報
     * @param sql sql
     * @return グループ情報一覧
     * @throws SQLException SQL実行例外
     */
    public SqlBuffer __searchWhere(
            ChatSearchModel searchMdl, SqlBuffer sql) throws SQLException {
        sql.addSql("   where");
        sql.addSql(" 1 = 1");

        // キーワード検索
        List < String > keywordList = searchMdl.getKeywordList();

        if (keywordList != null && !keywordList.isEmpty()) {
            String keywordJoin = "   and";
            if (searchMdl.getAndOr() == GSConstChat.KEYWORDKBN_OR) {
                keywordJoin = "   or";
            }

            boolean kakko = false;
            boolean isGroupId = (searchMdl.getGroupId() ==  GSConstChat.SEARCH_GROUPID_IN);
            boolean isGroupName = (searchMdl.getGroupName() == GSConstChat.SEARCH_GROUPNAME_IN);
            boolean isGroupInfo = (searchMdl.getGroupInfo() == GSConstChat.SEARCH_GROUPINFO_IN);
            int chkNum = searchMdl.getGroupId()
                    + searchMdl.getGroupName() + searchMdl.getGroupInfo();

            if (chkNum >= 2) {
                kakko = true;
                sql.addSql("  and");
                sql.addSql("   (");
            }

            // グループID
            if (isGroupId) {
                if (chkNum == 1) {
                    sql.addSql("  and");
                }
                  sql.addSql("     (");
                for (int i = 0; i < keywordList.size(); i++) {
                    if (i > 0) {
                        sql.addSql(keywordJoin);
                    }
                    sql.addSql("       CGI_ID like '%"
                            + JDBCUtil.escapeForLikeSearch(keywordList.get(i))
                            + "%' ESCAPE '"
                            + JDBCUtil.def_esc
                            + "'");
                }
                sql.addSql("     )");
            }

            // グループ名
            if (isGroupName) {
                if (isGroupId) {
                    sql.addSql("    or");
                }
                if (chkNum == 1) {
                    sql.addSql("  and");
                }
                sql.addSql("     (");
                for (int i = 0; i < keywordList.size(); i++) {
                    if (i > 0) {
                        sql.addSql(keywordJoin);
                    }
                    sql.addSql("       CGI_NAME like '%"
                            + JDBCUtil.escapeForLikeSearch(keywordList.get(i))
                            + "%' ESCAPE '"
                            + JDBCUtil.def_esc
                            + "'");
                }
                sql.addSql("     )");
            }

            // 備考
            if (isGroupInfo) {

                if (isGroupId || isGroupName) {
                    sql.addSql("    or");
                }
                if (chkNum == 1) {
                    sql.addSql("  and");
                }

                sql.addSql("     (");
                for (int i = 0; i < keywordList.size(); i++) {
                    if (i > 0) {
                        sql.addSql(keywordJoin);
                    }
                    sql.addSql("       CGI_CONTENT like '%"
                            + JDBCUtil.escapeForLikeSearch(keywordList.get(i))
                            + "%' ESCAPE '"
                            + JDBCUtil.def_esc
                            + "'");
                }
                sql.addSql("     )");
            }
            if (kakko) {
                sql.addSql("     )");
            }
        }
        // カテゴリ
        if (searchMdl.getCategory() != GSConstChat.CHAT_CHC_SID_ALL) {
            sql.addSql("  and");
            sql.addSql("   CHC_SID = ?");
            sql.addIntValue(searchMdl.getCategory());
        }
        // アーカイブ
        if (searchMdl.getStatusKbn() != GSConstChat.SEARCH_STATUSKBN_ALL) {
                sql.addSql("  and");
                switch (searchMdl.getStatusKbn()) {
                case GSConstChat.SEARCH_STATUSKBN_NOT_ARCHIVE:
                    sql.addSql("   CGI_COMP_FLG = ?");
                    sql.addIntValue(GSConstChat.CHAT_ARCHIVE_NOT_MODE);
                    break;
                case GSConstChat.SEARCH_STATUSKBN_ARCHIVE_ONLY:
                    sql.addSql("   CGI_COMP_FLG = ?");
                    sql.addIntValue(GSConstChat.CHAT_ARCHIVE_MODE);
                    break;
                default:
                    sql.addSql("   CGI_COMP_FLG = ?");
                    sql.addIntValue(GSConstChat.CHAT_ARCHIVE_NOT_MODE);
                    break;
            }
        }

        // メンバー
        if (searchMdl.getSelectUser() != -1 && searchMdl.getSelectGroup() != -1) {
            sql.addSql("  and");
            sql.addSql("   CHT_GROUP_INF.CGI_SID  in (");
            sql.addSql("    select");
            sql.addSql("      CGI_SID");
            sql.addSql("    from");
            sql.addSql("      CHT_GROUP_USER");
            sql.addSql("    where");
            sql.addSql("      CGU_SELECT_SID = ?");
            sql.addSql("    and");
            sql.addSql("      CGU_KBN = ?");
            sql.addIntValue(searchMdl.getSelectUser());
            sql.addIntValue(GSConstChat.CHAT_KBN_USER);
            if (searchMdl.getAdminMember() == GSConstChat.SEARCH_GROUPADMIN_IN
                    && searchMdl.getGeneralMember() == GSConstChat.SEARCH_GENERALUSER_OUT) {
                sql.addSql("  and");
                sql.addSql("  CGU_ADMIN_FLG = ?");
                sql.addIntValue(GSConstChat.SEARCH_GROUPADMIN_IN);
            } else if (searchMdl.getAdminMember() == GSConstChat.SEARCH_GROUPADMIN_OUT
                    && searchMdl.getGeneralMember() == GSConstChat.SEARCH_GENERALUSER_IN) {
                sql.addSql("  and");
                sql.addSql("  CGU_ADMIN_FLG = ?");
                sql.addIntValue(GSConstChat.SEARCH_GROUPADMIN_OUT);
            }
            sql.addSql("   )");
        } else if (searchMdl.getSelectGroup() != -1 && searchMdl.getSelectUser() == -1) {
            sql.addSql("  and");
            sql.addSql("   CHT_GROUP_INF.CGI_SID  in (");
            sql.addSql("    select");
            sql.addSql("      CGI_SID");
            sql.addSql("    from");
            sql.addSql("      CHT_GROUP_USER");
            sql.addSql("    where");
            sql.addSql("      CGU_SELECT_SID in (");
            sql.addSql("    select");
            sql.addSql("      USR_SID");
            sql.addSql("    from");
            sql.addSql("      CMN_BELONGM");
            sql.addSql("    where");
            sql.addSql("      GRP_SID = ?");
            sql.addIntValue(searchMdl.getSelectGroup());
            sql.addSql("   )");
            if (searchMdl.getAdminMember() == GSConstChat.SEARCH_GROUPADMIN_IN
                    && searchMdl.getGeneralMember() == GSConstChat.SEARCH_GENERALUSER_OUT) {
                sql.addSql("  and");
                sql.addSql("  CGU_ADMIN_FLG = ?");
                sql.addIntValue(GSConstChat.SEARCH_GROUPADMIN_IN);
            } else if (searchMdl.getAdminMember() == GSConstChat.SEARCH_GROUPADMIN_OUT
                    && searchMdl.getGeneralMember() == GSConstChat.SEARCH_GENERALUSER_IN) {
                sql.addSql("  and");
                sql.addSql("  CGU_ADMIN_FLG = ?");
                sql.addIntValue(GSConstChat.SEARCH_GROUPADMIN_OUT);
            }
            sql.addSql("   )");
        }
        // 作成日時
        if (searchMdl.getCreateDateFr() != null) {
            sql.addSql(" and");
            sql.addSql("   CHT_GROUP_INF.CGI_ADATE >= ?");
            UDate date = UDate.getInstance(searchMdl.getCreateDateFr().getTimeMillis());
            date.setHour(0);
            date.setMinute(0);
            date.setSecond(0);
            date.setMilliSecond(0);
            sql.addDateValue(date);
        }
        if (searchMdl.getCreateDateTo() != null) {
            sql.addSql(" and");
            sql.addSql("   CGI_ADATE <= ?");
            UDate date = UDate.getInstance(searchMdl.getCreateDateTo().getTimeMillis());
            date.setHour(23);
            date.setMinute(59);
            date.setSecond(59);
            date.setMilliSecond(999);
            sql.addDateValue(date);
        }
        // 最終投稿日時
        if (searchMdl.getUpDateFr() != null) {
            sql.addSql(" and");
            sql.addSql("   CGD_ADATE >= ?");
            UDate date = UDate.getInstance(searchMdl.getUpDateFr().getTimeMillis());
            date.setHour(0);
            date.setMinute(0);
            date.setSecond(0);
            date.setMilliSecond(0);
            sql.addDateValue(date);
        }
        if (searchMdl.getUpDateTo() != null) {
            sql.addSql(" and");
            sql.addSql("   CGD_ADATE <= ?");
            UDate date = UDate.getInstance(searchMdl.getUpDateTo().getTimeMillis());
            date.setHour(23);
            date.setMinute(59);
            date.setSecond(59);
            date.setMilliSecond(999);
            sql.addDateValue(date);
        }
        //削除
        sql.addSql(" and");
        sql.addSql("   CGI_DEL_FLG <> ?");
        sql.addIntValue(GSConstChat.CHAT_GROUP_LOGIC_DELETE);
        return sql;
    }

    /**
     * <br>[機  能] sort
     * <br>[解  説]
     * <br>[備  考]
     * @param searchMdl 検索情報
     * @param sql sql
     * @return グループ情報一覧
     * @throws SQLException SQL実行例外
     */
    private SqlBuffer __searchSort(ChatSearchModel searchMdl, SqlBuffer sql) throws SQLException {
        sql.addSql("   order by ");
        String order = "asc";
        if (searchMdl.getOrderKey() == GSConst.ORDER_KEY_DESC) {
            order = "desc";
        }
        switch (searchMdl.getSortKey()) {
            case GSConstChat.CHAT_SORT_GRPID:
                sql.addSql("   CGI_ID " + order);
                break;
            case GSConstChat.CHAT_SORT_GRPNAME:
                sql.addSql("   CGI_NAME " + order);
                break;
            case GSConstChat.CHAT_SORT_ADATE:
                sql.addSql("   CGI_ADATE " + order);
                break;
            case GSConstChat.CHAT_SORT_EDATE:
                sql.addSql("   CGD_ADATE " + order);
                break;
            default:
                sql.addSql("   CGI_SID asc");
                break;
        }
        return sql;
    }

    /**
     * <br>[機  能] page
     * <br>[解  説]
     * <br>[備  考]
     * @param searchMdl 検索情報
     * @param sql sql
     * @return グループ情報一覧
     * @throws SQLException SQL実行例外
     */
    private SqlBuffer __page(ChatSearchModel searchMdl, SqlBuffer sql) throws SQLException {
        int page = searchMdl.getPage();
        if (page <= 0) {
            page = 1;
        }
        int maxCnt = searchMdl.getMaxCnt();
        sql.setPagingValue(PageUtil.getRowNumber(page, maxCnt) - 1, maxCnt);
        return sql;
    }

    /**
     * 日付文字列を取得する
     * @param date 日付
     * @return 日付文字列
     */
    private String __createDateStr(UDate date) {
        if (date == null) {
            return null;
        }

        StringBuilder strDate = new StringBuilder("");
        strDate.append(UDateUtil.getSlashYYMD(date));
        strDate.append(" ");
        strDate.append(UDateUtil.getSeparateHMS(date));

        return strDate.toString();
    }

    /**
     * <br>[機  能] グループSID内に所属するユーザを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param grpList グループリスト
     * @return ユーザSIDリスト
     * @throws SQLException SQL実行例外
     */
    public List<Integer> getUsrSid(List<Integer> grpList)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            // SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID ");
            sql.addSql(" from");
            sql.addSql("   CMN_BELONGM");
            sql.addSql(" where");
            sql.addSql("   GRP_SID IN (");
            for (int idx = 0; idx < grpList.size(); idx++) {
                if (idx != 0) {
                    sql.addSql(" , ");
                }
                sql.addSql(" ? ");
                sql.addIntValue(grpList.get(idx));
            }
            sql.addSql("   )");


            // 検索条件
            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
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

    /**
     * <br>[機  能] グループSIDの採番値を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return グループSID
     * @throws SQLException SQL実行例外
     */
    public int getSaibanCgiSid()
            throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 1;
        con = getCon();

        try {
            // SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("select");
            sql.addSql(" SBN_NUMBER ");
            sql.addSql("from");
            sql.addSql(" CMN_SAIBAN ");
            sql.addSql("where");
            sql.addSql(" SBN_SID = ? ");
            sql.addSql("and");
            sql.addSql(" SBN_SID_SUB = ? ");
            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(GSConstChat.SBNSID_CHAT);
            sql.addStrValue(GSConstChat.SBNSID_SUB_CHAT_GROUP);
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
              ret = rs.getInt("SBN_NUMBER");
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
     * <p>update
     * @param sidList バイナリSIDリスト
     * @throws SQLException SQL実行例外
     * @return count
     */
    public int updateTempJkbn(List<Integer> sidList) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = getCon();
        int ret = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_BINF");
            sql.addSql(" set");
            sql.addSql("   BIN_JKBN = ?");
            sql.addIntValue(GSConst.JTKBN_DELETE);
            sql.addSql(" where ");
            sql.addSql("   BIN_SID IN ( ");
            for (int idx = 0; idx < sidList.size(); idx++) {
                if (idx != 0) {
                    sql.addSql("  , ");
                }
                sql.addSql("  ? ");
                sql.addIntValue(sidList.get(idx));
            }
            sql.addSql("   ) ");
            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            ret = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }




}
