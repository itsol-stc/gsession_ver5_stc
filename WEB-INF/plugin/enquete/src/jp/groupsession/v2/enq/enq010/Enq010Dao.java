package jp.groupsession.v2.enq.enq010;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.enq.GSConstEnquete;
import jp.groupsession.v2.enq.biz.EnqCommonBiz;

/**
 * <br>[機  能] アンケート一覧画面で使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq010Dao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Enq010Dao.class);

    /**
     * <p>Default Constructor
     */
    public Enq010Dao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Enq010Dao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] アンケート 草稿の件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return アンケート 草稿の件数
     * @throws SQLException SQL実行時例外
     */
    public int getEnqDraftCount(int userSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int count = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(EMN_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   ENQ_MAIN");
            sql.addSql(" where");
            sql.addSql("   EMN_DATA_KBN = ?");
            sql.addSql(" and");
            sql.addSql("   EMN_AUID = ?");
            sql.addIntValue(GSConstEnquete.DATA_KBN_DRAFT);
            sql.addIntValue(userSid);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("CNT");
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
     * <br>[機  能] アンケート 発信 未公開 or 公開の件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param type 未公開 or 公開
     * @return アンケート 草稿の件数
     * @throws SQLException SQL実行時例外
     */
    public int getEnqSendCount(int userSid, int type)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int count = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(EMN_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   ENQ_MAIN");
            sql.addSql(" where");
            sql.addSql("   EMN_DATA_KBN = ?");
            sql.addSql(" and");
            sql.addSql("   EMN_AUID = ?");
            sql.addIntValue(GSConstEnquete.DATA_KBN_SEND);
            sql.addIntValue(userSid);

            UDate now = new UDate();
            now.setZeroHhMmSs();

            sql.addSql(" and");
            if (type == Enq010Const.SUBFOLDER_NOT_PUBLIC) {
                sql.addSql("   ENQ_MAIN.EMN_OPEN_STR > ?");
                sql.addDateValue(now);
            } else if (type == Enq010Const.SUBFOLDER_PUBLIC) {
                sql.addSql("   (");
                sql.addSql("     ENQ_MAIN.EMN_OPEN_STR <= ?");
                sql.addSql("   and");
                sql.addSql("     ENQ_MAIN.EMN_RES_END >= ?");
                sql.addSql("   )");
                sql.addDateValue(now);
                sql.addDateValue(now);
            }

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("CNT");
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
     * <br>[機  能] アンケート情報一覧の件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param searchMdl 検索条件
     * @param reqMdl リクエスト情報
     * @return アンケート情報一覧
     * @throws SQLException SQL実行時例外
     */
    public int getEnqueteCount(Enq010SearchModel searchMdl, RequestModel reqMdl)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int count = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(ENQ_MAIN.EMN_SID) as CNT");

            //検索条件を設定
            __setSearchSql(sql, searchMdl);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("CNT");
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
     * <br>[機  能] アンケート情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param searchMdl 検索条件
     * @param reqMdl リクエスト情報
     * @return アンケート情報一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Enq010EnqueteModel> getEnqueteList(Enq010SearchModel searchMdl, RequestModel reqMdl)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Enq010EnqueteModel> enqList = new ArrayList<Enq010EnqueteModel>();
        con = getCon();
        EnqCommonBiz enqBiz = new EnqCommonBiz();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   ENQ_MAIN.EMN_SID as EMN_SID,");
            sql.addSql("   ENQ_MAIN.EMN_DATA_KBN as EMN_DATA_KBN,");
            sql.addSql("   ENQ_MAIN.ETP_SID as ETP_SID,");
            sql.addSql("   ENQ_MAIN.EMN_TITLE as EMN_TITLE,");
            sql.addSql("   ENQ_MAIN.EMN_PRI_KBN as EMN_PRI_KBN,");
            sql.addSql("   ENQ_MAIN.EMN_DESC as EMN_DESC,");
            sql.addSql("   ENQ_MAIN.EMN_ATTACH_KBN as EMN_ATTACH_KBN,");
            sql.addSql("   ENQ_MAIN.EMN_ATTACH_ID as EMN_ATTACH_ID,");
            sql.addSql("   ENQ_MAIN.EMN_ATTACH_NAME as EMN_ATTACH_NAME,");
            sql.addSql("   ENQ_MAIN.EMN_ATTACH_POS as EMN_ATTACH_POS,");
            sql.addSql("   ENQ_MAIN.EMN_OPEN_STR as EMN_OPEN_STR,");
            sql.addSql("   ENQ_MAIN.EMN_OPEN_END as EMN_OPEN_END,");
            sql.addSql("   ENQ_MAIN.EMN_OPEN_END_KBN as EMN_OPEN_END_KBN,");
            sql.addSql("   ENQ_MAIN.EMN_RES_END as EMN_RES_END,");
            sql.addSql("   ENQ_MAIN.EMN_ANS_PUB_STR as EMN_ANS_PUB_STR,");
            sql.addSql("   ENQ_MAIN.EMN_ANONY as EMN_ANONY,");
            sql.addSql("   ENQ_MAIN.EMN_ANS_OPEN as EMN_ANS_OPEN,");
            sql.addSql("   ENQ_MAIN.EMN_SEND_GRP as EMN_SEND_GRP,");
            sql.addSql("   ENQ_MAIN.EMN_SEND_USR as EMN_SEND_USR,");
            sql.addSql("   ENQ_MAIN.EMN_SEND_NAME as EMN_SEND_NAME,");
            sql.addSql("   ENQ_MAIN.EMN_TARGET as EMN_TARGET,");
            sql.addSql("   ENQ_MAIN.EMN_EDATE as EMN_EDATE,");
            sql.addSql("   ENQ_TYPE.ETP_NAME as ETP_NAME,");
            sql.addSql("   CMN_GROUPM.GRP_NAME as GRP_NAME,");
            sql.addSql("   CMN_GROUPM.GRP_JKBN as GRP_JKBN,");
            sql.addSql("   CMN_USRM.USR_JKBN as USR_JKBN,");
            sql.addSql("   CMN_USRM.USR_UKO_FLG as USR_UKO_FLG,");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI");
            if (searchMdl.getFolder() == Enq010Const.FOLDER_RECEIVE) {
                sql.addSql("   ,ENQ_ANS_MAIN.EAM_STS_KBN as EAM_STS_KBN");
            }

            //検索条件を設定
            __setSearchSql(sql, searchMdl);

            //並び順
            String order = "";
            String resOrder = " desc";
            if (searchMdl.getOrder() == Enq010Const.ORDER_DESC) {
                order = " desc";
                resOrder = "";
            }
            sql.addSql(" order by");
            if (searchMdl.getFolder() == Enq010Const.FOLDER_TEMPLATE) {
                sql.addSql("   case when ENQ_MAIN.ETP_SID < 1 then 1 else 0 end,");
                sql.addSql("   ETP_DSP_SEQ, EMN_TITLE, EMN_EDATE DESC");
            } else {
                switch (searchMdl.getSortKey()) {
                    //状態
                    case Enq010Const.SORTKEY_STATUS:
                        sql.addSql("   ENQ_ANS_MAIN.EAM_STS_KBN" + order + ",");
                        sql.addSql("   ENQ_MAIN.EMN_RES_END" + resOrder + ",");
                        sql.addSql("   ENQ_MAIN.EMN_OPEN_END desc");
                        break;
                    //重要度
                    case Enq010Const.SORTKEY_PRIORITY:
                        sql.addSql("   ENQ_MAIN.EMN_PRI_KBN" + order);
                        break;
                    //発信者
                    case Enq010Const.SORTKEY_SENDER:
                        sql.addSql("   case");
                        sql.addSql("   when GRP_NAME_KN is not null then GRP_NAME");
                        sql.addSql("   else USI_SEI_KN || ' ' || USI_MEI_KN");
                        sql.addSql("   end" + order);
                        break;
                    //タイトル
                    case Enq010Const.SORTKEY_TITLE:
                        sql.addSql("   ENQ_MAIN.EMN_TITLE" + order);
                        break;
                    //回答期限
                    case Enq010Const.SORTKEY_ANSLIMIT:
                        sql.addSql("   ENQ_MAIN.EMN_RES_END" + order);
                        break;
                    //公開期間
                    case Enq010Const.SORTKEY_OPEN:
                        sql.addSql("   ENQ_MAIN.EMN_OPEN_STR" + order + ",");
                        sql.addSql("   ENQ_MAIN.EMN_OPEN_END_KBN" + order + ",");
                        sql.addSql("   ENQ_MAIN.EMN_OPEN_END" + order);
                        break;
                    //作成日
                    case Enq010Const.SORTKEY_MAKEDATE:
                        sql.addSql("   ENQ_MAIN.EMN_EDATE" + order);
                        break;
                    //結果公開期間
                    case Enq010Const.SORTKEY_ANS_OPEN:
                        sql.addSql("   ENQ_MAIN.EMN_ANS_OPEN" + order + ",");
                        sql.addSql("   ENQ_MAIN.EMN_ANS_PUB_STR" + order + ",");
                        sql.addSql("   ENQ_MAIN.EMN_OPEN_END_KBN" + order + ",");
                        sql.addSql("   ENQ_MAIN.EMN_OPEN_END" + order);
                        break;
                    default:
                        sql.addSql("   ENQ_MAIN.EMN_OPEN_END_KBN" + resOrder + ",");
                        sql.addSql("   ENQ_MAIN.EMN_OPEN_END" + order + ",");
                        sql.addSql("   ENQ_MAIN.EMN_ANS_PUB_STR" + order);
                }
                sql.addSql("  ,ENQ_MAIN.EMN_TITLE, ENQ_MAIN.EMN_EDATE DESC");
            }

            int rowNum = PageUtil.getRowNumber(searchMdl.getPage(), searchMdl.getMaxCount()) - 1;
            if (rowNum < 0) {
                rowNum = 0;
            }
            sql.setPagingValue(
                    rowNum,
                    searchMdl.getMaxCount());
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            UDate now = new UDate();
            now.setZeroHhMmSs();
            while (rs.next()) {
                Enq010EnqueteModel enqData = new Enq010EnqueteModel();

                // アンケートSID
                enqData.setEnqSid(rs.getInt("EMN_SID"));
                // 重要度
                enqData.setPriority(rs.getInt("EMN_PRI_KBN"));
                // 状態
                if (searchMdl.getFolder() == Enq010Const.FOLDER_RECEIVE) {
                    enqData.setStatus(rs.getInt("EAM_STS_KBN"));
                } else {
                    enqData.setStatus(Enq010Const.STATUS_NOTANS);
                }

                // 発信者
                String groupName = rs.getString("GRP_NAME");
                if (rs.getInt("EMN_SEND_GRP") > 0 && groupName != null) {
                    enqData.setSender(groupName);
                    enqData.setSenderDelFlg(rs.getInt("GRP_JKBN") == GSConst.JTKBN_DELETE);
                } else {
                    enqData.setSender(rs.getString("USI_SEI")
                            + " " + rs.getString("USI_MEI"));
                    enqData.setSenderDelFlg(rs.getInt("USR_JKBN") == GSConst.JTKBN_DELETE);
                    enqData.setSenderUkoFlg(rs.getInt("USR_UKO_FLG"));
                }

                // 作成日
                enqData.setMakeDate(__createDateString(reqMdl, enqBiz, rs, "EMN_EDATE"));
                // 種類
                enqData.setTypeName(rs.getString("ETP_NAME"));
                // タイトル
                enqData.setTitle(rs.getString("EMN_TITLE"));
                // 回答公開期限 開始
                enqData.setAnsPubStartDate(
                        __createDateString(reqMdl, enqBiz, rs, "EMN_ANS_PUB_STR"));
                enqData.setAnsPubStartUDate(
                        UDate.getInstanceTimestamp(rs.getTimestamp("EMN_ANS_PUB_STR")));
                // 回答公開期限 開始 曜日
                enqData.setAnsPubStartDayOfWeek(
                        __createDayOfWeek(rs, "EMN_ANS_PUB_STR", reqMdl));
                // 回答期限
                enqData.setAnsLimitDate(__createDateString(reqMdl, enqBiz, rs, "EMN_RES_END"));
                enqData.setAnsLimitUDate(
                        UDate.getInstanceTimestamp(rs.getTimestamp("EMN_RES_END")));
                // 回答期限 曜日
                enqData.setAnsLimitDayOfWeek(__createDayOfWeek(rs, "EMN_RES_END", reqMdl));
                // 対象人数
//                enqData.setSubjects(__getEnqAnswerCount(enqData.getEnqSid()));
                enqData.setSubjects(rs.getLong("EMN_TARGET"));
                // 公開期限 開始
                enqData.setPubStartDate(
                        UDate.getInstanceTimestamp(rs.getTimestamp("EMN_OPEN_STR")));
                enqData.setPubStartDateStr(__createDateString(reqMdl, enqBiz, rs, "EMN_OPEN_STR"));

                // 公開期限 開始 曜日
                enqData.setPubStartDayOfWeek(__createDayOfWeek(rs, "EMN_OPEN_STR", reqMdl));
                // 公開期限 終了
                enqData.setPubEndDate(
                        UDate.getInstanceTimestamp(rs.getTimestamp("EMN_OPEN_END")));
                enqData.setPubEndDateStr(__createDateString(reqMdl, enqBiz, rs, "EMN_OPEN_END"));
                enqData.setPubEndDateKbn(rs.getInt("EMN_OPEN_END_KBN"));
                // 公開期限 終了 曜日
                enqData.setPubEndDayOfWeek(__createDayOfWeek(rs, "EMN_OPEN_END", reqMdl));



                //公開状態
                //公開期間_終了日 指定 指定ありの場合のみ 公開完了チェックを行う
                if (enqData.getPubEndDateKbn() == GSConstEnquete.EMN_OPEN_END_KBN_SPECIFIED) {

                    if (enqData.getPubStartDate() != null && enqData.getPubEndDate() != null) {
                        // 公開完了 (今 > 公開終了日）
                        if (enqData.getPubEndDate().compareDateYMD(now) == UDate.LARGE) {
                            enqData.setPublicKbn(Enq010Const.PUBLIC_END);
                        // 未公開（今 < 公開開始日）
                        } else if (enqData.getPubStartDate().compareDateYMD(now) == UDate.SMALL) {
                            enqData.setPublicKbn(Enq010Const.PUBLIC_NO);
                        // 公開中（今 >= 公開開始日 &&  今 <= 回答期限)
                        } else if (enqData.getPubStartDate().compareDateYMD(now) != UDate.SMALL
                                && enqData.getAnsLimitUDate().compareDateYMD(now) != UDate.LARGE) {
                            enqData.setPublicKbn(Enq010Const.PUBLIC_YES);
                        // 回答完了
                        } else {
                            enqData.setPublicKbn(Enq010Const.PUBLIC_ANSED);
                        }
                    }

                //公開完了チェックを行わない
                } else {

                    if (enqData.getPubStartDate() != null) {
                        // 未公開（今 < 公開開始日）
                        if (enqData.getPubStartDate().compareDateYMD(now) == UDate.SMALL) {
                            enqData.setPublicKbn(Enq010Const.PUBLIC_NO);
                        // 公開中（今 >= 公開開始日 &&  今 <= 回答期限)
                        } else if (enqData.getPubStartDate().compareDateYMD(now) != UDate.SMALL
                                && enqData.getAnsLimitUDate().compareDateYMD(now) != UDate.LARGE) {
                            enqData.setPublicKbn(Enq010Const.PUBLIC_YES);
                        // 回答完了
                        } else {
                            enqData.setPublicKbn(Enq010Const.PUBLIC_ANSED);
                        }
                    }
                }

                //結果公開期間内かを判定
                if (enqData.getAnsPubStartUDate() != null) {
                    if (enqData.getPubEndDateKbn() == GSConstEnquete.EMN_OPEN_END_KBN_NON) {
                        enqData.setAnsPubDateFlg(
                            enqData.getAnsPubStartUDate().compareDateYMD(now) != UDate.SMALL);
                    } else {
                        enqData.setAnsPubDateFlg(
                                enqData.getAnsPubStartUDate().compareDateYMD(now) != UDate.SMALL
                                && enqData.getPubEndDate().compareDateYMD(now) != UDate.LARGE);
                    }
                }

                // 匿名
                enqData.setAnnoy(rs.getInt("EMN_ANONY"));
                // 結果公開
                enqData.setAnsOpen(rs.getInt("EMN_ANS_OPEN"));

                // 公開期間フラグ
                if (enqData.getPubEndDateKbn() == GSConstEnquete.EMN_OPEN_END_KBN_NON) {
                    enqData.setDisClosureFlg(Enq010Const.RESULT_DISCLOSURE);
                } else {
                    if (enqData.getPubEndDate() != null
                    && enqData.getPubEndDate().compareDateYMD(now) != UDate.LARGE) {
                       enqData.setDisClosureFlg(Enq010Const.RESULT_DISCLOSURE);
                   } else {
                       enqData.setDisClosureFlg(Enq010Const.RESULT_NON_DISCLOSURE);
                   }
                }

                // 未回答アンケートの回答期限切れフラグ
                if (enqData.getStatus() == GSConstEnquete.EAM_STS_KBN_NO
                 && enqData.getAnsLimitUDate().compareDateYMD(now) == UDate.LARGE) {
                    enqData.setCanAnsFlg(Enq010Const.PUBLIC_ANSFLG_NG);
                } else {
                    enqData.setCanAnsFlg(Enq010Const.PUBLIC_ANSFLG_OK);
                }

                enqList.add(enqData);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return enqList;
    }

    /**
     * <br>[機  能] アンケート一覧の検索SQLを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SqlBuffer
     * @param searchMdl 検索条件
     */
    private void __setSearchSql(SqlBuffer sql, Enq010SearchModel searchMdl) {
        sql.addSql(" from ");
        if (searchMdl.getFolder() == Enq010Const.FOLDER_RECEIVE) {
            sql.addSql("   ENQ_ANS_MAIN,");
        }
        sql.addSql("   ENQ_MAIN");
        sql.addSql("   left join");
        sql.addSql("     ENQ_TYPE");
        sql.addSql("   on");
        sql.addSql("     ENQ_MAIN.ETP_SID = ENQ_TYPE.ETP_SID");
        sql.addSql("   left join");
        sql.addSql("     CMN_GROUPM");
        sql.addSql("   on");
        sql.addSql("     ENQ_MAIN.EMN_SEND_GRP = CMN_GROUPM.GRP_SID");
        sql.addSql("   left join");
        sql.addSql("     CMN_USRM");
        sql.addSql("   on");
        sql.addSql("     ENQ_MAIN.EMN_SEND_USR = CMN_USRM.USR_SID");
        sql.addSql("   left join");
        sql.addSql("     CMN_USRM_INF");
        sql.addSql("   on");
        sql.addSql("     ENQ_MAIN.EMN_SEND_USR = CMN_USRM_INF.USR_SID");

        sql.addSql(" where");
        //データ区分
        switch (searchMdl.getFolder()) {
            case Enq010Const.FOLDER_RECEIVE:
                sql.addSql("   ENQ_MAIN.EMN_DATA_KBN = ?");
                sql.addSql("  and");
                sql.addSql("   ENQ_ANS_MAIN.USR_SID = ?");
                sql.addIntValue(GSConstEnquete.DATA_KBN_SEND);
                sql.addIntValue(searchMdl.getSessionUserSid());

                //公開期間内のアンケートを設定
                UDate openDate = new UDate();
                openDate.setZeroHhMmSs();
                sql.addSql("  and");
                sql.addSql("    ENQ_MAIN.EMN_OPEN_STR <= ?");
                sql.addDateValue(openDate);

                break;
            case Enq010Const.FOLDER_SEND:
                sql.addSql("   ENQ_MAIN.EMN_DATA_KBN = ?");
                sql.addIntValue(GSConstEnquete.DATA_KBN_SEND);
                if (!searchMdl.isEnqAdminFlg()) {
                    sql.addSql(" and");
                    sql.addSql("   ENQ_MAIN.EMN_AUID = ?");
                    sql.addIntValue(searchMdl.getSessionUserSid());
                }
                break;
            case Enq010Const.FOLDER_DRAFT:
                sql.addSql("   ENQ_MAIN.EMN_DATA_KBN = ?");
                sql.addSql(" and");
                sql.addSql("   ENQ_MAIN.EMN_AUID = ?");
                sql.addIntValue(GSConstEnquete.DATA_KBN_DRAFT);
                sql.addIntValue(searchMdl.getSessionUserSid());
                break;
            case Enq010Const.FOLDER_TEMPLATE:
                sql.addSql("   ENQ_MAIN.EMN_DATA_KBN = ?");
                sql.addIntValue(GSConstEnquete.DATA_KBN_TEMPLATE);
                break;
            default:
        }

        //種類
        if (searchMdl.getEnqType() > 0) {
            sql.addSql(" and");
            sql.addSql("   ENQ_MAIN.ETP_SID = ?");
            sql.addIntValue(searchMdl.getEnqType());
        }

        //キーワード
        String[] keywordList = searchMdl.getKeyword();
        if (keywordList != null && keywordList.length > 0) {
            String keywordCd = "     and";
            if (searchMdl.getKeywordType() == Enq010Const.KEYWORDKBN_OR) {
                keywordCd = "     or";
            }
            String[] keywordFieldList = {"ENQ_MAIN.EMN_TITLE", "ENQ_MAIN.EMN_DESC"};

            sql.addSql(" and");
            sql.addSql("   (");
            for (int fldIdx = 0; fldIdx < keywordFieldList.length; fldIdx++) {
                if (fldIdx > 0) {
                    sql.addSql("   or");
                }
                sql.addSql("     (");
                for  (int keywordIdx = 0; keywordIdx < keywordList.length; keywordIdx++) {
                    if (keywordIdx > 0) {
                        sql.addSql(keywordCd);
                    }
                    sql.addSql("       " + keywordFieldList[fldIdx] + " like '%"
                            + JDBCUtil.escapeForLikeSearch(keywordList[keywordIdx])
                            + "%' ESCAPE '"
                            + JDBCUtil.def_esc
                            + "'");
                }
                sql.addSql("     )");
            }
            sql.addSql("   )");
        }

        //発信者
        if (!StringUtil.isNullZeroString(searchMdl.getSenderInput())) {
            String senderInputText = JDBCUtil.escapeForLikeSearch(searchMdl.getSenderInput())
                    + "%' ESCAPE '"
                    + JDBCUtil.def_esc
                    + "'";
            sql.addSql(" and");
            sql.addSql("   (");
            sql.addSql("     ENQ_MAIN.EMN_SEND_USR in (");
            sql.addSql("       select USR_SID from CMN_USRM_INF");
            sql.addSql("       where CMN_USRM_INF.USI_SEI || ' ' || CMN_USRM_INF.USI_MEI like '%"
                            + senderInputText);
            sql.addSql("     )");
            sql.addSql("   or");
            sql.addSql("     ENQ_MAIN.EMN_SEND_GRP in (");
            sql.addSql("       select GRP_SID from CMN_GROUPM");
            sql.addSql("       where");
            sql.addSql("         GRP_NAME like '%" + senderInputText);
            sql.addSql("     )");
            sql.addSql("   )");

        } else {
            if (searchMdl.getSenderUser() > 0) {
                sql.addSql(" and");
                sql.addSql("   ENQ_MAIN.EMN_SEND_USR = ?");
                sql.addIntValue(searchMdl.getSenderUser());
            } else if (searchMdl.getSenderGroup() > 0) {
                sql.addSql(" and");
                sql.addSql("   ENQ_MAIN.EMN_SEND_GRP = ?");
                sql.addIntValue(searchMdl.getSenderGroup());
            }
        }

        //作成日
        if (searchMdl.getMakeDateFrom() != null) {
            sql.addSql("  and");
            sql.addSql("    ENQ_MAIN.EMN_EDATE >= ?");
            sql.addDateValue(searchMdl.getMakeDateFrom());
        }
        if (searchMdl.getMakeDateTo() != null) {
            UDate makeDateTo = searchMdl.getMakeDateTo().cloneUDate();
            makeDateTo.setMaxHhMmSs();
            sql.addSql("  and");
            sql.addSql("    ENQ_MAIN.EMN_EDATE <= ?");
            sql.addDateValue(makeDateTo);
        }
        //結果期間
        if (searchMdl.getResPubLimitDateFrom() != null
                && searchMdl.getResPubLimitDateTo() != null) {
            sql.addSql("  and ENQ_MAIN.EMN_ANS_OPEN = ?");
            sql.addIntValue(GSConstEnquete.EMN_ANS_OPEN_PUBLIC);

            sql.addSql("  and");
            sql.addSql("   (");
            sql.addSql("    (");
            sql.addSql("         ENQ_MAIN.EMN_ANS_PUB_STR >= ?");
            sql.addSql("     and ENQ_MAIN.EMN_ANS_PUB_STR <= ?");
            sql.addSql("    )");
            sql.addDateValue(searchMdl.getResPubLimitDateFrom());
            sql.addDateValue(searchMdl.getResPubLimitDateTo());
            sql.addSql("   or");
            sql.addSql("    (");
            sql.addSql("      ENQ_MAIN.EMN_OPEN_END_KBN = ?");
            sql.addSql("    or");
            sql.addSql("      (");
            sql.addSql("        ENQ_MAIN.EMN_OPEN_END_KBN = ?");
            sql.addSql("      and");
            sql.addSql("        ENQ_MAIN.EMN_OPEN_END >= ?");
            sql.addSql("      and");
            sql.addSql("        ENQ_MAIN.EMN_OPEN_END <= ?");
            sql.addSql("      )");
            sql.addSql("    )");
            sql.addIntValue(GSConstEnquete.EMN_OPEN_END_KBN_NON);
            sql.addIntValue(GSConstEnquete.EMN_OPEN_END_KBN_SPECIFIED);
            sql.addDateValue(searchMdl.getResPubLimitDateFrom());
            sql.addDateValue(searchMdl.getResPubLimitDateTo());
            sql.addSql("   or");
            sql.addSql("    (");
            sql.addSql("         ENQ_MAIN.EMN_ANS_PUB_STR <= ?");
            sql.addSql("     and");
            sql.addSql("       (");
            sql.addSql("         ENQ_MAIN.EMN_OPEN_END_KBN = ?");
            sql.addSql("       or");
            sql.addSql("         (");
            sql.addSql("           ENQ_MAIN.EMN_OPEN_END_KBN = ?");
            sql.addSql("         and");
            sql.addSql("           ENQ_MAIN.EMN_OPEN_END >= ?");
            sql.addSql("         )");
            sql.addSql("       )");
            sql.addSql("    )");
            sql.addDateValue(searchMdl.getResPubLimitDateFrom());
            sql.addIntValue(GSConstEnquete.EMN_OPEN_END_KBN_NON);
            sql.addIntValue(GSConstEnquete.EMN_OPEN_END_KBN_SPECIFIED);
            sql.addDateValue(searchMdl.getResPubLimitDateFrom());
            sql.addSql("   or");
            sql.addSql("    (");
            sql.addSql("         ENQ_MAIN.EMN_ANS_PUB_STR <= ?");
            sql.addSql("     and");
            sql.addSql("       (");
            sql.addSql("         ENQ_MAIN.EMN_OPEN_END_KBN = ?");
            sql.addSql("       or");
            sql.addSql("         (");
            sql.addSql("           ENQ_MAIN.EMN_OPEN_END_KBN = ?");
            sql.addSql("         and");
            sql.addSql("           ENQ_MAIN.EMN_OPEN_END >= ?");
            sql.addSql("         )");
            sql.addSql("       )");
            sql.addSql("    )");
            sql.addDateValue(searchMdl.getResPubLimitDateTo());
            sql.addIntValue(GSConstEnquete.EMN_OPEN_END_KBN_NON);
            sql.addIntValue(GSConstEnquete.EMN_OPEN_END_KBN_SPECIFIED);
            sql.addDateValue(searchMdl.getResPubLimitDateTo());
            sql.addSql("   )");
        }

        //公開期間
        if (searchMdl.getPubLimitDateFrom() != null && searchMdl.getPubLimitDateTo() != null) {
            sql.addSql("  and");
            sql.addSql("   (");
            sql.addSql("    (");
            sql.addSql("         ENQ_MAIN.EMN_OPEN_STR >= ?");
            sql.addSql("     and ENQ_MAIN.EMN_OPEN_STR <= ?");
            sql.addSql("    )");
            sql.addDateValue(searchMdl.getPubLimitDateFrom());
            sql.addDateValue(searchMdl.getPubLimitDateTo());
            sql.addSql("   or");
            sql.addSql("    (");
            sql.addSql("      ENQ_MAIN.EMN_OPEN_END_KBN = ?");
            sql.addSql("    or");
            sql.addSql("      (");
            sql.addSql("        ENQ_MAIN.EMN_OPEN_END_KBN = ?");
            sql.addSql("      and");
            sql.addSql("        ENQ_MAIN.EMN_OPEN_END >= ?");
            sql.addSql("      and");
            sql.addSql("        ENQ_MAIN.EMN_OPEN_END <= ?");
            sql.addSql("      )");
            sql.addSql("    )");
            sql.addIntValue(GSConstEnquete.EMN_OPEN_END_KBN_NON);
            sql.addIntValue(GSConstEnquete.EMN_OPEN_END_KBN_SPECIFIED);
            sql.addDateValue(searchMdl.getPubLimitDateFrom());
            sql.addDateValue(searchMdl.getPubLimitDateTo());
            sql.addSql("   or");
            sql.addSql("    (");
            sql.addSql("         ENQ_MAIN.EMN_OPEN_STR <= ?");
            sql.addSql("     and");
            sql.addSql("       (");
            sql.addSql("         ENQ_MAIN.EMN_OPEN_END_KBN = ?");
            sql.addSql("       or");
            sql.addSql("         (");
            sql.addSql("           ENQ_MAIN.EMN_OPEN_END_KBN = ?");
            sql.addSql("         and");
            sql.addSql("           ENQ_MAIN.EMN_OPEN_END >= ?");
            sql.addSql("         )");
            sql.addSql("       )");
            sql.addSql("    )");
            sql.addDateValue(searchMdl.getPubLimitDateFrom());
            sql.addIntValue(GSConstEnquete.EMN_OPEN_END_KBN_NON);
            sql.addIntValue(GSConstEnquete.EMN_OPEN_END_KBN_SPECIFIED);
            sql.addDateValue(searchMdl.getPubLimitDateFrom());
            sql.addSql("   or");
            sql.addSql("    (");
            sql.addSql("         ENQ_MAIN.EMN_OPEN_STR <= ?");
            sql.addSql("     and");
            sql.addSql("       (");
            sql.addSql("         ENQ_MAIN.EMN_OPEN_END_KBN = ?");
            sql.addSql("       or");
            sql.addSql("         (");
            sql.addSql("           ENQ_MAIN.EMN_OPEN_END_KBN = ?");
            sql.addSql("         and");
            sql.addSql("           ENQ_MAIN.EMN_OPEN_END >= ?");
            sql.addSql("         )");
            sql.addSql("       )");
            sql.addSql("    )");
            sql.addDateValue(searchMdl.getPubLimitDateTo());
            sql.addIntValue(GSConstEnquete.EMN_OPEN_END_KBN_NON);
            sql.addIntValue(GSConstEnquete.EMN_OPEN_END_KBN_SPECIFIED);
            sql.addDateValue(searchMdl.getPubLimitDateTo());
            sql.addSql("   )");
        }

        //回答期限
        if (searchMdl.getAnsLimitDateFrom() != null) {
            sql.addSql("  and");
            sql.addSql("    ENQ_MAIN.EMN_RES_END >= ?");
            sql.addDateValue(searchMdl.getAnsLimitDateFrom());
        }
        if (searchMdl.getAnsLimitDateTo() != null) {
            sql.addSql("  and");
            sql.addSql("    ENQ_MAIN.EMN_RES_END <= ?");
            sql.addDateValue(searchMdl.getAnsLimitDateTo());
        }

        //重要度
        int[] priority = searchMdl.getPriority();
        if (priority != null && priority.length > 0) {
            sql.addSql(" and");
            sql.addSql("   EMN_PRI_KBN in (");
            for (int idx = 0; idx < priority.length; idx++) {
                if (idx > 0) {
                    sql.addSql("     ,?");
                } else {
                    sql.addSql("     ?");
                }
                sql.addIntValue(priority[idx]);
            }
            sql.addSql("   )");
        }


        //状態
        int[] statusList = searchMdl.getStatus();
        if (statusList != null && statusList.length > 0) {
            if (searchMdl.getFolder() == Enq010Const.FOLDER_RECEIVE) {
                sql.addSql(" and");
                sql.addSql("   ENQ_ANS_MAIN.EAM_STS_KBN in (");
                for (int idx = 0; idx < statusList.length; idx++) {
                    if (idx > 0) {
                        sql.addSql("     ,?");
                    } else {
                        sql.addSql("     ?");
                    }
                    if (statusList[idx] == Enq010Const.STATUS_NOTANS) {
                        sql.addIntValue(GSConstEnquete.EAM_STS_KBN_NO);
                    } else if (statusList[idx] == Enq010Const.STATUS_ANS) {
                        sql.addIntValue(GSConstEnquete.EAM_STS_KBN_YES);
                    }
                }
                sql.addSql("   )");
            } else if (searchMdl.getFolder() == Enq010Const.FOLDER_SEND) {
                UDate now = new UDate();
                now.setZeroHhMmSs();

                sql.addSql(" and");
                sql.addSql("   (");
                for (int idx = 0; idx < statusList.length; idx++) {
                    if (idx > 0) {
                        sql.addSql("   or");
                    }
                    switch (statusList[idx]) {
                        case Enq010Const.SUBFOLDER_NOT_PUBLIC:
                            sql.addSql("   ENQ_MAIN.EMN_OPEN_STR > ?");
                            sql.addDateValue(now);
                            break;
                        case Enq010Const.SUBFOLDER_PUBLIC:
                            sql.addSql("   (");
                            sql.addSql("     ENQ_MAIN.EMN_OPEN_STR <= ?");
                            sql.addSql("   and");
                            sql.addSql("     ENQ_MAIN.EMN_RES_END >= ?");
                            sql.addSql("   )");
                            sql.addDateValue(now);
                            sql.addDateValue(now);
                            break;
                        case Enq010Const.SUBFOLDER_COMP_ANS:
                            sql.addSql("   (");
                            sql.addSql("     ENQ_MAIN.EMN_RES_END < ?");
                            sql.addSql("   and");
                            sql.addSql("      (");
                            sql.addSql("        ENQ_MAIN.EMN_OPEN_END_KBN = ?");
                            sql.addSql("      or");
                            sql.addSql("        (");
                            sql.addSql("          ENQ_MAIN.EMN_OPEN_END_KBN = ?");
                            sql.addSql("        and");
                            sql.addSql("          ENQ_MAIN.EMN_OPEN_END >= ?");
                            sql.addSql("        )");
                            sql.addSql("      )");
                            sql.addSql("   )");
                            sql.addDateValue(now);
                            sql.addIntValue(GSConstEnquete.EMN_OPEN_END_KBN_NON);
                            sql.addIntValue(GSConstEnquete.EMN_OPEN_END_KBN_SPECIFIED);
                            sql.addDateValue(now);
                            break;
                        case Enq010Const.SUBFOLDER_COMP_PUB:
                            sql.addSql("     (");
                            sql.addSql("       ENQ_MAIN.EMN_OPEN_END_KBN = ?");
                            sql.addSql("     and");
                            sql.addSql("       ENQ_MAIN.EMN_OPEN_END < ?");
                            sql.addSql("     )");
                            sql.addIntValue(GSConstEnquete.EMN_OPEN_END_KBN_SPECIFIED);
                            sql.addDateValue(now);
                            break;
                        default:
                    }
                }
                sql.addSql("   )");
            }
        }
        int[] statusAnsOver = searchMdl.getStatusAnsOver();
        if (statusAnsOver != null && statusAnsOver.length > 0
                && searchMdl.getFolder() == Enq010Const.FOLDER_RECEIVE) {
            UDate toDate = new UDate();
            toDate.setZeroHhMmSs();
            sql.addSql(" and");
            sql.addSql("   (");
            for (int idx = 0; idx < statusAnsOver.length; idx++) {
                if (idx > 0) {
                    sql.addSql("   or");
                }
                switch (statusAnsOver[idx]) {
                    case Enq010Const.PUBLIC_ANSFLG_OK:
                        sql.addSql("    ENQ_MAIN.EMN_RES_END >= ?");
                        sql.addDateValue(toDate);
                        break;
                    case Enq010Const.PUBLIC_ANSFLG_NG:
                        sql.addSql("    ENQ_MAIN.EMN_RES_END < ?");
                        sql.addDateValue(toDate);
                        break;
                    default:
                }
            }
            sql.addSql("   )");
        }


        //匿名
        if (searchMdl.getAnony() == Enq010Const.ANONY_NON) {
            sql.addSql(" and");
            sql.addSql("   ENQ_MAIN.EMN_ANONY = ?");
            sql.addIntValue(GSConstEnquete.ANONYMUS_OFF);
        } else if (searchMdl.getAnony() == Enq010Const.ANONY_ON) {
            sql.addSql(" and");
            sql.addSql("   ENQ_MAIN.EMN_ANONY = ?");
            sql.addIntValue(GSConstEnquete.ANONYMUS_ON);
        }

        if (searchMdl.getFolder() == Enq010Const.FOLDER_RECEIVE) {
            sql.addSql(" and");
            sql.addSql("   ENQ_MAIN.EMN_SID = ENQ_ANS_MAIN.EMN_SID");
        }
    }

    /**
     * <br>[機  能] 指定した日付フィールドの値を日付文字列に変換して返す
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param enqBiz アンケート共通ビジネスクラスのインスタンス
     * @param rs ResultSet
     * @param fieldName フィールド名
     * @return 日付文字列
     * @throws SQLException 日付の取得に失敗
     */
    private String __createDateString(RequestModel reqMdl, EnqCommonBiz enqBiz,
                                      ResultSet rs, String fieldName) throws SQLException {
        UDate date = UDate.getInstanceTimestamp(rs.getTimestamp(fieldName));
        if (date == null) {
            return null;
        }
        return enqBiz.getStrDate(reqMdl, date);
    }

    /**
     * <br>[機  能] 指定した日付フィールドの値から曜日を取得して返す
     * <br>[解  説]
     * <br>[備  考]
     * @param rs ResultSet
     * @param fieldName フィールド名
     * @param reqMdl リクエストモデル
     * @return 日付文字列
     * @throws SQLException 日付の取得に失敗
     */
    private String __createDayOfWeek(ResultSet rs, String fieldName, RequestModel reqMdl)
            throws SQLException {
        UDate date = UDate.getInstanceTimestamp(rs.getTimestamp(fieldName));
        if (date == null) {
            return null;
        }

        return date.getStrWeekJ(reqMdl);
    }
}
