package jp.groupsession.v2.cht.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cht.model.ChtGroupDataSumModel;

/**
 * <p>CHT_GROUP_DATA_SUM Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class ChtGroupDataSumDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ChtGroupDataSumDao.class);

    /**
     * <p>Default Constructor
     */
    public ChtGroupDataSumDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public ChtGroupDataSumDao(Connection con) {
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
            sql.addSql("drop table CHT_GROUP_DATA_SUM");

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
            sql.addSql(" create table CHT_GROUP_DATA_SUM (");
            sql.addSql("   CGI_SID integer not null,");
            sql.addSql("   CGS_CNT integer not null,");
            sql.addSql("   CGS_LASTSID bigint not null,");
            sql.addSql("   CGS_LASTDATE timestamp,");
            sql.addSql("   primary key (CGI_SID)");
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
     * <p>Insert CHT_GROUP_DATA_SUM Data Bindding JavaBean
     * @param bean CHT_GROUP_DATA_SUM Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(ChtGroupDataSumModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CHT_GROUP_DATA_SUM(");
            sql.addSql("   CGI_SID,");
            sql.addSql("   CGS_CNT,");
            sql.addSql("   CGS_LASTSID,");
            sql.addSql("   CGS_LASTDATE");
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
            sql.addIntValue(bean.getCgsCnt());
            sql.addLongValue(bean.getCgsLastsid());
            sql.addDateValue(bean.getCgsLastdate());
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
     * <p>Update CHT_GROUP_DATA_SUM Data Bindding JavaBean
     * @param bean CHT_GROUP_DATA_SUM Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(ChtGroupDataSumModel bean) throws SQLException {
        return update(
                Arrays.asList(bean)
                );

    }
    /**
     * <p>Update CHT_GROUP_DATA_SUM Data Bindding JavaBean
     * @param beanList CHT_GROUP_DATA_SUM Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(List<ChtGroupDataSumModel> beanList) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CHT_GROUP_DATA_SUM");
            sql.addSql(" set ");
            sql.addSql("   CGS_CNT=?,");
            sql.addSql("   CGS_LASTSID=?,");
            sql.addSql("   CGS_LASTDATE=?");
            sql.addSql(" where ");
            sql.addSql("   CGI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            for (ChtGroupDataSumModel bean : beanList) {

                sql.addIntValue(bean.getCgsCnt());
                sql.addLongValue(bean.getCgsLastsid());
                sql.addDateValue(bean.getCgsLastdate());
                //where
                sql.addIntValue(bean.getCgiSid());

                log__.info(sql.toLogString());
                sql.setParameter(pstmt);
                count += pstmt.executeUpdate();

                sql.clearValue();
                pstmt.clearParameters();
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <p>Select CHT_GROUP_DATA_SUM All Data
     * @return List in CHT_GROUP_DATA_SUMModel
     * @throws SQLException SQL実行例外
     */
    public List<ChtGroupDataSumModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ChtGroupDataSumModel> ret = new ArrayList<>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CGI_SID,");
            sql.addSql("   CGS_CNT,");
            sql.addSql("   CGS_LASTSID,");
            sql.addSql("   CGS_LASTDATE");
            sql.addSql(" from ");
            sql.addSql("   CHT_GROUP_DATA_SUM");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getChtGroupCorrectFromRs(rs));
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
     * <p>Select CHT_GROUP_DATA_SUM
     * @param cgiSid CGI_SID
     * @return CHT_GROUP_DATA_SUMModel
     * @throws SQLException SQL実行例外
     */
    public ChtGroupDataSumModel select(int cgiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ChtGroupDataSumModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CGI_SID,");
            sql.addSql("   CGS_CNT,");
            sql.addSql("   CGS_LASTSID,");
            sql.addSql("   CGS_LASTDATE");
            sql.addSql(" from");
            sql.addSql("   CHT_GROUP_DATA_SUM");
            sql.addSql(" where ");
            sql.addSql("   CGI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(cgiSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getChtGroupCorrectFromRs(rs);
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
     * <p>Delete CHT_GROUP_DATA_SUM
     * @param cgiSid CGI_SID
     * @throws SQLException SQL実行例外
     * @return 削除件数
     */
    public int delete(int cgiSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CHT_GROUP_DATA_SUM");
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
     * <p>Delete CHT_GROUP_DATA_SUM
     * @param groupDelList CGI_SID
     * @throws SQLException SQL実行例外
     * @return 削除件数
     */
    public int delete(List<Integer> groupDelList) throws SQLException {
        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();
        if (groupDelList == null || groupDelList.size() == 0) {
            return count;
        }
        Iterator<Integer> it = groupDelList.iterator();
        int cursor = 0;
        while (it.hasNext()) {

            try {
                //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" delete");
                sql.addSql(" from");
                sql.addSql("   CHT_GROUP_DATA_SUM");
                sql.addSql(" where ");
                sql.addSql("   CGI_SID in (");
                boolean first = true;
                while ((first || cursor % 1000 != 0)
                        && it.hasNext()) {
                    if (first) {
                        sql.addSql(" ? ");
                    } else {
                        sql.addSql(" ,? ");
                    }
                    sql.addIntValue(it.next());

                    first = false;
                    cursor++;
                }

                sql.addSql(" )");

                pstmt = con.prepareStatement(sql.toSqlString());

                log__.info(sql.toLogString());
                sql.setParameter(pstmt);
                count += pstmt.executeUpdate();
            } catch (SQLException e) {
                throw e;
            } finally {
                JDBCUtil.closeStatement(pstmt);
            }
        }
        return count;

    }


    /**
     * <p>Create CHT_GROUP_DATA_SUM Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created ChtGroupCorrectModel
     * @throws SQLException SQL実行例外
     */
    private ChtGroupDataSumModel __getChtGroupCorrectFromRs(ResultSet rs) throws SQLException {
        ChtGroupDataSumModel bean = new ChtGroupDataSumModel();
        bean.setCgiSid(rs.getInt("CGI_SID"));
        bean.setCgsCnt(rs.getInt("CGS_CNT"));
        bean.setCgsLastsid(rs.getInt("CGS_LASTSID"));
        bean.setCgsLastdate(UDate.getInstanceTimestamp(rs.getTimestamp("CGS_LASTDATE")));
        return bean;
    }

    /**
    *
    * <br>[機  能] 投稿件数を減らす
    * <br>[解  説]
    * <br>[備  考]
    * @param cgiSid チャットグループSID
    * @param size 減算量
    * @throws SQLException SQL実行時例外
    */
    public void subtractCnt(int cgiSid, int size) throws SQLException {
        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CHT_GROUP_DATA_SUM");
            sql.addSql(" set ");
            sql.addSql("   CGS_CNT = case");
            sql.addSql("     when CGS_CNT < ? then 0");
            sql.addSql("     else CGS_CNT - ? end");
            sql.addIntValue(size);
            sql.addIntValue(size);
            sql.addSql("   , ");
            sql.addSql("   CGS_LASTDATE = case");
            sql.addSql("     when CGS_CNT < ? then null");
            sql.addSql("     else CGS_LASTDATE end");
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
    /**
    *
    * <br>[機  能] 未集計の投稿情報から投稿集計モデルを作成する
    * <br>[解  説]
    * <br>[備  考]
    * @return 更新情報
    * @throws SQLException SQL実行例外
    */
    private List<ChtGroupDataSumModel> __selectUpdateSum() throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        ArrayList<ChtGroupDataSumModel> ret = new ArrayList<>();
        ChatDao chtDao = new ChatDao(con);
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   DATA.CGI_SID as CGI_SID,");
            sql.addSql("   DATA.CNT + CHT_GROUP_DATA_SUM.CGS_CNT as CGS_CNT,");
            sql.addSql("   DATA.LASTSID as CGS_LASTSID,");
            sql.addSql("   DATA.LASTDATE as CGS_LASTDATE");
            sql.addSql(" from ");
            sql.addSql("   (");
            chtDao.writeQueryGroupDataSumMisyukei(sql);
            sql.addSql("   ) DATA");
            sql.addSql("     inner join");
            sql.addSql("     CHT_GROUP_DATA_SUM");
            sql.addSql("       on DATA.CGI_SID = CHT_GROUP_DATA_SUM.CGI_SID");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ChtGroupDataSumModel mdl = __getChtGroupCorrectFromRs(rs);
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
    *
    * <br>[機  能] チャットユーザ投稿集計情報を最新に更新する
    * <br>[解  説]
    * <br>[備  考] 未集計のチャットユーザ投稿を利用した差分で更新
    * @return 更新件数
    * @throws SQLException SQL実行例外
    */
    public int updateNow() throws SQLException {
        List<ChtGroupDataSumModel> sumList = __selectUpdateSum();
        int cnt = 0;
        update(sumList);
        return cnt;
    }

}