package jp.groupsession.v2.tcd.dao;

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
import jp.groupsession.v2.tcd.model.TcdPriConfModel;

/**
 * <p>TCD_PRI_CONF Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class TcdPriConfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(TcdPriConfDao.class);

    /**
     * <p>Default Constructor
     */
    public TcdPriConfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public TcdPriConfDao(Connection con) {
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
            sql.addSql("drop table TCD_PRI_CONF");

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
            sql.addSql(" create table TCD_PRI_CONF (");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   TPC_IN_HOUR NUMBER(10,0) not null,");
            sql.addSql("   TPC_IN_MIN NUMBER(10,0) not null,");
            sql.addSql("   TPC_OUT_HOUR NUMBER(10,0) not null,");
            sql.addSql("   TPC_OUT_MIN NUMBER(10,0) not null,");
            sql.addSql("   TPC_MAIN_DSP NUMBER(10,0) not null,");
            sql.addSql("   primary key (USR_SID)");
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
     * <p>Insert TCD_PRI_CONF Data Bindding JavaBean
     * @param bean TCD_PRI_CONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(TcdPriConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" TCD_PRI_CONF(");
            sql.addSql("   USR_SID,");
            sql.addSql("   TPC_IN_HOUR,");
            sql.addSql("   TPC_IN_MIN,");
            sql.addSql("   TPC_OUT_HOUR,");
            sql.addSql("   TPC_OUT_MIN,");
            sql.addSql("   TPC_MAIN_DSP,");
            sql.addSql("   TPC_KINMU_OUT,");
            sql.addSql("   TPC_ZSK_STS");
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
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getTpcInHour());
            sql.addIntValue(bean.getTpcInMin());
            sql.addIntValue(bean.getTpcOutHour());
            sql.addIntValue(bean.getTpcOutMin());
            sql.addIntValue(bean.getTpcMainDsp());
            sql.addIntValue(bean.getTpcKinmuOut());
            sql.addIntValue(bean.getTpcZaisekiSts());
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
     * <p>Update TCD_PRI_CONF Data Bindding JavaBean
     * @param bean TCD_PRI_CONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     * @return int
     */
    public int update(TcdPriConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   TCD_PRI_CONF");
            sql.addSql(" set ");
            sql.addSql("   TPC_IN_HOUR=?,");
            sql.addSql("   TPC_IN_MIN=?,");
            sql.addSql("   TPC_OUT_HOUR=?,");
            sql.addSql("   TPC_OUT_MIN=?,");
            sql.addSql("   TPC_MAIN_DSP=?,");
            sql.addSql("   TPC_KINMU_OUT=?,");
            sql.addSql("   TPC_ZSK_STS=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getTpcInHour());
            sql.addIntValue(bean.getTpcInMin());
            sql.addIntValue(bean.getTpcOutHour());
            sql.addIntValue(bean.getTpcOutMin());
            sql.addIntValue(bean.getTpcMainDsp());
            sql.addIntValue(bean.getTpcKinmuOut());
            sql.addIntValue(bean.getTpcZaisekiSts());
            //where
            sql.addIntValue(bean.getUsrSid());

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
     * <p>Update TCD_PRI_CONF Data Bindding JavaBean
     * @param bean TCD_PRI_CONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     * @return int
     */
    public int updateKinmuZyogai(TcdPriConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   TCD_PRI_CONF");
            sql.addSql(" set ");
            sql.addSql("   TPC_IN_HOUR=?,");
            sql.addSql("   TPC_IN_MIN=?,");
            sql.addSql("   TPC_OUT_HOUR=?,");
            sql.addSql("   TPC_OUT_MIN=?,");
            sql.addSql("   TPC_MAIN_DSP=?,");
            sql.addSql("   TPC_ZSK_STS=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getTpcInHour());
            sql.addIntValue(bean.getTpcInMin());
            sql.addIntValue(bean.getTpcOutHour());
            sql.addIntValue(bean.getTpcOutMin());
            sql.addIntValue(bean.getTpcMainDsp());
            sql.addIntValue(bean.getTpcZaisekiSts());
            //where
            sql.addIntValue(bean.getUsrSid());

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
     * <p>Select TCD_PRI_CONF All Data
     * @return List in TCD_PRI_CONFModel
     * @throws SQLException SQL実行例外
     */
    public List<TcdPriConfModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<TcdPriConfModel> ret = new ArrayList<TcdPriConfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   TPC_IN_HOUR,");
            sql.addSql("   TPC_IN_MIN,");
            sql.addSql("   TPC_OUT_HOUR,");
            sql.addSql("   TPC_OUT_MIN,");
            sql.addSql("   TPC_MAIN_DSP,");
            sql.addSql("   TPC_KINMU_OUT,");
            sql.addSql("   TPC_ZSK_STS");
            sql.addSql(" from ");
            sql.addSql("   TCD_PRI_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getTcdPriConfFromRs(rs));
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
     * <p>Select TCD_PRI_CONF
     * @param bean TCD_PRI_CONF Model
     * @return TCD_PRI_CONFModel
     * @throws SQLException SQL実行例外
     */
    public TcdPriConfModel select(TcdPriConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        TcdPriConfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   TPC_IN_HOUR,");
            sql.addSql("   TPC_IN_MIN,");
            sql.addSql("   TPC_OUT_HOUR,");
            sql.addSql("   TPC_OUT_MIN,");
            sql.addSql("   TPC_MAIN_DSP,");
            sql.addSql("   TPC_KINMU_OUT,");
            sql.addSql("   TPC_ZSK_STS");
            sql.addSql(" from");
            sql.addSql("   TCD_PRI_CONF");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUsrSid());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getTcdPriConfFromRs(rs);
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
     * <p>ユーザSIDを指定し個人設定を取得します
     * @param usrSid ユーザSID
     * @return TCD_PRI_CONFModel
     * @throws SQLException SQL実行例外
     */
    public TcdPriConfModel select(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        TcdPriConfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   TPC_IN_HOUR,");
            sql.addSql("   TPC_IN_MIN,");
            sql.addSql("   TPC_OUT_HOUR,");
            sql.addSql("   TPC_OUT_MIN,");
            sql.addSql("   TPC_MAIN_DSP,");
            sql.addSql("   TPC_KINMU_OUT,");
            sql.addSql("   TPC_ZSK_STS");
            sql.addSql(" from");
            sql.addSql("   TCD_PRI_CONF");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getTcdPriConfFromRs(rs);
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
     * <p>Delete TCD_PRI_CONF
     * @param bean TCD_PRI_CONF Model
     * @throws SQLException SQL実行例外
     * @return int
     */
    public int delete(TcdPriConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   TCD_PRI_CONF");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUsrSid());

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
     * <p>Create TCD_PRI_CONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created TcdPriConfModel
     * @throws SQLException SQL実行例外
     */
    private TcdPriConfModel __getTcdPriConfFromRs(ResultSet rs) throws SQLException {
        TcdPriConfModel bean = new TcdPriConfModel();
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setTpcInHour(rs.getInt("TPC_IN_HOUR"));
        bean.setTpcInMin(rs.getInt("TPC_IN_MIN"));
        bean.setTpcOutHour(rs.getInt("TPC_OUT_HOUR"));
        bean.setTpcOutMin(rs.getInt("TPC_OUT_MIN"));
        bean.setTpcMainDsp(rs.getInt("TPC_MAIN_DSP"));
        bean.setTpcKinmuOut(rs.getInt("TPC_KINMU_OUT"));
        bean.setTpcZaisekiSts(rs.getInt("TPC_ZSK_STS"));
        return bean;
    }
}
