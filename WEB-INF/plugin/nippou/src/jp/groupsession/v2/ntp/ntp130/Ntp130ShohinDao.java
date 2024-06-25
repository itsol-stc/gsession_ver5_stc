package jp.groupsession.v2.ntp.ntp130;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.csv.CSVException;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.model.NtpShohinModel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 日報 商品一覧画面で使用する商品情報を取得するためのDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp130ShohinDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp130ShohinDao.class);
    /** RequestModel */
    private RequestModel reqMdl__ = null;

    /**
    * <p>Set Connection
    * @param con Connection
    */
   public Ntp130ShohinDao(Connection con) {
       super(con);
   }

    /**
     * <p>Set Connection
     * @param con Connection
     * @param reqMdl RequestModel
     */
    public Ntp130ShohinDao(Connection con, RequestModel reqMdl) {
        super(con);
        reqMdl__ = reqMdl;
    }
    /**
     * <p>Select NTP_SHOHIN All Data
     * @param model 検索用モデル
     * @return 商品件数
     * @throws SQLException SQL実行例外
     */
    public int getShohinCount(Ntp130SearchModel model) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int count = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(NHN_SID) as shohinCount");
            sql.addSql(" from ");
            sql.addSql("   NTP_SHOHIN");
            sql.addSql(" where ");
            sql.addSql("   NHN_SID != -1");
            sql = __createWhereSql(sql, model);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt("shohinCount");
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
     * <p>Select NTP_SHOHIN All Dat
     * @param model 検索用モデル
     * @param page ページ
     * @param maxCnt １ページの最大表示件数
     * @return List in NTP_SHOHINModel
     * @throws SQLException SQL実行例外
     */
    public List<Ntp130ShohinModel> select(Ntp130SearchModel model, int page, int maxCnt)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Ntp130ShohinModel> ret = new ArrayList<Ntp130ShohinModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NTP_SHOHIN.NHN_SID as NHN_SID,");
            sql.addSql("   NTP_SHOHIN.NHN_CODE as NHN_CODE,");
            sql.addSql("   NTP_SHOHIN.NHN_NAME as NHN_NAME,");
            sql.addSql("   NTP_SHOHIN.NHN_PRICE_SALE as NHN_PRICE_SALE,");
            sql.addSql("   NTP_SHOHIN.NHN_PRICE_COST as NHN_PRICE_COST,");
            sql.addSql("   NTP_SHOHIN.NHN_HOSOKU as NHN_HOSOKU,");
            sql.addSql("   NTP_SHOHIN.NHN_AUID as NHN_AUID,");
            sql.addSql("   NTP_SHOHIN.NHN_ADATE as NHN_ADATE,");
            sql.addSql("   NTP_SHOHIN.NHN_EUID as NHN_EUID,");
            sql.addSql("   NTP_SHOHIN.NHN_EDATE as NHN_EDATE,");
            sql.addSql("   NTP_SHOHIN_CATEGORY.NSC_NAME as NSC_NAME");
            sql.addSql(" from ");
            sql.addSql("   NTP_SHOHIN");
            sql.addSql(" LEFT JOIN NTP_SHOHIN_CATEGORY");
            sql.addSql(" ON NTP_SHOHIN.NSC_SID = NTP_SHOHIN_CATEGORY.NSC_SID");
            sql.addSql(" where ");
            sql.addSql("   NHN_SID != -1");
            sql = __createWhereSql(sql, model);

            sql.addSql(" order by ");
            int sort1 = model.getSortKey1();
            int order1 = model.getOrderKey1();
            int sort2 = model.getSortKey2();
            int order2 = model.getOrderKey2();

            sql = __createOrderSql(sql, sort1, order1);
            sql.addSql("   ,");
            sql = __createOrderSql(sql, sort2, order2);

            pstmt = con.prepareStatement(sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            if (page > 1) {
                rs.absolute((page - 1) * maxCnt);
            }

            for (int i = 0; rs.next() && i < maxCnt; i++) {
                ret.add(__getNtpShohinFromRs(rs));
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
     * <br>[機  能] 商品の検索条件SQL文を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SQLBuffer
     * @param model 検索モデル
     * @return SQLBuffer
     * @throws SQLException SQL実行時例外
     */
    private SqlBuffer __createWhereSql(SqlBuffer sql, Ntp130SearchModel model) throws SQLException {


        int categorySid = model.getNscSid();
        if (categorySid > 0) {
            sql.addSql("   and");
            sql.addSql("   NTP_SHOHIN.NSC_SID = ?");
            sql.addIntValue(categorySid);
        }


        String code = model.getNhnCode();
        if (code != null && !code.equals("")) {
            sql.addSql("   and");
            sql.addSql("   NTP_SHOHIN.NHN_CODE = ?");
            sql.addStrValue(code);
        }

        String name = model.getNhnName();
        if (name != null && !name.equals("")) {
            sql.addSql("   and");
            sql.addSql("   NTP_SHOHIN.NHN_NAME like '%"
                    + JDBCUtil.escapeForLikeSearch(name)
                    + "%' ESCAPE '"
                    + JDBCUtil.def_esc
                    + "'");
        }

        int sale = model.getNhnPriceSale();
        if (sale != -1) {
            sql.addSql("   and");
            int saleKbn = model.getNhnPriceSaleKbn();
        if (saleKbn == Ntp130Biz.PRICE_MORE) {
                sql.addSql("   NTP_SHOHIN.NHN_PRICE_SALE >= ?");
        } else {
                sql.addSql("   NTP_SHOHIN.NHN_PRICE_SALE <= ?");
        }
            sql.addIntValue(sale);
        }

        int cost = model.getNhnPriceCost();
        if (cost != -1) {
            sql.addSql("   and");
            int costKbn = model.getNhnPriceCostKbn();
        if (costKbn == Ntp130Biz.PRICE_MORE) {
                sql.addSql("   NTP_SHOHIN.NHN_PRICE_COST >= ?");
        } else {
                sql.addSql("   NTP_SHOHIN.NHN_PRICE_COST <= ?");
        }
            sql.addIntValue(cost);
        }
        return sql;
    }
    /**
     * <br>[機  能] 商品のオーダSQL文を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SQLBuffer
     * @param sort ソートキー
     * @param order オーダキー
     * @return SQLBuffer
     * @throws SQLException SQL実行時例外
     */
    private SqlBuffer __createOrderSql(SqlBuffer sql, int sort, int order) throws SQLException {
        String orderStr = "desc";
        if (order == GSConstNippou.ORDER_KEY_ASC) {
            orderStr = "asc";
        }
        switch (sort) {
            case GSConstNippou.SORT_KEY_NHK_CODE:
                sql.addSql("   NTP_SHOHIN.NHN_CODE " + orderStr);
                break;
            case GSConstNippou.SORT_KEY_NHK_NAME:
                sql.addSql("   NTP_SHOHIN.NHN_NAME " + orderStr);
                break;
            case GSConstNippou.SORT_KEY_NHK_PRICE_SALE:
                sql.addSql("   NTP_SHOHIN.NHN_PRICE_SALE " + orderStr);
                break;
            case GSConstNippou.SORT_KEY_NHK_PRICE_COST:
                sql.addSql("   NTP_SHOHIN.NHN_PRICE_COST " + orderStr);
                break;
            case GSConstNippou.SORT_KEY_NHK_ADATE:
                sql.addSql("   NTP_SHOHIN.NHN_ADATE " + orderStr);
                break;
            case GSConstNippou.SORT_KEY_NHK_EDATE:
                sql.addSql("   NTP_SHOHIN.NHN_EDATE " + orderStr);
                break;
            default:
                sql.addSql("   NTP_SHOHIN.NHN_CODE asc");
                break;
        }
        return sql;
    }
    /**
     * <p>Create NTP_SHOHIN Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created NtpShohinModel
     * @throws SQLException SQL実行例外
     */
    private Ntp130ShohinModel __getNtpShohinFromRs(ResultSet rs) throws SQLException {
        Ntp130ShohinModel bean = new Ntp130ShohinModel();
        bean.setNhnSid(rs.getInt("NHN_SID"));
        bean.setNhnCode(rs.getString("NHN_CODE"));
        bean.setNhnName(rs.getString("NHN_NAME"));
        bean.setNhnPriceSale(rs.getInt("NHN_PRICE_SALE"));
        bean.setNhnPriceCost(rs.getInt("NHN_PRICE_COST"));

        String money = StringUtil.toCommaFormat(String.valueOf(bean.getNhnPriceSale()));
        bean.setNtp130PriceSale(money);

        money = StringUtil.toCommaFormat(String.valueOf(bean.getNhnPriceCost()));
        bean.setNtp130PriceCost(money);

        bean.setNhnHosoku(rs.getString("NHN_HOSOKU"));
        bean.setNhnAuid(rs.getInt("NHN_AUID"));
        bean.setNhnAdate(UDate.getInstanceTimestamp(rs.getTimestamp("NHN_ADATE")));
        bean.setNhnEuid(rs.getInt("NHN_EUID"));
        bean.setNhnEdate(UDate.getInstanceTimestamp(rs.getTimestamp("NHN_EDATE")));
        bean.setNscName(rs.getString("NSC_NAME"));

        String aDate = UDateUtil.getYymdJ(bean.getNhnAdate(), reqMdl__);
        bean.setNtp130ADate(aDate);

        String eDate = UDateUtil.getYymdJ(bean.getNhnEdate(), reqMdl__);
        bean.setNtp130EDate(eDate);

        return bean;
    }


    /**
     * <p>グループ情報CSVを出力する。
     * @param rl UsrCsvRecordListenerImpl
     * @param model Ntp130SearchModel
     * @throws SQLException SQL実行例外
     * @throws CSVException CSV出力時例外
     */
    public void selectShohinInfoForCsv(ShohinCsvRecordListenerImpl rl, Ntp130SearchModel model)
        throws SQLException, CSVException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NHN_SID,");
            sql.addSql("   NHN_CODE,");
            sql.addSql("   NHN_NAME,");
            sql.addSql("   NHN_PRICE_SALE,");
            sql.addSql("   NHN_PRICE_COST,");
            sql.addSql("   NHN_HOSOKU,");
            sql.addSql("   NHN_AUID,");
            sql.addSql("   NHN_ADATE,");
            sql.addSql("   NHN_EUID,");
            sql.addSql("   NHN_EDATE");
            sql.addSql(" from ");
            sql.addSql("   NTP_SHOHIN");
            sql.addSql(" where ");
            sql.addSql("   NHN_SID != -1");
            sql = __createWhereSql(sql, model);

            sql.addSql(" order by ");
            int sort1 = model.getSortKey1();
            int order1 = model.getOrderKey1();
            int sort2 = model.getSortKey2();
            int order2 = model.getOrderKey2();

            sql = __createOrderSql(sql, sort1, order1);
            sql.addSql("   ,");
            sql = __createOrderSql(sql, sort2, order2);
            pstmt = con.prepareStatement(sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                setUsrCsvRecordFromRs(rs, rl);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
    }
    /**
     * <p>結果セットからGroupCsvModelを取得する。
     * @param rs 結果セット
     * @param rl UsrCsvRecordListenerImpl
     * @throws SQLException SQL実行例外
     * @throws CSVException CSV出力時例外
     */
    public static void setUsrCsvRecordFromRs(ResultSet rs, ShohinCsvRecordListenerImpl rl)
        throws SQLException, CSVException {

      NtpShohinModel bean = new NtpShohinModel();
      bean.setNhnCode(rs.getString("NHN_CODE"));
      bean.setNhnName(rs.getString("NHN_NAME"));
      bean.setNhnPriceSale(rs.getInt("NHN_PRICE_SALE"));
      bean.setNhnPriceCost(rs.getInt("NHN_PRICE_COST"));
      bean.setNhnHosoku(rs.getString("NHN_HOSOKU"));

        rl.setRecord(bean);
    }
}
