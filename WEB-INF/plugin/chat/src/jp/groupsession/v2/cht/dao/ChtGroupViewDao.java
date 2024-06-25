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
import jp.groupsession.v2.cht.model.ChtGroupViewModel;

/**
 * <p>CHT_GROUP_VIEW Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class ChtGroupViewDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ChtGroupViewDao.class);

    /**
     * <p>Default Constructor
     */
    public ChtGroupViewDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public ChtGroupViewDao(Connection con) {
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
            sql.addSql("drop table CHT_GROUP_VIEW");

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
            sql.addSql(" create table CHT_GROUP_VIEW (");
            sql.addSql("   CGI_SID integer not null,");
            sql.addSql("   CGV_UID integer not null,");
            sql.addSql("   CGD_SID bigint not null,");
            sql.addSql("   CGV_VIEWCNT integer not null,");
            sql.addSql("   primary key (CGI_SID,CGV_UID)");
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
     * @return List in ChtGroupViewModel
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
            sql.addSql("   CHT_GROUP_VIEW");

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
     * <p>Insert CHT_GROUP_VIEW Data Bindding JavaBean
     * @param bean CHT_GROUP_VIEW Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(ChtGroupViewModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CHT_GROUP_VIEW(");
            sql.addSql("   CGI_SID,");
            sql.addSql("   CGV_UID,");
            sql.addSql("   CGD_SID,");
            sql.addSql("   CGV_VIEWCNT");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCgiSid());
            sql.addIntValue(bean.getCgvUid());
            sql.addLongValue(bean.getCgdSid());
            sql.addIntValue(bean.getCgvViewcnt());
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
     * <br>[機  能] チャットグループ閲覧情報を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param beanList ChtGroupUserModel DataList
     * @throws SQLException SQL実行例外
     */
    public void insertData(List<ChtGroupViewModel> beanList) throws SQLException {

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
            sql.addSql(" CHT_GROUP_VIEW(");
            sql.addSql("   CGI_SID,");
            sql.addSql("   CGV_UID,");
            sql.addSql("   CGD_SID,");
            sql.addSql("   CGV_VIEWCNT");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            for (ChtGroupViewModel bean : beanList) {
                sql.addIntValue(bean.getCgiSid());
                sql.addIntValue(bean.getCgvUid());
                sql.addLongValue(bean.getCgdSid());
                sql.addIntValue(bean.getCgvViewcnt());
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
     * <p>Update CHT_GROUP_VIEW Data Bindding JavaBean
     * @param bean CHT_GROUP_VIEW Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(ChtGroupViewModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CHT_GROUP_VIEW");
            sql.addSql(" set ");
            sql.addSql("   CGD_SID=?");
            sql.addSql(" where ");
            sql.addSql("   CGI_SID=?");
            sql.addSql(" and");
            sql.addSql("   CGV_UID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(bean.getCgdSid());
            //where
            sql.addIntValue(bean.getCgiSid());
            sql.addIntValue(bean.getCgvUid());

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
     * <p>Select CHT_GROUP_VIEW All Data
     * @return List in CHT_GROUP_VIEWModel
     * @throws SQLException SQL実行例外
     */
    public List<ChtGroupViewModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ChtGroupViewModel> ret = new ArrayList<ChtGroupViewModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CGI_SID,");
            sql.addSql("   CGV_UID,");
            sql.addSql("   CGD_SID,");
            sql.addSql("   CGV_VIEWCNT");
            sql.addSql(" from ");
            sql.addSql("   CHT_GROUP_VIEW");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getChtGroupViewFromRs(rs));
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
     * @return List in ChtGroupDataModel
     * @throws SQLException SQL実行例外
     */
    public List<ChtGroupViewModel> selectLimit(
            int offset, int limit) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ChtGroupViewModel> ret = new ArrayList<ChtGroupViewModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CGI_SID,");
            sql.addSql("   CGV_UID,");
            sql.addSql("   CGD_SID,");
            sql.addSql("   CGV_VIEWCNT");
            sql.addSql(" from ");
            sql.addSql("   CHT_GROUP_VIEW");
            sql.addSql(" order by ");
            sql.addSql("   CGI_SID asc");

            sql.setPagingValue(offset, limit);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getChtGroupViewFromRs(rs));
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
     * <p>Select CHT_GROUP_VIEW
     * @param cgiSid CGI_SID
     * @param cgvUid CGV_UID
     * @return CHT_GROUP_VIEWModel
     * @throws SQLException SQL実行例外
     */
    public ChtGroupViewModel select(int cgiSid, int cgvUid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ChtGroupViewModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CGI_SID,");
            sql.addSql("   CGV_UID,");
            sql.addSql("   CGD_SID,");
            sql.addSql("   CGV_VIEWCNT");
            sql.addSql(" from");
            sql.addSql("   CHT_GROUP_VIEW");
            sql.addSql(" where ");
            sql.addSql("   CGI_SID=?");
            sql.addSql(" and");
            sql.addSql("   CGV_UID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(cgiSid);
            sql.addIntValue(cgvUid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getChtGroupViewFromRs(rs);
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
     * <p>Delete CHT_GROUP_VIEW
     * @param cgiSid CGI_SID
     * @param cgvUid CGV_UID
     * @throws SQLException SQL実行例外
     * @return count
     */
    public int delete(int cgiSid, int cgvUid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CHT_GROUP_VIEW");
            sql.addSql(" where ");
            sql.addSql("   CGI_SID=?");
            sql.addSql(" and");
            sql.addSql("   CGV_UID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(cgiSid);
            sql.addIntValue(cgvUid);

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
     * <p>追加するメッセージが既存のメッセージSIDよりも大きい場合削除
     * @param cgiSid CGI_SID
     * @param cgvUid CGV_UID
     * @param msgSid 追加するメッセージSID
     * @throws SQLException SQL実行例外
     * @return count
     */
    public int deleteIfMax(int cgiSid, int cgvUid, long msgSid)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs  = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            // 対象レコードの存在確認
            sql.addSql(" select");
            sql.addSql("   count(CGI_SID) CNT");
            sql.addSql(" from");
            sql.addSql("   CHT_GROUP_VIEW");
            sql.addSql(" where");
            sql.addSql("   CGI_SID = ?");
            sql.addSql(" and");
            sql.addSql("   CGV_UID=?");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(cgiSid);
            sql.addIntValue(cgvUid);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            int viewCnt = 0;
            if (rs.next()) {
                viewCnt = rs.getInt("CNT");
            }
            sql = new SqlBuffer();

            // 削除対象のレコードが存在する場合は削除を実行
            if (viewCnt > 0) {
                sql.addSql(" delete");
                sql.addSql(" from");
                sql.addSql("   CHT_GROUP_VIEW");
                sql.addSql(" where ");
                sql.addSql("   CGI_SID=?");
                sql.addSql(" and");
                sql.addSql("   CGV_UID=?");
                sql.addSql(" and");
                sql.addSql(" ( select");
                sql.addSql("     max(CGD_SID) MAX_SID");
                sql.addSql("   from");
                sql.addSql("     CHT_GROUP_VIEW");
                sql.addSql("   where");
                sql.addSql("     CGI_SID = ?");
                sql.addSql("   and");
                sql.addSql("     CGV_UID=?");
                sql.addSql(" ) <= ?");

                pstmt = con.prepareStatement(sql.toSqlString());
                sql.addIntValue(cgiSid);
                sql.addIntValue(cgvUid);
                sql.addIntValue(cgiSid);
                sql.addIntValue(cgvUid);
                sql.addLongValue(msgSid);

                log__.info(sql.toLogString());
                sql.setParameter(pstmt);
                count = pstmt.executeUpdate();
            // 存在しない場合は1を返す
            } else {
                count = 1;
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <p>Delete CHT_GROUP_VIEW
     * @param sidList cgiSidList
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
            sql.addSql("   CHT_GROUP_VIEW");
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
     * <p>Delete CHT_GROUP_VIEW
     * @param cgiSid グループSID
     * @param sidList usrList
     * @throws SQLException SQL実行例外
     * @return count
     */
    public int deleteUser(int cgiSid, List<Integer> sidList) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CHT_GROUP_VIEW");
            sql.addSql(" where ");
            sql.addSql("   CGI_SID = ?");
            sql.addIntValue(cgiSid);
            sql.addSql(" and ");
            sql.addSql("   CGV_UID NOT IN ( ");
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
     * <p>Create CHT_GROUP_VIEW Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created ChtGroupViewModel
     * @throws SQLException SQL実行例外
     */
    private ChtGroupViewModel __getChtGroupViewFromRs(ResultSet rs) throws SQLException {
        ChtGroupViewModel bean = new ChtGroupViewModel();
        bean.setCgiSid(rs.getInt("CGI_SID"));
        bean.setCgvUid(rs.getInt("CGV_UID"));
        bean.setCgdSid(rs.getLong("CGD_SID"));
        bean.setCgvViewcnt(rs.getInt("CGV_VIEWCNT"));
        return bean;
    }
    /**
    *
    * <br>[機  能] 既読件数を減らす
    * <br>[解  説]
    * <br>[備  考]
    * @param cgiSid ペアSID
    * @param size 減算量
    * @throws SQLException SQL実行時例外
    */
   public void subtractViewCnt(int cgiSid, int size) throws SQLException {
       PreparedStatement pstmt = null;
       Connection con = null;
       con = getCon();

       try {
           //SQL文
           SqlBuffer sql = new SqlBuffer();
           sql.addSql(" update");
           sql.addSql("   CHT_GROUP_VIEW");
           sql.addSql(" set ");
           sql.addSql("   CGV_VIEWCNT = case");
           sql.addSql("     when CGV_VIEWCNT < ? then 0");
           sql.addSql("     else CGV_VIEWCNT - ? end");
           sql.addIntValue(size);
           sql.addIntValue(size);
           sql.addSql(" where ");
           sql.addSql("   CGI_SID=?");

           pstmt = con.prepareStatement(sql.toSqlString());
           //where
           sql.addIntValue(cgiSid);

           log__.info(sql.toLogString());
           sql.setParameter(pstmt);
           pstmt.executeUpdate();
       } catch (SQLException e) {
           throw e;
       } finally {
           JDBCUtil.closeStatement(pstmt);
       }

   }

}
