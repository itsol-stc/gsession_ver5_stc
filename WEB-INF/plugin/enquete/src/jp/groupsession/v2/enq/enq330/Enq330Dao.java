package jp.groupsession.v2.enq.enq330;

import java.math.BigDecimal;
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
import jp.groupsession.v2.enq.dao.EnqMainDao;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] アンケート 結果確認（詳細）画面で使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq330Dao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Enq330Dao.class);

    /**
     * <p>Default Constructor
     */
    public Enq330Dao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Enq330Dao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] 対象者情報件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param searchMdl 検索条件
     * @param reqMdl リクエスト情報
     * @return 対象者情報件数
     * @throws SQLException SQL実行時例外
     */
    public int getAnswerCount(Enq330SearchModel searchMdl, RequestModel reqMdl)
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
            sql.addSql("   count(ENQ_ANS_MAIN.USR_SID) as CNT");

            //検索条件を設定
            __setAnswerSQL(sql, searchMdl, reqMdl);

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
     * <br>[機  能] 対象者情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param searchMdl 検索条件
     * @param reqMdl リクエスト情報
     * @return 対象者情報一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Enq330AnswerModel> getAnswerList(Enq330SearchModel searchMdl, RequestModel reqMdl)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Enq330AnswerModel> ansList = new ArrayList<Enq330AnswerModel>();
        con = getCon();
        EnqCommonBiz enqBiz = new EnqCommonBiz();

        //単一選択または複数選択
        boolean selectAns = searchMdl.getQueKbn() == GSConstEnquete.SYURUI_SINGLE
                                    || searchMdl.getQueKbn() == GSConstEnquete.SYURUI_MULTIPLE;

        long emnSid = searchMdl.getEmnSid();
        EnqMainDao enqMainDao = new EnqMainDao(con);
        int anony = enqMainDao.getAnony(emnSid);
        boolean anonyFlg
            = anony == GSConstEnquete.EMN_ANONNY_ANONNY;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   ENQ_ANS_MAIN.EAM_STS_KBN as EAM_STS_KBN,");
            sql.addSql("   ENQ_ANS_MAIN.EQM_ANS_DATE as EQM_ANS_DATE,");
            if (!selectAns) {
                sql.addSql("   ANS_SUB.EAS_ANS_TXT as EAS_ANS_TXT,");
                sql.addSql("   ANS_SUB.EAS_ANS_NUM as EAS_ANS_NUM,");
                sql.addSql("   ANS_SUB.EAS_ANS_DAT as EAS_ANS_DAT,");
                sql.addSql("   ANS_SUB.EAS_ANS as EAS_ANS,");
            }
            sql.addSql("   GROUP_DATA.GRP_NAME as GRP_NAME,");
            sql.addSql("   GROUP_DATA.GRP_JKBN as GRP_JKBN,");
            sql.addSql("   CMN_USRM.USR_SID as USR_SID,");
            sql.addSql("   CMN_USRM.USR_JKBN as USR_JKBN,");
            sql.addSql("   CMN_USRM.USR_UKO_FLG as USR_UKO_FLG,");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI");

            //検索条件を設定
            __setAnswerSQL(sql, searchMdl, reqMdl);

            //ソート
            sql.addSql(" order by");
            String order = "";
            if (searchMdl.getOrder() == Enq330Const.ORDER_DESC) {
                order = " desc";
            }
            switch (searchMdl.getSortKey()) {
                case Enq330Const.SORTKEY_GROUP:
                    sql.addSql("   GROUP_DATA.GRP_NAME_KN" + order);
                    break;
                case Enq330Const.SORTKEY_USER:
                    sql.addSql("   CMN_USRM_INF.USI_SEI_KN" + order + ",");
                    sql.addSql("   CMN_USRM_INF.USI_MEI_KN" + order);
                    break;
                default:
                    sql.addSql("   case when EQM_ANS_DATE is null then 0 else 1 end" + order + ",");
                    sql.addSql("   ENQ_ANS_MAIN.EQM_ANS_DATE" + order);
                    if (!anonyFlg) {
                        sql.addSql("   ,CMN_USRM_INF.USI_SEI_KN" + order + ",");
                        sql.addSql("   CMN_USRM_INF.USI_MEI_KN" + order);
                    }
            }

            //ページング
            int rowNum = PageUtil.getRowNumber(searchMdl.getPage(), searchMdl.getMaxCount()) - 1;
            if (rowNum < 0) {
                rowNum = 0;
            }
            sql.setPagingValue(rowNum, searchMdl.getMaxCount());

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Enq330AnswerModel ansData = new Enq330AnswerModel();

                //ユーザSID
                ansData.setUserSid(rs.getInt("USR_SID"));
                //匿名フラグ
                ansData.setAnony(anonyFlg);

                //グループ
                //ユーザ
                if (anonyFlg) {
                    ansData.setGroup("*****");
                    ansData.setUser("*****");
                } else {
                    ansData.setGroup(rs.getString("GRP_NAME"));
                    ansData.setGroupDelFlg(rs.getInt("GRP_JKBN") == GSConst.JTKBN_DELETE);
                    ansData.setUser(rs.getString("USI_SEI") + " " + rs.getString("USI_MEI"));
                    ansData.setUserDelFlg(rs.getInt("USR_JKBN") == GSConst.JTKBN_DELETE);
                    ansData.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
                }
                //回答日
                ansData.setAnsDate(__createDateString(reqMdl, enqBiz, rs, "EQM_ANS_DATE"));

                //回答値
                if (!selectAns) {
                    String ansValue = null;
                    switch (searchMdl.getQueKbn()) {

                    // テキスト入力
                    // テキスト入力（複数行）
                    case(GSConstEnquete.SYURUI_TEXT):
                    case(GSConstEnquete.SYURUI_TEXTAREA):
                        ansValue = rs.getString("EAS_ANS_TXT");
                        break;

                    // 数値入力
                    case(GSConstEnquete.SYURUI_INTEGER):
                        ansValue = StringUtil.toCommaFormat(rs.getString("EAS_ANS_NUM"));
                        break;

                    // 日付入力
                    case(GSConstEnquete.SYURUI_DAY):
                        ansValue = __createDateString(reqMdl, enqBiz, rs, "EAS_ANS_DAT");
                        break;

                    // コメント
                    default:
                        ansValue = rs.getString("EAS_ANS");
                    }
                    ansData.setAnsValue(ansValue);
                }
                ansList.add(ansData);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        if (selectAns && ansList != null && !ansList.isEmpty()) {
            GsMessage gsMsg = new GsMessage(reqMdl);
            __setAnsSubData(searchMdl, gsMsg, ansList);
        }

        return ansList;
    }


    /**
     * <br>[機  能] 対象者情報検索SQLを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SqlBuffer
     * @param searchMdl 検索条件
     * @param reqMdl リクエスト情報
     * @return SqlBuffer
     * @throws SQLException SQL実行時例外
     */
    private SqlBuffer __setAnswerSQL(SqlBuffer sql, Enq330SearchModel searchMdl,
                                                        RequestModel reqMdl)
    throws SQLException {
        //単一選択または複数選択
        boolean selectAns = searchMdl.getQueKbn() == GSConstEnquete.SYURUI_SINGLE
                                    || searchMdl.getQueKbn() == GSConstEnquete.SYURUI_MULTIPLE;

        sql.addSql(" from");
        sql.addSql("   CMN_USRM,");
        sql.addSql("   CMN_USRM_INF");
        sql.addSql("   left join");
        sql.addSql("     (");
        sql.addSql("       select");
        sql.addSql("         CMN_GROUPM.GRP_NAME as GRP_NAME,");
        sql.addSql("         CMN_GROUPM.GRP_NAME_KN as GRP_NAME_KN,");
        sql.addSql("         CMN_GROUPM.GRP_JKBN as GRP_JKBN,");
        sql.addSql("         CMN_BELONGM.USR_SID as USR_SID");
        sql.addSql("       from");
        sql.addSql("         CMN_GROUPM,");
        sql.addSql("         CMN_BELONGM");
        sql.addSql("       where");
        sql.addSql("         CMN_BELONGM.BEG_DEFGRP = ?");
        sql.addSql("       and");
        sql.addSql("         CMN_BELONGM.GRP_SID = CMN_GROUPM.GRP_SID");
        sql.addSql("     ) GROUP_DATA");
        sql.addSql("   on");
        sql.addSql("     CMN_USRM_INF.USR_SID = GROUP_DATA.USR_SID,");
        sql.addSql("   ENQ_ANS_MAIN");
        sql.addIntValue(GSConstUser.BEG_DEFGRP_DEFAULT);
        if (!selectAns) {
            sql.addSql("   left join");
            sql.addSql("     (");
            sql.addSql("       select * from ENQ_ANS_SUB");
            sql.addSql("       where EMN_SID = ?");
            sql.addSql("       and EQM_SEQ = ?");
            sql.addSql("     ) ANS_SUB");
            sql.addSql("   on");
            sql.addSql("     ENQ_ANS_MAIN.EMN_SID = ANS_SUB.EMN_SID");
            sql.addSql("     and ENQ_ANS_MAIN.USR_SID = ANS_SUB.USR_SID");
            sql.addLongValue(searchMdl.getEmnSid());
            sql.addIntValue(searchMdl.getEqmSeq());
        }
        sql.addSql(" where");
        sql.addSql("   ENQ_ANS_MAIN.EMN_SID = ?");
        sql.addSql(" and");
        sql.addSql("   ENQ_ANS_MAIN.USR_SID = CMN_USRM.USR_SID");
        sql.addSql(" and");
        sql.addSql("   CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID");
        sql.addLongValue(searchMdl.getEmnSid());

        //対象(グループ・ユーザ)
        if (searchMdl.getUser() >= 0) {
            sql.addSql(" and");
            sql.addSql("   ENQ_ANS_MAIN.USR_SID = ?");
            sql.addIntValue(searchMdl.getUser());
        } else if (searchMdl.getGroup() >= 0) {
            sql.addSql(" and");
            sql.addSql("   ENQ_ANS_MAIN.USR_SID in (");
            sql.addSql("     select USR_SID from CMN_BELONGM");
            sql.addSql("     where GRP_SID = ?");
            sql.addSql("   )");
            sql.addIntValue(searchMdl.getGroup());
        }

        //回答(テキスト)
        if (!StringUtil.isNullZeroString(searchMdl.getAnsText())) {
            sql.addSql(" and");
            sql.addSql("   ENQ_ANS_MAIN.USR_SID in (");
            sql.addSql("       select USR_SID from ENQ_ANS_SUB");
            sql.addSql("       where ENQ_ANS_SUB.EMN_SID = ?");
            sql.addSql("       and ENQ_ANS_SUB.EQM_SEQ = ?");
            sql.addSql("       and ENQ_ANS_SUB.EAS_ANS is not null");
            sql.addLongValue(searchMdl.getEmnSid());
            sql.addIntValue(searchMdl.getEqmSeq());

            sql.addSql("       and ENQ_ANS_SUB.EAS_ANS_TXT like '%"
                + JDBCUtil.escapeForLikeSearch(searchMdl.getAnsText())
                + "%' ESCAPE '"
                + JDBCUtil.def_esc
                + "'");
            sql.addSql("   )");
        }

        //回答(数値)
        boolean range = searchMdl.getAnsNumKbn() == Enq330Const.ANS_NUM_KBN_RANGE;
        if (searchMdl.getQueKbn() == GSConstEnquete.SYURUI_INTEGER) {
            if ((!range && !StringUtil.isNullZeroString(searchMdl.getAnsNumFrom()))
            || (range
                && (!StringUtil.isNullZeroString(searchMdl.getAnsNumFrom())
                || !StringUtil.isNullZeroString(searchMdl.getAnsNumTo())))) {

                sql.addSql(" and");
                sql.addSql("   ENQ_ANS_MAIN.USR_SID in (");
                sql.addSql("       select USR_SID from ENQ_ANS_SUB");
                sql.addSql("       where ENQ_ANS_SUB.EMN_SID = ?");
                sql.addSql("       and ENQ_ANS_SUB.EQM_SEQ = ?");
                sql.addSql("       and ENQ_ANS_SUB.EAS_ANS is not null");
                sql.addLongValue(searchMdl.getEmnSid());
                sql.addIntValue(searchMdl.getEqmSeq());

                if (range) {
                    if (!StringUtil.isNullZeroString(searchMdl.getAnsNumFrom())) {
                        sql.addSql("       and ENQ_ANS_SUB.EAS_ANS_NUM >= ?");
                        sql.addDoubleValue(Double.parseDouble(searchMdl.getAnsNumFrom()));
                    }
                    if (!StringUtil.isNullZeroString(searchMdl.getAnsNumTo())) {
                        sql.addSql("       and ENQ_ANS_SUB.EAS_ANS_NUM <= ?");
                        sql.addDoubleValue(Double.parseDouble(searchMdl.getAnsNumTo()));
                    }
                } else {
                    sql.addSql("       and ENQ_ANS_SUB.EAS_ANS_NUM = ?");
                    sql.addDoubleValue(Double.parseDouble(searchMdl.getAnsNumFrom()));
                }
                sql.addSql("   )");
            }
        }

        //回答(日付)
        if (searchMdl.getQueKbn() == GSConstEnquete.SYURUI_DAY) {
            if ((!range && searchMdl.getAnsDateFrom() != null)
            || (range
                && (searchMdl.getAnsDateFrom() != null || searchMdl.getAnsDateTo() != null))) {

                sql.addSql(" and");
                sql.addSql("   ENQ_ANS_MAIN.USR_SID in (");
                sql.addSql("       select USR_SID from ENQ_ANS_SUB");
                sql.addSql("       where ENQ_ANS_SUB.EMN_SID = ?");
                sql.addSql("       and ENQ_ANS_SUB.EQM_SEQ = ?");
                sql.addSql("       and ENQ_ANS_SUB.EAS_ANS is not null");
                sql.addLongValue(searchMdl.getEmnSid());
                sql.addIntValue(searchMdl.getEqmSeq());

                if (range) {
                    if (searchMdl.getAnsDateFrom() != null) {
                        sql.addSql("       and ENQ_ANS_SUB.EAS_ANS_DAT >= ?");
                        sql.addDateValue(searchMdl.getAnsDateFrom());
                    }
                    if (searchMdl.getAnsDateTo() != null) {
                        sql.addSql("       and ENQ_ANS_SUB.EAS_ANS_DAT <= ?");
                        sql.addDateValue(searchMdl.getAnsDateTo());
                    }
                } else {
                    sql.addSql("       and ENQ_ANS_SUB.EAS_ANS_DAT = ?");
                    sql.addDateValue(searchMdl.getAnsDateFrom());
                }
                sql.addSql("   )");
            }
        }

        //回答(選択肢)
        int[] ansSubList = searchMdl.getAns();
        if (ansSubList != null && ansSubList.length > 0) {
            sql.addSql(" and");
            sql.addSql("   ENQ_ANS_MAIN.USR_SID in (");
            sql.addSql("       select USR_SID from ENQ_ANS_SUB");
            sql.addSql("       where ENQ_ANS_SUB.EMN_SID = ?");
            sql.addSql("       and ENQ_ANS_SUB.EQM_SEQ = ?");
            sql.addSql("       and ENQ_ANS_SUB.EAS_ANS is not null");
            sql.addLongValue(searchMdl.getEmnSid());
            sql.addIntValue(searchMdl.getEqmSeq());

            sql.addSql("       and ENQ_ANS_SUB.EQS_SEQ in (");
            for (int ansIdx = 0; ansIdx < ansSubList.length; ansIdx++) {
                if (ansIdx > 0) {
                    sql.addSql("         ,?");
                } else {
                    sql.addSql("         ?");
                }
                sql.addIntValue(ansSubList[ansIdx]);
            }
            sql.addSql("       )");

            sql.addSql("   )");
        }

        return sql;
    }

    /**
     * <br>[機  能] 対象者情報に回答(選択)情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param searchMdl 検索条件
     * @param gsMsg GsMessage
     * @param ansList 対象者情報一覧
     * @throws SQLException SQL実行時例外
     */
    private void __setAnsSubData(Enq330SearchModel searchMdl, GsMessage gsMsg,
                                                List<Enq330AnswerModel> ansList)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   ENQ_ANS_SUB.EQS_SEQ as EQS_SEQ,");
            sql.addSql("   ENQ_ANS_SUB.EAS_ANS_TXT as EAS_ANS_TXT,");
            sql.addSql("   ENQ_ANS_SUB.EAS_ANS as EAS_ANS,");
            sql.addSql("   ENQ_QUE_SUB.EQS_DSP_NAME as EQS_DSP_NAME");
            sql.addSql(" from");
            sql.addSql("   ENQ_ANS_SUB");
            sql.addSql("   left join");
            sql.addSql("     ENQ_QUE_SUB");
            sql.addSql("   on");
            sql.addSql("     ENQ_ANS_SUB.EMN_SID = ENQ_QUE_SUB.EMN_SID");
            sql.addSql("   and");
            sql.addSql("     ENQ_ANS_SUB.EQM_SEQ = ENQ_QUE_SUB.EQM_SEQ");
            sql.addSql("   and");
            sql.addSql("     ENQ_ANS_SUB.EQS_SEQ = ENQ_QUE_SUB.EQS_SEQ");
            sql.addSql(" where");
            sql.addSql("   ENQ_ANS_SUB.EMN_SID = ?");
            sql.addSql(" and");
            sql.addSql("   ENQ_ANS_SUB.USR_SID = ?");
            sql.addSql(" and");
            sql.addSql("   ENQ_ANS_SUB.EQM_SEQ = ?");
            sql.addSql(" and");
            sql.addSql("   ENQ_ANS_SUB.EAS_ANS_NUM = ?");
            sql.addSql(" order by");
            sql.addSql("   case when ENQ_QUE_SUB.EQS_DSP_SEC is null then 1 else 0 end,");
            sql.addSql("   ENQ_QUE_SUB.EQS_DSP_SEC");

            pstmt = con.prepareStatement(sql.toSqlString());

            String ansTxt = null;
            for (int idx = 0; idx < ansList.size(); idx++) {
                sql.addLongValue(searchMdl.getEmnSid());
                sql.addIntValue(ansList.get(idx).getUserSid());
                sql.addIntValue(searchMdl.getEqmSeq());
                sql.addIntValue(GSConstEnquete.EAS_ANS_NUM_SELECT);

                log__.info(sql.toLogString());
                sql.setParameter(pstmt);
                rs = pstmt.executeQuery();

                ansTxt = "";
                while (rs.next()) {
                    if (rs.getInt("EQS_SEQ") == GSConstEnquete.EQS_SEQ_OTHER) {
                        ansTxt +=
                                gsMsg.getMessage("enq.ans.selectval",
                                        new String[] {gsMsg.getMessage("cmn.other") + ": "
                                        + rs.getString("EAS_ANS")});
                    } else {
                        ansTxt +=
                                gsMsg.getMessage("enq.ans.selectval",
                                        new String[] {rs.getString("EQS_DSP_NAME")});
                    }
                }
                ansList.get(idx).setAnsValue(ansTxt);

                JDBCUtil.closeResultSet(rs);
                sql.clearValue();
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] アンケート 回答結果(数値入力)の集計値を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param emnSid アンケートSID
     * @param eqmSeq 設問連番
     * @return 回答結果(数値入力)の集計値
     * @throws SQLException SQL実行時例外
     */
    public long[] getNumAnsCount(long emnSid, int eqmSeq)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        long[] result = new long[3];
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   min(EAS_ANS_NUM) as MIN_VALUE,");
            sql.addSql("   max(EAS_ANS_NUM) as MAX_VALUE,");
            sql.addSql("   sum(EAS_ANS_NUM) as SUM_VALUE,");
            sql.addSql("   count(EMN_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   ENQ_ANS_SUB");
            sql.addSql(" where");
            sql.addSql("   EMN_SID = ?");
            sql.addSql(" and");
            sql.addSql("   EQM_SEQ = ?");
            sql.addSql(" and");
            sql.addSql("   EAS_ANS is not null");
            sql.addSql(" and");
            sql.addSql("   EAS_ANS <> ''");
            sql.addLongValue(emnSid);
            sql.addIntValue(eqmSeq);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                result[0] = rs.getLong("MIN_VALUE");
                result[1] = rs.getLong("MAX_VALUE");
                long sumValue = rs.getLong("SUM_VALUE");
                int ansCnt = rs.getInt("CNT");
                if (sumValue > 0 && ansCnt > 0) {
                    BigDecimal aveValue = new BigDecimal(String.valueOf(sumValue));
                    aveValue = aveValue.divide(
                            new BigDecimal(String.valueOf(ansCnt)),
                            0,
                            BigDecimal.ROUND_DOWN);
                    result[2] = aveValue.longValue();
                }
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
     * <br>[機  能] アンケート 回答結果(日付入力)の集計値を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param emnSid アンケートSID
     * @param eqmSeq 設問連番
     * @return 回答結果(日付入力)の集計値
     * @throws SQLException SQL実行時例外
     */
    public String[] getDateAnsCount(RequestModel reqMdl, long emnSid, int eqmSeq)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        String[] result = new String[2];
        con = getCon();
        EnqCommonBiz enqBiz = new EnqCommonBiz();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   min(EAS_ANS_DAT) as MIN_VALUE,");
            sql.addSql("   max(EAS_ANS_DAT) as MAX_VALUE,");
            sql.addSql("   count(EMN_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   ENQ_ANS_SUB");
            sql.addSql(" where");
            sql.addSql("   EMN_SID = ?");
            sql.addSql(" and");
            sql.addSql("   EQM_SEQ = ?");
            sql.addSql(" and");
            sql.addSql("   EAS_ANS is not null");
            sql.addLongValue(emnSid);
            sql.addIntValue(eqmSeq);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                result[0] = __createDateString(reqMdl, enqBiz, rs, "MIN_VALUE");
                result[1] = __createDateString(reqMdl, enqBiz, rs, "MAX_VALUE");
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
}
