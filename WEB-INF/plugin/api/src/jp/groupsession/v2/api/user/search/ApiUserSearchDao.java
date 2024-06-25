package jp.groupsession.v2.api.user.search;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.model.base.CmnLabelUsrModel;
import jp.groupsession.v2.usr.GSConstUser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *
 * <br>[機  能] Api ユーザ検索DAO
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiUserSearchDao extends AbstractDao {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiUserSearchDao.class);
    /**
     * <p>デフォルトコンストラクタ
     */
    public ApiUserSearchDao() {
        super();
    }

    /**
     * <p>デフォルトコンストラクタ
     * @param con DBコネクション
     */
    public ApiUserSearchDao(Connection con) {
        super(con);
    }
    /**
     * <p>詳細検索(カウント)
     * @param searchModel 検索条件
     * @return 検索条件に該当する件数
     * @throws SQLException SQL実行例外
     */
    public int getSearchCount(ApiUserSearchModel searchModel) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        int ret = 0;

        try {
            //SQL文
            SqlBuffer sql = __getSearchSql(searchModel, false);
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
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
     * <p>詳細検索
     * @param searchModel 検索条件
     * @param start 取得開始位置
     * @param limit 取得件数(上限値)
     * @return List in QueryTableModel
     * @throws SQLException SQL実行例外
     */
    public List<ApiUserModel> getSearchList(ApiUserSearchModel searchModel,
            int start, int limit) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ApiUserModel> ret = new ArrayList<ApiUserModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = __getSearchSql(searchModel, true);
            log__.info(sql.toLogString());

            if (limit > 0 && start > 1) {
                pstmt = con.prepareStatement(sql.toSqlString(),
                        ResultSet.TYPE_SCROLL_INSENSITIVE ,
                        ResultSet.CONCUR_READ_ONLY);
            } else {
                pstmt = con.prepareStatement(sql.toSqlString());
            }

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (limit > 0) {
                if (start > 1) {
                    rs.absolute(start - 1);
                }

                for (int i = 0; rs.next() && i < limit; i++) {
                    ret.add(__getCmnUsrmInfFromRs(rs));
                }
            } else {
                while (rs.next()) {
                    ret.add(__getCmnUsrmInfFromRs(rs));
                }
            }
            //ラベル設定
            __setLabels(ret);
            //グループ設定
            __setGroups(ret);

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }
    /**
     * <p>Create CMN_USRM_INF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnUsrmInfModel
     * @throws SQLException SQL実行例外
     */
    private ApiUserModel __getCmnUsrmInfFromRs(ResultSet rs) throws SQLException {
        ApiUserModel bean = new ApiUserModel();
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setUsiSei(rs.getString("USI_SEI"));
        bean.setUsiMei(rs.getString("USI_MEI"));
        bean.setUsiSeiKn(rs.getString("USI_SEI_KN"));
        bean.setUsiMeiKn(rs.getString("USI_MEI_KN"));
        bean.setUsiSini(rs.getString("USI_SINI"));
        bean.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
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
        return bean;
    }

    /**
     * <p>詳細検索で使用するSQLを取得する。
     * @param searchModel 検索条件
     * @param sqlType true:select, false:count
     * @return List in QueryTableModel
     * @throws SQLException SQL実行例外
     */
    private SqlBuffer __getSearchSql(ApiUserSearchModel searchModel,
            boolean sqlType) throws SQLException {
        //SQL文
        SqlBuffer sql = new SqlBuffer();

        if (sqlType == true) {
            //SELECT
            sql.addSql(" select");
            sql.addSql("   SIDLIST.USR_UKO_FLG,");
            sql.addSql("   CMN_USRM_INF.USR_SID,");
            sql.addSql("   CMN_USRM_INF.USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI,");
            sql.addSql("   CMN_USRM_INF.USI_SEI_KN,");
            sql.addSql("   CMN_USRM_INF.USI_MEI_KN,");
            sql.addSql("   CMN_USRM_INF.USI_SINI,");
            sql.addSql("   CMN_USRM_INF.USI_BDATE,");
            sql.addSql("   CMN_USRM_INF.USI_ZIP1,");
            sql.addSql("   CMN_USRM_INF.USI_ZIP2,");
            sql.addSql("   CMN_USRM_INF.TDF_SID,");
            sql.addSql("   CMN_USRM_INF.USI_ADDR1,");
            sql.addSql("   CMN_USRM_INF.USI_ADDR2,");
            sql.addSql("   CMN_USRM_INF.USI_TEL1,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_NAI1,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_CMT1,");
            sql.addSql("   CMN_USRM_INF.USI_TEL2,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_NAI2,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_CMT2,");
            sql.addSql("   CMN_USRM_INF.USI_TEL3,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_NAI3,");
            sql.addSql("   CMN_USRM_INF.USI_TEL_CMT3,");
            sql.addSql("   CMN_USRM_INF.USI_FAX1,");
            sql.addSql("   CMN_USRM_INF.USI_FAX_CMT1,");
            sql.addSql("   CMN_USRM_INF.USI_FAX2,");
            sql.addSql("   CMN_USRM_INF.USI_FAX_CMT2,");
            sql.addSql("   CMN_USRM_INF.USI_FAX3,");
            sql.addSql("   CMN_USRM_INF.USI_FAX_CMT3,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL1,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL_CMT1,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL2,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL_CMT2,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL3,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL_CMT3,");
            sql.addSql("   CMN_USRM_INF.USI_SYAIN_NO,");
            sql.addSql("   CMN_USRM_INF.USI_SYOZOKU,");
            sql.addSql("   CMN_USRM_INF.POS_SID,");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then ''");
            sql.addSql("      else (select");
            sql.addSql("            POS_NAME");
            sql.addSql("          from");
            sql.addSql("            CMN_POSITION");
            sql.addSql("          where CMN_USRM_INF.POS_SID = CMN_POSITION.POS_SID)");
            sql.addSql("    end) as USI_YAKUSYOKU,");
            sql.addSql("   CMN_USRM_INF.USI_MBL_USE,");
            sql.addSql("   CMN_USRM_INF.USI_BIKO,");
            sql.addSql("   CMN_USRM_INF.BIN_SID,");
            sql.addSql("   CMN_USRM_INF.USI_PICT_KF,");
            sql.addSql("   CMN_USRM_INF.USI_BDATE_KF,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL1_KF,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL2_KF,");
            sql.addSql("   CMN_USRM_INF.USI_MAIL3_KF,");
            sql.addSql("   CMN_USRM_INF.USI_ZIP_KF,");
            sql.addSql("   CMN_USRM_INF.USI_TDF_KF,");
            sql.addSql("   CMN_USRM_INF.USI_ADDR1_KF,");
            sql.addSql("   CMN_USRM_INF.USI_ADDR2_KF,");
            sql.addSql("   CMN_USRM_INF.USI_TEL1_KF,");
            sql.addSql("   CMN_USRM_INF.USI_TEL2_KF,");
            sql.addSql("   CMN_USRM_INF.USI_TEL3_KF,");
            sql.addSql("   CMN_USRM_INF.USI_FAX1_KF,");
            sql.addSql("   CMN_USRM_INF.USI_FAX2_KF,");
            sql.addSql("   CMN_USRM_INF.USI_FAX3_KF,");
            sql.addSql("   CMN_USRM_INF.USI_LTLGIN,");
            sql.addSql("   CMN_USRM_INF.USI_AUID,");
            sql.addSql("   CMN_USRM_INF.USI_ADATE,");
            sql.addSql("   CMN_USRM_INF.USI_EUID,");
            sql.addSql("   CMN_USRM_INF.USI_EDATE,");
            sql.addSql("   CMN_USRM_INF.USI_NUM_CONT,");
            sql.addSql("   CMN_USRM_INF.USI_NUM_AUTADD,");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then 1");
            sql.addSql("      else 0");
            sql.addSql("    end) as YAKUSYOKU_EXIST,");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then 0");
            sql.addSql("      else (select");
            sql.addSql("              POS_SORT");
            sql.addSql("            from");
            sql.addSql("              CMN_POSITION");
            sql.addSql("            where CMN_USRM_INF.POS_SID = CMN_POSITION.POS_SID)");
            sql.addSql("    end) as YAKUSYOKU_SORT,");
            sql.addSql("  COALESCE(CMN_USRM_INF.USI_SORTKEY1, '') as SORTKEY1_SORT,");
            sql.addSql("  COALESCE(CMN_USRM_INF.USI_SORTKEY2, '') as SORTKEY2_SORT");

            sql.addSql(" from (");
        } else {
            //COUNT
            sql.addSql(" select count(*) as CNT from (");
        }
        sql.addSql(" select");
        sql.addSql("   IUSER.USR_UKO_FLG,");
        sql.addSql("   IUSER.USR_SID");
        sql.addSql(" from");
        sql.addSql("   (");
        sql.addSql("   select");
        sql.addSql("     CMN_USRM.USR_UKO_FLG as USR_UKO_FLG,");
        sql.addSql("     CMN_USRM.USR_LGID as USR_LGID,");
        sql.addSql("     CMN_USRM_INF.USR_SID as USR_SID,");
        sql.addSql("     CMN_USRM_INF.USI_SEI as USI_SEI,");
        sql.addSql("     CMN_USRM_INF.USI_MEI as USI_MEI,");
        sql.addSql("     CMN_USRM_INF.USI_SEI_KN as USI_SEI_KN,");
        sql.addSql("     CMN_USRM_INF.USI_MEI_KN as USI_MEI_KN,");
        sql.addSql("     CMN_USRM_INF.USI_SINI as USI_SINI,");
        sql.addSql("     case");
        sql.addSql("     when");
        sql.addSql("       CMN_USRM_INF.USI_BDATE_KF=0 then CMN_USRM_INF.USI_BDATE");
        sql.addSql("     else");
        sql.addSql("       null");
        sql.addSql("     end USI_BDATE,");
        sql.addSql("     CMN_USRM_INF.USI_ZIP1 as USI_ZIP1,");
        sql.addSql("     CMN_USRM_INF.USI_ZIP2 as USI_ZIP2,");
        sql.addSql("     case");
        sql.addSql("     when");
        sql.addSql("       CMN_USRM_INF.USI_TDF_KF=0 then CMN_USRM_INF.TDF_SID");
        sql.addSql("     else");
        sql.addSql("       0");
        sql.addSql("     end TDF_SID,");
        sql.addSql("     CMN_USRM_INF.USI_ADDR1 as USI_ADDR1,");
        sql.addSql("     CMN_USRM_INF.USI_ADDR2 as USI_ADDR2,");
        sql.addSql("     CMN_USRM_INF.USI_TEL1 as USI_TEL1,");
        sql.addSql("     CMN_USRM_INF.USI_TEL2 as USI_TEL2,");
        sql.addSql("     CMN_USRM_INF.USI_TEL3 as USI_TEL3,");
        sql.addSql("     CMN_USRM_INF.USI_FAX1 as USI_FAX1,");
        sql.addSql("     CMN_USRM_INF.USI_FAX2 as USI_FAX2,");
        sql.addSql("     CMN_USRM_INF.USI_FAX3 as USI_FAX3,");
        sql.addSql("     case");
        sql.addSql("     when");
        sql.addSql("       CMN_USRM_INF.USI_MAIL1_KF=0 then CMN_USRM_INF.USI_MAIL1");
        sql.addSql("     else");
        sql.addSql("       null");
        sql.addSql("     end USI_MAIL1,");
        sql.addSql("     case");
        sql.addSql("     when");
        sql.addSql("       CMN_USRM_INF.USI_MAIL2_KF=0 then CMN_USRM_INF.USI_MAIL2");
        sql.addSql("     else");
        sql.addSql("       null");
        sql.addSql("     end USI_MAIL2,");
        sql.addSql("     case");
        sql.addSql("     when");
        sql.addSql("       CMN_USRM_INF.USI_MAIL3_KF=0 then CMN_USRM_INF.USI_MAIL3");
        sql.addSql("     else");
        sql.addSql("       null");
        sql.addSql("     end USI_MAIL3,");
        sql.addSql("     CMN_USRM_INF.USI_SYAIN_NO as USI_SYAIN_NO,");
        sql.addSql("     CMN_USRM_INF.USI_SYOZOKU as USI_SYOZOKU,");
        sql.addSql("     CMN_USRM_INF.POS_SID as POS_SID,");
        sql.addSql("     CMN_USRM_INF.USI_BIKO as USI_BIKO,");
        sql.addSql("     CMN_USRM_INF.BIN_SID as BIN_SID");
        sql.addSql("   from");
        sql.addSql("     CMN_USRM");
        sql.addSql("     left join");
        sql.addSql("        CMN_USRM_INF ");
        sql.addSql("     on");
        sql.addSql("        CMN_USRM.USR_SID=CMN_USRM_INF.USR_SID");
        sql.addSql("   where ");
        sql.addSql("     CMN_USRM.USR_JKBN=0");
        sql.addSql("  ) as IUSER,");
        sql.addSql("   (");
        sql.addSql("     select");
        sql.addSql("       CMN_GROUPM.GRP_SID,");
        sql.addSql("       CMN_BELONGM.USR_SID");
        sql.addSql("     from");
        sql.addSql("       CMN_GROUPM,");
        sql.addSql("       CMN_BELONGM");
        sql.addSql("     where ");
        sql.addSql("       CMN_GROUPM.GRP_SID=CMN_BELONGM.GRP_SID");
        sql.addSql("     and");
        sql.addSql("       CMN_GROUPM.GRP_JKBN=0");
        sql.addSql("   ) as BELONG");
        sql.addSql(" where");
        sql.addSql("   IUSER.USR_SID = BELONG.USR_SID");

        if (searchModel.isExcludeSysUser()) {
            //ユーザSID < 100は除外
            sql.addSql(" and");
            sql.addSql("   IUSER.USR_SID>?");
            sql.addIntValue(GSConstUser.USER_RESERV_SID);
        }
        //氏名カナ 頭文字
        if (!StringUtil.isNullZeroString(searchModel.getSearchKana())) {
            String[] initials = searchModel.getSearchKana().split(",");

            sql.addSql(" and");
            sql.addSql("   IUSER.USI_SINI in (");
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

        //ユーザID
        String userId = searchModel.getUserId();
        if (!StringUtil.isNullZeroString(userId)) {
            sql.addSql(" and");
            sql.addSql("   IUSER.USR_LGID=?");
            sql.addStrValue(userId);
        }

        //氏名 姓
        String sei = searchModel.getSei();
        if (!StringUtil.isNullZeroString(sei)) {
            sql.addSql(" and");
            sql.addSql("       IUSER.USI_SEI like '%"
                    + JDBCUtil.escapeForLikeSearch(sei)
                    + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
        }

        //氏名 名
        String mei = searchModel.getMei();
        if (!StringUtil.isNullZeroString(mei)) {
            sql.addSql(" and");
            sql.addSql("       IUSER.USI_MEI like '%"
                    + JDBCUtil.escapeForLikeSearch(mei)
                    + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
        }

        //氏名 セイ
        String seiKn = searchModel.getSeikn();
        if (!StringUtil.isNullZeroString(seiKn)) {
            sql.addSql(" and");
            sql.addSql("       IUSER.USI_SEI_KN like '%"
                    + JDBCUtil.escapeForLikeSearch(seiKn)
                    + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
        }

        //氏名 メイ
        String meiKn = searchModel.getMeikn();
        if (!StringUtil.isNullZeroString(meiKn)) {
            sql.addSql(" and");
            sql.addSql("       IUSER.USI_MEI_KN like '%"
                    + JDBCUtil.escapeForLikeSearch(meiKn)
                    + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
        }

        //社員/職員番号
        String syainno = searchModel.getShainno();
        if (!StringUtil.isNullZeroString(syainno)) {
            sql.addSql(" and");
            sql.addSql("       IUSER.USI_SYAIN_NO like '%"
                    + JDBCUtil.escapeForLikeSearch(syainno)
                    + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
        }

        //役職
        int yakushoku = searchModel.getYakushoku();
        if (yakushoku != GSConstCommon.NUM_INIT) {
            sql.addSql(" and");
            sql.addSql("   IUSER.POS_SID = ?");
            sql.addIntValue(yakushoku);
        }

        //メールアドレス
        String mail = searchModel.getMail();
        if (!StringUtil.isNullZeroString(mail)) {
            sql.addSql(" and");
            sql.addSql("       (IUSER.USI_MAIL1 like '%"
                    + JDBCUtil.escapeForLikeSearch(mail)
                    + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
            sql.addSql("     or");
            sql.addSql("       IUSER.USI_MAIL2 like '%"
                    + JDBCUtil.escapeForLikeSearch(mail)
                    + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
            sql.addSql("     or");
            sql.addSql("       IUSER.USI_MAIL3 like '%"
                    + JDBCUtil.escapeForLikeSearch(mail)
                    + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
            sql.addSql("   )");
        }

        //都道府県
        String tdfk = searchModel.getTdfkCd();
        if (!StringUtil.isNullZeroString(tdfk)) {
            int tdfsid = NullDefault.getInt(tdfk, 0);
            if (tdfsid > 0) {
                sql.addSql(" and");
                sql.addSql("   IUSER.TDF_SID=?");
                sql.addIntValue(tdfsid);
            }
        }

        //ラベル
        String[] label = searchModel.getLabelSid();
        if (label != null) {
            sql.addSql(" and");
            sql.addSql("   exists (");
            sql.addSql("     select");
            sql.addSql("       USR_SID");
            sql.addSql("     from");
            sql.addSql("       CMN_USRM_LABEL");
            sql.addSql("     where");
            sql.addSql("       CMN_USRM_LABEL.USR_SID = IUSER.USR_SID");
            sql.addSql("     and");
            if (label.length >= 2) {
                sql.addSql("     LAB_SID in ( ?");
                sql.addIntValue(Integer.parseInt(label[0]));
                for (int i = 1; i < label.length; i++) {
                    sql.addSql(",?");
                    sql.addIntValue(Integer.parseInt(label[i]));
                }
                sql.addSql("       )");
            } else {
                sql.addSql("       LAB_SID=? ");
                sql.addIntValue(Integer.parseInt(label[0]));
            }
            sql.addSql("       )");
        }


        //年齢
        String ageFrom = searchModel.getAgefrom();
        String ageTo = searchModel.getAgeto();
        if (!StringUtil.isNullZeroString(ageFrom)
          && StringUtil.isNullZeroString(ageTo)) {
            //Fromのみ入力の場合
            int iage = NullDefault.getInt(ageFrom, 0);
            UDate bdate = new UDate();
            log__.debug("iage = " + iage);
            //基準年を作成
            bdate.addYear(-iage);
            bdate.setZeroHhMmSs();

            sql.addSql(" and");
            sql.addSql("   IUSER.USI_BDATE <= ?");
            sql.addDateValue(bdate);
        } else if (StringUtil.isNullZeroString(ageFrom)
                && !StringUtil.isNullZeroString(ageTo)) {
            //Toのみ入力の場合
            int iage = NullDefault.getInt(ageTo, 0);
            iage = iage + 1;
            UDate bdate = new UDate();
            log__.debug("iage = " + iage);
            bdate.addYear(-iage);
            bdate.addDay(1);
            bdate.setZeroHhMmSs();

            sql.addSql(" and");
            sql.addSql("   IUSER.USI_BDATE >= ?");
            sql.addDateValue(bdate);
        } else if (!StringUtil.isNullZeroString(ageFrom)
                && !StringUtil.isNullZeroString(ageTo)) {
            //両方入力の場合
            //FROM
            int fage = NullDefault.getInt(ageFrom, 0);
            UDate fdate = new UDate();
            fdate.addYear(-fage);
            fdate.setZeroHhMmSs();

            //TO
            int tage = NullDefault.getInt(ageTo, 0);
            tage = tage + 1;
            UDate tdate = new UDate();
            tdate.addYear(-tage);
            tdate.addDay(1);
            tdate.setZeroHhMmSs();

            sql.addSql(" and");
            sql.addSql("   IUSER.USI_BDATE");
            sql.addSql("     between");
            sql.addSql("       ?");
            sql.addSql("     and");
            sql.addSql("       ?");

            sql.addDateValue(tdate);
            sql.addDateValue(fdate);
        }

        //所属グループ
        int gsid = searchModel.getSelectgsid();
        if (gsid >= 0) {
            sql.addSql(" and");
            sql.addSql("   BELONG.GRP_SID=?");
            sql.addIntValue(gsid);
        }

        sql.addSql(" group by");
        sql.addSql("   IUSER.USR_UKO_FLG,IUSER.USR_SID");

        sql.addSql(" ) as SIDLIST , CMN_USRM_INF");
        sql.addSql(" where SIDLIST.USR_SID = CMN_USRM_INF.USR_SID");

        //ソート(selectの場合のみ)
        if (sqlType == true) {
            //第一ソート
            String orderStr = "";
            int orderKey = searchModel.getSortOrder();
            //オーダー
            if (orderKey == GSConst.ORDER_KEY_ASC) {
                orderStr = "  asc";
            } else {
                orderStr = "  desc";
            }

            sql.addSql(" order by");
            int sortKey = searchModel.getSortKey();
            log__.debug("sortkey = " + sortKey);
            //ソートカラム
            switch (sortKey) {
                //氏名
                case GSConstUser.USER_SORT_NAME:
                    sql.addSql("   CMN_USRM_INF.USI_SEI_KN");
                    sql.addSql(orderStr);
                    sql.addSql("   ,");
                    sql.addSql("   CMN_USRM_INF.USI_MEI_KN");
                    sql.addSql(orderStr);
                    break;
                //社員/職員番号
                case GSConstUser.USER_SORT_SNO:
                    sql.addSql("   case when CMN_USRM_INF.USI_SYAIN_NO is null then ''");
                    sql.addSql("   else CMN_USRM_INF.USI_SYAIN_NO end ");
                    sql.addSql(orderStr);
//                    sql.addSql("   ,");
//                    sql.addSql("   CMN_USRM_INF.USI_SEI_KN");
//                    sql.addSql("    asc");
//                    sql.addSql("   ,");
//                    sql.addSql("   CMN_USRM_INF.USI_MEI_KN");
//                    sql.addSql("    asc");
                    break;
                //役職
                case GSConstUser.USER_SORT_YKSK:
                    sql.addSql("  YAKUSYOKU_EXIST");
                    sql.addSql(orderStr);
                    sql.addSql("  ,");
                    sql.addSql("  YAKUSYOKU_SORT");
                    sql.addSql(orderStr);
//                    sql.addSql("   ,");
//                    sql.addSql("   CMN_USRM_INF.USI_SEI_KN");
//                    sql.addSql("    asc");
//                    sql.addSql("   ,");
//                    sql.addSql("   CMN_USRM_INF.USI_MEI_KN");
//                    sql.addSql("    asc");
                    break;
                //生年月日
                case GSConstUser.USER_SORT_BDATE:
                    sql.addSql("  CMN_USRM_INF.USI_BDATE");
                    sql.addSql(orderStr);
                    break;
                //ソートキー1
                case GSConstUser.USER_SORT_SORTKEY1:
                    //sql.addSql("  CMN_USRM_INF.USI_SORTKEY1");
                    sql.addSql("  SORTKEY1_SORT");
                    sql.addSql(orderStr);
                    break;
                //ソートキー2
                case GSConstUser.USER_SORT_SORTKEY2:
                    //sql.addSql("  CMN_USRM_INF.USI_SORTKEY2");
                    sql.addSql("  SORTKEY2_SORT");
                    sql.addSql(orderStr);
                    break;
                default:
                    break;
            }

            sql.addSql("   ,");

            //第二ソート
            String orderStr2 = "";
            int orderKey2 = searchModel.getSortOrder2();
            //オーダー
            if (orderKey2 == GSConst.ORDER_KEY_ASC) {
                orderStr2 = "  asc";
            } else {
                orderStr2 = "  desc";
            }

            int sortKey2 = searchModel.getSortKey2();
            log__.debug("sortkey2 = " + sortKey2);
            //ソートカラム
            switch (sortKey2) {
                //氏名
                case GSConstUser.USER_SORT_NAME:
                    sql.addSql("   CMN_USRM_INF.USI_SEI_KN");
                    sql.addSql(orderStr2);
                    sql.addSql("   ,");
                    sql.addSql("   CMN_USRM_INF.USI_MEI_KN");
                    sql.addSql(orderStr2);
                    break;
                //社員/職員番号
                case GSConstUser.USER_SORT_SNO:
                    sql.addSql("   case when CMN_USRM_INF.USI_SYAIN_NO is null then ''");
                    sql.addSql("   else CMN_USRM_INF.USI_SYAIN_NO end ");
                    sql.addSql(orderStr2);
                    sql.addSql("   ,");
                    sql.addSql("   CMN_USRM_INF.USI_SEI_KN");
                    sql.addSql("    asc");
                    sql.addSql("   ,");
                    sql.addSql("   CMN_USRM_INF.USI_MEI_KN");
                    sql.addSql("    asc");
                    break;
                //役職
                case GSConstUser.USER_SORT_YKSK:
                    sql.addSql("  YAKUSYOKU_EXIST");
                    sql.addSql(orderStr2);
                    sql.addSql("  ,");
                    sql.addSql("  YAKUSYOKU_SORT");
                    sql.addSql(orderStr2);
                    sql.addSql("   ,");
                    sql.addSql("   CMN_USRM_INF.USI_SEI_KN");
                    sql.addSql("    asc");
                    sql.addSql("   ,");
                    sql.addSql("   CMN_USRM_INF.USI_MEI_KN");
                    sql.addSql("    asc");
                    break;
                    //生年月日
                case GSConstUser.USER_SORT_BDATE:
                    sql.addSql("  CMN_USRM_INF.USI_BDATE");
                    sql.addSql(orderStr2);
                    sql.addSql("   ,");
                    sql.addSql("   CMN_USRM_INF.USI_SEI_KN");
                    sql.addSql("    asc");
                    sql.addSql("   ,");
                    sql.addSql("   CMN_USRM_INF.USI_MEI_KN");
                    sql.addSql("    asc");
                    break;
                //ソートキー1
                case GSConstUser.USER_SORT_SORTKEY1:
                    //sql.addSql("  CMN_USRM_INF.SORTKEY1");
                    sql.addSql("  SORTKEY1_SORT");
                    sql.addSql(orderStr2);
                    sql.addSql("   ,");
                    sql.addSql("   CMN_USRM_INF.USI_SEI_KN");
                    sql.addSql("    asc");
                    sql.addSql("   ,");
                    sql.addSql("   CMN_USRM_INF.USI_MEI_KN");
                    sql.addSql("    asc");
                    break;
                //ソートキー2
                case GSConstUser.USER_SORT_SORTKEY2:
                    //sql.addSql("  CMN_USRM_INF.SORTKEY2");
                    sql.addSql("  SORTKEY2_SORT");
                    sql.addSql(orderStr2);
                    sql.addSql("   ,");
                    sql.addSql("   CMN_USRM_INF.USI_SEI_KN");
                    sql.addSql("    asc");
                    sql.addSql("   ,");
                    sql.addSql("   CMN_USRM_INF.USI_MEI_KN");
                    sql.addSql("    asc");
                    break;
                default:
                    break;
            }
        }
        log__.info(sql.toLogString());
        return sql;
    }
    /**
     * <p>ユーザ情報に付与されているラベルを設定する
     * @param ret ユーザ情報一覧
     * @throws SQLException SQL実行例外
     */
    private void __setLabels(ArrayList<ApiUserModel> ret) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();

        //ユーザ情報に付与されているラベルの名称を取得する
        if (!ret.isEmpty()) {
            try {
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" select");
                sql.addSql("   CMN_LABEL_USR.LAB_SID as LAB_SID,");
                sql.addSql("   CMN_LABEL_USR.LAB_NAME as LAB_NAME,");
                sql.addSql("   CMN_LABEL_USR.LUC_SID as LUC_SID,");
                sql.addSql("   CMN_LABEL_USR.LAB_BIKO as LAB_BIKO,");
                sql.addSql("   CMN_LABEL_USR.LAB_ADATE as LAB_ADATE,");
                sql.addSql("   CMN_LABEL_USR.LAB_AUID as LAB_AUID,");
                sql.addSql("   CMN_LABEL_USR.LAB_EDATE as LAB_EDATE,");
                sql.addSql("   CMN_LABEL_USR.LAB_EUID as LAB_EUID,");
                sql.addSql("   CMN_LABEL_USR.LAB_SORT as LAB_SORT");
                sql.addSql(" from");
                sql.addSql("   CMN_LABEL_USR,");
                sql.addSql("   CMN_USRM_LABEL");
                sql.addSql(" where");
                sql.addSql("   CMN_USRM_LABEL.USR_SID = ?");
                sql.addSql(" and");
                sql.addSql("   CMN_USRM_LABEL.LAB_SID = CMN_LABEL_USR.LAB_SID");
                sql.addSql(" order by");
                sql.addSql("   CMN_LABEL_USR.LAB_NAME");

                pstmt = con.prepareStatement(sql.toSqlString());

                for (int idx = 0; idx < ret.size(); idx++) {
                    int usrSid = ret.get(idx).getUsrSid();
                    JDBCUtil.closeResultSet(rs);
                    sql.clearValue();
                    sql.addIntValue(usrSid);
                    sql.toLogString();

                    List<String> labelNameList = new ArrayList<String>();
                    List<CmnLabelUsrModel> labelList = new ArrayList<CmnLabelUsrModel>();
                    pstmt.setInt(1, usrSid);
                    rs = pstmt.executeQuery();
                    while (rs.next()) {
                        labelNameList.add(rs.getString("LAB_NAME"));

                        CmnLabelUsrModel bean = new CmnLabelUsrModel();
                        bean.setLabSid(rs.getInt("LAB_SID"));
                        bean.setLucSid(rs.getInt("LUC_SID"));
                        bean.setLabName(rs.getString("LAB_NAME"));
                        bean.setLabBiko(rs.getString("LAB_BIKO"));
                        bean.setLabAdate(UDate.getInstanceTimestamp(rs.getTimestamp("LAB_ADATE")));
                        bean.setLabAuid(rs.getInt("LAB_AUID"));
                        bean.setLabEdate(UDate.getInstanceTimestamp(rs.getTimestamp("LAB_EDATE")));
                        bean.setLabEuid(rs.getInt("LAB_EUID"));
                        bean.setLabSort(rs.getInt("LAB_SORT"));
                        labelList.add(bean);
                    }
                    ret.get(idx).setLabelNameList(labelNameList);
                    ret.get(idx).setLabels(labelList);
                }
            } catch (SQLException e) {
                throw e;
            } finally {
                JDBCUtil.closeResultSet(rs);
                JDBCUtil.closeStatement(pstmt);
            }

        }
    }
    /**
     * <p>ユーザ情報に付与されているグループを設定する
     * @param ret ユーザ情報一覧
     * @throws SQLException SQL実行例外
     */
    public void __setGroups(ArrayList<ApiUserModel> ret) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();

        //ユーザ情報に付与されているラベルの名称を取得する
        if (!ret.isEmpty()) {
            try {
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" select ");
                sql.addSql("   CMN_GROUPM.GRP_SID as GRP_SID, ");
                sql.addSql("   CMN_GROUPM.GRP_NAME as GRP_NAME, ");
                sql.addSql("   CMN_BELONGM.BEG_GRPKBN as BEG_GRPKBN");
                sql.addSql(" from ");
                sql.addSql("   CMN_GROUPM, ");
                sql.addSql("   CMN_BELONGM ");
                sql.addSql(" where ");
                sql.addSql("   CMN_BELONGM.USR_SID = ? ");
                sql.addSql(" and ");
                sql.addSql("   CMN_BELONGM.GRP_SID = CMN_GROUPM.GRP_SID ");
                sql.addSql(" order by ");
                sql.addSql("   GRP_NAME ");
                pstmt = con.prepareStatement(sql.toSqlString());

                for (int idx = 0; idx < ret.size(); idx++) {
                    int usrSid = ret.get(idx).getUsrSid();
                    JDBCUtil.closeResultSet(rs);
                    sql.clearValue();
                    sql.addIntValue(usrSid);
                    sql.toLogString();

                    List<GroupModel> grpList = new ArrayList<GroupModel>();
                    pstmt.setInt(1, usrSid);
                    rs = pstmt.executeQuery();
                    while (rs.next()) {
                        GroupModel bean = new GroupModel();
                        bean.setGroupSid(rs.getInt("GRP_SID"));
                        bean.setGroupName(rs.getString("GRP_NAME"));
                        bean.setGrpKbn(rs.getInt("BEG_GRPKBN"));
                        grpList.add(bean);
                    }
                    ret.get(idx).setGroups(grpList);
                }

            } catch (SQLException e) {
                throw e;
            } finally {
                JDBCUtil.closeResultSet(rs);
                JDBCUtil.closeStatement(pstmt);
            }

        }
    }

}
