package jp.groupsession.v2.enq.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.base.CmnBinfDao;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.enq.GSConstEnquete;
import jp.groupsession.v2.enq.model.EnqQueMainModel;

/**
 * <p>ENQ_QUE_MAIN Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class EnqQueMainDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(EnqQueMainDao.class);

    /**
     * <p>Default Constructor
     */
    public EnqQueMainDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public EnqQueMainDao(Connection con) {
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
            sql.addSql("drop table ENQ_QUE_MAIN");

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
            sql.addSql(" create table ENQ_QUE_MAIN (");
            sql.addSql("   EMN_SID bigint not null,");
            sql.addSql("   EQM_SEQ integer not null,");
            sql.addSql("   EQM_DSP_SEC integer,");
            sql.addSql("   EQM_QUE_SEC varchar(10),");
            sql.addSql("   EQM_QUESTION varchar(100),");
            sql.addSql("   EQM_QUE_KBN integer,");
            sql.addSql("   EQM_REQUIRE integer,");
            sql.addSql("   EQM_OTHER integer,");
            sql.addSql("   EQM_DESC text,");
            sql.addSql("   EQM_DESC_PLAIN text,");
            sql.addSql("   EQM_ATTACH_KBN integer,");
            sql.addSql("   EQM_ATTACH_ID varchar(100),");
            sql.addSql("   EQM_ATTACH_NAME varchar(100),");
            sql.addSql("   EQM_ATTACH_POS integer,");
            sql.addSql("   EQM_LINE_KBN integer,");
            sql.addSql("   EQM_GRF_KBN integer,");
            sql.addSql("   EQM_AUID integer not null,");
            sql.addSql("   EQM_ADATE timestamp not null,");
            sql.addSql("   EQM_EUID integer not null,");
            sql.addSql("   EQM_EDATE timestamp not null,");
            sql.addSql("   primary key (EMN_SID,EQM_SEQ)");
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
     * <br>[機  能] 設問連番の最大値+1を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param enqSid アンケートSID
     * @return 連番のカウント値
     * @throws SQLException SQL実行例外
     */
    public int getSeqMax(long enqSid) throws SQLException {

        int sequence = 0;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   case when max(EQM_SEQ) is null then 1");
            sql.addSql("        else max(EQM_SEQ) + 1");
            sql.addSql("    end as SEQUENCE");
            sql.addSql(" from");
            sql.addSql("   ENQ_QUE_MAIN");
            sql.addSql(" where");
            sql.addSql("   EMN_SID = ?");

            sql.addLongValue(enqSid);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                sequence = rs.getInt("SEQUENCE");
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return sequence;
    }

    /**
     * <p>Insert ENQ_QUE_MAIN Data Binding JavaBean
     * @param bean ENQ_QUE_MAIN Data Binding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(EnqQueMainModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" ENQ_QUE_MAIN(");
            sql.addSql("   EMN_SID,");
            sql.addSql("   EQM_SEQ,");
            sql.addSql("   EQM_DSP_SEC,");
            sql.addSql("   EQM_QUE_SEC,");
            sql.addSql("   EQM_QUESTION,");
            sql.addSql("   EQM_QUE_KBN,");
            sql.addSql("   EQM_REQUIRE,");
            sql.addSql("   EQM_OTHER,");
            sql.addSql("   EQM_DESC,");
            sql.addSql("   EQM_DESC_PLAIN,");
            sql.addSql("   EQM_ATTACH_KBN,");
            sql.addSql("   EQM_ATTACH_ID,");
            sql.addSql("   EQM_ATTACH_NAME,");
            sql.addSql("   EQM_ATTACH_POS,");
            sql.addSql("   EQM_LINE_KBN,");
            sql.addSql("   EQM_GRF_KBN,");
            sql.addSql("   EQM_AUID,");
            sql.addSql("   EQM_ADATE,");
            sql.addSql("   EQM_EUID,");
            sql.addSql("   EQM_EDATE");
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
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(bean.getEmnSid());
            sql.addIntValue(bean.getEqmSeq());
            sql.addIntValue(bean.getEqmDspSec());
            sql.addStrValue(bean.getEqmQueSec());
            sql.addStrValue(bean.getEqmQuestion());
            sql.addIntValue(bean.getEqmQueKbn());
            sql.addIntValue(bean.getEqmRequire());
            sql.addIntValue(bean.getEqmOther());
            sql.addStrValue(bean.getEqmDesc());
            sql.addStrValue(bean.getEqmDescPlain());
            sql.addIntValue(bean.getEqmAttachKbn());
            sql.addStrValue(bean.getEqmAttachId());
            sql.addStrValue(bean.getEqmAttachName());
            sql.addIntValue(bean.getEqmAttachPos());
            sql.addIntValue(bean.getEqmLineKbn());
            sql.addIntValue(bean.getEqmGrfKbn());
            sql.addIntValue(bean.getEqmAuid());
            sql.addDateValue(bean.getEqmAdate());
            sql.addIntValue(bean.getEqmEuid());
            sql.addDateValue(bean.getEqmEdate());
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
     * <p>Update ENQ_QUE_MAIN Data Binding JavaBean
     * @param bean ENQ_QUE_MAIN Data Binding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(EnqQueMainModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ENQ_QUE_MAIN");
            sql.addSql(" set ");
            sql.addSql("   EQM_DSP_SEC=?,");
            sql.addSql("   EQM_QUE_SEC=?,");
            sql.addSql("   EQM_QUESTION=?,");
            sql.addSql("   EQM_QUE_KBN=?,");
            sql.addSql("   EQM_REQUIRE=?,");
            sql.addSql("   EQM_OTHER=?,");
            sql.addSql("   EQM_DESC=?,");
            sql.addSql("   EQM_DESC_PLAIN=?,");
            sql.addSql("   EQM_ATTACH_KBN=?,");
            sql.addSql("   EQM_ATTACH_ID=?,");
            sql.addSql("   EQM_ATTACH_NAME=?,");
            sql.addSql("   EQM_ATTACH_POS=?,");
            sql.addSql("   EQM_LINE_KBN=?,");
            sql.addSql("   EQM_GRF_KBN=?,");
            sql.addSql("   EQM_AUID=?,");
            sql.addSql("   EQM_ADATE=?,");
            sql.addSql("   EQM_EUID=?,");
            sql.addSql("   EQM_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   EMN_SID=?");
            sql.addSql(" and");
            sql.addSql("   EQM_SEQ=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getEqmDspSec());
            sql.addStrValue(bean.getEqmQueSec());
            sql.addStrValue(bean.getEqmQuestion());
            sql.addIntValue(bean.getEqmQueKbn());
            sql.addIntValue(bean.getEqmRequire());
            sql.addIntValue(bean.getEqmOther());
            sql.addStrValue(bean.getEqmDesc());
            sql.addStrValue(bean.getEqmDescPlain());
            sql.addIntValue(bean.getEqmAttachKbn());
            sql.addStrValue(bean.getEqmAttachId());
            sql.addStrValue(bean.getEqmAttachName());
            sql.addIntValue(bean.getEqmAttachPos());
            sql.addIntValue(bean.getEqmLineKbn());
            sql.addIntValue(bean.getEqmGrfKbn());
            sql.addIntValue(bean.getEqmAuid());
            sql.addDateValue(bean.getEqmAdate());
            sql.addIntValue(bean.getEqmEuid());
            sql.addDateValue(bean.getEqmEdate());
            //where
            sql.addLongValue(bean.getEmnSid());
            sql.addIntValue(bean.getEqmSeq());

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
     * <p>Update ENQ_QUE_MAIN Data Binding JavaBean
     * @param bean ENQ_QUE_MAIN Data Binding JavaBean
     * @param mode モード
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(EnqQueMainModel bean, int mode) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ENQ_QUE_MAIN");
            sql.addSql(" set ");
            sql.addSql("   EQM_DSP_SEC=?,");
            sql.addSql("   EQM_QUE_SEC=?,");
            sql.addSql("   EQM_QUESTION=?,");
            sql.addSql("   EQM_REQUIRE=?,");
            sql.addSql("   EQM_EUID=?,");
            sql.addSql("   EQM_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   EMN_SID=?");
            sql.addSql(" and");
            sql.addSql("   EQM_SEQ=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getEqmDspSec());
            sql.addStrValue(bean.getEqmQueSec());
            sql.addStrValue(bean.getEqmQuestion());
            sql.addIntValue(bean.getEqmRequire());
            sql.addIntValue(bean.getEqmEuid());
            sql.addDateValue(bean.getEqmEdate());
            //where
            sql.addLongValue(bean.getEmnSid());
            sql.addIntValue(bean.getEqmSeq());

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
     * <p>Select ENQ_QUE_MAIN All Data
     * @return List in ENQ_QUE_MAINModel
     * @throws SQLException SQL実行例外
     */
    public List<EnqQueMainModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<EnqQueMainModel> ret = new ArrayList<EnqQueMainModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   EMN_SID,");
            sql.addSql("   EQM_SEQ,");
            sql.addSql("   EQM_DSP_SEC,");
            sql.addSql("   EQM_QUE_SEC,");
            sql.addSql("   EQM_QUESTION,");
            sql.addSql("   EQM_QUE_KBN,");
            sql.addSql("   EQM_REQUIRE,");
            sql.addSql("   EQM_OTHER,");
            sql.addSql("   EQM_DESC,");
            sql.addSql("   EQM_DESC_PLAIN,");
            sql.addSql("   EQM_ATTACH_KBN,");
            sql.addSql("   EQM_ATTACH_ID,");
            sql.addSql("   EQM_ATTACH_NAME,");
            sql.addSql("   EQM_ATTACH_POS,");
            sql.addSql("   EQM_LINE_KBN,");
            sql.addSql("   EQM_GRF_KBN,");
            sql.addSql("   EQM_AUID,");
            sql.addSql("   EQM_ADATE,");
            sql.addSql("   EQM_EUID,");
            sql.addSql("   EQM_EDATE");
            sql.addSql(" from ");
            sql.addSql("   ENQ_QUE_MAIN");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getEnqQueMainFromRs(rs));
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
     * <p>Select ENQ_QUE_MAIN
     * @param emnSid EMN_SID
     * @return ENQ_QUE_MAINModel
     * @throws SQLException SQL実行例外
     */

    public ArrayList<EnqQueMainModel> select(long emnSid) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<EnqQueMainModel> ret = new ArrayList<EnqQueMainModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   EMN_SID,");
            sql.addSql("   EQM_SEQ,");
            sql.addSql("   EQM_DSP_SEC,");
            sql.addSql("   EQM_QUE_SEC,");
            sql.addSql("   EQM_QUESTION,");
            sql.addSql("   EQM_QUE_KBN,");
            sql.addSql("   EQM_REQUIRE,");
            sql.addSql("   EQM_OTHER,");
            sql.addSql("   EQM_DESC,");
            sql.addSql("   EQM_DESC_PLAIN,");
            sql.addSql("   EQM_ATTACH_KBN,");
            sql.addSql("   EQM_ATTACH_ID,");
            sql.addSql("   EQM_ATTACH_NAME,");
            sql.addSql("   EQM_ATTACH_POS,");
            sql.addSql("   EQM_LINE_KBN,");
            sql.addSql("   EQM_GRF_KBN,");
            sql.addSql("   EQM_AUID,");
            sql.addSql("   EQM_ADATE,");
            sql.addSql("   EQM_EUID,");
            sql.addSql("   EQM_EDATE");
            sql.addSql(" from");
            sql.addSql("   ENQ_QUE_MAIN");
            sql.addSql(" where ");
            sql.addSql("   EMN_SID=?");
            sql.addSql(" order by");
            sql.addSql("   EQM_DSP_SEC");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(emnSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getEnqQueMainFromRs(rs));
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
     * <p>Select ENQ_QUE_MAIN
     * @param emnSid EMN_SID
     * @param eqmSeq EQM_SEQ
     * @return ENQ_QUE_MAINModel
     * @throws SQLException SQL実行例外
     */
    public EnqQueMainModel select(long emnSid, int eqmSeq) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        EnqQueMainModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   EMN_SID,");
            sql.addSql("   EQM_SEQ,");
            sql.addSql("   EQM_DSP_SEC,");
            sql.addSql("   EQM_QUE_SEC,");
            sql.addSql("   EQM_QUESTION,");
            sql.addSql("   EQM_QUE_KBN,");
            sql.addSql("   EQM_REQUIRE,");
            sql.addSql("   EQM_OTHER,");
            sql.addSql("   EQM_DESC,");
            sql.addSql("   EQM_DESC_PLAIN,");
            sql.addSql("   EQM_ATTACH_KBN,");
            sql.addSql("   EQM_ATTACH_ID,");
            sql.addSql("   EQM_ATTACH_NAME,");
            sql.addSql("   EQM_ATTACH_POS,");
            sql.addSql("   EQM_LINE_KBN,");
            sql.addSql("   EQM_GRF_KBN,");
            sql.addSql("   EQM_AUID,");
            sql.addSql("   EQM_ADATE,");
            sql.addSql("   EQM_EUID,");
            sql.addSql("   EQM_EDATE");
            sql.addSql(" from");
            sql.addSql("   ENQ_QUE_MAIN");
            sql.addSql(" where ");
            sql.addSql("   EMN_SID=?");
            sql.addSql(" and");
            sql.addSql("   EQM_SEQ=?");
            sql.addSql(" order by");
            sql.addSql("   EQM_DSP_SEC");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(emnSid);
            sql.addIntValue(eqmSeq);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getEnqQueMainFromRs(rs);
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
     * <p>Delete ENQ_QUE_MAIN
     * @param emnSid EMN_SID
     * @param eqmSeq EQM_SEQ
     * @return count 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(long emnSid, int eqmSeq) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   ENQ_QUE_MAIN");
            sql.addSql(" where ");
            sql.addSql("   EMN_SID=?");
            sql.addSql(" and");
            sql.addSql("   EQM_SEQ=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(emnSid);
            sql.addIntValue(eqmSeq);

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
     * <p>Delete ENQ_QUE_MAIN
     * @param emnSid EMN_SID
     * @return count 削除件数
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
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   ENQ_QUE_MAIN");
            sql.addSql(" where");
            sql.addSql("   EMN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(emnSid);

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
     * <br>[機  能] 発信フォルダ手動削除処理
     * <br>[解  説]
     * <br>[備  考] アンケート_基本情報の物理削除前に実行すること
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
            sql.addSql(" delete from ");
            sql.addSql("   ENQ_QUE_MAIN");
            sql.addSql("  where exists (");
            sql.addSql("    select 1");
            sql.addSql("      from ENQ_MAIN");
            sql.addSql("      where ENQ_QUE_MAIN.EMN_SID = ENQ_MAIN.EMN_SID");
            sql.addSql("      and EMN_OPEN_END_KBN = ?");
            sql.addSql("      and EMN_OPEN_END <= cast(? as date)");
            sql.addSql("      and EMN_DATA_KBN = ? ");
            sql.addSql("  )");
            sql.addIntValue(GSConstEnquete.EMN_OPEN_END_KBN_SPECIFIED);
            sql.addDateValue(date);
            sql.addIntValue(GSConstEnquete.DATA_KBN_SEND);

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
     * <br>[機  能] 草稿フォルダ手動削除処理
     * <br>[解  説]
     * <br>[備  考] アンケート_基本情報の物理削除前に実行すること
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
            sql.addSql(" delete from ");
            sql.addSql("   ENQ_QUE_MAIN");
            sql.addSql("  where exists (");
            sql.addSql("    select 1");
            sql.addSql("      from ENQ_MAIN");
            sql.addSql("     where ENQ_QUE_MAIN.EMN_SID = ENQ_MAIN.EMN_SID");
            sql.addSql("       and EMN_EDATE <= ?");
            sql.addSql("       and EMN_DATA_KBN = ? ");
            sql.addSql("  )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addDateValue(date);
            sql.addIntValue(GSConstEnquete.DATA_KBN_DRAFT);

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
     * <br>[機  能] 発信フォルダ手動削除時における、バイナリ情報論理削除処理
     * <br>[解  説]
     * <br>[備  考] 設問_基本テーブルの物理削除前に実行すること
     * @param date 年月の閾値
     * @param now 現在日
     * @param usrSid ユーザSID
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateJkbnFromSendEnq(UDate date, UDate now, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        //削除対象となるファイルのバイナリSIDを取得
        List<Long> binSidList = new ArrayList<Long>();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ENQ_QUE_MAIN.EQM_ATTACH_ID as EQM_ATTACH_ID");
            sql.addSql(" from ENQ_MAIN, ENQ_QUE_MAIN");
            sql.addSql(" where ENQ_MAIN.EMN_OPEN_END_KBN = ?");
            sql.addSql(" and ENQ_MAIN.EMN_OPEN_END <= ?");
            sql.addSql(" and ENQ_MAIN.EMN_DATA_KBN = ?");
            sql.addSql(" and ENQ_MAIN.EMN_SID = ENQ_QUE_MAIN.EMN_SID");
            sql.addSql(" and ENQ_QUE_MAIN.EQM_ATTACH_KBN in(?,");
            sql.addSql("          ?)");

            UDate openEnd = date.cloneUDate();
            openEnd.setMaxHhMmSs();
            sql.addIntValue(GSConstEnquete.EMN_OPEN_END_KBN_SPECIFIED);
            sql.addDateValue(openEnd);
            sql.addIntValue(GSConstEnquete.DATA_KBN_SEND);
            sql.addIntValue(GSConstEnquete.TEMP_IMAGE);
            sql.addIntValue(GSConstEnquete.TEMP_FILE);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String binSid = rs.getString("EQM_ATTACH_ID");
                if (binSid != null && ValidateUtil.isNumber(binSid)) {
                    binSidList.add(Long.parseLong(binSid));
                }
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        //削除対象ファイルが存在する場合、バイナリ情報を論理削除する
        if (!binSidList.isEmpty()) {
            CmnBinfModel binMdl = new CmnBinfModel();
            binMdl.setBinUpuser(usrSid);
            binMdl.setBinUpdate(now);
            binMdl.setBinJkbn(GSConst.JTKBN_DELETE);
            CmnBinfDao binDao = new CmnBinfDao(con);
            count = binDao.updateJKbn(binMdl, binSidList);
        }

        return count;
    }

    /**
     * <br>[機  能] 草稿フォルダ手動削除時における、バイナリ情報論理削除処理
     * <br>[解  説]
     * <br>[備  考] 設問_基本テーブルの物理削除前に実行すること
     * @param date 年月の閾値
     * @param now 現在日
     * @param usrSid ユーザSID
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateJkbnFromDraftEnq(UDate date, UDate now, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        //削除対象となるファイルのバイナリSIDを取得
        List<Long> binSidList = new ArrayList<Long>();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ENQ_QUE_MAIN.EQM_ATTACH_ID as EQM_ATTACH_ID");
            sql.addSql(" from ENQ_MAIN, ENQ_QUE_MAIN");
            sql.addSql(" where ENQ_MAIN.EMN_EDATE <= ?");
            sql.addSql(" and ENQ_MAIN.EMN_DATA_KBN = ?");
            sql.addSql(" and ENQ_MAIN.EMN_SID = ENQ_QUE_MAIN.EMN_SID");
            sql.addSql(" and ENQ_QUE_MAIN.EQM_ATTACH_KBN in(?,");
            sql.addSql("          ?)");

            sql.addDateValue(date);
            sql.addIntValue(GSConstEnquete.DATA_KBN_DRAFT);
            sql.addIntValue(GSConstEnquete.TEMP_IMAGE);
            sql.addIntValue(GSConstEnquete.TEMP_FILE);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String binSid = rs.getString("EQM_ATTACH_ID");
                if (binSid != null && ValidateUtil.isNumber(binSid)) {
                    binSidList.add(Long.parseLong(binSid));
                }
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        //削除対象ファイルが存在する場合、バイナリ情報を論理削除する
        if (!binSidList.isEmpty()) {
            CmnBinfModel binMdl = new CmnBinfModel();
            binMdl.setBinUpuser(usrSid);
            binMdl.setBinUpdate(now);
            binMdl.setBinJkbn(GSConst.JTKBN_DELETE);
            CmnBinfDao binDao = new CmnBinfDao(con);
            count = binDao.updateJKbn(binMdl, binSidList);
        }

        return count;
    }

    /**
     * <br>[機  能] 指定した設問_基本情報に関連するバイナリー情報の論理削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param emnSid アンケートSID
     * @param userSid 更新者SID
     * @param date 更新日時
     * @return int 更新件数
     * @throws SQLException SQL実行例外
     */
    public int removeBinData(long emnSid, int userSid, UDate date)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        //削除対象となるファイルのバイナリSIDを取得
        List<Long> binSidList = new ArrayList<Long>();
        try {
            //バイナリー情報の論理削除
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select  EQM_ATTACH_ID from ENQ_QUE_MAIN");
            sql.addSql(" where EMN_SID = ? ");
            sql.addSql(" and");
            sql.addSql("   (EQM_ATTACH_KBN = ? ");
            sql.addSql("   or EQM_ATTACH_KBN = ?) ");
            sql.addLongValue(emnSid);
            sql.addIntValue(GSConstEnquete.EQM_ATTACH_KBN_IMAGE);
            sql.addIntValue(GSConstEnquete.EQM_ATTACH_KBN_FILE);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String binSid = rs.getString("EQM_ATTACH_ID");
                if (binSid != null && ValidateUtil.isNumber(binSid)) {
                    binSidList.add(Long.parseLong(binSid));
                }
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        //削除対象ファイルが存在する場合、バイナリ情報を論理削除する
        if (!binSidList.isEmpty()) {
            CmnBinfModel binMdl = new CmnBinfModel();
            binMdl.setBinUpuser(userSid);
            binMdl.setBinUpdate(date);
            binMdl.setBinJkbn(GSConst.JTKBN_DELETE);
            CmnBinfDao binDao = new CmnBinfDao(con);
            count = binDao.updateJKbn(binMdl, binSidList);
        }

        return count;
    }

    /**
     * 指定したアンケート基本情報に関連する設問_基本情報の件数を取得する
     * @param emnSidList EMN_SID
     * @return 件数
     * @throws SQLException SQL実行例外
     */
    public long getQueCount(List<Long> emnSidList) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        long ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(EMN_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   ENQ_QUE_MAIN");
            sql.addSql(" where ");
            sql.addSql("   EMN_SID in (");

            for (int idx = 0; idx < emnSidList.size(); idx++) {
                if (idx == 0) {
                    sql.addSql("     ?");
                } else {
                    sql.addSql("     ,?");
                }
                sql.addLongValue(emnSidList.get(idx));
            }

            sql.addSql("  )");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getLong("CNT");
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
     * <br>[機  能] 指定したアンケート設問情報_メインの「説明」サイズ合計を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param emnSidList アンケートSID
     * @return 「説明」サイズ合計
     * @throws SQLException SQL実行例外
     */
    public long getTotalDescSize(List<Long> emnSidList) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        long descSize = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   sum(octet_length(EQM_QUESTION)) as EQM_QUESTION,");
            sql.addSql("   sum(octet_length(EQM_DESC)) as EQM_DESC,");
            sql.addSql("   sum(octet_length(EQM_DESC_PLAIN)) as EQM_DESC_PLAIN");
            sql.addSql(" from");
            sql.addSql("   ENQ_QUE_MAIN");
            sql.addSql(" where");
            sql.addSql("   EMN_SID in (");

            for (int idx = 0; idx < emnSidList.size(); idx++) {
                if (idx == 0) {
                    sql.addSql("     ?");
                } else {
                    sql.addSql("     ,?");
                }
                sql.addLongValue(emnSidList.get(idx));
            }

            sql.addSql("  )");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                descSize = rs.getLong("EQM_QUESTION");
                descSize += rs.getLong("EQM_DESC");
                descSize += rs.getLong("EQM_DESC_PLAIN");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return descSize;
    }

    /**
     * <br>[機  能] 指定した設問_基本情報の添付ファイルサイズ合計を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param emnSidList アンケートSID
     * @return ファイルサイズ合計
     * @throws SQLException SQL実行例外
     */
    public long getTotalFileSize(List<Long> emnSidList) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        long fileSize = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   sum(CMN_BINF.BIN_FILE_SIZE) as FILE_SIZE");
            sql.addSql(" from");
            sql.addSql("   ENQ_QUE_MAIN,");
            sql.addSql("   CMN_BINF");
            sql.addSql(" where");
            sql.addSql("   (");
            sql.addSql("     EQM_ATTACH_KBN = ?");
            sql.addSql("   or");
            sql.addSql("     EQM_ATTACH_KBN = ?");
            sql.addSql("   )");
            sql.addIntValue(GSConstEnquete.EQM_ATTACH_KBN_IMAGE);
            sql.addIntValue(GSConstEnquete.EQM_ATTACH_KBN_FILE);

            sql.addSql(" and");
            sql.addSql("   ENQ_QUE_MAIN.EQM_ATTACH_ID is not null");
            sql.addSql(" and");
            sql.addSql("   cast(ENQ_QUE_MAIN.EQM_ATTACH_ID as bigint) = CMN_BINF.BIN_SID");
            sql.addSql(" and");
            sql.addSql("   ENQ_QUE_MAIN.EMN_SID in (");

            for (int idx = 0; idx < emnSidList.size(); idx++) {
                if (idx == 0) {
                    sql.addSql("     ?");
                } else {
                    sql.addSql("     ,?");
                }
                sql.addLongValue(emnSidList.get(idx));
            }

            sql.addSql("  )");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                fileSize = rs.getLong("FILE_SIZE");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return fileSize;
    }

    /**
     * <p>Create ENQ_QUE_MAIN Data Binding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created EnqQueMainModel
     * @throws SQLException SQL実行例外
     */
    private EnqQueMainModel __getEnqQueMainFromRs(ResultSet rs) throws SQLException {
        EnqQueMainModel bean = new EnqQueMainModel();
        bean.setEmnSid(rs.getInt("EMN_SID"));
        bean.setEqmSeq(rs.getInt("EQM_SEQ"));
        bean.setEqmDspSec(rs.getInt("EQM_DSP_SEC"));
        bean.setEqmQueSec(rs.getString("EQM_QUE_SEC"));
        bean.setEqmQuestion(rs.getString("EQM_QUESTION"));
        bean.setEqmQueKbn(rs.getInt("EQM_QUE_KBN"));
        bean.setEqmRequire(rs.getInt("EQM_REQUIRE"));
        bean.setEqmOther(rs.getInt("EQM_OTHER"));
        bean.setEqmDesc(rs.getString("EQM_DESC"));
        bean.setEqmDescPlain(rs.getString("EQM_DESC_PLAIN"));
        bean.setEqmAttachKbn(rs.getInt("EQM_ATTACH_KBN"));
        bean.setEqmAttachId(rs.getString("EQM_ATTACH_ID"));
        bean.setEqmAttachName(rs.getString("EQM_ATTACH_NAME"));
        bean.setEqmAttachPos(rs.getInt("EQM_ATTACH_POS"));
        bean.setEqmLineKbn(rs.getInt("EQM_LINE_KBN"));
        bean.setEqmGrfKbn(rs.getInt("EQM_GRF_KBN"));
        bean.setEqmAuid(rs.getInt("EQM_AUID"));
        bean.setEqmAdate(UDate.getInstanceTimestamp(rs.getTimestamp("EQM_ADATE")));
        bean.setEqmEuid(rs.getInt("EQM_EUID"));
        bean.setEqmEdate(UDate.getInstanceTimestamp(rs.getTimestamp("EQM_EDATE")));
        return bean;
    }
}
