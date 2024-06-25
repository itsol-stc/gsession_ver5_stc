package jp.groupsession.v2.ntp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.csv.CSVException;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.model.UserSearchModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.biz.NtpCommonBiz;
import jp.groupsession.v2.ntp.model.AnkenPermitCheckModel;
import jp.groupsession.v2.ntp.model.NippouExSearchModel;
import jp.groupsession.v2.ntp.model.NippouListSearchModel;
import jp.groupsession.v2.ntp.model.NippouListSearchModel.ENUM_SORT;
import jp.groupsession.v2.ntp.model.NippouSearchModel;
import jp.groupsession.v2.ntp.model.NtpDataModel;
import jp.groupsession.v2.ntp.ntp100.NippouCsvModel;
import jp.groupsession.v2.ntp.ntp100.NtpCsvRecordListenerIppanImpl;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 日報情報検索用のDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class NippouSearchDao extends AbstractDao {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(NippouSearchDao.class);

    /**
     * <p>
     * デフォルトコンストラクタ
     */
    public NippouSearchDao() {
    }

    /**
     * <p>
     * デフォルトコンストラクタ
     *
     * @param con
     *            DBコネクション
     */
    public NippouSearchDao(Connection con) {
        super(con);
    }

    /**
     * <p>
     * ユーザSID、ユーザ区分、公開区分、日付範囲を指定し日報情報を取得する絞り込み条件の書き込み
     * @param sql 書き込み対象sqlバッファ
     * @param belongList ユーザ検索リスト
     * @param usrKbn ユーザ区分
     * @param pub 公開区分 ※-1を指定すると条件から除外されます
     * @param from 日付from
     * @param to 日付to
     * @param mod 取得モード (週間:"1" 月間:"2" 日間:"3")
     * @throws SQLException
     *             SQL実行例外
     */
    private void __writeWhereSelectNtp(SqlBuffer sql,
            ArrayList<UserSearchModel> belongList, int usrKbn, int pub,
            UDate from, UDate to, String mod
            ) throws SQLException {
        sql.addSql("   NTP_DATA.USR_SID in(");
        CmnUsrmInfModel uMdl = null;
        if (belongList.size() < 1) {
            sql.addSql("   -1");
        }
        for (int i = 0; i < belongList.size(); i++) {
            uMdl = belongList.get(i);
            if (i == 0) {
                sql.addSql("   ?");
            } else {
                sql.addSql("   ,?");
            }
            sql.addIntValue(uMdl.getUsrSid());
        }
        sql.addSql("   )");

        if (pub != -1) {
            sql.addSql(" and ");
            sql.addSql("   NTP_DATA.NIP_PUBLIC = ?");
            sql.addIntValue(pub);
        }

        if (mod.equals(GSConstNippou.DSP_MOD_WEEK)) {

            String fromDateStr = from.getDateStringForSql();
            String toDateStr = to.getDateStringForSql();

            sql.addSql(" and");
            sql.addSql("     NTP_DATA.NIP_DATE");
            sql.addSql("     between cast('" + fromDateStr + "' as DATE)");
            sql.addSql("     and cast('" + toDateStr + "' as DATE)");
        }

        if (mod.equals(GSConstNippou.DSP_MOD_MONTH)) {

            UDate frYmd = new UDate();
            frYmd.setDate(from.getYear(), from.getMonth(), 1);
            UDate toYmd = new UDate();
            toYmd.setDate(to.getYear(), to.getMonth(), to
                    .getMaxDayOfMonth());
            String fromDateStr = frYmd.getDateStringForSql();
            String toDateStr = toYmd.getDateStringForSql();

            sql.addSql(" and");
            sql.addSql("     NTP_DATA.NTP_DATE");
            sql.addSql("     between cast('" + fromDateStr + "' as DATE)");
            sql.addSql("     and cast('" + toDateStr + "' as DATE)");
        }

        if (mod.equals(GSConstNippou.DSP_MOD_DAY)) {
            String fromDateTmp = from.toSQLTimestamp().toString();

            sql.addSql(" and");
            sql.addSql("      NTP_DATA.NIP_DATE = ?");

            sql.addStrValue(fromDateTmp);
        }
    }
    /**
     * <p>
     * 日報のコメント数を取得する
     * 日報SIDをキーとするマップ型
     * @param nipSidList 日報SIDリスト
     * @return Map
     * @throws SQLException
     *             SQL実行例外
     */
    public Map<Integer, Integer> getCommentCntMap(
            List<Integer> nipSidList) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Map<Integer, Integer> ret = new HashMap<>();
        con = getCon();
        try {
            // SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NTP_COMMENT.NIP_SID as NIP_SID,");
            sql.addSql("   count(NTP_COMMENT.USR_SID) CNT");
            sql.addSql(" from");
            sql.addSql("   NTP_COMMENT");
            sql.addSql(" where ");
            sql.addSql(" NTP_COMMENT.NIP_SID in (");
            if (nipSidList.size() < 1) {
                sql.addSql("   -1");
            }
            for (int i = 0; i < nipSidList.size(); i++) {
                if (i == 0) {
                    sql.addSql("   ?");
                } else {
                    sql.addSql("   ,?");
                }
                sql.addIntValue(nipSidList.get(i));
            }
            sql.addSql(" )");
            sql.addSql(" group by NTP_COMMENT.NIP_SID");


            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.put(rs.getInt("NIP_SID"), rs.getInt("CNT"));
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
     * <p>
     * ユーザSID、ユーザ区分、公開区分、日付範囲を指定し日報のコメント数情報を取得する
     * 日報SIDをキーとするマップ型
     * @param belongList ユーザ検索リスト
     * @param usrKbn ユーザ区分
     * @param pub 公開区分 ※-1を指定すると条件から除外されます
     * @param from 日付from
     * @param to 日付to
     * @param mod 取得モード (週間:"1" 月間:"2" 日間:"3")
     * @return ArrayList in NtpDataModel
     * @throws SQLException
     *             SQL実行例外
     */
    public Map<Integer, Integer> getCommentCntMap(
            ArrayList<UserSearchModel> belongList, int usrKbn, int pub,
            UDate from, UDate to, String mod
            ) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Map<Integer, Integer> ret = new HashMap<>();
        con = getCon();
        try {
            // SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NTP_DATA.NIP_SID as NIP_SID,");
            sql.addSql("   count(NTP_COMMENT.NPC_SID) CNT");
            sql.addSql(" from");
            sql.addSql("   NTP_COMMENT,");
            sql.addSql("   NTP_DATA");
            sql.addSql(" where ");
            __writeWhereSelectNtp(sql, belongList, usrKbn,
                    pub, from, to, mod);
            sql.addSql(" and NTP_COMMENT.NIP_SID = NTP_DATA.NIP_SID");
            sql.addSql(" group by NTP_DATA.NIP_SID");


            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.put(rs.getInt("NIP_SID"), rs.getInt("CNT"));
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
     * <p>
     * 日報のいいね数を取得する
     * 日報SIDをキーとするマップ型
     * @param nipSidList 日報SIDリスト
     * @return Map
     * @throws SQLException
     *             SQL実行例外
     */
    public Map<Integer, Integer> getGoodCntMap(
            List<Integer> nipSidList) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Map<Integer, Integer> ret = new HashMap<>();
        con = getCon();
        try {
            // SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NTP_GOOD.NTP_SID as NIP_SID,");
            sql.addSql("   count(NTP_GOOD.USR_SID) CNT");
            sql.addSql(" from");
            sql.addSql("   NTP_GOOD");
            sql.addSql(" where ");
            sql.addSql(" NTP_GOOD.NTP_SID in (");
            if (nipSidList.size() < 1) {
                sql.addSql("   -1");
            }
            for (int i = 0; i < nipSidList.size(); i++) {
                if (i == 0) {
                    sql.addSql("   ?");
                } else {
                    sql.addSql("   ,?");
                }
                sql.addIntValue(nipSidList.get(i));
            }
            sql.addSql(" )");
            sql.addSql(" group by NTP_GOOD.NTP_SID");


            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.put(rs.getInt("NIP_SID"), rs.getInt("CNT"));
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
     * <p>
     * ユーザSID、ユーザ区分、公開区分、日付範囲を指定し日報の確認情報を取得する
     * 日報SIDをキーとするマップ型
     * @param belongList ユーザ検索リスト
     * @param usrKbn ユーザ区分
     * @param pub 公開区分 ※-1を指定すると条件から除外されます
     * @param from 日付from
     * @param to 日付to
     * @param mod 取得モード (週間:"1" 月間:"2" 日間:"3")
     * @return ArrayList in NtpDataModel
     * @throws SQLException
     *             SQL実行例外
     */
    public Map<Integer, Integer> getGoodCntMap(
            ArrayList<UserSearchModel> belongList, int usrKbn, int pub,
            UDate from, UDate to, String mod
            ) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Map<Integer, Integer> ret = new HashMap<>();
        con = getCon();
        try {
            // SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NTP_DATA.NIP_SID as NIP_SID,");
            sql.addSql("   count(NTP_GOOD.USR_SID) CNT");
            sql.addSql(" from");
            sql.addSql("   NTP_GOOD,");
            sql.addSql("   NTP_DATA");
            sql.addSql(" where ");
            __writeWhereSelectNtp(sql, belongList,
                    usrKbn, pub, from, to, mod);
            sql.addSql(" and NTP_GOOD.NTP_SID = NTP_DATA.NIP_SID");
            sql.addSql(" group by NTP_DATA.NIP_SID");


            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.put(rs.getInt("NIP_SID"), rs.getInt("CNT"));
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
     * <p>
     * ユーザSID、ユーザ区分、公開区分、日付範囲を指定し日報の確認情報を取得する
     * 日報SIDをキーとするマップ型
     * @param belongList ユーザ検索リスト
     * @param usrSid セッションユーザSID
     * @param usrKbn ユーザ区分
     * @param pub 公開区分 ※-1を指定すると条件から除外されます
     * @param from 日付from
     * @param to 日付to
     * @param mod 取得モード (週間:"1" 月間:"2" 日間:"3")
     * @return ArrayList in NtpDataModel
     * @throws SQLException
     *             SQL実行例外
     */
    public Map<Integer, Integer> getCheckCntMap(
            ArrayList<UserSearchModel> belongList, int usrSid, int usrKbn, int pub,
            UDate from, UDate to, String mod
            ) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Map<Integer, Integer> ret = new HashMap<>();
        con = getCon();
        try {
            // SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NTP_DATA.NIP_SID as NIP_SID,");
            sql.addSql("   count(NTP_CHECK.USR_SID) CNT");
            sql.addSql(" from");
            sql.addSql("   NTP_CHECK,");
            sql.addSql("   NTP_DATA");
            sql.addSql(" where ");
            __writeWhereSelectNtp(sql, belongList, usrKbn,
                    pub, from, to, mod);
            sql.addSql(" and NTP_CHECK.NTP_SID = NTP_DATA.NIP_SID");
            sql.addSql(" and NTP_CHECK.USR_SID = ?");
            sql.addIntValue(usrSid);
            sql.addSql(" group by NTP_DATA.NIP_SID");


            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.put(rs.getInt("NIP_SID"), rs.getInt("CNT"));
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
     * <p>
     * ユーザSID、ユーザ区分、公開区分、日付範囲を指定し日報の確認情報を取得する
     * 日報SIDをキーとするマップ型
     * @param nipSidList 日報SIDリスト
     * @param usrSid セッションユーザSID
     * @return Map
     * @throws SQLException
     *             SQL実行例外
     */
    public Map<Integer, Integer> getCheckCntMap(
            List<Integer> nipSidList, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Map<Integer, Integer> ret = new HashMap<>();
        con = getCon();
        try {
            // SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NTP_CHECK.NTP_SID as NIP_SID,");
            sql.addSql("   count(NTP_CHECK.USR_SID) CNT");
            sql.addSql(" from");
            sql.addSql("   NTP_CHECK");
            sql.addSql(" where ");
            sql.addSql(" NTP_CHECK.NTP_SID in (");
            if (nipSidList.size() < 1) {
                sql.addSql("   -1");
            }
            for (int i = 0; i < nipSidList.size(); i++) {
                if (i == 0) {
                    sql.addSql("   ?");
                } else {
                    sql.addSql("   ,?");
                }
                sql.addIntValue(nipSidList.get(i));
            }
            sql.addSql(" )");
            sql.addSql(" and NTP_CHECK.USR_SID = ?");
            sql.addIntValue(usrSid);
            sql.addSql(" group by NTP_CHECK.NTP_SID");


            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.put(rs.getInt("NIP_SID"), rs.getInt("CNT"));
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
     * <p>
     * ユーザSID、ユーザ区分、公開区分、日付範囲を指定し日報情報を取得する
     *
     * @param usrSid ユーザSID
     * @param usrKbn ユーザ区分
     * @param pub 公開区分 ※-1を指定すると条件から除外されます
     * @param from 日付from
     * @param to 日付to
     * @param mod 取得モード (週間:"1" 月間:"2" 日間:"3")
     * @param napMdl 案件権限チェックモデル
     * @return ArrayList in NtpDataModel
     * @throws SQLException
     *             SQL実行例外
     */
    public ArrayList<NtpDataModel> select(int usrSid, int usrKbn, int pub,
            UDate from, UDate to, String mod, AnkenPermitCheckModel napMdl
            ) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<NtpDataModel> ret = new ArrayList<NtpDataModel>();
        con = getCon();
        //管理者ではなく、日報の対象ユーザとセッションユーザが異なる場合に案件の権限チェックが必要
        int sessionUsrSid = napMdl.getSessionUsrSid();
        boolean needNapCheck = (!napMdl.isAdmin() && usrSid != sessionUsrSid);
        try {
            // SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NTP_DATA.NIP_SID as NIP_SID,");
            sql.addSql("   NTP_DATA.USR_SID as USR_SID,");
            sql.addSql("   NTP_DATA.NIP_DATE as NIP_DATE,");
            sql.addSql("   NTP_DATA.NIP_FR_TIME as NIP_FR_TIME,");
            sql.addSql("   NTP_DATA.NIP_TO_TIME as NIP_TO_TIME,");
            sql.addSql("   NTP_DATA.NIP_KADO_HH as NIP_KADO_HH,");
            sql.addSql("   NTP_DATA.NIP_KADO_MM as NIP_KADO_MM,");
            sql.addSql("   NTP_DATA.NIP_MGY_SID as NIP_MGY_SID,");
            sql.addSql("   NTP_DATA.NAN_SID as NAN_SID,");
            if (needNapCheck) {
                sql.addSql("    NTP_ANKEN.NAN_PERMIT_VIEW as NAN_PERMIT_VIEW,");
                sql.addSql("    NAN_MEM_TABLE.NAN_MEM as NAN_MEM,");
                sql.addSql("    NAP_USR_TABLE.NAP_USR as NAP_USR,");
                sql.addSql("    NAP_GRP_TABLE.NAP_GRP as NAP_GRP,");
            }
            sql.addSql("   NTP_DATA.ACO_SID as ACO_SID,");
            sql.addSql("   NTP_DATA.ABA_SID as ABA_SID,");
            sql.addSql("   NTP_DATA.NIP_TITLE as NIP_TITLE,");
            sql.addSql("   NTP_DATA.NIP_TITLE_CLO as NIP_TITLE_CLO,");
            sql.addSql("   NTP_DATA.MPR_SID as MPR_SID,");
            sql.addSql("   NTP_DATA.MKB_SID as MKB_SID,");
            sql.addSql("   NTP_DATA.MKH_SID as MKH_SID,");
            sql.addSql("   NTP_DATA.NIP_TIEUP_SID as NIP_TIEUP_SID,");
            sql.addSql("   NTP_DATA.NIP_KEIZOKU as NIP_KEIZOKU,");
            sql.addSql("   NTP_DATA.NIP_ACTEND as NIP_ACTEND,");
            sql.addSql("   NTP_DATA.NIP_DETAIL as NIP_DETAIL,");
            sql.addSql("   NTP_DATA.NIP_ACTION_DATE as NIP_ACTION_DATE,");
            sql.addSql("   NTP_DATA.NIP_ACTION as NIP_ACTION,");
            sql.addSql("   NTP_DATA.NIP_ACTDATE_KBN as NIP_ACTDATE_KBN,");
            sql.addSql("   NTP_DATA.NIP_ASSIGN as NIP_ASSIGN,");
            sql.addSql("   NTP_DATA.NIP_KINGAKU as NIP_KINGAKU,");
            sql.addSql("   NTP_DATA.NIP_MIKOMI as NIP_MIKOMI,");
            sql.addSql("   NTP_DATA.NIP_SYOKAN as NIP_SYOKAN,");
            sql.addSql("   NTP_DATA.NIP_PUBLIC as NIP_PUBLIC,");
            sql.addSql("   NTP_DATA.NIP_EDIT as NIP_EDIT,");
            sql.addSql("   NTP_DATA.NEX_SID as NEX_SID,");
            sql.addSql("   NTP_DATA.NIP_AUID as NIP_AUID,");
            sql.addSql("   NTP_DATA.NIP_ADATE as NIP_ADATE,");
            sql.addSql("   NTP_DATA.NIP_EUID as NIP_EUID,");
            sql.addSql("   NTP_DATA.NIP_EDATE as NIP_EDATE");
            sql.addSql(" from ");
            sql.addSql("   NTP_DATA");
            if (needNapCheck) {
                sql.addSql("   left join  ");
                sql.addSql("    NTP_ANKEN ");
                sql.addSql(" ON NTP_DATA.NAN_SID = NTP_ANKEN.NAN_SID ");
                sql.addSql("   left join  ");
                sql.addSql("   ( ");
                sql.addSql("     select  ");
                sql.addSql("       NAN_SID, ");
                sql.addSql("       1 as NAN_MEM");
                sql.addSql("     from NTP_AN_MEMBER");
                sql.addSql("       where USR_SID = ?");
                sql.addIntValue(sessionUsrSid);
                sql.addSql("     ) NAN_MEM_TABLE");
                sql.addSql(" ON NTP_DATA.NAN_SID = NAN_MEM_TABLE.NAN_SID ");
                sql.addSql("   left join  ");
                sql.addSql("   ( ");
                sql.addSql("     select  ");
                sql.addSql("       NAN_SID, ");
                sql.addSql("       1 as NAP_USR");
                sql.addSql("     from NTP_ANKEN_PERMIT");
                sql.addSql("       where USR_SID = ?");
                sql.addIntValue(sessionUsrSid);
                sql.addSql("     ) NAP_USR_TABLE");
                sql.addSql(" ON NTP_DATA.NAN_SID = NAP_USR_TABLE.NAN_SID ");
                sql.addSql("   left join  ");
                sql.addSql("   ( ");
                sql.addSql("     select  ");
                sql.addSql("       NAN_SID, ");
                sql.addSql("       1 as NAP_GRP");
                sql.addSql("     from NTP_ANKEN_PERMIT");
                sql.addSql("       where GRP_SID in (");
                sql.addSql("             select GRP_SID from CMN_BELONGM");
                sql.addSql("             where CMN_BELONGM.USR_SID = ?");
                sql.addIntValue(sessionUsrSid);
                sql.addSql("       )");
                sql.addSql("     ) NAP_GRP_TABLE");
                sql.addSql(" ON NTP_DATA.NAN_SID = NAP_GRP_TABLE.NAN_SID ");
            }
            sql.addSql(" where ");
            sql.addSql("   NTP_DATA.USR_SID = ?");
            sql.addIntValue(usrSid);

            if (mod.equals(GSConstNippou.DSP_MOD_WEEK)) {

                String fromDateStr = from.getDateStringForSql();
                String toDateStr = to.getDateStringForSql();

                sql.addSql(" and");
                sql.addSql("  (");
                sql.addSql("   (");
                sql.addSql("     NTP_DATA.NIP_DATE");
                sql.addSql("     between cast('" + fromDateStr + "' as DATE)");
                sql.addSql("     and cast('" + toDateStr + "' as DATE)");
                sql.addSql("   )");
                sql.addSql("  )");
            }

            if (mod.equals(GSConstNippou.DSP_MOD_MONTH)) {

                UDate frYmd = new UDate();
                frYmd.setDate(from.getYear(), from.getMonth(), 1);
                UDate toYmd = new UDate();
                toYmd.setDate(to.getYear(), to.getMonth(), to
                        .getMaxDayOfMonth());
                String fromDateStr = frYmd.getDateStringForSql();
                String toDateStr = to.getDateStringForSql();

                sql.addSql(" and");
                sql.addSql("  (");
                sql.addSql("   (");
                sql.addSql("     NTP_DATA.NIP_DATE");
                sql.addSql("     between cast('" + fromDateStr + "' as DATE)");
                sql.addSql("     and cast('" + toDateStr + "' as DATE)");
                sql.addSql("   )");
                sql.addSql("  )");
            }

            if (mod.equals(GSConstNippou.DSP_MOD_DAY)) {
                String fromDateTmp = from.toSQLTimestamp().toString();

                sql.addSql(" and");
                sql.addSql("  NTP_DATA.NIP_DATE = ?");
                sql.addStrValue(fromDateTmp);

            }

            sql.addSql(" order by ");
            sql.addSql("   NTP_DATA.USR_SID,");
            sql.addSql("   NTP_DATA.NIP_FR_TIME,");
            sql.addSql("   NTP_DATA.NIP_SID");

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            GsMessage gsMsg = new GsMessage(napMdl.getReqMdl());
            while (rs.next()) {
                NtpDataModel data = __getNtpDataFromRs(rs);
                if (needNapCheck) {
                    boolean viewable = napMdl.hanteiViewable(
                            napMdl.isAdmin(),
                            rs.getInt("NAN_PERMIT_VIEW"),
                            (rs.getInt("NAN_MEM") == 1),
                            (rs.getInt("NAP_USR") == 1),
                            (rs.getInt("NAP_GRP") == 1));
                    data.setAnkenViewable(viewable);
                    data.resetNoPermitAnkenView(gsMsg);
                }
                ret.add(data);
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
     * <p>
     * 指定ユーザの指定期間の次のアクションを取得する
     * @param usrSidList ユーザSID
     * @param from 日付from
     * @param to 日付to
     * @return ArrayList in NtpDataModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<NtpDataModel> selectNextAction(List<Integer> usrSidList,
            UDate from, UDate to) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<NtpDataModel> ret = new ArrayList<NtpDataModel>();
        con = getCon();
        if (usrSidList == null || usrSidList.size() == 0) {
            return ret;
        }
        try {
            // SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NIP_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   NIP_DATE,");
            sql.addSql("   NIP_FR_TIME,");
            sql.addSql("   NIP_TO_TIME,");
            sql.addSql("   NIP_KADO_HH,");
            sql.addSql("   NIP_KADO_MM,");
            sql.addSql("   NIP_MGY_SID,");
            sql.addSql("   NAN_SID,");
            sql.addSql("   ACO_SID,");
            sql.addSql("   ABA_SID,");
            sql.addSql("   NIP_TITLE,");
            sql.addSql("   NIP_TITLE_CLO,");
            sql.addSql("   MPR_SID,");
            sql.addSql("   MKB_SID,");
            sql.addSql("   MKH_SID,");
            sql.addSql("   NIP_TIEUP_SID,");
            sql.addSql("   NIP_KEIZOKU,");
            sql.addSql("   NIP_ACTEND,");
            sql.addSql("   NIP_DETAIL,");
            sql.addSql("   NIP_ACTION_DATE,");
            sql.addSql("   NIP_ACTION,");
            sql.addSql("   NIP_ACTDATE_KBN,");
            sql.addSql("   NIP_ASSIGN,");
            sql.addSql("   NIP_KINGAKU,");
            sql.addSql("   NIP_MIKOMI,");
            sql.addSql("   NIP_SYOKAN,");
            sql.addSql("   NIP_PUBLIC,");
            sql.addSql("   NIP_EDIT,");
            sql.addSql("   NEX_SID,");
            sql.addSql("   NIP_AUID,");
            sql.addSql("   NIP_ADATE,");
            sql.addSql("   NIP_EUID,");
            sql.addSql("   NIP_EDATE");
            sql.addSql(" from ");
            sql.addSql("   NTP_DATA");
            sql.addSql(" where ");
            sql.addSql("   (USR_SID in (");
            for (int i = 0; i < usrSidList.size(); i++) {
                if (i > 0 && i % 100 == 0) {
                    sql.addSql("  ) or USR_SID in (");
                }
                if (i % 100 != 0) {
                    sql.addSql("  ,");
                }
                sql.addSql(String.valueOf(usrSidList.get(i)));
            }
            sql.addSql("  ))");

            UDate frYmd = new UDate();
            frYmd.setDate(from.getYear(), from.getMonth(), from.getIntDay());
            UDate toYmd = new UDate();
            toYmd.setDate(to.getYear(), to.getMonth(), to.getIntDay());
            String fromDateStr = frYmd.getDateStringForSql();
            String toDateStr = to.getDateStringForSql();

            sql.addSql(" and");
            sql.addSql("  (");
            sql.addSql("   (");
            sql.addSql("     NIP_ACTION_DATE");
            sql.addSql("     between cast(? as DATE)");
            sql.addSql("     and cast(? as DATE)");
            sql.addSql("   )");
            sql.addSql("  )");
            sql.addStrValue(fromDateStr);
            sql.addStrValue(toDateStr);
            sql.addSql(" and");
            sql.addSql(" NIP_ACTDATE_KBN=1");
            sql.addSql(" order by ");
            sql.addSql("   USR_SID,");
            sql.addSql("   NIP_FR_TIME,");
            sql.addSql("   NIP_SID");

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getNtpDataFromRs(rs));
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
     * <p>
     * ユーザ(複数)、ユーザ区分、公開区分、日付範囲を指定し日報情報を取得する
     * @param belongList
     *            ユーザ情報リスト
     * @param usrKbn
     *            ユーザ区分
     * @param pub
     *            公開区分 ※-1を指定すると条件から除外されます
     * @param from
     *            日付from
     * @param to
     *            日付to
     * @param mod
     *            取得モード (週間:"1" 月間:"2" 日間:"3")
     * @param napMdl 案件権限チェックモデル
     * @return ArrayList in NtpDataModel
     * @throws SQLException
     *             SQL実行例外
     */
    public ArrayList<NtpDataModel> selectUsers(
            ArrayList<UserSearchModel> belongList, int usrKbn, int pub,
            UDate from, UDate to, String mod,
            AnkenPermitCheckModel napMdl) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<NtpDataModel> ret = new ArrayList<NtpDataModel>();
        con = getCon();
        //管理者ではなく、日報の対象ユーザとセッションユーザが異なる場合に案件の権限チェックが必要
        int sessionUsrSid = napMdl.getSessionUsrSid();
        boolean needNapCheck = (!napMdl.isAdmin());

        try {
            // SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NTP_DATA.NIP_SID as NIP_SID,");
            sql.addSql("   NTP_DATA.USR_SID as USR_SID,");
            sql.addSql("   NTP_DATA.NIP_DATE as NIP_DATE,");
            sql.addSql("   NTP_DATA.NIP_FR_TIME as NIP_FR_TIME,");
            sql.addSql("   NTP_DATA.NIP_TO_TIME as NIP_TO_TIME,");
            sql.addSql("   NTP_DATA.NIP_KADO_HH as NIP_KADO_HH,");
            sql.addSql("   NTP_DATA.NIP_KADO_MM as NIP_KADO_MM,");
            sql.addSql("   NTP_DATA.NIP_MGY_SID as NIP_MGY_SID,");
            sql.addSql("   NTP_DATA.NAN_SID as NAN_SID,");
            if (needNapCheck) {
                sql.addSql("    NTP_ANKEN.NAN_PERMIT_VIEW as NAN_PERMIT_VIEW,");
                sql.addSql("    NAN_MEM_TABLE.NAN_MEM as NAN_MEM,");
                sql.addSql("    NAP_USR_TABLE.NAP_USR as NAP_USR,");
                sql.addSql("    NAP_GRP_TABLE.NAP_GRP as NAP_GRP,");
            }
            sql.addSql("   NTP_DATA.ACO_SID as ACO_SID,");
            sql.addSql("   NTP_DATA.ABA_SID as ABA_SID,");
            sql.addSql("   NTP_DATA.NIP_TITLE as NIP_TITLE,");
            sql.addSql("   NTP_DATA.NIP_TITLE_CLO as NIP_TITLE_CLO,");
            sql.addSql("   NTP_DATA.MPR_SID as MPR_SID,");
            sql.addSql("   NTP_DATA.MKB_SID as MKB_SID,");
            sql.addSql("   NTP_DATA.MKH_SID as MKH_SID,");
            sql.addSql("   NTP_DATA.NIP_TIEUP_SID as NIP_TIEUP_SID,");
            sql.addSql("   NTP_DATA.NIP_KEIZOKU as NIP_KEIZOKU,");
            sql.addSql("   NTP_DATA.NIP_ACTEND as NIP_ACTEND,");
            sql.addSql("   NTP_DATA.NIP_DETAIL as NIP_DETAIL,");
            sql.addSql("   NTP_DATA.NIP_ACTION_DATE as NIP_ACTION_DATE,");
            sql.addSql("   NTP_DATA.NIP_ACTION as NIP_ACTION,");
            sql.addSql("   NTP_DATA.NIP_ACTDATE_KBN as NIP_ACTDATE_KBN,");
            sql.addSql("   NTP_DATA.NIP_ASSIGN as NIP_ASSIGN,");
            sql.addSql("   NTP_DATA.NIP_KINGAKU as NIP_KINGAKU,");
            sql.addSql("   NTP_DATA.NIP_MIKOMI as NIP_MIKOMI,");
            sql.addSql("   NTP_DATA.NIP_SYOKAN as NIP_SYOKAN,");
            sql.addSql("   NTP_DATA.NIP_PUBLIC as NIP_PUBLIC,");
            sql.addSql("   NTP_DATA.NIP_EDIT as NIP_EDIT,");
            sql.addSql("   NTP_DATA.NEX_SID as NEX_SID,");
            sql.addSql("   NTP_DATA.NIP_AUID as NIP_AUID,");
            sql.addSql("   NTP_DATA.NIP_ADATE as NIP_ADATE,");
            sql.addSql("   NTP_DATA.NIP_EUID as NIP_EUID,");
            sql.addSql("   NTP_DATA.NIP_EDATE as NIP_EDATE,");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI, ");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI");
            sql.addSql(" from ");
            sql.addSql("   NTP_DATA");
            if (needNapCheck) {
                sql.addSql("   left join  ");
                sql.addSql("    NTP_ANKEN ");
                sql.addSql(" ON NTP_DATA.NAN_SID = NTP_ANKEN.NAN_SID ");
                sql.addSql("   left join  ");
                sql.addSql("   ( ");
                sql.addSql("     select  ");
                sql.addSql("       NAN_SID, ");
                sql.addSql("       1 as NAN_MEM");
                sql.addSql("     from NTP_AN_MEMBER");
                sql.addSql("       where USR_SID = ?");
                sql.addIntValue(sessionUsrSid);
                sql.addSql("     ) NAN_MEM_TABLE");
                sql.addSql(" ON NTP_DATA.NAN_SID = NAN_MEM_TABLE.NAN_SID ");
                sql.addSql("   left join  ");
                sql.addSql("   ( ");
                sql.addSql("     select  ");
                sql.addSql("       NAN_SID, ");
                sql.addSql("       1 as NAP_USR");
                sql.addSql("     from NTP_ANKEN_PERMIT");
                sql.addSql("       where USR_SID = ?");
                sql.addIntValue(sessionUsrSid);
                sql.addSql("     ) NAP_USR_TABLE");
                sql.addSql(" ON NTP_DATA.NAN_SID = NAP_USR_TABLE.NAN_SID ");
                sql.addSql("   left join  ");
                sql.addSql("   ( ");
                sql.addSql("     select  ");
                sql.addSql("       NAN_SID, ");
                sql.addSql("       1 as NAP_GRP");
                sql.addSql("     from NTP_ANKEN_PERMIT");
                sql.addSql("       where GRP_SID in (");
                sql.addSql("             select GRP_SID from CMN_BELONGM");
                sql.addSql("             where CMN_BELONGM.USR_SID = ?");
                sql.addIntValue(sessionUsrSid);
                sql.addSql("       )");
                sql.addSql("     ) NAP_GRP_TABLE");
                sql.addSql(" ON NTP_DATA.NAN_SID = NAP_GRP_TABLE.NAN_SID ");
            }
            sql.addSql("   ,CMN_USRM_INF");
            sql.addSql(" where ");
            sql.addSql("   NTP_DATA.USR_SID = CMN_USRM_INF.USR_SID ");
            sql.addSql(" and");
            __writeWhereSelectNtp(sql, belongList, usrKbn, pub, from, to, mod);
            sql.addSql(" order by ");
            sql.addSql("   NTP_DATA.USR_SID,");
            sql.addSql("   NTP_DATA.NIP_FR_TIME,");
            sql.addSql("   NTP_DATA.NIP_SID");
            /** ******************************************* */

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            GsMessage gsMsg = new GsMessage(napMdl.getReqMdl());

            while (rs.next()) {
                NtpDataModel data = __getNtpDataPlusNameFromRs(rs);
                if (needNapCheck) {
                    boolean isAdmin = (napMdl.isAdmin() || data.getUsrSid() == sessionUsrSid);
                    boolean viewable = napMdl.hanteiViewable(
                            isAdmin,
                            rs.getInt("NAN_PERMIT_VIEW"),
                            (rs.getInt("NAN_MEM") == 1),
                            (rs.getInt("NAP_USR") == 1),
                            (rs.getInt("NAP_GRP") == 1));
                    data.setAnkenViewable(viewable);
                    data.resetNoPermitAnkenView(gsMsg);

                }
                ret.add(data);
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
     * <br>
     * [機 能] SQL(キーワード入力時の検索条件)を作成 <br>
     * [解 説] <br>
     * [備 考]
     * @param bean CirSearchModel
     * @param sql SqlBuffer
     */
    private void __cleateKeyWord(NippouListSearchModel bean, SqlBuffer sql) {

        // キーワード入力時
        List<String> keywordList = bean.getNtpKeyValue();
        if (keywordList != null && keywordList.size() > 0) {

            String keywordJoin = "  and";
            if (bean.getKeyWordkbn() == GSConstNippou.KEY_WORD_KBN_OR) {
                keywordJoin = "   or";
            }

            // 、検索対象の「タイトル」がチェック済みの場合
            if (bean.isTargetTitle()) {
                sql.addSql("  and");
                if (bean.isTargetValue()) {
                    sql.addSql("    (");
                }
                sql.addSql("      (");
                for (int i = 0; i < keywordList.size(); i++) {
                    if (i > 0) {
                        sql.addSql(keywordJoin);
                    }
                    sql.addSql("       NTP.NIP_TITLE like '%"
                            + JDBCUtil.escapeForLikeSearch(keywordList.get(i))
                            + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
                }
                sql.addSql("      )");
            }

            if (bean.isTargetValue()) {
                if (bean.isTargetTitle()) {
                    sql.addSql("      or");
                } else {
                    sql.addSql("      and");
                }
                sql.addSql("      (");
                for (int i = 0; i < keywordList.size(); i++) {
                    if (i > 0) {
                        sql.addSql(keywordJoin);
                    }
                    sql.addSql("       NTP.NIP_DETAIL like '%"
                            + JDBCUtil.escapeForLikeSearch(keywordList.get(i))
                            + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
                }
                sql.addSql("      )");

                if (bean.isTargetTitle()) {
                    sql.addSql("    )");
                }
            }
        }
    }

    /**
     * <p>
     * ユーザSID、ユーザ区分、公開区分、日付範囲を指定しスケジュール情報を取得する
     * @param searchMdl 検索条件
     * @param sessionUsrSid セッションユーザSID
     * @param notAccessUserList 特例アクセスで閲覧不可のユーザ
     * @return int データ件数
     * @throws SQLException SQL実行例外
     */
    public int getCount(NippouListSearchModel searchMdl, int sessionUsrSid,
            List<Integer> notAccessUserList)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();
        SqlBuffer sql = null;
        try {
            // SQL文
            sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(*) CNT");
            sql.addSql(" from ");
            sql.addSql("   NTP_DATA as NTP");
            __createWhereQuery(sql, searchMdl, sessionUsrSid, notAccessUserList);
            pstmt = con.prepareStatement(sql.toSqlString());

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getInt("CNT");
            }
        } catch (SQLException e) {
            log__.debug("SQL=>" + sql.toLogString());
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }
    /**
     *
     * <br>[機  能] 検索用SQL作成
     * <br>[解  説]
     * <br>[備  考]
     * @param searchMdl 検索用モデル
     * @param sessionUsrSid セッションユーザSID
     * @param offset オフセット有無(オフセットしない場合は全てを取得)
     * @param notAccessUserList 特例アクセス閲覧不可リスト
     * @return sqlBuffer
     */
    private SqlBuffer __makeSelectSQL(NippouListSearchModel searchMdl, int sessionUsrSid,
            boolean offset, List<Integer> notAccessUserList) {
        boolean needNapCheck = false;
        AnkenPermitCheckModel napMdl = searchMdl.getNapMdl();
        if (napMdl != null) {
            needNapCheck = !napMdl.isAdmin();
        }

        SqlBuffer sql = new SqlBuffer();
        sql.addSql(" select ");
        sql.addSql("   NTP.NIP_SID,");
        sql.addSql("   NTP.USR_SID,");
        sql.addSql("   NTP.NIP_DATE,");
        sql.addSql("   NTP.NIP_FR_TIME,");
        sql.addSql("   NTP.NIP_TO_TIME,");
        sql.addSql("   NTP.NIP_KADO_HH,");
        sql.addSql("   NTP.NIP_KADO_MM,");
        sql.addSql("   NTP.NIP_MGY_SID,");
        sql.addSql("   NTP.NAN_SID,");
        if (needNapCheck) {
            sql.addSql("    NTP_ANKEN.NAN_PERMIT_VIEW as NAN_PERMIT_VIEW,");
            sql.addSql("    NAN_MEM_TABLE.NAN_MEM as NAN_MEM,");
            sql.addSql("    NAP_USR_TABLE.NAP_USR as NAP_USR,");
            sql.addSql("    NAP_GRP_TABLE.NAP_GRP as NAP_GRP,");
        }
        sql.addSql("   NTP.ACO_SID,");
        sql.addSql("   NTP.ABA_SID,");
        sql.addSql("   NTP.NIP_TITLE,");
        sql.addSql("   NTP.NIP_TITLE_CLO,");
        sql.addSql("   NTP.MPR_SID,");
        sql.addSql("   NTP.MKB_SID,");
        sql.addSql("   NTP.MKH_SID,");
        sql.addSql("   NTP.NIP_TIEUP_SID,");
        sql.addSql("   NTP.NIP_KEIZOKU,");
        sql.addSql("   NTP.NIP_ACTEND,");
        sql.addSql("   NTP.NIP_DETAIL,");
        sql.addSql("   NTP.NIP_ACTION_DATE,");
        sql.addSql("   NTP.NIP_ACTION,");
        sql.addSql("   NTP.NIP_ACTDATE_KBN,");
        sql.addSql("   NTP.NIP_ASSIGN,");
        sql.addSql("   NTP.NIP_KINGAKU,");
        sql.addSql("   NTP.NIP_MIKOMI,");
        sql.addSql("   NTP.NIP_SYOKAN,");
        sql.addSql("   NTP.NIP_PUBLIC,");
        sql.addSql("   NTP.NIP_EDIT,");
        sql.addSql("   NTP.NEX_SID,");
        sql.addSql("   NTP.NIP_AUID,");
        sql.addSql("   NTP.NIP_ADATE,");
        sql.addSql("   NTP.NIP_EUID,");
        sql.addSql("   NTP.NIP_EDATE,");
        sql.addSql("   USRM.USI_SEI_KN || USRM.USI_MEI_KN USR_NAME");
        sql.addSql(" from");
        sql.addSql("   NTP_DATA NTP");
        sql.addSql("     left join CMN_USRM_INF USRM on NTP.USR_SID = USRM.USR_SID");
        if (needNapCheck) {
            sql.addSql("   left join  ");
            sql.addSql("    NTP_ANKEN ");
            sql.addSql(" ON NTP.NAN_SID = NTP_ANKEN.NAN_SID ");
            sql.addSql("   left join  ");
            sql.addSql("   ( ");
            sql.addSql("     select  ");
            sql.addSql("       NAN_SID, ");
            sql.addSql("       1 as NAN_MEM");
            sql.addSql("     from NTP_AN_MEMBER");
            sql.addSql("       where USR_SID = ?");
            sql.addIntValue(sessionUsrSid);
            sql.addSql("     ) NAN_MEM_TABLE");
            sql.addSql(" ON NTP.NAN_SID = NAN_MEM_TABLE.NAN_SID ");
            sql.addSql("   left join  ");
            sql.addSql("   ( ");
            sql.addSql("     select  ");
            sql.addSql("       NAN_SID, ");
            sql.addSql("       1 as NAP_USR");
            sql.addSql("     from NTP_ANKEN_PERMIT");
            sql.addSql("       where USR_SID = ?");
            sql.addIntValue(sessionUsrSid);
            sql.addSql("     ) NAP_USR_TABLE");
            sql.addSql(" ON NTP.NAN_SID = NAP_USR_TABLE.NAN_SID ");
            sql.addSql("   left join  ");
            sql.addSql("   ( ");
            sql.addSql("     select  ");
            sql.addSql("       NAN_SID, ");
            sql.addSql("       1 as NAP_GRP");
            sql.addSql("     from NTP_ANKEN_PERMIT");
            sql.addSql("       where GRP_SID in (");
            sql.addSql("             select GRP_SID from CMN_BELONGM");
            sql.addSql("             where CMN_BELONGM.USR_SID = ?");
            sql.addIntValue(sessionUsrSid);
            sql.addSql("       )");
            sql.addSql("     ) NAP_GRP_TABLE");
            sql.addSql(" ON NTP.NAN_SID = NAP_GRP_TABLE.NAN_SID ");
        }
        __createWhereQuery(sql, searchMdl, sessionUsrSid, notAccessUserList);
        // オーダー
        __createOrderQuery(sql, searchMdl);

        return sql;
    }
    /**
     * <p>
     * ユーザSID、ユーザ区分、公開区分、日付範囲を指定し、日報の情報を取得する
     * @param searchMdl 検索用モデル
     * @param sessionUsrSid セッションユーザSID
     * @param offset オフセット有無(オフセットしない場合は全てを取得)
     * @param notAccessUserList 特例アクセス閲覧不可リスト
     * @return ArrayList in SchDataModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<NtpDataModel> select(
            NippouListSearchModel searchMdl, int sessionUsrSid,
            boolean offset, List<Integer> notAccessUserList) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<NtpDataModel> ret = new ArrayList<NtpDataModel>();
        con = getCon();
        //管理者ではなく、日報の対象ユーザとセッションユーザが異なる場合に案件の権限チェックが必要
        boolean needNapCheck = false;
        AnkenPermitCheckModel napMdl = searchMdl.getNapMdl();
        if (napMdl != null) {
            needNapCheck = !napMdl.isAdmin();
        }


        try {
            // SQL文
            SqlBuffer sql = __makeSelectSQL(searchMdl, sessionUsrSid, offset, notAccessUserList);

            GsMessage gsMsg = new GsMessage(napMdl.getReqMdl());

            if (offset) {
                sql.setPagingValue(searchMdl.getNtpOffset() - 1, searchMdl.getNtpLimit());

                pstmt = con.prepareStatement(sql.toSqlString());
                sql.setParameter(pstmt);
                log__.info(sql.toLogString());
                rs = pstmt.executeQuery();

                while (rs.next()) {
                    NippouSearchModel data = __getNipUsrDataFromRs(rs);
                    if (needNapCheck) {
                        boolean isAdmin = (napMdl.isAdmin() || data.getUsrSid() == sessionUsrSid);
                        boolean viewable = napMdl.hanteiViewable(
                                isAdmin,
                                rs.getInt("NAN_PERMIT_VIEW"),
                                (rs.getInt("NAN_MEM") == 1),
                                (rs.getInt("NAP_USR") == 1),
                                (rs.getInt("NAP_GRP") == 1));
                        data.setAnkenViewable(viewable);
                        data.resetNoPermitAnkenView(gsMsg);
                    }
                    ret.add(data);


                }

            } else {

                pstmt = con.prepareStatement(sql.toSqlString());
                sql.setParameter(pstmt);
                log__.info(sql.toLogString());
                rs = pstmt.executeQuery();

                for (int i = 0; rs.next() && i < searchMdl.getNtpLimit(); i++) {
                    NippouSearchModel data = __getNipUsrDataFromRs(rs);
                    if (needNapCheck) {
                        boolean isAdmin = (napMdl.isAdmin() || data.getUsrSid() == sessionUsrSid);
                        boolean viewable = napMdl.hanteiViewable(
                                isAdmin,
                                rs.getInt("NAN_PERMIT_VIEW"),
                                (rs.getInt("NAN_MEM") == 1),
                                (rs.getInt("NAP_USR") == 1),
                                (rs.getInt("NAP_GRP") == 1));
                        data.setAnkenViewable(viewable);
                        data.resetNoPermitAnkenView(gsMsg);
                    }
                    ret.add(data);
                }
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
     * <br>[機  能] 検索のオーダー句の生成
     * <br>[解  説]
     * <br>[備  考]
     * @param sql 生成対象SQLバッファ
     * @param searchMdl 検索条件モデル
     */
    private void __createOrderQuery(SqlBuffer sql, NippouListSearchModel searchMdl) {
        ENUM_SORT sort = searchMdl.getNtpSortKey();
        if (sort == null) {
            return;
        }
        if (sort.getSortKbn() == NippouListSearchModel.SORTKBN_NIPPO_TIMELINE) {
            if (sort == ENUM_SORT.DATE_DESC_EDATE_ASC) {
                //登録日時▲更新日時▼
                sql.addSql(" order by NIP_DATE DESC, NIP_EDATE ASC");
            } else if (sort == ENUM_SORT.DATE_ASC_EDATE_DESC) {
                //登録日時▼更新日時▲
                sql.addSql(" order by NIP_DATE ASC, NIP_EDATE DESC");
            } else if (sort == ENUM_SORT.DATE_ASC_EDATE_ASC) {
                //登録日時▼更新日時▼
                sql.addSql(" order by NIP_DATE ASC, NIP_EDATE ASC");
            } else {
                //登録日時▲更新日時▲
                sql.addSql(" order by NIP_DATE DESC, NIP_EDATE DESC");
            }
        } else if (sort.getSortKbn() == NippouListSearchModel.SORTKBN_NIPPO_SEARCH) {
            String orderStr = "";
            // オーダー
            if (searchMdl.getNtpOrder() == GSConstNippou.ORDER_KEY_ASC) {
                orderStr = "  asc";
            } else if (searchMdl.getNtpOrder() == GSConstNippou.ORDER_KEY_DESC) {
                orderStr = "  desc";
            }

            sql.addSql(" order by ");
            switch (searchMdl.getNtpSortKey()) {
            case SORT_KEY_NAME:
                sql.addSql("   USR_NAME " + orderStr);
                break;
            case SORT_KEY_FRDATE:
                sql.addSql("   NTP.NIP_DATE " + orderStr);
                break;
            case SORT_KEY_TITLE:
                sql.addSql("   NTP.NIP_TITLE " + orderStr);
                break;
            default:
                break;
            }

            if (searchMdl.getNtpSortKey2() != null) {
                // ソート第2条件
                String orderStr2 = "";
                // オーダー
                if (searchMdl.getNtpOrder2() == GSConstNippou.ORDER_KEY_ASC) {
                    orderStr2 = "  asc";
                } else if (searchMdl.getNtpOrder2() == GSConstNippou.ORDER_KEY_DESC) {
                    orderStr2 = "  desc";
                }

                switch (searchMdl.getNtpSortKey2()) {
                case SORT_KEY_NAME:
                    sql.addSql("   ,USR_NAME " + orderStr2);
                    break;
                case SORT_KEY_FRDATE:
                    sql.addSql("   ,NTP.NIP_DATE " + orderStr2);
                    break;
                case SORT_KEY_TITLE:
                    sql.addSql("   ,NTP.NIP_TITLE " + orderStr2);
                    break;
                default:
                    break;
                }
            }
            sql.addSql("   ,NTP.NIP_SID");
        } else {
            sql.addSql("   NTP.USR_SID,");
            sql.addSql("   NTP.NIP_FR_TIME,");
            sql.addSql("   NTP.NIP_SID");
        }
    }
    /**
     *
     * <br>[機  能] 日報検索 のwhere句 を生成する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql 生成対象SQLバッファ
     * @param searchMdl 検索条件モデル
     * @param sessionUsrSid セッションユーザSID
     * @param notAccessUserList 特例アクセス閲覧不可リスト
     */
    private void __createWhereQuery(SqlBuffer sql, NippouListSearchModel searchMdl,
            int sessionUsrSid,
            List<Integer> notAccessUserList) {
        boolean needAnd = false;
        if (searchMdl.getUsrSid() != -1) {
            sql.addSql(" where ");
            sql.addSql("   NTP.USR_SID = ?");
            sql.addIntValue(searchMdl.getUsrSid());
            needAnd = true;
        } else {
            sql.addSql(" where ");
        }
        //特例のアクセス不可ユーザは取得しない
        if (notAccessUserList != null && notAccessUserList.size() != 0) {
            if (needAnd) {
                sql.addSql("   and");
            }
            needAnd = true;
            if (notAccessUserList.size() == 1) {
                sql.addSql("   NTP.USR_SID <> ?");
                sql.addIntValue(notAccessUserList.get(0));
            } else {
                __createNotInNotAccessUserList("NTP.USR_SID", sql, notAccessUserList);
            }
        }

        if (searchMdl.getNipSid() != null) {
            if (needAnd) {
                sql.addSql("   and");
            }
            needAnd = true;
            sql.addSql("   NTP.NIP_SID = ?");
            sql.addIntValue(searchMdl.getNipSid());
            if (searchMdl.getNtpDateFrom() != null && searchMdl.getNtpDateTo() != null) {
            }
        }
        if (searchMdl.getNtpDateFrom() != null && searchMdl.getNtpDateTo() != null) {
            if (needAnd) {
                sql.addSql("   and");
            }
            /** ********** 時間設定無し (h2用) *********** */
            String startDateFrStr = searchMdl.getNtpDateFrom()
                    .getDateStringForSql();
            String startDateToStr = searchMdl.getNtpDateTo()
                    .getDateStringForSql();

            sql.addSql("   (");
            sql.addSql("     NTP.NIP_DATE");
            sql.addSql("     between cast('" + startDateFrStr + "' as DATE)");
            sql.addSql("     and cast('" + startDateToStr + "' as DATE)");
            sql.addSql("   )");
            needAnd = true;
        }
        if (!needAnd) {
            sql.addSql("   NTP.NTP_SID <> -1");
        }
        if (searchMdl.getUsrSid() == -1
                && searchMdl.getNtpSltGroupSid() != null
                && NtpCommonBiz.getDspGroupSid(searchMdl.getNtpSltGroupSid()) != -1) {
            sql.addSql("   and");
            sql.addSql("   (");
            sql.addSql("   NTP.USR_SID in (");

            if (searchMdl.isMyGrpFlg()) {
                sql.addSql("   select");
                sql.addSql("     CMN_MY_GROUP_MS.MGM_SID");
                sql.addSql("   from");
                sql.addSql("     CMN_MY_GROUP_MS");
                sql.addSql("   where");
                sql.addSql("     CMN_MY_GROUP_MS.MGP_SID = ?");
                sql.addSql("   and");
                sql.addIntValue(NtpCommonBiz.getDspGroupSid(searchMdl
                        .getNtpSltGroupSid()));
                sql.addSql("   (");
                sql.addSql("   CMN_MY_GROUP_MS.USR_SID = ?");
                sql.addIntValue(sessionUsrSid);
                sql.addSql("   or");
                sql.addSql("   CMN_MY_GROUP_MS.MGP_SID in (");
                sql.addSql("     select MGP_SID");
                sql.addSql("     from CMN_MY_GROUP_SHARE");
                sql.addSql("     where ");
                sql.addSql("       MGS_USR_SID = ?");
                sql.addSql("       or (MGS_USR_SID = -1 and MGS_GRP_SID in (");
                sql.addSql("         select GRP_SID from CMN_BELONGM");
                sql.addSql("         where USR_SID=?");
                sql.addSql("         )");
                sql.addSql("       )");
                sql.addSql("     )");
                sql.addIntValue(sessionUsrSid);
                sql.addIntValue(sessionUsrSid);
                sql.addSql("   )");
            } else {
                sql.addSql("   select");
                sql.addSql("     CMN_BELONGM.USR_SID");
                sql.addSql("   from");
                sql.addSql("     CMN_BELONGM");
                sql.addSql("   where");
                sql.addSql("     CMN_BELONGM.GRP_SID = ?");
                sql.addIntValue(Integer.parseInt(searchMdl.getNtpSltGroupSid()));
            }
            sql.addSql("   )");
            sql.addSql("   )");
        }

        //案件
        if (searchMdl.getAnkenSid() > 0) {
            sql.addSql("   and");
            sql.addSql("   NTP.NAN_SID = ?");
            sql.addIntValue(searchMdl.getAnkenSid());
        }

        //活動分類
        if (searchMdl.getKatudouBunrui() > 0) {
            sql.addSql("   and");
            sql.addSql("   NTP.MKB_SID = ?");
            sql.addIntValue(searchMdl.getKatudouBunrui());
        }

        //活動方法
        if (searchMdl.getKatudouHouhou() > 0) {
            sql.addSql("   and");
            sql.addSql("   NTP.MKH_SID = ?");
            sql.addIntValue(searchMdl.getKatudouHouhou());
        }

        //企業コード
        if (searchMdl.getCompanySid() > 0) {
            sql.addSql("   and");
            sql.addSql("   NTP.ACO_SID = ?");
            sql.addIntValue(searchMdl.getCompanySid());

            if (searchMdl.getCompanyBaseSid() > 0) {
                sql.addSql("   and");
                sql.addSql("   NTP.ABA_SID = ?");
                sql.addIntValue(searchMdl.getCompanyBaseSid());
            }

        }

        // KEYワード
        __cleateKeyWord(searchMdl, sql);

        //タイトルカラーコメント
        if (searchMdl.getBgcolor() != null && searchMdl.getBgcolor().length > 0) {
            String[] bgColorList = searchMdl.getBgcolor();
            for (int i = 0; i < bgColorList.length; i++) {
                if (i == 0) {
                    sql.addSql(" and ");
                    sql.addSql(" ( ");
                } else {
                    sql.addSql(" or ");
                }
                sql.addSql(" NTP.NIP_TITLE_CLO = ?");
                sql.addIntValue(Integer.parseInt(bgColorList[i]));
            }
            sql.addSql(" ) ");
        }

        //見込み度
        if (searchMdl.getMikomido() != null && searchMdl.getMikomido().length > 0) {
            String[] mikomidoList = searchMdl.getMikomido();
            for (int i = 0; i < mikomidoList.length; i++) {
                if (i == 0) {
                    sql.addSql(" and ");
                    sql.addSql(" ( ");
                } else {
                    sql.addSql(" or ");
                }
                sql.addSql(" NTP.NIP_MIKOMI = ?");
                sql.addIntValue(Integer.parseInt(mikomidoList[i]));
            }
            sql.addSql(" ) ");
        }

    }
    /**
     * <p>
    * ユーザSID、ユーザ区分、公開区分、日付範囲を指定し日報情報を取得する
     * @param searchMdl 検索用モデル
     * @param sessionUsrSid セッションユーザSID(実行者)
     * @param rl NtpCsvRecordListenerIppanImpl
     * @param notAccessUserList 特例アクセス閲覧不可ユーザ
     * @return int 明細件数
     * @throws SQLException SQL実行例外
     * @throws CSVException CSV出力時例外
     */
    public int createNippouForCsv(NippouListSearchModel searchMdl,
                                                      int sessionUsrSid,
                                                      NtpCsvRecordListenerIppanImpl rl,
                                                      List<Integer> notAccessUserList
                                                      )
                                                      throws SQLException, CSVException  {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();
        //管理者ではなく、日報の対象ユーザとセッションユーザが異なる場合に案件の権限チェックが必要
        boolean needNapCheck = false;
        AnkenPermitCheckModel napMdl = searchMdl.getNapMdl();
        if (napMdl != null) {
            needNapCheck = !napMdl.isAdmin();
        }

        try {
            // SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NTP.NIP_SID as NIP_SID,");
            sql.addSql("   CMN_USRM.USR_LGID as USR_LGID,");
            sql.addSql("   NTP.USR_SID as USR_SID,");
            sql.addSql("   NTP.NIP_DATE as NIP_DATE,");
            sql.addSql("   NTP.NIP_FR_TIME as NIP_FR_TIME,");
            sql.addSql("   NTP.NIP_TO_TIME as NIP_TO_TIME,");
            sql.addSql("   NTP.NAN_SID as NAN_SID,");
            sql.addSql("   NTP.ACO_SID as ACO_SID,");
            sql.addSql("   NTP.ABA_SID as ABA_SID,");
            sql.addSql("   NTP.NIP_TITLE as NIP_TITLE,");
            sql.addSql("   NTP.NIP_TITLE_CLO as NIP_TITLE_CLO,");
            sql.addSql("   NTP.MKB_SID as MKB_SID,");
            sql.addSql("   NTP.MKH_SID as MKH_SID,");
            sql.addSql("   NTP.NIP_DETAIL as NIP_DETAIL,");
            sql.addSql("   NTP.NIP_MIKOMI as NIP_MIKOMI,");
            sql.addSql("   NTP.NIP_SYOKAN as NIP_SYOKAN,");
            sql.addSql("   NTP.NIP_AUID as NIP_AUID,");
            sql.addSql("   NTP.NIP_ADATE as NIP_ADATE,");
            sql.addSql("   NTP.NIP_EUID as NIP_EUID,");
            sql.addSql("   NTP.NIP_EDATE as NIP_EDATE,");
            sql.addSql("   NTP_ANKEN.NAN_CODE as NAN_CODE,");
            if (needNapCheck) {
                sql.addSql("    NTP_ANKEN.NAN_PERMIT_VIEW as NAN_PERMIT_VIEW,");
                sql.addSql("    NAN_MEM_TABLE.NAN_MEM as NAN_MEM,");
                sql.addSql("    NAP_USR_TABLE.NAP_USR as NAP_USR,");
                sql.addSql("    NAP_GRP_TABLE.NAP_GRP as NAP_GRP,");
            }

            sql.addSql("   ADR_COMPANY.ACO_CODE as ACO_CODE,");
            sql.addSql("   NTP_KTBUNRUI.NKB_CODE as NKB_CODE,");
            sql.addSql("   NTP_KTHOUHOU.NKH_CODE as NKH_CODE,");
            sql.addSql("   CMN_USRM_INF.USI_SEI  || CMN_USRM_INF.USI_MEI USR_NAME,");
            sql.addSql("   CMN_USRM_INF.USI_SEI_KN || CMN_USRM_INF.USI_MEI_KN USR_NAME_KN");
            sql.addSql(" from");
            sql.addSql("   NTP_DATA NTP ");
            sql.addSql("     left join CMN_USRM_INF"
                             + " on NTP.USR_SID = CMN_USRM_INF.USR_SID");
            sql.addSql("     left join NTP_ANKEN"
                             + " on NTP_ANKEN.NAN_SID = NTP.NAN_SID");
            sql.addSql("     left join ADR_COMPANY"
                             + " on ADR_COMPANY.ACO_SID = NTP.ACO_SID");
            sql.addSql("     left join NTP_KTBUNRUI"
                             + " on NTP_KTBUNRUI.NKB_SID = NTP.MKB_SID");
            sql.addSql("     left join NTP_KTHOUHOU"
                             + " on NTP_KTHOUHOU.NKH_SID = NTP.MKH_SID");
            sql.addSql("     left join CMN_USRM on NTP.USR_SID = CMN_USRM.USR_SID");
            if (needNapCheck) {
                sql.addSql("   left join  ");
                sql.addSql("   ( ");
                sql.addSql("     select  ");
                sql.addSql("       NAN_SID, ");
                sql.addSql("       1 as NAN_MEM");
                sql.addSql("     from NTP_AN_MEMBER");
                sql.addSql("       where USR_SID = ?");
                sql.addIntValue(sessionUsrSid);
                sql.addSql("     ) NAN_MEM_TABLE");
                sql.addSql(" ON NTP.NAN_SID = NAN_MEM_TABLE.NAN_SID ");
                sql.addSql("   left join  ");
                sql.addSql("   ( ");
                sql.addSql("     select  ");
                sql.addSql("       NAN_SID, ");
                sql.addSql("       1 as NAP_USR");
                sql.addSql("     from NTP_ANKEN_PERMIT");
                sql.addSql("       where USR_SID = ?");
                sql.addIntValue(sessionUsrSid);
                sql.addSql("     ) NAP_USR_TABLE");
                sql.addSql(" ON NTP.NAN_SID = NAP_USR_TABLE.NAN_SID ");
                sql.addSql("   left join  ");
                sql.addSql("   ( ");
                sql.addSql("     select  ");
                sql.addSql("       NAN_SID, ");
                sql.addSql("       1 as NAP_GRP");
                sql.addSql("     from NTP_ANKEN_PERMIT");
                sql.addSql("       where GRP_SID in (");
                sql.addSql("             select GRP_SID from CMN_BELONGM");
                sql.addSql("             where CMN_BELONGM.USR_SID = ?");
                sql.addIntValue(sessionUsrSid);
                sql.addSql("       )");
                sql.addSql("     ) NAP_GRP_TABLE");
                sql.addSql(" ON NTP.NAN_SID = NAP_GRP_TABLE.NAN_SID ");
            }

            if (searchMdl.getUsrSid() != -1) {
                sql.addSql(" where ");
                sql.addSql("   NTP.USR_SID = ?");
                sql.addIntValue(searchMdl.getUsrSid());
                sql.addSql(" and");
            } else {
                sql.addSql(" where ");
            }

            if (notAccessUserList != null && notAccessUserList.size() != 0) {
                if (notAccessUserList.size() == 1) {
                    sql.addSql("   NTP.USR_SID <> ?");
                    sql.addIntValue(notAccessUserList.get(0));
                } else {
                    __createNotInNotAccessUserList("NTP.USR_SID", sql, notAccessUserList);
                }
                sql.addSql(" and");
            }


            /** ********** 時間設定無し (h2用) *********** */
            String startDateFrStr = searchMdl.getNtpDateFrom()
                    .getDateStringForSql();
            String startDateToStr = searchMdl.getNtpDateTo()
                    .getDateStringForSql();

            sql.addSql("   (");
            sql.addSql("     NIP_DATE");
            sql.addSql("     between cast('" + startDateFrStr + "' as DATE)");
            sql.addSql("     and cast('" + startDateToStr + "' as DATE)");
            sql.addSql("   )");

            if (searchMdl.getUsrSid() == -1
                    && NtpCommonBiz.getDspGroupSid(searchMdl.getNtpSltGroupSid()) != -1) {
                sql.addSql("   and");
                sql.addSql("   (");
                sql.addSql("   NTP.USR_SID in (");

                if (searchMdl.isMyGrpFlg()) {
                    sql.addSql("   select");
                    sql.addSql("     CMN_MY_GROUP_MS.MGM_SID");
                    sql.addSql("   from");
                    sql.addSql("     CMN_MY_GROUP_MS");
                    sql.addSql("   where");
                    sql.addSql("     CMN_MY_GROUP_MS.MGP_SID = ?");
                    sql.addSql("   and");
                    sql.addIntValue(NtpCommonBiz.getDspGroupSid(searchMdl
                            .getNtpSltGroupSid()));
                    sql.addSql("   (");
                    sql.addSql("   CMN_MY_GROUP_MS.USR_SID = ?");
                    sql.addIntValue(sessionUsrSid);
                    sql.addSql("   or");
                    sql.addSql("   CMN_MY_GROUP_MS.MGP_SID in (");
                    sql.addSql("     select MGP_SID");
                    sql.addSql("     from CMN_MY_GROUP_SHARE");
                    sql.addSql("     where ");
                    sql.addSql("       MGS_USR_SID = ?");
                    sql.addSql("       or (MGS_USR_SID = -1 and MGS_GRP_SID in (");
                    sql.addSql("         select GRP_SID from CMN_BELONGM");
                    sql.addSql("         where USR_SID=?");
                    sql.addSql("         )");
                    sql.addSql("       )");
                    sql.addSql("     )");
                    sql.addIntValue(sessionUsrSid);
                    sql.addIntValue(sessionUsrSid);
                    sql.addSql("   )");
                } else {
                    sql.addSql("   select");
                    sql.addSql("     CMN_BELONGM.USR_SID");
                    sql.addSql("   from");
                    sql.addSql("     CMN_BELONGM");
                    sql.addSql("   where");
                    sql.addSql("     CMN_BELONGM.GRP_SID = ?");
                    sql.addIntValue(Integer.parseInt(searchMdl.getNtpSltGroupSid()));
                }
                sql.addSql("   )");
                sql.addSql("   )");
            }


            //案件
            if (searchMdl.getAnkenSid() > 0) {
                sql.addSql("   and");
                sql.addSql("   NTP.NAN_SID = ?");
                sql.addIntValue(searchMdl.getAnkenSid());
            }

            //活動分類
            if (searchMdl.getKatudouBunrui() > 0) {
                sql.addSql("   and");
                sql.addSql("   NTP.MKB_SID = ?");
                sql.addIntValue(searchMdl.getKatudouBunrui());
            }

            //活動方法
            if (searchMdl.getKatudouHouhou() > 0) {
                sql.addSql("   and");
                sql.addSql("   NTP.MKH_SID = ?");
                sql.addIntValue(searchMdl.getKatudouHouhou());
            }

            //企業コード
            if (searchMdl.getCompanySid() > 0) {
                sql.addSql("   and");
                sql.addSql("   NTP.ACO_SID = ?");
                sql.addIntValue(searchMdl.getCompanySid());

                if (searchMdl.getCompanyBaseSid() > 0) {
                    sql.addSql("   and");
                    sql.addSql("   NTP.ABA_SID = ?");
                    sql.addIntValue(searchMdl.getCompanyBaseSid());
                }

            }

            // KEYワード
            __cleateKeyWord(searchMdl, sql);

            //タイトルカラーコメント
            if (searchMdl.getBgcolor() != null && searchMdl.getBgcolor().length > 0) {
                String[] bgColorList = searchMdl.getBgcolor();
                for (int i = 0; i < bgColorList.length; i++) {
                    if (i == 0) {
                        sql.addSql(" and ");
                        sql.addSql(" ( ");
                    } else {
                        sql.addSql(" or ");
                    }
                    sql.addSql(" NTP.NIP_TITLE_CLO = ?");
                    sql.addIntValue(Integer.parseInt(bgColorList[i]));
                }
                sql.addSql(" ) ");
            }

            //見込み度
            if (searchMdl.getMikomido() != null && searchMdl.getMikomido().length > 0) {
                String[] mikomidoList = searchMdl.getMikomido();
                for (int i = 0; i < mikomidoList.length; i++) {
                    if (i == 0) {
                        sql.addSql(" and ");
                        sql.addSql(" ( ");
                    } else {
                        sql.addSql(" or ");
                    }
                    sql.addSql(" NTP.NIP_MIKOMI = ?");
                    sql.addIntValue(Integer.parseInt(mikomidoList[i]));
                }
                sql.addSql(" ) ");
            }


            // オーダー
            __createOrderQuery(sql, searchMdl);

            pstmt = con.prepareStatement(sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret++;
                NippouCsvModel model = __getNippouCsvFromRs(rs);
                if (needNapCheck) {
                    GsMessage gsMsg = new GsMessage(napMdl.getReqMdl());
                    boolean isAdmin = (napMdl.isAdmin() || model.getUsrSid() == sessionUsrSid);
                    boolean viewable = napMdl.hanteiViewable(
                            isAdmin,
                            rs.getInt("NAN_PERMIT_VIEW"),
                            (rs.getInt("NAN_MEM") == 1),
                            (rs.getInt("NAP_USR") == 1),
                            (rs.getInt("NAP_GRP") == 1));
                    model.setAnkenViewable(viewable);
                    if (!viewable) {
                        model.resetNoPermitAnkenView(gsMsg);
                    }
                }

                //NtpDataModelからNippouCsvModelを作成し、NippouCsvRecordListenerImplにセットする。
                rl.setRecord(model);
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
     * <p>
     * 日報SIDから同日に登録された日報情報の件数を取得する
     * @param nipSid 日報SID
     * @param usrSid ユーザSID
     * @return NippouSearchModel
     * @throws SQLException
     *             SQL実行例外
     */
    public int getNippouDataCount(int nipSid, int usrSid)
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
            sql.addSql("   count(NTP_DATA_VAL.NIP_SID)       as CNT");
            sql.addSql(" from");
            sql.addSql("   NTP_DATA as NTP_DATA_VAL,");
            sql.addSql("   (select");
            sql.addSql("      NTP_DATA_IN.NIP_DATE");
            sql.addSql("    from");
            sql.addSql("      NTP_DATA as NTP_DATA_IN");
            sql.addSql("    where");
            sql.addSql("      NTP_DATA_IN.NIP_SID = ?");
            sql.addSql("     )  as NTP_DATA_BASE");
            sql.addSql(" where");
            sql.addSql("   NTP_DATA_VAL.NIP_DATE = NTP_DATA_BASE.NIP_DATE");
            sql.addSql(" and");
            sql.addSql("   NTP_DATA_VAL.USR_SID = ?;");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nipSid);
            sql.addIntValue(usrSid);
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
     * <p>
     * 日報SIDから同日に登録された日報情報＋ユーザ情報を取得する
     * <br>[備  考] 非公開の案件について非公開かのフラグをつけて返すが、日報の秘匿処理は行わない
     * @param nipSid 日報SID
     * @param usrSid ユーザSID
     * @param napMdl 案件権限チェックモデル
     * @return NippouSearchModel
     * @throws SQLException
     *             SQL実行例外
     */
    public ArrayList<NippouSearchModel> getNippouDataAll(int nipSid,
            int usrSid, AnkenPermitCheckModel napMdl)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<NippouSearchModel> mdlList = new ArrayList<NippouSearchModel>();
        NippouSearchModel ret = null;
        con = getCon();
        boolean needNapCheck = false;
        int sessionUsrSid = usrSid;
        if (napMdl != null) {
            needNapCheck = (!napMdl.isAdmin());
            sessionUsrSid = napMdl.getSessionUsrSid();
        }
        try {
            // SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select");
            sql.addSql("   NTP_DATA_VAL.NIP_SID       as NIP_SID,");
            sql.addSql("   NTP_DATA_VAL.USR_SID       as USR_SID,");
            sql.addSql("   NTP_DATA_VAL.NIP_DATE      as NIP_DATE,");
            sql.addSql("   NTP_DATA_VAL.NIP_FR_TIME   as NIP_FR_TIME,");
            sql.addSql("   NTP_DATA_VAL.NIP_TO_TIME   as NIP_TO_TIME,");
            sql.addSql("   NTP_DATA_VAL.NIP_KADO_HH   as NIP_KADO_HH,");
            sql.addSql("   NTP_DATA_VAL.NIP_KADO_MM   as NIP_KADO_MM,");
            sql.addSql("   NTP_DATA_VAL.NIP_MGY_SID   as NIP_MGY_SID,");
            sql.addSql("   NTP_DATA_VAL.NAN_SID       as NAN_SID,");
            if (needNapCheck) {
                sql.addSql("    NTP_ANKEN.NAN_PERMIT_VIEW as NAN_PERMIT_VIEW,");
                sql.addSql("    NAN_MEM_TABLE.NAN_MEM as NAN_MEM,");
                sql.addSql("    NAP_USR_TABLE.NAP_USR as NAP_USR,");
                sql.addSql("    NAP_GRP_TABLE.NAP_GRP as NAP_GRP,");
            }
            sql.addSql("   NTP_DATA_VAL.ACO_SID       as ACO_SID,");
            sql.addSql("   NTP_DATA_VAL.ABA_SID       as ABA_SID,");
            sql.addSql("   NTP_DATA_VAL.NIP_TITLE     as NIP_TITLE,");
            sql.addSql("   NTP_DATA_VAL.NIP_TITLE_CLO as NIP_TITLE_CLO,");
            sql.addSql("   NTP_DATA_VAL.MPR_SID       as MPR_SID,");
            sql.addSql("   NTP_DATA_VAL.MKB_SID       as MKB_SID,");
            sql.addSql("   NTP_DATA_VAL.MKH_SID       as MKH_SID,");
            sql.addSql("   NTP_DATA_VAL.NIP_TIEUP_SID as NIP_TIEUP_SID,");
            sql.addSql("   NTP_DATA_VAL.NIP_KEIZOKU   as NIP_KEIZOKU,");
            sql.addSql("   NTP_DATA_VAL.NIP_ACTEND    as NIP_ACTEND,");
            sql.addSql("   NTP_DATA_VAL.NIP_DETAIL    as NIP_DETAIL,");
            sql.addSql("   NTP_DATA_VAL.NIP_ACTION_DATE  as NIP_ACTION_DATE,");
            sql.addSql("   NTP_DATA_VAL.NIP_ACTION    as NIP_ACTION,");
            sql.addSql("   NTP_DATA_VAL.NIP_ACTDATE_KBN    as NIP_ACTDATE_KBN,");
            sql.addSql("   NTP_DATA_VAL.NIP_ASSIGN    as NIP_ASSIGN,");
            sql.addSql("   NTP_DATA_VAL.NIP_KINGAKU   as NIP_KINGAKU,");
            sql.addSql("   NTP_DATA_VAL.NIP_MIKOMI    as NIP_MIKOMI,");
            sql.addSql("   NTP_DATA_VAL.NIP_SYOKAN    as NIP_SYOKAN,");
            sql.addSql("   NTP_DATA_VAL.NIP_PUBLIC    as NIP_PUBLIC,");
            sql.addSql("   NTP_DATA_VAL.NIP_EDIT      as NIP_EDIT,");
            sql.addSql("   NTP_DATA_VAL.NEX_SID       as NEX_SID,");
            sql.addSql("   NTP_DATA_VAL.NIP_AUID      as NIP_AUID,");
            sql.addSql("   NTP_DATA_VAL.NIP_ADATE     as NIP_ADATE,");
            sql.addSql("   NTP_DATA_VAL.NIP_EUID      as NIP_EUID,");
            sql.addSql("   NTP_DATA_VAL.NIP_EDATE     as NIP_EDATE");
            sql.addSql(" from");
            sql.addSql("   NTP_DATA as NTP_DATA_VAL");
            if (needNapCheck) {
                sql.addSql("   left join  ");
                sql.addSql("    NTP_ANKEN ");
                sql.addSql(" ON NTP_DATA_VAL.NAN_SID = NTP_ANKEN.NAN_SID ");
                sql.addSql("   left join  ");
                sql.addSql("   ( ");
                sql.addSql("     select  ");
                sql.addSql("       NAN_SID, ");
                sql.addSql("       1 as NAN_MEM");
                sql.addSql("     from NTP_AN_MEMBER");
                sql.addSql("       where USR_SID = ?");
                sql.addIntValue(sessionUsrSid);
                sql.addSql("     ) NAN_MEM_TABLE");
                sql.addSql(" ON NTP_DATA_VAL.NAN_SID = NAN_MEM_TABLE.NAN_SID ");
                sql.addSql("   left join  ");
                sql.addSql("   ( ");
                sql.addSql("     select  ");
                sql.addSql("       NAN_SID, ");
                sql.addSql("       1 as NAP_USR");
                sql.addSql("     from NTP_ANKEN_PERMIT");
                sql.addSql("       where USR_SID = ?");
                sql.addIntValue(sessionUsrSid);
                sql.addSql("     ) NAP_USR_TABLE");
                sql.addSql(" ON NTP_DATA_VAL.NAN_SID = NAP_USR_TABLE.NAN_SID ");
                sql.addSql("   left join  ");
                sql.addSql("   ( ");
                sql.addSql("     select  ");
                sql.addSql("       NAN_SID, ");
                sql.addSql("       1 as NAP_GRP");
                sql.addSql("     from NTP_ANKEN_PERMIT");
                sql.addSql("       where GRP_SID in (");
                sql.addSql("             select GRP_SID from CMN_BELONGM");
                sql.addSql("             where CMN_BELONGM.USR_SID = ?");
                sql.addIntValue(sessionUsrSid);
                sql.addSql("       )");
                sql.addSql("     ) NAP_GRP_TABLE");
                sql.addSql(" ON NTP_DATA_VAL.NAN_SID = NAP_GRP_TABLE.NAN_SID ");
            }
            sql.addSql("   ,(select");
            sql.addSql("      NTP_DATA_IN.NIP_DATE");
            sql.addSql("    from");
            sql.addSql("      NTP_DATA as NTP_DATA_IN");
            sql.addSql("    where");
            sql.addSql("      NTP_DATA_IN.NIP_SID = ?");
            sql.addSql("     )  as NTP_DATA_BASE");
            sql.addSql(" where");
            sql.addSql("   NTP_DATA_VAL.NIP_DATE = NTP_DATA_BASE.NIP_DATE");
            sql.addSql(" and");
            sql.addSql("   NTP_DATA_VAL.USR_SID = ?");
            sql.addSql(" order by ");
            sql.addSql("   NIP_FR_TIME");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nipSid);
            sql.addIntValue(usrSid);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret = __getNipUsrDataFromRs(rs);
                if (needNapCheck) {
                    boolean isAdmin = (napMdl.isAdmin() || ret.getUsrSid() == sessionUsrSid);
                    boolean viewable = napMdl.hanteiViewable(
                            isAdmin,
                            rs.getInt("NAN_PERMIT_VIEW"),
                            (rs.getInt("NAN_MEM") == 1),
                            (rs.getInt("NAP_USR") == 1),
                            (rs.getInt("NAP_GRP") == 1));
                    ret.setAnkenViewable(viewable);
                }
                mdlList.add(ret);
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return mdlList;
    }

    /**
     * <p>
     * 日報SIDから日報情報を取得する
     * @param nipSid 日報SID
     * @return NippouSearchModel
     * @throws SQLException
     *             SQL実行例外
     */
    public NippouSearchModel getNippouDataSingle(int nipSid)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        NippouSearchModel ret = null;
        con = getCon();

        try {
            // SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select");
            sql.addSql("   NTP_DATA_VAL.NIP_SID       as NIP_SID,");
            sql.addSql("   NTP_DATA_VAL.USR_SID       as USR_SID,");
            sql.addSql("   NTP_DATA_VAL.NIP_DATE      as NIP_DATE,");
            sql.addSql("   NTP_DATA_VAL.NIP_FR_TIME   as NIP_FR_TIME,");
            sql.addSql("   NTP_DATA_VAL.NIP_TO_TIME   as NIP_TO_TIME,");
            sql.addSql("   NTP_DATA_VAL.NIP_KADO_HH   as NIP_KADO_HH,");
            sql.addSql("   NTP_DATA_VAL.NIP_KADO_MM   as NIP_KADO_MM,");
            sql.addSql("   NTP_DATA_VAL.NIP_MGY_SID   as NIP_MGY_SID,");
            sql.addSql("   NTP_DATA_VAL.NAN_SID       as NAN_SID,");

            sql.addSql("   NTP_DATA_VAL.ACO_SID       as ACO_SID,");
            sql.addSql("   NTP_DATA_VAL.ABA_SID       as ABA_SID,");
            sql.addSql("   NTP_DATA_VAL.NIP_TITLE     as NIP_TITLE,");
            sql.addSql("   NTP_DATA_VAL.NIP_TITLE_CLO as NIP_TITLE_CLO,");
            sql.addSql("   NTP_DATA_VAL.MPR_SID       as MPR_SID,");
            sql.addSql("   NTP_DATA_VAL.MKB_SID       as MKB_SID,");
            sql.addSql("   NTP_DATA_VAL.MKH_SID       as MKH_SID,");
            sql.addSql("   NTP_DATA_VAL.NIP_TIEUP_SID as NIP_TIEUP_SID,");
            sql.addSql("   NTP_DATA_VAL.NIP_KEIZOKU   as NIP_KEIZOKU,");
            sql.addSql("   NTP_DATA_VAL.NIP_ACTEND    as NIP_ACTEND,");
            sql.addSql("   NTP_DATA_VAL.NIP_DETAIL    as NIP_DETAIL,");
            sql.addSql("   NTP_DATA_VAL.NIP_ACTION_DATE    as NIP_ACTION_DATE,");
            sql.addSql("   NTP_DATA_VAL.NIP_ACTION    as NIP_ACTION,");
            sql.addSql("   NTP_DATA_VAL.NIP_ACTDATE_KBN    as NIP_ACTDATE_KBN,");
            sql.addSql("   NTP_DATA_VAL.NIP_ASSIGN    as NIP_ASSIGN,");
            sql.addSql("   NTP_DATA_VAL.NIP_KINGAKU   as NIP_KINGAKU,");
            sql.addSql("   NTP_DATA_VAL.NIP_MIKOMI    as NIP_MIKOMI,");
            sql.addSql("   NTP_DATA_VAL.NIP_SYOKAN    as NIP_SYOKAN,");
            sql.addSql("   NTP_DATA_VAL.NIP_PUBLIC    as NIP_PUBLIC,");
            sql.addSql("   NTP_DATA_VAL.NIP_EDIT      as NIP_EDIT,");
            sql.addSql("   NTP_DATA_VAL.NEX_SID       as NEX_SID,");
            sql.addSql("   NTP_DATA_VAL.NIP_AUID      as NIP_AUID,");
            sql.addSql("   NTP_DATA_VAL.NIP_ADATE     as NIP_ADATE,");
            sql.addSql("   NTP_DATA_VAL.NIP_EUID      as NIP_EUID,");
            sql.addSql("   NTP_DATA_VAL.NIP_EDATE     as NIP_EDATE");
            sql.addSql(" from");
            sql.addSql("   NTP_DATA as NTP_DATA_VAL");
            sql.addSql(" where");
            sql.addSql("   NTP_DATA_VAL.NIP_SID = ?");
            sql.addSql(" order by ");
            sql.addSql("   NIP_FR_TIME");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nipSid);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = __getNipUsrDataFromRs(rs);
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
     * <p>
     * 現在閲覧している日報より前 or 後の日付の日報SIDを取得する
     * @param nipDate 現在の日報の日付
     * @param usrSid ユーザSID
     * @return NippouSearchModel
     * @throws SQLException
     *             SQL実行例外
     */
    public NippouSearchModel getPrevNextNippouSid(UDate nipDate, int usrSid)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        NippouSearchModel ret = null;
        con = getCon();

        try {
            // SQL文
            SqlBuffer sql = new SqlBuffer();


            sql.addSql(" select");
            sql.addSql("   NTP_DATA_VAL.NIP_SID       as NIP_SID,");
            sql.addSql("   NTP_DATA_VAL.USR_SID       as USR_SID,");
            sql.addSql("   NTP_DATA_VAL.NIP_DATE      as NIP_DATE,");
            sql.addSql("   NTP_DATA_VAL.NIP_FR_TIME   as NIP_FR_TIME,");
            sql.addSql("   NTP_DATA_VAL.NIP_TO_TIME   as NIP_TO_TIME,");
            sql.addSql("   NTP_DATA_VAL.NIP_KADO_HH   as NIP_KADO_HH,");
            sql.addSql("   NTP_DATA_VAL.NIP_KADO_MM   as NIP_KADO_MM,");
            sql.addSql("   NTP_DATA_VAL.NIP_MGY_SID   as NIP_MGY_SID,");
            sql.addSql("   NTP_DATA_VAL.NAN_SID       as NAN_SID,");
            sql.addSql("   NTP_DATA_VAL.ACO_SID       as ACO_SID,");
            sql.addSql("   NTP_DATA_VAL.ABA_SID       as ABA_SID,");
            sql.addSql("   NTP_DATA_VAL.NIP_TITLE     as NIP_TITLE,");
            sql.addSql("   NTP_DATA_VAL.NIP_TITLE_CLO as NIP_TITLE_CLO,");
            sql.addSql("   NTP_DATA_VAL.MPR_SID       as MPR_SID,");
            sql.addSql("   NTP_DATA_VAL.MKB_SID       as MKB_SID,");
            sql.addSql("   NTP_DATA_VAL.MKH_SID       as MKH_SID,");
            sql.addSql("   NTP_DATA_VAL.NIP_TIEUP_SID as NIP_TIEUP_SID,");
            sql.addSql("   NTP_DATA_VAL.NIP_KEIZOKU   as NIP_KEIZOKU,");
            sql.addSql("   NTP_DATA_VAL.NIP_ACTEND    as NIP_ACTEND,");
            sql.addSql("   NTP_DATA_VAL.NIP_DETAIL    as NIP_DETAIL,");
            sql.addSql("   NTP_DATA_VAL.NIP_ACTION_DATE    as NIP_ACTION_DATE,");
            sql.addSql("   NTP_DATA_VAL.NIP_ACTION    as NIP_ACTION,");
            sql.addSql("   NTP_DATA_VAL.NIP_ACTDATE_KBN    as NIP_ACTDATE_KBN,");
            sql.addSql("   NTP_DATA_VAL.NIP_ASSIGN    as NIP_ASSIGN,");
            sql.addSql("   NTP_DATA_VAL.NIP_KINGAKU   as NIP_KINGAKU,");
            sql.addSql("   NTP_DATA_VAL.NIP_MIKOMI    as NIP_MIKOMI,");
            sql.addSql("   NTP_DATA_VAL.NIP_SYOKAN    as NIP_SYOKAN,");
            sql.addSql("   NTP_DATA_VAL.NIP_PUBLIC    as NIP_PUBLIC,");
            sql.addSql("   NTP_DATA_VAL.NIP_EDIT      as NIP_EDIT,");
            sql.addSql("   NTP_DATA_VAL.NEX_SID       as NEX_SID,");
            sql.addSql("   NTP_DATA_VAL.NIP_AUID      as NIP_AUID,");
            sql.addSql("   NTP_DATA_VAL.NIP_ADATE     as NIP_ADATE,");
            sql.addSql("   NTP_DATA_VAL.NIP_EUID      as NIP_EUID,");
            sql.addSql("   NTP_DATA_VAL.NIP_EDATE     as NIP_EDATE");
            sql.addSql(" from");
            sql.addSql("   NTP_DATA as NTP_DATA_VAL");
            sql.addSql(" where");
            sql.addSql("   NTP_DATA_VAL.USR_SID = ?");
            sql.addSql(" and");
            sql.addSql("   NTP_DATA_VAL.NIP_DATE = cast(? as DATE)");
            sql.addSql(" order by ");
            sql.addSql("   NTP_DATA_VAL.NIP_FR_TIME");
            sql.addSql(" limit 1 offset 0");


            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);
            String date = nipDate.getDateStringForSql();
            sql.addStrValue(date);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = __getNipUsrDataFromRs(rs);
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
     * <p>
     * ユーザSIDと登録日付から日報情報＋ユーザ情報を取得する
     * @param searchMdl searchモデル
     * @param sessionUsrSid セッションユーザSID
     * @param sort ソート
     * @return NippouSearchModel
     * @throws SQLException
     *             SQL実行例外
     */
    public ArrayList<NippouSearchModel> getNippouDataAll(
            NippouListSearchModel searchMdl, int sessionUsrSid, int sort) throws SQLException {
        return getNippouDataAll(searchMdl, sessionUsrSid, sort, null, null);
    }

    /**
     * <p>
     * ユーザSIDと登録日付から日報情報＋ユーザ情報を取得する
     * @param searchMdl searchモデル
     * @param sessionUsrSid セッションユーザSID
     * @param sort ソート
     * @param notAccessUserList アクセス不可ユーザリスト
     * @param napMdl 案件権限チェックモデル
     * @return NippouSearchModel
     * @throws SQLException
     *             SQL実行例外
     */
    public ArrayList<NippouSearchModel> getNippouDataAll(
            NippouListSearchModel searchMdl, int sessionUsrSid,
            int sort, List<Integer> notAccessUserList,
            AnkenPermitCheckModel napMdl)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<NippouSearchModel> mdlList = new ArrayList<NippouSearchModel>();
        con = getCon();
        boolean needNapCheck = false;
        GsMessage gsMsg = null;
        if (napMdl != null) {
            needNapCheck = (!napMdl.isAdmin());
            gsMsg = new GsMessage(napMdl.getReqMdl());
        }

        try {
            // SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select");
            sql.addSql("   NTP_DATA.NIP_SID       as NIP_SID,");
            sql.addSql("   NTP_DATA.USR_SID       as USR_SID,");
            sql.addSql("   NTP_DATA.NIP_DATE      as NIP_DATE,");
            sql.addSql("   NTP_DATA.NIP_FR_TIME   as NIP_FR_TIME,");
            sql.addSql("   NTP_DATA.NIP_TO_TIME   as NIP_TO_TIME,");
            sql.addSql("   NTP_DATA.NIP_KADO_HH   as NIP_KADO_HH,");
            sql.addSql("   NTP_DATA.NIP_KADO_MM   as NIP_KADO_MM,");
            sql.addSql("   NTP_DATA.NIP_MGY_SID   as NIP_MGY_SID,");
            sql.addSql("   NTP_DATA.NAN_SID       as NAN_SID,");
            if (needNapCheck) {
                sql.addSql("    NTP_ANKEN.NAN_PERMIT_VIEW as NAN_PERMIT_VIEW,");
                sql.addSql("    NAN_MEM_TABLE.NAN_MEM as NAN_MEM,");
                sql.addSql("    NAP_USR_TABLE.NAP_USR as NAP_USR,");
                sql.addSql("    NAP_GRP_TABLE.NAP_GRP as NAP_GRP,");
            }
            sql.addSql("   NTP_DATA.ACO_SID       as ACO_SID,");
            sql.addSql("   NTP_DATA.ABA_SID       as ABA_SID,");
            sql.addSql("   NTP_DATA.NIP_TITLE     as NIP_TITLE,");
            sql.addSql("   NTP_DATA.NIP_TITLE_CLO as NIP_TITLE_CLO,");
            sql.addSql("   NTP_DATA.MPR_SID       as MPR_SID,");
            sql.addSql("   NTP_DATA.MKB_SID       as MKB_SID,");
            sql.addSql("   NTP_DATA.MKH_SID       as MKH_SID,");
            sql.addSql("   NTP_DATA.NIP_TIEUP_SID as NIP_TIEUP_SID,");
            sql.addSql("   NTP_DATA.NIP_KEIZOKU   as NIP_KEIZOKU,");
            sql.addSql("   NTP_DATA.NIP_ACTEND    as NIP_ACTEND,");
            sql.addSql("   NTP_DATA.NIP_DETAIL    as NIP_DETAIL,");
            sql.addSql("   NTP_DATA.NIP_ACTION_DATE    as NIP_ACTION_DATE,");
            sql.addSql("   NTP_DATA.NIP_ACTION    as NIP_ACTION,");
            sql.addSql("   NTP_DATA.NIP_ACTDATE_KBN    as NIP_ACTDATE_KBN,");
            sql.addSql("   NTP_DATA.NIP_ASSIGN    as NIP_ASSIGN,");
            sql.addSql("   NTP_DATA.NIP_KINGAKU   as NIP_KINGAKU,");
            sql.addSql("   NTP_DATA.NIP_MIKOMI    as NIP_MIKOMI,");
            sql.addSql("   NTP_DATA.NIP_SYOKAN    as NIP_SYOKAN,");
            sql.addSql("   NTP_DATA.NIP_PUBLIC    as NIP_PUBLIC,");
            sql.addSql("   NTP_DATA.NIP_EDIT      as NIP_EDIT,");
            sql.addSql("   NTP_DATA.NEX_SID       as NEX_SID,");
            sql.addSql("   NTP_DATA.NIP_AUID      as NIP_AUID,");
            sql.addSql("   NTP_DATA.NIP_ADATE     as NIP_ADATE,");
            sql.addSql("   NTP_DATA.NIP_EUID      as NIP_EUID,");
            sql.addSql("   NTP_DATA.NIP_EDATE     as NIP_EDATE");
            sql.addSql(" from");
            sql.addSql("   NTP_DATA");
            if (needNapCheck) {
                sql.addSql("   left join  ");
                sql.addSql("    NTP_ANKEN ");
                sql.addSql(" ON NTP_DATA.NAN_SID = NTP_ANKEN.NAN_SID ");
                sql.addSql("   left join  ");
                sql.addSql("   ( ");
                sql.addSql("     select  ");
                sql.addSql("       NAN_SID, ");
                sql.addSql("       1 as NAN_MEM");
                sql.addSql("     from NTP_AN_MEMBER");
                sql.addSql("       where USR_SID = ?");
                sql.addIntValue(sessionUsrSid);
                sql.addSql("     ) NAN_MEM_TABLE");
                sql.addSql(" ON NTP_DATA.NAN_SID = NAN_MEM_TABLE.NAN_SID ");
                sql.addSql("   left join  ");
                sql.addSql("   ( ");
                sql.addSql("     select  ");
                sql.addSql("       NAN_SID, ");
                sql.addSql("       1 as NAP_USR");
                sql.addSql("     from NTP_ANKEN_PERMIT");
                sql.addSql("       where USR_SID = ?");
                sql.addIntValue(sessionUsrSid);
                sql.addSql("     ) NAP_USR_TABLE");
                sql.addSql(" ON NTP_DATA.NAN_SID = NAP_USR_TABLE.NAN_SID ");
                sql.addSql("   left join  ");
                sql.addSql("   ( ");
                sql.addSql("     select  ");
                sql.addSql("       NAN_SID, ");
                sql.addSql("       1 as NAP_GRP");
                sql.addSql("     from NTP_ANKEN_PERMIT");
                sql.addSql("       where GRP_SID in (");
                sql.addSql("             select GRP_SID from CMN_BELONGM");
                sql.addSql("             where CMN_BELONGM.USR_SID = ?");
                sql.addIntValue(sessionUsrSid);
                sql.addSql("       )");
                sql.addSql("     ) NAP_GRP_TABLE");
                sql.addSql(" ON NTP_DATA.NAN_SID = NAP_GRP_TABLE.NAN_SID ");
            }

            sql.addSql(" where ");

            //特例のアクセス不可ユーザは取得しない
            if (notAccessUserList != null && notAccessUserList.size() != 0) {
                for (int notAccessUser:notAccessUserList) {
                    sql.addSql("   NTP_DATA.USR_SID != ?");
                    sql.addIntValue(notAccessUser);
                    sql.addSql("   and");
                }
            }

            if (searchMdl.getUsrSid() != -1) {
                sql.addSql("   NTP_DATA.USR_SID = ?");
                    sql.addIntValue(searchMdl.getUsrSid());
            } else {
                sql.addSql("   (");
                sql.addSql("   NTP_DATA.USR_SID in (");

                if (searchMdl.isMyGrpFlg()) {
                    sql.addSql("   select");
                    sql.addSql("     CMN_MY_GROUP_MS.MGM_SID");
                    sql.addSql("   from");
                    sql.addSql("     CMN_MY_GROUP_MS");
                    sql.addSql("   where");
                    sql.addSql("     CMN_MY_GROUP_MS.MGP_SID = ?");
                    sql.addSql("   and");
                    sql.addIntValue(Integer.valueOf(searchMdl.getNtpSltGroupSid()));
                    sql.addSql("   (");
                    sql.addSql("   CMN_MY_GROUP_MS.USR_SID = ?");
                    sql.addIntValue(sessionUsrSid);
                    sql.addSql("   or");
                    sql.addSql("   CMN_MY_GROUP_MS.MGP_SID in (");
                    sql.addSql("     select MGP_SID");
                    sql.addSql("     from CMN_MY_GROUP_SHARE");
                    sql.addSql("     where ");
                    sql.addSql("       MGS_USR_SID = ?");
                    sql.addSql("       or (MGS_USR_SID = -1 and MGS_GRP_SID in (");
                    sql.addSql("         select GRP_SID from CMN_BELONGM");
                    sql.addSql("         where USR_SID=?");
                    sql.addSql("         )");
                    sql.addSql("       )");
                    sql.addSql("     )");
                    sql.addIntValue(sessionUsrSid);
                    sql.addIntValue(sessionUsrSid);
                    sql.addSql("   )");
                } else {
                    sql.addSql("   select");
                    sql.addSql("     CMN_BELONGM.USR_SID");
                    sql.addSql("   from");
                    sql.addSql("     CMN_BELONGM");
                    sql.addSql("   where");
                    sql.addSql("     CMN_BELONGM.GRP_SID = ?");
                    sql.addIntValue(Integer.valueOf(searchMdl.getNtpSltGroupSid()));
                }
                sql.addSql("   )");
                sql.addSql("   )");
            }

            if (sort == GSConstNippou.DATE_DESC_EDATE_ASC) {
                //登録日時▲更新日時▼
                sql.addSql(" order by NTP_DATA.NIP_DATE DESC, NTP_DATA.NIP_EDATE ASC");
            } else if (sort == GSConstNippou.DATE_ASC_EDATE_DESC) {
                //登録日時▼更新日時▲
                sql.addSql(" order by NTP_DATA.NIP_DATE ASC, NTP_DATA.NIP_EDATE DESC");
            } else if (sort == GSConstNippou.DATE_ASC_EDATE_ASC) {
                //登録日時▼更新日時▼
                sql.addSql(" order by NTP_DATA.NIP_DATE ASC, NTP_DATA.NIP_EDATE ASC");
            } else {
                //登録日時▲更新日時▲
                sql.addSql(" order by NTP_DATA.NIP_DATE DESC, NTP_DATA.NIP_EDATE DESC");
            }

            if (searchMdl.getNtpOffset() > 1) {
                sql.setPagingValue(
                        (searchMdl.getNtpOffset() - 1) * searchMdl.getNtpLimit(),
                        searchMdl.getNtpLimit());
            } else {
                sql.setPagingValue(
                        0,
                        searchMdl.getNtpLimit());

            }
            pstmt = con.prepareStatement(sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();


            for (int i = 0; rs.next() && i < searchMdl.getNtpLimit(); i++) {
                NippouSearchModel data = __getNipUsrDataFromRs(rs);
                if (needNapCheck) {
                    boolean isAdmin = (napMdl.isAdmin() || data.getUsrSid() == sessionUsrSid);
                    boolean viewable = napMdl.hanteiViewable(
                            isAdmin,
                            rs.getInt("NAN_PERMIT_VIEW"),
                            (rs.getInt("NAN_MEM") == 1),
                            (rs.getInt("NAP_USR") == 1),
                            (rs.getInt("NAP_GRP") == 1));
                    data.setAnkenViewable(viewable);
                    data.resetNoPermitAnkenView(gsMsg);
                }
                mdlList.add(data);

            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return mdlList;
    }
    /**
     *
     * <br>[機  能] 特例アクセスで制限されるユーザ除外するnotin を出力する
     * <br>[解  説]
     * <br>[備  考]
     * @param fieldName フィールド名
     * @param sql 対象SQLBuffer
     * @param notAccessUserList 制限ユーザSIDリスト
     */
    private void __createNotInNotAccessUserList(
            String fieldName,
            SqlBuffer sql,
            List<Integer> notAccessUserList) {
        int index = 0;
        for (int i = 0; i * 500 < notAccessUserList.size(); i++) {
            if (i != 0) {
                sql.addSql("    and");
            }
            sql.addSql("    " + fieldName);
            sql.addSql("    not in (");
            for (; index < notAccessUserList.size() &&  index < (i + 1) * 500; index++) {
                if (i * 500 != index) {
                    sql.addSql("    ,");
                }
                sql.addSql(" " + String.valueOf(notAccessUserList.get(index)));
            }
            sql.addSql(" )");
        }
    }
    /**
     * <p>
     * ユーザSIDと日付から日報情報を取得する
     * @param searchMdl searchモデル
     * @param sessionUsrSid ユーザSID
     * @return NippouSearchModel
     * @throws SQLException
     *             SQL実行例外
     */
    public ArrayList<NippouSearchModel> getNippouDateData(
            NippouListSearchModel searchMdl, int sessionUsrSid)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<NippouSearchModel> mdlList = new ArrayList<NippouSearchModel>();
        NippouSearchModel ret = null;
        con = getCon();
        boolean needNapCheck = false;
        AnkenPermitCheckModel napMdl = searchMdl.getNapMdl();
        GsMessage gsMsg;
        if (napMdl != null) {
            needNapCheck = !napMdl.isAdmin();
            gsMsg = new GsMessage(napMdl.getReqMdl());
        } else {
            gsMsg = new GsMessage();
        }

        try {
            // SQL文
            SqlBuffer sql = __makeSelectSQL(searchMdl, sessionUsrSid, false, null);

            pstmt = con.prepareStatement(sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();



            while (rs.next()) {
                ret = __getNipUsrDataFromRs(rs);
                if (needNapCheck) {
                    boolean isAdmin = (napMdl.isAdmin() || ret.getUsrSid() == sessionUsrSid);
                    boolean viewable = napMdl.hanteiViewable(
                            isAdmin,
                            rs.getInt("NAN_PERMIT_VIEW"),
                            (rs.getInt("NAN_MEM") == 1),
                            (rs.getInt("NAP_USR") == 1),
                            (rs.getInt("NAP_GRP") == 1));

                    ret.setAnkenViewable(viewable);
                    ret.resetNoPermitAnkenView(gsMsg);
                }
                mdlList.add(ret);
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return mdlList;
    }


    /**
     * <p>
     * 日報SIDから日報情報を取得する
     * @param nipSid 日報SID
     * @return NippouSearchModel
     * @throws SQLException
     *             SQL実行例外
     */
    public NippouSearchModel getNippouData(int nipSid)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        NippouSearchModel ret = null;
        con = getCon();

        try {
            // SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select");
            sql.addSql("   NTP_DATA_VAL.NIP_SID       as NIP_SID,");
            sql.addSql("   NTP_DATA_VAL.USR_SID       as USR_SID,");
            sql.addSql("   NTP_DATA_VAL.NIP_DATE      as NIP_DATE,");
            sql.addSql("   NTP_DATA_VAL.NIP_FR_TIME   as NIP_FR_TIME,");
            sql.addSql("   NTP_DATA_VAL.NIP_TO_TIME   as NIP_TO_TIME,");
            sql.addSql("   NTP_DATA_VAL.NIP_KADO_HH   as NIP_KADO_HH,");
            sql.addSql("   NTP_DATA_VAL.NIP_KADO_MM   as NIP_KADO_MM,");
            sql.addSql("   NTP_DATA_VAL.NIP_MGY_SID   as NIP_MGY_SID,");
            sql.addSql("   NTP_DATA_VAL.NAN_SID       as NAN_SID,");
            sql.addSql("   NTP_DATA_VAL.ACO_SID       as ACO_SID,");
            sql.addSql("   NTP_DATA_VAL.ABA_SID       as ABA_SID,");
            sql.addSql("   NTP_DATA_VAL.NIP_TITLE     as NIP_TITLE,");
            sql.addSql("   NTP_DATA_VAL.NIP_TITLE_CLO as NIP_TITLE_CLO,");
            sql.addSql("   NTP_DATA_VAL.MPR_SID       as MPR_SID,");
            sql.addSql("   NTP_DATA_VAL.MKB_SID       as MKB_SID,");
            sql.addSql("   NTP_DATA_VAL.MKH_SID       as MKH_SID,");
            sql.addSql("   NTP_DATA_VAL.NIP_TIEUP_SID as NIP_TIEUP_SID,");
            sql.addSql("   NTP_DATA_VAL.NIP_KEIZOKU   as NIP_KEIZOKU,");
            sql.addSql("   NTP_DATA_VAL.NIP_ACTEND    as NIP_ACTEND,");
            sql.addSql("   NTP_DATA_VAL.NIP_DETAIL    as NIP_DETAIL,");
            sql.addSql("   NTP_DATA_VAL.NIP_ACTION_DATE    as NIP_ACTION_DATE,");
            sql.addSql("   NTP_DATA_VAL.NIP_ACTION    as NIP_ACTION,");
            sql.addSql("   NTP_DATA_VAL.NIP_ACTDATE_KBN    as NIP_ACTDATE_KBN,");
            sql.addSql("   NTP_DATA_VAL.NIP_ASSIGN    as NIP_ASSIGN,");
            sql.addSql("   NTP_DATA_VAL.NIP_KINGAKU   as NIP_KINGAKU,");
            sql.addSql("   NTP_DATA_VAL.NIP_MIKOMI    as NIP_MIKOMI,");
            sql.addSql("   NTP_DATA_VAL.NIP_SYOKAN    as NIP_SYOKAN,");
            sql.addSql("   NTP_DATA_VAL.NIP_PUBLIC    as NIP_PUBLIC,");
            sql.addSql("   NTP_DATA_VAL.NIP_EDIT      as NIP_EDIT,");
            sql.addSql("   NTP_DATA_VAL.NEX_SID       as NEX_SID,");
            sql.addSql("   NTP_DATA_VAL.NIP_AUID      as NIP_AUID,");
            sql.addSql("   NTP_DATA_VAL.NIP_ADATE     as NIP_ADATE,");
            sql.addSql("   NTP_DATA_VAL.NIP_EUID      as NIP_EUID,");
            sql.addSql("   NTP_DATA_VAL.NIP_EDATE     as NIP_EDATE");
            sql.addSql(" from");
            sql.addSql("   NTP_DATA as NTP_DATA_VAL");
            sql.addSql(" where");
            sql.addSql("   NTP_DATA_VAL.NIP_SID = ?");


            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nipSid);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = __getNipUsrDataFromRs(rs);
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
     * <p>
     * スケジュール拡張SIDからスケジュール情報の件数を取得する
     * @param nexSid
     *            スケジュール拡張SID
     * @return NippouSearchModel
     * @throws SQLException
     *             SQL実行例外
     */
    public int getExCount(int nexSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            // SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from");
            sql.addSql("   NTP_DATA");
            sql.addSql(" where ");
            sql.addSql("   NTP_DATA.NEX_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nexSid);
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
     * <p>
     * 日報SIDからスケジュール拡張情報＋ユーザ情報を取得する
     * @param nipSid
     *            日報SID
     * @param crange
     *            共有範囲設定 0=全て、1=所属グループ内のみ
     * @return NippouExSearchModel
     * @throws SQLException
     *             SQL実行例外
     */
    public NippouExSearchModel getNippouExData(int nipSid, int crange)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        NippouExSearchModel ret = null;
        con = getCon();

        try {
            // SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select");
            sql.addSql("   NTP_DATA.NIP_SID,");
            sql.addSql("   NTP_DATA.USR_SID,");
            sql.addSql("   NTP_EXDATA.NEX_SID,");
            sql.addSql("   NTP_EXDATA.NEX_KBN,");
            sql.addSql("   NTP_EXDATA.NEX_DWEEK1,");
            sql.addSql("   NTP_EXDATA.NEX_DWEEK2,");
            sql.addSql("   NTP_EXDATA.NEX_DWEEK3,");
            sql.addSql("   NTP_EXDATA.NEX_DWEEK4,");
            sql.addSql("   NTP_EXDATA.NEX_DWEEK5,");
            sql.addSql("   NTP_EXDATA.NEX_DWEEK6,");
            sql.addSql("   NTP_EXDATA.NEX_DWEEK7,");
            sql.addSql("   NTP_EXDATA.NEX_DAY,");
            sql.addSql("   NTP_EXDATA.NEX_WEEK,");
            sql.addSql("   NTP_EXDATA.NEX_TIME_FR,");
            sql.addSql("   NTP_EXDATA.NEX_TIME_TO,");
            sql.addSql("   NTP_EXDATA.NEX_KADO_HH,");
            sql.addSql("   NTP_EXDATA.NEX_KADO_MM,");
            sql.addSql("   NTP_EXDATA.NEX_TRAN_KBN,");
            sql.addSql("   NTP_EXDATA.NEX_DATE_FR,");
            sql.addSql("   NTP_EXDATA.NEX_DATE_TO,");
            sql.addSql("   NTP_EXDATA.NEX_MGY_SID,");
            sql.addSql("   NTP_EXDATA.NEX_NAN_SID,");
            sql.addSql("   NTP_EXDATA.NEX_ACO_SID,");
            sql.addSql("   NTP_EXDATA.NEX_ABO_SID,");
            sql.addSql("   NTP_EXDATA.NEX_TITLE,");
            sql.addSql("   NTP_EXDATA.NEX_TITLE_CLO,");
            sql.addSql("   NTP_EXDATA.NEX_MPR_SID,");
            sql.addSql("   NTP_EXDATA.NEX_MKB_SID,");
            sql.addSql("   NTP_EXDATA.NEX_MKH_SID,");
            sql.addSql("   NTP_EXDATA.NEX_TIEUP_SID,");
            sql.addSql("   NTP_EXDATA.NEX_KEIZOKU,");
            sql.addSql("   NTP_EXDATA.NEX_ACTEND,");
            sql.addSql("   NTP_EXDATA.NEX_DETAIL,");
            sql.addSql("   NTP_EXDATA.NEX_ASSIGN,");
            sql.addSql("   NTP_EXDATA.NEX_KINGAKU,");
            sql.addSql("   NTP_EXDATA.NEX_MIKOMI,");
            sql.addSql("   NTP_EXDATA.NEX_SYOKAN,");
            sql.addSql("   NTP_EXDATA.NEX_PUBLIC,");
            sql.addSql("   NTP_EXDATA.NEX_EDIT,");
            sql.addSql("   NTP_EXDATA.NEX_AUID,");
            sql.addSql("   NTP_EXDATA.NEX_ADATE,");
            sql.addSql("   NTP_EXDATA.NEX_EUID,");
            sql.addSql("   NTP_EXDATA.NEX_EDATE");
            sql.addSql(" from");
            sql.addSql("   NTP_DATA,");
            sql.addSql("   NTP_EXDATA");
            sql.addSql(" where ");
            sql.addSql("   NTP_DATA.NIP_SID=?");
            sql.addSql(" and");
            sql.addSql("   NTP_DATA.NEX_SID = NTP_EXDATA.NEX_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nipSid);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getNtpExdataFromRs(rs);
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
     * <p>
     * Create NTP_DATA Data Bindding JavaBean From ResultSet
     * @param rs
     *            ResultSet
     * @return created SchDataModel
     * @throws SQLException
     *             SQL実行例外
     */
    private NtpDataModel __getNtpDataFromRs(ResultSet rs) throws SQLException {
        NtpDataModel bean = new NtpDataModel();
        bean.setNipSid(rs.getInt("NIP_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setNipDate(UDate.getInstanceTimestamp(rs
                .getTimestamp("NIP_DATE")));
        bean.setNipFrTime(UDate.getInstanceTimestamp(rs
                .getTimestamp("NIP_FR_TIME")));
        bean.setNipToTime(UDate.getInstanceTimestamp(rs
                .getTimestamp("NIP_TO_TIME")));
        bean.setNipMgySid(rs.getInt("NIP_MGY_SID"));
        bean.setNanSid(rs.getInt("NAN_SID"));
        bean.setAcoSid(rs.getInt("ACO_SID"));
        bean.setAbaSid(rs.getInt("ABA_SID"));
        bean.setNipTitle(rs.getString("NIP_TITLE"));
        bean.setNipTitleClo(rs.getInt("NIP_TITLE_CLO"));
        bean.setMprSid(rs.getInt("MPR_SID"));
        bean.setMkbSid(rs.getInt("MKB_SID"));
        bean.setMkhSid(rs.getInt("MKH_SID"));
        bean.setNipTieupSid(rs.getInt("NIP_TIEUP_SID"));
        bean.setNipKeizoku(rs.getInt("NIP_KEIZOKU"));
        bean.setNipActend(UDate.getInstanceTimestamp(rs
                .getTimestamp("NIP_ACTEND")));
        bean.setNipDetail(rs.getString("NIP_DETAIL"));
        bean.setNipActionDate(UDate.getInstanceTimestamp(rs
                .getTimestamp("NIP_ACTION_DATE")));
        bean.setNipAction(rs.getString("NIP_ACTION"));
        bean.setNipActDateKbn(rs.getInt("NIP_ACTDATE_KBN"));
        bean.setNipAssign(rs.getString("NIP_ASSIGN"));
        bean.setNipKingaku(rs.getInt("NIP_KINGAKU"));
        bean.setNipMikomi(rs.getInt("NIP_MIKOMI"));
        bean.setNipSyokan(rs.getString("NIP_SYOKAN"));
        bean.setNipPublic(rs.getInt("NIP_PUBLIC"));
        bean.setNipEdit(rs.getInt("NIP_EDIT"));
        bean.setNexSid(rs.getInt("NEX_SID"));
        bean.setNipAuid(rs.getInt("NIP_AUID"));
        bean.setNipAdate(UDate.getInstanceTimestamp(rs
                .getTimestamp("NIP_ADATE")));
        bean.setNipEuid(rs.getInt("NIP_EUID"));
        bean.setNipEdate(UDate.getInstanceTimestamp(rs
                .getTimestamp("NIP_EDATE")));
        return bean;
    }
    /**
     * <p>
     * Create NTP_DATA Data Bindding JavaBean From ResultSet
     * @param rs
     *            ResultSet
     * @return created SchDataModel
     * @throws SQLException
     *             SQL実行例外
     */
    private NtpDataModel __getNtpDataPlusNameFromRs(ResultSet rs) throws SQLException {
        NtpDataModel bean = new NtpDataModel();
        bean.setNipSid(rs.getInt("NIP_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setNipDate(UDate.getInstanceTimestamp(rs
                .getTimestamp("NIP_DATE")));
        bean.setNipFrTime(UDate.getInstanceTimestamp(rs
                .getTimestamp("NIP_FR_TIME")));
        bean.setNipToTime(UDate.getInstanceTimestamp(rs
                .getTimestamp("NIP_TO_TIME")));
        bean.setNipMgySid(rs.getInt("NIP_MGY_SID"));
        bean.setNanSid(rs.getInt("NAN_SID"));
        bean.setAcoSid(rs.getInt("ACO_SID"));
        bean.setAbaSid(rs.getInt("ABA_SID"));
        bean.setNipTitle(rs.getString("NIP_TITLE"));
        bean.setNipTitleClo(rs.getInt("NIP_TITLE_CLO"));
        bean.setMprSid(rs.getInt("MPR_SID"));
        bean.setMkbSid(rs.getInt("MKB_SID"));
        bean.setMkhSid(rs.getInt("MKH_SID"));
        bean.setNipTieupSid(rs.getInt("NIP_TIEUP_SID"));
        bean.setNipKeizoku(rs.getInt("NIP_KEIZOKU"));
        bean.setNipActend(UDate.getInstanceTimestamp(rs
                .getTimestamp("NIP_ACTEND")));
        bean.setNipDetail(rs.getString("NIP_DETAIL"));
        bean.setNipActionDate(UDate.getInstanceTimestamp(rs
                .getTimestamp("NIP_ACTION_DATE")));
        bean.setNipAction(rs.getString("NIP_ACTION"));
        bean.setNipActDateKbn(rs.getInt("NIP_ACTDATE_KBN"));
        bean.setNipAssign(rs.getString("NIP_ASSIGN"));
        bean.setNipKingaku(rs.getInt("NIP_KINGAKU"));
        bean.setNipMikomi(rs.getInt("NIP_MIKOMI"));
        bean.setNipSyokan(rs.getString("NIP_SYOKAN"));
        bean.setNipPublic(rs.getInt("NIP_PUBLIC"));
        bean.setNipEdit(rs.getInt("NIP_EDIT"));
        bean.setNexSid(rs.getInt("NEX_SID"));
        bean.setNipAuid(rs.getInt("NIP_AUID"));
        bean.setNipAdate(UDate.getInstanceTimestamp(rs
                .getTimestamp("NIP_ADATE")));
        bean.setNipEuid(rs.getInt("NIP_EUID"));
        bean.setNipEdate(UDate.getInstanceTimestamp(rs
                .getTimestamp("NIP_EDATE")));
        bean.setNtpUserName(rs.getString("USI_SEI") + " " + rs.getString("USI_MEI"));
        return bean;
    }
    /**
     * <p>
     * NippouSearchModel Bindding JavaBean From ResultSet
     * @param rs
     *            ResultSet
     * @return created ScheduleSearchModel
     * @throws SQLException
     *             SQL実行例外
     */
    private NippouSearchModel __getNipUsrDataFromRs(ResultSet rs)
            throws SQLException {
        NippouSearchModel bean = new NippouSearchModel();
        bean.setNipSid(rs.getInt("NIP_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setNipDate(UDate.getInstanceTimestamp(rs.getTimestamp("NIP_DATE")));
        bean.setNipFrTime(UDate.getInstanceTimestamp(rs.getTimestamp("NIP_FR_TIME")));
        bean.setNipToTime(UDate.getInstanceTimestamp(rs.getTimestamp("NIP_TO_TIME")));
        bean.setNipKadoHh(rs.getInt("NIP_KADO_HH"));
        bean.setNipKadoMm(rs.getInt("NIP_KADO_MM"));
        bean.setNipMgySid(rs.getInt("NIP_MGY_SID"));
        bean.setNanSid(rs.getInt("NAN_SID"));
        bean.setAcoSid(rs.getInt("ACO_SID"));
        bean.setAbaSid(rs.getInt("ABA_SID"));
        bean.setNipTitle(rs.getString("NIP_TITLE"));
        bean.setNipTitleClo(rs.getInt("NIP_TITLE_CLO"));
        bean.setMprSid(rs.getInt("MPR_SID"));
        bean.setMkbSid(rs.getInt("MKB_SID"));
        bean.setMkhSid(rs.getInt("MKH_SID"));
        bean.setNipTieupSid(rs.getInt("NIP_TIEUP_SID"));
        bean.setNipKeizoku(rs.getInt("NIP_KEIZOKU"));
        bean.setNipActend(UDate.getInstanceTimestamp(rs.getTimestamp("NIP_ACTEND")));
        bean.setNipActionDate(UDate.getInstanceTimestamp(rs
                .getTimestamp("NIP_ACTION_DATE")));
        bean.setNipAction(rs.getString("NIP_ACTION"));
        bean.setNipActDateKbn(rs.getInt("NIP_ACTDATE_KBN"));
        bean.setNipDetail(rs.getString("NIP_DETAIL"));
        bean.setNipAssign(rs.getString("NIP_ASSIGN"));
        bean.setNipKingaku(rs.getInt("NIP_KINGAKU"));
        bean.setNipMikomi(rs.getInt("NIP_MIKOMI"));
        bean.setNipSyokan(rs.getString("NIP_SYOKAN"));
        bean.setNipPublic(rs.getInt("NIP_PUBLIC"));
        bean.setNipEdit(rs.getInt("NIP_EDIT"));
        bean.setNexSid(rs.getInt("NEX_SID"));
        bean.setNipAuid(rs.getInt("NIP_AUID"));
        bean.setNipAdate(UDate.getInstanceTimestamp(rs.getTimestamp("NIP_ADATE")));
        bean.setNipEuid(rs.getInt("NIP_EUID"));
        bean.setNipEdate(UDate.getInstanceTimestamp(rs.getTimestamp("NIP_EDATE")));
        return bean;
    }

    /**
     * <p>
     * Create NTP_EXDATA Data Bindding JavaBean From ResultSet
     * @param rs
     *            ResultSet
     * @return created NippouExSearchModel
     * @throws SQLException
     *             SQL実行例外
     */
    private NippouExSearchModel __getNtpExdataFromRs(ResultSet rs)
            throws SQLException {
        NippouExSearchModel bean = new NippouExSearchModel();
        // 選択日報情報
        bean.setNipSid(rs.getInt("NIP_SID"));
        bean.setNipUsrSid(rs.getInt("USR_SID"));
        // 拡張情報
        bean.setNexSid(rs.getInt("NEX_SID"));
        bean.setNexKbn(rs.getInt("NEX_KBN"));
        bean.setNexDweek1(rs.getInt("NEX_DWEEK1"));
        bean.setNexDweek2(rs.getInt("NEX_DWEEK2"));
        bean.setNexDweek3(rs.getInt("NEX_DWEEK3"));
        bean.setNexDweek4(rs.getInt("NEX_DWEEK4"));
        bean.setNexDweek5(rs.getInt("NEX_DWEEK5"));
        bean.setNexDweek6(rs.getInt("NEX_DWEEK6"));
        bean.setNexDweek7(rs.getInt("NEX_DWEEK7"));
        bean.setNexDay(rs.getInt("NEX_DAY"));
        bean.setNexWeek(rs.getInt("NEX_WEEK"));
        bean.setNexTimeFr(UDate.getInstanceTimestamp(rs.getTimestamp("NEX_TIME_FR")));
        bean.setNexTimeTo(UDate.getInstanceTimestamp(rs.getTimestamp("NEX_TIME_TO")));
        bean.setNexKadoHh(rs.getInt("NEX_KADO_HH"));
        bean.setNexKadoMm(rs.getInt("NEX_KADO_MM"));
        bean.setNexTranKbn(rs.getInt("NEX_TRAN_KBN"));
        bean.setNexDateFr(UDate.getInstanceTimestamp(rs.getTimestamp("NEX_DATE_FR")));
        bean.setNexDateTo(UDate.getInstanceTimestamp(rs.getTimestamp("NEX_DATE_TO")));
        bean.setNexMgySid(rs.getInt("NEX_MGY_SID"));
        bean.setNexNanSid(rs.getInt("NEX_NAN_SID"));
        bean.setNexAcoSid(rs.getInt("NEX_ACO_SID"));
        bean.setNexAboSid(rs.getInt("NEX_ABO_SID"));
        bean.setNexTitle(rs.getString("NEX_TITLE"));
        bean.setNexTitleClo(rs.getInt("NEX_TITLE_CLO"));
        bean.setNexMprSid(rs.getInt("NEX_MPR_SID"));
        bean.setNexMkbSid(rs.getInt("NEX_MKB_SID"));
        bean.setNexMkhSid(rs.getInt("NEX_MKH_SID"));
        bean.setNexTieupSid(rs.getInt("NEX_TIEUP_SID"));
        bean.setNexKeizoku(rs.getInt("NEX_KEIZOKU"));
        bean.setNexActend(UDate.getInstanceTimestamp(rs.getTimestamp("NEX_ACTEND")));
        bean.setNexDetail(rs.getString("NEX_DETAIL"));
        bean.setNexAssign(rs.getString("NEX_ASSIGN"));
        bean.setNexKingaku(rs.getInt("NEX_KINGAKU"));
        bean.setNexMikomi(rs.getInt("NEX_MIKOMI"));
        bean.setNexSyokan(rs.getString("NEX_SYOKAN"));
        bean.setNexPublic(rs.getInt("NEX_PUBLIC"));
        bean.setNexEdit(rs.getInt("NEX_EDIT"));
        bean.setNexAuid(rs.getInt("NEX_AUID"));
        bean.setNexAdate(UDate.getInstanceTimestamp(rs.getTimestamp("NEX_ADATE")));
        bean.setNexEuid(rs.getInt("NEX_EUID"));
        bean.setNexEdate(UDate.getInstanceTimestamp(rs.getTimestamp("NEX_EDATE")));
        return bean;
    }

    /**
     * <p>
     * Create SCH_DATA Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created ScheduleCsvModel
     * @throws SQLException SQL実行例外
     */
    private NippouCsvModel __getNippouCsvFromRs(ResultSet rs)
            throws SQLException {
        NippouCsvModel bean = new NippouCsvModel();
        bean.setNipSid(rs.getInt("NIP_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setNipDate(UDate.getInstanceTimestamp(rs
                .getTimestamp("NIP_DATE")));
        bean.setNipFrTime(UDate.getInstanceTimestamp(rs
                .getTimestamp("NIP_FR_TIME")));
        bean.setNipToTime(UDate.getInstanceTimestamp(rs
                .getTimestamp("NIP_TO_TIME")));
        bean.setNanSid(rs.getInt("NAN_SID"));
        bean.setAcoSid(rs.getInt("ACO_SID"));
        bean.setAbaSid(rs.getInt("ABA_SID"));
        bean.setNipTitle(rs.getString("NIP_TITLE"));
        bean.setNipTitleClo(rs.getInt("NIP_TITLE_CLO"));
        bean.setMkbSid(rs.getInt("MKB_SID"));
        bean.setMkhSid(rs.getInt("MKH_SID"));
        bean.setNipDetail(rs.getString("NIP_DETAIL"));
        bean.setNipMikomi(rs.getInt("NIP_MIKOMI"));

        bean.setNtpDateStr(UDateUtil.getYYMD(bean.getNipDate()));
        bean.setNtpFrDateStr(UDateUtil.getSlashYYMD(bean.getNipFrTime()));
        bean.setNtpFrTimeStr(UDateUtil.getSeparateHM(bean.getNipFrTime()));
        bean.setNtpToDateStr(UDateUtil.getSlashYYMD(bean.getNipToTime()));
        bean.setNtpToTimeStr(UDateUtil.getSeparateHM(bean.getNipToTime()));

        bean.setUsrLgId(rs.getString("USR_LGID"));
        bean.setAnkenCode(rs.getString("NAN_CODE"));
        bean.setAcoCode(rs.getString("ACO_CODE"));
        bean.setKbunruiCode(rs.getString("NKB_CODE"));
        bean.setKhouhouCode(rs.getString("NKH_CODE"));

        bean.setUsrName(rs.getString("USR_NAME"));

        return bean;
    }

}
