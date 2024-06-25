package jp.groupsession.v2.enq.enq310;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.enq.GSConstEnquete;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] アンケート 結果確認画面で使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq310Dao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Enq310Dao.class);

    /**
     * <p>Default Constructor
     */
    public Enq310Dao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Enq310Dao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] 指定したアンケートの対象人数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param emnSid アンケートSID
     * @return 対象者件数
     * @throws SQLException SQL実行時例外
     */
    public int[] getAnswerCount(long emnSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int[] result = new int[3];
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(EMN_SID) as ALLCNT,");
            sql.addSql("   count(case when EAM_STS_KBN = ? then 1 else null end) as ANSCNT,");
            sql.addSql("   count(case when EAM_STS_KBN = ? then 1 else null end) as NOANSCNT");
            sql.addSql(" from");
            sql.addSql("   ENQ_ANS_MAIN");
            sql.addSql(" where");
            sql.addSql("   ENQ_ANS_MAIN.EMN_SID = ?");
            sql.addIntValue(GSConstEnquete.EAM_STS_KBN_YES);
            sql.addIntValue(GSConstEnquete.EAM_STS_KBN_NO);
            sql.addLongValue(emnSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                result[0] = rs.getInt("ALLCNT");
                result[1] = rs.getInt("ANSCNT");
                result[2] = rs.getInt("NOANSCNT");
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
     * <br>[機  能] 指定したアンケートの対象人数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param emnSid アンケートSID
     * @param eqmSeq 設問連番
     * @return 対象者件数
     * @throws SQLException SQL実行時例外
     */
    public int[] getAnswerCount(long emnSid, int eqmSeq)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int[] result = new int[3];
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(EMN_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   ENQ_ANS_MAIN");
            sql.addSql(" where");
            sql.addSql("   EMN_SID = ?");
            sql.addLongValue(emnSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                result[0] = rs.getInt("CNT");
            }

            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closePreparedStatement(pstmt);

            sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(distinct USR_SID) as ANSCNT");
            sql.addSql("  from");
            sql.addSql("    ENQ_ANS_SUB");
            sql.addSql("  where");
            sql.addSql("    EMN_SID = ?");
            sql.addSql("  and");
            sql.addSql("    EQM_SEQ = ?");
            sql.addSql("  and");
            sql.addSql("    EAS_ANS is not null ");
            sql.addSql("  and");
            sql.addSql("    EAS_ANS <> ''");
            sql.addLongValue(emnSid);
            sql.addLongValue(eqmSeq);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                result[1] = rs.getInt("ANSCNT");
                if (result[0] > 0) {
                    result[2] = result[0] - result[1];
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
     * <br>[機  能] 指定したアンケートの回答情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param emnSid アンケートSID
     * @param eqmSeq 設問連番
     * @param queKbn 設問種類
     * @return 回答情報
     * @throws SQLException SQL実行時例外
     */
    public List<Enq310QuestionSubModel> getAnswerSubList(RequestModel reqMdl,
                                                         long emnSid,
                                                         int eqmSeq,
                                                         int queKbn) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Enq310QuestionSubModel> ansSubList = new ArrayList<Enq310QuestionSubModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   ENQ_QUE_SUB.EMN_SID as EMN_SID,");
            sql.addSql("   ENQ_QUE_SUB.EQM_SEQ as EQM_SEQ,");
            sql.addSql("   ENQ_QUE_SUB.EQS_SEQ as EQS_SEQ,");
            sql.addSql("   ENQ_QUE_SUB.EQS_DSP_NAME as EQS_DSP_NAME,");
            sql.addSql("   ANS_MAIN.CNT as ALLCNT,");
            sql.addSql("   ANS_MAIN.ANS_CNT as ANS_CNT,");
            sql.addSql("   ANS_SUB.ANS_SUB_CNT as ANS_SUB_CNT");
            sql.addSql(" from");
            sql.addSql("   (");
            sql.addSql("     select");
            sql.addSql("       EMN_SID,");
            sql.addSql("       count(EMN_SID) as CNT,");
            sql.addSql("       count(case when EAM_STS_KBN = ? then 1 else null end) as ANS_CNT");
            sql.addSql("     from");
            sql.addSql("       ENQ_ANS_MAIN");
            sql.addSql("     where");
            sql.addSql("       ENQ_ANS_MAIN.EMN_SID = ?");
            sql.addSql("     group by");
            sql.addSql("       ENQ_ANS_MAIN.EMN_SID");
            sql.addSql("   ) ANS_MAIN,");
            sql.addSql("   ENQ_QUE_SUB");
            sql.addSql("   left join");
            sql.addSql("     (");
            sql.addSql("       select");
            sql.addSql("         ENQ_ANS_SUB.EMN_SID as EMN_SID,");
            sql.addSql("         ENQ_ANS_SUB.EQM_SEQ as EQM_SEQ,");
            sql.addSql("         ENQ_ANS_SUB.EQS_SEQ as EQS_SEQ,");
            sql.addSql("         count(ENQ_ANS_SUB.EAS_ANS) as ANS_SUB_CNT");
            sql.addSql("       from");
            sql.addSql("         ENQ_ANS_SUB");
            sql.addSql("       where");
            sql.addSql("         ENQ_ANS_SUB.EMN_SID = ?");
            sql.addSql("       and");
            sql.addSql("         ENQ_ANS_SUB.EQM_SEQ = ?");
            sql.addSql("       group by");
            sql.addSql("         ENQ_ANS_SUB.EMN_SID,");
            sql.addSql("         ENQ_ANS_SUB.EQM_SEQ,");
            sql.addSql("         ENQ_ANS_SUB.EQS_SEQ");
            sql.addSql("     ) ANS_SUB");
            sql.addSql("   on");
            sql.addSql("     ENQ_QUE_SUB.EMN_SID = ANS_SUB.EMN_SID");
            sql.addSql("     and ENQ_QUE_SUB.EQM_SEQ = ANS_SUB.EQM_SEQ");
            sql.addSql("     and ENQ_QUE_SUB.EQS_SEQ = ANS_SUB.EQS_SEQ");
            sql.addSql(" where");
            sql.addSql("   ENQ_QUE_SUB.EMN_SID = ?");
            sql.addSql(" and");
            sql.addSql("   ENQ_QUE_SUB.EQM_SEQ = ?");
            sql.addSql(" and");
            sql.addSql("   ENQ_QUE_SUB.EMN_SID = ANS_MAIN.EMN_SID ");
            sql.addSql(" order by");
            sql.addSql("   ENQ_QUE_SUB.EQS_DSP_SEC");
            sql.addIntValue(GSConstEnquete.EAM_STS_KBN_YES);
            sql.addLongValue(emnSid);
            sql.addLongValue(emnSid);
            sql.addLongValue(eqmSeq);
            sql.addLongValue(emnSid);
            sql.addLongValue(eqmSeq);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            Enq310Biz biz310 = new Enq310Biz();
            while (rs.next()) {
                Enq310QuestionSubModel ansSubData = new Enq310QuestionSubModel();
                //設問SID
                ansSubData.setEmnSid(emnSid);
                //設問連番
                ansSubData.setQueSeq(eqmSeq);
                //設問サブ連番
                ansSubData.setQueSubSeq(rs.getInt("EQS_SEQ"));
                //表示名
                if (rs.getInt("EQS_SEQ") == GSConstEnquete.CHOICE_KBN_OTHER) {
                    GsMessage gsMsg = new GsMessage(reqMdl);
                    ansSubData.setDspName(gsMsg.getMessage("cmn.other"));
                } else {
                    ansSubData.setDspName(rs.getString("EQS_DSP_NAME"));
                }
                //対象人数
                int ansSubCnt = rs.getInt("ANS_SUB_CNT");
                ansSubData.setAnswer(StringUtil.toCommaFormat(String.valueOf(ansSubCnt)));
                ansSubData.setAnswerNum(String.valueOf(ansSubCnt));
                //対象人数 割合 全体
                int allCnt = rs.getInt("ALLCNT");
                int arPer = biz310.getRatio(allCnt, ansSubCnt);
                ansSubData.setAnswerAllPer(String.valueOf(arPer));
                //対象人数 割合 回答人数
                int ansCnt = rs.getInt("ANS_CNT");
                int ansPer = biz310.getRatio(ansCnt, ansSubCnt);
                ansSubData.setAnswerArPer(String.valueOf(ansPer));

                ansSubList.add(ansSubData);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return ansSubList;
    }
}
