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
import jp.groupsession.v2.cht.model.ChtSpaccessTargetModel;

/**
 * <p>CHT_SPACCESS_TARGET Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class ChtSpaccessTargetDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ChtSpaccessTargetDao.class);

    /**
     * <p>Default Constructor
     */
    public ChtSpaccessTargetDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public ChtSpaccessTargetDao(Connection con) {
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
            sql.addSql("drop table CHT_SPACCESS_TARGET");

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
            sql.addSql(" create table CHT_SPACCESS_TARGET (");
            sql.addSql("   CHS_SID integer,");
            sql.addSql("   CST_TYPE integer not null,");
            sql.addSql("   CST_PSID integer not null");
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
     * <p>Insert CHT_SPACCESS_TARGET Data Bindding JavaBean
     * @param bean CHT_SPACCESS_TARGET Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(ChtSpaccessTargetModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CHT_SPACCESS_TARGET(");
            sql.addSql("   CHS_SID,");
            sql.addSql("   CST_TYPE,");
            sql.addSql("   CST_PSID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getChsSid());
            sql.addIntValue(bean.getCstType());
            sql.addIntValue(bean.getCstPsid());
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
     * <p>Update CHT_SPACCESS_TARGET Data Bindding JavaBean
     * @param bean CHT_SPACCESS_TARGET Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(ChtSpaccessTargetModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CHT_SPACCESS_TARGET");
            sql.addSql(" set ");
            sql.addSql("   CHS_SID=?,");
            sql.addSql("   CST_TYPE=?,");
            sql.addSql("   CST_PSID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getChsSid());
            sql.addIntValue(bean.getCstType());
            sql.addIntValue(bean.getCstPsid());

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
     * <p>Delete CHT_SPACCESS_TARGET Data Bindding JavaBean
     * @param sid 削除SID
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int sid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CHT_SPACCESS_TARGET");
            sql.addSql(" where ");
            sql.addSql("   CHS_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sid);

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
     * <p>Select CHT_SPACCESS_TARGET All Data
     * @return List in CHT_SPACCESS_TARGETModel
     * @throws SQLException SQL実行例外
     */
    public List<ChtSpaccessTargetModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ChtSpaccessTargetModel> ret = new ArrayList<ChtSpaccessTargetModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CHS_SID,");
            sql.addSql("   CST_TYPE,");
            sql.addSql("   CST_PSID");
            sql.addSql(" from ");
            sql.addSql("   CHT_SPACCESS_TARGET");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getChtSpaccessTargetFromRs(rs));
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
     * <p>Select CHT_SPACCESS_TARGET CHS_SID Data
     * @param usrSid ユーザSID
     * @return List in CHT_SPACCESS_TARGETModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Integer> selectSid(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CHS_SID");
            sql.addSql(" from");
            sql.addSql("   CHT_SPACCESS_TARGET");
            sql.addSql(" where");
            sql.addSql("   (");
            sql.addSql("     CST_TYPE = ?");
            sql.addSql("   and");
            sql.addSql("     CST_PSID = ?");
            sql.addSql("   ) ");
            sql.addSql(" or");
            sql.addSql("   (");
            sql.addSql("     CST_TYPE = ?");
            sql.addSql("   and");
            sql.addSql("     CST_PSID IN (");
            sql.addSql("       select");
            sql.addSql("         GRP_SID");
            sql.addSql("       from");
            sql.addSql("         CMN_BELONGM");
            sql.addSql("       where");
            sql.addSql("         USR_SID = ?");
            sql.addSql("     )");
            sql.addSql("   )");

            sql.addIntValue(GSConstChat.CHAT_SPACCESS_TYPE_USER);
            sql.addIntValue(usrSid);
            sql.addIntValue(GSConstChat.CHAT_SPACCESS_TYPE_GROUP);
            sql.addIntValue(usrSid);
            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getInt("CHS_SID"));
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
     * <br>[機  能] 指定した特例アクセスの制限対象を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param chsSid 特例アクセスSID
     * @return 制限対象
     * @throws SQLException SQL実行時例外
     */
    public List<ChtSpaccessTargetModel> getTargetList(int chsSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ChtSpaccessTargetModel> ret = new ArrayList<ChtSpaccessTargetModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CHS_SID,");
            sql.addSql("   CST_TYPE,");
            sql.addSql("   CST_PSID");
            sql.addSql(" from ");
            sql.addSql("   CHT_SPACCESS_TARGET");
            sql.addSql(" where ");
            sql.addSql("   CHS_SID = ?");
            sql.addIntValue(chsSid);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getChtSpaccessTargetFromRs(rs));
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
     * <p>Create CHT_SPACCESS_TARGET Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created ChtSpaccessTargetModel
     * @throws SQLException SQL実行例外
     */
    private ChtSpaccessTargetModel __getChtSpaccessTargetFromRs(ResultSet rs) throws SQLException {
        ChtSpaccessTargetModel bean = new ChtSpaccessTargetModel();
        bean.setChsSid(rs.getInt("CHS_SID"));
        bean.setCstType(rs.getInt("CST_TYPE"));
        bean.setCstPsid(rs.getInt("CST_PSID"));
        return bean;
    }

    /**
     * <br>[機  能] 制限されたグループメンバ―SID一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @return グループメンバ―SID一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getTargetGroupMemberList(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Integer> ret = new ArrayList<Integer>();
        con = getCon();
        SqlBuffer sql = new SqlBuffer();
        try {
            //SQL文
            sql.addSql("select ");
            sql.addSql("  CMN_GROUPM.GRP_SID");
            sql.addSql(" from");
            sql.addSql("  CMN_GROUPM");
            sql.addSql(" where");
            sql.addSql("  CMN_GROUPM.GRP_SID");
            sql.addSql("   in");
            sql.addSql(" (");
            __createSqlPermittedTarget(sql, usrSid, GSConstChat.CHAT_SPACCESS_TYPE_GROUP);
            sql.addSql("  ) ");

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getInt("GRP_SID"));
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
     * <br>[機  能] 制限されたユーザメンバ―SID一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @param tokureiGrpList 特例グループ
     * @return グループメンバ―SID一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getTargetUserMemberList(int usrSid,
            List<Integer> tokureiGrpList) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Integer> ret = new ArrayList<Integer>();
        con = getCon();
        SqlBuffer sql = new SqlBuffer();
        try {
            //SQL文
            sql.addSql("select ");
            sql.addSql("  CMN_USRM_INF.USR_SID");
            sql.addSql(" from");
            sql.addSql("  CMN_USRM_INF");
            sql.addSql(" where");
            sql.addSql("  CMN_USRM_INF.USR_SID");
            sql.addSql(" in");
            sql.addSql(" (");
            __createSqlPermittedTarget(sql, usrSid, GSConstChat.CHAT_SPACCESS_TYPE_USER);
            sql.addSql("  )");
            if (!tokureiGrpList.isEmpty()) {
                sql.addSql(" or");
                sql.addSql("  CMN_USRM_INF.USR_SID");
                sql.addSql(" in");
                sql.addSql(" (");
                sql.addSql("  select");
                sql.addSql("    USR_SID");
                sql.addSql("  from");
                sql.addSql("    CMN_BELONGM");
                sql.addSql("  where");
                sql.addSql("  GRP_SID in (");
                for (int idx = 0; idx < tokureiGrpList.size(); idx++) {
                    if (idx != 0) {
                        sql.addSql("    ,");
                    }
                    sql.addSql("    ?");
                    sql.addIntValue(tokureiGrpList.get(idx));
                }
                sql.addSql("             )");
                sql.addSql("  )");
            }
            sql.addSql(" group by");
            sql.addSql("  CMN_USRM_INF.USR_SID");
            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getInt("USR_SID"));
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
     *
     * <br>[機  能] 特例アクセス権限のないユーザまたはグループを取得するSQLクエリ作成
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SqlBuffer
     * @param usrSid ユーザSID
     * @param type 1：ユーザ、2：グループ
     */
    private void __createSqlPermittedTarget(SqlBuffer sql, int usrSid, int type) {
        sql.addSql(" select ");
        sql.addSql("   CHT_SPACCESS_TARGET.CST_PSID");
        sql.addSql("  from ");
        sql.addSql("   CHT_SPACCESS_TARGET");
        sql.addSql("   where");
        sql.addSql("      CHT_SPACCESS_TARGET.CST_TYPE = ? ");
        sql.addSql(" and ");
        sql.addSql("   CHT_SPACCESS_TARGET.CHS_SID");
        sql.addSql(" not in ");
        sql.addSql("   (");
        sql.addSql("    select");
        sql.addSql("     CHT_SPACCESS_PERMIT.CHS_SID");
        sql.addSql("   from ");
        sql.addSql("     CHT_SPACCESS_PERMIT");
        sql.addSql("   where ");
        sql.addSql("     (");
        sql.addSql("       (");
        sql.addSql("          CSP_TYPE = ?");
        sql.addSql("        and");
        sql.addSql("          CSP_PSID = ?");
        sql.addSql("       ) ");
        sql.addSql("       or");
        sql.addSql("       (");
        sql.addSql("          CSP_TYPE = ?");
        sql.addSql("        and");
        sql.addSql("          CSP_PSID in (");
        sql.addSql("            select");
        sql.addSql("              GRP_SID");
        sql.addSql("            from");
        sql.addSql("              CMN_BELONGM");
        sql.addSql("            where");
        sql.addSql("              USR_SID = ?");
        sql.addSql("          )");
        sql.addSql("       )");
        sql.addSql("       or");
        sql.addSql("       (");
        sql.addSql("          CSP_TYPE = ?");
        sql.addSql("           and");
        sql.addSql("          CSP_PSID in (");
        sql.addSql("            select");
        sql.addSql("              POS_SID");
        sql.addSql("            from");
        sql.addSql("              CMN_POSITION");
        sql.addSql("            where");
        sql.addSql("              POS_SORT >= (");
        sql.addSql("                select");
        sql.addSql("                  POS_SORT");
        sql.addSql("                from");
        sql.addSql("                  CMN_POSITION");
        sql.addSql("                where");
        sql.addSql("                  POS_SID = (");
        sql.addSql("                    select");
        sql.addSql("                      POS_SID");
        sql.addSql("                    from");
        sql.addSql("                      CMN_USRM_INF");
        sql.addSql("                    where");
        sql.addSql("                      USR_SID = ?");
        sql.addSql("                  )");
        sql.addSql("                and");
        sql.addSql("                  POS_SID <> 0");
        sql.addSql("            )");
        sql.addSql("          )");
        sql.addSql("       )");
        sql.addSql("     )");
        sql.addSql("   )");
        sql.addIntValue(type);
        sql.addIntValue(GSConstChat.CHAT_SPACCESS_TYPE_USER);
        sql.addIntValue(usrSid);
        sql.addIntValue(GSConstChat.CHAT_SPACCESS_TYPE_GROUP);
        sql.addIntValue(usrSid);
        sql.addIntValue(GSConstChat.CHAT_SPACCESS_TYPE_POSITION);
        sql.addIntValue(usrSid);
    }


    /**
     *
     * <br>[機  能] 特例アクセス権限のあるユーザまたはグループを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @param type 1:ユーザ、2:グループ
     * @return 特例アクセス権限のあるユーザまたはグループを取得
     * @throws SQLException SQL実行例外
     */
    public List<Integer> getPermittedTarget(int usrSid, int type) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            // SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CHT_SPACCESS_TARGET.CST_PSID CST_PSID");
            sql.addSql("  from ");
            sql.addSql("   CHT_SPACCESS_TARGET");
            sql.addSql("   where");
            sql.addSql("      CHT_SPACCESS_TARGET.CST_TYPE = ? ");
            sql.addSql(" and ");
            sql.addSql("   CHT_SPACCESS_TARGET.CHS_SID");
            sql.addSql(" in ");
            sql.addSql("   (");
            sql.addSql("    select");
            sql.addSql("     CHT_SPACCESS_PERMIT.CHS_SID");
            sql.addSql("   from ");
            sql.addSql("     CHT_SPACCESS_PERMIT");
            sql.addSql("   where ");
            sql.addSql("     (");
            sql.addSql("       (");
            sql.addSql("          CSP_TYPE = ?");
            sql.addSql("        and");
            sql.addSql("          CSP_PSID = ?");
            sql.addSql("       ) ");
            sql.addSql("       or");
            sql.addSql("       (");
            sql.addSql("          CSP_TYPE = ?");
            sql.addSql("        and");
            sql.addSql("          CSP_PSID in (");
            sql.addSql("            select");
            sql.addSql("              GRP_SID");
            sql.addSql("            from");
            sql.addSql("              CMN_BELONGM");
            sql.addSql("            where");
            sql.addSql("              USR_SID = ?");
            sql.addSql("          )");
            sql.addSql("       )");
            sql.addSql("       or");
            sql.addSql("       (");
            sql.addSql("          CSP_TYPE = ?");
            sql.addSql("           and");
            sql.addSql("          CSP_PSID in (");
            sql.addSql("            select");
            sql.addSql("              POS_SID");
            sql.addSql("            from");
            sql.addSql("              CMN_POSITION");
            sql.addSql("            where");
            sql.addSql("              POS_SORT >= (");
            sql.addSql("                select");
            sql.addSql("                  POS_SORT");
            sql.addSql("                from");
            sql.addSql("                  CMN_POSITION");
            sql.addSql("                where");
            sql.addSql("                  POS_SID = (");
            sql.addSql("                    select");
            sql.addSql("                      POS_SID");
            sql.addSql("                    from");
            sql.addSql("                      CMN_USRM_INF");
            sql.addSql("                    where");
            sql.addSql("                      USR_SID = ?");
            sql.addSql("                  )");
            sql.addSql("                and");
            sql.addSql("                  POS_SID <> 0");
            sql.addSql("            )");
            sql.addSql("          )");
            sql.addSql("       )");
            sql.addSql("     )");
            sql.addSql("   )");
            sql.addIntValue(type);
            sql.addIntValue(GSConstChat.CHAT_SPACCESS_TYPE_USER);
            sql.addIntValue(usrSid);
            sql.addIntValue(GSConstChat.CHAT_SPACCESS_TYPE_GROUP);
            sql.addIntValue(usrSid);
            sql.addIntValue(GSConstChat.CHAT_SPACCESS_TYPE_POSITION);
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getInt("CST_PSID"));
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
