package jp.groupsession.v2.prj.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.csv.CSVException;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.model.ProjectItemModel;
import jp.groupsession.v2.prj.model.ProjectSearchModel;
import jp.groupsession.v2.prj.prj040.Prj040CsvRecordListenerImpl;
import jp.groupsession.v2.prj.prj070.Prj070CsvRecordListenerImpl;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] プロジェクトの検索を行うDAO
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ProjectSearchDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ProjectSearchDao.class);

    /** 取得区分(カウント) */
    public static final int GET_COUNT = 0;
    /** 取得区分(一覧表示) */
    public static final int GET_LIST = 1;
    /** 取得区分(完了・未完了カウント) */
    public static final int GET_KAN_MIKAN = 2;
    /** リクエストモデル */
    private GsMessage gsMsg__ = new GsMessage();
    /**
     * <p>Default Constructor
     */
    public ProjectSearchDao() {
    }
    /**
     * <p>Set Connection
     * @param con Connection
     */
    public ProjectSearchDao(Connection con) {
        super(con);
    }
    /**
     * <p>Set Connection
     * @param con Connection
     * @param gsMsg メッセージ
     */
    public ProjectSearchDao(Connection con, GsMessage gsMsg) {
        super(con);
        gsMsg__ = gsMsg;
    }

    /**
     * <br>[機  能] 自分のマイプロジェクトのSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param userSid ユーザSID
     * @return ret マイプロジェクトSID
     * @throws SQLException SQL実行例外
     */
    public int getMyPrjSid(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        int ret = -1;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PRJ_PRJDATA.PRJ_SID as PRJ_SID");
            sql.addSql(" from");
            sql.addSql("   PRJ_PRJDATA");
            sql.addSql(" where");
            sql.addSql("   PRJ_PRJDATA.PRJ_MY_KBN = ?");
            sql.addIntValue(GSConstProject.KBN_MY_PRJ_MY);

            sql.addSql(" and");

            __addBelongMemberSql(sql, userSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toLogString());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = rs.getInt("PRJ_SID");
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
     * <br>[機  能] 警告中のTODO情報の件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return int 警告中のTODO情報の件数
     * @throws SQLException SQL実行例外
     */
    public int getKeikokuTodoCount(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("    count(*) as CNT");
            sql.addSql(" from");
            sql.addSql("   PRJ_TODODATA,");
            sql.addSql("   PRJ_TODOSTATUS,");
            sql.addSql("   PRJ_PRJDATA");
            sql.addSql(" where");
            sql.addSql("   PRJ_TODODATA.PRJ_SID = PRJ_TODOSTATUS.PRJ_SID");
            sql.addSql(" and");
            sql.addSql("   PRJ_TODODATA.PTS_SID = PRJ_TODOSTATUS.PTS_SID");
            sql.addSql(" and");
            sql.addSql("   PRJ_TODODATA.PRJ_SID = PRJ_PRJDATA.PRJ_SID");
            sql.addSql(" and");
            sql.addSql("   PRJ_TODOSTATUS.PTS_RATE != ?");

            sql.addIntValue(GSConstProject.RATE_MAX);

            sql.addSql("  and");
            sql.addSql("      exists");
            sql.addSql("      (");
            sql.addSql("        select");
            sql.addSql("          *");
            sql.addSql("        from");
            sql.addSql("          PRJ_TODOMEMBER");
            sql.addSql("        where");
            sql.addSql("          PRJ_TODOMEMBER.USR_SID = ?");
            sql.addIntValue(userSid);
            sql.addSql("        and");
            sql.addSql("          PRJ_TODOMEMBER.PRJ_SID = PRJ_TODODATA.PRJ_SID");
            sql.addSql("        and");
            sql.addSql("          PRJ_TODOMEMBER.PTD_SID = PRJ_TODODATA.PTD_SID");
            sql.addSql("    )");

            //所属プロジェクトのみ取得
            sql.addSql("  and");
            __addBelongMemberSql(sql, userSid);

            //警告区分 = 有りのみ取得
            sql.addSql("  and");
            __addKeikokuSql(sql);
            sql.addSql("    = ?");
            sql.addIntValue(GSConstProject.KEIKOKU_ARI);

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
     * <br>[機  能] TODO情報の件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param search 検索条件Model
     * @return int  TODO情報の件数
     * @throws SQLException SQL実行例外
     */
    public int getTodoCount(ProjectSearchModel search) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = __cleateTodoGetSql(search, GET_COUNT);

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
     * <br>[機  能] TODO情報リストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param search 検索条件Model
     * @return List int ProjectItemModel
     * @throws SQLException SQL実行例外
     */
    public List<ProjectItemModel> getTodoList(ProjectSearchModel search) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ProjectItemModel> ret = new ArrayList<ProjectItemModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = __cleateTodoGetSql(search, GET_LIST);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toLogString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            rs = pstmt.executeQuery();

            if (search.getStart() > 1) {
                rs.absolute(search.getStart() - 1);
            }

            for (int i = 0; rs.next() && i < search.getLimit(); i++) {
                ProjectItemModel model = __getProjectItemModelTodo(rs);
                model.setPrjTodoCommentCnt(rs.getInt("COMMENT_CNT"));
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
     * <br>[機  能] TODO情報リストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param search 検索条件Model
     * @return List int ProjectItemModel
     * @throws SQLException SQL実行例外
     */
    public List<ProjectItemModel> getTodoList2(ProjectSearchModel search) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ProjectItemModel> ret = new ArrayList<ProjectItemModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = __cleateTodoGetSql(search, GET_LIST);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();


            while (rs.next()) {
                ProjectItemModel model = __getProjectItemModelTodo(rs);
                model.setPrjTodoCommentCnt(rs.getInt("COMMENT_CNT"));
                if (search.isMemberBaseTodo()) {
                    model.setSchUsrSid(rs.getInt("USR_SID"));
                }
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
     * <br>[機  能] TODO件数情報Modelを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param search 検索条件Model
     * @return List int ProjectItemModel
     * @throws SQLException SQL実行例外
     */
    public ProjectItemModel getTodoCnt(ProjectSearchModel search) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ProjectItemModel ret = new ProjectItemModel();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = __cleateTodoGetSql(search, GET_KAN_MIKAN);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret.setPrjTodoKanryoCnt(rs.getInt("kanryoCnt"));
                ret.setPrjTodoMikanryoCnt(rs.getInt("mikanryoCnt"));
                ret.setPrjTodoSinkotyuCnt(rs.getInt("sinkotyuCnt"));
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
     * <br>[機  能] TODO情報リストを取得する(全件)
     * <br>[解  説]
     * <br>[備  考]
     * @param search 検索条件Model
     * @return List int ProjectItemModel
     * @throws SQLException SQL実行例外
     */
    public List<ProjectItemModel> getAllTodoList(ProjectSearchModel search) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ProjectItemModel> ret = new ArrayList<ProjectItemModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = __cleateTodoGetSql(search, GET_LIST);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getProjectItemModelTodo(rs));
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
     * <br>[機  能] プロジェクト情報リストを取得、CSVを生成する
     * <br>[解  説]
     * <br>[備  考]
     * @param search 検索条件Model
     * @param rl Prj070CsvRecordListenerImpl
     * @throws SQLException SQL実行例外
     * @throws CSVException CSV出力時例外
     */
    public void createTodoList(
        ProjectSearchModel search,
        Prj070CsvRecordListenerImpl rl) throws SQLException, CSVException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = __cleateTodoGetSql(search, GET_LIST);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toLogString());
            rs = pstmt.executeQuery();

            PrjTodomemberDao ptmDao = new PrjTodomemberDao(con);

            while (rs.next()) {
                ProjectItemModel retMdl = __getProjectItemModelTodo(rs);
                //担当メンバーを取得
                retMdl.setMemberList(
                        ptmDao.getTantoMemberList(retMdl.getProjectSid(), retMdl.getTodoSid()));
                rl.setRecord(retMdl);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] TODO情報を取得する(1件)
     * <br>[解  説]
     * <br>[備  考]
     * @param search 検索条件Model
     * @return List int ProjectItemModel
     * @throws SQLException SQL実行例外
     */
    public ProjectItemModel getTodoData(ProjectSearchModel search) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ProjectItemModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = __cleateTodoGetSql(search, GET_LIST);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toLogString());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = __getProjectItemModelTodo(rs);
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
     * <br>[機  能] SQLを作成する(TODO取得時)
     * <br>[解  説]
     * <br>[備  考]
     * @param search 検索条件Model
     * @param getKbn 0=カウント、1=一覧表示
     * @return SqlBuffer
     */
    private SqlBuffer __cleateTodoGetSql(ProjectSearchModel search, int getKbn) {

        UDate now = new UDate();
        //未選択
        String textUnSelection = gsMsg__.getMessage("project.src.1");

        SqlBuffer sql = new SqlBuffer();
        sql.addSql("  select");

        if (getKbn == GET_COUNT) {
            //カウント
            sql.addSql("    count(*) as CNT");

        } else if (getKbn == GET_KAN_MIKAN) {
            //完了・未完了カウント
            sql.addSql("    sum(case when PRJ_TODOSTATUS.PTS_RATE = ? then 1 else 0 end) "
                         + "as kanryoCnt,");
            sql.addSql("    sum(case when PRJ_TODOSTATUS.PTS_RATE > ? "
                         + "and PRJ_TODOSTATUS.PTS_RATE < ? "
                         + "then 1 else 0 end) as sinkotyuCnt,");
            sql.addSql("    sum(case when PRJ_TODOSTATUS.PTS_RATE = ? then 1 else 0 end) "
                         + "as mikanryoCnt");

            sql.addIntValue(GSConstProject.RATE_MAX);
            sql.addIntValue(GSConstProject.RATE_MIN);
            sql.addIntValue(GSConstProject.RATE_MAX);
            sql.addIntValue(GSConstProject.RATE_MIN);

        } else {
            //一覧表示
            sql.addSql("    PRJ_PRJDATA.PRJ_SID,");
            sql.addSql("    PRJ_PRJDATA.PRJ_MY_KBN,");
            sql.addSql("    PRJ_PRJDATA.PRJ_NAME,");
            sql.addSql("    PRJ_PRJDATA.PRJ_ID,");
            sql.addSql("    PRJ_PRJDATA.PRJ_MAIL_KBN,");
            sql.addSql("    PRJ_PRJDATA.BIN_SID,");
            sql.addSql("    (");
            sql.addSql("      case");
            sql.addSql("        PRJ_TODODATA.PTD_CATEGORY");
            sql.addSql("      when");
            sql.addSql("        ?");
            sql.addIntValue(GSConstCommon.NUM_INIT);
            sql.addSql("      then");
            sql.addSql("        ?");
            sql.addStrValue(textUnSelection);
            sql.addSql("      else");
            sql.addSql("        (");
            sql.addSql("          select");
            sql.addSql("            PRJ_TODOCATEGORY.PTC_NAME");
            sql.addSql("          from");
            sql.addSql("            PRJ_TODOCATEGORY");
            sql.addSql("          where");
            sql.addSql("            PRJ_TODOCATEGORY.PRJ_SID = PRJ_TODODATA.PRJ_SID");
            sql.addSql("          and");
            sql.addSql("            PRJ_TODOCATEGORY.PTC_CATEGORY_SID = PRJ_TODODATA.PTD_CATEGORY");
            sql.addSql("        )");
            sql.addSql("      end");
            sql.addSql("    )");
            sql.addSql("    as CATEGORY,");
            sql.addSql("    PRJ_TODOSTATUS.PTS_NAME,");
            sql.addSql("    PRJ_TODOSTATUS.PTS_RATE,");
            sql.addSql("    PRJ_TODODATA.PTD_SID,");
            sql.addSql("    PRJ_TODODATA.PTD_NO,");
            sql.addSql("    PRJ_TODODATA.PTD_TITLE,");
            sql.addSql("    PRJ_TODODATA.PTD_IMPORTANCE,");
            sql.addSql("    PRJ_TODODATA.PTD_DATE_PLAN,");
            sql.addSql("    PRJ_TODODATA.PRJ_DATE_LIMIT,");
            sql.addSql("    PRJ_TODODATA.PTD_DATE_START,");
            sql.addSql("    PRJ_TODODATA.PTD_DATE_END,");
            sql.addSql("    PRJ_TODODATA.PTD_PLAN_KOSU,");
            sql.addSql("    PRJ_TODODATA.PTD_RESULTS_KOSU,");
            sql.addSql("    PRJ_TODODATA.PTD_CONTENT,");
            sql.addSql("    PRJ_TODODATA.PTD_CATEGORY,");
            sql.addSql("    PRJ_TODODATA.PTD_AUID,");
            sql.addSql("    TODO_COMMENT.CNT as COMMENT_CNT,");
            if (search.isMemberBaseTodo()) {
              sql.addSql("  PRJ_TODOMEMBER.USR_SID as USR_SID,");
            }
            //取得条件(警告区分)をSQLに追加する
            __addKeikokuSql(sql);
            sql.addSql("    as KEIKOKU");
        }

        sql.addSql("  from");
        sql.addSql("    PRJ_PRJDATA,");
        sql.addSql("    PRJ_TODOSTATUS,");
        sql.addSql("    PRJ_TODODATA");
        sql.addSql("    left join");
        sql.addSql("      (");
        sql.addSql("        select PRJ_SID, PTD_SID, count(PRJ_SID) as CNT");
        sql.addSql("        from PRJ_TODOCOMMENT");
        sql.addSql("        group by PRJ_SID, PTD_SID");
        sql.addSql("      ) TODO_COMMENT");
        sql.addSql("    on");
        sql.addSql("      PRJ_TODODATA.PRJ_SID = TODO_COMMENT.PRJ_SID");
        sql.addSql("    and");
        sql.addSql("      PRJ_TODODATA.PTD_SID = TODO_COMMENT.PTD_SID");
        if (search.isMemberBaseTodo()) {
            sql.addSql("  , PRJ_TODOMEMBER");
        }
        sql.addSql("  where");
        sql.addSql("    PRJ_TODODATA.PRJ_SID = PRJ_TODOSTATUS.PRJ_SID");
        sql.addSql("  and");
        sql.addSql("    PRJ_TODODATA.PTS_SID = PRJ_TODOSTATUS.PTS_SID");
        sql.addSql("  and");
        sql.addSql("    PRJ_TODODATA.PRJ_SID = PRJ_PRJDATA.PRJ_SID");
        if (!search.isEndPrjFlg()) {
            //完了TODOを表示しない
            sql.addSql("  and");
            sql.addSql("    PRJ_TODOSTATUS.PTS_RATE != ?");
            sql.addIntValue(GSConstProject.RATE_MAX);
        }

        //---------------- 2008/01/11 追加 ここから
        String selectingStatus = search.getSelectingStatus();

        //条件:「状態」が指定されている && 「状態」!=「全て」
        if (!StringUtil.isNullZeroString(selectingStatus)
                && !selectingStatus.equals(String.valueOf(GSConstProject.STATUS_ALL))) {

            int intSts = Integer.parseInt(selectingStatus);
            if (intSts > 0) {
                sql.addSql("  and");
                sql.addSql("    PRJ_TODOSTATUS.PTS_SID = ?");
                sql.addIntValue(intSts);
            } else {

                //0% 予定
                if (intSts == GSConstProject.STATUS_MIKAN) {
                    sql.addSql("  and");
                    sql.addSql("    PRJ_TODOSTATUS.PTS_RATE = ?");
                    sql.addIntValue(GSConstProject.RATE_MIN);
                //進行中
                } else if (intSts == GSConstProject.STATUS_SINKO) {
                    sql.addSql("  and");
                    sql.addSql("    PRJ_TODOSTATUS.PTS_RATE > ?");
                    sql.addSql("  and");
                    sql.addSql("    PRJ_TODOSTATUS.PTS_RATE < ?");
                    sql.addIntValue(GSConstProject.RATE_MIN);
                    sql.addIntValue(GSConstProject.RATE_MAX);
                //完了
                } else if (intSts == GSConstProject.STATUS_KANRYO) {
                    sql.addSql("  and");
                    sql.addSql("    PRJ_TODOSTATUS.PTS_RATE = ?");
                    sql.addIntValue(GSConstProject.RATE_MAX);
                //未完了
                } else if (intSts == GSConstProject.STATUS_YOTEI_AND_MIKAN) {
                    sql.addSql("  and");
                    sql.addSql("    PRJ_TODOSTATUS.PTS_RATE < ?");
                    sql.addIntValue(GSConstProject.RATE_MAX);
                }
            }
        }
        //---------------- 2008/01/11 追加 ここまで

        if (search.getGetKbn() != ProjectSearchModel.GET_BELONG) {
            //所属プロジェクト以外も取得する場合
            //自分以外のマイプロジェクトは取得しない
            sql.addSql("  and");
            sql.addSql("  (");
            sql.addSql("    PRJ_PRJDATA.PRJ_MY_KBN = ?");
            sql.addIntValue(GSConstProject.KBN_MY_PRJ_DEF);
            sql.addSql("    or");
            sql.addSql("    (");
            sql.addSql("      PRJ_PRJDATA.PRJ_MY_KBN = ?");
            sql.addIntValue(GSConstProject.KBN_MY_PRJ_MY);
            sql.addSql("      and");
            sql.addSql("      (");
            __addBelongMemberSql(sql, search.getUserSid());
            sql.addSql("      ");
            sql.addSql("      )");
            sql.addSql("    )");
            sql.addSql("  )");
        }

        //「参加プロジェクトのみ表示」だった場合は参加プロジェクトのみに絞り込み
        if (search.getGetKbn() == ProjectSearchModel.GET_BELONG
                || search.getJoinOnlyFlg()) {
            //所属プロジェクトのみ取得
            sql.addSql("  and");
            __addBelongMemberSql(sql, search.getUserSid());

        } else if (search.getGetKbn() == ProjectSearchModel.GET_OPEN) {
            //公開プロジェクトのみ取得(公開 or 所属プロジェクトを取得)
            sql.addSql("  and");
            sql.addSql("  (");
            sql.addSql("    PRJ_PRJDATA.PRJ_KOUKAI_KBN = ?");
            sql.addIntValue(GSConstProject.KBN_KOUKAI_ENABLED);
            sql.addSql("  or");
            //所属プロジェクトを取得
            sql.addSql("    (");
            __addBelongMemberSql(sql, search.getUserSid());
            sql.addSql("    )");
            sql.addSql("  )");
        }

        if (search.getProjectSid() > 0) {
            //プロジェクトSIDを指定
            sql.addSql("  and");
            sql.addSql("    PRJ_TODODATA.PRJ_SID = ?");
            sql.addIntValue(search.getProjectSid());
        }

        if (search.getTodoSid() > -1) {
            //TODOSIDを指定
            sql.addSql("  and");
            sql.addSql("    PRJ_TODODATA.PTD_SID = ?");
            sql.addIntValue(search.getTodoSid());
        }

        //---------------- 2008/01/11 追加 ここから
        String selectingMember = NullDefault.getString(search.getSelectingMember(), "");

        //条件「メンバ」が指定されている
        if (!StringUtil.isNullZeroString(selectingMember)) {
            //条件「メンバ」!=「全員」or「担当なし」
            if (!selectingMember.equals(String.valueOf(GSConstProject.MEMBER_ALL))
            && !selectingMember.equals(String.valueOf(GSConstProject.MEMBER_NOTHING))) {
                String[] targetUser = new String[1];
                targetUser[0] = selectingMember;
                search.setMemberSid(targetUser);
            }
        }
        //---------------- 2008/01/11 追加 ここまで
        if (search.isMemberBaseTodo()) {
            sql.addSql("      and");
            sql.addSql("        PRJ_TODOMEMBER.PRJ_SID = PRJ_TODODATA.PRJ_SID");
            sql.addSql("      and");
            sql.addSql("        PRJ_TODOMEMBER.PTD_SID = PRJ_TODODATA.PTD_SID");
            String[] memberSid = search.getMemberSid();
            if (memberSid == null || memberSid.length == 0) {
                sql.addSql("  and");
                sql.addSql("    PRJ_TODOMEMBER.USR_SID = -1");

            } else {

                sql.addSql("      and (PRJ_TODOMEMBER.USR_SID in (");

                for (int i = 0; i < memberSid.length; i++) {
                    if (i > 0 && i % 1000 == 0) {
                        sql.addSql("  ) or PRJ_TODOMEMBER.USR_SID in (");
                    }
                    if (i % 1000 != 0) {
                        sql.addSql("  ,");
                    }
                    sql.addSql(String.valueOf(
                            NullDefault.getInt(memberSid[i], 0)));
                }
                sql.addSql("  ))");
            }
        } else {
            String[] memberSid = search.getMemberSid();
            if (memberSid != null && memberSid.length > 0) {
                //担当メンバーSID入力時
                sql.addSql("  and");
                sql.addSql("    exists");
                sql.addSql("    (");
                sql.addSql("      select");
                sql.addSql("        0");
                sql.addSql("      from");
                sql.addSql("        PRJ_TODOMEMBER");
                sql.addSql("      where");
                sql.addSql("        PRJ_TODOMEMBER.USR_SID in (");
                for (int i = 0; i < memberSid.length; i++) {
                    sql.addSql("     ? ");
                    sql.addIntValue(NullDefault.getInt(memberSid[i], 0));

                    if (i < memberSid.length - 1) {
                        sql.addSql("     , ");
                    }
                }
                sql.addSql("              )");
                sql.addSql("      and");
                sql.addSql("        PRJ_TODOMEMBER.PRJ_SID = PRJ_TODODATA.PRJ_SID");
                sql.addSql("      and");
                sql.addSql("        PRJ_TODOMEMBER.PTD_SID = PRJ_TODODATA.PTD_SID");
                sql.addSql("    )");

            } else if (selectingMember.equals(String.valueOf(GSConstProject.MEMBER_NOTHING))) {
                //担当メンバーSID入力時
                sql.addSql("  and");
                sql.addSql("    not exists (");
                sql.addSql("      select 0 from PRJ_TODOMEMBER");
                sql.addSql("      where");
                sql.addSql("        PRJ_TODOMEMBER.PRJ_SID = PRJ_TODODATA.PRJ_SID");
                sql.addSql("      and");
                sql.addSql("        PRJ_TODOMEMBER.PTD_SID = PRJ_TODODATA.PTD_SID");
                sql.addSql("    )");
            }
        }
        String[] juyo = search.getJuyo();
        if (juyo != null && juyo.length > 0) {
            //重要度入力時
            sql.addSql("  and");
            sql.addSql("    PRJ_TODODATA.PTD_IMPORTANCE in (");
            for (int i = 0; i < juyo.length; i++) {
                sql.addSql("     ? ");
                sql.addIntValue(NullDefault.getInt(juyo[i], 0));

                if (i < juyo.length - 1) {
                    sql.addSql("     , ");
                }
            }
            sql.addSql("    )");
        }

        if (search.getStatusFrom() > -1) {
            //状態From入力時
            sql.addSql("  and");
            sql.addSql("    PRJ_TODOSTATUS.PTS_RATE >= ?");
            sql.addIntValue(search.getStatusFrom());
        }

        if (search.getStatusTo() > -1) {
            //状態To入力時
            sql.addSql("  and");
            sql.addSql("    PRJ_TODOSTATUS.PTS_RATE <= ?");
            sql.addIntValue(search.getStatusTo());
        }

        if (search.getPrjSearchKbn() == 0) {
            //プロジェクト
            if (search.getStartFrom() != null) {
                UDate startFr = search.getStartFrom().cloneUDate();
                startFr.setZeroHhMmSs();

                //開始予定入力時
                sql.addSql("  and");
                sql.addSql("    PRJ_TODODATA.PTD_DATE_PLAN >= ?");
                sql.addDateValue(startFr);
            }

            if (search.getStartTo() != null) {
                UDate startTo = search.getStartTo().cloneUDate();
                startTo.setMaxHhMmSs();

                //期限入力時
                sql.addSql("  and");
                sql.addSql("    PRJ_TODODATA.PRJ_DATE_LIMIT <= ?");
                sql.addDateValue(startTo);
            }
        } else if (search.getPrjSearchKbn() == 1) {
            //スケジュール用データ
            if (search.getStartFrom() != null) {
                //開始予定入力時
                UDate dateFr = search.getStartFrom().cloneUDate();
                dateFr.setZeroHhMmSs();
                UDate dateTo = search.getStartTo().cloneUDate();
                dateTo.setMaxHhMmSs();
                sql.addSql("  and");
                sql.addSql("    (");
                sql.addSql("     (");
                sql.addSql("       PRJ_TODODATA.PTD_DATE_PLAN >= ?");
                sql.addSql("       and PRJ_TODODATA.PTD_DATE_PLAN <= ?");
                sql.addDateValue(dateFr);
                sql.addDateValue(dateTo);
                sql.addSql("     )");
                sql.addSql("     or");
                sql.addSql("     (");
                sql.addSql("       PRJ_TODODATA.PRJ_DATE_LIMIT >= ?");
                sql.addSql("       and PRJ_TODODATA.PRJ_DATE_LIMIT <= ?");
                sql.addDateValue(dateFr);
                sql.addDateValue(dateTo);
                sql.addSql("     )");
                sql.addSql("     or");
                sql.addSql("     (");
                sql.addSql("       PRJ_TODODATA.PTD_DATE_PLAN <= ?");
                sql.addSql("       and PRJ_TODODATA.PRJ_DATE_LIMIT >= ?");
                sql.addDateValue(dateFr);
                sql.addDateValue(dateFr);
                sql.addSql("     )");
                sql.addSql("     or");
                sql.addSql("     (");
                sql.addSql("       PRJ_TODODATA.PTD_DATE_PLAN <= ?");
                sql.addSql("       and PRJ_TODODATA.PRJ_DATE_LIMIT >= ?");
                sql.addDateValue(dateTo);
                sql.addDateValue(dateTo);
                sql.addSql("     )");
                sql.addSql("    )");
            }
        } else if (search.getPrjSearchKbn() == 2) {
            //日報用データ
            UDate endFrDate = search.getEndFrom().cloneUDate();
            endFrDate.setZeroHhMmSs();
            UDate endToDate = search.getEndTo().cloneUDate();
            endToDate.setMaxHhMmSs();

            sql.addSql(" and");
            sql.addSql("  (");
            sql.addSql("    PRJ_TODODATA.PTD_DATE_START <= ?");
            sql.addDateValue(endToDate);
            sql.addSql("   and");
            sql.addSql("     (");
            sql.addSql("       PRJ_TODODATA.PTD_DATE_END >= ?");
            sql.addDateValue(endFrDate);
            sql.addSql("      or ");
            sql.addSql("       PRJ_TODODATA.PTD_DATE_END IS NULL");
            sql.addSql("     )");
            sql.addSql("  )");

        }

        if (search.getPrjSearchKbn() != 2) {
            //日報データ取得以外

            if (search.getEndFrom() != null) {
                UDate endFr = search.getEndFrom().cloneUDate();
                endFr.setZeroHhMmSs();
                //開始実績入力時
                sql.addSql("  and");
                sql.addSql("    PRJ_TODODATA.PTD_DATE_START >= ?");
                sql.addDateValue(endFr);
            }

            if (search.getEndTo() != null) {
                UDate endTo = search.getEndTo().cloneUDate();
                endTo.setMaxHhMmSs();
                //終了実績入力時
                sql.addSql("  and");
                sql.addSql("    PRJ_TODODATA.PTD_DATE_END <= ?");
                sql.addDateValue(endTo);
            }
        }



        // キーワード入力時
        List<String> keywordList = search.getPrjKeyValue();
        if (keywordList != null && keywordList.size() > 0) {

            String keywordJoin = "  and";
            if (search.getKeyWordkbn() == GSConstProject.KEY_WORD_KBN_OR) {
                keywordJoin = "   or";
            }

            //検索対象の「タイトル」がチェック済みの場合
            if (search.isTargetTitle()) {
                sql.addSql("  and");
                if (search.isTargetValue()) {
                    sql.addSql("    (");
                }
                sql.addSql("      (");
                for (int i = 0; i < keywordList.size(); i++) {
                    if (i > 0) {
                        sql.addSql(keywordJoin);
                    }
                    sql.addSql("       PRJ_TODODATA.PTD_TITLE like '%"
                            + JDBCUtil.escapeForLikeSearch(keywordList.get(i))
                            + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
                }
                sql.addSql("      )");
            }

            //検索対象の「内容」がチェック済みの場合
            if (search.isTargetValue()) {
                if (search.isTargetTitle()) {
                    sql.addSql("      or");
                } else {
                    sql.addSql("      and");
                }
                sql.addSql("      (");
                for (int i = 0; i < keywordList.size(); i++) {
                    if (i > 0) {
                        sql.addSql(keywordJoin);
                    }
                    sql.addSql("       PRJ_TODODATA.PTD_CONTENT like '%"
                            + JDBCUtil.escapeForLikeSearch(keywordList.get(i))
                            + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
                }
                sql.addSql("      )");

                if (search.isTargetTitle()) {
                    sql.addSql("    )");
                }
            }
        }

        String[] addUser = search.getAddUserSid();
        if (addUser != null && addUser.length > 0) {
            //登録者SID入力時
            sql.addSql("  and");
            sql.addSql("    PRJ_TODODATA.PTD_AUID in (");
            for (int i = 0; i < addUser.length; i++) {
                sql.addSql("     ? ");
                sql.addIntValue(NullDefault.getInt(addUser[i], 0));

                if (i < addUser.length - 1) {
                    sql.addSql("     , ");
                }
            }
            sql.addSql("    )");
        }

        //---------------- 2011/06/13 追加 ここから(管理番号検索)
        int kanriNumber = search.getKanriNumber();
        if (kanriNumber > 0) {
            //管理番号入力時
            sql.addSql("  and");
            sql.addSql("    PRJ_TODODATA.PTD_NO = ?");
            sql.addIntValue(search.getKanriNumber());
        }
        //---------------- 2011/06/13 追加 ここまで(管理番号検索)

        //---------------- 2010/12/15 追加 ここから
        //条件「カテゴリ」が指定されている
        String selectingCategory = search.getSelectingCategory();

        //条件「カテゴリ」が指定されている かつ 「カテゴリ」!=「全て」
        if (!StringUtil.isNullZeroString(selectingCategory)
        && !selectingCategory.equals(GSConstProject.TODO_CATEGORY_ALL)) {

            sql.addSql("  and");
            sql.addSql("    PRJ_TODODATA.PTD_CATEGORY = ?");
            sql.addIntValue(Integer.parseInt(selectingCategory));
        }
        //---------------- 2010/12/15 追加 ここまで


        //---------------- 2008/01/11 追加 ここから
        String selectingDate = search.getSelectingDate();

        //条件「日付」が指定されている
        if (!StringUtil.isNullZeroString(selectingDate)) {
            //条件「日付」!=「全て」
            if (!selectingDate.equals(String.valueOf(GSConstProject.DATE_ALL))) {

                //「日付」=「今日」
                if (selectingDate.equals(String.valueOf(GSConstProject.DATE_TODAY))) {

                    UDate dateFr = now.cloneUDate();
                    dateFr.setZeroHhMmSs();
                    UDate dateTo = now.cloneUDate();
                    dateTo.setMaxHhMmSs();

                    sql.addSql("  and");
                    sql.addSql("    PRJ_TODODATA.PTD_DATE_PLAN is not null");
                    sql.addSql("  and");
                    sql.addSql("    PRJ_TODODATA.PRJ_DATE_LIMIT is not null");
                    sql.addSql("  and");
                    sql.addSql("    (");
                    sql.addSql("     (");
                    sql.addSql("       PRJ_TODODATA.PTD_DATE_PLAN >= ?");
                    sql.addSql("       and PRJ_TODODATA.PTD_DATE_PLAN <= ?");
                    sql.addDateValue(dateFr);
                    sql.addDateValue(dateTo);
                    sql.addSql("     )");
                    sql.addSql("     or");
                    sql.addSql("     (");
                    sql.addSql("       PRJ_TODODATA.PRJ_DATE_LIMIT >= ?");
                    sql.addSql("       and PRJ_TODODATA.PRJ_DATE_LIMIT <= ?");
                    sql.addDateValue(dateFr);
                    sql.addDateValue(dateTo);
                    sql.addSql("     )");
                    sql.addSql("     or");
                    sql.addSql("     (");
                    sql.addSql("       PRJ_TODODATA.PTD_DATE_PLAN <= ?");
                    sql.addSql("       and PRJ_TODODATA.PRJ_DATE_LIMIT >= ?");
                    sql.addDateValue(dateFr);
                    sql.addDateValue(dateFr);
                    sql.addSql("     )");
                    sql.addSql("     or");
                    sql.addSql("     (");
                    sql.addSql("       PRJ_TODODATA.PTD_DATE_PLAN <= ?");
                    sql.addSql("       and PRJ_TODODATA.PRJ_DATE_LIMIT >= ?");
                    sql.addDateValue(dateTo);
                    sql.addDateValue(dateTo);
                    sql.addSql("     )");
                    sql.addSql("    )");


                //「日付」=「今日以前」
                } else if (selectingDate.equals(String.valueOf(GSConstProject.DATE_THE_PAST))) {

                    UDate date = now.cloneUDate();
                    date.setMaxHhMmSs();

                    sql.addSql("  and");
                    sql.addSql("    PRJ_TODODATA.PTD_DATE_PLAN is not null");
                    sql.addSql("  and");
                    sql.addSql("    PRJ_TODODATA.PRJ_DATE_LIMIT is not null");
                    sql.addSql("  and");
                    sql.addSql("    PRJ_TODODATA.PTD_DATE_PLAN <= ?");
                    sql.addDateValue(date);

                //「日付」=「今日以降」
                } else if (selectingDate.equals(String.valueOf(GSConstProject.DATE_THE_FUTURE))) {

                    UDate dateFr = now.cloneUDate();
                    dateFr.setZeroHhMmSs();
                    UDate dateTo = now.cloneUDate();
                    dateTo.setMaxHhMmSs();

                    sql.addSql("  and");
                    sql.addSql("    PRJ_TODODATA.PTD_DATE_PLAN is not null");
                    sql.addSql("  and");
                    sql.addSql("    PRJ_TODODATA.PRJ_DATE_LIMIT is not null");

                    sql.addSql("  and");
                    sql.addSql("    (");
                    sql.addSql("     PRJ_TODODATA.PTD_DATE_PLAN >= ?");
                    sql.addDateValue(dateFr);
//                    sql.addSql("     or");
//                    sql.addSql("     (");
//                    sql.addSql("       PRJ_TODODATA.PTD_DATE_PLAN >= ?");
//                    sql.addSql("       and PRJ_TODODATA.PTD_DATE_PLAN <= ?");
//                    sql.addDateValue(dateFr);
//                    sql.addDateValue(dateTo);
//                    sql.addSql("     )");
//                    sql.addSql("     or");
//                    sql.addSql("     (");
//                    sql.addSql("       PRJ_TODODATA.PRJ_DATE_LIMIT >= ?");
//                    sql.addSql("       and PRJ_TODODATA.PRJ_DATE_LIMIT <= ?");
//                    sql.addDateValue(dateFr);
//                    sql.addDateValue(dateTo);
//                    sql.addSql("     )");
                    sql.addSql("     or");
                    sql.addSql("     (");
                    sql.addSql("       PRJ_TODODATA.PTD_DATE_PLAN <= ?");
                    sql.addSql("       and PRJ_TODODATA.PRJ_DATE_LIMIT >= ?");
                    sql.addDateValue(dateFr);
                    sql.addDateValue(dateFr);
                    sql.addSql("     )");
                    sql.addSql("     or");
                    sql.addSql("     (");
                    sql.addSql("       PRJ_TODODATA.PTD_DATE_PLAN <= ?");
                    sql.addSql("       and PRJ_TODODATA.PRJ_DATE_LIMIT >= ?");
                    sql.addDateValue(dateTo);
                    sql.addDateValue(dateTo);
                    sql.addSql("     )");
                    sql.addSql("    )");


                //「日付」=「未入力」
                } else if (selectingDate.equals(String.valueOf(GSConstProject.DATE_NOT_INPUT))) {

                    sql.addSql("  and");
                    sql.addSql("    (PRJ_TODODATA.PTD_DATE_PLAN is null");
                    sql.addSql("     or");
                    sql.addSql("     PRJ_TODODATA.PRJ_DATE_LIMIT is null");
                    sql.addSql("    )");
                }
            }

          //---------------- 2008/01/11 追加 ここまで
//        } else {
//
//            if (NullDefault.getString(search.getMirai(), "").equals("")) {
//                //未来の予定も表示するにチェックが付いていない場合
//                sql.addSql("  and");
//                sql.addSql("    PRJ_TODODATA.PTD_DATE_PLAN <= cast(? as DATE)");
//                sql.addValue(now.getDateStringForSql());
//            }
        }

        //今日のTODOのみ表示
        if (search.isTodayFlg()) {
            UDate dateTo = now.cloneUDate();
            dateTo.setMaxHhMmSs();

            sql.addSql("  and");
            sql.addSql("   (");
            sql.addSql("     case");
            //開始・終了共にnullの場合は表示しない
            sql.addSql("     when");
            sql.addSql("       PRJ_TODODATA.PTD_DATE_PLAN is null");
            sql.addSql("     and");
            sql.addSql("       PRJ_TODODATA.PRJ_DATE_LIMIT is null");
            sql.addSql("     then");
            sql.addSql("       0");
            //開始のみ入力済みの場合は
            //開始<=システム日付のデータのみ表示
            sql.addSql("     when");
            sql.addSql("       PRJ_TODODATA.PTD_DATE_PLAN is not null");
            sql.addSql("     and");
            sql.addSql("       PRJ_TODODATA.PRJ_DATE_LIMIT is null");
            sql.addSql("     then");
            sql.addSql("       (");
            sql.addSql("         case");
            sql.addSql("         when");
            sql.addSql("           PRJ_TODODATA.PTD_DATE_PLAN <= ?");
            sql.addDateValue(dateTo);
            sql.addSql("         then");
            sql.addSql("           1");
            sql.addSql("         else");
            sql.addSql("           0");
            sql.addSql("         end");
            sql.addSql("       )");
            //終了のみ入力済みの場合は表示する
            sql.addSql("     when");
            sql.addSql("       PRJ_TODODATA.PTD_DATE_PLAN is null");
            sql.addSql("     and");
            sql.addSql("       PRJ_TODODATA.PRJ_DATE_LIMIT is not null");
            sql.addSql("     then");
            sql.addSql("       1");
            //開始・終了共に入力済みの場合は
            //開始<=システム日付のデータのみ表示
            sql.addSql("     when");
            sql.addSql("       PRJ_TODODATA.PTD_DATE_PLAN is not null");
            sql.addSql("     and");
            sql.addSql("       PRJ_TODODATA.PRJ_DATE_LIMIT is not null");
            sql.addSql("     then");
            sql.addSql("       (");
            sql.addSql("         case");
            sql.addSql("         when");
            sql.addSql("           PRJ_TODODATA.PTD_DATE_PLAN <= ?");
            sql.addDateValue(dateTo);
            sql.addSql("         then");
            sql.addSql("           1");
            sql.addSql("         else");
            sql.addSql("           0");
            sql.addSql("         end");
            sql.addSql("       )");
            sql.addSql("     else");
            sql.addSql("       0");
            sql.addSql("     end");
            sql.addSql("   )");
            sql.addSql("   = 1");
        }

        if (getKbn == GET_LIST) {
            //一覧表示
            //ソート
            sql.addSql(" order by ");
            __cleateOder(sql, search.getSort(), search.getOrder());
        }

        return sql;
    }

    /**
     * <br>[機  能] order by 以下のSQLを作成する(TODO取得時)
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SqlBuffer
     * @param sort ソートキー
     * @param order オーダーキー
     */
    private void __cleateOder(SqlBuffer sql, int sort, int order) {

        //昇順,降順指定
        String strOrder = "DESC";
        if (order == GSConst.ORDER_KEY_ASC) {
            strOrder = "ASC";
        }

        switch (sort) {
            case GSConstProject.SORT_PROJECT_TITLE:
                //プロジェクト名
                sql.addSql("     PRJ_PRJDATA.PRJ_NAME " + strOrder);
                break;

            case GSConstProject.SORT_TODO_NO:
                //管理番号
                sql.addSql("     PRJ_TODODATA.PTD_NO " + strOrder);
                break;

            case GSConstProject.SORT_TODO_TITLE:
                //TODOタイトル
                sql.addSql("     PRJ_TODODATA.PTD_TITLE " + strOrder);
                break;

            case GSConstProject.SORT_TODO_WEIGHT:
                //重要度 (+開始予定)
                sql.addSql("     PRJ_TODODATA.PTD_IMPORTANCE " + strOrder + ",");
                sql.addSql("     PRJ_TODODATA.PTD_DATE_PLAN " + strOrder);
                break;

            case GSConstProject.SORT_TODO_STATUS:
                //状態 (+開始予定)
                sql.addSql("     PRJ_TODOSTATUS.PTS_RATE " + strOrder + ",");
                sql.addSql("     PRJ_TODODATA.PTD_DATE_PLAN " + strOrder);
                break;

            case GSConstProject.SORT_TODO_START_PLAN:
                //開始予定 (+プロジェクトID)
                sql.addSql("     PRJ_TODODATA.PTD_DATE_PLAN " + strOrder + ",");
                sql.addSql("     PRJ_PRJDATA.PRJ_ID " + strOrder);
                break;

            case GSConstProject.SORT_TODO_LIMIT_PLAN:
                //期限 (+プロジェクトID)
                sql.addSql("     PRJ_TODODATA.PRJ_DATE_LIMIT " + strOrder + ",");
                sql.addSql("     PRJ_PRJDATA.PRJ_ID " + strOrder);
                break;

            case GSConstProject.SORT_PRJECT_ID:
                //プロジェクトID
                sql.addSql("     PRJ_PRJDATA.PRJ_ID " + strOrder);
                break;

            case GSConstProject.SORT_PROJECT_NAME:
                //プロジェクト名称
                sql.addSql("     PRJ_PRJDATA.PRJ_NAME " + strOrder);
                break;

            case GSConstProject.SORT_PRJECT_STATUS:
                //状態 (+開始)
                sql.addSql("     PRJ_PRJSTATUS.PRS_RATE " + strOrder + ",");
                sql.addSql("     PRJ_PRJDATA.PRJ_DATE_START " + strOrder);
                break;

            case GSConstProject.SORT_PRJECT_START:
                //開始 (+プロジェクトID)
                sql.addSql("     PRJ_PRJDATA.PRJ_DATE_START " + strOrder + ",");
                sql.addSql("     PRJ_PRJDATA.PRJ_ID " + strOrder);
                break;

            case GSConstProject.SORT_PRJECT_END:
                //終了 (+プロジェクトID)
                sql.addSql("     PRJ_PRJDATA.PRJ_DATE_END " + strOrder + ",");
                sql.addSql("     PRJ_PRJDATA.PRJ_ID " + strOrder);
                break;

            case GSConstProject.SORT_PRJECT_YOSAN:
                //予算
                sql.addSql("     PRJ_PRJDATA.PRJ_YOSAN " + strOrder);
                break;

            default:
                break;
        }
    }

    /**
     * <br>[機  能] プロジェクト情報の件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param search 検索条件Model
     * @return int プロジェクト情報の件数
     * @throws SQLException SQL実行例外
     */
    public int getDashBoardProjectCount(ProjectSearchModel search) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = __cleateDashBoardProjectGetSql(search, GET_COUNT);

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
     * <br>[機  能] プロジェクト情報の件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param search 検索条件Model
     * @return int プロジェクト情報の件数
     * @throws SQLException SQL実行例外
     */
    public int getProjectCount(ProjectSearchModel search) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = __cleateProjectGetSql(search, GET_COUNT);

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
     * <br>[機  能] プロジェクト情報リストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param search 検索条件Model
     * @return List int ProjectItemModel
     * @throws SQLException SQL実行例外
     */
    public List<ProjectItemModel> getDashBoardProjectList(ProjectSearchModel search)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ProjectItemModel> ret = new ArrayList<ProjectItemModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = __cleateDashBoardProjectGetSql(search, GET_LIST);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (search.getStart() > 1) {
                rs.absolute(search.getStart() - 1);
            }

            PrjMembersDao pmDao = new PrjMembersDao(con);

            for (int i = 0; rs.next() && i < search.getLimit(); i++) {
                ProjectItemModel retMdl = __getProjectItemModel(rs);
                //管理者を取得
                retMdl.setMemberList(pmDao.getMemberList(retMdl.getProjectSid(), true));
                ret.add(retMdl);
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
     * <br>[機  能] プロジェクト情報リストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param search 検索条件Model
     * @return List int ProjectItemModel
     * @throws SQLException SQL実行例外
     */
    public List<ProjectItemModel> getProjectList(ProjectSearchModel search) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ProjectItemModel> ret = new ArrayList<ProjectItemModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = __cleateProjectGetSql(search, GET_LIST);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (search.getStart() > 1) {
                rs.absolute(search.getStart() - 1);
            }

            PrjMembersDao pmDao = new PrjMembersDao(con);

            for (int i = 0; rs.next() && i < search.getLimit(); i++) {
                ProjectItemModel retMdl = __getProjectItemModel(rs);
                //管理者を取得
                retMdl.setMemberList(pmDao.getMemberList(retMdl.getProjectSid(), true));
                ret.add(retMdl);
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
     * <br>[機  能] プロジェクト情報リストを取得、CSVを生成する
     * <br>[解  説]
     * <br>[備  考]
     * @param search 検索条件Model
     * @param rl Prj040CsvRecordListenerImpl
     * @throws SQLException SQL実行例外
     * @throws CSVException CSV出力時例外
     */
    public void createProjectList(
        ProjectSearchModel search,
        Prj040CsvRecordListenerImpl rl) throws SQLException, CSVException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = __cleateProjectGetSql(search, GET_LIST);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            PrjMembersDao pmDao = new PrjMembersDao(con);

            while (rs.next()) {
                ProjectItemModel retMdl = __getProjectItemModel(rs);
                //メンバーを取得
                retMdl.setMemberList(pmDao.getMemberList(retMdl.getProjectSid(), false));
                rl.setRecord(retMdl);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] プロジェクト情報リストを取得する(ページングなし)
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param buMdl BaseUserModel
     * @param getKbn TODO作成時 処理モード
     *   0:全てのTODO作成権限があるプロジェクト
     *   1:自分が参加していてTODO作成権限があるプロジュクト
     * @return List int ProjectItemModel
     * @throws SQLException SQL実行例外
     */
    public List<ProjectItemModel> getCanCreateTodoProjectList(
            BaseUserModel buMdl, int getKbn)
        throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        ArrayList<ProjectItemModel> ret = new ArrayList<ProjectItemModel>();

        try {

            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    distinct PRJ_PRJDATA.PRJ_SID,");
            sql.addSql("    PRJ_PRJDATA.PRJ_MY_KBN,");
            sql.addSql("    PRJ_PRJDATA.PRJ_ID,");
            sql.addSql("    PRJ_PRJDATA.PRJ_NAME,");
            sql.addSql("    PRJ_PRJDATA.PRJ_NAME_SHORT,");
            sql.addSql("    PRJ_PRJDATA.PRJ_YOSAN,");
            sql.addSql("    PRJ_PRJDATA.PRJ_KOUKAI_KBN,");
            sql.addSql("    PRJ_PRJDATA.PRJ_DATE_START,");
            sql.addSql("    PRJ_PRJDATA.PRJ_DATE_END,");
            sql.addSql("    PRJ_PRJDATA.PRJ_TARGET,");
            sql.addSql("    PRJ_PRJDATA.PRJ_CONTENT,");
            sql.addSql("    PRJ_PRJDATA.BIN_SID,");
            sql.addSql("    PRJ_PRJSTATUS.PRS_NAME,");
            sql.addSql("    PRJ_PRJSTATUS.PRS_RATE");
            sql.addSql("  from");
            sql.addSql("    PRJ_PRJDATA,");
            sql.addSql("    PRJ_PRJSTATUS");
            sql.addSql("  where");
            sql.addSql("    PRJ_PRJDATA.PRJ_SID = PRJ_PRJSTATUS.PRJ_SID");
            sql.addSql("  and");
            sql.addSql("    PRJ_PRJDATA.PRJ_STATUS_SID = PRJ_PRJSTATUS.PRS_SID");

            sql.addSql("  and");
            sql.addSql("    (");

            //条件1：自分のマイプロジェクト
            sql.addSql("      (");
            sql.addSql("        PRJ_PRJDATA.PRJ_MY_KBN = ?");
            sql.addIntValue(GSConstProject.KBN_MY_PRJ_MY);
            sql.addSql("        and");
            sql.addSql("          exists");
            sql.addSql("            (");
            sql.addSql("              select");
            sql.addSql("                *");
            sql.addSql("              from");
            sql.addSql("                PRJ_MEMBERS");
            sql.addSql("              where");
            sql.addSql("                PRJ_MEMBERS.USR_SID = ?");
            sql.addIntValue(buMdl.getUsrsid());
            sql.addSql("              and");
            sql.addSql("                PRJ_MEMBERS.PRJ_SID = PRJ_PRJDATA.PRJ_SID");
            sql.addSql("            )");
            sql.addSql("      )");

            sql.addSql("      or");

            //TODO作成権限がある全てのプロジェクト
            if (getKbn == GSConstProject.PRJ_KBN_ADMIN) {

                //条件2-A：通常プロジェクト
                sql.addSql("      (");
                sql.addSql("        PRJ_PRJDATA.PRJ_MY_KBN = ?");
                sql.addIntValue(GSConstProject.KBN_MY_PRJ_DEF);


                sql.addSql("        and");

                //条件2-B：所属している & 権限が「所属メンバー」
                sql.addSql("          (");
                sql.addSql("            (");
                sql.addSql("              exists");
                sql.addSql("                (");
                sql.addSql("                  select");
                sql.addSql("                    *");
                sql.addSql("                  from");
                sql.addSql("                    PRJ_MEMBERS");
                sql.addSql("                  where");
                sql.addSql("                    PRJ_MEMBERS.USR_SID = ?");
                sql.addIntValue(buMdl.getUsrsid());
                sql.addSql("                  and");
                sql.addSql("                    PRJ_MEMBERS.PRJ_SID = PRJ_PRJDATA.PRJ_SID");
                sql.addSql("                  and");
                sql.addSql("                    PRJ_PRJDATA.PRJ_EDIT = ?");
                sql.addIntValue(GSConstProject.TODO_EDIT_KENGEN_MEM);
                sql.addSql("                )");
                sql.addSql("            )");

                sql.addSql("            or");

                //条件2-C：所属している & 権限が「管理者のみ」& 管理者である
                sql.addSql("            (");
                sql.addSql("              exists");
                sql.addSql("                (");
                sql.addSql("                  select");
                sql.addSql("                    *");
                sql.addSql("                  from");
                sql.addSql("                    PRJ_MEMBERS");
                sql.addSql("                  where");
                sql.addSql("                    PRJ_MEMBERS.USR_SID = ?");
                sql.addIntValue(buMdl.getUsrsid());
                sql.addSql("                  and");
                sql.addSql("                    PRJ_MEMBERS.PRJ_SID = PRJ_PRJDATA.PRJ_SID");
                sql.addSql("                  and");
                sql.addSql("                    PRJ_PRJDATA.PRJ_EDIT = ?");
                sql.addIntValue(GSConstProject.TODO_EDIT_KENGEN_ADM);
                sql.addSql("                  and");
                sql.addSql("                    PRJ_MEMBERS.PRM_ADMIN_KBN = ?");
                sql.addIntValue(GSConstProject.KBN_POWER_ADMIN);
                sql.addSql("                )");
                sql.addSql("            )");

                sql.addSql("            or");

                //条件2-D：公開されている & 権限が「権限設定なし」
                sql.addSql("            (");
                sql.addSql("              PRJ_PRJDATA.PRJ_EDIT = ?");
                sql.addIntValue(GSConstProject.TODO_EDIT_KENGEN_ALL);
                sql.addSql("              and");
                sql.addSql("              PRJ_PRJDATA.PRJ_KOUKAI_KBN = ?");
                sql.addIntValue(GSConstProject.KBN_KOUKAI_ENABLED);
                sql.addSql("            )");

                sql.addSql("            or");

                //条件2-E：非公開 & 所属している & 「権限設定なし」
                sql.addSql("            (");
                sql.addSql("              PRJ_PRJDATA.PRJ_EDIT = ?");
                sql.addIntValue(GSConstProject.TODO_EDIT_KENGEN_ALL);
                sql.addSql("              and");
                sql.addSql("              PRJ_PRJDATA.PRJ_KOUKAI_KBN = ?");
                sql.addIntValue(GSConstProject.KBN_KOUKAI_DISABLED);

                sql.addSql("              and");
                sql.addSql("                exists");
                sql.addSql("                  (");
                sql.addSql("                    select");
                sql.addSql("                      *");
                sql.addSql("                    from");
                sql.addSql("                      PRJ_MEMBERS");
                sql.addSql("                    where");
                sql.addSql("                      PRJ_MEMBERS.USR_SID = ?");
                sql.addIntValue(buMdl.getUsrsid());
                sql.addSql("                    and");
                sql.addSql("                      PRJ_MEMBERS.PRJ_SID = PRJ_PRJDATA.PRJ_SID");
                sql.addSql("                  )");
                sql.addSql("            )");

                sql.addSql("          )");
                sql.addSql("      )");

            //自分が参加していてTODO作成権限があるプロジェクト
            } else if (getKbn == GSConstProject.PRJ_KBN_PARTICIPATION) {

                //条件2-A：通常プロジェクト
                sql.addSql("      (");
                sql.addSql("        PRJ_PRJDATA.PRJ_MY_KBN = ?");
                sql.addIntValue(GSConstProject.KBN_MY_PRJ_DEF);

                sql.addSql("        and");

                //条件2-B：所属している & 権限が「所属メンバー」
                sql.addSql("          (");
                sql.addSql("            (");
                sql.addSql("              exists");
                sql.addSql("                (");
                sql.addSql("                  select");
                sql.addSql("                    *");
                sql.addSql("                  from");
                sql.addSql("                    PRJ_MEMBERS");
                sql.addSql("                  where");
                sql.addSql("                    PRJ_MEMBERS.USR_SID = ?");
                sql.addIntValue(buMdl.getUsrsid());
                sql.addSql("                  and");
                sql.addSql("                    PRJ_MEMBERS.PRJ_SID = PRJ_PRJDATA.PRJ_SID");
                sql.addSql("                  and");
                sql.addSql("                    PRJ_PRJDATA.PRJ_EDIT = ?");
                sql.addIntValue(GSConstProject.TODO_EDIT_KENGEN_MEM);
                sql.addSql("                )");
                sql.addSql("            )");

                sql.addSql("            or");

                //条件2-C：所属している & 権限が「管理者のみ」& 管理者である
                sql.addSql("            (");
                sql.addSql("              exists");
                sql.addSql("                (");
                sql.addSql("                  select");
                sql.addSql("                    *");
                sql.addSql("                  from");
                sql.addSql("                    PRJ_MEMBERS");
                sql.addSql("                  where");
                sql.addSql("                    PRJ_MEMBERS.USR_SID = ?");
                sql.addIntValue(buMdl.getUsrsid());
                sql.addSql("                  and");
                sql.addSql("                    PRJ_MEMBERS.PRJ_SID = PRJ_PRJDATA.PRJ_SID");
                sql.addSql("                  and");
                sql.addSql("                    PRJ_PRJDATA.PRJ_EDIT = ?");
                sql.addIntValue(GSConstProject.TODO_EDIT_KENGEN_ADM);
                sql.addSql("                  and");
                sql.addSql("                    PRJ_MEMBERS.PRM_ADMIN_KBN = ?");
                sql.addIntValue(GSConstProject.KBN_POWER_ADMIN);
                sql.addSql("                )");
                sql.addSql("            )");

                sql.addSql("            or");

                //条件2-D：所属している & 権限が「権限設定なし」
                sql.addSql("            (");
                sql.addSql("              exists");
                sql.addSql("                (");
                sql.addSql("                  select");
                sql.addSql("                    *");
                sql.addSql("                  from");
                sql.addSql("                    PRJ_MEMBERS");
                sql.addSql("                  where");
                sql.addSql("                    PRJ_MEMBERS.USR_SID = ?");
                sql.addIntValue(buMdl.getUsrsid());
                sql.addSql("                  and");
                sql.addSql("                    PRJ_MEMBERS.PRJ_SID = PRJ_PRJDATA.PRJ_SID");
                sql.addSql("                  and");
                sql.addSql("                    PRJ_PRJDATA.PRJ_EDIT = ?");
                sql.addIntValue(GSConstProject.TODO_EDIT_KENGEN_ALL);
                sql.addSql("                )");
                sql.addSql("            )");

                sql.addSql("          )");

                sql.addSql("      )");
            }

            sql.addSql("    )");

            sql.addSql("  order by");
            sql.addSql("    PRJ_PRJDATA.PRJ_MY_KBN desc,");
            sql.addSql("    PRJ_PRJSTATUS.PRS_RATE asc,");
            sql.addSql("    PRJ_PRJDATA.PRJ_ID desc");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getProjectItemModel(rs));
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
     * <br>[機  能] プロジェクト情報リストを取得する(ページングなし)
     * <br>[解  説]
     * <br>[備  考]
     * @param search 検索条件Model
     * @return List int ProjectItemModel
     * @throws SQLException SQL実行例外
     */
    public List<ProjectItemModel> getAllProjectList(ProjectSearchModel search)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ProjectItemModel> ret = new ArrayList<ProjectItemModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = __cleateProjectGetSql(search, GET_LIST);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getProjectItemModel(rs));
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
     * <br>[機  能] SQLを作成する(プロジェクト取得時)
     * <br>[解  説]
     * <br>[備  考]
     * @param search 検索条件Model
     * @param getKbn 0=カウント、1=一覧表示
     * @return SqlBuffer
     */
    private SqlBuffer __cleateDashBoardProjectGetSql(ProjectSearchModel search, int getKbn) {

        SqlBuffer sql = new SqlBuffer();
        sql.addSql("  select");

        if (getKbn == GET_COUNT) {
            //カウント
            sql.addSql("    count(distinct PRJ_PRJDATA.PRJ_SID) as CNT");

        } else {
            //一覧表示
            sql.addSql("    distinct PRJ_PRJDATA.PRJ_SID,");
            sql.addSql("    PRJ_PRJDATA.PRJ_MY_KBN,");
            sql.addSql("    PRJ_PRJDATA.PRJ_ID,");
            sql.addSql("    PRJ_PRJDATA.PRJ_NAME,");
            sql.addSql("    PRJ_PRJDATA.PRJ_NAME_SHORT,");
            sql.addSql("    PRJ_PRJDATA.PRJ_YOSAN,");
            sql.addSql("    PRJ_PRJDATA.PRJ_KOUKAI_KBN,");
            sql.addSql("    PRJ_PRJDATA.PRJ_DATE_START,");
            sql.addSql("    PRJ_PRJDATA.PRJ_DATE_END,");
            sql.addSql("    PRJ_PRJDATA.PRJ_TARGET,");
            sql.addSql("    PRJ_PRJDATA.PRJ_CONTENT,");
            sql.addSql("    PRJ_PRJDATA.BIN_SID,");
            sql.addSql("    PRJ_PRJSTATUS.PRS_NAME,");
            sql.addSql("    PRJ_PRJSTATUS.PRS_RATE");
        }

        sql.addSql("  from");
        sql.addSql("    PRJ_PRJDATA,");
        sql.addSql("    PRJ_PRJSTATUS");
        sql.addSql("  where");
        sql.addSql("    PRJ_PRJDATA.PRJ_SID = PRJ_PRJSTATUS.PRJ_SID");
        sql.addSql("  and");
        sql.addSql("    PRJ_PRJDATA.PRJ_STATUS_SID = PRJ_PRJSTATUS.PRS_SID");

        sql.addSql("  and");
        sql.addSql("    (");

        //自分のマイプロジェクト
        sql.addSql("      (");
        sql.addSql("        PRJ_PRJDATA.PRJ_MY_KBN = ?");
        sql.addIntValue(GSConstProject.KBN_MY_PRJ_MY);
        sql.addSql("        and");
        sql.addSql("          exists");
        sql.addSql("            (");
        sql.addSql("              select");
        sql.addSql("                *");
        sql.addSql("              from");
        sql.addSql("                PRJ_MEMBERS");
        sql.addSql("              where");
        sql.addSql("                PRJ_MEMBERS.USR_SID = ?");
        sql.addIntValue(search.getUserSid());
        sql.addSql("              and");
        sql.addSql("                PRJ_MEMBERS.PRJ_SID = PRJ_PRJDATA.PRJ_SID");
        sql.addSql("            )");
        sql.addSql("      )");


        int prjStatus = search.getPrjStatus();

        //プロジェクト条件：参加プロジェクト
        if (prjStatus >= GSConstProject.PRJ_MEMBER_NOT_END
                && prjStatus <= GSConstProject.PRJ_MEMBER_ALL) {

            //所属している通常プロジェクト
            sql.addSql("      or");
            sql.addSql("      (");
            sql.addSql("        PRJ_PRJDATA.PRJ_MY_KBN = ?");
            sql.addIntValue(GSConstProject.KBN_MY_PRJ_DEF);
            sql.addSql("        and");
            sql.addSql("          exists");
            sql.addSql("            (");
            sql.addSql("              select");
            sql.addSql("                *");
            sql.addSql("              from");
            sql.addSql("                PRJ_MEMBERS");
            sql.addSql("              where");
            sql.addSql("                PRJ_MEMBERS.USR_SID = ?");
            sql.addIntValue(search.getUserSid());
            sql.addSql("              and");
            sql.addSql("                PRJ_MEMBERS.PRJ_SID = PRJ_PRJDATA.PRJ_SID");
            sql.addSql("            )");
            sql.addSql("      )");
            sql.addSql("    )");

            switch (prjStatus) {
                //未完
                case GSConstProject.PRJ_MEMBER_NOT_END:
                    sql.addSql("  and");
                    sql.addSql("    PRJ_PRJSTATUS.PRS_RATE < ?");
                    sql.addIntValue(GSConstProject.RATE_MAX);
                    break;
                //完了
                case GSConstProject.PRJ_MEMBER_END:
                    sql.addSql("  and");
                    sql.addSql("    PRJ_PRJSTATUS.PRS_RATE = ?");
                    sql.addIntValue(GSConstProject.RATE_MAX);
                    break;
                //全て
                case GSConstProject.PRJ_MEMBER_ALL:
                    break;
                default:
                    break;
            }

        //プロジェクト条件：公開プロジェクト
        } else if (prjStatus >= GSConstProject.PRJ_OPEN_NOT_END
                && prjStatus <= GSConstProject.PRJ_OPEN_ALL) {
            sql.addSql("      or");
            //通常プロジェクト
            sql.addSql("      (");
            sql.addSql("        PRJ_PRJDATA.PRJ_MY_KBN = ?");
            sql.addIntValue(GSConstProject.KBN_MY_PRJ_DEF);
            sql.addSql("      )");
            sql.addSql("    )");

            sql.addSql("  and");
            sql.addSql("    PRJ_PRJDATA.PRJ_KOUKAI_KBN = ?");
            sql.addIntValue(GSConstProject.KBN_KOUKAI_ENABLED);

            switch (prjStatus) {
                //公開プロジェクト 未完
                case GSConstProject.PRJ_OPEN_NOT_END:
                    sql.addSql("  and");
                    sql.addSql("    PRJ_PRJSTATUS.PRS_RATE < ?");
                    sql.addIntValue(GSConstProject.RATE_MAX);
                    break;
                //公開プロジェクト 完了
                case GSConstProject.PRJ_OPEN_END:
                    sql.addSql("  and");
                    sql.addSql("    PRJ_PRJSTATUS.PRS_RATE = ?");
                    sql.addIntValue(GSConstProject.RATE_MAX);
                    break;
                //公開プロジェクト 全て
                case GSConstProject.PRJ_OPEN_ALL:
                    break;
                default:
                    break;
            }

        //プロジェクト条件：全て(システム管理者限定)
        } else if (prjStatus >= GSConstProject.PRJ_NOT_END_ALL
                && prjStatus <= GSConstProject.PRJ_ALL) {

            //通常プロジェクト
            sql.addSql("      or");
            sql.addSql("      (");
            sql.addSql("        PRJ_PRJDATA.PRJ_MY_KBN = ?");
            sql.addIntValue(GSConstProject.KBN_MY_PRJ_DEF);
            sql.addSql("      )");
            sql.addSql("    )");

            switch (prjStatus) {
                //全ての未完プロジェクト
                case GSConstProject.PRJ_NOT_END_ALL:
                    sql.addSql("  and");
                    sql.addSql("    PRJ_PRJSTATUS.PRS_RATE < ?");
                    sql.addIntValue(GSConstProject.RATE_MAX);
                    break;
                //全ての完了プロジェクト
                case GSConstProject.PRJ_END_ALL:
                    sql.addSql("  and");
                    sql.addSql("    PRJ_PRJSTATUS.PRS_RATE = ?");
                    sql.addIntValue(GSConstProject.RATE_MAX);
                    break;
                //全てのプロジェクト
                case GSConstProject.PRJ_ALL:
                    break;
                default:
                    break;
            }
        } else {
            sql.addSql("    )");
        }

        if (getKbn == GET_LIST) {
            //一覧表示
            //ソート
            sql.addSql(" order by ");
            __cleateOder(sql, search.getSort(), search.getOrder());
        }

        return sql;
    }

    /**
     * <br>[機  能] SQLを作成する(プロジェクト取得時)
     * <br>[解  説]
     * <br>[備  考]
     * @param search 検索条件Model
     * @param getKbn 0=カウント、1=一覧表示
     * @return SqlBuffer
     */
    private SqlBuffer __cleateProjectGetSql(ProjectSearchModel search, int getKbn) {

        SqlBuffer sql = new SqlBuffer();
        sql.addSql("  select");

        if (getKbn == GET_COUNT) {
            //カウント
            sql.addSql("    count(distinct PRJ_PRJDATA.PRJ_SID) as CNT");

        } else {
            //一覧表示
            sql.addSql("    distinct PRJ_PRJDATA.PRJ_SID,");
            sql.addSql("    PRJ_PRJDATA.PRJ_MY_KBN,");
            sql.addSql("    PRJ_PRJDATA.PRJ_ID,");
            sql.addSql("    PRJ_PRJDATA.PRJ_NAME,");
            sql.addSql("    PRJ_PRJDATA.PRJ_NAME_SHORT,");
            sql.addSql("    PRJ_PRJDATA.PRJ_YOSAN,");
            sql.addSql("    PRJ_PRJDATA.PRJ_KOUKAI_KBN,");
            sql.addSql("    PRJ_PRJDATA.PRJ_DATE_START,");
            sql.addSql("    PRJ_PRJDATA.PRJ_DATE_END,");
            sql.addSql("    PRJ_PRJDATA.PRJ_TARGET,");
            sql.addSql("    PRJ_PRJDATA.PRJ_CONTENT,");
            sql.addSql("    PRJ_PRJDATA.BIN_SID,");
            sql.addSql("    PRJ_PRJSTATUS.PRS_NAME,");
            sql.addSql("    PRJ_PRJSTATUS.PRS_RATE");
        }

        sql.addSql("  from");
        sql.addSql("    PRJ_PRJDATA,");
        sql.addSql("    PRJ_PRJSTATUS");
        sql.addSql("  where");
        sql.addSql("    PRJ_PRJDATA.PRJ_SID = PRJ_PRJSTATUS.PRJ_SID");
        sql.addSql("  and");
        sql.addSql("    PRJ_PRJDATA.PRJ_STATUS_SID = PRJ_PRJSTATUS.PRS_SID");

        if (!search.isEndPrjFlg()) {
            //完了プロジェクトを表示しない
            sql.addSql("  and");
            sql.addSql("    PRJ_PRJSTATUS.PRS_RATE != ?");
            sql.addIntValue(GSConstProject.RATE_MAX);
        }

        if (search.getGetKbn() != ProjectSearchModel.GET_BELONG
                && search.getGetKbn() != ProjectSearchModel.GET_EDIT
                && search.getGetKbn() != ProjectSearchModel.GET_SELECTED) {
            //所属プロジェクト以外も取得する場合
            //自分以外のマイプロジェクトは取得しない
            sql.addSql("  and");
            sql.addSql("  (");
            sql.addSql("    PRJ_PRJDATA.PRJ_MY_KBN = ?");
            sql.addIntValue(GSConstProject.KBN_MY_PRJ_DEF);
            sql.addSql("    or");
            sql.addSql("    (");
            sql.addSql("      PRJ_PRJDATA.PRJ_MY_KBN = ?");
            sql.addIntValue(GSConstProject.KBN_MY_PRJ_MY);
            sql.addSql("      and");
            sql.addSql("      (");
            __addBelongMemberSql(sql, search.getUserSid());
            sql.addSql("      ");
            sql.addSql("      )");
            sql.addSql("    )");
            sql.addSql("  )");
        }

        if (search.getGetKbn() == ProjectSearchModel.GET_BELONG) {
            //所属プロジェクトのみ取得
            sql.addSql("  and");
            __addBelongMemberSql(sql, search.getUserSid());

            if (search.getProjectSid() > 0) {
                sql.addSql("  and");
                sql.addSql("    PRJ_PRJDATA.PRJ_SID = ?");
                sql.addIntValue(search.getProjectSid());
            }

        } else if (search.getGetKbn() == ProjectSearchModel.GET_OPEN
                || search.getGetKbn() == ProjectSearchModel.GET_OPEN_NOT_BELONG) {

            //公開プロジェクトのみ取得
            sql.addSql("  and");
            sql.addSql("  (");
            sql.addSql("    PRJ_PRJDATA.PRJ_KOUKAI_KBN = ?");
            sql.addIntValue(GSConstProject.KBN_KOUKAI_ENABLED);

            if (search.getGetKbn() == ProjectSearchModel.GET_OPEN) {
                //公開プロジェクトのみ取得(所属プロジェクトを含む)
                sql.addSql("  or");
                //所属プロジェクトを取得
                sql.addSql("    (");
                __addBelongMemberSql(sql, search.getUserSid());
                sql.addSql("    )");
            }

            sql.addSql("  )");

        } else if (search.getGetKbn() == ProjectSearchModel.GET_EDIT) {

            sql.addSql("  and");
            sql.addSql("    (");

            //条件1：自分のマイプロジェクト
            sql.addSql("      (");
            sql.addSql("        PRJ_PRJDATA.PRJ_MY_KBN = ?");
            sql.addIntValue(GSConstProject.KBN_MY_PRJ_MY);
            sql.addSql("        and");
            sql.addSql("          exists");
            sql.addSql("            (");
            sql.addSql("              select");
            sql.addSql("                *");
            sql.addSql("              from");
            sql.addSql("                PRJ_MEMBERS");
            sql.addSql("              where");
            sql.addSql("                PRJ_MEMBERS.USR_SID = ?");
            sql.addIntValue(search.getUserSid());
            sql.addSql("              and");
            sql.addSql("                PRJ_MEMBERS.PRJ_SID = PRJ_PRJDATA.PRJ_SID");
            sql.addSql("            )");
            sql.addSql("      )");

            sql.addSql("      or");

            //条件2-A：通常プロジェクト
            sql.addSql("      (");
            sql.addSql("        PRJ_PRJDATA.PRJ_MY_KBN = ?");
            sql.addIntValue(GSConstProject.KBN_MY_PRJ_DEF);

            if (search.isAdmin()) {
                sql.addSql("      )");
                sql.addSql("    )");
            } else {

                sql.addSql("        and");

                //条件2-B：所属している & 権限が「所属メンバー」
                sql.addSql("          (");
                sql.addSql("            (");
                sql.addSql("              exists");
                sql.addSql("                (");
                sql.addSql("                  select");
                sql.addSql("                    *");
                sql.addSql("                  from");
                sql.addSql("                    PRJ_MEMBERS");
                sql.addSql("                  where");
                sql.addSql("                    PRJ_MEMBERS.USR_SID = ?");
                sql.addIntValue(search.getUserSid());
                sql.addSql("                  and");
                sql.addSql("                    PRJ_MEMBERS.PRJ_SID = PRJ_PRJDATA.PRJ_SID");
                sql.addSql("                  and");
                sql.addSql("                    PRJ_PRJDATA.PRJ_EDIT = ?");
                sql.addIntValue(GSConstProject.TODO_EDIT_KENGEN_MEM);
                sql.addSql("                )");
                sql.addSql("            )");

                sql.addSql("            or");

                //条件2-C：所属している & 権限が「管理者のみ」& 管理者である
                sql.addSql("            (");
                sql.addSql("              exists");
                sql.addSql("                (");
                sql.addSql("                  select");
                sql.addSql("                    *");
                sql.addSql("                  from");
                sql.addSql("                    PRJ_MEMBERS");
                sql.addSql("                  where");
                sql.addSql("                    PRJ_MEMBERS.USR_SID = ?");
                sql.addIntValue(search.getUserSid());
                sql.addSql("                  and");
                sql.addSql("                    PRJ_MEMBERS.PRJ_SID = PRJ_PRJDATA.PRJ_SID");
                sql.addSql("                  and");
                sql.addSql("                    PRJ_PRJDATA.PRJ_EDIT = ?");
                sql.addIntValue(GSConstProject.TODO_EDIT_KENGEN_ADM);
                sql.addSql("                  and");
                sql.addSql("                    PRJ_MEMBERS.PRM_ADMIN_KBN = ?");
                sql.addIntValue(GSConstProject.KBN_POWER_ADMIN);
                sql.addSql("                )");
                sql.addSql("            )");

                sql.addSql("            or");

                //条件2-D：公開されている & 権限が「権限設定なし」
                sql.addSql("            (");
                sql.addSql("              PRJ_PRJDATA.PRJ_EDIT = ?");
                sql.addIntValue(GSConstProject.TODO_EDIT_KENGEN_ALL);
                sql.addSql("              and");
                sql.addSql("              PRJ_PRJDATA.PRJ_KOUKAI_KBN = ?");
                sql.addIntValue(GSConstProject.KBN_KOUKAI_ENABLED);
                sql.addSql("            )");

                sql.addSql("            or");

                //条件2-E：非公開 & 所属している & 「権限設定なし」
                sql.addSql("            (");
                sql.addSql("              PRJ_PRJDATA.PRJ_EDIT = ?");
                sql.addIntValue(GSConstProject.TODO_EDIT_KENGEN_ALL);
                sql.addSql("              and");
                sql.addSql("              PRJ_PRJDATA.PRJ_KOUKAI_KBN = ?");
                sql.addIntValue(GSConstProject.KBN_KOUKAI_DISABLED);

                sql.addSql("              and");
                sql.addSql("                exists");
                sql.addSql("                  (");
                sql.addSql("                    select");
                sql.addSql("                      *");
                sql.addSql("                    from");
                sql.addSql("                      PRJ_MEMBERS");
                sql.addSql("                    where");
                sql.addSql("                      PRJ_MEMBERS.USR_SID = ?");
                sql.addIntValue(search.getUserSid());
                sql.addSql("                    and");
                sql.addSql("                      PRJ_MEMBERS.PRJ_SID = PRJ_PRJDATA.PRJ_SID");
                sql.addSql("                  )");
                sql.addSql("            )");

                sql.addSql("          )");
                sql.addSql("      )");
                sql.addSql("    )");
            }

        //プロジェクトの状態により選択
        } else if (search.getGetKbn() == ProjectSearchModel.GET_SELECTED) {

            sql.addSql("  and");
            sql.addSql("    (");

            //自分のマイプロジェクト
            sql.addSql("      (");
            sql.addSql("        PRJ_PRJDATA.PRJ_MY_KBN = ?");
            sql.addIntValue(GSConstProject.KBN_MY_PRJ_MY);
            sql.addSql("        and");
            sql.addSql("          exists");
            sql.addSql("            (");
            sql.addSql("              select");
            sql.addSql("                *");
            sql.addSql("              from");
            sql.addSql("                PRJ_MEMBERS");
            sql.addSql("              where");
            sql.addSql("                PRJ_MEMBERS.USR_SID = ?");
            sql.addIntValue(search.getUserSid());
            sql.addSql("              and");
            sql.addSql("                PRJ_MEMBERS.PRJ_SID = PRJ_PRJDATA.PRJ_SID");
            sql.addSql("            )");
            sql.addSql("      )");

            sql.addSql("      or");

            sql.addSql("      (");
            sql.addSql("        PRJ_PRJDATA.PRJ_MY_KBN = ?");
            sql.addIntValue(GSConstProject.KBN_MY_PRJ_DEF);
            sql.addSql("        and");
            sql.addSql("          exists");
            sql.addSql("            (");
            sql.addSql("              select");
            sql.addSql("                *");
            sql.addSql("              from");
            sql.addSql("                PRJ_MEMBERS");
            sql.addSql("              where");
            sql.addSql("                PRJ_MEMBERS.USR_SID = ?");
            sql.addIntValue(search.getUserSid());
            sql.addSql("              and");
            sql.addSql("                PRJ_MEMBERS.PRJ_SID = PRJ_PRJDATA.PRJ_SID");
            sql.addSql("            )");
            sql.addSql("      )");
            sql.addSql("    )");

            int prjStatus = search.getPrjStatus();

            //プロジェクト条件：参加プロジェクト
            if (prjStatus >= GSConstProject.PRJ_MEMBER_NOT_END
                    && prjStatus <= GSConstProject.PRJ_MEMBER_ALL) {

                switch (prjStatus) {
                    //未完
                    case GSConstProject.PRJ_MEMBER_NOT_END:
                        sql.addSql("  and");
                        sql.addSql("    PRJ_PRJSTATUS.PRS_RATE < ?");
                        sql.addIntValue(GSConstProject.RATE_MAX);
                        break;
                    //完了
                    case GSConstProject.PRJ_MEMBER_END:
                        sql.addSql("  and");
                        sql.addSql("    PRJ_PRJSTATUS.PRS_RATE = ?");
                        sql.addIntValue(GSConstProject.RATE_MAX);
                        break;
                    //全て
                    case GSConstProject.PRJ_MEMBER_ALL:
                        break;
                    default:
                        break;
                }

            //プロジェクト条件：公開プロジェクト
            } else if (prjStatus >= GSConstProject.PRJ_OPEN_NOT_END
                    && prjStatus <= GSConstProject.PRJ_OPEN_ALL) {

                sql.addSql("  and");
                sql.addSql("    PRJ_PRJDATA.PRJ_KOUKAI_KBN = ?");
                sql.addIntValue(GSConstProject.KBN_KOUKAI_ENABLED);

                switch (prjStatus) {
                    //公開プロジェクト 未完
                    case GSConstProject.PRJ_OPEN_NOT_END:
                        sql.addSql("  and");
                        sql.addSql("    PRJ_PRJSTATUS.PRS_RATE < ?");
                        sql.addIntValue(GSConstProject.RATE_MAX);
                        break;
                    //公開プロジェクト 完了
                    case GSConstProject.PRJ_OPEN_END:
                        sql.addSql("  and");
                        sql.addSql("    PRJ_PRJSTATUS.PRS_RATE = ?");
                        sql.addIntValue(GSConstProject.RATE_MAX);
                        break;
                    //公開プロジェクト 全て
                    case GSConstProject.PRJ_OPEN_ALL:
                        break;
                    default:
                        break;
                }

            //プロジェクト条件：全て(システム管理者限定)
            } else if (prjStatus >= GSConstProject.PRJ_NOT_END_ALL
                    && prjStatus <= GSConstProject.PRJ_ALL) {

                switch (prjStatus) {
                    //全ての未完プロジェクト
                    case GSConstProject.PRJ_NOT_END_ALL:
                        sql.addSql("  and");
                        sql.addSql("    PRJ_PRJSTATUS.PRS_RATE < ?");
                        sql.addIntValue(GSConstProject.RATE_MAX);
                        break;
                    //全ての完了プロジェクト
                    case GSConstProject.PRJ_END_ALL:
                        sql.addSql("  and");
                        sql.addSql("    PRJ_PRJSTATUS.PRS_RATE = ?");
                        sql.addIntValue(GSConstProject.RATE_MAX);
                        break;
                    //全てのプロジェクト
                    case GSConstProject.PRJ_ALL:
                        break;
                    default:
                        break;
                }
            }
        }

        if (!NullDefault.getString(search.getPrjId(), "").equals("")) {
            //プロジェクトID入力時
            sql.addSql("  and");
            sql.addSql("    PRJ_PRJDATA.PRJ_ID like '%"
                    + JDBCUtil.escapeForLikeSearch(search.getPrjId())
                    + "%' ESCAPE '"
                    + JDBCUtil.def_esc
                    + "'");
        }

        if (search.getStatusFrom() > -1) {
            //状態From入力時
            sql.addSql("  and");
            sql.addSql("  (");
            sql.addSql("    PRJ_PRJSTATUS.PRS_RATE >= ?");
            sql.addSql("  and");
            sql.addSql("    PRJ_PRJDATA.PRJ_MY_KBN = ?");
            sql.addSql("  )");
            sql.addIntValue(search.getStatusFrom());
            sql.addIntValue(GSConstProject.KBN_MY_PRJ_DEF);
        }

        if (search.getStatusTo() > -1) {
            //状態To入力時
            sql.addSql("  and");
            sql.addSql("  (");
            sql.addSql("    PRJ_PRJSTATUS.PRS_RATE <= ?");
            sql.addSql("  and");
            sql.addSql("    PRJ_PRJDATA.PRJ_MY_KBN = ?");
            sql.addSql("  )");
            sql.addIntValue(search.getStatusTo());
            sql.addIntValue(GSConstProject.KBN_MY_PRJ_DEF);
        }

        if (!NullDefault.getString(search.getPrjName(), "").equals("")) {
            //プロジェクト名入力時
            sql.addSql("  and");
            sql.addSql("    PRJ_PRJDATA.PRJ_NAME like '%"
                    + JDBCUtil.escapeForLikeSearch(search.getPrjName())
                    + "%' ESCAPE '"
                    + JDBCUtil.def_esc
                    + "'");
        }

        String[] memberSid = search.getMemberSid();
        if (memberSid != null && memberSid.length > 0) {
            //メンバーSID入力時
            sql.addSql("  and");
            sql.addSql("    exists");
            sql.addSql("    (");
            sql.addSql("      select");
            sql.addSql("        0");
            sql.addSql("      from");
            sql.addSql("        PRJ_MEMBERS");
            sql.addSql("      where");
            sql.addSql("        PRJ_MEMBERS.USR_SID in (");
            for (int i = 0; i < memberSid.length; i++) {
                sql.addSql("     ? ");
                sql.addIntValue(NullDefault.getInt(memberSid[i], 0));

                if (i < memberSid.length - 1) {
                    sql.addSql("     , ");
                }
            }
            sql.addSql("              )");
            sql.addSql("      and");
            sql.addSql("        PRJ_MEMBERS.PRJ_SID = PRJ_PRJDATA.PRJ_SID");
            sql.addSql("    )");
        }

        if (search.getStartFrom() != null) {
            //開始From入力時
            sql.addSql("  and");
            sql.addSql("    PRJ_PRJDATA.PRJ_DATE_START >= cast(? as DATE)");
            sql.addStrValue(search.getStartFrom().getDateStringForSql());
        }

        if (search.getStartTo() != null) {
            //開始To入力時
            sql.addSql("  and");
            sql.addSql("    PRJ_PRJDATA.PRJ_DATE_START <= cast(? as DATE)");
            sql.addStrValue(search.getStartTo().getDateStringForSql());
        }

        if (search.getEndFrom() != null) {
            //終了From入力時
            sql.addSql("  and");
            sql.addSql("    PRJ_PRJDATA.PRJ_DATE_END >= cast(? as DATE)");
            sql.addStrValue(search.getEndFrom().getDateStringForSql());
        }

        if (search.getEndTo() != null) {
            //終了To入力時
            sql.addSql("  and");
            sql.addSql("    PRJ_PRJDATA.PRJ_DATE_END <= cast(? as DATE)");
            sql.addStrValue(search.getEndTo().getDateStringForSql());
        }

        if (search.getYosanFrom() > -1) {
            //予算From入力時
            sql.addSql("  and");
            sql.addSql("    PRJ_PRJDATA.PRJ_YOSAN >= ?");
            sql.addLongValue(search.getYosanFrom());
        }

        if (search.getYosanTo() > -1) {
            //予算To入力時
            sql.addSql("  and");
            sql.addSql("    PRJ_PRJDATA.PRJ_YOSAN <= ?");
            sql.addSql("  and");
            sql.addSql("    PRJ_PRJDATA.PRJ_YOSAN > -1");
            sql.addLongValue(search.getYosanTo());
        }

        if (getKbn == GET_LIST) {
            //一覧表示
            //ソート
            sql.addSql(" order by ");
            __cleateOder(sql, search.getSort(), search.getOrder());
        }

        return sql;
    }

    /**
     * <br>[機  能] 取得条件(所属プロジェクトのみ取得)をSQLに追加する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SqlBuffer
     * @param userSid ユーザSID
     */
    private void __addBelongMemberSql(SqlBuffer sql, int userSid) {

        sql.addSql("    exists");
        sql.addSql("    (");
        sql.addSql("      select");
        sql.addSql("        0");
        sql.addSql("      from");
        sql.addSql("        PRJ_MEMBERS");
        sql.addSql("      where");
        sql.addSql("        PRJ_MEMBERS.USR_SID = ?");
        sql.addIntValue(userSid);
        sql.addSql("      and");
        sql.addSql("        PRJ_MEMBERS.PRJ_SID = PRJ_PRJDATA.PRJ_SID");
        sql.addSql("    )");
    }

    /**
     * <br>[機  能] 取得条件(警告区分)をSQLに追加する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SqlBuffer
     */
    private void __addKeikokuSql(SqlBuffer sql) {

        UDate now = new UDate();

        //1ヶ月後
        UDate bef30 = now.cloneUDate();
        bef30.add(Calendar.MONTH, +1);
        bef30.setMaxHhMmSs();
        //10日後
        UDate bef10 = now.cloneUDate();
        bef10.add(Calendar.DATE, +10);
        bef10.setMaxHhMmSs();
        //5日後
        UDate bef5 = now.cloneUDate();
        bef5.add(Calendar.DATE, +5);
        bef5.setMaxHhMmSs();
        //3日後
        UDate bef3 = now.cloneUDate();
        bef3.add(Calendar.DATE, +3);
        bef3.setMaxHhMmSs();
        //1日後
        UDate bef1 = now.cloneUDate();
        bef1.add(Calendar.DATE, +1);
        bef1.setMaxHhMmSs();

        sql.addSql("    (");
        sql.addSql("      case");
        sql.addSql("        PRJ_TODODATA.PTD_ALARM_KBN");
        sql.addSql("      when");
        sql.addSql("        ?");
        sql.addIntValue(GSConstProject.KEIKOKU_BEF30);
        sql.addSql("      then");
        sql.addSql("        (");
        sql.addSql("          case");
        sql.addSql("          when");
        sql.addSql("            PRJ_TODODATA.PRJ_DATE_LIMIT <= ?");
        sql.addDateValue(bef30);
        sql.addSql("          then");
        sql.addSql("            ?::integer");
        sql.addIntValue(GSConstProject.KEIKOKU_ARI);
        sql.addSql("          else");
        sql.addSql("            ?::integer");
        sql.addIntValue(GSConstProject.KEIKOKU_NASI);
        sql.addSql("          end");
        sql.addSql("        )");

        sql.addSql("      when");
        sql.addSql("        ?");
        sql.addIntValue(GSConstProject.KEIKOKU_BEF10);
        sql.addSql("      then");
        sql.addSql("        (");
        sql.addSql("          case");
        sql.addSql("          when");
        sql.addSql("            PRJ_TODODATA.PRJ_DATE_LIMIT <= ?");
        sql.addDateValue(bef10);
        sql.addSql("          then");
        sql.addSql("            ?::integer");
        sql.addIntValue(GSConstProject.KEIKOKU_ARI);
        sql.addSql("          else");
        sql.addSql("            ?::integer");
        sql.addIntValue(GSConstProject.KEIKOKU_NASI);
        sql.addSql("          end");
        sql.addSql("        )");

        sql.addSql("      when");
        sql.addSql("        ?");
        sql.addIntValue(GSConstProject.KEIKOKU_BEF5);
        sql.addSql("      then");
        sql.addSql("        (");
        sql.addSql("          case");
        sql.addSql("          when");
        sql.addSql("            PRJ_TODODATA.PRJ_DATE_LIMIT <= ?");
        sql.addDateValue(bef5);
        sql.addSql("          then");
        sql.addSql("            ?::integer");
        sql.addIntValue(GSConstProject.KEIKOKU_ARI);
        sql.addSql("          else");
        sql.addSql("            ?::integer");
        sql.addIntValue(GSConstProject.KEIKOKU_NASI);
        sql.addSql("          end");
        sql.addSql("        )");

        sql.addSql("      when");
        sql.addSql("        ?");
        sql.addIntValue(GSConstProject.KEIKOKU_BEF3);
        sql.addSql("      then");
        sql.addSql("        (");
        sql.addSql("          case");
        sql.addSql("          when");
        sql.addSql("            PRJ_TODODATA.PRJ_DATE_LIMIT <= ?");
        sql.addDateValue(bef3);
        sql.addSql("          then");
        sql.addSql("            ?::integer");
        sql.addIntValue(GSConstProject.KEIKOKU_ARI);
        sql.addSql("          else");
        sql.addSql("            ?::integer");
        sql.addIntValue(GSConstProject.KEIKOKU_NASI);
        sql.addSql("          end");
        sql.addSql("        )");

        sql.addSql("      when");
        sql.addSql("        ?");
        sql.addIntValue(GSConstProject.KEIKOKU_BEF1);
        sql.addSql("      then");
        sql.addSql("        (");
        sql.addSql("          case");
        sql.addSql("          when");
        sql.addSql("            PRJ_TODODATA.PRJ_DATE_LIMIT <= ?");
        sql.addDateValue(bef1);
        sql.addSql("          then");
        sql.addSql("            ?::integer");
        sql.addIntValue(GSConstProject.KEIKOKU_ARI);
        sql.addSql("          else");
        sql.addSql("            ?::integer");
        sql.addIntValue(GSConstProject.KEIKOKU_NASI);
        sql.addSql("          end");
        sql.addSql("        )");

        sql.addSql("      else");
        sql.addSql("        ?::integer");
        sql.addIntValue(GSConstProject.KEIKOKU_NASI);
        sql.addSql("      end");
        sql.addSql("    )");
    }

    /**
     * <br>[機  能] ResultSetからProjectItemModelを返す(TODO用)
     * <br>[解  説]
     * <br>[備  考]
     * @param rs ResultSet
     * @return created ProjectItemModel
     * @throws SQLException SQL実行例外
     */
    private ProjectItemModel __getProjectItemModelTodo(ResultSet rs) throws SQLException {
        ProjectItemModel bean = new ProjectItemModel();
        bean.setProjectSid(rs.getInt("PRJ_SID"));
        bean.setPrjMyKbn(rs.getInt("PRJ_MY_KBN"));
        bean.setProjectName(rs.getString("PRJ_NAME"));
        bean.setProjectId(rs.getString("PRJ_ID"));
        bean.setPrjMailKbn(rs.getInt("PRJ_MAIL_KBN"));
        bean.setCategory(rs.getString("CATEGORY"));
        bean.setStatusName(rs.getString("PTS_NAME"));
        bean.setRate(rs.getInt("PTS_RATE"));
        bean.setPrjBinSid(rs.getLong("BIN_SID"));
        bean.setTodoSid(rs.getInt("PTD_SID"));
        bean.setKanriNo(rs.getInt("PTD_NO"));
        bean.setTodoTitle(StringUtilHtml.transToHTmlWithWbr(rs.getString("PTD_TITLE"), 15));
        bean.setJuyo(rs.getInt("PTD_IMPORTANCE"));
        bean.setStartDate(UDate.getInstanceTimestamp(rs.getTimestamp("PTD_DATE_PLAN")));
        bean.setEndDate(UDate.getInstanceTimestamp(rs.getTimestamp("PRJ_DATE_LIMIT")));
        bean.setStartJissekiDate(UDate.getInstanceTimestamp(rs.getTimestamp("PTD_DATE_START")));
        bean.setEndJissekiDate(UDate.getInstanceTimestamp(rs.getTimestamp("PTD_DATE_END")));
        bean.setYoteiKosu(rs.getBigDecimal("PTD_PLAN_KOSU"));
        bean.setJissekiKosu(rs.getBigDecimal("PTD_RESULTS_KOSU"));
        bean.setCategoryValue(rs.getInt("PTD_CATEGORY"));
        bean.setAddUserSid(rs.getInt("PTD_AUID"));
        bean.setNaiyou(rs.getString("PTD_CONTENT"));
        bean.setKeikoku(rs.getInt("KEIKOKU"));
        bean.setPrjTodoCommentCnt(rs.getInt("COMMENT_CNT"));
        return bean;
    }

    /**
     * <br>[機  能] ResultSetからProjectItemModelを返す(プロジェクト用)
     * <br>[解  説]
     * <br>[備  考]
     * @param rs ResultSet
     * @return created ProjectItemModel
     * @throws SQLException SQL実行例外
     */
    private ProjectItemModel __getProjectItemModel(ResultSet rs) throws SQLException {
        ProjectItemModel bean = new ProjectItemModel();
        bean.setProjectSid(rs.getInt("PRJ_SID"));
        bean.setPrjMyKbn(rs.getInt("PRJ_MY_KBN"));
        bean.setProjectId(rs.getString("PRJ_ID"));
        bean.setProjectName(rs.getString("PRJ_NAME"));
        bean.setProjectRyakuName(rs.getString("PRJ_NAME_SHORT"));
        bean.setYosan(rs.getLong("PRJ_YOSAN"));
        if (bean.getYosan() > -1) {
            bean.setStrYosan(StringUtil.toCommaFormat(String.valueOf(bean.getYosan())));
        }
        bean.setKoukaiKbn(rs.getInt("PRJ_KOUKAI_KBN"));
        bean.setStartDate(UDate.getInstanceTimestamp(rs.getTimestamp("PRJ_DATE_START")));
        bean.setEndDate(UDate.getInstanceTimestamp(rs.getTimestamp("PRJ_DATE_END")));
        bean.setMokuhyou(rs.getString("PRJ_TARGET"));
        bean.setNaiyou(rs.getString("PRJ_CONTENT"));
        bean.setStatusName(rs.getString("PRS_NAME"));
        bean.setRate(rs.getInt("PRS_RATE"));
        bean.setPrjBinSid(rs.getLong("BIN_SID"));
        return bean;
    }
}