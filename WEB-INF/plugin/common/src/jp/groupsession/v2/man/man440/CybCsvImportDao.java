package jp.groupsession.v2.man.man440;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.man.man491.CybCsvSchModel;
import jp.groupsession.v2.sch.model.SchDataModel;
import jp.groupsession.v2.usr.GSConstUser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] サイボウズLiveデータ移行 各インポート画面で使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CybCsvImportDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CybCsvImportDao.class);

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     */
    public CybCsvImportDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] ユーザ名が存在するユーザSIDを取得する
     * <br>[解  説] 該当するユーザが見つからない場合はシステムユーザを返す
     * <br>[備  考]
     * @param nameMap ユーザ名マップ
     * @throws SQLException SQL実行時例外
     */
    public void setUserSidToNameMap(HashMap<String, Integer> nameMap)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        if (nameMap == null || nameMap.isEmpty()) {
            return;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CMN_USRM.USR_SID as USR_SID, ");
            sql.addSql("   CMN_USRM.USR_UKO_FLG as USR_UKO_FLG, ");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI, ");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI ");
            sql.addSql(" from");
            sql.addSql("   CMN_USRM, ");
            sql.addSql("   CMN_USRM_INF ");
            sql.addSql(" where ");
            sql.addSql("   CMN_USRM.USR_JKBN = ? ");
            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);
            sql.addSql(" and ");
            sql.addSql("   CMN_USRM_INF.USI_SEI || ' ' || CMN_USRM_INF.USI_MEI in (");
            int idx = 0;
            for (String name : nameMap.keySet()) {
                if (idx == 0) {
                    sql.addSql("     ?");
                } else {
                    sql.addSql("     ,?");
                }
                sql.addStrValue(name);
                idx++;
            }
            sql.addSql("   )");
            sql.addSql(" and ");
            sql.addSql("   CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID ");


            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                // 一致するユーザ名が存在する場合のみユーザSIDをセットする
                String  userName = rs.getString("USI_SEI") + " " + rs.getString("USI_MEI");
                if (nameMap.containsKey(userName)) {
                    Integer userSid  = Integer.valueOf(rs.getInt("USR_SID"));
                    nameMap.put(userName, userSid);
                }
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return;
    }

    /**
     * <br>[機  能] 施設名が存在する施設SIDを取得する
     * <br>[解  説] 該当する施設情報が見つからない場合は更新しない
     * <br>[備  考]
     * @param nameMap 施設名マップ
     * @throws SQLException SQL実行時例外
     */
    public void setRsdSidToSisetsuMap(HashMap<String, Integer> nameMap)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        if (nameMap == null || nameMap.isEmpty()) {
            return;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSD_SID, ");
            sql.addSql("   RSD_NAME ");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_DATA ");
            sql.addSql(" where ");
            sql.addSql("   RSD_NAME in (");
            int idx = 0;
            for (String name : nameMap.keySet()) {
                if (idx == 0) {
                    sql.addSql("     ?");
                } else {
                    sql.addSql("     ,?");
                }
                sql.addStrValue(name);
                idx++;
            }
            sql.addSql("   )");

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                // 一致する施設名が存在する場合のみ施設SIDをセットする
                String rsdName = rs.getString("RSD_NAME");
                if (nameMap.containsKey(rsdName)) {
                    Integer rsdSid  = Integer.valueOf(rs.getInt("RSD_SID"));
                    nameMap.put(rsdName, rsdSid);
                }
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return;
    }

    /**
     * <br>[機  能] インポート用スケジュールと一致すると思われるスケジュール一覧を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param schList インポート用スケジュール一覧
     * @throws SQLException SQL実行時例外
     * @return スケジュール一覧
     */
    public ArrayList<SchDataModel> getMatchSchList(ArrayList<CybCsvSchModel> schList)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<SchDataModel> ret = new ArrayList<SchDataModel>();
        con = getCon();

        if (schList == null || schList.isEmpty()) {
            return ret;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SCD_SID, ");
            sql.addSql("   SCD_USR_SID, ");
            sql.addSql("   SCD_GRP_SID, ");
            sql.addSql("   SCD_USR_KBN, ");
            sql.addSql("   SCD_TITLE, ");
            sql.addSql("   SCD_FR_DATE, ");
            sql.addSql("   SCD_TO_DATE ");
            sql.addSql(" from");
            sql.addSql("   SCH_DATA ");
            sql.addSql(" where ");
            sql.addSql("   SCD_USR_KBN = ?");
            sql.addSql(" and");

            // インポートはグループでの登録なし
            sql.addIntValue(GSConstSchedule.USER_KBN_USER);

            int idx = 0;
            for (SchDataModel mdl : schList) {
                if (idx > 0) {
                    sql.addSql("   or");
                }
                sql.addSql("   (");
                sql.addSql("     SCD_TITLE = ?");
                sql.addSql("     and SCD_FR_DATE = ?");
                sql.addSql("     and SCD_TO_DATE = ?");
                sql.addSql("   )");
                sql.addStrValue(mdl.getScdTitle());
                sql.addDateValue(mdl.getScdFrDate());
                sql.addDateValue(mdl.getScdToDate());
                idx++;
            }

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                SchDataModel mdl = new SchDataModel();
                mdl.setScdSid(rs.getInt("SCD_SID"));
                mdl.setScdUsrSid(rs.getInt("SCD_USR_SID"));
                mdl.setScdGrpSid(rs.getInt("SCD_GRP_SID"));
                mdl.setScdUsrKbn(rs.getInt("SCD_USR_KBN"));
                mdl.setScdTitle(rs.getString("SCD_TITLE"));

                Date frDate = rs.getDate("SCD_FR_DATE");
                Date toDate = rs.getDate("SCD_TO_DATE");
                mdl.setScdFrDate(UDate.getInstanceSqlDate(frDate));
                mdl.setScdToDate(UDate.getInstanceSqlDate(toDate));
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
     * <br>[機  能] フォーラムに所属するユーザSID一覧を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param bfiSid フォーラムSID
     * @return フォーラムメンバーのユーザSID一覧
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<Integer> getForumMemberList(int bfiSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select");
            sql.addSql("   CMN_USRM.USR_SID as USR_SID,");
            sql.addSql("   CMN_USRM.USR_UKO_FLG as USR_UKO_FLG");

            sql.addSql(" from");
            sql.addSql("   CMN_USRM");
            sql.addSql(" where");
            sql.addSql("   CMN_USRM.USR_JKBN = ?");
            sql.addSql(" and");
            //ユーザSID < 100は除外
            sql.addSql("   CMN_USRM.USR_SID > ?");

            sql.addSql(" and");
            sql.addSql(" (");
            sql.addSql("   CMN_USRM.USR_SID in (");
            sql.addSql("     select");
            sql.addSql("       BBS_FOR_MEM.USR_SID as USR_SID");
            sql.addSql("     from");
            sql.addSql("       BBS_FOR_MEM ");
            sql.addSql("     where ");
            sql.addSql("       BBS_FOR_MEM.BFI_SID = ? ");

            sql.addSql("   )");
            sql.addSql("   or");
            sql.addSql("   CMN_USRM.USR_SID in (");
            sql.addSql("     select");
            sql.addSql("       CMN_BELONGM.USR_SID");
            sql.addSql("     from");
            sql.addSql("       CMN_BELONGM,");
            sql.addSql("       BBS_FOR_MEM");
            sql.addSql("     where");
            sql.addSql("       BBS_FOR_MEM.BFI_SID = ?");
            sql.addSql("     and");
            sql.addSql("       BBS_FOR_MEM.GRP_SID = CMN_BELONGM.GRP_SID");
            sql.addSql("   )");
            sql.addSql(" )");


            sql.addIntValue(GSConst.JTKBN_TOROKU);
            sql.addIntValue(GSConstUser.USER_RESERV_SID);
            sql.addIntValue(bfiSid);
            sql.addIntValue(bfiSid);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(Integer.valueOf(rs.getInt("USR_SID")));
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
     * <br>[機  能] フォーラムに所属するユーザSID一覧を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param bfiSid フォーラムSID
     * @return フォーラムメンバーのユーザSID一覧
     * @throws SQLException SQL実行時例外
     */
    public UDate getForumLastUpdate(int bfiSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        UDate ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select");
            sql.addSql("   BBS_THRE_SUM.BTS_WRT_DATE as BTS_WRT_DATE");
            sql.addSql(" from");
            sql.addSql("   BBS_THRE_SUM,");
            sql.addSql("   BBS_THRE_INF");
            sql.addSql(" where");
            sql.addSql("   BBS_THRE_INF.BFI_SID = ?");
            sql.addSql(" and");
            sql.addSql("   BBS_THRE_SUM.BTI_SID = BBS_THRE_INF.BTI_SID");
            sql.addSql(" order by BBS_THRE_SUM.BTS_WRT_DATE desc");

            sql.addIntValue(bfiSid);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                Date date = rs.getDate("BTS_WRT_DATE");
                ret = UDate.getInstanceSqlDate(date);
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
