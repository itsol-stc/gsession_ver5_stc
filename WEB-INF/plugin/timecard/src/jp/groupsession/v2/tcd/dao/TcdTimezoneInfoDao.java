package jp.groupsession.v2.tcd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.tcd.model.TcdTimezoneInfoModel;
import jp.groupsession.v2.tcd.tcd130.Tcd130DispModel;

/**
 * <p>TCD_TIMEZONE_INFO Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class TcdTimezoneInfoDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(TcdTimezoneInfoDao.class);

    /**
     * <p>Default Constructor
     */
    public TcdTimezoneInfoDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public TcdTimezoneInfoDao(Connection con) {
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
            sql.addSql("drop table TCD_TIMEZONE_INFO");

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
            sql.addSql(" create table TCD_TIMEZONE_INFO (");
            sql.addSql("   TTI_SID integer not null,");
            sql.addSql("   TTI_NAME varchar(100) not null,");
            sql.addSql("   TTI_RYAKU varchar(5) not null,");
            sql.addSql("   TTI_SORT integer not null,");
            sql.addSql("   TTI_USE integer not null,");
            sql.addSql("   TTI_HOLIDAY integer not null,");
            sql.addSql("   TTI_AUID integer not null,");
            sql.addSql("   TTI_ADATE timestamp not null,");
            sql.addSql("   TTI_EUID integer not null,");
            sql.addSql("   TTI_EDATE timestamp not null,");
            sql.addSql("   primary key (TTI_SID)");
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
     * <p>Insert TCD_TIMEZONE_INFO Data Bindding JavaBean
     * @param bean TCD_TIMEZONE_INFO Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(TcdTimezoneInfoModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" TCD_TIMEZONE_INFO(");
            sql.addSql("   TTI_SID,");
            sql.addSql("   TTI_NAME,");
            sql.addSql("   TTI_RYAKU,");
            sql.addSql("   TTI_SORT,");
            sql.addSql("   TTI_USE,");
            sql.addSql("   TTI_HOLIDAY,");
            sql.addSql("   TTI_AUID,");
            sql.addSql("   TTI_ADATE,");
            sql.addSql("   TTI_EUID,");
            sql.addSql("   TTI_EDATE");
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
            sql.addIntValue(bean.getTtiSid());
            sql.addStrValue(bean.getTtiName());
            sql.addStrValue(bean.getTtiRyaku());
            sql.addIntValue(bean.getTtiSort());
            sql.addIntValue(bean.getTtiUse());
            sql.addIntValue(bean.getTtiHoliday());
            sql.addIntValue(bean.getTtiAuid());
            sql.addDateValue(bean.getTtiAdate());
            sql.addIntValue(bean.getTtiEuid());
            sql.addDateValue(bean.getTtiEdate());
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
     * <p>Update TCD_TIMEZONE_INFO Data Bindding JavaBean
     * @param bean TCD_TIMEZONE_INFO Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(TcdTimezoneInfoModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   TCD_TIMEZONE_INFO");
            sql.addSql(" set ");
            sql.addSql("   TTI_NAME=?,");
            sql.addSql("   TTI_RYAKU=?,");
            sql.addSql("   TTI_USE=?,");
            sql.addSql("   TTI_HOLIDAY=?,");
            sql.addSql("   TTI_EUID=?,");
            sql.addSql("   TTI_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   TTI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getTtiName());
            sql.addStrValue(bean.getTtiRyaku());
            sql.addIntValue(bean.getTtiUse());
            sql.addIntValue(bean.getTtiHoliday());
            sql.addIntValue(bean.getTtiEuid());
            sql.addDateValue(bean.getTtiEdate());
            //where
            sql.addIntValue(bean.getTtiSid());

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
     * <p>Select TCD_TIMEZONE_INFO All Data
     * @return List in TCD_TIMEZONE_INFOModel
     * @throws SQLException SQL実行例外
     */
    public List<TcdTimezoneInfoModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<TcdTimezoneInfoModel> ret = new ArrayList<TcdTimezoneInfoModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   TTI_SID,");
            sql.addSql("   TTI_NAME,");
            sql.addSql("   TTI_RYAKU,");
            sql.addSql("   TTI_SORT,");
            sql.addSql("   TTI_USE,");
            sql.addSql("   TTI_HOLIDAY,");
            sql.addSql("   TTI_AUID,");
            sql.addSql("   TTI_ADATE,");
            sql.addSql("   TTI_EUID,");
            sql.addSql("   TTI_EDATE");
            sql.addSql(" from ");
            sql.addSql("   TCD_TIMEZONE_INFO");
            sql.addSql(" order by ");
            sql.addSql("   TTI_SORT asc ");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getTcdTimezoneInfoFromRs(rs));
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
     * <p>Select TCD_TIMEZONE_INFO All Data
     * @return List in TCD_TIMEZONE_INFOModel
     * @throws SQLException SQL実行例外
     */
    public List<TcdTimezoneInfoModel> selectCanUse() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<TcdTimezoneInfoModel> ret = new ArrayList<TcdTimezoneInfoModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   TTI_SID,");
            sql.addSql("   TTI_NAME,");
            sql.addSql("   TTI_RYAKU,");
            sql.addSql("   TTI_SORT,");
            sql.addSql("   TTI_USE,");
            sql.addSql("   TTI_HOLIDAY,");
            sql.addSql("   TTI_AUID,");
            sql.addSql("   TTI_ADATE,");
            sql.addSql("   TTI_EUID,");
            sql.addSql("   TTI_EDATE");
            sql.addSql(" from ");
            sql.addSql("   TCD_TIMEZONE_INFO");
            sql.addSql(" where ");
            sql.addSql("   TTI_USE = ? ");
            sql.addSql(" order by ");
            sql.addSql("   TTI_SORT asc ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(GSConstTimecard.TIMEZONE_USE_KBN_OK);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getTcdTimezoneInfoFromRs(rs));
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
     * <p>Select TCD_TIMEZONE_INFO
     * @param ttiSid TTI_SID
     * @return TCD_TIMEZONE_INFOModel
     * @throws SQLException SQL実行例外
     */
    public TcdTimezoneInfoModel select(int ttiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        TcdTimezoneInfoModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   TTI_SID,");
            sql.addSql("   TTI_NAME,");
            sql.addSql("   TTI_RYAKU,");
            sql.addSql("   TTI_SORT,");
            sql.addSql("   TTI_USE,");
            sql.addSql("   TTI_HOLIDAY,");
            sql.addSql("   TTI_AUID,");
            sql.addSql("   TTI_ADATE,");
            sql.addSql("   TTI_EUID,");
            sql.addSql("   TTI_EDATE");
            sql.addSql(" from");
            sql.addSql("   TCD_TIMEZONE_INFO");
            sql.addSql(" where ");
            sql.addSql("   TTI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ttiSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getTcdTimezoneInfoFromRs(rs);
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
     * <p>Select TCD_TIMEZONE_INFO
     * @param ttiSid TTI_SID
     * @return TCD_TIMEZONE_INFOModel
     * @throws SQLException SQL実行例外
     */
    public List<TcdTimezoneInfoModel> selectZyogai(int ttiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<TcdTimezoneInfoModel> ret = new ArrayList<TcdTimezoneInfoModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   TTI_SID,");
            sql.addSql("   TTI_NAME,");
            sql.addSql("   TTI_RYAKU,");
            sql.addSql("   TTI_SORT,");
            sql.addSql("   TTI_USE,");
            sql.addSql("   TTI_HOLIDAY,");
            sql.addSql("   TTI_AUID,");
            sql.addSql("   TTI_ADATE,");
            sql.addSql("   TTI_EUID,");
            sql.addSql("   TTI_EDATE");
            sql.addSql(" from");
            sql.addSql("   TCD_TIMEZONE_INFO");
            sql.addSql(" where ");
            sql.addSql("   TTI_SID <> ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ttiSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getTcdTimezoneInfoFromRs(rs));
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
     * <p>Select TCD_TIMEZONE_INFO
     * @param ttiSid TTI_SID
     * @return TCD_TIMEZONE_INFOModel
     * @throws SQLException SQL実行例外
     */
    public int timezoneCount(int ttiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int count = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from");
            sql.addSql("   TCD_TIMEZONE_INFO");
            sql.addSql(" where ");
            sql.addSql("   TTI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ttiSid);

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
     * <p>Delete TCD_TIMEZONE_INFO
     * @param ttiSid TTI_SID
     * @throws SQLException SQL実行例外
     */
    public int delete(int ttiSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   TCD_TIMEZONE_INFO");
            sql.addSql(" where ");
            sql.addSql("   TTI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ttiSid);

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
            sql.addSql("   max(TTI_SORT) as MAX_ORDER");
            sql.addSql(" from");
            sql.addSql("   TCD_TIMEZONE_INFO");
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
     * @return CIR_LABELModel
     * @throws SQLException SQL実行例外
     */
    public TcdTimezoneInfoModel selectFromOrder(int order) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        TcdTimezoneInfoModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   TTI_SID,");
            sql.addSql("   TTI_NAME,");
            sql.addSql("   TTI_RYAKU,");
            sql.addSql("   TTI_SORT,");
            sql.addSql("   TTI_USE,");
            sql.addSql("   TTI_HOLIDAY,");
            sql.addSql("   TTI_AUID,");
            sql.addSql("   TTI_ADATE,");
            sql.addSql("   TTI_EUID,");
            sql.addSql("   TTI_EDATE");
            sql.addSql(" from");
            sql.addSql("   TCD_TIMEZONE_INFO");
            sql.addSql(" where ");
            sql.addSql("   TTI_SORT=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(order);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getTcdTimezoneInfoFromRs(rs);
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
     * <br>[機  能] 時間帯情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSidList ユーザSID
     * @return 時間帯情報一覧マップ
     * @throws SQLException SQL実行例外
     */
    public Map<Integer, List<Tcd130DispModel>>
        getTimeZoneInfoMap(List<Integer> usrSidList)
            throws SQLException {
        Connection con = null;
        con = getCon();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Map<Integer, List<Tcd130DispModel>> retMap =
                new HashMap<Integer, List<Tcd130DispModel>>();
        if (usrSidList == null || usrSidList.size() == 0) {
            return retMap;
        }
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   TCD_TIMEZONE_PRI.USR_SID,");
            sql.addSql("   TCD_TIMEZONE_PRI.TTP_DEFAULT,");
            sql.addSql("   TCD_TIMEZONE_INFO.TTI_SID,");
            sql.addSql("   TCD_TIMEZONE_INFO.TTI_NAME,");
            sql.addSql("   TCD_TIMEZONE_INFO.TTI_USE");
            sql.addSql(" from ");
            sql.addSql("   TCD_TIMEZONE_INFO");
            sql.addSql(" left join ");
            sql.addSql("   TCD_TIMEZONE_PRI");
            sql.addSql(" on ");
            sql.addSql("   TCD_TIMEZONE_INFO.TTI_SID = TCD_TIMEZONE_PRI.TTI_SID");
            sql.addSql(" where ");
            sql.addSql("   TCD_TIMEZONE_PRI.USR_SID in (");
            for (int i = 0; i < usrSidList.size(); i++) {
                if (i > 0) {
                    sql.addSql("     , ");
                }
                sql.addSql("     ?");
                sql.addIntValue(usrSidList.get(i));
            }
            sql.addSql("   )");
            sql.addSql(" order by ");
            sql.addSql("   TTI_SORT");
            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int key = rs.getInt("USR_SID");
                // キーがないなら
                if (retMap.containsKey(key) == false) {
                    retMap.put(key, new ArrayList<Tcd130DispModel>());
                }
                Tcd130DispModel bean = new Tcd130DispModel();
                bean.setTtiSid(rs.getInt("TTI_SID"));
                bean.setTimeZoneName(rs.getString("TTI_NAME"));
                bean.setTtpDefault(rs.getInt("TTP_DEFAULT"));
                bean.setTimeZoneUkoFlg(rs.getInt("TTI_USE"));
                retMap.get(key).add(bean);
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return retMap;

    }

    /**
     * <br>[機  能] 時間帯情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSidList ユーザSID
     * @return 時間帯情報一覧マップ
     * @throws SQLException SQL実行例外
     */
    public ArrayList<TcdTimezoneInfoModel>
        getTimeZoneInfo(int usrSid)
            throws SQLException {
        Connection con = null;
        con = getCon();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<TcdTimezoneInfoModel> ret = new ArrayList<TcdTimezoneInfoModel>();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   TCD_TIMEZONE_PRI.USR_SID,");
            sql.addSql("   TCD_TIMEZONE_INFO.TTI_SID,");
            sql.addSql("   TCD_TIMEZONE_INFO.TTI_NAME,");
            sql.addSql("   TCD_TIMEZONE_INFO.TTI_RYAKU,");
            sql.addSql("   TCD_TIMEZONE_INFO.TTI_USE,");
            sql.addSql("   TCD_TIMEZONE_INFO.TTI_HOLIDAY,");
            sql.addSql("   TCD_TIMEZONE_INFO.TTI_SORT,");
            sql.addSql("   TCD_TIMEZONE_INFO.TTI_AUID,");
            sql.addSql("   TCD_TIMEZONE_INFO.TTI_ADATE,");
            sql.addSql("   TCD_TIMEZONE_INFO.TTI_EUID,");
            sql.addSql("   TCD_TIMEZONE_INFO.TTI_EDATE ");
            sql.addSql(" from ");
            sql.addSql("   TCD_TIMEZONE_INFO");
            sql.addSql(" left join ");
            sql.addSql("   TCD_TIMEZONE_PRI");
            sql.addSql(" on ");
            sql.addSql("   TCD_TIMEZONE_INFO.TTI_SID = TCD_TIMEZONE_PRI.TTI_SID");
            sql.addSql(" where ");
            sql.addSql("   TCD_TIMEZONE_PRI.USR_SID = ?");
            sql.addIntValue(usrSid);
            sql.addSql(" order by ");
            sql.addSql("   TTI_SORT");
            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getTcdTimezoneInfoFromRs(rs));
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
            sql.addSql("   TCD_TIMEZONE_INFO");
            sql.addSql(" set ");
            sql.addSql("   TTI_SORT=?,");
            sql.addSql("   TTI_EUID=?,");
            sql.addSql("   TTI_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   TTI_SID=?");
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
    public int updateSortNum(TcdTimezoneInfoModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   TCD_TIMEZONE_INFO");
            sql.addSql(" set ");
            sql.addSql("   TTI_SORT=?");
            sql.addSql(" where ");
            sql.addSql("   TTI_SID=?");
            sql.addIntValue(bean.getTtiSort());
            sql.addIntValue(bean.getTtiSid());
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
     * <p>Select TCD_TIMEZONE_INFO All Data
     * @return List in TCD_TIMEZONE_INFOModel
     * @throws SQLException SQL実行例外
     */
    public List<TcdTimezoneInfoModel> selectTimeZoneInfoList() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<TcdTimezoneInfoModel> ret = new ArrayList<TcdTimezoneInfoModel>();
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   TTI_SID,");
            sql.addSql("   TTI_NAME,");
            sql.addSql("   TTI_RYAKU,");
            sql.addSql("   TTI_USE,");
            sql.addSql("   TTI_HOLIDAY,");
            sql.addSql("   TTI_SORT,");
            sql.addSql("   TTI_AUID,");
            sql.addSql("   TTI_ADATE,");
            sql.addSql("   TTI_EUID,");
            sql.addSql("   TTI_EDATE ");
            sql.addSql(" from ");
            sql.addSql("   TCD_TIMEZONE_INFO");
            sql.addSql(" order by ");
            sql.addSql("   TTI_SORT asc");
            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getTcdTimezoneInfoFromRs(rs));
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
     * <p>Select 使用可能チェック単一
     * @param ttiSid 時間帯SID
     * @return true 可能
     * @throws SQLException SQL実行例外
     */
    public boolean canSelectOne(
            int ttiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean ret = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   TTI_SID");
            sql.addSql(" from ");
            sql.addSql("   TCD_TIMEZONE_INFO");
            sql.addSql(" where ");
            sql.addSql("   TTI_USE = ?");
            sql.addSql(" and");
            sql.addSql("   TTI_SID = ?");
            sql.addIntValue(GSConstTimecard.TIMEZONE_USE_KBN_OK);
            sql.addIntValue(ttiSid);
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = true;
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
     * <p>Select 使用可能チェック複数
     * @param String[]ttiSid 時間帯SID
     * @return true 可能
     * @throws SQLException SQL実行例外
     */
    public boolean canSelect(
            String[] ttiSid) throws SQLException {

        if (ttiSid == null || ttiSid.length < 1) {
            return false;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean ret = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   TTI_SID");
            sql.addSql(" from ");
            sql.addSql("   TCD_TIMEZONE_INFO");
            sql.addSql(" where ");
            sql.addSql("   TTI_USE = ?");
            sql.addIntValue(GSConstTimecard.TIMEZONE_USE_KBN_OK);
            sql.addSql(" and");
            sql.addSql("   TTI_SID in (");
            //where
            for (int i = 0; i < ttiSid.length; i++) {
                sql.addSql("     ? ");
                sql.addIntValue(NullDefault.getInt(ttiSid[i], 0));

                if (i < ttiSid.length - 1) {
                    sql.addSql("     , ");
                }
            }
            sql.addSql("   )");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = true;
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
     * <br>[機  能] 個人が使用できる時間帯の略称を取得
     * <br>[解  説] 使用できる時間帯がない場合、管理者設定のデフォルト時間帯を設定
     * <br>[備  考] 
     * @param usrSid ユーザSID
     * @return TcdTcdataModel 更新対象
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<TcdTimezoneInfoModel> getCanUseTimeZone(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<TcdTimezoneInfoModel> ret = new ArrayList<TcdTimezoneInfoModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   TTI_SID,");
            sql.addSql("   TTI_NAME,");
            sql.addSql("   TTI_RYAKU,");
            sql.addSql("   TTI_SORT,");
            sql.addSql("   TTI_USE,");
            sql.addSql("   TTI_HOLIDAY,");
            sql.addSql("   TTI_AUID,");
            sql.addSql("   TTI_ADATE,");
            sql.addSql("   TTI_EUID,");
            sql.addSql("   TTI_EDATE");
            sql.addSql(" from");
            sql.addSql("   TCD_TIMEZONE_INFO");
            sql.addSql(" where ");
            sql.addSql("   TTI_SID IN");
            sql.addSql("   (");
            sql.addSql("    select");
            sql.addSql("      TAC_DEF_TIMEZONE");
            sql.addSql("    from");
            sql.addSql("      TCD_ADM_CONF");
            sql.addSql("    where");
            sql.addSql("      not exists");
            sql.addSql("      (");
            sql.addSql("       select");
            sql.addSql("         TTI_SID");
            sql.addSql("       from");
            sql.addSql("         TCD_TIMEZONE_PRI");
            sql.addSql("       where");
            sql.addSql("         USR_SID=?");
            sql.addSql("      )");
            sql.addSql("      union all");
            sql.addSql("      (");
            sql.addSql("       select");
            sql.addSql("         TTI_SID");
            sql.addSql("       from");
            sql.addSql("         TCD_TIMEZONE_PRI");
            sql.addSql("       where");
            sql.addSql("         USR_SID=?");
            sql.addSql("      )");
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getTcdTimezoneInfoFromRs(rs));
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
     * <p>Create TCD_TIMEZONE_INFO Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created Tcd060ChangeModel
     * @throws SQLException SQL実行例外
     */
    private TcdTimezoneInfoModel __getTcdTimezoneInfoFromRs(ResultSet rs) throws SQLException {
        TcdTimezoneInfoModel bean = new TcdTimezoneInfoModel();
        bean.setTtiSid(rs.getInt("TTI_SID"));
        bean.setTtiName(rs.getString("TTI_NAME"));
        bean.setTtiRyaku(rs.getString("TTI_RYAKU"));
        bean.setTtiSort(rs.getInt("TTI_SORT"));
        bean.setTtiUse(rs.getInt("TTI_USE"));
        bean.setTtiHoliday(rs.getInt("TTI_HOLIDAY"));
        bean.setTtiAuid(rs.getInt("TTI_AUID"));
        bean.setTtiAdate(UDate.getInstanceTimestamp(rs.getTimestamp("TTI_ADATE")));
        bean.setTtiEuid(rs.getInt("TTI_EUID"));
        bean.setTtiEdate(UDate.getInstanceTimestamp(rs.getTimestamp("TTI_EDATE")));
        return bean;
    }
}
