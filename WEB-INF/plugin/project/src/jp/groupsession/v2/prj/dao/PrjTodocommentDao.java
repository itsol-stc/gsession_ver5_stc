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
import jp.groupsession.v2.prj.model.PrjTodocommentModel;
import jp.groupsession.v2.prj.model.TodocommentModel;

/**
 * <p>PRJ_TODOCOMMENT Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class PrjTodocommentDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(PrjTodocommentDao.class);

    /**
     * <p>Default Constructor
     */
    public PrjTodocommentDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public PrjTodocommentDao(Connection con) {
        super(con);
    }

    /**
     * <p>Insert PRJ_TODOCOMMENT Data Bindding JavaBean
     * @param bean PRJ_TODOCOMMENT Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(PrjTodocommentModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" PRJ_TODOCOMMENT(");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   PTD_SID,");
            sql.addSql("   PCM_SID,");
            sql.addSql("   PCM_COMMENT,");
            sql.addSql("   PCM_AUID,");
            sql.addSql("   PCM_ADATE,");
            sql.addSql("   PCM_EUID,");
            sql.addSql("   PCM_EDATE");
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

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getPrjSid());
            sql.addIntValue(bean.getPtdSid());
            sql.addIntValue(bean.getPcmSid());
            sql.addStrValue(bean.getPcmComment());
            sql.addIntValue(bean.getPcmAuid());
            sql.addDateValue(bean.getPcmAdate());
            sql.addIntValue(bean.getPcmEuid());
            sql.addDateValue(bean.getPcmEdate());
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
     * <p>Update PRJ_TODOCOMMENT Data Bindding JavaBean
     * @param bean PRJ_TODOCOMMENT Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(PrjTodocommentModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   PRJ_TODOCOMMENT");
            sql.addSql(" set ");
            sql.addSql("   PCM_COMMENT=?,");
            sql.addSql("   PCM_AUID=?,");
            sql.addSql("   PCM_ADATE=?,");
            sql.addSql("   PCM_EUID=?,");
            sql.addSql("   PCM_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");
            sql.addSql(" and");
            sql.addSql("   PTD_SID=?");
            sql.addSql(" and");
            sql.addSql("   PCM_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getPcmComment());
            sql.addIntValue(bean.getPcmAuid());
            sql.addDateValue(bean.getPcmAdate());
            sql.addIntValue(bean.getPcmEuid());
            sql.addDateValue(bean.getPcmEdate());
            //where
            sql.addIntValue(bean.getPrjSid());
            sql.addIntValue(bean.getPtdSid());
            sql.addIntValue(bean.getPcmSid());

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
     * <p>Select PRJ_TODOCOMMENT All Data
     * @return List in PRJ_TODOCOMMENTModel
     * @throws SQLException SQL実行例外
     */
    public List<PrjTodocommentModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<PrjTodocommentModel> ret = new ArrayList<PrjTodocommentModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   PTD_SID,");
            sql.addSql("   PCM_SID,");
            sql.addSql("   PCM_COMMENT,");
            sql.addSql("   PCM_AUID,");
            sql.addSql("   PCM_ADATE,");
            sql.addSql("   PCM_EUID,");
            sql.addSql("   PCM_EDATE");
            sql.addSql(" from ");
            sql.addSql("   PRJ_TODOCOMMENT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getPrjTodocommentFromRs(rs));
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
     * <br>[機  能] TODOコメントリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param prjSid プロジェクトSID
     * @param todoSid TODOSID
     * @param usrSid セッションユーザSID
     * @return List in TodocommentModel
     * @throws SQLException SQL実行例外
     */
    public List<TodocommentModel> getTodoCommentList(int prjSid, int todoSid, int usrSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<TodocommentModel> ret = new ArrayList<TodocommentModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PRJ_TODOCOMMENT.PCM_SID,");
            sql.addSql("   PRJ_TODOCOMMENT.PCM_ADATE,");
            sql.addSql("   PRJ_TODOCOMMENT.PCM_COMMENT,");
            sql.addSql("   PRJ_TODOCOMMENT.PCM_AUID,");
            sql.addSql("   CMN_USRM_INF.USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI,");
            sql.addSql("   CMN_USRM.USR_JKBN,");
            sql.addSql("   CMN_USRM.USR_UKO_FLG");
            sql.addSql(" from");
            sql.addSql("   PRJ_TODOCOMMENT,");
            sql.addSql("   CMN_USRM_INF,");
            sql.addSql("   CMN_USRM");
            sql.addSql(" where");
            sql.addSql("   PRJ_TODOCOMMENT.PCM_AUID = CMN_USRM_INF.USR_SID");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM_INF.USR_SID = CMN_USRM.USR_SID");
            sql.addSql(" and");
            sql.addSql("   PRJ_TODOCOMMENT.PRJ_SID = ?");
            sql.addSql(" and");
            sql.addSql("   PRJ_TODOCOMMENT.PTD_SID = ?");
            sql.addSql(" order by");
            sql.addSql("   PRJ_TODOCOMMENT.PCM_SID asc");
            sql.addIntValue(prjSid);
            sql.addIntValue(todoSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                TodocommentModel bean = new TodocommentModel();
                bean.setPcmSid(rs.getInt("PCM_SID"));
                bean.setPcmAdate(UDate.getInstanceTimestamp(rs.getTimestamp("PCM_ADATE")));
                bean.setPcmComment(rs.getString("PCM_COMMENT"));
                bean.setSei(rs.getString("USI_SEI"));
                bean.setMei(rs.getString("USI_MEI"));
                bean.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
                bean.setStatus(rs.getInt("USR_JKBN"));
                bean.setDeleteKbn(rs.getInt("PCM_AUID") == usrSid);

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
     * <p>Select PRJ_TODOCOMMENT
     * @param prjSid PRJ_SID
     * @param ptdSid PTD_SID
     * @param pcmSid PCM_SID
     * @return PRJ_TODOCOMMENTModel
     * @throws SQLException SQL実行例外
     */
    public PrjTodocommentModel select(int prjSid, int ptdSid, int pcmSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        PrjTodocommentModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   PTD_SID,");
            sql.addSql("   PCM_SID,");
            sql.addSql("   PCM_COMMENT,");
            sql.addSql("   PCM_AUID,");
            sql.addSql("   PCM_ADATE,");
            sql.addSql("   PCM_EUID,");
            sql.addSql("   PCM_EDATE");
            sql.addSql(" from");
            sql.addSql("   PRJ_TODOCOMMENT");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");
            sql.addSql(" and");
            sql.addSql("   PTD_SID=?");
            sql.addSql(" and");
            sql.addSql("   PCM_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(prjSid);
            sql.addIntValue(ptdSid);
            sql.addIntValue(pcmSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getPrjTodocommentFromRs(rs);
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
     * <br>[機  能] TODOコメントの件数をカウントする
     * <br>[解  説]
     * <br>[備  考]
     * @param ptdSid PTD_SID
     * @return int TODOコメントの件数
     * @throws SQLException SQL実行例外
     */
    public int getCmtCount(String[] ptdSid) throws SQLException {

        if (ptdSid == null || ptdSid.length == 0) {
            return 0;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(PRJ_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   PRJ_TODOCOMMENT");
            sql.addSql(" where ");

            if (ptdSid.length == 0) {
                sql.addSql("   PTD_SID=?");
                sql.addIntValue(Integer.parseInt(ptdSid[0]));
            } else {
                sql.addSql("   PTD_SID in (");
                sql.addSql("     ?");
                sql.addIntValue(Integer.parseInt(ptdSid[0]));

                for (int idx = 1; idx < ptdSid.length; idx++) {
                    sql.addSql("     ,?");
                    sql.addIntValue(Integer.parseInt(ptdSid[idx]));
                }
                sql.addSql("   )");
            }

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
     * <br>[機  能] TODOコメントのデータサイズ合計を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param ptdSidList PTD_SID
     * @return TODOコメントのデータサイズ合計
     * @throws SQLException SQL実行例外
     */
    public long getCmtDataSizeInTodo(List<Integer> ptdSidList) throws SQLException {

        if (ptdSidList == null || ptdSidList.isEmpty()) {
            return 0;
        }

        long dataSize = 0;

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   sum(octet_length(PCM_COMMENT)) as COMMENT_SIZE");
            sql.addSql(" from");
            sql.addSql("   PRJ_TODOCOMMENT");
            sql.addSql(" where ");

            sql.addSql("   PTD_SID in (");
            sql.addSql("     ?");
            sql.addIntValue(ptdSidList.get(0));

            for (int idx = 1; idx < ptdSidList.size(); idx++) {
                sql.addSql("     ,?");
                sql.addIntValue(ptdSidList.get(idx));
            }
            sql.addSql("   )");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                dataSize = rs.getLong("COMMENT_SIZE");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return dataSize;
    }

    /**
     * <br>[機  能] TODOコメントのデータサイズ合計を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param ptdSid PTD_SID
     * @param pcmSidList コメントSID
     * @return TODOコメントのデータサイズ合計
     * @throws SQLException SQL実行例外
     */
    public long getCmtDataSize(int ptdSid, List<Integer> pcmSidList) throws SQLException {

        if (pcmSidList == null || pcmSidList.isEmpty()) {
            return 0;
        }

        long dataSize = 0;

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   sum(octet_length(PCM_COMMENT)) as COMMENT_SIZE");
            sql.addSql(" from");
            sql.addSql("   PRJ_TODOCOMMENT");
            sql.addSql(" where ");
            sql.addSql("   PTD_SID = ?");
            sql.addIntValue(ptdSid);

            sql.addSql(" and ");
            sql.addSql("   PCM_SID in (");
            sql.addSql("     ?");
            sql.addIntValue(pcmSidList.get(0));

            for (int idx = 1; idx < pcmSidList.size(); idx++) {
                sql.addSql("     ,?");
                sql.addIntValue(pcmSidList.get(idx));
            }
            sql.addSql("   )");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                dataSize = rs.getLong("COMMENT_SIZE");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return dataSize;
    }

    /**
     * <p>Delete PRJ_TODOCOMMENT
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
            sql.addSql("   PRJ_TODOCOMMENT");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");
            sql.addIntValue(prjSid);

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
     * <br>[機  能] TODOSIDを指定してTODOコメント情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param todoSid TODOSID
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int deleteFromTodoSid(int todoSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   PRJ_TODOCOMMENT");
            sql.addSql(" where ");
            sql.addSql("   PTD_SID=?");
            sql.addIntValue(todoSid);

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
     * <br>[機  能] TODOコメント情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param prjSid プロジェクトSID
     * @param todoSid TODOSID
     * @param cmtSid コメントSID
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int deleteCommemt(int prjSid, int todoSid, int cmtSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   PRJ_TODOCOMMENT");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");
            sql.addSql(" and ");
            sql.addSql("   PTD_SID=?");
            sql.addSql(" and ");
            sql.addSql("   PCM_SID=?");
            sql.addIntValue(prjSid);
            sql.addIntValue(todoSid);
            sql.addIntValue(cmtSid);

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
     * <p>Create PRJ_TODOCOMMENT Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created PrjTodocommentModel
     * @throws SQLException SQL実行例外
     */
    private PrjTodocommentModel __getPrjTodocommentFromRs(ResultSet rs) throws SQLException {
        PrjTodocommentModel bean = new PrjTodocommentModel();
        bean.setPrjSid(rs.getInt("PRJ_SID"));
        bean.setPtdSid(rs.getInt("PTD_SID"));
        bean.setPcmSid(rs.getInt("PCM_SID"));
        bean.setPcmComment(rs.getString("PCM_COMMENT"));
        bean.setPcmAuid(rs.getInt("PCM_AUID"));
        bean.setPcmAdate(UDate.getInstanceTimestamp(rs.getTimestamp("PCM_ADATE")));
        bean.setPcmEuid(rs.getInt("PCM_EUID"));
        bean.setPcmEdate(UDate.getInstanceTimestamp(rs.getTimestamp("PCM_EDATE")));
        return bean;
    }
}
