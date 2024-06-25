package jp.groupsession.v2.ntp.ntp060;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.csv.CSVException;
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 日報 案件情報の検索を行うDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp060AnkenDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp060AnkenDao.class);

    /**
     * <p>Default Constructor
     */
    public Ntp060AnkenDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Ntp060AnkenDao(Connection con) {
        super(con);
    }
    /**
     * <p>Select NTP_SHOHIN All Data
     * @param model 検索用モデル
     * @param sessionUserSid セッションユーザSID(実行者)
     * @param rl SchCsvRecordListenerIppanImpl
     * @return int 明細件数
     * @param reqMdl RequestModel
     * @throws SQLException SQL実行例外
     * @throws CSVException CSV出力時例外
     */
    public int createAnkenForCsv(Ntp060SearchModel model,
        int sessionUserSid, Ntp060CsvRecordListenerIppanImpl rl,
        RequestModel reqMdl)
        throws SQLException, CSVException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   distinct ");
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
            sql.addSql("   NTP_ANKEN.NAN_MITUMORI_DATE,");
            sql.addSql("   NTP_ANKEN.NAN_JUTYU_DATE,");
            sql.addSql("   NTP_ANKEN.NAN_MIKOMI,");
            sql.addSql("   NTP_ANKEN.NAN_SYODAN,");
            sql.addSql("   NTP_ANKEN.NAN_STATE,");
            sql.addSql("   NTP_ANKEN.NCN_SID,");
            sql.addSql("   NTP_ANKEN.NAN_EDATE,");
            sql.addSql("   ANKEN_COMPANY.ACO_CODE,");
            sql.addSql("   ANKEN_COMPANY.ACO_NAME,");
            sql.addSql("   ANKEN_COMPANY.ACO_NAME_KN,");
            sql.addSql("   ANKEN_COMPANY_BASE.ABA_NAME,");
            sql.addSql("   ANKEN_COMPANY_BASE.ABA_TYPE,");
            sql.addSql("   ANKEN_PROCESS.NGP_NAME,");
            sql.addSql("   ANKEN_PROCESS.NGP_CODE,");
            sql.addSql("   ANKEN_PROCESS.NGY_NAME,");
            sql.addSql("   ANKEN_PROCESS.NGY_CODE,");
            sql.addSql("   ANKEN_CONTACT.NCN_NAME,");
            sql.addSql("   ANKEN_CONTACT.NCN_CODE");

            sql = __createFromSql(sql);

            sql.addSql(" where ");
            sql.addSql("   NTP_ANKEN.NAN_SID != -1");
            sql = __createWhereSql(sql, model);

            sql.addSql(" order by ");
            int sort1 = model.getSortKey1();
            int order1 = model.getOrderKey1();
            int sort2 = model.getSortKey2();
            int order2 = model.getOrderKey2();

            sql = __createOrderSql(sql, sort1, order1);
            sql.addSql("   ,");
            sql = __createOrderSql(sql, sort2, order2);

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret++;
                Ntp060AnkenModel ankenmodel = __getAnkenCsvFromRs(rs, reqMdl);
                setNtpCsvRecordFromSchDataModel(ankenmodel, sessionUserSid, rl);
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
     * <p>Select NTP_ANKEN All Data
     * @param model 検索用モデル
     * @return 案件件数
     * @throws SQLException SQL実行例外
     */
    public int getAnkenCount(Ntp060SearchModel model) throws SQLException {
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
            sql.addSql("   NTP_ANKEN.NAN_SID != -1");
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
    public List<Ntp060AnkenModel> select(Ntp060SearchModel model, int page, int maxCnt,
            RequestModel reqMdl) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Ntp060AnkenModel> resultList = new ArrayList<Ntp060AnkenModel>();
        ArrayList<Ntp060AnkenModel> ret = new ArrayList<Ntp060AnkenModel>();
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
            sql.addSql("   NTP_ANKEN.NAN_EDATE,");
            sql.addSql("   ANKEN_COMPANY.ACO_CODE,");
            sql.addSql("   ANKEN_COMPANY.ACO_NAME,");
            sql.addSql("   ANKEN_COMPANY.ACO_NAME_KN,");
            sql.addSql("   ANKEN_COMPANY_BASE.ABA_NAME,");
            sql.addSql("   ANKEN_COMPANY_BASE.ABA_TYPE,");
            sql.addSql("   ANKEN_PROCESS.NGP_NAME,");
            sql.addSql("   ANKEN_PROCESS.NGY_NAME,");
            sql.addSql("   ANKEN_CONTACT.NCN_NAME,");
            sql.addSql("   NTP_ANKEN.NAN_MITUMORI_DATE,");
            sql.addSql("   NTP_ANKEN.NAN_JUTYU_DATE");
            sql = __createFromSql(sql);

            sql.addSql(" where ");
            sql.addSql("   NTP_ANKEN.NAN_SID != -1");
            sql = __createWhereSql(sql, model);
            sql.addSql(" order by ");
            int sort1 = model.getSortKey1();
            int order1 = model.getOrderKey1();

            sql = __createOrderSql(sql, sort1, order1);
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

                    Ntp060AnkenModel ankenModel = null;
                    ankenModel = __getNtpAnkenFromRs(rs, reqMdl);

                    //商品情報を取得
                    String[] nhnSids = anShohinDao.select(rs.getInt("NAN_SID"));
                    if (nhnSids.length > 0) {
                        NtpShohinDao shohinDao = new NtpShohinDao(con);
                        ArrayList<NtpShohinModel> shohinList
                            = (ArrayList<NtpShohinModel>) shohinDao.select(nhnSids);
                        if (!shohinList.isEmpty()) {
                            ankenModel.setNtp060ShohinList(shohinList);
                        }
                    }

                    ankenModel.setNtp060Edate(
                            ankenModel.getNanEdate().getStrYear()
                            + "年"
                            + ankenModel.getNanEdate().getStrMonth()
                            + "月"
                            + ankenModel.getNanEdate().getStrDay()
                            + "日");

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
        sql.addSql("       NTP_PROCESS.NGP_CODE,");
        sql.addSql("       NTP_PROCESS.NGP_NAME,");
        sql.addSql("       NTP_GYOMU.NGY_NAME,");
        sql.addSql("       NTP_GYOMU.NGY_CODE");
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
        sql.addSql("       NTP_CONTACT.NCN_CODE,");
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
    *
    * <br>[機  能] SQLに閲覧権限制限用Where句を追加
    * <br>[解  説]
    * <br>[備  考]
    * @param sql 追記先SQLBuffer
    * @param permKbn 権限区分
    * @param sessionUserSid ユーザSID
    */
    public static void addPermissionWhereSQL(SqlBuffer sql, int sessionUserSid, int permKbn) {

        sql.addSql("   and (");
        if (permKbn == GSConst.SP_AUTH_EDIT) {
            sql.addSql(" NTP_ANKEN.NAN_PERMIT_EDIT = ?");
        } else {
            sql.addSql(" NTP_ANKEN.NAN_PERMIT_VIEW = ?");
        }
        sql.addIntValue(GSConstNippou.NAP_KBN_ALL);
        sql.addSql("   or ");
        sql.addSql("     exists (");
        sql.addSql("       select NAN_SID from NTP_AN_MEMBER");
        sql.addSql("       where NTP_AN_MEMBER.USR_SID = ?");
        sql.addIntValue(sessionUserSid);
        sql.addSql("       and NTP_AN_MEMBER.NAN_SID = NTP_ANKEN.NAN_SID");
        sql.addSql("     )");
        sql.addSql("   or");
        sql.addSql("     (");
        if (permKbn == GSConst.SP_AUTH_EDIT) {
            sql.addSql(" NTP_ANKEN.NAN_PERMIT_EDIT = ?");
        } else {
            sql.addSql(" NTP_ANKEN.NAN_PERMIT_VIEW = ?");
        }
        sql.addIntValue(GSConstNippou.NAP_KBN_USERGROUP);
        sql.addSql("     and");
        sql.addSql("       exists (");
        sql.addSql("         select NAN_SID from NTP_ANKEN_PERMIT");
        sql.addSql("         where");
        sql.addSql("           NTP_ANKEN_PERMIT.GRP_SID in (");
        sql.addSql("             select GRP_SID from CMN_BELONGM");
        sql.addSql("             where CMN_BELONGM.USR_SID = ?");
        sql.addIntValue(sessionUserSid);
        sql.addSql("           )");
        sql.addSql("           and NTP_ANKEN_PERMIT.NAN_SID = NTP_ANKEN.NAN_SID");
        if (permKbn == GSConst.SP_AUTH_EDIT) {
            sql.addSql("           and NTP_ANKEN_PERMIT.NAP_KBN = ?");
            sql.addIntValue(GSConst.SP_AUTH_EDIT);
        }
        sql.addSql("       )");
        sql.addSql("     )");
        sql.addSql("   or");
        sql.addSql("     (");
        sql.addSql("       NTP_ANKEN.NAN_PERMIT_VIEW = ?");
        sql.addIntValue(GSConstNippou.NAP_KBN_USERGROUP);
        sql.addSql("     and");
        sql.addSql("       exists (");
        sql.addSql("         select NAN_SID from NTP_ANKEN_PERMIT");
        sql.addSql("         where NTP_ANKEN_PERMIT.USR_SID = ?");
        sql.addIntValue(sessionUserSid);
        sql.addSql("           and NTP_ANKEN_PERMIT.NAN_SID = NTP_ANKEN.NAN_SID");
        if (permKbn == GSConst.SP_AUTH_EDIT) {
            sql.addSql("           and NTP_ANKEN_PERMIT.NAP_KBN = ?");
            sql.addIntValue(GSConst.SP_AUTH_EDIT);
        }
        sql.addSql("       )");
        sql.addSql("     )");
        sql.addSql("   )");




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
    private SqlBuffer __createWhereSql(SqlBuffer sql, Ntp060SearchModel model) throws SQLException {
        //閲覧権限
        if (!model.isAdminSearch()) {
            addPermissionWhereSQL(sql,
                    model.getSessionUsrSid(),
                    GSConst.SP_AUTH_VIEWONLY);
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
        code = model.getNtp060AcoCode();
        if (code != null && !code.equals("")) {
            sql.addSql("   and");
            sql.addSql("   ANKEN_COMPANY.ACO_CODE = ?");
            sql.addStrValue(code);
        }

        //会社名
        name = model.getNtp060AcoName();
        if (name != null && !name.equals("")) {
            sql.addSql("   and");
            sql.addSql("   ANKEN_COMPANY.ACO_NAME like '%"
                    + JDBCUtil.escapeForLikeSearch(name)
                    + "%' ESCAPE '"
                    + JDBCUtil.def_esc
                    + "'");
        }

        //会社名カナ
        name = model.getNtp060AcoNameKana();
        if (name != null && !name.equals("")) {
            sql.addSql("   and");
            sql.addSql("   ANKEN_COMPANY.ACO_NAME_KN like '%"
                    + JDBCUtil.escapeForLikeSearch(name)
                    + "%' ESCAPE '"
                    + JDBCUtil.def_esc
                    + "'");
        }

        //拠点名
        name = model.getNtp060AbaName();
        if (name != null && !name.equals("")) {
            sql.addSql("   and");
            sql.addSql("   ANKEN_COMPANY_BASE.ABA_NAME like '%"
                    + JDBCUtil.escapeForLikeSearch(name)
                    + "%' ESCAPE '"
                    + JDBCUtil.def_esc
                    + "'");
        }

        //商品カテゴリ
        int category = model.getNtp060ShohinCategory();
        if (category > 0) {
            sql.addSql("   and");
            sql.addSql("   ANKEN_SHOHIN.NSC_SID = " + category);
        }

        //商品名
        name = model.getNtp060ShohinName();
        if (name != null && !name.equals("")) {
            sql.addSql("   and");
            sql.addSql("   ANKEN_SHOHIN.NHN_NAME like '%"
                    + JDBCUtil.escapeForLikeSearch(name)
                    + "%' ESCAPE '"
                    + JDBCUtil.def_esc
                    + "'");
        }

        //プロセス
        int process = model.getNgpSid();
        if (process != 0 && process != -1) {
            sql.addSql("   and");
            sql.addSql("   NTP_ANKEN.NGP_SID = ?");
            sql.addIntValue(process);
        }

        //見込度
        String[] mikomi = model.getNtp060Mikomi();
        if (mikomi != null && mikomi.length > 0) {
            sql.addSql("   and");
            sql.addSql("   NTP_ANKEN.NAN_MIKOMI in (");
            for (int i = 0; i < mikomi.length; i++) {
                if (i > 0) {
                    sql.addSql("     , ");
                }
                sql.addSql("     ?");
                sql.addIntValue(Integer.parseInt(mikomi[i]));
            }
            sql.addSql("   )");
        }

        //商談結果
        String[] syodan = model.getNtp060Syodan();
        if (syodan != null && syodan.length > 0) {
            sql.addSql("   and");
            sql.addSql("   NTP_ANKEN.NAN_SYODAN in (");
            for (int i = 0; i < syodan.length; i++) {
                if (i > 0) {
                    sql.addSql("     , ");
                }
                sql.addSql("     ?");
                sql.addIntValue(Integer.parseInt(syodan[i]));
            }
            sql.addSql("   )");
        }

        //状態
        int state = model.getNtp060State();
        if (state != -1) {
            sql.addSql("   and");
            sql.addSql("   NTP_ANKEN.NAN_STATE =");
            sql.addSql(String.valueOf(state));
        }

        //コンタクト
        int contact = model.getNcnSid();
        if (contact != 0 && contact != -1) {
            sql.addSql("   and");
            sql.addSql("   NTP_ANKEN.NCN_SID = ?");
            sql.addIntValue(contact);
        }

        //見積金額
        int mitumori = model.getNanKinMitumori();
        if (mitumori != -1) {
            sql.addSql("   and");
            int mitumoriKbn = model.getNhnKinMitumoriKbn();
        if (mitumoriKbn == Ntp060Biz.PRICE_MORE) {
                sql.addSql("   NTP_ANKEN.NAN_KIN_MITUMORI >= ?");
        } else {
                sql.addSql("   NTP_ANKEN.NAN_KIN_MITUMORI <= ?");
        }
            sql.addIntValue(mitumori);
        }

        //受注金額
        int jutyu = model.getNanKinJutyu();
        if (jutyu != -1) {
            sql.addSql("   and");
            int jutyuKbn = model.getNhnKinJutyuKbn();
        if (jutyuKbn == Ntp060Biz.PRICE_MORE) {
                sql.addSql("   NTP_ANKEN.NAN_KIN_JUTYU >= ?");
        } else {
                sql.addSql("   NTP_ANKEN.NAN_KIN_JUTYU <= ?");
        }
            sql.addIntValue(jutyu);
        }

        //登録日時
        if (model.getNtp060FrDate() != null) {
            sql.addSql("  and");
            sql.addSql("    NTP_ANKEN.NAN_DATE >= ?");
            sql.addDateValue(model.getNtp060FrDate());
        }

        if (model.getNtp060ToDate() != null) {
            sql.addSql("  and");
            sql.addSql("    NTP_ANKEN.NAN_DATE <= ?");
            sql.addDateValue(model.getNtp060ToDate());
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
            case GSConstNippou.SORT_KEY_NAN_ANKEN:
                sql.addSql("   NTP_ANKEN.NAN_CODE " + orderStr);
                break;
            case GSConstNippou.SORT_KEY_NAN_COMPANY:
                sql.addSql("   ANKEN_COMPANY.ACO_NAME " + orderStr);
                break;
            case GSConstNippou.SORT_KEY_NAN_SHOHIN:
                sql.addSql("   ANKEN_SHOHIN.NHN_NAME " + orderStr);
                break;
            case GSConstNippou.SORT_KEY_NAN_GYOMU:
                sql.addSql("   ANKEN_PROCESS.NGY_NAME " + orderStr);
                break;
            case GSConstNippou.SORT_KEY_NAN_PROCESS:
                sql.addSql("   ANKEN_PROCESS.NGP_NAME " + orderStr);
                break;
            case GSConstNippou.SORT_KEY_NAN_MIKOMI:
                sql.addSql("   NTP_ANKEN.NAN_MIKOMI " + orderStr);
                break;
            case GSConstNippou.SORT_KEY_NAN_TOUROKU:
                sql.addSql("   NTP_ANKEN.NAN_DATE " + orderStr);
                break;
            case GSConstNippou.SORT_KEY_NAN_MITUMORI:
                sql.addSql("   NTP_ANKEN.NAN_KIN_MITUMORI " + orderStr);
                break;
            case GSConstNippou.SORT_KEY_NAN_JUTYU:
                sql.addSql("   NTP_ANKEN.NAN_KIN_JUTYU " + orderStr);
                break;
            case GSConstNippou.SORT_KEY_NAN_CONTACT:
                sql.addSql("   ANKEN_CONTACT.NCN_NAME " + orderStr);
                break;
            case GSConstNippou.SORT_KEY_NAN_SYODAN:
                sql.addSql("   NTP_ANKEN.NAN_SYODAN " + orderStr);
                break;
            case GSConstNippou.SORT_KEY_NAN_KOUSHIN:
                sql.addSql("   NTP_ANKEN.NAN_EDATE " + orderStr);
                break;
            case GSConstNippou.SORT_KEY_NAN_ANKEN_NAME:
                sql.addSql("   NTP_ANKEN.NAN_NAME " + orderStr);
                break;
            default:
                sql.addSql("   NTP_ANKEN.NAN_CODE asc");
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
    private Ntp060AnkenModel __getNtpAnkenFromRs(ResultSet rs, RequestModel reqMdl)
    throws SQLException {
        Ntp060AnkenModel bean = new Ntp060AnkenModel();
        bean.setNanSid(rs.getInt("NAN_SID"));
        bean.setNanCode(rs.getString("NAN_CODE"));
        bean.setNanName(rs.getString("NAN_NAME"));
        bean.setNanDetial(rs.getString("NAN_DETIAL"));
        bean.setAcoSid(rs.getInt("ACO_SID"));
        bean.setAbaSid(rs.getInt("ABA_SID"));
        bean.setNgpSid(rs.getInt("NGP_SID"));

        bean.setNanMikomi(rs.getInt("NAN_MIKOMI"));
        bean.setNanKinMitumori(rs.getInt("NAN_KIN_MITUMORI"));
        bean.setNtp060KinMitumori(StringUtil.toCommaFormat(
                       String.valueOf(rs.getInt("NAN_KIN_MITUMORI"))));
        bean.setNanKinJutyu(rs.getInt("NAN_KIN_JUTYU"));
        bean.setNtp060KinJutyu(StringUtil.toCommaFormat(
                String.valueOf(rs.getInt("NAN_KIN_JUTYU"))));
        bean.setNanSyodan(rs.getInt("NAN_SYODAN"));
        bean.setNanState(rs.getInt("NAN_STATE"));
        bean.setNcnSid(rs.getInt("NCN_SID"));
        bean.setNanDate(UDate.getInstanceTimestamp(rs.getTimestamp("NAN_DATE")));
        bean.setNtp060Date(UDateUtil.getSlashYYMD(bean.getNanDate()));
        bean.setNanEdate(UDate.getInstanceTimestamp(rs.getTimestamp("NAN_EDATE")));
        String money = StringUtil.toCommaFormat(String.valueOf(bean.getNanKinMitumori()));
        bean.setNtp060KinMitumori(money);

        money = StringUtil.toCommaFormat(String.valueOf(bean.getNanKinJutyu()));
        bean.setNtp060KinJutyu(money);

        bean.setNtp060CompanyName(rs.getString("ACO_NAME"));

        String companyBaseName = rs.getString("ABA_NAME");
        String companyBaseType
            = AddressBiz.getCompanyBaseTypeName(rs.getInt("ABA_TYPE"), reqMdl);
        if (!StringUtil.isNullZeroString(companyBaseType)) {
            companyBaseName = companyBaseType + " ： " + companyBaseName;
        }
        bean.setNtp060BaseName(companyBaseName);

        bean.setNtp060GyomuName(rs.getString("NGY_NAME"));
        bean.setNtp060ProcessName(rs.getString("NGP_NAME"));
        bean.setNtp060ContactName(rs.getString("NCN_NAME"));

        bean.setNanMitumoriDate(UDate.getInstanceTimestamp(rs.getTimestamp("NAN_MITUMORI_DATE")));
        String mitumoriDateStr = bean.getNanMitumoriDate().getYear() + "年"
                               + bean.getNanMitumoriDate().getMonth() + "月"
                               + bean.getNanMitumoriDate().getIntDay() + "日";
        bean.setNtp060MitumoriDate(mitumoriDateStr);

        bean.setNanJutyuDate(UDate.getInstanceTimestamp(rs.getTimestamp("NAN_JUTYU_DATE")));
        String jutyuDateStr = bean.getNanJutyuDate().getYear() + "年"
                               + bean.getNanJutyuDate().getMonth() + "月"
                               + bean.getNanJutyuDate().getIntDay() + "日";
        bean.setNtp060JutyuDate(jutyuDateStr);

        return bean;
    }

    /**
     * <p>
     * Create NTP_ANKEN Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @param reqMdl RequestModel
     * @return created ScheduleCsvModel
     * @throws SQLException SQL実行例外
     */
    private Ntp060AnkenModel __getAnkenCsvFromRs(ResultSet rs, RequestModel reqMdl)
            throws SQLException {
        Ntp060AnkenModel bean = new Ntp060AnkenModel();
        //案件コード
        bean.setNanCode(rs.getString("NAN_CODE"));
        //案件名
        bean.setNanName(rs.getString("NAN_NAME"));
        //案件詳細
        bean.setNanDetial(rs.getString("NAN_DETIAL"));
        //会社名
        bean.setNtp060CompanyName(rs.getString("ACO_NAME"));
        //会社名
        bean.setNtp060CompanyCode(rs.getString("ACO_CODE"));
        //拠点
        String companyBaseName = rs.getString("ABA_NAME");
        String companyBaseType
            = AddressBiz.getCompanyBaseTypeName(rs.getInt("ABA_TYPE"), reqMdl);
        if (!StringUtil.isNullZeroString(companyBaseType)) {
            companyBaseName = companyBaseType + " ： " + companyBaseName;
        }
        bean.setNtp060BaseName(companyBaseName);
        //見積金額
        String mitumori = StringUtil.toCommaFormat(String.valueOf(rs.getInt("NAN_KIN_MITUMORI")));
        bean.setNtp060KinMitumori(mitumori);
        //提出日
        bean.setNanMitumoriDate(UDate.getInstanceTimestamp(rs.getTimestamp("NAN_MITUMORI_DATE")));
        bean.setNtp060MitumoriDate(UDateUtil.getSlashYYMD(bean.getNanMitumoriDate()));
        //受注金額
        String jutyu = StringUtil.toCommaFormat(String.valueOf(rs.getInt("NAN_KIN_JUTYU")));
        bean.setNtp060KinJutyu(jutyu);
        //受注日
        bean.setNanJutyuDate(UDate.getInstanceTimestamp(rs.getTimestamp("NAN_JUTYU_DATE")));
        bean.setNtp060JutyuDate(UDateUtil.getSlashYYMD(bean.getNanJutyuDate()));
        //業務
        bean.setNtp060GyomuName(rs.getString("NGY_NAME"));
        //業務コード
        bean.setNtp060GyomuCode(rs.getString("NGY_CODE"));
        //プロセス
        bean.setNtp060ProcessName(rs.getString("NGP_NAME"));
        //プロセスコード
        bean.setNtp060ProcessCode(rs.getString("NGP_CODE"));
        //見込度
        bean.setNanMikomi(rs.getInt("NAN_MIKOMI"));
        //顧客源泉
        bean.setNtp060ContactName(rs.getString("NCN_NAME"));
        //顧客源泉コード
        bean.setNtp060ContactName(rs.getString("NCN_CODE"));
        //商談結果
        bean.setNanSyodan(rs.getInt("NAN_SYODAN"));
        //状態
        bean.setNanState(rs.getInt("NAN_STATE"));
        //登録日
        bean.setNanDate(UDate.getInstanceTimestamp(rs.getTimestamp("NAN_DATE")));
        bean.setNtp060Date(UDateUtil.getSlashYYMD(bean.getNanDate()));
        return bean;
    }
    /**
     * <p>
     * NtpCsvRecordListenerImplにセットする。
     * @param model Ntp060AnkenModel
     * @param sessionUsrSid セッションユーザSID(実行者)
     * @param rl NtpCsvRecordListenerIppanImpl
     * @throws SQLException SQL実行例外
     * @throws CSVException CSV出力時例外
     */
    public static void setNtpCsvRecordFromSchDataModel(Ntp060AnkenModel model,
            int sessionUsrSid, Ntp060CsvRecordListenerIppanImpl rl)
            throws SQLException, CSVException {
        rl.setRecord(model);
    }
    /**
     * <br>[機  能] 指定案件のSIDを権限で絞り込む
     * <br>[解  説]
     * <br>[備  考] 返り値に削除済み案件を含む
     * @param nanSids 案件SID
     * @param permKbn 権限区分
     * @param sessionUsrSid セッションユーザSID
     * @param isAdmin 管理者権限での検索かどうか
     * @return ankenSids 案件SID
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Integer> getNoPermissionAnkenSids(
            Collection<Integer> nanSids, int permKbn,
            int sessionUsrSid, boolean isAdmin) throws SQLException {
        ArrayList<Integer> ret = new ArrayList<Integer>();
        List<Integer> viewable = getPermissionAnkenSids(nanSids,
                permKbn,
                sessionUsrSid,
                isAdmin);
        for (int nanSid : nanSids) {
            if (!viewable.contains(nanSid)) {
               ret.add(nanSid);
            }
        }
        return ret;
    }

    /**
     * <br>[機  能] 指定案件のSIDを権限で絞り込む
     * <br>[解  説]
     * <br>[備  考]
     * @param nanSids 案件SID
     * @param permKbn 権限区分
     * @param sessionUsrSid セッションユーザSID
     * @param isAdmin 管理者権限での検索かどうか
     * @return ankenSids 案件SID
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Integer> getPermissionAnkenSids(
            Collection<Integer> nanSids,
            int permKbn,
            int sessionUsrSid,
            boolean isAdmin) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   distinct NTP_ANKEN.NAN_SID as NAN_SID");
            sql.addSql(" from ");
            sql.addSql("   NTP_ANKEN ");
            sql.addSql(" where ");
            sql.addSql("   NTP_ANKEN.NAN_SID in ( ");
            boolean isNotFirst = false;
            for (int nanSid : nanSids) {
                if (isNotFirst) {
                    sql.addSql(", ");
                }
                sql.addSql(String.valueOf(nanSid));
                isNotFirst = true;
            }
            sql.addSql(" )");
            if (!isAdmin) {
                addPermissionWhereSQL(sql,
                        sessionUsrSid,
                        permKbn);
            }

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(rs.getInt("NAN_SID"));
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
