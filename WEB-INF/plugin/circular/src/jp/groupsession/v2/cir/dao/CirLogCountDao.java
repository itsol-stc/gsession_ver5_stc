package jp.groupsession.v2.cir.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cir.model.CirLogCountModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>CIR_LOG_COUNT Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CirLogCountDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CirLogCountDao.class);

    /**
     * <p>Default Constructor
     */
    public CirLogCountDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CirLogCountDao(Connection con) {
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
            sql.addSql("drop table CIR_LOG_COUNT");

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
            sql.addSql(" create table CIR_LOG_COUNT (");
            sql.addSql("   CAC_SID NUMBER(10,0) not null,");
            sql.addSql("   CLC_KBN NUMBER(10,0) not null,");
            sql.addSql("   CLC_CNT NUMBER(10,0) not null,");
            sql.addSql("   CLC_DATE varchar(23) not null");
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
     * <p>Insert CIR_LOG_COUNT Data Bindding JavaBean
     * @param bean CIR_LOG_COUNT Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CirLogCountModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CIR_LOG_COUNT(");
            sql.addSql("   CAC_SID,");
            sql.addSql("   CLC_KBN,");
            sql.addSql("   CLC_CNT,");
            sql.addSql("   CLC_DATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCacSid());
            sql.addIntValue(bean.getClcKbn());
            sql.addIntValue(bean.getClcCnt());
            sql.addDateValue(bean.getClcDate());
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
     * <p>Update CIR_LOG_COUNT Data Bindding JavaBean
     * @param bean CIR_LOG_COUNT Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CirLogCountModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CIR_LOG_COUNT");
            sql.addSql(" set ");
            sql.addSql("   CAC_SID=?,");
            sql.addSql("   CLC_KBN=?,");
            sql.addSql("   CLC_CNT=?,");
            sql.addSql("   CLC_DATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCacSid());
            sql.addIntValue(bean.getClcKbn());
            sql.addIntValue(bean.getClcCnt());
            sql.addDateValue(bean.getClcDate());

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
     * <p>Select CIR_LOG_COUNT All Data
     * @return List in CIR_LOG_COUNTModel
     * @throws SQLException SQL実行例外
     */
    public List<CirLogCountModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CirLogCountModel> ret = new ArrayList<CirLogCountModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CAC_SID,");
            sql.addSql("   CLC_KBN,");
            sql.addSql("   CLC_CNT,");
            sql.addSql("   CLC_DATE");
            sql.addSql(" from ");
            sql.addSql("   CIR_LOG_COUNT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCirLogCountFromRs(rs));
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
     * <br>[機  能] 削除を行う
     * <br>[解  説]
     * <br>[備  考] 前日までの集計データを削除する
     * @param date 現在日時
     * @return 削除件数
     * @throws SQLException SQL実行時例外
     */
    public int delete(UDate date) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();
        
        UDate preDay = date.cloneUDate();
        preDay.addDay(-1);
        preDay.setZeroHhMmSs();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete from");
            sql.addSql("   CIR_LOG_COUNT");
            sql.addSql(" where ");
            sql.addSql("   CLC_DATE <= ?");
            sql.addDateValue(preDay);

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
     * <p>Create CIR_LOG_COUNT Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CirLogCountModel
     * @throws SQLException SQL実行例外
     */
    private CirLogCountModel __getCirLogCountFromRs(ResultSet rs) throws SQLException {
        CirLogCountModel bean = new CirLogCountModel();
        bean.setCacSid(rs.getInt("CAC_SID"));
        bean.setClcKbn(rs.getInt("CLC_KBN"));
        bean.setClcCnt(rs.getInt("CLC_CNT"));
        bean.setClcDate(UDate.getInstanceTimestamp(rs.getTimestamp("CLC_DATE")));
        return bean;
    }
}
