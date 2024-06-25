package jp.groupsession.v2.bmk.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.bmk.GSConstBookmark;
import jp.groupsession.v2.bmk.biz.BookmarkBiz;
import jp.groupsession.v2.bmk.model.BmkUrlDataModel;
import jp.groupsession.v2.cmn.model.RequestModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>BMK_URL Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class BmkUrlDataDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(BmkUrlDataDao.class);
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <p>Set Connection
     * @param con Connection
     * @param reqMdl RequestModel
     */
    public BmkUrlDataDao(Connection con, RequestModel reqMdl) {
        super(con);
        reqMdl__ = reqMdl;
    }

    /**
     * <p>URLマスタの件数を取得する
     * @return URLマスタの件数
     * @throws SQLException SQL実行例外
     */
    public int getUrlCount() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(BMU_SID) as urlCount");
            sql.addSql(" from ");
            sql.addSql("   BMK_URL");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = rs.getInt("urlCount");
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
     * <p>URL情報取得
     * @param bmuSid URLSID
     * @return BmkUrlDataModel
     * @throws SQLException SQL実行例外
     */
    public BmkUrlDataModel getUrlInfo(int bmuSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        BmkUrlDataModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   BMK_URL.BMU_SID,");
            sql.addSql("   BMK_URL.BMU_URL,");
            sql.addSql("   BMK_URL.BMU_TITLE,");
            sql.addSql("   avg(BMK_BOOKMARK.BMK_SCORE) as AVG,");
            sql.addSql("   sum(BMK_BOOKMARK.BMK_SCORE) as SUM,");
            sql.addSql("   count(BMK_BOOKMARK.BMK_SID) as COUNT");
            sql.addSql(" from ");
            sql.addSql("   BMK_URL,");
            sql.addSql("   BMK_BOOKMARK,");
            sql.addSql("   CMN_USRM");
            sql.addSql(" where ");
            sql.addSql("   BMK_URL.BMU_SID = BMK_BOOKMARK.BMU_SID");
            sql.addSql("   and");
            sql.addSql("   BMK_BOOKMARK.BMK_KBN = 0");
            sql.addSql("   and");
            sql.addSql("   BMK_URL.BMU_SID = ?");
            sql.addSql(" and");
            sql.addSql("   BMK_BOOKMARK.USR_SID = CMN_USRM.USR_SID");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM.USR_JKBN = 0");
            sql.addSql(" group by ");
            sql.addSql("   BMK_BOOKMARK.BMU_SID,");
            sql.addSql("   BMK_URL.BMU_SID,");
            sql.addSql("   BMK_URL.BMU_URL,");
            sql.addSql("   BMK_URL.BMU_TITLE");

            sql.addIntValue(bmuSid);

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getBmkUrlCommentFromRs(rs);
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
     * <p>登録ブックマークURL件数を取得する
     * @return URLマスタの登録ブックマーク件数
     * @throws SQLException SQL実行例外
     */
    public int getUrlBmkCount() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(distinct(BMK_BOOKMARK.BMU_SID)) as urlCount");
            sql.addSql(" from ");
            sql.addSql("   BMK_URL,");
            sql.addSql("   BMK_BOOKMARK,");
            sql.addSql("   CMN_USRM");
            sql.addSql(" where ");
            sql.addSql("   BMK_URL.BMU_SID = BMK_BOOKMARK.BMU_SID");
            sql.addSql(" and ");
            sql.addSql("   BMK_BOOKMARK.BMK_KBN = 0");
            sql.addSql(" and");
            sql.addSql("   BMK_URL.BMU_PUB_DATE is not null");
            sql.addSql(" and");
            sql.addSql("   BMK_BOOKMARK.USR_SID = CMN_USRM.USR_SID");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM.USR_JKBN = 0");
            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = rs.getInt("urlCount");
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
     * <p>URL情報一覧の取得
     * @param page ページ
     * @param maxCnt １ページの最大表示件数
     * @param usrSid ユーザSID
     * @return List in BMK_URLModel
     * @throws SQLException SQL実行例外
     */
    public List<BmkUrlDataModel> getRankingBmkList(int page, int maxCnt, int usrSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<BmkUrlDataModel> ret = new ArrayList<BmkUrlDataModel>();
        con = getCon();

        try {
            Map<Integer, Integer> rankMap = getUserCountMap();
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   BMK_URL.BMU_SID,");
            sql.addSql("   BMK_URL.BMU_URL,");
            sql.addSql("   BMK_URL.BMU_TITLE,");
            sql.addSql("   count(BMK_BOOKMARK.BMK_SID) as COUNT,");
            sql.addSql("   BMK_TOUROKU.tourokuCount");
            sql.addSql(" from ");
            sql.addSql("   BMK_URL,");
            sql.addSql("   CMN_USRM,");
            sql.addSql("   BMK_BOOKMARK");
            sql.addSql("   left join (");
            sql.addSql("        select");
            sql.addSql("        count(*) as tourokuCount,");
            sql.addSql("        BMK_BOOKMARK.BMU_SID");
            sql.addSql("        from");
            sql.addSql("        BMK_BOOKMARK");
            sql.addSql("        where");
            sql.addSql("        BMK_BOOKMARK.USR_SID = ?");
            sql.addSql("        group by");
            sql.addSql("        BMK_BOOKMARK.BMU_SID");
            sql.addSql("      ) as BMK_TOUROKU");
            sql.addSql("      on BMK_BOOKMARK.BMU_SID = BMK_TOUROKU.BMU_SID");
            sql.addSql(" where ");
            sql.addSql("   BMK_URL.BMU_SID = BMK_BOOKMARK.BMU_SID");
            sql.addSql(" and");
            sql.addSql("   BMK_BOOKMARK.BMK_KBN = ?");
            sql.addSql(" and");
            sql.addSql("   BMK_URL.BMU_PUB_DATE is not null");
            sql.addSql(" and");
            sql.addSql("   BMK_BOOKMARK.USR_SID = CMN_USRM.USR_SID");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM.USR_JKBN = 0");
            sql.addSql(" group by ");
            sql.addSql("   BMK_BOOKMARK.BMU_SID,");
            sql.addSql("   BMK_URL.BMU_SID,");
            sql.addSql("   BMK_URL.BMU_URL,");
            sql.addSql("   BMK_URL.BMU_TITLE,");
            sql.addSql("   BMK_TOUROKU.tourokuCount");
            sql.addSql(" order by ");
            sql.addSql("   COUNT desc,");
            sql.addSql("   BMK_URL.BMU_SID asc");

            sql.addIntValue(usrSid);
            sql.addIntValue(GSConstBookmark.BMK_KBN_KOJIN);

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
                ret.add(__getBmkUrlRankingFromRs(rs, rankMap));
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
     * <p>Create BMK_URL Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created BmkUrlModel
     * @throws SQLException SQL実行例外
     */
    private BmkUrlDataModel __getBmkUrlCommentFromRs(ResultSet rs)
    throws SQLException {
        BmkUrlDataModel bean = new BmkUrlDataModel();
        bean.setBmuSid(rs.getInt("BMU_SID"));
        bean.setBmuUrl(rs.getString("BMU_URL"));
        bean.setBmuTitle(rs.getString("BMU_TITLE"));
        BookmarkBiz bBiz = new BookmarkBiz(getCon(), reqMdl__);
        bean.setBmkTitleDspList(bBiz.getStringCutList(60, rs.getString("BMU_TITLE")));
        bean.setBmkUrlDspList(bBiz.getStringCutList(90, rs.getString("BMU_URL")));

        bean.setUserCount(rs.getInt("COUNT"));
//        bean.setScoreAvg(rs.getInt("AVG"));
        int avg = Math.round((float) rs.getInt("SUM") / rs.getInt("COUNT"));
        bean.setScoreAvg(avg);
        return bean;
    }

    /**
     * <p>Create BMK_URL Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @param rankMap Map
     * @return created BmkUrlModel
     * @throws SQLException SQL実行例外
     */
    private BmkUrlDataModel __getBmkUrlRankingFromRs(ResultSet rs, Map<Integer, Integer> rankMap)
    throws SQLException {
        BmkUrlDataModel bean = new BmkUrlDataModel();
        bean.setBmuSid(rs.getInt("BMU_SID"));
        bean.setBmuUrl(rs.getString("BMU_URL"));
        bean.setBmuTitle(rs.getString("BMU_TITLE"));
        BookmarkBiz bBiz = new BookmarkBiz(getCon(), reqMdl__);
        bean.setBmkTitleDspList(bBiz.getStringCutList(50, rs.getString("BMU_TITLE")));
        bean.setBmkUrlDspList(bBiz.getStringCutList(50, rs.getString("BMU_URL")));

        bean.setUserCount(rs.getInt("COUNT"));
        bean.setTourokuCount(rs.getInt("tourokuCount"));

        Integer ranking = null;

        if (rankMap != null) {
            ranking = rankMap.get(new Integer(bean.getBmuSid()));
        }
        if (ranking != null) {
            bean.setRanking(ranking.toString());
        }
        return bean;
    }

    /**
     * <p>BMUSIDとランキング順位のMappingを取得する
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
            sql.addSql("   BMK_USERCOUNT.BMU_SID,");
            sql.addSql("   BMK_USERCOUNT.USER_COUNT,");
            sql.addSql("   USERCOUNT_RANKING.RANKING");
            sql.addSql(" from");
            sql.addSql("   (");
            sql.addSql("    select");
            sql.addSql("      BMK_BOOKMARK.BMU_SID,");
            sql.addSql("      count(BMK_BOOKMARK.BMK_SID) as USER_COUNT");
            sql.addSql("    from");
            sql.addSql("      BMK_URL,");
            sql.addSql("      BMK_BOOKMARK");
            sql.addSql("    where ");
            sql.addSql("      BMK_URL.BMU_SID = BMK_BOOKMARK.BMU_SID");
            sql.addSql("      and");
            sql.addSql("      BMK_BOOKMARK.BMK_KBN = ?");
            sql.addSql("      and");
            sql.addSql("      BMK_URL.BMU_PUB_DATE is not null");
            sql.addSql("    group by");
            sql.addSql("      BMK_BOOKMARK.BMU_SID");
            sql.addSql("   ) BMK_USERCOUNT,");
            sql.addSql("   (");
            sql.addSql("    select");
            sql.addSql("      USER_COUNT,");
            sql.addSql("      count(USER_COUNT) as RANKING");
            sql.addSql("    from");
            sql.addSql("      (");
            sql.addSql("       select");
            sql.addSql("         count(BMK_BOOKMARK.BMK_SID) as USER_COUNT");
            sql.addSql("       from");
            sql.addSql("         BMK_URL,");
            sql.addSql("         BMK_BOOKMARK");
            sql.addSql("       where ");
            sql.addSql("         BMK_URL.BMU_SID = BMK_BOOKMARK.BMU_SID");
            sql.addSql("         and");
            sql.addSql("         BMK_BOOKMARK.BMK_KBN = ?");
            sql.addSql("         and");
            sql.addSql("         BMK_URL.BMU_PUB_DATE is not null");
            sql.addSql("       group by");
            sql.addSql("         BMK_BOOKMARK.BMU_SID");
            sql.addSql("      ) BMK_USERSUM");
            sql.addSql("    group by");
            sql.addSql("      USER_COUNT");
            sql.addSql("   ) USERCOUNT_RANKING");
            sql.addSql(" where");
            sql.addSql("   BMK_USERCOUNT.USER_COUNT = USERCOUNT_RANKING.USER_COUNT");
            sql.addSql(" order by");
            sql.addSql("   BMK_USERCOUNT.USER_COUNT desc");

            sql.addIntValue(GSConstBookmark.BMK_KBN_KOJIN);
            sql.addIntValue(GSConstBookmark.BMK_KBN_KOJIN);

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
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

                ret.put(new Integer(rs.getInt("BMU_SID")),
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
