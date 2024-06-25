package jp.groupsession.v2.api.adress.companybase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.adr.model.AdrCompanyModel;
import jp.groupsession.v2.api.adress.companybase.model.ApiCompanyBaseModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * <br>[機  能] WEB API アドレス帳 拠点情報取得DAO
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiCompanyBaseDao extends AbstractDao {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiCompanyBaseDao.class);
    /**
     * コンストラクタ
     * @param con コネクション
     */
    public ApiCompanyBaseDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] 会社名の先頭一文字の一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return 検索結果
     * @throws SQLException SQL実行時例外
     */
    public List<String> getCompanyInitialList()
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<String> ret = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   ADR_COMPANY.ACO_SINI");
            sql.addSql(" from");
            sql.addSql("   ADR_COMPANY");
            sql.addSql(" group by");
            sql.addSql("   ADR_COMPANY.ACO_SINI");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(rs.getString("ACO_SINI"));
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
     * <br>[機  能] 会社名の先頭一文字の一覧を取得する
     * <br>[解  説] 引数で指定した文字で始まる会社名のみを検索する。
     * <br>[備  考]
     * @param indexLine 行の五音
     * @return 検索結果
     * @throws SQLException SQL実行時例外
     */
    public List<String> getCompanyInitialList(String[] indexLine)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<String> ret = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   ADR_COMPANY.ACO_SINI");
            sql.addSql(" from");
            sql.addSql("   ADR_COMPANY");

            if (indexLine != null && indexLine.length > 0) {
                sql.addSql(" where");
                int i = 0;
                for (String kana : indexLine) {
                    if (i > 0) {
                        sql.addSql(" or");
                    }
                    sql.addSql("   ADR_COMPANY.ACO_SINI=?");
                    sql.addStrValue(kana);
                    i++;
                }

            }

            sql.addSql(" group by");
            sql.addSql("   ADR_COMPANY.ACO_SINI");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(rs.getString("ACO_SINI"));
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
     * <br>[機  能] 会社拠点一覧を取得する
     * <br>[解  説] 拠点情報単位で取得する。
     * <br>[備  考]
     * @param initial 検索対象文字リスト
     * @param start ページ数
     * @param maxCnt 1ページ最大表示件数
     * @return 検索結果
     * @throws SQLException SQL実行時例外
     */
    public List<ApiCompanyBaseModel> getCompanyBaseList(String initial, int start, int maxCnt)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<ApiCompanyBaseModel> ret = new ArrayList<ApiCompanyBaseModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   COMPANY_DATA.ACO_SID as ACO_SID,");
            sql.addSql("   COMPANY_DATA.ACO_CODE as ACO_CODE,");
            sql.addSql("   COMPANY_DATA.ACO_NAME as ACO_NAME,");
            sql.addSql("   COMPANY_DATA.ACO_NAME_KN as ACO_NAME_KN,");
            sql.addSql("   COMPANY_DATA.ACO_URL as ACO_URL,");
            sql.addSql("   COMPANY_DATA.ACO_BIKO as ACO_BIKO,");
            sql.addSql("   COMPANY_DATA.ABA_SID as ABA_SID,");
            sql.addSql("   COMPANY_DATA.ABA_NAME as ABA_NAME,");
            sql.addSql("   COMPANY_DATA.ABA_TYPE as ABA_TYPE,");
            sql.addSql("   COMPANY_DATA.ABA_POSTNO1 as ABA_POSTNO1,");
            sql.addSql("   COMPANY_DATA.ABA_POSTNO2 as ABA_POSTNO2,");
            sql.addSql("   COMPANY_DATA.TDF_SID as TDF_SID,");
            sql.addSql("   CMN_TDFK.TDF_NAME as TDF_NAME,");
            sql.addSql("   COMPANY_DATA.ABA_ADDR1 as ABA_ADDR1,");
            sql.addSql("   COMPANY_DATA.ABA_ADDR2 as ABA_ADDR2,");
            sql.addSql("   COMPANY_DATA.ABA_BIKO as ABA_BIKO ");
            sql.addSql(" from");
            sql.addSql("   (");
            sql.addSql("      select");
            sql.addSql("        ADR_COMPANY.ACO_SID as ACO_SID,");
            sql.addSql("        ADR_COMPANY.ACO_CODE as ACO_CODE,");
            sql.addSql("        ADR_COMPANY.ACO_NAME as ACO_NAME,");
            sql.addSql("        ADR_COMPANY.ACO_NAME_KN as ACO_NAME_KN,");
            sql.addSql("        ADR_COMPANY.ACO_SINI as ACO_SINI,");
            sql.addSql("        ADR_COMPANY.ACO_URL as ACO_URL,");
            sql.addSql("        ADR_COMPANY.ACO_BIKO as ACO_BIKO,");
            sql.addSql("        0 as ABA_SID,");
            sql.addSql("        -1 as ABA_TYPE,");
            sql.addSql("        null as ABA_NAME,");
            sql.addSql("        null AS ABA_POSTNO1,");
            sql.addSql("        null AS ABA_POSTNO2,");
            sql.addSql("        -1 AS TDF_SID,");
            sql.addSql("        null AS ABA_ADDR1,");
            sql.addSql("        null AS ABA_ADDR2,");
            sql.addSql("        null AS ABA_BIKO");
            sql.addSql("       from");
            sql.addSql("        ADR_COMPANY");
            sql.addSql("    union all");
            sql.addSql("      select");
            sql.addSql("        ADR_COMPANY.ACO_SID as ACO_SID,");
            sql.addSql("        ADR_COMPANY.ACO_CODE as ACO_CODE,");
            sql.addSql("        ADR_COMPANY.ACO_NAME as ACO_NAME,");
            sql.addSql("        ADR_COMPANY.ACO_NAME_KN as ACO_NAME_KN,");
            sql.addSql("        ADR_COMPANY.ACO_SINI as ACO_SINI,");
            sql.addSql("        ADR_COMPANY.ACO_URL as ACO_URL,");
            sql.addSql("        ADR_COMPANY.ACO_BIKO as ACO_BIKO,");
            sql.addSql("        ADR_COMPANY_BASE.ABA_SID as ABA_SID,");
            sql.addSql("        ADR_COMPANY_BASE.ABA_TYPE as ABA_TYPE,");
            sql.addSql("        ADR_COMPANY_BASE.ABA_NAME as ABA_NAME,");
            sql.addSql("        ADR_COMPANY_BASE.ABA_POSTNO1 AS ABA_POSTNO1,");
            sql.addSql("        ADR_COMPANY_BASE.ABA_POSTNO2 AS ABA_POSTNO2,");
            sql.addSql("        ADR_COMPANY_BASE.TDF_SID AS TDF_SID,");
            sql.addSql("        ADR_COMPANY_BASE.ABA_ADDR1 AS ABA_ADDR1,");
            sql.addSql("        ADR_COMPANY_BASE.ABA_ADDR2 AS ABA_ADDR2,");
            sql.addSql("        ADR_COMPANY_BASE.ABA_BIKO AS ABA_BIKO");
            sql.addSql("      from");
            sql.addSql("        ADR_COMPANY,");
            sql.addSql("        ADR_COMPANY_BASE");
            sql.addSql("      where");
            sql.addSql("        ADR_COMPANY.ACO_SID = ADR_COMPANY_BASE.ACO_SID");
            sql.addSql("   ) COMPANY_DATA");
            sql.addSql("   left join");
            sql.addSql("      CMN_TDFK");
            sql.addSql("    on");
            sql.addSql("      COMPANY_DATA.TDF_SID = CMN_TDFK.TDF_SID");

            if (!StringUtil.isNullZeroString(initial)) {
                String[] initials = initial.split(",");

                sql.addSql(" where");
                sql.addSql("   ACO_SINI in (");
                int i = 0;
                for (String kana : initials) {
                    if (i > 0) {
                        sql.addSql(" ,");
                    }
                    sql.addSql("?");
                    sql.addStrValue(kana);
                    i++;
                }

                sql.addSql(")");

            }

            sql.addSql(" order by");
            sql.addSql("   ACO_NAME_KN asc,");
            sql.addSql("   ACO_SID asc,");
            sql.addSql("   ABA_TYPE asc,");
            sql.addSql("   ABA_SID asc");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            rs.absolute(start);

            Map<Integer, AdrCompanyModel> companyMap = new HashMap<Integer, AdrCompanyModel>();
            ApiCompanyBaseModel model = null;
            for (int i = 0; rs.next() && i < maxCnt; i++) {

                model = new ApiCompanyBaseModel();
                Integer acoSid = new Integer(rs.getInt("ACO_SID"));
                if (companyMap.containsKey(acoSid)) {
                    model.setAco(companyMap.get(acoSid));
                } else {
                    AdrCompanyModel aco = new AdrCompanyModel();
                    aco.setAcoSid(rs.getInt("ACO_SID"));
                    aco.setAcoName(rs.getString("ACO_NAME"));
                    aco.setAcoNameKn(rs.getString("ACO_NAME_KN"));
                    aco.setAcoCode(rs.getString("ACO_CODE"));
                    aco.setAcoBiko(rs.getString("ACO_BIKO"));
                    aco.setAcoUrl(rs.getString("ACO_URL"));
                    companyMap.put(acoSid, aco);
                    model.setAco(aco);
                }
                model.setAbaSid(rs.getInt("ABA_SID"));
                model.setAbaType(rs.getInt("ABA_TYPE"));
                model.setAbaName(rs.getString("ABA_NAME"));
                model.setAbaPostno1(rs.getString("ABA_POSTNO1"));
                model.setAbaPostno2(rs.getString("ABA_POSTNO2"));
                model.setTdfSid(rs.getInt("TDF_SID"));
                model.setTdfkName(rs.getString("TDF_NAME"));
                model.setAbaAddr1(rs.getString("ABA_ADDR1"));
                model.setAbaAddr2(rs.getString("ABA_ADDR2"));
                model.setAbaBiko(rs.getString("ABA_BIKO"));

                ret.add(model);
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
     * <br>[機  能] 会社一覧を取得する
     * <br>[解  説] 拠点情報単位で取得する。
     * <br>[備  考]
     * @param initial 検索対象頭文字
     * @return 件数
     * @throws SQLException SQL実行時例外
     */
    public int countCompanyList(String initial)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int count = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select count(*) as CNT");
            sql.addSql(" from");
            sql.addSql("   (");
            sql.addSql("      select");
            sql.addSql("        ADR_COMPANY.ACO_SID as ACO_SID,");
            sql.addSql("        ADR_COMPANY.ACO_NAME as ACO_NAME,");
            sql.addSql("        ADR_COMPANY.ACO_NAME_KN as ACO_NAME_KN,");
            sql.addSql("        ADR_COMPANY.ACO_SINI as ACO_SINI,");
            sql.addSql("        0 as ABA_SID,");
            sql.addSql("        -1 as ABA_TYPE,");
            sql.addSql("        null as ABA_NAME");
            sql.addSql("       from");
            sql.addSql("        ADR_COMPANY");
            sql.addSql("    union all");
            sql.addSql("      select");
            sql.addSql("        ADR_COMPANY.ACO_SID as ACO_SID,");
            sql.addSql("        ADR_COMPANY.ACO_NAME as ACO_NAME,");
            sql.addSql("        ADR_COMPANY.ACO_NAME_KN as ACO_NAME_KN,");
            sql.addSql("        ADR_COMPANY.ACO_SINI as ACO_SINI,");
            sql.addSql("        ADR_COMPANY_BASE.ABA_SID as ABA_SID,");
            sql.addSql("        ADR_COMPANY_BASE.ABA_TYPE as ABA_TYPE,");
            sql.addSql("        ADR_COMPANY_BASE.ABA_NAME as ABA_NAME");
            sql.addSql("      from");
            sql.addSql("        ADR_COMPANY,");
            sql.addSql("        ADR_COMPANY_BASE");
            sql.addSql("      where");
            sql.addSql("        ADR_COMPANY.ACO_SID = ADR_COMPANY_BASE.ACO_SID");
            sql.addSql("   ) COMPANY_DATA");

            if (!StringUtil.isNullZeroString(initial)) {
                String[] initials = initial.split(",");

                sql.addSql(" where");
                sql.addSql("   COMPANY_DATA.ACO_SINI in (");
                int i = 0;
                for (String kana : initials) {
                    if (i > 0) {
                        sql.addSql(" ,");
                    }
                    sql.addSql("?");
                    sql.addStrValue(kana);
                    i++;
                }

                sql.addSql(")");

            }

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

}
