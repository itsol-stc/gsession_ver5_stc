package jp.groupsession.v2.prj.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.model.PrjPrjdataTmpModel;
import jp.groupsession.v2.prj.model.ProjectItemModel;

/**
 * <p>PRJ_PRJDATA_TMP Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class PrjPrjdataTmpDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(PrjPrjdataTmpDao.class);

    /**
     * <p>Default Constructor
     */
    public PrjPrjdataTmpDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public PrjPrjdataTmpDao(Connection con) {
        super(con);
    }

    /**
     * <p>Insert PRJ_PRJDATA_TMP Data Bindding JavaBean
     * @param bean PRJ_PRJDATA_TMP Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(PrjPrjdataTmpModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" PRJ_PRJDATA_TMP(");
            sql.addSql("   PRT_SID,");
            sql.addSql("   PRT_KBN,");
            sql.addSql("   PRT_TMP_NAME,");
            sql.addSql("   PRT_USR_SID,");
            sql.addSql("   PRT_ID,");
            sql.addSql("   PRT_NAME,");
            sql.addSql("   PRT_NAME_SHORT,");
            sql.addSql("   PRT_YOSAN,");
            sql.addSql("   PRT_KOUKAI_KBN,");
            sql.addSql("   PRT_DATE_START,");
            sql.addSql("   PRT_DATE_END,");
            sql.addSql("   PRT_STATUS_SID,");
            sql.addSql("   PRT_TARGET,");
            sql.addSql("   PRT_CONTENT,");
            sql.addSql("   PRT_MAIL_KBN,");
            sql.addSql("   PRT_EDIT,");
            sql.addSql("   PRT_AUID,");
            sql.addSql("   PRT_ADATE,");
            sql.addSql("   PRT_EUID,");
            sql.addSql("   PRT_EDATE");
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
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getPrtSid());
            sql.addIntValue(bean.getPrtKbn());
            sql.addStrValue(bean.getPrtTmpName());
            sql.addIntValue(bean.getPrtUsrSid());
            sql.addStrValue(bean.getPrtId());
            sql.addStrValue(bean.getPrtName());
            sql.addStrValue(bean.getPrtNameShort());
            sql.addIntValue(bean.getPrtYosan());
            sql.addIntValue(bean.getPrtKoukaiKbn());
            sql.addDateValue(bean.getPrtDateStart());
            sql.addDateValue(bean.getPrtDateEnd());
            sql.addIntValue(bean.getPrtStatusSid());
            sql.addStrValue(bean.getPrtTarget());
            sql.addStrValue(bean.getPrtContent());
            sql.addIntValue(bean.getPrtMailKbn());
            sql.addIntValue(bean.getPrtEdit());
            sql.addIntValue(bean.getPrtAuid());
            sql.addDateValue(bean.getPrtAdate());
            sql.addIntValue(bean.getPrtEuid());
            sql.addDateValue(bean.getPrtEdate());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Update PRJ_PRJDATA_TMP Data Bindding JavaBean
     * @param bean PRJ_PRJDATA_TMP Data Bindding JavaBean
     * @return count update count
     * @throws SQLException SQL実行例外
     */
    public int update(PrjPrjdataTmpModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   PRJ_PRJDATA_TMP");
            sql.addSql(" set ");
            sql.addSql("   PRT_KBN=?,");
            sql.addSql("   PRT_TMP_NAME=?,");
            sql.addSql("   PRT_USR_SID=?,");
            sql.addSql("   PRT_ID=?,");
            sql.addSql("   PRT_NAME=?,");
            sql.addSql("   PRT_NAME_SHORT=?,");
            sql.addSql("   PRT_YOSAN=?,");
            sql.addSql("   PRT_KOUKAI_KBN=?,");
            sql.addSql("   PRT_DATE_START=?,");
            sql.addSql("   PRT_DATE_END=?,");
            sql.addSql("   PRT_STATUS_SID=?,");
            sql.addSql("   PRT_TARGET=?,");
            sql.addSql("   PRT_CONTENT=?,");
            sql.addSql("   PRT_EDIT=?,");
            sql.addSql("   PRT_AUID=?,");
            sql.addSql("   PRT_ADATE=?,");
            sql.addSql("   PRT_EUID=?,");
            sql.addSql("   PRT_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   PRT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getPrtKbn());
            sql.addStrValue(bean.getPrtTmpName());
            sql.addIntValue(bean.getPrtUsrSid());
            sql.addStrValue(bean.getPrtId());
            sql.addStrValue(bean.getPrtName());
            sql.addStrValue(bean.getPrtNameShort());
            sql.addIntValue(bean.getPrtYosan());
            sql.addIntValue(bean.getPrtKoukaiKbn());
            sql.addDateValue(bean.getPrtDateStart());
            sql.addDateValue(bean.getPrtDateEnd());
            sql.addIntValue(bean.getPrtStatusSid());
            sql.addStrValue(bean.getPrtTarget());
            sql.addStrValue(bean.getPrtContent());
            sql.addIntValue(bean.getPrtEdit());
            sql.addIntValue(bean.getPrtAuid());
            sql.addDateValue(bean.getPrtAdate());
            sql.addIntValue(bean.getPrtEuid());
            sql.addDateValue(bean.getPrtEdate());
            //where
            sql.addIntValue(bean.getPrtSid());

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
     * <br>[機  能] プロジェクトテンプレート情報を更新する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param bean PRJ_PRJDATA_TMP Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateTemplate(PrjPrjdataTmpModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   PRJ_PRJDATA_TMP");
            sql.addSql(" set ");
            sql.addSql("   PRT_TMP_NAME=?,");
            sql.addSql("   PRT_ID=?,");
            sql.addSql("   PRT_NAME=?,");
            sql.addSql("   PRT_NAME_SHORT=?,");
            sql.addSql("   PRT_YOSAN=?,");
            sql.addSql("   PRT_KOUKAI_KBN=?,");
            sql.addSql("   PRT_DATE_START=?,");
            sql.addSql("   PRT_DATE_END=?,");
            sql.addSql("   PRT_STATUS_SID=?,");
            sql.addSql("   PRT_TARGET=?,");
            sql.addSql("   PRT_CONTENT=?,");
            sql.addSql("   PRT_MAIL_KBN=?,");
            sql.addSql("   PRT_EDIT=?,");
            sql.addSql("   PRT_EUID=?,");
            sql.addSql("   PRT_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   PRT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getPrtTmpName());
            sql.addStrValue(bean.getPrtId());
            sql.addStrValue(bean.getPrtName());
            sql.addStrValue(bean.getPrtNameShort());
            sql.addIntValue(bean.getPrtYosan());
            sql.addIntValue(bean.getPrtKoukaiKbn());
            sql.addDateValue(bean.getPrtDateStart());
            sql.addDateValue(bean.getPrtDateEnd());
            sql.addIntValue(bean.getPrtStatusSid());
            sql.addStrValue(bean.getPrtTarget());
            sql.addStrValue(bean.getPrtContent());
            sql.addIntValue(bean.getPrtMailKbn());
            sql.addIntValue(bean.getPrtEdit());
            sql.addIntValue(bean.getPrtEuid());
            sql.addDateValue(bean.getPrtEdate());
            //where
            sql.addIntValue(bean.getPrtSid());

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
     * <p>Select PRJ_PRJDATA_TMP All Data
     * @return List in PRJ_PRJDATA_TMPModel
     * @throws SQLException SQL実行例外
     */
    public List<PrjPrjdataTmpModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<PrjPrjdataTmpModel> ret = new ArrayList<PrjPrjdataTmpModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   PRT_SID,");
            sql.addSql("   PRT_KBN,");
            sql.addSql("   PRT_TMP_NAME,");
            sql.addSql("   PRT_USR_SID,");
            sql.addSql("   PRT_ID,");
            sql.addSql("   PRT_NAME,");
            sql.addSql("   PRT_NAME_SHORT,");
            sql.addSql("   PRT_YOSAN,");
            sql.addSql("   PRT_KOUKAI_KBN,");
            sql.addSql("   PRT_DATE_START,");
            sql.addSql("   PRT_DATE_END,");
            sql.addSql("   PRT_STATUS_SID,");
            sql.addSql("   PRT_TARGET,");
            sql.addSql("   PRT_CONTENT,");
            sql.addSql("   PRT_EDIT,");
            sql.addSql("   PRT_AUID,");
            sql.addSql("   PRT_ADATE,");
            sql.addSql("   PRT_EUID,");
            sql.addSql("   PRT_EDATE");
            sql.addSql(" from ");
            sql.addSql("   PRJ_PRJDATA_TMP");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getPrjPrjdataTmpFromRs(rs));
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
     * <p>Select PRJ_PRJDATA_TMP
     * @param bean PRJ_PRJDATA_TMP Model
     * @return PRJ_PRJDATA_TMPModel
     * @throws SQLException SQL実行例外
     */
    public PrjPrjdataTmpModel select(PrjPrjdataTmpModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        PrjPrjdataTmpModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PRT_SID,");
            sql.addSql("   PRT_KBN,");
            sql.addSql("   PRT_TMP_NAME,");
            sql.addSql("   PRT_USR_SID,");
            sql.addSql("   PRT_ID,");
            sql.addSql("   PRT_NAME,");
            sql.addSql("   PRT_NAME_SHORT,");
            sql.addSql("   PRT_YOSAN,");
            sql.addSql("   PRT_KOUKAI_KBN,");
            sql.addSql("   PRT_DATE_START,");
            sql.addSql("   PRT_DATE_END,");
            sql.addSql("   PRT_STATUS_SID,");
            sql.addSql("   PRT_TARGET,");
            sql.addSql("   PRT_CONTENT,");
            sql.addSql("   PRT_EDIT,");
            sql.addSql("   PRT_AUID,");
            sql.addSql("   PRT_ADATE,");
            sql.addSql("   PRT_EUID,");
            sql.addSql("   PRT_EDATE");
            sql.addSql(" from");
            sql.addSql("   PRJ_PRJDATA_TMP");
            sql.addSql(" where ");
            sql.addSql("   PRT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getPrtSid());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getPrjPrjdataTmpFromRs(rs);
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
     * <p>Delete PRJ_PRJDATA_TMP
     * @param bean PRJ_PRJDATA_TMP Model
     * @return count delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(PrjPrjdataTmpModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   PRJ_PRJDATA_TMP");
            sql.addSql(" where ");
            sql.addSql("   PRT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getPrtSid());

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
     * <br>[機  能] プロジェクトテンプレート(複数)を削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param prjTmpSidList 削除SIDリスト
     * @throws SQLException SQL実行例外
     */
    public void deletePrjTemplate(ArrayList<PrjPrjdataTmpModel> prjTmpSidList)
        throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        if (prjTmpSidList == null || prjTmpSidList.size() < 1) {
            return;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   PRJ_PRJDATA_TMP");
            sql.addSql(" where ");
            sql.addSql("   PRT_SID in (");

            for (int i = 0; i < prjTmpSidList.size(); i++) {
                sql.addSql("?");
                PrjPrjdataTmpModel param = prjTmpSidList.get(i);
                sql.addIntValue(param.getPrtSid());
                if (i < prjTmpSidList.size() - 1) {
                    sql.addSql(", ");
                }
            }
            sql.addSql(")");

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
     * <p>Delete PRJ_PRJDATA_TMP
     * @param prtSid prtSid
     * @return count delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int prtSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   PRJ_PRJDATA_TMP");
            sql.addSql(" where ");
            sql.addSql("   PRT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(prtSid);

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
     * <br>[機  能] プロジェクトテンプレートリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param prtKbn プロジェクト区分
     * @param usrSid ユーザSID
     * @return ret テンプレート一覧リスト
     * @throws SQLException SQL実行例外
     */
    public ArrayList<PrjPrjdataTmpModel> selectTemlateList(int prtKbn, int usrSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        ArrayList<PrjPrjdataTmpModel> ret = new ArrayList<PrjPrjdataTmpModel>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   PRT_SID,");
            sql.addSql("   PRT_KBN,");
            sql.addSql("   PRT_TMP_NAME,");
            sql.addSql("   PRT_USR_SID,");
            sql.addSql("   PRT_ID,");
            sql.addSql("   PRT_NAME,");
            sql.addSql("   PRT_NAME_SHORT,");
            sql.addSql("   PRT_YOSAN,");
            sql.addSql("   PRT_KOUKAI_KBN,");
            sql.addSql("   PRT_DATE_START,");
            sql.addSql("   PRT_DATE_END,");
            sql.addSql("   PRT_STATUS_SID,");
            sql.addSql("   PRT_TARGET,");
            sql.addSql("   PRT_CONTENT,");
            sql.addSql("   PRT_EDIT,");
            sql.addSql("   PRT_AUID,");
            sql.addSql("   PRT_ADATE,");
            sql.addSql("   PRT_EUID,");
            sql.addSql("   PRT_EDATE");
            sql.addSql(" from ");
            sql.addSql("   PRJ_PRJDATA_TMP");
            sql.addSql(" where ");
            sql.addSql("   PRT_KBN = ?");

            sql.addIntValue(prtKbn);

            if (prtKbn == GSConstProject.MODE_TMP_KOJIN) {
                sql.addSql(" and ");
                sql.addSql("   PRT_USR_SID = ?");

                sql.addIntValue(usrSid);
            }

            sql.addSql(" order by ");
            sql.addSql("   PRT_TMP_NAME,");
            sql.addSql("   PRT_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getPrjPrjdataTmpFromRs(rs));
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
     * <br>[機  能] プロジェクトテンプレート情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param prtSid PRT_SID
     * @return ProjectItem Model
     * @throws SQLException SQL実行例外
     */
    public ProjectItemModel getProjectTmpInfo(int prtSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ProjectItemModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PRJ_PRJDATA_TMP.PRT_TMP_NAME,");
            sql.addSql("   PRJ_PRJDATA_TMP.PRT_ID,");
            sql.addSql("   PRJ_PRJDATA_TMP.PRT_NAME,");
            sql.addSql("   PRJ_PRJDATA_TMP.PRT_NAME_SHORT,");
            sql.addSql("   PRJ_PRJDATA_TMP.PRT_YOSAN,");
            sql.addSql("   PRJ_PRJDATA_TMP.PRT_KOUKAI_KBN,");
            sql.addSql("   PRJ_PRJDATA_TMP.PRT_DATE_START,");
            sql.addSql("   PRJ_PRJDATA_TMP.PRT_DATE_END,");
            sql.addSql("   PRJ_PRJDATA_TMP.PRT_STATUS_SID,");
            sql.addSql("   PRJ_PRJDATA_TMP.PRT_TARGET,");
            sql.addSql("   PRJ_PRJDATA_TMP.PRT_CONTENT,");
            sql.addSql("   PRJ_PRJDATA_TMP.PRT_EDIT,");
            sql.addSql("   PRJ_PRJDATA_TMP.PRT_MAIL_KBN,");
            sql.addSql("   PRJ_PRJSTATUS_TMP.PTT_NAME,");
            sql.addSql("   PRJ_PRJSTATUS_TMP.PTT_RATE");
            sql.addSql(" from");
            sql.addSql("   PRJ_PRJDATA_TMP,");
            sql.addSql("   PRJ_PRJSTATUS_TMP");
            sql.addSql(" where ");
            sql.addSql("   PRJ_PRJDATA_TMP.PRT_SID = PRJ_PRJSTATUS_TMP.PRT_SID");
            sql.addSql(" and");
            sql.addSql("   PRJ_PRJDATA_TMP.PRT_STATUS_SID = PRJ_PRJSTATUS_TMP.PTT_SID");
            sql.addSql(" and");
            sql.addSql("   PRJ_PRJDATA_TMP.PRT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(prtSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = new ProjectItemModel();
                ret.setPrtTmpName(rs.getString("PRT_TMP_NAME"));
                ret.setProjectId(rs.getString("PRT_ID"));
                ret.setProjectName(rs.getString("PRT_NAME"));
                ret.setProjectRyakuName(rs.getString("PRT_NAME_SHORT"));
                ret.setYosan(rs.getInt("PRT_YOSAN"));
                ret.setKoukaiKbn(rs.getInt("PRT_KOUKAI_KBN"));
                ret.setStartDate(UDate.getInstanceTimestamp(rs.getTimestamp("PRT_DATE_START")));
                ret.setEndDate(UDate.getInstanceTimestamp(rs.getTimestamp("PRT_DATE_END")));
                ret.setStatus(rs.getInt("PRT_STATUS_SID"));
                ret.setMokuhyou(rs.getString("PRT_TARGET"));
                ret.setNaiyou(rs.getString("PRT_CONTENT"));
                ret.setEditKengen(rs.getInt("PRT_EDIT"));
                ret.setPrjMailKbn(rs.getInt("PRT_MAIL_KBN"));
                ret.setStatusName(rs.getString("PTT_NAME"));
                ret.setRate(rs.getInt("PTT_RATE"));
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
     * <br>[機  能] プロジェクトテンプレート情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param prtSid テンプレートSID
     * @return PRJ_PRJDATA_TMPModel
     * @throws SQLException SQL実行例外
     */
    public PrjPrjdataTmpModel getPrjTmpMdl(int prtSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        PrjPrjdataTmpModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PRT_SID,");
            sql.addSql("   PRT_KBN,");
            sql.addSql("   PRT_TMP_NAME,");
            sql.addSql("   PRT_USR_SID,");
            sql.addSql("   PRT_ID,");
            sql.addSql("   PRT_NAME,");
            sql.addSql("   PRT_NAME_SHORT,");
            sql.addSql("   PRT_YOSAN,");
            sql.addSql("   PRT_KOUKAI_KBN,");
            sql.addSql("   PRT_DATE_START,");
            sql.addSql("   PRT_DATE_END,");
            sql.addSql("   PRT_STATUS_SID,");
            sql.addSql("   PRT_TARGET,");
            sql.addSql("   PRT_CONTENT,");
            sql.addSql("   PRT_EDIT,");
            sql.addSql("   PRT_AUID,");
            sql.addSql("   PRT_ADATE,");
            sql.addSql("   PRT_EUID,");
            sql.addSql("   PRT_EDATE");
            sql.addSql(" from");
            sql.addSql("   PRJ_PRJDATA_TMP");
            sql.addSql(" where ");
            sql.addSql("   PRT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(prtSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getPrjPrjdataTmpFromRs(rs);
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
     * <p>Create PRJ_PRJDATA_TMP Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created PrjPrjdataTmpModel
     * @throws SQLException SQL実行例外
     */
    private PrjPrjdataTmpModel __getPrjPrjdataTmpFromRs(ResultSet rs) throws SQLException {
        PrjPrjdataTmpModel bean = new PrjPrjdataTmpModel();
        bean.setPrtSid(rs.getInt("PRT_SID"));
        bean.setPrtKbn(rs.getInt("PRT_KBN"));
        bean.setPrtTmpName(rs.getString("PRT_TMP_NAME"));
        bean.setPrtUsrSid(rs.getInt("PRT_USR_SID"));
        bean.setPrtId(rs.getString("PRT_ID"));
        bean.setPrtName(rs.getString("PRT_NAME"));
        bean.setPrtNameShort(rs.getString("PRT_NAME_SHORT"));
        bean.setPrtYosan(rs.getInt("PRT_YOSAN"));
        bean.setPrtKoukaiKbn(rs.getInt("PRT_KOUKAI_KBN"));
        bean.setPrtDateStart(UDate.getInstanceTimestamp(rs.getTimestamp("PRT_DATE_START")));
        bean.setPrtDateEnd(UDate.getInstanceTimestamp(rs.getTimestamp("PRT_DATE_END")));
        bean.setPrtStatusSid(rs.getInt("PRT_STATUS_SID"));
        bean.setPrtTarget(rs.getString("PRT_TARGET"));
        bean.setPrtContent(rs.getString("PRT_CONTENT"));
        bean.setPrtEdit(rs.getInt("PRT_EDIT"));
        bean.setPrtAuid(rs.getInt("PRT_AUID"));
        bean.setPrtAdate(UDate.getInstanceTimestamp(rs.getTimestamp("PRT_ADATE")));
        bean.setPrtEuid(rs.getInt("PRT_EUID"));
        bean.setPrtEdate(UDate.getInstanceTimestamp(rs.getTimestamp("PRT_EDATE")));
        return bean;
    }
}