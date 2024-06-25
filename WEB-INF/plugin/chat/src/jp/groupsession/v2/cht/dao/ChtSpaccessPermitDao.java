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
import jp.groupsession.v2.cht.model.ChtSpaccessPermitModel;

/**
 * <p>CHT_SPACCESS_PERMIT Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class ChtSpaccessPermitDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ChtSpaccessPermitDao.class);

    /**
     * <p>Default Constructor
     */
    public ChtSpaccessPermitDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public ChtSpaccessPermitDao(Connection con) {
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
            sql.addSql("drop table CHT_SPACCESS_PERMIT");

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
            sql.addSql(" create table CHT_SPACCESS_PERMIT (");
            sql.addSql("   CHS_SID integer,");
            sql.addSql("   CSP_TYPE integer not null,");
            sql.addSql("   CSP_PSID integer not null");
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
     * <p>Insert CHT_SPACCESS_PERMIT Data Bindding JavaBean
     * @param bean CHT_SPACCESS_PERMIT Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(ChtSpaccessPermitModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CHT_SPACCESS_PERMIT(");
            sql.addSql("   CHS_SID,");
            sql.addSql("   CSP_TYPE,");
            sql.addSql("   CSP_PSID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getChsSid());
            sql.addIntValue(bean.getCspType());
            sql.addIntValue(bean.getCspPsid());
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
     * <p>Update CHT_SPACCESS_PERMIT Data Bindding JavaBean
     * @param bean CHT_SPACCESS_PERMIT Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(ChtSpaccessPermitModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CHT_SPACCESS_PERMIT");
            sql.addSql(" set ");
            sql.addSql("   CHS_SID=?,");
            sql.addSql("   CSP_TYPE=?,");
            sql.addSql("   CSP_PSID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getChsSid());
            sql.addIntValue(bean.getCspType());
            sql.addIntValue(bean.getCspPsid());

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
     * <p>delete CHT_SPACCESS_PERMIT Data Bindding JavaBean
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
            sql.addSql("   CHT_SPACCESS_PERMIT");
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
     * <p>Select CHT_SPACCESS_PERMIT All Data
     * @return List in CHT_SPACCESS_PERMITModel
     * @throws SQLException SQL実行例外
     */
    public List<ChtSpaccessPermitModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ChtSpaccessPermitModel> ret = new ArrayList<ChtSpaccessPermitModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CHS_SID,");
            sql.addSql("   CSP_TYPE,");
            sql.addSql("   CSP_PSID");
            sql.addSql(" from ");
            sql.addSql("   CHT_SPACCESS_PERMIT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getChtSpaccessPermitFromRs(rs));
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
     * <p>Select CHT_SPACCESS_PERMIT All Data
     * @param chsList 特例アクセスSIDリスト
     * @param usrSid ユーザSID
     * @return List in CHT_SPACCESS_PERMITModel
     * @throws SQLException SQL実行例外
     */
    public int selectCount(ArrayList<Integer> chsList, int usrSid)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(CHS_SID) as CNT_SID");
            sql.addSql(" from ");
            sql.addSql("   CHT_SPACCESS_PERMIT");
            sql.addSql(" where");
            sql.addSql("   CHS_SID IN (");
            for (int idx = 0; idx < chsList.size(); idx++) {
                if (idx != 0) {
                    sql.addSql("   ,");
                }
                sql.addSql("   ?");
                sql.addIntValue(chsList.get(idx));
            }
            sql.addSql("   )");
            sql.addSql(" and");
            sql.addSql("   (");
            sql.addSql("     (");
            sql.addSql("        CSP_TYPE = ?");
            sql.addSql("      and");
            sql.addSql("        CSP_PSID = ?");
            sql.addSql("     ) ");
            sql.addSql("     or");
            sql.addSql("     (");
            sql.addSql("        CSP_TYPE = ?");
            sql.addSql("      and");
            sql.addSql("        CSP_PSID IN (");
            sql.addSql("          select");
            sql.addSql("            GRP_SID");
            sql.addSql("          from");
            sql.addSql("            CMN_BELONGM");
            sql.addSql("          where");
            sql.addSql("            USR_SID = ?");
            sql.addSql("        )");
            sql.addSql("     )");
            sql.addSql("     or");
            sql.addSql("     (");
            sql.addSql("        CSP_TYPE = ?");
            sql.addSql("      and");
            sql.addSql("        CSP_PSID IN (");
            sql.addSql("          select");
            sql.addSql("            POS_SID");
            sql.addSql("          from");
            sql.addSql("            CMN_POSITION");
            sql.addSql("          where");
            sql.addSql("            POS_SORT >= (");
            sql.addSql("              select");
            sql.addSql("                POS_SORT");
            sql.addSql("              from");
            sql.addSql("                CMN_POSITION");
            sql.addSql("              where");
            sql.addSql("                POS_SID = (");
            sql.addSql("                  select");
            sql.addSql("                    POS_SID");
            sql.addSql("                  from");
            sql.addSql("                    CMN_USRM_INF");
            sql.addSql("                  where");
            sql.addSql("                    USR_SID = ?");
            sql.addSql("                )");
            sql.addSql("              and");
            sql.addSql("                POS_SID <> 0");
            sql.addSql("            )");
            sql.addSql("        )");
            sql.addSql("     )");
            sql.addSql("   )");

            sql.addIntValue(GSConstChat.CHAT_SPACCESS_TYPE_USER);
            sql.addIntValue(usrSid);
            sql.addIntValue(GSConstChat.CHAT_SPACCESS_TYPE_GROUP);
            sql.addIntValue(usrSid);
            sql.addIntValue(GSConstChat.CHAT_SPACCESS_TYPE_POSITION);
            sql.addIntValue(usrSid);
            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getInt("CNT_SID");
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
     * <br>[機  能] 指定された特例アクセスの許可対象を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param chsSid 特例アクセスSID
     * @return 許可対象
     * @throws SQLException SQL実行時例外
     */
    public List<ChtSpaccessPermitModel> getPermitList(int chsSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ChtSpaccessPermitModel> ret = new ArrayList<ChtSpaccessPermitModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CHS_SID,");
            sql.addSql("   CSP_TYPE,");
            sql.addSql("   CSP_PSID");
            sql.addSql(" from ");
            sql.addSql("   CHT_SPACCESS_PERMIT");
            sql.addSql(" where ");
            sql.addSql("   CHS_SID = ?");
            sql.addIntValue(chsSid);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getChtSpaccessPermitFromRs(rs));
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
     * <p>Create CHT_SPACCESS_PERMIT Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created ChtSpaccessPermitModel
     * @throws SQLException SQL実行例外
     */
    private ChtSpaccessPermitModel __getChtSpaccessPermitFromRs(ResultSet rs) throws SQLException {
        ChtSpaccessPermitModel bean = new ChtSpaccessPermitModel();
        bean.setChsSid(rs.getInt("CHS_SID"));
        bean.setCspType(rs.getInt("CSP_TYPE"));
        bean.setCspPsid(rs.getInt("CSP_PSID"));
        return bean;
    }
}
