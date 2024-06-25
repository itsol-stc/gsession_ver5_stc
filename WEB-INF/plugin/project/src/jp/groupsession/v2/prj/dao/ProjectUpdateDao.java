package jp.groupsession.v2.prj.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.PrjUsedDataBiz;
import jp.groupsession.v2.prj.model.PrjMembersModel;
import jp.groupsession.v2.prj.model.PrjMembersTmpModel;
import jp.groupsession.v2.prj.model.PrjPrjdataModel;
import jp.groupsession.v2.prj.model.PrjPrjdataTmpModel;
import jp.groupsession.v2.prj.model.PrjPrjstatusModel;
import jp.groupsession.v2.prj.model.PrjPrjstatusTmpModel;
import jp.groupsession.v2.prj.model.PrjStatusHistoryModel;
import jp.groupsession.v2.prj.model.PrjTodocategoryModel;
import jp.groupsession.v2.prj.model.PrjTodocategoryTmpModel;
import jp.groupsession.v2.prj.model.PrjTododataModel;
import jp.groupsession.v2.prj.model.PrjTodomemberModel;
import jp.groupsession.v2.prj.model.PrjTodostatusModel;
import jp.groupsession.v2.prj.model.PrjTodostatusTmpModel;
import jp.groupsession.v2.prj.model.ProjectStatusModel;
import jp.groupsession.v2.prj.model.ProjectStatusTmpModel;

/**
 * <br>[機  能] プロジェクトの登録、更新を行うDAO
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ProjectUpdateDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ProjectUpdateDao.class);

    /**
     * <p>Default Constructor
     */
    public ProjectUpdateDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public ProjectUpdateDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] TODO状態を登録・更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param ppMdl PrjPrjdataModel
     * @param todoStatusList TODO状態
     * @throws SQLException SQL実行例外
     */
    public void updateTodoStatus(
        PrjPrjdataModel ppMdl,
        List<PrjTodostatusModel> todoStatusList) throws SQLException {

        if (todoStatusList == null || todoStatusList.size() < 1) {
            return;
        }

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {

            for (PrjTodostatusModel ptsMdl : todoStatusList) {
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" update");
                sql.addSql("   PRJ_TODOSTATUS");
                sql.addSql(" set ");
                sql.addSql("   PTS_NAME=?,");
                sql.addSql("   PTS_RATE=?,");
                sql.addSql("   PTS_SORT=?,");
                sql.addSql("   PTS_EUID=?,");
                sql.addSql("   PTS_EDATE=?");
                sql.addSql(" where ");
                sql.addSql("   PRJ_SID=?");
                sql.addSql(" and");
                sql.addSql("   PTS_SID=?");

                sql.addStrValue(ptsMdl.getPtsName());
                sql.addIntValue(ptsMdl.getPtsRate());
                sql.addIntValue(ptsMdl.getPtsSort());
                sql.addIntValue(ppMdl.getPrjEuid());
                sql.addDateValue(ppMdl.getPrjEdate());
                //where
                sql.addIntValue(ppMdl.getPrjSid());
                sql.addIntValue(ptsMdl.getPtsSid());

                log__.info(sql.toLogString());

                pstmt = con.prepareStatement(sql.toSqlString());
                sql.setParameter(pstmt);
                int count = pstmt.executeUpdate();

                if (count < 1) {
                    //レコードが無い場合はinsert
                    insertTodoStatus(ppMdl, ptsMdl);
                }
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] TODO状態_テンプレートを登録・更新する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param ppMdl PrjPrjdataTmpModel
     * @param todoStatusList TODO状態
     * @throws SQLException SQL実行例外
     */
    public void updateTodoStatusTmp(PrjPrjdataTmpModel ppMdl,
                                         List<PrjTodostatusTmpModel> todoStatusList)
        throws SQLException {

        if (todoStatusList == null || todoStatusList.size() < 1) {
            return;
        }

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {

            for (PrjTodostatusTmpModel ptsMdl : todoStatusList) {
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" update");
                sql.addSql("   PRJ_TODOSTATUS_TMP");
                sql.addSql(" set ");
                sql.addSql("   PST_NAME=?,");
                sql.addSql("   PST_RATE=?,");
                sql.addSql("   PST_SORT=?,");
                sql.addSql("   PST_EUID=?,");
                sql.addSql("   PST_EDATE=?");
                sql.addSql(" where ");
                sql.addSql("   PRT_SID=?");
                sql.addSql(" and");
                sql.addSql("   PST_SID=?");

                sql.addStrValue(ptsMdl.getPstName());
                sql.addIntValue(ptsMdl.getPstRate());
                sql.addIntValue(ptsMdl.getPstSort());
                sql.addIntValue(ppMdl.getPrtEuid());
                sql.addDateValue(ppMdl.getPrtEdate());
                //where
                sql.addIntValue(ppMdl.getPrtSid());
                sql.addIntValue(ptsMdl.getPstSid());

                log__.info(sql.toLogString());

                pstmt = con.prepareStatement(sql.toSqlString());
                sql.setParameter(pstmt);
                int count = pstmt.executeUpdate();

                if (count < 1) {
                    //レコードが無い場合はinsert
                    insertTodoStatusTmp(ppMdl, ptsMdl);
                }
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] 不要になったTODO状態を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param prjSid プロジェクトSID
     * @param todoStatusList TODO状態
     * @throws SQLException SQL実行例外
     */
    public void deleteTodoStatus(int prjSid, List<PrjTodostatusModel> todoStatusList)
    throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   PRJ_TODOSTATUS");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");
            sql.addIntValue(prjSid);

            if (todoStatusList != null && todoStatusList.size() > 0) {
                sql.addSql(" and ");
                sql.addSql("    PTS_SID not in (");

                int index = 0;
                for (PrjTodostatusModel ptsMdl : todoStatusList) {
                    sql.addSql("     ? ");
                    sql.addIntValue(ptsMdl.getPtsSid());

                    if (index < todoStatusList.size() - 1) {
                        sql.addSql("     , ");
                    }
                    index++;
                }
                sql.addSql("   )");
            }

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] 不要になったTODO状態_テンプレートを削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param prtSid プロジェクトテンプレートSID
     * @param todoStatusList TODO状態
     * @throws SQLException SQL実行例外
     */
    public void deleteTodoStatusTmp(int prtSid, List<PrjTodostatusTmpModel> todoStatusList)
        throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   PRJ_TODOSTATUS_TMP");
            sql.addSql(" where ");
            sql.addSql("   PRT_SID=?");
            sql.addIntValue(prtSid);

            if (todoStatusList != null && todoStatusList.size() > 0) {
                sql.addSql(" and ");
                sql.addSql("   PST_SID not in (");

                int index = 0;
                for (PrjTodostatusTmpModel ptsMdl : todoStatusList) {
                    sql.addSql("     ? ");
                    sql.addIntValue(ptsMdl.getPstSid());

                    if (index < todoStatusList.size() - 1) {
                        sql.addSql("     , ");
                    }
                    index++;
                }
                sql.addSql("   )");
            }

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] TODO状態がマスタに存在しない場合、TODO情報のTODO状態を0%に更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param date システム日付
     * @param prjSid プロジェクトSID
     * @throws SQLException SQL実行例外
     */
    public void updateStatusNotExists(int userSid, UDate date, int prjSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   PRJ_TODODATA");
            sql.addSql(" set");
            sql.addSql("   PTS_SID = ?,");
            sql.addSql("   PTD_EUID = ?,");
            sql.addSql("   PTD_EDATE = ?");
            sql.addSql(" where");
            sql.addSql("   PRJ_SID = ?");
            sql.addSql(" and");
            sql.addSql("   not exists");
            sql.addSql("   (");
            sql.addSql("     select");
            sql.addSql("       0");
            sql.addSql("     from");
            sql.addSql("       PRJ_TODOSTATUS");
            sql.addSql("     where");
            sql.addSql("       PRJ_TODOSTATUS.PRJ_SID = PRJ_TODODATA.PRJ_SID");
            sql.addSql("     and");
            sql.addSql("       PRJ_TODOSTATUS.PTS_SID = PRJ_TODODATA.PTS_SID");
            sql.addSql("   )");

            sql.addIntValue(GSConstProject.STATUS_0);
            sql.addIntValue(userSid);
            sql.addDateValue(date);
            //where
            sql.addIntValue(prjSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] カテゴリがマスタに存在しない場合、TODO情報のカテゴリを「未選択」に更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param ppMdl PrjPrjdataModel
     * @throws SQLException SQL実行例外
     */
    public void updateCateNotExists(PrjPrjdataModel ppMdl) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   PRJ_TODODATA");
            sql.addSql(" set ");
            sql.addSql("   PTD_CATEGORY=?,");
            sql.addSql("   PTD_EUID=?,");
            sql.addSql("   PTD_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");
            sql.addSql(" and");
            sql.addSql("   not exists");
            sql.addSql("   (");
            sql.addSql("     select");
            sql.addSql("       0");
            sql.addSql("     from");
            sql.addSql("       PRJ_TODOCATEGORY");
            sql.addSql("     where");
            sql.addSql("       PRJ_TODOCATEGORY.PRJ_SID = PRJ_TODODATA.PRJ_SID");
            sql.addSql("     and");
            sql.addSql("       PRJ_TODOCATEGORY.PTC_CATEGORY_SID = PRJ_TODODATA.PTD_CATEGORY");
            sql.addSql("   )");

            sql.addIntValue(GSConstCommon.NUM_INIT);
            sql.addIntValue(ppMdl.getPrjEuid());
            sql.addDateValue(ppMdl.getPrjEdate());
            //where
            sql.addIntValue(ppMdl.getPrjSid());

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] TODO状態がマスタに存在しない場合、TODO変更履歴のTODO状態を0%に更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param date システム日付
     * @param prjSid プロジェクトSID
     * @throws SQLException SQL実行例外
     */
    public void updateTodoHisStatusNotExists(int userSid, UDate date, int prjSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   PRJ_STATUS_HISTORY");
            sql.addSql(" set");
            sql.addSql("   PTS_SID = ?,");
            sql.addSql("   PSH_EUID = ?,");
            sql.addSql("   PSH_EDATE = ?");
            sql.addSql(" where");
            sql.addSql("   PRJ_SID = ?");
            sql.addSql(" and");
            sql.addSql("   not exists");
            sql.addSql("   (");
            sql.addSql("     select");
            sql.addSql("       0");
            sql.addSql("     from");
            sql.addSql("       PRJ_TODOSTATUS");
            sql.addSql("     where");
            sql.addSql("       PRJ_TODOSTATUS.PRJ_SID = PRJ_STATUS_HISTORY.PRJ_SID");
            sql.addSql("     and");
            sql.addSql("       PRJ_TODOSTATUS.PTS_SID = PRJ_STATUS_HISTORY.PTS_SID");
            sql.addSql("   )");

            sql.addIntValue(GSConstProject.STATUS_0);
            sql.addIntValue(userSid);
            sql.addDateValue(date);
            //where
            sql.addIntValue(prjSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] TODO情報のTODO状態を更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param updateStatus TODO状態更新SID
     * @param ppMdl PrjPrjdataModel
     * @throws SQLException SQL実行例外
     */
    public void updateStatus(HashMap<String, String> updateStatus, PrjPrjdataModel ppMdl)
    throws SQLException {

        if (updateStatus == null || updateStatus.size() < 1) {
            return;
        }

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();
        Iterator<Entry<String, String>> itr = (updateStatus.entrySet()).iterator();

        try {

            while (itr.hasNext()) {
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" update");
                sql.addSql("   PRJ_TODODATA");
                sql.addSql(" set ");
                sql.addSql("   PTS_SID=?,");
                sql.addSql("   PTD_EUID=?,");
                sql.addSql("   PTD_EDATE=?");
                sql.addSql(" where ");
                sql.addSql("   PRJ_SID=?");
                sql.addSql(" and");
                sql.addSql("   PTS_SID=?");

                Entry<String, String> map = (Entry<String, String>) itr.next();
                sql.addIntValue(NullDefault.getInt((String) map.getValue(), -1));
                sql.addIntValue(ppMdl.getPrjEuid());
                sql.addDateValue(ppMdl.getPrjEdate());
                //where
                sql.addIntValue(ppMdl.getPrjSid());
                sql.addIntValue(NullDefault.getInt((String) map.getKey(), -1));

                log__.info(sql.toLogString());

                pstmt = con.prepareStatement(sql.toSqlString());
                sql.setParameter(pstmt);
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] TODO変更履歴のTODO状態を更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param updateStatus TODO状態更新SID
     * @param ppMdl PrjPrjdataModel
     * @throws SQLException SQL実行例外
     */
    public void updateTodoHisStatus(
        HashMap<String, String> updateStatus,
        PrjPrjdataModel ppMdl) throws SQLException {

        if (updateStatus == null || updateStatus.size() < 1) {
            return;
        }

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();
        Iterator<Entry<String, String>> itr = (updateStatus.entrySet()).iterator();

        try {

            while (itr.hasNext()) {
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" update");
                sql.addSql("   PRJ_STATUS_HISTORY");
                sql.addSql(" set ");
                sql.addSql("   PTS_SID=?,");
                sql.addSql("   PSH_EUID=?,");
                sql.addSql("   PSH_EDATE=?");
                sql.addSql(" where ");
                sql.addSql("   PRJ_SID=?");
                sql.addSql(" and");
                sql.addSql("   PTS_SID=?");

                Entry<String, String> map = (Entry<String, String>) itr.next();
                sql.addIntValue(NullDefault.getInt((String) map.getValue(), -1));
                sql.addIntValue(ppMdl.getPrjEuid());
                sql.addDateValue(ppMdl.getPrjEdate());
                //where
                sql.addIntValue(ppMdl.getPrjSid());
                sql.addIntValue(NullDefault.getInt((String) map.getKey(), -1));

                log__.info(sql.toLogString());

                pstmt = con.prepareStatement(sql.toSqlString());
                sql.setParameter(pstmt);
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] TODOカテゴリを登録・更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param ppMdl PrjPrjdataModel
     * @param todoCateList TODOカテゴリ
     * @throws SQLException SQL実行例外
     */
    public void updateTodoCate(
        PrjPrjdataModel ppMdl,
        List<PrjTodocategoryModel> todoCateList) throws SQLException {

        if (todoCateList == null || todoCateList.size() < 1) {
            return;
        }

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {

            for (PrjTodocategoryModel ptcMdl : todoCateList) {
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" update");
                sql.addSql("   PRJ_TODOCATEGORY");
                sql.addSql(" set ");
                sql.addSql("   PTC_SORT=?,");
                sql.addSql("   PTC_NAME=?,");
                sql.addSql("   PTC_EUID=?,");
                sql.addSql("   PTC_EDATE=?");
                sql.addSql(" where ");
                sql.addSql("   PRJ_SID=?");
                sql.addSql(" and");
                sql.addSql("   PTC_CATEGORY_SID=?");

                sql.addIntValue(ptcMdl.getPtcSort());
                sql.addStrValue(ptcMdl.getPtcName());
                sql.addIntValue(ppMdl.getPrjEuid());
                sql.addDateValue(ppMdl.getPrjEdate());
                //where
                sql.addIntValue(ppMdl.getPrjSid());
                sql.addIntValue(ptcMdl.getPtcCategorySid());

                log__.info(sql.toLogString());

                pstmt = con.prepareStatement(sql.toSqlString());
                sql.setParameter(pstmt);
                int count = pstmt.executeUpdate();

                if (count < 1) {
                    //レコードが無い場合はinsert
                    insertTodoCate(ppMdl, ptcMdl);
                }
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] TODOカテゴリ_テンプレートを登録・更新する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param ppMdl PrjPrjdataTmpModel
     * @param todoCateList TODOカテゴリ
     * @throws SQLException SQL実行例外
     */
    public void updateTodoCateTmp(PrjPrjdataTmpModel ppMdl,
                                      List<PrjTodocategoryTmpModel> todoCateList)
        throws SQLException {

        if (todoCateList == null || todoCateList.size() < 1) {
            return;
        }

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {

            for (PrjTodocategoryTmpModel ptcMdl : todoCateList) {
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" update");
                sql.addSql("   PRJ_TODOCATEGORY_TMP");
                sql.addSql(" set ");
                sql.addSql("   PCT_SORT=?,");
                sql.addSql("   PCT_NAME=?,");
                sql.addSql("   PCT_EUID=?,");
                sql.addSql("   PCT_EDATE=?");
                sql.addSql(" where ");
                sql.addSql("   PRT_SID=?");
                sql.addSql(" and");
                sql.addSql("   PCT_CATEGORY_SID=?");

                sql.addIntValue(ptcMdl.getPctSort());
                sql.addStrValue(ptcMdl.getPctName());
                sql.addIntValue(ppMdl.getPrtEuid());
                sql.addDateValue(ppMdl.getPrtEdate());
                //where
                sql.addIntValue(ppMdl.getPrtSid());
                sql.addIntValue(ptcMdl.getPctCategorySid());

                log__.info(sql.toLogString());

                pstmt = con.prepareStatement(sql.toSqlString());
                sql.setParameter(pstmt);
                int count = pstmt.executeUpdate();

                if (count < 1) {
                    //レコードが無い場合はinsert
                    insertTodoCateTmp(ppMdl, ptcMdl);
                }
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] 不要になったTODOカテゴリを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param prjSid プロジェクトSID
     * @param todoCateList TODOカテゴリ
     * @throws SQLException SQL実行例外
     */
    public void deleteProjectCate(int prjSid, List<PrjTodocategoryModel> todoCateList)
    throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   PRJ_TODOCATEGORY");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");
            sql.addIntValue(prjSid);

            if (todoCateList != null && todoCateList.size() > 0) {
                sql.addSql(" and ");
                sql.addSql("    PTC_CATEGORY_SID not in (");

                int index = 0;
                for (PrjTodocategoryModel ptcMdl : todoCateList) {
                    sql.addSql("     ? ");
                    sql.addIntValue(ptcMdl.getPtcCategorySid());

                    if (index < todoCateList.size() - 1) {
                        sql.addSql("     , ");
                    }
                    index++;
                }
                sql.addSql("   )");
            }

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] 不要になったTODOカテゴリ_テンプレートを削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param prtSid プロジェクトテンプレートSID
     * @param todoCateList TODOカテゴリ
     * @throws SQLException SQL実行例外
     */
    public void deleteProjectCateTmp(int prtSid, List<PrjTodocategoryTmpModel> todoCateList)
    throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   PRJ_TODOCATEGORY_TMP");
            sql.addSql(" where ");
            sql.addSql("   PRT_SID=?");
            sql.addIntValue(prtSid);

            if (todoCateList != null && todoCateList.size() > 0) {
                sql.addSql(" and ");
                sql.addSql("   PCT_CATEGORY_SID not in (");

                int index = 0;
                for (PrjTodocategoryTmpModel ptcMdl : todoCateList) {
                    sql.addSql("     ? ");
                    sql.addIntValue(ptcMdl.getPctCategorySid());

                    if (index < todoCateList.size() - 1) {
                        sql.addSql("     , ");
                    }
                    index++;
                }
                sql.addSql("   )");
            }

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] TODO情報のカテゴリを更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param updateCate TODOカテゴリ更新SID
     * @param ppMdl PrjPrjdataModel
     * @throws SQLException SQL実行例外
     */
    public void updateCate(HashMap<String, String> updateCate, PrjPrjdataModel ppMdl)
    throws SQLException {

        if (updateCate == null || updateCate.size() < 1) {
            return;
        }

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();
        Iterator<Entry<String, String>> itr = (updateCate.entrySet()).iterator();

        try {

            while (itr.hasNext()) {
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" update");
                sql.addSql("   PRJ_TODODATA");
                sql.addSql(" set ");
                sql.addSql("   PTD_CATEGORY=?,");
                sql.addSql("   PTD_EUID=?,");
                sql.addSql("   PTD_EDATE=?");
                sql.addSql(" where ");
                sql.addSql("   PRJ_SID=?");
                sql.addSql(" and");
                sql.addSql("   PTD_CATEGORY=?");

                Entry<String, String> map = (Entry<String, String>) itr.next();
                sql.addIntValue(NullDefault.getInt((String) map.getValue(), -1));
                sql.addIntValue(ppMdl.getPrjEuid());
                sql.addDateValue(ppMdl.getPrjEdate());
                //where
                sql.addIntValue(ppMdl.getPrjSid());
                sql.addIntValue(NullDefault.getInt((String) map.getKey(), -1));

                log__.info(sql.toLogString());

                pstmt = con.prepareStatement(sql.toSqlString());
                sql.setParameter(pstmt);
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] プロジェクトメンバーを登録・更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param memberList メンバーリスト
     * @throws SQLException SQL実行例外
     */
    public void updateProjectMember(List<PrjMembersModel> memberList) throws SQLException {

        if (memberList == null || memberList.size() < 1) {
            return;
        }

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {

            for (PrjMembersModel member : memberList) {
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" update");
                sql.addSql("   PRJ_MEMBERS");
                sql.addSql(" set ");
                sql.addSql("   PRM_ADMIN_KBN=?,");
                sql.addSql("   PRM_EUID=?,");
                sql.addSql("   PRM_EDATE=?,");
                sql.addSql("   PRM_MEM_KEY=?");
                sql.addSql(" where ");
                sql.addSql("   PRJ_SID=?");
                sql.addSql(" and");
                sql.addSql("   USR_SID=?");
                sql.addSql(" and");
                sql.addSql("   PRM_EMPLOYEE_KBN=?");

                sql.addIntValue(member.getPrmAdminKbn());
                sql.addIntValue(member.getPrmEuid());
                sql.addDateValue(member.getPrmEdate());
                sql.addStrValue(member.getPrmMemKey());
                //where
                sql.addIntValue(member.getPrjSid());
                sql.addIntValue(member.getUsrSid());
                sql.addIntValue(member.getPrmEmployeeKbn());

                log__.info(sql.toLogString());

                pstmt = con.prepareStatement(sql.toSqlString());
                sql.setParameter(pstmt);
                int count = pstmt.executeUpdate();

                if (count < 1) {
                    //レコードが無い場合はinsert
                    insertProjectMember(member);
                }
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] プロジェクトテンプレートメンバーを登録・更新する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param memberList メンバーリスト
     * @throws SQLException SQL実行例外
     */
    public void updateProjectMemberTmp(List<PrjMembersTmpModel> memberList)
        throws SQLException {

        if (memberList == null || memberList.size() < 1) {
            return;
        }

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {

            for (PrjMembersTmpModel member : memberList) {
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" update");
                sql.addSql("   PRJ_MEMBERS_TMP");
                sql.addSql(" set ");
                sql.addSql("   PMT_ADMIN_KBN=?,");
                sql.addSql("   PMT_EUID=?,");
                sql.addSql("   PMT_EDATE=?,");
                sql.addSql("   PMT_MEM_KEY=?");
                sql.addSql(" where ");
                sql.addSql("   PRT_SID=?");
                sql.addSql(" and");
                sql.addSql("   USR_SID=?");
                sql.addSql(" and");
                sql.addSql("   PMT_EMPLOYEE_KBN=?");

                sql.addIntValue(member.getPmtAdminKbn());
                sql.addIntValue(member.getPmtEuid());
                sql.addDateValue(member.getPmtEdate());
                sql.addStrValue(member.getPmtMemKey());
                //where
                sql.addIntValue(member.getPrtSid());
                sql.addIntValue(member.getUsrSid());
                sql.addIntValue(member.getPmtEmployeeKbn());

                log__.info(sql.toLogString());

                pstmt = con.prepareStatement(sql.toSqlString());
                sql.setParameter(pstmt);
                int count = pstmt.executeUpdate();

                if (count < 1) {
                    //レコードが無い場合はinsert
                    insertProjectTmpMember(member);
                }
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] 不要になったプロジェクトメンバーを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param prjSid プロジェクトSID
     * @param memberList プロジェクトメンバー
     * @throws SQLException SQL実行例外
     */
    public void deleteProjectMember(int prjSid, List<PrjMembersModel> memberList)
    throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   PRJ_MEMBERS");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");
            sql.addIntValue(prjSid);

            if (memberList != null && memberList.size() > 0) {
                sql.addSql(" and ");
                sql.addSql("    USR_SID not in (");

                int index = 0;
                for (PrjMembersModel pmMdl : memberList) {
                    sql.addSql("     ? ");
                    sql.addIntValue(pmMdl.getUsrSid());

                    if (index < memberList.size() - 1) {
                        sql.addSql("     , ");
                    }
                    index++;
                }
                sql.addSql("   )");
            }

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] 不要になったプロジェクトテンプレートメンバーを削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param prtSid プロジェクトテンプレートSID
     * @param memberList プロジェクトメンバー
     * @throws SQLException SQL実行例外
     */
    public void deleteProjectMemberTmp(int prtSid, List<PrjMembersTmpModel> memberList)
    throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   PRJ_MEMBERS_TMP");
            sql.addSql(" where ");
            sql.addSql("   PRT_SID=?");
            sql.addIntValue(prtSid);

            if (memberList != null && memberList.size() > 0) {
                sql.addSql(" and ");
                sql.addSql("   USR_SID not in (");

                int index = 0;
                for (PrjMembersTmpModel pmMdl : memberList) {
                    sql.addSql("     ? ");
                    sql.addIntValue(pmMdl.getUsrSid());

                    if (index < memberList.size() - 1) {
                        sql.addSql("     , ");
                    }
                    index++;
                }
                sql.addSql("   )");
            }

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] プロジェクト状態を登録・更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param ppMdl PrjPrjdataModel
     * @param prjStatusList プロジェクト状態
     * @throws SQLException SQL実行例外
     */
    public void updateProjectStatus(
        PrjPrjdataModel ppMdl,
        List<PrjPrjstatusModel> prjStatusList) throws SQLException {

        if (prjStatusList == null || prjStatusList.size() < 1) {
            return;
        }

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {

            for (PrjPrjstatusModel ppsMdl : prjStatusList) {
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" update");
                sql.addSql("   PRJ_PRJSTATUS");
                sql.addSql(" set ");
                sql.addSql("   PRS_SORT=?,");
                sql.addSql("   PRS_NAME=?,");
                sql.addSql("   PRS_RATE=?,");
                sql.addSql("   PRS_EUID=?,");
                sql.addSql("   PRS_EDATE=?");
                sql.addSql(" where ");
                sql.addSql("   PRJ_SID=?");
                sql.addSql(" and");
                sql.addSql("   PRS_SID=?");

                sql.addIntValue(ppsMdl.getPrsSort());
                sql.addStrValue(ppsMdl.getPrsName());
                sql.addIntValue(ppsMdl.getPrsRate());
                sql.addIntValue(ppMdl.getPrjEuid());
                sql.addDateValue(ppMdl.getPrjEdate());
                //where
                sql.addIntValue(ppMdl.getPrjSid());
                sql.addIntValue(ppsMdl.getPrsSid());

                log__.info(sql.toLogString());

                pstmt = con.prepareStatement(sql.toSqlString());
                sql.setParameter(pstmt);
                int count = pstmt.executeUpdate();

                if (count < 1) {
                    //レコードが無い場合はinsert
                    insertProjectStatus(ppMdl, ppsMdl);
                }
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] プロジェクトテンプレート状態を登録・更新する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param ppMdl PrjPrjdataTmpModel
     * @param prjStatusList プロジェクト状態
     * @throws SQLException SQL実行例外
     */
    public void updateProjectStatusTmp(PrjPrjdataTmpModel ppMdl,
                                           List<PrjPrjstatusTmpModel> prjStatusList)
        throws SQLException {

        if (prjStatusList == null || prjStatusList.size() < 1) {
            return;
        }

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {

            for (PrjPrjstatusTmpModel ppsMdl : prjStatusList) {
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" update");
                sql.addSql("   PRJ_PRJSTATUS_TMP");
                sql.addSql(" set ");
                sql.addSql("   PTT_SORT=?,");
                sql.addSql("   PTT_NAME=?,");
                sql.addSql("   PTT_RATE=?,");
                sql.addSql("   PTT_EUID=?,");
                sql.addSql("   PTT_EDATE=?");
                sql.addSql(" where ");
                sql.addSql("   PRT_SID=?");
                sql.addSql(" and");
                sql.addSql("   PTT_SID=?");

                sql.addIntValue(ppsMdl.getPttSort());
                sql.addStrValue(ppsMdl.getPttName());
                sql.addIntValue(ppsMdl.getPttRate());
                sql.addIntValue(ppMdl.getPrtEuid());
                sql.addDateValue(ppMdl.getPrtEdate());
                //where
                sql.addIntValue(ppMdl.getPrtSid());
                sql.addIntValue(ppsMdl.getPttSid());

                log__.info(sql.toLogString());

                pstmt = con.prepareStatement(sql.toSqlString());
                sql.setParameter(pstmt);
                int count = pstmt.executeUpdate();

                if (count < 1) {
                    //レコードが無い場合はinsert
                    insertProjectTmpStatus(ppMdl, ppsMdl);
                }
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] 不要になったプロジェクト状態を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param prjSid プロジェクトSID
     * @param prjStatusList プロジェクト状態
     * @throws SQLException SQL実行例外
     */
    public void deleteStatus(int prjSid, List<PrjPrjstatusModel> prjStatusList)
    throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   PRJ_PRJSTATUS");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");
            sql.addIntValue(prjSid);

            if (prjStatusList != null && prjStatusList.size() > 0) {
                sql.addSql(" and ");
                sql.addSql("    PRS_SID not in (");

                int index = 0;
                for (PrjPrjstatusModel ppsMdl : prjStatusList) {
                    sql.addSql("     ? ");
                    sql.addIntValue(ppsMdl.getPrsSid());

                    if (index < prjStatusList.size() - 1) {
                        sql.addSql("     , ");
                    }
                    index++;
                }
                sql.addSql("   )");
            }

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] 不要になったプロジェクトテンプレート状態を削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param prtSid プロジェクトテンプレートSID
     * @param prjStatusList プロジェクト状態
     * @throws SQLException SQL実行例外
     */
    public void deleteStatusTmp(int prtSid, List<PrjPrjstatusTmpModel> prjStatusList)
    throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   PRJ_PRJSTATUS_TMP");
            sql.addSql(" where ");
            sql.addSql("   PRT_SID=?");
            sql.addIntValue(prtSid);

            if (prjStatusList != null && prjStatusList.size() > 0) {
                sql.addSql(" and ");
                sql.addSql("   PTT_SID not in (");

                int index = 0;
                for (PrjPrjstatusTmpModel ppsMdl : prjStatusList) {
                    sql.addSql("     ? ");
                    sql.addIntValue(ppsMdl.getPttSid());

                    if (index < prjStatusList.size() - 1) {
                        sql.addSql("     , ");
                    }
                    index++;
                }
                sql.addSql("   )");
            }

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] プロジェクト情報を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param ppMdl PrjPrjdataModel
     * @param prjStatus プロジェクト状態
     * @param memberList メンバーリスト
     * @throws SQLException SQL実行例外
     */
    public void insertProject(
        PrjPrjdataModel ppMdl,
        ProjectStatusModel prjStatus,
        List<PrjMembersModel> memberList) throws SQLException {

        List<PrjPrjstatusModel> prjStatusList = prjStatus.getPrjStatusList();
        List<PrjTodocategoryModel> todoCateList = prjStatus.getTodoCateList();
        List<PrjTodostatusModel> todoStatusList = prjStatus.getTodoStatusList();

        //プロジェクト状態を登録する
        insertProjectStatus(ppMdl, prjStatusList);

        //プロジェクト情報を登録
        PrjPrjdataDao ppdDao = new PrjPrjdataDao(getCon());
        ppdDao.insert(ppMdl);

        //プロジェクトメンバー情報を登録する
        insertProjectMember(memberList);

        //TODOカテゴリ情報を登録する
        insertTodoCate(ppMdl, todoCateList);

        //TODO状態を登録する
        insertTodoStatus(ppMdl, todoStatusList);
    }

    /**
     * <br>[機  能] プロジェクトテンプレート情報を登録する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param ppMdl PrjPrjdataModel
     * @param prjStatus プロジェクト状態
     * @param memberList メンバーリスト
     * @throws SQLException SQL実行例外
     */
    public void insertProjectTmp(PrjPrjdataTmpModel ppMdl,
                                  ProjectStatusTmpModel prjStatus,
                                  List<PrjMembersTmpModel> memberList)
        throws SQLException {

        List<PrjPrjstatusTmpModel> prjStatusList = prjStatus.getPrjStatusList();
        List<PrjTodocategoryTmpModel> todoCateList = prjStatus.getTodoCateList();
        List<PrjTodostatusTmpModel> todoStatusList = prjStatus.getTodoStatusList();

        //プロジェクトテンプレート情報を登録
        PrjPrjdataTmpDao ppdDao = new PrjPrjdataTmpDao(getCon());
        ppdDao.insert(ppMdl);

        //プロジェクトテンプレート状態を登録する
        insertProjectTmpStatus(ppMdl, prjStatusList);

        //プロジェクトテンプレートメンバー情報を登録する
        insertProjectTmpMember(memberList);

        //TODOカテゴリ_テンプレート情報を登録する
        insertTodoCateTmp(ppMdl, todoCateList);

        //TODO状態_テンプレートを登録する
        insertTodoStatusTmp(ppMdl, todoStatusList);
    }

    /**
     * <br>[機  能] TODO状態を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param ppMdl PrjPrjdataModel
     * @param todoStatusList TODO状態
     * @throws SQLException SQL実行例外
     */
    public void insertTodoStatus(
        PrjPrjdataModel ppMdl,
        List<PrjTodostatusModel> todoStatusList) throws SQLException {

        if (todoStatusList == null || todoStatusList.size() < 1) {
            return;
        }

        for (PrjTodostatusModel ptsMdl : todoStatusList) {
            insertTodoStatus(ppMdl, ptsMdl);
        }
    }

    /**
     * <br>[機  能] TODO状態_テンプレートを登録する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param ppMdl PrjPrjdataTmpModel
     * @param todoStatusList TODO状態
     * @throws SQLException SQL実行例外
     */
    public void insertTodoStatusTmp(PrjPrjdataTmpModel ppMdl,
                                         List<PrjTodostatusTmpModel> todoStatusList)
        throws SQLException {

        if (todoStatusList == null || todoStatusList.size() < 1) {
            return;
        }

        for (PrjTodostatusTmpModel ptsMdl : todoStatusList) {
            insertTodoStatusTmp(ppMdl, ptsMdl);
        }
    }

    /**
     * <br>[機  能] TODO状態を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param ppMdl PrjPrjdataModel
     * @param ptsMdl PrjTodostatusModel
     * @throws SQLException SQL実行例外
     */
    public void insertTodoStatus(PrjPrjdataModel ppMdl, PrjTodostatusModel ptsMdl)
    throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" PRJ_TODOSTATUS(");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   PTS_SID,");
            sql.addSql("   PTS_NAME,");
            sql.addSql("   PTS_RATE,");
            sql.addSql("   PTS_SORT,");
            sql.addSql("   PTS_AUID,");
            sql.addSql("   PTS_ADATE,");
            sql.addSql("   PTS_EUID,");
            sql.addSql("   PTS_EDATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            sql.addIntValue(ppMdl.getPrjSid());
            sql.addIntValue(ptsMdl.getPtsSid());
            sql.addStrValue(ptsMdl.getPtsName());
            sql.addIntValue(ptsMdl.getPtsRate());
            sql.addIntValue(ptsMdl.getPtsSort());
            sql.addIntValue(ppMdl.getPrjAuid());
            sql.addDateValue(ppMdl.getPrjAdate());
            sql.addIntValue(ppMdl.getPrjEuid());
            sql.addDateValue(ppMdl.getPrjEdate());

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] TODO状態_テンプレートを登録する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param ppMdl PrjPrjdataTmpModel
     * @param ptsMdl PrjTodostatusTmpModel
     * @throws SQLException SQL実行例外
     */
    public void insertTodoStatusTmp(PrjPrjdataTmpModel ppMdl, PrjTodostatusTmpModel ptsMdl)
        throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" PRJ_TODOSTATUS_TMP (");
            sql.addSql("   PRT_SID,");
            sql.addSql("   PST_SID,");
            sql.addSql("   PST_NAME,");
            sql.addSql("   PST_RATE,");
            sql.addSql("   PST_SORT,");
            sql.addSql("   PST_AUID,");
            sql.addSql("   PST_ADATE,");
            sql.addSql("   PST_EUID,");
            sql.addSql("   PST_EDATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            sql.addIntValue(ppMdl.getPrtSid());
            sql.addIntValue(ptsMdl.getPstSid());
            sql.addStrValue(ptsMdl.getPstName());
            sql.addIntValue(ptsMdl.getPstRate());
            sql.addIntValue(ptsMdl.getPstSort());
            sql.addIntValue(ppMdl.getPrtAuid());
            sql.addDateValue(ppMdl.getPrtAdate());
            sql.addIntValue(ppMdl.getPrtEuid());
            sql.addDateValue(ppMdl.getPrtEdate());

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] TODOカテゴリ情報を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param ppMdl PrjPrjdataModel
     * @param todoCateList TODOカテゴリ情報
     * @throws SQLException SQL実行例外
     */
    public void insertTodoCate(
        PrjPrjdataModel ppMdl,
        List<PrjTodocategoryModel> todoCateList) throws SQLException {

        if (todoCateList == null || todoCateList.size() < 1) {
            return;
        }

        for (PrjTodocategoryModel ptcMdl : todoCateList) {
            insertTodoCate(ppMdl, ptcMdl);
        }
    }

    /**
     * <br>[機  能] TODOカテゴリ_テンプレート情報を登録する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param ppMdl PrjPrjdataTmpModel
     * @param todoCateList TODOカテゴリ情報
     * @throws SQLException SQL実行例外
     */
    public void insertTodoCateTmp(PrjPrjdataTmpModel ppMdl,
                                      List<PrjTodocategoryTmpModel> todoCateList)
        throws SQLException {

        if (todoCateList == null || todoCateList.size() < 1) {
            return;
        }

        for (PrjTodocategoryTmpModel ptcMdl : todoCateList) {
            insertTodoCateTmp(ppMdl, ptcMdl);
        }
    }

    /**
     * <br>[機  能] TODOカテゴリ情報を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param ppMdl PrjPrjdataModel
     * @param ptcMdl PrjTodocategoryModel
     * @throws SQLException SQL実行例外
     */
    public void insertTodoCate(PrjPrjdataModel ppMdl, PrjTodocategoryModel ptcMdl)
    throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" PRJ_TODOCATEGORY(");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   PTC_CATEGORY_SID,");
            sql.addSql("   PTC_SORT,");
            sql.addSql("   PTC_NAME,");
            sql.addSql("   PTC_AUID,");
            sql.addSql("   PTC_ADATE,");
            sql.addSql("   PTC_EUID,");
            sql.addSql("   PTC_EDATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            sql.addIntValue(ppMdl.getPrjSid());
            sql.addIntValue(ptcMdl.getPtcCategorySid());
            sql.addIntValue(ptcMdl.getPtcSort());
            sql.addStrValue(ptcMdl.getPtcName());
            sql.addIntValue(ppMdl.getPrjAuid());
            sql.addDateValue(ppMdl.getPrjAdate());
            sql.addIntValue(ppMdl.getPrjEuid());
            sql.addDateValue(ppMdl.getPrjEdate());

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] TODOカテゴリ_テンプレート情報を登録する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param ppMdl PrjPrjdataTmpModel
     * @param ptcMdl PrjTodocategoryTmpModel
     * @throws SQLException SQL実行例外
     */
    public void insertTodoCateTmp(PrjPrjdataTmpModel ppMdl, PrjTodocategoryTmpModel ptcMdl)
    throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" PRJ_TODOCATEGORY_TMP (");
            sql.addSql("   PRT_SID,");
            sql.addSql("   PCT_CATEGORY_SID,");
            sql.addSql("   PCT_SORT,");
            sql.addSql("   PCT_NAME,");
            sql.addSql("   PCT_AUID,");
            sql.addSql("   PCT_ADATE,");
            sql.addSql("   PCT_EUID,");
            sql.addSql("   PCT_EDATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            sql.addIntValue(ppMdl.getPrtSid());
            sql.addIntValue(ptcMdl.getPctCategorySid());
            sql.addIntValue(ptcMdl.getPctSort());
            sql.addStrValue(ptcMdl.getPctName());
            sql.addIntValue(ppMdl.getPrtAuid());
            sql.addDateValue(ppMdl.getPrtAdate());
            sql.addIntValue(ppMdl.getPrtEuid());
            sql.addDateValue(ppMdl.getPrtEdate());

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] プロジェクトメンバー情報を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param memberList メンバーリスト
     * @throws SQLException SQL実行例外
     */
    public void insertProjectMember(List<PrjMembersModel> memberList) throws SQLException {

        if (memberList == null || memberList.size() < 1) {
            return;
        }

        for (PrjMembersModel member : memberList) {
            insertProjectMember(member);
        }
    }

    /**
     * <br>[機  能] プロジェクトテンプレートメンバー情報を登録する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param memberList メンバーリスト
     * @throws SQLException SQL実行例外
     */
    public void insertProjectTmpMember(List<PrjMembersTmpModel> memberList)
        throws SQLException {

        if (memberList == null || memberList.size() < 1) {
            return;
        }

        for (PrjMembersTmpModel member : memberList) {
            insertProjectTmpMember(member);
        }
    }

    /**
     * <br>[機  能] プロジェクトメンバー情報を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param member PrjMembersModel
     * @throws SQLException SQL実行例外
     */
    public void insertProjectMember(PrjMembersModel member) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" PRJ_MEMBERS(");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   PRM_EMPLOYEE_KBN,");
            sql.addSql("   PRM_ADMIN_KBN,");
            sql.addSql("   PRM_AUID,");
            sql.addSql("   PRM_ADATE,");
            sql.addSql("   PRM_EUID,");
            sql.addSql("   PRM_EDATE,");
            sql.addSql("   PRM_MEM_KEY,");
            sql.addSql("   PRM_SORT");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            sql.addIntValue(member.getPrjSid());
            sql.addIntValue(member.getUsrSid());
            sql.addIntValue(member.getPrmEmployeeKbn());
            sql.addIntValue(member.getPrmAdminKbn());
            sql.addIntValue(member.getPrmAuid());
            sql.addDateValue(member.getPrmAdate());
            sql.addIntValue(member.getPrmEuid());
            sql.addDateValue(member.getPrmEdate());
            sql.addStrValue(member.getPrmMemKey());
            sql.addIntValue(member.getPrmSort());

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] プロジェクトテンプレートメンバー情報を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param member PrjMembersTmpModel
     * @throws SQLException SQL実行例外
     */
    public void insertProjectTmpMember(PrjMembersTmpModel member) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" PRJ_MEMBERS_TMP (");
            sql.addSql("   PRT_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   PMT_EMPLOYEE_KBN,");
            sql.addSql("   PMT_ADMIN_KBN,");
            sql.addSql("   PMT_AUID,");
            sql.addSql("   PMT_ADATE,");
            sql.addSql("   PMT_EUID,");
            sql.addSql("   PMT_EDATE,");
            sql.addSql("   PMT_MEM_KEY");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            sql.addIntValue(member.getPrtSid());
            sql.addIntValue(member.getUsrSid());
            sql.addIntValue(member.getPmtEmployeeKbn());
            sql.addIntValue(member.getPmtAdminKbn());
            sql.addIntValue(member.getPmtAuid());
            sql.addDateValue(member.getPmtAdate());
            sql.addIntValue(member.getPmtEuid());
            sql.addDateValue(member.getPmtEdate());
            sql.addStrValue(member.getPmtMemKey());

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] プロジェクト状態を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param ppMdl PrjPrjdataModel
     * @param prjStatusList プロジェクト状態
     * @throws SQLException SQL実行例外
     */
    public void insertProjectStatus(
        PrjPrjdataModel ppMdl,
        List<PrjPrjstatusModel> prjStatusList) throws SQLException {

        if (prjStatusList == null || prjStatusList.size() < 1) {
            return;
        }

        for (PrjPrjstatusModel ppsMdl : prjStatusList) {
            insertProjectStatus(ppMdl, ppsMdl);
        }
    }

    /**
     * <br>[機  能] プロジェクトテンプレート状態を登録する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param ppMdl PrjPrjdataTmpModel
     * @param prjStatusList プロジェクト状態
     * @throws SQLException SQL実行例外
     */
    public void insertProjectTmpStatus(PrjPrjdataTmpModel ppMdl,
                                           List<PrjPrjstatusTmpModel> prjStatusList)
        throws SQLException {

        if (prjStatusList == null || prjStatusList.size() < 1) {
            return;
        }

        for (PrjPrjstatusTmpModel ppsMdl : prjStatusList) {
            insertProjectTmpStatus(ppMdl, ppsMdl);
        }
    }

    /**
     * <br>[機  能] プロジェクト状態を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param ppMdl PrjPrjdataModel
     * @param ppsMdl PrjPrjstatusModel
     * @throws SQLException SQL実行例外
     */
    public void insertProjectStatus(PrjPrjdataModel ppMdl, PrjPrjstatusModel ppsMdl)
    throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" PRJ_PRJSTATUS(");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   PRS_SID,");
            sql.addSql("   PRS_SORT,");
            sql.addSql("   PRS_NAME,");
            sql.addSql("   PRS_RATE,");
            sql.addSql("   PRS_AUID,");
            sql.addSql("   PRS_ADATE,");
            sql.addSql("   PRS_EUID,");
            sql.addSql("   PRS_EDATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            sql.addIntValue(ppMdl.getPrjSid());
            sql.addIntValue(ppsMdl.getPrsSid());
            sql.addIntValue(ppsMdl.getPrsSort());
            sql.addStrValue(ppsMdl.getPrsName());
            sql.addIntValue(ppsMdl.getPrsRate());
            sql.addIntValue(ppMdl.getPrjAuid());
            sql.addDateValue(ppMdl.getPrjAdate());
            sql.addIntValue(ppMdl.getPrjEuid());
            sql.addDateValue(ppMdl.getPrjEdate());

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] プロジェクトテンプレート状態を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param ppMdl PrjPrjdataTmpModel
     * @param ppsMdl PrjPrjstatusTmpModel
     * @throws SQLException SQL実行例外
     */
    public void insertProjectTmpStatus(PrjPrjdataTmpModel ppMdl, PrjPrjstatusTmpModel ppsMdl)
    throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" PRJ_PRJSTATUS_TMP (");
            sql.addSql("   PRT_SID,");
            sql.addSql("   PTT_SID,");
            sql.addSql("   PTT_SORT,");
            sql.addSql("   PTT_NAME,");
            sql.addSql("   PTT_RATE,");
            sql.addSql("   PTT_AUID,");
            sql.addSql("   PTT_ADATE,");
            sql.addSql("   PTT_EUID,");
            sql.addSql("   PTT_EDATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            sql.addIntValue(ppMdl.getPrtSid());
            sql.addIntValue(ppsMdl.getPttSid());
            sql.addIntValue(ppsMdl.getPttSort());
            sql.addStrValue(ppsMdl.getPttName());
            sql.addIntValue(ppsMdl.getPttRate());
            sql.addIntValue(ppMdl.getPrtAuid());
            sql.addDateValue(ppMdl.getPrtAdate());
            sql.addIntValue(ppMdl.getPrtEuid());
            sql.addDateValue(ppMdl.getPrtEdate());

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] TODO情報を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param ptMdl PrjTododataModel
     * @param memberList メンバーリスト
     * @param pshMdl PrjStatusHistoryModel
     * @param binList バイナリSIDリスト
     * @throws SQLException SQL実行例外
     */
    public void insertTodo(
        PrjTododataModel ptMdl,
        List<PrjTodomemberModel> memberList,
        PrjStatusHistoryModel pshMdl,
        List<String> binList) throws SQLException {

        //TODO情報を登録
        PrjTododataDao ppdDao = new PrjTododataDao(getCon());
        ppdDao.insert(ptMdl);

        //担当者情報を登録する
        insertTodoTantou(memberList);

        //TODO変更履歴情報を登録する
        PrjStatusHistoryDao pshDao = new PrjStatusHistoryDao(getCon());
        pshDao.insert(pshMdl);

        //TODO添付情報を登録する
        insertTodoBin(binList, ptMdl);

        //TODO情報のデータ使用量を登録
        PrjUsedDataBiz usedDataBiz = new PrjUsedDataBiz(getCon());
        usedDataBiz.insertTodoSize(Arrays.asList(ptMdl.getPtdSid()), true);

    }

    /**
     * <br>[機  能] TODO添付情報を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param binList バイナリSIDリスト
     * @param ptMdl PrjTododataModel
     * @throws SQLException SQL実行例外
     */
    public void insertTodoBin(List<String> binList, PrjTododataModel ptMdl)
    throws SQLException {

        if (binList == null || binList.size() < 1) {
            return;
        }

        for (String binSid : binList) {
            insertTodoBin(NullDefault.getLong(binSid, -1), ptMdl);
        }
    }

    /**
     * <br>[機  能] TODO添付情報を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param binSid バイナリSID
     * @param ptMdl PrjTododataModel
     * @throws SQLException SQL実行例外
     */
    public void insertTodoBin(Long binSid, PrjTododataModel ptMdl) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" PRJ_TODO_BIN(");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   PTD_SID,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   USR_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            sql.addIntValue(ptMdl.getPrjSid());
            sql.addIntValue(ptMdl.getPtdSid());
            sql.addLongValue(binSid);
            sql.addIntValue(ptMdl.getPtdEuid());

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] 担当者情報を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param memberList メンバーリスト
     * @throws SQLException SQL実行例外
     */
    public void insertTodoTantou(List<PrjTodomemberModel> memberList) throws SQLException {

        if (memberList == null || memberList.size() < 1) {
            return;
        }

        for (PrjTodomemberModel member : memberList) {
            insertTodoTantou(member);
        }
    }

    /**
     * <br>[機  能] 担当者情報を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param member PrjTodomemberModel
     * @throws SQLException SQL実行例外
     */
    public void insertTodoTantou(PrjTodomemberModel member) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" PRJ_TODOMEMBER(");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   PTD_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   PTM_EMPLOYEE_KBN,");
            sql.addSql("   PTM_AUID,");
            sql.addSql("   PTM_ADATE,");
            sql.addSql("   PTM_EUID,");
            sql.addSql("   PTM_EDATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            sql.addIntValue(member.getPrjSid());
            sql.addIntValue(member.getPtdSid());
            sql.addIntValue(member.getUsrSid());
            sql.addIntValue(member.getPtmEmployeeKbn());
            sql.addIntValue(member.getPtmAuid());
            sql.addDateValue(member.getPtmAdate());
            sql.addIntValue(member.getPtmEuid());
            sql.addDateValue(member.getPtmEdate());

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] TODO情報を更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param ptMdl PrjTododataModel
     * @param memberList メンバーリスト
     * @param pshMdl PrjStatusHistoryModel
     * @param binList バイナリSIDリスト
     * @throws SQLException SQL実行例外
     */
    public void updateTodo(
        PrjTododataModel ptMdl,
        List<PrjTodomemberModel> memberList,
        PrjStatusHistoryModel pshMdl,
        List<String> binList) throws SQLException {

        //TODO情報のデータ使用量を登録(変更前のデータ使用量を減算)
        PrjUsedDataBiz usedDataBiz = new PrjUsedDataBiz(getCon());
        usedDataBiz.insertTodoSize(Arrays.asList(ptMdl.getPtdSid()), false);

        //TODO情報を更新
        PrjTododataDao ppdDao = new PrjTododataDao(getCon());
        ppdDao.update(ptMdl);

        //不要になった担当者を削除する
        deleteTodoTanto(ptMdl.getPtdSid(), memberList);
        //担当者を登録・更新する
        updateTodoTanto(memberList);

        //TODO変更履歴情報を登録する
        if (pshMdl != null) {
            PrjStatusHistoryDao pshDao = new PrjStatusHistoryDao(getCon());
            pshDao.insert(pshMdl);
        }

        //削除対象のバイナリSIDを取得する
        List<String> delBinList = getDeleteTodoBin(ptMdl.getPtdSid(), binList);
        //不要になったTODO添付情報を削除する
        deleteTodoBin(ptMdl.getPtdSid(), binList);
        //不要になったバイナリー情報を論理削除する
        deleteBin(delBinList, ptMdl);
        //TODO添付情報を登録・更新する
        updateTodoBin(binList, ptMdl);

        //TODOコメント情報のプロジェクトSIDを変更する
        updateTodoCmtPrjSid(ptMdl);

        //TODO変更履歴のプロジェクトSIDを変更する
        updateTodoHisPrjSid(ptMdl);
        //TODO状態がマスタに存在しない場合、TODO変更履歴のTODO状態を0%に更新する
        updateTodoHisStatusNotExists(ptMdl.getPtdEuid(), ptMdl.getPtdEdate(), ptMdl.getPrjSid());

        //TODO情報のデータ使用量を登録
        usedDataBiz.insertTodoSize(Arrays.asList(ptMdl.getPtdSid()), true);

    }

    /**
     * <br>[機  能] TODOコメント情報のプロジェクトSIDを変更する
     * <br>[解  説]
     * <br>[備  考]
     * @param ptMdl PrjTododataModel
     * @throws SQLException SQL実行例外
     */
    public void updateTodoCmtPrjSid(PrjTododataModel ptMdl) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   PRJ_TODOCOMMENT");
            sql.addSql(" set ");
            sql.addSql("   PRJ_SID=?,");
            sql.addSql("   PCM_EUID=?,");
            sql.addSql("   PCM_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   PTD_SID=?");

            sql.addIntValue(ptMdl.getPrjSid());
            sql.addIntValue(ptMdl.getPtdEuid());
            sql.addDateValue(ptMdl.getPtdEdate());
            //where
            sql.addIntValue(ptMdl.getPtdSid());

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] TODO変更履歴のプロジェクトSIDを変更する
     * <br>[解  説]
     * <br>[備  考]
     * @param ptMdl PrjTododataModel
     * @throws SQLException SQL実行例外
     */
    public void updateTodoHisPrjSid(PrjTododataModel ptMdl) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   PRJ_STATUS_HISTORY");
            sql.addSql(" set ");
            sql.addSql("   PRJ_SID=?,");
            sql.addSql("   PSH_EUID=?,");
            sql.addSql("   PSH_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   PTD_SID=?");

            sql.addIntValue(ptMdl.getPrjSid());
            sql.addIntValue(ptMdl.getPtdEuid());
            sql.addDateValue(ptMdl.getPtdEdate());
            //where
            sql.addIntValue(ptMdl.getPtdSid());

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] TODO添付情報を登録・更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param binList バイナリSIDリスト
     * @param ptMdl PrjTododataModel
     * @throws SQLException SQL実行例外
     */
    public void updateTodoBin(List<String> binList, PrjTododataModel ptMdl)
    throws SQLException {

        if (binList == null || binList.size() < 1) {
            return;
        }

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {

            for (String binSid : binList) {
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" update");
                sql.addSql("   PRJ_TODO_BIN");
                sql.addSql(" set ");
                sql.addSql("   PRJ_SID=?,");
                sql.addSql("   USR_SID=?");
                sql.addSql(" where ");
                sql.addSql("   PTD_SID=?");
                sql.addSql(" and");
                sql.addSql("   BIN_SID=?");
                sql.addIntValue(ptMdl.getPrjSid());
                sql.addIntValue(ptMdl.getPtdEuid());
                //where
                sql.addIntValue(ptMdl.getPtdSid());
                sql.addLongValue(NullDefault.getLong(binSid, -1));

                log__.info(sql.toLogString());

                pstmt = con.prepareStatement(sql.toSqlString());
                sql.setParameter(pstmt);
                int count = pstmt.executeUpdate();

                if (count < 1) {
                    //レコードが無い場合はinsert
                    insertTodoBin(NullDefault.getLong(binSid, -1), ptMdl);
                }
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] 削除対象のバイナリSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param todoSid TODOSID
     * @param binList バイナリSIDリスト
     * @return List in String
     * @throws SQLException SQL実行例外
     */
    public List<String> getDeleteTodoBin(int todoSid, List<String> binList)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        List<String> ret = new ArrayList<String>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   BIN_SID");
            sql.addSql(" from ");
            sql.addSql("   PRJ_TODO_BIN");
            sql.addSql(" where ");
            sql.addSql("   PTD_SID=?");
            sql.addIntValue(todoSid);

            if (binList != null && binList.size() > 0) {
                sql.addSql(" and ");
                sql.addSql("    BIN_SID not in (");

                int index = 0;
                for (String binSid : binList) {
                    sql.addSql("     ? ");
                    sql.addLongValue(NullDefault.getLong(binSid, -1));

                    if (index < binList.size() - 1) {
                        sql.addSql("     , ");
                    }
                    index++;
                }
                sql.addSql("   )");
            }

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(rs.getString("BIN_SID"));
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
     * <br>[機  能] 不要になったバイナリー情報を論理削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param binList 削除するバイナリSIDリスト
     * @param ptMdl PrjTododataModel
     * @throws SQLException SQL実行例外
     */
    public void deleteBin(List<String> binList, PrjTododataModel ptMdl)
    throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        if (binList == null || binList.size() < 1) {
            return;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_BINF");
            sql.addSql(" set ");
            sql.addSql("   BIN_UPUSER = ?,");
            sql.addSql("   BIN_UPDATE = ?,");
            sql.addSql("   BIN_JKBN = ?");
            sql.addIntValue(ptMdl.getPtdEuid());
            sql.addDateValue(ptMdl.getPtdEdate());
            sql.addIntValue(GSConst.JTKBN_DELETE);

            sql.addSql(" where ");
            sql.addSql("   BIN_SID in (");
            int index = 0;
            for (String binSid : binList) {
                sql.addSql("     ? ");
                sql.addLongValue(NullDefault.getLong(binSid, -1));

                if (index < binList.size() - 1) {
                    sql.addSql("     , ");
                }
                index++;
            }
            sql.addSql("   )");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] 不要になったTODO添付情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param todoSid TODOSID
     * @param binList バイナリSIDリスト
     * @throws SQLException SQL実行例外
     */
    public void deleteTodoBin(int todoSid, List<String> binList) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   PRJ_TODO_BIN");
            sql.addSql(" where ");
            sql.addSql("   PTD_SID=?");
            sql.addIntValue(todoSid);

            if (binList != null && binList.size() > 0) {
                sql.addSql(" and ");
                sql.addSql("    BIN_SID not in (");

                int index = 0;
                for (String binSid : binList) {
                    sql.addSql("     ? ");
                    sql.addLongValue(NullDefault.getLong(binSid, -1));

                    if (index < binList.size() - 1) {
                        sql.addSql("     , ");
                    }
                    index++;
                }
                sql.addSql("   )");
            }

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] 不要になった担当者を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param todoSid TODOSID
     * @param memberList プロジェクトメンバー
     * @throws SQLException SQL実行例外
     */
    public void deleteTodoTanto(int todoSid, List<PrjTodomemberModel> memberList)
    throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   PRJ_TODOMEMBER");
            sql.addSql(" where ");
            sql.addSql("   PTD_SID=?");
            sql.addIntValue(todoSid);

            if (memberList != null && memberList.size() > 0) {
                sql.addSql(" and ");
                sql.addSql("    USR_SID not in (");

                int index = 0;
                for (PrjTodomemberModel pmMdl : memberList) {
                    sql.addSql("     ? ");
                    sql.addIntValue(pmMdl.getUsrSid());

                    if (index < memberList.size() - 1) {
                        sql.addSql("     , ");
                    }
                    index++;
                }
                sql.addSql("   )");
            }

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] 担当者を登録・更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param memberList メンバーリスト
     * @throws SQLException SQL実行例外
     */
    public void updateTodoTanto(List<PrjTodomemberModel> memberList) throws SQLException {

        if (memberList == null || memberList.size() < 1) {
            return;
        }

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {

            for (PrjTodomemberModel member : memberList) {
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" update");
                sql.addSql("   PRJ_TODOMEMBER");
                sql.addSql(" set ");
                sql.addSql("   PRJ_SID=?,");
                sql.addSql("   PTM_EUID=?,");
                sql.addSql("   PTM_EDATE=?");
                sql.addSql(" where ");
                sql.addSql("   PTD_SID=?");
                sql.addSql(" and");
                sql.addSql("   USR_SID=?");
                sql.addSql(" and");
                sql.addSql("   PTM_EMPLOYEE_KBN=?");

                sql.addIntValue(member.getPrjSid());
                sql.addIntValue(member.getPtmEuid());
                sql.addDateValue(member.getPtmEdate());
                //where
                sql.addIntValue(member.getPtdSid());
                sql.addIntValue(member.getUsrSid());
                sql.addIntValue(member.getPtmEmployeeKbn());

                log__.info(sql.toLogString());

                pstmt = con.prepareStatement(sql.toSqlString());
                sql.setParameter(pstmt);
                int count = pstmt.executeUpdate();

                if (count < 1) {
                    //レコードが無い場合はinsert
                    insertTodoTantou(member);
                }
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

}
