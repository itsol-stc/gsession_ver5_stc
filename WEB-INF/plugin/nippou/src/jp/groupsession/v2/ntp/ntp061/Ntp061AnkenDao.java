package jp.groupsession.v2.ntp.ntp061;

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
import jp.groupsession.v2.usr.UserUtil;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 日報 案件情報の検索を行うDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp061AnkenDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp061AnkenDao.class);

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Ntp061AnkenDao(Connection con) {
        super(con);
    }
    /**
     * <p>Select NTP_ANKEN
     * @param nanSid NAN_SID
     * @param reqMdl RequestModel
     * @return NTP_ANKENModel
     * @throws SQLException SQL実行例外
     */
    public Ntp061AnkenModel select(int nanSid, RequestModel reqMdl) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Ntp061AnkenModel ret = null;
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
            sql.addSql("   NTP_ANKEN.NAN_STATE,");
            sql.addSql("   NTP_ANKEN.NAN_PERMIT_VIEW,");
            sql.addSql("   NTP_ANKEN.NAN_PERMIT_EDIT,");
            sql.addSql("   NTP_ANKEN.NCN_SID,");
            sql.addSql("   NTP_ANKEN.NAN_AUID,");
            sql.addSql("   NTP_ANKEN.NAN_ADATE,");
            sql.addSql("   NTP_ANKEN.NAN_EUID,");
            sql.addSql("   NTP_ANKEN.NAN_EDATE,");
            sql.addSql("   NTP_ANKEN.NAN_MITUMORI_DATE,");
            sql.addSql("   NTP_ANKEN.NAN_JUTYU_DATE,");
            sql.addSql("   ANKEN_PROCESS.NGY_SID,");
            sql.addSql("   CMN_USRM_INF.USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI,");
            sql.addSql("   CMN_USRM.USR_UKO_FLG,");
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
            sql.addSql("   CMN_USRM,");
            sql.addSql("   CMN_USRM_INF");
            sql.addSql(" where ");
            sql.addSql("   NTP_ANKEN.NAN_SID=?");
            sql.addSql("   and");
            sql.addSql("   NTP_ANKEN.NAN_EUID = CMN_USRM_INF.USR_SID");
            sql.addSql("   and");
            sql.addSql("   NTP_ANKEN.NAN_EUID = CMN_USRM.USR_SID");

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
     * <p>Create NTP_SHOHIN Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @param reqMdl RequestModel
     * @return created NtpShohinModel
     * @throws SQLException SQL実行例外
     */
    private Ntp061AnkenModel __getNtpAnkenFromRs(ResultSet rs, RequestModel reqMdl)
    throws SQLException {
        Ntp061AnkenModel bean = new Ntp061AnkenModel();
        bean.setNanSid(rs.getInt("NAN_SID"));
        bean.setNanCode(rs.getString("NAN_CODE"));
        bean.setNanName(rs.getString("NAN_NAME"));
        bean.setNanDetial(rs.getString("NAN_DETIAL"));
        bean.setNanDate(UDate.getInstanceTimestamp(rs.getTimestamp("NAN_DATE")));
        bean.setAcoSid(rs.getInt("ACO_SID"));
        bean.setAbaSid(rs.getInt("ABA_SID"));
        bean.setNgpSid(rs.getInt("NGP_SID"));
        bean.setNtp061NgySid(rs.getInt("NGY_SID"));
        bean.setNanMikomi(rs.getInt("NAN_MIKOMI"));
        bean.setNanKinMitumori(rs.getInt("NAN_KIN_MITUMORI"));
        bean.setNanKinJutyu(rs.getInt("NAN_KIN_JUTYU"));
        bean.setNanSyodan(rs.getInt("NAN_SYODAN"));
        bean.setNanState(rs.getInt("NAN_STATE"));
        bean.setNanPermitView(rs.getInt("NAN_PERMIT_VIEW"));
        bean.setNanPermitEdit(rs.getInt("NAN_PERMIT_EDIT"));
        bean.setNcnSid(rs.getInt("NCN_SID"));
        bean.setNanDate(UDate.getInstanceTimestamp(rs.getTimestamp("NAN_DATE")));
        bean.setNanMitumoriDate(UDate.getInstanceTimestamp(rs.getTimestamp("NAN_MITUMORI_DATE")));
        bean.setNanJutyuDate(UDate.getInstanceTimestamp(rs.getTimestamp("NAN_JUTYU_DATE")));
        bean.setNtp061CompanyCode(rs.getString("ACO_CODE"));
        bean.setNtp061CompanyName(rs.getString("ACO_NAME"));
        String companyBaseName = rs.getString("ABA_NAME");
        String companyBaseType
            = AddressBiz.getCompanyBaseTypeName(rs.getInt("ABA_TYPE"), reqMdl);
        if (!StringUtil.isNullZeroString(companyBaseType)) {
            companyBaseName = companyBaseType + " ： " + companyBaseName;
        }
        bean.setNtp061BaseName(companyBaseName);
        UsrLabelValueBean touroku = new UsrLabelValueBean();
        touroku.setLabel(UserUtil.makeName(
                NullDefault.getString(rs.getString("USI_SEI"), ""),
                NullDefault.getString(rs.getString("USI_MEI"), "")));
        touroku.setValue(String.valueOf(rs.getInt("NAN_EUID")));
        touroku.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
        bean.setNtp061TourokuName(touroku);
        return bean;
    }
}
