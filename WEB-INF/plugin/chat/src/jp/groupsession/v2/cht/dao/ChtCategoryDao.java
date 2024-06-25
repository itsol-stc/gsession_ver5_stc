package jp.groupsession.v2.cht.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cht.GSConstChat;
import jp.groupsession.v2.cht.model.ChtCategoryModel;

/**
 * <p>CHT_CATEGORY Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class ChtCategoryDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ChtCategoryDao.class);

    /**
     * <p>Default Constructor
     */
    public ChtCategoryDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public ChtCategoryDao(Connection con) {
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
            sql.addSql("drop table CHT_CATEGORY");

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
            sql.addSql(" create table CHT_CATEGORY (");
            sql.addSql("   CHC_SID integer not null,");
            sql.addSql("   CHC_NAME varchar(20) not null,");
            sql.addSql("   CHC_SORT integer not null,");
            sql.addSql("   CHC_AUID integer not null,");
            sql.addSql("   CHC_ADATE timestamp not null,");
            sql.addSql("   CHC_EUID integer not null,");
            sql.addSql("   CHC_EDATE timestamp not null,");
            sql.addSql("   primary key (CHC_SID)");
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
     * <p>Insert CHT_CATEGORY Data Bindding JavaBean
     * @param bean CHT_CATEGORY Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(ChtCategoryModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CHT_CATEGORY(");
            sql.addSql("   CHC_SID,");
            sql.addSql("   CHC_NAME,");
            sql.addSql("   CHC_SORT,");
            sql.addSql("   CHC_AUID,");
            sql.addSql("   CHC_ADATE,");
            sql.addSql("   CHC_EUID,");
            sql.addSql("   CHC_EDATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getChcSid());
            sql.addStrValue(bean.getChcName());
            sql.addIntValue(bean.getChcSort());
            sql.addIntValue(bean.getChcAuid());
            sql.addDateValue(bean.getChcAdate());
            sql.addIntValue(bean.getChcEuid());
            sql.addDateValue(bean.getChcEdate());
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
     * <p>Update CHT_CATEGORY Data Bindding JavaBean
     * @param bean CHT_CATEGORY Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(ChtCategoryModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CHT_CATEGORY");
            sql.addSql(" set ");
            sql.addSql("   CHC_NAME=?,");
            sql.addSql("   CHC_SORT=?,");
            sql.addSql("   CHC_EUID=?,");
            sql.addSql("   CHC_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   CHC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getChcName());
            sql.addIntValue(bean.getChcSort());
            sql.addIntValue(bean.getChcEuid());
            sql.addDateValue(bean.getChcEdate());
            //where
            sql.addIntValue(bean.getChcSid());

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
     * <p>Select CHT_CATEGORY All Data
     * @return List in CHT_CATEGORYModel
     * @throws SQLException SQL実行例外
     */
    public List<ChtCategoryModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ChtCategoryModel> ret = new ArrayList<ChtCategoryModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CHC_SID,");
            sql.addSql("   CHC_NAME,");
            sql.addSql("   CHC_SORT,");
            sql.addSql("   CHC_AUID,");
            sql.addSql("   CHC_ADATE,");
            sql.addSql("   CHC_EUID,");
            sql.addSql("   CHC_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CHT_CATEGORY");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getChtCategoryFromRs(rs));
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
     * <p>Select CHT_CATEGORY All Data
     * @return List in CHT_CATEGORYModel
     * @throws SQLException SQL実行例外
     */
    public List<ChtCategoryModel> selectCategory() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ChtCategoryModel> ret = new ArrayList<ChtCategoryModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CHC_SID,");
            sql.addSql("   CHC_NAME,");
            sql.addSql("   CHC_SORT,");
            sql.addSql("   CHC_AUID,");
            sql.addSql("   CHC_ADATE,");
            sql.addSql("   CHC_EUID,");
            sql.addSql("   CHC_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CHT_CATEGORY");
            sql.addSql(" where ");
            sql.addSql("   CHC_SID <> ?");
            sql.addSql(" and ");
            sql.addSql("   CHC_SID <> ?");
            sql.addSql(" order by  ");
            sql.addSql("   CHC_SORT");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(GSConstChat.CHAT_CHC_SID_NO);
            sql.addIntValue(GSConstChat.CHAT_CHC_SID_GENERAL);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getChtCategoryFromRs(rs));
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
     * <p>Select CHT_CATEGORY
     * @param chcSid CHC_SID
     * @return CHT_CATEGORYModel
     * @throws SQLException SQL実行例外
     */
    public ChtCategoryModel select(int chcSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ChtCategoryModel ret = new ChtCategoryModel();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CHC_SID,");
            sql.addSql("   CHC_NAME,");
            sql.addSql("   CHC_SORT,");
            sql.addSql("   CHC_AUID,");
            sql.addSql("   CHC_ADATE,");
            sql.addSql("   CHC_EUID,");
            sql.addSql("   CHC_EDATE");
            sql.addSql(" from");
            sql.addSql("   CHT_CATEGORY");
            sql.addSql(" where ");
            sql.addSql("   CHC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(chcSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getChtCategoryFromRs(rs);
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
     * カテゴリ一覧を取得
     * @param list カテゴリ一覧
     * @return カテゴリ一覧
     * @throws SQLException SQL実行例外
     */
    public List<LabelValueBean> getCategoryLabel(List<LabelValueBean> list) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CHC_SID,");
            sql.addSql("   CHC_NAME");
            sql.addSql(" from");
            sql.addSql("   CHT_CATEGORY");
            sql.addSql(" order by");
            sql.addSql("   CHC_SORT");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                LabelValueBean label = new LabelValueBean();
                label.setValue(rs.getString("CHC_SID"));
                label.setLabel(rs.getString("CHC_NAME"));
                list.add(label);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return list;
    }

    /**
     * カテゴリ存在チェック
     * @param chcSid カテゴリSID
     * @param defCategory カテゴリなし、グループカテゴリを含むか
     * @return カテゴリ有(true)無(false)
     * @throws SQLException SQL実行例外
     */
    public boolean isExitCategory(int chcSid, boolean defCategory) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        boolean ret = false;
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CHC_SID");
            sql.addSql(" from");
            sql.addSql("   CHT_CATEGORY");
            sql.addSql(" where");
            sql.addSql("   CHC_SID = ?");
            if (!defCategory) {
                sql.addSql(" and ");
                sql.addSql("   CHC_SID <> ?");
                sql.addSql(" and ");
                sql.addSql("   CHC_SID <> ?");
            }
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(chcSid);
            if (!defCategory) {
                sql.addIntValue(GSConstChat.CHAT_CHC_SID_NO);
                sql.addIntValue(GSConstChat.CHAT_CHC_SID_GENERAL);
            }
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = true;
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
     * <p>Delete CHT_CATEGORY
     * @param chcSid CHC_SID
     * @throws SQLException SQL実行例外
     * @return count count
     */
    public int delete(int chcSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CHT_CATEGORY");
            sql.addSql(" where ");
            sql.addSql("   CHC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(chcSid);

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
     * <p>Create CHT_CATEGORY Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created ChtCategoryModel
     * @throws SQLException SQL実行例外
     */
    private ChtCategoryModel __getChtCategoryFromRs(ResultSet rs) throws SQLException {
        ChtCategoryModel bean = new ChtCategoryModel();
        bean.setChcSid(rs.getInt("CHC_SID"));
        bean.setChcName(rs.getString("CHC_NAME"));
        bean.setChcSort(rs.getInt("CHC_SORT"));
        bean.setChcAuid(rs.getInt("CHC_AUID"));
        bean.setChcAdate(UDate.getInstanceTimestamp(rs.getTimestamp("CHC_ADATE")));
        bean.setChcEuid(rs.getInt("CHC_EUID"));
        bean.setChcEdate(UDate.getInstanceTimestamp(rs.getTimestamp("CHC_EDATE")));
        return bean;
    }

}
