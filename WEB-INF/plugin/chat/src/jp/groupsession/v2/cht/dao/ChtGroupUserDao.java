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
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cht.GSConstChat;
import jp.groupsession.v2.cht.model.ChtGroupUserModel;

/**
 * <p>CHT_GROUP_USER Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class ChtGroupUserDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ChtGroupUserDao.class);

    /**
     * <p>Default Constructor
     */
    public ChtGroupUserDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public ChtGroupUserDao(Connection con) {
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
            sql.addSql("drop table CHT_GROUP_USER");

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
            sql.addSql(" create table CHT_GROUP_USER (");
            sql.addSql("   CGI_SID integer not null,");
            sql.addSql("   CGU_KBN integer not null,");
            sql.addSql("   CGU_SELECT_SID integer not null,");
            sql.addSql("   CGU_ADMIN_FLG integer not null,");
            sql.addSql("   CGU_AUID integer not null,");
            sql.addSql("   CGU_ADATE timestamp not null,");
            sql.addSql("   CGU_EUID integer not null,");
            sql.addSql("   CGU_EDATE timestamp not null,");
            sql.addSql("   primary key (CGI_SID,CGU_KBN,CGU_SELECT_SID)");
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
     * <p>Insert CHT_GROUP_USER Data Bindding JavaBean
     * @param bean CHT_GROUP_USER Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(ChtGroupUserModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CHT_GROUP_USER(");
            sql.addSql("   CGI_SID,");
            sql.addSql("   CGU_KBN,");
            sql.addSql("   CGU_SELECT_SID,");
            sql.addSql("   CGU_ADMIN_FLG,");
            sql.addSql("   CGU_AUID,");
            sql.addSql("   CGU_ADATE,");
            sql.addSql("   CGU_EUID,");
            sql.addSql("   CGU_EDATE");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCgiSid());
            sql.addIntValue(bean.getCguKbn());
            sql.addIntValue(bean.getCguSelectSid());
            sql.addIntValue(bean.getCguAdminFlg());
            sql.addIntValue(bean.getCguAuid());
            sql.addDateValue(bean.getCguAdate());
            sql.addIntValue(bean.getCguEuid());
            sql.addDateValue(bean.getCguEdate());
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
     * <br>[機  能] チャットグループユーザ情報を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param beanList ChtGroupUserModel DataList
     * @throws SQLException SQL実行例外
     */
    public void insertCirInf(List<ChtGroupUserModel> beanList) throws SQLException {

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
            sql.addSql(" CHT_GROUP_USER(");
            sql.addSql("   CGI_SID,");
            sql.addSql("   CGU_KBN,");
            sql.addSql("   CGU_SELECT_SID,");
            sql.addSql("   CGU_ADMIN_FLG,");
            sql.addSql("   CGU_AUID,");
            sql.addSql("   CGU_ADATE,");
            sql.addSql("   CGU_EUID,");
            sql.addSql("   CGU_EDATE");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            for (ChtGroupUserModel bean : beanList) {
                sql.addIntValue(bean.getCgiSid());
                sql.addIntValue(bean.getCguKbn());
                sql.addIntValue(bean.getCguSelectSid());
                sql.addIntValue(bean.getCguAdminFlg());
                sql.addIntValue(bean.getCguAuid());
                sql.addDateValue(bean.getCguAdate());
                sql.addIntValue(bean.getCguEuid());
                sql.addDateValue(bean.getCguEdate());
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
     * <p>Update CHT_GROUP_USER Data Bindding JavaBean
     * @param bean CHT_GROUP_USER Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(ChtGroupUserModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CHT_GROUP_USER");
            sql.addSql(" set ");
            sql.addSql("   CGU_ADMIN_FLG=?,");
            sql.addSql("   CGU_AUID=?,");
            sql.addSql("   CGU_ADATE=?,");
            sql.addSql("   CGU_EUID=?,");
            sql.addSql("   CGU_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   CGI_SID=?");
            sql.addSql(" and");
            sql.addSql("   CGU_KBN=?");
            sql.addSql(" and");
            sql.addSql("   CGU_SELECT_SID=?");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCguAdminFlg());
            sql.addIntValue(bean.getCguAuid());
            sql.addDateValue(bean.getCguAdate());
            sql.addIntValue(bean.getCguEuid());
            sql.addDateValue(bean.getCguEdate());
            //where
            sql.addIntValue(bean.getCgiSid());
            sql.addIntValue(bean.getCguKbn());
            sql.addIntValue(bean.getCguSelectSid());

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
     * <p>Select CHT_GROUP_USER All Data
     * @return List in CHT_GROUP_USERModel
     * @throws SQLException SQL実行例外
     */
    public List<ChtGroupUserModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ChtGroupUserModel> ret = new ArrayList<ChtGroupUserModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CGI_SID,");
            sql.addSql("   CGU_KBN,");
            sql.addSql("   CGU_SELECT_SID,");
            sql.addSql("   CGU_ADMIN_FLG,");
            sql.addSql("   CGU_AUID,");
            sql.addSql("   CGU_ADATE,");
            sql.addSql("   CGU_EUID,");
            sql.addSql("   CGU_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CHT_GROUP_USER");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getChtGroupUserFromRs(rs));
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
     * @return List in ChtGroupUserModel
     * @throws SQLException SQL実行例外
     */
    public List<ChtGroupUserModel> selectLimit(
            int offset, int limit) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ChtGroupUserModel> ret = new ArrayList<ChtGroupUserModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CGI_SID,");
            sql.addSql("   CGU_KBN,");
            sql.addSql("   CGU_SELECT_SID,");
            sql.addSql("   CGU_ADMIN_FLG,");
            sql.addSql("   CGU_AUID,");
            sql.addSql("   CGU_ADATE,");
            sql.addSql("   CGU_EUID,");
            sql.addSql("   CGU_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CHT_GROUP_USER");
            sql.addSql(" order by ");
            sql.addSql("   CGI_SID asc");

            sql.setPagingValue(offset, limit);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getChtGroupUserFromRs(rs));
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
     * <p>全件数を取得する
     * @return List in ChtGroupUserModel
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
            sql.addSql("   CHT_GROUP_USER");

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
     * <p>Select CHT_GROUP_USER
     * @param cgiSid CGI_SID
     * @param kbn CGU_KBN
     * @param cguSelectSid CGU_SELECT_SID
     * @return CHT_GROUP_USERModel
     * @throws SQLException SQL実行例外
     */
    public ChtGroupUserModel select(int cgiSid, int kbn, int cguSelectSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ChtGroupUserModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CGI_SID,");
            sql.addSql("   CGU_KBN,");
            sql.addSql("   CGU_SELECT_SID,");
            sql.addSql("   CGU_ADMIN_FLG,");
            sql.addSql("   CGU_AUID,");
            sql.addSql("   CGU_ADATE,");
            sql.addSql("   CGU_EUID,");
            sql.addSql("   CGU_EDATE");
            sql.addSql(" from");
            sql.addSql("   CHT_GROUP_USER");
            sql.addSql(" where ");
            sql.addSql("   CGI_SID=?");
            sql.addSql(" and");
            sql.addSql("   CGU_KBN=?");
            sql.addSql(" and");
            sql.addSql("   CGU_SELECT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(cgiSid);
            sql.addIntValue(kbn);
            sql.addIntValue(cguSelectSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getChtGroupUserFromRs(rs);
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
     * <p>Select CHT_GROUP_USER
     * @param cgiSid CGI_SID
     * @return CHT_GROUP_USERModel
     * @throws SQLException SQL実行例外
     */
    public List<ChtGroupUserModel> select(int cgiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<ChtGroupUserModel> ret = new ArrayList<ChtGroupUserModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CGI_SID,");
            sql.addSql("   CGU_KBN,");
            sql.addSql("   CGU_SELECT_SID,");
            sql.addSql("   CGU_ADMIN_FLG,");
            sql.addSql("   CGU_AUID,");
            sql.addSql("   CGU_ADATE,");
            sql.addSql("   CGU_EUID,");
            sql.addSql("   CGU_EDATE");
            sql.addSql(" from");
            sql.addSql("   CHT_GROUP_USER");
            sql.addSql(" where ");
            sql.addSql("   CGI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(cgiSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ChtGroupUserModel mdl = new ChtGroupUserModel();
                mdl = __getChtGroupUserFromRs(rs);
                ret.add(mdl);
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
     * <p>Select CHT_GROUP_USER
     * @param usrSid USR_SID
     * @return CHT_GROUP_USERModel
     * @throws SQLException SQL実行例外
     */
    public int getGroupEditUser(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   COUNT(CGI_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   CHT_GROUP_USER");
            sql.addSql(" where ");
            sql.addSql("   CGU_ADMIN_FLG=?");
            sql.addSql(" and ");
            sql.addSql("   ( ");
            sql.addSql("    ( ");
            sql.addSql("       CGU_KBN=?");
            sql.addSql("     and");
            sql.addSql("       CGU_SELECT_SID=?");
            sql.addSql("    ) ");
            sql.addSql("    or ");
            sql.addSql("    ( ");
            sql.addSql("       CGU_KBN=?");
            sql.addSql("     and");
            sql.addSql("       CGU_SELECT_SID IN(");
            sql.addSql("         select");
            sql.addSql("           GRP_SID");
            sql.addSql("         from");
            sql.addSql("           CMN_BELONGM");
            sql.addSql("         where");
            sql.addSql("           USR_SID = ?");
            sql.addSql("       )");
            sql.addSql("    ) ");
            sql.addSql("   ) ");
            sql.addSql(" and ");
            sql.addSql("   CGI_SID IN (");
            sql.addSql("     select ");
            sql.addSql("       CGI_SID ");
            sql.addSql("     from ");
            sql.addSql("       CHT_GROUP_INF ");
            sql.addSql("     where ");
            sql.addSql("        CGI_DEL_FLG <> ?");
            sql.addSql("   ) ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(GSConstChat.CHAT_GROUP_ADMIN);
            sql.addIntValue(GSConstChat.CHAT_KBN_USER);
            sql.addIntValue(usrSid);
            sql.addIntValue(GSConstChat.CHAT_KBN_GROUP);
            sql.addIntValue(usrSid);
            sql.addIntValue(GSConstChat.CHAT_MODE_DELETE);

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
     * <p>Select CHT_GROUP_USER
     * @param usrSid USR_SID
     * @param cgiSid CGI_SID
     * @return CHT_GROUP_USERModel
     * @throws SQLException SQL実行例外
     */
    public int getAuthority(int usrSid, int cgiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   COUNT(CGI_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   CHT_GROUP_USER");
            sql.addSql(" where ");
            sql.addSql("   CGI_SID = ? ");
            sql.addSql(" and ");
            sql.addSql("   ( ");
            sql.addSql("    ( ");
            sql.addSql("     CGU_KBN = ? ");
            sql.addSql("    and ");
            sql.addSql("      CGU_SELECT_SID = ? ");
            sql.addSql("    ) ");
            sql.addSql("    or ");
            sql.addSql("    ( ");
            sql.addSql("      CGU_KBN = ? ");
            sql.addSql("    and ");
            sql.addSql("      CGU_SELECT_SID IN ( ");
            sql.addSql("        select ");
            sql.addSql("          GRP_SID ");
            sql.addSql("        from ");
            sql.addSql("          CMN_BELONGM ");
            sql.addSql("        where ");
            sql.addSql("          USR_SID = ? ");
            sql.addSql("      ) ");
            sql.addSql("    ) ");
            sql.addSql("   ) ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(cgiSid);
            sql.addIntValue(GSConstChat.CHAT_KBN_USER);
            sql.addIntValue(usrSid);
            sql.addIntValue(GSConstChat.CHAT_KBN_GROUP);
            sql.addIntValue(usrSid);

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
     * <p>Delete CHT_GROUP_USER
     * @param cgiSid CGI_SID
     * @param kbn CGU_KBN
     * @param cguSelectSid CGU_SELECT_SID
     * @throws SQLException SQL実行例外
     * @return count
     */
    public int delete(int cgiSid, int kbn, int cguSelectSid)throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CHT_GROUP_USER");
            sql.addSql(" where ");
            sql.addSql("   CGI_SID=?");
            sql.addSql(" and");
            sql.addSql("   CGU_KBN=?");
            sql.addSql(" and");
            sql.addSql("   CGU_SELECT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(cgiSid);
            sql.addIntValue(kbn);
            sql.addIntValue(cguSelectSid);

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
     * <p>Delete CHT_GROUP_USER
     * @param cgiSid CGI_SID
     * @throws SQLException SQL実行例外
     * @return count
     */
    public int delete(int cgiSid)throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CHT_GROUP_USER");
            sql.addSql(" where ");
            sql.addSql("   CGI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(cgiSid);

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
     * <p>Delete CHT_GROUP_USER
     * @param sidList cgiSidList
     * @throws SQLException SQL実行例外
     * @return count
     */
    public int delete(List<Integer> sidList)throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CHT_GROUP_USER");
            sql.addSql(" where ");
            sql.addSql("   CGI_SID IN (");
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
     * 指定したチャットグループのメンバーユーザのSIDを重複なく出力します
     * @param cgiSid チャットグループSID
     * @return メンバーユーザSID
     * @throws SQLException SQL実行例外
     * */
    public List<Integer> membersOfChatGroup(int cgiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        // SQL文
        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select distinct");
            sql.addSql("   case ");
            sql.addSql("     when CHT_GROUP_USER.CGU_KBN=? then CMN_BELONGM.USR_SID");
            sql.addSql("     else CHT_GROUP_USER.CGU_SELECT_SID");
            sql.addSql("   end MEMBER");
            sql.addSql(" from");
            sql.addSql("   CHT_GROUP_USER");
            sql.addSql(" left join");
            sql.addSql("   CMN_BELONGM");
            sql.addSql("   on (");
            sql.addSql("     CHT_GROUP_USER.CGU_KBN=?");
            sql.addSql("   and ");
            sql.addSql("     CHT_GROUP_USER.CGU_SELECT_SID = CMN_BELONGM.GRP_SID");
            sql.addSql("   )");
            sql.addSql(" where");
            sql.addSql("   CHT_GROUP_USER.CGI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(GSConstChat.CHAT_KBN_GROUP);
            sql.addIntValue(GSConstChat.CHAT_KBN_GROUP);
            sql.addIntValue(cgiSid);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(rs.getInt("MEMBER"));
            }
        }  catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }

    /**
     * <p>Create CHT_GROUP_USER Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created ChtGroupUserModel
     * @throws SQLException SQL実行例外
     */
    private ChtGroupUserModel __getChtGroupUserFromRs(ResultSet rs) throws SQLException {
        ChtGroupUserModel bean = new ChtGroupUserModel();
        bean.setCgiSid(rs.getInt("CGI_SID"));
        bean.setCguKbn(rs.getInt("CGU_KBN"));
        bean.setCguSelectSid(rs.getInt("CGU_SELECT_SID"));
        bean.setCguAdminFlg(rs.getInt("CGU_ADMIN_FLG"));
        bean.setCguAuid(rs.getInt("CGU_AUID"));
        bean.setCguAdate(UDate.getInstanceTimestamp(rs.getTimestamp("CGU_ADATE")));
        bean.setCguEuid(rs.getInt("CGU_EUID"));
        bean.setCguEdate(UDate.getInstanceTimestamp(rs.getTimestamp("CGU_EDATE")));
        return bean;
    }
}
