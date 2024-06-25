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
import jp.groupsession.v2.cht.model.ChtPriConfModel;

/**
 * <p>CHT_PRI_CONF Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class ChtPriConfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ChtPriConfDao.class);

    /**
     * <p>Default Constructor
     */
    public ChtPriConfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public ChtPriConfDao(Connection con) {
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
            sql.addSql("drop table CHT_PRI_CONF");

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
            sql.addSql(" create table CHT_PRI_CONF (");
            sql.addSql("   CPC_PRI_UID integer not null,");
            sql.addSql("   CPC_PUSH_FLG integer not null,");
            sql.addSql("   CPC_PUSH_TIME integer not null,");
            sql.addSql("   CPC_DEF_FLG integer not null,");
            sql.addSql("   CPC_CHAT_KBN integer not null,");
            sql.addSql("   CPC_SEL_SID integer not null,");
            sql.addSql("   CPC_ENTER_FLG integer not null,");
            sql.addSql("   CPC_AUID integer not null,");
            sql.addSql("   CPC_ADATE timestamp not null,");
            sql.addSql("   CPC_EUID integer not null,");
            sql.addSql("   CPC_EDATE timestamp not null,");
            sql.addSql("   primary key (CPC_PRI_UID)");
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
     * <p>Insert CHT_PRI_CONF Data Bindding JavaBean
     * @param bean CHT_PRI_CONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(ChtPriConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CHT_PRI_CONF(");
            sql.addSql("   CPC_PRI_UID,");
            sql.addSql("   CPC_PUSH_FLG,");
            sql.addSql("   CPC_PUSH_TIME,");
            sql.addSql("   CPC_DEF_FLG,");
            sql.addSql("   CPC_CHAT_KBN,");
            sql.addSql("   CPC_SEL_SID,");
            sql.addSql("   CPC_ENTER_FLG,");
            sql.addSql("   CPC_AUID,");
            sql.addSql("   CPC_ADATE,");
            sql.addSql("   CPC_EUID,");
            sql.addSql("   CPC_EDATE,");
            sql.addSql("   CPC_SEL_TAB");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCpcPriUid());
            sql.addIntValue(bean.getCpcPushFlg());
            sql.addIntValue(bean.getCpcPushTime());
            sql.addIntValue(bean.getCpcDefFlg());
            sql.addIntValue(bean.getCpcChatKbn());
            sql.addIntValue(bean.getCpcSelSid());
            sql.addIntValue(bean.getCpcEnterFlg());
            sql.addIntValue(bean.getCpcAuid());
            sql.addDateValue(bean.getCpcAdate());
            sql.addIntValue(bean.getCpcEuid());
            sql.addDateValue(bean.getCpcEdate());
            sql.addIntValue(bean.getCpcSelTab());
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
     * <p>Update CHT_PRI_CONF Data Bindding JavaBean
     * @param bean CHT_PRI_CONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(ChtPriConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CHT_PRI_CONF");
            sql.addSql(" set ");
            sql.addSql("   CPC_PUSH_FLG=?,");
            sql.addSql("   CPC_PUSH_TIME=?,");
            sql.addSql("   CPC_DEF_FLG=?,");
            sql.addSql("   CPC_CHAT_KBN=?,");
            sql.addSql("   CPC_SEL_SID=?,");
            sql.addSql("   CPC_ENTER_FLG=?,");
            sql.addSql("   CPC_EUID=?,");
            sql.addSql("   CPC_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   CPC_PRI_UID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCpcPushFlg());
            sql.addIntValue(bean.getCpcPushTime());
            sql.addIntValue(bean.getCpcDefFlg());
            sql.addIntValue(bean.getCpcChatKbn());
            sql.addIntValue(bean.getCpcSelSid());
            sql.addIntValue(bean.getCpcEnterFlg());
            sql.addIntValue(bean.getCpcEuid());
            sql.addDateValue(bean.getCpcEdate());
            //where
            sql.addIntValue(bean.getCpcPriUid());

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
     * <p>Update CHT_PRI_CONF Data Bindding JavaBean
     * @param bean CHT_PRI_CONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateTuuti(ChtPriConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CHT_PRI_CONF");
            sql.addSql(" set ");
            sql.addSql("   CPC_PUSH_FLG=?,");
            sql.addSql("   CPC_PUSH_TIME=?,");
            sql.addSql("   CPC_AUID=?,");
            sql.addSql("   CPC_ADATE=?,");
            sql.addSql("   CPC_EUID=?,");
            sql.addSql("   CPC_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   CPC_PRI_UID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCpcPushFlg());
            sql.addIntValue(bean.getCpcPushTime());
            sql.addIntValue(bean.getCpcAuid());
            sql.addDateValue(bean.getCpcAdate());
            sql.addIntValue(bean.getCpcEuid());
            sql.addDateValue(bean.getCpcEdate());
            //where
            sql.addIntValue(bean.getCpcPriUid());

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
     * <p>Update CHT_PRI_CONF Data Bindding JavaBean
     * @param bean CHT_PRI_CONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateDefaultDsp(ChtPriConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CHT_PRI_CONF");
            sql.addSql(" set ");
            sql.addSql("   CPC_DEF_FLG=?,");
            sql.addSql("   CPC_CHAT_KBN=?,");
            sql.addSql("   CPC_SEL_SID=?,");
            sql.addSql("   CPC_EUID=?,");
            sql.addSql("   CPC_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   CPC_PRI_UID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCpcDefFlg());
            sql.addIntValue(bean.getCpcChatKbn());
            sql.addIntValue(bean.getCpcSelSid());
            sql.addIntValue(bean.getCpcEuid());
            sql.addDateValue(bean.getCpcEdate());
            //where
            sql.addIntValue(bean.getCpcPriUid());

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

   /** <br>[機  能]Enter送信フラグアップデート
     * <br>[解  説]
     * <br>[備  考]
     * @param enterKbn Enter区分
     * @param userSid ユーザSID
     * @return count カウント
     * @throws SQLException SQLException
     */
    public int updateEnter(int enterKbn, int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CHT_PRI_CONF");
            sql.addSql(" set ");
            sql.addSql("   CPC_ENTER_FLG=?");
            sql.addSql(" where ");
            sql.addSql("   CPC_PRI_UID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(enterKbn);
            //where
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

    /** <br>[機  能]選択タブアップデート
     * <br>[解  説]
     * <br>[備  考]
     * @param tabCode タブ 0：全て 1:タイムライン
     * @param userSid ユーザSID
     * @return count カウント
     * @throws SQLException SQLException
     */
    public int updateTab(int tabCode, int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CHT_PRI_CONF");
            sql.addSql(" set ");
            sql.addSql("   CPC_SEL_TAB=?");
            sql.addSql(" where ");
            sql.addSql("   CPC_PRI_UID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(tabCode);
            //where
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
     * <p>Select CHT_PRI_CONF All Data
     * @return List in CHT_PRI_CONFModel
     * @throws SQLException SQL実行例外
     */
    public List<ChtPriConfModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ChtPriConfModel> ret = new ArrayList<ChtPriConfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CPC_PRI_UID,");
            sql.addSql("   CPC_PUSH_FLG,");
            sql.addSql("   CPC_PUSH_TIME,");
            sql.addSql("   CPC_DEF_FLG,");
            sql.addSql("   CPC_CHAT_KBN,");
            sql.addSql("   CPC_SEL_SID,");
            sql.addSql("   CPC_ENTER_FLG,");
            sql.addSql("   CPC_AUID,");
            sql.addSql("   CPC_ADATE,");
            sql.addSql("   CPC_EUID,");
            sql.addSql("   CPC_EDATE,");
            sql.addSql("   CPC_SEL_TAB");
            sql.addSql(" from ");
            sql.addSql("   CHT_PRI_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getChtPriConfFromRs(rs));
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
     * <p>Select CHT_PRI_CONF
     * @param cpcPriUid CPC_PRI_UID
     * @return CHT_PRI_CONFModel
     * @throws SQLException SQL実行例外
     */
    public ChtPriConfModel select(int cpcPriUid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ChtPriConfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CPC_PRI_UID,");
            sql.addSql("   CPC_PUSH_FLG,");
            sql.addSql("   CPC_PUSH_TIME,");
            sql.addSql("   CPC_DEF_FLG,");
            sql.addSql("   CPC_CHAT_KBN,");
            sql.addSql("   CPC_SEL_SID,");
            sql.addSql("   CPC_ENTER_FLG,");
            sql.addSql("   CPC_AUID,");
            sql.addSql("   CPC_ADATE,");
            sql.addSql("   CPC_EUID,");
            sql.addSql("   CPC_EDATE,");
            sql.addSql("   CPC_SEL_TAB");
            sql.addSql(" from");
            sql.addSql("   CHT_PRI_CONF");
            sql.addSql(" where ");
            sql.addSql("   CPC_PRI_UID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(cpcPriUid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getChtPriConfFromRs(rs);
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
     * <p>Delete CHT_PRI_CONF
     * @param cpcPriUid CPC_PRI_UID
     * @throws SQLException SQL実行例外
     * @return count
     */
    public int delete(int cpcPriUid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CHT_PRI_CONF");
            sql.addSql(" where ");
            sql.addSql("   CPC_PRI_UID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(cpcPriUid);

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
     * <p>Create CHT_PRI_CONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created ChtPriConfModel
     * @throws SQLException SQL実行例外
     */
    private ChtPriConfModel __getChtPriConfFromRs(ResultSet rs) throws SQLException {
        ChtPriConfModel bean = new ChtPriConfModel();
        bean.setCpcPriUid(rs.getInt("CPC_PRI_UID"));
        bean.setCpcPushFlg(rs.getInt("CPC_PUSH_FLG"));
        bean.setCpcPushTime(rs.getInt("CPC_PUSH_TIME"));
        bean.setCpcDefFlg(rs.getInt("CPC_DEF_FLG"));
        bean.setCpcChatKbn(rs.getInt("CPC_CHAT_KBN"));
        bean.setCpcSelSid(rs.getInt("CPC_SEL_SID"));
        bean.setCpcEnterFlg(rs.getInt("CPC_ENTER_FLG"));
        bean.setCpcAuid(rs.getInt("CPC_AUID"));
        bean.setCpcAdate(UDate.getInstanceTimestamp(rs.getTimestamp("CPC_ADATE")));
        bean.setCpcEuid(rs.getInt("CPC_EUID"));
        bean.setCpcEdate(UDate.getInstanceTimestamp(rs.getTimestamp("CPC_EDATE")));
        bean.setCpcSelTab(rs.getInt("CPC_SEL_TAB"));
        return bean;
    }
}
