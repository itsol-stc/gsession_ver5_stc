package jp.groupsession.v2.ntp.ntp040;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.UserSearchDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.UserSearchModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.dao.NtpDataDao;
import jp.groupsession.v2.ntp.model.NtpAnkenModel;
import jp.groupsession.v2.ntp.model.NtpCommentModel;
import jp.groupsession.v2.ntp.model.NtpDataModel;
import jp.groupsession.v2.ntp.ntp010.Ntp010Biz;
import jp.groupsession.v2.ntp.ntp040.model.Ntp040AddressModel;
import jp.groupsession.v2.ntp.ntp040.model.Ntp040CommentModel;
import jp.groupsession.v2.ntp.ntp040.model.Ntp040ContactModel;
import jp.groupsession.v2.ntp.ntp040.model.Ntp040DBCompanyBaseModel;
import jp.groupsession.v2.ntp.ntp040.model.Ntp040DBCompanyModel;
import jp.groupsession.v2.ntp.ntp040.model.Ntp040DataModel;
import jp.groupsession.v2.ntp.ntp040.model.Ntp040DspCommentModel;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] 日報 日報登録画面で使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp040Dao extends AbstractDao {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp040Dao.class);

    /**
     * <p>
     * デフォルトコンストラクタ
     */
    public Ntp040Dao() {
    }

    /**
     * <p>
     * デフォルトコンストラクタ
     *
     * @param con
     *            DBコネクション
     */
    public Ntp040Dao(Connection con) {
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
     * 過去3ヶ月の日報で登録された会社SIDを取得する
     * @param usrSid ユーザSID
     * @return List in NTP_DATAModel
     * @throws SQLException SQL実行例外
     */
    public List<Ntp040AddressModel> getNtpAdrHistory(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Ntp040AddressModel adrMdl = null;
        List<Ntp040AddressModel> ret = new ArrayList<Ntp040AddressModel>();
        UDate now = new UDate();
        UDate oldDate = now.cloneUDate();
        oldDate.addMonth(-3);

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();


            sql.addSql("  select ");
            sql.addSql("    distinct ");
            sql.addSql("    NTP_DATA_COM.ACO_SID  as ACO_SID, ");
            sql.addSql("    NTP_DATA_COM.ABA_SID  as ABA_SID ");
            sql.addSql("  from ");
            sql.addSql("    (select ");
            sql.addSql("      NTP_DATA.ACO_SID  as ACO_SID, ");
            sql.addSql("      NTP_DATA.ABA_SID  as ABA_SID, ");
            sql.addSql("      NTP_DATA.NIP_EDATE as NIP_EDATE ");
            sql.addSql("    from ");
            sql.addSql("      NTP_DATA ");
            sql.addSql("    where ");
            sql.addSql("      USR_SID=? ");
            sql.addSql("    and ");
            String frStr = oldDate.getDateStringForSql();
            String toStr = now.getDateStringForSql();
            sql.addSql("     (");
            sql.addSql("       NIP_DATE ");
            sql.addSql("       between cast('" + frStr + "' as DATE) ");
            sql.addSql("       and cast('" + toStr + "' as DATE) ");
            sql.addSql("     )");
            sql.addSql("    and ");
            sql.addSql("      ACO_SID > 0 ");
            sql.addSql("    group by ACO_SID, ABA_SID, NIP_EDATE");
            sql.addSql("    order by NIP_EDATE DESC ");
            sql.addSql("     ) NTP_DATA_COM");

            sql.addIntValue(usrSid);

            pstmt = con.prepareStatement(sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                adrMdl = new Ntp040AddressModel();
                adrMdl.setCompanySid(rs.getInt("ACO_SID"));

                int abaSid = 0;
                if (rs.getInt("ABA_SID") > 0) {
                    abaSid = rs.getInt("ABA_SID");
                }

                adrMdl.setCompanyBaseSid(abaSid);
                ret.add(adrMdl);
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
     * 過去3ヶ月の日報で登録された案件SIDを10件取得する
     * @param usrSid ユーザSID
     * @param isAdmin 管理者権限があるか
     * @return List in NTP_DATAModel
     * @throws SQLException SQL実行例外
     */
    public List<Integer> getNtpAnkenHistory(
            int usrSid, boolean isAdmin) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Integer> ret = new ArrayList<Integer>();
        UDate now = new UDate();
        UDate oldDate = now.cloneUDate();
        oldDate.addMonth(-3);

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql("  select ");
            sql.addSql("    NTP_NAN_DATA.DATA_NAN_SID as NAN_SID");
            sql.addSql("  from (");
            sql.addSql("    select ");
            sql.addSql("      NTP_DATA.NAN_SID    as DATA_NAN_SID,");
            sql.addSql("      NTP_DATA.NIP_EDATE  as DATA_NIP_EDATE");
            sql.addSql("    from ");
            sql.addSql("      NTP_DATA ");
            sql.addSql("      ,NTP_ANKEN");
            sql.addSql("    where ");
            sql.addSql("      NTP_DATA.USR_SID=? ");
            sql.addIntValue(usrSid);
            sql.addSql("    and ");
            String frStr = oldDate.getDateStringForSql();
            String toStr = now.getDateStringForSql();
            sql.addSql("     (");
            sql.addSql("       NTP_DATA.NIP_DATE ");
            sql.addSql("       between cast('" + frStr + "' as DATE) ");
            sql.addSql("       and cast('" + toStr + "' as DATE) ");
            sql.addSql("     )");
            sql.addSql("    and ");
            sql.addSql("      NTP_DATA.NAN_SID > 0 ");
            sql.addSql("    and NTP_DATA.NAN_SID = NTP_ANKEN.NAN_SID ");
            if (!isAdmin) {
                sql.addSql(" and (");
                sql.addSql(" NTP_ANKEN.NAN_PERMIT_EDIT = ?");
                sql.addIntValue(GSConstNippou.NAP_KBN_ALL);
                sql.addSql("   or ");
                sql.addSql("     exists (");
                sql.addSql("       select NAN_SID from NTP_AN_MEMBER");
                sql.addSql("       where NTP_AN_MEMBER.USR_SID = ?");
                sql.addIntValue(usrSid);
                sql.addSql("       and NTP_AN_MEMBER.NAN_SID = NTP_ANKEN.NAN_SID");
                sql.addSql("     )");
                sql.addSql("   or");
                sql.addSql("     (");
                sql.addSql(" NTP_ANKEN.NAN_PERMIT_EDIT = ?");
                sql.addIntValue(GSConstNippou.NAP_KBN_USERGROUP);
                sql.addSql("     and");
                sql.addSql("       exists (");
                sql.addSql("         select NAN_SID from NTP_ANKEN_PERMIT");
                sql.addSql("         where");
                sql.addSql("           NTP_ANKEN_PERMIT.GRP_SID in (");
                sql.addSql("             select GRP_SID from CMN_BELONGM");
                sql.addSql("             where CMN_BELONGM.USR_SID = ?");
                sql.addIntValue(usrSid);
                sql.addSql("           )");
                sql.addSql("           and NTP_ANKEN_PERMIT.NAN_SID = NTP_ANKEN.NAN_SID");
                sql.addSql("           and NTP_ANKEN_PERMIT.NAP_KBN = ?");
                sql.addIntValue(GSConst.SP_AUTH_EDIT);
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
                sql.addIntValue(usrSid);
                sql.addSql("           and NTP_ANKEN_PERMIT.NAN_SID = NTP_ANKEN.NAN_SID");
                sql.addSql("           and NTP_ANKEN_PERMIT.NAP_KBN = ?");
                sql.addIntValue(GSConst.SP_AUTH_EDIT);
                sql.addSql("       )");
                sql.addSql("     )");
                sql.addSql("   )");
            }

            sql.addSql("    group by NTP_DATA.NAN_SID, NTP_DATA.NIP_EDATE");
            sql.addSql("    order by NTP_DATA.NIP_EDATE DESC ");
            sql.addSql("    limit 10 offset 0 ");
            sql.addSql(" ) NTP_NAN_DATA");
            sql.addSql("  group by DATA_NAN_SID");


            pstmt = con.prepareStatement(sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
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
     * 過去3ヶ月の日報で登録された案件SIDを取得する
     * @param usrSid ユーザSID
     * @param isAdmin 管理者権限があるか
     * @return List in NTP_DATAModel
     * @throws SQLException SQL実行例外
     */
    public List<NtpAnkenModel> getNtpAnkenHistory2(
            int usrSid, boolean isAdmin) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        NtpAnkenModel mdl = null;
        List<NtpAnkenModel> ret = new ArrayList<NtpAnkenModel>();
        UDate now = new UDate();
        UDate oldDate = now.cloneUDate();
        oldDate.addMonth(-3);

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql("  select ");
            sql.addSql("    distinct ");
            sql.addSql("    NTP_NAN_DATA.DATA_NAN_SID  as NAN_SID,");
            sql.addSql("    NTP_ANKEN.NAN_CODE as NAN_CODE,");
            sql.addSql("    NTP_ANKEN.NAN_NAME as NAN_NAME");
            sql.addSql("  from (");
            sql.addSql("    select ");
            sql.addSql("      NTP_DATA.NAN_SID    as DATA_NAN_SID,");
            sql.addSql("      NTP_DATA.NIP_EDATE  as DATA_NIP_EDATE");
            sql.addSql("    from ");
            sql.addSql("      NTP_DATA ");
            sql.addSql("    where ");
            sql.addSql("      USR_SID=? ");
            sql.addIntValue(usrSid);
            sql.addSql("    and ");
            String frStr = oldDate.getDateStringForSql();
            String toStr = now.getDateStringForSql();
            sql.addSql("     (");
            sql.addSql("       NIP_DATE ");
            sql.addSql("       between cast('" + frStr + "' as DATE) ");
            sql.addSql("       and cast('" + toStr + "' as DATE) ");
            sql.addSql("     )");
            sql.addSql("    and ");
            sql.addSql("      NAN_SID > 0 ");
            sql.addSql("    group by NAN_SID, NIP_EDATE");
            sql.addSql("    order by NIP_EDATE DESC ");
            sql.addSql(" ) NTP_NAN_DATA");
            sql.addSql(" LEFT JOIN NTP_ANKEN ON NTP_NAN_DATA.DATA_NAN_SID = NTP_ANKEN.NAN_SID");
            if (!isAdmin) {
                sql.addSql(" where (");
                sql.addSql(" NTP_ANKEN.NAN_PERMIT_EDIT = ?");
                sql.addIntValue(GSConstNippou.NAP_KBN_ALL);
                sql.addSql("   or ");
                sql.addSql("     exists (");
                sql.addSql("       select NAN_SID from NTP_AN_MEMBER");
                sql.addSql("       where NTP_AN_MEMBER.USR_SID = ?");
                sql.addIntValue(usrSid);
                sql.addSql("       and NTP_AN_MEMBER.NAN_SID = NTP_ANKEN.NAN_SID");
                sql.addSql("     )");
                sql.addSql("   or");
                sql.addSql("     (");
                sql.addSql(" NTP_ANKEN.NAN_PERMIT_EDIT = ?");
                sql.addIntValue(GSConstNippou.NAP_KBN_USERGROUP);
                sql.addSql("     and");
                sql.addSql("       exists (");
                sql.addSql("         select NAN_SID from NTP_ANKEN_PERMIT");
                sql.addSql("         where");
                sql.addSql("           NTP_ANKEN_PERMIT.GRP_SID in (");
                sql.addSql("             select GRP_SID from CMN_BELONGM");
                sql.addSql("             where CMN_BELONGM.USR_SID = ?");
                sql.addIntValue(usrSid);
                sql.addSql("           )");
                sql.addSql("           and NTP_ANKEN_PERMIT.NAN_SID = NTP_ANKEN.NAN_SID");
                sql.addSql("           and NTP_ANKEN_PERMIT.NAP_KBN = ?");
                sql.addIntValue(GSConst.SP_AUTH_EDIT);
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
                sql.addIntValue(usrSid);
                sql.addSql("           and NTP_ANKEN_PERMIT.NAN_SID = NTP_ANKEN.NAN_SID");
                sql.addSql("           and NTP_ANKEN_PERMIT.NAP_KBN = ?");
                sql.addIntValue(GSConst.SP_AUTH_EDIT);
                sql.addSql("       )");
                sql.addSql("     )");
                sql.addSql("   )");
            }
            sql.addSql("  group by NTP_NAN_DATA.DATA_NAN_SID, NAN_CODE, NAN_NAME");

            pstmt = con.prepareStatement(sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                mdl = new NtpAnkenModel();
                mdl.setNanSid(rs.getInt("NAN_SID"));
                mdl.setNanCode(rs.getString("NAN_CODE"));
                mdl.setNanName(rs.getString("NAN_NAME"));
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
     * <br>[機  能] 指定したスケジュールのコンタクト履歴情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param scdSid スケジュールSID
     * @throws SQLException SQL実行時例外
     */
    public void deleteScheduleContact(Connection con, int scdSid)
    throws SQLException {

        PreparedStatement pstmt = null;

        try {
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   ADR_CONTACT");
            sql.addSql(" where");
            sql.addSql("   ADC_SID in (");
            sql.addSql("     select ADC_SID from SCH_ADDRESS");
            sql.addSql("     where");
            sql.addSql("       SCD_SID = ?");
            sql.addSql("     and");
            sql.addSql("       ADC_SID not in (");
            sql.addSql("         select ADC_SID from SCH_ADDRESS");
            sql.addSql("         where SCD_SID <> ?");
            sql.addSql("       )");
            sql.addSql("   )");

            sql.addIntValue(scdSid);
            sql.addIntValue(scdSid);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] スケジュールSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param sceSid スケジュール拡張SID
     * @return スケジュールSID
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getScdSidList(int sceSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Integer> scdSidList = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SCD_SID");
            sql.addSql(" from");
            sql.addSql("   SCH_DATA");
            sql.addSql(" where ");
            sql.addSql("   SCE_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sceSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                scdSidList.add(rs.getInt("SCD_SID"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return scdSidList;
    }

    /**
     * <br>[機  能] コンタクト履歴を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param bean コンタクト履歴情報
     * @throws SQLException SQL実行時例外
     */
    public void insertContact(Ntp040ContactModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" ADR_CONTACT(");
            sql.addSql("   ADC_SID,");
            sql.addSql("   ADR_SID,");
            sql.addSql("   ADC_TITLE,");
            sql.addSql("   ADC_TYPE,");
            sql.addSql("   ADC_CTTIME,");
            sql.addSql("   ADC_CTTIME_TO,");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   ADC_BIKO,");
            sql.addSql("   ADC_AUID,");
            sql.addSql("   ADC_ADATE,");
            sql.addSql("   ADC_EUID,");
            sql.addSql("   ADC_EDATE,");
            sql.addSql("   ADC_GRP_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getAdcSid());
            sql.addIntValue(bean.getAdrSid());
            sql.addStrValue(bean.getAdcTitle());
            sql.addIntValue(bean.getAdcType());
            sql.addDateValue(bean.getAdcCttime());
            sql.addDateValue(bean.getAdcCttimeTo());
            sql.addIntValue(bean.getPrjSid());
            sql.addStrValue(bean.getAdcBiko());
            sql.addIntValue(bean.getAdcAuid());
            sql.addDateValue(bean.getAdcAdate());
            sql.addIntValue(bean.getAdcEuid());
            sql.addDateValue(bean.getAdcEdate());
            sql.addIntValue(bean.getAdcGrpSid());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] 指定したスケジュール拡張情報に関するコンタクト履歴が登録されているかを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param sceSid スケジュール拡張SID
     * @return true:登録 false:未登録
     * @throws SQLException SQL実行時例外
     */
    public boolean isExistAdrContact(int sceSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean result = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(SCH_ADDRESS.*) as CNT");
            sql.addSql(" from ");
            sql.addSql("   SCH_DATA,");
            sql.addSql("   SCH_ADDRESS,");
            sql.addSql("   SCH_EXADDRESS");
            sql.addSql(" where ");
            sql.addSql("   SCH_EXADDRESS.SCE_SID = ?");
            sql.addSql(" and ");
            sql.addSql("   SCH_ADDRESS.SCD_SID = SCH_DATA.SCD_SID");
            sql.addSql(" and ");
            sql.addSql("   SCH_EXADDRESS.SCE_SID = SCH_DATA.SCE_SID");
            sql.addSql(" and ");
            sql.addSql("   SCH_ADDRESS.ADC_SID > 0");
            sql.addIntValue(sceSid);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                result = rs.getInt("CNT") > 0;
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return result;
    }
    /**
    *
    * <br>[機  能] 表示用コメントMapを取得
    * <br>[解  説]
    * <br>[備  考]
    * @param reqMdl リクエストモデル
    * @param nipSidSet 日報SIDセット
    * @param eauthMap 編集権限Map
    * @param usrMap 読み込み済みユーザ情報Map
    * @return Map
    * @throws SQLException SQL実行時例外
    */
   public HashMap<Integer, List<Ntp040CommentModel>> getNpcMap(RequestModel reqMdl,
           Collection<Integer> nipSidSet, Map<Integer, Integer> eauthMap,
           HashMap<Integer, UserSearchModel> usrMap) throws SQLException {
       PreparedStatement pstmt = null;
       ResultSet rs = null;
       Connection con = null;
       HashMap<Integer, List<Ntp040CommentModel>> ret = new HashMap<>();
       con = getCon();
       if (nipSidSet == null || nipSidSet.size() <= 0) {
           return ret;
       }
       List<Ntp040CommentModel> afterConvComm = new ArrayList<>();
       Set<Integer> addUsrSid = new HashSet<>();
       try {
           //SQL文
           SqlBuffer sql = new SqlBuffer();
           sql.addSql(" select");
           sql.addSql("   NPC_SID,");
           sql.addSql("   NIP_SID,");
           sql.addSql("   USR_SID,");
           sql.addSql("   NPC_COMMENT,");
           sql.addSql("   NPC_VIEW_KBN,");
           sql.addSql("   NPC_AUID,");
           sql.addSql("   NPC_ADATE,");
           sql.addSql("   NPC_EUID,");
           sql.addSql("   NPC_EDATE");
           sql.addSql(" from");
           sql.addSql("   NTP_COMMENT");
           sql.addSql(" where ");
           int i = 0;
           sql.addSql("   (NIP_SID in (");
           for (Integer sid : nipSidSet) {
               if (sid == null) {
                   continue;
               }
               if (i > 0 && i % 1000 == 0) {
                   sql.addSql("  ) or NIP_SID in (");
               }
               if (i % 1000 != 0) {
                   sql.addSql("  ,");
               }
               sql.addSql(String.valueOf(sid));
               i++;
           }
           sql.addSql("  ))");
           sql.addSql(" order by NPC_EDATE ASC");

           pstmt = con.prepareStatement(sql.toSqlString());

           log__.info(sql.toLogString());
           sql.setParameter(pstmt);
           rs = pstmt.executeQuery();


           while (rs.next()) {
               int nipSid = rs.getInt("NIP_SID");
               List<Ntp040CommentModel> list;
               if (ret.containsKey(nipSid)) {
                   list = ret.get(nipSid);
               } else {
                   list = new ArrayList<>();
                   ret.put(nipSid, list);
               }

               //コメント情報を取得
               NtpCommentModel npcMdl = new NtpCommentModel();
               npcMdl = __getNtpCommentFromRs(rs);


               //データ形成
               Ntp040CommentModel ntpCmtMdl = new Ntp040CommentModel();
               ntpCmtMdl.setNtp040CommentMdl(npcMdl);


               //表示用日付
               ntpCmtMdl.setNtp040CommentDate(
                   UDateUtil.getYymdJ(npcMdl.getNpcAdate(), reqMdl)
                   + "("
                   + npcMdl.getNpcAdate().getStrWeekJ(reqMdl)
                   + ")"
                   + " "
                   + UDateUtil.getSeparateHMJ(npcMdl.getNpcAdate(), reqMdl));


               //ユーザ情報を取得
               CmnUsrmInfModel uInfMdl;
               if (usrMap.containsKey(npcMdl.getUsrSid())) {
                   uInfMdl = usrMap.get(npcMdl.getUsrSid());
                   ntpCmtMdl.setNtp040UsrInfMdl(uInfMdl);
               } else {
                   uInfMdl = new CmnUsrmInfModel();
                   int usrSid = npcMdl.getUsrSid();
                   ntpCmtMdl.setNtp040UsrInfMdl(uInfMdl);
                   uInfMdl.setUsrSid(usrSid);
                   afterConvComm.add(ntpCmtMdl);
                   addUsrSid.add(usrSid);
               }
               int authkbn = 0;
               //削除フラグ
               if (eauthMap.get(nipSid) == GSConstNippou.AUTH_NOT_ADD_EDIT) {
                   if (uInfMdl.getUsrSid() == reqMdl.getSmodel().getUsrsid()) {
                       authkbn = 1;
                   }
               } else {
                   authkbn = 1;
               }

               ntpCmtMdl.setNtp040CommentDelFlg(authkbn);


               list.add(ntpCmtMdl);
           }
       } catch (SQLException e) {
           throw e;
       } finally {
           JDBCUtil.closeResultSet(rs);
           JDBCUtil.closeStatement(pstmt);
       }
       if (afterConvComm.size() > 0) {
           UserSearchDao uDao = new UserSearchDao(con);
           List<UserSearchModel> usrList = uDao.getUsersInfoJtkb(new ArrayList<>(addUsrSid),
                   GSConstUser.USER_SORT_YKSK, GSConst.ORDER_KEY_ASC, -1, -1);
           for (UserSearchModel user : usrList) {
               usrMap.put(user.getUsrSid(), user);
           }
           for (Ntp040CommentModel ntpCmtMdl : afterConvComm) {
               CmnUsrmInfModel uInfMdl = usrMap.get(
                       ntpCmtMdl.getNtp040UsrInfMdl().getUsrSid());
               ntpCmtMdl.setNtp040UsrInfMdl(uInfMdl);
           }
       }
       return ret;
   }

    /**
     * <p>Select NTP_COMMENT
     * @param reqMdl リクエストモデル
     * @param nipSid NIP_SID
     * @param authFlg 管理者区分 0：なし 1:あり
     * @return NTP_COMMENTModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Ntp040CommentModel> getNpcList(
                        RequestModel reqMdl, int nipSid,
                        int authFlg) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        NtpCommentModel npcMdl = null;
        CmnUsrmInfModel uInfMdl = null;
        Ntp040CommentModel ntpCmtMdl = null;
        ArrayList<Ntp040CommentModel> ret = new ArrayList<Ntp040CommentModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NTP_COMMENT.NPC_SID as NPC_SID,");
            sql.addSql("   NTP_COMMENT.NIP_SID as NIP_SID,");
            sql.addSql("   NTP_COMMENT.USR_SID as USR_SID,");
            sql.addSql("   NTP_COMMENT.NPC_COMMENT as NPC_COMMENT,");
            sql.addSql("   NTP_COMMENT.NPC_VIEW_KBN as NPC_VIEW_KBN,");
            sql.addSql("   NTP_COMMENT.NPC_AUID as NPC_AUID,");
            sql.addSql("   NTP_COMMENT.NPC_ADATE as NPC_ADATE,");
            sql.addSql("   NTP_COMMENT.NPC_EUID as NPC_EUID,");
            sql.addSql("   NTP_COMMENT.NPC_EDATE as NPC_EDATE,");
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
            sql.addSql("   CMN_USRM_INF.USI_YAKUSYOKU as USI_YAKUSYOKU,");
            sql.addSql("   CMN_USRM_INF.USI_SEIBETU as USI_SEIBETU,");
            sql.addSql("   CMN_USRM_INF.USI_ENTRANCE_DATE as USI_ENTRANCE_DATE,");
            sql.addSql("   CMN_USRM_INF.USI_SORTKEY1 as USI_SORTKEY1,");
            sql.addSql("   CMN_USRM_INF.USI_SORTKEY2 as USI_SORTKEY2,");
            sql.addSql("   CMN_USRM_INF.POS_SID as POS_SID,");
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
            sql.addSql("   CMN_USRM_INF.USI_MBL_USE as USI_MBL_USE,");
            sql.addSql("   CMN_USRM_INF.USI_LTLGIN as USI_LTLGIN,");
            sql.addSql("   CMN_USRM_INF.USI_AUID as USI_AUID,");
            sql.addSql("   CMN_USRM_INF.USI_ADATE as USI_ADATE,");
            sql.addSql("   CMN_USRM_INF.USI_EUID as USI_EUID,");
            sql.addSql("   CMN_USRM_INF.USI_EDATE as USI_EDATE,");
            sql.addSql("   CMN_USRM_INF.USI_NUM_CONT as USI_NUM_CONT,");
            sql.addSql("   CMN_USRM_INF.USI_NUM_AUTADD as USI_NUM_AUTADD,");
            sql.addSql("   CMN_USRM.USR_UKO_FLG as USR_UKO_FLG,");
            sql.addSql("   CMN_USRM.USR_JKBN as USR_JKBN");
            sql.addSql(" from");
            sql.addSql("   NTP_COMMENT,");
            sql.addSql("   CMN_USRM_INF,");
            sql.addSql("   CMN_USRM");
            sql.addSql(" where ");
            sql.addSql("   NTP_COMMENT.NIP_SID=?");
            sql.addSql("   and NTP_COMMENT.USR_SID=CMN_USRM_INF.USR_SID");
            sql.addSql("   and NTP_COMMENT.USR_SID=CMN_USRM.USR_SID");

            sql.addSql(" order by NPC_EDATE ASC");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nipSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();


            while (rs.next()) {

                int authkbn = authFlg;

                //コメント情報を取得
                npcMdl = new NtpCommentModel();
                npcMdl = __getNtpCommentFromRs(rs);

                //ユーザ情報を取得
                uInfMdl = __getCmnUsrmInfFromRs(rs);

                //データ形成
                ntpCmtMdl = new Ntp040CommentModel();
                ntpCmtMdl.setNtp040CommentMdl(npcMdl);
                ntpCmtMdl.setNtp040UsrInfMdl(uInfMdl);

                //表示用日付
                ntpCmtMdl.setNtp040CommentDate(
                    UDateUtil.getYymdJ(npcMdl.getNpcAdate(), reqMdl)
                    + "("
                    + npcMdl.getNpcAdate().getStrWeekJ(reqMdl)
                    + ")"
                    + " "
                    + UDateUtil.getSeparateHMJ(npcMdl.getNpcAdate(), reqMdl));


                //セッション情報を取得
                HttpSession session = reqMdl.getSession();
                BaseUserModel usModel =
                    (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
                if (authkbn == 0) {
                    if (uInfMdl.getUsrSid() == usModel.getUsrsid()) {
                        authkbn = 1;
                    }
                }

                //削除フラグ
                ntpCmtMdl.setNtp040CommentDelFlg(authkbn);

                ret.add(ntpCmtMdl);
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
     * <p>表示用コメントリスト取得
     * @param reqMdl RequestModel
     * @param nipSid NIP_SID
     * @return NTP_COMMENTModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Ntp040DspCommentModel> getDspNpcList(
                      RequestModel reqMdl, int nipSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        NtpCommentModel npcMdl = null;
        CmnUsrmInfModel uInfMdl = null;
        Ntp040DspCommentModel ntpDspCmtMdl = null;
        ArrayList<Ntp040DspCommentModel> ret = new ArrayList<Ntp040DspCommentModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select");
            sql.addSql("   NTP_COMMENT.NPC_SID as NPC_SID,");
            sql.addSql("   NTP_COMMENT.NIP_SID as NIP_SID,");
            sql.addSql("   NTP_COMMENT.USR_SID as USR_SID,");
            sql.addSql("   NTP_COMMENT.NPC_COMMENT as NPC_COMMENT,");
            sql.addSql("   NTP_COMMENT.NPC_VIEW_KBN as NPC_VIEW_KBN,");
            sql.addSql("   NTP_COMMENT.NPC_AUID as NPC_AUID,");
            sql.addSql("   NTP_COMMENT.NPC_ADATE as NPC_ADATE,");
            sql.addSql("   NTP_COMMENT.NPC_EUID as NPC_EUID,");
            sql.addSql("   NTP_COMMENT.NPC_EDATE as NPC_EDATE,");
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
            sql.addSql("   CMN_USRM_INF.USI_YAKUSYOKU as USI_YAKUSYOKU,");
            sql.addSql("   CMN_USRM_INF.USI_SEIBETU as USI_SEIBETU,");
            sql.addSql("   CMN_USRM_INF.USI_ENTRANCE_DATE as USI_ENTRANCE_DATE,");
            sql.addSql("   CMN_USRM_INF.USI_SORTKEY1 as USI_SORTKEY1,");
            sql.addSql("   CMN_USRM_INF.USI_SORTKEY2 as USI_SORTKEY2,");
            sql.addSql("   CMN_USRM_INF.POS_SID as POS_SID,");
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
            sql.addSql("   CMN_USRM_INF.USI_MBL_USE as USI_MBL_USE,");
            sql.addSql("   CMN_USRM_INF.USI_LTLGIN as USI_LTLGIN,");
            sql.addSql("   CMN_USRM_INF.USI_AUID as USI_AUID,");
            sql.addSql("   CMN_USRM_INF.USI_ADATE as USI_ADATE,");
            sql.addSql("   CMN_USRM_INF.USI_EUID as USI_EUID,");
            sql.addSql("   CMN_USRM_INF.USI_EDATE as USI_EDATE,");
            sql.addSql("   CMN_USRM_INF.USI_NUM_CONT as USI_NUM_CONT,");
            sql.addSql("   CMN_USRM_INF.USI_NUM_AUTADD as USI_NUM_AUTADD,");
            sql.addSql("   CMN_USRM.USR_UKO_FLG as USR_UKO_FLG,");
            sql.addSql("   CMN_USRM.USR_JKBN as USR_JKBN");
            sql.addSql(" from");
            sql.addSql("   NTP_COMMENT,");
            sql.addSql("   CMN_USRM_INF,");
            sql.addSql("   CMN_USRM");
            sql.addSql(" where ");
            sql.addSql("   NTP_COMMENT.NIP_SID=?");
            sql.addSql("   and NTP_COMMENT.USR_SID=CMN_USRM_INF.USR_SID");
            sql.addSql("   and NTP_COMMENT.USR_SID=CMN_USRM.USR_SID");
            sql.addSql(" order by NPC_EDATE ASC");


            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nipSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            //日報情報取得
            NtpDataDao ntpdao = new NtpDataDao(con);
            NtpDataModel ntpDataMdl = new NtpDataModel();
            ntpDataMdl = ntpdao.select(nipSid);
            Ntp010Biz ntp010biz = new Ntp010Biz(con, reqMdl);
            int authFlg = 0;
            if (ntp010biz.isAddEditOk(
                    ntpDataMdl.getUsrSid(), con) == 0) {
                authFlg = 1;
            }
            //セッション情報を取得
            HttpSession session = reqMdl.getSession();
            BaseUserModel usModel =
                (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);


            while (rs.next()) {

                int authkbn = authFlg;

                //コメント情報を取得
                npcMdl = new NtpCommentModel();
                npcMdl = __getNtpCommentFromRs(rs);

                //ユーザ情報を取得
                uInfMdl = __getCmnUsrmInfFromRs(rs);


                if (authkbn == 0) {
                    if (npcMdl.getUsrSid() == usModel.getUsrsid()) {
                        authkbn = 1;
                    }
                }

                ntpDspCmtMdl = new Ntp040DspCommentModel();
                ntpDspCmtMdl.setCommentSid(String.valueOf(npcMdl.getNpcSid()));
                ntpDspCmtMdl.setCommentUserName(
                        uInfMdl.getUsiSei() + "&nbsp;" + uInfMdl.getUsiMei());
                ntpDspCmtMdl.setCommentUserBinSid(String.valueOf(uInfMdl.getBinSid()));
                ntpDspCmtMdl.setCommentUsiPictKf(String.valueOf(uInfMdl.getUsiPictKf()));
                ntpDspCmtMdl.setCommentDate(
                    UDateUtil.getYymdJ(npcMdl.getNpcAdate(), reqMdl)
                    + "("
                    + npcMdl.getNpcAdate().getStrWeekJ(reqMdl)
                    + ")"
                    + " "
                    + UDateUtil.getSeparateHMJ(npcMdl.getNpcAdate(), reqMdl));
                ntpDspCmtMdl.setCommentValue(npcMdl.getNpcComment());
                ntpDspCmtMdl.setCommentDelKbn(authkbn);
                ntpDspCmtMdl.setCommentUsrJkbn(uInfMdl.getUsrJkbn());
                ntpDspCmtMdl.setCommentUsrUkoFlg(uInfMdl.getUsrUkoFlg());
                ret.add(ntpDspCmtMdl);
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
     * <p>Create NTP_COMMENT Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created NtpCommentModel
     * @throws SQLException SQL実行例外
     */
    private NtpCommentModel __getNtpCommentFromRs(ResultSet rs) throws SQLException {
        NtpCommentModel bean = new NtpCommentModel();
        bean.setNpcSid(rs.getInt("NPC_SID"));
        bean.setNipSid(rs.getInt("NIP_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        String dspCommentStr = StringUtilHtml.transToHTmlForTextArea(rs.getString("NPC_COMMENT"));
        bean.setNpcComment(StringUtil.transToLink(StringUtilHtml.returntoBR(dspCommentStr),
                StringUtil.OTHER_WIN, true));
        bean.setNpcViewKbn(rs.getInt("NPC_VIEW_KBN"));
        bean.setNpcAuid(rs.getInt("NPC_AUID"));
        bean.setNpcAdate(UDate.getInstanceTimestamp(rs.getTimestamp("NPC_ADATE")));
        bean.setNpcEuid(rs.getInt("NPC_EUID"));
        bean.setNpcEdate(UDate.getInstanceTimestamp(rs.getTimestamp("NPC_EDATE")));
        return bean;
    }
    /**
     * <p>Create CMN_USRM_INF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnUsrmInfModel
     * @throws SQLException SQL実行例外
     */
    private CmnUsrmInfModel __getCmnUsrmInfFromRs(ResultSet rs) throws SQLException {
        CmnUsrmInfModel bean = new CmnUsrmInfModel();
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setUsiSei(rs.getString("USI_SEI"));
        bean.setUsiMei(rs.getString("USI_MEI"));
        bean.setUsiSeiKn(rs.getString("USI_SEI_KN"));
        bean.setUsiMeiKn(rs.getString("USI_MEI_KN"));
        bean.setUsiSini(rs.getString("USI_SINI"));
        bean.setUsiBdate(UDate.getInstanceTimestamp(rs.getTimestamp("USI_BDATE")));
        bean.setUsiZip1(rs.getString("USI_ZIP1"));
        bean.setUsiZip2(rs.getString("USI_ZIP2"));
        bean.setTdfSid(rs.getInt("TDF_SID"));
        bean.setUsiAddr1(rs.getString("USI_ADDR1"));
        bean.setUsiAddr2(rs.getString("USI_ADDR2"));
        bean.setUsiTel1(rs.getString("USI_TEL1"));
        bean.setUsiTelNai1(rs.getString("USI_TEL_NAI1"));
        bean.setUsiTelCmt1(rs.getString("USI_TEL_CMT1"));
        bean.setUsiTel2(rs.getString("USI_TEL2"));
        bean.setUsiTelNai2(rs.getString("USI_TEL_NAI2"));
        bean.setUsiTelCmt2(rs.getString("USI_TEL_CMT2"));
        bean.setUsiTel3(rs.getString("USI_TEL3"));
        bean.setUsiTelNai3(rs.getString("USI_TEL_NAI3"));
        bean.setUsiTelCmt3(rs.getString("USI_TEL_CMT3"));
        bean.setUsiFax1(rs.getString("USI_FAX1"));
        bean.setUsiFaxCmt1(rs.getString("USI_FAX_CMT1"));
        bean.setUsiFax2(rs.getString("USI_FAX2"));
        bean.setUsiFaxCmt2(rs.getString("USI_FAX_CMT2"));
        bean.setUsiFax3(rs.getString("USI_FAX3"));
        bean.setUsiFaxCmt3(rs.getString("USI_FAX_CMT3"));
        bean.setUsiMail1(rs.getString("USI_MAIL1"));
        bean.setUsiMailCmt1(rs.getString("USI_MAIL_CMT1"));
        bean.setUsiMail2(rs.getString("USI_MAIL2"));
        bean.setUsiMailCmt2(rs.getString("USI_MAIL_CMT2"));
        bean.setUsiMail3(rs.getString("USI_MAIL3"));
        bean.setUsiMailCmt3(rs.getString("USI_MAIL_CMT3"));
        bean.setUsiSyainNo(rs.getString("USI_SYAIN_NO"));
        bean.setUsiSyozoku(rs.getString("USI_SYOZOKU"));
        bean.setUsiYakusyoku(rs.getString("USI_YAKUSYOKU"));
        bean.setUsiSeibetu(rs.getInt("USI_SEIBETU"));
        bean.setUsiEntranceDate(
            UDate.getInstanceTimestamp(rs.getTimestamp("USI_ENTRANCE_DATE")));
        bean.setUsiSortkey1(rs.getString("USI_SORTKEY1"));
        bean.setUsiSortkey2(rs.getString("USI_SORTKEY2"));
        bean.setPosSid(rs.getInt("POS_SID"));
        bean.setUsiBiko(rs.getString("USI_BIKO"));
        bean.setBinSid(rs.getLong("BIN_SID"));
        bean.setUsiPictKf(rs.getInt("USI_PICT_KF"));
        bean.setUsiBdateKf(rs.getInt("USI_BDATE_KF"));
        bean.setUsiMail1Kf(rs.getInt("USI_MAIL1_KF"));
        bean.setUsiMail2Kf(rs.getInt("USI_MAIL2_KF"));
        bean.setUsiMail3Kf(rs.getInt("USI_MAIL3_KF"));
        bean.setUsiZipKf(rs.getInt("USI_ZIP_KF"));
        bean.setUsiTdfKf(rs.getInt("USI_TDF_KF"));
        bean.setUsiAddr1Kf(rs.getInt("USI_ADDR1_KF"));
        bean.setUsiAddr2Kf(rs.getInt("USI_ADDR2_KF"));
        bean.setUsiTel1Kf(rs.getInt("USI_TEL1_KF"));
        bean.setUsiTel2Kf(rs.getInt("USI_TEL2_KF"));
        bean.setUsiTel3Kf(rs.getInt("USI_TEL3_KF"));
        bean.setUsiFax1Kf(rs.getInt("USI_FAX1_KF"));
        bean.setUsiFax2Kf(rs.getInt("USI_FAX2_KF"));
        bean.setUsiFax3Kf(rs.getInt("USI_FAX3_KF"));
        bean.setUsiMblUse(rs.getInt("USI_MBL_USE"));
        bean.setUsiLtlgin(UDate.getInstanceTimestamp(rs.getTimestamp("USI_LTLGIN")));
        bean.setUsiAuid(rs.getInt("USI_AUID"));
        bean.setUsiAdate(UDate.getInstanceTimestamp(rs.getTimestamp("USI_ADATE")));
        bean.setUsiEuid(rs.getInt("USI_EUID"));
        bean.setUsiEdate(UDate.getInstanceTimestamp(rs.getTimestamp("USI_EDATE")));
        bean.setUsiNumCont(rs.getInt("USI_NUM_CONT"));
        bean.setUsiNumAutadd(rs.getInt("USI_NUM_AUTADD"));
        bean.setUsrJkbn(rs.getInt("USR_JKBN"));
        bean.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
        return bean;
    }

    /**
     * <br>[機  能] 案件SIDから案件情報(会社、会社拠点情報を含む)を取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @param ankenSid 案件SID
     * @return 案件情報
     * @throws SQLException  SQL実行例外
     */
    public Ntp040DataModel getAnkenWithCompany(int ankenSid)
            throws SQLException {
        Ntp040DataModel model = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("   select");
            sql.addSql("     NTP_ANKEN.NAN_SID,");
            sql.addSql("     NTP_ANKEN.NAN_CODE,");
            sql.addSql("     NTP_ANKEN.NAN_NAME,");
            sql.addSql("     NTP_ANKEN.NAN_MIKOMI,");
            sql.addSql("     ADR_COMPANY.ACO_SID,");
            sql.addSql("     ADR_COMPANY.ACO_CODE,");
            sql.addSql("     ADR_COMPANY.ACO_NAME,");
            sql.addSql("     ADR_COMPANY_BASE.ABA_SID,");
            sql.addSql("     ADR_COMPANY_BASE.ACO_SID,");
            sql.addSql("     ADR_COMPANY_BASE.ABA_NAME");
            sql.addSql("   from");
            sql.addSql("     NTP_ANKEN");
            sql.addSql("   left join");
            sql.addSql("     ADR_COMPANY");
            sql.addSql("   on NTP_ANKEN.ACO_SID = ADR_COMPANY.ACO_SID");
            sql.addSql("   left join");
            sql.addSql("     ADR_COMPANY_BASE");
            sql.addSql("   on NTP_ANKEN.ABA_SID = ADR_COMPANY_BASE.ABA_SID");
            sql.addSql("   where");
            sql.addSql("     NTP_ANKEN.NAN_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ankenSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                model = new Ntp040DataModel();
                model.setAnkenSid(rs.getInt("NAN_SID"));      // 案件SID
                model.setAnkenCode(rs.getString("NAN_CODE")); // 案件コード
                model.setAnkenName(rs.getString("NAN_NAME")); // 案件名
                model.setMikomido(rs.getInt("NAN_MIKOMI"));   // 見込み度
                model.setCompanySid(rs.getInt("ACO_SID"));
                model.setCompanyCode(rs.getString("ACO_CODE"));
                model.setCompanyName(rs.getString("ACO_NAME"));
                model.setCompanyBaseSid(rs.getInt("ABA_SID"));
                model.setCompanyBaseName(rs.getString("ABA_NAME"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return model;
    }
}
