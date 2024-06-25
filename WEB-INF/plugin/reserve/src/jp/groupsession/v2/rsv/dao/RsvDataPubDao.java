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
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.rsv.model.RsvDataPubModel;

/**
 * <p>RSV_DATA_PUB Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class RsvDataPubDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RsvDataPubDao.class);

    /**
     * <p>Default Constructor
     */
    public RsvDataPubDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public RsvDataPubDao(Connection con) {
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
            sql.addSql("drop table RSV_DATA_PUB");

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
            sql.addSql(" create table RSV_DATA_PUB (");
            sql.addSql("   RSY_SID integer not null,");
            sql.addSql("   RDP_TYPE integer not null,");
            sql.addSql("   RDP_PSID integer not null,");
            sql.addSql("   primary key (RSY_SID,RDP_TYPE,RDP_PSID)");
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
     * <p>Insert RSV_DATA_PUB Data Bindding JavaBean
     * @param bean RSV_DATA_PUB Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(RsvDataPubModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" RSV_DATA_PUB(");
            sql.addSql("   RSY_SID,");
            sql.addSql("   RDP_TYPE,");
            sql.addSql("   RDP_PSID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRsySid());
            sql.addIntValue(bean.getRdpType());
            sql.addIntValue(bean.getRdpPsid());
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
     * <p>Insert RSV_DATA_PUB Data Bindding JavaBean
     * @param beanList RSV_DATA_PUB List Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(List<RsvDataPubModel> beanList) throws SQLException {

        if (beanList == null || beanList.size() <= 0) {
            return;
        }
        List<RsvDataPubModel> exeList = new ArrayList<>();
        Iterator<RsvDataPubModel> itr = beanList.iterator();
        StringBuilder sb = new StringBuilder();
        sb.append(" insert ");
        sb.append(" into ");
        sb.append(" RSV_DATA_PUB(");
        sb.append("   RSY_SID,");
        sb.append("   RDP_TYPE,");
        sb.append("   RDP_PSID");
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

            Iterator<RsvDataPubModel> exeItr = exeList.iterator();
            while (exeItr.hasNext()) {
                RsvDataPubModel bean = exeItr.next();
                sql.addSql(" (");
                sql.addSql("   ?,");
                sql.addSql("   ?,");
                sql.addSql("   ?");
                sql.addSql(" )");

                sql.addIntValue(bean.getRsySid());
                sql.addIntValue(bean.getRdpType());
                sql.addIntValue(bean.getRdpPsid());

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
     * <p>Update RSV_DATA_PUB Data Bindding JavaBean
     * @param bean RSV_DATA_PUB Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(RsvDataPubModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RSV_DATA_PUB");
            sql.addSql(" set ");
            sql.addSql(" where ");
            sql.addSql("   RSY_SID=?");
            sql.addSql(" and");
            sql.addSql("   RDP_TYPE=?");
            sql.addSql(" and");
            sql.addSql("   RDP_PSID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            //where
            sql.addIntValue(bean.getRsySid());
            sql.addIntValue(bean.getRdpType());
            sql.addIntValue(bean.getRdpPsid());

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
     * <p>Select RSV_DATA_PUB All Data
     * @return List in RSV_DATA_PUBModel
     * @throws SQLException SQL実行例外
     */
    public List<RsvDataPubModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RsvDataPubModel> ret = new ArrayList<RsvDataPubModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RSY_SID,");
            sql.addSql("   RDP_TYPE,");
            sql.addSql("   RDP_PSID");
            sql.addSql(" from ");
            sql.addSql("   RSV_DATA_PUB");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRsvDataPubFromRs(rs));
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
     * <br>[機  能] 施設予約SIDに対応する公開対象を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param rsySid 施設予約SID
     * @return 公開対象リスト
     * @throws SQLException SQL実行例外
     */
    public List<RsvDataPubModel> select(int rsySid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RsvDataPubModel> ret = new ArrayList<RsvDataPubModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RSY_SID,");
            sql.addSql("   RDP_TYPE,");
            sql.addSql("   RDP_PSID");
            sql.addSql(" from ");
            sql.addSql("   RSV_DATA_PUB");
            sql.addSql(" where ");
            sql.addSql("   RSY_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rsySid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRsvDataPubFromRs(rs));
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
     * <br>[機  能] 対象施設予約を参照可能か判定
     * <br>[解  説]
     * <br>[備  考]
     * @param rsySid 対象施設予約SID
     * @param userSid セッションユーザSID
     * @return true:可能 false:不可能
     */
    public boolean select(int rsySid, int userSid) throws SQLException {

        int count = 0;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(*) CNT");
            sql.addSql(" from");
            sql.addSql("   RSV_DATA_PUB");
            sql.addSql(" where ");
            sql.addSql("   RSY_SID=?");
            sql.addSql(" and");
            sql.addSql("   (");
            sql.addSql("     (");
            sql.addSql("     RDP_TYPE=?");
            sql.addSql("   and");
            sql.addSql("     RDP_PSID=?");
            sql.addSql("     )");
            sql.addSql("   or");
            sql.addSql("     (");
            sql.addSql("     RDP_TYPE=?");
            sql.addSql("   and");
            sql.addSql("     RDP_PSID in");
            sql.addSql("       (");
            sql.addSql("       select");
            sql.addSql("         GRP_SID");
            sql.addSql("       from");
            sql.addSql("         CMN_BELONGM");
            sql.addSql("       where");
            sql.addSql("         USR_SID=?");
            sql.addSql("       )");
            sql.addSql("     )");
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rsySid);
            sql.addIntValue(GSConstSchedule.USER_KBN_USER);
            sql.addIntValue(userSid);
            sql.addIntValue(GSConstSchedule.USER_KBN_GROUP);
            sql.addIntValue(userSid);

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
        return count > 0;
    }

    /**
     * <p>Select RSV_DATA_PUB
     * @param rsySid RSY_SID
     * @param rdpType RDP_TYPE
     * @param rdpPsid RDP_PSID
     * @return RSV_DATA_PUBModel
     * @throws SQLException SQL実行例外
     */
    public RsvDataPubModel select(int rsySid, int rdpType, int rdpPsid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        RsvDataPubModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSY_SID,");
            sql.addSql("   RDP_TYPE,");
            sql.addSql("   RDP_PSID");
            sql.addSql(" from");
            sql.addSql("   RSV_DATA_PUB");
            sql.addSql(" where ");
            sql.addSql("   RSY_SID=?");
            sql.addSql(" and");
            sql.addSql("   RDP_TYPE=?");
            sql.addSql(" and");
            sql.addSql("   RDP_PSID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rsySid);
            sql.addIntValue(rdpType);
            sql.addIntValue(rdpPsid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getRsvDataPubFromRs(rs);
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
     * <br>[機  能] 施設予約SIDに対応する公開対象を削除
     * <br>[解  説]
     * <br>[備  考]
     * @param rsySid 施設予約SID
     * @return 公開対象リスト
     * @throws SQLException SQL実行例外
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
            sql.addSql("   RSV_DATA_PUB");
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
     * <br>[機  能] 施設予約SIDに対応する公開対象を削除
     * <br>[解  説]
     * <br>[備  考]
     * @param rsySidList 施設予約SIDリスト
     * @return 公開対象リスト
     * @throws SQLException SQL実行例外
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
            sql.addSql("   RSV_DATA_PUB");
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
     * <br>[機  能] 指定されたスケジュール施設予約SIDの公開情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param scdRsSid スケジュール施設予約SID
     * @return count delete count
     * @throws SQLException SQL実行例外
     */
    public int deleteScdRsSid(int scdRsSid) throws SQLException {
        if (scdRsSid <= 0) {
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
            sql.addSql("   RSV_DATA_PUB");
            sql.addSql(" where ");
            sql.addSql("   RSY_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(scdRsSid);
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
     * <br>[機  能] 施設予約SIDに対応する公開対象一覧を削除
     * <br>[解  説]
     * <br>[備  考]
     * @param rsySidList 施設予約SIDリスト
     * @throws SQLException SQL実行例外
     */
    public void deleteList(ArrayList<Integer> rsySidList)
            throws SQLException {

        if (rsySidList == null || rsySidList.size() <= 0) {
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
            sql.addSql("   RSV_DATA_PUB");
            sql.addSql(" where ");
            sql.addSql("   RSY_SID in (");
            for (int i = 0; i < rsySidList.size(); i++) {
                sql.addSql(" ? ");
                sql.addIntValue(rsySidList.get(i));
                if (i != rsySidList.size() - 1) {
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
     * <p>Delete RSV_DATA_PUB
     * @param rsySid RSY_SID
     * @param rdpType RDP_TYPE
     * @param rdpPsid RDP_PSID
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int rsySid, int rdpType, int rdpPsid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RSV_DATA_PUB");
            sql.addSql(" where ");
            sql.addSql("   RSY_SID=?");
            sql.addSql(" and");
            sql.addSql("   RDP_TYPE=?");
            sql.addSql(" and");
            sql.addSql("   RDP_PSID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rsySid);
            sql.addIntValue(rdpType);
            sql.addIntValue(rdpPsid);

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
     * <p>Create RSV_DATA_PUB Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created RsvDataPubModel
     * @throws SQLException SQL実行例外
     */
    private RsvDataPubModel __getRsvDataPubFromRs(ResultSet rs) throws SQLException {
        RsvDataPubModel bean = new RsvDataPubModel();
        bean.setRsySid(rs.getInt("RSY_SID"));
        bean.setRdpType(rs.getInt("RDP_TYPE"));
        bean.setRdpPsid(rs.getInt("RDP_PSID"));
        return bean;
    }
}
