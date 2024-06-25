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
import jp.groupsession.v2.cht.model.ChtUserViewModel;

/**
 * <p>CHT_USER_VIEW Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class ChtUserViewDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ChtUserViewDao.class);

    /**
     * <p>Default Constructor
     */
    public ChtUserViewDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public ChtUserViewDao(Connection con) {
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
            sql.addSql("drop table CHT_USER_VIEW");

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
            sql.addSql(" create table CHT_USER_VIEW (");
            sql.addSql("   CUP_SID integer not null,");
            sql.addSql("   CUV_UID integer not null,");
            sql.addSql("   CUD_SID bigint not null,");
            sql.addSql("   primary key (CUP_SID,CUV_UID)");
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
     * @return List in CHT_USER_VIEW
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
            sql.addSql("   CHT_USER_VIEW");

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
     * <p>Insert CHT_USER_VIEW Data Bindding JavaBean
     * @param bean CHT_USER_VIEW Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(ChtUserViewModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CHT_USER_VIEW(");
            sql.addSql("   CUP_SID,");
            sql.addSql("   CUV_UID,");
            sql.addSql("   CUD_SID,");
            sql.addSql("   CUV_VIEWCNT");
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
            sql.addIntValue(bean.getCuvUid());
            sql.addLongValue(bean.getCudSid());
            sql.addIntValue(bean.getCuvViewcnt());
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
     * <br>[機  能] チャットユーザ閲覧情報を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param beanList ChtGroupUserModel DataList
     * @throws SQLException SQL実行例外
     */
    public void insertData(List<ChtUserViewModel> beanList) throws SQLException {

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
            sql.addSql(" CHT_USER_VIEW(");
            sql.addSql("   CUP_SID,");
            sql.addSql("   CUV_UID,");
            sql.addSql("   CUD_SID,");
            sql.addSql("   CUV_VIEWCNT");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            for (ChtUserViewModel bean : beanList) {
                sql.addIntValue(bean.getCupSid());
                sql.addIntValue(bean.getCuvUid());
                sql.addLongValue(bean.getCudSid());
                sql.addIntValue(bean.getCuvViewcnt());
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
     * <p>Update CHT_USER_VIEW Data Bindding JavaBean
     * @param bean CHT_USER_VIEW Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(ChtUserViewModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CHT_USER_VIEW");
            sql.addSql(" set ");
            sql.addSql("   CUD_SID=?");
            sql.addSql(" where ");
            sql.addSql("   CUP_SID=?");
            sql.addSql(" and");
            sql.addSql("   CUV_UID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(bean.getCudSid());
            //where
            sql.addIntValue(bean.getCupSid());
            sql.addIntValue(bean.getCuvUid());

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
     * <p>Select CHT_USER_VIEW All Data
     * @return List in CHT_USER_VIEWModel
     * @throws SQLException SQL実行例外
     */
    public List<ChtUserViewModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ChtUserViewModel> ret = new ArrayList<ChtUserViewModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CUP_SID,");
            sql.addSql("   CUV_UID,");
            sql.addSql("   CUD_SID,");
            sql.addSql("   CUV_VIEWCNT");
            sql.addSql(" from ");
            sql.addSql("   CHT_USER_VIEW");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getChtUserViewFromRs(rs));
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
     * <p>Select ChtUserView All Data
     * @param offset レコードの読取開始行
     * @param limit 1ページの最大件数
     * @return List in ChtUserViewModel
     * @throws SQLException SQL実行例外
     */
    public List<ChtUserViewModel> selectLimit(
            int offset, int limit) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ChtUserViewModel> ret = new ArrayList<ChtUserViewModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CUP_SID,");
            sql.addSql("   CUV_UID,");
            sql.addSql("   CUD_SID,");
            sql.addSql("   CUV_VIEWCNT");
            sql.addSql(" from ");
            sql.addSql("   CHT_USER_VIEW");
            sql.addSql(" order by ");
            sql.addSql("   CUP_SID asc");

            sql.setPagingValue(offset, limit);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getChtUserViewFromRs(rs));
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
     * <p>Select CHT_USER_VIEW
     * @param cupSid CUP_SID
     * @param cuvUid CUV_UID
     * @return CHT_USER_VIEWModel
     * @throws SQLException SQL実行例外
     */
    public ChtUserViewModel select(int cupSid, int cuvUid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ChtUserViewModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CUP_SID,");
            sql.addSql("   CUV_UID,");
            sql.addSql("   CUD_SID,");
            sql.addSql("   CUV_VIEWCNT");
            sql.addSql(" from");
            sql.addSql("   CHT_USER_VIEW");
            sql.addSql(" where ");
            sql.addSql("   CUP_SID=?");
            sql.addSql(" and");
            sql.addSql("   CUV_UID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(cupSid);
            sql.addIntValue(cuvUid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getChtUserViewFromRs(rs);
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
     * <p>Delete CHT_USER_VIEW
     * @param cupSid CUP_SID
     * @param cuvUid CUV_UID
     * @throws SQLException SQL実行例外
     * @return count
     */
    public int delete(int cupSid, int cuvUid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CHT_USER_VIEW");
            sql.addSql(" where ");
            sql.addSql("   CUP_SID=?");
            sql.addSql(" and");
            sql.addSql("   CUV_UID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(cupSid);
            sql.addIntValue(cuvUid);

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
     * <p>指定したメッセージSIDが最大の場合のみ削除
     * <p>ただし削除対象のレコードが存在しない場合
     * @param cupSid CUP_SID
     * @param cuvUid CUV_UID
     * @param msgSid 送信したメッセージSID
     * @throws SQLException SQL実行例外
     * @return count
     */
    public int deleteIfMax(int cupSid, int cuvUid, long msgSid)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            // 対象レコードの存在確認
            sql.addSql(" select");
            sql.addSql("   count(CUP_SID) CNT");
            sql.addSql(" from");
            sql.addSql("   CHT_USER_VIEW");
            sql.addSql(" where");
            sql.addSql("   CUP_SID = ?");
            sql.addSql(" and");
            sql.addSql("   CUV_UID = ?");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(cupSid);
            sql.addIntValue(cuvUid);
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
                sql.addSql("   CHT_USER_VIEW");
                sql.addSql(" where ");
                sql.addSql("   CUP_SID=?");
                sql.addSql(" and");
                sql.addSql("   CUV_UID=?");
                sql.addSql(" and");
                sql.addSql("   ( select");
                sql.addSql("       max(CUD_SID) MAX_SID");
                sql.addSql("     from");
                sql.addSql("       CHT_USER_VIEW");
                sql.addSql("     where");
                sql.addSql("       CUP_SID = ?");
                sql.addSql("     and");
                sql.addSql("       CUV_UID=?");
                sql.addSql("   ) <= ?");

                pstmt = con.prepareStatement(sql.toSqlString());
                sql.addIntValue(cupSid);
                sql.addIntValue(cuvUid);
                sql.addIntValue(cupSid);
                sql.addIntValue(cuvUid);
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
     * <p>Delete CHT_USER_VIEW
     * @param sidList CUP_SID List
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
            sql.addSql("   CHT_USER_VIEW");
            sql.addSql(" where ");
            sql.addSql("   CUP_SID IN (");
            for (int idx = 0; idx < sidList.size(); idx++) {
                if (idx != 0) {
                    sql.addSql("  ,");
                }
                sql.addSql("  ?");
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
     * <p>Select CHT_USER_VIEW
     * @param cupSid ペアSID
     * @param usrSid ユーザSID
     * @return CHT_USER_VIEWModel
     * @throws SQLException SQL実行例外
     */
    public int selectCudSid(int cupSid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CUD_SID");
            sql.addSql(" from");
            sql.addSql("   CHT_USER_VIEW");
            sql.addSql(" where ");
            sql.addSql("   CUP_SID=?");
            sql.addSql(" and");
            sql.addSql("   CUV_UID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(cupSid);
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getInt("CUD_SID");
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
     * <p>Create CHT_USER_VIEW Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created ChtUserViewModel
     * @throws SQLException SQL実行例外
     */
    private ChtUserViewModel __getChtUserViewFromRs(ResultSet rs) throws SQLException {
        ChtUserViewModel bean = new ChtUserViewModel();
        bean.setCupSid(rs.getInt("CUP_SID"));
        bean.setCuvUid(rs.getInt("CUV_UID"));
        bean.setCudSid(rs.getLong("CUD_SID"));
        bean.setCuvViewcnt(rs.getInt("CUV_VIEWCNT"));
        return bean;
    }
    /**
     *
     * <br>[機  能] 既読件数を減らす
     * <br>[解  説]
     * <br>[備  考]
     * @param cupSid ペアSID
     * @param size 減算量
     * @throws SQLException SQL実行時例外
     */
    public void subtractViewCnt(int cupSid, int size) throws SQLException {
        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CHT_USER_VIEW");
            sql.addSql(" set ");
            sql.addSql("   CUV_VIEWCNT = case");
            sql.addSql("     when CUV_VIEWCNT < ? then 0");
            sql.addSql("     else CUV_VIEWCNT - ? end");
            sql.addIntValue(size);
            sql.addIntValue(size);
            sql.addSql(" where ");
            sql.addSql("   CUP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            //where
            sql.addIntValue(cupSid);

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
