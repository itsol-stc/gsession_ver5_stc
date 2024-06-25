package jp.groupsession.v2.cir.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cir.model.CirAccountSortModel;

/**
 * <p>CIR_ACCOUNT_SORT Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CirAccountSortDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CirAccountSortDao.class);

    /**
     * <p>Default Constructor
     */
    public CirAccountSortDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CirAccountSortDao(Connection con) {
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
            sql.addSql("drop table CIR_ACCOUNT_SORT");

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
            sql.addSql(" create table CIR_ACCOUNT_SORT (");
            sql.addSql("   CAC_SID NUMBER(10,0) not null,");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   CAS_SORT Date not null,");
            sql.addSql("   primary key (CAC_SID,USR_SID)");
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
     * <p>Insert CIR_ACCOUNT_SORT Data Bindding JavaBean
     * @param bean CIR_ACCOUNT_SORT Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CirAccountSortModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CIR_ACCOUNT_SORT(");
            sql.addSql("   CAC_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   CAS_SORT");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCacSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addLongValue(bean.getCasSort());
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
     * <p>Update CIR_ACCOUNT_SORT Data Bindding JavaBean
     * @param bean CIR_ACCOUNT_SORT Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CirAccountSortModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CIR_ACCOUNT_SORT");
            sql.addSql(" set ");
            sql.addSql("   CAS_SORT=?");
            sql.addSql(" where ");
            sql.addSql("   CAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(bean.getCasSort());
            //where
            sql.addIntValue(bean.getCacSid());
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
     * <p>Select CIR_ACCOUNT_SORT All Data
     * @return List in CIR_ACCOUNT_SORTModel
     * @throws SQLException SQL実行例外
     */
    public List<CirAccountSortModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CirAccountSortModel> ret = new ArrayList<CirAccountSortModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CAC_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   CAS_SORT");
            sql.addSql(" from ");
            sql.addSql("   CIR_ACCOUNT_SORT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCirAccountSortFromRs(rs));
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
     * <p>Select CIR_ACCOUNT_SORT
     * @param wacSid CAC_SID
     * @param usrSid USR_SID
     * @return CIR_ACCOUNT_SORTModel
     * @throws SQLException SQL実行例外
     */
    public CirAccountSortModel select(int wacSid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CirAccountSortModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CAC_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   CAS_SORT");
            sql.addSql(" from");
            sql.addSql("   CIR_ACCOUNT_SORT");
            sql.addSql(" where ");
            sql.addSql("   CAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wacSid);
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCirAccountSortFromRs(rs);
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
     * <p>レコード件数を取得する
     * @param wacSid CAC_SID
     * @param usrSid USR_SID
     * @return CIR_ACCOUNT_SORTModel
     * @throws SQLException SQL実行例外
     */
    public int count(int wacSid, int usrSid) throws SQLException {

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
            sql.addSql("   CIR_ACCOUNT_SORT");
            sql.addSql(" where ");
            sql.addSql("   CAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wacSid);
            sql.addIntValue(usrSid);

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
     * <p>Delete CIR_ACCOUNT_SORT
     * @param wacSid CAC_SID
     * @param usrSid USR_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int wacSid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CIR_ACCOUNT_SORT");
            sql.addSql(" where ");
            sql.addSql("   CAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wacSid);
            sql.addIntValue(usrSid);

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
     * <p>Delete CIR_ACCOUNT_SORT
     * @param usrSid USR_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int deleteAllSort(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CIR_ACCOUNT_SORT");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);

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
     * <br>[機  能] アカウントの並び順の登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param accountSid アカウントSID
     * @param sessionSid セッションSID
     * @throws SQLException SQL実行時例外
     */
    public void insertAccountSort(int accountSid, int sessionSid) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert into CIR_ACCOUNT_SORT ");
            sql.addSql(" select ");
            sql.addSql("    ?, ");
            sql.addSql("    CMN_USRM.USR_SID, ");
            sql.addSql("    max(COALESCE(CIR_ACCOUNT_SORT.CAS_SORT, 0)) + 1 ");
            sql.addSql("  from ");
            sql.addSql("    CMN_USRM");
            sql.addSql("  left join ");
            sql.addSql("    CIR_ACCOUNT_SORT ");
            sql.addSql("  on ");
            sql.addSql("    CMN_USRM.USR_SID = CIR_ACCOUNT_SORT.USR_SID ");
            sql.addSql("  where ");
            sql.addSql("    CMN_USRM.USR_SID = ? ");
            sql.addSql("  group by ");
            sql.addSql("    CMN_USRM.USR_SID ");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(accountSid);
            sql.addIntValue(sessionSid);

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
     * <br>[機  能] アカウント(グループ)の並び順の登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param accountSid アカウントSID
     * @param sml040userKbnGroup グループ
     * @throws SQLException SQL実行時例外
     */
    public void insertAccountSortGp(int accountSid,
                                   String[] sml040userKbnGroup) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert into CIR_ACCOUNT_SORT ");
            sql.addSql(" select ");
            sql.addSql("    ?, ");
            sql.addSql("    CMN_USRM.USR_SID, ");
            sql.addSql("    max(COALESCE(CIR_ACCOUNT_SORT.CAS_SORT, 0)) + 1 ");
            sql.addSql("  from ");
            sql.addSql("    CMN_BELONGM,");
            sql.addSql("    CMN_USRM");
            sql.addSql("  left join ");
            sql.addSql("    CIR_ACCOUNT_SORT ");
            sql.addSql("  on ");
            sql.addSql("    CMN_USRM.USR_SID = CIR_ACCOUNT_SORT.USR_SID ");
            sql.addSql("  where ");
            sql.addSql("    CMN_USRM.USR_SID = CMN_BELONGM.USR_SID ");
            sql.addSql("  and ");
            sql.addSql("    CMN_BELONGM.GRP_SID in ( ");
            for (int i = 0; i < sml040userKbnGroup.length; i++) {
                if (sml040userKbnGroup.length == 1 || (i == sml040userKbnGroup.length - 1)) {
                    sql.addSql("  ?  ");
                } else {
                    sql.addSql("  ?,  ");
                }
            }
            sql.addSql(" ) ");
            sql.addSql("  group by ");
            sql.addSql("    CMN_USRM.USR_SID ");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(accountSid);
            for (int i = 0; i < sml040userKbnGroup.length; i++) {
                sql.addIntValue(Integer.parseInt(sml040userKbnGroup[i]));
            }

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
     * <br>[機  能] アカウント(ユーザ)の並び順の登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param accountSid アカウントSID
     * @param sml040userKbnUser ユーザ
     * @throws SQLException SQL実行時例外
     */
    public void insertAccountSortUsr(int accountSid,
                                   String[] sml040userKbnUser) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert into CIR_ACCOUNT_SORT ");
            sql.addSql(" select ");
            sql.addSql("    ?, ");
            sql.addSql("    CMN_USRM.USR_SID, ");
            sql.addSql("    max(COALESCE(CIR_ACCOUNT_SORT.CAS_SORT, 0)) + 1 ");
            sql.addSql(" from ");
            sql.addSql("    CMN_USRM");
            sql.addSql(" left join ");
            sql.addSql("    CIR_ACCOUNT_SORT ");
            sql.addSql(" on ");
            sql.addSql("    CMN_USRM.USR_SID = CIR_ACCOUNT_SORT.USR_SID ");
            sql.addSql(" where ");
            sql.addSql("    CMN_USRM.USR_SID in ( ");
            for (int i = 0; i < sml040userKbnUser.length; i++) {
                if (sml040userKbnUser.length == 1 || (i == sml040userKbnUser.length - 1)) {
                    sql.addSql("  ?  ");
                } else {
                    sql.addSql("  ?,  ");
                }

            }
            sql.addSql(" ) ");
            sql.addSql("  group by ");
            sql.addSql("    CMN_USRM.USR_SID ");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(accountSid);
            for (int i = 0; i < sml040userKbnUser.length; i++) {
                sql.addIntValue(Integer.parseInt(sml040userKbnUser[i]));
            }

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
     * <br>[機  能] アカウント(グループ)の並び順の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param accountSid アカウントSID
     * @param sml040userKbnGroup グループ
     * @throws SQLException SQL実行時例外
     */
    public void delAccountSortGp(int accountSid,
                                   String[] sml040userKbnGroup) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete from CIR_ACCOUNT_SORT ");
            sql.addSql(" where ");
            sql.addSql("   CAC_SID = ? ");
            sql.addSql(" and ");
            sql.addSql("   USR_SID not in ( ");
            sql.addSql("   select  ");
            sql.addSql("     USR_SID  ");
            sql.addSql("   from ");
            sql.addSql("     CMN_BELONGM");
            sql.addSql("   where ");
            sql.addSql("     GRP_SID in ( ");
            for (int i = 0; i < sml040userKbnGroup.length; i++) {
                if (sml040userKbnGroup.length == 1 || (i == sml040userKbnGroup.length - 1)) {
                    sql.addSql("  ?  ");
                } else {
                    sql.addSql("  ?,  ");
                }
            }
            sql.addSql(" )) ");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(accountSid);
            for (int i = 0; i < sml040userKbnGroup.length; i++) {
                sql.addIntValue(Integer.parseInt(sml040userKbnGroup[i]));
            }

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
     * <br>[機  能] アカウント(ユーザ)の並び順の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param accountSid アカウントSID
     * @param sml040userKbnUser ユーザ
     * @throws SQLException SQL実行時例外
     */
    public void delAccountSortUsr(int accountSid,
                                   String[] sml040userKbnUser) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete from CIR_ACCOUNT_SORT ");
            sql.addSql(" where ");
            sql.addSql("   CAC_SID = ? ");
            sql.addSql(" and ");
            sql.addSql("   USR_SID not in ( ");
            for (int i = 0; i < sml040userKbnUser.length; i++) {
                if (sml040userKbnUser.length == 1 || (i == sml040userKbnUser.length - 1)) {
                    sql.addSql("  ?  ");
                } else {
                    sql.addSql("  ?,  ");
                }

            }
            sql.addSql(" ) ");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(accountSid);
            for (int i = 0; i < sml040userKbnUser.length; i++) {
                sql.addIntValue(NullDefault.getInt(sml040userKbnUser[i], -1));
            }

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
     * <br>[機  能] アカウントの並び順の新規登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param accountSid アカウントSID
     * @param sml040userKbnGroup グループ
     * @throws SQLException SQL実行時例外
     */
    public void updateAccountSort(int accountSid,
                                    String[] sml040userKbnGroup) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert into CIR_ACCOUNT_SORT ");
            sql.addSql(" select ");
            sql.addSql("    ?, ");
            sql.addSql("    CMN_USRM.USR_SID, ");
            sql.addSql("    max(COALESCE(CIR_ACCOUNT_SORT.CAS_SORT, 0)) + 1 ");
            sql.addSql("  from ");
            sql.addSql("    CMN_BELONGM,");
            sql.addSql("    CMN_USRM");
            sql.addSql("  left join ");
            sql.addSql("    CIR_ACCOUNT_SORT ");
            sql.addSql("  on ");
            sql.addSql("    CMN_USRM.USR_SID = CIR_ACCOUNT_SORT.USR_SID ");
            sql.addSql("  where ");
            sql.addSql("    CMN_USRM.USR_SID = CMN_BELONGM.USR_SID ");
            sql.addSql("  and ");
            sql.addSql("    CMN_BELONGM.GRP_SID in ( ");
            for (int i = 0; i < sml040userKbnGroup.length; i++) {
                if (sml040userKbnGroup.length == 1 || (i == sml040userKbnGroup.length - 1)) {
                    sql.addSql("  ?  ");
                } else {
                    sql.addSql("  ?,  ");
                }
            }
            sql.addSql(" ) ");
            sql.addSql(" and ");
            sql.addSql("   CMN_USRM.USR_SID not in ( ");
            sql.addSql("   select ");
            sql.addSql("     USR_SID ");
            sql.addSql("   from ");
            sql.addSql("     CIR_ACCOUNT_SORT ");
            sql.addSql("   where ");
            sql.addSql("     CAC_SID = ? ");
            sql.addSql(" ) ");
            sql.addSql("  group by ");
            sql.addSql("    CMN_USRM.USR_SID ");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(accountSid);
            for (int i = 0; i < sml040userKbnGroup.length; i++) {
                sql.addIntValue(NullDefault.getInt(sml040userKbnGroup[i], -1));
            }
            sql.addIntValue(accountSid);

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
     * <br>[機  能] アカウント(ユーザ)の新規登録の登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param acutSaiSid 採番アカウントSID
     * @param sml040userKbnUser ユーザ
     * @param accountSid アカウントSID
     * @throws SQLException SQL実行時例外
     */
    public void updateAccountSortUsr(int acutSaiSid,
                                   String[] sml040userKbnUser,
                                   int accountSid) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert into CIR_ACCOUNT_SORT ");
            sql.addSql(" select ");
            sql.addSql("    ?, ");
            sql.addSql("    CMN_USRM.USR_SID, ");
            sql.addSql("    max(COALESCE(CIR_ACCOUNT_SORT.CAS_SORT, 0)) + 1 ");
            sql.addSql(" from ");
            sql.addSql("    CMN_USRM");
            sql.addSql(" left join ");
            sql.addSql("    CIR_ACCOUNT_SORT ");
            sql.addSql(" on ");
            sql.addSql("    CMN_USRM.USR_SID = CIR_ACCOUNT_SORT.USR_SID ");
            sql.addSql(" where ");
            sql.addSql("    CMN_USRM.USR_SID in ( ");
            for (int i = 0; i < sml040userKbnUser.length; i++) {
                if (sml040userKbnUser.length == 1 || (i == sml040userKbnUser.length - 1)) {
                    sql.addSql("  ?  ");
                } else {
                    sql.addSql("  ?,  ");
                }

            }
            sql.addSql(" ) ");
            sql.addSql(" and ");
            sql.addSql("   CMN_USRM.USR_SID not in ( ");
            sql.addSql("   select ");
            sql.addSql("     USR_SID ");
            sql.addSql("   from ");
            sql.addSql("     CIR_ACCOUNT_SORT ");
            sql.addSql("   where ");
            sql.addSql("     CAC_SID = ? ");
            sql.addSql(" ) ");
            sql.addSql("  group by ");
            sql.addSql("    CMN_USRM.USR_SID ");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(acutSaiSid);
            for (int i = 0; i < sml040userKbnUser.length; i++) {
                sql.addIntValue(NullDefault.getInt(sml040userKbnUser[i], -1));
            }
            sql.addIntValue(accountSid);
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
     * <p>Create CIR_ACCOUNT_SORT Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CirAccountSortModel
     * @throws SQLException SQL実行例外
     */
    private CirAccountSortModel __getCirAccountSortFromRs(ResultSet rs) throws SQLException {
        CirAccountSortModel bean = new CirAccountSortModel();
        bean.setCacSid(rs.getInt("CAC_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setCasSort(rs.getInt("CAS_SORT"));
        return bean;
    }
}
