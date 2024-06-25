package jp.groupsession.v2.rsv.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.rsv.model.RsvAccessConfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>RSV_ACCESS_CONF Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class RsvAccessConfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RsvAccessConfDao.class);

    /**
     * <p>Default Constructor
     */
    public RsvAccessConfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public RsvAccessConfDao(Connection con) {
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
            sql.addSql("drop table RSV_ACCESS_CONF");

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
            sql.addSql(" create table RSV_ACCESS_CONF (");
            sql.addSql("   RSG_SID NUMBER(10,0) not null,");
            sql.addSql("   RSD_SID NUMBER(10,0) not null,");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   GRP_SID NUMBER(10,0) not null,");
            sql.addSql("   RAC_AUTH NUMBER(10,0) not null");
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
     * <p>Insert RSV_ACCESS_CONF Data Bindding JavaBean
     * @param bean RSV_ACCESS_CONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(RsvAccessConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" RSV_ACCESS_CONF(");
            sql.addSql("   RSG_SID,");
            sql.addSql("   RSD_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   RAC_AUTH");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRsgSid());
            sql.addIntValue(bean.getRsdSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getGrpSid());
            sql.addIntValue(bean.getRacAuth());
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
     * <p>Update RSV_ACCESS_CONF Data Bindding JavaBean
     * @param bean RSV_ACCESS_CONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(RsvAccessConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RSV_ACCESS_CONF");
            sql.addSql(" set ");
            sql.addSql("   RSG_SID=?,");
            sql.addSql("   RSD_SID=?,");
            sql.addSql("   USR_SID=?,");
            sql.addSql("   GRP_SID=?,");
            sql.addSql("   RAC_AUTH=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRsgSid());
            sql.addIntValue(bean.getRsdSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getGrpSid());
            sql.addIntValue(bean.getRacAuth());

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
     * <p>Select RSV_ACCESS_CONF All Data
     * @return List in RSV_ACCESS_CONFModel
     * @throws SQLException SQL実行例外
     */
    public List<RsvAccessConfModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RsvAccessConfModel> ret = new ArrayList<RsvAccessConfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RSG_SID,");
            sql.addSql("   RSD_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   RAC_AUTH");
            sql.addSql(" from ");
            sql.addSql("   RSV_ACCESS_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRsvAcsLmitConfFromRs(rs));
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
     * <br>[機  能] アクセス権限情報を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param rsgSid 施設グループSID 施設権限を取得する時は-1を指定する。
     * @param rsdSid 施設SID グループ権限を取得する時は-1を指定する。
     * @return List in RSV_ACCESS_CONFModel
     * @throws SQLException SQL実行例外
     */
    public List<RsvAccessConfModel> getUsrData(int rsgSid, int rsdSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RsvAccessConfModel> ret = new ArrayList<RsvAccessConfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RSG_SID,");
            sql.addSql("   RSD_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   RAC_AUTH");
            sql.addSql(" from ");
            sql.addSql("   RSV_ACCESS_CONF");
            sql.addSql(" where ");
            if (rsgSid > 0) {
                sql.addSql("   RSG_SID=?");
                sql.addIntValue(rsgSid);
            } else {
                sql.addSql("   RSD_SID=?");
                sql.addIntValue(rsdSid);
            }

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);

            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRsvAcsLmitConfFromRs(rs));
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
     * <p>施設グループのアクセス権限を削除する。
     * @param rsgSid groupSid
     * @return count delete count
     * @throws SQLException SQL実行例外
     */
    public int deleteRsvGrpConf(int rsgSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RSV_ACCESS_CONF");
            sql.addSql(" where ");
            sql.addSql("   RSG_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rsgSid);

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
     * <p>Create RSV_ACCESS_CONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created RsvAcsLimitConfModel
     * @throws SQLException SQL実行例外
     */
    private RsvAccessConfModel __getRsvAcsLmitConfFromRs(ResultSet rs) throws SQLException {
        RsvAccessConfModel bean = new RsvAccessConfModel();
        bean.setRsgSid(rs.getInt("RSG_SID"));
        bean.setRsdSid(rs.getInt("RSD_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setGrpSid(rs.getInt("GRP_SID"));
        bean.setRacAuth(rs.getInt("RAC_AUTH"));
        return bean;
    }
}
