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
import jp.groupsession.v2.enq.GSConstEnquete;
import jp.groupsession.v2.enq.model.EnqSubjectModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>ENQ_SUBJECT Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class EnqSubjectDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(EnqSubjectDao.class);

    /**
     * <p>Default Constructor
     */
    public EnqSubjectDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public EnqSubjectDao(Connection con) {
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
            sql.addSql("drop table ENQ_SUBJECT");

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
            sql.addSql(" create table ENQ_SUBJECT (");
            sql.addSql("   EMN_SID Date not null,");
            sql.addSql("   USR_SID NUMBER(10,0),");
            sql.addSql("   GRP_SID NUMBER(10,0)");
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
     * <p>Insert ENQ_SUBJECT Data Bindding JavaBean
     * @param bean ENQ_SUBJECT Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(EnqSubjectModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" ENQ_SUBJECT(");
            sql.addSql("   EMN_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(bean.getEmnSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getGrpSid());
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
     * <p>Insert ENQ_SUBJECT Data Bindding JavaBean
     * @param beanList ENQ_SUBJECT DataList Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(ArrayList<EnqSubjectModel> beanList) throws SQLException {

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
            sql.addSql(" ENQ_SUBJECT(");
            sql.addSql("   EMN_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            for (EnqSubjectModel bean : beanList) {
                sql.addLongValue(bean.getEmnSid());
                sql.addIntValue(bean.getUsrSid());
                sql.addIntValue(bean.getGrpSid());
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
     * <p>Update ENQ_SUBJECT Data Bindding JavaBean
     * @param bean ENQ_SUBJECT Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(EnqSubjectModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ENQ_SUBJECT");
            sql.addSql(" set ");
            sql.addSql("   EMN_SID=?,");
            sql.addSql("   USR_SID=?,");
            sql.addSql("   GRP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(bean.getEmnSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getGrpSid());

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
     * <br>[機  能] 指定したアンケートの対象者を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param emnSid EMN_SID
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(long emnSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete from");
            sql.addSql("   ENQ_SUBJECT");
            sql.addSql(" where ");
            sql.addSql("   EMN_SID=?");
            sql.addLongValue(emnSid);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] 発信フォルダ削除処理
     * <br>[解  説]
     * <br>[備  考]
     * @param date 年月
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int deleteSendEnq(UDate date) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete from");
            sql.addSql("   ENQ_SUBJECT");
            sql.addSql(" where exists (");
            sql.addSql("    select 1");
            sql.addSql("      from ENQ_MAIN");
            sql.addSql("      where ENQ_SUBJECT.EMN_SID = ENQ_MAIN.EMN_SID");
            sql.addSql("      and EMN_OPEN_END_KBN = ?");
            sql.addSql("      and EMN_OPEN_END <= cast(? as date)");
            sql.addSql("      and EMN_DATA_KBN = ?");
            sql.addSql(" )");
            sql.addIntValue(GSConstEnquete.EMN_OPEN_END_KBN_SPECIFIED);
            sql.addDateValue(date);
            sql.addIntValue(GSConstEnquete.DATA_KBN_SEND);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] 指定したアンケートの対象者を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param date 年月
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int deleteDraftEnq(UDate date) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete from");
            sql.addSql("   ENQ_SUBJECT");
            sql.addSql(" where exists (");
            sql.addSql("    select 1");
            sql.addSql("      from ENQ_MAIN");
            sql.addSql("     where ENQ_SUBJECT.EMN_SID = ENQ_MAIN.EMN_SID");
            sql.addSql("       and EMN_EDATE <= ?");
            sql.addSql("       and EMN_DATA_KBN = ?");
            sql.addSql(" )");

            sql.addDateValue(date);
            sql.addIntValue(GSConstEnquete.DATA_KBN_DRAFT);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <p>Select ENQ_SUBJECT All Data
     * @return List in ENQ_SUBJECTModel
     * @throws SQLException SQL実行例外
     */
    public List<EnqSubjectModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<EnqSubjectModel> ret = new ArrayList<EnqSubjectModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   EMN_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID");
            sql.addSql(" from ");
            sql.addSql("   ENQ_SUBJECT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getEnqSubjectFromRs(rs));
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
     * <p>Select ENQ_SUBJECT All Data
     * @param emnSid EMN_SID
     * @return List in ENQ_SUBJECTModel
     * @throws SQLException SQL実行例外
     */
    public List<EnqSubjectModel> getSubjectList(long emnSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<EnqSubjectModel> ret = new ArrayList<EnqSubjectModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   EMN_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID");
            sql.addSql(" from ");
            sql.addSql("   ENQ_SUBJECT");
            sql.addSql(" where ");
            sql.addSql("   EMN_SID = ?");
            sql.addLongValue(emnSid);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getEnqSubjectFromRs(rs));
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
     * <p>Create ENQ_SUBJECT Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created EnqSubjectModel
     * @throws SQLException SQL実行例外
     */
    private EnqSubjectModel __getEnqSubjectFromRs(ResultSet rs) throws SQLException {
        EnqSubjectModel bean = new EnqSubjectModel();
        bean.setEmnSid(rs.getInt("EMN_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setGrpSid(rs.getInt("GRP_SID"));
        return bean;
    }
}
