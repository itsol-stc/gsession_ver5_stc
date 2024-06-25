package jp.groupsession.v2.tcd.dao;

import java.math.BigDecimal;
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
import jp.groupsession.v2.tcd.model.TcdYukyuAlertModel;

/**
 * <p>TCD_YUKYU_ALERT Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class TcdYukyuAlertDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(TcdYukyuAlertDao.class);

    /**
     * <p>Default Constructor
     */
    public TcdYukyuAlertDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public TcdYukyuAlertDao(Connection con) {
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
            sql.addSql("drop table TCD_YUKYU_ALERT");

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
            sql.addSql(" create table TCD_YUKYU_ALERT (");
            sql.addSql("   TYA_NO integer not null,");
            sql.addSql("   TYA_MONTH integer not null,");
            sql.addSql("   TYA_BASEDAYS integer not null,");
            sql.addSql("   TYA_MESSAGE varchar(50) not null");
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
     * <p>Insert TCD_YUKYU_ALERT Data Bindding JavaBean
     * @param bean TCD_YUKYU_ALERT Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(TcdYukyuAlertModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" TCD_YUKYU_ALERT(");
            sql.addSql("   TYA_NO,");
            sql.addSql("   TYA_MONTH,");
            sql.addSql("   TYA_BASEDAYS,");
            sql.addSql("   TYA_MESSAGE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getTyaNo());
            sql.addIntValue(bean.getTyaMonth());
            sql.addIntValue(bean.getTyaBasedays());
            sql.addStrValue(bean.getTyaMessage());
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
     * <p>Update TCD_YUKYU_ALERT Data Bindding JavaBean
     * @param bean TCD_YUKYU_ALERT Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(TcdYukyuAlertModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   TCD_YUKYU_ALERT");
            sql.addSql(" set ");
            sql.addSql("   TYA_NO=?,");
            sql.addSql("   TYA_MONTH=?,");
            sql.addSql("   TYA_BASEDAYS=?,");
            sql.addSql("   TYA_MESSAGE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getTyaNo());
            sql.addIntValue(bean.getTyaMonth());
            sql.addIntValue(bean.getTyaBasedays());
            sql.addStrValue(bean.getTyaMessage());

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
     * <p>Select TCD_YUKYU_ALERT All Data
     * @return List in TCD_YUKYU_ALERTModel
     * @throws SQLException SQL実行例外
     */
    public List<TcdYukyuAlertModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<TcdYukyuAlertModel> ret = new ArrayList<TcdYukyuAlertModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   TYA_NO,");
            sql.addSql("   TYA_MONTH,");
            sql.addSql("   TYA_BASEDAYS,");
            sql.addSql("   TYA_MESSAGE");
            sql.addSql(" from ");
            sql.addSql("   TCD_YUKYU_ALERT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getTcdYukyuAlertFromRs(rs));
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
     * <br>[機  能] 有休警告メッセージを取得する
     * <br>[解  説]
     * <br>[備  考] 最大有休日数 > 警告基準日数の場合のみ警告メッセージを出します。
     * @param month 表示月
     * @param yukyuUseCnt 有休使用数
     * @param yukyuUseCnt 有休使用数
     * @param beginMonth 期首月
     * @return 有休警告メッセージ
     * @throws SQLException SQL実行例外
     */
    public String getAlertMessage(int month,
            BigDecimal yukyuUseCnt, BigDecimal yukyuMaxCnt, int beginMonth) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        String ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   TYA_MESSAGE");
            sql.addSql(" from");
            sql.addSql("   (");
            sql.addSql("     select");
            sql.addSql("       case");
            sql.addSql("         when TYA_MONTH < ? then TYA_MONTH + 200");
            sql.addSql("         else TYA_MONTH + 100");
            sql.addSql("       end as MONTH_PLUS,");
            sql.addSql("       TYA_BASEDAYS,");
            sql.addSql("       TYA_MESSAGE");
            sql.addSql("     from");
            sql.addSql("      TCD_YUKYU_ALERT");
            sql.addSql("   ) ALERT_DATA");
            sql.addSql(" where");
            sql.addSql("   MONTH_PLUS <= ?");
            sql.addSql(" and");
            sql.addSql("   TYA_BASEDAYS > ?");
            sql.addSql(" and");
            sql.addSql("   TYA_BASEDAYS < ?");
            sql.addSql(" order by");
            sql.addSql("   MONTH_PLUS desc,");
            sql.addSql("   TYA_BASEDAYS");
            sql.addSql(" limit 1");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(beginMonth);
            if (month < beginMonth) {
                sql.addIntValue(month + 200);
            } else {
                sql.addIntValue(month + 100);
            }
            sql.addDecimalValue(yukyuUseCnt);
            sql.addDecimalValue(yukyuMaxCnt);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getString("TYA_MESSAGE");
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
     * <br>[機  能] 有休警告情報の一覧を取得
     * <br>[解  説]
     * <br>[備  考]
     * @return 有休警告情報一覧
     * @throws SQLException SQL実行例外
     */
    public List<TcdYukyuAlertModel> getYukyuAlertList() throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<TcdYukyuAlertModel> ret = new ArrayList<TcdYukyuAlertModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   TYA_NO,");
            sql.addSql("   TYA_MONTH,");
            sql.addSql("   TYA_BASEDAYS,");
            sql.addSql("   TYA_MESSAGE");
            sql.addSql(" from");
            sql.addSql("   TCD_YUKYU_ALERT");
            sql.addSql(" order by");
            sql.addSql("   TYA_NO");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                TcdYukyuAlertModel tyaMdl = __getTcdYukyuAlertFromRs(rs);
                ret.add(tyaMdl);
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
     * <br>[機  能] 有休警告情報を全て削除する
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行例外
     */
    public void delete() throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   TCD_YUKYU_ALERT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
    }


    /**
     * <p>Create TCD_YUKYU_ALERT Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created TcdYukyuAlertModel
     * @throws SQLException SQL実行例外
     */
    private TcdYukyuAlertModel __getTcdYukyuAlertFromRs(ResultSet rs) throws SQLException {
        TcdYukyuAlertModel bean = new TcdYukyuAlertModel();
        bean.setTyaNo(rs.getInt("TYA_NO"));
        bean.setTyaMonth(rs.getInt("TYA_MONTH"));
        bean.setTyaBasedays(rs.getInt("TYA_BASEDAYS"));
        bean.setTyaMessage(rs.getString("TYA_MESSAGE"));
        return bean;
    }
}
