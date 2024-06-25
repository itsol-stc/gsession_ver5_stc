package jp.groupsession.v2.sch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.sch.model.SchDataPubModel;

/**
 * <p>SCH_DATA_PUB Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class SchDataPubDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SchDataPubDao.class);

    /**
     * <p>Default Constructor
     */
    public SchDataPubDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public SchDataPubDao(Connection con) {
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
            sql.addSql("drop table SCH_DATA_PUB");

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
            sql.addSql(" create table SCH_DATA_PUB (");
            sql.addSql("   SCD_SID integer not null,");
            sql.addSql("   SDP_TYPE integer not null,");
            sql.addSql("   SDP_PSID integer not null,");
            sql.addSql("   primary key (SCD_SID,SDP_TYPE,SDP_PSID)");
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
     * <p>Insert SCH_DATA_PUB Data Bindding JavaBean
     * @param bean SCH_DATA_PUB Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(SchDataPubModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" SCH_DATA_PUB(");
            sql.addSql("   SCD_SID,");
            sql.addSql("   SDP_TYPE,");
            sql.addSql("   SDP_PSID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getScdSid());
            sql.addIntValue(bean.getSdpType());
            sql.addIntValue(bean.getSdpPsid());
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
    public void insert(List<SchDataPubModel> beanList) throws SQLException {

        if (beanList == null || beanList.size() <= 0) {
            return;
        }
        List<SchDataPubModel> exeList = new ArrayList<>();
        Iterator<SchDataPubModel> itr = beanList.iterator();
        StringBuilder sb = new StringBuilder();
        sb.append(" insert ");
        sb.append(" into ");
        sb.append(" SCH_DATA_PUB(");
        sb.append("   SCD_SID,");
        sb.append("   SDP_TYPE,");
        sb.append("   SDP_PSID");
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

            Iterator<SchDataPubModel> exeItr = exeList.iterator();
            while (exeItr.hasNext()) {
                SchDataPubModel bean = exeItr.next();
                sql.addSql(" (");
                sql.addSql("   ?,");
                sql.addSql("   ?,");
                sql.addSql("   ?");
                sql.addSql(" )");

                sql.addIntValue(bean.getScdSid());
                sql.addIntValue(bean.getSdpType());
                sql.addIntValue(bean.getSdpPsid());

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
     * <p>Insert SCH_DATA_PUB Data Bindding JavaBean
     * @param beanList SCH_DATA_PUB List
     * @throws SQLException SQL実行例外
     */
    @Deprecated
    //TODO_TASK 後で削除
    public void insertIkkatu(List<SchDataPubModel> beanList) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //500件づつ登録する
            for (int idx = 0; idx < beanList.size(); idx += 500) {
                //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" insert ");
                sql.addSql(" into ");
                sql.addSql(" SCH_DATA_PUB(");
                sql.addSql("   SCD_SID,");
                sql.addSql("   SDP_TYPE,");
                sql.addSql("   SDP_PSID");
                sql.addSql(" )");
                sql.addSql(" values");

                SchDataPubModel bean = new SchDataPubModel();
                for (int sdpIdx = idx; sdpIdx < beanList.size() && sdpIdx < idx + 500; sdpIdx++) {
                    sql.addSql(" (");
                    sql.addSql("   ?,");
                    sql.addSql("   ?,");
                    sql.addSql("   ?");
                    sql.addSql(" )");

                    if (sdpIdx == beanList.size() - 1 && sdpIdx == idx + 499) {
                        sql.addSql(";");
                    } else {
                        sql.addSql(",");
                    }
                    bean = beanList.get(sdpIdx);
                    sql.addIntValue(bean.getScdSid());
                    sql.addIntValue(bean.getSdpType());
                    sql.addIntValue(bean.getSdpPsid());
                }
                pstmt = con.prepareStatement(sql.toSqlString());
                sql.setParameter(pstmt);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Update SCH_DATA_PUB Data Bindding JavaBean
     * @param bean SCH_DATA_PUB Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(SchDataPubModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SCH_DATA_PUB");
            sql.addSql(" set ");
            sql.addSql(" where ");
            sql.addSql("   SCD_SID=?");
            sql.addSql(" and");
            sql.addSql("   SDP_TYPE=?");
            sql.addSql(" and");
            sql.addSql("   SDP_PSID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            //where
            sql.addIntValue(bean.getScdSid());
            sql.addIntValue(bean.getSdpType());
            sql.addIntValue(bean.getSdpPsid());

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
     * <p>Select SCH_DATA_PUB All Data
     * @return List in SCH_DATA_PUBModel
     * @throws SQLException SQL実行例外
     */
    public List<SchDataPubModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<SchDataPubModel> ret = new ArrayList<SchDataPubModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SCD_SID,");
            sql.addSql("   SDP_TYPE,");
            sql.addSql("   SDP_PSID");
            sql.addSql(" from ");
            sql.addSql("   SCH_DATA_PUB");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSchDataPubFromRs(rs));
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
     * <p>Select SCH_DATA_PUB
     * @param scdSid SCD_SID
     * @return SCH_DATA_PUBModel
     * @throws SQLException SQL実行例外
     */
    public List<SchDataPubModel> select(int scdSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<SchDataPubModel> ret = new ArrayList<SchDataPubModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SCD_SID,");
            sql.addSql("   SDP_TYPE,");
            sql.addSql("   SDP_PSID");
            sql.addSql(" from");
            sql.addSql("   SCH_DATA_PUB");
            sql.addSql(" where ");
            sql.addSql("   SCD_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(scdSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSchDataPubFromRs(rs));
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
     * <p>Select SCH_DATA_PUB
     * @param sids SCD_SID
     * @return SCH_DATA_PUBModel
     * @throws SQLException SQL実行例外
     */
    public Map<Integer, List<SchDataPubModel>> select(
            Collection<Integer> sids) throws SQLException {
        Connection con = null;
        Map<Integer, List<SchDataPubModel>> ret = new HashMap<>();
        if (sids == null || sids.isEmpty()) {
            return ret;
        }

        List<Integer> exeList = new ArrayList<>();
        Iterator<Integer> itr = sids.iterator();
        StringBuilder sb = new StringBuilder();
        sb.append(" select");
        sb.append("   SCD_SID,");
        sb.append("   SDP_TYPE,");
        sb.append("   SDP_PSID");
        sb.append(" from");
        sb.append("   SCH_DATA_PUB");
        sb.append(" where");

        con = getCon();

        while (itr.hasNext()) {
            exeList.add(itr.next());
            if (exeList.size() < 500
                    && itr.hasNext()) {
                continue;
            }

            //500件毎に実行
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(sb.toString());
            sql.addSql(" SCD_SID in (");

            Iterator<Integer> exeItr = exeList.iterator();
            while (exeItr.hasNext()) {
                sql.addSql("   ?");
                sql.addLongValue(exeItr.next());

                if (exeItr.hasNext()) {
                    sql.addSql(",");
                }
            }
            sql.addSql(" )");

            try (PreparedStatement pstmt = con.prepareStatement(sql.toSqlString());) {
                sql.setParameter(pstmt);
                log__.info(sql.toLogString());
                try (ResultSet rs = pstmt.executeQuery();) {

                    while (rs.next()) {
                        SchDataPubModel mdl = __getSchDataPubFromRs(rs);
                        List<SchDataPubModel> list = ret.get(mdl.getScdSid());
                        if (list == null) {
                            list = new ArrayList<>();
                            ret.put(mdl.getScdSid(), list);
                        }
                        list.add(mdl);
                    }

                }
            }
            exeList.clear();
        }
        return ret;
    }

    /**
     * <p>Select SCH_DATA_PUB
     * @param scdSid SCD_SID
     * @param sdpType SDP_TYPE
     * @param sdpPsid SDP_PSID
     * @return SCH_DATA_PUBModel
     * @throws SQLException SQL実行例外
     */
    public SchDataPubModel select(int scdSid, int sdpType, int sdpPsid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        SchDataPubModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SCD_SID,");
            sql.addSql("   SDP_TYPE,");
            sql.addSql("   SDP_PSID");
            sql.addSql(" from");
            sql.addSql("   SCH_DATA_PUB");
            sql.addSql(" where ");
            sql.addSql("   SCD_SID=?");
            sql.addSql(" and");
            sql.addSql("   SDP_TYPE=?");
            sql.addSql(" and");
            sql.addSql("   SDP_PSID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(scdSid);
            sql.addIntValue(sdpType);
            sql.addIntValue(sdpPsid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getSchDataPubFromRs(rs);
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
     * <br>[機  能] 対象スケジュールを参照可能か判定
     * <br>[解  説]
     * <br>[備  考]
     * @param scdSid 対象スケジュールSID
     * @param userSid セッションユーザSID
     * @return true:可能 false:不可能
     */
    public boolean select(int scdSid, int userSid) throws SQLException {

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
            sql.addSql("   SCH_DATA_PUB");
            sql.addSql(" where ");
            sql.addSql("   SCD_SID=?");
            sql.addSql(" and");
            sql.addSql("   (");
            sql.addSql("     (");
            sql.addSql("     SDP_TYPE=?");
            sql.addSql("   and");
            sql.addSql("     SDP_PSID=?");
            sql.addSql("     )");
            sql.addSql("   or");
            sql.addSql("     (");
            sql.addSql("     SDP_TYPE=?");
            sql.addSql("   and");
            sql.addSql("     SDP_PSID in");
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
            sql.addIntValue(scdSid);
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
     * <br>[機  能] 指定したスケジュールの公開対象を取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param scdSid スケジュールSID
     * @return スケジュール公開対象SIDリスト
     * @throws SQLException SQL実行時例外
     */
    public List<SchDataPubModel> getDspTarget(int scdSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<SchDataPubModel> ret = new ArrayList<SchDataPubModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SCD_SID,");
            sql.addSql("   SDP_TYPE,");
            sql.addSql("   SDP_PSID");
            sql.addSql(" from");
            sql.addSql("   SCH_DATA_PUB");
            sql.addSql(" where ");
            sql.addSql("   SCD_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(scdSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSchDataPubFromRs(rs));
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
     * <br>[機  能] 公開対象に指定ユーザが含まれるスケジュールのSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param checkScdSidList チェック対象のスケジュールSID
     * @return 公開対象に指定ユーザが含まれるスケジュールのSID
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getUserPubScdSidList(int userSid, List<Integer> checkScdSidList)
    throws SQLException {

        List<Integer> scdSidList = new ArrayList<Integer>();

        if (checkScdSidList == null || checkScdSidList.isEmpty()) {
            return scdSidList;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //500件毎に検索を行う
            SqlBuffer sql = null;
            for (int idx = 0; idx < checkScdSidList.size(); idx += 500) {
                sql = new SqlBuffer();
                sql.addSql(" select");
                sql.addSql("   SCH_DATA_PUB.SCD_SID as SCD_SID");
                sql.addSql(" from");
                sql.addSql("   SCH_DATA_PUB");
                sql.addSql("   left join");
                sql.addSql("     CMN_USRM");
                sql.addSql("   on");
                sql.addSql("     SDP_TYPE = ?");
                sql.addSql("   and");
                sql.addSql("     CMN_USRM.USR_SID = ?");
                sql.addSql("   and");
                sql.addSql("     SDP_PSID = CMN_USRM.USR_SID");
                sql.addSql("   left join");
                sql.addSql("     CMN_BELONGM");
                sql.addSql("   on ");
                sql.addSql("     SDP_TYPE = ?");
                sql.addSql("   and");
                sql.addSql("     CMN_BELONGM.USR_SID = ?");
                sql.addSql("   and");
                sql.addSql("     SDP_PSID = CMN_BELONGM.GRP_SID");
                sql.addIntValue(GSConstSchedule.SDP_TYPE_USER);
                sql.addIntValue(userSid);
                sql.addIntValue(GSConstSchedule.SDP_TYPE_GROUP);
                sql.addIntValue(userSid);

                sql.addSql(" where");
                sql.addSql("   SCD_SID in (");

                int startIdx = idx;
                for (int scdIdx = startIdx;
                scdIdx < checkScdSidList.size() && scdIdx < startIdx + 500; scdIdx++) {
                    if (scdIdx > startIdx) {
                        sql.addSql(",?");
                    } else {
                        sql.addSql("?");
                    }
                    sql.addIntValue(checkScdSidList.get(scdIdx));
                }

                sql.addSql("   )");
                sql.addSql(" and");
                sql.addSql("   (CMN_USRM.USR_SID is not null or CMN_BELONGM.GRP_SID is not null)");
                log__.info(sql.toLogString());

                pstmt = con.prepareStatement(sql.toSqlString());
                sql.setParameter(pstmt);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    scdSidList.add(rs.getInt("SCD_SID"));
                }

                JDBCUtil.closeResultSet(rs);
                JDBCUtil.closeStatement(pstmt);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return scdSidList;
    }

    /**
     * <p>Delete SCH_DATA_PUB
     * @param scdSid SCD_SID
     * @param sdpType SDP_TYPE
     * @param sdpPsid SDP_PSID
     * @throws SQLException SQL実行例外
     * @return 実行件数
     */
    public int delete(int scdSid, int sdpType, int sdpPsid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SCH_DATA_PUB");
            sql.addSql(" where ");
            sql.addSql("   SCD_SID=?");
            sql.addSql(" and");
            sql.addSql("   SDP_TYPE=?");
            sql.addSql(" and");
            sql.addSql("   SDP_PSID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(scdSid);
            sql.addIntValue(sdpType);
            sql.addIntValue(sdpPsid);

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
     * <br>[機  能] 指定されたスケジュールの公開対象情報を削除します
     * <br>[解  説]
     * <br>[備  考]
     * @param scdSidList スケジュールSIDリスト
     * @throws SQLException SQL実行時例外
     * @return 実行件数
     */
    public int delete(List<Integer> scdSidList) throws SQLException {

        int count = 0;
        if (scdSidList == null || scdSidList.size() < 1) {
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
            sql.addSql("   SCH_DATA_PUB");
            sql.addSql(" where ");
            sql.addSql("   SCD_SID in (");
            for (int idx = 0; idx < scdSidList.size(); idx++) {
                sql.addSql("     ?");
                sql.addIntValue(scdSidList.get(idx));
                if (idx != scdSidList.size() - 1) {
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
     * <p>Create SCH_DATA_PUB Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created SchDataPubModel
     * @throws SQLException SQL実行例外
     */
    private SchDataPubModel __getSchDataPubFromRs(ResultSet rs) throws SQLException {
        SchDataPubModel bean = new SchDataPubModel();
        bean.setScdSid(rs.getInt("SCD_SID"));
        bean.setSdpType(rs.getInt("SDP_TYPE"));
        bean.setSdpPsid(rs.getInt("SDP_PSID"));
        return bean;
    }

}
