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
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.prj.model.PrjTodoBinModel;

/**
 * <p>PRJ_TODO_BIN Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class PrjTodoBinDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(PrjTodoBinDao.class);

    /**
     * <p>Default Constructor
     */
    public PrjTodoBinDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public PrjTodoBinDao(Connection con) {
        super(con);
    }

    /**
     * <p>Insert PRJ_TODO_BIN Data Bindding JavaBean
     * @param bean PRJ_TODO_BIN Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(PrjTodoBinModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
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

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getPrjSid());
            sql.addIntValue(bean.getPtdSid());
            sql.addLongValue(bean.getBinSid());
            sql.addIntValue(bean.getUsrSid());
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
     * <p>Update PRJ_TODO_BIN Data Bindding JavaBean
     * @param bean PRJ_TODO_BIN Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(PrjTodoBinModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   PRJ_TODO_BIN");
            sql.addSql(" set ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");
            sql.addSql(" and");
            sql.addSql("   PTD_SID=?");
            sql.addSql(" and");
            sql.addSql("   BIN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUsrSid());
            //where
            sql.addIntValue(bean.getPrjSid());
            sql.addIntValue(bean.getPtdSid());
            sql.addLongValue(bean.getBinSid());

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
     * <br>[機  能] 添付ファイル情報リストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param todoSid TODOSID
     * @return List in PRJ_TODO_BINModel
     * @throws SQLException SQL実行例外
     */
    public List<PrjTodoBinModel> getBinList(String[] todoSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<PrjTodoBinModel> ret = new ArrayList<PrjTodoBinModel>();
        con = getCon();

        if (todoSid == null) {
            return ret;
        }
        if (todoSid.length < 1) {
            return ret;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   PTD_SID,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   USR_SID");
            sql.addSql(" from ");
            sql.addSql("   PRJ_TODO_BIN");
            sql.addSql(" where ");
            sql.addSql("   PTD_SID in ( ");
            for (int i = 0; i < todoSid.length; i++) {
                if (i > 0) {
                    sql.addSql("     , ");
                }
                sql.addSql("     ?");
                sql.addIntValue(Integer.parseInt(todoSid[i]));
            }
            sql.addSql("        )");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getPrjTodoBinFromRs(rs));
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
     * <br>[機  能] 添付ファイル情報リストを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param prjSid プロジェクトSID
     * @return List in PRJ_TODO_BINModel
     * @throws SQLException SQL実行例外
     */
    public List<PrjTodoBinModel> getBinList(int prjSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<PrjTodoBinModel> ret = new ArrayList<PrjTodoBinModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   PTD_SID,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   USR_SID");
            sql.addSql(" from ");
            sql.addSql("   PRJ_TODO_BIN");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(prjSid);
            sql.setParameter(pstmt);

            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getPrjTodoBinFromRs(rs));
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
     * <br>[機  能] 添付ファイル情報リストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param prjSid プロジェクトSID
     * @param todoSid TODOSID
     * @return List in PRJ_TODO_BINModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnBinfModel> getBinList(int prjSid, int todoSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<CmnBinfModel> ret = new ArrayList<CmnBinfModel>();
        CommonBiz cmnBiz = new CommonBiz();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CMN_BINF.BIN_SID,");
            sql.addSql("   CMN_BINF.BIN_FILE_NAME,");
            sql.addSql("   CMN_BINF.BIN_FILE_PATH,");
            sql.addSql("   CMN_BINF.BIN_FILE_SIZE");
            sql.addSql(" from");
            sql.addSql("   PRJ_TODO_BIN,");
            sql.addSql("   CMN_BINF");
            sql.addSql(" where");
            sql.addSql("   PRJ_TODO_BIN.BIN_SID = CMN_BINF.BIN_SID");
            sql.addSql(" and");
            sql.addSql("   PRJ_TODO_BIN.PRJ_SID = ?");
            sql.addSql(" and");
            sql.addSql("   PRJ_TODO_BIN.PTD_SID = ?");
            sql.addSql(" and");
            sql.addSql("   CMN_BINF.BIN_JKBN = ?");
            sql.addSql(" order by");
            sql.addSql("   CMN_BINF.BIN_SID asc");
            sql.addIntValue(prjSid);
            sql.addIntValue(todoSid);
            sql.addIntValue(GSConst.JTKBN_TOROKU);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CmnBinfModel bean = new CmnBinfModel();
                bean.setBinSid(rs.getLong("BIN_SID"));
                bean.setBinFileName(rs.getString("BIN_FILE_NAME"));
                bean.setBinFilePath(rs.getString("BIN_FILE_PATH"));
                long size = rs.getInt("BIN_FILE_SIZE");
                String strSize = cmnBiz.getByteSizeString(size);
                bean.setBinFileSizeDsp(strSize);
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
     * <br>[機  能] バイナリSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param prjSid プロジェクトSID
     * @param todoSid TODOSID
     * @return バイナリSID
     * @throws SQLException SQL実行例外
     */
    public String[] getBinSids(int prjSid, int todoSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        String[] ret = null;
        List<String> binList = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PRJ_TODO_BIN.BIN_SID");
            sql.addSql(" from");
            sql.addSql("   PRJ_TODO_BIN");
            sql.addSql(" where");
            sql.addSql("   PRJ_TODO_BIN.PRJ_SID = ?");
            sql.addSql(" and");
            sql.addSql("   PRJ_TODO_BIN.PTD_SID = ?");
            sql.addSql(" order by");
            sql.addSql("   PRJ_TODO_BIN.BIN_SID asc");
            sql.addIntValue(prjSid);
            sql.addIntValue(todoSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                binList.add(String.valueOf(rs.getLong("BIN_SID")));
            }
            ret = binList.toArray(new String[binList.size()]);
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }

    /**
     * <br>[機  能] 添付ファイル情報リストを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return List in PRJ_TODO_BINModel
     * @throws SQLException SQL実行例外
     */
    public List<PrjTodoBinModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<PrjTodoBinModel> ret = new ArrayList<PrjTodoBinModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   PTD_SID,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   USR_SID");
            sql.addSql(" from ");
            sql.addSql("   PRJ_TODO_BIN");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getPrjTodoBinFromRs(rs));
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
     * <p>Select PRJ_TODO_BIN
     * @param prjSid PRJ_SID
     * @param ptdSid PTD_SID
     * @param binSid BIN_SID
     * @return PRJ_TODO_BINModel
     * @throws SQLException SQL実行例外
     */
    public PrjTodoBinModel select(int prjSid, int ptdSid, Long binSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        PrjTodoBinModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PRJ_SID,");
            sql.addSql("   PTD_SID,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   USR_SID");
            sql.addSql(" from");
            sql.addSql("   PRJ_TODO_BIN");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");
            sql.addSql(" and");
            sql.addSql("   PTD_SID=?");
            sql.addSql(" and");
            sql.addSql("   BIN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(prjSid);
            sql.addIntValue(ptdSid);
            sql.addLongValue(binSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getPrjTodoBinFromRs(rs);
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
     * <p>バイナリSIDがTODOの添付ファイルのものであるかチェックする
     * @param prjSid プロジェクトSID
     * @param ptdSid TODOSID
     * @param binSid バイナリSID
     * @return true or false
     * @throws SQLException SQL実行例外
     */
    public boolean isCheckPrjTemp(int prjSid, int ptdSid, Long binSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int cnt = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from");
            sql.addSql("   PRJ_TODO_BIN");
            sql.addSql(" where ");
            sql.addSql("   PRJ_SID=?");
            sql.addSql(" and");
            sql.addSql("   PTD_SID=?");
            sql.addSql(" and");
            sql.addSql("   BIN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(prjSid);
            sql.addIntValue(ptdSid);
            sql.addLongValue(binSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                cnt = rs.getInt("CNT");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return cnt > 0;
    }

    /**
     * <br>[機  能] 指定したTODO情報の添付ファイルサイズ合計を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param todoSidList TODOSID
     * @return ファイルサイズ合計
     * @throws SQLException SQL実行例外
     */
    public long getSumFileSize(List<Integer> todoSidList) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        long fileSize = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   sum(CMN_BINF.BIN_FILE_SIZE) as FILE_SIZE");
            sql.addSql(" from");
            sql.addSql("   PRJ_TODO_BIN,");
            sql.addSql("   CMN_BINF");
            sql.addSql(" where");
            sql.addSql("   PRJ_TODO_BIN.BIN_SID = CMN_BINF.BIN_SID");
            sql.addSql(" and");
            sql.addSql("   PRJ_TODO_BIN.PTD_SID in (");

            for (int idx = 0; idx < todoSidList.size(); idx++) {
                if (idx == 0) {
                    sql.addSql("     ?");
                } else {
                    sql.addSql("     ,?");
                }
                sql.addIntValue(todoSidList.get(idx));
            }

            sql.addSql("  )");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                fileSize = rs.getLong("FILE_SIZE");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return fileSize;
    }

    /**
     * <p>Delete PRJ_TODO_BIN
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
            sql.addSql("   PRJ_TODO_BIN");
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
     * <br>[機  能] TODOSIDを指定してTODO添付情報を削除する
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
            sql.addSql("   PRJ_TODO_BIN");
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
     * <p>Create PRJ_TODO_BIN Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created PrjTodoBinModel
     * @throws SQLException SQL実行例外
     */
    private PrjTodoBinModel __getPrjTodoBinFromRs(ResultSet rs) throws SQLException {
        PrjTodoBinModel bean = new PrjTodoBinModel();
        bean.setPrjSid(rs.getInt("PRJ_SID"));
        bean.setPtdSid(rs.getInt("PTD_SID"));
        bean.setBinSid(rs.getLong("BIN_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        return bean;
    }
}
