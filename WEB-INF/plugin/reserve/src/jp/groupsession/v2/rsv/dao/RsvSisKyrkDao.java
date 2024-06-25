package jp.groupsession.v2.rsv.dao;

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
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.rsv.model.RsvSisKyrkModel;

/**
 * <p>RSV_SIS_KYRK Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RsvSisKyrkDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RsvSisKyrkDao.class);

    /**
     * <p>Default Constructor
     */
    public RsvSisKyrkDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public RsvSisKyrkDao(Connection con) {
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
            sql.addSql("drop table RSV_SIS_KYRK");

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
            sql.addSql(" create table RSV_SIS_KYRK (");
            sql.addSql("   RSY_SID NUMBER(10,0) not null,");
            sql.addSql("   RKY_BUSYO varchar(50),");
            sql.addSql("   RKY_NAME varchar(70),");
            sql.addSql("   RKY_NUM varchar(15),");
            sql.addSql("   RKY_USE_KBN NUMBER(10,0),");
            sql.addSql("   RKY_CONTACT varchar(50),");
            sql.addSql("   RKY_GUIDE varchar(50),");
            sql.addSql("   RKY_PARK_NUM varchar(15),");
            sql.addSql("   RKY_PRINT_KBN NUMBER(10,0),");
            sql.addSql("   RKY_DEST varchar(50),");
            sql.addSql("   RKY_AUID NUMBER(10,0) not null,");
            sql.addSql("   RKY_ADATE varchar(23) not null,");
            sql.addSql("   RKY_EUID NUMBER(10,0) not null,");
            sql.addSql("   RKY_EDATE varchar(23) not null,");
            sql.addSql("   primary key (RSY_SID)");
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
     * <p>Insert RSV_SIS_KYRK Data Bindding JavaBean
     * @param bean RSV_SIS_KYRK Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(RsvSisKyrkModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" RSV_SIS_KYRK(");
            sql.addSql("   RSY_SID,");
            sql.addSql("   RKY_BUSYO,");
            sql.addSql("   RKY_NAME,");
            sql.addSql("   RKY_NUM,");
            sql.addSql("   RKY_USE_KBN,");
            sql.addSql("   RKY_CONTACT,");
            sql.addSql("   RKY_GUIDE,");
            sql.addSql("   RKY_PARK_NUM,");
            sql.addSql("   RKY_PRINT_KBN,");
            sql.addSql("   RKY_DEST,");
            sql.addSql("   RKY_AUID,");
            sql.addSql("   RKY_ADATE,");
            sql.addSql("   RKY_EUID,");
            sql.addSql("   RKY_EDATE");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRsySid());
            sql.addStrValue(bean.getRkyBusyo());
            sql.addStrValue(bean.getRkyName());
            sql.addStrValue(bean.getRkyNum());
            sql.addIntValue(bean.getRkyUseKbn());
            sql.addStrValue(bean.getRkyContact());
            sql.addStrValue(bean.getRkyGuide());
            sql.addStrValue(bean.getRkyParkNum());
            sql.addIntValue(bean.getRkyPrintKbn());
            sql.addStrValue(bean.getRkyDest());
            sql.addIntValue(bean.getRkyAuid());
            sql.addDateValue(bean.getRkyAdate());
            sql.addIntValue(bean.getRkyEuid());
            sql.addDateValue(bean.getRkyEdate());
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
     * <p>Insert RSV_SIS_KYRK Data Bindding JavaBean
     * @param beanList RSV_SIS_KYRK DataList
     * @throws SQLException SQL実行例外
     */
    public void insert(List<RsvSisKyrkModel> beanList) throws SQLException {


        if (beanList == null || beanList.size() <= 0) {
            return;
        }
        List<RsvSisKyrkModel> exeList = new ArrayList<>();
        Iterator<RsvSisKyrkModel> itr = beanList.iterator();
        StringBuilder sb = new StringBuilder();
        sb.append(" insert ");
        sb.append(" into ");
        sb.append(" RSV_SIS_KYRK(");
        sb.append("   RSY_SID,");
        sb.append("   RKY_BUSYO,");
        sb.append("   RKY_NAME,");
        sb.append("   RKY_NUM,");
        sb.append("   RKY_USE_KBN,");
        sb.append("   RKY_CONTACT,");
        sb.append("   RKY_GUIDE,");
        sb.append("   RKY_PARK_NUM,");
        sb.append("   RKY_PRINT_KBN,");
        sb.append("   RKY_DEST,");
        sb.append("   RKY_AUID,");
        sb.append("   RKY_ADATE,");
        sb.append("   RKY_EUID,");
        sb.append("   RKY_EDATE");
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

            Iterator<RsvSisKyrkModel> exeItr = exeList.iterator();
            while (exeItr.hasNext()) {
                RsvSisKyrkModel bean = exeItr.next();
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
                sql.addSql("   ?");
                sql.addSql(" )");

                sql.addIntValue(bean.getRsySid());
                sql.addStrValue(bean.getRkyBusyo());
                sql.addStrValue(bean.getRkyName());
                sql.addStrValue(bean.getRkyNum());
                sql.addIntValue(bean.getRkyUseKbn());
                sql.addStrValue(bean.getRkyContact());
                sql.addStrValue(bean.getRkyGuide());
                sql.addStrValue(bean.getRkyParkNum());
                sql.addIntValue(bean.getRkyPrintKbn());
                sql.addStrValue(bean.getRkyDest());
                sql.addIntValue(bean.getRkyAuid());
                sql.addDateValue(bean.getRkyAdate());
                sql.addIntValue(bean.getRkyEuid());
                sql.addDateValue(bean.getRkyEdate());

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
     * <p>Update RSV_SIS_KYRK Data Bindding JavaBean
     * @param bean RSV_SIS_KYRK Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(RsvSisKyrkModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RSV_SIS_KYRK");
            sql.addSql(" set ");
            sql.addSql("   RKY_BUSYO=?,");
            sql.addSql("   RKY_NAME=?,");
            sql.addSql("   RKY_NUM=?,");
            sql.addSql("   RKY_USE_KBN=?,");
            sql.addSql("   RKY_CONTACT=?,");
            sql.addSql("   RKY_GUIDE=?,");
            sql.addSql("   RKY_PARK_NUM=?,");
            sql.addSql("   RKY_PRINT_KBN=?,");
            sql.addSql("   RKY_DEST=?,");
            sql.addSql("   RKY_EUID=?,");
            sql.addSql("   RKY_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   RSY_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getRkyBusyo());
            sql.addStrValue(bean.getRkyName());
            sql.addStrValue(bean.getRkyNum());
            sql.addIntValue(bean.getRkyUseKbn());
            sql.addStrValue(bean.getRkyContact());
            sql.addStrValue(bean.getRkyGuide());
            sql.addStrValue(bean.getRkyParkNum());
            sql.addIntValue(bean.getRkyPrintKbn());
            sql.addStrValue(bean.getRkyDest());
            sql.addIntValue(bean.getRkyEuid());
            sql.addDateValue(bean.getRkyEdate());
            //where
            sql.addIntValue(bean.getRsySid());

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
     * <p>Select RSV_SIS_KYRK All Data
     * @return List in RSV_SIS_KYRKModel
     * @throws SQLException SQL実行例外
     */
    public List<RsvSisKyrkModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RsvSisKyrkModel> ret = new ArrayList<RsvSisKyrkModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RSY_SID,");
            sql.addSql("   RKY_BUSYO,");
            sql.addSql("   RKY_NAME,");
            sql.addSql("   RKY_NUM,");
            sql.addSql("   RKY_USE_KBN,");
            sql.addSql("   RKY_CONTACT,");
            sql.addSql("   RKY_GUIDE,");
            sql.addSql("   RKY_PARK_NUM,");
            sql.addSql("   RKY_PRINT_KBN,");
            sql.addSql("   RKY_DEST,");
            sql.addSql("   RKY_AUID,");
            sql.addSql("   RKY_ADATE,");
            sql.addSql("   RKY_EUID,");
            sql.addSql("   RKY_EDATE");
            sql.addSql(" from ");
            sql.addSql("   RSV_SIS_KYRK");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRsvSisKyrkFromRs(rs));
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
     * <p>Select RSV_SIS_KYRK All Data
     * @param offset レコードの読取開始行
     * @param limit 1ページの最大件数
     * @return List in RSV_SIS_KYRKModel
     * @throws SQLException SQL実行例外
     */
    public List<RsvSisKyrkModel> selectLimit(
            int offset, int limit) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RsvSisKyrkModel> ret = new ArrayList<RsvSisKyrkModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RSY_SID,");
            sql.addSql("   RKY_BUSYO,");
            sql.addSql("   RKY_NAME,");
            sql.addSql("   RKY_NUM,");
            sql.addSql("   RKY_USE_KBN,");
            sql.addSql("   RKY_CONTACT,");
            sql.addSql("   RKY_GUIDE,");
            sql.addSql("   RKY_PARK_NUM,");
            sql.addSql("   RKY_PRINT_KBN,");
            sql.addSql("   RKY_DEST,");
            sql.addSql("   RKY_AUID,");
            sql.addSql("   RKY_ADATE,");
            sql.addSql("   RKY_EUID,");
            sql.addSql("   RKY_EDATE");
            sql.addSql(" from ");
            sql.addSql("   RSV_SIS_KYRK");
            sql.addSql(" order by ");
            sql.addSql("   RSY_SID asc");

            sql.setPagingValue(offset, limit);

            pstmt = con.prepareStatement(sql.toSqlString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRsvSisKyrkFromRs(rs));
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
     * <p>count RSV_SIS_KYRK All Data
     * @return List in RSV_SIS_KYRKModel
     * @throws SQLException SQL実行例外
     */
    public int count() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from ");
            sql.addSql("   RSV_SIS_KYRK");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
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
     * <br>[機  能] データサイズ合計を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return データサイズ集計
     * @throws SQLException SQL実行時例外
     */
    public long getTotalDataSize() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        long dataSize = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   sum(octet_length(RKY_BUSYO)) as SIZE1,");
            sql.addSql("   sum(octet_length(RKY_NAME)) as SIZE2,");
            sql.addSql("   sum(octet_length(RKY_NUM)) as SIZE3,");
            sql.addSql("   sum(octet_length(RKY_CONTACT)) as SIZE4,");
            sql.addSql("   sum(octet_length(RKY_GUIDE)) as SIZE5,");
            sql.addSql("   sum(octet_length(RKY_PARK_NUM)) as SIZE6,");
            sql.addSql("   sum(octet_length(RKY_DEST)) as SIZE7");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_KYRK");
            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                dataSize = rs.getLong("SIZE1");
                dataSize += rs.getLong("SIZE2");
                dataSize += rs.getLong("SIZE3");
                dataSize += rs.getLong("SIZE4");
                dataSize += rs.getLong("SIZE5");
                dataSize += rs.getLong("SIZE6");
                dataSize += rs.getLong("SIZE7");
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
     * <p>Select RSV_SIS_KYRK
     * @param rsySid RSY_SID
     * @return RSV_SIS_KYRKModel
     * @throws SQLException SQL実行例外
     */
    public RsvSisKyrkModel select(int rsySid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        RsvSisKyrkModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSY_SID,");
            sql.addSql("   RKY_BUSYO,");
            sql.addSql("   RKY_NAME,");
            sql.addSql("   RKY_NUM,");
            sql.addSql("   RKY_USE_KBN,");
            sql.addSql("   RKY_CONTACT,");
            sql.addSql("   RKY_GUIDE,");
            sql.addSql("   RKY_PARK_NUM,");
            sql.addSql("   RKY_PRINT_KBN,");
            sql.addSql("   RKY_DEST,");
            sql.addSql("   RKY_AUID,");
            sql.addSql("   RKY_ADATE,");
            sql.addSql("   RKY_EUID,");
            sql.addSql("   RKY_EDATE");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_KYRK");
            sql.addSql(" where ");
            sql.addSql("   RSY_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rsySid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getRsvSisKyrkFromRs(rs);
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
     * <p>Delete RSV_SIS_KYRK
     * @param rsySid RSY_SID
     * @throws SQLException SQL実行例外
     * @return 件数
     */
    public int delete(int rsySid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_KYRK");
            sql.addSql(" where ");
            sql.addSql("   RSY_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rsySid);

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
     * <p>Delete RSV_SIS_KYRK
     * @param rsySidList 施設予約SIDリスト
     * @throws SQLException SQL実行例外
     * @return 件数
     */
    public int delete(ArrayList<Integer> rsySidList) throws SQLException {

        if (rsySidList == null || rsySidList.size() <= 0) {
            return 0;
        }

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_KYRK");
            sql.addSql(" where ");
            sql.addSql("   RSY_SID in (");
            for (int i = 0; i < rsySidList.size(); i++) {
                if ((i % 500) == 0 && i != 0) {
                    sql.addSql("   )");
                    sql.addSql(" or ");
                    sql.addSql("   RSY_SID in (");
                } else if (i != 0) {
                    sql.addSql(", ");
                }
                sql.addSql(String.valueOf(rsySidList.get(i)));
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
     * <p>Create RSV_SIS_KYRK Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created RsvSisKyrkModel
     * @throws SQLException SQL実行例外
     */
    private RsvSisKyrkModel __getRsvSisKyrkFromRs(ResultSet rs) throws SQLException {
        RsvSisKyrkModel bean = new RsvSisKyrkModel();
        bean.setRsySid(rs.getInt("RSY_SID"));
        bean.setRkyBusyo(rs.getString("RKY_BUSYO"));
        bean.setRkyName(rs.getString("RKY_NAME"));
        bean.setRkyNum(rs.getString("RKY_NUM"));
        bean.setRkyUseKbn(rs.getInt("RKY_USE_KBN"));
        bean.setRkyContact(rs.getString("RKY_CONTACT"));
        bean.setRkyGuide(rs.getString("RKY_GUIDE"));
        bean.setRkyParkNum(rs.getString("RKY_PARK_NUM"));
        bean.setRkyPrintKbn(rs.getInt("RKY_PRINT_KBN"));
        bean.setRkyDest(rs.getString("RKY_DEST"));
        bean.setRkyAuid(rs.getInt("RKY_AUID"));
        bean.setRkyAdate(UDate.getInstanceTimestamp(rs.getTimestamp("RKY_ADATE")));
        bean.setRkyEuid(rs.getInt("RKY_EUID"));
        bean.setRkyEdate(UDate.getInstanceTimestamp(rs.getTimestamp("RKY_EDATE")));
        return bean;
    }
}
