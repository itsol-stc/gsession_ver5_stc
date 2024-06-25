package jp.groupsession.v2.enq.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.WmlDao;
import jp.groupsession.v2.cmn.model.EnqueteDataModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] アンケートに関するDB操作を行う
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class EnqueteDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WmlDao.class);
    /** アンケート通知区分  未回答者のみ */
    public static final int SML_NOTICE_NOANS = 0;
    /** アンケート通知区分  全回答者 */
    public static final int SML_NOTICE_ALL = 1;
    /** 状態区分 未回答 */
    public static final int ANS_KBN_UNANSWERED = 0;

    /**
     * <p>Default Constructor
     */
    public EnqueteDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public EnqueteDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] アンケート情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param emnSid アンケートSID
     * @param sendKbn 発信区分(0:未回答者のみ、1:回答済含む)
     * @return アンケート情報
     * @throws SQLException SQL実行時例外
     */
    public EnqueteDataModel getEnqueteData(long emnSid, int sendKbn)
    throws SQLException {
        EnqueteDataModel enqData = null;

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   ENQ_MAIN.EMN_SID as EMN_SID,");
            sql.addSql("   ENQ_MAIN.EMN_DATA_KBN as EMN_DATA_KBN,");
            sql.addSql("   ENQ_MAIN.ETP_SID as ETP_SID,");
            sql.addSql("   ENQ_MAIN.EMN_TITLE as EMN_TITLE,");
            sql.addSql("   ENQ_MAIN.EMN_PRI_KBN as EMN_PRI_KBN,");
            sql.addSql("   ENQ_MAIN.EMN_DESC as EMN_DESC,");
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
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI");
            sql.addSql(" from ");
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
            sql.addSql("     CMN_USRM_INF");
            sql.addSql("   on");
            sql.addSql("     ENQ_MAIN.EMN_SEND_USR = CMN_USRM_INF.USR_SID");
            sql.addSql(" where");
            sql.addSql("   ENQ_MAIN.EMN_SID = ?");

            sql.addLongValue(emnSid);
            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                enqData = new EnqueteDataModel();
                //アンケートSID
                enqData.setEnqSid(rs.getInt("EMN_SID"));
                //重要度
                enqData.setPriority(rs.getInt("EMN_PRI_KBN"));
                //発信者
                String groupName = rs.getString("GRP_NAME");
                if (groupName != null) {
                    enqData.setSender(groupName);
                } else {
                    enqData.setSender(rs.getString("USI_SEI")
                            + " " + rs.getString("USI_MEI"));
                }
                //種類
                enqData.setTypeName(rs.getString("ETP_NAME"));
                //タイトル
                enqData.setTitle(rs.getString("EMN_TITLE"));
                // 回答期限
                enqData.setAnsLimitDate(
                        UDate.getInstanceTimestamp(rs.getTimestamp("EMN_RES_END")));
                //公開期限 開始
                enqData.setPubStartDate(
                        UDate.getInstanceTimestamp(rs.getTimestamp("EMN_OPEN_STR")));
                //公開期限 終了
                enqData.setPubEndDate(
                        UDate.getInstanceTimestamp(rs.getTimestamp("EMN_OPEN_END")));
                enqData.setPubEndKbn(rs.getInt("EMN_OPEN_END_KBN"));
                //回答公開期限 開始
                enqData.setAnsPubStartDate(
                        UDate.getInstanceTimestamp(rs.getTimestamp("EMN_ANS_PUB_STR")));

                //匿名
                enqData.setAnnoy(rs.getInt("EMN_ANONY"));

                //対象者を設定
                JDBCUtil.closeResultSet(rs);
                JDBCUtil.closePreparedStatement(pstmt);

                List<String> answerList = new ArrayList<String>();
                sql = new SqlBuffer();
                sql.addSql(" select ");
                sql.addSql("   ENQ_ANS_MAIN.USR_SID as USR_SID");
                sql.addSql(" from ");
                sql.addSql("   CMN_USRM,");
                sql.addSql("   ENQ_ANS_MAIN");
                sql.addSql(" where");
                sql.addSql("   ENQ_ANS_MAIN.EMN_SID = ?");
                sql.addLongValue(emnSid);
                if (sendKbn == SML_NOTICE_NOANS) {
                    sql.addSql(" and");
                    sql.addSql("    ENQ_ANS_MAIN.EAM_STS_KBN = ?");
                    sql.addIntValue(ANS_KBN_UNANSWERED);
                }
                sql.addSql(" and");
                sql.addSql("   CMN_USRM.USR_JKBN <> ?");
                sql.addSql(" and");
                sql.addSql("   CMN_USRM.USR_SID = ENQ_ANS_MAIN.USR_SID");
                sql.addIntValue(GSConst.JTKBN_DELETE);
                log__.info(sql.toLogString());

                pstmt = getCon().prepareStatement(sql.toSqlString());
                sql.setParameter(pstmt);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    answerList.add(rs.getString("USR_SID"));
                }
                enqData.setAnswerList(answerList);
            }

        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closePreparedStatement(pstmt);
        }

        return enqData;
    }
}
