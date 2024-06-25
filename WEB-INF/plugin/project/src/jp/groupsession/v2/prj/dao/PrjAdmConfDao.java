package jp.groupsession.v2.prj.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.prj.model.PrjAdmConfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>PRJ_ADM_CONF Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class PrjAdmConfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(PrjAdmConfDao.class);

    /**
     * <p>Default Constructor
     */
    public PrjAdmConfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public PrjAdmConfDao(Connection con) {
        super(con);
    }

    /**
     * <p>Insert PRJ_ADM_CONF Data Bindding JavaBean
     * @param bean PRJ_ADM_CONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(PrjAdmConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" PRJ_ADM_CONF(");
            sql.addSql("   PAC_PRJ_EDIT,");
            sql.addSql("   PAC_AUID,");
            sql.addSql("   PAC_ADATE,");
            sql.addSql("   PAC_EUID,");
            sql.addSql("   PAC_EDATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getPacPrjEdit());
            sql.addIntValue(bean.getPacAuid());
            sql.addDateValue(bean.getPacAdate());
            sql.addIntValue(bean.getPacEuid());
            sql.addDateValue(bean.getPacEdate());
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
     * <br>[機  能] 管理者設定情報の更新を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param bean PRJ_ADM_CONF Data Bindding JavaBean
     * @return int 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(PrjAdmConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   PRJ_ADM_CONF");
            sql.addSql(" set ");
            sql.addSql("   PAC_PRJ_EDIT=?,");
            sql.addSql("   PAC_EUID=?,");
            sql.addSql("   PAC_EDATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getPacPrjEdit());
            sql.addIntValue(bean.getPacEuid());
            sql.addDateValue(bean.getPacEdate());

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
     * <br>[機  能] プロジェクト管理者設定を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return PRJ_ADM_CONFModel
     * @throws SQLException SQL実行例外
     */
    public PrjAdmConfModel select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        PrjAdmConfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PAC_PRJ_EDIT,");
            sql.addSql("   PAC_AUID,");
            sql.addSql("   PAC_ADATE,");
            sql.addSql("   PAC_EUID,");
            sql.addSql("   PAC_EDATE");
            sql.addSql(" from");
            sql.addSql("   PRJ_ADM_CONF");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = __getPrjAdmConfFromRs(rs);
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
     * <p>Delete PRJ_ADM_CONF
     * @param bean PRJ_ADM_CONF Model
     * @return int 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(PrjAdmConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   PRJ_ADM_CONF");

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
     * <p>Create PRJ_ADM_CONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created PrjAdmConfModel
     * @throws SQLException SQL実行例外
     */
    private PrjAdmConfModel __getPrjAdmConfFromRs(ResultSet rs) throws SQLException {
        PrjAdmConfModel bean = new PrjAdmConfModel();
        bean.setPacPrjEdit(rs.getInt("PAC_PRJ_EDIT"));
        bean.setPacAuid(rs.getInt("PAC_AUID"));
        bean.setPacAdate(UDate.getInstanceTimestamp(rs.getTimestamp("PAC_ADATE")));
        bean.setPacEuid(rs.getInt("PAC_EUID"));
        bean.setPacEdate(UDate.getInstanceTimestamp(rs.getTimestamp("PAC_EDATE")));
        return bean;
    }
}
