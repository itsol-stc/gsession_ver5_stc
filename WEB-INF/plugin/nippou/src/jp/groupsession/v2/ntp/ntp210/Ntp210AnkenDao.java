package jp.groupsession.v2.ntp.ntp210;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.adr.biz.AddressBiz;
import jp.groupsession.v2.cmn.model.RequestModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能]  日報 案件情報ポップアップで使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp210AnkenDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp210AnkenDao.class);

    /**
     * <p>Default Constructor
     */
    public Ntp210AnkenDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Ntp210AnkenDao(Connection con) {
        super(con);
    }
    /**
     * <p>Select NTP_ANKEN
     * @param nanSid NAN_SID
     * @param reqMdl RequestModel
     * @return NTP_ANKENModel
     * @throws SQLException SQL実行例外
     */
    public Ntp210AnkenModel select(int nanSid, RequestModel reqMdl) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Ntp210AnkenModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
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
            sql.addSql("   NTP_ANKEN.NCN_SID,");
            sql.addSql("   NTP_ANKEN.NAN_AUID,");
            sql.addSql("   NTP_ANKEN.NAN_ADATE,");
            sql.addSql("   NTP_ANKEN.NAN_EUID,");
            sql.addSql("   NTP_ANKEN.NAN_EDATE,");
            sql.addSql("   ANKEN_PROCESS.NGY_SID,");
            sql.addSql("   CMN_USRM_INF.USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI,");
            sql.addSql("   ANKEN_COMPANY.ACO_CODE,");
            sql.addSql("   ANKEN_COMPANY.ACO_NAME,");
            sql.addSql("   ANKEN_COMPANY_BASE.ABA_NAME,");
            sql.addSql("   ANKEN_COMPANY_BASE.ABA_TYPE");
            sql.addSql(" from");
            sql.addSql("   NTP_ANKEN");

            sql.addSql("   left join (");
            sql.addSql("     select");
            sql.addSql("       NTP_PROCESS.NGP_SID,");
            sql.addSql("       NTP_PROCESS.NGY_SID");
            sql.addSql("     from");
            sql.addSql("       NTP_PROCESS");
            sql.addSql("   ) ANKEN_PROCESS");
            sql.addSql("   on NTP_ANKEN.NGP_SID = ANKEN_PROCESS.NGP_SID");

            sql.addSql("   left join (");
            sql.addSql("     select");
            sql.addSql("       ADR_COMPANY.ACO_SID,");
            sql.addSql("       ADR_COMPANY.ACO_CODE,");
            sql.addSql("       ADR_COMPANY.ACO_NAME");
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
            sql.addSql("   on NTP_ANKEN.ABA_SID = ANKEN_COMPANY_BASE.ABA_SID,");

            sql.addSql("   CMN_USRM_INF");
            sql.addSql(" where ");
            sql.addSql("   NTP_ANKEN.NAN_SID=?");
            sql.addSql("   and");
            sql.addSql("   NTP_ANKEN.NAN_EUID = CMN_USRM_INF.USR_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nanSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getNtpAnkenFromRs(rs, reqMdl);
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
     * <p>Select NTP_AN_HISTORY
     * @param nahSid NAH_SID
     * @param reqMdl RequestModel
     * @return NTP_ANKENModel
     * @throws SQLException SQL実行例外
     */
    public Ntp210AnkenModel selectFromHistory(
            int nahSid, RequestModel reqMdl) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Ntp210AnkenModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NTP_AN_HISTORY.NAN_SID,");
            sql.addSql("   NTP_AN_HISTORY.NAN_CODE,");
            sql.addSql("   NTP_AN_HISTORY.NAN_NAME,");
            sql.addSql("   NTP_AN_HISTORY.NAN_DETIAL,");
            sql.addSql("   NTP_AN_HISTORY.NAN_DATE,");
            sql.addSql("   NTP_AN_HISTORY.ACO_SID,");
            sql.addSql("   NTP_AN_HISTORY.ABA_SID,");
            sql.addSql("   NTP_AN_HISTORY.NGP_SID,");
            sql.addSql("   NTP_AN_HISTORY.NAN_MIKOMI,");
            sql.addSql("   NTP_AN_HISTORY.NAN_KIN_MITUMORI,");
            sql.addSql("   NTP_AN_HISTORY.NAN_KIN_JUTYU,");
            sql.addSql("   NTP_AN_HISTORY.NAN_SYODAN,");
            sql.addSql("   NTP_AN_HISTORY.NCN_SID,");
            sql.addSql("   NTP_AN_HISTORY.NAN_AUID,");
            sql.addSql("   NTP_AN_HISTORY.NAN_ADATE,");
            sql.addSql("   NTP_AN_HISTORY.NAN_EUID,");
            sql.addSql("   NTP_AN_HISTORY.NAN_EDATE,");
            sql.addSql("   ANKEN_PROCESS.NGY_SID,");
            sql.addSql("   CMN_USRM_INF.USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI,");
            sql.addSql("   ANKEN_COMPANY.ACO_CODE,");
            sql.addSql("   ANKEN_COMPANY.ACO_NAME,");
            sql.addSql("   ANKEN_COMPANY_BASE.ABA_NAME,");
            sql.addSql("   ANKEN_COMPANY_BASE.ABA_TYPE");
            sql.addSql(" from");
            sql.addSql("   NTP_AN_HISTORY");

            sql.addSql("   left join (");
            sql.addSql("     select");
            sql.addSql("       NTP_PROCESS.NGP_SID,");
            sql.addSql("       NTP_PROCESS.NGY_SID");
            sql.addSql("     from");
            sql.addSql("       NTP_PROCESS");
            sql.addSql("   ) ANKEN_PROCESS");
            sql.addSql("   on NTP_AN_HISTORY.NGP_SID = ANKEN_PROCESS.NGP_SID");

            sql.addSql("   left join (");
            sql.addSql("     select");
            sql.addSql("       ADR_COMPANY.ACO_SID,");
            sql.addSql("       ADR_COMPANY.ACO_CODE,");
            sql.addSql("       ADR_COMPANY.ACO_NAME");
            sql.addSql("     from");
            sql.addSql("       ADR_COMPANY");
            sql.addSql("   ) ANKEN_COMPANY");
            sql.addSql("   on NTP_AN_HISTORY.ACO_SID = ANKEN_COMPANY.ACO_SID");

            sql.addSql("   left join (");
            sql.addSql("     select");
            sql.addSql("       ADR_COMPANY_BASE.ABA_SID,");
            sql.addSql("       ADR_COMPANY_BASE.ABA_TYPE,");
            sql.addSql("       ADR_COMPANY_BASE.ABA_NAME");
            sql.addSql("     from");
            sql.addSql("       ADR_COMPANY_BASE");
            sql.addSql("   ) ANKEN_COMPANY_BASE");
            sql.addSql("   on NTP_AN_HISTORY.ABA_SID = ANKEN_COMPANY_BASE.ABA_SID,");

            sql.addSql("   CMN_USRM_INF");
            sql.addSql(" where ");
            sql.addSql("   NTP_AN_HISTORY.NAH_SID=?");
            sql.addSql("   and");
            sql.addSql("   NTP_AN_HISTORY.NAN_EUID = CMN_USRM_INF.USR_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nahSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getNtpAnkenFromRs(rs, reqMdl);
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
     * <p>Create NTP_SHOHIN Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @param reqMdl RequestModel
     * @return created NtpShohinModel
     * @throws SQLException SQL実行例外
     */
    private Ntp210AnkenModel __getNtpAnkenFromRs(ResultSet rs, RequestModel reqMdl)
    throws SQLException {
        Ntp210AnkenModel bean = new Ntp210AnkenModel();
        bean.setNanSid(rs.getInt("NAN_SID"));
        bean.setNanCode(rs.getString("NAN_CODE"));
        bean.setNanName(rs.getString("NAN_NAME"));
        bean.setNanDetial(rs.getString("NAN_DETIAL"));
        bean.setNanDate(UDate.getInstanceTimestamp(rs.getTimestamp("NAN_DATE")));
        bean.setAcoSid(rs.getInt("ACO_SID"));
        bean.setAbaSid(rs.getInt("ABA_SID"));
        bean.setNgpSid(rs.getInt("NGP_SID"));
        bean.setNtp210NgySid(rs.getInt("NGY_SID"));
        bean.setNanMikomi(rs.getInt("NAN_MIKOMI"));
        bean.setNanKinMitumori(rs.getInt("NAN_KIN_MITUMORI"));
        bean.setNanKinJutyu(rs.getInt("NAN_KIN_JUTYU"));
        bean.setNanSyodan(rs.getInt("NAN_SYODAN"));
        bean.setNcnSid(rs.getInt("NCN_SID"));
        bean.setNanDate(UDate.getInstanceTimestamp(rs.getTimestamp("NAN_DATE")));
        bean.setNtp210CompanyCode(rs.getString("ACO_CODE"));
        bean.setNtp210CompanyName(rs.getString("ACO_NAME"));
        String companyBaseName = rs.getString("ABA_NAME");
        String companyBaseType
            = AddressBiz.getCompanyBaseTypeName(rs.getInt("ABA_TYPE"), reqMdl);
        if (!StringUtil.isNullZeroString(companyBaseType)) {
            companyBaseName = companyBaseType + " ： " + companyBaseName;
        }
        bean.setNtp210BaseName(companyBaseName);
        bean.setNtp210TourokuName(NullDefault.getString(rs.getString("USI_SEI"), "")
            + "　" + NullDefault.getString(rs.getString("USI_MEI"), ""));
        return bean;
    }
}
