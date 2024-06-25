package jp.groupsession.v2.cir.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cir.model.CirInitModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 初期値に関する情報を取得するためのDAOクラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CirInitDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CirInitDao.class);

    /**
     * <p>Default Constructor
     */
    public CirInitDao() {
    }


    /**
     * <p>Default Constructor
     * @param con 子コネクション
     */
    public CirInitDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能]管理者の設定による初期値取得する。。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @throws SQLException SQL実行例外
     * @return ret CirInitModel
     * @author JTS
     */
    public CirInitModel select()
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        CirInitModel ret = null;

        try {
            SqlBuffer sql = new SqlBuffer();

            sql.addSql("select");
            sql.addSql("   CIN_INITSET_KEN,");
            sql.addSql("   CIN_MEMO_KBN,");
            sql.addSql("   CIN_MEMO_DAY,");
            sql.addSql("   CIN_KOU_KBN,");
            sql.addSql("   CIN_ACNT_MAKE,");
            sql.addSql("   CIN_AUTO_DEL_KBN,");
            sql.addSql("   CIN_ACNT_USER,");
            sql.addSql("   CIN_AUID,");
            sql.addSql("   CIN_ADATE,");
            sql.addSql("   CIN_EUID,");
            sql.addSql("   CIN_EDATE ");
            sql.addSql(" from");
            sql.addSql("   CIR_INIT");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret = __getCirInitFromRs(rs);
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
     * <br>[機  能]データ件数を取得。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @throws SQLException SQL実行例外
     * @return ret CirInitModel
     * @author JTS
     */
    public int getCount()
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        int count = 0;

        try {
            SqlBuffer sql = new SqlBuffer();

            sql.addSql("select");
            sql.addSql("   count(*)");
            sql.addSql(" from");
            sql.addSql("   CIR_INIT");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                count = rs.getInt(1);
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return count;

    }


    /**
     * 初期値 設定値を新規登録する。
     * @param bean CIR_USER Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insertAdmInitSet(CirInitModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert");
            sql.addSql(" into ");
            sql.addSql(" CIR_INIT(");
            sql.addSql("   CIN_INITSET_KEN,");
            sql.addSql("   CIN_MEMO_KBN,");
            sql.addSql("   CIN_MEMO_DAY,");
            sql.addSql("   CIN_KOU_KBN,");
            sql.addSql("   CIN_AUID,");
            sql.addSql("   CIN_ADATE,");
            sql.addSql("   CIN_EUID,");
            sql.addSql("   CIN_EDATE,");
            sql.addSql("   CIN_ACNT_MAKE,");
            sql.addSql("   CIN_AUTO_DEL_KBN,");
            sql.addSql("   CIN_ACNT_USER");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCinInitSetKen());
            sql.addIntValue(bean.getCinMemoKbn());
            sql.addIntValue(bean.getCinMemoDay());
            sql.addIntValue(bean.getCinKouKbn());
            sql.addIntValue(bean.getCinAuid());
            sql.addDateValue(bean.getCinAdate());
            sql.addIntValue(bean.getCinEuid());
            sql.addDateValue(bean.getCinEdate());
            sql.addIntValue(bean.getCinAcntMake());
            sql.addIntValue(bean.getCinAutoDelKbn());
            sql.addIntValue(bean.getCinAcntUser());

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
     * 初期値 設定値を更新登録する。
     * @param bean CIR_USER Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void updateAdmInitSet(CirInitModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CIR_INIT");
            sql.addSql(" set ");
            sql.addSql("   CIN_INITSET_KEN = ?,");
            sql.addSql("   CIN_MEMO_KBN = ?,");
            sql.addSql("   CIN_MEMO_DAY = ?,");
            sql.addSql("   CIN_KOU_KBN = ?,");
            sql.addSql("   CIN_EUID = ?,");
            sql.addSql("   CIN_EDATE = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCinInitSetKen());
            sql.addIntValue(bean.getCinMemoKbn());
            sql.addIntValue(bean.getCinMemoDay());
            sql.addIntValue(bean.getCinKouKbn());
            sql.addIntValue(bean.getCinEuid());
            sql.addDateValue(bean.getCinEdate());

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
     * <br>[機  能] レコード件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return 件数
     * @throws SQLException SQL実行例外
     */
    public int selectCount() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int count = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from ");
            sql.addSql("   CIR_INIT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("CNT");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <p>Update CIR_INIT Data Bindding JavaBean
     * @param bean CIR_INIT Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateActSetting(CirInitModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CIR_INIT");
            sql.addSql(" set ");
            sql.addSql("   CIN_ACNT_MAKE=?,");
            sql.addSql("   CIN_AUTO_DEL_KBN=?,");
            sql.addSql("   CIN_ACNT_USER=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCinAcntMake());
            sql.addIntValue(bean.getCinAutoDelKbn());
            sql.addIntValue(bean.getCinAcntUser());

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
     * <p>Create CIR_Init Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CirInitModel
     * @throws SQLException SQL実行例外
     */
    private CirInitModel __getCirInitFromRs(ResultSet rs) throws SQLException {
        CirInitModel bean = new CirInitModel();

        bean.setCinInitSetKen(rs.getInt("CIN_INITSET_KEN"));
        bean.setCinMemoKbn(rs.getInt("CIN_MEMO_KBN"));
        bean.setCinMemoDay(rs.getInt("CIN_MEMO_DAY"));
        bean.setCinKouKbn(rs.getInt("CIN_KOU_KBN"));
        bean.setCinAcntMake(rs.getInt("CIN_ACNT_MAKE"));
        bean.setCinAutoDelKbn(rs.getInt("CIN_AUTO_DEL_KBN"));
        bean.setCinAcntUser(rs.getInt("CIN_ACNT_USER"));
        bean.setCinAuid(rs.getInt("CIN_AUID"));
        bean.setCinAdate(UDate.getInstanceTimestamp(rs.getTimestamp("CIN_ADATE")));
        bean.setCinEuid(rs.getInt("CIN_EUID"));
        bean.setCinEdate(UDate.getInstanceTimestamp(rs.getTimestamp("CIN_EDATE")));
        return bean;
    }



}
