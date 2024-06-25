package jp.groupsession.v2.rss.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstRss;
import jp.groupsession.v2.cmn.GSFeedList;
import jp.groupsession.v2.cmn.ITempFileUtil;
import jp.groupsession.v2.rss.model.RssModel;
import jp.groupsession.v2.usr.GSConstUser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>RSSリーダーで使用するDAOクラス
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RssDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RssDao.class);

    /**
     * <p>Default Constructor
     */
    public RssDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public RssDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] RSS集計情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param itmp TempFileUtil
     * @param page ページ
     * @param maxCnt １ページの最大表示件数
     * @param userSid ユーザSID
     * @return RSS集計情報一覧
     * @throws Exception 実行例外
     */
    public List<RssModel> getRssCountDataList(ITempFileUtil itmp, int page, int maxCnt, int userSid)
    throws Exception {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<RssModel> ret = new ArrayList<RssModel>();
        con = getCon();

        boolean autoCommit = con.getAutoCommit();
        JDBCUtil.autoCommitOff(con);

        try {
            //RSSSIDとランキング順位のMappingを取得
            Map<Integer, Integer> rankMap = getUserCountMap();
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSS_INFOM.RSS_SID as RSS_SID,");
            sql.addSql("   RSS_INFOM.RSM_FEEDDATA as FEEDDATA,");
            sql.addSql("   RSS_INFOM.RSM_URL_FEED as feedUrl,");
            sql.addSql("   count(RSS_DATA.USR_SID) as userCount,");
            sql.addSql("   RSSKOUDOKU.koudokuCount");
            sql.addSql(" from");
            sql.addSql("   CMN_USRM,");
            sql.addSql("   RSS_DATA,");
            sql.addSql("   RSS_INFOM");
            sql.addSql("   left join (");
            sql.addSql("     select count(*) as koudokuCount,");
            sql.addSql("     RSS_DATA.RSS_SID");
            sql.addSql("     from RSS_DATA");
            sql.addSql("     where RSS_DATA.USR_SID=?");
            sql.addSql("     group by RSS_SID");
            sql.addSql("     ) as RSSKOUDOKU");
            sql.addSql("     on");
            sql.addSql("     RSS_INFOM.RSS_SID = RSSKOUDOKU.RSS_SID");
            sql.addSql(" where");
            sql.addSql("   RSS_INFOM.RSS_SID = RSS_DATA.RSS_SID");
            sql.addSql(" and");
            sql.addSql("   RSS_DATA.USR_SID = CMN_USRM.USR_SID");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM.USR_JKBN = ?");
            sql.addSql(" group by");
            sql.addSql("    RSS_INFOM.RSS_SID,");
            sql.addSql("    RSS_INFOM.RSM_FEEDDATA,");
            sql.addSql("    RSS_INFOM.RSM_URL_FEED,");
            sql.addSql("    RSSKOUDOKU.koudokuCount");
            sql.addSql("  order by");
            sql.addSql("    userCount desc");
            sql.addIntValue(userSid);
            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString(),
                                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                                        ResultSet.CONCUR_READ_ONLY);
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (page > 1) {
                rs.absolute((page - 1) * maxCnt);
            }

            for (int i = 0; rs.next() && i < maxCnt; i++) {
                RssModel model = new RssModel();
                model.setRssSid(rs.getInt("RSS_SID"));
                model.setRsdUrlFeed(rs.getString("feedUrl"));
                model.setUserCount(rs.getInt("userCount"));
                model.setKoudokuCount(rs.getInt("koudokuCount"));
                Integer ranking = rankMap.get(new Integer(model.getRssSid()));
                if (ranking != null) {
                    model.setRanking(ranking.toString());
                }
                model.setFeedData((GSFeedList) itmp.readLobObjectFieldInMem(rs, "FEEDDATA"));
                ret.add(model);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
            con.setAutoCommit(autoCommit);
        }
        return ret;
    }

    /**
     * <br>[機  能] RSS集計情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param page ページ
     * @param maxCnt １ページの最大表示件数
     * @param sort ソートキー
     * @param order オーダーキー
     * @return RSS集計情報一覧
     * @throws Exception 実行例外
     */
    public List<RssModel> getRssCountDataAllList(int page, int maxCnt, int sort, int order)
    throws Exception {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<RssModel> ret = new ArrayList<RssModel>();
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
//            sql.addSql(" select");
//            sql.addSql("   RSS_INFOM.RSS_SID as RSS_SID,");
//            sql.addSql("   RSS_DATA.RSD_URL,");
//            sql.addSql("   RSS_DATA.RSD_URL_FEED,");
//            sql.addSql("   count(RSS_DATA.USR_SID) as userCount,");
//            sql.addSql("   RSS_INFOM.RSM_UPDATE_TIME,");
//            sql.addSql("   RSSTITLE.RSD_TITLE");
//            sql.addSql(" from");
//            sql.addSql("   RSS_DATA");
//            sql.addSql("   left join (");
//            sql.addSql("     select ");
//            sql.addSql("     RSS_DATA.RSS_SID,");
//            sql.addSql("     RSS_DATA.RSD_TITLE");
//            sql.addSql("     from RSS_DATA");
//            sql.addSql("     where ROWNUM=1");
//            sql.addSql("   ) as RSSTITLE");
//            sql.addSql("   on");
//            sql.addSql("   RSS_DATA.RSS_SID = RSSTITLE.RSS_SID,");
//
//            sql.addSql("   CMN_USRM,");
//            sql.addSql("   RSS_INFOM");
//            sql.addSql(" where");
//            sql.addSql("   RSS_INFOM.RSS_SID = RSS_DATA.RSS_SID");
//            sql.addSql(" and");
//            sql.addSql("   RSS_DATA.USR_SID = CMN_USRM.USR_SID");
//            sql.addSql(" and");
//            sql.addSql("   CMN_USRM.USR_SID > 100");
//            sql.addSql(" and");
//            sql.addSql("   CMN_USRM.USR_JKBN = 0");
//            sql.addSql("  group by");
//            sql.addSql("    RSS_INFOM.RSS_SID,");
//            sql.addSql("    RSS_DATA.RSD_URL,");
//            sql.addSql("    RSS_DATA.RSD_URL_FEED,");
//            sql.addSql("    RSS_INFOM.RSM_UPDATE_TIME");

            sql.addSql(" select");
            sql.addSql("   RSS_INFOM.RSS_SID as RSS_SID,");
            sql.addSql("   RSS_DATA.RSD_URL,");
            sql.addSql("   RSS_DATA.RSD_URL_FEED,");
            sql.addSql("   count(RSS_DATA.USR_SID) as userCount,");
            sql.addSql("   RSS_INFOM.RSM_UPDATE_TIME,");
            sql.addSql("   RSSTITLE.RSD_TITLE");
            sql.addSql(" from");
            sql.addSql("   RSS_DATA");
            sql.addSql("   left join (");
            sql.addSql("     select distinct");
            sql.addSql("     (RSS_DATA.RSS_SID),");
            sql.addSql("     RSS_DATA.RSD_TITLE");
            sql.addSql("     from RSS_DATA");
            sql.addSql("   ) as RSSTITLE");
            sql.addSql("   on");
            sql.addSql("   RSS_DATA.RSS_SID = RSSTITLE.RSS_SID,");

            sql.addSql("   CMN_USRM,");
            sql.addSql("   RSS_INFOM");
            sql.addSql(" where");
            sql.addSql("   RSS_INFOM.RSS_SID = RSS_DATA.RSS_SID");
            sql.addSql(" and");
            sql.addSql("   RSS_DATA.USR_SID = CMN_USRM.USR_SID");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM.USR_SID > 100");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM.USR_JKBN = 0");
            sql.addSql(" group by");
            sql.addSql("   RSS_INFOM.RSS_SID,");
            sql.addSql("   RSS_DATA.RSD_URL,");
            sql.addSql("   RSS_DATA.RSD_URL_FEED,");
            sql.addSql("   RSS_INFOM.RSM_UPDATE_TIME,");
            sql.addSql("   RSSTITLE.RSD_TITLE");




            String orderStr = "";
            // オーダー
            if (order == GSConstRss.ORDER_KEY_ASC) {
                orderStr = "  asc";
            } else if (order == GSConstRss.ORDER_KEY_DESC) {
                orderStr = "  desc";
            }

            sql.addSql(" order by ");
            switch (sort) {

            case GSConstRss.RSS_SORT_NAME:
                sql.addSql("   RSD_TITLE " + orderStr);
                break;

            case GSConstRss.RSS_SORT_USER_COUNT:
                sql.addSql("   userCount " + orderStr);
                break;

            case GSConstRss.RSS_SORT_LAST_UPDATE:
                sql.addSql("   RSM_UPDATE_TIME " + orderStr);
                break;

            default:
                sql.addSql("   RSM_UPDATE_TIME " + GSConstRss.ORDER_KEY_ASC);
                break;

            }

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString(),
                                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                                        ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (page > 1) {
                rs.absolute((page - 1) * maxCnt);
            }

            for (int i = 0; rs.next() && i < maxCnt; i++) {
                RssModel model = new RssModel();
                model.setRssSid(rs.getInt("RSS_SID"));
                model.setRsdTitle(rs.getString("RSD_TITLE"));
                model.setRsdUrl(rs.getString("RSD_URL"));
                model.setRsdUrlFeed(rs.getString("RSD_URL_FEED"));
                model.setUserCount(rs.getInt("userCount"));
                String date = UDateUtil.getSlashYYMD(
                       UDate.getInstanceTimestamp(rs.getTimestamp("RSM_UPDATE_TIME")));
                String time = UDateUtil.getSeparateHMS(
                        UDate.getInstanceTimestamp(rs.getTimestamp("RSM_UPDATE_TIME")));
                model.setDspFeedUpdateTime(date + " " + time);
                ret.add(model);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }

    /**
     * <br>[機  能] RSS登録ユーザ情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param page ページ
     * @param maxCnt １ページの最大表示件数
     * @param rssSid RSSSID
     * @return RSS集計情報一覧
     * @throws Exception 実行例外
     */
    public List<RssModel> getRssUserList(int page, int maxCnt, int rssSid)
    throws Exception {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<RssModel> ret = new ArrayList<RssModel>();
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSS_DATA.RSS_SID as RSS_SID,");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI");
            sql.addSql(" from");
            sql.addSql("   RSS_DATA,");
            sql.addSql("   CMN_USRM,");
            sql.addSql("   CMN_USRM_INF");
            sql.addSql(" where");
            sql.addSql("   RSS_DATA.USR_SID = CMN_USRM_INF.USR_SID");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM.USR_JKBN = ?");
            sql.addSql(" and");
            //ユーザSID < 100は除外
            sql.addSql("     CMN_USRM.USR_SID > ?");
            sql.addSql(" and");
            sql.addSql("   RSS_DATA.RSS_SID = ?");
            sql.addIntValue(GSConst.JTKBN_TOROKU);
            sql.addIntValue(GSConstUser.USER_RESERV_SID);
            sql.addIntValue(rssSid);
            sql.addSql(" order by ");
            sql.addSql("   USI_SEI, ");
            sql.addSql("   USI_MEI ");
            sql.addSql(" asc ");

            pstmt = con.prepareStatement(sql.toSqlString(),
                                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                                        ResultSet.CONCUR_READ_ONLY);

            log__.info(sql.toLogString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (page > 1) {
                rs.absolute((page - 1) * maxCnt);
            }

            for (int i = 0; rs.next() && i < maxCnt; i++) {
                RssModel model = new RssModel();
                model.setRssSid(rs.getInt("RSS_SID"));
                model.setRegistSei(rs.getString("USI_SEI"));
                model.setRegistMei(rs.getString("USI_MEI"));
                ret.add(model);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }

    /**
     * <br>[機  能] RSS登録ユーザ件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rssSid RSSSID
     * @return RSS購読ユーザ件数
     * @throws Exception 実行例外
     */
    public int getRssUserCnt(int rssSid)
    throws Exception {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(RSS_SID) as rssCount");
            sql.addSql(" from");
            sql.addSql("   RSS_DATA,");
            sql.addSql("   CMN_USRM,");
            sql.addSql("   CMN_USRM_INF");
            sql.addSql(" where");
            sql.addSql("   RSS_DATA.USR_SID = CMN_USRM_INF.USR_SID");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM.USR_JKBN = ?");
            sql.addSql(" and");
            //ユーザSID < 100は除外
            sql.addSql("     CMN_USRM.USR_SID > ?");
            sql.addSql(" and");
            sql.addSql("   RSS_DATA.RSS_SID = ?");
            sql.addIntValue(GSConst.JTKBN_TOROKU);
            sql.addIntValue(GSConstUser.USER_RESERV_SID);
            sql.addIntValue(rssSid);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = rs.getInt("rssCount");
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
     * <p>RSSSIDとランキング順位のMappingを取得する
     * @return ユーザ件数と順位のMapping
     * @throws SQLException SQL実行例外
     */
    public Map<Integer, Integer> getUserCountMap()
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Map<Integer, Integer> ret = new HashMap<Integer, Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSS_USERCOUNT.RSS_SID,");
            sql.addSql("   RSS_USERCOUNT.USER_COUNT,");
            sql.addSql("   USERCOUNT_RANKING.RANKING");
            sql.addSql(" from");
            sql.addSql("   (");
            sql.addSql("    select");
            sql.addSql("      RSS_DATA.RSS_SID as RSS_SID,");
            sql.addSql("      count(RSS_DATA.USR_SID) as USER_COUNT");
            sql.addSql("    from");
            sql.addSql("      CMN_USRM,");
            sql.addSql("      RSS_DATA");
            sql.addSql("    where");
            sql.addSql("      CMN_USRM.USR_JKBN = ?");
            sql.addSql("    and");
            sql.addSql("      CMN_USRM.USR_SID = RSS_DATA.USR_SID");
            sql.addSql("    group by");
            sql.addSql("      RSS_DATA.RSS_SID");
            sql.addSql("   ) RSS_USERCOUNT,");
            sql.addSql("   (");
            sql.addSql("    select");
            sql.addSql("      USER_COUNT,");
            sql.addSql("      count(USER_COUNT) as RANKING");
            sql.addSql("    from");
            sql.addSql("      (");
            sql.addSql("       select");
            sql.addSql("         count(RSS_DATA.USR_SID) as USER_COUNT");
            sql.addSql("       from");
            sql.addSql("         CMN_USRM,");
            sql.addSql("         RSS_DATA");
            sql.addSql("       where");
            sql.addSql("         CMN_USRM.USR_JKBN = ?");
            sql.addSql("       and");
            sql.addSql("         CMN_USRM.USR_SID = RSS_DATA.USR_SID");
            sql.addSql("       group by");
            sql.addSql("         RSS_DATA.RSS_SID");
            sql.addSql("      ) RSS_USERSUM");
            sql.addSql("    group by");
            sql.addSql("      USER_COUNT");
            sql.addSql("   ) USERCOUNT_RANKING");
            sql.addSql(" where");
            sql.addSql("   RSS_USERCOUNT.USER_COUNT = USERCOUNT_RANKING.USER_COUNT");
            sql.addSql(" order by");
            sql.addSql("   RSS_USERCOUNT.USER_COUNT desc");
            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);
            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            int ranking = 1;
            int count = 1;
            int beforeUserCount = 1;
            while (rs.next()) {
                int userCount = rs.getInt("USER_COUNT");
                if (beforeUserCount != userCount) {
                    ranking = count;
                    beforeUserCount = userCount;
                }

                ret.put(new Integer(rs.getInt("RSS_SID")),
                        new Integer(ranking));

                count++;
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
