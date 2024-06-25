package jp.groupsession.v2.anp.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.anp.GSConstAnpi;
import jp.groupsession.v2.anp.model.AnpStateModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 安否確認共通 DAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class AnpiCommonDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(AnpiCommonDao.class);
    /** リクエストモデル */
    private RequestModel reqMdl__ = null;

    /**
     * <p>Default Constructor
     */
    public AnpiCommonDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     * @param reqMdl リクエストモデル
     */
    public AnpiCommonDao(Connection con, RequestModel reqMdl) {
        super(con);
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 現在の状況内容を取得
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param anpiSid 安否確認SID
     * @return 状況内容格納MODEL
     * @throws SQLException SQL実行例外
     */
    public AnpStateModel getStateInfo(int anpiSid) throws SQLException {
        return getStateInfo(anpiSid, true);
    }

    /**
     * <br>[機  能] 現在の状況内容を取得
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param anpiSid 安否確認SID
     * @param isFinish true：完了以外
     * @return 状況内容格納MODEL
     * @throws SQLException SQL実行例外
     */
    public AnpStateModel getStateInfo(int anpiSid, boolean isFinish) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        AnpStateModel ret = null;
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   ANP_HDATA.APH_SID,");
            sql.addSql("   ANP_HDATA.APH_HDATE,");
            sql.addSql("   ANP_HDATA.APH_SDATE,");
            sql.addSql("   count(*) as CNT_ALL,");
            sql.addSql("   sum(case when APD_RDATE is null then 0 else 1 end) as CNT_R,");
            sql.addSql("   sum(case when APD_JOKYO_FLG = ? then 1 else 0 end) as CNT_JGD,");
            sql.addSql("   sum(case when APD_JOKYO_FLG = ? then 1 else 0 end) as CNT_JKS,");
            sql.addSql("   sum(case when APD_JOKYO_FLG = ? then 1 else 0 end) as CNT_JJS,");
            sql.addSql("   sum(case when APD_SYUSYA_FLG in (");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql("   ) then 1 else 0 end) as CNT_SOK,");
            sql.addSql("   sum(case when APD_SYUSYA_FLG = ? then 1 else 0 end) as CNT_SNO,");
            sql.addSql("   max(ANP_JDATA.APD_RDATE) as LAST_DATE");
            sql.addSql(" from");
            sql.addSql("   ANP_HDATA");
            sql.addSql(" left join");
            sql.addSql("   ANP_JDATA on ANP_HDATA.APH_SID = ANP_JDATA.APH_SID");
            sql.addSql(" where");
            sql.addSql("   ANP_HDATA.APH_SID = ?");

            if (isFinish) {
                sql.addSql(" and");
                sql.addSql("   ANP_HDATA.APH_END_FLG <> 1");
            }

            sql.addSql(" group by ");
            sql.addSql("   ANP_HDATA.APH_SID,");
            sql.addSql("   ANP_HDATA.APH_HDATE,");
            sql.addSql("   ANP_HDATA.APH_SDATE");


            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(GSConstAnpi.JOKYO_FLG_GOOD);
            sql.addIntValue(GSConstAnpi.JOKYO_FLG_KEISYO);
            sql.addIntValue(GSConstAnpi.JOKYO_FLG_JUSYO);
            sql.addIntValue(GSConstAnpi.SYUSYA_FLG_OK);
            sql.addIntValue(GSConstAnpi.SYUSYA_FLG_OKD);
            sql.addIntValue(GSConstAnpi.SYUSYA_FLG_NO);
            sql.addIntValue(anpiSid);
            sql.setParameter(pstmt);

            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getStateFromRs(rs);
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
     * <br>[機  能] 現在の状況内容を取得
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param rs ResultSet
     * @return created Anp010StateModel
     * @throws SQLException SQL実行例外
     */
    private AnpStateModel __getStateFromRs(ResultSet rs) throws SQLException {
        AnpStateModel bean = new AnpStateModel();

        GsMessage gsMsg = new GsMessage(reqMdl__);
        String mei = gsMsg.getMessage("anp.count.people");

        DecimalFormat percentformat = new DecimalFormat("0%");
        DecimalFormat commaformat = new DecimalFormat("###,###,###,###");

        //配信日時
        bean.setHaisinDate(__getDspDate(rs, "APH_HDATE"));
        //再送日時
        bean.setResendDate(__getDspDate(rs, "APH_SDATE"));

        //返信状況
        BigDecimal aCount = rs.getBigDecimal("CNT_ALL");
        BigDecimal rCount = rs.getBigDecimal("CNT_R");
        if (aCount != BigDecimal.ZERO) {
            BigDecimal rPercent = rCount.divide(aCount, 3, BigDecimal.ROUND_UP);
            String replyState = percentformat.format(rPercent) + " （"
                              + commaformat.format(rCount) + "/"
                              + commaformat.format(aCount) + mei + "）";
            bean.setReplyState(replyState);
            //状態
            bean.setJokyoGood(commaformat.format(rs.getBigDecimal("CNT_JGD")) + mei);
            bean.setJokyoKeisyo(commaformat.format(rs.getBigDecimal("CNT_JKS")) + mei);
            bean.setJokyoJusyo(commaformat.format(rs.getBigDecimal("CNT_JJS")) + mei);
            //出社
            bean.setSyusyaOk(commaformat.format(rs.getBigDecimal("CNT_SOK")) + mei);
            bean.setSyusyaNo(commaformat.format(rs.getBigDecimal("CNT_SNO")) + mei);
            //最終回答日時
            bean.setLastDate(__getDspDate(rs, "LAST_DATE"));
        }

        if (aCount == rCount) {
            bean.setStateFlg(1);
        }

        return bean;
    }

    /**
     * 一覧に表示する日付の書式を整えて戻す
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param rs ResultSet
     * @param fieldName 列名
     * @return 表示日付
     * @throws SQLException SQL実行例外
     */
    private String __getDspDate(ResultSet rs, String fieldName) throws SQLException {
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        if (rs.getDate(fieldName) == null) {
            return "-";
        } else {
            return dateformat.format(rs.getTimestamp(fieldName));
        }
    }
}