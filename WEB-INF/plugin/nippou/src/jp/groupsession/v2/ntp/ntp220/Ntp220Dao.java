package jp.groupsession.v2.ntp.ntp220;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.ntp040.model.Ntp040DBCompanyBaseModel;
import jp.groupsession.v2.ntp.ntp040.model.Ntp040DBCompanyModel;
import jp.groupsession.v2.ntp.ntp060.Ntp060AnkenDao;
import jp.groupsession.v2.ntp.ntp220.model.Ntp220AnkenModel;
import jp.groupsession.v2.ntp.ntp220.model.Ntp220AnkenSearchModel;
import jp.groupsession.v2.ntp.ntp220.model.Ntp220ComparativeModel;
import jp.groupsession.v2.ntp.ntp220.model.Ntp220MenuParam;
import jp.groupsession.v2.ntp.ntp220.model.Ntp220ShohinModel;
import jp.groupsession.v2.ntp.ntp220.model.Ntp220StateModel;
import jp.groupsession.v2.ntp.ntp220.model.Ntp220TotalModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 日報 分析画面で使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp220Dao extends AbstractDao {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp220Dao.class);

    /**
     * <p>
     * デフォルトコンストラクタ
     */
    public Ntp220Dao() {
    }

    /**
     * <p>
     * デフォルトコンストラクタ
     *
     * @param con
     *            DBコネクション
     */
    public Ntp220Dao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] 会社情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param acoSid ACO_SID
     * @return 会社情報
     * @throws SQLException SQL実行例外
     */
    public Ntp040DBCompanyModel getCompanyData(int acoSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Ntp040DBCompanyModel bean = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   ACO_SID,");
            sql.addSql("   ACO_CODE,");
            sql.addSql("   ACO_NAME,");
            sql.addSql("   ACO_NAME_KN,");
            sql.addSql("   ACO_SINI,");
            sql.addSql("   ACO_URL,");
            sql.addSql("   ACO_BIKO,");
            sql.addSql("   ACO_AUID,");
            sql.addSql("   ACO_ADATE,");
            sql.addSql("   ACO_EUID,");
            sql.addSql("   ACO_EDATE");
            sql.addSql(" from");
            sql.addSql("   ADR_COMPANY");
            sql.addSql(" where ");
            sql.addSql("   ACO_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(acoSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                bean = new Ntp040DBCompanyModel();
                bean.setAcoSid(rs.getInt("ACO_SID"));
                bean.setAcoCode(rs.getString("ACO_CODE"));
                bean.setAcoName(rs.getString("ACO_NAME"));
                bean.setAcoNameKn(rs.getString("ACO_NAME_KN"));
                bean.setAcoSini(rs.getString("ACO_SINI"));
                bean.setAcoUrl(rs.getString("ACO_URL"));
                bean.setAcoBiko(rs.getString("ACO_BIKO"));
                bean.setAcoAuid(rs.getInt("ACO_AUID"));
                bean.setAcoAdate(UDate.getInstanceTimestamp(rs.getTimestamp("ACO_ADATE")));
                bean.setAcoEuid(rs.getInt("ACO_EUID"));
                bean.setAcoEdate(UDate.getInstanceTimestamp(rs.getTimestamp("ACO_EDATE")));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return bean;
    }

    /**
     * <br>[機  能] 会社拠点情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param abaSid ABA_SID
     * @return 会社拠点情報
     * @throws SQLException SQL実行例外
     */
    public Ntp040DBCompanyBaseModel getCompanyBaseData(int abaSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Ntp040DBCompanyBaseModel bean = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   ABA_SID,");
            sql.addSql("   ACO_SID,");
            sql.addSql("   ABA_TYPE,");
            sql.addSql("   ABA_NAME,");
            sql.addSql("   ABA_POSTNO1,");
            sql.addSql("   ABA_POSTNO2,");
            sql.addSql("   TDF_SID,");
            sql.addSql("   ABA_ADDR1,");
            sql.addSql("   ABA_ADDR2,");
            sql.addSql("   ABA_BIKO,");
            sql.addSql("   ABA_AUID,");
            sql.addSql("   ABA_ADATE,");
            sql.addSql("   ABA_EUID,");
            sql.addSql("   ABA_EDATE");
            sql.addSql(" from");
            sql.addSql("   ADR_COMPANY_BASE");
            sql.addSql(" where ");
            sql.addSql("   ABA_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(abaSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                bean = new Ntp040DBCompanyBaseModel();
                bean.setAbaSid(rs.getInt("ABA_SID"));
                bean.setAcoSid(rs.getInt("ACO_SID"));
                bean.setAbaType(rs.getInt("ABA_TYPE"));
                bean.setAbaName(rs.getString("ABA_NAME"));
                bean.setAbaPostno1(rs.getString("ABA_POSTNO1"));
                bean.setAbaPostno2(rs.getString("ABA_POSTNO2"));
                bean.setTdfSid(rs.getInt("TDF_SID"));
                bean.setAbaAddr1(rs.getString("ABA_ADDR1"));
                bean.setAbaAddr2(rs.getString("ABA_ADDR2"));
                bean.setAbaBiko(rs.getString("ABA_BIKO"));
                bean.setAbaAuid(rs.getInt("ABA_AUID"));
                bean.setAbaAdate(UDate.getInstanceTimestamp(rs.getTimestamp("ABA_ADATE")));
                bean.setAbaEuid(rs.getInt("ABA_EUID"));
                bean.setAbaEdate(UDate.getInstanceTimestamp(rs.getTimestamp("ABA_EDATE")));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return bean;
    }

    /**
     * 案件に登録された企業を取得する
     * @param ankenSids 案件SID
     * @param searchWord 検索キーワード
     * @param limit リミット
     * @param offset オフセット
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @param alreadySelList 選択済み
     * @return List in NTP_DATAModel
     * @throws SQLException SQL実行例外
     */
    public List<Ntp220MenuParam> getAnkenKigyouList(
            List<Integer> ankenSids,
            int limit,
            int offset,
            UDate frdate,
            UDate todate,
            String searchWord,
            ArrayList<String> alreadySelList)
                    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Ntp220MenuParam prmMdl = null;
        List<Ntp220MenuParam> ret = new ArrayList<Ntp220MenuParam>();
        boolean whereFlg = false;

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select ");
            sql.addSql("   distinct ");
            sql.addSql("   ADR_COMPANY.ACO_SID as ACO_SID, ");
            sql.addSql("   ADR_COMPANY.ACO_NAME as ACO_NAME, ");
            sql.addSql("   ADR_COMPANY_BASE.ABA_SID as ABA_SID, ");
            sql.addSql("   ADR_COMPANY_BASE.ABA_NAME as ABA_NAME ");
            sql.addSql(" from ");
            sql.addSql("   (ADR_COMPANY INNER JOIN ");
            sql.addSql("   (select  ");
            sql.addSql("      distinct  ");
            sql.addSql("      NTP_ANKEN.ACO_SID as ACO_SID, ");
            sql.addSql("      NTP_ANKEN.ABA_SID as ABA_SID ");
            sql.addSql("    from  ");
            sql.addSql("      NTP_ANKEN ");
            sql = __getWhereDateForAnken(frdate, todate, sql, "NTP_ANKEN", whereFlg);
            sql = __getWhereFromAnkenSids(ankenSids, sql, "NTP_ANKEN", true);
            sql.addSql("    union ");
            sql.addSql("    select  ");
            sql.addSql("      distinct  ");
            sql.addSql("      NTP_AN_HIS.ACO_SID as ACO_SID, ");
            sql.addSql("      NTP_AN_HIS.ABA_SID as ABA_SID ");
            sql.addSql("    from  ");
            sql.addSql("      (");
            sql = __getAnkenHistoryTable(frdate, todate, sql, null);
            sql.addSql("      ) NTP_AN_HIS");
            sql = __getWhereFromAnkenSids(ankenSids, sql, "NTP_AN_HIS", false);
            sql.addSql("      ) NTP_AN_DATA ");
            sql.addSql("    ON ");
            sql.addSql("      ADR_COMPANY.ACO_SID=NTP_AN_DATA.ACO_SID)  ");
            sql.addSql("    LEFT JOIN ");
            sql.addSql("      ADR_COMPANY_BASE  ");
            sql.addSql("    ON ");
            sql.addSql("      NTP_AN_DATA.ABA_SID=ADR_COMPANY_BASE.ABA_SID ");

            if (!StringUtil.isNullZeroStringSpace(searchWord)) {
                sql.addSql(" where ");
                sql.addSql(" ADR_COMPANY.ACO_NAME like '%"
                        + JDBCUtil.escapeForLikeSearch(searchWord)
                        + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
                sql.addSql(" or ");
                sql.addSql(" ADR_COMPANY.ACO_CODE like '%"
                        + JDBCUtil.escapeForLikeSearch(searchWord)
                        + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
                sql.addSql(" or ");
                sql.addSql(" ADR_COMPANY.ACO_NAME_KN like '%"
                        + JDBCUtil.escapeForLikeSearch(searchWord)
                        + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
                sql.addSql(" or ");
                sql.addSql(" ADR_COMPANY_BASE.ABA_NAME like '%"
                        + JDBCUtil.escapeForLikeSearch(searchWord)
                        + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
            }

            pstmt = con.prepareStatement(sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            if (offset > 1) {
                rs.absolute((offset - 1) * limit);
            }

            for (int i = 0; rs.next() && i < limit; i++) {
                prmMdl = new Ntp220MenuParam();
                prmMdl.setContentSid1(rs.getInt("ACO_SID"));
                prmMdl.setContentSid2(rs.getInt("ABA_SID"));
                prmMdl.setContentName1(rs.getString("ACO_NAME"));
                prmMdl.setContentName2(rs.getString("ABA_NAME"));

                if (alreadySelList != null && !alreadySelList.isEmpty()) {
                    if (alreadySelList.indexOf(
                            String.valueOf(rs.getInt("ACO_SID"))
                            + "_"
                            + String.valueOf(rs.getInt("ABA_SID"))) != -1) {
                        prmMdl.setAlreadyFlg(true);
                    }
                }

                ret.add(prmMdl);
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
     * 案件に登録された顧客源泉を取得する
     * @param ankenSids 案件SID
     * @param searchWord 検索キーワード
     * @param limit リミット
     * @param offset オフセット
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @param alreadySelList 選択済み
     * @return List in NTP_DATAModel
     * @throws SQLException SQL実行例外
     */
    public List<Ntp220MenuParam> getAnkenKokyakugensenList(List<Integer> ankenSids,
            int limit,
            int offset,
            UDate frdate,
            UDate todate,
            String searchWord,
            ArrayList<String> alreadySelList)
                    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Ntp220MenuParam prmMdl = null;
        List<Ntp220MenuParam> ret = new ArrayList<Ntp220MenuParam>();
        boolean whereFlg = false;

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql("  select  ");
            sql.addSql("    distinct  ");
            sql.addSql("    NTP_CONTACT.NCN_SID as NCN_SID,  ");
            sql.addSql("    NTP_CONTACT.NCN_NAME as NCN_NAME ");
            sql.addSql("  from  ");
            sql.addSql("    (NTP_CONTACT INNER JOIN  ");
            sql.addSql("    (select   ");
            sql.addSql("       distinct   ");
            sql.addSql("       NTP_ANKEN.NCN_SID as NCN_SID ");
            sql.addSql("     from   ");
            sql.addSql("       NTP_ANKEN  ");
            sql = __getWhereDateForAnken(frdate, todate, sql, "NTP_ANKEN", whereFlg);
            sql = __getWhereFromAnkenSids(ankenSids, sql, "NTP_ANKEN", true);
            sql.addSql("     union  ");
            sql.addSql("     select   ");
            sql.addSql("       distinct   ");
            sql.addSql("       NTP_AN_HIS.NCN_SID as NCN_SID ");
            sql.addSql("     from   ");
            sql.addSql("       ( ");
            sql = __getAnkenHistoryTable(frdate, todate, sql, null);
            sql.addSql("       ) NTP_AN_HIS ");
            sql = __getWhereFromAnkenSids(ankenSids, sql, "NTP_AN_HIS", false);
            sql.addSql("       ) NTP_AN_DATA  ");
            sql.addSql("     ON  ");
            sql.addSql("       NTP_CONTACT.NCN_SID=NTP_AN_DATA.NCN_SID)   ");

            if (!StringUtil.isNullZeroStringSpace(searchWord)) {
                sql.addSql(" where ");
                sql.addSql(" NTP_CONTACT.NCN_CODE like '%"
                        + JDBCUtil.escapeForLikeSearch(searchWord)
                        + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
                sql.addSql(" or ");
                sql.addSql(" NTP_CONTACT.NCN_NAME like '%"
                        + JDBCUtil.escapeForLikeSearch(searchWord)
                        + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
            }

            pstmt = con.prepareStatement(sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            if (offset > 1) {
                rs.absolute((offset - 1) * limit);
            }

            for (int i = 0; rs.next() && i < limit; i++) {
                prmMdl = new Ntp220MenuParam();
                prmMdl.setContentSid1(rs.getInt("NCN_SID"));
                prmMdl.setContentName1(rs.getString("NCN_NAME"));

                if (alreadySelList != null && !alreadySelList.isEmpty()) {
                    if (alreadySelList.indexOf(
                            String.valueOf(rs.getInt("NCN_SID")) + "_" + "0") != -1) {
                        prmMdl.setAlreadyFlg(true);
                    }
                }

                ret.add(prmMdl);
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
     * 案件に登録された見込み度を取得する
     * @param ankenSids 案件SID
     * @param limit リミット
     * @param offset オフセット
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @param alreadySelList 選択済み
     * @return List in NTP_DATAModel
     * @throws SQLException SQL実行例外
     */
    public List<Ntp220MenuParam> getAnkenMikomidoList(
            List<Integer> ankenSids,
            int limit,
            int offset,
            UDate frdate,
            UDate todate,
            ArrayList<String> alreadySelList)
                    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Ntp220MenuParam prmMdl = null;
        List<Ntp220MenuParam> ret = new ArrayList<Ntp220MenuParam>();
        boolean whereFlg = false;

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();


            sql.addSql("     select   ");
            sql.addSql("       distinct   ");
            sql.addSql("       NTP_ANKEN.NAN_MIKOMI as NAN_MIKOMI, ");
            sql.addSql("       CASE NTP_ANKEN.NAN_MIKOMI");
            sql.addSql("       WHEN 0 THEN '10%'");
            sql.addSql("       WHEN 1 THEN '30%'");
            sql.addSql("       WHEN 2 THEN '50%'");
            sql.addSql("       WHEN 3 THEN '70%'");
            sql.addSql("       WHEN 4 THEN '100%'");
            sql.addSql("       ELSE '0%' END  as NAN_MIKOMI_VAL");
            sql.addSql("     from   ");
            sql.addSql("       NTP_ANKEN  ");
            sql = __getWhereDateForAnken(frdate, todate, sql, "NTP_ANKEN", whereFlg);
            sql = __getWhereFromAnkenSids(ankenSids, sql, "NTP_ANKEN", true);
            sql.addSql("     union  ");
            sql.addSql("     select   ");
            sql.addSql("       distinct   ");
            sql.addSql("       NTP_AN_HIS.NAN_MIKOMI as NAN_MIKOMI, ");
            sql.addSql("       CASE NTP_AN_HIS.NAN_MIKOMI");
            sql.addSql("       WHEN 0 THEN '10%'");
            sql.addSql("       WHEN 1 THEN '30%'");
            sql.addSql("       WHEN 2 THEN '50%'");
            sql.addSql("       WHEN 3 THEN '70%'");
            sql.addSql("       WHEN 4 THEN '100%'");
            sql.addSql("       ELSE '0%' END  as NAN_MIKOMI_VAL");
            sql.addSql("     from   ");
            sql.addSql("       ( ");
            sql = __getAnkenHistoryTable(frdate, todate, sql, null);
            sql.addSql("       ) NTP_AN_HIS ");
            sql = __getWhereFromAnkenSids(ankenSids, sql, "NTP_AN_HIS", false);
            sql.addSql("       order by NAN_MIKOMI asc ");

            pstmt = con.prepareStatement(sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            if (offset > 1) {
                rs.absolute((offset - 1) * limit);
            }

            for (int i = 0; rs.next() && i < limit; i++) {
                prmMdl = new Ntp220MenuParam();
                prmMdl.setContentSid1(rs.getInt("NAN_MIKOMI"));
                prmMdl.setContentName1(rs.getString("NAN_MIKOMI_VAL"));

                if (alreadySelList != null && !alreadySelList.isEmpty()) {
                    if (alreadySelList.indexOf(
                            String.valueOf(rs.getInt("NAN_MIKOMI")) + "_" + "0") != -1) {
                        prmMdl.setAlreadyFlg(true);
                    }
                }

                ret.add(prmMdl);
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
     * プロセスを取得する
     * @param ankenSids 案件SID
     * @param limit リミット
     * @param offset オフセット
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @param alreadySelList 選択済み
     * @param gyoushuSid 業種SID
     * @return List in NTP_DATAModel
     * @throws SQLException SQL実行例外
     */
    public List<Ntp220MenuParam> getAnkenProcessList(
            List<Integer> ankenSids,
            int limit,
            int offset,
            UDate frdate,
            UDate todate,
            ArrayList<String> alreadySelList,
            int gyoushuSid)
                    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Ntp220MenuParam prmMdl = null;
        List<Ntp220MenuParam> ret = new ArrayList<Ntp220MenuParam>();
        boolean whereFlg = false;

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql("  select  ");
            sql.addSql("    distinct  ");
            sql.addSql("    NTP_PROCESS.NGP_SID as NGP_SID,  ");
            sql.addSql("    NTP_PROCESS.NGP_NAME as NGP_NAME ");
            sql.addSql("  from  ");
            sql.addSql("    (NTP_PROCESS INNER JOIN  ");
            sql.addSql("    (select   ");
            sql.addSql("       distinct   ");
            sql.addSql("       NTP_ANKEN.NGP_SID as NGP_SID ");
            sql.addSql("     from   ");
            sql.addSql("       NTP_ANKEN  ");
            sql = __getWhereDateForAnken(frdate, todate, sql, "NTP_ANKEN", whereFlg);
            sql = __getWhereFromAnkenSids(ankenSids, sql, "NTP_ANKEN", true);
            sql.addSql("     union  ");
            sql.addSql("     select   ");
            sql.addSql("       distinct   ");
            sql.addSql("       NTP_AN_HIS.NGP_SID as NGP_SID ");
            sql.addSql("     from   ");
            sql.addSql("       ( ");
            sql = __getAnkenHistoryTable(frdate, todate, sql, null);
            sql.addSql("       ) NTP_AN_HIS ");
            sql = __getWhereFromAnkenSids(ankenSids, sql, "NTP_AN_HIS", false);
            sql.addSql("       ) NTP_AN_DATA  ");
            sql.addSql("     ON  ");
            sql.addSql("       NTP_PROCESS.NGP_SID=NTP_AN_DATA.NGP_SID)   ");
            if (gyoushuSid != -1) {
                sql.addSql("   where NTP_PROCESS.NGY_SID=" + gyoushuSid);
            }


            pstmt = con.prepareStatement(sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            if (offset > 1) {
                rs.absolute((offset - 1) * limit);
            }

            for (int i = 0; rs.next() && i < limit; i++) {
                prmMdl = new Ntp220MenuParam();
                prmMdl.setContentSid1(rs.getInt("NGP_SID"));
                prmMdl.setContentName1(rs.getString("NGP_NAME"));

                if (alreadySelList != null && !alreadySelList.isEmpty()) {
                    if (alreadySelList.indexOf(
                            String.valueOf(rs.getInt("NGP_SID")) + "_" + "0") != -1) {
                        prmMdl.setAlreadyFlg(true);
                    }
                }

                ret.add(prmMdl);
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
     * 案件に登録された顧客源泉を取得する
     * @param ankenSids 案件SID
     * @param searchWord 検索キーワード
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @return List in NTP_DATAModel
     * @throws SQLException SQL実行例外
     */
    public int getAnkenKokyakugensenListCount(List<Integer> ankenSids,
                                                                String searchWord,
                                                                UDate frdate,
                                                                UDate todate) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        boolean whereFlg = false;

        if (ankenSids != null && !ankenSids.isEmpty()) {
            whereFlg = true;
        }

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql("  select  ");
            sql.addSql("    count( distinct NTP_CONTACT.NCN_SID) as CNT  ");
            sql.addSql("  from  ");
            sql.addSql("    (NTP_CONTACT INNER JOIN  ");
            sql.addSql("    (select   ");
            sql.addSql("       distinct   ");
            sql.addSql("       NTP_ANKEN.NCN_SID as NCN_SID ");
            sql.addSql("     from   ");
            sql.addSql("       NTP_ANKEN  ");
            sql = __getWhereDateForAnken(frdate, todate, sql, "NTP_ANKEN", whereFlg);
            sql = __getWhereFromAnkenSids(ankenSids, sql, "NTP_ANKEN", true);
            sql.addSql("     union  ");
            sql.addSql("     select   ");
            sql.addSql("       distinct   ");
            sql.addSql("       NTP_AN_HIS.NCN_SID as NCN_SID ");
            sql.addSql("     from   ");
            sql.addSql("       ( ");
            sql = __getAnkenHistoryTable(frdate, todate, sql, null);
            sql.addSql("       ) NTP_AN_HIS ");
            sql = __getWhereFromAnkenSids(ankenSids, sql, "NTP_AN_HIS", false);
            sql.addSql("       ) NTP_AN_DATA  ");
            sql.addSql("     ON  ");
            sql.addSql("       NTP_CONTACT.NCN_SID=NTP_AN_DATA.NCN_SID)   ");

            if (!StringUtil.isNullZeroStringSpace(searchWord)) {
                sql.addSql(" where ");
                sql.addSql(" NTP_CONTACT.NCN_CODE like '%"
                        + JDBCUtil.escapeForLikeSearch(searchWord)
                        + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
                sql.addSql(" or ");
                sql.addSql(" NTP_CONTACT.NCN_NAME like '%"
                        + JDBCUtil.escapeForLikeSearch(searchWord)
                        + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
            }

            pstmt = con.prepareStatement(sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
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
     * 案件に登録された商品を取得する
     * @param ankenSids 案件SID
     * @param searchWord 検索キーワード
     * @param limit リミット
     * @param offset オフセット
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @param alreadySelList 選択済み
     * @return List in NTP_DATAModel
     * @throws SQLException SQL実行例外
     */
    public List<Ntp220MenuParam> getAnkenShohinList(List<Integer> ankenSids,
                                                    int limit,
                                                    int offset,
                                                    UDate frdate,
                                                    UDate todate,
                                                    String searchWord,
                                                    ArrayList<String> alreadySelList)
                                                    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Ntp220MenuParam prmMdl = null;
        List<Ntp220MenuParam> ret = new ArrayList<Ntp220MenuParam>();
        boolean whereFlg = false;

        if (ankenSids != null && !ankenSids.isEmpty()) {
            whereFlg = true;
        }

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select ");
            sql.addSql("   NHN_SID, ");
            sql.addSql("   NHN_CODE, ");
            sql.addSql("   NHN_NAME, ");
            sql.addSql("   NHN_PRICE_SALE, ");
            sql.addSql("   NHN_PRICE_COST, ");
            sql.addSql("   NHN_HOSOKU, ");
            sql.addSql("   NHN_AUID, ");
            sql.addSql("   NHN_ADATE, ");
            sql.addSql("   NHN_EUID, ");
            sql.addSql("   NHN_EDATE ");
            sql.addSql(" from ");
            sql.addSql("   NTP_SHOHIN ");
            sql.addSql(" where ");
            sql.addSql("   NHN_SID in ( ");

            sql.addSql("         select ");
            sql.addSql("           distinct ");
            sql.addSql("           NTP_AN_SHOHIN.NHN_SID ");
            sql.addSql("         from ");
            sql.addSql("           NTP_AN_SHOHIN ");
            sql.addSql("         RIGHT JOIN ");
            sql.addSql("           ( ");
            sql.addSql("             select   ");
            sql.addSql("               distinct   ");
            sql.addSql("               NTP_ANKEN.NAN_SID as NAN_SID ");
            sql.addSql("             from   ");
            sql.addSql("               NTP_ANKEN  ");
            sql = __getWhereDateForAnken(frdate, todate, sql, "NTP_ANKEN", whereFlg);
            sql = __getWhereFromAnkenSids(ankenSids, sql, "NTP_ANKEN", true);
            sql.addSql("           ) NTP_ANKEN_SID ");
            sql.addSql("         ON  ");
            sql.addSql("           NTP_AN_SHOHIN.NAN_SID=NTP_ANKEN_SID.NAN_SID ");

            sql.addSql("         union  ");

            sql.addSql("         select ");
            sql.addSql("           distinct ");
            sql.addSql("           NTP_AN_SHOHIN_HISTORY.NHN_SID ");
            sql.addSql("         from ");
            sql.addSql("           NTP_AN_SHOHIN_HISTORY ");
            sql.addSql("         RIGHT JOIN ");
            sql.addSql("           ( ");
            sql.addSql("             select   ");
            sql.addSql("               distinct ");
            sql.addSql("               NTP_AN_HIS.NAH_SID as NAH_SID  ");
            sql.addSql("             from   ");
            sql.addSql("               ( ");
            sql = __getAnkenHistoryTable(frdate, todate, sql, null);
            sql.addSql("             ) NTP_AN_HIS ");
            sql = __getWhereFromAnkenSids(ankenSids, sql, "NTP_AN_HIS", false);
            sql.addSql("          ) NTP_AN_HIS_SID ");
            sql.addSql("         ON  ");
            sql.addSql("           NTP_AN_SHOHIN_HISTORY.NAH_SID=NTP_AN_HIS_SID.NAH_SID ");
            sql.addSql(" ) ");

            if (!StringUtil.isNullZeroStringSpace(searchWord)) {
                sql.addSql(" and ");
                sql.addSql(" NTP_SHOHIN.NHN_NAME like '%"
                        + JDBCUtil.escapeForLikeSearch(searchWord)
                        + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
                sql.addSql(" or ");
                sql.addSql(" NTP_SHOHIN.NHN_CODE like '%"
                        + JDBCUtil.escapeForLikeSearch(searchWord)
                        + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
            }

            pstmt = con.prepareStatement(sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            if (offset > 1) {
                rs.absolute((offset - 1) * limit);
            }

            for (int i = 0; rs.next() && i < limit; i++) {
                prmMdl = new Ntp220MenuParam();
                prmMdl.setContentSid1(rs.getInt("NHN_SID"));
                prmMdl.setContentName1(rs.getString("NHN_NAME"));

                if (alreadySelList != null && !alreadySelList.isEmpty()) {
                    if (alreadySelList.indexOf(
                            String.valueOf(rs.getInt("NHN_SID"))) != -1) {
                        prmMdl.setAlreadyFlg(true);
                    }
                }
                ret.add(prmMdl);
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
     * 案件に登録された商品を取得する
     * @param ankenSids 案件SID
     * @param searchWord 検索キーワード
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @return List in NTP_DATAModel
     * @throws SQLException SQL実行例外
     */
    public int getAnkenShohinListCount(List<Integer> ankenSids,
                                       String searchWord,
                                       UDate frdate,
                                       UDate todate) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        boolean whereFlg = false;

        if (ankenSids != null && !ankenSids.isEmpty()) {
            whereFlg = true;
        }

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select ");
            sql.addSql("   count( distinct NTP_SHOHIN.NHN_SID) as CNT ");
            sql.addSql(" from ");
            sql.addSql("   NTP_SHOHIN ");
            sql.addSql(" where ");
            sql.addSql("   NHN_SID in ( ");

            sql.addSql("         select ");
            sql.addSql("           distinct ");
            sql.addSql("           NTP_AN_SHOHIN.NHN_SID ");
            sql.addSql("         from ");
            sql.addSql("           NTP_AN_SHOHIN ");
            sql.addSql("         RIGHT JOIN ");
            sql.addSql("           ( ");
            sql.addSql("             select   ");
            sql.addSql("               distinct   ");
            sql.addSql("               NTP_ANKEN.NAN_SID as NAN_SID ");
            sql.addSql("             from   ");
            sql.addSql("               NTP_ANKEN  ");
            sql = __getWhereDateForAnken(frdate, todate, sql, "NTP_ANKEN", whereFlg);
            sql = __getWhereFromAnkenSids(ankenSids, sql, "NTP_ANKEN", true);
            sql.addSql("           ) NTP_ANKEN_SID ");
            sql.addSql("         ON  ");
            sql.addSql("           NTP_AN_SHOHIN.NAN_SID=NTP_ANKEN_SID.NAN_SID ");

            sql.addSql("         union  ");

            sql.addSql("         select ");
            sql.addSql("           distinct ");
            sql.addSql("           NTP_AN_SHOHIN_HISTORY.NHN_SID ");
            sql.addSql("         from ");
            sql.addSql("           NTP_AN_SHOHIN_HISTORY ");
            sql.addSql("         RIGHT JOIN ");
            sql.addSql("           ( ");
            sql.addSql("             select   ");
            sql.addSql("               distinct ");
            sql.addSql("               NTP_AN_HIS.NAH_SID as NAH_SID  ");
            sql.addSql("             from   ");
            sql.addSql("               ( ");
            sql = __getAnkenHistoryTable(frdate, todate, sql, null);
            sql.addSql("             ) NTP_AN_HIS ");
            sql = __getWhereFromAnkenSids(ankenSids, sql, "NTP_AN_HIS", false);
            sql.addSql("          ) NTP_AN_HIS_SID ");
            sql.addSql("         ON  ");
            sql.addSql("           NTP_AN_SHOHIN_HISTORY.NAH_SID=NTP_AN_HIS_SID.NAH_SID ");
            sql.addSql(" ) ");

            if (!StringUtil.isNullZeroStringSpace(searchWord)) {
                sql.addSql(" and ");
                sql.addSql(" NTP_SHOHIN.NHN_NAME like '%"
                        + JDBCUtil.escapeForLikeSearch(searchWord)
                        + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
                sql.addSql(" or ");
                sql.addSql(" NTP_SHOHIN.NHN_CODE like '%"
                        + JDBCUtil.escapeForLikeSearch(searchWord)
                        + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
            }

            pstmt = con.prepareStatement(sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
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
     * 案件に登録された企業を取得する
     * @param ankenSids 選択グループ案件
     * @param searchWord 検索キーワード
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @return List in NTP_DATAModel
     * @throws SQLException SQL実行例外
     */
    public int getAnkenKigyouListCount(List<Integer> ankenSids,
                                       String searchWord,
                                       UDate frdate,
                                       UDate todate) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        boolean whereFlg = false;

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql("  select ");
            sql.addSql("    count( distinct ADR_COMPANY.ACO_SID) as CNT");
            sql.addSql("  from ");
            sql.addSql("   (ADR_COMPANY INNER JOIN ");
            sql.addSql("   (select  ");
            sql.addSql("      distinct  ");
            sql.addSql("      NTP_ANKEN.ACO_SID as ACO_SID, ");
            sql.addSql("      NTP_ANKEN.ABA_SID as ABA_SID ");
            sql.addSql("    from  ");
            sql.addSql("      NTP_ANKEN ");
            sql = __getWhereDateForAnken(frdate, todate, sql, "NTP_ANKEN", whereFlg);
            sql = __getWhereFromAnkenSids(ankenSids, sql, "NTP_ANKEN", true);
            sql.addSql("    union ");
            sql.addSql("    select  ");
            sql.addSql("      distinct  ");
            sql.addSql("      NTP_AN_HIS.ACO_SID as ACO_SID,  ");
            sql.addSql("      NTP_AN_HIS.ABA_SID as ABA_SID ");
            sql.addSql("    from  ");
            sql.addSql("      (");
            sql = __getAnkenHistoryTable(frdate, todate, sql, null);
            sql.addSql("      ) NTP_AN_HIS");
            sql = __getWhereFromAnkenSids(ankenSids, sql, "NTP_AN_HIS", false);
            sql.addSql("      ) NTP_AN_DATA ");
            sql.addSql("    ON ");
            sql.addSql("      ADR_COMPANY.ACO_SID=NTP_AN_DATA.ACO_SID)  ");
            sql.addSql("    LEFT JOIN ");
            sql.addSql("      ADR_COMPANY_BASE  ");
            sql.addSql("    ON ");
            sql.addSql("      NTP_AN_DATA.ABA_SID=ADR_COMPANY_BASE.ABA_SID ");

            if (!StringUtil.isNullZeroStringSpace(searchWord)) {
                sql.addSql(" where ");
                sql.addSql(" ADR_COMPANY.ACO_NAME like '%"
                        + JDBCUtil.escapeForLikeSearch(searchWord)
                        + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
                sql.addSql(" or ");
                sql.addSql(" ADR_COMPANY.ACO_CODE like '%"
                        + JDBCUtil.escapeForLikeSearch(searchWord)
                        + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
                sql.addSql(" or ");
                sql.addSql(" ADR_COMPANY.ACO_NAME_KN like '%"
                        + JDBCUtil.escapeForLikeSearch(searchWord)
                        + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
                sql.addSql(" or ");
                sql.addSql(" ADR_COMPANY_BASE.ABA_NAME like '%"
                        + JDBCUtil.escapeForLikeSearch(searchWord)
                        + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
            }

            pstmt = con.prepareStatement(sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
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
     * 案件に登録された企業を取得する
     * @param ankenSids 選択グループ案件
     * @param state 状態 0:進行中 1:完了
     * @param ankenState 案件状態  1:商談中 2:受注 3:失注
     * @param pageNum 表示ページ
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @param limit 表示件数
     * @return ArrayList<Ntp220CompanySalesModel>
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Object>
                        getAllCompanySales(List<Integer> ankenSids,
                                                       int state,
                                                       int ankenState,
                                                       int pageNum,
                                                       int limit,
                                                       UDate frdate,
                                                       UDate todate) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Object> result = new ArrayList<Object>();
        ArrayList<Object> ret = new ArrayList<Object>();
        boolean whereFlg = false;

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select  ");
            sql.addSql("   NTP_AN_DATA.ACO_SID as ACO_SID, ");
            sql.addSql("   NTP_AN_DATA.ACO_NAME as ACO_NAME, ");
            sql.addSql("   NTP_AN_DATA.ABA_SID as ABA_SID, ");
            sql.addSql("   ADR_COMPANY_BASE.ABA_NAME as ABA_NAME, ");
            sql.addSql("   sum(NTP_AN_DATA.NAN_KIN_MITUMORI) as NAN_KIN_MITUMORI, ");
            sql.addSql("   sum(NTP_AN_DATA.NAN_KIN_JUTYU) as NAN_KIN_JUTYU ");
            sql.addSql(" from  ");
            sql.addSql(" (select  ");
            sql.addSql("     NTP_ANKEN.NAN_SID as NAN_SID, ");
            sql.addSql("     NTP_ANKEN.NAN_NAME as NAN_NAME, ");
            sql.addSql("     NTP_ANKEN.ACO_SID as ACO_SID, ");
            sql.addSql("     NTP_ANKEN.ABA_SID as ABA_SID, ");
            sql.addSql("     NTP_ANKEN_MITUMORI.NAN_KIN_MITUMORI as NAN_KIN_MITUMORI, ");
            sql.addSql("     NTP_ANKEN_JUTYU.NAN_KIN_JUTYU as NAN_KIN_JUTYU, ");
            sql.addSql("     NTP_ANKEN.NAN_SYODAN as NAN_SYODAN, ");
            sql.addSql("     NTP_ANKEN.NAN_STATE as NAN_STATE, ");
            sql.addSql("     ADR_COMPANY.ACO_NAME as ACO_NAME ");
            sql.addSql("  from ");
            sql.addSql("     NTP_ANKEN  ");
            sql.addSql("  LEFT JOIN ");
            sql.addSql("     ADR_COMPANY ");
            sql.addSql("  ON ");
            sql.addSql("     NTP_ANKEN.ACO_SID=ADR_COMPANY.ACO_SID ");

            //見積もり金額
            __getMitumoriSql(frdate, todate, sql, null, "NTP_ANKEN", "NTP_ANKEN_MITUMORI");
            //受注金額
            __getJutyuSql(frdate, todate, sql, null, "NTP_ANKEN", "NTP_ANKEN_JUTYU");

            sql = __getWhereDateForAnken(frdate, todate, sql, "NTP_ANKEN", false);

            if (state >= 0) {
                //状態
                sql.addSql("  and ");
                sql.addSql("    NTP_ANKEN.NAN_STATE =" + state);
            }

            //商談状態
            if (ankenState > -1) {
                sql.addSql(" and ");
                sql.addSql("    NTP_ANKEN.NAN_SYODAN =" + ankenState);
            }

            //グループ指定時の案件
            sql = __getWhereFromAnkenSids(ankenSids, sql, "NTP_ANKEN", true);

            sql.addSql("  ) NTP_AN_DATA ");
            sql.addSql("  LEFT JOIN ");
            sql.addSql("    ADR_COMPANY_BASE ");
            sql.addSql("  ON ");
            sql.addSql("    NTP_AN_DATA.ABA_SID=ADR_COMPANY_BASE.ABA_SID ");
            sql.addSql("  where ");
            sql.addSql("    NTP_AN_DATA.ACO_SID not in (0,-1) ");
            sql.addSql("  group by ");
            sql.addSql("    NTP_AN_DATA.ACO_SID, ");
            sql.addSql("    NTP_AN_DATA.ACO_NAME, ");
            sql.addSql("    NTP_AN_DATA.ABA_SID, ");
            sql.addSql("    ADR_COMPANY_BASE.ABA_NAME ");

            sql.addSql(" union ");

            sql.addSql(" select   ");
            sql.addSql("   NTP_AN_DATA.ACO_SID as ACO_SID,  ");
            sql.addSql("   NTP_AN_DATA.ACO_NAME as ACO_NAME,  ");
            sql.addSql("   NTP_AN_DATA.ABA_SID as ABA_SID,  ");
            sql.addSql("   ADR_COMPANY_BASE.ABA_NAME as ABA_NAME,  ");
            sql.addSql("   sum(NTP_AN_DATA.NAN_KIN_MITUMORI) as NAN_KIN_MITUMORI,  ");
            sql.addSql("   sum(NTP_AN_DATA.NAN_KIN_JUTYU) as NAN_KIN_JUTYU  ");
            sql.addSql(" from  ");
            sql.addSql(" (select  ");
            sql.addSql("     NTP_AN_HIS.NAN_SID as NAN_SID, ");
            sql.addSql("     NTP_AN_HIS.NAN_NAME as NAN_NAME, ");
            sql.addSql("     NTP_AN_HIS.ACO_SID as ACO_SID, ");
            sql.addSql("     NTP_AN_HIS.ABA_SID as ABA_SID, ");
            sql.addSql("     NTP_AN_HIS.NAN_KIN_MITUMORI as NAN_KIN_MITUMORI, ");
            sql.addSql("     NTP_AN_HIS.NAN_KIN_JUTYU as NAN_KIN_JUTYU, ");
            sql.addSql("     NTP_AN_HIS.NAN_SYODAN as NAN_SYODAN, ");
            sql.addSql("     NTP_AN_HIS.NAN_STATE as NAN_STATE, ");
            sql.addSql("     ADR_COMPANY.ACO_NAME as ACO_NAME ");
            sql.addSql("  from ");
            sql.addSql("  ( ");
            sql = __getAnkenHistoryTable(frdate, todate, sql, null);
            sql.addSql("    )  NTP_AN_HIS");
            sql.addSql("  LEFT JOIN ");
            sql.addSql("     ADR_COMPANY ");
            sql.addSql("  ON ");
            sql.addSql("     NTP_AN_HIS.ACO_SID=ADR_COMPANY.ACO_SID ");

            if (state >= 0) {
                //状態
                sql.addSql("  where ");
                sql.addSql("    NTP_AN_HIS.NAN_STATE =" + state);
                whereFlg = true;
            }

            //商談状態
            if (ankenState > -1) {
                if (whereFlg) {
                    sql.addSql(" and ");
                } else {
                    sql.addSql(" where ");
                    whereFlg = true;
                }

                sql.addSql("    NTP_AN_HIS.NAN_SYODAN =" + ankenState);
            }

            //グループ指定時の案件
            sql = __getWhereFromAnkenSids(ankenSids, sql, "NTP_AN_HIS", whereFlg);

            sql.addSql("  ) NTP_AN_DATA ");
            sql.addSql("  LEFT JOIN ");
            sql.addSql("    ADR_COMPANY_BASE ");
            sql.addSql("  ON ");
            sql.addSql("    NTP_AN_DATA.ABA_SID=ADR_COMPANY_BASE.ABA_SID ");
            sql.addSql("  where ");
            sql.addSql("    NTP_AN_DATA.ACO_SID not in (0,-1) ");
            sql.addSql("  group by ");
            sql.addSql("    NTP_AN_DATA.ACO_SID, ");
            sql.addSql("    NTP_AN_DATA.ACO_NAME, ");
            sql.addSql("    NTP_AN_DATA.ABA_SID, ");
            sql.addSql("    ADR_COMPANY_BASE.ABA_NAME ");

            sql.addSql("  order by NAN_KIN_JUTYU DESC");


            pstmt = con.prepareStatement(sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            Ntp220AnkenModel cmpSalesMdl = null;

            limit = pageNum * limit;

            for (int i = 0; rs.next() && i < limit; i++) {
                cmpSalesMdl = new Ntp220AnkenModel();
                cmpSalesMdl.setCntSid1(rs.getInt("ACO_SID"));
                cmpSalesMdl.setCntName1(rs.getString("ACO_NAME"));
                cmpSalesMdl.setCntSid2(rs.getInt("ABA_SID"));
                cmpSalesMdl.setCntName2(rs.getString("ABA_NAME"));
                cmpSalesMdl.setNanKinMitumori(rs.getInt("NAN_KIN_MITUMORI"));
                cmpSalesMdl.setNanKinJutyu(rs.getInt("NAN_KIN_JUTYU"));
                result.add(cmpSalesMdl);
            }

            for (int n = result.size(); n > 0; n--) {
                ret.add(result.get(n - 1));
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
     * 案件に登録された顧客源泉を取得する
     * @param ankenSids 選択グループ案件
     * @param state 状態 0:進行中 1:完了
     * @param ankenState 案件状態  1:商談中 2:受注 3:失注
     * @param pageNum 表示ページ
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @param limit 表示件数
     * @return ArrayList<Ntp220CompanySalesModel>
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Object>
                        getAllKokyakugensenSales(List<Integer> ankenSids,
                                                       int state,
                                                       int ankenState,
                                                       int pageNum,
                                                       int limit,
                                                       UDate frdate,
                                                       UDate todate) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Object> result = new ArrayList<Object>();
        ArrayList<Object> ret = new ArrayList<Object>();
        boolean whereFlg = false;

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select  ");
            sql.addSql("   NTP_AN_DATA.NCN_SID as NCN_SID, ");
            sql.addSql("   NTP_AN_DATA.NCN_NAME as NCN_NAME, ");
            sql.addSql("   sum(NTP_AN_DATA.NAN_KIN_MITUMORI) as NAN_KIN_MITUMORI, ");
            sql.addSql("   sum(NTP_AN_DATA.NAN_KIN_JUTYU) as NAN_KIN_JUTYU ");
            sql.addSql(" from  ");
            sql.addSql(" (select  ");
            sql.addSql("     NTP_ANKEN.NAN_SID as NAN_SID, ");
            sql.addSql("     NTP_ANKEN.NAN_NAME as NAN_NAME, ");
            sql.addSql("     NTP_ANKEN.NCN_SID as NCN_SID, ");
            sql.addSql("     NTP_ANKEN_MITUMORI.NAN_KIN_MITUMORI as NAN_KIN_MITUMORI, ");
            sql.addSql("     NTP_ANKEN_JUTYU.NAN_KIN_JUTYU as NAN_KIN_JUTYU, ");
            sql.addSql("     NTP_ANKEN.NAN_SYODAN as NAN_SYODAN, ");
            sql.addSql("     NTP_ANKEN.NAN_STATE as NAN_STATE, ");
            sql.addSql("     NTP_CONTACT.NCN_NAME as NCN_NAME ");
            sql.addSql("  from ");
            sql.addSql("     NTP_ANKEN  ");
            sql.addSql("  LEFT JOIN ");
            sql.addSql("     NTP_CONTACT ");
            sql.addSql("  ON ");
            sql.addSql("     NTP_ANKEN.NCN_SID=NTP_CONTACT.NCN_SID ");

            //見積もり金額
            __getMitumoriSql(frdate, todate, sql, null, "NTP_ANKEN", "NTP_ANKEN_MITUMORI");
            //受注金額
            __getJutyuSql(frdate, todate, sql, null, "NTP_ANKEN", "NTP_ANKEN_JUTYU");

            sql = __getWhereDateForAnken(frdate, todate, sql, "NTP_ANKEN", false);

            if (state >= 0) {
                //状態
                sql.addSql("  and ");
                sql.addSql("    NTP_ANKEN.NAN_STATE =" + state);
            }

            //商談状態
            if (ankenState > -1) {
                sql.addSql(" and ");
                sql.addSql("    NTP_ANKEN.NAN_SYODAN =" + ankenState);
            }

            //グループ指定時の案件
            sql = __getWhereFromAnkenSids(ankenSids, sql, "NTP_ANKEN", true);

            sql.addSql("  ) NTP_AN_DATA ");
            sql.addSql("  group by ");
            sql.addSql("    NTP_AN_DATA.NCN_SID, ");
            sql.addSql("    NTP_AN_DATA.NCN_NAME ");

            sql.addSql(" union ");

            sql.addSql(" select   ");
            sql.addSql("   NTP_AN_DATA.NCN_SID as NCN_SID,  ");
            sql.addSql("   NTP_AN_DATA.NCN_NAME as NCN_NAME,  ");
            sql.addSql("   sum(NTP_AN_DATA.NAN_KIN_MITUMORI) as NAN_KIN_MITUMORI,  ");
            sql.addSql("   sum(NTP_AN_DATA.NAN_KIN_JUTYU) as NAN_KIN_JUTYU  ");
            sql.addSql(" from  ");
            sql.addSql(" (select  ");
            sql.addSql("     NTP_AN_HIS.NAN_SID as NAN_SID, ");
            sql.addSql("     NTP_AN_HIS.NAN_NAME as NAN_NAME, ");
            sql.addSql("     NTP_AN_HIS.NCN_SID as NCN_SID, ");
            sql.addSql("     NTP_AN_HIS.NAN_KIN_MITUMORI as NAN_KIN_MITUMORI, ");
            sql.addSql("     NTP_AN_HIS.NAN_KIN_JUTYU as NAN_KIN_JUTYU, ");
            sql.addSql("     NTP_AN_HIS.NAN_SYODAN as NAN_SYODAN, ");
            sql.addSql("     NTP_AN_HIS.NAN_STATE as NAN_STATE, ");
            sql.addSql("     NTP_CONTACT.NCN_NAME as NCN_NAME ");
            sql.addSql("  from ");
            sql.addSql("  ( ");
            sql = __getAnkenHistoryTable(frdate, todate, sql, null);
            sql.addSql("    )  NTP_AN_HIS");
            sql.addSql("  LEFT JOIN ");
            sql.addSql("     NTP_CONTACT ");
            sql.addSql("  ON ");
            sql.addSql("     NTP_AN_HIS.NCN_SID=NTP_CONTACT.NCN_SID ");

            if (state >= 0) {
                //状態
                sql.addSql("  where ");
                sql.addSql("    NTP_AN_HIS.NAN_STATE =" + state);
                whereFlg = true;
            }

            //商談状態
            if (ankenState > -1) {
                if (whereFlg) {
                    sql.addSql(" and ");
                } else {
                    sql.addSql(" where ");
                    whereFlg = true;
                }

                sql.addSql("    NTP_AN_HIS.NAN_SYODAN =" + ankenState);
            }

            //グループ指定時の案件
            sql = __getWhereFromAnkenSids(ankenSids, sql, "NTP_AN_HIS", whereFlg);

            sql.addSql("  ) NTP_AN_DATA ");
            sql.addSql("  group by ");
            sql.addSql("    NTP_AN_DATA.NCN_SID, ");
            sql.addSql("    NTP_AN_DATA.NCN_NAME ");

            sql.addSql("  order by NAN_KIN_JUTYU DESC");


            pstmt = con.prepareStatement(sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            Ntp220AnkenModel cmpSalesMdl = null;

            limit = pageNum * limit;

            for (int i = 0; rs.next() && i < limit; i++) {
                cmpSalesMdl = new Ntp220AnkenModel();
                cmpSalesMdl.setCntSid1(rs.getInt("NCN_SID"));
                cmpSalesMdl.setCntName1(rs.getString("NCN_NAME"));
                cmpSalesMdl.setNanKinMitumori(rs.getInt("NAN_KIN_MITUMORI"));
                cmpSalesMdl.setNanKinJutyu(rs.getInt("NAN_KIN_JUTYU"));
                result.add(cmpSalesMdl);
            }

            for (int n = result.size(); n > 0; n--) {
                ret.add(result.get(n - 1));
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
     * 案件に登録された顧客源泉を取得する
     * @param usrSids 選択グループ案件
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @return ArrayList<Ntp220CompanySalesModel>
     * @throws SQLException SQL実行例外
     */
    public Ntp220TotalModel getPeriodKadouData(
            List<Integer> usrSids,
            UDate frdate,
            UDate todate)
                    throws SQLException {

        Ntp220TotalModel ret = new Ntp220TotalModel();

        if (usrSids == null || usrSids.size() == 0) {
            return ret;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Long sumKadouTime = (long) 0;

        String fromDateStr = getFrDateString(frdate);
        String toDateStr = getToDateString(todate);

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NAN_SID, ");
            sql.addSql("   ACO_SID, ");
            sql.addSql("   NIP_MIKOMI, ");
            sql.addSql("   MKB_SID, ");
            sql.addSql("   MKH_SID, ");
            sql.addSql("   NIP_FR_TIME, ");
            sql.addSql("   NIP_TO_TIME ");
            sql.addSql(" from ");
            sql.addSql("   NTP_DATA ");
            sql.addSql(" where  ");
            sql.addSql("(");
            sql.addSql("   NIP_DATE ");
            sql.addSql("   between cast('" + fromDateStr + "' as DATE)");
            sql.addSql("   and cast('" + toDateStr + "' as DATE)");
            sql.addSql(")");

            sql.addSql(" and ");
            sql.addSql("   USR_SID in (");
            for (int i = 0; i < usrSids.size(); i++) {
                if (i != 0) {
                    sql.addSql(",");
                }
                sql.addSql(String.valueOf(usrSids.get(i)));
            }
            sql.addSql(")");

            pstmt = con.prepareStatement(sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            UDate frDate = null;
            UDate toDate = null;

            while (rs.next()) {
                frDate = UDate.getInstanceTimestamp(rs.getTimestamp("NIP_FR_TIME"));
                toDate = UDate.getInstanceTimestamp(rs.getTimestamp("NIP_TO_TIME"));
                Long time = UDateUtil.diffMinute(frDate, toDate);
                sumKadouTime += time;
            }

            ret.setSumKadouTimeMins(sumKadouTime);

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }

    /**
     * <p>閲覧可能か判定します。
     * @param isAdmin 管理者権限
     * @param permKbn 案件権限区分
     * @param isTanto 担当者か
     * @param isPermitUsr 許可ユーザか
     * @param isPermitGrp 許可グループか
     * @return 閲覧可能か
     */
    public boolean hanteiViewable(boolean isAdmin,
            int permKbn,
            boolean isTanto,
            boolean isPermitUsr,
            boolean isPermitGrp) {
        if (isAdmin) {
            return true;
        }
        if (permKbn == GSConstNippou.NAP_KBN_ALL) {
            return true;
        }
        if (isTanto) {
            return true;
        }
        if (isPermitUsr) {
            return true;
        }
        if (isPermitGrp) {
            return true;
        }
        return false;
    }
    /**
     * 日報に登録された案件と時間を取得する
     * @param usrSids 選択グループ案件
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @param pageNum 表示ページ
     * @param limit 表示件数
     * @param reqMdl リクエストモデル
     * @return ArrayList<Ntp220CompanySalesModel>
     * @throws SQLException SQL実行例外
     */
    public List<Ntp220TotalModel> getKadouAnkenData(
            List<Integer> usrSids,
            UDate frdate,
            UDate todate,
            int pageNum,
            int limit,
            RequestModel reqMdl
            )
                    throws SQLException {

        List<Ntp220TotalModel> ret = new ArrayList<Ntp220TotalModel>();

        if (usrSids == null || usrSids.size() == 0) {
            return ret;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;

        int ankenSid = 0;
        String ankenName = null;

        Map<Integer, Long> totalMap = new HashMap<Integer, Long>();
        Map<Integer, String> ankenNameMap = new HashMap<Integer, String>();
        Ntp220TotalModel mdl = null;

        String fromDateStr = getFrDateString(frdate);
        String toDateStr = getToDateString(todate);

        con = getCon();
        //管理者権限がある場合
        CommonBiz commonBiz = new CommonBiz();
        boolean isAdmin = commonBiz.isPluginAdmin(con,
                        reqMdl.getSmodel(),
                        GSConstNippou.PLUGIN_ID_NIPPOU);
        GsMessage gsMsg = new GsMessage(reqMdl);
        int sessionUsrSid = reqMdl.getSmodel().getUsrsid();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select  ");
            sql.addSql("   NTP_ANKEN.NAN_SID     as NAN_SID, ");
            sql.addSql("   NTP_ANKEN.NAN_NAME    as NAN_NAME, ");
            sql.addSql("   NTP_ANKEN.NAN_PERMIT_VIEW   as NAN_PERMIT_VIEW, ");
            sql.addSql("   NTP_TABLE.NIP_FR_TIME as NIP_FR_TIME, ");
            sql.addSql("   NTP_TABLE.NIP_TO_TIME as NIP_TO_TIME,");
            sql.addSql("   NAN_MEM_TABLE.NAN_MEM as NAN_MEM,");
            sql.addSql("   NAP_USR_TABLE.NAP_USR as NAP_USR,");
            sql.addSql("   NAP_GRP_TABLE.NAP_GRP as NAP_GRP");
            sql.addSql(" from ");
            sql.addSql("   NTP_ANKEN right join  ");
            sql.addSql("   ( ");
            sql.addSql("     select  ");
            sql.addSql("       NAN_SID, ");
            sql.addSql("       NIP_FR_TIME, ");
            sql.addSql("       NIP_TO_TIME ");
            sql.addSql("     from  ");
            sql.addSql("       NTP_DATA  ");
            sql.addSql("     where   ");
            sql.addSql("     (");
            sql.addSql("       NIP_DATE ");
            sql.addSql("       between cast('" + fromDateStr + "' as DATE)");
            sql.addSql("       and cast('" + toDateStr + "' as DATE)");
            sql.addSql("      )");
            sql.addSql("     and  ");
            sql.addSql("       USR_SID in ( ");

            for (int i = 0; i < usrSids.size(); i++) {
                if (i != 0) {
                    sql.addSql(",");
                }
                sql.addSql(String.valueOf(usrSids.get(i)));
            }

            sql.addSql("     ) ");
            sql.addSql(" ) NTP_TABLE ");
            sql.addSql(" ON NTP_ANKEN.NAN_SID = NTP_TABLE.NAN_SID ");
            sql.addSql("   left join  ");
            sql.addSql("   ( ");
            sql.addSql("     select  ");
            sql.addSql("       NAN_SID, ");
            sql.addSql("       1 as NAN_MEM");
            sql.addSql("     from NTP_AN_MEMBER");
            sql.addSql("       where USR_SID = ?");
            sql.addIntValue(sessionUsrSid);
            sql.addSql("     ) NAN_MEM_TABLE");
            sql.addSql(" ON NTP_ANKEN.NAN_SID = NAN_MEM_TABLE.NAN_SID ");
            sql.addSql("   left join  ");
            sql.addSql("   ( ");
            sql.addSql("     select  ");
            sql.addSql("       NAN_SID, ");
            sql.addSql("       1 as NAP_USR");
            sql.addSql("     from NTP_ANKEN_PERMIT");
            sql.addSql("       where USR_SID = ?");
            sql.addIntValue(sessionUsrSid);
            sql.addSql("     ) NAP_USR_TABLE");
            sql.addSql(" ON NTP_ANKEN.NAN_SID = NAP_USR_TABLE.NAN_SID ");
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
            sql.addSql(" ON NTP_ANKEN.NAN_SID = NAP_GRP_TABLE.NAN_SID ");

            pstmt = con.prepareStatement(sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            UDate frDate = null;
            UDate toDate = null;

            Long totalKadouTime = (long) 0;

            while (rs.next()) {
                frDate = UDate.getInstanceTimestamp(rs.getTimestamp("NIP_FR_TIME"));
                toDate = UDate.getInstanceTimestamp(rs.getTimestamp("NIP_TO_TIME"));
                Long time = UDateUtil.diffMinute(frDate, toDate);
                ankenSid = rs.getInt("NAN_SID");
                ankenName = rs.getString("NAN_NAME");

                totalKadouTime += time;

                if (StringUtil.isNullZeroStringSpace(ankenName)) {
                    ankenName = gsMsg.getMessage("cmn.specified.no");
                }
                boolean viewable = hanteiViewable(isAdmin,
                        rs.getInt("NAN_PERMIT_VIEW"),
                        (rs.getInt("NAN_MEM") == 1),
                        (rs.getInt("NAP_USR") == 1),
                        (rs.getInt("NAP_GRP") == 1));
                if (!viewable) {
                    ankenName = gsMsg.getMessage("ntp.ntp220.1");
                }

                if (totalMap.get(ankenSid) != null) {
                    time += totalMap.get(ankenSid);
                }
                totalMap.put(ankenSid, time);
                ankenNameMap.put(ankenSid, ankenName);
            }

            List<Map.Entry<Integer, Long>> entries
            = new LinkedList<Map.Entry<Integer, Long>>(totalMap.entrySet());

            Collections.sort(entries, new Comparator<Map.Entry<Integer, Long>>() {
                public int compare(Map.Entry<Integer, Long> o1,
                  Map.Entry<Integer, Long> o2) {
                  Map.Entry<Integer, Long> entry1 = (Map.Entry<Integer, Long>) o1;
                  Map.Entry<Integer, Long> entry2 = (Map.Entry<Integer, Long>) o2;
                  Long long1 = (Long) entry1.getValue();
                  Long long2 = (Long) entry2.getValue();
                  return Integer.valueOf(String.valueOf(long2 - long1));
                }
            });

            if (pageNum > 0) {
                limit = pageNum * limit;

                if (limit > entries.size()) {
                    limit = entries.size();
                }

                for (int i = 0; i < limit; i++) {
                    Map.Entry<Integer, Long> entry = entries.get(i);
                    mdl = new Ntp220TotalModel();
                    int nanSid = entry.getKey();
                    mdl.setSid(nanSid);
                    mdl.setName(ankenNameMap.get(entry.getKey()));
                    mdl.setSumKadouTime(Ntp220Biz.convMinToHour(entry.getValue()));
                    mdl.setTotalKadouTime(Ntp220Biz.convMinToHour(totalKadouTime));
                    ret.add(mdl);
                }
            } else {
                for (Map.Entry<Integer, Long> entry : entries) {
                    mdl = new Ntp220TotalModel();
                    int nanSid = entry.getKey();
                    mdl.setSid(nanSid);
                    mdl.setName(ankenNameMap.get(nanSid));
                    mdl.setSumKadouTime(Ntp220Biz.convMinToHour(entry.getValue()));
                    mdl.setTotalKadouTime(Ntp220Biz.convMinToHour(totalKadouTime));
                    ret.add(mdl);
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
     * 日報に登録された案件の詳細と時間を取得する
     * @param usrSids 選択グループ案件
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @param pageNum 表示ページ
     * @param limit 表示件数
     * @param reqMdl リクエストモデル
     * @return ArrayList<Ntp220CompanySalesModel>
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Ntp220AnkenModel> getKadouAnkenDataDetail(
            List<Integer> usrSids,
            UDate frdate,
            UDate todate,
            int pageNum,
            int limit,
            RequestModel reqMdl)
                    throws SQLException {

        ArrayList<Ntp220AnkenModel> ret = new ArrayList<Ntp220AnkenModel>();

        if (usrSids == null || usrSids.size() == 0) {
            return ret;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Ntp220AnkenModel ankenMdl = null;

        Map<Integer, Long> totalMap = new HashMap<Integer, Long>();
        Map<Integer, Ntp220AnkenModel> ankenMdlMap = new HashMap<Integer, Ntp220AnkenModel>();

        int ankenSid = 0;

        String fromDateStr = getFrDateString(frdate);
        String toDateStr = getToDateString(todate);

        con = getCon();
        //管理者権限がある場合
        CommonBiz commonBiz = new CommonBiz();

        boolean isAdmin = commonBiz.isPluginAdmin(con,
                        reqMdl.getSmodel(),
                        GSConstNippou.PLUGIN_ID_NIPPOU);
        GsMessage gsMsg = new GsMessage(reqMdl);
        int sessionUsrSid = reqMdl.getSmodel().getUsrsid();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql("  select ");
            sql.addSql("    NTP_ANKEN.NAN_SID           as NAN_SID, ");
            sql.addSql("    NTP_ANKEN.NAN_CODE          as NAN_CODE, ");
            sql.addSql("    NTP_ANKEN.NAN_NAME          as NAN_NAME, ");
            sql.addSql("    NTP_ANKEN.NAN_DETIAL        as NAN_DETIAL, ");
            sql.addSql("    NTP_ANKEN.NAN_DATE          as NAN_DATE, ");
            sql.addSql("    NTP_ANKEN.ACO_SID           as ACO_SID, ");
            sql.addSql("    NTP_ANKEN.ABA_SID           as ABA_SID, ");
            sql.addSql("    NTP_ANKEN.NGP_SID           as NGP_SID, ");
            sql.addSql("    NTP_ANKEN.NAN_MIKOMI        as NAN_MIKOMI, ");
            sql.addSql("    NTP_ANKEN.NAN_KIN_MITUMORI  as NAN_KIN_MITUMORI, ");
            sql.addSql("    NTP_ANKEN.NAN_KIN_JUTYU     as NAN_KIN_JUTYU, ");
            sql.addSql("    NTP_ANKEN.NAN_SYODAN        as NAN_SYODAN, ");
            sql.addSql("    NTP_ANKEN.NAN_STATE         as NAN_STATE, ");
            sql.addSql("    NTP_ANKEN.NCN_SID           as NCN_SID, ");
            sql.addSql("    NTP_ANKEN.NAN_AUID          as NAN_AUID, ");
            sql.addSql("    NTP_ANKEN.NAN_ADATE         as NAN_ADATE, ");
            sql.addSql("    NTP_ANKEN.NAN_EUID          as NAN_EUID, ");
            sql.addSql("    NTP_ANKEN.NAN_EDATE         as NAN_EDATE, ");
            sql.addSql("    NTP_ANKEN.NAN_MITUMORI_DATE as NAN_MITUMORI_DATE, ");
            sql.addSql("    NTP_ANKEN.NAN_JUTYU_DATE    as NAN_JUTYU_DATE, ");
            sql.addSql("    NTP_ANKEN.NAN_PERMIT_VIEW   as NAN_PERMIT_VIEW, ");
            sql.addSql("    NTP_ANKEN_TABLE.NIP_FR_TIME as NIP_FR_TIME, ");
            sql.addSql("    NTP_ANKEN_TABLE.NIP_TO_TIME as NIP_TO_TIME, ");
            sql.addSql("    ADR_COMPANY.ACO_NAME        as ACO_NAME, ");
            sql.addSql("    ADR_COMPANY_BASE.ABA_NAME   as ABA_NAME, ");
            sql.addSql("    CASE NTP_ANKEN.NAN_MIKOMI");
            sql.addSql("    WHEN 0 THEN '10%'");
            sql.addSql("    WHEN 1 THEN '30%'");
            sql.addSql("    WHEN 2 THEN '50%'");
            sql.addSql("    WHEN 3 THEN '70%'");
            sql.addSql("    WHEN 4 THEN '100%'");
            sql.addSql("    ELSE '0%' END  NAN_MIKOMI_VAL, ");
            sql.addSql("    NTP_PROCESS.NGY_SID         as NGY_SID, ");
            sql.addSql("    NTP_PROCESS.NGP_NAME        as NGP_NAME, ");
            sql.addSql("    NTP_GYOMU.NGY_NAME          as NGY_NAME, ");
            sql.addSql("    NTP_CONTACT.NCN_NAME        as NCN_NAME, ");
            sql.addSql("    NAN_MEM_TABLE.NAN_MEM as NAN_MEM,");
            sql.addSql("    NAP_USR_TABLE.NAP_USR as NAP_USR,");
            sql.addSql("    NAP_GRP_TABLE.NAP_GRP as NAP_GRP");
            sql.addSql("  from ");
            sql.addSql("    NTP_ANKEN ");
            sql.addSql("  right join ");
            sql.addSql("  ( ");
            sql.addSql("     select ");
            sql.addSql("            NTP_ANKEN.NAN_SID      as NAN_SID, ");
            sql.addSql("            NTP_TABLE.NIP_FR_TIME  as NIP_FR_TIME, ");
            sql.addSql("            NTP_TABLE.NIP_TO_TIME  as NIP_TO_TIME ");
            sql.addSql("          from  ");
            sql.addSql("            NTP_ANKEN right join ");
            sql.addSql("            (  ");
            sql.addSql("              select   ");
            sql.addSql("                NAN_SID, ");
            sql.addSql("                NIP_FR_TIME, ");
            sql.addSql("                NIP_TO_TIME ");
            sql.addSql("              from   ");
            sql.addSql("                NTP_DATA   ");
            sql.addSql("              where   ");
            sql.addSql("              (");
            sql.addSql("                NIP_DATE ");
            sql.addSql("                between cast('" + fromDateStr + "' as DATE)");
            sql.addSql("                and cast('" + toDateStr + "' as DATE)");
            sql.addSql("               )");
            sql.addSql("              and   ");
            sql.addSql("                USR_SID in (  ");

            for (int i = 0; i < usrSids.size(); i++) {
                if (i != 0) {
                    sql.addSql(",");
                }
                sql.addSql(String.valueOf(usrSids.get(i)));
            }

            sql.addSql("              )  ");
            sql.addSql("          ) NTP_TABLE  ");
            sql.addSql("          ON NTP_ANKEN.NAN_SID = NTP_TABLE.NAN_SID ");
            sql.addSql("   ) NTP_ANKEN_TABLE  ON NTP_ANKEN.NAN_SID = NTP_ANKEN_TABLE.NAN_SID");
            sql.addSql("  left join ");
            sql.addSql("    ADR_COMPANY ");
            sql.addSql("      ON NTP_ANKEN.ACO_SID = ADR_COMPANY.ACO_SID ");
            sql.addSql("  left join ");
            sql.addSql("    ADR_COMPANY_BASE ");
            sql.addSql("      ON NTP_ANKEN.ABA_SID = ADR_COMPANY_BASE.ABA_SID ");
            sql.addSql("  left join ");
            sql.addSql("     NTP_PROCESS ");
            sql.addSql("       ON NTP_ANKEN.NGP_SID = NTP_PROCESS.NGP_SID ");
            sql.addSql(" LEFT JOIN");
            sql.addSql("   NTP_GYOMU");
            sql.addSql(" ON");
            sql.addSql("   NTP_PROCESS.NGY_SID = NTP_GYOMU.NGY_SID");
            sql.addSql(" LEFT JOIN");
            sql.addSql("   NTP_CONTACT");
            sql.addSql(" ON");
            sql.addSql("   NTP_ANKEN.NCN_SID = NTP_CONTACT.NCN_SID");
            sql.addSql("   left join  ");
            sql.addSql("   ( ");
            sql.addSql("     select  ");
            sql.addSql("       NAN_SID, ");
            sql.addSql("       1 as NAN_MEM");
            sql.addSql("     from NTP_AN_MEMBER");
            sql.addSql("       where USR_SID = ?");
            sql.addIntValue(sessionUsrSid);
            sql.addSql("     ) NAN_MEM_TABLE");
            sql.addSql(" ON NTP_ANKEN.NAN_SID = NAN_MEM_TABLE.NAN_SID ");
            sql.addSql("   left join  ");
            sql.addSql("   ( ");
            sql.addSql("     select  ");
            sql.addSql("       NAN_SID, ");
            sql.addSql("       1 as NAP_USR");
            sql.addSql("     from NTP_ANKEN_PERMIT");
            sql.addSql("       where USR_SID = ?");
            sql.addIntValue(sessionUsrSid);
            sql.addSql("     ) NAP_USR_TABLE");
            sql.addSql(" ON NTP_ANKEN.NAN_SID = NAP_USR_TABLE.NAN_SID ");
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
            sql.addSql(" ON NTP_ANKEN.NAN_SID = NAP_GRP_TABLE.NAN_SID ");

            pstmt = con.prepareStatement(sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            UDate frDate = null;
            UDate toDate = null;

            Long totalTime = (long) 0;

            while (rs.next()) {
                frDate = UDate.getInstanceTimestamp(rs.getTimestamp("NIP_FR_TIME"));
                toDate = UDate.getInstanceTimestamp(rs.getTimestamp("NIP_TO_TIME"));
                Long time = UDateUtil.diffMinute(frDate, toDate);
                totalTime += time;

                ankenSid = rs.getInt("NAN_SID");

                if (totalMap.get(ankenSid) != null) {
                    time += totalMap.get(ankenSid);
                }

                totalMap.put(ankenSid, time);

                if (ankenMdlMap.get(ankenSid) != null) {
                    ankenMdl = ankenMdlMap.get(ankenSid);
                } else {
                    boolean viewable = hanteiViewable(isAdmin,
                            rs.getInt("NAN_PERMIT_VIEW"),
                            (rs.getInt("NAN_MEM") == 1),
                            (rs.getInt("NAP_USR") == 1),
                            (rs.getInt("NAP_GRP") == 1));
                    if (!viewable) {
                        ankenMdl = new Ntp220AnkenModel();
                        ankenMdl.setNanSid(rs.getInt("NAN_SID"));
                        ankenMdl.setNanName(gsMsg.getMessage("ntp.ntp220.1"));
                    } else {
                        ankenMdl = __getNtpAnkenDataFromRs2(rs, frdate, todate);
                    }
                    ankenMdl.setViewable(viewable);
                }

                ankenMdl.setKadouHours(Ntp220Biz.convMinToHour(time));

                ankenMdlMap.put(ankenSid, ankenMdl);
            }

            List<Map.Entry<Integer, Long>> entries
            = new LinkedList<Map.Entry<Integer, Long>>(totalMap.entrySet());
            Collections.sort(entries, new Comparator<Map.Entry<Integer, Long>>() {
                public int compare(Map.Entry<Integer, Long> o1,
                  Map.Entry<Integer, Long> o2) {
                  Map.Entry<Integer, Long> entry1 = (Map.Entry<Integer, Long>) o1;
                  Map.Entry<Integer, Long> entry2 = (Map.Entry<Integer, Long>) o2;
                  Long long1 = (Long) entry1.getValue();
                  Long long2 = (Long) entry2.getValue();
                  return Integer.valueOf(String.valueOf(long2 - long1));
                }
            });

            if (pageNum > 0) {

                int startNum = 0;

                if (pageNum > 1) {
                    startNum = (pageNum - 1) * limit;
                }

                limit = pageNum * limit;

                if (limit > entries.size()) {
                    limit = entries.size();
                }

                for (int i = startNum; i < limit; i++) {
                    ankenMdl = new Ntp220AnkenModel();
                    Map.Entry<Integer, Long> entry = entries.get(i);
                    int nanSid = entry.getKey();
                    ankenMdl = ankenMdlMap.get(nanSid);
                    ankenMdl.setTotalKadouHours(Ntp220Biz.convMinToHour(totalTime));
                    ret.add(ankenMdl);
                }
            } else {
                for (Map.Entry<Integer, Long> entry : entries) {
                    int nanSid = entry.getKey();
                    ankenMdl = ankenMdlMap.get(nanSid);
                    ankenMdl.setTotalKadouHours(Ntp220Biz.convMinToHour(totalTime));
                    ret.add(ankenMdl);
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
     * 日報に登録された企業と時間を取得する
     * @param usrSids 選択グループ案件
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @param pageNum 表示ページ
     * @param limit 表示件数
     * @param kbn リクエスト区分 0:グラフ描画時 1:詳細取得時
     * @return ArrayList<Ntp220CompanySalesModel>
     * @throws SQLException SQL実行例外
     */
    public List<Ntp220TotalModel> getKadouKigyouData(
            List<Integer> usrSids,
            UDate frdate,
            UDate todate,
            int pageNum,
            int limit,
            int kbn)
                    throws SQLException {

        List<Ntp220TotalModel> ret = new ArrayList<Ntp220TotalModel>();

        if (usrSids == null || usrSids.size() == 0) {
            return ret;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;

        int acoSid = 0;
        String acoName = null;
        Map<Integer, Long> totalMap = new TreeMap<Integer, Long>();
        Map<Integer, String> nameMap = new TreeMap<Integer, String>();

        Ntp220TotalModel mdl = null;

        String fromDateStr = getFrDateString(frdate);
        String toDateStr = getToDateString(todate);

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   ADR_COMPANY.ACO_SID     as ACO_SID, ");
            sql.addSql("   ADR_COMPANY.ACO_NAME    as ACO_NAME, ");
            sql.addSql("   NTP_TABLE.NIP_FR_TIME as NIP_FR_TIME, ");
            sql.addSql("   NTP_TABLE.NIP_TO_TIME as NIP_TO_TIME ");
            sql.addSql(" from ");
            sql.addSql("   ADR_COMPANY right join ");
            sql.addSql("   ( ");
            sql.addSql("     select ");
            sql.addSql("       ACO_SID, ");
            sql.addSql("       NIP_FR_TIME, ");
            sql.addSql("       NIP_TO_TIME ");
            sql.addSql("     from ");
            sql.addSql("       NTP_DATA ");
            sql.addSql("     where ");
            sql.addSql("     ( ");
            sql.addSql("       NIP_DATE ");
            sql.addSql("       between cast('" + fromDateStr + "' as DATE)");
            sql.addSql("       and cast('" + toDateStr + "' as DATE)");
            sql.addSql("      )");
            sql.addSql("     and  ");
            sql.addSql("       USR_SID in ( ");

            for (int i = 0; i < usrSids.size(); i++) {
                if (i != 0) {
                    sql.addSql(",");
                }
                sql.addSql(String.valueOf(usrSids.get(i)));
            }

            sql.addSql("     ) ");
            sql.addSql(" ) NTP_TABLE  ");
            sql.addSql(" ON ADR_COMPANY.ACO_SID = NTP_TABLE.ACO_SID;  ");

            pstmt = con.prepareStatement(sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            UDate frDate = null;
            UDate toDate = null;

            Long totalKadouTime = (long) 0;

            while (rs.next()) {
                frDate = UDate.getInstanceTimestamp(rs.getTimestamp("NIP_FR_TIME"));
                toDate = UDate.getInstanceTimestamp(rs.getTimestamp("NIP_TO_TIME"));
                Long time = UDateUtil.diffMinute(frDate, toDate);
                totalKadouTime += time;
                acoName = rs.getString("ACO_NAME");
                acoSid = rs.getInt("ACO_SID");
                if (StringUtil.isNullZeroStringSpace(acoName)) {
                    acoName = "指定なし";
                }

                if (totalMap.get(acoSid) != null) {
                    time += totalMap.get(acoSid);
                }
                totalMap.put(acoSid, time);
                nameMap.put(acoSid, acoName);
            }


            List<Map.Entry<Integer, Long>> entries
                  = new LinkedList<Map.Entry<Integer, Long>>(totalMap.entrySet());
            Collections.sort(entries, new Comparator<Map.Entry<Integer, Long>>() {
                public int compare(Map.Entry<Integer, Long> o1,
                        Map.Entry<Integer, Long> o2) {
                    Map.Entry<Integer, Long> entry1 = (Map.Entry<Integer, Long>) o1;
                    Map.Entry<Integer, Long> entry2 = (Map.Entry<Integer, Long>) o2;
                    Long long1 = (Long) entry1.getValue();
                    Long long2 = (Long) entry2.getValue();
                    return Integer.valueOf(String.valueOf(long2 - long1));
                }
            });


            if (kbn == 0) {
                if (pageNum > 0) {
                    limit = pageNum * limit;

                    if (limit > entries.size()) {
                        limit = entries.size();
                    }

                    for (int i = 0; i < limit; i++) {
                        Map.Entry<Integer, Long> entry = entries.get(i);
                        mdl = new Ntp220TotalModel();
                        mdl.setSid(entry.getKey());
                        mdl.setName(nameMap.get(entry.getKey()));
                        mdl.setSumKadouTime(Ntp220Biz.convMinToHour(entry.getValue()));
                        mdl.setTotalKadouTime(Ntp220Biz.convMinToHour(totalKadouTime));
                        ret.add(mdl);
                    }
                } else {
                    for (Map.Entry<Integer, Long> entry : entries) {
                        mdl = new Ntp220TotalModel();
                        mdl.setSid(entry.getKey());
                        mdl.setName(nameMap.get(entry.getKey()));
                        mdl.setSumKadouTime(Ntp220Biz.convMinToHour(entry.getValue()));
                        mdl.setTotalKadouTime(Ntp220Biz.convMinToHour(totalKadouTime));
                        ret.add(mdl);
                    }
                }
            } else {
                if (pageNum > 0) {

                    int startNum = 0;

                    if (pageNum > 1) {
                        startNum = (pageNum - 1) * limit;
                    }

                    limit = pageNum * limit;

                    if (limit > entries.size()) {
                        limit = entries.size();
                    }

                    for (int i = startNum; i < limit; i++) {
                        Map.Entry<Integer, Long> entry = entries.get(i);
                        mdl = new Ntp220TotalModel();
                        mdl.setSid(entry.getKey());
                        mdl.setName(nameMap.get(entry.getKey()));
                        mdl.setSumKadouTime(Ntp220Biz.convMinToHour(entry.getValue()));
                        mdl.setTotalKadouTime(Ntp220Biz.convMinToHour(totalKadouTime));
                        ret.add(mdl);
                    }
                } else {
                    for (Map.Entry<Integer, Long> entry : entries) {
                        mdl = new Ntp220TotalModel();
                        mdl.setSid(entry.getKey());
                        mdl.setName(nameMap.get(entry.getKey()));
                        mdl.setSumKadouTime(Ntp220Biz.convMinToHour(entry.getValue()));
                        mdl.setTotalKadouTime(Ntp220Biz.convMinToHour(totalKadouTime));
                        ret.add(mdl);
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
     * 日報に登録された活動分類と時間を取得する
     * @param usrSids 選択グループ案件
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @param pageNum 表示ページ
     * @param limit 表示件数
     * @param kbn リクエスト区分 0:グラフ描画時 1:詳細取得時
     * @return ArrayList<Ntp220CompanySalesModel>
     * @throws SQLException SQL実行例外
     */
    public List<Ntp220TotalModel> getKadouKbunruiData(
            List<Integer> usrSids,
            UDate frdate,
            UDate todate,
            int pageNum,
            int limit,
            int kbn)
                    throws SQLException {

        List<Ntp220TotalModel> ret = new ArrayList<Ntp220TotalModel>();

        if (usrSids == null || usrSids.size() == 0) {
            return ret;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;

        int kbunruiSid = 0;
        String kbunruiName = null;
        Map<Integer, Long> totalMap = new HashMap<Integer, Long>();
        Map<Integer, String> nameMap = new HashMap<Integer, String>();
        Ntp220TotalModel mdl = null;

        String fromDateStr = getFrDateString(frdate);
        String toDateStr = getToDateString(todate);

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select  ");
            sql.addSql("   NTP_KTBUNRUI.NKB_SID     as NKB_SID, ");
            sql.addSql("   NTP_KTBUNRUI.NKB_NAME    as NKB_NAME, ");
            sql.addSql("   NTP_TABLE.NIP_FR_TIME as NIP_FR_TIME, ");
            sql.addSql("   NTP_TABLE.NIP_TO_TIME as NIP_TO_TIME");
            sql.addSql(" from ");
            sql.addSql("   NTP_KTBUNRUI right join  ");
            sql.addSql("   ( ");
            sql.addSql("     select  ");
            sql.addSql("       MKB_SID, ");
            sql.addSql("       NIP_FR_TIME, ");
            sql.addSql("       NIP_TO_TIME ");
            sql.addSql("     from  ");
            sql.addSql("       NTP_DATA  ");
            sql.addSql("     where   ");
            sql.addSql("     (");
            sql.addSql("       NIP_DATE ");
            sql.addSql("       between cast('" + fromDateStr + "' as DATE)");
            sql.addSql("       and cast('" + toDateStr + "' as DATE)");
            sql.addSql("      )");
            sql.addSql("     and  ");
            sql.addSql("       USR_SID in ( ");

            for (int i = 0; i < usrSids.size(); i++) {
                if (i != 0) {
                    sql.addSql(",");
                }
                sql.addSql(String.valueOf(usrSids.get(i)));
            }

            sql.addSql("     ) ");
            sql.addSql(" ) NTP_TABLE ");
            sql.addSql(" ON NTP_KTBUNRUI.NKB_SID = NTP_TABLE.MKB_SID; ");

            pstmt = con.prepareStatement(sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            UDate frDate = null;
            UDate toDate = null;

            Long totalKadouTime = (long) 0;

            while (rs.next()) {
                frDate = UDate.getInstanceTimestamp(rs.getTimestamp("NIP_FR_TIME"));
                toDate = UDate.getInstanceTimestamp(rs.getTimestamp("NIP_TO_TIME"));
                Long time = UDateUtil.diffMinute(frDate, toDate);
                totalKadouTime += time;

                kbunruiName = rs.getString("NKB_NAME");
                kbunruiSid = rs.getInt("NKB_SID");

                if (StringUtil.isNullZeroStringSpace(kbunruiName)) {
                    kbunruiName = "指定なし";
                }

                if (totalMap.get(kbunruiSid) != null) {
                    time += totalMap.get(kbunruiSid);
                }
                totalMap.put(kbunruiSid, time);
                nameMap.put(kbunruiSid, kbunruiName);
            }

            List<Map.Entry<Integer, Long>> entries
            = new LinkedList<Map.Entry<Integer, Long>>(totalMap.entrySet());
            Collections.sort(entries, new Comparator<Map.Entry<Integer, Long>>() {
                public int compare(Map.Entry<Integer, Long> o1,
                  Map.Entry<Integer, Long> o2) {
                  Map.Entry<Integer, Long> entry1 = (Map.Entry<Integer, Long>) o1;
                  Map.Entry<Integer, Long> entry2 = (Map.Entry<Integer, Long>) o2;
                  Long long1 = (Long) entry1.getValue();
                  Long long2 = (Long) entry2.getValue();
                  return Integer.valueOf(String.valueOf(long2 - long1));
                }
            });

            if (kbn == 0) {
                if (pageNum > 0) {
                    limit = pageNum * limit;

                    if (limit > entries.size()) {
                        limit = entries.size();
                    }

                    for (int i = 0; i < limit; i++) {
                        Map.Entry<Integer, Long> entry = entries.get(i);
                        mdl = new Ntp220TotalModel();
                        mdl.setName(nameMap.get(entry.getKey()));
                        mdl.setSumKadouTime(Ntp220Biz.convMinToHour(entry.getValue()));
                        mdl.setTotalKadouTime(Ntp220Biz.convMinToHour(totalKadouTime));
                        ret.add(mdl);
                    }
                } else {
                    for (Map.Entry<Integer, Long> entry : entries) {
                        mdl = new Ntp220TotalModel();
                        mdl.setName(nameMap.get(entry.getKey()));
                        mdl.setSumKadouTime(Ntp220Biz.convMinToHour(entry.getValue()));
                        mdl.setTotalKadouTime(Ntp220Biz.convMinToHour(totalKadouTime));
                        ret.add(mdl);
                    }
                }
            } else {
                if (pageNum > 0) {

                    int startNum = 0;

                    if (pageNum > 1) {
                        startNum = (pageNum - 1) * limit;
                    }

                    limit = pageNum * limit;

                    if (limit > entries.size()) {
                        limit = entries.size();
                    }

                    for (int i = startNum; i < limit; i++) {
                        Map.Entry<Integer, Long> entry = entries.get(i);
                        mdl = new Ntp220TotalModel();
                        mdl.setName(nameMap.get(entry.getKey()));
                        mdl.setSumKadouTime(Ntp220Biz.convMinToHour(entry.getValue()));
                        mdl.setTotalKadouTime(Ntp220Biz.convMinToHour(totalKadouTime));
                        ret.add(mdl);
                    }
                } else {
                    for (Map.Entry<Integer, Long> entry : entries) {
                        mdl = new Ntp220TotalModel();
                        mdl.setName(nameMap.get(entry.getKey()));
                        mdl.setSumKadouTime(Ntp220Biz.convMinToHour(entry.getValue()));
                        mdl.setTotalKadouTime(Ntp220Biz.convMinToHour(totalKadouTime));
                        ret.add(mdl);
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
     * 日報に登録された活動方法と時間を取得する
     * @param usrSids 選択グループ案件
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @param pageNum 表示ページ
     * @param limit 表示件数
     * @param kbn リクエスト区分 0:グラフ描画時 1:詳細取得時
     * @return ArrayList<Ntp220CompanySalesModel>
     * @throws SQLException SQL実行例外
     */
    public List<Ntp220TotalModel> getKadouKhouhouData(
            List<Integer> usrSids,
            UDate frdate,
            UDate todate,
            int pageNum,
            int limit,
            int kbn)
                    throws SQLException {

        List<Ntp220TotalModel> ret = new ArrayList<Ntp220TotalModel>();

        if (usrSids == null || usrSids.size() == 0) {
            return ret;
        }
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;

        int khouhouSid = 0;
        String khouhouName = null;
        Map<Integer, Long> totalMap = new HashMap<Integer, Long>();
        Map<Integer, String> nameMap = new HashMap<Integer, String>();
        Ntp220TotalModel mdl = null;

        String fromDateStr = getFrDateString(frdate);
        String toDateStr = getToDateString(todate);

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select  ");
            sql.addSql("   NTP_KTHOUHOU.NKH_SID     as NKH_SID, ");
            sql.addSql("   NTP_KTHOUHOU.NKH_NAME    as NKH_NAME, ");
            sql.addSql("   NTP_TABLE.NIP_FR_TIME as NIP_FR_TIME, ");
            sql.addSql("   NTP_TABLE.NIP_TO_TIME as NIP_TO_TIME");
            sql.addSql(" from ");
            sql.addSql("   NTP_KTHOUHOU right join  ");
            sql.addSql("   ( ");
            sql.addSql("     select  ");
            sql.addSql("       MKH_SID, ");
            sql.addSql("       NIP_FR_TIME, ");
            sql.addSql("       NIP_TO_TIME ");
            sql.addSql("     from  ");
            sql.addSql("       NTP_DATA  ");
            sql.addSql("     where   ");
            sql.addSql("     (");
            sql.addSql("       NIP_DATE ");
            sql.addSql("       between cast('" + fromDateStr + "' as DATE)");
            sql.addSql("       and cast('" + toDateStr + "' as DATE)");
            sql.addSql("      )");
            sql.addSql("     and  ");
            sql.addSql("       USR_SID in ( ");

            for (int i = 0; i < usrSids.size(); i++) {
                if (i != 0) {
                    sql.addSql(",");
                }
                sql.addSql(String.valueOf(usrSids.get(i)));
            }

            sql.addSql("     ) ");
            sql.addSql(" ) NTP_TABLE ");
            sql.addSql(" ON NTP_KTHOUHOU.NKH_SID = NTP_TABLE.MKH_SID; ");

            pstmt = con.prepareStatement(sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            UDate frDate = null;
            UDate toDate = null;

            Long totalKadouTime = (long) 0;

            while (rs.next()) {
                frDate = UDate.getInstanceTimestamp(rs.getTimestamp("NIP_FR_TIME"));
                toDate = UDate.getInstanceTimestamp(rs.getTimestamp("NIP_TO_TIME"));
                Long time = UDateUtil.diffMinute(frDate, toDate);
                totalKadouTime += time;
                khouhouName = rs.getString("NKH_NAME");
                khouhouSid = rs.getInt("NKH_SID");
                if (StringUtil.isNullZeroStringSpace(khouhouName)) {
                    khouhouName = "指定なし";
                }

                if (totalMap.get(khouhouSid) != null) {
                    time += totalMap.get(khouhouSid);
                }
                totalMap.put(khouhouSid, time);
                nameMap.put(khouhouSid, khouhouName);
            }

            List<Map.Entry<Integer, Long>> entries
            = new LinkedList<Map.Entry<Integer, Long>>(totalMap.entrySet());
            Collections.sort(entries, new Comparator<Map.Entry<Integer, Long>>() {
                public int compare(Map.Entry<Integer, Long> o1,
                  Map.Entry<Integer, Long> o2) {
                  Map.Entry<Integer, Long> entry1 = (Map.Entry<Integer, Long>) o1;
                  Map.Entry<Integer, Long> entry2 = (Map.Entry<Integer, Long>) o2;
                  Long long1 = (Long) entry1.getValue();
                  Long long2 = (Long) entry2.getValue();
                  return Integer.valueOf(String.valueOf(long2 - long1));
                }
            });

            if (kbn == 0) {
                if (pageNum > 0) {
                    limit = pageNum * limit;

                    if (limit > entries.size()) {
                        limit = entries.size();
                    }

                    for (int i = 0; i < limit; i++) {
                        Map.Entry<Integer, Long> entry = entries.get(i);
                        mdl = new Ntp220TotalModel();
                        mdl.setName(nameMap.get(entry.getKey()));
                        mdl.setSumKadouTime(Ntp220Biz.convMinToHour(entry.getValue()));
                        mdl.setTotalKadouTime(Ntp220Biz.convMinToHour(totalKadouTime));
                        ret.add(mdl);
                    }
                } else {
                    for (Map.Entry<Integer, Long> entry : entries) {
                        mdl = new Ntp220TotalModel();
                        mdl.setName(nameMap.get(entry.getKey()));
                        mdl.setSumKadouTime(Ntp220Biz.convMinToHour(entry.getValue()));
                        mdl.setTotalKadouTime(Ntp220Biz.convMinToHour(totalKadouTime));
                        ret.add(mdl);
                    }
                }
            } else {
                if (pageNum > 0) {

                    int startNum = 0;

                    if (pageNum > 1) {
                        startNum = (pageNum - 1) * limit;
                    }

                    limit = pageNum * limit;

                    if (limit > entries.size()) {
                        limit = entries.size();
                    }

                    for (int i = startNum; i < limit; i++) {
                        Map.Entry<Integer, Long> entry = entries.get(i);
                        mdl = new Ntp220TotalModel();
                        mdl.setName(nameMap.get(entry.getKey()));
                        mdl.setSumKadouTime(Ntp220Biz.convMinToHour(entry.getValue()));
                        mdl.setTotalKadouTime(Ntp220Biz.convMinToHour(totalKadouTime));
                        ret.add(mdl);
                    }
                } else {
                    for (Map.Entry<Integer, Long> entry : entries) {
                        mdl = new Ntp220TotalModel();
                        mdl.setName(nameMap.get(entry.getKey()));
                        mdl.setSumKadouTime(Ntp220Biz.convMinToHour(entry.getValue()));
                        mdl.setTotalKadouTime(Ntp220Biz.convMinToHour(totalKadouTime));
                        ret.add(mdl);
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
     * 案件に登録されたプロセスを取得する
     * @param ankenSids 選択グループ案件
     * @param state 状態 0:進行中 1:完了
     * @param ankenState 案件状態  1:商談中 2:受注 3:失注
     * @param pageNum 表示ページ
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @param limit 表示件数
     * @param gyoushuSid 業種SID
     * @return ArrayList<Ntp220CompanySalesModel>
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Object>
                        getAllProcessSales(List<Integer> ankenSids,
                                                       int state,
                                                       int ankenState,
                                                       int pageNum,
                                                       int limit,
                                                       UDate frdate,
                                                       UDate todate,
                                                       int gyoushuSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Object> result = new ArrayList<Object>();
        ArrayList<Object> ret = new ArrayList<Object>();
        boolean whereFlg = false;

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select  ");
            sql.addSql("   NTP_AN_DATA.NGP_SID as NGP_SID, ");
            sql.addSql("   NTP_AN_DATA.NGP_NAME as NGP_NAME, ");
            sql.addSql("   sum(NTP_AN_DATA.NAN_KIN_MITUMORI) as NAN_KIN_MITUMORI, ");
            sql.addSql("   sum(NTP_AN_DATA.NAN_KIN_JUTYU) as NAN_KIN_JUTYU ");
            sql.addSql(" from  ");
            sql.addSql(" (select  ");
            sql.addSql("     NTP_ANKEN.NAN_SID as NAN_SID, ");
            sql.addSql("     NTP_ANKEN.NAN_NAME as NAN_NAME, ");
            sql.addSql("     NTP_ANKEN.NGP_SID as NGP_SID, ");
            sql.addSql("     NTP_ANKEN_MITUMORI.NAN_KIN_MITUMORI as NAN_KIN_MITUMORI, ");
            sql.addSql("     NTP_ANKEN_JUTYU.NAN_KIN_JUTYU as NAN_KIN_JUTYU, ");
            sql.addSql("     NTP_ANKEN.NAN_SYODAN as NAN_SYODAN, ");
            sql.addSql("     NTP_ANKEN.NAN_STATE as NAN_STATE, ");
            sql.addSql("     NTP_PROCESS.NGP_NAME as NGP_NAME ");
            sql.addSql("  from ");
            sql.addSql("     NTP_ANKEN  ");
            sql.addSql("  LEFT JOIN ");
            sql.addSql("     NTP_PROCESS ");
            sql.addSql("  ON ");
            sql.addSql("     NTP_ANKEN.NGP_SID=NTP_PROCESS.NGP_SID ");

            //見積もり金額
            __getMitumoriSql(frdate, todate, sql, null, "NTP_ANKEN", "NTP_ANKEN_MITUMORI");
            //受注金額
            __getJutyuSql(frdate, todate, sql, null, "NTP_ANKEN", "NTP_ANKEN_JUTYU");

            sql = __getWhereDateForAnken(frdate, todate, sql, "NTP_ANKEN", false);

            if (state >= 0) {
                //状態
                sql.addSql("  and ");
                sql.addSql("    NTP_ANKEN.NAN_STATE =" + state);
            }

            //商談状態
            if (ankenState > -1) {
                sql.addSql(" and ");
                sql.addSql("    NTP_ANKEN.NAN_SYODAN =" + ankenState);
            }

            //グループ指定時の案件
            sql = __getWhereFromAnkenSids(ankenSids, sql, "NTP_ANKEN", true);
            if (gyoushuSid != -1) {
                sql.addSql(" and ");
                sql.addSql("    NTP_PROCESS.NGY_SID =" + gyoushuSid);
            }


            sql.addSql("  ) NTP_AN_DATA ");
            sql.addSql("  group by ");
            sql.addSql("    NTP_AN_DATA.NGP_SID, ");
            sql.addSql("    NTP_AN_DATA.NGP_NAME ");

            sql.addSql(" union ");

            sql.addSql(" select   ");
            sql.addSql("   NTP_AN_DATA.NGP_SID as NGP_SID,  ");
            sql.addSql("   NTP_AN_DATA.NGP_NAME as NGP_NAME,  ");
            sql.addSql("   sum(NTP_AN_DATA.NAN_KIN_MITUMORI) as NAN_KIN_MITUMORI,  ");
            sql.addSql("   sum(NTP_AN_DATA.NAN_KIN_JUTYU) as NAN_KIN_JUTYU  ");
            sql.addSql(" from  ");
            sql.addSql(" (select  ");
            sql.addSql("     NTP_AN_HIS.NAN_SID as NAN_SID, ");
            sql.addSql("     NTP_AN_HIS.NAN_NAME as NAN_NAME, ");
            sql.addSql("     NTP_AN_HIS.NGP_SID as NGP_SID, ");
            sql.addSql("     NTP_AN_HIS.NAN_KIN_MITUMORI as NAN_KIN_MITUMORI, ");
            sql.addSql("     NTP_AN_HIS.NAN_KIN_JUTYU as NAN_KIN_JUTYU, ");
            sql.addSql("     NTP_AN_HIS.NAN_SYODAN as NAN_SYODAN, ");
            sql.addSql("     NTP_AN_HIS.NAN_STATE as NAN_STATE, ");
            sql.addSql("     NTP_PROCESS.NGP_NAME as NGP_NAME ");
            sql.addSql("  from ");
            sql.addSql("  ( ");
            sql = __getAnkenHistoryTable(frdate, todate, sql, null);
            sql.addSql("    )  NTP_AN_HIS");
            sql.addSql("  LEFT JOIN ");
            sql.addSql("     NTP_PROCESS ");
            sql.addSql("  ON ");
            sql.addSql("     NTP_AN_HIS.NGP_SID=NTP_PROCESS.NGP_SID ");

            if (state >= 0) {
                //状態
                sql.addSql("  where ");
                sql.addSql("    NTP_AN_HIS.NAN_STATE =" + state);
                whereFlg = true;
            }

            //商談状態
            if (ankenState > -1) {
                if (whereFlg) {
                    sql.addSql(" and ");
                } else {
                    sql.addSql(" where ");
                    whereFlg = true;
                }

                sql.addSql("    NTP_AN_HIS.NAN_SYODAN =" + ankenState);
            }

            if (gyoushuSid != -1) {
                if (whereFlg) {
                    sql.addSql(" and ");
                } else {
                    sql.addSql(" where ");
                    whereFlg = true;
                }
                sql.addSql("    NTP_PROCESS.NGY_SID =" + gyoushuSid);
            }

            //グループ指定時の案件
            sql = __getWhereFromAnkenSids(ankenSids, sql, "NTP_AN_HIS", whereFlg);

            sql.addSql("  ) NTP_AN_DATA ");
            sql.addSql("  group by ");
            sql.addSql("    NTP_AN_DATA.NGP_SID, ");
            sql.addSql("    NTP_AN_DATA.NGP_NAME ");

            sql.addSql("  order by NAN_KIN_JUTYU DESC");

            pstmt = con.prepareStatement(sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            Ntp220AnkenModel cmpSalesMdl = null;

            limit = pageNum * limit;

            for (int i = 0; rs.next() && i < limit; i++) {
                cmpSalesMdl = new Ntp220AnkenModel();
                cmpSalesMdl.setCntSid1(rs.getInt("NGP_SID"));
                cmpSalesMdl.setCntName1(rs.getString("NGP_NAME"));
                cmpSalesMdl.setNanKinMitumori(rs.getInt("NAN_KIN_MITUMORI"));
                cmpSalesMdl.setNanKinJutyu(rs.getInt("NAN_KIN_JUTYU"));
                result.add(cmpSalesMdl);
            }

            for (int n = result.size(); n > 0; n--) {
                ret.add(result.get(n - 1));
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
     * 案件に登録された見込み度を取得する
     * @param ankenSids 選択グループ案件
     * @param state 状態 0:進行中 1:完了
     * @param ankenState 案件状態  1:商談中 2:受注 3:失注
     * @param pageNum 表示ページ
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @param limit 表示件数
     * @return ArrayList<Ntp220CompanySalesModel>
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Object>
                        getAllMikomidoSales(List<Integer> ankenSids,
                                                       int state,
                                                       int ankenState,
                                                       int pageNum,
                                                       int limit,
                                                       UDate frdate,
                                                       UDate todate) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Object> result = new ArrayList<Object>();
        ArrayList<Object> ret = new ArrayList<Object>();
        boolean whereFlg = false;

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select  ");
            sql.addSql("   NTP_AN_DATA.NAN_MIKOMI as NAN_MIKOMI, ");
            sql.addSql("   CASE NTP_AN_DATA.NAN_MIKOMI");
            sql.addSql("   WHEN 0 THEN '10%'");
            sql.addSql("   WHEN 1 THEN '30%'");
            sql.addSql("   WHEN 2 THEN '50%'");
            sql.addSql("   WHEN 3 THEN '70%'");
            sql.addSql("   WHEN 4 THEN '100%'");
            sql.addSql("   ELSE '0%' END  NAN_MIKOMI_VAL,");
            sql.addSql("   sum(NTP_AN_DATA.NAN_KIN_MITUMORI) NAN_KIN_MITUMORI, ");
            sql.addSql("   sum(NTP_AN_DATA.NAN_KIN_JUTYU) as NAN_KIN_JUTYU ");
            sql.addSql(" from  ");
            sql.addSql(" (select  ");
            sql.addSql("     NTP_ANKEN.NAN_SID as NAN_SID, ");
            sql.addSql("     NTP_ANKEN.NAN_NAME as NAN_NAME, ");
            sql.addSql("     NTP_ANKEN.NCN_SID as NCN_SID, ");
            sql.addSql("     NTP_ANKEN_MITUMORI.NAN_KIN_MITUMORI as NAN_KIN_MITUMORI, ");
            sql.addSql("     NTP_ANKEN_JUTYU.NAN_KIN_JUTYU as NAN_KIN_JUTYU, ");
            sql.addSql("     NTP_ANKEN.NAN_SYODAN as NAN_SYODAN, ");
            sql.addSql("     NTP_ANKEN.NAN_STATE as NAN_STATE, ");
            sql.addSql("     NTP_ANKEN.NAN_MIKOMI as NAN_MIKOMI ");
            sql.addSql("  from ");
            sql.addSql("     NTP_ANKEN  ");

            //見積もり金額
            __getMitumoriSql(frdate, todate, sql, null, "NTP_ANKEN", "NTP_ANKEN_MITUMORI");
            //受注金額
            __getJutyuSql(frdate, todate, sql, null, "NTP_ANKEN", "NTP_ANKEN_JUTYU");

            sql = __getWhereDateForAnken(frdate, todate, sql, "NTP_ANKEN", false);

            if (state >= 0) {
                //状態
                sql.addSql("  and ");
                sql.addSql("    NTP_ANKEN.NAN_STATE =" + state);
            }

            //商談状態
            if (ankenState > -1) {
                sql.addSql(" and ");
                sql.addSql("    NTP_ANKEN.NAN_SYODAN =" + ankenState);
            }

            //グループ指定時の案件
            sql = __getWhereFromAnkenSids(ankenSids, sql, "NTP_ANKEN", true);

            sql.addSql("  ) NTP_AN_DATA ");
            sql.addSql("  group by ");
            sql.addSql("    NTP_AN_DATA.NAN_MIKOMI, ");
            sql.addSql("    NAN_MIKOMI_VAL ");

            sql.addSql(" union ");

            sql.addSql(" select   ");
            sql.addSql("   NTP_AN_DATA.NAN_MIKOMI as NAN_MIKOMI, ");
            sql.addSql("   CASE NTP_AN_DATA.NAN_MIKOMI");
            sql.addSql("   WHEN 0 THEN '10%'");
            sql.addSql("   WHEN 1 THEN '30%'");
            sql.addSql("   WHEN 2 THEN '50%'");
            sql.addSql("   WHEN 3 THEN '70%'");
            sql.addSql("   WHEN 4 THEN '100%'");
            sql.addSql("   ELSE '0%' END  as NAN_MIKOMI_VAL,");
            sql.addSql("   sum(NTP_AN_DATA.NAN_KIN_MITUMORI) as NAN_KIN_MITUMORI,  ");
            sql.addSql("   sum(NTP_AN_DATA.NAN_KIN_JUTYU) as NAN_KIN_JUTYU  ");
            sql.addSql(" from  ");
            sql.addSql(" (select  ");
            sql.addSql("     NTP_AN_HIS.NAN_SID as NAN_SID, ");
            sql.addSql("     NTP_AN_HIS.NAN_NAME as NAN_NAME, ");
            sql.addSql("     NTP_AN_HIS.NCN_SID as NCN_SID, ");
            sql.addSql("     NTP_AN_HIS.NAN_KIN_MITUMORI as NAN_KIN_MITUMORI, ");
            sql.addSql("     NTP_AN_HIS.NAN_KIN_JUTYU as NAN_KIN_JUTYU, ");
            sql.addSql("     NTP_AN_HIS.NAN_SYODAN as NAN_SYODAN, ");
            sql.addSql("     NTP_AN_HIS.NAN_STATE as NAN_STATE, ");
            sql.addSql("     NTP_AN_HIS.NAN_MIKOMI as NAN_MIKOMI ");
            sql.addSql("  from ");
            sql.addSql("  ( ");
            sql = __getAnkenHistoryTable(frdate, todate, sql, null);
            sql.addSql("    )  NTP_AN_HIS");

            if (state >= 0) {
                //状態
                sql.addSql("  where ");
                sql.addSql("    NTP_AN_HIS.NAN_STATE =" + state);
                whereFlg = true;
            }

            //商談状態
            if (ankenState > -1) {
                if (whereFlg) {
                    sql.addSql(" and ");
                } else {
                    sql.addSql(" where ");
                    whereFlg = true;
                }

                sql.addSql("    NTP_AN_HIS.NAN_SYODAN =" + ankenState);
            }

            //グループ指定時の案件
            sql = __getWhereFromAnkenSids(ankenSids, sql, "NTP_AN_HIS", whereFlg);

            sql.addSql("  ) NTP_AN_DATA ");
            sql.addSql("  group by ");
            sql.addSql("    NTP_AN_DATA.NAN_MIKOMI, ");
            sql.addSql("    NAN_MIKOMI_VAL ");

            sql.addSql("  order by NAN_KIN_JUTYU DESC");

            pstmt = con.prepareStatement(sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            Ntp220AnkenModel cmpSalesMdl = null;

            limit = pageNum * limit;

            for (int i = 0; rs.next() && i < limit; i++) {
                cmpSalesMdl = new Ntp220AnkenModel();
                cmpSalesMdl.setCntSid1(rs.getInt("NAN_MIKOMI"));
                cmpSalesMdl.setCntName1(rs.getString("NAN_MIKOMI_VAL"));
                cmpSalesMdl.setNanKinMitumori(rs.getInt("NAN_KIN_MITUMORI"));
                cmpSalesMdl.setNanKinJutyu(rs.getInt("NAN_KIN_JUTYU"));
                result.add(cmpSalesMdl);
            }

            for (int n = result.size(); n > 0; n--) {
                ret.add(result.get(n - 1));
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
     * 案件に登録された商品を取得する
     * @param ankenSids 選択グループ案件
     * @param state 状態 0:進行中 1:完了
     * @param ankenState 案件状態  1:商談中 2:受注 3:失注
     * @param pageNum 表示ページ
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @param limit 表示件数
     * @return ArrayList<Ntp220CompanySalesModel>
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Object>
                        getAllShohinSales(List<Integer> ankenSids,
                                                       int state,
                                                       int ankenState,
                                                       int pageNum,
                                                       int limit,
                                                       UDate frdate,
                                                       UDate todate) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Object> result = new ArrayList<Object>();
        ArrayList<Object> ret = new ArrayList<Object>();
        boolean whereFlg = false;

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select  ");
            sql.addSql("   NTP_AN_DATA.ACO_SID as ACO_SID, ");
            sql.addSql("   NTP_AN_DATA.ACO_NAME as ACO_NAME, ");
            sql.addSql("   NTP_AN_DATA.ABA_SID as ABA_SID, ");
            sql.addSql("   ADR_COMPANY_BASE.ABA_NAME as ABA_NAME, ");
            sql.addSql("   sum(NTP_AN_DATA.NAN_KIN_MITUMORI) as NAN_KIN_MITUMORI, ");
            sql.addSql("   sum(NTP_AN_DATA.NAN_KIN_JUTYU) as NAN_KIN_JUTYU ");
            sql.addSql(" from  ");
            sql.addSql(" (select  ");
            sql.addSql("     NTP_ANKEN.NAN_SID as NAN_SID, ");
            sql.addSql("     NTP_ANKEN.NAN_NAME as NAN_NAME, ");
            sql.addSql("     NTP_ANKEN.ACO_SID as ACO_SID, ");
            sql.addSql("     NTP_ANKEN.ABA_SID as ABA_SID, ");
            sql.addSql("     NTP_ANKEN.NAN_KIN_MITUMORI as NAN_KIN_MITUMORI, ");
            sql.addSql("     NTP_ANKEN.NAN_KIN_JUTYU as NAN_KIN_JUTYU, ");
            sql.addSql("     NTP_ANKEN.NAN_SYODAN as NAN_SYODAN, ");
            sql.addSql("     NTP_ANKEN.NAN_STATE as NAN_STATE, ");
            sql.addSql("     ADR_COMPANY.ACO_NAME as ACO_NAME ");
            sql.addSql("  from ");
            sql.addSql("     NTP_ANKEN  ");
            sql.addSql("  LEFT JOIN ");
            sql.addSql("     ADR_COMPANY ");
            sql.addSql("  ON ");
            sql.addSql("     NTP_ANKEN.ACO_SID=ADR_COMPANY.ACO_SID ");

            sql = __getWhereDateForAnken(frdate, todate, sql, "NTP_ANKEN", false);

            if (state >= 0) {
                //状態
                sql.addSql("  and ");
                sql.addSql("    NTP_ANKEN.NAN_STATE =" + state);
            }

            //商談状態
            if (ankenState > -1) {
                sql.addSql(" and ");
                sql.addSql("    NTP_ANKEN.NAN_SYODAN =" + ankenState);
            }

            //グループ指定時の案件
            sql = __getWhereFromAnkenSids(ankenSids, sql, "NTP_ANKEN", true);

            sql.addSql("  ) NTP_AN_DATA ");
            sql.addSql("  LEFT JOIN ");
            sql.addSql("    ADR_COMPANY_BASE ");
            sql.addSql("  ON ");
            sql.addSql("    NTP_AN_DATA.ABA_SID=ADR_COMPANY_BASE.ABA_SID ");
            sql.addSql("  where ");
            sql.addSql("    NTP_AN_DATA.ACO_SID not in (0,-1) ");
            sql.addSql("  group by ");
            sql.addSql("    NTP_AN_DATA.ACO_SID, ");
            sql.addSql("    NTP_AN_DATA.ACO_NAME, ");
            sql.addSql("    NTP_AN_DATA.ABA_SID, ");
            sql.addSql("    ADR_COMPANY_BASE.ABA_NAME ");

            sql.addSql(" union ");

            sql.addSql(" select   ");
            sql.addSql("   NTP_AN_DATA.ACO_SID as ACO_SID,  ");
            sql.addSql("   NTP_AN_DATA.ACO_NAME as ACO_NAME,  ");
            sql.addSql("   NTP_AN_DATA.ABA_SID as ABA_SID,  ");
            sql.addSql("   ADR_COMPANY_BASE.ABA_NAME as ABA_NAME,  ");
            sql.addSql("   sum(NTP_AN_DATA.NAN_KIN_MITUMORI) as NAN_KIN_MITUMORI,  ");
            sql.addSql("   sum(NTP_AN_DATA.NAN_KIN_JUTYU) as NAN_KIN_JUTYU  ");
            sql.addSql(" from  ");
            sql.addSql(" (select  ");
            sql.addSql("     NTP_AN_HIS.NAN_SID as NAN_SID, ");
            sql.addSql("     NTP_AN_HIS.NAN_NAME as NAN_NAME, ");
            sql.addSql("     NTP_AN_HIS.ACO_SID as ACO_SID, ");
            sql.addSql("     NTP_AN_HIS.ABA_SID as ABA_SID, ");
            sql.addSql("     NTP_AN_HIS.NAN_KIN_MITUMORI as NAN_KIN_MITUMORI, ");
            sql.addSql("     NTP_AN_HIS.NAN_KIN_JUTYU as NAN_KIN_JUTYU, ");
            sql.addSql("     NTP_AN_HIS.NAN_SYODAN as NAN_SYODAN, ");
            sql.addSql("     NTP_AN_HIS.NAN_STATE as NAN_STATE, ");
            sql.addSql("     ADR_COMPANY.ACO_NAME as ACO_NAME ");
            sql.addSql("  from ");
            sql.addSql("  ( ");
            sql = __getAnkenHistoryTable(frdate, todate, sql, null);
            sql.addSql("    )  NTP_AN_HIS");
            sql.addSql("  LEFT JOIN ");
            sql.addSql("     ADR_COMPANY ");
            sql.addSql("  ON ");
            sql.addSql("     NTP_AN_HIS.ACO_SID=ADR_COMPANY.ACO_SID ");

            if (state >= 0) {
                //状態
                sql.addSql("  where ");
                sql.addSql("    NTP_AN_HIS.NAN_STATE =" + state);
                whereFlg = true;
            }

            //商談状態
            if (ankenState > -1) {
                if (whereFlg) {
                    sql.addSql(" and ");
                } else {
                    sql.addSql(" where ");
                    whereFlg = true;
                }

                sql.addSql("    NTP_AN_HIS.NAN_SYODAN =" + ankenState);
            }

            //グループ指定時の案件
            sql = __getWhereFromAnkenSids(ankenSids, sql, "NTP_AN_HIS", whereFlg);

            sql.addSql("  ) NTP_AN_DATA ");
            sql.addSql("  LEFT JOIN ");
            sql.addSql("    ADR_COMPANY_BASE ");
            sql.addSql("  ON ");
            sql.addSql("    NTP_AN_DATA.ABA_SID=ADR_COMPANY_BASE.ABA_SID ");
            sql.addSql("  where ");
            sql.addSql("    NTP_AN_DATA.ACO_SID not in (0,-1) ");
            sql.addSql("  group by ");
            sql.addSql("    NTP_AN_DATA.ACO_SID, ");
            sql.addSql("    NTP_AN_DATA.ACO_NAME, ");
            sql.addSql("    NTP_AN_DATA.ABA_SID, ");
            sql.addSql("    ADR_COMPANY_BASE.ABA_NAME ");

            sql.addSql("  order by NAN_KIN_JUTYU DESC");


            pstmt = con.prepareStatement(sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            Ntp220AnkenModel cmpSalesMdl = null;

            limit = pageNum * limit;

            for (int i = 0; rs.next() && i < limit; i++) {
                cmpSalesMdl = new Ntp220AnkenModel();
                cmpSalesMdl.setCntSid1(rs.getInt("ACO_SID"));
                cmpSalesMdl.setCntName1(rs.getString("ACO_NAME"));
                cmpSalesMdl.setCntSid2(rs.getInt("ABA_SID"));
                cmpSalesMdl.setCntName2(rs.getString("ABA_NAME"));
                cmpSalesMdl.setNanKinMitumori(rs.getInt("NAN_KIN_MITUMORI"));
                cmpSalesMdl.setNanKinJutyu(rs.getInt("NAN_KIN_JUTYU"));
                result.add(cmpSalesMdl);
            }

            for (int n = result.size(); n > 0; n--) {
                ret.add(result.get(n - 1));
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
     * 指定した案件の最高金額と最低金額を取得する
     * @param state 状態 0:進行中 1:完了
     * @param ankenState 案件状態  1:商談中 2:受注 3:失注
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @return ArrayList<Ntp220CompanySalesModel>
     * @throws SQLException SQL実行例外
     */
    public Ntp220ComparativeModel
                        getAnkenMaxMinMoney(int state,
                                            int ankenState,
                                            UDate frdate,
                                            UDate todate) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Ntp220ComparativeModel ret = null;

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select ");
            sql.addSql("   MAX(NAN_KIN_MITUMORI) as MAX_MITUMORI, ");
            sql.addSql("   MAX(NAN_KIN_JUTYU) as MAX_JUTYU, ");
            sql.addSql("   MIN(NAN_KIN_MITUMORI) as MIN_MITUMORI, ");
            sql.addSql("   MIN(NAN_KIN_JUTYU) as MIN_JUTYU ");
            sql.addSql(" from ");
            sql.addSql("   NTP_ANKEN ");
            sql.addSql(" where ");

            if (state >= 0) {
                //状態
                sql.addSql("    NTP_ANKEN.NAN_STATE =" + state);
                sql.addSql(" and ");
            }

            sql.addSql("   NTP_ANKEN.NAN_SYODAN =" + ankenState);

            sql = __getWhereDateForAnken(frdate, todate, sql, "NTP_ANKEN", true);

            pstmt = con.prepareStatement(sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = new Ntp220ComparativeModel();
                ret.setMaxMitumoriPrice(rs.getInt("MAX_MITUMORI"));
                ret.setMinMitumoriPrice(rs.getInt("MIN_MITUMORI"));
                ret.setMaxJutyuPrice(rs.getInt("MAX_JUTYU"));
                ret.setMinJutyuPrice(rs.getInt("MIN_JUTYU"));
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
     * 状態の件数を取得する
     * @param state 状態 0:進行中 1:完了
     * @param searchMdl 検索モデル
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @return ArrayList<Ntp220CompanySalesModel>
     * @throws SQLException SQL実行例外
     */
    public Ntp220StateModel getAnkenState(int state,
                                          Ntp220AnkenSearchModel searchMdl,
                                          UDate frdate,
                                          UDate todate) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Ntp220StateModel ret = null;

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select  ");
            sql.addSql("   SYODAN_CNT.CNT_SYODAN as CNT_SYODAN, ");
            sql.addSql("   JUTYU_CNT.CNT_JUTYU as CNT_JUTYU, ");
            sql.addSql("   SITYU_CNT.CNT_SITYU as CNT_SITYU ");
            sql.addSql(" from ");
            sql.addSql(" (");
            sql.addSql(" select sum(CNT_RESULT.CNT_SYODAN) as CNT_SYODAN");
            sql.addSql("   from");
            sql.addSql(" (");
            sql.addSql("   select ");
            sql.addSql("     count(distinct NTP_ANKEN.NAN_SID) as CNT_SYODAN ");
            sql.addSql("   from ");
            sql.addSql("     NTP_ANKEN ");

            //カテゴリ指定時
            if (searchMdl != null) {
                __getCategorySql(sql, "NTP_ANKEN", searchMdl.getShohinCatSid());
            }

            sql.addSql("   where ");
            sql.addSql("     NTP_ANKEN.NAN_SYODAN=0 ");
            if (state >= 0) {
                sql.addSql("   and ");
                sql.addSql("     NTP_ANKEN.NAN_STATE=" + state);
            }
            sql = __getWhereFromSearchMdl(searchMdl, sql, "NTP_ANKEN", true);
            sql = __getWhereDateForAnken(frdate, todate, sql, "NTP_ANKEN", true);

            sql.addSql("   union ");

            sql.addSql("   select ");
            sql.addSql("     count(distinct NTP_AN_HIS.NAN_SID) as CNT_SYODAN ");
            sql.addSql("   from ");
            sql.addSql("     ( ");
            sql = __getAnkenHistoryTable(frdate, todate, sql, searchMdl);
            sql.addSql("     ) NTP_AN_HIS");
            sql.addSql("   where ");
            sql.addSql("     NTP_AN_HIS.NAN_SYODAN=0 ");
            if (state >= 0) {
                sql.addSql("   and ");
                sql.addSql("     NTP_AN_HIS.NAN_STATE=" + state);
            }
            sql = __getWhereFromSearchMdl(searchMdl, sql, "NTP_AN_HIS", true);
            sql = __getWhereDateForAnken(frdate, todate, sql, "NTP_AN_HIS", true);

            sql.addSql(" ) CNT_RESULT");
            sql.addSql(") SYODAN_CNT, ");

            sql.addSql(" (");
            sql.addSql(" select sum(CNT_RESULT.CNT_JUTYU) as CNT_JUTYU");
            sql.addSql("   from");
            sql.addSql("   (select ");
            sql.addSql("     count(distinct NTP_ANKEN.NAN_SID) as CNT_JUTYU ");
            sql.addSql("   from ");
            sql.addSql("     NTP_ANKEN ");

            //カテゴリ指定時
            if (searchMdl != null) {
                __getCategorySql(sql, "NTP_ANKEN", searchMdl.getShohinCatSid());
            }

            sql.addSql("   where ");
            sql.addSql("     NTP_ANKEN.NAN_SYODAN=1 ");
            if (state >= 0) {
                sql.addSql("   and ");
                sql.addSql("     NTP_ANKEN.NAN_STATE=" + state);
            }
            sql = __getWhereFromSearchMdl(searchMdl, sql, "NTP_ANKEN", true);
            sql = __getWhereDateForAnken(frdate, todate, sql, "NTP_ANKEN", true);


            sql.addSql("  union ");

            sql.addSql("   select ");
            sql.addSql("     count(distinct NTP_AN_HIS.NAN_SID) as CNT_JUTYU ");
            sql.addSql("   from ");
            sql.addSql("     ( ");
            sql = __getAnkenHistoryTable(frdate, todate, sql, searchMdl);
            sql.addSql("     ) NTP_AN_HIS");
            sql.addSql("   where ");
            sql.addSql("     NTP_AN_HIS.NAN_SYODAN=1 ");
            if (state >= 0) {
                sql.addSql("   and ");
                sql.addSql("     NTP_AN_HIS.NAN_STATE=" + state);
            }
            sql = __getWhereFromSearchMdl(searchMdl, sql, "NTP_AN_HIS", true);
            sql = __getWhereDateForAnken(frdate, todate, sql, "NTP_AN_HIS", true);

            sql.addSql(" ) CNT_RESULT");
            sql.addSql(") JUTYU_CNT, ");

            sql.addSql(" (");
            sql.addSql(" select sum(CNT_RESULT.CNT_SITYU) as CNT_SITYU");
            sql.addSql("   from");
            sql.addSql("   (select ");
            sql.addSql("     count(distinct NTP_ANKEN.NAN_SID) as CNT_SITYU ");
            sql.addSql("   from ");
            sql.addSql("     NTP_ANKEN ");

            //カテゴリ指定時
            if (searchMdl != null) {
                __getCategorySql(sql, "NTP_ANKEN", searchMdl.getShohinCatSid());
            }

            sql.addSql("   where ");
            sql.addSql("     NTP_ANKEN.NAN_SYODAN=2 ");
            if (state >= 0) {
                sql.addSql("   and ");
                sql.addSql("     NTP_ANKEN.NAN_STATE=" + state);
            }
            sql = __getWhereFromSearchMdl(searchMdl, sql, "NTP_ANKEN", true);
            sql = __getWhereDateForAnken(frdate, todate, sql, "NTP_ANKEN", true);

            sql.addSql("  union ");

            sql.addSql("   select ");
            sql.addSql("     count(distinct NTP_AN_HIS.NAN_SID) as CNT_SITYU ");
            sql.addSql("   from ");
            sql.addSql("     ( ");
            sql = __getAnkenHistoryTable(frdate, todate, sql, searchMdl);
            sql.addSql("     ) NTP_AN_HIS");
            sql.addSql("   where ");
            sql.addSql("     NTP_AN_HIS.NAN_SYODAN=2 ");
            if (state >= 0) {
                sql.addSql("   and ");
                sql.addSql("     NTP_AN_HIS.NAN_STATE=" + state);
            }
            sql = __getWhereFromSearchMdl(searchMdl, sql, "NTP_AN_HIS", true);
            sql = __getWhereDateForAnken(frdate, todate, sql, "NTP_AN_HIS", true);

            sql.addSql(" ) CNT_RESULT");
            sql.addSql(") SITYU_CNT ");

            pstmt = con.prepareStatement(sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = new Ntp220StateModel();
                ret.setJutyuCnt(rs.getInt("CNT_JUTYU"));
                ret.setSityuCnt(rs.getInt("CNT_SITYU"));
                ret.setSyodanCnt(rs.getInt("CNT_SYODAN"));
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
     * 状態の件数を取得する(プロセス)
     * @param state 状態 0:進行中 1:完了
     * @param ankenState 案件状態  1:商談中 2:受注 3:失注
     * @param searchMdl 検索モデル
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @param gyoushuSid 業種SID
     * @return ArrayList<Ntp220CompanySalesModel>
     * @throws SQLException SQL実行例外
     */
    public List<Ntp220StateModel> getProcessAnkenState(int state,
                                          int ankenState,
                                          Ntp220AnkenSearchModel searchMdl,
                                          UDate frdate,
                                          UDate todate,
                                          int gyoushuSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Ntp220StateModel mdl = null;
        List<Ntp220StateModel> ret = new ArrayList<Ntp220StateModel>();


        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select ");
            sql.addSql("   NTP_PROCESS.NGP_SID as NGP_SID, ");
            sql.addSql("   NTP_PROCESS.NGP_NAME as NGP_NAME, ");
            sql.addSql("   RESULT_CNT.CNT_STATE as CNT_STATE");
            sql.addSql(" from ");
            sql.addSql("   NTP_PROCESS ");
            sql.addSql(" RIGHT JOIN ");
            sql.addSql("   ( ");

            sql = __getAnkenContentStateSql(sql,
                                            "NGP_SID",
                                            state,
                                            ankenState,
                                            searchMdl,
                                            frdate,
                                            todate);

            sql.addSql("   ) RESULT_CNT ");
            sql.addSql(" ON ");
            sql.addSql("   NTP_PROCESS.NGP_SID = RESULT_CNT.NGP_SID ");
            if (gyoushuSid != -1) {
                sql.addSql(" where ");
                sql.addSql("   NTP_PROCESS.NGY_SID = " + gyoushuSid);
            }


            pstmt = con.prepareStatement(sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                mdl = new Ntp220StateModel();
                mdl.setContentSid(rs.getInt("NGP_SID"));
                mdl.setContentName(rs.getString("NGP_NAME"));
                mdl.setAnkenCnt(rs.getInt("CNT_STATE"));
                ret.add(mdl);
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
     * 状態の件数を取得する(顧客源泉)
     * @param state 状態 0:進行中 1:完了
     * @param ankenState 案件状態  1:商談中 2:受注 3:失注
     * @param searchMdl 検索モデル
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @return ArrayList<Ntp220CompanySalesModel>
     * @throws SQLException SQL実行例外
     */
    public List<Ntp220StateModel> getKokyakuAnkenState(int state,
                                          int ankenState,
                                          Ntp220AnkenSearchModel searchMdl,
                                          UDate frdate,
                                          UDate todate) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Ntp220StateModel mdl = null;
        List<Ntp220StateModel> ret = new ArrayList<Ntp220StateModel>();


        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select ");
            sql.addSql("   NTP_CONTACT.NCN_SID as NCN_SID, ");
            sql.addSql("   NTP_CONTACT.NCN_NAME as NCN_NAME, ");
            sql.addSql("   RESULT_CNT.CNT_STATE as CNT_STATE");
            sql.addSql(" from ");
            sql.addSql("   NTP_CONTACT ");
            sql.addSql(" RIGHT JOIN ");
            sql.addSql("   ( ");

            sql = __getAnkenContentStateSql(sql,
                                            "NCN_SID",
                                            state,
                                            ankenState,
                                            searchMdl,
                                            frdate,
                                            todate);

            sql.addSql("   ) RESULT_CNT ");
            sql.addSql(" ON ");
            sql.addSql("   NTP_CONTACT.NCN_SID = RESULT_CNT.NCN_SID ");

            pstmt = con.prepareStatement(sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                mdl = new Ntp220StateModel();
                mdl.setContentSid(rs.getInt("NCN_SID"));
                mdl.setContentName(rs.getString("NCN_NAME"));
                mdl.setAnkenCnt(rs.getInt("CNT_STATE"));
                ret.add(mdl);
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
     * 状態の件数を取得する(見込み度)
     * @param state 状態 0:進行中 1:完了
     * @param ankenState 案件状態  1:商談中 2:受注 3:失注
     * @param searchMdl 検索モデル
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @return ArrayList<Ntp220CompanySalesModel>
     * @throws SQLException SQL実行例外
     */
    public List<Ntp220StateModel> getMikomidoAnkenState(int state,
                                          int ankenState,
                                          Ntp220AnkenSearchModel searchMdl,
                                          UDate frdate,
                                          UDate todate) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Ntp220StateModel mdl = null;
        List<Ntp220StateModel> ret = new ArrayList<Ntp220StateModel>();


        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select ");
            sql.addSql("   RESULT_CNT.NAN_MIKOMI as NAN_MIKOMI, ");
            sql.addSql("   CASE RESULT_CNT.NAN_MIKOMI");
            sql.addSql("   WHEN 0 THEN '10%'");
            sql.addSql("   WHEN 1 THEN '30%'");
            sql.addSql("   WHEN 2 THEN '50%'");
            sql.addSql("   WHEN 3 THEN '70%'");
            sql.addSql("   WHEN 4 THEN '100%'");
            sql.addSql("   ELSE '0%' END  as NAN_MIKOMI_VAL,");
            sql.addSql("   RESULT_CNT.CNT_STATE as CNT_STATE");
            sql.addSql(" from ");
            sql.addSql("   ( ");

            sql = __getAnkenContentStateSql(sql,
                                            "NAN_MIKOMI",
                                            state,
                                            ankenState,
                                            searchMdl,
                                            frdate,
                                            todate);

            sql.addSql("   ) RESULT_CNT ");

            pstmt = con.prepareStatement(sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                mdl = new Ntp220StateModel();
                mdl.setContentSid(rs.getInt("NAN_MIKOMI"));
                mdl.setContentName(rs.getString("NAN_MIKOMI_VAL"));
                mdl.setAnkenCnt(rs.getInt("CNT_STATE"));
                ret.add(mdl);
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
     * 状態の件数を取得する(項目・状態指定)
     * @param sql sql
     * @param content 項目
     * @param state 状態 0:進行中 1:完了
     * @param ankenState 案件状態  1:商談中 2:受注 3:失注
     * @param searchMdl 検索モデル
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @return ArrayList<Ntp220CompanySalesModel>
     * @throws SQLException SQL実行例外
     */
    private SqlBuffer __getAnkenContentStateSql(
                                          SqlBuffer sql,
                                          String content,
                                          int state,
                                          int ankenState,
                                          Ntp220AnkenSearchModel searchMdl,
                                          UDate frdate,
                                          UDate todate) throws SQLException {

        boolean whereFlg = false;

        sql.addSql(" select  ");
        sql.addSql("   " + content + " as " + content + ",  ");
        sql.addSql("   sum(CNT_STATE_TABLE.CNT_STATE) as CNT_STATE ");
        sql.addSql(" from ");
        sql.addSql("   ( ");
        sql.addSql("    select  ");
        sql.addSql("      count(nan_sid) as CNT_STATE, ");
        sql.addSql("      NTP_ANKEN." + content + " " + content);
        sql.addSql("    from  ");
        sql.addSql("      NTP_ANKEN  ");

        if (ankenState >= 0) {
            sql.addSql("    where  ");
            sql.addSql("      NTP_ANKEN.NAN_SYODAN=" + ankenState);
            whereFlg = true;
        }

        if (state >= 0) {
            if (whereFlg) {
                sql.addSql("   and ");
            } else {
                sql.addSql("   where ");
                whereFlg = true;
            }
            sql.addSql("     NTP_ANKEN.NAN_STATE=" + state);
        }
        sql = __getWhereDateForAnken(frdate, todate, sql, "NTP_ANKEN", whereFlg);
        sql = __getWhereFromSearchMdl(searchMdl, sql, "NTP_ANKEN", true);

        sql.addSql("    group by NTP_ANKEN." + content);

        whereFlg = false;
        sql.addSql("    union  ");

        sql.addSql("    select  ");
        sql.addSql("   " + content + " as " + content + ",  ");
        sql.addSql("      count(nan_sid) as CNT_STATE  ");
        sql.addSql("    from  ");
        sql.addSql("      (  ");
        sql = __getAnkenHistoryTable(frdate, todate, sql, searchMdl);
        sql.addSql("     ) NTP_AN_HIS ");

        if (ankenState >= 0) {
            sql.addSql("   where  ");
            sql.addSql("     NTP_AN_HIS.NAN_SYODAN=" + ankenState);
            whereFlg = true;
        }

        if (state >= 0) {
            if (whereFlg) {
                sql.addSql("   and ");
            } else {
                sql.addSql("   where ");
                whereFlg = true;
            }
            sql.addSql("     NTP_AN_HIS.NAN_STATE=" + state);
        }

        sql = __getWhereDateForAnken(frdate, todate, sql, "NTP_AN_HIS", whereFlg);
        sql = __getWhereFromSearchMdl(searchMdl, sql, "NTP_AN_HIS", true);

        sql.addSql("   group by NTP_AN_HIS." + content);
        sql.addSql(" ) CNT_STATE_TABLE group by " + content);

        return sql;
    }

    /**
     * 案件情報を取得する
     * @param state 状態 0:進行中 1:完了
     * @param ankenState 案件状態  1:商談中 2:受注 3:失注
     * @param searchMdl 検索モデル
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @return ArrayList<Ntp220CompanySalesModel>
     * @throws SQLException SQL実行例外
     */
    public Ntp220TotalModel getPeriodAnkenData(
                                     int state,
                                     int ankenState,
                                     Ntp220AnkenSearchModel searchMdl,
                                     UDate frdate,
                                     UDate todate) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Ntp220TotalModel ret = new Ntp220TotalModel();
        boolean whereFlg = false;

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select ");
            sql.addSql("   sum(ANKEN_TOTAL.NAN_KIN_JUTYU) as JUTYU_TOTAL, ");
            sql.addSql("   sum(ANKEN_TOTAL.NAN_KIN_MITUMORI) as MITUMORI_TOTAL");
            sql.addSql(" from ( ");

            sql.addSql(" select ");
            sql.addSql("     distinct ");
            sql.addSql("     NTP_AN_DATA.NAN_SID          as NAN_SID,");
            sql.addSql("     NTP_AN_DATA.NAN_CODE         as NAN_CODE,");
            sql.addSql("     NTP_AN_DATA.NAN_NAME         as NAN_NAME,");
            sql.addSql("     NTP_AN_DATA.NAN_DETIAL       as NAN_DETIAL,");
            sql.addSql("     NTP_AN_DATA.ACO_SID          as ACO_SID,");
            sql.addSql("     NTP_AN_DATA.ACO_NAME         as ACO_NAME,");
            sql.addSql("     NTP_AN_DATA.ABA_SID          as ABA_SID,");
            sql.addSql("     ADR_COMPANY_BASE.ABA_NAME    as ABA_NAME,");
            sql.addSql("     NTP_AN_DATA.NGP_SID          as NGP_SID,");
            sql.addSql("     NTP_AN_DATA.NAN_MIKOMI       as NAN_MIKOMI,");
            sql.addSql("     NTP_AN_DATA.NAN_KIN_MITUMORI as NAN_KIN_MITUMORI,");
            sql.addSql("     NTP_AN_DATA.NAN_KIN_JUTYU    as NAN_KIN_JUTYU,");
            sql.addSql("     NTP_AN_DATA.NAN_SYODAN       as NAN_SYODAN,");
            sql.addSql("     NTP_AN_DATA.NAN_STATE        as NAN_STATE,");
            sql.addSql("     NTP_AN_DATA.NCN_SID          as NCN_SID,");
            sql.addSql("     ANKEN_PROCESS.NGY_SID        as NGY_SID,");
            sql.addSql("     NTP_GYOMU.NGY_NAME           as NGY_NAME,");
            sql.addSql("     NTP_PROCESS.NGP_NAME         as NGP_NAME,");
            sql.addSql("     NTP_CONTACT.NCN_NAME         as NCN_NAME,");
            sql.addSql("     CASE NTP_AN_DATA.NAN_MIKOMI");
            sql.addSql("     WHEN 0 THEN '10%'");
            sql.addSql("     WHEN 1 THEN '30%'");
            sql.addSql("     WHEN 2 THEN '50%'");
            sql.addSql("     WHEN 3 THEN '70%'");
            sql.addSql("     WHEN 4 THEN '100%'");
            sql.addSql("     ELSE '0%' END  as NAN_MIKOMI_VAL,");
            sql.addSql("     -1          as NAH_SID");
            sql.addSql(" from");
            sql.addSql("   (");
            sql.addSql("     select ");
            sql.addSql("         distinct");
            sql.addSql("         NTP_ANKEN.NAN_SID,");
            sql.addSql("         NTP_ANKEN.NAN_CODE,");
            sql.addSql("         NTP_ANKEN.NAN_NAME,");
            sql.addSql("         NTP_ANKEN.NAN_DETIAL,");
            sql.addSql("         NTP_ANKEN.NAN_DATE,");
            sql.addSql("         NTP_ANKEN.ACO_SID,");
            sql.addSql("         ADR_COMPANY.ACO_NAME,");
            sql.addSql("         NTP_ANKEN.ABA_SID,");
            sql.addSql("         NTP_ANKEN.NGP_SID,");
            sql.addSql("         NTP_ANKEN.NAN_MIKOMI,");
            sql.addSql("         NTP_ANKEN_MITUMORI.NAN_KIN_MITUMORI,");
            sql.addSql("         NTP_ANKEN_JUTYU.NAN_KIN_JUTYU,");
            sql.addSql("         NTP_ANKEN.NAN_SYODAN,");
            sql.addSql("         NTP_ANKEN.NAN_STATE,");
            sql.addSql("         NTP_ANKEN.NCN_SID,");
            sql.addSql("         NTP_ANKEN.NAN_EDATE");
            sql.addSql("     from ");
            sql.addSql("         NTP_ANKEN");

            //カテゴリ指定時
            if (searchMdl != null) {
                __getCategorySql(sql, "NTP_ANKEN", searchMdl.getShohinCatSid());
            }

            sql.addSql("     LEFT JOIN");
            sql.addSql("         ADR_COMPANY");
            sql.addSql("     ON");
            sql.addSql("         ADR_COMPANY.ACO_SID=NTP_ANKEN.ACO_SID");

            //見積もり金額SQL
            __getMitumoriSql(
                    frdate, todate, sql, searchMdl, "NTP_ANKEN", "NTP_ANKEN_MITUMORI");
            //受注金額SQL
            __getJutyuSql(
                    frdate, todate, sql, searchMdl, "NTP_ANKEN", "NTP_ANKEN_JUTYU");

            sql.addSql("   ) NTP_AN_DATA");

            sql.addSql(" LEFT JOIN");
            sql.addSql("   ADR_COMPANY_BASE");
            sql.addSql(" ON");
            sql.addSql("   ADR_COMPANY_BASE.ABA_SID=NTP_AN_DATA.ABA_SID");
            sql.addSql(" LEFT JOIN");
            sql.addSql("  (");
            sql.addSql("    select");
            sql.addSql("      NTP_PROCESS.NGP_SID,");
            sql.addSql("      NTP_PROCESS.NGY_SID");
            sql.addSql("    from");
            sql.addSql("      NTP_PROCESS");
            sql.addSql("   ) ANKEN_PROCESS");
            sql.addSql(" ON ");
            sql.addSql("   NTP_AN_DATA.NGP_SID = ANKEN_PROCESS.NGP_SID");
            sql.addSql(" LEFT JOIN");
            sql.addSql("   NTP_GYOMU");
            sql.addSql(" ON");
            sql.addSql("   ANKEN_PROCESS.NGY_SID = NTP_GYOMU.NGY_SID");
            sql.addSql(" LEFT JOIN");
            sql.addSql("   NTP_PROCESS");
            sql.addSql(" ON");
            sql.addSql("   ANKEN_PROCESS.NGP_SID = NTP_PROCESS.NGP_SID");
            sql.addSql(" LEFT JOIN");
            sql.addSql("   NTP_CONTACT");
            sql.addSql(" ON");
            sql.addSql("   NTP_AN_DATA.NCN_SID = NTP_CONTACT.NCN_SID");

            if (ankenState >= 0) {
                sql.addSql(" where ");
                sql.addSql("   NTP_AN_DATA.NAN_SYODAN = " + ankenState);
                whereFlg = true;
            }

            if (state > -1) {
                if (!whereFlg) {
                    sql.addSql(" where ");
                    whereFlg = true;
                } else {
                    sql.addSql(" and");
                }
                sql.addSql("   NTP_AN_DATA.NAN_STATE = " + state);
            }

            sql = __getWhereDateForAnken(frdate, todate, sql, "NTP_AN_DATA", whereFlg);

            sql = __getWhereFromSearchMdl(searchMdl, sql, "NTP_AN_DATA", true);


            sql.addSql(" union ");

            sql.addSql(" select ");
            sql.addSql("     NTP_AN_DATA.NAN_SID          as NAN_SID,");
            sql.addSql("     NTP_AN_DATA.NAN_CODE         as NAN_CODE,");
            sql.addSql("     NTP_AN_DATA.NAN_NAME         as NAN_NAME,");
            sql.addSql("     NTP_AN_DATA.NAN_DETIAL       as NAN_DETIAL,");
            sql.addSql("     NTP_AN_DATA.ACO_SID          as ACO_SID,");
            sql.addSql("     NTP_AN_DATA.ACO_NAME         as ACO_NAME,");
            sql.addSql("     NTP_AN_DATA.ABA_SID          as ABA_SID,");
            sql.addSql("     ADR_COMPANY_BASE.ABA_NAME    as ABA_NAME,");
            sql.addSql("     NTP_AN_DATA.NGP_SID          as NGP_SID,");
            sql.addSql("     NTP_AN_DATA.NAN_MIKOMI       as NAN_MIKOMI,");
            sql.addSql("     NTP_AN_DATA.NAN_KIN_MITUMORI as NAN_KIN_MITUMORI,");
            sql.addSql("     NTP_AN_DATA.NAN_KIN_JUTYU    as NAN_KIN_JUTYU,");
            sql.addSql("     NTP_AN_DATA.NAN_SYODAN       as NAN_SYODAN,");
            sql.addSql("     NTP_AN_DATA.NAN_STATE        as NAN_STATE,");
            sql.addSql("     NTP_AN_DATA.NCN_SID          as NCN_SID,");
            sql.addSql("     ANKEN_PROCESS.NGY_SID        as NGY_SID,");
            sql.addSql("     NTP_GYOMU.NGY_NAME           as NGY_NAME,");
            sql.addSql("     NTP_PROCESS.NGP_NAME         as NGP_NAME,");
            sql.addSql("     NTP_CONTACT.NCN_NAME         as NCN_NAME,");
            sql.addSql("     CASE NTP_AN_DATA.NAN_MIKOMI");
            sql.addSql("     WHEN 0 THEN '10%'");
            sql.addSql("     WHEN 1 THEN '30%'");
            sql.addSql("     WHEN 2 THEN '50%'");
            sql.addSql("     WHEN 3 THEN '70%'");
            sql.addSql("     WHEN 4 THEN '100%'");
            sql.addSql("     ELSE '0%' END  as NAN_MIKOMI_VAL,");
            sql.addSql("     NTP_AN_DATA.NAH_SID          as NAH_SID");
            sql.addSql(" from");
            sql.addSql("   (");
            sql.addSql("     select ");
            sql.addSql("         distinct ");
            sql.addSql("         NTP_AN_HIS.NAH_SID,");
            sql.addSql("         NTP_AN_HIS.NAN_SID,");
            sql.addSql("         NTP_AN_HIS.NAN_CODE,");
            sql.addSql("         NTP_AN_HIS.NAN_NAME,");
            sql.addSql("         NTP_AN_HIS.NAN_DETIAL,");
            sql.addSql("         NTP_AN_HIS.NAN_DATE,");
            sql.addSql("         NTP_AN_HIS.ACO_SID,");
            sql.addSql("         ADR_COMPANY.ACO_NAME,");
            sql.addSql("         NTP_AN_HIS.ABA_SID,");
            sql.addSql("         NTP_AN_HIS.NGP_SID,");
            sql.addSql("         NTP_AN_HIS.NAN_MIKOMI,");
            sql.addSql("         NTP_AN_HIS.NAN_KIN_MITUMORI,");
            sql.addSql("         NTP_AN_HIS.NAN_KIN_JUTYU,");
            sql.addSql("         NTP_AN_HIS.NAN_SYODAN,");
            sql.addSql("         NTP_AN_HIS.NAN_STATE,");
            sql.addSql("         NTP_AN_HIS.NCN_SID,");
            sql.addSql("         NTP_AN_HIS.NAN_EDATE");
            sql.addSql("     from ");

            sql.addSql("     ( ");
            sql = __getAnkenHistoryTable(frdate, todate, sql, searchMdl);
            sql.addSql("     ) NTP_AN_HIS");

            sql.addSql("     LEFT JOIN");
            sql.addSql("         ADR_COMPANY");
            sql.addSql("     ON");
            sql.addSql("         ADR_COMPANY.ACO_SID=NTP_AN_HIS.ACO_SID");
            sql.addSql("   ) NTP_AN_DATA");
            sql.addSql(" LEFT JOIN");
            sql.addSql("   ADR_COMPANY_BASE");
            sql.addSql(" ON");
            sql.addSql("   ADR_COMPANY_BASE.ABA_SID=NTP_AN_DATA.ABA_SID");
            sql.addSql(" LEFT JOIN");
            sql.addSql("  (");
            sql.addSql("    select");
            sql.addSql("      NTP_PROCESS.NGP_SID,");
            sql.addSql("      NTP_PROCESS.NGY_SID");
            sql.addSql("    from");
            sql.addSql("      NTP_PROCESS");
            sql.addSql("   ) ANKEN_PROCESS");
            sql.addSql(" ON ");
            sql.addSql("   NTP_AN_DATA.NGP_SID = ANKEN_PROCESS.NGP_SID");
            sql.addSql(" LEFT JOIN");
            sql.addSql("   NTP_GYOMU");
            sql.addSql(" ON");
            sql.addSql("   ANKEN_PROCESS.NGY_SID = NTP_GYOMU.NGY_SID");
            sql.addSql(" LEFT JOIN");
            sql.addSql("   NTP_PROCESS");
            sql.addSql(" ON");
            sql.addSql("   ANKEN_PROCESS.NGP_SID = NTP_PROCESS.NGP_SID");
            sql.addSql(" LEFT JOIN");
            sql.addSql("   NTP_CONTACT");
            sql.addSql(" ON");
            sql.addSql("   NTP_AN_DATA.NCN_SID = NTP_CONTACT.NCN_SID");

            whereFlg = false;

            if (ankenState >= 0) {
                sql.addSql(" where ");
                sql.addSql("   NTP_AN_DATA.NAN_SYODAN = " + ankenState);
                whereFlg = true;
            }

            if (state > -1) {
                if (!whereFlg) {
                    sql.addSql(" where ");
                    whereFlg = true;
                } else {
                    sql.addSql(" and");
                }
                sql.addSql("   NTP_AN_DATA.NAN_STATE = " + state);
            }

            sql = __getWhereFromSearchMdl(searchMdl, sql, "NTP_AN_DATA", whereFlg);

            sql.addSql("  order by NAN_KIN_JUTYU DESC");

            sql.addSql(" ) ANKEN_TOTAL ");
            pstmt = con.prepareStatement(sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret.setSumJutyuPrice(rs.getInt("JUTYU_TOTAL"));
                ret.setSumMitumoriPrice(rs.getInt("MITUMORI_TOTAL"));
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
     * 案件情報を取得する
     * @param limit リミット
     * @param offset オフセット
     * @param state 状態 0:進行中 1:完了
     * @param ankenState 案件状態  1:商談中 2:受注 3:失注
     * @param pageNum 表示ページ
     * @param searchMdl 検索モデル
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @return ArrayList<Ntp220CompanySalesModel>
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Ntp220AnkenModel> getAnkenData(
                                     int limit,
                                     int offset,
                                     int state,
                                     int ankenState,
                                     int pageNum,
                                     Ntp220AnkenSearchModel searchMdl,
                                     UDate frdate,
                                     UDate todate) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Ntp220AnkenModel> ret = new ArrayList<Ntp220AnkenModel>();
        Ntp220AnkenModel ankenMdl = null;
        boolean whereFlg = false;

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("     distinct ");
            sql.addSql("     NTP_AN_DATA.NAN_SID           as NAN_SID,");
            sql.addSql("     NTP_AN_DATA.NAN_CODE          as NAN_CODE,");
            sql.addSql("     NTP_AN_DATA.NAN_NAME          as NAN_NAME,");
            sql.addSql("     NTP_AN_DATA.NAN_DETIAL        as NAN_DETIAL,");
            sql.addSql("     NTP_AN_DATA.NAN_DATE          as NAN_DATE,");
            sql.addSql("     NTP_AN_DATA.ACO_SID           as ACO_SID,");
            sql.addSql("     NTP_AN_DATA.ACO_NAME          as ACO_NAME,");
            sql.addSql("     NTP_AN_DATA.ABA_SID           as ABA_SID,");
            sql.addSql("     ADR_COMPANY_BASE.ABA_NAME     as ABA_NAME,");
            sql.addSql("     NTP_AN_DATA.NGP_SID           as NGP_SID,");
            sql.addSql("     NTP_AN_DATA.NAN_MIKOMI        as NAN_MIKOMI,");
            sql.addSql("     NTP_AN_DATA.NAN_KIN_MITUMORI  as NAN_KIN_MITUMORI,");
            sql.addSql("     NTP_AN_DATA.NAN_KIN_JUTYU     as NAN_KIN_JUTYU,");
            sql.addSql("     NTP_AN_DATA.NAN_MITUMORI_DATE as NAN_MITUMORI_DATE,");
            sql.addSql("     NTP_AN_DATA.NAN_JUTYU_DATE    as NAN_JUTYU_DATE,");
            sql.addSql("     NTP_AN_DATA.NAN_SYODAN        as NAN_SYODAN,");
            sql.addSql("     NTP_AN_DATA.NAN_STATE         as NAN_STATE,");
            sql.addSql("     NTP_AN_DATA.NCN_SID           as NCN_SID,");
            sql.addSql("     ANKEN_PROCESS.NGY_SID         as NGY_SID,");
            sql.addSql("     NTP_GYOMU.NGY_NAME            as NGY_NAME,");
            sql.addSql("     NTP_PROCESS.NGP_NAME          as NGP_NAME,");
            sql.addSql("     NTP_CONTACT.NCN_NAME          as NCN_NAME,");
            sql.addSql("     CASE NTP_AN_DATA.NAN_MIKOMI");
            sql.addSql("     WHEN 0 THEN '10%'");
            sql.addSql("     WHEN 1 THEN '30%'");
            sql.addSql("     WHEN 2 THEN '50%'");
            sql.addSql("     WHEN 3 THEN '70%'");
            sql.addSql("     WHEN 4 THEN '100%'");
            sql.addSql("     ELSE '0%' END  as NAN_MIKOMI_VAL,");
            sql.addSql("     -1          as NAH_SID");
            sql.addSql(" from");
            sql.addSql("   (");
            sql.addSql("     select ");
            sql.addSql("         distinct ");
            sql.addSql("         NTP_ANKEN.NAN_SID,");
            sql.addSql("         NTP_ANKEN.NAN_CODE,");
            sql.addSql("         NTP_ANKEN.NAN_NAME,");
            sql.addSql("         NTP_ANKEN.NAN_DETIAL,");
            sql.addSql("         NTP_ANKEN.NAN_DATE,");
            sql.addSql("         NTP_ANKEN.ACO_SID,");
            sql.addSql("         ADR_COMPANY.ACO_NAME,");
            sql.addSql("         NTP_ANKEN.ABA_SID,");
            sql.addSql("         NTP_ANKEN.NGP_SID,");
            sql.addSql("         NTP_ANKEN.NAN_MIKOMI,");
            sql.addSql("         NTP_ANKEN_MITUMORI.NAN_KIN_MITUMORI,");
            sql.addSql("         NTP_ANKEN_JUTYU.NAN_KIN_JUTYU,");
            sql.addSql("         NTP_ANKEN.NAN_MITUMORI_DATE,");
            sql.addSql("         NTP_ANKEN.NAN_JUTYU_DATE,");
            sql.addSql("         NTP_ANKEN.NAN_SYODAN,");
            sql.addSql("         NTP_ANKEN.NAN_STATE,");
            sql.addSql("         NTP_ANKEN.NCN_SID,");
            sql.addSql("         NTP_ANKEN.NAN_EDATE");
            sql.addSql("     from ");
            sql.addSql("         NTP_ANKEN");
            sql.addSql("     LEFT JOIN");
            sql.addSql("         ADR_COMPANY");
            sql.addSql("     ON");
            sql.addSql("         ADR_COMPANY.ACO_SID=NTP_ANKEN.ACO_SID");

            //カテゴリ指定時
            if (searchMdl != null) {
                __getCategorySql(sql, "NTP_ANKEN", searchMdl.getShohinCatSid());
            }
            //見積もり金額
            __getMitumoriSql(frdate, todate, sql, searchMdl, "NTP_ANKEN", "NTP_ANKEN_MITUMORI");
            //受注金額
            __getJutyuSql(frdate, todate, sql, searchMdl, "NTP_ANKEN", "NTP_ANKEN_JUTYU");

            sql.addSql("   ) NTP_AN_DATA");
            sql.addSql(" LEFT JOIN");
            sql.addSql("   ADR_COMPANY_BASE");
            sql.addSql(" ON");
            sql.addSql("   ADR_COMPANY_BASE.ABA_SID=NTP_AN_DATA.ABA_SID");
            sql.addSql(" LEFT JOIN");
            sql.addSql("  (");
            sql.addSql("    select");
            sql.addSql("      NTP_PROCESS.NGP_SID,");
            sql.addSql("      NTP_PROCESS.NGY_SID");
            sql.addSql("    from");
            sql.addSql("      NTP_PROCESS");
            sql.addSql("   ) ANKEN_PROCESS");
            sql.addSql(" ON ");
            sql.addSql("   NTP_AN_DATA.NGP_SID = ANKEN_PROCESS.NGP_SID");
            sql.addSql(" LEFT JOIN");
            sql.addSql("   NTP_GYOMU");
            sql.addSql(" ON");
            sql.addSql("   ANKEN_PROCESS.NGY_SID = NTP_GYOMU.NGY_SID");
            sql.addSql(" LEFT JOIN");
            sql.addSql("   NTP_PROCESS");
            sql.addSql(" ON");
            sql.addSql("   ANKEN_PROCESS.NGP_SID = NTP_PROCESS.NGP_SID");
            sql.addSql(" LEFT JOIN");
            sql.addSql("   NTP_CONTACT");
            sql.addSql(" ON");
            sql.addSql("   NTP_AN_DATA.NCN_SID = NTP_CONTACT.NCN_SID");

            if (ankenState >= 0) {
                sql.addSql(" where ");
                sql.addSql("   NTP_AN_DATA.NAN_SYODAN = " + ankenState);
                whereFlg = true;
            }

            if (state > -1) {
                if (!whereFlg) {
                    sql.addSql(" where ");
                    whereFlg = true;
                } else {
                    sql.addSql(" and");
                }
                sql.addSql("   NTP_AN_DATA.NAN_STATE = " + state);
            }

            sql = __getWhereDateForAnken(frdate, todate, sql, "NTP_AN_DATA", whereFlg);

            sql = __getWhereFromSearchMdl(searchMdl, sql, "NTP_AN_DATA", true);


            sql.addSql(" union ");

            sql.addSql(" select ");
            sql.addSql("     distinct ");
            sql.addSql("     NTP_AN_DATA.NAN_SID           as NAN_SID,");
            sql.addSql("     NTP_AN_DATA.NAN_CODE          as NAN_CODE,");
            sql.addSql("     NTP_AN_DATA.NAN_NAME          as NAN_NAME,");
            sql.addSql("     NTP_AN_DATA.NAN_DETIAL        as NAN_DETIAL,");
            sql.addSql("     NTP_AN_DATA.NAN_DATE          as NAN_DATE,");
            sql.addSql("     NTP_AN_DATA.ACO_SID           as ACO_SID,");
            sql.addSql("     NTP_AN_DATA.ACO_NAME          as ACO_NAME,");
            sql.addSql("     NTP_AN_DATA.ABA_SID           as ABA_SID,");
            sql.addSql("     ADR_COMPANY_BASE.ABA_NAME     as ABA_NAME,");
            sql.addSql("     NTP_AN_DATA.NGP_SID           as NGP_SID,");
            sql.addSql("     NTP_AN_DATA.NAN_MIKOMI        as NAN_MIKOMI,");
            sql.addSql("     NTP_AN_DATA.NAN_KIN_MITUMORI  as NAN_KIN_MITUMORI,");
            sql.addSql("     NTP_AN_DATA.NAN_KIN_JUTYU     as NAN_KIN_JUTYU,");
            sql.addSql("     NTP_AN_DATA.NAN_MITUMORI_DATE as NAN_MITUMORI_DATE,");
            sql.addSql("     NTP_AN_DATA.NAN_JUTYU_DATE    as NAN_JUTYU_DATE,");
            sql.addSql("     NTP_AN_DATA.NAN_SYODAN        as NAN_SYODAN,");
            sql.addSql("     NTP_AN_DATA.NAN_STATE         as NAN_STATE,");
            sql.addSql("     NTP_AN_DATA.NCN_SID           as NCN_SID,");
            sql.addSql("     ANKEN_PROCESS.NGY_SID         as NGY_SID,");
            sql.addSql("     NTP_GYOMU.NGY_NAME            as NGY_NAME,");
            sql.addSql("     NTP_PROCESS.NGP_NAME          as NGP_NAME,");
            sql.addSql("     NTP_CONTACT.NCN_NAME          as NCN_NAME,");
            sql.addSql("     CASE NTP_AN_DATA.NAN_MIKOMI");
            sql.addSql("     WHEN 0 THEN '10%'");
            sql.addSql("     WHEN 1 THEN '30%'");
            sql.addSql("     WHEN 2 THEN '50%'");
            sql.addSql("     WHEN 3 THEN '70%'");
            sql.addSql("     WHEN 4 THEN '100%'");
            sql.addSql("     ELSE '0%' END  as NAN_MIKOMI_VAL,");
            sql.addSql("     NTP_AN_DATA.NAH_SID          as NAH_SID");
            sql.addSql(" from");
            sql.addSql("   (");
            sql.addSql("     select ");
            sql.addSql("         NTP_AN_HIS.NAH_SID,");
            sql.addSql("         NTP_AN_HIS.NAN_SID,");
            sql.addSql("         NTP_AN_HIS.NAN_CODE,");
            sql.addSql("         NTP_AN_HIS.NAN_NAME,");
            sql.addSql("         NTP_AN_HIS.NAN_DETIAL,");
            sql.addSql("         NTP_AN_HIS.NAN_DATE,");
            sql.addSql("         NTP_AN_HIS.ACO_SID,");
            sql.addSql("         ADR_COMPANY.ACO_NAME,");
            sql.addSql("         NTP_AN_HIS.ABA_SID,");
            sql.addSql("         NTP_AN_HIS.NGP_SID,");
            sql.addSql("         NTP_AN_HIS.NAN_MIKOMI,");
            sql.addSql("         NTP_AN_HIS.NAN_KIN_MITUMORI,");
            sql.addSql("         NTP_AN_HIS.NAN_KIN_JUTYU,");
            sql.addSql("         NTP_AN_HIS.NAN_MITUMORI_DATE,");
            sql.addSql("         NTP_AN_HIS.NAN_JUTYU_DATE,");
            sql.addSql("         NTP_AN_HIS.NAN_SYODAN,");
            sql.addSql("         NTP_AN_HIS.NAN_STATE,");
            sql.addSql("         NTP_AN_HIS.NCN_SID,");
            sql.addSql("         NTP_AN_HIS.NAN_EDATE");
            sql.addSql("     from ");

            sql.addSql("     ( ");
            sql = __getAnkenHistoryTable(frdate, todate, sql, searchMdl);
            sql.addSql("     ) NTP_AN_HIS");

            sql.addSql("     LEFT JOIN");
            sql.addSql("         ADR_COMPANY");
            sql.addSql("     ON");
            sql.addSql("         ADR_COMPANY.ACO_SID=NTP_AN_HIS.ACO_SID");
            sql.addSql("   ) NTP_AN_DATA");
            sql.addSql(" LEFT JOIN");
            sql.addSql("   ADR_COMPANY_BASE");
            sql.addSql(" ON");
            sql.addSql("   ADR_COMPANY_BASE.ABA_SID=NTP_AN_DATA.ABA_SID");
            sql.addSql(" LEFT JOIN");
            sql.addSql("  (");
            sql.addSql("    select");
            sql.addSql("      NTP_PROCESS.NGP_SID,");
            sql.addSql("      NTP_PROCESS.NGY_SID");
            sql.addSql("    from");
            sql.addSql("      NTP_PROCESS");
            sql.addSql("   ) ANKEN_PROCESS");
            sql.addSql(" ON ");
            sql.addSql("   NTP_AN_DATA.NGP_SID = ANKEN_PROCESS.NGP_SID");
            sql.addSql(" LEFT JOIN");
            sql.addSql("   NTP_GYOMU");
            sql.addSql(" ON");
            sql.addSql("   ANKEN_PROCESS.NGY_SID = NTP_GYOMU.NGY_SID");
            sql.addSql(" LEFT JOIN");
            sql.addSql("   NTP_PROCESS");
            sql.addSql(" ON");
            sql.addSql("   ANKEN_PROCESS.NGP_SID = NTP_PROCESS.NGP_SID");
            sql.addSql(" LEFT JOIN");
            sql.addSql("   NTP_CONTACT");
            sql.addSql(" ON");
            sql.addSql("   NTP_AN_DATA.NCN_SID = NTP_CONTACT.NCN_SID");

            whereFlg = false;

            if (ankenState >= 0) {
                sql.addSql(" where ");
                sql.addSql("   NTP_AN_DATA.NAN_SYODAN = " + ankenState);
                whereFlg = true;
            }

            if (state > -1) {
                if (!whereFlg) {
                    sql.addSql(" where ");
                    whereFlg = true;
                } else {
                    sql.addSql(" and");
                }
                sql.addSql("   NTP_AN_DATA.NAN_STATE = " + state);
            }

            sql = __getWhereFromSearchMdl(searchMdl, sql, "NTP_AN_DATA", whereFlg);

            sql.addSql("  order by NAN_KIN_JUTYU DESC");

            pstmt = con.prepareStatement(sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            if (pageNum != -1 && offset > 1) {
                rs.absolute((offset - 1) * limit);
            }

            if (pageNum != -1) {
                for (int i = 0; rs.next() && i < limit; i++) {
                    ankenMdl = __getNtpAnkenDataFromRs(rs, frdate, todate);
                    ret.add(ankenMdl);
                }
            } else {
                while (rs.next()) {
                    ankenMdl = __getNtpAnkenDataFromRs(rs, frdate, todate);
                    ret.add(ankenMdl);
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
     * <p>Select NTP_SHOHIN
     * @param nanSid NAN_SID
     * @return NTP_SHOHINModel
     * @throws SQLException SQL実行例外
     */
    public List<Ntp220ShohinModel> getAnkenShohin(int nanSid)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Ntp220ShohinModel> ret = new ArrayList<Ntp220ShohinModel>();
        Ntp220ShohinModel shnMdl = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NHN_SID,");
            sql.addSql("   NSC_SID,");
            sql.addSql("   NHN_CODE,");
            sql.addSql("   NHN_NAME,");
            sql.addSql("   NHN_PRICE_SALE,");
            sql.addSql("   NHN_PRICE_COST,");
            sql.addSql("   NHN_HOSOKU");
            sql.addSql(" from");
            sql.addSql("   NTP_SHOHIN");
            sql.addSql(" where ");
            sql.addSql("   NHN_SID in");
            sql.addSql("   (");
            sql.addSql("     select");
            sql.addSql("       NHN_SID");
            sql.addSql("     from ");
            sql.addSql("       NTP_AN_SHOHIN");
            sql.addSql("     where");
            sql.addSql("       NAN_SID =" + nanSid);
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                shnMdl = new Ntp220ShohinModel();
                shnMdl.setNhnSid(rs.getInt("NHN_SID"));
                shnMdl.setNscSid(rs.getInt("NSC_SID"));
                shnMdl.setNhnCode(rs.getString("NHN_CODE"));
                shnMdl.setNhnName(rs.getString("NHN_NAME"));
                shnMdl.setNhnPriceSale(rs.getInt("NHN_PRICE_SALE"));
                shnMdl.setNhnPriceCost(rs.getInt("NHN_PRICE_COST"));
                shnMdl.setNhnHosoku(rs.getString("NHN_HOSOKU"));
                ret.add(shnMdl);
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
     * <p>Select NTP_SHOHIN
     * @param nahSid NAN_SID
     * @return NTP_SHOHINModel
     * @throws SQLException SQL実行例外
     */
    public List<Ntp220ShohinModel> getAnkenShohinHistory(int nahSid)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Ntp220ShohinModel> ret = new ArrayList<Ntp220ShohinModel>();
        Ntp220ShohinModel shnMdl = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NHN_SID,");
            sql.addSql("   NSC_SID,");
            sql.addSql("   NHN_CODE,");
            sql.addSql("   NHN_NAME,");
            sql.addSql("   NHN_PRICE_SALE,");
            sql.addSql("   NHN_PRICE_COST,");
            sql.addSql("   NHN_HOSOKU");
            sql.addSql(" from");
            sql.addSql("   NTP_SHOHIN");
            sql.addSql(" where ");
            sql.addSql("   NHN_SID in");
            sql.addSql("   (");
            sql.addSql("     select");
            sql.addSql("       NHN_SID");
            sql.addSql("     from ");
            sql.addSql("       NTP_AN_SHOHIN_HISTORY");
            sql.addSql("     where");
            sql.addSql("       NAH_SID =" + nahSid);
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                shnMdl = new Ntp220ShohinModel();
                shnMdl.setNhnSid(rs.getInt("NHN_SID"));
                shnMdl.setNscSid(rs.getInt("NSC_SID"));
                shnMdl.setNhnCode(rs.getString("NHN_CODE"));
                shnMdl.setNhnName(rs.getString("NHN_NAME"));
                shnMdl.setNhnPriceSale(rs.getInt("NHN_PRICE_SALE"));
                shnMdl.setNhnPriceCost(rs.getInt("NHN_PRICE_COST"));
                shnMdl.setNhnHosoku(rs.getString("NHN_HOSOKU"));
                ret.add(shnMdl);
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
     * <p>指定条件の商品の件数と情報を取得する
     * @param state 状態-1:すべて 0:進行中   1:完了
     * @param ankenState 案件状態 1:商談中   2:受注   3:失注
     * @param searchMdl 検索モデル
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @return NTP_SHOHINModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Ntp220ShohinModel> getAnkenShohinData(
                       int state,
                       int ankenState,
                       Ntp220AnkenSearchModel searchMdl,
                       UDate frdate,
                       UDate todate) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Ntp220ShohinModel> ret = new ArrayList<Ntp220ShohinModel>();
        Ntp220ShohinModel shnMdl = null;
        boolean whereFlg = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select  ");
            sql.addSql(" sum(SHOHIN_DATA.CNT) as CNT, ");
            sql.addSql(" SHOHIN_DATA.NHN_SID as NHN_SID, ");
            sql.addSql(" SHOHIN_DATA.NHN_CODE as NHN_CODE, ");
            sql.addSql(" SHOHIN_DATA.NHN_NAME as NHN_NAME, ");
            sql.addSql(" SHOHIN_DATA.NHN_PRICE_SALE as NHN_PRICE_SALE, ");
            sql.addSql(" SHOHIN_DATA.NHN_PRICE_COST as NHN_PRICE_COST, ");
            sql.addSql(" SHOHIN_DATA.NHN_HOSOKU as NHN_HOSOKU ");
            sql.addSql(" from ");
            sql.addSql(" ( ");

            sql.addSql(" select ");
            sql.addSql("   NSH_DATA.CNT as CNT,");
            sql.addSql("   NTP_SHOHIN.NHN_SID as NHN_SID,");
            sql.addSql("   NTP_SHOHIN.NHN_CODE as NHN_CODE,");
            sql.addSql("   NTP_SHOHIN.NHN_NAME as NHN_NAME,");
            sql.addSql("   NTP_SHOHIN.NHN_PRICE_SALE as NHN_PRICE_SALE,");
            sql.addSql("   NTP_SHOHIN.NHN_PRICE_COST as NHN_PRICE_COST,");
            sql.addSql("   NTP_SHOHIN.NHN_HOSOKU as NHN_HOSOKU");
            sql.addSql(" from");
            sql.addSql("   (");
            sql.addSql("     select");
            sql.addSql("       NTP_AN_SHOHIN.NHN_SID as NHN_SID,");
            sql.addSql("       count(NTP_AN_SHOHIN.NHN_SID) as CNT");
            sql.addSql("     from");
            sql.addSql("       NTP_AN_SHOHIN");
            sql.addSql("     where");
            sql.addSql("       NTP_AN_SHOHIN.NAN_SID in");
            sql.addSql("     (");
            sql.addSql("       select");
            sql.addSql("         distinct");
            sql.addSql("         NTP_ANKEN.NAN_SID");
            sql.addSql("       from ");
            sql.addSql("         NTP_ANKEN");

            //カテゴリ指定時
            if (searchMdl != null) {
                __getCategorySql(sql, "NTP_ANKEN", searchMdl.getShohinCatSid());
            }


            if (ankenState >= 0) {
                sql.addSql("   where ");
                sql.addSql("     NTP_ANKEN.NAN_SYODAN = " + ankenState);
                whereFlg = true;
            }

            if (state > -1) {
                if (!whereFlg) {
                    sql.addSql(" where ");
                    whereFlg = true;
                } else {
                    sql.addSql(" and");
                }
                sql.addSql("       NTP_ANKEN.NAN_STATE = " + state);
            }

            sql = __getWhereDateForAnken(frdate, todate, sql, "NTP_ANKEN", whereFlg);

            sql = __getWhereFromSearchMdl(searchMdl, sql, "NTP_ANKEN", true);

            sql.addSql("     )");
            sql.addSql("     group by NTP_AN_SHOHIN.NHN_SID");
            sql.addSql("   ) NSH_DATA");
            sql.addSql(" LEFT JOIN");
            sql.addSql("   NTP_SHOHIN");
            sql.addSql(" ON");
            sql.addSql("   NSH_DATA.NHN_SID = NTP_SHOHIN.NHN_SID");

            if (searchMdl != null && searchMdl.getShohinCatSid() > 0) {
                sql.addSql(" where ");
                sql.addSql("   NTP_SHOHIN.NSC_SID = " + searchMdl.getShohinCatSid());
            }

            sql.addSql(" union all");

            sql.addSql(" select ");
            sql.addSql("   NSH_DATA.CNT CNT,");
            sql.addSql("   NTP_SHOHIN.NHN_SID as NHN_SID,");
            sql.addSql("   NTP_SHOHIN.NHN_CODE as NHN_CODE,");
            sql.addSql("   NTP_SHOHIN.NHN_NAME as NHN_NAME,");
            sql.addSql("   NTP_SHOHIN.NHN_PRICE_SALE as NHN_PRICE_SALE,");
            sql.addSql("   NTP_SHOHIN.NHN_PRICE_COST as NHN_PRICE_COST,");
            sql.addSql("   NTP_SHOHIN.NHN_HOSOKU as NHN_HOSOKU");
            sql.addSql(" from");
            sql.addSql("   (");
            sql.addSql("     select");
            sql.addSql("       NTP_AN_SHOHIN_HISTORY.NHN_SID as NHN_SID,");
            sql.addSql("       count(NTP_AN_SHOHIN_HISTORY.NHN_SID) as CNT");
            sql.addSql("     from");
            sql.addSql("       NTP_AN_SHOHIN_HISTORY");
            sql.addSql("     where");
            sql.addSql("       NTP_AN_SHOHIN_HISTORY.NAN_SID in");
            sql.addSql("     (");
            sql.addSql("       select");
            sql.addSql("         NTP_AN_HIS.NAN_SID");
            sql.addSql("       from ");
            sql.addSql("         (");
            sql = __getAnkenHistoryTable(frdate, todate, sql, searchMdl);
            sql.addSql("         ) NTP_AN_HIS");
            whereFlg = false;
            if (ankenState >= 0) {
                sql.addSql("   where ");
                sql.addSql("     NTP_AN_HIS.NAN_SYODAN = " + ankenState);
                whereFlg = true;
            }

            if (state > -1) {
                if (!whereFlg) {
                    sql.addSql(" where ");
                    whereFlg = true;
                } else {
                    sql.addSql(" and");
                }
                sql.addSql("       NTP_AN_HIS.NAN_STATE = " + state);
            }

            if (!whereFlg) {
                sql.addSql(" where ");
                whereFlg = true;
            } else {
                sql.addSql(" and");
            }
            sql.addSql("            NTP_AN_SHOHIN_HISTORY.NAH_SID = NTP_AN_HIS.NAH_SID ");

            sql = __getWhereFromSearchMdl(searchMdl, sql, "NTP_AN_HIS", whereFlg);

            sql.addSql("     )");
            sql.addSql("     group by NTP_AN_SHOHIN_HISTORY.NHN_SID");
            sql.addSql("   ) NSH_DATA");
            sql.addSql(" LEFT JOIN");
            sql.addSql("   NTP_SHOHIN");
            sql.addSql(" ON");
            sql.addSql("   NSH_DATA.NHN_SID = NTP_SHOHIN.NHN_SID");

            if (searchMdl != null && searchMdl.getShohinCatSid() > 0) {
                sql.addSql(" where ");
                sql.addSql("   NTP_SHOHIN.NSC_SID = " + searchMdl.getShohinCatSid());
            }

            sql.addSql(" ) SHOHIN_DATA ");
            sql.addSql(" group by SHOHIN_DATA.NHN_SID, ");
            sql.addSql("          SHOHIN_DATA.NHN_CODE, ");
            sql.addSql("          SHOHIN_DATA.NHN_NAME, ");
            sql.addSql("          SHOHIN_DATA.NHN_PRICE_SALE, ");
            sql.addSql("          SHOHIN_DATA.NHN_PRICE_COST, ");
            sql.addSql("          SHOHIN_DATA.NHN_HOSOKU ");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                shnMdl = new Ntp220ShohinModel();
                shnMdl.setNshCnt(rs.getInt("CNT"));
                shnMdl.setNhnSid(rs.getInt("NHN_SID"));
                shnMdl.setNhnCode(rs.getString("NHN_CODE"));
                shnMdl.setNhnName(rs.getString("NHN_NAME"));
                shnMdl.setNhnPriceSale(rs.getInt("NHN_PRICE_SALE"));
                shnMdl.setNhnPriceCost(rs.getInt("NHN_PRICE_COST"));
                shnMdl.setNhnHosoku(rs.getString("NHN_HOSOKU"));
                ret.add(shnMdl);
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
     * <br>[機  能] 検索モデルからwhere句のSQLを生成する
     * <br>[解  説]
     * <br>[備  考]
     * @param searchMdl 検索モデル
     * @param sql sql
     * @param tableName テーブル名
     * @param whereFlg true:すでにwhereを利用 false:未使用
     * @return jsonData json案件リスト
     */
    private SqlBuffer __getWhereFromSearchMdl(Ntp220AnkenSearchModel searchMdl,
                                             SqlBuffer sql,
                                             String tableName,
                                             boolean whereFlg) {
        if (searchMdl != null) {
            if (searchMdl.isSearchPramFlg()
                && (searchMdl.getSearchContent().equals(GSConstNippou.STR_KIGYOU)
                    || searchMdl.getSearchContent().equals(GSConstNippou.STR_PROCESS)
                    || searchMdl.getSearchContent().equals(GSConstNippou.STR_MIKOMIDO)
                    || searchMdl.getSearchContent().equals(GSConstNippou.STR_KOKYAKUGENSEN))) {
                if (!whereFlg) {
                    sql.addSql(" where ");
                    whereFlg = true;
                } else {
                    sql.addSql(" and");
                }
                if (searchMdl.getSearchContent().equals(GSConstNippou.STR_KIGYOU)) {
                    //企業選択状態
                    if (searchMdl.getAcoSid() > 0) {
                        sql.addSql("   " + tableName + ".ACO_SID = " + searchMdl.getAcoSid());
                    }
                    if (searchMdl.getAbaSid() > 0) {
                        sql.addSql(" and");
                        sql.addSql("   " + tableName + ".ABA_SID = " + searchMdl.getAbaSid());
                    }
                } else if (searchMdl.getSearchContent().equals(GSConstNippou.STR_PROCESS)) {
                    //プロセス選択状態
                    if (searchMdl.getNgpSid() > 0) {
                        sql.addSql("   " + tableName + ".NGP_SID = "
                                   + searchMdl.getNgpSid());
                    }
                } else if (searchMdl.getSearchContent().equals(GSConstNippou.STR_MIKOMIDO)) {
                    //見込み度選択状態
                    if (searchMdl.getNanMikomiSearchVal() > -1) {
                        sql.addSql("   " + tableName + ".NAN_MIKOMI = "
                                   + searchMdl.getNanMikomiSearchVal());
                    }
                } else if (searchMdl.getSearchContent().equals(GSConstNippou.STR_KOKYAKUGENSEN)) {
                    //顧客源泉選択状態
                    if (searchMdl.getNcnSid() > 0) {
                        sql.addSql("   " + tableName + ".NCN_SID = " + searchMdl.getNcnSid());
                    }
                }

            }

            //グループ指定時の案件
            if (searchMdl.getAnkenSidList() != null && !searchMdl.getAnkenSidList().isEmpty()) {
                if (!whereFlg) {
                    sql.addSql(" where ");
                    whereFlg = true;
                } else {
                    sql.addSql(" and ");
                }
                sql.addSql(" "  + tableName + ".NAN_SID in (");

                for (int i = 0; i < searchMdl.getAnkenSidList().size(); i++) {
                    if (i != 0) {
                        sql.addSql(",");
                    }
                    sql.addSql(String.valueOf(searchMdl.getAnkenSidList().get(i)));
                }
                sql.addSql(")");
            }

            //業種指定時
            if (searchMdl.getGyoushuSid() != -1) {
                if (!whereFlg) {
                    sql.addSql(" where ");
                    whereFlg = true;
                } else {
                    sql.addSql(" and ");
                }
                sql.addSql(" "  + tableName + ".NGP_SID in (");
                sql.addSql(" select ");
                sql.addSql("   NGP_SID ");
                sql.addSql(" from ");
                sql.addSql("   NTP_PROCESS ");
                sql.addSql(" where ");
                sql.addSql("   NGY_SID = " + searchMdl.getGyoushuSid());
                sql.addSql(" )");
            }

        }
        return sql;
    }

    /**
     * <br>[機  能] 案件SID指定時のSQLを生成する
     * <br>[解  説]
     * <br>[備  考]
     * @param ankenSids 案件SID
     * @param sql sql
     * @param tableName テーブル名
     * @param whereFlg true:すでにwhereを利用 false:未使用
     * @return jsonData json案件リスト
     */
    private SqlBuffer __getWhereFromAnkenSids(List<Integer> ankenSids,
                                             SqlBuffer sql,
                                             String tableName,
                                             boolean whereFlg) {

        if (ankenSids != null && !ankenSids.isEmpty()) {
            if (!whereFlg) {
                sql.addSql(" where ");
            } else {
                sql.addSql(" and ");
            }
            sql.addSql(" "  + tableName + ".NAN_SID in (");

            for (int i = 0; i < ankenSids.size(); i++) {
                if (i != 0) {
                    sql.addSql(",");
                }
                sql.addSql(String.valueOf(ankenSids.get(i)));
            }
            sql.addSql(")");
        }
        return sql;
    }

    /**
     * <br>[機  能] NTP_ANKENテーブルの日付選択範囲の条件文を生成する
     * <br>[解  説]
     * <br>[備  考]
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @param sql sql
     * @param tableName テーブル名
     * @param whereFlg true:すでにwhereを利用 false:未使用
     * @return jsonData json案件リスト
     */
    private SqlBuffer __getWhereDateForAnken(UDate frdate,
                                              UDate todate,
                                             SqlBuffer sql,
                                             String tableName,
                                             boolean whereFlg) {

        if (frdate != null && todate != null) {

            String fromDateStr = getFrDateString(frdate);
            String toDateStr = getToDateString(todate);

            if (!whereFlg) {
                sql.addSql(" where ");
            } else {
                sql.addSql(" and ");
            }
            sql.addSql("(");
            sql.addSql("     "  + tableName + ".NAN_DATE ");
            sql.addSql("     between cast('" + fromDateStr + "' as DATE)");
            sql.addSql("     and cast('" + toDateStr + "' as DATE)");
            sql.addSql(")");
        }
        return sql;
    }


    /**
     * <br>[機  能] 指定日付の条件のNTP_AN_HISTORYテーブルのselect文を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param frdate 選択開始日付
     * @param todate 選択終了日付
     * @param sql sql
     * @param searchMdl 検索モデル
     * @return jsonData json案件リスト
     */
    private SqlBuffer __getAnkenHistoryTable(UDate frdate,
                                              UDate todate,
                                              SqlBuffer sql,
                                              Ntp220AnkenSearchModel searchMdl) {

        String fromDateStr = getFrDateString(frdate);
        String toDateStr = getToDateString(todate);

        sql.addSql("    select   ");
        sql.addSql("      distinct  ");
        sql.addSql("      NTP_AN_HISTORY.NAH_SID as NAH_SID,  ");
        sql.addSql("      NTP_AN_HISTORY.NAN_SID as NAN_SID,  ");
        sql.addSql("      NTP_AN_HISTORY.NAN_NAME as NAN_NAME,  ");
        sql.addSql("      NTP_AN_HISTORY.ACO_SID as ACO_SID,  ");
        sql.addSql("      NTP_AN_HISTORY.ABA_SID as ABA_SID,  ");
        sql.addSql("      NTP_ANKEN_MITUMORI.NAN_KIN_MITUMORI as NAN_KIN_MITUMORI, ");
        sql.addSql("      NTP_ANKEN_JUTYU.NAN_KIN_JUTYU as NAN_KIN_JUTYU,  ");
        sql.addSql("      NTP_ANKEN_MITUMORI.NAN_MITUMORI_DATE as NAN_MITUMORI_DATE,");
        sql.addSql("      NTP_ANKEN_JUTYU.NAN_JUTYU_DATE as NAN_JUTYU_DATE,");
        sql.addSql("      NTP_AN_HISTORY.NAN_SYODAN as NAN_SYODAN,  ");
        sql.addSql("      NTP_AN_HISTORY.NAN_STATE as NAN_STATE, ");
        sql.addSql("      NTP_AN_HISTORY.NAN_CODE as NAN_CODE, ");
        sql.addSql("      NTP_AN_HISTORY.NAN_DETIAL as NAN_DETIAL, ");
        sql.addSql("      NTP_AN_HISTORY.NAN_DATE as NAN_DATE, ");
        sql.addSql("      NTP_AN_HISTORY.NGP_SID as NGP_SID, ");
        sql.addSql("      NTP_AN_HISTORY.NAN_MIKOMI as NAN_MIKOMI, ");
        sql.addSql("      NTP_AN_HISTORY.NCN_SID as NCN_SID, ");
        sql.addSql("      NTP_AN_HISTORY.NAN_AUID as NAN_AUID, ");
        sql.addSql("      NTP_AN_HISTORY.NAN_ADATE as NAN_ADATE, ");
        sql.addSql("      NTP_AN_HISTORY.NAN_EUID as NAN_EUID, ");
        sql.addSql("      NTP_AN_HISTORY.NAN_EDATE as NAN_EDATE ");
        sql.addSql("    from  ");
        sql.addSql("      NTP_AN_HISTORY ");

        //カテゴリ指定時
        if (searchMdl != null) {
            __getCategorySql(sql, "NTP_AN_HISTORY", searchMdl.getShohinCatSid());
        }

        sql.addSql("   RIGHT JOIN ( ");
        sql.addSql("    select  ");
        sql.addSql("      MAX(NTP_AN_HISTORY.NAN_DATE) as NAN_DATE, ");
        sql.addSql("      NTP_AN_HISTORY.NAN_SID as NAN_SID ");
        sql.addSql("    from  ");
        sql.addSql("      NTP_AN_HISTORY ");
        sql.addSql("    where  ");
        sql.addSql("      ( ");
        sql.addSql("       NTP_AN_HISTORY.NAN_DATE ");
        sql.addSql("       between cast('" + fromDateStr + "' as DATE) ");
        sql.addSql("       and cast('" + toDateStr + "' as DATE) ");
        sql.addSql("      ) ");
        sql.addSql("    and  ");
        sql.addSql("      NAN_SID not in ( ");
        sql.addSql("        select  ");
        sql.addSql("          distinct ");
        sql.addSql("          NAN_SID ");
        sql.addSql("        from  ");
        sql.addSql("          NTP_ANKEN ");
        sql.addSql("        where  ");
        sql.addSql("         ( ");
        sql.addSql("          NTP_ANKEN.NAN_DATE ");
        sql.addSql("          between cast('" + fromDateStr + "' as DATE) ");
        sql.addSql("          and cast('" + toDateStr + "' as DATE) ");
        sql.addSql("          ) ");
        sql.addSql("      ) ");
        sql = __getWhereFromSearchMdl(searchMdl, sql, "NTP_AN_HISTORY", true);
        sql.addSql("   group by NAN_SID ");
        sql.addSql("   ) NTP_HIS ");
        sql.addSql("   ON NTP_AN_HISTORY.NAN_SID = NTP_HIS.NAN_SID ");
        sql.addSql("   AND NTP_AN_HISTORY.NAN_DATE = NTP_HIS.NAN_DATE ");

        //見積もり金額
        __getMitumoriSql(frdate, todate, sql, searchMdl, "NTP_AN_HISTORY", "NTP_ANKEN_MITUMORI");
        //受注金額
        __getJutyuSql(frdate, todate, sql, searchMdl, "NTP_AN_HISTORY", "NTP_ANKEN_JUTYU");


        return sql;
    }

    /**
     * <br>[機  能] カテゴリ指定時のSQLを返す
     * <br>[解  説]
     * <br>[備  考]
     * @param sql sql
     * @param tableName テーブル名
     * @param nscSid カテゴリSID
     * @return sql sql
     */
    private SqlBuffer __getCategorySql(SqlBuffer sql,
                                       String tableName,
                                       int nscSid) {

        if (nscSid > 0) {
            sql.addSql(" RIGHT JOIN ( ");
            sql.addSql("   select ");
            sql.addSql("     NTP_AN_SHOHIN.NAN_SID, ");
            sql.addSql("     NTP_SHOHIN.NHN_NAME, ");
            sql.addSql("     NTP_SHOHIN.NSC_SID ");
            sql.addSql("   from  ");
            sql.addSql("     NTP_AN_SHOHIN, ");
            sql.addSql("     NTP_SHOHIN ");
            sql.addSql("   where ");
            sql.addSql("     NTP_AN_SHOHIN.NHN_SID = NTP_SHOHIN.NHN_SID ");
            sql.addSql("   and ");
            sql.addSql("     NTP_SHOHIN.NSC_SID = " + nscSid);
            sql.addSql("   ) ANKEN_SHOHIN ");
            sql.addSql(" ON " + tableName + ".NAN_SID = ANKEN_SHOHIN.NAN_SID  ");

        }
        return sql;
    }

    /**
     * <br>[機  能] 見積もり金額指定条件SQLを返す
     * <br>[解  説]
     * <br>[備  考]
     * @param frdate 指定開始日
     * @param todate 指定終了日
     * @param sql sql
     * @param tableName1 結合テーブル名
     * @param tableName2 作成テーブル名
     * @param searchMdl 検索モデル
     * @return sql sql
     */
    private SqlBuffer __getMitumoriSql(UDate frdate,
                                       UDate todate,
                                       SqlBuffer sql,
                                       Ntp220AnkenSearchModel searchMdl,
                                       String tableName1,
                                       String tableName2) {

        String fromDateStr = getFrDateString(frdate);
        String toDateStr = getToDateString(todate);

        //見積もり金額
        sql.addSql("   LEFT JOIN ");
        sql.addSql("   ( ");
        sql.addSql("   select ");
        sql.addSql("     distinct ");
        sql.addSql("     NTP_ANKEN.NAN_SID, ");
        sql.addSql("     NTP_ANKEN.NAN_MITUMORI_DATE, ");
        sql.addSql("     NTP_ANKEN.NAN_KIN_MITUMORI as NAN_KIN_MITUMORI");
        sql.addSql("   from ");
        sql.addSql("     NTP_ANKEN ");
        sql.addSql("   where ");
        sql.addSql("     ( ");
        sql.addSql("       NTP_ANKEN.NAN_MITUMORI_DATE ");
        sql.addSql("       between cast('" + fromDateStr + "' as DATE) ");
        sql.addSql("       and cast('" + toDateStr + "' as DATE) ");
        sql.addSql("     )  ");
        if (searchMdl != null) {
            sql = __getWhereFromSearchMdl(searchMdl, sql, "NTP_ANKEN", true);
        }
        sql.addSql("   ) " + tableName2 + " ");
        sql.addSql("   ON " + tableName1  + ".NAN_SID = " + tableName2 + ".NAN_SID ");

        return sql;
    }


    /**
     * <br>[機  能] 受注金額指定条件SQLを返す
     * <br>[解  説]
     * <br>[備  考]
     * @param frdate 指定開始日
     * @param todate 指定終了日
     * @param sql sql
     * @param tableName1 結合テーブル名
     * @param tableName2 作成テーブル名
     * @param searchMdl 検索モデル
     * @return sql sql
     */
    private SqlBuffer __getJutyuSql(UDate frdate,
                                       UDate todate,
                                       SqlBuffer sql,
                                       Ntp220AnkenSearchModel searchMdl,
                                       String tableName1,
                                       String tableName2) {

        String fromDateStr = getFrDateString(frdate);
        String toDateStr = getToDateString(todate);

        //見積もり金額
        //受注金額
        sql.addSql("   LEFT JOIN ");
        sql.addSql("   ( ");
        sql.addSql("   select ");
        sql.addSql("     distinct ");
        sql.addSql("     NTP_ANKEN.NAN_SID, ");
        sql.addSql("     NTP_ANKEN.NAN_JUTYU_DATE, ");
        sql.addSql("     NTP_ANKEN.NAN_KIN_JUTYU as NAN_KIN_JUTYU");
        sql.addSql("   from ");
        sql.addSql("     NTP_ANKEN ");
        sql.addSql("   where ");
        sql.addSql("     (  ");
        sql.addSql("       NTP_ANKEN.NAN_JUTYU_DATE ");
        sql.addSql("       between cast('" + fromDateStr + "' as DATE) ");
        sql.addSql("       and cast('" + toDateStr + "' as DATE) ");
        sql.addSql("     )  ");
        if (searchMdl != null) {
            sql = __getWhereFromSearchMdl(searchMdl, sql, "NTP_ANKEN", true);
        }
        sql.addSql("   ) " + tableName2 + "  ");
        sql.addSql("   ON " + tableName1  + ".NAN_SID = " + tableName2 + ".NAN_SID  ");

        return sql;
    }

    /**
     * <p>Create NTP_ANKEN Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @param frdate 指定開始日
     * @param todate 指定終了日
     * @return created NtpAnkenModel
     * @throws SQLException SQL実行例外
     */
    private Ntp220AnkenModel __getNtpAnkenDataFromRs(ResultSet rs,
                                                     UDate frdate,
                                                     UDate todate) throws SQLException {

        Ntp220AnkenModel bean = new Ntp220AnkenModel();
        bean.setNanSid(rs.getInt("NAN_SID"));
        bean.setNanCode(rs.getString("NAN_CODE"));
        bean.setNanName(rs.getString("NAN_NAME"));
        bean.setNanDetial(rs.getString("NAN_DETIAL"));
        bean.setCntSid1(rs.getInt("ACO_SID"));
        bean.setCntName1(rs.getString("ACO_NAME"));
        bean.setCntSid2(rs.getInt("ABA_SID"));
        bean.setCntName2(rs.getString("ABA_NAME"));
        bean.setNgpSid(rs.getInt("NGP_SID"));
        bean.setNanMikomi(rs.getInt("NAN_MIKOMI"));
        bean.setNanKinMitumori(rs.getInt("NAN_KIN_MITUMORI"));
        bean.setNanKinJutyu(rs.getInt("NAN_KIN_JUTYU"));


        UDate mitumoriDate = UDate.getInstanceTimestamp(rs.getTimestamp("NAN_MITUMORI_DATE"));
        UDate jutyuDate = UDate.getInstanceTimestamp(rs.getTimestamp("NAN_JUTYU_DATE"));
        bean.setNanMitumoriDateStr(mitumoriDate.getYear()
                                + "年"
                                + mitumoriDate.getMonth()
                                + "月"
                                + mitumoriDate.getIntDay()
                                + "日");
        bean.setNanJutyuDateStr(jutyuDate.getYear()
                                + "年"
                                + jutyuDate.getMonth()
                                + "月"
                                + jutyuDate.getIntDay()
                                + "日");

        if (!mitumoriDate.betweenYMDHM(frdate, todate)) {
            //日付が指定範囲内か判定
            bean.setNanMitumoriDateKbn(1);
        }

        if (!jutyuDate.betweenYMDHM(frdate, todate)) {
            //日付が指定範囲内か判定
            bean.setNanJutyuDateKbn(1);
        }

        bean.setNanSyodan(rs.getInt("NAN_SYODAN"));
        bean.setNanState(rs.getInt("NAN_STATE"));
        bean.setNcnSid(rs.getInt("NCN_SID"));
        bean.setNgySid(rs.getInt("NGY_SID"));
        bean.setNgyName(rs.getString("NGY_NAME"));
        bean.setNgpName(rs.getString("NGP_NAME"));
        bean.setNcnName(rs.getString("NCN_NAME"));
        bean.setNanMikomiVal(rs.getString("NAN_MIKOMI_VAL"));
        bean.setNahSid(rs.getInt("NAH_SID"));
        return bean;
    }


    /**
     * <p>Create NTP_ANKEN Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @param frdate 指定開始日
     * @param todate 指定終了日
     * @return created NtpAnkenModel
     * @throws SQLException SQL実行例外
     */
    private Ntp220AnkenModel __getNtpAnkenDataFromRs2(ResultSet rs,
                                                     UDate frdate,
                                                     UDate todate) throws SQLException {

        Ntp220AnkenModel bean = new Ntp220AnkenModel();
        bean.setNanSid(rs.getInt("NAN_SID"));
        bean.setNanCode(rs.getString("NAN_CODE"));
        bean.setNanName(rs.getString("NAN_NAME"));
        bean.setNanDetial(rs.getString("NAN_DETIAL"));
        bean.setCntSid1(rs.getInt("ACO_SID"));
        bean.setCntName1(rs.getString("ACO_NAME"));
        bean.setCntSid2(rs.getInt("ABA_SID"));
        bean.setCntName2(rs.getString("ABA_NAME"));
        bean.setNgpSid(rs.getInt("NGP_SID"));
        bean.setNanMikomi(rs.getInt("NAN_MIKOMI"));
        bean.setNanKinMitumori(rs.getInt("NAN_KIN_MITUMORI"));
        bean.setNanKinJutyu(rs.getInt("NAN_KIN_JUTYU"));


        UDate mitumoriDate = UDate.getInstanceTimestamp(rs.getTimestamp("NAN_MITUMORI_DATE"));
        UDate jutyuDate = UDate.getInstanceTimestamp(rs.getTimestamp("NAN_JUTYU_DATE"));

        if (mitumoriDate != null) {
            bean.setNanMitumoriDateStr(mitumoriDate.getYear()
                    + "年"
                    + mitumoriDate.getMonth()
                    + "月"
                    + mitumoriDate.getIntDay()
                    + "日");
            if (!mitumoriDate.betweenYMDHM(frdate, todate)) {
                //日付が指定範囲内か判定
                bean.setNanMitumoriDateKbn(1);
            }
        }

        if (jutyuDate != null) {
            bean.setNanJutyuDateStr(jutyuDate.getYear()
                    + "年"
                    + jutyuDate.getMonth()
                    + "月"
                    + jutyuDate.getIntDay()
                    + "日");
            if (!jutyuDate.betweenYMDHM(frdate, todate)) {
                //日付が指定範囲内か判定
                bean.setNanJutyuDateKbn(1);
            }
        }


        bean.setNanSyodan(rs.getInt("NAN_SYODAN"));
        bean.setNanState(rs.getInt("NAN_STATE"));
        bean.setNcnSid(rs.getInt("NCN_SID"));
        bean.setNgySid(rs.getInt("NGY_SID"));
        bean.setNgyName(rs.getString("NGY_NAME"));
        bean.setNgpName(rs.getString("NGP_NAME"));
        bean.setNcnName(rs.getString("NCN_NAME"));
        bean.setNanMikomiVal(rs.getString("NAN_MIKOMI_VAL"));
        return bean;
    }

    /**
     * <br>[機  能] 指定ユーザの担当する案件のSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSids ユーザSID
     * @param nscSid 商品カテゴリSID
     * @param sessionUsrSid セッションユーザSID
     * @param isAdmin 管理者権限での検索かどうか
     * @return ankenSids 案件SID
     * @throws SQLException SQL実行例外
     */
    public List<Integer> getUsersAnkenSids(
            List<Integer> usrSids, int nscSid,
            int sessionUsrSid, boolean isAdmin) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   distinct NTP_AN_MEMBER.NAN_SID as NAN_SID");
            sql.addSql(" from ");
            sql.addSql("   NTP_AN_MEMBER ");

            //カテゴリ指定時
            if (nscSid > 0) {
                __getCategorySql(sql, "NTP_AN_MEMBER", nscSid);
            }
            if (!isAdmin) {
                sql.addSql("   ,NTP_ANKEN ");
            }
            sql.addSql(" where ");
            sql.addSql("   NTP_AN_MEMBER.USR_SID in ( ");
            for (int i = 0; i < usrSids.size(); i++) {
                if (i != 0) {
                    sql.addSql(", ");
                }
                sql.addSql(String.valueOf(usrSids.get(i)));
            }
            sql.addSql(" )");
            if (!isAdmin) {
                sql.addSql(" and NTP_AN_MEMBER.NAN_SID = NTP_ANKEN.NAN_SID");
                Ntp060AnkenDao.addPermissionWhereSQL(sql,
                        sessionUsrSid,
                        GSConst.SP_AUTH_VIEWONLY);
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

    /**
     * <p>Select NTP_AN_MEMBER
     * @param nanSid NAN_SID
     * @return NTP_AN_MEMBERModel
     * @throws SQLException SQL実行例外
     */
    public String[] selectMember(int nanSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<String> ret = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID");
            sql.addSql(" from");
            sql.addSql("   NTP_AN_MEMBER");
            sql.addSql(" where ");
            sql.addSql("   NAN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nanSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(Integer.toString(rs.getInt("USR_SID")));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret.toArray(new String[ret.size()]);
    }

    /**
     * <p>Select NTP_AN_MEMBER
     * @param nahSid NAH_SID
     * @return NTP_AN_MEMBERModel
     * @throws SQLException SQL実行例外
     */
    public String[] selectMemberFromHistory(int nahSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<String> ret = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID");
            sql.addSql(" from");
            sql.addSql("   NTP_AN_MEMBER_HISTORY");
            sql.addSql(" where ");
            sql.addSql("   NAH_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nahSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(Integer.toString(rs.getInt("USR_SID")));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret.toArray(new String[ret.size()]);
    }


  /**
   * <p>指定期間内に指定グループに所属しているユーザのSIDを取得する
   * @param grpSid グループSID
   * @param frdate 選択開始日付
   * @param todate 選択終了日付
   * @return String[] usrSids
   * @throws SQLException SQL実行例外
   */
  public List<Integer> getUsrSidBelongGrpHistory(int grpSid,
                                            UDate frdate,
                                            UDate todate)
                                            throws SQLException {

      PreparedStatement pstmt = null;
      ResultSet rs = null;
      Connection con = null;
      List<Integer> ret = new ArrayList<Integer>();
      con = getCon();

      String fromDateStr = getFrDateString(frdate);
      String toDateStr = getToDateString(todate);

      try {
          //SQL文
          SqlBuffer sql = new SqlBuffer();
          sql.addSql(" select");
          sql.addSql("   MAX(BEG_DATE) as BEG_DATE,");
          sql.addSql("   USR_SID as USR_SID");
          sql.addSql(" from");
          sql.addSql("   CMN_BELONGM_HISTORY");
          sql.addSql(" where");
          sql.addSql("   BEG_DATE <= cast('" + toDateStr + "' as DATE)");
          sql.addSql(" and");
          sql.addSql("   GRP_SID = ?");
          sql.addSql(" and");
          sql.addSql("   USR_SID not in (");

          //指定期間の開始日より前に所属グループが変更したユーザを除外
          sql.addSql("     select");
          sql.addSql("       distinct");
          sql.addSql("       USR_SID as CHANGE_USR_SID");
          sql.addSql("     from");
          sql.addSql("       CMN_BELONGM_HISTORY,");
          sql.addSql("       (select");
          sql.addSql("          MAX(BEG_DATE) as ALL_SEL_BEG_DATE,");
          sql.addSql("          USR_SID as ALL_SEL_USR_SID");
          sql.addSql("        from");
          sql.addSql("          CMN_BELONGM_HISTORY");
          sql.addSql("        where");
          sql.addSql("          BEG_DATE <= cast('" + toDateStr + "' as DATE)");
          sql.addSql("        and");
          sql.addSql("          GRP_SID = ?");
          sql.addSql("        group by USR_SID) ALL_SEL_BELONG");
          sql.addSql("      where");
          sql.addSql("        CMN_BELONGM_HISTORY.USR_SID = ALL_SEL_BELONG.ALL_SEL_USR_SID");
          sql.addSql("      and");
          sql.addSql("        CMN_BELONGM_HISTORY.BEG_DATE > ALL_SEL_BELONG.ALL_SEL_BEG_DATE");
          sql.addSql("      and");
          sql.addSql("        CMN_BELONGM_HISTORY.BEG_DATE < cast('" + fromDateStr + "' as DATE)");
          sql.addSql("      group by USR_SID");
          sql.addSql("    )");

          sql.addSql(" group by USR_SID");

          pstmt = con.prepareStatement(sql.toSqlString());
          sql.addIntValue(grpSid);
          sql.addIntValue(grpSid);

          log__.info(sql.toLogString());
          sql.setParameter(pstmt);
          rs = pstmt.executeQuery();
          while (rs.next()) {
              ret.add(rs.getInt("USR_SID"));
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
   * <p>開始日付文字列を取得
   * @param frdate 開始日付
   * @return frDateString
   */
  public String getFrDateString(UDate frdate) {

      UDate frYmd = new UDate();
      frYmd.setDate(frdate.getYear(), frdate.getMonth(), frdate.getIntDay());
      frYmd.setZeroHhMmSs();

      return frYmd.getDateStringForSql();
  }

  /**
   * <p>終了日付文字列を取得
   * @param todate 終了日付
   * @return toDateString
   */
  public String getToDateString(UDate todate) {

      UDate toYmd = new UDate();
      toYmd.setDate(todate.getYear(), todate.getMonth(), todate.getIntDay());
      toYmd.setMaxHhMmSs();

      return toYmd.getDateStringForSql();
  }

}