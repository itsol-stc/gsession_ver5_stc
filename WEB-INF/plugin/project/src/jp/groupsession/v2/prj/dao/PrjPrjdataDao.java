package jp.groupsession.v2.prj.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.model.PrjPrjdataModel;
import jp.groupsession.v2.prj.model.ProjectItemModel;

/**
 * <p>PRJ_PRJDATA Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class PrjPrjdataDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(PrjPrjdataDao.class);

    /**
     * <p>Default Constructor
     */
    public PrjPrjdataDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public PrjPrjdataDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] プロジェクト情報を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param bean PRJ_PRJDATA Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(PrjPrjdataModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" PRJ_PRJDATA(");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   PRJ_MY_KBN,");
            sql.addSql("   PRJ_ID,");
            sql.addSql("   PRJ_NAME,");
            sql.addSql("   PRJ_NAME_SHORT,");
            sql.addSql("   PRJ_YOSAN,");
            sql.addSql("   PRJ_KOUKAI_KBN,");
            sql.addSql("   PRJ_DATE_START,");
            sql.addSql("   PRJ_DATE_END,");
            sql.addSql("   PRJ_STATUS_SID,");
            sql.addSql("   PRJ_TARGET,");
            sql.addSql("   PRJ_CONTENT,");
            sql.addSql("   PRJ_EDIT,");
            sql.addSql("   PRJ_MAIL_KBN,");
            sql.addSql("   PRJ_AUID,");
            sql.addSql("   PRJ_ADATE,");
            sql.addSql("   PRJ_EUID,");
            sql.addSql("   PRJ_EDATE,");
            sql.addSql("   BIN_SID");
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

            sql.addIntValue(bean.getPrjSid());
            sql.addIntValue(bean.getPrjMyKbn());
            sql.addStrValue(bean.getPrjId());
            sql.addStrValue(bean.getPrjName());
            sql.addStrValue(bean.getPrjNameShort());
            sql.addLongValue(bean.getPrjYosan());
            sql.addIntValue(bean.getPrjKoukaiKbn());
            sql.addDateValue(bean.getPrjDateStart());
            sql.addDateValue(bean.getPrjDateEnd());
            sql.addIntValue(bean.getPrjStatusSid());
            sql.addStrValue(bean.getPrjTarget());
            sql.addStrValue(bean.getPrjContent());
            sql.addIntValue(bean.getPrjEdit());
            sql.addIntValue(bean.getPrjMailKbn());
            sql.addIntValue(bean.getPrjAuid());
            sql.addDateValue(bean.getPrjAdate());
            sql.addIntValue(bean.getPrjEuid());
            sql.addDateValue(bean.getPrjEdate());
            sql.addLongValue(bean.getBinSid());

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
     * <br>[機  能] プロジェクト情報を更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param bean PRJ_PRJDATA Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(PrjPrjdataModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   PRJ_PRJDATA");
            sql.addSql(" set ");
            sql.addSql("   PRJ_ID=?,");
            sql.addSql("   PRJ_NAME=?,");
            sql.addSql("   PRJ_NAME_SHORT=?,");
            sql.addSql("   PRJ_YOSAN=?,");
            sql.addSql("   PRJ_KOUKAI_KBN=?,");
            sql.addSql("   PRJ_DATE_START=?,");
            sql.addSql("   PRJ_DATE_END=?,");
            sql.addSql("   PRJ_STATUS_SID=?,");
            sql.addSql("   PRJ_TARGET=?,");
            sql.addSql("   PRJ_CONTENT=?,");
            sql.addSql("   PRJ_EDIT=?,");
            sql.addSql("   PRJ_MAIL_KBN=?,");
            sql.addSql("   PRJ_EUID=?,");
            sql.addSql("   PRJ_EDATE=?,");
            sql.addSql("   BIN_SID=?");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");

            sql.addStrValue(bean.getPrjId());
            sql.addStrValue(bean.getPrjName());
            sql.addStrValue(bean.getPrjNameShort());
            sql.addLongValue(bean.getPrjYosan());
            sql.addIntValue(bean.getPrjKoukaiKbn());
            sql.addDateValue(bean.getPrjDateStart());
            sql.addDateValue(bean.getPrjDateEnd());
            sql.addIntValue(bean.getPrjStatusSid());
            sql.addStrValue(bean.getPrjTarget());
            sql.addStrValue(bean.getPrjContent());
            sql.addIntValue(bean.getPrjEdit());
            sql.addIntValue(bean.getPrjMailKbn());
            sql.addIntValue(bean.getPrjEuid());
            sql.addDateValue(bean.getPrjEdate());
            sql.addLongValue(bean.getBinSid());

            //where
            sql.addIntValue(bean.getPrjSid());

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            count = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <p>Select PRJ_PRJDATA All Data
     * @return List in PRJ_PRJDATAModel
     * @throws SQLException SQL実行例外
     */
    public List<PrjPrjdataModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<PrjPrjdataModel> ret = new ArrayList<PrjPrjdataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   PRJ_MY_KBN,");
            sql.addSql("   PRJ_ID,");
            sql.addSql("   PRJ_NAME,");
            sql.addSql("   PRJ_NAME_SHORT,");
            sql.addSql("   PRJ_YOSAN,");
            sql.addSql("   PRJ_KOUKAI_KBN,");
            sql.addSql("   PRJ_DATE_START,");
            sql.addSql("   PRJ_DATE_END,");
            sql.addSql("   PRJ_STATUS_SID,");
            sql.addSql("   PRJ_TARGET,");
            sql.addSql("   PRJ_CONTENT,");
            sql.addSql("   PRJ_EDIT,");
            sql.addSql("   PRJ_MAIL_KBN,");
            sql.addSql("   PRJ_AUID,");
            sql.addSql("   PRJ_ADATE,");
            sql.addSql("   PRJ_EUID,");
            sql.addSql("   PRJ_EDATE,");
            sql.addSql("   BIN_SID");
            sql.addSql(" from ");
            sql.addSql("   PRJ_PRJDATA");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getPrjPrjdataFromRs(rs));
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
     * <br>[機  能] プロジェクト情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param prjSid PRJ_SID
     * @return ProjectItemModel
     * @throws SQLException SQL実行例外
     */
    public PrjPrjdataModel getProjectData(int prjSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        PrjPrjdataModel ret = null;
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   PRJ_MY_KBN,");
            sql.addSql("   PRJ_ID,");
            sql.addSql("   PRJ_NAME,");
            sql.addSql("   PRJ_NAME_SHORT,");
            sql.addSql("   PRJ_YOSAN,");
            sql.addSql("   PRJ_KOUKAI_KBN,");
            sql.addSql("   PRJ_DATE_START,");
            sql.addSql("   PRJ_DATE_END,");
            sql.addSql("   PRJ_STATUS_SID,");
            sql.addSql("   PRJ_TARGET,");
            sql.addSql("   PRJ_CONTENT,");
            sql.addSql("   PRJ_EDIT,");
            sql.addSql("   PRJ_MAIL_KBN,");
            sql.addSql("   PRJ_AUID,");
            sql.addSql("   PRJ_ADATE,");
            sql.addSql("   PRJ_EUID,");
            sql.addSql("   PRJ_EDATE,");
            sql.addSql("   BIN_SID");
            sql.addSql(" from ");
            sql.addSql("   PRJ_PRJDATA");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");
            sql.addIntValue(prjSid);
            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getPrjPrjdataFromRs(rs);
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
     * <br>[備  考] 通常プロジェクト情報を取得します。
     * @param excludeList 結果に含めないプロジェクト
     * @return プロジェクト情報の件数
     * @throws SQLException SQL実行例外
     */
    public int getProjectCount(List<Integer> excludeList)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int count = 0;
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(PRJ_SID) as CNT");
            sql.addSql(" from ");
            sql.addSql("   PRJ_PRJDATA");
            sql.addSql(" where ");
            sql.addSql("   PRJ_MY_KBN=?");
            sql.addIntValue(GSConstProject.KBN_MY_PRJ_DEF);

            if (!excludeList.isEmpty()) {
                for (int excludeSid : excludeList) {
                    sql.addSql(" and");
                    sql.addSql("   PRJ_SID <> ?");
                    sql.addIntValue(excludeSid);
                }
            }

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
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
        return count;
    }

    /**
     * <br>[機  能] プロジェクト情報リストを取得する
     * <br>[解  説]
     * <br>[備  考] 通常プロジェクト情報を取得します。
     * @param excludeList 結果に含めないプロジェクト
     * @param start 一覧開始位置
     * @param end   一覧終了位置
     * @return 通常プロジェクト情報
     * @throws SQLException SQL実行例外
     */
    public List<PrjPrjdataModel> getProjectList(List<Integer> excludeList, int start, int end)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<PrjPrjdataModel> ret = new ArrayList<PrjPrjdataModel>();
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   PRJ_MY_KBN,");
            sql.addSql("   PRJ_ID,");
            sql.addSql("   PRJ_NAME,");
            sql.addSql("   PRJ_NAME_SHORT,");
            sql.addSql("   PRJ_YOSAN,");
            sql.addSql("   PRJ_KOUKAI_KBN,");
            sql.addSql("   PRJ_DATE_START,");
            sql.addSql("   PRJ_DATE_END,");
            sql.addSql("   PRJ_STATUS_SID,");
            sql.addSql("   PRJ_TARGET,");
            sql.addSql("   PRJ_CONTENT,");
            sql.addSql("   PRJ_EDIT,");
            sql.addSql("   PRJ_MAIL_KBN,");
            sql.addSql("   PRJ_AUID,");
            sql.addSql("   PRJ_ADATE,");
            sql.addSql("   PRJ_EUID,");
            sql.addSql("   PRJ_EDATE,");
            sql.addSql("   BIN_SID");
            sql.addSql(" from ");
            sql.addSql("   PRJ_PRJDATA");
            sql.addSql(" where ");
            sql.addSql("   PRJ_MY_KBN=?");
            sql.addIntValue(GSConstProject.KBN_MY_PRJ_DEF);

            if (!excludeList.isEmpty()) {
                for (int excludeSid : excludeList) {
                    sql.addSql(" and");
                    sql.addSql("   PRJ_SID <> ?");
                    sql.addIntValue(excludeSid);
                }
            }

            sql.addSql(" order by");
            sql.addSql("   (");
            sql.addSql("    case");
            sql.addSql("    when PRJ_STATUS_SID = ? then 1");
            sql.addSql("    else 0");
            sql.addSql("    end");
            sql.addSql("   ),");
            sql.addSql("   PRJ_NAME");
            sql.addIntValue(GSConstProject.STATUS_100);

            sql.setPagingValue(start - 1, end - start + 1);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getPrjPrjdataFromRs(rs));
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
     * <br>[機  能] プロジェクト情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param prjSids PRJ_SIDリスト
     * @return ProjectItemModel
     * @throws SQLException SQL実行例外
     */
    public PrjPrjdataModel getProjectData(String[] prjSids) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        PrjPrjdataModel ret = null;
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   PRJ_MY_KBN,");
            sql.addSql("   PRJ_ID,");
            sql.addSql("   PRJ_NAME,");
            sql.addSql("   PRJ_NAME_SHORT,");
            sql.addSql("   PRJ_YOSAN,");
            sql.addSql("   PRJ_KOUKAI_KBN,");
            sql.addSql("   PRJ_DATE_START,");
            sql.addSql("   PRJ_DATE_END,");
            sql.addSql("   PRJ_STATUS_SID,");
            sql.addSql("   PRJ_TARGET,");
            sql.addSql("   PRJ_CONTENT,");
            sql.addSql("   PRJ_EDIT,");
            sql.addSql("   PRJ_MAIL_KBN,");
            sql.addSql("   PRJ_AUID,");
            sql.addSql("   PRJ_ADATE,");
            sql.addSql("   PRJ_EUID,");
            sql.addSql("   PRJ_EDATE,");
            sql.addSql("   BIN_SID");
            sql.addSql(" from ");
            sql.addSql("   PRJ_PRJDATA");
            sql.addSql(" where ");

            sql.addSql("   PRJ_SID in (");

            for (int i = 0; i < prjSids.length; i++) {
                sql.addSql("     ? ");
                sql.addIntValue(NullDefault.getInt(prjSids[i], 0));

                if (i < prjSids.length - 1) {
                    sql.addSql("     , ");
                }
            }

            sql.addSql("   )");

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getPrjPrjdataFromRs(rs);
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
     * <br>[機  能] プロジェクト情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param prjSid PRJ_SID
     * @return ProjectItemModel
     * @throws SQLException SQL実行例外
     */
    public ProjectItemModel getProjectInfo(int prjSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ProjectItemModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PRJ_PRJDATA.PRJ_ID,");
            sql.addSql("   PRJ_PRJDATA.PRJ_MY_KBN,");
            sql.addSql("   PRJ_PRJDATA.PRJ_NAME,");
            sql.addSql("   PRJ_PRJDATA.PRJ_NAME_SHORT,");
            sql.addSql("   PRJ_PRJDATA.PRJ_YOSAN,");
            sql.addSql("   PRJ_PRJDATA.PRJ_KOUKAI_KBN,");
            sql.addSql("   PRJ_PRJDATA.PRJ_DATE_START,");
            sql.addSql("   PRJ_PRJDATA.PRJ_DATE_END,");
            sql.addSql("   PRJ_PRJDATA.PRJ_STATUS_SID,");
            sql.addSql("   PRJ_PRJDATA.PRJ_TARGET,");
            sql.addSql("   PRJ_PRJDATA.PRJ_CONTENT,");
            sql.addSql("   PRJ_PRJDATA.PRJ_MAIL_KBN,");
            sql.addSql("   PRJ_PRJDATA.PRJ_EDIT,");
            sql.addSql("   PRJ_PRJDATA.BIN_SID,");
            sql.addSql("   PRJ_PRJSTATUS.PRS_NAME,");
            sql.addSql("   PRJ_PRJSTATUS.PRS_RATE");
            sql.addSql(" from");
            sql.addSql("   PRJ_PRJDATA,");
            sql.addSql("   PRJ_PRJSTATUS");
            sql.addSql(" where ");
            sql.addSql("   PRJ_PRJDATA.PRJ_SID = PRJ_PRJSTATUS.PRJ_SID");
            sql.addSql(" and");
            sql.addSql("   PRJ_PRJDATA.PRJ_STATUS_SID = PRJ_PRJSTATUS.PRS_SID");
            sql.addSql(" and");
            sql.addSql("   PRJ_PRJDATA.PRJ_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(prjSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = new ProjectItemModel();
                ret.setProjectId(rs.getString("PRJ_ID"));
                ret.setPrjMyKbn(rs.getInt("PRJ_MY_KBN"));
                ret.setProjectName(rs.getString("PRJ_NAME"));
                ret.setProjectRyakuName(rs.getString("PRJ_NAME_SHORT"));
                ret.setYosan(rs.getLong("PRJ_YOSAN"));
                ret.setKoukaiKbn(rs.getInt("PRJ_KOUKAI_KBN"));
                ret.setStartDate(UDate.getInstanceTimestamp(rs.getTimestamp("PRJ_DATE_START")));
                ret.setEndDate(UDate.getInstanceTimestamp(rs.getTimestamp("PRJ_DATE_END")));
                ret.setStatus(rs.getInt("PRJ_STATUS_SID"));
                ret.setMokuhyou(rs.getString("PRJ_TARGET"));
                ret.setNaiyou(rs.getString("PRJ_CONTENT"));
                ret.setPrjMailKbn(rs.getInt("PRJ_MAIL_KBN"));
                ret.setEditKengen(rs.getInt("PRJ_EDIT"));
                ret.setPrjBinSid(rs.getLong("BIN_SID"));
                ret.setStatusName(rs.getString("PRS_NAME"));
                ret.setRate(rs.getInt("PRS_RATE"));
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
     * <br>[機  能] 編集プロジェクト(マイプロジェクト)のユーザを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param prjSid PRJ_SID
     * @return usrSid ユーザSID(マイプロジェクトではない場合は-1)
     * @throws SQLException SQL実行例外
     */
    public int getEditMyProjectUsrSid(int prjSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int usrSid = -1;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PRJ_MEMBERS.USR_SID");
            sql.addSql(" from");
            sql.addSql("   PRJ_PRJDATA,");
            sql.addSql("   PRJ_MEMBERS");
            sql.addSql(" where ");
            sql.addSql("   PRJ_PRJDATA.PRJ_SID = ?");
            sql.addSql(" and");
            sql.addSql("   PRJ_PRJDATA.PRJ_MY_KBN = ?");
            sql.addSql(" and");
            sql.addSql("   PRJ_PRJDATA.PRJ_SID = PRJ_MEMBERS.PRJ_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(prjSid);
            sql.addIntValue(GSConstProject.KBN_MY_PRJ_MY);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                usrSid = rs.getInt("USR_SID");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return usrSid;
    }

    /**
     * <br>[機  能] ユーザのマイプロジェクト数を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return count ユーザのマイプロジェクト数（マイプロジェクトが存在しているか判定する）
     * @throws SQLException SQL実行例外
     */
    public int getMyPrjCount(int userSid) throws SQLException {

        int count = 0;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count (*) as CNT");
            sql.addSql(" from");
            sql.addSql("   PRJ_PRJDATA,");
            sql.addSql("   PRJ_MEMBERS");
            sql.addSql(" where ");
            sql.addSql("   PRJ_MEMBERS.USR_SID = ?");
            sql.addSql(" and");
            sql.addSql("   PRJ_PRJDATA.PRJ_MY_KBN = ?");
            sql.addSql(" and");
            sql.addSql("   PRJ_PRJDATA.PRJ_SID = PRJ_MEMBERS.PRJ_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(userSid);
            sql.addIntValue(GSConstProject.KBN_MY_PRJ_MY);

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
        return count;
    }

    /**
     * <p>Delete PRJ_PRJDATA
     * @param prjSid PRJ_SID
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int prjSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   PRJ_PRJDATA");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(prjSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <br>[機  能] 指定されたプロジェクトIDが存在するかチェックする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param prjSid プロジェクトSID
     * @param prjId プロジェクトID
     * @param usrSid セッションユーザSID
     * @return ret true:存在する false:存在しない
     * @throws SQLException SQL実行例外
     */
    public boolean isExistSameProjectId(int prjSid, String prjId, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        boolean ret = false;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(PRJ_PRJDATA.PRJ_SID) as cnt");
            sql.addSql(" from");
            sql.addSql("   PRJ_PRJDATA");
            sql.addSql(" where ");
            sql.addSql("   (");
            sql.addSql("     (");
            sql.addSql("       PRJ_PRJDATA.PRJ_ID = ?");
            sql.addSql("     and");
            sql.addSql("       PRJ_PRJDATA.PRJ_MY_KBN = ?");
            sql.addSql("     )");
            sql.addSql("     or");
            sql.addSql("     (");
            sql.addSql("       PRJ_PRJDATA.PRJ_ID = ?");
            sql.addSql("     and");
            sql.addSql("       PRJ_PRJDATA.PRJ_MY_KBN = ?");
            sql.addSql("     and");
            sql.addSql("       exists");
            sql.addSql("       (");
            sql.addSql("         select");
            sql.addSql("           *");
            sql.addSql("         from");
            sql.addSql("           PRJ_MEMBERS");
            sql.addSql("         where");
            sql.addSql("           PRJ_MEMBERS.USR_SID = ?");
            sql.addSql("         and");
            sql.addSql("           PRJ_MEMBERS.PRJ_SID = PRJ_PRJDATA.PRJ_SID");
            sql.addSql("       )");
            sql.addSql("     )");
            sql.addSql("   )");

            sql.addStrValue(prjId);
            sql.addIntValue(GSConstProject.KBN_MY_PRJ_DEF);
            sql.addStrValue(prjId);
            sql.addIntValue(GSConstProject.KBN_MY_PRJ_MY);
            sql.addIntValue(usrSid);

            if (prjSid > 0) {
                sql.addSql(" and");
                sql.addSql("   PRJ_PRJDATA.PRJ_SID <> ?");
                sql.addIntValue(prjSid);
            }

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] TODOショートメール通知区分を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param prjSid PRJ_SID
     * @return TODOショートメール通知区分
     * @throws SQLException SQL実行例外
     */
    public int getTodoSMailKbn(int prjSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int mailKbn = -1;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PRJ_MAIL_KBN");
            sql.addSql(" from");
            sql.addSql("   PRJ_PRJDATA");
            sql.addSql(" where ");
            sql.addSql("   PRJ_PRJDATA.PRJ_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(prjSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                mailKbn = rs.getInt("PRJ_MAIL_KBN");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return mailKbn;
    }

    /**
     * <br>[機  能] 指定のプロジェクトのバイナリーファイル情報を論理削除する
     * <br>[解  説] 状態区分を9:削除に更新する
     * <br>[備  考]
     * @param prjSid プロジェクトSID
     * @return 削除(更新)件数
     * @throws SQLException SQL実行例外
     */
    public int deleteBinfProject(int prjSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  update");
            sql.addSql("    CMN_BINF");
            sql.addSql("  set");
            sql.addSql("    BIN_JKBN = ?");
            sql.addSql("  where");
            sql.addSql("    BIN_SID in (");
            sql.addSql("      select BIN_SID from PRJ_PRJDATA");
            sql.addSql("      where PRJ_SID = ?");
            sql.addSql("    )");

            sql.addIntValue(GSConst.JTKBN_DELETE);
            sql.addIntValue(prjSid);

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            count = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }

        return count;
    }

    /**
     * <p>指定されたプロジェクトSIDとアイコンバイナリSIDの組み合わせが存在するかを確認する
     * @param prjSid プロジェクトSID
     * @param icoBinSid アイコンバイナリSID
     * @return 結果 true:存在する false:存在しない
     * @throws SQLException SQL実行例外
     */
    public boolean existPrjIco(int prjSid, Long icoBinSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean ret = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PRJ_SID");
            sql.addSql(" from");
            sql.addSql("   PRJ_PRJDATA");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");
            sql.addSql(" and ");
            sql.addSql("   BIN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(prjSid);
            sql.addLongValue(icoBinSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            ret = rs.next();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return ret;
    }

    /**
     * <p>Create PRJ_PRJDATA Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created PrjPrjdataModel
     * @throws SQLException SQL実行例外
     */
    private PrjPrjdataModel __getPrjPrjdataFromRs(ResultSet rs) throws SQLException {
        PrjPrjdataModel bean = new PrjPrjdataModel();
        bean.setPrjSid(rs.getInt("PRJ_SID"));
        bean.setPrjMyKbn(rs.getInt("PRJ_MY_KBN"));
        bean.setPrjId(rs.getString("PRJ_ID"));
        bean.setPrjName(rs.getString("PRJ_NAME"));
        bean.setPrjNameShort(rs.getString("PRJ_NAME_SHORT"));
        bean.setPrjYosan(rs.getLong("PRJ_YOSAN"));
        bean.setPrjKoukaiKbn(rs.getInt("PRJ_KOUKAI_KBN"));
        bean.setPrjDateStart(UDate.getInstanceTimestamp(rs.getTimestamp("PRJ_DATE_START")));
        bean.setPrjDateEnd(UDate.getInstanceTimestamp(rs.getTimestamp("PRJ_DATE_END")));
        bean.setPrjStatusSid(rs.getInt("PRJ_STATUS_SID"));
        bean.setPrjTarget(rs.getString("PRJ_TARGET"));
        bean.setPrjContent(rs.getString("PRJ_CONTENT"));
        bean.setPrjEdit(rs.getInt("PRJ_EDIT"));
        bean.setPrjMailKbn(rs.getInt("PRJ_MAIL_KBN"));
        bean.setPrjAuid(rs.getInt("PRJ_AUID"));
        bean.setPrjAdate(UDate.getInstanceTimestamp(rs.getTimestamp("PRJ_ADATE")));
        bean.setPrjEuid(rs.getInt("PRJ_EUID"));
        bean.setPrjEdate(UDate.getInstanceTimestamp(rs.getTimestamp("PRJ_EDATE")));
        bean.setBinSid(rs.getLong("BIN_SID"));
        return bean;
    }
}
