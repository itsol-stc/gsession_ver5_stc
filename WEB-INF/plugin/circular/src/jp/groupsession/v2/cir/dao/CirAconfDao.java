package jp.groupsession.v2.cir.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cir.model.CirAconfModel;

/**
 * <p>CIR_ACONF Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CirAconfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CirAconfDao.class);

    /**
     * <p>Default Constructor
     */
    public CirAconfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CirAconfDao(Connection con) {
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
            sql.addSql("drop table CIR_ACONF");

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
            sql.addSql(" create table CIR_ACONF (");
            sql.addSql("   CAF_SMAIL_SEND_KBN NUMBER(10,0) not null,");
            sql.addSql("   CAF_SMAIL_SEND NUMBER(10,0) not null,");
            sql.addSql("   CAF_AUID NUMBER(10,0) not null,");
            sql.addSql("   CAF_ADATE varchar(23) not null,");
            sql.addSql("   CAF_EUID NUMBER(10,0) not null,");
            sql.addSql("   CAF_EDATE varchar(23) not null");
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
     * <p>Insert CIR_ACONF Data Bindding JavaBean
     * @param bean CIR_ACONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CirAconfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CIR_ACONF(");
            sql.addSql("   CAF_SMAIL_SEND_KBN,");
            sql.addSql("   CAF_SMAIL_SEND,");
            sql.addSql("   CAF_AUID,");
            sql.addSql("   CAF_ADATE,");
            sql.addSql("   CAF_EUID,");
            sql.addSql("   CAF_EDATE,");
            sql.addSql("   CAF_AREST_KBN,");
            sql.addSql("   CAF_SMAIL_SEND_MEMO,");
            sql.addSql("   CAF_SMAIL_SEND_EDIT");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCafSmailSendKbn());
            sql.addIntValue(bean.getCafSmailSend());
            sql.addIntValue(bean.getCafAuid());
            sql.addDateValue(bean.getCafAdate());
            sql.addIntValue(bean.getCafEuid());
            sql.addDateValue(bean.getCafEdate());
            sql.addIntValue(bean.getCafArestKbn());
            sql.addIntValue(bean.getCafSmailSendMemo());
            sql.addIntValue(bean.getCafSmailSendEdit());
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
     * <p>Update CIR_ACONF Data Bindding JavaBean
     * @param bean CIR_ACONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CirAconfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CIR_ACONF");
            sql.addSql(" set ");
            sql.addSql("   CAF_SMAIL_SEND_KBN=?,");
            sql.addSql("   CAF_SMAIL_SEND=?,");
            sql.addSql("   CAF_EUID=?,");
            sql.addSql("   CAF_EDATE=?,");
            sql.addSql("   CAF_SMAIL_SEND_MEMO=?,");
            sql.addSql("   CAF_SMAIL_SEND_EDIT=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCafSmailSendKbn());
            sql.addIntValue(bean.getCafSmailSend());
            sql.addIntValue(bean.getCafEuid());
            sql.addDateValue(bean.getCafEdate());
            sql.addIntValue(bean.getCafSmailSendMemo());
            sql.addIntValue(bean.getCafSmailSendEdit());

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
     * <p>Select CIR_ACONF All Data
     * @return List in CIR_ACONFModel
     * @throws SQLException SQL実行例外
     */
    public CirAconfModel select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CirAconfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CAF_SMAIL_SEND_KBN,");
            sql.addSql("   CAF_SMAIL_SEND,");
            sql.addSql("   CAF_AUID,");
            sql.addSql("   CAF_ADATE,");
            sql.addSql("   CAF_EUID,");
            sql.addSql("   CAF_EDATE,");
            sql.addSql("   CAF_AREST_KBN,");
            sql.addSql("   CAF_SMAIL_SEND_MEMO,");
            sql.addSql("   CAF_SMAIL_SEND_EDIT");
            sql.addSql(" from ");
            sql.addSql("   CIR_ACONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCirAconfFromRs(rs);
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
     * <br>[機  能] アカウント使用者設定制限の更新を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param bean 回覧板 管理者設定
     * @throws SQLException SQL実行時例外
     */
    public void updateArest(CirAconfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CIR_ACONF");
            sql.addSql(" set ");
            sql.addSql("   CAF_AREST_KBN=?,");
            sql.addSql("   CAF_EUID=?,");
            sql.addSql("   CAF_EDATE=?");
            sql.addIntValue(bean.getCafArestKbn());
            sql.addIntValue(bean.getCafEuid());
            sql.addDateValue(bean.getCafEdate());

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Create CIR_ACONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CirAconfModel
     * @throws SQLException SQL実行例外
     */
    private CirAconfModel __getCirAconfFromRs(ResultSet rs) throws SQLException {
        CirAconfModel bean = new CirAconfModel();
        bean.setCafSmailSendKbn(rs.getInt("CAF_SMAIL_SEND_KBN"));
        bean.setCafSmailSend(rs.getInt("CAF_SMAIL_SEND"));
        bean.setCafAuid(rs.getInt("CAF_AUID"));
        bean.setCafAdate(UDate.getInstanceTimestamp(rs.getTimestamp("CAF_ADATE")));
        bean.setCafEuid(rs.getInt("CAF_EUID"));
        bean.setCafEdate(UDate.getInstanceTimestamp(rs.getTimestamp("CAF_EDATE")));
        bean.setCafArestKbn(rs.getInt("CAF_AREST_KBN"));
        bean.setCafSmailSendMemo(rs.getInt("CAF_SMAIL_SEND_MEMO"));
        bean.setCafSmailSendEdit(rs.getInt("CAF_SMAIL_SEND_EDIT"));
        return bean;
    }
}
