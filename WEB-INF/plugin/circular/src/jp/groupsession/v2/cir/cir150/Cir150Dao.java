package jp.groupsession.v2.cir.cir150;

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
import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cmn.model.RequestModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 回覧板 アカウントマネージャー画面で使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir150Dao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cir150Dao.class);

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     */
    public Cir150Dao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] アカウント情報の一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param searchMdl 検索条件
     * @param reqMdl リクエスト情報
     * @return アカウント情報の一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Cir150AccountModel> getAccountList(Cir150SearchModel searchMdl,
                                                        RequestModel reqMdl)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Cir150AccountModel> ret = new ArrayList<Cir150AccountModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CIR_ACCOUNT.CAC_SID as CAC_SID,");
            sql.addSql("   CIR_ACCOUNT.CAC_TYPE as CAC_TYPE,");
            sql.addSql("   CIR_ACCOUNT.USR_SID as USR_SID,");
            sql.addSql("   CIR_ACCOUNT.CAC_NAME as CAC_NAME,");
            sql.addSql("   CIR_ACCOUNT.CAC_BIKO as CAC_BIKO,");
            sql.addSql("   ACCOUNT_USER_COUNT.DNT_COUNT as DNT_COUNT,");
            sql.addSql("   ACCOUNT_USER_COUNT.USR_COUNT as USR_COUNT,");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI,");
            sql.addSql("   CMN_USRM.USR_UKO_FLG as USR_UKO_FLG");
            //sql.addSql("   CIR_ACCOUNT_DISK.SDS_SIZE as SDS_SIZE");
            sql.addSql(" from ");
//            sql.addSql("   ( ");
            sql.addSql("     CIR_ACCOUNT");
//            sql.addSql("     inner join ");
//            sql.addSql("       CIR_ACCOUNT_DISK");
//            sql.addSql("     on ");
//            sql.addSql("       CIR_ACCOUNT.CAC_SID = CIR_ACCOUNT_DISK.CAC_SID");
//            sql.addSql("   ) ");
            sql.addSql("   left join");
            sql.addSql("     (");
            sql.addSql("       select");
            sql.addSql("         CIR_ACCOUNT_USER.CAC_SID,");
            sql.addSql("         count(CMN_GROUPM_TABLE.GRP_SID) as DNT_COUNT,");
            sql.addSql("         count(CMN_USRM_TABLE.USR_SID) as USR_COUNT");
            sql.addSql("       from");
            sql.addSql("         CIR_ACCOUNT_USER");

            sql.addSql("       left join");
            sql.addSql("         (select CMN_USRM.USR_SID from CMN_USRM"
                                    + " where CMN_USRM.USR_JKBN != 9) CMN_USRM_TABLE ");
            sql.addSql("       on ");
            sql.addSql("         CIR_ACCOUNT_USER.USR_SID = CMN_USRM_TABLE.USR_SID");

            sql.addSql("       left join");
            sql.addSql("         (select GRP_SID from CMN_GROUPM"
                                    + " where CMN_GROUPM.GRP_JKBN != 9) CMN_GROUPM_TABLE ");
            sql.addSql("       on ");
            sql.addSql("         CIR_ACCOUNT_USER.GRP_SID = CMN_GROUPM_TABLE.GRP_SID");

            sql.addSql("       group by");
            sql.addSql("         CIR_ACCOUNT_USER.CAC_SID");
            sql.addSql("     ) ACCOUNT_USER_COUNT");
            sql.addSql("   on");
            sql.addSql("     CIR_ACCOUNT.CAC_SID = ACCOUNT_USER_COUNT.CAC_SID");
            sql.addSql("   left join");
            sql.addSql("     CMN_USRM_INF");
            sql.addSql("   on");
            sql.addSql("      CIR_ACCOUNT.USR_SID = CMN_USRM_INF.USR_SID");
            sql.addSql("   left join");
            sql.addSql("     CMN_USRM");
            sql.addSql("   on");
            sql.addSql("      CIR_ACCOUNT.USR_SID = CMN_USRM.USR_SID");

            //検索条件
            sql = __setSqlWhere(sql, searchMdl);

            //ソート
            String order = "";
            if (searchMdl.getOrder() == GSConstCircular.ORDER_DESC) {
                order = " desc";
            }
            sql.addSql(" order by");
            if (searchMdl.getSortKey() == GSConstCircular.SKEY_USER) {
                sql.addSql("   (DNT_COUNT + USR_COUNT)" + order);
//            } else if (searchMdl.getSortKey() == GSConstCircular.SKEY_DISKSIZE) {
//                sql.addSql("   CIR_ACCOUNT_DISK.SDS_SIZE" + order);
            } else {
                sql.addSql("   CAC_NAME" + order);
            }

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString(),
                                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                                        ResultSet.CONCUR_READ_ONLY);
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (searchMdl.getMaxCount() > 0) {
                if (searchMdl.getPage() > 1) {
                    rs.absolute(searchMdl.getPage() - 1);
                }

                for (int i = 0; rs.next() && i < searchMdl.getMaxCount(); i++) {

                    Cir150AccountModel model = new Cir150AccountModel(reqMdl);
                    model.setAccountSid(rs.getInt("CAC_SID"));

                    if (!StringUtil.isNullZeroStringSpace(rs.getString("USI_SEI"))
                            && !StringUtil.isNullZeroStringSpace(rs.getString("USI_MEI"))) {
                        model.setAccountName(
                                rs.getString("USI_SEI") + " " + rs.getString("USI_MEI"));
                        model.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
                    } else {
                        model.setAccountName(rs.getString("CAC_NAME"));
                    }

                    model.setAccountUserKbn(rs.getInt("CAC_TYPE"));

//                    //BからMBへ変換
//                    model.setDiskSizeUse(
//                            rs.getBigDecimal("SDS_SIZE").divide(
//                                    new BigDecimal(1024 * 1024), 1, RoundingMode.HALF_UP));

                    model.setBiko(rs.getString("CAC_BIKO"));

                    model.setAccountGroupCount(rs.getInt("DNT_COUNT"));
                    model.setAccountUserCount(rs.getInt("USR_COUNT"));

                    ret.add(model);
                }
            } else {

                while (rs.next()) {
                    Cir150AccountModel model = new Cir150AccountModel(reqMdl);
                    model.setAccountSid(rs.getInt("CAC_SID"));

                    if (!StringUtil.isNullZeroStringSpace(rs.getString("USI_SEI"))
                            && !StringUtil.isNullZeroStringSpace(rs.getString("USI_MEI"))) {
                        model.setAccountName(
                                rs.getString("USI_SEI") + " " + rs.getString("USI_MEI"));
                        model.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
                    } else {
                        model.setAccountName(rs.getString("CAC_NAME"));
                    }

                    model.setAccountUserKbn(rs.getInt("CAC_TYPE"));

//                    //BからMBへ変換
//                    model.setDiskSizeUse(
//                            rs.getBigDecimal("SDS_SIZE").divide(
//                                    new BigDecimal(1024 * 1024), 1, RoundingMode.HALF_UP));

                    model.setBiko(rs.getString("CAC_BIKO"));
                    model.setAccountGroupCount(rs.getInt("DNT_COUNT"));
                    model.setAccountUserCount(rs.getInt("USR_COUNT"));

                    ret.add(model);
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
     * <br>[機  能] レコード件数を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param  searchMdl 検索パラメータモデル
     * @throws SQLException SQL実行例外
     * @return count 件数
     */
    public int recordCount(Cir150SearchModel searchMdl) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int count = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql(" count(*)");
            sql.addSql(" as CNT");
            sql.addSql(" from ");
            sql.addSql("   CIR_ACCOUNT");
            sql.addSql("   left join");
            sql.addSql("     (");
            sql.addSql("       select");
            sql.addSql("         CAC_SID,");
            sql.addSql("         count(GRP_SID) as DNT_COUNT,");
            sql.addSql("         count(USR_SID) as USR_COUNT");
            sql.addSql("       from");
            sql.addSql("         CIR_ACCOUNT_USER");
            sql.addSql("       group by");
            sql.addSql("         CAC_SID");
            sql.addSql("     ) ACCOUNT_USER_COUNT");
            sql.addSql("   on");
            sql.addSql("     CIR_ACCOUNT.CAC_SID = ACCOUNT_USER_COUNT.CAC_SID");
            sql.addSql("   left join");
            sql.addSql("     CMN_USRM_INF");
            sql.addSql("   on");
            sql.addSql("     CIR_ACCOUNT.USR_SID = CMN_USRM_INF.USR_SID");

            //検索条件
            sql = __setSqlWhere(sql, searchMdl);

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
     * <br>[機  能] SqlBufferに検索条件を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SqlBuffer
     * @param searchMdl 検索条件Model
     * @return SqlBuffer
     */
    private SqlBuffer __setSqlWhere(SqlBuffer sql, Cir150SearchModel searchMdl) {
        sql.addSql(" where");
        sql.addSql("   CIR_ACCOUNT.CAC_JKBN = ?");
        sql.addIntValue(GSConstCircular.CAC_JKBN_NORMAL);


        if (searchMdl.existSearchCondition()) {

            //キーワード
            if (!StringUtil.isNullZeroString(searchMdl.getKeyword())) {
                sql.addSql(" and");
                sql.addSql(" ( CIR_ACCOUNT.CAC_NAME like '%"
                        + JDBCUtil.escapeForLikeSearch(searchMdl.getKeyword())
                        + "%' ESCAPE '"
                        + JDBCUtil.def_esc
                        + "'");
                sql.addSql(" or");
                sql.addSql("   CMN_USRM_INF.USI_SEI like '%"
                        + JDBCUtil.escapeForLikeSearch(searchMdl.getKeyword())
                        + "%' ESCAPE '"
                        + JDBCUtil.def_esc
                        + "'  ");
                sql.addSql(" or");
                sql.addSql("   CMN_USRM_INF.USI_MEI like '%"
                        + JDBCUtil.escapeForLikeSearch(searchMdl.getKeyword())
                        + "%' ESCAPE '"
                        + JDBCUtil.def_esc
                        + "' ) ");
            }

            //グループSID
            if (searchMdl.getGrpSid() >= 0 && searchMdl.getUserSid() <= 0) {
                sql.addSql(" and");
                sql.addSql("     CIR_ACCOUNT.CAC_SID in (");
                sql.addSql("       select CAC_SID from CIR_ACCOUNT_USER");
                sql.addSql("       where CIR_ACCOUNT_USER.GRP_SID = ?");
                sql.addSql("       union all");
                sql.addSql("       select CAC_SID from");
                sql.addSql("         CMN_BELONGM,");
                sql.addSql("         CIR_ACCOUNT_USER");
                sql.addSql("       where");
                sql.addSql("         CMN_BELONGM.GRP_SID = ?");
                sql.addSql("       and");
                sql.addSql("         CMN_BELONGM.USR_SID = CIR_ACCOUNT_USER.USR_SID");
                sql.addSql("     )");
                sql.addIntValue(searchMdl.getGrpSid());
                sql.addIntValue(searchMdl.getGrpSid());
            }

            //ユーザSID
            if (searchMdl.getUserSid() > 0) {
                sql.addSql(" and");
                sql.addSql("     CIR_ACCOUNT.CAC_SID in (");
                sql.addSql("       select CAC_SID from CIR_ACCOUNT_USER");
                sql.addSql("       where CIR_ACCOUNT_USER.USR_SID = ?");
                sql.addSql("       union all");
                sql.addSql("       select CAC_SID from");
                sql.addSql("         CMN_BELONGM,");
                sql.addSql("         CIR_ACCOUNT_USER");
                sql.addSql("       where");
                sql.addSql("         CMN_BELONGM.USR_SID = ?");
                sql.addSql("       and");
                sql.addSql("         CMN_BELONGM.GRP_SID = CIR_ACCOUNT_USER.GRP_SID");
                sql.addSql("     )");
                sql.addIntValue(searchMdl.getUserSid());
                sql.addIntValue(searchMdl.getUserSid());
            }
        }

        return sql;
    }

    /**
     * <br>[機  能] 指定したアカウント名を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param accountList アカウントSIDリスト
     * @return アカウント名の一覧
     * @throws SQLException SQL実行時例外
     */
    public List<String> getAccountNameList(String[] accountList)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<String> ret = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CIR_ACCOUNT.CAC_NAME as CAC_NAME,");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI");
            sql.addSql(" from ");
            sql.addSql("     CIR_ACCOUNT");
            sql.addSql("   left join");
            sql.addSql("      CMN_USRM_INF");
            sql.addSql("   on");
            sql.addSql("      CIR_ACCOUNT.USR_SID = CMN_USRM_INF.USR_SID");
            sql.addSql(" where ");
            sql.addSql("   CIR_ACCOUNT.CAC_SID in (");
            sql.addSql("     ?");
            sql.addIntValue(Integer.parseInt(accountList[0]));
            for (String accountSid : accountList) {
                sql.addSql("     ,?");
                sql.addIntValue(Integer.parseInt(accountSid));
            }
            sql.addSql("   )");
            sql.addSql("   ORDER BY CIR_ACCOUNT.CAC_NAME ASC");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {

                if (!StringUtil.isNullZeroStringSpace(rs.getString("USI_SEI"))
                        && !StringUtil.isNullZeroStringSpace(rs.getString("USI_MEI"))) {
                    ret.add(
                            rs.getString("USI_SEI") + " " + rs.getString("USI_MEI"));
                } else {
                    ret.add(rs.getString("CAC_NAME"));
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
}