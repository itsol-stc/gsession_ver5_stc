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
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.tcd.model.TcdHolidayInfModel;

/**
 * <p>TCD_HOLIDAY_INF Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class TcdHolidayInfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(TcdHolidayInfDao.class);

    /**
     * <p>Default Constructor
     */
    public TcdHolidayInfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public TcdHolidayInfDao(Connection con) {
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
            sql.addSql("drop table TCD_HOLIDAY_INF");

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
            sql.addSql(" create table TCD_HOLIDAY_INF (");
            sql.addSql("   THI_SID integer not null,");
            sql.addSql("   THI_NAME varchar(10) not null,");
            sql.addSql("   THI_HOLTOTAL_KBN integer not null,");
            sql.addSql("   THI_LIMIT integer not null,");
            sql.addSql("   THI_ORDER integer not null,");
            sql.addSql("   THI_AUID integer not null,");
            sql.addSql("   THI_ADATE timestamp not null,");
            sql.addSql("   THI_EUID integer not null,");
            sql.addSql("   THI_EDATE timestamp not null,");
            sql.addSql("   THI_CONTENT_KBN integer not null,");
            sql.addSql("   primary key (THI_SID)");
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
     * <p>Insert TCD_HOLIDAY_INF Data Bindding JavaBean
     * @param bean TCD_HOLIDAY_INF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(TcdHolidayInfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" TCD_HOLIDAY_INF(");
            sql.addSql("   THI_SID,");
            sql.addSql("   THI_NAME,");
            sql.addSql("   THI_HOLTOTAL_KBN,");
            sql.addSql("   THI_LIMIT,");
            sql.addSql("   THI_ORDER,");
            sql.addSql("   THI_CONTENT_KBN,");
            sql.addSql("   THI_AUID,");
            sql.addSql("   THI_ADATE,");
            sql.addSql("   THI_EUID,");
            sql.addSql("   THI_EDATE");
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

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getThiSid());
            sql.addStrValue(bean.getThiName());
            sql.addIntValue(bean.getThiHoltotalKbn());
            sql.addIntValue(bean.getThiLimit());
            sql.addIntValue(bean.getThiOrder());
            sql.addIntValue(bean.getThiContentKbn());
            sql.addIntValue(bean.getThiAuid());
            sql.addDateValue(bean.getThiAdate());
            sql.addIntValue(bean.getThiEuid());
            sql.addDateValue(bean.getThiEdate());
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
     * <p>Update TCD_HOLIDAY_INF Data Bindding JavaBean
     * @param bean TCD_HOLIDAY_INF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(TcdHolidayInfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   TCD_HOLIDAY_INF");
            sql.addSql(" set ");
            sql.addSql("   THI_NAME=?,");
            sql.addSql("   THI_HOLTOTAL_KBN=?,");
            sql.addSql("   THI_LIMIT=?,");
            sql.addSql("   THI_CONTENT_KBN=?,");
            sql.addSql("   THI_EUID=?,");
            sql.addSql("   THI_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   THI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getThiName());
            sql.addIntValue(bean.getThiHoltotalKbn());
            sql.addIntValue(bean.getThiLimit());
            sql.addIntValue(bean.getThiContentKbn());
            sql.addIntValue(bean.getThiEuid());
            sql.addDateValue(bean.getThiEdate());
            //where
            sql.addIntValue(bean.getThiSid());

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
     * <p>Select TCD_HOLIDAY_INF All Data
     * @return List in TCD_HOLIDAY_INFModel
     * @throws SQLException SQL実行例外
     */
    public List<TcdHolidayInfModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<TcdHolidayInfModel> ret = new ArrayList<TcdHolidayInfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   THI_SID,");
            sql.addSql("   THI_NAME,");
            sql.addSql("   THI_HOLTOTAL_KBN,");
            sql.addSql("   THI_LIMIT,");
            sql.addSql("   THI_ORDER,");
            sql.addSql("   THI_CONTENT_KBN,");
            sql.addSql("   THI_AUID,");
            sql.addSql("   THI_ADATE,");
            sql.addSql("   THI_EUID,");
            sql.addSql("   THI_EDATE");
            sql.addSql(" from ");
            sql.addSql("   TCD_HOLIDAY_INF");
            sql.addSql(" order by ");
            sql.addSql("   THI_ORDER asc ");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getTcdHolidayInfFromRs(rs));
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
     * <br>[機  能] 休日区分名からTcdHolidayInfModelを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param thiName 休日区分名
     * @return　TcdHolidayInfModel
     * @throws SQLException SQL実行例外
     */
    public TcdHolidayInfModel getHolidayInf(String thiName) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        TcdHolidayInfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   THI_SID,");
            sql.addSql("   THI_NAME,");
            sql.addSql("   THI_HOLTOTAL_KBN,");
            sql.addSql("   THI_LIMIT,");
            sql.addSql("   THI_ORDER,");
            sql.addSql("   THI_CONTENT_KBN,");
            sql.addSql("   THI_AUID,");
            sql.addSql("   THI_ADATE,");
            sql.addSql("   THI_EUID,");
            sql.addSql("   THI_EDATE");
            sql.addSql(" from");
            sql.addSql("   TCD_HOLIDAY_INF");
            sql.addSql(" where");
            sql.addSql("   THI_NAME = ?");
            sql.addSql(" and");
            sql.addSql("   THI_LIMIT = 0");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(thiName);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret = __getTcdHolidayInfFromRs(rs);
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
     * <p>Select TCD_HOLIDAY_INF All Data
     * @return List in TCD_HOLIDAY_INFModel
     * @throws SQLException SQL実行例外
     */
    public List<TcdHolidayInfModel> getCanUseHolidayList() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<TcdHolidayInfModel> ret = new ArrayList<TcdHolidayInfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   THI_SID,");
            sql.addSql("   THI_NAME,");
            sql.addSql("   THI_HOLTOTAL_KBN,");
            sql.addSql("   THI_LIMIT,");
            sql.addSql("   THI_ORDER,");
            sql.addSql("   THI_CONTENT_KBN,");
            sql.addSql("   THI_AUID,");
            sql.addSql("   THI_ADATE,");
            sql.addSql("   THI_EUID,");
            sql.addSql("   THI_EDATE");
            sql.addSql(" from ");
            sql.addSql("   TCD_HOLIDAY_INF");
            sql.addSql(" where ");
            sql.addSql("   THI_LIMIT = ?");
            sql.addSql(" order by ");
            sql.addSql("   THI_ORDER asc");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(GSConstTimecard.USE_KBN_OK);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getTcdHolidayInfFromRs(rs));
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
     * <p>Select TCD_HOLIDAY_INF All Data
     * @return List in TCD_HOLIDAY_INFModel
     * @throws SQLException SQL実行例外
     */
    public List<TcdHolidayInfModel> getAllHolidayList() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<TcdHolidayInfModel> ret = new ArrayList<TcdHolidayInfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   THI_SID,");
            sql.addSql("   THI_NAME,");
            sql.addSql("   THI_HOLTOTAL_KBN,");
            sql.addSql("   THI_LIMIT,");
            sql.addSql("   THI_ORDER,");
            sql.addSql("   THI_CONTENT_KBN,");
            sql.addSql("   THI_AUID,");
            sql.addSql("   THI_ADATE,");
            sql.addSql("   THI_EUID,");
            sql.addSql("   THI_EDATE");
            sql.addSql(" from ");
            sql.addSql("   TCD_HOLIDAY_INF");
            sql.addSql(" order by ");
            sql.addSql("   THI_ORDER asc");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getTcdHolidayInfFromRs(rs));
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
     * <p>Select TCD_HOLIDAY_INF
     * @param thiSid THI_SID
     * @return TCD_HOLIDAY_INFModel
     * @throws SQLException SQL実行例外
     */
    public TcdHolidayInfModel select(int thiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        TcdHolidayInfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   THI_SID,");
            sql.addSql("   THI_NAME,");
            sql.addSql("   THI_HOLTOTAL_KBN,");
            sql.addSql("   THI_LIMIT,");
            sql.addSql("   THI_ORDER,");
            sql.addSql("   THI_CONTENT_KBN,");
            sql.addSql("   THI_AUID,");
            sql.addSql("   THI_ADATE,");
            sql.addSql("   THI_EUID,");
            sql.addSql("   THI_EDATE");
            sql.addSql(" from");
            sql.addSql("   TCD_HOLIDAY_INF");
            sql.addSql(" where ");
            sql.addSql("   THI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(thiSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getTcdHolidayInfFromRs(rs);
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
     * <br>[機  能] 休日区分の使用可能判定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param thiSid THI_SID
     * @return ret 件数
     * @throws SQLException SQL実行例外
     */
    public int existsHoliday(int thiSid, boolean useFlg) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from");
            sql.addSql("   TCD_HOLIDAY_INF");
            sql.addSql(" where ");
            sql.addSql("   THI_SID=?");
            sql.addIntValue(thiSid);
            if (useFlg) {
                sql.addSql(" and ");
                sql.addSql("   THI_LIMIT=?");
                sql.addIntValue(GSConstTimecard.USE_KBN_OK);
            }

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
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
     * <br>[機  能] 並び順の最大値を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return 最大ソート値
     * @throws SQLException SQL実行時例外
     */
    public int maxSortNumber()
    throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int maxNumber = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   max(THI_ORDER) as MAX_ORDER");
            sql.addSql(" from");
            sql.addSql("   TCD_HOLIDAY_INF");
            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                maxNumber = rs.getInt("MAX_ORDER");
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return maxNumber;
    }

    /**
     * <p>Select CIR_LABEL
     * @param order ソート番号
     * @return TcdHolidayInfModel
     * @throws SQLException SQL実行例外
     */
    public TcdHolidayInfModel selectFromOrder(int order) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        TcdHolidayInfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   THI_SID,");
            sql.addSql("   THI_NAME,");
            sql.addSql("   THI_HOLTOTAL_KBN,");
            sql.addSql("   THI_LIMIT,");
            sql.addSql("   THI_ORDER,");
            sql.addSql("   THI_CONTENT_KBN,");
            sql.addSql("   THI_AUID,");
            sql.addSql("   THI_ADATE,");
            sql.addSql("   THI_EUID,");
            sql.addSql("   THI_EDATE");
            sql.addSql(" from");
            sql.addSql("   TCD_HOLIDAY_INF");
            sql.addSql(" where ");
            sql.addSql("   THI_ORDER=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(order);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getTcdHolidayInfFromRs(rs);
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
     * <p>Update Data Bindding JavaBean
     * @param order 順番
     * @param userSid ユーザSID
     * @param ttiSid SID
     * @param now UDate
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateSort(int order, int userSid, UDate now, int ttiSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   TCD_HOLIDAY_INF");
            sql.addSql(" set ");
            sql.addSql("   THI_ORDER=?,");
            sql.addSql("   THI_EUID=?,");
            sql.addSql("   THI_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   THI_SID=?");
            sql.addIntValue(order);
            sql.addIntValue(userSid);
            sql.addDateValue(now);
            sql.addIntValue(ttiSid);
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }
    /**
     * <p>Update Data Bindding JavaBean
     * @param order 順番
     * @param userSid ユーザSID
     * @param ttiSid SID
     * @param now UDate
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateSort(TcdHolidayInfModel mdl) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   TCD_HOLIDAY_INF");
            sql.addSql(" set ");
            sql.addSql("   THI_ORDER=?");
            sql.addSql(" where ");
            sql.addSql("   THI_SID=?");
            sql.addIntValue(mdl.getThiOrder());
            sql.addIntValue(mdl.getThiSid());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <p>Delete TCD_HOLIDAY_INF
     * @param thiSid THI_SID
     * @throws SQLException SQL実行例外
     */
    public int delete(int thiSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   TCD_HOLIDAY_INF");
            sql.addSql(" where ");
            sql.addSql("   THI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(thiSid);

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
     * <br>[機  能]指定した名称の休日を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param thiName 休日区分名
     * @return TcdHolidayInfModel
     * @throws SQLException
     */
    public TcdHolidayInfModel getHolidayDataWithName(String thiName)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        TcdHolidayInfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   THI_SID,");
            sql.addSql("   THI_NAME,");
            sql.addSql("   THI_HOLTOTAL_KBN,");
            sql.addSql("   THI_LIMIT,");
            sql.addSql("   THI_ORDER,");
            sql.addSql("   THI_CONTENT_KBN,");
            sql.addSql("   THI_AUID,");
            sql.addSql("   THI_ADATE,");
            sql.addSql("   THI_EUID,");
            sql.addSql("   THI_EDATE");
            sql.addSql(" from");
            sql.addSql("   TCD_HOLIDAY_INF");
            sql.addSql(" where ");
            sql.addSql("   THI_NAME = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(thiName);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getTcdHolidayInfFromRs(rs);
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
     * <p>Create TCD_HOLIDAY_INF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created TcdHolidayInfModel
     * @throws SQLException SQL実行例外
     */
    private TcdHolidayInfModel __getTcdHolidayInfFromRs(ResultSet rs) throws SQLException {
        TcdHolidayInfModel bean = new TcdHolidayInfModel();
        bean.setThiSid(rs.getInt("THI_SID"));
        bean.setThiName(rs.getString("THI_NAME"));
        bean.setThiHoltotalKbn(rs.getInt("THI_HOLTOTAL_KBN"));
        bean.setThiLimit(rs.getInt("THI_LIMIT"));
        bean.setThiOrder(rs.getInt("THI_ORDER"));
        bean.setThiContentKbn(rs.getInt("THI_CONTENT_KBN"));
        bean.setThiAuid(rs.getInt("THI_AUID"));
        bean.setThiAdate(UDate.getInstanceTimestamp(rs.getTimestamp("THI_ADATE")));
        bean.setThiEuid(rs.getInt("THI_EUID"));
        bean.setThiEdate(UDate.getInstanceTimestamp(rs.getTimestamp("THI_EDATE")));
        return bean;
    }
}
