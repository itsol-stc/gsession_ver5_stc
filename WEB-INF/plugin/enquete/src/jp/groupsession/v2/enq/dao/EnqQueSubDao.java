package jp.groupsession.v2.enq.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.enq.GSConstEnquete;
import jp.groupsession.v2.enq.enq210.Enq210QueModel;
import jp.groupsession.v2.enq.model.EnqQueSubModel;

/**
 * <p>ENQ_QUE_SUB Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class EnqQueSubDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(EnqQueSubDao.class);

    /**
     * <p>Default Constructor
     */
    public EnqQueSubDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public EnqQueSubDao(Connection con) {
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
            sql.addSql("drop table ENQ_QUE_SUB");

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
            sql.addSql(" create table ENQ_QUE_SUB (");
            sql.addSql("   EMN_SID bigint not null,");
            sql.addSql("   EQM_SEQ integer not null,");
            sql.addSql("   EQS_SEQ integer not null,");
            sql.addSql("   EQS_DSP_SEC integer,");
            sql.addSql("   EQS_DSP_NAME varchar(30),");
            sql.addSql("   EQS_DEF_TXT varchar(1000),");
            sql.addSql("   EQS_DEF_NUM decimal(15, 5),");
            sql.addSql("   EQS_DEF_DAT Date,");
            sql.addSql("   EQS_DEF varchar(1000),");
            sql.addSql("   EQS_RNG_STR_NUM decimal(15, 5),");
            sql.addSql("   EQS_RNG_END_NUM decimal(15, 5),");
            sql.addSql("   EQS_RNG_STR_DAT Date,");
            sql.addSql("   EQS_RNG_END_DAT Date,");
            sql.addSql("   EQS_UNIT_NUM varchar(10),");
            sql.addSql("   EQS_AUID integer not null,");
            sql.addSql("   EQS_ADATE timestamp not null,");
            sql.addSql("   EQS_EUID integer not null,");
            sql.addSql("   EQS_EDATE timestamp not null,");
            sql.addSql("   primary key (EMN_SID,EQM_SEQ,EQS_SEQ)");
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
     * <p>Insert ENQ_QUE_SUB Data Binding JavaBean
     * @param bean ENQ_QUE_SUB Data Binding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(EnqQueSubModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" ENQ_QUE_SUB(");
            sql.addSql("   EMN_SID,");
            sql.addSql("   EQM_SEQ,");
            sql.addSql("   EQS_SEQ,");
            sql.addSql("   EQS_DSP_SEC,");
            sql.addSql("   EQS_DSP_NAME,");
            sql.addSql("   EQS_DEF_TXT,");
            sql.addSql("   EQS_DEF_NUM,");
            sql.addSql("   EQS_DEF_DAT,");
            sql.addSql("   EQS_DEF,");
            sql.addSql("   EQS_RNG_STR_NUM,");
            sql.addSql("   EQS_RNG_END_NUM,");
            sql.addSql("   EQS_RNG_STR_DAT,");
            sql.addSql("   EQS_RNG_END_DAT,");
            sql.addSql("   EQS_UNIT_NUM,");
            sql.addSql("   EQS_AUID,");
            sql.addSql("   EQS_ADATE,");
            sql.addSql("   EQS_EUID,");
            sql.addSql("   EQS_EDATE");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(bean.getEmnSid());
            sql.addIntValue(bean.getEqmSeq());
            sql.addIntValue(bean.getEqsSeq());
            sql.addIntValue(bean.getEqsDspSec());
            sql.addStrValue(bean.getEqsDspName());
            sql.addStrValue(bean.getEqsDefTxt());
            sql.addDecimalValue(bean.getEqsDefNum());
            sql.addDateValue(bean.getEqsDefDat());
            sql.addStrValue(bean.getEqsDef());
            sql.addDecimalValue(bean.getEqsRngStrNum());
            sql.addDecimalValue(bean.getEqsRngEndNum());
            sql.addDateValue(bean.getEqsRngStrDat());
            sql.addDateValue(bean.getEqsRngEndDat());
            sql.addStrValue(bean.getEqsUnitNum());
            sql.addIntValue(bean.getEqsAuid());
            sql.addDateValue(bean.getEqsAdate());
            sql.addIntValue(bean.getEqsEuid());
            sql.addDateValue(bean.getEqsEdate());
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
     * <br>[機  能] 設問リスト登録処理
     * <br>[解  説]
     * <br>[備  考]
     * @param bean ENQ_QUE_SUB Data Binding JavaBean
     * @param baseForm 設問リスト
     * @throws SQLException SQL実行例外
     */
    public void insert(EnqQueSubModel bean, Enq210QueModel baseForm) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" ENQ_QUE_SUB(");
            sql.addSql("   EMN_SID,");
            sql.addSql("   EQM_SEQ,");
            sql.addSql("   EQS_SEQ,");
            sql.addSql("   EQS_DSP_SEC,");
            sql.addSql("   EQS_DSP_NAME,");
            sql.addSql("   EQS_DEF_TXT,");
            sql.addSql("   EQS_DEF_NUM,");
            sql.addSql("   EQS_DEF_DAT,");
            sql.addSql("   EQS_DEF,");
            if (baseForm.getEnq210QueKbn() == GSConstEnquete.SYURUI_INTEGER) {
                if (!StringUtil.isNullZeroString(baseForm.getEnq210rangeTxtFr())) {
                    sql.addSql("   EQS_RNG_STR_NUM,");
                }
                if (!StringUtil.isNullZeroString(baseForm.getEnq210rangeTxtTo())) {
                    sql.addSql("   EQS_RNG_END_NUM,");
                }
            }
            sql.addSql("   EQS_RNG_STR_DAT,");
            sql.addSql("   EQS_RNG_END_DAT,");
            sql.addSql("   EQS_UNIT_NUM,");
            sql.addSql("   EQS_AUID,");
            sql.addSql("   EQS_ADATE,");
            sql.addSql("   EQS_EUID,");
            sql.addSql("   EQS_EDATE");
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
            if (baseForm.getEnq210QueKbn() == GSConstEnquete.SYURUI_INTEGER) {
                if (!StringUtil.isNullZeroString(baseForm.getEnq210rangeTxtFr())) {
                    sql.addSql("   ?,");
                }
                if (!StringUtil.isNullZeroString(baseForm.getEnq210rangeTxtTo())) {
                    sql.addSql("   ?,");
                }
            }
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
            sql.addIntValue(bean.getEqsSeq());
            sql.addIntValue(bean.getEqsDspSec());
            sql.addStrValue(bean.getEqsDspName());
            sql.addStrValue(bean.getEqsDefTxt());
            sql.addDecimalValue(bean.getEqsDefNum());
            sql.addDateValue(bean.getEqsDefDat());
            sql.addStrValue(bean.getEqsDef());
            if (baseForm.getEnq210QueKbn() == GSConstEnquete.SYURUI_INTEGER) {
                if (!StringUtil.isNullZeroString(baseForm.getEnq210rangeTxtFr())) {
                    sql.addDecimalValue(bean.getEqsRngStrNum());
                }
                if (!StringUtil.isNullZeroString(baseForm.getEnq210rangeTxtTo())) {
                    sql.addDecimalValue(bean.getEqsRngEndNum());
                }
            }
            sql.addDateValue(bean.getEqsRngStrDat());
            sql.addDateValue(bean.getEqsRngEndDat());
            sql.addStrValue(bean.getEqsUnitNum());
            sql.addIntValue(bean.getEqsAuid());
            sql.addDateValue(bean.getEqsAdate());
            sql.addIntValue(bean.getEqsEuid());
            sql.addDateValue(bean.getEqsEdate());
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
     * <p>Update ENQ_QUE_SUB Data Binding JavaBean
     * @param bean ENQ_QUE_SUB Data Binding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(EnqQueSubModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ENQ_QUE_SUB");
            sql.addSql(" set ");
            sql.addSql("   EQS_DSP_SEC=?,");
            sql.addSql("   EQS_DSP_NAME=?,");
            sql.addSql("   EQS_DEF_TXT=?,");
            sql.addSql("   EQS_DEF_NUM=?,");
            sql.addSql("   EQS_DEF_DAT=?,");
            sql.addSql("   EQS_DEF=?,");
            sql.addSql("   EQS_RNG_STR_NUM=?,");
            sql.addSql("   EQS_RNG_END_NUM=?,");
            sql.addSql("   EQS_RNG_STR_DAT=?,");
            sql.addSql("   EQS_RNG_END_DAT=?,");
            sql.addSql("   EQS_UNIT_NUM=?,");
            sql.addSql("   EQS_AUID=?,");
            sql.addSql("   EQS_ADATE=?,");
            sql.addSql("   EQS_EUID=?,");
            sql.addSql("   EQS_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   EMN_SID=?");
            sql.addSql(" and");
            sql.addSql("   EQM_SEQ=?");
            sql.addSql(" and");
            sql.addSql("   EQS_SEQ=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getEqsDspSec());
            sql.addStrValue(bean.getEqsDspName());
            sql.addStrValue(bean.getEqsDefTxt());
            sql.addDecimalValue(bean.getEqsDefNum());
            sql.addDateValue(bean.getEqsDefDat());
            sql.addStrValue(bean.getEqsDef());
            sql.addDecimalValue(bean.getEqsRngStrNum());
            sql.addDecimalValue(bean.getEqsRngEndNum());
            sql.addDateValue(bean.getEqsRngStrDat());
            sql.addDateValue(bean.getEqsRngEndDat());
            sql.addStrValue(bean.getEqsUnitNum());
            sql.addIntValue(bean.getEqsAuid());
            sql.addDateValue(bean.getEqsAdate());
            sql.addIntValue(bean.getEqsEuid());
            sql.addDateValue(bean.getEqsEdate());
            //where
            sql.addLongValue(bean.getEmnSid());
            sql.addIntValue(bean.getEqmSeq());
            sql.addIntValue(bean.getEqsSeq());

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
     * <p>Select ENQ_QUE_SUB All Data
     * @return List in ENQ_QUE_SUBModel
     * @throws SQLException SQL実行例外
     */
    public List<EnqQueSubModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<EnqQueSubModel> ret = new ArrayList<EnqQueSubModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   EMN_SID,");
            sql.addSql("   EQM_SEQ,");
            sql.addSql("   EQS_SEQ,");
            sql.addSql("   EQS_DSP_SEC,");
            sql.addSql("   EQS_DSP_NAME,");
            sql.addSql("   EQS_DEF_TXT,");
            sql.addSql("   EQS_DEF_NUM,");
            sql.addSql("   EQS_DEF_DAT,");
            sql.addSql("   EQS_DEF,");
            sql.addSql("   EQS_RNG_STR_NUM,");
            sql.addSql("   EQS_RNG_END_NUM,");
            sql.addSql("   EQS_RNG_STR_DAT,");
            sql.addSql("   EQS_RNG_END_DAT,");
            sql.addSql("   EQS_UNIT_NUM,");
            sql.addSql("   EQS_AUID,");
            sql.addSql("   EQS_ADATE,");
            sql.addSql("   EQS_EUID,");
            sql.addSql("   EQS_EDATE");
            sql.addSql(" from ");
            sql.addSql("   ENQ_QUE_SUB");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getEnqQueSubFromRs(rs));
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
     * <p>Select ENQ_QUE_SUB
     * @param emnSid EMN_SID
     * @return ENQ_QUE_SUBModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<EnqQueSubModel> select(long emnSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<EnqQueSubModel> ret = new ArrayList<EnqQueSubModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   EMN_SID,");
            sql.addSql("   EQM_SEQ,");
            sql.addSql("   EQS_SEQ,");
            sql.addSql("   EQS_DSP_SEC,");
            sql.addSql("   EQS_DSP_NAME,");
            sql.addSql("   EQS_DEF_TXT,");
            sql.addSql("   EQS_DEF_NUM,");
            sql.addSql("   EQS_DEF_DAT,");
            sql.addSql("   EQS_DEF,");
            sql.addSql("   EQS_RNG_STR_NUM,");
            sql.addSql("   EQS_RNG_END_NUM,");
            sql.addSql("   EQS_RNG_STR_DAT,");
            sql.addSql("   EQS_RNG_END_DAT,");
            sql.addSql("   EQS_UNIT_NUM,");
            sql.addSql("   EQS_AUID,");
            sql.addSql("   EQS_ADATE,");
            sql.addSql("   EQS_EUID,");
            sql.addSql("   EQS_EDATE");
            sql.addSql(" from");
            sql.addSql("   ENQ_QUE_SUB");
            sql.addSql(" where ");
            sql.addSql("   EMN_SID=?");
            sql.addSql(" order by");
            sql.addSql("   EQM_SEQ ASC,");
            sql.addSql("   EQS_DSP_SEC ASC");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(emnSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getEnqQueSubFromRs(rs));
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
     * <p>Select ENQ_QUE_SUB
     * @param emnSid EMN_SID
     * @param eqmSeq EQM_SEQ
     * @return ENQ_QUE_SUBModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<EnqQueSubModel> select(long emnSid, int eqmSeq) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<EnqQueSubModel> ret = new ArrayList<EnqQueSubModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   EMN_SID,");
            sql.addSql("   EQM_SEQ,");
            sql.addSql("   EQS_SEQ,");
            sql.addSql("   EQS_DSP_SEC,");
            sql.addSql("   EQS_DSP_NAME,");
            sql.addSql("   EQS_DEF_TXT,");
            sql.addSql("   EQS_DEF_NUM,");
            sql.addSql("   EQS_DEF_DAT,");
            sql.addSql("   EQS_DEF,");
            sql.addSql("   EQS_RNG_STR_NUM,");
            sql.addSql("   EQS_RNG_END_NUM,");
            sql.addSql("   EQS_RNG_STR_DAT,");
            sql.addSql("   EQS_RNG_END_DAT,");
            sql.addSql("   EQS_UNIT_NUM,");
            sql.addSql("   EQS_AUID,");
            sql.addSql("   EQS_ADATE,");
            sql.addSql("   EQS_EUID,");
            sql.addSql("   EQS_EDATE");
            sql.addSql(" from");
            sql.addSql("   ENQ_QUE_SUB");
            sql.addSql(" where ");
            sql.addSql("   EMN_SID=?");
            sql.addSql(" and");
            sql.addSql("   EQM_SEQ=?");
            sql.addSql(" order by");
            sql.addSql("   EQS_DSP_SEC ASC");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(emnSid);
            sql.addIntValue(eqmSeq);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getEnqQueSubFromRs(rs));
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
     * <p>Select ENQ_QUE_SUB
     * @param emnSid EMN_SID
     * @param eqmSeq EQM_SEQ
     * @param eqsSeq EQS_SEQ
     * @return ENQ_QUE_SUBModel
     * @throws SQLException SQL実行例外
     */
    public EnqQueSubModel select(long emnSid, int eqmSeq, int eqsSeq) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        EnqQueSubModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   EMN_SID,");
            sql.addSql("   EQM_SEQ,");
            sql.addSql("   EQS_SEQ,");
            sql.addSql("   EQS_DSP_SEC,");
            sql.addSql("   EQS_DSP_NAME,");
            sql.addSql("   EQS_DEF_TXT,");
            sql.addSql("   EQS_DEF_NUM,");
            sql.addSql("   EQS_DEF_DAT,");
            sql.addSql("   EQS_DEF,");
            sql.addSql("   EQS_RNG_STR_NUM,");
            sql.addSql("   EQS_RNG_END_NUM,");
            sql.addSql("   EQS_RNG_STR_DAT,");
            sql.addSql("   EQS_RNG_END_DAT,");
            sql.addSql("   EQS_UNIT_NUM,");
            sql.addSql("   EQS_AUID,");
            sql.addSql("   EQS_ADATE,");
            sql.addSql("   EQS_EUID,");
            sql.addSql("   EQS_EDATE");
            sql.addSql(" from");
            sql.addSql("   ENQ_QUE_SUB");
            sql.addSql(" where ");
            sql.addSql("   EMN_SID=?");
            sql.addSql(" and");
            sql.addSql("   EQM_SEQ=?");
            sql.addSql(" and");
            sql.addSql("   EQS_SEQ=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(emnSid);
            sql.addIntValue(eqmSeq);
            sql.addIntValue(eqsSeq);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getEnqQueSubFromRs(rs);
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
     * <p>Delete ENQ_QUE_SUB
     * @param emnSid EMN_SID
     * @param eqmSeq EQM_SEQ
     * @param eqsSeq EQS_SEQ
     * @return count 件数
     * @throws SQLException SQL実行例外
     */
    public int delete(long emnSid, int eqmSeq, int eqsSeq) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   ENQ_QUE_SUB");
            sql.addSql(" where ");
            sql.addSql("   EMN_SID=?");
            sql.addSql(" and");
            sql.addSql("   EQM_SEQ=?");
            sql.addSql(" and");
            sql.addSql("   EQS_SEQ=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(emnSid);
            sql.addIntValue(eqmSeq);
            sql.addIntValue(eqsSeq);

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
     * <p>Delete ENQ_QUE_SUB
     * @param emnSid EMN_SID
     * @param eqmSeq EQM_SEQ
     * @return count 件数
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
            sql.addSql("   ENQ_QUE_SUB");
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
     * <p>Delete ENQ_QUE_SUB
     * @param emnSid EMN_SID
     * @return count 件数
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
            sql.addSql("   ENQ_QUE_SUB");
            sql.addSql(" where ");
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
            sql.addSql("   ENQ_QUE_SUB");
            sql.addSql("  where exists (");
            sql.addSql("    select 1");
            sql.addSql("      from ENQ_MAIN");
            sql.addSql("      where ENQ_QUE_SUB.EMN_SID = ENQ_MAIN.EMN_SID");
            sql.addSql("      and EMN_OPEN_END_KBN = ?");
            sql.addSql("      and EMN_OPEN_END <= cast(? as date)");
            sql.addSql("      and EMN_DATA_KBN = ?");
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
            sql.addSql("   ENQ_QUE_SUB");
            sql.addSql("  where exists (");
            sql.addSql("    select 1");
            sql.addSql("      from ENQ_MAIN");
            sql.addSql("     where ENQ_QUE_SUB.EMN_SID = ENQ_MAIN.EMN_SID");
            sql.addSql("       and EMN_EDATE <= ?");
            sql.addSql("       and EMN_DATA_KBN = ?");
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
     * <p>Create ENQ_QUE_SUB Data Binding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created EnqQueSubModel
     * @throws SQLException SQL実行例外
     */
    private EnqQueSubModel __getEnqQueSubFromRs(ResultSet rs) throws SQLException {
        EnqQueSubModel bean = new EnqQueSubModel();
        bean.setEmnSid(rs.getInt("EMN_SID"));
        bean.setEqmSeq(rs.getInt("EQM_SEQ"));
        bean.setEqsSeq(rs.getInt("EQS_SEQ"));
        bean.setEqsDspSec(rs.getInt("EQS_DSP_SEC"));
        bean.setEqsDspName(rs.getString("EQS_DSP_NAME"));
        bean.setEqsDefTxt(rs.getString("EQS_DEF_TXT"));
        bean.setEqsDefNum(rs.getBigDecimal("EQS_DEF_NUM"));
        bean.setEqsDefDat(UDate.getInstanceTimestamp(rs.getTimestamp("EQS_DEF_DAT")));
        bean.setEqsDef(rs.getString("EQS_DEF"));
        bean.setEqsRngStrNum(rs.getBigDecimal("EQS_RNG_STR_NUM"));
        bean.setEqsRngEndNum(rs.getBigDecimal("EQS_RNG_END_NUM"));
        bean.setEqsRngStrDat(UDate.getInstanceTimestamp(rs.getTimestamp("EQS_RNG_STR_DAT")));
        bean.setEqsRngEndDat(UDate.getInstanceTimestamp(rs.getTimestamp("EQS_RNG_END_DAT")));
        bean.setEqsUnitNum(rs.getString("EQS_UNIT_NUM"));
        bean.setEqsAuid(rs.getInt("EQS_AUID"));
        bean.setEqsAdate(UDate.getInstanceTimestamp(rs.getTimestamp("EQS_ADATE")));
        bean.setEqsEuid(rs.getInt("EQS_EUID"));
        bean.setEqsEdate(UDate.getInstanceTimestamp(rs.getTimestamp("EQS_EDATE")));
        return bean;
    }
}
