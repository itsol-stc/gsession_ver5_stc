package jp.groupsession.v2.rsv.dao;

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
import jp.groupsession.v2.rsv.model.RsvExdataPubModel;

/**
 * <p>RSV_EXDATA_PUB Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class RsvExdataPubDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RsvExdataPubDao.class);

    /**
     * <p>Default Constructor
     */
    public RsvExdataPubDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public RsvExdataPubDao(Connection con) {
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
            sql.addSql("drop table RSV_EXDATA_PUB");

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
            sql.addSql(" create table RSV_EXDATA_PUB (");
            sql.addSql("   RSR_RSID integer not null,");
            sql.addSql("   REP_TYPE integer not null,");
            sql.addSql("   REP_PSID integer not null,");
            sql.addSql("   primary key (RSR_RSID,REP_TYPE,REP_PSID)");
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
     * <p>Insert RSV_EXDATA_PUB Data Bindding JavaBean
     * @param bean RSV_EXDATA_PUB Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(RsvExdataPubModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" RSV_EXDATA_PUB(");
            sql.addSql("   RSR_RSID,");
            sql.addSql("   REP_TYPE,");
            sql.addSql("   REP_PSID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRsrRsid());
            sql.addIntValue(bean.getRepType());
            sql.addIntValue(bean.getRepPsid());
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
     * <p>Update RSV_EXDATA_PUB Data Bindding JavaBean
     * @param bean RSV_EXDATA_PUB Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(RsvExdataPubModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RSV_EXDATA_PUB");
            sql.addSql(" set ");
            sql.addSql(" where ");
            sql.addSql("   RSR_RSID=?");
            sql.addSql(" and");
            sql.addSql("   REP_TYPE=?");
            sql.addSql(" and");
            sql.addSql("   REP_PSID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            //where
            sql.addIntValue(bean.getRsrRsid());
            sql.addIntValue(bean.getRepType());
            sql.addIntValue(bean.getRepPsid());

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
     * <p>Select RSV_EXDATA_PUB All Data
     * @return List in RSV_EXDATA_PUBModel
     * @throws SQLException SQL実行例外
     */
    public List<RsvExdataPubModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RsvExdataPubModel> ret = new ArrayList<RsvExdataPubModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RSR_RSID,");
            sql.addSql("   REP_TYPE,");
            sql.addSql("   REP_PSID");
            sql.addSql(" from ");
            sql.addSql("   RSV_EXDATA_PUB");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRsvExdataPubFromRs(rs));
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
     * <p>Select RSV_EXDATA_PUB
     * @param rsrRsid RSR_RSID
     * @param repType REP_TYPE
     * @param repPsid REP_PSID
     * @return RSV_EXDATA_PUBModel
     * @throws SQLException SQL実行例外
     */
    public RsvExdataPubModel select(int rsrRsid, int repType, int repPsid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        RsvExdataPubModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSR_RSID,");
            sql.addSql("   REP_TYPE,");
            sql.addSql("   REP_PSID");
            sql.addSql(" from");
            sql.addSql("   RSV_EXDATA_PUB");
            sql.addSql(" where ");
            sql.addSql("   RSR_RSID=?");
            sql.addSql(" and");
            sql.addSql("   REP_TYPE=?");
            sql.addSql(" and");
            sql.addSql("   REP_PSID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rsrRsid);
            sql.addIntValue(repType);
            sql.addIntValue(repPsid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getRsvExdataPubFromRs(rs);
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
     * <p>Delete RSV_EXDATA_PUB
     * @param rsrRsid RSR_RSID
     * @param repType REP_TYPE
     * @param repPsid REP_PSID
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int rsrRsid, int repType, int repPsid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RSV_EXDATA_PUB");
            sql.addSql(" where ");
            sql.addSql("   RSR_RSID=?");
            sql.addSql(" and");
            sql.addSql("   REP_TYPE=?");
            sql.addSql(" and");
            sql.addSql("   REP_PSID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rsrRsid);
            sql.addIntValue(repType);
            sql.addIntValue(repPsid);

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
     * <br>[機  能] 指定された拡張予約SIDのデータを削除
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param rsrRsidList 拡張予約SID配列
     * @throws SQLException 例外
     */
    public void delete(ArrayList<Integer> rsrRsidList) throws SQLException {
        if (rsrRsidList == null || rsrRsidList.size() <= 0) {
            return;
        }
        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RSV_EXDATA_PUB");
            sql.addSql(" where ");
            sql.addSql("   RSR_RSID in (");

            for (int i = 0; i < rsrRsidList.size(); i++) {

                sql.addSql("?");
                sql.addIntValue(rsrRsidList.get(i));

                if (i != rsrRsidList.size() - 1) {
                    sql.addSql(", ");
                }
            }

            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());

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
     * <br>[機  能] 指定された拡張予約SIDのデータを削除
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param rsrRsid 拡張予約SID
     * @throws SQLException 例外
     */
    public void delete(int rsrRsid) throws SQLException {
        if (rsrRsid <= 0) {
            return;
        }

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RSV_EXDATA_PUB");
            sql.addSql(" where ");
            sql.addSql("   RSR_RSID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rsrRsid);

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
     * <p>Create RSV_EXDATA_PUB Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created RsvExdataPubModel
     * @throws SQLException SQL実行例外
     */
    private RsvExdataPubModel __getRsvExdataPubFromRs(ResultSet rs) throws SQLException {
        RsvExdataPubModel bean = new RsvExdataPubModel();
        bean.setRsrRsid(rs.getInt("RSR_RSID"));
        bean.setRepType(rs.getInt("REP_TYPE"));
        bean.setRepPsid(rs.getInt("REP_PSID"));
        return bean;
    }
}
