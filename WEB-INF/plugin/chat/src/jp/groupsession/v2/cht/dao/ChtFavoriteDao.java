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
import jp.groupsession.v2.cht.model.ChtFavoriteModel;

/**
 * <p>CHT_FAVORITE Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class ChtFavoriteDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ChtFavoriteDao.class);

    /**
     * <p>Default Constructor
     */
    public ChtFavoriteDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public ChtFavoriteDao(Connection con) {
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
            sql.addSql("drop table CHT_FAVORITE");

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
            sql.addSql(" create table CHT_FAVORITE (");
            sql.addSql("   CHF_UID integer not null,");
            sql.addSql("   CHF_CHAT_KBN integer not null,");
            sql.addSql("   CHF_SID integer not null");
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
     * <p>Insert CHT_FAVORITE Data Bindding JavaBean
     * @param bean CHT_FAVORITE Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(ChtFavoriteModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CHT_FAVORITE(");
            sql.addSql("   CHF_UID,");
            sql.addSql("   CHF_CHAT_KBN,");
            sql.addSql("   CHF_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getChfUid());
            sql.addIntValue(bean.getChfChatKbn());
            sql.addIntValue(bean.getChfSid());
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
     * <p>Update CHT_FAVORITE Data Bindding JavaBean
     * @param bean CHT_FAVORITE Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(ChtFavoriteModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CHT_FAVORITE");
            sql.addSql(" set ");
            sql.addSql("   CHF_UID=?,");
            sql.addSql("   CHF_CHAT_KBN=?,");
            sql.addSql("   CHF_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getChfUid());
            sql.addIntValue(bean.getChfChatKbn());
            sql.addIntValue(bean.getChfSid());

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
     * <p>Update CHT_FAVORITE Data Bindding JavaBean
     * @param bean CHT_FAVORITE Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int delete(ChtFavoriteModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CHT_FAVORITE");
            sql.addSql(" where ");
            sql.addSql("   CHF_UID=?");
            sql.addSql(" and ");
            sql.addSql("   CHF_CHAT_KBN=?");
            sql.addSql(" and ");
            sql.addSql("   CHF_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getChfUid());
            sql.addIntValue(bean.getChfChatKbn());
            sql.addIntValue(bean.getChfSid());

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
     * <p>Update CHT_FAVORITE Data Bindding JavaBean
     * @param sidList グループSID
     * @param chtKbn 1:ユーザ 2:グループ
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int delete(List<Integer> sidList, int chtKbn) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CHT_FAVORITE");
            sql.addSql(" where ");
            sql.addSql("   CHF_CHAT_KBN=?");
            sql.addIntValue(chtKbn);
            sql.addSql(" and ");
            sql.addSql("   CHF_SID IN (");
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
     * <p>Select CHT_FAVORITE All Data
     * @return List in CHT_FAVORITEModel
     * @throws SQLException SQL実行例外
     */
    public List<ChtFavoriteModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ChtFavoriteModel> ret = new ArrayList<ChtFavoriteModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CHF_UID,");
            sql.addSql("   CHF_CHAT_KBN,");
            sql.addSql("   CHF_SID");
            sql.addSql(" from ");
            sql.addSql("   CHT_FAVORITE");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getChtFavoriteFromRs(rs));
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
     * <p>count
     * @param userSid ユーザSID
     * @param chtKbn チャット区分
     * @param selectSid 選択SID
     * @return List in CHT_FAVORITEModel
     * @throws SQLException SQL実行例外
     */
    public int select(int userSid, int chtKbn, int selectSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   COUNT(CHF_UID) as CNT");
            sql.addSql(" from ");
            sql.addSql("   CHT_FAVORITE");
            sql.addSql(" where ");
            sql.addSql("   CHF_UID = ? ");
            sql.addSql(" and ");
            sql.addSql("   CHF_CHAT_KBN = ? ");
            sql.addSql(" and ");
            sql.addSql("   CHF_SID = ? ");

            sql.addIntValue(userSid);
            sql.addIntValue(chtKbn);
            sql.addIntValue(selectSid);
            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <p>Create CHT_FAVORITE Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created ChtFavoriteModel
     * @throws SQLException SQL実行例外
     */
    private ChtFavoriteModel __getChtFavoriteFromRs(ResultSet rs) throws SQLException {
        ChtFavoriteModel bean = new ChtFavoriteModel();
        bean.setChfUid(rs.getInt("CHF_UID"));
        bean.setChfChatKbn(rs.getInt("CHF_CHAT_KBN"));
        bean.setChfSid(rs.getInt("CHF_SID"));
        return bean;
    }
}
