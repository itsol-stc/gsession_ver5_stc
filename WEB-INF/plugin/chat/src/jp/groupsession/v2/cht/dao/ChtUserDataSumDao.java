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
import jp.groupsession.v2.cht.model.ChtUserDataSumModel;

/**
 * <p>CHT_USER_DATA_SUM Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class ChtUserDataSumDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ChtUserDataSumDao.class);

    /**
     * <p>Default Constructor
     */
    public ChtUserDataSumDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public ChtUserDataSumDao(Connection con) {
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
            sql.addSql("drop table CHT_USER_DATA_SUM");

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
            sql.addSql(" create table CHT_USER_DATA_SUM (");
            sql.addSql("   CUP_SID integer not null,");
            sql.addSql("   CUS_CNT integer not null,");
            sql.addSql("   CUS_LASTSID bigint not null,");
            sql.addSql("   CUS_LASTDATE timestamp,");
            sql.addSql("   primary key (CUP_SID)");
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
     * <p>Insert CHT_USER_DATA_SUM Data Bindding JavaBean
     * @param bean CHT_USER_DATA_SUM Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(ChtUserDataSumModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CHT_USER_DATA_SUM(");
            sql.addSql("   CUP_SID,");
            sql.addSql("   CUS_CNT,");
            sql.addSql("   CUS_LASTSID,");
            sql.addSql("   CUS_LASTDATE");
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
            sql.addIntValue(bean.getCusCnt());
            sql.addLongValue(bean.getCusLastsid());
            sql.addDateValue(bean.getCusLastdate());
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
     * <p>Update CHT_USER_DATA_SUM Data Bindding JavaBean
     * @param bean CHT_USER_DATA_SUM Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(ChtUserDataSumModel bean) throws SQLException {
        return update(
                Arrays.asList(bean)
                );

    }
    /**
     * <p>Update CHT_USER_DATA_SUM Data Bindding JavaBean
     * @param beanList CHT_USER_DATA_SUM Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(List<ChtUserDataSumModel> beanList) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();
        if (beanList == null || beanList.size() == 0) {
            return count;
        }
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CHT_USER_DATA_SUM");
            sql.addSql(" set ");
            sql.addSql("   CUS_CNT=?,");
            sql.addSql("   CUS_LASTSID=?,");
            sql.addSql("   CUS_LASTDATE=?");
            sql.addSql(" where ");
            sql.addSql("   CUP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());

            for (ChtUserDataSumModel bean : beanList) {

                sql.addIntValue(bean.getCusCnt());
                sql.addLongValue(bean.getCusLastsid());
                sql.addDateValue(bean.getCusLastdate());
                //where
                sql.addIntValue(bean.getCupSid());

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
     * <p>Select CHT_USER_DATA_SUM All Data
     * @return List in CHT_USER_DATA_SUMModel
     * @throws SQLException SQL実行例外
     */
    public List<ChtUserDataSumModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ChtUserDataSumModel> ret = new ArrayList<>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CUP_SID,");
            sql.addSql("   CUS_CNT,");
            sql.addSql("   CUS_LASTSID,");
            sql.addSql("   CUS_LASTDATE");
            sql.addSql(" from ");
            sql.addSql("   CHT_USER_DATA_SUM");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getChtUserCorrectFromRs(rs));
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
     * <p>Select CHT_USER_DATA_SUM
     * @param cupSid CUP_SID
     * @return CHT_USER_DATA_SUMModel
     * @throws SQLException SQL実行例外
     */
    public ChtUserDataSumModel select(int cupSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ChtUserDataSumModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CUP_SID,");
            sql.addSql("   CUS_CNT,");
            sql.addSql("   CUS_LASTSID,");
            sql.addSql("   CUS_LASTDATE");
            sql.addSql(" from");
            sql.addSql("   CHT_USER_DATA_SUM");
            sql.addSql(" where ");
            sql.addSql("   CUP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(cupSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getChtUserCorrectFromRs(rs);
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
     * <p>Delete CHT_USER_DATA_SUM
     * @param cupSid CUP_SID
     * @throws SQLException SQL実行例外
     * @return 削除数
     */
    public int delete(int cupSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CHT_USER_DATA_SUM");
            sql.addSql(" where ");
            sql.addSql("   CUP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(cupSid);

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
     * <p>Delete CHT_USER_DATA_SUM
     * @param cupSidList CUP_SID
     * @throws SQLException SQL実行例外
     * @return 削除数
     */
    public int delete(List<Integer> cupSidList) throws SQLException {
        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();
        if (cupSidList == null || cupSidList.size() == 0) {
            return count;
        }
        Iterator<Integer> it = cupSidList.iterator();
        int cursor = 0;
        while (it.hasNext()) {

            try {
                //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" delete");
                sql.addSql(" from");
                sql.addSql("   CHT_USER_DATA_SUM");
                sql.addSql(" where ");
                sql.addSql("   CUP_SID in (");
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
     * <p>Create CHT_USER_DATA_SUM Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created ChtUserDataSumModel
     * @throws SQLException SQL実行例外
     */
    private ChtUserDataSumModel __getChtUserCorrectFromRs(ResultSet rs) throws SQLException {
        ChtUserDataSumModel bean = new ChtUserDataSumModel();
        bean.setCupSid(rs.getInt("CUP_SID"));
        bean.setCusCnt(rs.getInt("CUS_CNT"));
        bean.setCusLastsid(rs.getInt("CUS_LASTSID"));
        bean.setCusLastdate(UDate.getInstanceTimestamp(rs.getTimestamp("CUS_LASTDATE")));
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
    public void subtractCnt(int cupSid, int size) throws SQLException {
        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CHT_USER_DATA_SUM");
            sql.addSql(" set ");
            sql.addSql("   CUS_CNT = case");
            sql.addSql("     when CUS_CNT < ? then 0");
            sql.addSql("     else CUS_CNT - ? end");
            sql.addIntValue(size);
            sql.addIntValue(size);
            sql.addSql(" , ");
            sql.addSql("   CUS_LASTDATE = case");
            sql.addSql("     when CUS_CNT < ? then null");
            sql.addSql("     else CUS_LASTDATE end");
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
    /**
     *
     * <br>[機  能] 未集計のCHT_USER_DATA部からCHT_USER_DATA_SUMモデルを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @return 更新情報
     * @throws SQLException SQL実行例外
     */
    private List<ChtUserDataSumModel> __selectUpdateSum() throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        ArrayList<ChtUserDataSumModel> ret = new ArrayList<>();
        ChatDao chtDao = new ChatDao(con);
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   DATA.CUP_SID as CUP_SID,");
            sql.addSql("   DATA.CNT + CHT_USER_DATA_SUM.CUS_CNT as CUS_CNT,");
            sql.addSql("   DATA.LASTSID as CUS_LASTSID,");
            sql.addSql("   DATA.LASTDATE as CUS_LASTDATE");
            sql.addSql(" from ");
            sql.addSql("   (");
            chtDao.writeQueryUserDataSumMisyukei(sql);
            sql.addSql("   ) DATA");
            sql.addSql("     inner join");
            sql.addSql("     CHT_USER_DATA_SUM");
            sql.addSql("       on DATA.CUP_SID = CHT_USER_DATA_SUM.CUP_SID");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ChtUserDataSumModel mdl = __getChtUserCorrectFromRs(rs);
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
        List<ChtUserDataSumModel> sumList = __selectUpdateSum();
        int cnt = 0;
        update(sumList);
        return cnt;
    }
    /**
     *
     * <br>[機  能] 全データ件数を返す
     * <br>[解  説]
     * <br>[備  考]
     * @return 全データ件数
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
            sql.addSql("   CHT_USER_DATA_SUM");

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
     * <p>Select CHT_USER_DATA_SUM All Data
     * @param offset レコードの読取開始行
     * @param limit 1ページの最大件数
     * @return List in CHT_USER_DATA_SUMModel
     * @throws SQLException SQL実行例外
     */
    public List<ChtUserDataSumModel> selectLimit(int offset, int limit) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ChtUserDataSumModel> ret = new ArrayList<>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CUP_SID,");
            sql.addSql("   CUS_CNT,");
            sql.addSql("   CUS_LASTSID,");
            sql.addSql("   CUS_LASTDATE");
            sql.addSql(" from ");
            sql.addSql("   CHT_USER_DATA_SUM");
            sql.addSql(" order by ");
            sql.addSql("   CUS_LASTSID asc");

            sql.setPagingValue(offset, limit);
            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getChtUserCorrectFromRs(rs));
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
     * <p>Insert CHT_USER_DATA_SUM Data Bindding JavaBean
     * @param beanList CHT_USER_DATA_SUM Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insertData(List<ChtUserDataSumModel> beanList) throws SQLException {
        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();
        if (beanList == null || beanList.size() == 0) {
            return;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CHT_USER_DATA_SUM(");
            sql.addSql("   CUP_SID,");
            sql.addSql("   CUS_CNT,");
            sql.addSql("   CUS_LASTSID,");
            sql.addSql("   CUS_LASTDATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());

            for (ChtUserDataSumModel bean : beanList) {

                sql.addIntValue(bean.getCupSid());
                sql.addIntValue(bean.getCusCnt());
                sql.addLongValue(bean.getCusLastsid());
                sql.addDateValue(bean.getCusLastdate());
                log__.info(sql.toLogString());
                sql.setParameter(pstmt);
                pstmt.executeUpdate();

                sql.clearValue();
                pstmt.clearParameters();
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }

    }

}
