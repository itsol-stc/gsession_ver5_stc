package jp.groupsession.v2.rsv.dao;

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
import jp.groupsession.v2.rsv.model.RsvSisKryrkModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>RSV_SIS_KRYRK Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RsvSisKryrkDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RsvSisKryrkDao.class);

    /**
     * <p>Default Constructor
     */
    public RsvSisKryrkDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public RsvSisKryrkDao(Connection con) {
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
            sql.addSql("drop table RSV_SIS_KRYRK");

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
            sql.addSql(" create table RSV_SIS_KRYRK (");
            sql.addSql("   RSR_RSID NUMBER(10,0) not null,");
            sql.addSql("   RKR_BUSYO varchar(50),");
            sql.addSql("   RKR_NAME varchar(50),");
            sql.addSql("   RKR_NUM varchar(5),");
            sql.addSql("   RKR_USE_KBN NUMBER(10,0),");
            sql.addSql("   RKR_CONTACT varchar(50),");
            sql.addSql("   RKR_GUIDE varchar(50),");
            sql.addSql("   RKR_PARK_NUM varchar(5),");
            sql.addSql("   RKR_PRINT_KBN NUMBER(10,0),");
            sql.addSql("   RKR_DEST varchar(50),");
            sql.addSql("   RKR_AUID NUMBER(10,0) not null,");
            sql.addSql("   RKR_ADATE varchar(23) not null,");
            sql.addSql("   RKR_EUID NUMBER(10,0) not null,");
            sql.addSql("   RKR_EDATE varchar(23) not null,");
            sql.addSql("   primary key (RSR_RSID)");
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
     * <p>Insert RSV_SIS_KRYRK Data Bindding JavaBean
     * @param bean RSV_SIS_KRYRK Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(RsvSisKryrkModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" RSV_SIS_KRYRK(");
            sql.addSql("   RSR_RSID,");
            sql.addSql("   RKR_BUSYO,");
            sql.addSql("   RKR_NAME,");
            sql.addSql("   RKR_NUM,");
            sql.addSql("   RKR_USE_KBN,");
            sql.addSql("   RKR_CONTACT,");
            sql.addSql("   RKR_GUIDE,");
            sql.addSql("   RKR_PARK_NUM,");
            sql.addSql("   RKR_PRINT_KBN,");
            sql.addSql("   RKR_DEST,");
            sql.addSql("   RKR_AUID,");
            sql.addSql("   RKR_ADATE,");
            sql.addSql("   RKR_EUID,");
            sql.addSql("   RKR_EDATE");
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
            sql.addIntValue(bean.getRsrRsid());
            sql.addStrValue(bean.getRkrBusyo());
            sql.addStrValue(bean.getRkrName());
            sql.addStrValue(bean.getRkrNum());
            sql.addIntValue(bean.getRkrUseKbn());
            sql.addStrValue(bean.getRkrContact());
            sql.addStrValue(bean.getRkrGuide());
            sql.addStrValue(bean.getRkrParkNum());
            sql.addIntValue(bean.getRkrPrintKbn());
            sql.addStrValue(bean.getRkrDest());
            sql.addIntValue(bean.getRkrAuid());
            sql.addDateValue(bean.getRkrAdate());
            sql.addIntValue(bean.getRkrEuid());
            sql.addDateValue(bean.getRkrEdate());
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
     * <p>Update RSV_SIS_KRYRK Data Bindding JavaBean
     * @param bean RSV_SIS_KRYRK Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(RsvSisKryrkModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RSV_SIS_KRYRK");
            sql.addSql(" set ");
            sql.addSql("   RKR_BUSYO=?,");
            sql.addSql("   RKR_NAME=?,");
            sql.addSql("   RKR_NUM=?,");
            sql.addSql("   RKR_USE_KBN=?,");
            sql.addSql("   RKR_CONTACT=?,");
            sql.addSql("   RKR_GUIDE=?,");
            sql.addSql("   RKR_PARK_NUM=?,");
            sql.addSql("   RKR_PRINT_KBN=?,");
            sql.addSql("   RKR_DEST=?,");
            sql.addSql("   RKR_EUID=?,");
            sql.addSql("   RKR_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   RSR_RSID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getRkrBusyo());
            sql.addStrValue(bean.getRkrName());
            sql.addStrValue(bean.getRkrNum());
            sql.addIntValue(bean.getRkrUseKbn());
            sql.addStrValue(bean.getRkrContact());
            sql.addStrValue(bean.getRkrGuide());
            sql.addStrValue(bean.getRkrParkNum());
            sql.addIntValue(bean.getRkrPrintKbn());
            sql.addStrValue(bean.getRkrDest());
            sql.addIntValue(bean.getRkrEuid());
            sql.addDateValue(bean.getRkrEdate());
            //where
            sql.addIntValue(bean.getRsrRsid());

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
     * <p>Select RSV_SIS_KRYRK All Data
     * @return List in RSV_SIS_KRYRKModel
     * @throws SQLException SQL実行例外
     */
    public List<RsvSisKryrkModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RsvSisKryrkModel> ret = new ArrayList<RsvSisKryrkModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RSR_RSID,");
            sql.addSql("   RKR_BUSYO,");
            sql.addSql("   RKR_NAME,");
            sql.addSql("   RKR_NUM,");
            sql.addSql("   RKR_USE_KBN,");
            sql.addSql("   RKR_CONTACT,");
            sql.addSql("   RKR_GUIDE,");
            sql.addSql("   RKR_PARK_NUM,");
            sql.addSql("   RKR_PRINT_KBN,");
            sql.addSql("   RKR_DEST,");
            sql.addSql("   RKR_AUID,");
            sql.addSql("   RKR_ADATE,");
            sql.addSql("   RKR_EUID,");
            sql.addSql("   RKR_EDATE");
            sql.addSql(" from ");
            sql.addSql("   RSV_SIS_KRYRK");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRsvSisKryrkFromRs(rs));
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
     * <p>Select RSV_SIS_KRYRK
     * @param rsrRsid RSR_RSID
     * @return RSV_SIS_KRYRKModel
     * @throws SQLException SQL実行例外
     */
    public RsvSisKryrkModel select(int rsrRsid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        RsvSisKryrkModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSR_RSID,");
            sql.addSql("   RKR_BUSYO,");
            sql.addSql("   RKR_NAME,");
            sql.addSql("   RKR_NUM,");
            sql.addSql("   RKR_USE_KBN,");
            sql.addSql("   RKR_CONTACT,");
            sql.addSql("   RKR_GUIDE,");
            sql.addSql("   RKR_PARK_NUM,");
            sql.addSql("   RKR_PRINT_KBN,");
            sql.addSql("   RKR_DEST,");
            sql.addSql("   RKR_AUID,");
            sql.addSql("   RKR_ADATE,");
            sql.addSql("   RKR_EUID,");
            sql.addSql("   RKR_EDATE");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_KRYRK");
            sql.addSql(" where ");
            sql.addSql("   RSR_RSID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rsrRsid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getRsvSisKryrkFromRs(rs);
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
     * <p>Delete RSV_SIS_KRYRK
     * @param rsrRsid RSR_RSID
     * @throws SQLException SQL実行例外
     * @return count
     */
    public int delete(int rsrRsid) throws SQLException {
        if (rsrRsid <= 0) {
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
            sql.addSql("   RSV_SIS_KRYRK");
            sql.addSql(" where ");
            sql.addSql("   RSR_RSID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rsrRsid);

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
     * <p>Delete RSV_SIS_KRYRK
     * @param rsrRsidList 施設拡張SIDリスト
     * @throws SQLException SQL実行例外
     * @return count
     */
    public int delete(ArrayList<Integer> rsrRsidList) throws SQLException {

        if (rsrRsidList == null || rsrRsidList.size() <= 0) {
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
            sql.addSql("   RSV_SIS_KRYRK");
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
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <p>Create RSV_SIS_KRYRK Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created RsvSisKryrkModel
     * @throws SQLException SQL実行例外
     */
    private RsvSisKryrkModel __getRsvSisKryrkFromRs(ResultSet rs) throws SQLException {
        RsvSisKryrkModel bean = new RsvSisKryrkModel();
        bean.setRsrRsid(rs.getInt("RSR_RSID"));
        bean.setRkrBusyo(rs.getString("RKR_BUSYO"));
        bean.setRkrName(rs.getString("RKR_NAME"));
        bean.setRkrNum(rs.getString("RKR_NUM"));
        bean.setRkrUseKbn(rs.getInt("RKR_USE_KBN"));
        bean.setRkrContact(rs.getString("RKR_CONTACT"));
        bean.setRkrGuide(rs.getString("RKR_GUIDE"));
        bean.setRkrParkNum(rs.getString("RKR_PARK_NUM"));
        bean.setRkrPrintKbn(rs.getInt("RKR_PRINT_KBN"));
        bean.setRkrDest(rs.getString("RKR_DEST"));
        bean.setRkrAuid(rs.getInt("RKR_AUID"));
        bean.setRkrAdate(UDate.getInstanceTimestamp(rs.getTimestamp("RKR_ADATE")));
        bean.setRkrEuid(rs.getInt("RKR_EUID"));
        bean.setRkrEdate(UDate.getInstanceTimestamp(rs.getTimestamp("RKR_EDATE")));
        return bean;
    }
}
