package jp.groupsession.v2.bmk.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.bmk.GSConstBookmark;
import jp.groupsession.v2.bmk.biz.BookmarkBiz;
import jp.groupsession.v2.bmk.model.BmkBookmarkDataModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.usr.GSConstUser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>BMK_URL Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class BmkBookmarkDataDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(BmkBookmarkDataDao.class);
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <p>Set Connection
     * @param con Connection
     * @param reqMdl RequestModel
     */
    public BmkBookmarkDataDao(Connection con, RequestModel reqMdl) {
        super(con);
        reqMdl__ = reqMdl;
    }
    /**
     * <p>Select 指定ユーザのブックマーク一覧を取得
     * @param usrSid ユーザSID
     * @return List in BMK_URLModel
     * @throws SQLException SQL実行例外
     */
    public List<BmkBookmarkDataModel> getBookMarkList(int usrSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<BmkBookmarkDataModel> ret = new ArrayList<BmkBookmarkDataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BMK_BOOKMARK.BMK_SID,");
            sql.addSql("   BMK_BOOKMARK.BMU_SID,");
            sql.addSql("   BMK_BOOKMARK.BMK_TITLE,");
            sql.addSql("   BMK_BOOKMARK.BMK_CMT,");
            sql.addSql("   BMK_BOOKMARK.BMK_SORT,");
            sql.addSql("   BMK_COUNT.BMU_URL,");
            sql.addSql("   BMK_COUNT.TOUROKU_COUNT");
            sql.addSql(" from");
            sql.addSql("   BMK_BOOKMARK");
            sql.addSql("   left join (");
            sql.addSql("     select");
            sql.addSql("       BMK_URL.BMU_SID,");
            sql.addSql("       BMK_URL.BMU_URL,");
            sql.addSql("       count(BMK_BOOKMARK.BMU_SID) as TOUROKU_COUNT");
            sql.addSql("     from");
            sql.addSql("       BMK_BOOKMARK,");
            sql.addSql("       BMK_URL,");
            sql.addSql("       CMN_USRM");
            sql.addSql("     where");
            sql.addSql("       BMK_URL.BMU_SID = BMK_BOOKMARK.BMU_SID");
            sql.addSql("     and");
            sql.addSql("       BMK_BOOKMARK.USR_SID = CMN_USRM.USR_SID");
            sql.addSql("     and");
            sql.addSql("       CMN_USRM.USR_JKBN = 0");
            sql.addSql("     group by");
//            sql.addSql("       BMK_BOOKMARK.BMU_SID");
            sql.addSql("       BMK_URL.BMU_SID,");
            sql.addSql("       BMK_URL.BMU_URL");
            sql.addSql("   ) BMK_COUNT");
            sql.addSql("   on BMK_BOOKMARK.BMU_SID = BMK_COUNT.BMU_SID");
            sql.addSql(" where");
            sql.addSql("   BMK_BOOKMARK.USR_SID = ?");
            sql.addSql("   and");
            sql.addSql("   BMK_BOOKMARK.BMK_MAIN = ?");
            sql.addSql(" order by");
            sql.addSql("   BMK_BOOKMARK.BMK_SORT");
            sql.addIntValue(usrSid);
            sql.addIntValue(GSConstBookmark.DSP_YES);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                BmkBookmarkDataModel bean = new BmkBookmarkDataModel();
                bean.setBmkSid(rs.getInt("BMK_SID"));
                bean.setBmuSid(rs.getInt("BMU_SID"));
                bean.setBmkCmt(rs.getString("BMK_CMT"));
                bean.setBmkTitle(rs.getString("BMK_TITLE"));

                BookmarkBiz bBiz = new BookmarkBiz(getCon(), reqMdl__);
                bean.setBmkTitleDspList(bBiz.getStringCutList(30, rs.getString("BMK_TITLE")));
                bean.setBmkSort(rs.getInt("BMK_SORT"));
                bean.setBmkUrl(rs.getString("BMU_URL"));
                bean.setUserCount(rs.getInt("TOUROKU_COUNT"));
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
     * <p>指定URLSIDのブックマークの件数を取得する
     * @param bmuSid URLSID
     * @return ブックマークの件数の件数
     * @throws SQLException SQL実行例外
     */
    public int getBookmarkCount(int bmuSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(BMK_BOOKMARK.BMU_SID) as urlCount");
            sql.addSql(" from ");
            sql.addSql("   BMK_BOOKMARK,");
            sql.addSql("   CMN_USRM");
            sql.addSql(" where ");
            sql.addSql("   BMK_BOOKMARK.USR_SID = CMN_USRM.USR_SID");
            sql.addSql("   and");
            sql.addSql("   BMK_BOOKMARK.BMU_SID = ?");
            sql.addSql("   and");
            sql.addSql("   BMK_BOOKMARK.BMK_KBN = 0");
            sql.addSql("   and");
            sql.addSql("   BMK_BOOKMARK.BMK_PUBLIC = 1");
            sql.addSql("   and");
            sql.addSql("   CMN_USRM.USR_JKBN = 0");
            sql.addIntValue(bmuSid);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
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
     * <p>Select コメント・評価一覧の取得
     * @param page ページ
     * @param maxCnt １ページの最大表示件数
     * @param bmuSid URLSID
     * @param appRoot アプリケーションのルートパス
     * @param order オーダーキー
     * @param sort ソートキー
     * @return List in BMK_URLModel
     * @throws SQLException SQL実行例外
     * @throws IOException 実行例外
     * @throws IOToolsException 実行例外
     */
    public List<BmkBookmarkDataModel> getCommentList(int page, int maxCnt, int bmuSid,
        String appRoot, int order, int sort)
        throws SQLException, IOException, IOToolsException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<BmkBookmarkDataModel> ret = new ArrayList<BmkBookmarkDataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   BMK_BOOKMARK.BMK_SID,");
            sql.addSql("   BMK_BOOKMARK.BMU_SID,");
            sql.addSql("   BMK_BOOKMARK.USR_SID,");
            sql.addSql("   BMK_BOOKMARK.BMK_CMT,");
            sql.addSql("   BMK_BOOKMARK.BMK_SCORE,");
            sql.addSql("   BMK_BOOKMARK.BMK_ADATE,");
            sql.addSql("   CMN_USRM_INF.USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI,");
            sql.addSql("   CMN_USRM_INF.USI_PICT_KF,");
            sql.addSql("   CMN_USRM.USR_UKO_FLG,");
            sql.addSql("   BINF.BIN_SID,");
            sql.addSql("   BINF.BIN_FILE_NAME,");
            sql.addSql("   BINF.BIN_FILE_PATH");
            sql.addSql(" from ");
            sql.addSql("   BMK_BOOKMARK,");
            sql.addSql("   CMN_USRM,");
            sql.addSql("   CMN_USRM_INF");
            sql.addSql("    left join");
            sql.addSql("      (");
            sql.addSql("       select");
            sql.addSql("         BIN_SID,");
            sql.addSql("         BIN_FILE_NAME,");
            sql.addSql("         BIN_FILE_PATH");
            sql.addSql("       from");
            sql.addSql("         CMN_BINF");
            sql.addSql("       where");
            sql.addSql("         BIN_JKBN = 0");
            sql.addSql("      ) BINF");
            sql.addSql("    on");
            sql.addSql("      CMN_USRM_INF.BIN_SID = BINF.BIN_SID");
            sql.addSql(" where ");
            sql.addSql("   BMK_BOOKMARK.USR_SID = CMN_USRM.USR_SID");
            sql.addSql("   and");
            sql.addSql("   CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID");
            sql.addSql("   and");
            sql.addSql("   BMK_BOOKMARK.BMK_KBN = 0");
            sql.addSql("   and");
            sql.addSql("   BMK_BOOKMARK.BMK_PUBLIC = 1");
            sql.addSql("   and");
            sql.addSql("   BMK_BOOKMARK.BMU_SID = ?");
            sql.addSql("   and");
            sql.addSql("   CMN_USRM.USR_JKBN = 0");
            sql.addIntValue(bmuSid);

            //並び順を設定
            sql.addSql(" order by");

            String orderKey = "asc";

            if (order == GSConstBookmark.ORDERKEY_DESC) {
                orderKey = "desc";
            }
            switch (sort) {
                case GSConstBookmark.SORTKEY_ADATE:
                    sql.addSql("   BMK_ADATE " + orderKey);
                    break;
                case GSConstBookmark.SORTKEY_SCORE:
                    sql.addSql("   BMK_SCORE " + orderKey);
                    break;
                case GSConstBookmark.SORTKEY_AUID:
                default :
                    sql.addSql("   USI_SEI_KN " + orderKey + ",");
                    sql.addSql("   USI_MEI_KN " + orderKey);
                break;
            }

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
                ret.add(__getBmkCommentFromRs(rs, i, appRoot));
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
     * @param index index
     * @param appRoot アプリケーションのルートパス
     * @return created BmkBookmarkDataModel
     * @throws SQLException SQL実行例外
     * @throws IOException 実行例外
     * @throws IOToolsException 実行例外
     */
    private BmkBookmarkDataModel __getBmkCommentFromRs(ResultSet rs, int index,
        String appRoot) throws SQLException,  IOException, IOToolsException {
        BmkBookmarkDataModel bean = new BmkBookmarkDataModel();
        bean.setBmkSid(rs.getInt("BMK_SID"));
        bean.setBmuSid(rs.getInt("BMU_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setBmkCmt(StringUtilHtml.transToHTmlPlusAmparsant(rs.getString("BMK_CMT")));
        bean.setBmkScore(rs.getInt("BMK_SCORE"));
        bean.setBmkAdate(UDate.getInstanceTimestamp(rs.getTimestamp("BMK_ADATE")));
        bean.setStrBmkAdate(UDateUtil.getSlashYYMD(bean.getBmkAdate()));

        bean.setUsrName(rs.getString("USI_SEI") + " " + rs.getString("USI_MEI"));
        //写真公開可否を取得
        int pictKf = rs.getInt("USI_PICT_KF");
        bean.setUserPictKf(rs.getInt("USI_PICT_KF"));

        //ユーザ画像ファイル名を設定
        String binFilePath = rs.getString("BIN_FILE_PATH");
        if (binFilePath != null && pictKf == GSConstUser.INDIVIDUAL_INFO_OPEN) {
            bean.setPhotoFileSid(rs.getLong("BIN_SID"));
            bean.setPhotoFileName(rs.getString("BIN_FILE_NAME"));
            bean.setPhotoFilePath(binFilePath);
        }
        bean.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
        return bean;
    }
}
