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
import jp.groupsession.v2.enq.model.EnqAnsSubModel;

/**
 * <p>ENQ_ANS_SUB Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class EnqAnsSubDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(EnqAnsSubDao.class);

    /**
     * <p>Default Constructor
     */
    public EnqAnsSubDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public EnqAnsSubDao(Connection con) {
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
            sql.addSql("drop table ENQ_ANS_SUB");

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
            sql.addSql(" create table ENQ_ANS_SUB (");
            sql.addSql("   EMN_SID bigint not null,");
            sql.addSql("   USR_SID integer not null,");
            sql.addSql("   EQM_SEQ integer not null,");
            sql.addSql("   EQS_SEQ integer not null,");
            sql.addSql("   EAS_ANS_TXT varchar(1000),");
            sql.addSql("   EAS_ANS_NUM decimal(15, 5),");
            sql.addSql("   EAS_ANS_DAT Date,");
            sql.addSql("   EAS_ANS varchar(1000),");
            sql.addSql("   EQM_AUID integer not null,");
            sql.addSql("   EQM_ADATE timestamp not null,");
            sql.addSql("   EQM_EUID integer not null,");
            sql.addSql("   EQM_EDATE timestamp not null,");
            sql.addSql("   primary key (EMN_SID,USR_SID,EQM_SEQ,EQS_SEQ)");
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
     * <p>Insert ENQ_ANS_SUB Data Binding JavaBean
     * @param bean ENQ_ANS_SUB Data Binding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(EnqAnsSubModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" ENQ_ANS_SUB(");
            sql.addSql("   EMN_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   EQM_SEQ,");
            sql.addSql("   EQS_SEQ,");
            sql.addSql("   EAS_ANS_TXT,");
            sql.addSql("   EAS_ANS_NUM,");
            sql.addSql("   EAS_ANS_DAT,");
            sql.addSql("   EAS_ANS,");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(bean.getEmnSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getEqmSeq());
            sql.addIntValue(bean.getEqsSeq());
            sql.addStrValue(bean.getEasAnsTxt());
            sql.addDecimalValue(bean.getEasAnsNum());
            sql.addDateValue(bean.getEasAnsDat());
            sql.addStrValue(bean.getEasAns());
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
     * <p>Update ENQ_ANS_SUB Data Binding JavaBean
     * @param bean ENQ_ANS_SUB Data Binding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(EnqAnsSubModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ENQ_ANS_SUB");
            sql.addSql(" set ");
            sql.addSql("   EAS_ANS_TXT=?,");
            sql.addSql("   EAS_ANS_NUM=?,");
            sql.addSql("   EAS_ANS_DAT=?,");
            sql.addSql("   EAS_ANS=?,");
            sql.addSql("   EQM_AUID=?,");
            sql.addSql("   EQM_ADATE=?,");
            sql.addSql("   EQM_EUID=?,");
            sql.addSql("   EQM_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   EMN_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   EQM_SEQ=?");
            sql.addSql(" and");
            sql.addSql("   EQS_SEQ=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getEasAnsTxt());
            sql.addDecimalValue(bean.getEasAnsNum());
            sql.addDateValue(bean.getEasAnsDat());
            sql.addStrValue(bean.getEasAns());
            sql.addIntValue(bean.getEqmAuid());
            sql.addDateValue(bean.getEqmAdate());
            sql.addIntValue(bean.getEqmEuid());
            sql.addDateValue(bean.getEqmEdate());
            //where
            sql.addLongValue(bean.getEmnSid());
            sql.addIntValue(bean.getUsrSid());
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
     * <br>[機  能] 回答アンケート登録処理
     * <br>[解  説]
     * <br>[備  考]
     * @param queKbn 設問区分
     * @param bean ENQ_ANS_SUB Data Binding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insertAnsSub(int queKbn, EnqAnsSubModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" ENQ_ANS_SUB(");
            sql.addSql("   EMN_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   EQM_SEQ,");
            sql.addSql("   EQS_SEQ,");
            if (queKbn == GSConstEnquete.SYURUI_TEXT || queKbn == GSConstEnquete.SYURUI_TEXTAREA) {
                sql.addSql("   EAS_ANS_TXT,");
            } else if (queKbn == GSConstEnquete.SYURUI_DAY) {
                sql.addSql("   EAS_ANS_DAT,");
            } else if (queKbn == GSConstEnquete.SYURUI_INTEGER
                    && !StringUtil.isNullZeroString(bean.getEasAns())) {
                sql.addSql("   EAS_ANS_NUM,");
            } else if (queKbn == GSConstEnquete.SYURUI_SINGLE
                    || queKbn == GSConstEnquete.SYURUI_MULTIPLE) {
                sql.addSql("   EAS_ANS_NUM,");
            }

            sql.addSql("   EAS_ANS,");
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
            if (queKbn == GSConstEnquete.SYURUI_TEXT || queKbn == GSConstEnquete.SYURUI_TEXTAREA) {
                sql.addSql("   ?,");
            } else if (queKbn == GSConstEnquete.SYURUI_DAY) {
                sql.addSql("   ?,");
            } else if (queKbn == GSConstEnquete.SYURUI_INTEGER
                    && !StringUtil.isNullZeroString(bean.getEasAns())) {
                sql.addSql("   ?,");
            } else if (queKbn == GSConstEnquete.SYURUI_SINGLE
                    || queKbn == GSConstEnquete.SYURUI_MULTIPLE) {
                sql.addSql("   ?,");
            }
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(bean.getEmnSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getEqmSeq());
            sql.addIntValue(bean.getEqsSeq());
            if (queKbn == GSConstEnquete.SYURUI_TEXT || queKbn == GSConstEnquete.SYURUI_TEXTAREA) {
                sql.addStrValue(bean.getEasAnsTxt());
            } else if (queKbn == GSConstEnquete.SYURUI_DAY) {
                sql.addDateValue(bean.getEasAnsDat());
            } else if (queKbn == GSConstEnquete.SYURUI_INTEGER
                    && !StringUtil.isNullZeroString(bean.getEasAns())) {
                sql.addDecimalValue(bean.getEasAnsNum());
            } else if (queKbn == GSConstEnquete.SYURUI_SINGLE
                    || queKbn == GSConstEnquete.SYURUI_MULTIPLE) {
                sql.addDecimalValue(bean.getEasAnsNum());
            }
            sql.addStrValue(bean.getEasAns());
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
     * <p>Select ENQ_ANS_SUB All Data
     * @return List in ENQ_AMS_SUBModel
     * @throws SQLException SQL実行例外
     */
    public List<EnqAnsSubModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<EnqAnsSubModel> ret = new ArrayList<EnqAnsSubModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   EMN_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   EQM_SEQ,");
            sql.addSql("   EQS_SEQ,");
            sql.addSql("   EAS_ANS_TXT,");
            sql.addSql("   EAS_ANS_NUM,");
            sql.addSql("   EAS_ANS_DAT,");
            sql.addSql("   EAS_ANS,");
            sql.addSql("   EQM_AUID,");
            sql.addSql("   EQM_ADATE,");
            sql.addSql("   EQM_EUID,");
            sql.addSql("   EQM_EDATE");
            sql.addSql(" from ");
            sql.addSql("   ENQ_ANS_SUB");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getEnqAmsSubFromRs(rs));
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
     * <p>Select ENQ_ANS_SUB
     * @param emnSid EMN_SID
     * @param usrSid USR_SID
     * @param eqmSeq EQM_SEQ
     * @param eqsSeq EQS_SEQ
     * @return ENQ_AMS_SUBModel
     * @throws SQLException SQL実行例外
     */
    public EnqAnsSubModel select(long emnSid, int usrSid, int eqmSeq, int eqsSeq)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        EnqAnsSubModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   EMN_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   EQM_SEQ,");
            sql.addSql("   EQS_SEQ,");
            sql.addSql("   EAS_ANS_TXT,");
            sql.addSql("   EAS_ANS_NUM,");
            sql.addSql("   EAS_ANS_DAT,");
            sql.addSql("   EAS_ANS,");
            sql.addSql("   EQM_AUID,");
            sql.addSql("   EQM_ADATE,");
            sql.addSql("   EQM_EUID,");
            sql.addSql("   EQM_EDATE");
            sql.addSql(" from");
            sql.addSql("   ENQ_ANS_SUB");
            sql.addSql(" where ");
            sql.addSql("   EMN_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   EQM_SEQ=?");
            sql.addSql(" and");
            sql.addSql("   EQS_SEQ=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(emnSid);
            sql.addIntValue(usrSid);
            sql.addIntValue(eqmSeq);
            sql.addIntValue(eqsSeq);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getEnqAmsSubFromRs(rs);
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
     * <p>Select ENQ_ANS_SUB
     * @param emnSid EMN_SID
     * @param usrSid USR_SID
     * @return ENQ_AMS_SUBModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<EnqAnsSubModel> select(long emnSid, int usrSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<EnqAnsSubModel> ret = new ArrayList<EnqAnsSubModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   EMN_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   EQM_SEQ,");
            sql.addSql("   EQS_SEQ,");
            sql.addSql("   EAS_ANS_TXT,");
            sql.addSql("   EAS_ANS_NUM,");
            sql.addSql("   EAS_ANS_DAT,");
            sql.addSql("   EAS_ANS,");
            sql.addSql("   EQM_AUID,");
            sql.addSql("   EQM_ADATE,");
            sql.addSql("   EQM_EUID,");
            sql.addSql("   EQM_EDATE");
            sql.addSql(" from");
            sql.addSql("   ENQ_ANS_SUB");
            sql.addSql(" where ");
            sql.addSql("   EMN_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(emnSid);
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getEnqAmsSubFromRs(rs));
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
     * 指定したアンケート基本情報に関連する回答_サブ情報の件数を取得する
     * @param emnSidList EMN_SID
     * @return 件数
     * @throws SQLException SQL実行例外
     */
    public long getTotalDescSize(List<Long> emnSidList) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        long ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   sum(octet_length(EAS_ANS_TXT)) as EAS_ANS_TXT,");
            sql.addSql("   sum(octet_length(EAS_ANS)) as EAS_ANS");
            sql.addSql(" from");
            sql.addSql("   ENQ_ANS_SUB");
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
                ret = rs.getLong("EAS_ANS_TXT");
                ret += rs.getLong("EAS_ANS");
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
     * <p>Delete ENQ_ANS_SUB
     * @param emnSid EMN_SID
     * @param usrSid USR_SID
     * @param eqmSeq EQM_SEQ
     * @param eqsSeq EQS_SEQ
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(long emnSid, int usrSid, int eqmSeq, int eqsSeq) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   ENQ_ANS_SUB");
            sql.addSql(" where ");
            sql.addSql("   EMN_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   EQM_SEQ=?");
            sql.addSql(" and");
            sql.addSql("   EQS_SEQ=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(emnSid);
            sql.addIntValue(usrSid);
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
     * <br>[機  能] 回答_サブ情報を削除する
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
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   ENQ_ANS_SUB");
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
     * <br>[機  能] 回答アンケート情報の削除
     * <br>[解  説]
     * <br>[備  考]
     * @param emnSid EMN_SID
     * @param usrSid USR_SID
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int deleteAnsSub(long emnSid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   ENQ_ANS_SUB");
            sql.addSql(" where ");
            sql.addSql("   EMN_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(emnSid);
            sql.addIntValue(usrSid);

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
            sql.addSql("   ENQ_ANS_SUB");
            sql.addSql("  where exists (");
            sql.addSql("    select 1");
            sql.addSql("      from ENQ_MAIN");
            sql.addSql("     where ENQ_ANS_SUB.EMN_SID = ENQ_MAIN.EMN_SID");
            sql.addSql("     and EMN_OPEN_END_KBN = ?");
            sql.addSql("     and EMN_OPEN_END <= cast(? as date)");
            sql.addSql("  )");
            sql.addIntValue(GSConstEnquete.EMN_OPEN_END_KBN_SPECIFIED);
            sql.addDateValue(date);

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
     * <br>[機  能] アンケート対象者以外の回答_サブ情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param emnSid EMN_SID
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int deleteNonSubject(long emnSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   ENQ_ANS_SUB");
            sql.addSql(" where ");
            sql.addSql("   EMN_SID=?");
            sql.addSql(" and ");
            sql.addSql("   USR_SID not in (");
            sql.addSql("     select USR_SID from ENQ_SUBJECT");
            sql.addSql("     where EMN_SID = ?");
            sql.addSql("     and USR_SID >= 0");
            sql.addSql("   )");
            sql.addSql(" and ");
            sql.addSql("   USR_SID not in (");
            sql.addSql("     select");
            sql.addSql("       CMN_BELONGM.USR_SID");
            sql.addSql("     from");
            sql.addSql("       CMN_BELONGM,");
            sql.addSql("       ENQ_SUBJECT");
            sql.addSql("     where");
            sql.addSql("       ENQ_SUBJECT.EMN_SID = ?");
            sql.addSql("     and");
            sql.addSql("       ENQ_SUBJECT.GRP_SID >= 0");
            sql.addSql("     and");
            sql.addSql("       ENQ_SUBJECT.GRP_SID = CMN_BELONGM.GRP_SID");
            sql.addSql("   )");
            sql.addLongValue(emnSid);
            sql.addLongValue(emnSid);
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
     * <p>Create ENQ_ANS_SUB Data Binding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created EnqAmsSubModel
     * @throws SQLException SQL実行例外
     */
    private EnqAnsSubModel __getEnqAmsSubFromRs(ResultSet rs) throws SQLException {
        EnqAnsSubModel bean = new EnqAnsSubModel();
        bean.setEmnSid(rs.getInt("EMN_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setEqmSeq(rs.getInt("EQM_SEQ"));
        bean.setEqsSeq(rs.getInt("EQS_SEQ"));
        bean.setEasAnsTxt(rs.getString("EAS_ANS_TXT"));
        bean.setEasAnsNum(rs.getBigDecimal("EAS_ANS_NUM"));
        bean.setEasAnsDat(UDate.getInstanceTimestamp(rs.getTimestamp("EAS_ANS_DAT")));
        bean.setEasAns(rs.getString("EAS_ANS"));
        bean.setEqmAuid(rs.getInt("EQM_AUID"));
        bean.setEqmAdate(UDate.getInstanceTimestamp(rs.getTimestamp("EQM_ADATE")));
        bean.setEqmEuid(rs.getInt("EQM_EUID"));
        bean.setEqmEdate(UDate.getInstanceTimestamp(rs.getTimestamp("EQM_EDATE")));
        return bean;
    }
}
