package jp.groupsession.v2.ntp.ntp200;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.adr.biz.AddressBiz;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.dao.NtpAnShohinDao;
import jp.groupsession.v2.ntp.dao.NtpShohinDao;
import jp.groupsession.v2.ntp.model.NtpShohinModel;
import jp.groupsession.v2.ntp.ntp060.Ntp060AnkenDao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 日報 案件検索画面で使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp200AnkenDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp200AnkenDao.class);

    /**
     * <p>Default Constructor
     */
    public Ntp200AnkenDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Ntp200AnkenDao(Connection con) {
        super(con);
    }

    /**
     * <p>Select NTP_ANKEN All Data
     * @param model 検索用モデル
     * @return 案件件数
     * @throws SQLException SQL実行例外
     */
    public int getAnkenCount(Ntp200SearchModel model) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int count = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(NTP_ANKEN.NAN_SID) as ankenCount");
            sql = __createFromSql(sql);
            sql.addSql(" where ");
            if (model.getNanSid() > 0) {
                sql.addSql("   NTP_ANKEN.NAN_SID = ?");
                sql.addIntValue(model.getNanSid());
            } else {
                sql.addSql("   NTP_ANKEN.NAN_SID != -1");
            }
            sql = __createWhereSql(sql, model);
            sql.addSql(" group by NTP_ANKEN.NAN_SID");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                count++;
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
     * <p>Select NTP_SHOHIN All Data
     * @param model 検索用モデル
     * @param page ページ
     * @param maxCnt １ページの最大表示件数
     * @param reqMdl RequestModel
     * @return List in NTP_SHOHINModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Ntp200AnkenModel> select(Ntp200SearchModel model, int page, int maxCnt,
                                RequestModel reqMdl) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Ntp200AnkenModel> resultList = new ArrayList<Ntp200AnkenModel>();
        ArrayList<Ntp200AnkenModel> ret = new ArrayList<Ntp200AnkenModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NTP_ANKEN.NAN_SID,");
            sql.addSql("   NTP_ANKEN.NAN_CODE,");
            sql.addSql("   NTP_ANKEN.NAN_NAME,");
            sql.addSql("   NTP_ANKEN.NAN_DETIAL,");
            sql.addSql("   NTP_ANKEN.NAN_DATE,");
            sql.addSql("   NTP_ANKEN.ACO_SID,");
            sql.addSql("   NTP_ANKEN.ABA_SID,");
            sql.addSql("   NTP_ANKEN.NGP_SID,");
            sql.addSql("   NTP_ANKEN.NAN_MIKOMI,");
            sql.addSql("   NTP_ANKEN.NAN_KIN_MITUMORI,");
            sql.addSql("   NTP_ANKEN.NAN_KIN_JUTYU,");
            sql.addSql("   NTP_ANKEN.NAN_SYODAN,");
            sql.addSql("   NTP_ANKEN.NAN_STATE,");
            sql.addSql("   NTP_ANKEN.NCN_SID,");
            sql.addSql("   ANKEN_COMPANY.ACO_CODE,");
            sql.addSql("   ANKEN_COMPANY.ACO_NAME,");
            sql.addSql("   ANKEN_COMPANY.ACO_NAME_KN,");
            sql.addSql("   ANKEN_COMPANY_BASE.ABA_NAME,");
            sql.addSql("   ANKEN_COMPANY_BASE.ABA_TYPE,");
            sql.addSql("   ANKEN_PROCESS.NGP_NAME,");
            sql.addSql("   ANKEN_PROCESS.NGY_NAME,");
            sql.addSql("   ANKEN_CONTACT.NCN_NAME");
            sql = __createFromSql(sql);

            sql.addSql(" where ");
            if (model.getNanSid() > 0) {
                sql.addSql("   NTP_ANKEN.NAN_SID = ?");
                sql.addIntValue(model.getNanSid());
            } else {
                sql.addSql("   NTP_ANKEN.NAN_SID != -1");
            }
            sql = __createWhereSql(sql, model);
            sql.addSql(" order by ");
            int sort1 = model.getSortKey1();
            int order1 = model.getOrderKey1();
//            int sort2 = model.getSortKey2();
//            int order2 = model.getOrderKey2();

            sql = __createOrderSql(sql, sort1, order1);
            sql.addSql("   ,");
            sql.addSql("   NTP_ANKEN.NAN_SID desc");

            pstmt = con.prepareStatement(sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            NtpAnShohinDao anShohinDao = new NtpAnShohinDao(con);

            Map<Integer, Integer> nanMap = new HashMap<Integer, Integer>();

            int ankenStartNum = 0;
            int ankenMaxCnt = maxCnt;

            if (page > 1) {
                ankenStartNum = (page - 1) * maxCnt;
                ankenMaxCnt = page * maxCnt;
            }

            while (rs.next()) {

                int selNanSid = rs.getInt("NAN_SID");

                if (nanMap.get(selNanSid) == null) {

                    Ntp200AnkenModel ankenModel = null;
                    ankenModel = __getNtpAnkenFromRs(rs, reqMdl);

                    //商品情報を取得
                    String[] nhnSids = anShohinDao.select(rs.getInt("NAN_SID"));
                    if (nhnSids.length > 0) {
                        NtpShohinDao shohinDao = new NtpShohinDao(con);
                        ArrayList<NtpShohinModel> shohinList
                            = (ArrayList<NtpShohinModel>) shohinDao.select(nhnSids);
                        if (!shohinList.isEmpty()) {
                            ankenModel.setNtp200ShohinList(shohinList);
                        }
                    }

//                    ankenModel.setNtp200Edate(
//                            ankenModel.getNanEdate().getStrYear()
//                            + "年"
//                            + ankenModel.getNanEdate().getStrMonth()
//                            + "月"
//                            + ankenModel.getNanEdate().getStrDay()
//                            + "日");

                    resultList.add(ankenModel);

                    nanMap.put(selNanSid, selNanSid);

                }
            }

            if (!resultList.isEmpty()) {
                for (int i = ankenStartNum; i < ankenMaxCnt && i < resultList.size(); i++) {
                    if (resultList.get(i) != null) {
                        ret.add(resultList.get(i));
                    }
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
     * <br>[機  能] 案件のFromSQL文を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SQLBuffer
     * @return SQLBuffer
     * @throws SQLException SQL実行時例外
     */
    private SqlBuffer __createFromSql(SqlBuffer sql) throws SQLException {
        sql.addSql(" from ");
        sql.addSql("   NTP_ANKEN");

        sql.addSql("   left join (");
        sql.addSql("     select");
        sql.addSql("       NTP_AN_SHOHIN.NAN_SID,");
        sql.addSql("       NTP_SHOHIN.NHN_NAME,");
        sql.addSql("       NTP_SHOHIN.NSC_SID");
        sql.addSql("     from");
        sql.addSql("       NTP_AN_SHOHIN,");
        sql.addSql("       NTP_SHOHIN");
        sql.addSql("     where");
        sql.addSql("       NTP_AN_SHOHIN.NHN_SID = NTP_SHOHIN.NHN_SID");
        sql.addSql("   ) ANKEN_SHOHIN");
        sql.addSql("   on NTP_ANKEN.NAN_SID = ANKEN_SHOHIN.NAN_SID");

        sql.addSql("   left join (");
        sql.addSql("     select");
        sql.addSql("       NTP_PROCESS.NGP_SID,");
        sql.addSql("       NTP_PROCESS.NGP_NAME,");
        sql.addSql("       NTP_GYOMU.NGY_NAME");
        sql.addSql("     from");
        sql.addSql("       NTP_PROCESS,");
        sql.addSql("       NTP_GYOMU");
        sql.addSql("     where");
        sql.addSql("       NTP_PROCESS.NGY_SID = NTP_GYOMU.NGY_SID");
        sql.addSql("   ) ANKEN_PROCESS");
        sql.addSql("   on NTP_ANKEN.NGP_SID = ANKEN_PROCESS.NGP_SID");

        sql.addSql("   left join (");
        sql.addSql("     select");
        sql.addSql("       NTP_CONTACT.NCN_SID,");
        sql.addSql("       NTP_CONTACT.NCN_NAME");
        sql.addSql("     from");
        sql.addSql("       NTP_CONTACT");
        sql.addSql("   ) ANKEN_CONTACT");
        sql.addSql("   on NTP_ANKEN.NCN_SID = ANKEN_CONTACT.NCN_SID");

        sql.addSql("   left join (");
        sql.addSql("     select");
        sql.addSql("       ADR_COMPANY.ACO_SID,");
        sql.addSql("       ADR_COMPANY.ACO_CODE,");
        sql.addSql("       ADR_COMPANY.ACO_NAME,");
        sql.addSql("       ADR_COMPANY.ACO_NAME_KN");
        sql.addSql("     from");
        sql.addSql("       ADR_COMPANY");
        sql.addSql("   ) ANKEN_COMPANY");
        sql.addSql("   on NTP_ANKEN.ACO_SID = ANKEN_COMPANY.ACO_SID");

        sql.addSql("   left join (");
        sql.addSql("     select");
        sql.addSql("       ADR_COMPANY_BASE.ABA_SID,");
        sql.addSql("       ADR_COMPANY_BASE.ABA_TYPE,");
        sql.addSql("       ADR_COMPANY_BASE.ABA_NAME");
        sql.addSql("     from");
        sql.addSql("       ADR_COMPANY_BASE");
        sql.addSql("   ) ANKEN_COMPANY_BASE");
        sql.addSql("   on NTP_ANKEN.ABA_SID = ANKEN_COMPANY_BASE.ABA_SID");
        return sql;
    }
    /**
     * <br>[機  能] 案件の検索条件SQL文を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SQLBuffer
     * @param model 検索モデル
     * @return SQLBuffer
     * @throws SQLException SQL実行時例外
     */
    private SqlBuffer __createWhereSql(SqlBuffer sql, Ntp200SearchModel model) throws SQLException {

        //閲覧権限
        if (!model.isAdminSearch()) {
            Ntp060AnkenDao.addPermissionWhereSQL(sql,
                    model.getSessionUsrSid(),
                    model.getPermitKbn());
        }

        //状態が未完了の案件
        if (model.getNtp200State() != -1) {
            sql.addSql("   and");
            sql.addSql("   NTP_ANKEN.NAN_STATE="
                    + model.getNtp200State());
        }

        //商談結果
        if (model.getNtp200Syodan() != -1) {
            sql.addSql("   and");
            sql.addSql("   NTP_ANKEN.NAN_SYODAN="
                    + model.getNtp200Syodan());
        }


        //案件コード
        String code = model.getNanCode();
        if (code != null && !code.equals("")) {
            sql.addSql("   and");
            sql.addSql("   NTP_ANKEN.NAN_CODE = ?");
            sql.addStrValue(code);
        }

        //案件名
        String name = model.getNanName();
        if (name != null && !name.equals("")) {
            sql.addSql("   and");
            sql.addSql("   NTP_ANKEN.NAN_NAME like '%"
                    + JDBCUtil.escapeForLikeSearch(name)
                    + "%' ESCAPE '"
                    + JDBCUtil.def_esc
                    + "'");
        }

        //企業コード
        code = model.getNtp200AcoCode();
        if (code != null && !code.equals("")) {
            sql.addSql("   and");
            sql.addSql("   ANKEN_COMPANY.ACO_CODE = ?");
            sql.addStrValue(code);
        }

        //会社名
        name = model.getNtp200AcoName();
        if (name != null && !name.equals("")) {
            sql.addSql("   and");
            sql.addSql("   ANKEN_COMPANY.ACO_NAME like '%"
                    + JDBCUtil.escapeForLikeSearch(name)
                    + "%' ESCAPE '"
                    + JDBCUtil.def_esc
                    + "'");
        }

        //会社名カナ
        name = model.getNtp200AcoNameKana();
        if (name != null && !name.equals("")) {
            sql.addSql("   and");
            sql.addSql("   ANKEN_COMPANY.ACO_NAME_KN like '%"
                    + JDBCUtil.escapeForLikeSearch(name)
                    + "%' ESCAPE '"
                    + JDBCUtil.def_esc
                    + "'");
        }

        //拠点名
        name = model.getNtp200AbaName();
        if (name != null && !name.equals("")) {
            sql.addSql("   and");
            sql.addSql("   ANKEN_COMPANY_BASE.ABA_NAME like '%"
                    + JDBCUtil.escapeForLikeSearch(name)
                    + "%' ESCAPE '"
                    + JDBCUtil.def_esc
                    + "'");
        }

        //商品カテゴリ
        int category = model.getNtp200ShohinCategory();
        if (category > 0) {
            sql.addSql("   and");
            sql.addSql("   ANKEN_SHOHIN.NSC_SID = " + category);
        }

        //商品名
        name = model.getNtp200ShohinName();
        if (name != null && !name.equals("")) {
            sql.addSql("   and");
            sql.addSql("   ANKEN_SHOHIN.NHN_NAME like '%"
                    + JDBCUtil.escapeForLikeSearch(name)
                    + "%' ESCAPE '"
                    + JDBCUtil.def_esc
                    + "'");
        }

        return sql;
    }
    /**
     * <br>[機  能] 案件のオーダSQL文を設定する
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
            case GSConstNippou.ANKEN_SEARCH_SORT_ANKEN:
                sql.addSql("   NTP_ANKEN.NAN_NAME " + orderStr);
                break;
            case GSConstNippou.ANKEN_SEARCH_SORT_COMPANY:
                sql.addSql("   ANKEN_COMPANY.ACO_NAME " + orderStr);
                break;
            case GSConstNippou.ANKEN_SEARCH_SORT_KOUSHIN:
                sql.addSql("   NTP_ANKEN.NAN_DATE " + orderStr);
                break;
            case GSConstNippou.ANKEN_SEARCH_SORT_SYODAN:
                sql.addSql("   NTP_ANKEN.NAN_SYODAN " + orderStr);
                break;
            default:
                sql.addSql("   NTP_ANKEN.NAN_DATE desc");
                break;
        }
        return sql;
    }
    /**
     * <p>Create NTP_SHOHIN Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @param reqMdl RequestModel
     * @return created NtpShohinModel
     * @throws SQLException SQL実行例外
     */
    private Ntp200AnkenModel __getNtpAnkenFromRs(ResultSet rs, RequestModel reqMdl)
    throws SQLException {
        Ntp200AnkenModel bean = new Ntp200AnkenModel();
        bean.setNanSid(rs.getInt("NAN_SID"));
        bean.setNanCode(rs.getString("NAN_CODE"));
        bean.setNanName(rs.getString("NAN_NAME"));
        bean.setNanDetial(rs.getString("NAN_DETIAL"));
        bean.setAcoSid(rs.getInt("ACO_SID"));
        bean.setAbaSid(rs.getInt("ABA_SID"));
        bean.setNgpSid(rs.getInt("NGP_SID"));

        bean.setNanMikomi(rs.getInt("NAN_MIKOMI"));
        bean.setNanKinMitumori(rs.getInt("NAN_KIN_MITUMORI"));
        bean.setNanKinJutyu(rs.getInt("NAN_KIN_JUTYU"));
        bean.setNanSyodan(rs.getInt("NAN_SYODAN"));
        bean.setNanState(rs.getInt("NAN_STATE"));
        bean.setNcnSid(rs.getInt("NCN_SID"));
        bean.setNanDate(UDate.getInstanceTimestamp(rs.getTimestamp("NAN_DATE")));
        bean.setNtp200Date(UDateUtil.getSlashYYMD(bean.getNanDate()));
        String money = StringUtil.toCommaFormat(String.valueOf(bean.getNanKinMitumori()));
        bean.setNtp200KinMitumori(money);

        money = StringUtil.toCommaFormat(String.valueOf(bean.getNanKinJutyu()));
        bean.setNtp200KinJutyu(money);

        bean.setNtp200CompanyName(rs.getString("ACO_NAME"));
        bean.setNtp200CompanyCode(rs.getString("ACO_CODE"));
        String companyBaseName = rs.getString("ABA_NAME");
        String companyBaseType
            = AddressBiz.getCompanyBaseTypeName(rs.getInt("ABA_TYPE"), reqMdl);
        if (!StringUtil.isNullZeroString(companyBaseType)) {
            companyBaseName = companyBaseType + " ： " + companyBaseName;
        }
        bean.setNtp200BaseName(companyBaseName);

        bean.setNtp200GyomuName(rs.getString("NGY_NAME"));
        bean.setNtp200ProcessName(rs.getString("NGP_NAME"));
        bean.setNtp200ContactName(rs.getString("NCN_NAME"));

        return bean;
    }
}
