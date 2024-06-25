package jp.groupsession.v2.prj.prj150;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.adr.dao.AddressDao;
import jp.groupsession.v2.cmn.model.PrjMemberEditModel;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.prj150.model.Prj150DBCompanyBaseModel;
import jp.groupsession.v2.prj.prj150.model.Prj150DBCompanyModel;
import jp.groupsession.v2.prj.prj150.model.Prj150DspMdl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] プロジェクト管理 プロジェクトメンバー設定画面で使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj150Dao extends AbstractDao {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Prj150Dao.class);

    /**
     * <p>
     * デフォルトコンストラクタ
     */
    public Prj150Dao() {
    }

    /**
     * <p>
     * デフォルトコンストラクタ
     *
     * @param con
     *            DBコネクション
     */
    public Prj150Dao(Connection con) {
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
    public Prj150DBCompanyModel getCompanyData(int acoSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Prj150DBCompanyModel bean = null;
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
                bean = new Prj150DBCompanyModel();
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
    public Prj150DBCompanyBaseModel getCompanyBaseData(int abaSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Prj150DBCompanyBaseModel bean = null;
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
                bean = new Prj150DBCompanyBaseModel();
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
     * <br>[機  能] アドレス帳情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param adrSidList アドレス帳情報SID
     * @param userSid ユーザSID
     * @param prjSid プロジェクトID
     * @return アドレス帳の名称一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Prj150DspModel> getAddressList(Connection con,
            String[] adrSidList, int userSid, int prjSid)
    throws SQLException {

        if (adrSidList == null || adrSidList.length == 0) {
            return new ArrayList<Prj150DspModel>();
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Prj150DspModel> adrNameList = new ArrayList<Prj150DspModel>();

        try {
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select");
            sql.addSql("   ADR_ADDRESS.ADR_SID,");
            sql.addSql("   ADR_ADDRESS.ADR_SEI,");
            sql.addSql("   ADR_ADDRESS.ADR_MEI,");
            sql.addSql("   ADR_ADDRESS.ACO_SID,");
            sql.addSql("   ADR_ADDRESS.ADR_MAIL1,");
            sql.addSql("   ADR_ADDRESS.ADR_TEL1,");
            sql.addSql("   ADR_ADDRESS.ABA_SID,");
            sql.addSql("   PRJ_ADDRESS.PRA_SORT");
            sql.addSql(" from");
            sql.addSql("   ADR_ADDRESS LEFT JOIN"
                    + " PRJ_ADDRESS on ADR_ADDRESS.ADR_SID = PRJ_ADDRESS.ADR_SID");

            sql.addSql(" where");
            //閲覧権限
            AddressDao.addViewableWhereSQL(sql, userSid);

            //アドレス帳SID
            sql.addSql(" and");
            sql.addSql("   ADR_ADDRESS.ADR_SID in (");

            sql.addSql("     ?");
            sql.addIntValue(Integer.parseInt(adrSidList[0]));

            for (int index = 1; index < adrSidList.length; index++) {
                sql.addSql("     ,?");
                sql.addIntValue(Integer.parseInt(adrSidList[index]));
            }

            sql.addSql("   )");
            sql.addSql(" and");
            sql.addSql("   PRJ_ADDRESS.PRJ_SID = ?");
            sql.addIntValue(prjSid);
            sql.addSql(" order by");
            sql.addSql("   PRJ_ADDRESS.PRA_SORT");

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Prj150DspModel model = new Prj150DspModel();
                model.setAdrSid(rs.getInt("ADR_SID"));
                model.setAdrName(rs.getString("ADR_SEI") + " " + rs.getString("ADR_MEI"));
                model.setCompanySid(rs.getInt("ACO_SID"));
                model.setCompanyBaseSid(rs.getInt("ABA_SID"));
                model.setAdrMail(rs.getString("ADR_MAIL1"));
                model.setAdrTel(rs.getString("ADR_TEL1"));

                adrNameList.add(model);
            }

        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return adrNameList;
    }
    /**
     * <br>[機  能] アドレス帳情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param adrSidList アドレス帳情報SID
     * @param userSid ユーザSID
     * @return アドレス帳の名称一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Prj150DspModel> getAddressList(Connection con,
            String[] adrSidList, int userSid)
    throws SQLException {

        if (adrSidList == null || adrSidList.length == 0) {
            return new ArrayList<Prj150DspModel>();
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Prj150DspModel> adrNameList = new ArrayList<Prj150DspModel>();

        try {
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select");
            sql.addSql("   ADR_ADDRESS.ADR_SID,");
            sql.addSql("   ADR_ADDRESS.ADR_SEI,");
            sql.addSql("   ADR_ADDRESS.ADR_MEI,");
            sql.addSql("   ADR_ADDRESS.ACO_SID,");
            sql.addSql("   ADR_ADDRESS.ADR_MAIL1,");
            sql.addSql("   ADR_ADDRESS.ADR_TEL1,");
            sql.addSql("   ADR_ADDRESS.ABA_SID");
            sql.addSql(" from");
            sql.addSql("   ADR_ADDRESS");

            //閲覧権限
            sql.addSql(" where");
            AddressDao.addViewableWhereSQL(sql, userSid);
            //アドレス帳SID
            sql.addSql(" and");
            sql.addSql("   ADR_ADDRESS.ADR_SID in (");

            sql.addSql("     ?");
            sql.addIntValue(Integer.parseInt(adrSidList[0]));

            for (int index = 1; index < adrSidList.length; index++) {
                sql.addSql("     ,?");
                sql.addIntValue(Integer.parseInt(adrSidList[index]));
            }

            sql.addSql("   )");

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Prj150DspModel model = new Prj150DspModel();
                model.setAdrSid(rs.getInt("ADR_SID"));
                model.setAdrName(rs.getString("ADR_SEI") + " " + rs.getString("ADR_MEI"));
                model.setCompanySid(rs.getInt("ACO_SID"));
                model.setCompanyBaseSid(rs.getInt("ABA_SID"));
                model.setAdrMail(rs.getString("ADR_MAIL1"));
                model.setAdrTel(rs.getString("ADR_TEL1"));

                adrNameList.add(model);
            }

        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return adrNameList;
    }
    /**
     * <br>[機  能] アドレス帳情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param adrSidList アドレス帳情報SID
     * @param userSid ユーザSID
     * @param prjSid プロジェクトID
     * @return アドレス帳の名称一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Prj150DspMdl> getAddressList2(Connection con,
            String[] adrSidList, int userSid, int prjSid)
    throws SQLException {

        if (adrSidList == null || adrSidList.length == 0) {
            return new ArrayList<Prj150DspMdl>();
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Prj150DspMdl> adrNameList = new ArrayList<Prj150DspMdl>();

        try {
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select");
            sql.addSql("   ADR_ADDRESS.ADR_SID,");
            sql.addSql("   ADR_ADDRESS.ADR_SEI,");
            sql.addSql("   ADR_ADDRESS.ADR_MEI,");
            sql.addSql("   ADR_ADDRESS.ACO_SID,");
            sql.addSql("   ADR_ADDRESS.ADR_MAIL1,");
            sql.addSql("   ADR_ADDRESS.ADR_TEL1,");
            sql.addSql("   ADR_ADDRESS.ABA_SID,");
            sql.addSql("   PRJ_ADDRESS.PRA_SORT");
            sql.addSql(" from");
            sql.addSql("   ADR_ADDRESS LEFT JOIN"
                    + " PRJ_ADDRESS on ADR_ADDRESS.ADR_SID = PRJ_ADDRESS.ADR_SID");

            //閲覧権限
            sql.addSql(" where");
            AddressDao.addViewableWhereSQL(sql, userSid);

            //アドレス帳SID
            sql.addSql(" and");
            sql.addSql("   ADR_ADDRESS.ADR_SID in (");

            sql.addSql("     ?");
            sql.addIntValue(Integer.parseInt(adrSidList[0]));

            for (int index = 1; index < adrSidList.length; index++) {
                sql.addSql("     ,?");
                sql.addIntValue(Integer.parseInt(adrSidList[index]));
            }

            sql.addSql("   )");
            sql.addSql(" and");
            sql.addSql("   PRJ_ADDRESS.PRJ_SID = ?");
            sql.addIntValue(prjSid);
            sql.addSql(" order by");
            sql.addSql("   PRJ_ADDRESS.PRA_SORT");

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Prj150DspMdl model = new Prj150DspMdl();
                model.setAdrSid(rs.getInt("ADR_SID"));
                model.setAdrName(rs.getString("ADR_SEI") + " " + rs.getString("ADR_MEI"));
                model.setCompanySid(rs.getInt("ACO_SID"));
                model.setCompanyBaseSid(rs.getInt("ABA_SID"));
                model.setAdrMail(rs.getString("ADR_MAIL1"));
                model.setAdrTel(rs.getString("ADR_TEL1"));

                adrNameList.add(model);
            }

        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return adrNameList;
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
     * <br>[機  能] プロジェクトメンバー情報を作成する
     * <br>[解  説] メンバーの氏名、状態区分、管理者区分、役職等も取得
     * <br>[備  考] 新規メンバー情報
     * @param projectSid プロジェクトSID
     * @param memberSid ユーザSID
     * @return List in UserModel
     * @throws SQLException SQL実行例外
     */
    public List<PrjMemberEditModel> createNewMemberList(int projectSid, String[] memberSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<PrjMemberEditModel> ret = new ArrayList<PrjMemberEditModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    CMN_USRM.USR_SID,");
            sql.addSql("    CMN_USRM.USR_JKBN,");
            sql.addSql("    CMN_USRM.USR_UKO_FLG,");
            sql.addSql("    CMN_USRM_INF.USI_SEI,");
            sql.addSql("    CMN_USRM_INF.USI_MEI,");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID > 0 then 2");
            sql.addSql("      else 3");
            sql.addSql("    end) as YAKUSYOKU_EXIST,");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then ''");
            sql.addSql("      else CMN_POSITION.POS_NAME");
            sql.addSql("    end) as YAKUSYOKU_NAME");
            sql.addSql("  from");
            sql.addSql("    CMN_USRM,");
            sql.addSql("    CMN_USRM_INF,");
            sql.addSql("    CMN_POSITION");
            sql.addSql("  where");
            sql.addSql("    CMN_USRM_INF.USR_SID = CMN_USRM.USR_SID");
            sql.addSql("  and");
            sql.addSql("    CMN_USRM_INF.POS_SID = CMN_POSITION.POS_SID");


            if (memberSid != null && memberSid.length > 0) {
                sql.addSql("  and");
                if (memberSid.length == 1) {
                    sql.addSql("    CMN_USRM.USR_SID = ?");
                    sql.addIntValue(Integer.parseInt(memberSid[0]));
                } else {
                    sql.addSql("    CMN_USRM.USR_SID in ( ?");
                    sql.addIntValue(Integer.parseInt(memberSid[0]));

                    for (int idx = 1; idx < memberSid.length; idx++) {
                        sql.addSql("      ,?");
                        sql.addIntValue(Integer.parseInt(memberSid[idx]));
                    }
                    sql.addSql("    )");
                }
            }

            sql.addSql(" order by");
            sql.addSql("  YAKUSYOKU_EXIST asc,");
            sql.addSql("  CMN_POSITION.POS_SORT asc");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                PrjMemberEditModel bean = new PrjMemberEditModel();
                bean.setUsrSid(rs.getInt("USR_SID"));
                bean.setUsiSei(rs.getString("USI_SEI"));
                bean.setUsiMei(rs.getString("USI_MEI"));
                bean.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
                bean.setMemberKey(null);
                bean.setPrmAdminKbn(GSConstProject.KBN_POWER_NORMAL);
                ret.add(bean);
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
