package jp.groupsession.v2.enq.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.enq.GSConstEnquete;
import jp.groupsession.v2.enq.model.EnqQuestionDataModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 設問情報の取得処理
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class EnqQuestionDataDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(EnqQuestionDataDao.class);

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public EnqQuestionDataDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] アンケートを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param enqSid アンケートSID
     * @return EnqQuestionModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<EnqQuestionDataModel> select(long enqSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<EnqQuestionDataModel> ret = new ArrayList<EnqQuestionDataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   ENQ_QUE_MAIN.EMN_SID,");
            sql.addSql("   ENQ_QUE_MAIN.EQM_SEQ,");
            sql.addSql("   EQM_DSP_SEC,");
            sql.addSql("   EQS_SEQ,");
            sql.addSql("   EQS_DSP_SEC,");
            sql.addSql("   EQM_QUE_SEC,");
            sql.addSql("   EQM_QUESTION,");
            sql.addSql("   EQM_QUE_KBN,");
            sql.addSql("   EQM_REQUIRE,");
            sql.addSql("   EQM_OTHER,");
            sql.addSql("   EQM_DESC,");
            sql.addSql("   EQM_ATTACH_KBN,");
            sql.addSql("   EQM_ATTACH_ID,");
            sql.addSql("   EQM_ATTACH_NAME,");
            sql.addSql("   EQM_ATTACH_POS,");
            sql.addSql("   EQM_LINE_KBN,");
            sql.addSql("   EQM_GRF_KBN,");
            sql.addSql("   EQS_DSP_NAME,");
            sql.addSql("   EQS_DEF_TXT,");
            sql.addSql("   EQS_DEF_NUM,");
            sql.addSql("   EQS_DEF_DAT,");
            sql.addSql("   EQS_DEF,");
            sql.addSql("   EQS_RNG_STR_NUM,");
            sql.addSql("   EQS_RNG_END_NUM,");
            sql.addSql("   EQS_RNG_STR_DAT,");
            sql.addSql("   EQS_RNG_END_DAT,");
            sql.addSql("   EQS_UNIT_NUM");
            sql.addSql(" from ENQ_QUE_MAIN");
            sql.addSql(" left join ENQ_QUE_SUB on ENQ_QUE_MAIN.EMN_SID = ENQ_QUE_SUB.EMN_SID");
            sql.addSql("                      and ENQ_QUE_MAIN.EQM_SEQ = ENQ_QUE_SUB.EQM_SEQ");
            sql.addSql(" where ENQ_QUE_MAIN.EMN_SID = ?");
            sql.addSql(" order by EQM_DSP_SEC, EQS_DSP_SEC");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(enqSid);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getEnqQuestionFromRs(rs));
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
     * <br>[機  能] 回答済アンケートを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param enqSid アンケートSID
     * @param usrSid ユーザSID
     * @return EnqQuestionModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<EnqQuestionDataModel> selectAnswered(long enqSid, int usrSid)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<EnqQuestionDataModel> ret = new ArrayList<EnqQuestionDataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   ENQ_QUE_MAIN.EMN_SID as EMN_SID,");
            sql.addSql("   ENQ_QUE_MAIN.EQM_SEQ as EQM_SEQ,");
            sql.addSql("   EQM_DSP_SEC,");
            sql.addSql("   ENQ_QUE_SUB.EQS_SEQ as EQS_SEQ,");
            sql.addSql("   EQS_DSP_SEC,");
            sql.addSql("   EQM_QUE_SEC,");
            sql.addSql("   EQM_QUESTION,");
            sql.addSql("   EQM_QUE_KBN,");
            sql.addSql("   EQM_REQUIRE,");
            sql.addSql("   EQM_OTHER,");
            sql.addSql("   EQM_DESC,");
            sql.addSql("   EQM_ATTACH_KBN,");
            sql.addSql("   EQM_ATTACH_ID,");
            sql.addSql("   EQM_ATTACH_NAME,");
            sql.addSql("   EQM_ATTACH_POS,");
            sql.addSql("   EQM_LINE_KBN,");
            sql.addSql("   EQM_GRF_KBN,");
            sql.addSql("   EQS_DSP_NAME,");
            sql.addSql("   EQS_DEF,");
            sql.addSql("   EQS_RNG_STR_NUM,");
            sql.addSql("   EQS_RNG_END_NUM,");
            sql.addSql("   EQS_RNG_STR_DAT,");
            sql.addSql("   EQS_RNG_END_DAT,");
            sql.addSql("   EQS_UNIT_NUM,");
            sql.addSql("   EAS_ANS_TXT,");
            sql.addSql("   EAS_ANS_NUM,");
            sql.addSql("   EAS_ANS_DAT,");
            sql.addSql("   EAS_ANS");
            sql.addSql("   ");
            sql.addSql(" from");
            sql.addSql("   ENQ_QUE_MAIN");
            sql.addSql("   left join");
            sql.addSql("     ENQ_QUE_SUB");
            sql.addSql("   on");
            sql.addSql("     ENQ_QUE_MAIN.EMN_SID = ENQ_QUE_SUB.EMN_SID");
            sql.addSql("   and");
            sql.addSql("     ENQ_QUE_MAIN.EQM_SEQ = ENQ_QUE_SUB.EQM_SEQ");
            sql.addSql("   left join");
            sql.addSql("     (");
            sql.addSql("       select * from ENQ_ANS_MAIN");
            sql.addSql("       where ENQ_ANS_MAIN.EMN_SID = ?");
            sql.addSql("       and ENQ_ANS_MAIN.USR_SID = ?");
            sql.addSql("       and ENQ_ANS_MAIN.EAM_STS_KBN = ?");
            sql.addSql("     ) ANS_MAIN");
            sql.addSql("   on");
            sql.addSql("     ENQ_QUE_MAIN.EMN_SID = ANS_MAIN.EMN_SID");
            sql.addSql("   left join");
            sql.addSql("     (");
            sql.addSql("       select * from ENQ_ANS_SUB");
            sql.addSql("       where ENQ_ANS_SUB.EMN_SID = ?");
            sql.addSql("       and ENQ_ANS_SUB.USR_SID = ?");
            sql.addSql("     ) ANS_SUB");
            sql.addSql("   on");
            sql.addSql("     ENQ_QUE_MAIN.EMN_SID = ANS_SUB.EMN_SID");
            sql.addSql("   and");
            sql.addSql("     ENQ_QUE_MAIN.EQM_SEQ = ANS_SUB.EQM_SEQ");
            sql.addSql("   and");
            sql.addSql("     ENQ_QUE_SUB.EQS_SEQ = ANS_SUB.EQS_SEQ");
            sql.addSql(" where");
            sql.addSql("   ENQ_QUE_MAIN.EMN_SID = ?");
            sql.addSql(" order by");
            sql.addSql("   ENQ_QUE_MAIN.EQM_DSP_SEC, ENQ_QUE_SUB.EQS_DSP_SEC");

            sql.addLongValue(enqSid);
            sql.addIntValue(usrSid);
            sql.addIntValue(GSConstEnquete.ANS_KBN_ANSWERED);
            sql.addLongValue(enqSid);
            sql.addIntValue(usrSid);
            sql.addLongValue(enqSid);
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getEnqQueAnsedFromRs(rs));
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
     * <p>Create ENQ_TYPE Data Binding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created EnqTypeModel
     * @throws SQLException SQL実行例外
     */
    private EnqQuestionDataModel __getEnqQuestionFromRs(ResultSet rs) throws SQLException {
        EnqQuestionDataModel bean = new EnqQuestionDataModel();
        // 設問_基本
        bean.setEmnSid(rs.getLong("EMN_SID"));
        bean.setEqmSeq(rs.getInt("EQM_SEQ"));
        bean.setEqmDspSec(rs.getInt("EQM_DSP_SEC"));
        bean.setEqmQueSec(rs.getString("EQM_QUE_SEC"));
        bean.setEqmQuestion(rs.getString("EQM_QUESTION"));
        bean.setEqmQueKbn(rs.getInt("EQM_QUE_KBN"));
        bean.setEqmRequire(rs.getInt("EQM_REQUIRE"));
        bean.setEqmOther(rs.getInt("EQM_OTHER"));
        bean.setEqmDesc(rs.getString("EQM_DESC"));
        bean.setEqmAttachKbn(rs.getInt("EQM_ATTACH_KBN"));
        bean.setEqmAttachId(rs.getString("EQM_ATTACH_ID"));
        bean.setEqmAttachName(rs.getString("EQM_ATTACH_NAME"));
        bean.setEqmAttachPos(rs.getInt("EQM_ATTACH_POS"));
        bean.setEqmLineKbn(rs.getInt("EQM_LINE_KBN"));
        bean.setEqmGrfKbn(rs.getInt("EQM_GRF_KBN"));
        // 設問_サブ
        bean.setEqsSeq(rs.getInt("EQS_SEQ"));
        bean.setEqsDspSec(rs.getInt("EQS_DSP_SEC"));
        bean.setEqsDspName(rs.getString("EQS_DSP_NAME"));
        bean.setEqsDefTxt(rs.getString("EQS_DEF_TXT"));
        bean.setEqsDefNum(rs.getLong("EQS_DEF_NUM"));
        bean.setEqsDefDat(UDate.getInstanceTimestamp(rs.getTimestamp("EQS_DEF_DAT")));
        bean.setEqsDef(rs.getString("EQS_DEF"));
        bean.setEqsRngStrNum(rs.getString("EQS_RNG_STR_NUM"));
        bean.setEqsRngEndNum(rs.getString("EQS_RNG_END_NUM"));
        bean.setEqsRngStrDat(UDate.getInstanceTimestamp(rs.getTimestamp("EQS_RNG_STR_DAT")));
        bean.setEqsRngEndDat(UDate.getInstanceTimestamp(rs.getTimestamp("EQS_RNG_END_DAT")));
        bean.setEqsUnitNum(rs.getString("EQS_UNIT_NUM"));

        return bean;
    }

    /**
     * <p>Create ENQ_TYPE Data Binding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created EnqTypeModel
     * @throws SQLException SQL実行例外
     */
    private EnqQuestionDataModel __getEnqQueAnsedFromRs(ResultSet rs) throws SQLException {
        EnqQuestionDataModel bean = new EnqQuestionDataModel();
        // 設問_基本
        bean.setEmnSid(rs.getLong("EMN_SID"));
        bean.setEqmSeq(rs.getInt("EQM_SEQ"));
        bean.setEqmDspSec(rs.getInt("EQM_DSP_SEC"));
        bean.setEqmQueSec(rs.getString("EQM_QUE_SEC"));
        bean.setEqmQuestion(rs.getString("EQM_QUESTION"));
        bean.setEqmQueKbn(rs.getInt("EQM_QUE_KBN"));
        bean.setEqmRequire(rs.getInt("EQM_REQUIRE"));
        bean.setEqmOther(rs.getInt("EQM_OTHER"));
        bean.setEqmDesc(rs.getString("EQM_DESC"));
        bean.setEqmAttachKbn(rs.getInt("EQM_ATTACH_KBN"));
        bean.setEqmAttachId(rs.getString("EQM_ATTACH_ID"));
        bean.setEqmAttachName(rs.getString("EQM_ATTACH_NAME"));
        bean.setEqmAttachPos(rs.getInt("EQM_ATTACH_POS"));
        bean.setEqmLineKbn(rs.getInt("EQM_LINE_KBN"));
        bean.setEqmGrfKbn(rs.getInt("EQM_GRF_KBN"));
        // 設問_サブ
        bean.setEqsSeq(rs.getInt("EQS_SEQ"));
        bean.setEqsDspSec(rs.getInt("EQS_DSP_SEC"));
        bean.setEqsDspName(rs.getString("EQS_DSP_NAME"));
        bean.setEqsRngStrNum(rs.getString("EQS_RNG_STR_NUM"));
        bean.setEqsRngEndNum(rs.getString("EQS_RNG_END_NUM"));
        bean.setEqsRngStrDat(UDate.getInstanceTimestamp(rs.getTimestamp("EQS_RNG_STR_DAT")));
        bean.setEqsRngEndDat(UDate.getInstanceTimestamp(rs.getTimestamp("EQS_RNG_END_DAT")));
        bean.setEqsUnitNum(rs.getString("EQS_UNIT_NUM"));
        // 回答_サブ
        bean.setEasAnsTxt(rs.getString("EAS_ANS_TXT"));
        bean.setEasAnsNum(rs.getLong("EAS_ANS_NUM"));
        bean.setEasAnsDat(UDate.getInstanceTimestamp(rs.getTimestamp("EAS_ANS_DAT")));
        bean.setEasAns(rs.getString("EAS_ANS"));

        return bean;
    }
}
