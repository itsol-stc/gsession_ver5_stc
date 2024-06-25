package jp.groupsession.v2.cht.dao;

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
import jp.groupsession.v2.cht.GSConstChat;
import jp.groupsession.v2.cht.model.ChtUserPairModel;
import jp.groupsession.v2.cmn.GSConst;

/**
 * <p>CHT_USER_PAIR Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class ChtUserPairDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ChtUserPairDao.class);

    /**
     * <p>Default Constructor
     */
    public ChtUserPairDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public ChtUserPairDao(Connection con) {
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
            sql.addSql("drop table CHT_USER_PAIR");

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
            sql.addSql(" create table CHT_USER_PAIR (");
            sql.addSql("   CUP_SID integer not null,");
            sql.addSql("   CUP_UID_F integer not null,");
            sql.addSql("   CUP_UID_S integer not null,");
            sql.addSql("   CUP_COMP_FLG integer not null,");
            sql.addSql("   primary key (CUP_SID)");
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
     * <p>全件数を取得する
     * @return List in ChtUserPairModel
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
            sql.addSql("   CHT_USER_PAIR");

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
     * <p>Insert CHT_USER_PAIR Data Bindding JavaBean
     * @param bean CHT_USER_PAIR Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(ChtUserPairModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CHT_USER_PAIR(");
            sql.addSql("   CUP_SID,");
            sql.addSql("   CUP_UID_F,");
            sql.addSql("   CUP_UID_S,");
            sql.addSql("   CUP_COMP_FLG");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCupSid());
            sql.addIntValue(bean.getCupUidF());
            sql.addIntValue(bean.getCupUidS());
            sql.addIntValue(bean.getCupCompFlg());
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
     * <br>[機  能] チャットユーザペア情報を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param beanList ChtGroupUserModel DataList
     * @throws SQLException SQL実行例外
     */
    public void insertData(List<ChtUserPairModel> beanList) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        if (beanList == null || beanList.size() <= 0) {
            return;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CHT_USER_PAIR(");
            sql.addSql("   CUP_SID,");
            sql.addSql("   CUP_UID_F,");
            sql.addSql("   CUP_UID_S,");
            sql.addSql("   CUP_COMP_FLG");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            for (ChtUserPairModel bean : beanList) {
                sql.addIntValue(bean.getCupSid());
                sql.addIntValue(bean.getCupUidF());
                sql.addIntValue(bean.getCupUidS());
                sql.addIntValue(bean.getCupCompFlg());
                log__.info(sql.toLogString());

                sql.setParameter(pstmt);
                pstmt.executeUpdate();
                sql.clearValue();
            }


        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }


    /**
     * <p>Update CHT_USER_PAIR Data Bindding JavaBean
     * @param bean CHT_USER_PAIR Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(ChtUserPairModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CHT_USER_PAIR");
            sql.addSql(" set ");
            sql.addSql("   CUP_UID_F=?,");
            sql.addSql("   CUP_UID_S=?,");
            sql.addSql("   CUP_COMP_FLG=?");
            sql.addSql(" where ");
            sql.addSql("   CUP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCupUidF());
            sql.addIntValue(bean.getCupUidS());
            sql.addIntValue(bean.getCupCompFlg());
            //where
            sql.addIntValue(bean.getCupSid());

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
     * <p>Update CHT_USER_PAIR Data Bindding JavaBean
     * @param cupSid cupsid
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(int cupSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CHT_USER_PAIR");
            sql.addSql(" set ");
            sql.addSql("   CUP_COMP_FLG=?");
            sql.addSql(" where ");
            sql.addSql("   CUP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(GSConstChat.CHAT_USER_COMP);
            //where
            sql.addIntValue(cupSid);

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
     * <p>Select CHT_USER_PAIR All Data
     * @return List in CHT_USER_PAIRModel
     * @throws SQLException SQL実行例外
     */
    public List<ChtUserPairModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ChtUserPairModel> ret = new ArrayList<ChtUserPairModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CUP_SID,");
            sql.addSql("   CUP_UID_F,");
            sql.addSql("   CUP_UID_S,");
            sql.addSql("   CUP_COMP_FLG");
            sql.addSql(" from ");
            sql.addSql("   CHT_USER_PAIR");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getChtUserPairFromRs(rs));
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
     * <p>Select CHT_GROUP_USER All Data
     * @param offset レコードの読取開始行
     * @param limit 1ページの最大件数
     * @return List in CHT_USER_PAIRModel
     * @throws SQLException SQL実行例外
     */
    public List<ChtUserPairModel> selectLimit(
            int offset, int limit) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ChtUserPairModel> ret = new ArrayList<ChtUserPairModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CUP_SID,");
            sql.addSql("   CUP_UID_F,");
            sql.addSql("   CUP_UID_S,");
            sql.addSql("   CUP_COMP_FLG");
            sql.addSql(" from ");
            sql.addSql("   CHT_USER_PAIR");
            sql.addSql(" order by ");
            sql.addSql("   CUP_SID asc");

            sql.setPagingValue(offset, limit);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getChtUserPairFromRs(rs));
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
     * <p>Select CHT_USER_PAIR
     * @param cupSid CUP_SID
     * @return CHT_USER_PAIRModel
     * @throws SQLException SQL実行例外
     */
    public ChtUserPairModel select(int cupSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ChtUserPairModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CUP_SID,");
            sql.addSql("   CUP_UID_F,");
            sql.addSql("   CUP_UID_S,");
            sql.addSql("   CUP_COMP_FLG");
            sql.addSql(" from");
            sql.addSql("   CHT_USER_PAIR");
            sql.addSql(" where ");
            sql.addSql("   CUP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(cupSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getChtUserPairFromRs(rs);
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
     * <p>Delete CHT_USER_PAIR
     * @param cupSid CUP_SID
     * @throws SQLException SQL実行例外
     * @return count
     */
    public int delete(int cupSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CHT_USER_PAIR");
            sql.addSql(" where ");
            sql.addSql("   CUP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(cupSid);

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
     * <p>Delete CHT_USER_PAIR
     * @param sidList CUP_SIDList
     * @throws SQLException SQL実行例外
     * @return count
     */
    public int delete(List<Integer> sidList) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CHT_USER_PAIR");
            sql.addSql(" where ");
            sql.addSql("   CUP_SID IN (");
            for (int idx = 0; idx < sidList.size(); idx++) {
                if (idx != 0) {
                    sql.addSql(" , ");
                }
                sql.addSql(" ? ");
                sql.addIntValue(sidList.get(idx));
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
     * <p>Select CUP_SID
     * @param sessionSid ユーザSID1
     * @param partnerUsrSid ユーザSID2
     * @return CHT_USER_PAIRModel
     * @throws SQLException SQL実行例外
     */
    public int select(int sessionSid, int partnerUsrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CUP_SID");
            sql.addSql(" from");
            sql.addSql("   CHT_USER_PAIR");
            sql.addSql(" where ");
            sql.addSql(" (");
            sql.addSql("    CUP_UID_F=?");
            sql.addSql("  and ");
            sql.addSql("    CUP_UID_S=?");
            sql.addSql(" ) or");
            sql.addSql(" (");
            sql.addSql("    CUP_UID_F=?");
            sql.addSql("  and ");
            sql.addSql("    CUP_UID_S=?");
            sql.addSql(" ) ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sessionSid);
            sql.addIntValue(partnerUsrSid);
            sql.addIntValue(partnerUsrSid);
            sql.addIntValue(sessionSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getInt("CUP_SID");
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
     * <p>Select CUP_SID
     * @return CHT_USER_PAIRModel
     * @throws SQLException SQL実行例外
     */
    public List<Integer> selectCupSid() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CUP_SID");
            sql.addSql(" from");
            sql.addSql("   CHT_USER_PAIR");
            sql.addSql(" where ");
            sql.addSql("   CUP_COMP_FLG = ? ");
            sql.addIntValue(GSConstChat.CHAT_USER_COMP);
            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getInt("CUP_SID"));
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
     * <p>Create CHT_USER_PAIR Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created ChtUserPairModel
     * @throws SQLException SQL実行例外
     */
    private ChtUserPairModel __getChtUserPairFromRs(ResultSet rs) throws SQLException {
        ChtUserPairModel bean = new ChtUserPairModel();
        bean.setCupSid(rs.getInt("CUP_SID"));
        bean.setCupUidF(rs.getInt("CUP_UID_F"));
        bean.setCupUidS(rs.getInt("CUP_UID_S"));
        bean.setCupCompFlg(rs.getInt("CUP_COMP_FLG"));
        return bean;
    }

    /**
     * <p>Select deleteUserSid
     * @param userSid ユーザSID
     * @return CHT_GROUP_USERModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Integer> selectCupSidForUserDel(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    CUP_SID");
            sql.addSql("  from");
            sql.addSql("    (select");
            sql.addSql("       CHT_USER_PAIR.CUP_SID,");
            sql.addSql("       case ");
            sql.addSql("         when CHT_USER_PAIR.CUP_UID_F = ? then CHT_USER_PAIR.CUP_UID_S");
            sql.addSql("         else CHT_USER_PAIR.CUP_UID_F");
            sql.addSql("       end as USR_SID");
            sql.addSql("     from");
            sql.addSql("       CHT_USER_PAIR");
            sql.addSql("     where");
            sql.addSql("       (");
            sql.addSql("          CHT_USER_PAIR.CUP_UID_F = ?");
            sql.addSql("        or");
            sql.addSql("          CHT_USER_PAIR.CUP_UID_S = ?");
            sql.addSql("       )");
            sql.addSql("    ) CUP_TABLE");
            sql.addSql("  left join");
            sql.addSql("    CMN_USRM");
            sql.addSql("  on");
            sql.addSql("    CUP_TABLE.USR_SID = CMN_USRM.USR_SID");
            sql.addSql("  where");
            sql.addSql("    CMN_USRM.USR_JKBN = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(userSid);
            sql.addIntValue(userSid);
            sql.addIntValue(userSid);
            sql.addIntValue(GSConst.JTKBN_DELETE);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getInt("CUP_SID"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }
}
