package jp.groupsession.v2.sch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.sch.model.SchExdataPubModel;

/**
 * <p>SCH_EXDATA_PUB Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class SchExdataPubDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SchExdataPubDao.class);

    /**
     * <p>Default Constructor
     */
    public SchExdataPubDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public SchExdataPubDao(Connection con) {
        super(con);
    }

    /**
     * <p>Drop Table
     * @throws SQLException SQL実行例外
     */
    public void dropTable() throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("drop table SCH_EXDATA_PUB");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Create Table
     * @throws SQLException SQL実行例外
     */
    public void createTable() throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" create table SCH_EXDATA_PUB (");
            sql.addSql("   SCE_SID integer not null,");
            sql.addSql("   SEP_TYPE integer not null,");
            sql.addSql("   SEP_PSID integer not null,");
            sql.addSql("   primary key (SCE_SID,SEP_TYPE,SEP_PSID)");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Insert SCH_EXDATA_PUB Data Bindding JavaBean
     * @param bean SCH_EXDATA_PUB Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(SchExdataPubModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" SCH_EXDATA_PUB(");
            sql.addSql("   SCE_SID,");
            sql.addSql("   SEP_TYPE,");
            sql.addSql("   SEP_PSID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSceSid());
            sql.addIntValue(bean.getSepType());
            sql.addIntValue(bean.getSepPsid());
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
     * <p>Insert SCH_DATA_PUB Data Bindding JavaBean
     * @param beanList SCH_DATA DataList
     * @throws SQLException SQL実行例外
     */
    public void insert(List<SchExdataPubModel> beanList) throws SQLException {

        if (beanList == null || beanList.size() <= 0) {
            return;
        }
        List<SchExdataPubModel> exeList = new ArrayList<>();
        Iterator<SchExdataPubModel> itr = beanList.iterator();
        StringBuilder sb = new StringBuilder();
        sb.append(" insert ");
        sb.append(" into ");
        sb.append(" SCH_EXDATA_PUB(");
        sb.append("   SCE_SID,");
        sb.append("   SEP_TYPE,");
        sb.append("   SEP_PSID");
        sb.append(" )");
        sb.append(" values");


        Connection con = null;
        con = getCon();

        while (itr.hasNext()) {
            exeList.add(itr.next());
            if (exeList.size() < 500
                    && itr.hasNext()) {
                continue;
            }

            //500件分インサート
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(sb.toString());

            Iterator<SchExdataPubModel> exeItr = exeList.iterator();
            while (exeItr.hasNext()) {
                SchExdataPubModel bean = exeItr.next();
                sql.addSql(" (");
                sql.addSql("   ?,");
                sql.addSql("   ?,");
                sql.addSql("   ?");
                sql.addSql(" )");

                sql.addIntValue(bean.getSceSid());
                sql.addIntValue(bean.getSepType());
                sql.addIntValue(bean.getSepPsid());

                if (exeItr.hasNext()) {
                    sql.addSql(",");
                }
            }
            try (PreparedStatement pstmt = con.prepareStatement(sql.toSqlString());) {
                sql.setParameter(pstmt);
                log__.info(sql.toLogString());
                pstmt.executeUpdate();

            }
            exeList.clear();
        }
    }
    /**
     * <p>Update SCH_EXDATA_PUB Data Bindding JavaBean
     * @param bean SCH_EXDATA_PUB Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(SchExdataPubModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SCH_EXDATA_PUB");
            sql.addSql(" set ");
            sql.addSql(" where ");
            sql.addSql("   SCE_SID=?");
            sql.addSql(" and");
            sql.addSql("   SEP_TYPE=?");
            sql.addSql(" and");
            sql.addSql("   SEP_PSID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            //where
            sql.addIntValue(bean.getSceSid());
            sql.addIntValue(bean.getSepType());
            sql.addIntValue(bean.getSepPsid());

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
     * <p>Select SCH_EXDATA_PUB All Data
     * @return List in SCH_EXDATA_PUBModel
     * @throws SQLException SQL実行例外
     */
    public List<SchExdataPubModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<SchExdataPubModel> ret = new ArrayList<SchExdataPubModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SCE_SID,");
            sql.addSql("   SEP_TYPE,");
            sql.addSql("   SEP_PSID");
            sql.addSql(" from ");
            sql.addSql("   SCH_EXDATA_PUB");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSchExdataPubFromRs(rs));
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
     * <p>Select SCH_EXDATA_PUB All Data
     * @param sceSid SCE_SID
     * @return List in SCH_EXDATA_PUBModel
     * @throws SQLException SQL実行例外
     */
    public List<SchExdataPubModel> select(int sceSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<SchExdataPubModel> ret = new ArrayList<SchExdataPubModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SCE_SID,");
            sql.addSql("   SEP_TYPE,");
            sql.addSql("   SEP_PSID");
            sql.addSql(" from");
            sql.addSql("   SCH_EXDATA_PUB");
            sql.addSql(" where ");
            sql.addSql("   SCE_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sceSid);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSchExdataPubFromRs(rs));
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
     * <p>Select SCH_EXDATA_PUB
     * @param sceSid SCE_SID
     * @param sepType SEP_TYPE
     * @param sepPsid SEP_PSID
     * @return SCH_EXDATA_PUBModel
     * @throws SQLException SQL実行例外
     */
    public SchExdataPubModel select(int sceSid, int sepType, int sepPsid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        SchExdataPubModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SCE_SID,");
            sql.addSql("   SEP_TYPE,");
            sql.addSql("   SEP_PSID");
            sql.addSql(" from");
            sql.addSql("   SCH_EXDATA_PUB");
            sql.addSql(" where ");
            sql.addSql("   SCE_SID=?");
            sql.addSql(" and");
            sql.addSql("   SEP_TYPE=?");
            sql.addSql(" and");
            sql.addSql("   SEP_PSID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sceSid);
            sql.addIntValue(sepType);
            sql.addIntValue(sepPsid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getSchExdataPubFromRs(rs);
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
     * <br>[機  能] 指定されたスケジュールの公開対象を削除します
     * <br>[解  説]
     * <br>[備  考]
     * @param sceSid スケジュール拡張SID
     * @return 削除件数
     * @throws SQLException SQL実行時例外
     */
    public int delete(int sceSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SCH_EXDATA_PUB");
            sql.addSql(" where ");
            sql.addSql("   SCE_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sceSid);

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
     * <p>Delete SCH_EXDATA_PUB
     * @param sceSid SCE_SID
     * @param sepType SEP_TYPE
     * @param sepPsid SEP_PSID
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int sceSid, int sepType, int sepPsid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SCH_EXDATA_PUB");
            sql.addSql(" where ");
            sql.addSql("   SCE_SID=?");
            sql.addSql(" and");
            sql.addSql("   SEP_TYPE=?");
            sql.addSql(" and");
            sql.addSql("   SEP_PSID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sceSid);
            sql.addIntValue(sepType);
            sql.addIntValue(sepPsid);

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
     * <br>[機  能] 指定されたスケジュールの拡張公開対象情報を削除します
     * <br>[解  説]
     * <br>[備  考]
     * @param scdExSidList スケジュール拡張SIDリスト
     * @throws SQLException SQL実行時例外
     * @return 削除件数
     */
    public int delete(List<Integer> scdExSidList) throws SQLException {

        int count = 0;
        if (scdExSidList == null || scdExSidList.size() < 1) {
            return count;
        }

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SCH_EXDATA_PUB");
            sql.addSql(" where ");
            sql.addSql("   SCE_SID in (");
            for (int idx = 0; idx < scdExSidList.size(); idx++) {
                sql.addSql("     ?");
                sql.addIntValue(scdExSidList.get(idx));
                if (idx != scdExSidList.size() - 1) {
                    sql.addSql("  ,");
                }
            }
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());

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
     * <p>Create SCH_EXDATA_PUB Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created SchExdataPubModel
     * @throws SQLException SQL実行例外
     */
    private SchExdataPubModel __getSchExdataPubFromRs(ResultSet rs) throws SQLException {
        SchExdataPubModel bean = new SchExdataPubModel();
        bean.setSceSid(rs.getInt("SCE_SID"));
        bean.setSepType(rs.getInt("SEP_TYPE"));
        bean.setSepPsid(rs.getInt("SEP_PSID"));
        return bean;
    }
}
