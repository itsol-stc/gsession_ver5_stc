package jp.groupsession.v2.cir.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.model.CirLogCountModel;
import jp.groupsession.v2.cir.model.CirLogCountSumModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>CIR_LOG_COUNT_SUM Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CirLogCountSumDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CirLogCountSumDao.class);

    /**
     * <p>Default Constructor
     */
    public CirLogCountSumDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CirLogCountSumDao(Connection con) {
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
            sql.addSql("drop table CIR_LOG_COUNT_SUM");

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
            sql.addSql(" create table CIR_LOG_COUNT_SUM (");
            sql.addSql("   CLS_KBN NUMBER(10,0) not null,");
            sql.addSql("   CLS_CNT Date not null,");
            sql.addSql("   CLS_CNT_SUM Date not null,");
            sql.addSql("   CLS_MONTH NUMBER(10,0) not null,");
            sql.addSql("   CLS_EDATE varchar(23) not null");
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
     * <p>Insert CIR_LOG_COUNT_SUM Data Bindding JavaBean
     * @param bean CIR_LOG_COUNT_SUM Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CirLogCountSumModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CIR_LOG_COUNT_SUM(");
            sql.addSql("   CLS_KBN,");
            sql.addSql("   CLS_CNT,");
            sql.addSql("   CLS_CNT_SUM,");
            sql.addSql("   CLS_DATE,");
            sql.addSql("   CLS_MONTH,");
            sql.addSql("   CLS_EDATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getClsKbn());
            sql.addLongValue(bean.getClsCnt());
            sql.addLongValue(bean.getClsCntSum());
            sql.addDateValue(bean.getClsDate());
            sql.addIntValue(bean.getClsMonth());
            sql.addDateValue(bean.getClsEdate());
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
     * <p>Update CIR_LOG_COUNT_SUM Data Bindding JavaBean
     * @param bean CIR_LOG_COUNT_SUM Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CirLogCountSumModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CIR_LOG_COUNT_SUM");
            sql.addSql(" set ");
            sql.addSql("   CLS_CNT=?,");
            sql.addSql("   CLS_CNT_SUM=?,");
            sql.addSql("   CLS_MONTH=?,");
            sql.addSql("   CLS_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   CLS_KBN=?");
            sql.addSql(" and ");
            sql.addSql("   CLS_DATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(bean.getClsCnt());
            sql.addLongValue(bean.getClsCntSum());
            sql.addIntValue(bean.getClsMonth());
            sql.addDateValue(bean.getClsEdate());
            sql.addIntValue(bean.getClsKbn());
            sql.addDateValue(bean.getClsDate());

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
     * <p>Select CIR_LOG_COUNT_SUM All Data
     * @return List in CIR_LOG_COUNT_SUMModel
     * @throws SQLException SQL実行例外
     */
    public List<CirLogCountSumModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CirLogCountSumModel> ret = new ArrayList<CirLogCountSumModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CLS_KBN,");
            sql.addSql("   CLS_CNT,");
            sql.addSql("   CLS_CNT_SUM,");
            sql.addSql("   CLS_DATE,");
            sql.addSql("   CLS_MONTH,");
            sql.addSql("   CLS_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CIR_LOG_COUNT_SUM");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCirLogCountSumFromRs(rs));
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
     * <br>[機  能] 集計ログの区分、日別集計結果を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param logMdl 集計ログ情報
     * @param date 指定日
     * @return 集計ログの日別集計結果
     * @throws SQLException SQL実行時例外
     */
    public CirLogCountSumModel getSumLogCount(CirLogCountModel logMdl, UDate date)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CirLogCountSumModel model = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CLC_KBN,");
            sql.addSql("   cast(CLC_DATE as date) as LOG_DATE,");
            sql.addSql("   count(*) as CNT,");
            sql.addSql("   sum(CLC_CNT) as CNT_SUM,");
            sql.addSql("   max(CLC_DATE) as MAX_EDATE");
            sql.addSql(" from");
            sql.addSql("   CIR_LOG_COUNT");
            sql.addSql(" where");
            sql.addSql("   CLC_KBN = ?");
            sql.addSql(" and");
            sql.addSql("   CLC_DATE >= ?");
            sql.addSql(" and");
            sql.addSql("   CLC_DATE <= ?");
            sql.addSql(" group by");
            sql.addSql("   CLC_KBN,");
            sql.addSql("   LOG_DATE");

            UDate frDate = date.cloneUDate();
            frDate.setZeroHhMmSs();
            UDate toDate = date.cloneUDate();
            toDate.setMaxHhMmSs();
            sql.addIntValue(logMdl.getClcKbn());
            sql.addDateValue(frDate);
            sql.addDateValue(toDate);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                model = __getCirLogSumData(rs);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return model;
    }

    /**
     * <br>[機  能] 集計ログの区分、日別集計結果を取得する
     * <br>[解  説] 集計結果(CIR_LOG_COUNT_SUM)に未登録のデータのみを対象とする。基準日含む過去3日間
     * <br>[備  考]
     * @param date 基準日
     * @return 集計ログの日別集計結果
     * @throws SQLException SQL実行時例外
     */
    public List<CirLogCountSumModel> getNonRegisteredList(UDate date)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<CirLogCountSumModel> logSumList = new ArrayList<CirLogCountSumModel>();
        con = getCon();
        UDate from = date.cloneUDate();
        from.addDay(-2);
        from.setZeroHhMmSs();
        UDate to = date.cloneUDate();
        to.setMaxHhMmSs();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   LOG_SUM.CLC_KBN as CLC_KBN,");
            sql.addSql("   LOG_SUM.LOG_DATE as LOG_DATE,");
            sql.addSql("   LOG_SUM.CNT as CNT,");
            sql.addSql("   LOG_SUM.CNT_SUM as CNT_SUM,");
            sql.addSql("   LOG_SUM.MAX_EDATE as MAX_EDATE");
            sql.addSql(" from");
            sql.addSql("   (");
            sql.addSql("    select");
            sql.addSql("      CLC_KBN,");
            sql.addSql("      cast(CLC_DATE as date) as LOG_DATE,");
            sql.addSql("      count(*) as CNT,");
            sql.addSql("      sum(CLC_CNT) as CNT_SUM,");
            sql.addSql("      max(CLC_DATE) as MAX_EDATE");
            sql.addSql("    from");
            sql.addSql("      CIR_LOG_COUNT");
            sql.addSql("    where ");
            sql.addSql("      CLC_DATE >= ?");
            sql.addDateValue(from);
            sql.addSql("    and");
            sql.addSql("      CLC_DATE <= ?");
            sql.addDateValue(to);
            sql.addSql("    group by");
            sql.addSql("      CLC_KBN,");
            sql.addSql("      LOG_DATE");
            sql.addSql("   ) LOG_SUM");
            sql.addSql(" where");
            sql.addSql("   not exists (");
            sql.addSql("     select 1 from CIR_LOG_COUNT_SUM");
            sql.addSql("     where");
            sql.addSql("       LOG_SUM.CLC_KBN = CIR_LOG_COUNT_SUM.CLS_KBN");
            sql.addSql("     and");
            sql.addSql("       LOG_SUM.LOG_DATE = CIR_LOG_COUNT_SUM.CLS_DATE");
            sql.addSql("     and");
            sql.addSql("       LOG_SUM.MAX_EDATE = CIR_LOG_COUNT_SUM.CLS_EDATE");
            sql.addSql("   )");
            sql.addSql(" order by");
            sql.addSql("   LOG_SUM.LOG_DATE,");
            sql.addSql("   LOG_SUM.CLC_KBN");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                logSumList.add(__getCirLogSumData(rs));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return logSumList;
    }

    /**
     * <p>Create CIR_LOG_COUNT_SUM Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CirLogCountSumModel
     * @throws SQLException SQL実行例外
     */
    private CirLogCountSumModel __getCirLogCountSumFromRs(ResultSet rs) throws SQLException {
        CirLogCountSumModel bean = new CirLogCountSumModel();
        bean.setClsKbn(rs.getInt("CLS_KBN"));
        bean.setClsCnt(rs.getLong("CLS_CNT"));
        bean.setClsCntSum(rs.getLong("CLS_CNT_SUM"));
        bean.setClsDate(UDate.getInstanceTimestamp(rs.getTimestamp("CLS_DATE")));
        bean.setClsMonth(rs.getInt("CLS_MONTH"));
        bean.setClsEdate(UDate.getInstanceTimestamp(rs.getTimestamp("CLS_EDATE")));
        return bean;
    }

    /**
     * <br>[機  能] 回覧板ログ集計の集計結果を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rs ResultSet
     * @return メールログ集計の集計結果
     * @throws SQLException SQL実行例外
     */
    private CirLogCountSumModel __getCirLogSumData(ResultSet rs) throws SQLException {
        CirLogCountSumModel model = new CirLogCountSumModel();
        model.setClsKbn(rs.getInt("CLC_KBN"));

        UDate clsDate = UDate.getInstanceTimestamp(rs.getTimestamp("LOG_DATE"));
        model.setClsDate(clsDate);
        model.setClsMonth(Integer.parseInt(clsDate.getDateString().substring(0, 6)));
        model.setClsCnt(rs.getLong("CNT"));
        if (model.getClsKbn() == GSConstCircular.LOG_COUNT_KBN_JCIR) {
            model.setClsCntSum(1);
        } else {
            model.setClsCntSum(rs.getLong("CNT_SUM"));
        }
        model.setClsEdate(UDate.getInstanceTimestamp(rs.getTimestamp("MAX_EDATE")));

        return model;
    }
}
