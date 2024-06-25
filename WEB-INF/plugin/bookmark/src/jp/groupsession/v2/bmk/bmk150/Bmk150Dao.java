package jp.groupsession.v2.bmk.bmk150;

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
import jp.groupsession.v2.bmk.GSConstBookmark;
import jp.groupsession.v2.bmk.biz.BookmarkBiz;
import jp.groupsession.v2.bmk.bmk010.model.Bmk010InfoModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.RequestModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 新着ブックマーク一覧画面のDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bmk150Dao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bmk150Dao.class);
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;
    /**
     * <p>Set Connection
     * @param con Connection
     * @param reqMdl RequestModel
     */
    public Bmk150Dao(Connection con, RequestModel reqMdl) {
        super(con);
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 新着ブックマーク一覧を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid セッションユーザSID
     * @param newBmkDspCnt 新着ブックマーク表示日数
     * @param offset レコードの読取開始行
     * @param limit 1ページの最大件数
     * @return List in Bmk010LabelModel
     * @throws SQLException SQL実行例外
     */
    public List<Bmk010InfoModel> selectNewBmk(int usrSid,
                                           int newBmkDspCnt,
                                           int offset,
                                           int limit) throws SQLException {
        log__.debug("新着ブックマーク一覧を取得する");
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Bmk010InfoModel> ret = new ArrayList<Bmk010InfoModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   BMU_SID,");
            sql.addSql("   BMU_URL,");
            sql.addSql("   BMU_TITLE,");
            sql.addSql("   BMU_PUB_DATE");
            sql.addSql(" from ");
            sql.addSql("   BMK_URL");
            sql.addSql(" where ");
            sql.addSql("   BMU_PUB_DATE is not null");
            sql.addSql("   and");
            sql.addSql("   BMU_PUB_DATE > ?");
            sql.addSql(" order by");
            sql.addSql("   BMU_PUB_DATE desc");

            pstmt = con.prepareStatement(
                    sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            //新着ブックマーク表示日数を設定
            UDate bdate = new UDate();
            bdate.resetTime();
            bdate.addDay(newBmkDspCnt * -1);
            sql.addDateValue(bdate);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (offset > 1) {
                rs.absolute(offset - 1);
            }

            for (int i = 0; rs.next() && i < limit; i++) {
                Bmk010InfoModel bean = new Bmk010InfoModel();

                //新着ブックマーク情報
                //URLSID
                int bmuSid = rs.getInt("BMU_SID");

                bean.setBmuSid(bmuSid);
                bean.setBmuUrl(rs.getString("BMU_URL"));
                bean.setBmuTitle(rs.getString("BMU_TITLE"));
                bean.setBmkPerCount(__selectPerCount(bmuSid));
                bean.setBmkMyKbn(__selectMyKbn(bmuSid, usrSid));
                BookmarkBiz bBiz = new BookmarkBiz(getCon(), reqMdl__);
                bean.setBmkTitleDspList(bBiz.getStringCutList(30, rs.getString("BMU_TITLE")));
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

    /**
     * <br>[機  能] 新着ブックマーク件数を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid セッションユーザSID
     * @param newBmkDspCnt 新着ブックマーク表示日数
     * @return 件数
     * @throws SQLException SQL実行例外
     */
    public int cntNewBmk(int usrSid,
                          int newBmkDspCnt) throws SQLException {
        log__.debug("新着ブックマーク件数を取得する");
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        int count = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   BMU_SID,");
            sql.addSql("   BMU_URL,");
            sql.addSql("   BMU_TITLE,");
            sql.addSql("   BMU_PUB_DATE");
            sql.addSql(" from ");
            sql.addSql("   BMK_URL");
            sql.addSql(" where ");
            sql.addSql("   BMU_PUB_DATE is not null");
            sql.addSql("   and");
            sql.addSql("   BMU_PUB_DATE > ?");
            sql.addSql(" order by");
            sql.addSql("   BMU_PUB_DATE desc");

            pstmt = con.prepareStatement(sql.toSqlString());

            //新着ブックマーク表示日数を設定
            UDate bdate = new UDate();
            bdate.resetTime();
            bdate.addDay(newBmkDspCnt * -1);
            sql.addDateValue(bdate);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                //新着ブックマーク件数
                rs.getInt("BMU_SID");
                count++;
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <br>[機  能] URLSIDをユーザSIDが個人ブックマークに登録しているかどうかを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param bmuSid URLSID
     * @param usrSid ユーザSID
     * @return 0:登録していない 1:登録している
     * @throws SQLException SQL実行例外
     */
    private int __selectMyKbn(int bmuSid, int usrSid)
    throws SQLException {
        log__.debug("URLSIDをユーザSIDが個人ブックマークに登録しているかどうかを取得");

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = GSConstBookmark.TOROKU_NO;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BMK_SID");
            sql.addSql(" from");
            sql.addSql("   BMK_BOOKMARK");
            sql.addSql(" where");
            sql.addSql("   BMK_KBN=?");
            sql.addIntValue(GSConstBookmark.BMK_KBN_KOJIN);
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");
            sql.addIntValue(usrSid);
            sql.addSql(" and");
            sql.addSql("   BMU_SID=?");
            sql.addIntValue(bmuSid);

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = GSConstBookmark.TOROKU_YES;
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
     * <br>[機  能] URLSIDを個人ブックマークに登録している人数を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param bmuSid URLSID
     * @return 人数
     * @throws SQLException SQL実行例外
     */
    private int __selectPerCount(int bmuSid)
    throws SQLException {
        log__.debug("URLSIDを個人ブックマークに登録している人数を取得する");

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(BMK_BOOKMARK.BMK_SID) as BMK_CNT");
            sql.addSql(" from");
            sql.addSql("   BMK_BOOKMARK,");
            sql.addSql("   CMN_USRM");
            sql.addSql(" where");
            sql.addSql("   BMK_BOOKMARK.USR_SID=CMN_USRM.USR_SID");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM.USR_JKBN=?");
            sql.addIntValue(GSConst.JTKBN_TOROKU);
            sql.addSql(" and");
            sql.addSql("   BMK_BOOKMARK.BMK_KBN=?");
            sql.addIntValue(GSConstBookmark.BMK_KBN_KOJIN);
            sql.addSql(" and");
            sql.addSql("   BMK_BOOKMARK.BMU_SID=?");
            sql.addIntValue(bmuSid);

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getInt("BMK_CNT");
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
