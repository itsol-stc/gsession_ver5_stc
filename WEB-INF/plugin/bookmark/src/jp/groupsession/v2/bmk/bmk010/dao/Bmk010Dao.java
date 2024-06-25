package jp.groupsession.v2.bmk.bmk010.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.bmk.GSConstBookmark;
import jp.groupsession.v2.bmk.biz.BookmarkBiz;
import jp.groupsession.v2.bmk.bmk010.Bmk010Biz;
import jp.groupsession.v2.bmk.bmk010.model.Bmk010BodyModel;
import jp.groupsession.v2.bmk.bmk010.model.Bmk010InfoModel;
import jp.groupsession.v2.bmk.bmk010.model.Bmk010LabelModel;
import jp.groupsession.v2.bmk.bmk010.model.Bmk010SearchModel;
import jp.groupsession.v2.bmk.model.BmkLabelModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>ブックマーク画面のDAOクラスです。
 *
 * @author JTS DaoGenerator version 0.5
 */
public class Bmk010Dao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bmk010Dao.class);

    /** コメント改行区分 改行なし */
    public static final int BMK_CMT_BR_NO = 0;
    /** コメント改行区分 改行あり */
    public static final int BMK_CMT_BR_YES = 1;
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <p>Set Connection
     * @param con Connection
     * @param reqMdl RequestModel
     */
    public Bmk010Dao(Connection con, RequestModel reqMdl) {
        super(con);
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] ブックマーク一覧の件数を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param model 検索条件
     * @return ブックマーク一覧の件数
     * @throws SQLException SQL実行例外
     */
    public int selectBmkCount(Bmk010SearchModel model)
    throws SQLException {

        log__.debug("ブックマーク一覧の件数を取得する");
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int result = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(*) as CNT");
            sql = __createSearchSql(sql, model);
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                result = rs.getInt("CNT");
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
     * <br>[機  能] ブックマーク情報の検索を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param model 検索条件
     * @return 検索結果
     * @throws SQLException SQL実行時例外
     */
    public List<Bmk010BodyModel> selectBmkList(Bmk010SearchModel model)
    throws SQLException {
        log__.debug("ブックマーク情報の検索を行う");

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Bmk010BodyModel> ret = new ArrayList<Bmk010BodyModel>();
        con = getCon();
        int sessionUsrSid = model.getUserMdl().getUsrsid();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   BMK_BOOKMARK.BMK_SID as BMK_SID,");
            sql.addSql("   BMK_BOOKMARK.BMK_KBN as BMK_KBN,");
            sql.addSql("   BMK_BOOKMARK.USR_SID as USR_SID,");
            sql.addSql("   BMK_BOOKMARK.GRP_SID as GRP_SID,");
            sql.addSql("   BMK_BOOKMARK.BMU_SID as BMU_SID,");
            sql.addSql("   BMK_BOOKMARK.BMK_TITLE as BMK_TITLE,");
            sql.addSql("   BMK_BOOKMARK.BMK_CMT as BMK_CMT,");
            sql.addSql("   BMK_BOOKMARK.BMK_SCORE as BMK_SCORE,");
            sql.addSql("   BMK_BOOKMARK.BMK_PUBLIC as BMK_PUBLIC,");
            sql.addSql("   BMK_BOOKMARK.BMK_MAIN as BMK_MAIN,");
            sql.addSql("   BMK_BOOKMARK.BMK_SORT as BMK_SORT,");
            sql.addSql("   BMK_BOOKMARK.BMK_AUID as BMK_AUID,");
            sql.addSql("   BMK_BOOKMARK.BMK_ADATE as BMK_ADATE,");
            sql.addSql("   BMK_BOOKMARK.BMK_EUID as BMK_EUID,");
            sql.addSql("   BMK_BOOKMARK.BMK_EDATE as BMK_EDATE,");
            sql.addSql("   BMK_URL.BMU_URL as BMU_URL");
            sql = __createSearchSql(sql, model);

            //並び順を設定
            int order = model.getOrderKey();
            sql.addSql(" order by");
            switch (model.getSortKey()) {
                case GSConstBookmark.SORTKEY_ADATE :
                    sql.addSql(__getSortSql("BMK_ADATE", order));
                    break;
                case GSConstBookmark.SORTKEY_SCORE :
                    sql.addSql(__getSortSql("BMK_SCORE", order));
                    break;
                case GSConstBookmark.SORTKEY_TITLE :
                    sql.addSql(__getSortSql("BMK_TITLE", order));
                    break;
                default :
                    sql.addSql(__getSortSql("BMK_ADATE", order));
                    break;
            }
            sql.addSql(" ,");
            sql.addSql("   BMK_BOOKMARK.BMK_SID");

            //ページ設定
            int page = model.getPage();
            int maxCnt = model.getMaxViewCount();
            int rowNum = PageUtil.getRowNumber(page, maxCnt) - 1;
            if (rowNum < 0) {
                rowNum = 0;
            }
            sql.setPagingValue(rowNum, maxCnt);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Bmk010BodyModel bodylMdl = __getBookmarkFromRs(rs, sessionUsrSid);
                ret.add(bodylMdl);
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
     * <br>[機  能] ブックマークの検索SQL文を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SQLBuffer
     * @param model 検索条件
     * @return SQLBuffer
     * @throws SQLException SQL実行時例外
     */
    private SqlBuffer __createSearchSql(SqlBuffer sql, Bmk010SearchModel model)
    throws SQLException {

        int bmkMode = model.getBmkMode();
        BaseUserModel userMdl = model.getUserMdl();
        int sessionUserSid = userMdl.getUsrsid();

        sql.addSql(" from ");
        sql.addSql("   BMK_BOOKMARK,");
        sql.addSql("   BMK_URL");
        sql.addSql(" where ");
        sql.addSql("   BMK_BOOKMARK.BMK_KBN = ?");
        sql.addIntValue(bmkMode);

        //個人ブックマーク
        if (bmkMode == GSConstBookmark.BMK_KBN_KOJIN) {
            sql.addSql(" and ");
            sql.addSql("   BMK_BOOKMARK.USR_SID = ?");
            sql.addIntValue(model.getUser());

            if (model.getUser() != sessionUserSid) {
                sql.addSql(" and");
                sql.addSql("   BMK_BOOKMARK.BMK_PUBLIC = ?");
                sql.addIntValue(GSConstBookmark.KOKAI_YES);
            }
        }

        //グループブックマーク
        if (bmkMode == GSConstBookmark.BMK_KBN_GROUP) {
            sql.addSql(" and ");
            sql.addSql("   BMK_BOOKMARK.GRP_SID = ?");
            sql.addIntValue(model.getGroup());

            //削除グループのブックマークは除く
            sql.addSql(" and ");
            sql.addSql("   BMK_BOOKMARK.GRP_SID in (");
            sql.addSql("     select GRP_SID from CMN_GROUPM");
            sql.addSql("     where GRP_JKBN = ?");
            sql.addSql("   )");
            sql.addIntValue(GSConst.JTKBN_TOROKU);

            //公開ブックマーク または ユーザが所属するグループ
            sql.addSql(" and");
            sql.addSql("   (");
            sql.addSql("     BMK_BOOKMARK.BMK_PUBLIC = ?");
            sql.addSql("   or");
            sql.addSql("     BMK_BOOKMARK.GRP_SID in (");
            sql.addSql("       select GRP_SID from CMN_BELONGM");
            sql.addSql("       where USR_SID = ?");
            sql.addSql("     )");
            sql.addSql("   )");
            sql.addIntValue(GSConstBookmark.KOKAI_YES);
            sql.addIntValue(sessionUserSid);
        }

        //ラベル
        int blbSid = model.getLabel();
        if (blbSid != Bmk010Biz.INIT_VALUE) {
            if (blbSid == Bmk010Biz.NO_LABEL_SID) {
                sql.addSql(" and");
                sql.addSql("   BMK_BOOKMARK.BMK_SID not in (");
                sql.addSql("     select");
                sql.addSql("       BMK_SID");
                sql.addSql("     from");
                sql.addSql("       BMK_BELONG_LABEL");
                sql.addSql("   )");
            } else {
                sql.addSql(" and");
                sql.addSql("   BMK_BOOKMARK.BMK_SID in (");
                sql.addSql("     select");
                sql.addSql("       BMK_SID");
                sql.addSql("     from");
                sql.addSql("       BMK_BELONG_LABEL");
                sql.addSql("     where ");
                sql.addSql("       BLB_SID=?");
                sql.addSql("   )");
                sql.addIntValue(blbSid);
            }
        }

        sql.addSql(" and ");
        sql.addSql("   BMK_BOOKMARK.BMU_SID = BMK_URL.BMU_SID");

        return sql;
    }

    /**
     * <br>[機  能] ソート部分のSQLを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param sortField 並び替えの基準となるフィールド名称
     * @param order 並び順(0:asc or 1:desc)
     * @return ソート部分のSQL
     */
    private String __getSortSql(String sortField, int order) {

        StringBuilder sb = new StringBuilder("   ");
        sb.append(sortField);
        if (order == GSConstBookmark.ORDERKEY_DESC) {
            sb.append(" desc");
        } else {
            sb.append(" asc");
        }

        return sb.toString();
    }

    /**
     * <br>[機  能] ブックマーク一覧表示モデルを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param rs 検索結果
     * @param sessionUsrSid セッションユーザSID
     * @return created BmkAconfModel
     * @throws SQLException SQL実行例外
     */
    private Bmk010BodyModel __getBookmarkFromRs(ResultSet rs, int sessionUsrSid)
    throws SQLException {
        Bmk010BodyModel bean = new Bmk010BodyModel();

        bean.setBmkSid(rs.getInt("BMK_SID"));
        bean.setBmkKbn(rs.getInt("BMK_KBN"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setGrpSid(rs.getInt("GRP_SID"));
        bean.setBmuSid(rs.getInt("BMU_SID"));
        bean.setBmkTitle(rs.getString("BMK_TITLE"));
        bean.setBmkCmt(NullDefault.getString(
                StringUtilHtml.transToHTmlPlusAmparsant(rs.getString("BMK_CMT")), ""));
        bean.setBmkScore(rs.getInt("BMK_SCORE"));
        bean.setBmkPublic(rs.getInt("BMK_PUBLIC"));
        bean.setBmkMain(rs.getInt("BMK_MAIN"));
        bean.setBmkSort(rs.getInt("BMK_SORT"));
        bean.setBmkAuid(rs.getInt("BMK_AUID"));
        bean.setBmkAdate(UDate.getInstanceTimestamp(rs.getTimestamp("BMK_ADATE")));
        bean.setBmkEuid(rs.getInt("BMK_EUID"));
        bean.setBmkEdate(UDate.getInstanceTimestamp(rs.getTimestamp("BMK_EDATE")));

        BookmarkBiz bBiz = new BookmarkBiz(getCon(), reqMdl__);
        bean.setBmkTitleDspList(bBiz.getStringCutList(40, rs.getString("BMK_TITLE")));
        bean.setBmuUrl(rs.getString("BMU_URL"));
        bean.setBmuUrlDspList(bBiz.getStringCutList(68, rs.getString("BMU_URL")));
        bean.setBmkPerCount(__selectPerCount(rs.getInt("BMU_SID")));
        bean.setBmkMyKbn(__selectMyKbn(rs.getInt("BMU_SID"), sessionUsrSid));
        bean.setBmkLabelList(getBmkLabelList(rs.getInt("BMK_SID")));
        UDate aDate = UDate.getInstanceTimestamp(rs.getTimestamp("BMK_ADATE"));
        bean.setBmkAdateDsp(aDate.getStrYear() + "/"
                              + aDate.getStrMonth() + "/"
                              + aDate.getStrDay());
        //コメント改行区分
        if (bean.getBmkCmt().indexOf("<BR>") >= 0 || bean.getBmkCmt().indexOf("<br>") >= 0) {
            bean.setBmkCmtBrKbn(BMK_CMT_BR_YES);
        } else {

            //改行コードを挿入
            String dspCmt = StringUtil.addReturnCode(bean.getBmkCmt(), 100);
            bean.setBmkCmt(dspCmt);

            bean.setBmkCmtBrKbn(BMK_CMT_BR_NO);
        }

        return bean;
    }

    /**
     * <br>[機  能] 区分、ユーザ、グループ別にラベル一覧を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param blbKbn ラベル区分
     * @param usrSid 選択ユーザSID
     * @param grpSid 選択グループSID
     * @param sort ソート順 0:使用数順 1:名前順
     * @return List in Bmk010LabelModel
     * @throws SQLException SQL実行例外
     */
    public List<Bmk010LabelModel> selectLabelList(int blbKbn, int usrSid, int grpSid, int sort)
    throws SQLException {
        log__.debug("ラベル一覧を取得する");
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Bmk010LabelModel> ret = new ArrayList<Bmk010LabelModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   BMK_LABEL.BLB_SID as BLB_SID,");
            sql.addSql("   BMK_LABEL.BLB_NAME as BLB_NAME,");
            sql.addSql("   count(BMK_BELONG_LABEL.BMK_SID) as CNT");
            sql.addSql(" from ");
            sql.addSql("   BMK_LABEL");
            sql.addSql(" left join ");
            sql.addSql("   BMK_BELONG_LABEL");
            sql.addSql(" on ");
            sql.addSql("   BMK_BELONG_LABEL.BLB_SID = BMK_LABEL.BLB_SID");
            sql.addSql(" where ");
            sql.addSql("   BMK_LABEL.BLB_KBN=?");
            sql.addIntValue(blbKbn);
            if (blbKbn == GSConstBookmark.BMK_KBN_KOJIN) {
                sql.addSql(" and ");
                sql.addSql("   BMK_LABEL.USR_SID=?");
                sql.addIntValue(usrSid);
            }
            if (blbKbn == GSConstBookmark.BMK_KBN_GROUP) {
                sql.addSql(" and ");
                sql.addSql("   BMK_LABEL.GRP_SID=?");
                sql.addIntValue(grpSid);
            }
            sql.addSql(" group by ");
            sql.addSql("   BMK_LABEL.BLB_SID,");
            sql.addSql("   BMK_LABEL.BLB_NAME");
            //ソート順
            sql.addSql(" order by ");
            if (sort == 0) {
                sql.addSql("   CNT");
                sql.addSql(" desc, ");
                sql.addSql("   BMK_LABEL.BLB_NAME");
                sql.addSql(" asc, ");
                sql.addSql("   BMK_LABEL.BLB_SID");
                sql.addSql(" asc ");
            } else {
                sql.addSql("   BMK_LABEL.BLB_NAME");
            }

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Bmk010LabelModel bean = new Bmk010LabelModel();
                bean.setBlbSid(rs.getInt("BLB_SID"));
                bean.setBlbName(rs.getString("BLB_NAME"));
                bean.setBmkLabelCount(rs.getInt("CNT"));
                bean.setBlbNameWebSearchWord(
                        StringUtil.toSingleCortationEscape(rs.getString("BLB_NAME")));
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
     * <br>[機  能] ラベルを使用していないブックマークの件数を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param blbKbn ラベル区分
     * @param usrSid 選択ユーザSID
     * @param grpSid 選択グループSID
     * @return 件数
     * @throws SQLException SQL実行例外
     */
    public int selectNoLabelCount(int blbKbn, int usrSid, int grpSid)
    throws SQLException {
        log__.debug("ラベルを使用していないブックマークの件数を取得する");
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   BMK_BOOKMARK.BMK_SID as BMK_SID,");
            sql.addSql("   count(BMK_BELONG_LABEL.BLB_SID) as CNT");
            sql.addSql(" from ");
            sql.addSql("   BMK_BOOKMARK");
            sql.addSql("   left join ");
            sql.addSql("     BMK_BELONG_LABEL");
            sql.addSql("   on ");
            sql.addSql("     BMK_BOOKMARK.BMK_SID = BMK_BELONG_LABEL.BMK_SID");
            sql.addSql(" where ");
            sql.addSql("   BMK_BOOKMARK.BMK_KBN=?");
            sql.addIntValue(blbKbn);
            if (blbKbn == GSConstBookmark.BMK_KBN_KOJIN) {
                sql.addSql(" and ");
                sql.addSql("   BMK_BOOKMARK.USR_SID=?");
                sql.addIntValue(usrSid);
            }
            if (blbKbn == GSConstBookmark.BMK_KBN_GROUP) {
                sql.addSql(" and ");
                sql.addSql("   BMK_BOOKMARK.GRP_SID=?");
                sql.addIntValue(grpSid);
            }
            sql.addSql(" group by ");
            sql.addSql("   BMK_BOOKMARK.BMK_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                if (rs.getInt("CNT") == 0) {
                    ret++;
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
     * <br>[機  能] 新着ブックマーク一覧を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid セッションユーザSID
     * @param newBmkDspCnt 新着ブックマーク表示日数
     * @return List in Bmk010LabelModel
     * @throws SQLException SQL実行例外
     */
    public List<Bmk010InfoModel> selectNewBmk(int usrSid, int newBmkDspCnt) throws SQLException {
        log__.debug("新着ブックマーク上位５件の一覧を取得する");
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
            sql.addSql(" and");
            sql.addSql("   BMU_PUB_DATE > ?");
            sql.addSql(" order by");
            sql.addSql("   BMU_PUB_DATE desc");
            sql.setPagingValue(0, GSConstBookmark.NEW_RANKING_COUNT);

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
                //URLSID
                int bmuSid = rs.getInt("BMU_SID");

                Bmk010InfoModel bean = new Bmk010InfoModel();
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
     * <br>[機  能] ランキング（個人ブックマーク登録数）一覧を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid セッションユーザSID
     * @return List in Bmk010LabelModel
     * @throws SQLException SQL実行例外
     */
    public List<Bmk010InfoModel> selectRankingList(int usrSid) throws SQLException {
        log__.debug("ランキング（個人ブックマーク登録数）上位５件の一覧を取得する");
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Bmk010InfoModel> ret = new ArrayList<Bmk010InfoModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BMK_BOOKMARK.BMU_SID as BMU_SID,");
            sql.addSql("   BMK_URL.BMU_URL as BMU_URL,");
            sql.addSql("   BMK_URL.BMU_TITLE as BMU_TITLE,");
            sql.addSql("   count(BMK_BOOKMARK.BMK_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   BMK_BOOKMARK,");
            sql.addSql("   BMK_URL,");
            sql.addSql("   CMN_USRM");
            sql.addSql(" where");
            sql.addSql("   BMK_BOOKMARK.BMK_KBN=?");
            sql.addIntValue(GSConstBookmark.BMK_KBN_KOJIN);
            sql.addSql(" and");
            sql.addSql("   BMK_BOOKMARK.BMU_SID = BMK_URL.BMU_SID");
            sql.addSql(" and");
            sql.addSql("   BMK_BOOKMARK.USR_SID=CMN_USRM.USR_SID");
            sql.addSql(" and");
            sql.addSql("   BMK_URL.BMU_PUB_DATE is not null");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM.USR_JKBN=?");
            sql.addIntValue(GSConst.JTKBN_TOROKU);
            sql.addSql(" group by");
            sql.addSql("   BMK_BOOKMARK.BMU_SID,");
            sql.addSql("   BMK_URL.BMU_URL,");
            sql.addSql("   BMK_URL.BMU_TITLE");
            sql.addSql(" order by");
            sql.addSql("   CNT desc,");
            sql.addSql("   BMK_BOOKMARK.BMU_SID asc");
            sql.setPagingValue(0, GSConstBookmark.NEW_RANKING_COUNT);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                //URLSID
                int bmuSid = rs.getInt("BMU_SID");

                Bmk010InfoModel bean = new Bmk010InfoModel();
                bean.setBmuSid(bmuSid);
                bean.setBmuUrl(rs.getString("BMU_URL"));
                bean.setBmuTitle(rs.getString("BMU_TITLE"));
                BookmarkBiz bBiz = new BookmarkBiz(getCon(), reqMdl__);
                bean.setBmkTitleDspList(bBiz.getStringCutList(30, rs.getString("BMU_TITLE")));
                bean.setBmkPerCount(rs.getInt("CNT"));
                bean.setBmkMyKbn(__selectMyKbn(bmuSid, usrSid));
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
            sql.addSql("   BMK_BOOKMARK.BMU_SID=?");
            sql.addSql(" and");
            sql.addSql("   BMK_BOOKMARK.BMK_KBN=?");
            sql.addSql(" and");
            sql.addSql("   BMK_BOOKMARK.USR_SID=CMN_USRM.USR_SID");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM.USR_JKBN=?");
            sql.addIntValue(bmuSid);
            sql.addIntValue(GSConstBookmark.BMK_KBN_KOJIN);
            sql.addIntValue(GSConst.JTKBN_TOROKU);

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
            sql.addSql("   BMU_SID=?");
            sql.addSql(" and");
            sql.addSql("   BMK_KBN=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");
            sql.addIntValue(bmuSid);
            sql.addIntValue(GSConstBookmark.BMK_KBN_KOJIN);
            sql.addIntValue(usrSid);

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
     * <br>[機  能] ブックマークSIDに登録されているラベル一覧を取得する。
     * <br>[解  説]
     * <br>[備  考] ラベルが未登録のときは"-1","なし"を返す。
     * @param bmkSid ブックマークSID
     * @return ラベル一覧
     * @throws SQLException SQL実行例外
     */
    public List<BmkLabelModel> getBmkLabelList(int bmkSid)
    throws SQLException {
        log__.debug("ブックマークSIDに登録されているラベル一覧を取得");
        GsMessage gsMsg = new GsMessage(reqMdl__);
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<BmkLabelModel> ret = new ArrayList<BmkLabelModel>();
        con = getCon();
        int count = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BMK_BELONG_LABEL.BLB_SID as BLB_SID,");
            sql.addSql("   BMK_LABEL.BLB_NAME as BLB_NAME");
            sql.addSql(" from");
            sql.addSql("   BMK_BELONG_LABEL,");
            sql.addSql("   BMK_LABEL");
            sql.addSql(" where ");
            sql.addSql("   BMK_BELONG_LABEL.BMK_SID=?");
            sql.addSql(" and ");
            sql.addSql("   BMK_BELONG_LABEL.BLB_SID=BMK_LABEL.BLB_SID");
            sql.addIntValue(bmkSid);

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                BmkLabelModel bean = new BmkLabelModel();
                bean.setBlbSid(rs.getInt("BLB_SID"));
                bean.setBlbName(rs.getString("BLB_NAME"));
                ret.add(bean);
                count++;
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        //ラベルなしのとき
        if (count == 0) {
            BmkLabelModel bean = new BmkLabelModel();
            bean.setBlbSid(Bmk010Biz.NO_LABEL_SID);
            bean.setBlbName(gsMsg.getMessage("cmn.no"));
            ret.add(bean);
        }
        return ret;
    }

    /**
     * <br>[機  能] ブックマークSID以外にURLSIDが登録されているか判定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param bmkSid ブックマークSID
     * @return true:登録されている false:登録されていない
     * @throws SQLException SQL実行例外
     */
    public boolean isBmkUrlCheck(int bmkSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean ret = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BMK_SID,");
            sql.addSql("   BMU_SID");
            sql.addSql(" from");
            sql.addSql("   BMK_BOOKMARK");
            sql.addSql(" where");
            sql.addSql("   BMU_SID = (select BMU_SID from BMK_BOOKMARK where BMK_SID=?)");
            sql.addIntValue(bmkSid);
            sql.addSql(" and");
            sql.addSql("   BMK_SID <> ?");
            sql.addIntValue(bmkSid);

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = true;
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