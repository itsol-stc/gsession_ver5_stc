package jp.groupsession.v2.sch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.rsv.model.RsvSisDataModel;
import jp.groupsession.v2.rsv.model.RsvSisYrkModel;

/**
 * <br>[機  能] スケジュール情報に関連する施設予約情報を取得するためのDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ScheduleReserveDao extends AbstractDao {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ScheduleReserveDao.class);

    /**
     * <p>
     * デフォルトコンストラクタ
     */
    public ScheduleReserveDao() {
    }

    /**
     * <p>
     * デフォルトコンストラクタ
     *
     * @param con
     *            DBコネクション
     */
    public ScheduleReserveDao(Connection con) {
        super(con);
    }

    /**
     * <p>
     * スケジュールSIDから同時登録された施設予約情報が存在するかを判定する
     * @param scdSid スケジュールSID
     * @return true:存在する false:存在しない
     * @throws SQLException
     *             SQL実行例外
     */
    public boolean existScheduleReserveData(int scdSid)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean result = false;
        con = getCon();

        try {
            // SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select");
            sql.addSql("   RSV_SIS_YRK.RSD_SID");
            sql.addSql(" from");
            sql.addSql("   SCH_DATA,");
            sql.addSql("   RSV_SIS_YRK");
            sql.addSql(" where ");
            sql.addSql("   SCH_DATA.SCD_SID=?");
            sql.addSql(" and");
            sql.addSql("   SCH_DATA.SCD_RSSID <> -1");
            sql.addSql(" and");
            sql.addSql("   SCH_DATA.SCD_RSSID = RSV_SIS_YRK.SCD_RSSID");
            sql.setPagingValue(0, 1);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(scdSid);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            result = rs.next();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return result;
    }

    /**
     * <p>
     * スケジュールSIDから同時登録された施設予約情報を取得する
     * @param scdSid
     *            スケジュールSID
     * @return ScheduleSearchModel
     * @throws SQLException
     *             SQL実行例外
     */
    public ArrayList<Integer> getScheduleReserveData(int scdSid)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            // SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select");
            sql.addSql("   RSV_SIS_YRK.RSD_SID");
            sql.addSql(" from");
            sql.addSql("   SCH_DATA,");
            sql.addSql("   RSV_SIS_YRK");
            sql.addSql(" where ");
            sql.addSql("   SCH_DATA.SCD_SID=?");
            sql.addSql(" and");
            sql.addSql("   SCH_DATA.SCD_RSSID <> -1");
            sql.addSql(" and");
            sql.addSql("   SCH_DATA.SCD_RSSID = RSV_SIS_YRK.SCD_RSSID");
            sql.addSql(" group by");
            sql.addSql("   RSV_SIS_YRK.RSD_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(scdSid);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(new Integer(rs.getInt("RSD_SID")));
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
     * <p>
     * スケジュールSIDから同時登録された施設予約情報Mapを取得する
     * @param scdSids
     *            スケジュールSID
     * @return 施設予約SID マップl
     * @throws SQLException
     *             SQL実行例外
     */
    public Map<Integer, List<Integer>> getScheduleRsvYrkSidMap(Collection<Integer> scdSids)
            throws SQLException {
        Connection con = null;
        con = getCon();
        Map<Integer, List<Integer>> ret = new HashMap<Integer, List<Integer>>();
        if (scdSids == null || scdSids.isEmpty()) {
            return ret;
        }
        List<Integer> exeList = new ArrayList<>();
        Iterator<Integer> itr = scdSids.iterator();
        StringBuilder sb = new StringBuilder();
        sb.append(" select");
        sb.append("   SCH_DATA.SCD_SID,");
        sb.append("   RSV_SIS_YRK.RSY_SID");
        sb.append(" from");
        sb.append("   SCH_DATA,");
        sb.append("   RSV_SIS_YRK");
        sb.append(" where ");


        while (itr.hasNext()) {
            exeList.add(itr.next());
            if (exeList.size() < 500
                    && itr.hasNext()) {
                continue;
            }

            //500件毎に実行
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(sb.toString());
            sql.addSql(" SCH_DATA.SCD_SID in (");

            Iterator<Integer> exeItr = exeList.iterator();
            while (exeItr.hasNext()) {
                sql.addSql("   ?");
                sql.addLongValue(exeItr.next());

                if (exeItr.hasNext()) {
                    sql.addSql(",");
                }
            }
            sql.addSql(" )");
            sql.addSql(" and");
            sql.addSql("   SCH_DATA.SCD_RSSID <> -1");
            sql.addSql(" and");
            sql.addSql("   SCH_DATA.SCD_RSSID = RSV_SIS_YRK.SCD_RSSID");
            sql.addSql(" group by");
            sql.addSql("   RSV_SIS_YRK.RSY_SID, SCH_DATA.SCD_SID");

            try (PreparedStatement pstmt = con.prepareStatement(sql.toSqlString());) {
                sql.setParameter(pstmt);
                log__.info(sql.toLogString());
                try (ResultSet rs = pstmt.executeQuery();) {

                    while (rs.next()) {
                        Integer scdSid = Integer.valueOf(rs.getInt("SCD_SID"));
                        Integer rsdSid = Integer.valueOf(rs.getInt("RSY_SID"));
                        List<Integer> list = ret.get(scdSid);
                        if (list == null) {
                            list = new ArrayList<Integer>();
                        }
                        list.add(rsdSid);
                        ret.put(scdSid, list);
                    }

                }

            }
            exeList.clear();
        }
        return ret;

    }

    /**
     * <p>
     * スケジュールSIDから同時登録された施設予約情報（施設SID）を取得する
     * @param scdSids
     *            スケジュールSID
     * @return ScheduleSearchModel
     * @throws SQLException
     *             SQL実行例外
     */
    public Map<Integer, List<Integer>> getScheduleReserveDataMap(List<Integer> scdSids)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Map<Integer, List<Integer>> ret = new HashMap<Integer, List<Integer>>();
        con = getCon();

        try {
            if (scdSids != null && scdSids.size() > 0) {
                // SQL文
                SqlBuffer sql = new SqlBuffer();

                sql.addSql(" select");
                sql.addSql("   SCH_DATA.SCD_SID,");
                sql.addSql("   RSV_SIS_YRK.RSD_SID");
                sql.addSql(" from");
                sql.addSql("   SCH_DATA,");
                sql.addSql("   RSV_SIS_YRK");
                sql.addSql(" where ");
                sql.addSql("   SCH_DATA.SCD_SID in (");
                for (int i = 0; i < scdSids.size(); i++) {
                    sql.addSql((i > 0 ? "   ," : "   ") + String.valueOf(scdSids.get(i)));
                }
                sql.addSql("   )");
                sql.addSql(" and");
                sql.addSql("   SCH_DATA.SCD_RSSID <> -1");
                sql.addSql(" and");
                sql.addSql("   SCH_DATA.SCD_RSSID = RSV_SIS_YRK.SCD_RSSID");
                sql.addSql(" group by");
                sql.addSql("   RSV_SIS_YRK.RSD_SID, SCH_DATA.SCD_SID");

                pstmt = con.prepareStatement(sql.toSqlString());
                sql.setParameter(pstmt);
                log__.info(sql.toLogString());
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    Integer scdSid = Integer.valueOf(rs.getInt("SCD_SID"));
                    Integer rsdSid = Integer.valueOf(rs.getInt("RSD_SID"));
                    List<Integer> list = ret.get(scdSid);
                    if (list == null) {
                        list = new ArrayList<Integer>();
                    }
                    list.add(rsdSid);
                    ret.put(scdSid, list);
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
     * <p>
     * スケジュールSIDから同時登録された施設予約情報を取得する
     * @param scdSid
     *            スケジュールSID
     * @return ScheduleSearchModel
     * @throws SQLException
     *             SQL実行例外
     */
    public ArrayList<RsvSisDataModel> getScheduleReserveDataModel(int scdSid)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RsvSisDataModel> ret = new ArrayList<RsvSisDataModel>();
        con = getCon();

        try {
            // SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select");
            sql.addSql("   RSV_SIS_YRK.RSD_SID,");
            sql.addSql("   RSV_SIS_YRK.RSG_SID");
            sql.addSql(" from");
            sql.addSql("   SCH_DATA,");
            sql.addSql("   RSV_SIS_YRK");
            sql.addSql(" where ");
            sql.addSql("   SCH_DATA.SCD_SID=?");
            sql.addSql(" and");
            sql.addSql("   SCH_DATA.SCD_RSSID <> -1");
            sql.addSql(" and");
            sql.addSql("   SCH_DATA.SCD_RSSID = RSV_SIS_YRK.SCD_RSSID");
            sql.addSql(" group by");
            sql.addSql("   RSV_SIS_YRK.RSD_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(scdSid);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            RsvSisDataModel model = null;
            while (rs.next()) {
                model = new RsvSisDataModel();
                model.setRsdSid(new Integer(rs.getInt("RSD_SID")));
                model.setRsgSid(new Integer(rs.getInt("RSG_SID")));
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
     * <p>
     * スケジュールSIDから同時登録された編集権限の施設を取得する
     * @param scdSid スケジュールSID
     * @param usrSid ユーザSID
     * @return ScheduleSearchModel
     * @throws SQLException
     *             SQL実行例外
     */
    public ArrayList<Integer> getCanEditScheduleReserveData(int scdSid, int usrSid)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            // SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select");
            sql.addSql("   RSV_SIS_YRK.RSD_SID");
            sql.addSql(" from");
            sql.addSql("   SCH_DATA,");
            sql.addSql("   RSV_SIS_YRK,");
            sql.addSql("   RSV_SIS_DATA,");
            sql.addSql("   RSV_SIS_GRP");
            sql.addSql(" where ");
            sql.addSql("   SCH_DATA.SCD_SID=?");
            sql.addSql(" and");
            sql.addSql("   SCH_DATA.SCD_RSSID <> -1");

            sql.addSql(" and");
            sql.addSql("(");

            sql.addSql("  (");
            sql.addSql("   RSV_SIS_GRP.RSG_ACS_LIMIT_KBN=?");
            sql.addSql("  )");

            sql.addSql(" or");

            sql.addSql("   ( ");
            sql.addSql("    RSV_SIS_GRP.RSG_ACS_LIMIT_KBN=?");
            sql.addSql("  and ");
            sql.addSql("    exists");
            sql.addSql("    (");
            sql.addSql("      select");
            sql.addSql("        RSV_ACCESS_CONF.RSG_SID");
            sql.addSql("      from");
            sql.addSql("        RSV_ACCESS_CONF");
            sql.addSql("        left join");
            sql.addSql("          CMN_BELONGM");
            sql.addSql("        on");
            sql.addSql("          RSV_ACCESS_CONF.GRP_SID = CMN_BELONGM.GRP_SID");
            sql.addSql("      where");
            sql.addSql("        (");
            sql.addSql("          RSV_ACCESS_CONF.USR_SID = ?");
            sql.addSql("        or");
            sql.addSql("          CMN_BELONGM.USR_SID = ?");
            sql.addSql("        )");
            sql.addSql("      and");
            sql.addSql("        RSV_ACCESS_CONF.RAC_AUTH = ?");
            sql.addSql("      and");
            sql.addSql("        RSV_ACCESS_CONF.RSG_SID = RSV_SIS_GRP.RSG_SID");
            sql.addSql("    )");
            sql.addSql("  )");

            sql.addSql(" or");

            sql.addSql("  (");
            sql.addSql("    RSV_SIS_GRP.RSG_ACS_LIMIT_KBN=?");
            sql.addSql("  and ");
            sql.addSql("    not exists");
            sql.addSql("    (");
            sql.addSql("      select");
            sql.addSql("        RSV_ACCESS_CONF.RSG_SID");
            sql.addSql("      from");
            sql.addSql("        RSV_ACCESS_CONF");
            sql.addSql("        left join");
            sql.addSql("          CMN_BELONGM");
            sql.addSql("        on");
            sql.addSql("          RSV_ACCESS_CONF.GRP_SID = CMN_BELONGM.GRP_SID");
            sql.addSql("      where");
            sql.addSql("        (");
            sql.addSql("          RSV_ACCESS_CONF.USR_SID = ?");
            sql.addSql("        or");
            sql.addSql("          CMN_BELONGM.USR_SID = ?");
            sql.addSql("        )");
            sql.addSql("      and");
            sql.addSql("        RSV_ACCESS_CONF.RSG_SID = RSV_SIS_GRP.RSG_SID");
            sql.addSql("    )");
            sql.addSql("  )");
            sql.addSql(")");
            sql.addSql(" and");
            sql.addSql("   SCH_DATA.SCD_RSSID = RSV_SIS_YRK.SCD_RSSID");
            sql.addSql(" and");
            sql.addSql("   RSV_SIS_YRK.RSD_SID = RSV_SIS_DATA.RSD_SID");
            sql.addSql(" and");
            sql.addSql("   RSV_SIS_DATA.RSG_SID = RSV_SIS_GRP.RSG_SID");
            sql.addSql(" group by");
            sql.addSql("   RSV_SIS_YRK.RSD_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(scdSid);
            sql.addIntValue(GSConstReserve.RSV_ACCESS_MODE_FREE);
            sql.addIntValue(GSConstReserve.RSV_ACCESS_MODE_LIMIT);
            sql.addIntValue(usrSid);
            sql.addIntValue(usrSid);
            sql.addIntValue(GSConstReserve.RSV_ACCESS_KBN_WRITE);
            sql.addIntValue(GSConstReserve.RSV_ACCESS_MODE_PERMIT);
            sql.addIntValue(usrSid);
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(new Integer(rs.getInt("RSD_SID")));
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
     * <p>
     * スケジュール拡張SIDから同時登録された施設予約情報を取得する
     * @param sceSid
     *            スケジュールSID
     * @return ScheduleSearchModel
     * @throws SQLException
     *             SQL実行例外
     */
    public ArrayList<Integer> getScheduleReserveDataFromExSid(int sceSid)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            // SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select");
            sql.addSql("   RSV_SIS_YRK.RSD_SID");
            sql.addSql(" from");
            sql.addSql("  (");
            sql.addSql("  select");
            sql.addSql("    SCH_DATA.SCD_SID,");
            sql.addSql("    SCH_DATA.SCD_RSSID");
            sql.addSql("  from");
            sql.addSql("    SCH_DATA,");
            sql.addSql("    SCH_EXDATA");
            sql.addSql("  where");
            sql.addSql("    SCH_EXDATA.SCE_SID = SCH_DATA.SCE_SID");
            sql.addSql("  and");
            sql.addSql("    SCH_EXDATA.SCE_SID = ?");
            sql.addSql("  ) EXDATA,");
            sql.addSql("   RSV_SIS_YRK");
            sql.addSql(" where");
            sql.addSql("   EXDATA.SCD_RSSID > 0");
            sql.addSql(" and");
            sql.addSql("   EXDATA.SCD_RSSID = RSV_SIS_YRK.SCD_RSSID");
            sql.addSql(" group by");
            sql.addSql("   RSV_SIS_YRK.RSD_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sceSid);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(new Integer(rs.getInt("RSD_SID")));
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
     * <p>
     * スケジュール拡張SIDから同時登録された施設予約情報を取得する
     * @param sceSid スケジュールSID
     * @param usrSid ユーザSID
     * @return ScheduleSearchModel
     * @throws SQLException
     *             SQL実行例外
     */
    public ArrayList<Integer> getCanEditScheduleReserveDataFromExSid(
            int sceSid, int usrSid)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            // SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select");
            sql.addSql("   RSV_SIS_YRK.RSD_SID");
            sql.addSql(" from");
            sql.addSql("  (");
            sql.addSql("  select");
            sql.addSql("    SCH_DATA.SCD_SID,");
            sql.addSql("    SCH_DATA.SCD_RSSID");
            sql.addSql("  from");
            sql.addSql("    SCH_DATA,");
            sql.addSql("    SCH_EXDATA");
            sql.addSql("  where");
            sql.addSql("    SCH_EXDATA.SCE_SID = SCH_DATA.SCE_SID");
            sql.addSql("  and");
            sql.addSql("    SCH_EXDATA.SCE_SID = ?");
            sql.addSql("  ) EXDATA,");
            sql.addSql("   RSV_SIS_YRK,");
            sql.addSql("   RSV_SIS_DATA,");
            sql.addSql("   RSV_SIS_GRP");

            sql.addSql(" where");
            sql.addSql("   EXDATA.SCD_RSSID > 0");
            sql.addSql(" and");
            sql.addSql("   EXDATA.SCD_RSSID = RSV_SIS_YRK.SCD_RSSID");
            sql.addSql(" and");
            sql.addSql("   RSV_SIS_YRK.RSD_SID = RSV_SIS_DATA.RSD_SID");
            sql.addSql(" and");
            sql.addSql("   RSV_SIS_DATA.RSG_SID = RSV_SIS_GRP.RSG_SID");

            sql.addSql(" and");
            sql.addSql("(");

            sql.addSql("  (");
            sql.addSql("   RSV_SIS_GRP.RSG_ACS_LIMIT_KBN=?");
            sql.addSql("  )");

            sql.addSql(" or");

            sql.addSql("   ( ");
            sql.addSql("    RSV_SIS_GRP.RSG_ACS_LIMIT_KBN=?");
            sql.addSql("  and ");
            sql.addSql("    exists");
            sql.addSql("    (");
            sql.addSql("      select");
            sql.addSql("        RSV_ACCESS_CONF.RSG_SID");
            sql.addSql("      from");
            sql.addSql("        RSV_ACCESS_CONF");
            sql.addSql("        left join");
            sql.addSql("          CMN_BELONGM");
            sql.addSql("        on");
            sql.addSql("          RSV_ACCESS_CONF.GRP_SID = CMN_BELONGM.GRP_SID");
            sql.addSql("      where");
            sql.addSql("        RSV_ACCESS_CONF.RSG_SID = RSV_SIS_GRP.RSG_SID");
            sql.addSql("      and");
            sql.addSql("        (");
            sql.addSql("          RSV_ACCESS_CONF.USR_SID = ?");
            sql.addSql("        or");
            sql.addSql("          CMN_BELONGM.USR_SID = ?");
            sql.addSql("        )");
            sql.addSql("      and");
            sql.addSql("        RSV_ACCESS_CONF.RAC_AUTH = ?");
            sql.addSql("    )");
            sql.addSql("  )");

            sql.addSql(" or");

            sql.addSql("  (");
            sql.addSql("    RSV_SIS_GRP.RSG_ACS_LIMIT_KBN=?");
            sql.addSql("  and ");
            sql.addSql("    not exists");
            sql.addSql("    (");
            sql.addSql("      select");
            sql.addSql("        RSV_ACCESS_CONF.RSG_SID");
            sql.addSql("      from");
            sql.addSql("        RSV_ACCESS_CONF");
            sql.addSql("        left join");
            sql.addSql("          CMN_BELONGM");
            sql.addSql("        on");
            sql.addSql("          RSV_ACCESS_CONF.GRP_SID = CMN_BELONGM.GRP_SID");
            sql.addSql("      where");
            sql.addSql("        RSV_ACCESS_CONF.RSG_SID = RSV_SIS_GRP.RSG_SID");
            sql.addSql("      and");
            sql.addSql("        (");
            sql.addSql("          RSV_ACCESS_CONF.USR_SID = ?");
            sql.addSql("        or");
            sql.addSql("          CMN_BELONGM.USR_SID = ?");
            sql.addSql("        )");
            sql.addSql("      and");
            sql.addSql("        RSV_ACCESS_CONF.RAC_AUTH = ?");
            sql.addSql("    )");
            sql.addSql("  )");
            sql.addSql(")");
            sql.addSql(" group by");
            sql.addSql("   RSV_SIS_YRK.RSD_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sceSid);
            sql.addIntValue(GSConstReserve.RSV_ACCESS_MODE_FREE);
            sql.addIntValue(GSConstReserve.RSV_ACCESS_MODE_LIMIT);
            sql.addIntValue(usrSid);
            sql.addIntValue(usrSid);
            sql.addIntValue(GSConstReserve.RSV_ACCESS_KBN_WRITE);
            sql.addIntValue(GSConstReserve.RSV_ACCESS_MODE_PERMIT);
            sql.addIntValue(usrSid);
            sql.addIntValue(usrSid);
            sql.addIntValue(GSConstReserve.RSV_ACCESS_KBN_WRITE);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(new Integer(rs.getInt("RSD_SID")));
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
     * <p>
     * スケジュール拡張SIDから同時登録された施設予約情報を取得する
     * @param sceSid
     *            スケジュールSID
     * @return ScheduleSearchModel
     * @throws SQLException
     *             SQL実行例外
     */
    public ArrayList<Integer> getScheduleReserveSidFromExSid(int sceSid)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            // SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select");
            sql.addSql("   RSV_SIS_YRK.RSY_SID");
            sql.addSql(" from");
            sql.addSql("  (");
            sql.addSql("  select");
            sql.addSql("    SCH_DATA.SCD_SID,");
            sql.addSql("    SCH_DATA.SCD_RSSID");
            sql.addSql("  from");
            sql.addSql("    SCH_DATA,");
            sql.addSql("    SCH_EXDATA");
            sql.addSql("  where");
            sql.addSql("    SCH_EXDATA.SCE_SID = SCH_DATA.SCE_SID");
            sql.addSql("  and");
            sql.addSql("    SCH_EXDATA.SCE_SID = ?");
            sql.addSql("  ) EXDATA,");
            sql.addSql("   RSV_SIS_YRK");
            sql.addSql(" where");
            sql.addSql("   EXDATA.SCD_RSSID > 0");
            sql.addSql(" and");
            sql.addSql("   EXDATA.SCD_RSSID = RSV_SIS_YRK.SCD_RSSID");
            sql.addSql(" group by");
            sql.addSql("   RSV_SIS_YRK.RSY_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sceSid);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getInt("RSY_SID"));
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
     * <br>[機  能] 対象施設予約の公開対象に含まれるかを判定
     * <br>[解  説]
     * <br>[備  考]
     * @param rsySid 対象施設予約SID
     * @param userSid ユーザSID
     * @return true:可能 false:不可能
     */
    public boolean checkRsvYrkPubTarget(int rsySid, int userSid) throws SQLException {

        int count = 0;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(*) CNT");
            sql.addSql(" from");
            sql.addSql("   RSV_DATA_PUB");
            sql.addSql(" where ");
            sql.addSql("   RSY_SID=?");
            sql.addSql(" and");
            sql.addSql("   (");
            sql.addSql("     (");
            sql.addSql("     RDP_TYPE=?");
            sql.addSql("   and");
            sql.addSql("     RDP_PSID=?");
            sql.addSql("     )");
            sql.addSql("   or");
            sql.addSql("     (");
            sql.addSql("     RDP_TYPE=?");
            sql.addSql("   and");
            sql.addSql("     RDP_PSID in");
            sql.addSql("       (");
            sql.addSql("       select");
            sql.addSql("         GRP_SID");
            sql.addSql("       from");
            sql.addSql("         CMN_BELONGM");
            sql.addSql("       where");
            sql.addSql("         USR_SID=?");
            sql.addSql("       )");
            sql.addSql("     )");
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rsySid);
            sql.addIntValue(GSConstSchedule.USER_KBN_USER);
            sql.addIntValue(userSid);
            sql.addIntValue(GSConstSchedule.USER_KBN_GROUP);
            sql.addIntValue(userSid);

            log__.info(sql.toLogString());
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
        return count > 0;
    }

    /**
     * <br>[機  能] 指定された施設予約SIDの施設SIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param rsySid 施設予約SID
     * @return 施設SID
     * @throws SQLException SQL実行例外
     */
    public int getSisDataSid(int rsySid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        int ret = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSD_SID");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_YRK");
            sql.addSql(" where");
            sql.addSql("   RSY_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rsySid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = rs.getInt("RSD_SID");
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
     * <br>[機  能] 指定された施設が属する施設グループの管理者かカウントする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param rsdSid 施設SID
     * @param userSid ユーザSID
     * @return true:施設グループ管理者
     * @throws SQLException SQL実行例外
     */
    public boolean isGroupAdmin(int rsdSid, int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        boolean result = false;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("   select");
            sql.addSql("     count(RSV_SIS_ADM.USR_SID) as cnt");
            sql.addSql("   from");
            sql.addSql("     RSV_SIS_DATA,");
            sql.addSql("     RSV_SIS_GRP,");
            sql.addSql("     RSV_SIS_ADM");
            sql.addSql("   where");
            sql.addSql("     RSV_SIS_DATA.RSD_SID = ?");
            sql.addSql("   and");
            sql.addSql("     RSV_SIS_GRP.RSG_ADM_KBN = ?");
            sql.addSql("   and");
            sql.addSql("     RSV_SIS_GRP.RSG_SID = RSV_SIS_DATA.RSG_SID");
            sql.addSql("   and");
            sql.addSql("     RSV_SIS_GRP.RSG_SID = RSV_SIS_ADM.RSG_SID");
            sql.addSql("   and (");
            sql.addSql("         (");
            sql.addSql("           RSV_SIS_ADM.USR_SID = ?");
            sql.addSql("           and RSV_SIS_ADM.GRP_SID = -1");
            sql.addSql("         )");
            sql.addSql("         or (");
            sql.addSql("           RSV_SIS_ADM.USR_SID = -1");
            sql.addSql("           and RSV_SIS_ADM.GRP_SID in (");
            sql.addSql("             select");
            sql.addSql("               GRP_SID");
            sql.addSql("             from");
            sql.addSql("               CMN_BELONGM");
            sql.addSql("             where");
            sql.addSql("               USR_SID = ?");
            sql.addSql("           )");
            sql.addSql("         )");
            sql.addSql("   )");

            sql.addIntValue(rsdSid);
            sql.addIntValue(GSConstReserve.RSG_ADM_KBN_OK);
            sql.addIntValue(userSid);
            sql.addIntValue(userSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                result = rs.getInt("cnt") > 0;
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
     * <br>[機  能] 指定されたスケジュールリレーションSIDの
     * <br>         スケジュール拡張SIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param scdRssid スケジュールリレーションSID
     * @return sceSid スケジュール拡張SID
     * @throws SQLException 例外
     */
    public int selectSceSid(int scdRssid) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        ResultSet rs = null;
        con = getCon();
        int sceSid = -1;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SCE_SID");
            sql.addSql(" from");
            sql.addSql("   SCH_DATA");
            sql.addSql(" where ");
            sql.addSql("   SCD_RSSID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(scdRssid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                sceSid = rs.getInt("SCE_SID");
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return sceSid;
    }

    /**
     * <br>[機  能] ユーザのスケジュールが登録されているかチェックする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param scdRsSid スケジュールリレーションSID
     * @param usrSid ユーザSID
     * @return ret true:登録されている false 登録されていない
     * @throws SQLException 例外
     */
    public boolean isUsingUserFromRsSid(int scdRsSid, int usrSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        ResultSet rs = null;
        con = getCon();
        boolean ret = false;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(SCD_SID) as cnt");
            sql.addSql(" from");
            sql.addSql("   SCH_DATA");
            sql.addSql(" where");
            sql.addSql("   SCD_RSSID = ?");
            sql.addSql(" and");
            sql.addSql("   SCD_USR_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(scdRsSid);
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                if (rs.getInt("cnt") > 0) {
                    ret = true;
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
     * <br>[機  能] 施設予約情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rsySid 予約SID
     * @return 施設予約情報
     * @throws SQLException SQL実行時例外
     */
    public RsvSisYrkModel getRsvYrkData(int rsySid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        RsvSisYrkModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RSY_SID,");
            sql.addSql("   RSD_SID,");
            sql.addSql("   RSY_YGRP_SID,");
            sql.addSql("   RSY_MOK,");
            sql.addSql("   RSY_FR_DATE,");
            sql.addSql("   RSY_TO_DATE,");
            sql.addSql("   RSY_BIKO,");
            sql.addSql("   RSY_AUID,");
            sql.addSql("   RSY_ADATE,");
            sql.addSql("   RSY_EUID,");
            sql.addSql("   RSY_EDATE,");
            sql.addSql("   SCD_RSSID,");
            sql.addSql("   RSY_EDIT,");
            sql.addSql("   RSY_PUBLIC,");
            sql.addSql("   RSR_RSID,");
            sql.addSql("   RSY_APPR_STATUS,");
            sql.addSql("   RSY_APPR_KBN,");
            sql.addSql("   RSY_APPR_UID,");
            sql.addSql("   RSY_APPR_DATE");
            sql.addSql(" from ");
            sql.addSql("   RSV_SIS_YRK");
            sql.addSql(" where");
            sql.addSql("   RSY_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rsySid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret = new RsvSisYrkModel();
                ret.setRsySid(rs.getInt("RSY_SID"));
                ret.setRsdSid(rs.getInt("RSD_SID"));
                ret.setRsyYgrpSid(rs.getInt("RSY_YGRP_SID"));
                ret.setRsyMok(rs.getString("RSY_MOK"));
                ret.setRsyFrDate(UDate.getInstanceTimestamp(rs.getTimestamp("RSY_FR_DATE")));
                ret.setRsyToDate(UDate.getInstanceTimestamp(rs.getTimestamp("RSY_TO_DATE")));
                ret.setRsyBiko(rs.getString("RSY_BIKO"));
                ret.setRsyAuid(rs.getInt("RSY_AUID"));
                ret.setRsyAdate(UDate.getInstanceTimestamp(rs.getTimestamp("RSY_ADATE")));
                ret.setRsyEuid(rs.getInt("RSY_EUID"));
                ret.setRsyEdate(UDate.getInstanceTimestamp(rs.getTimestamp("RSY_EDATE")));
                ret.setScdRsSid(rs.getInt("SCD_RSSID"));
                ret.setRsyEdit(rs.getInt("RSY_EDIT"));
                ret.setRsyPublic(rs.getInt("RSY_PUBLIC"));
                ret.setRsrRsid(rs.getInt("RSR_RSID"));
                ret.setRsyApprStatus(rs.getInt("RSY_APPR_STATUS"));
                ret.setRsyApprKbn(rs.getInt("RSY_APPR_KBN"));
                ret.setRsyApprUid(rs.getInt("RSY_APPR_UID"));
                ret.setRsyApprDate(UDate.getInstanceTimestamp(rs.getTimestamp("RSY_APPR_DATE")));
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
