package jp.groupsession.v2.enq.enq210;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.enq.GSConstEnquete;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] アンケート 設問登録画面で使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq210Dao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Enq210Dao.class);

    /**
     * <p>Default Constructor
     */
    public Enq210Dao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Enq210Dao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] 指定した設問情報へ初期値、範囲情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param queMdl 設問情報
     * @return 設問情報
     * @throws SQLException SQL実行時例外
     */
    public Enq210QueModel setEnqDefRngValue(Enq210QueModel queMdl)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   EQS_DEF_TXT,");
            sql.addSql("   EQS_DEF_NUM,");
            sql.addSql("   EQS_DEF_DAT,");
            sql.addSql("   EQS_DEF,");
            sql.addSql("   EQS_RNG_STR_NUM,");
            sql.addSql("   EQS_RNG_END_NUM,");
            sql.addSql("   EQS_RNG_STR_DAT,");
            sql.addSql("   EQS_RNG_END_DAT,");
            sql.addSql("   EQS_UNIT_NUM");
            sql.addSql(" from");
            sql.addSql("   ENQ_QUE_SUB");
            sql.addSql(" where ");
            sql.addSql("   EMN_SID=?");
            sql.addSql(" and");
            sql.addSql("   EQM_SEQ=?");
            sql.addLongValue(queMdl.getEnq210Sid());
            sql.addIntValue(queMdl.getEnq210Seq());
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                int queKbn = queMdl.getEnq210QueKbn();
                //初期値
                if (!StringUtil.isNullZeroString(rs.getString("EQS_DEF"))) {
                    queMdl.setEnq210initFlg(Enq210QueModel.INITFLG_SELECT);
                    if (queKbn == GSConstEnquete.SYURUI_TEXT
                    || queKbn == GSConstEnquete.SYURUI_TEXTAREA) {
                        //初期値_文字
                        queMdl.setEnq210initTxt(rs.getString("EQS_DEF_TXT"));
                    } else if (queKbn == GSConstEnquete.SYURUI_INTEGER) {
                        //初期値_数値
                        queMdl.setEnq210initTxt(rs.getString("EQS_DEF_NUM"));
                    } else if (queKbn == GSConstEnquete.SYURUI_DAY) {
                        //初期値_日付
                        queMdl.setEnq210initDate(
                                UDate.getInstanceTimestamp(rs.getTimestamp("EQS_DEF_DAT")));
                    }
                }

                //範囲、単位
                if (queKbn == GSConstEnquete.SYURUI_INTEGER) {
                    String rangeNumFr = rs.getString("EQS_RNG_STR_NUM");
                    String rangeNumTo = rs.getString("EQS_RNG_END_NUM");
                    if (!StringUtil.isNullZeroString(rangeNumFr)
                    && !StringUtil.isNullZeroString(rangeNumTo)) {
                        //範囲 数値
                        queMdl.setEnq210rangeFlg(Enq210QueModel.RANGEFLG_SELECT);
                        queMdl.setEnq210rangeTxtFr(rangeNumFr);
                        queMdl.setEnq210rangeTxtTo(rangeNumTo);
                    }
                    queMdl.setEnq210unitNum(rs.getString("EQS_UNIT_NUM"));
                } else if (queKbn == GSConstEnquete.SYURUI_DAY) {
                    //範囲 日付
                    UDate frDate = UDate.getInstanceTimestamp(rs.getTimestamp("EQS_RNG_STR_DAT"));
                    if (frDate != null) {
                        queMdl.setEnq210rangeFlg(Enq210QueModel.RANGEFLG_SELECT);
                        frDate.setZeroHhMmSs();
                        queMdl.setEnq210rangeDateFr(frDate);
                    }
                    UDate toDate = UDate.getInstanceTimestamp(rs.getTimestamp("EQS_RNG_END_DAT"));
                    if (toDate != null) {
                        queMdl.setEnq210rangeFlg(Enq210QueModel.RANGEFLG_SELECT);
                        toDate.setZeroHhMmSs();
                        queMdl.setEnq210rangeDateTo(toDate);
                    }
                }
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return queMdl;
    }

    /**
     * <br>[機  能] アンケート テンプレートの一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return アンケート テンプレートの一覧
     * @throws SQLException SQL実行時例外
     */
    public List<LabelValueBean> getTemplateList() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        List<LabelValueBean> templateList = new ArrayList<LabelValueBean>();

        try {

            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   EMN_SID,");
            sql.addSql("   EMN_TITLE");
            sql.addSql(" from");
            sql.addSql("   ENQ_MAIN");
            sql.addSql(" left join");
            sql.addSql("   ENQ_TYPE on ENQ_MAIN.ETP_SID = ENQ_TYPE.ETP_SID");
            sql.addSql(" where ");
            sql.addSql("   EMN_DATA_KBN=?");
            sql.addSql(" order by");
            sql.addSql("   case when ENQ_MAIN.ETP_SID < 1 then 1 else 0 end,");
            sql.addSql("   ETP_DSP_SEQ, EMN_TITLE");
            sql.addIntValue(GSConstEnquete.EMN_DATA_KBN_TEMPLATE);
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                templateList.add(
                        new LabelValueBean(rs.getString("EMN_TITLE"),
                                                    rs.getString("EMN_SID")));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return templateList;
    }

    /**
     * <br>[機  能] アンケート 回答済ユーザの件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param emnSid アンケートSID
     * @return 回答済ユーザの件数
     * @throws SQLException SQL実行時例外
     */
    public int getAnsCount(long emnSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        int count = 0;

        try {

            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(EMN_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   ENQ_ANS_MAIN");
            sql.addSql(" where ");
            sql.addSql("   EMN_SID=?");
            sql.addSql(" and ");
            sql.addSql("   EAM_STS_KBN=?");
            sql.addLongValue(emnSid);
            sql.addIntValue(GSConstEnquete.ANS_KBN_ANSWERED);
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
     * <br>[機  能] 指定したアンケートの対象者グループのうち、削除されたグループの名称を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param emnSid アンケートSID
     * @return グループ名
     * @throws SQLException SQL実行時例外
     */
    public List<String> getDeleteSubjectGroupName(long emnSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<String> ret = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CMN_GROUPM.GRP_NAME as GRP_NAME");
            sql.addSql(" from ");
            sql.addSql("   CMN_GROUPM,");
            sql.addSql("   ENQ_SUBJECT");
            sql.addSql(" where ");
            sql.addSql("   ENQ_SUBJECT.EMN_SID = ?");
            sql.addSql(" and ");
            sql.addSql("   CMN_GROUPM.GRP_JKBN = ?");
            sql.addSql(" and ");
            sql.addSql("   CMN_GROUPM.GRP_SID = ENQ_SUBJECT.GRP_SID");
            sql.addLongValue(emnSid);
            sql.addIntValue(GSConst.JTKBN_DELETE);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getString("GRP_NAME"));
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
     * <br>[機  能] 指定したアンケートの対象者ユーザのうち、削除されたユーザの名称を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param emnSid アンケートSID
     * @return ユーザ名
     * @throws SQLException SQL実行時例外
     */
    public List<String> getDeleteSubjectUserName(long emnSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<String> ret = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI");
            sql.addSql(" from ");
            sql.addSql("   CMN_USRM,");
            sql.addSql("   CMN_USRM_INF,");
            sql.addSql("   ENQ_SUBJECT");
            sql.addSql(" where ");
            sql.addSql("   ENQ_SUBJECT.EMN_SID = ?");
            sql.addSql(" and ");
            sql.addSql("   CMN_USRM.USR_JKBN = ?");
            sql.addSql(" and ");
            sql.addSql("   CMN_USRM.USR_SID = ENQ_SUBJECT.USR_SID");
            sql.addSql(" and ");
            sql.addSql("   CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID");
            sql.addLongValue(emnSid);
            sql.addIntValue(GSConst.JTKBN_DELETE);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getString("USI_SEI") + " " + rs.getString("USI_MEI"));
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
