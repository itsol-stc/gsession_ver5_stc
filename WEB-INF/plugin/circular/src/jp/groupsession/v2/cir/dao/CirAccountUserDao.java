package jp.groupsession.v2.cir.dao;

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
import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.model.CirAccountUserModel;

/**
 * <p>CIR_ACCOUNT_USER Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CirAccountUserDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CirAccountUserDao.class);

    /**
     * <p>Default Constructor
     */
    public CirAccountUserDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CirAccountUserDao(Connection con) {
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
            sql.addSql("drop table CIR_ACCOUNT_USER");

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
            sql.addSql(" create table CIR_ACCOUNT_USER (");
            sql.addSql("   CAC_SID NUMBER(10,0) not null,");
            sql.addSql("   GRP_SID NUMBER(10,0) not null,");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   primary key (CAC_SID)");
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
     * <p>Insert CIR_ACCOUNT_USER Data Bindding JavaBean
     * @param bean CIR_ACCOUNT_USER Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CirAccountUserModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CIR_ACCOUNT_USER(");
            sql.addSql("   CAC_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   USR_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCacSid());
            sql.addIntValue(bean.getGrpSid());
            sql.addIntValue(bean.getUsrSid());
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
     * <p>Insert CIR_ACCOUNT_USER Data Bindding JavaBean
     * @param bean CIR_ACCOUNT_USER Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insertUser(CirAccountUserModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CIR_ACCOUNT_USER(");
            sql.addSql("   CAC_SID,");
            sql.addSql("   USR_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCacSid());
            sql.addIntValue(bean.getUsrSid());
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
     * <p>Insert CIR_ACCOUNT_USER Data Bindding JavaBean
     * @param bean CIR_ACCOUNT_USER Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insertGrp(CirAccountUserModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CIR_ACCOUNT_USER(");
            sql.addSql("   CAC_SID,");
            sql.addSql("   GRP_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCacSid());
            sql.addIntValue(bean.getGrpSid());
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
     * <p>Update CIR_ACCOUNT_USER Data Bindding JavaBean
     * @param bean CIR_ACCOUNT_USER Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CirAccountUserModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CIR_ACCOUNT_USER");
            sql.addSql(" set ");
            sql.addSql("   GRP_SID=?,");
            sql.addSql("   USR_SID=?");
            sql.addSql(" where ");
            sql.addSql("   CAC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getGrpSid());
            sql.addIntValue(bean.getUsrSid());
            //where
            sql.addIntValue(bean.getCacSid());

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
     * <p>Select CIR_ACCOUNT_USER All Data
     * @return List in CIR_ACCOUNT_USERModel
     * @throws SQLException SQL実行例外
     */
    public List<CirAccountUserModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CirAccountUserModel> ret = new ArrayList<CirAccountUserModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CAC_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   USR_SID");
            sql.addSql(" from ");
            sql.addSql("   CIR_ACCOUNT_USER");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlAccountUserFromRs(rs));
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
     * <p>Select CIR_ACCOUNT_USER
     * @param wacSid CAC_SID
     * @return List in CIR_ACCOUNT_USERModel
     * @throws SQLException SQL実行例外
     */
    public List<CirAccountUserModel> select(int wacSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<CirAccountUserModel> ret = new ArrayList<CirAccountUserModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CAC_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   USR_SID");
            sql.addSql(" from");
            sql.addSql("   CIR_ACCOUNT_USER");
            sql.addSql(" where ");
            sql.addSql("   CAC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wacSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getWmlAccountUserFromRs(rs));
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
     * <p>Select CIR_ACCOUNT_USER
     * @param wacSid CAC_SID
     * @return List in CIR_ACCOUNT_USERModel
     * @throws SQLException SQL実行例外
     */
    public List<CirAccountUserModel> getAccountUserList(int wacSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<CirAccountUserModel> ret = new ArrayList<CirAccountUserModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CIR_ACCOUNT_USER.CAC_SID,");
            sql.addSql("   CMN_GROUPM_TABLE.GRP_SID,");
            sql.addSql("   CMN_USRM_TABLE.USR_SID");
            sql.addSql(" from");
            sql.addSql("   CIR_ACCOUNT_USER");
            sql.addSql("       left join");
            sql.addSql("         (select CMN_USRM.USR_SID from CMN_USRM"
                                    + " where CMN_USRM.USR_JKBN != 9) CMN_USRM_TABLE ");
            sql.addSql("       on ");
            sql.addSql("         CIR_ACCOUNT_USER.USR_SID = CMN_USRM_TABLE.USR_SID");

            sql.addSql("       left join");
            sql.addSql("         (select GRP_SID from CMN_GROUPM"
                                    + " where CMN_GROUPM.GRP_JKBN != 9) CMN_GROUPM_TABLE ");
            sql.addSql("       on ");
            sql.addSql("         CIR_ACCOUNT_USER.GRP_SID = CMN_GROUPM_TABLE.GRP_SID");
            sql.addSql(" where ");
            sql.addSql("   CIR_ACCOUNT_USER.CAC_SID=?");
            sql.addSql(" and ");
            sql.addSql("   (CMN_GROUPM_TABLE.GRP_SID is not null");
            sql.addSql("   or ");
            sql.addSql("   CMN_USRM_TABLE.USR_SID is not null)");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wacSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getWmlAccountUserFromRs(rs));
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
     * <p>Delete CIR_ACCOUNT_USER
     * @param wacSid CAC_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int wacSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CIR_ACCOUNT_USER");
            sql.addSql(" where ");
            sql.addSql("   CAC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wacSid);

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
     * <br>[機  能] 指定したユーザに関連するアカウント使用者情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int deleteOfUser(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CIR_ACCOUNT_USER");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(userSid);

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
     * <br>[機  能] 指定したアカウント使用者情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param cacSid アカウントSID
     * @param userSid ユーザSID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int deleteOfUser(int cacSid, int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CIR_ACCOUNT_USER");
            sql.addSql(" where ");
            sql.addSql("   CAC_SID=?");
            sql.addSql(" and ");
            sql.addSql("   USR_SID=?");

            sql.addIntValue(cacSid);
            sql.addIntValue(userSid);
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
     * <br>[機  能] 指定したグループに関連するアカウント使用者情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param grpSid グループSID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int deleteOfGroup(int grpSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CIR_ACCOUNT_USER");
            sql.addSql(" where ");
            sql.addSql("   GRP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(grpSid);

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
     * <br>[機  能] 新規登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @param wacType アカウント種別
     * @param sidList 団体SID or ユーザSIDの一覧
     * @throws SQLException SQL実行時例外
     */
    public void insert(int wacSid, int wacType, String[] sidList) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            String valueString = "(" + wacSid + ", ";
            if (wacType == GSConstCircular.CAC_TYPE_GROUP) {
                valueString += "?, -1)";
            } else {
                valueString += "-1, ?)";
            }

            SqlBuffer sql = null;
            for (int count = 0; count < sidList.length; count += 100) {
                sql = new SqlBuffer();
                sql.addSql(" insert into");
                sql.addSql("   CIR_ACCOUNT_USER(CAC_SID, GRP_SID, USR_SID)");
                sql.addSql(" values");
                sql.addSql("   " + valueString);
                sql.addIntValue(Integer.parseInt(sidList[count]));

                for (int index = count + 1;
                    (index < sidList.length && index < count + 100); index++) {
                    sql.addSql("   ," + valueString);
                    sql.addIntValue(Integer.parseInt(sidList[index]));
                }

                pstmt = con.prepareStatement(sql.toSqlString());
                log__.info(sql.toLogString());
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
     * <p>Create CIR_ACCOUNT_USER Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CirAccountUserModel
     * @throws SQLException SQL実行例外
     */
    private CirAccountUserModel __getWmlAccountUserFromRs(ResultSet rs) throws SQLException {
        CirAccountUserModel bean = new CirAccountUserModel();
        bean.setCacSid(rs.getInt("CAC_SID"));
        bean.setGrpSid(rs.getInt("GRP_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        return bean;
    }
}
