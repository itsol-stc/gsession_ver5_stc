package jp.groupsession.v2.enq.dao;

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
import jp.groupsession.v2.enq.model.EnqTypeModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

/**
 * <p>ENQ_TYPE Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class EnqTypeDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(EnqTypeDao.class);

    /**
     * <p>Default Constructor
     */
    public EnqTypeDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public EnqTypeDao(Connection con) {
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
            sql.addSql("drop table ENQ_TYPE");

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
            sql.addSql(" create table ENQ_TYPE (");
            sql.addSql("   ETP_SID bigint not null,");
            sql.addSql("   ETP_DSP_SEQ integer not null,");
            sql.addSql("   ETP_NAME varchar(100) not null,");
            sql.addSql("   ETP_AUID integer not null,");
            sql.addSql("   ETP_ADATE timestamp not null,");
            sql.addSql("   ETP_EUID integer not null,");
            sql.addSql("   ETP_EDATE timestamp not null,");
            sql.addSql("   primary key (ETP_SID)");
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
     * <p>Insert ENQ_TYPE Data Binding JavaBean
     * @param bean ENQ_TYPE Data Binding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(EnqTypeModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" ENQ_TYPE(");
            sql.addSql("   ETP_SID,");
            sql.addSql("   ETP_DSP_SEQ,");
            sql.addSql("   ETP_NAME,");
            sql.addSql("   ETP_AUID,");
            sql.addSql("   ETP_ADATE,");
            sql.addSql("   ETP_EUID,");
            sql.addSql("   ETP_EDATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(bean.getEtpSid());
            sql.addIntValue(bean.getEtpDspSeq());
            sql.addStrValue(bean.getEtpName());
            sql.addIntValue(bean.getEtpAuid());
            sql.addDateValue(bean.getEtpAdate());
            sql.addIntValue(bean.getEtpEuid());
            sql.addDateValue(bean.getEtpEdate());
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
     * <p>Update ENQ_TYPE Data Binding JavaBean
     * @param bean ENQ_TYPE Data Binding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(EnqTypeModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ENQ_TYPE");
            sql.addSql(" set ");
            sql.addSql("   ETP_DSP_SEQ=?,");
            sql.addSql("   ETP_NAME=?,");
            sql.addSql("   ETP_EUID=?,");
            sql.addSql("   ETP_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   ETP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getEtpDspSeq());
            sql.addStrValue(bean.getEtpName());
            sql.addIntValue(bean.getEtpEuid());
            sql.addDateValue(bean.getEtpEdate());
            //where
            sql.addLongValue(bean.getEtpSid());

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
     * <br>[機  能] アンケートの種類名を明示的にロックする処理
     * <br>[解  説]
     * <br>[備  考]
     * @param etpSid アンケート種類SIDを
     * @throws SQLException SQL実行例外
     */
    public void selectForUpdate(long etpSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select 1");
            sql.addSql(" from ");
            sql.addSql("   ENQ_TYPE");
            sql.addSql(" where ");
            sql.addSql("   ETP_SID=?");
            sql.addSql(" for update");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(etpSid);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return;
    }

    /**
     * <br>[機  能] アンケート種類名テーブルを明示的にロック
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行例外
     */
    public void lockTable() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select 1 from ENQ_TYPE for update");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return;
    }

    /**
     * <p>Select ENQ_TYPE All Data
     * @return List in ENQ_TYPEModel
     * @throws SQLException SQL実行例外
     */
    public List<EnqTypeModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<EnqTypeModel> ret = new ArrayList<EnqTypeModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   ETP_SID,");
            sql.addSql("   ETP_DSP_SEQ,");
            sql.addSql("   ETP_NAME,");
            sql.addSql("   ETP_AUID,");
            sql.addSql("   ETP_ADATE,");
            sql.addSql("   ETP_EUID,");
            sql.addSql("   ETP_EDATE");
            sql.addSql(" from ");
            sql.addSql("   ENQ_TYPE");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getEnqTypeFromRs(rs));
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
     * <br>[機  能] アンケート種類一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return アンケート種類一覧
     * @throws SQLException SQL実行時例外
     */
    public List<LabelValueBean> getEnqTypeList() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<LabelValueBean> ret = new ArrayList<LabelValueBean>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   ETP_SID,");
            sql.addSql("   ETP_NAME");
            sql.addSql(" from ");
            sql.addSql("   ENQ_TYPE");
            sql.addSql(" order by ");
            sql.addSql("   ETP_DSP_SEQ");

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(new LabelValueBean(rs.getString("ETP_NAME"), rs.getString("ETP_SID")));
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
     * <p>Select ENQ_TYPE
     * @param etpSid ETP_SID
     * @return ENQ_TYPEModel
     * @throws SQLException SQL実行例外
     */
    public EnqTypeModel select(long etpSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        EnqTypeModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   ETP_SID,");
            sql.addSql("   ETP_DSP_SEQ,");
            sql.addSql("   ETP_NAME,");
            sql.addSql("   ETP_AUID,");
            sql.addSql("   ETP_ADATE,");
            sql.addSql("   ETP_EUID,");
            sql.addSql("   ETP_EDATE");
            sql.addSql(" from");
            sql.addSql("   ENQ_TYPE");
            sql.addSql(" where ");
            sql.addSql("   ETP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(etpSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getEnqTypeFromRs(rs);
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
     * <br>[機  能] 指定した種別名が存在するかを判定します
     * <br>[解  説]
     * <br>[備  考]
     * @param etpName 種別名
     * @return 判定結果 true:存在する false:存在しない
     * @throws SQLException SQL実行例外
     */
    public boolean existEnqTypeName(String etpName) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean result = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(ETP_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   ENQ_TYPE");
            sql.addSql(" where ");
            sql.addSql("   ETP_NAME=?");
            sql.addStrValue(etpName);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                result = rs.getInt("CNT") > 0;
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return result;
    }

    /**
     * <p>Delete ENQ_TYPE
     * @param etpSid ETP_SID
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(long etpSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql("   from ENQ_TYPE");
            sql.addSql("  where ETP_SID = ?");
//            sql.addSql("    and not exists(");
//            sql.addSql("     select EMN.EMN_SID as EMN_SID");
//            sql.addSql("       from ENQ_MAIN EMN");
//            sql.addSql("      where EMN.ETP_SID = ?");
//            sql.addSql("        and EMN.EMN_DATA_KBN in(0, 3)");
//            sql.addSql("  )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(etpSid);
//            sql.addLongValue(etpSid);

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
     * <p>Create ENQ_TYPE Data Binding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created EnqTypeModel
     * @throws SQLException SQL実行例外
     */
    private EnqTypeModel __getEnqTypeFromRs(ResultSet rs) throws SQLException {
        EnqTypeModel bean = new EnqTypeModel();
        bean.setEtpSid(rs.getInt("ETP_SID"));
        bean.setEtpDspSeq(rs.getInt("ETP_DSP_SEQ"));
        bean.setEtpName(rs.getString("ETP_NAME"));
        bean.setEtpAuid(rs.getInt("ETP_AUID"));
        bean.setEtpAdate(UDate.getInstanceTimestamp(rs.getTimestamp("ETP_ADATE")));
        bean.setEtpEuid(rs.getInt("ETP_EUID"));
        bean.setEtpEdate(UDate.getInstanceTimestamp(rs.getTimestamp("ETP_EDATE")));
        return bean;
    }
}
