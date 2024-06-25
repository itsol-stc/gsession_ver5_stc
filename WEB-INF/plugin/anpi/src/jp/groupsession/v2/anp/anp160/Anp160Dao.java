package jp.groupsession.v2.anp.anp160;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.anp.GSConstAnpi;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.UserSearchDao;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] メッセージ配信確認 送信者一覧(ポップアップ画面)のDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp160Dao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Anp160Dao.class);

    /**
     * <p>Default Constructor
     */
    public Anp160Dao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Anp160Dao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] 再配信確認画面に表示するグループのユーザリストの件数を取得する
     * <br>[解  説] 指定したグループ and 配信データ有り のユーザリスト
     * <br>[備  考]
     * @param anpSid 安否SID
     * @param grpSid グループSID
     * @param procMode プロセスモード
     * @return 件数
     *
     * @throws SQLException SQL実行例外
     */
    public int getReSendBelongUserListCount(
            int anpSid, int grpSid, String procMode) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(*) as CNT");
            __setListFromWhereSQL(sql, grpSid, anpSid, procMode);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] 再配信確認画面に表示するグループのユーザリストを取得する
     * <br>[解  説] 指定したグループ and 配信データ有り のユーザリスト
     * <br>[備  考]
     * @param anpSid 安否SID
     * @param grpSid グループSID
     * @return ユーザリスト
     * @param sortKey1 ソート項目
     * @param orderKey1 ソートオーダー
     * @param sortKey2 ソート項目2
     * @param orderKey2 ソートオーダー2
     * @param start 開始位置
     * @param limit 表示件数
     * @param procMode プロセスモード
     *
     * @throws SQLException SQL実行例外
     */
    public ArrayList<CmnUsrmInfModel> getReSendBelongUserList(
            int anpSid, int grpSid, int sortKey1,
            int orderKey1, int sortKey2, int orderKey2,
            int start, int limit, String procMode) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnUsrmInfModel> ret = new ArrayList<CmnUsrmInfModel>();
        con = getCon();

        try {
            SqlBuffer sql = __getReSendUserSql();
            __setListFromWhereSQL(sql, grpSid, anpSid, procMode);
            __createOrderSql(sql, sortKey1, orderKey1, sortKey2, orderKey2);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (start > 1) {
                rs.absolute(start - 1);
            }

            for (int i = 0; rs.next() && i < limit; i++) {
                ret.add(UserSearchDao.getCmnUsrmInfModelFromRs2(rs));
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
     * <br>[機  能] 再配信確認画面に表示するグループのユーザリストを取得するSQLを返す
     * <br>[解  説]
     * <br>[備  考]
     * @return 生成したSqlBuffer
     */
    private SqlBuffer __getReSendUserSql() {
        SqlBuffer sql = new SqlBuffer();
        sql.addSql(" select");
        sql.addSql("   CMN_USRM.USR_UKO_FLG as USR_UKO_FLG,");
        sql.addSql("   CMN_USRM_INF.USR_SID as USR_SID,");
        sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI,");
        sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI,");
        sql.addSql("   CMN_USRM_INF.USI_SEI_KN as USI_SEI_KN,");
        sql.addSql("   CMN_USRM_INF.USI_MEI_KN as USI_MEI_KN,");
        sql.addSql("   CMN_USRM_INF.USI_SINI as USI_SINI,");
        sql.addSql("   CMN_USRM_INF.USI_BDATE as USI_BDATE,");
        sql.addSql("   CMN_USRM_INF.USI_ZIP1 as USI_ZIP1,");
        sql.addSql("   CMN_USRM_INF.USI_ZIP2 as USI_ZIP2,");
        sql.addSql("   CMN_USRM_INF.TDF_SID as TDF_SID,");
        sql.addSql("   CMN_USRM_INF.USI_ADDR1 as USI_ADDR1,");
        sql.addSql("   CMN_USRM_INF.USI_ADDR2 as USI_ADDR2,");
        sql.addSql("   CMN_USRM_INF.USI_TEL1 as USI_TEL1,");
        sql.addSql("   CMN_USRM_INF.USI_TEL_NAI1 as USI_TEL_NAI1,");
        sql.addSql("   CMN_USRM_INF.USI_TEL_CMT1 as USI_TEL_CMT1,");
        sql.addSql("   CMN_USRM_INF.USI_TEL2 as USI_TEL2,");
        sql.addSql("   CMN_USRM_INF.USI_TEL_NAI2 as USI_TEL_NAI2,");
        sql.addSql("   CMN_USRM_INF.USI_TEL_CMT2 as USI_TEL_CMT2,");
        sql.addSql("   CMN_USRM_INF.USI_TEL3 as USI_TEL3,");
        sql.addSql("   CMN_USRM_INF.USI_TEL_NAI3 as USI_TEL_NAI3,");
        sql.addSql("   CMN_USRM_INF.USI_TEL_CMT3 as USI_TEL_CMT3,");
        sql.addSql("   CMN_USRM_INF.USI_FAX1 as USI_FAX1,");
        sql.addSql("   CMN_USRM_INF.USI_FAX_CMT1 as USI_FAX_CMT1,");
        sql.addSql("   CMN_USRM_INF.USI_FAX2 as USI_FAX2,");
        sql.addSql("   CMN_USRM_INF.USI_FAX_CMT2 as USI_FAX_CMT2,");
        sql.addSql("   CMN_USRM_INF.USI_FAX3 as USI_FAX3,");
        sql.addSql("   CMN_USRM_INF.USI_FAX_CMT3 as USI_FAX_CMT3,");
        sql.addSql("   CMN_USRM_INF.USI_MAIL1 as USI_MAIL1,");
        sql.addSql("   CMN_USRM_INF.USI_MAIL_CMT1 as USI_MAIL_CMT1,");
        sql.addSql("   CMN_USRM_INF.USI_MAIL2 as USI_MAIL2,");
        sql.addSql("   CMN_USRM_INF.USI_MAIL_CMT2 as USI_MAIL_CMT2,");
        sql.addSql("   CMN_USRM_INF.USI_MAIL3 as USI_MAIL3,");
        sql.addSql("   CMN_USRM_INF.USI_MAIL_CMT3 as USI_MAIL_CMT3,");
        sql.addSql("   CMN_USRM_INF.USI_SYAIN_NO as USI_SYAIN_NO,");
        sql.addSql("   CMN_USRM_INF.USI_SYOZOKU as USI_SYOZOKU,");
        sql.addSql("   CMN_USRM_INF.USI_SEIBETU as USI_SEIBETU,");
        sql.addSql("   CMN_USRM_INF.USI_ENTRANCE_DATE as USI_ENTRANCE_DATE,");
        sql.addSql("   CMN_USRM_INF.USI_SORTKEY1 as USI_SORTKEY1,");
        sql.addSql("   CMN_USRM_INF.USI_SORTKEY2 as USI_SORTKEY2,");
        sql.addSql("   CMN_USRM_INF.POS_SID as POS_SID,");
        sql.addSql("   (case");
        sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then ''");
        sql.addSql("      else (select");
        sql.addSql("            POS_NAME");
        sql.addSql("          from");
        sql.addSql("            CMN_POSITION");
        sql.addSql("          where CMN_USRM_INF.POS_SID = CMN_POSITION.POS_SID)");
        sql.addSql("    end) as USI_YAKUSYOKU,");
        sql.addSql("   CMN_USRM_INF.USI_BIKO as USI_BIKO,");
        sql.addSql("   CMN_USRM_INF.BIN_SID as BIN_SID,");
        sql.addSql("   CMN_USRM_INF.USI_PICT_KF as USI_PICT_KF,");
        sql.addSql("   CMN_USRM_INF.USI_BDATE_KF as USI_BDATE_KF,");
        sql.addSql("   CMN_USRM_INF.USI_MAIL1_KF as USI_MAIL1_KF,");
        sql.addSql("   CMN_USRM_INF.USI_MAIL2_KF as USI_MAIL2_KF,");
        sql.addSql("   CMN_USRM_INF.USI_MAIL3_KF as USI_MAIL3_KF,");
        sql.addSql("   CMN_USRM_INF.USI_ZIP_KF as USI_ZIP_KF,");
        sql.addSql("   CMN_USRM_INF.USI_TDF_KF as USI_TDF_KF,");
        sql.addSql("   CMN_USRM_INF.USI_ADDR1_KF as USI_ADDR1_KF,");
        sql.addSql("   CMN_USRM_INF.USI_ADDR2_KF as USI_ADDR2_KF,");
        sql.addSql("   CMN_USRM_INF.USI_TEL1_KF as USI_TEL1_KF,");
        sql.addSql("   CMN_USRM_INF.USI_TEL2_KF as USI_TEL2_KF,");
        sql.addSql("   CMN_USRM_INF.USI_TEL3_KF as USI_TEL3_KF,");
        sql.addSql("   CMN_USRM_INF.USI_FAX1_KF as USI_FAX1_KF,");
        sql.addSql("   CMN_USRM_INF.USI_FAX2_KF as USI_FAX2_KF,");
        sql.addSql("   CMN_USRM_INF.USI_FAX3_KF as USI_FAX3_KF,");
        sql.addSql("   CMN_USRM_INF.USI_LTLGIN as USI_LTLGIN,");
        sql.addSql("   CMN_USRM_INF.USI_AUID as USI_AUID,");
        sql.addSql("   CMN_USRM_INF.USI_ADATE as USI_ADATE,");
        sql.addSql("   CMN_USRM_INF.USI_EUID as USI_EUID,");
        sql.addSql("   CMN_USRM_INF.USI_EDATE as USI_EDATE,");
        sql.addSql("   CMN_USRM.USR_EDATE as USI_EDATE,");
        sql.addSql("   (case");
        sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then 1");
        sql.addSql("      else 0");
        sql.addSql("    end) as YAKUSYOKU_EXIST,");
        sql.addSql("   (case");
        sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then 0");
        sql.addSql("      else (select");
        sql.addSql("              POS_SORT");
        sql.addSql("            from");
        sql.addSql("              CMN_POSITION");
        sql.addSql("            where CMN_USRM_INF.POS_SID = CMN_POSITION.POS_SID)");
        sql.addSql("    end) as YAKUSYOKU_SORT");
        return sql;
    }

    /**
     * <br>[機  能]  送信者一覧取得のSqlBufferにfrom, where句を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SqlBuffer
     * @param grpSid グループSID
     * @param anpSid 安否SID
     * @param procMode プロセスモード 2の場合返信ユーザ除外
     * @return SqlBuffer
     */
    private SqlBuffer __setListFromWhereSQL(
            SqlBuffer sql, int grpSid, int anpSid, String procMode) {

        sql.addSql(" from");
        sql.addSql("   CMN_USRM, ");
        sql.addSql("   CMN_USRM_INF, ");
        sql.addSql("   CMN_BELONGM, ");
        sql.addSql("   ANP_JDATA ");
        sql.addSql(" where ");
        sql.addSql("   CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID ");
        sql.addSql(" and");
        //ユーザSID < 100は除外
        sql.addSql("   CMN_USRM.USR_SID>?");
        sql.addIntValue(GSConstUser.USER_RESERV_SID);
        sql.addSql(" and ");
        sql.addSql("   CMN_USRM.USR_JKBN = ? ");
        sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);
        sql.addSql(" and ");
        sql.addSql("   CMN_BELONGM.USR_SID = CMN_USRM.USR_SID ");
        sql.addSql(" and ");
        sql.addSql("   CMN_BELONGM.GRP_SID = ? ");
        sql.addIntValue(grpSid);
        sql.addSql(" and ");
        sql.addSql("   ANP_JDATA.APH_SID = ?");
        sql.addIntValue(anpSid);
        sql.addSql(" and ");
        sql.addSql("   ANP_JDATA.USR_SID = CMN_USRM.USR_SID");

        if (procMode.equals(GSConstAnpi.MSG_HAISIN_MODE_MISAISOU)) {
            sql.addSql(" and ");
            sql.addSql("   ANP_JDATA.APD_RDATE is null ");
        }

        return sql;
    }

    /**
     * SQLのオーダ文を作成する
     * @param sql SQL
     * @param sortKey1 ソートキー1
     * @param orderKey1 ソートキー1オーダー
     * @param sortKey2 ソートキー2
     * @param orderKey2 ソートキー2オーダー
     * @return SqlBuffer
     */
    private SqlBuffer __createOrderSql(SqlBuffer sql, int sortKey1,
            int orderKey1, int sortKey2, int orderKey2) {
        sql.addSql(" order by");
        log__.info("sortkey1 = " + sortKey1);
        log__.info("sortkey2 = " + sortKey2);

        __createOrderSql(sql, sortKey1, orderKey1);

        if (sortKey2 > 0) {
            if (sortKey1 > 0) {
                sql.addSql("   ,");
            }
            __createOrderSql(sql, sortKey2, orderKey2);
        }
        return sql;
    }

    /**
     * SQLのオーダ文を作成する
     * @param sql SQL
     * @param sortKey ソートキー
     * @param orderKey ソートオーダー
     * @return SqlBuffer
     */
    private SqlBuffer __createOrderSql(SqlBuffer sql, int sortKey, int orderKey) {
        //SELECTの場合のみ指定
        String orderStr = "";
        //オーダー
        if (orderKey == GSConst.ORDER_KEY_ASC) {
            orderStr = "  asc";
        } else {
            orderStr = "  desc";
        }

        //ソートカラム
        switch (sortKey) {
            //氏名
            case GSConstUser.USER_SORT_NAME:
                sql.addSql("   CMN_USRM_INF.USI_SEI_KN");
                sql.addSql(orderStr);
                sql.addSql("   ,");
                sql.addSql("   CMN_USRM_INF.USI_MEI_KN");
                sql.addSql(orderStr);
                break;
            //社員/職員番号
            case GSConstUser.USER_SORT_SNO:
                sql.addSql("   case when CMN_USRM_INF.USI_SYAIN_NO is null then ''");
                sql.addSql("   else CMN_USRM_INF.USI_SYAIN_NO end ");
                sql.addSql(orderStr);
                break;
            //役職
            case GSConstUser.USER_SORT_YKSK:
                sql.addSql("  YAKUSYOKU_EXIST");
                sql.addSql(orderStr);
                sql.addSql("  ,");
                sql.addSql("  YAKUSYOKU_SORT");
                sql.addSql(orderStr);
                break;
             //生年月日
            case GSConstUser.USER_SORT_BDATE:
                sql.addSql("  CMN_USRM_INF.USI_BDATE");
                sql.addSql(orderStr);
                break;
            //ソートキー1
            case GSConstUser.USER_SORT_SORTKEY1:
                sql.addSql("  CMN_USRM_INF.USI_SORTKEY1");
                sql.addSql(orderStr);
                break;
            //ソートキー2
            case GSConstUser.USER_SORT_SORTKEY2:
                sql.addSql("  CMN_USRM_INF.USI_SORTKEY2");
                sql.addSql(orderStr);
                break;
            //在席状況
            case GSConstUser.USER_SORT_UIO:
                sql.addSql("  UIO_STATUS");
                sql.addSql(orderStr);
                break;
            //在席コメント
            case GSConstUser.USER_SORT_COMM:
                sql.addSql("  IO.UIO_BIKO");
                sql.addSql(orderStr);
                break;
            default:
                sql.addSql("   CMN_USRM_INF.USI_SEI_KN");
                sql.addSql(orderStr);
                sql.addSql("   ,");
                sql.addSql("   CMN_USRM_INF.USI_MEI_KN");
                sql.addSql(orderStr);
                break;
        }
        return sql;
    }
}
