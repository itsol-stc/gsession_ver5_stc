package jp.groupsession.v2.enq.enq320;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.enq.GSConstEnquete;
import jp.groupsession.v2.enq.biz.EnqCommonBiz;
import jp.groupsession.v2.enq.dao.EnqMainDao;
import jp.groupsession.v2.usr.GSConstUser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] アンケート 結果確認（一覧）画面で使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq320Dao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Enq320Dao.class);

    /**
     * <p>Default Constructor
     */
    public Enq320Dao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Enq320Dao(Connection con) {
        super(con);
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
    public List<Enq320AnswerModel> getAnswerList(Enq320SearchModel searchMdl, RequestModel reqMdl)
    throws SQLException {

        //匿名フラグを設定
        Connection con = getCon();
        long emnSid = searchMdl.getEmnSid();
        EnqMainDao enqMainDao = new EnqMainDao(con);
        int anony = enqMainDao.getAnony(emnSid);
        boolean anonyFlg
            = anony == GSConstEnquete.EMN_ANONNY_ANONNY;

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Enq320AnswerModel> ansList = new ArrayList<Enq320AnswerModel>();
        EnqCommonBiz enqBiz = new EnqCommonBiz();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   ENQ_ANS_MAIN.EAM_STS_KBN as EAM_STS_KBN,");
            sql.addSql("   ENQ_ANS_MAIN.EQM_ANS_DATE as EQM_ANS_DATE,");
            sql.addSql("   GROUP_DATA.GRP_NAME as GRP_NAME,");
            sql.addSql("   GROUP_DATA.GRP_JKBN as GRP_JKBN,");
            sql.addSql("   CMN_USRM.USR_SID as USR_SID,");
            sql.addSql("   CMN_USRM.USR_JKBN as USR_JKBN,");
            sql.addSql("   CMN_USRM.USR_UKO_FLG as USR_UKO_FLG,");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI");

            //検索条件を設定
            __setAnswerSQL(sql, searchMdl, reqMdl);

            sql.addSql(" order by");
            String order = "";
            if (searchMdl.getOrder() == Enq320Const.ORDER_DESC) {
                order = " desc";
            }
            switch (searchMdl.getSortKey()) {
                case Enq320Const.SORTKEY_JOUTAI:
                    sql.addSql("   ENQ_ANS_MAIN.EAM_STS_KBN" + order);
                    if (!anonyFlg) {
                        sql.addSql("   ,CMN_USRM_INF.USI_SEI_KN" + order + ",");
                        sql.addSql("   CMN_USRM_INF.USI_MEI_KN" + order);
                    }
                    break;
                case Enq320Const.SORTKEY_GROUP:
                    sql.addSql("   GROUP_DATA.GRP_NAME_KN" + order);
                    break;
                case Enq320Const.SORTKEY_USER:
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
                Enq320AnswerModel ansData = new Enq320AnswerModel();

                //ユーザSID
                ansData.setUserSid(rs.getInt("USR_SID"));
                //状態
                ansData.setStatus(rs.getInt("EAM_STS_KBN"));
                //グループ・ユーザ
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
                //ansData.setAnsDate(__createDateString(rs, "EQM_ANS_DATE"));
                ansData.setAnsDate(__createDateString(reqMdl, enqBiz, rs, "EQM_ANS_DATE"));
                //匿名
                ansData.setAnony(anony);

                ansList.add(ansData);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return ansList;
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
    public int getAnswerCount(Enq320SearchModel searchMdl, RequestModel reqMdl)
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
     * <br>[機  能] 対象者情報検索SQLを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SqlBuffer
     * @param searchMdl 検索条件
     * @param reqMdl リクエスト情報
     * @return SqlBuffer
     * @throws SQLException SQL実行時例外
     */
    private SqlBuffer __setAnswerSQL(SqlBuffer sql, Enq320SearchModel searchMdl,
                                                        RequestModel reqMdl)
    throws SQLException {

        sql.addSql(" from");
        sql.addSql("   ENQ_ANS_MAIN,");
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
        sql.addSql("     CMN_USRM_INF.USR_SID = GROUP_DATA.USR_SID");
        sql.addSql(" where");
        sql.addSql("   ENQ_ANS_MAIN.EMN_SID = ?");
        sql.addSql(" and");
        sql.addSql("   ENQ_ANS_MAIN.USR_SID = CMN_USRM.USR_SID");
        sql.addSql(" and");
        sql.addSql("   CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID");
        sql.addIntValue(GSConstUser.BEG_DEFGRP_DEFAULT);
        sql.addLongValue(searchMdl.getEmnSid());

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

        //状態
        if (searchMdl.getStsAns() == 1 && searchMdl.getStsNon() != 1) {
            //回答済
            sql.addSql(" and");
            sql.addSql("   ENQ_ANS_MAIN.EAM_STS_KBN = ?");
            sql.addIntValue(GSConstEnquete.EAM_STS_KBN_YES);
        } else if (searchMdl.getStsAns() != 1 && searchMdl.getStsNon() == 1) {
            //未回答
            sql.addSql(" and");
            sql.addSql("   ENQ_ANS_MAIN.EAM_STS_KBN = ?");
            sql.addIntValue(GSConstEnquete.EAM_STS_KBN_NO);
        }

        return sql;
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
