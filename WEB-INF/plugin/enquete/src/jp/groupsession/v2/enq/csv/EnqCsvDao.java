package jp.groupsession.v2.enq.csv;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.UserSearchDao;
import jp.groupsession.v2.cmn.dao.base.CmnCmbsortConfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnCmbsortConfModel;
import jp.groupsession.v2.enq.GSConstEnquete;
import jp.groupsession.v2.enq.model.EnqQueMainModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] アンケートCVS出力用データ取得
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class EnqCsvDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(EnqCsvDao.class);

    /**
     * <p>Default Constructor
     */
    public EnqCsvDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public EnqCsvDao(Connection con) {
        super(con);
    }

    /**
     *
     * <br>[機  能]EnqCsvSubMdelにセットする値を取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @param emnSid アンケートSID
     * @return ret
     * @throws SQLException SQL実行例外
     */
    public EnqCsvSubModel selectEnqCsvSub(long emnSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<String> queList = new ArrayList<String>();
        EnqCsvSubModel ret = new EnqCsvSubModel();

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   ENQ_MAIN.EMN_SID as EMN_SID,");
            sql.addSql("   ENQ_MAIN.EMN_TITLE as EMN_TITLE,");
            sql.addSql("   ENQ_MAIN.EMN_ANONY as EMN_ANONY,");
            sql.addSql("   ENQ_QUE_MAIN.EQM_QUE_SEC as EQM_QUE_SEC");
            sql.addSql(" from");
            sql.addSql("   ENQ_MAIN,");
            sql.addSql("   ENQ_QUE_MAIN");
            sql.addSql(" where");
            sql.addSql("   ENQ_MAIN.EMN_SID = ?");
            sql.addSql(" and");
            sql.addSql("   ENQ_MAIN.EMN_SID = ENQ_QUE_MAIN.EMN_SID");
            sql.addSql(" order by");
            sql.addSql("   ENQ_QUE_MAIN.EQM_DSP_SEC");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(emnSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.setEnqCsvSid(rs.getLong("EMN_SID"));
                ret.setEnqTitle((rs.getString("EMN_TITLE")));
                ret.setAnonyFlg(rs.getInt("EMN_ANONY"));
                queList.add(rs.getString("EQM_QUE_SEC"));
                ret.setQuestion(queList);
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
     * <br>[機  能]EnqCsvModelにセットする値を取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @param emnSid アンケートSID
     * @param reqMdl リクエスト情報
     * @return ret
     * @throws SQLException SQL実行例外
     */
    public ArrayList<EnqCsvModel> createEnqCsv(
            long emnSid, RequestModel reqMdl) throws SQLException {

        //設問情報を取得（設問番号がないものは取得しない、設問区分：コメント）
        ArrayList<EnqQueMainModel> eqmList = __questionEnqCsv(emnSid);
        //CSV出力データの取得
        return __answerEnqCsv(emnSid, reqMdl, eqmList);
    }

    /**
     *
     * <br>[機  能] 設問_基本情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param emnSid アンケートSID
     * @return 設問_基本情報リスト
     * @throws SQLException SQLException
     */
    private ArrayList<EnqQueMainModel> __questionEnqCsv(long emnSid)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<EnqQueMainModel> ret = new ArrayList<EnqQueMainModel>();

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select  ");
            sql.addSql("   EMN_SID,  ");
            sql.addSql("   EQM_SEQ,  ");
            sql.addSql("   EQM_DSP_SEC,  ");
            sql.addSql("   EQM_QUE_SEC  ");
            sql.addSql(" from  ");
            sql.addSql("   ENQ_QUE_MAIN  ");
            sql.addSql(" where  ");
            sql.addSql("   EMN_SID = ?  ");
            sql.addSql(" and  ");
            sql.addSql("   EQM_QUE_SEC IS NOT NULL ");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(emnSid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                EnqQueMainModel model = new EnqQueMainModel();
                model.setEmnSid(rs.getLong("EMN_SID"));
                model.setEqmSeq(rs.getInt("EQM_SEQ"));
                model.setEqmDspSec(rs.getInt("EQM_DSP_SEC"));
                model.setEqmQueSec(rs.getString("EQM_QUE_SEC"));
                ret.add(model);
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
     * <br>[機  能]EnqCsvModelにセットする値を取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @param emnSid アンケートSID
     * @param reqMdl リクエスト情報
     * @param eqmList 設問_基本情報モデル
     * @return ret
     * @throws SQLException SQL実行例外
     */
    private ArrayList<EnqCsvModel> __answerEnqCsv(
            long emnSid, RequestModel reqMdl,
            ArrayList<EnqQueMainModel> eqmList) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;

        EnqCsvModel enqCsvMdl = null;
        ArrayList<EnqCsvModel> ret = new ArrayList<EnqCsvModel>();
        ArrayList<String> ansList = null;;

        con = getCon();

        CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con);
        CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();

        int sortKey1 = UserBiz.getSortKey(sortMdl.getCscUserSkey1());
        int order1 = sortMdl.getCscUserOrder1();
        int sortKey2 = UserBiz.getSortKey(sortMdl.getCscUserSkey2());
        int order2 = sortMdl.getCscUserOrder2();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select  ");
            sql.addSql("   MAIN_INFO.GRP_NAME as GRP_NAME,  ");
            sql.addSql("   ENQ_ANS_MAIN.USR_SID as USR_SID,  ");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI,  ");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI,  ");
            sql.addSql("   case  ");
            sql.addSql("     when CMN_USRM_INF.POS_SID = 0 then 1  ");
            sql.addSql("     else 0  ");
            sql.addSql("   end as YAKUSYOKU_EXIST,  ");
            sql.addSql("   case  ");
            sql.addSql("     when CMN_USRM_INF.POS_SID = 0 then 0  ");
            sql.addSql("     else (select  ");
            sql.addSql("             POS_SORT  ");
            sql.addSql("           from  ");
            sql.addSql("             CMN_POSITION  ");
            sql.addSql("           where CMN_USRM_INF.POS_SID = CMN_POSITION.POS_SID)  ");
            sql.addSql("   end as YAKUSYOKU_SORT,  ");
            sql.addSql("   ENQ_ANS_MAIN.EAM_STS_KBN as EAM_STS_KBN, ");
            sql.addSql("   ENQ_INFO.EQM_SEQ as EQM_SEQ,  ");
            sql.addSql("   ENQ_INFO.EQS_SEQ as EQS_SEQ, ");
            sql.addSql("   ENQ_INFO.EAS_ANS as EAS_ANS,  ");
            sql.addSql("   ENQ_INFO.EAS_ANS_NUM as EAS_ANS_NUM, ");
            sql.addSql("   ENQ_INFO.EQM_QUE_KBN as QUE_KBN  ");
            sql.addSql(" from  ");
            sql.addSql("   ENQ_ANS_MAIN ");
            sql.addSql(" left join  ");
            sql.addSql("   ( ");
            sql.addSql("    select ");
            sql.addSql("      ENQ_ANS_SUB.USR_SID as USR_SID,  ");
            sql.addSql("      ENQ_QUE_MAIN.EQM_SEQ as EQM_SEQ,  ");
            sql.addSql("      ENQ_QUE_MAIN.EQM_QUE_KBN as EQM_QUE_KBN,  ");
            sql.addSql("      ENQ_QUE_MAIN.EQM_DSP_SEC as EQM_DSP_SEC, ");
            sql.addSql("      ENQ_QUE_SUB.EQS_DSP_SEC as EQS_DSP_SEC, ");
            sql.addSql("      ENQ_QUE_SUB.EQS_SEQ as EQS_SEQ, ");
            sql.addSql("      ENQ_ANS_SUB.EAS_ANS as EAS_ANS,  ");
            sql.addSql("      ENQ_ANS_SUB.EAS_ANS_NUM as EAS_ANS_NUM ");
            sql.addSql("    from ");
            sql.addSql("      ENQ_QUE_MAIN,  ");
            sql.addSql("      ENQ_QUE_SUB, ");
            sql.addSql("      ENQ_ANS_SUB  ");
            sql.addSql("    where  ");
            sql.addSql("      ENQ_QUE_MAIN.EQM_SEQ = ENQ_ANS_SUB.EQM_SEQ  ");
            sql.addSql("    and   ");
            sql.addSql("      ENQ_QUE_MAIN.EMN_SID = ENQ_ANS_SUB.EMN_SID  ");
            sql.addSql("    and   ");
            sql.addSql("      ENQ_QUE_MAIN.EMN_SID = ENQ_QUE_SUB.EMN_SID  ");
            sql.addSql("    and   ");
            sql.addSql("      ENQ_QUE_MAIN.EQM_SEQ = ENQ_QUE_SUB.EQM_SEQ  ");
            sql.addSql("    and   ");
            sql.addSql("      ENQ_ANS_SUB.EQS_SEQ = ENQ_QUE_SUB.EQS_SEQ  ");
            sql.addSql("    and  ");
            sql.addSql("      ENQ_QUE_MAIN.EMN_SID = ? ");
            sql.addSql("    and ");
            sql.addSql("      ENQ_ANS_SUB.EAS_ANS IS NOT NULL ");
            sql.addSql("   )ENQ_INFO ");
            sql.addSql(" on ");
            sql.addSql("   ENQ_ANS_MAIN.USR_SID = ENQ_INFO.USR_SID ");
            sql.addSql(" left join  ");
            sql.addSql("   (  ");
            sql.addSql("    select  ");
            sql.addSql("      CMN_GROUPM.GRP_NAME as GRP_NAME,  ");
            sql.addSql("      GRP_BELONG.USR_SID as USR_SID  ");
            sql.addSql("    from  ");
            sql.addSql("      CMN_GROUPM  ");
            sql.addSql("    left join  ");
            sql.addSql("    (  ");
            sql.addSql("     select  ");
            sql.addSql("       CMN_BELONGM.GRP_SID as GRP_SID,  ");
            sql.addSql("       CMN_BELONGM.USR_SID as USR_SID  ");
            sql.addSql("     from  ");
            sql.addSql("       CMN_BELONGM  ");
            sql.addSql("     where  ");
            sql.addSql("       CMN_BELONGM.BEG_DEFGRP = ? ");
            sql.addSql("    ) GRP_BELONG  ");
            sql.addSql("    on  ");
            sql.addSql("      CMN_GROUPM.GRP_SID = GRP_BELONG.GRP_SID  ");
            sql.addSql("    where  ");
            sql.addSql("      GRP_BELONG.USR_SID IN   ");
            sql.addSql("       (  ");
            sql.addSql("        select   ");
            sql.addSql("          ENQ_ANS_MAIN.USR_SID as USR_SID  ");
            sql.addSql("        from  ");
            sql.addSql("          ENQ_ANS_MAIN  ");
            sql.addSql("        where  ");
            sql.addSql("          ENQ_ANS_MAIN.EMN_SID = ? ");
            sql.addSql("       )  ");
            sql.addSql("   ) MAIN_INFO  ");
            sql.addSql(" on  ");
            sql.addSql("   ENQ_ANS_MAIN.USR_SID = MAIN_INFO.USR_SID , ");
            sql.addSql(" CMN_USRM_INF ");
            sql.addSql(" where ");
            sql.addSql("   ENQ_ANS_MAIN.EMN_SID = ? ");
            sql.addSql(" and ");
            sql.addSql("   ENQ_ANS_MAIN.USR_SID = CMN_USRM_INF.USR_SID ");


            //ユーザのソート順に従って並び替えを行う
            sql.addSql(" order by");
            UserSearchDao userSearchDao = new UserSearchDao(con);
            userSearchDao.createOrderSql(sql, sortKey1, order1, sortKey2, order2);

            sql.addSql("   ,ENQ_ANS_MAIN.USR_SID,");
            sql.addSql("   case ENQ_INFO.EQM_DSP_SEC when null then 1 else 0 end,");
            sql.addSql("   EQM_SEQ, ");
            sql.addSql("   case ENQ_INFO.EQS_DSP_SEC when null then 1 else 0 end,");
            sql.addSql("   EQS_DSP_SEC");

            pstmt = con.prepareStatement(
                        sql.toSqlString(),
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
            sql.addLongValue(emnSid);
            sql.addIntValue(GSConstUser.BEG_DEFGRP_DEFAULT);
            sql.addLongValue(emnSid);
            sql.addLongValue(emnSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            StringBuilder sb = null;
            GsMessage gsMsg = new GsMessage(reqMdl);
            int usrSid = -1;
            int ansCnt = eqmList.size();
            //前提1：rsとしてはユーザ順、設問順にデータを取得している。
            //前提2：単一選択や複数選択が未選択の場合データを取得していない。
            while (rs.next()) {
                //rsを順にみておりユーザが変わったか
                if (usrSid != rs.getInt("USR_SID")) {
                    if (enqCsvMdl != null) {
                        //前ユーザの回答情報を格納
                        ret.add(enqCsvMdl);
                    }
                    //新ユーザをリストに格納するためユーザ情報を取得。また前ユーザの回答情報を削除
                    enqCsvMdl = new EnqCsvModel();
                    enqCsvMdl.setUserSid(rs.getInt("USR_SID"));
                    enqCsvMdl.setGroup((rs.getString("GRP_NAME")));
                    enqCsvMdl.setUser(rs.getString("USI_SEI") + " " + rs.getString("USI_MEI"));
                    enqCsvMdl.setStatusFlg(rs.getInt("EAM_STS_KBN"));
                    usrSid = enqCsvMdl.getUserSid();
                    sb = null;
                    ansCnt = 0;
                    ansList = new ArrayList<String>();
                    enqCsvMdl.setAnsValue(ansList);
                }
                while (ansCnt < eqmList.size()) {
                    //設問情報分のループを行い、rsと一致するか確認する
                    EnqQueMainModel eqmMdl = eqmList.get(ansCnt);
                    ansCnt++;
                    //設問に対して、回答情報が存在する
                    if (eqmMdl.getEqmSeq() == rs.getInt("EQM_SEQ")) {
                        //回答情報を格納する
                        sb = __setEnqAnsCsv(enqCsvMdl, sb, rs, gsMsg);
                        //複数選択回答対応
                        if (rs.next()) {
                            if (eqmMdl.getEqmSeq() == rs.getInt("EQM_SEQ")
                            && usrSid == rs.getInt("USR_SID")) {
                                ansCnt--;
                            }
                        } 
                        rs.previous();
                        break;
                    }

                    if (sb == null) {
                        sb = new StringBuilder();
                    }
                    //設問に対して、回答情報が存在しない
                    enqCsvMdl.getAnsValue().add("");
                }
            }
            if (enqCsvMdl != null) {
                ret.add(enqCsvMdl);
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
    * <br>[機  能]EnqCsvModelに値をセットします。
    * <br>[解  説]
    * <br>[備  考]
    * @param enqCsvModel enqCsvModel
    * @param sb StringBuilder
    * @param rs ResultSet
    * @param gsMsg GsMessage
    * @return sb StringBuilder
    * @throws SQLException SQL実行例外
    */
    private StringBuilder __setEnqAnsCsv(EnqCsvModel enqCsvModel, StringBuilder sb, ResultSet rs,
            GsMessage gsMsg)
    throws SQLException {

        int queKbn = rs.getInt("QUE_KBN");
        String ansValue = rs.getString("EAS_ANS");
        switch (queKbn) {
            //コメント
            case GSConstEnquete.SYURUI_COMMENT:
                break;

            //単一選択、複数選択
            case GSConstEnquete.SYURUI_SINGLE:
            case GSConstEnquete.SYURUI_MULTIPLE:
                int seqFlg = rs.getInt("EQM_SEQ");

                if (sb == null) {
                    sb = new StringBuilder();
                }

                if (ansValue != null) {
                    if (queKbn == GSConstEnquete.SYURUI_SINGLE) {
                        sb.append(ansValue);
                    } else {
                        sb.append(gsMsg.getMessage("enq.ans.selectval",
                                new String[] {ansValue}));
                    }
                } else {
                    sb.append("");
                }

                if (rs.next()) {
                    if (seqFlg != rs.getInt("EQM_SEQ")
                    || enqCsvModel.getUserSid() != rs.getInt("USR_SID")) {
                        enqCsvModel.getAnsValue().add(sb.toString());
                        sb = null;
                    }
                } else {
                    enqCsvModel.getAnsValue().add(sb.toString());
                    sb = null;
                }
                //戻す
                rs.previous();


                break;

            //数値入力
            case GSConstEnquete.SYURUI_INTEGER:
                if (!StringUtil.isNullZeroString(ansValue)) {
                    enqCsvModel.getAnsValue().add(ansValue);
                } else {
                    enqCsvModel.getAnsValue().add("");
                }

                break;

            //テキスト入力、テキスト入力(複数行)、日付
            default:
                if (!StringUtil.isNullZeroString(ansValue)) {
                    enqCsvModel.getAnsValue().add(ansValue);
                } else {
                    enqCsvModel.getAnsValue().add("");
                }
        }

        return sb;
    }
}
