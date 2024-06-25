package jp.groupsession.v2.bmk.bmk030.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.bmk.GSConstBookmark;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p> ブックマーク登録画面のDAOクラスです。
 *
 * @author JTS DaoGenerator version 0.5
 */
public class Bmk030Dao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bmk030Dao.class);

    /**
     * <p>Default Constructor
     */
    public Bmk030Dao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Bmk030Dao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] URLからブックマークのタイトルを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param bmkKbn ブックマーク区分
     * @param usrSid ユーザSID
     * @param grpSid グループSID
     * @param bmuUrl URL
     * @return タイトル
     * @throws SQLException SQL実行例外
     */
    public String getBmkTitleFromUrl(int bmkKbn, int usrSid, int grpSid, String bmuUrl)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        String ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BMK_BOOKMARK.BMK_TITLE");
            sql.addSql(" from");
            sql.addSql("   BMK_BOOKMARK,");
            sql.addSql("   BMK_URL");
            sql.addSql(" where");
            sql.addSql("   BMK_BOOKMARK.BMU_SID = BMK_URL.BMU_SID");
            sql.addSql(" and");
            sql.addSql("   BMK_BOOKMARK.BMK_KBN=?");
            sql.addIntValue(bmkKbn);
            if (bmkKbn == GSConstBookmark.BMK_KBN_KOJIN) {
                sql.addSql(" and");
                sql.addSql("   BMK_BOOKMARK.USR_SID=?");
                sql.addIntValue(usrSid);
            } else if (bmkKbn == GSConstBookmark.BMK_KBN_GROUP) {
                sql.addSql(" and");
                sql.addSql("   BMK_BOOKMARK.GRP_SID=?");
                sql.addIntValue(grpSid);
            }
            sql.addSql(" and");
            sql.addSql("   BMK_URL.BMU_URL=?");
            sql.addStrValue(bmuUrl);

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getString("BMK_TITLE");
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
     * <br>[機  能] URLSIDを公開している最終更新日のブックマークで、タイトルを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param bmuSid URLSID
     * @return タイトル(nullのときは公開されていない)
     * @throws SQLException SQL実行例外
     */
    public String getPublicTitle(int bmuSid)
    throws SQLException {
        log__.debug("URLSIDを公開している最終更新日のブックマークで、タイトルを取得する");

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        String ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   max(BMK_EDATE),");
            sql.addSql("   BMK_TITLE");
            sql.addSql(" from");
            sql.addSql("   BMK_BOOKMARK");
            sql.addSql(" where");
            sql.addSql("   BMU_SID=?");
            sql.addIntValue(bmuSid);
            sql.addSql(" and");
            sql.addSql("   BMK_PUBLIC=?");
            sql.addIntValue(GSConstBookmark.KOKAI_YES);
            sql.addSql(" group by");
            sql.addSql("   BMK_TITLE");
            sql.addSql(" order by");
            sql.addSql("   max(BMK_EDATE)");
            sql.addSql(" desc");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getString("BMK_TITLE");;
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