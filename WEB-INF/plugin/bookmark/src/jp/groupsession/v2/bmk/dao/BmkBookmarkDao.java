package jp.groupsession.v2.bmk.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.bmk.model.BmkBookmarkModel;

/**
 * <p>BMK_BOOKMARK Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class BmkBookmarkDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(BmkBookmarkDao.class);

    /**
     * <p>Default Constructor
     */
    public BmkBookmarkDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public BmkBookmarkDao(Connection con) {
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
            sql.addSql("drop table BMK_BOOKMARK");

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
            sql.addSql(" create table BMK_BOOKMARK (");
            sql.addSql("   BMK_SID NUMBER(10,0) not null,");
            sql.addSql("   BMK_KBN NUMBER(10,0) not null,");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   GRP_SID NUMBER(10,0),");
            sql.addSql("   BMU_SID NUMBER(10,0),");
            sql.addSql("   BMK_TITLE varchar(150) not null,");
            sql.addSql("   BMK_CMT varchar(1000) not null,");
            sql.addSql("   BMK_SCORE NUMBER(10,0) not null,");
            sql.addSql("   BMK_PUBLIC NUMBER(10,0) not null,");
            sql.addSql("   BMK_MAIN NUMBER(10,0) not null,");
            sql.addSql("   BMK_SORT NUMBER(10,0) not null,");
            sql.addSql("   BMK_AUID NUMBER(10,0) not null,");
            sql.addSql("   BMK_ADATE varchar(23) not null,");
            sql.addSql("   BMK_EUID NUMBER(10,0) not null,");
            sql.addSql("   BMK_EDATE varchar(23) not null,");
            sql.addSql("   primary key (BMK_SID)");
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
     * <p>Insert BMK_BOOKMARK Data Bindding JavaBean
     * @param bean BMK_BOOKMARK Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(BmkBookmarkModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" BMK_BOOKMARK(");
            sql.addSql("   BMK_SID,");
            sql.addSql("   BMK_KBN,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   BMU_SID,");
            sql.addSql("   BMK_TITLE,");
            sql.addSql("   BMK_CMT,");
            sql.addSql("   BMK_SCORE,");
            sql.addSql("   BMK_PUBLIC,");
            sql.addSql("   BMK_MAIN,");
            sql.addSql("   BMK_SORT,");
            sql.addSql("   BMK_AUID,");
            sql.addSql("   BMK_ADATE,");
            sql.addSql("   BMK_EUID,");
            sql.addSql("   BMK_EDATE");
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
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getBmkSid());
            sql.addIntValue(bean.getBmkKbn());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getGrpSid());
            sql.addIntValue(bean.getBmuSid());
            sql.addStrValue(bean.getBmkTitle());
            sql.addStrValue(bean.getBmkCmt());
            sql.addIntValue(bean.getBmkScore());
            sql.addIntValue(bean.getBmkPublic());
            sql.addIntValue(bean.getBmkMain());
            sql.addIntValue(bean.getBmkSort());
            sql.addIntValue(bean.getBmkAuid());
            sql.addDateValue(bean.getBmkAdate());
            sql.addIntValue(bean.getBmkEuid());
            sql.addDateValue(bean.getBmkEdate());
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
     * <p>Update BMK_BOOKMARK Data Bindding JavaBean
     * @param bean BMK_BOOKMARK Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(BmkBookmarkModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   BMK_BOOKMARK");
            sql.addSql(" set ");
            sql.addSql("   BMK_TITLE=?,");
            sql.addSql("   BMK_CMT=?,");
            sql.addSql("   BMK_SCORE=?,");
            sql.addSql("   BMK_PUBLIC=?,");
            sql.addSql("   BMK_MAIN=?,");
            sql.addSql("   BMK_EUID=?,");
            sql.addSql("   BMK_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   BMK_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getBmkTitle());
            sql.addStrValue(bean.getBmkCmt());
            sql.addIntValue(bean.getBmkScore());
            sql.addIntValue(bean.getBmkPublic());
            sql.addIntValue(bean.getBmkMain());
            sql.addIntValue(bean.getBmkEuid());
            sql.addDateValue(bean.getBmkEdate());
            //where
            sql.addIntValue(bean.getBmkSid());

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
     * <p>Update BMK_BOOKMARK Data Bindding JavaBean
     * @param bean BMK_BOOKMARK Data Bindding JavaBean
     * @param usrSid USR_SID
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateMainKbn(BmkBookmarkModel bean, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   BMK_BOOKMARK");
            sql.addSql(" set ");
            sql.addSql("   BMK_MAIN=?,");
            sql.addSql("   BMK_SORT=?,");
            sql.addSql("   BMK_EUID=?,");
            sql.addSql("   BMK_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   BMK_SID=?");
            sql.addSql("   and");
            sql.addSql("   USR_SID=?");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getBmkMain());
            sql.addIntValue(bean.getBmkSort());
            sql.addIntValue(bean.getBmkEuid());
            sql.addDateValue(bean.getBmkEdate());
            //where
            sql.addIntValue(bean.getBmkSid());
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
     * <p>Select BMK_BOOKMARK All Data
     * @return List in BMK_BOOKMARKModel
     * @throws SQLException SQL実行例外
     */
    public List<BmkBookmarkModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<BmkBookmarkModel> ret = new ArrayList<BmkBookmarkModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   BMK_SID,");
            sql.addSql("   BMK_KBN,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   BMU_SID,");
            sql.addSql("   BMK_TITLE,");
            sql.addSql("   BMK_CMT,");
            sql.addSql("   BMK_SCORE,");
            sql.addSql("   BMK_PUBLIC,");
            sql.addSql("   BMK_MAIN,");
            sql.addSql("   BMK_SORT,");
            sql.addSql("   BMK_AUID,");
            sql.addSql("   BMK_ADATE,");
            sql.addSql("   BMK_EUID,");
            sql.addSql("   BMK_EDATE");
            sql.addSql(" from ");
            sql.addSql("   BMK_BOOKMARK");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getBmkBookmarkFromRs(rs));
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
     * <p>Select BMK_BOOKMARK
     * @param bmkSid BMK_SID
     * @return BMK_BOOKMARKModel
     * @throws SQLException SQL実行例外
     */
    public BmkBookmarkModel select(int bmkSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        BmkBookmarkModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BMK_SID,");
            sql.addSql("   BMK_KBN,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   BMU_SID,");
            sql.addSql("   BMK_TITLE,");
            sql.addSql("   BMK_CMT,");
            sql.addSql("   BMK_SCORE,");
            sql.addSql("   BMK_PUBLIC,");
            sql.addSql("   BMK_MAIN,");
            sql.addSql("   BMK_SORT,");
            sql.addSql("   BMK_AUID,");
            sql.addSql("   BMK_ADATE,");
            sql.addSql("   BMK_EUID,");
            sql.addSql("   BMK_EDATE");
            sql.addSql(" from");
            sql.addSql("   BMK_BOOKMARK");
            sql.addSql(" where ");
            sql.addSql("   BMK_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bmkSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getBmkBookmarkFromRs(rs);
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
     * <br>[機  能] 指定されたユーザの指定URLブックマーク情報を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param bmuSid URLSID
     * @param usrSid ユーザSID
     * @return BmkBookmarkModel
     * @throws SQLException SQL実行例外
     */
    public BmkBookmarkModel getUsrBookmark(int bmuSid, int usrSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        BmkBookmarkModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BMK_SID,");
            sql.addSql("   BMK_KBN,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   BMU_SID,");
            sql.addSql("   BMK_TITLE,");
            sql.addSql("   BMK_CMT,");
            sql.addSql("   BMK_SCORE,");
            sql.addSql("   BMK_PUBLIC,");
            sql.addSql("   BMK_MAIN,");
            sql.addSql("   BMK_SORT,");
            sql.addSql("   BMK_AUID,");
            sql.addSql("   BMK_ADATE,");
            sql.addSql("   BMK_EUID,");
            sql.addSql("   BMK_EDATE");
            sql.addSql(" from");
            sql.addSql("   BMK_BOOKMARK");
            sql.addSql(" where");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   BMU_SID=?");
            sql.addIntValue(usrSid);
            sql.addIntValue(bmuSid);

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getBmkBookmarkFromRs(rs);
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
     * <br>[機  能] 指定されたユーザのブックマーク情報を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return List in BmkBookmarkModel
     * @throws SQLException SQL実行例外
     */
    public List<BmkBookmarkModel> getUsrBookmark(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<BmkBookmarkModel> ret = new ArrayList<BmkBookmarkModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   BMK_SID,");
            sql.addSql("   BMK_KBN,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   BMU_SID,");
            sql.addSql("   BMK_TITLE,");
            sql.addSql("   BMK_CMT,");
            sql.addSql("   BMK_SCORE,");
            sql.addSql("   BMK_PUBLIC,");
            sql.addSql("   BMK_MAIN,");
            sql.addSql("   BMK_SORT,");
            sql.addSql("   BMK_AUID,");
            sql.addSql("   BMK_ADATE,");
            sql.addSql("   BMK_EUID,");
            sql.addSql("   BMK_EDATE");
            sql.addSql(" from ");
            sql.addSql("   BMK_BOOKMARK");
            sql.addSql(" where ");
            sql.addSql("   USR_SID = ?");
            sql.addSql(" order by ");
            sql.addSql("   BMK_SORT asc");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(userSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getBmkBookmarkFromRs(rs));
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
     * <br>[機  能] 指定されたグループのブックマーク情報を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param groupSid グループSID
     * @return List in BmkBookmarkModel
     * @throws SQLException SQL実行例外
     */
    public List<BmkBookmarkModel> getGroupBookmark(int groupSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<BmkBookmarkModel> ret = new ArrayList<BmkBookmarkModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   BMK_SID,");
            sql.addSql("   BMK_KBN,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   BMU_SID,");
            sql.addSql("   BMK_TITLE,");
            sql.addSql("   BMK_CMT,");
            sql.addSql("   BMK_SCORE,");
            sql.addSql("   BMK_PUBLIC,");
            sql.addSql("   BMK_MAIN,");
            sql.addSql("   BMK_SORT,");
            sql.addSql("   BMK_AUID,");
            sql.addSql("   BMK_ADATE,");
            sql.addSql("   BMK_EUID,");
            sql.addSql("   BMK_EDATE");
            sql.addSql(" from ");
            sql.addSql("   BMK_BOOKMARK");
            sql.addSql(" where ");
            sql.addSql("   GRP_SID = ?");
            sql.addSql(" order by ");
            sql.addSql("   BMK_SORT asc");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(groupSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getBmkBookmarkFromRs(rs));
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
     * <br>[機  能] 編集対象のブックマーク情報の取得を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param bmkSidList ブックマークSIDリスト
     * @return ブックマーク情報リスト
     * @throws SQLException SQL実行例外
     */
    public List<BmkBookmarkModel> select(List<Integer> bmkSidList) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<BmkBookmarkModel> ret = new ArrayList<BmkBookmarkModel>();
        con = getCon();

        if (bmkSidList == null || bmkSidList.size() == 0) {
            return ret;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BMK_SID,");
            sql.addSql("   BMK_KBN,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   BMU_SID,");
            sql.addSql("   BMK_TITLE,");
            sql.addSql("   BMK_CMT,");
            sql.addSql("   BMK_SCORE,");
            sql.addSql("   BMK_PUBLIC,");
            sql.addSql("   BMK_MAIN,");
            sql.addSql("   BMK_SORT,");
            sql.addSql("   BMK_AUID,");
            sql.addSql("   BMK_ADATE,");
            sql.addSql("   BMK_EUID,");
            sql.addSql("   BMK_EDATE");
            sql.addSql(" from");
            sql.addSql("   BMK_BOOKMARK");
            sql.addSql(" where");
            sql.addSql("   BMK_SID in (");
            for (int i = 0; i < bmkSidList.size(); i++) {
                if (i != 0) {
                    sql.addSql(" , ");
                }
                sql.addSql("     ?");
                sql.addIntValue(bmkSidList.get(i));
            }
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getBmkBookmarkFromRs(rs));
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
     * <p>Delete BMK_BOOKMARK
     * @param bmkSid BMK_SID
     * @return int
     * @throws SQLException SQL実行例外
     */
    public int delete(int bmkSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   BMK_BOOKMARK");
            sql.addSql(" where ");
            sql.addSql("   BMK_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bmkSid);

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
     * <p>Delete BMK_BOOKMARK
     * @param grpSid GRP_SID
     * @param usrSid USR_SID
     * @return int
     * @throws SQLException SQL実行例外
     */
    public int delete(int grpSid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   BMK_BOOKMARK");
            sql.addSql(" where ");
            if (grpSid != -1) {
                sql.addSql("   GRP_SID=?");
                sql.addIntValue(grpSid);
            } else {
                sql.addSql("   USR_SID=?");
                sql.addIntValue(usrSid);
            }
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
    *
    * <br>[機  能] 指定SIDのデータサイズを返す
    * <br>[解  説]
    * <br>[備  考]
    * @param sidList 指定SID
    * @return データサイズ
    * @throws SQLException SQL実行時例外
    */
   public Long getDiskSize(List<Integer> sidList) throws SQLException {

       if (sidList == null || sidList.size() == 0) {
           return (long) 0;
       }
       String inSidStr = sidList.stream()
               .map(sid -> sid.toString())
               .collect(Collectors.joining(",", "(", ")"));
       long ret = 0;

       PreparedStatement pstmt = null;
       ResultSet rs = null;
       Connection con = null;
       con = getCon();

       try {
           //SQL文
           SqlBuffer sql = new SqlBuffer();
           sql.addSql(" select ");
           sql.addSql("   sum(octet_length(BMK_TITLE)) as BMK_TITLE,");
           sql.addSql("   sum(octet_length(BMK_CMT)) as BMK_CMT");
           sql.addSql(" from ");
           sql.addSql("   BMK_BOOKMARK ");
           sql.addSql(" where  ");
           sql.addSql("   BMK_SID in ");
           sql.addSql(inSidStr);

           pstmt = con.prepareStatement(sql.toSqlString());
           log__.info(sql.toLogString());
           rs = pstmt.executeQuery();
           if (rs.next()) {
               ret = rs.getLong("BMK_TITLE");
               ret += rs.getLong("BMK_CMT");
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
     * <p>Create BMK_BOOKMARK Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created BmkBookmarkModel
     * @throws SQLException SQL実行例外
     */
    private BmkBookmarkModel __getBmkBookmarkFromRs(ResultSet rs) throws SQLException {
        BmkBookmarkModel bean = new BmkBookmarkModel();
        bean.setBmkSid(rs.getInt("BMK_SID"));
        bean.setBmkKbn(rs.getInt("BMK_KBN"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setGrpSid(rs.getInt("GRP_SID"));
        bean.setBmuSid(rs.getInt("BMU_SID"));
        bean.setBmkTitle(rs.getString("BMK_TITLE"));
        bean.setBmkCmt(rs.getString("BMK_CMT"));
        bean.setBmkScore(rs.getInt("BMK_SCORE"));
        bean.setBmkPublic(rs.getInt("BMK_PUBLIC"));
        bean.setBmkMain(rs.getInt("BMK_MAIN"));
        bean.setBmkSort(rs.getInt("BMK_SORT"));
        bean.setBmkAuid(rs.getInt("BMK_AUID"));
        bean.setBmkAdate(UDate.getInstanceTimestamp(rs.getTimestamp("BMK_ADATE")));
        bean.setBmkEuid(rs.getInt("BMK_EUID"));
        bean.setBmkEdate(UDate.getInstanceTimestamp(rs.getTimestamp("BMK_EDATE")));
        return bean;
    }
}
