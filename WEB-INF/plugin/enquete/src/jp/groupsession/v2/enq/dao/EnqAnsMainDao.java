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
import jp.groupsession.v2.enq.model.EnqAnsMainModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>ENQ_ANS_MAIN Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class EnqAnsMainDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(EnqAnsMainDao.class);

    /**
     * <p>Default Constructor
     */
    public EnqAnsMainDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public EnqAnsMainDao(Connection con) {
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
            sql.addSql("drop table ENQ_ANS_MAIN");

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
            sql.addSql(" create table ENQ_ANS_MAIN (");
            sql.addSql("   EMN_SID bigint not null,");
            sql.addSql("   USR_SID integer not null,");
            sql.addSql("   EAM_STS_KBN integer,");
            sql.addSql("   EQM_ANS_DATE timestamp,");
            sql.addSql("   EAM_AUID integer not null,");
            sql.addSql("   EAM_ADATE timestamp not null,");
            sql.addSql("   EAM_EUID integer not null,");
            sql.addSql("   EAM_EDATE timestamp not null,");
            sql.addSql("   primary key (EMN_SID,USR_SID)");
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
     * <br>[機  能] 未回答または回答済アンケートの件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @param kbn 状態区分
     * @return 件数
     * @throws SQLException SQL実行例外
     */
    public int count(int usrSid, int kbn) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int count = 0;
        con = getCon();

        UDate now = new UDate();
        now.setZeroHhMmSs();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(*) CNT");
            sql.addSql(" from");
            sql.addSql("   ENQ_MAIN");
            sql.addSql(" left join");
            sql.addSql("   ENQ_ANS_MAIN on ENQ_MAIN.EMN_SID = ENQ_ANS_MAIN.EMN_SID");
            sql.addSql(" where ");
            sql.addSql("   EMN_DATA_KBN = ?");
            sql.addSql(" and");
            sql.addSql("   USR_SID = ?");
            sql.addSql(" and");
            sql.addSql("   EAM_STS_KBN = ?");
            sql.addSql(" and");
            sql.addSql("   EMN_OPEN_STR <= ?");
            sql.addSql(" and");
            sql.addSql("   EMN_RES_END >= ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(GSConstEnquete.DATA_KBN_SEND);
            sql.addIntValue(usrSid);
            sql.addIntValue(kbn);
            sql.addDateValue(now);
            sql.addDateValue(now);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
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
     * <p>Insert ENQ_ANS_MAIN Data Binding JavaBean
     * @param bean ENQ_ANS_MAIN Data Binding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(EnqAnsMainModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" ENQ_ANS_MAIN(");
            sql.addSql("   EMN_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   EAM_STS_KBN,");
            sql.addSql("   EQM_ANS_DATE,");
            sql.addSql("   EAM_AUID,");
            sql.addSql("   EAM_ADATE,");
            sql.addSql("   EAM_EUID,");
            sql.addSql("   EAM_EDATE");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(bean.getEmnSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getEamStsKbn());
            sql.addDateValue(bean.getEqmAnsDate());
            sql.addIntValue(bean.getEamAuid());
            sql.addDateValue(bean.getEamAdate());
            sql.addIntValue(bean.getEamEuid());
            sql.addDateValue(bean.getEamEdate());
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
     * <p>Insert ENQ_ANS_MAIN Data Binding JavaBean
     * @param subjectUserList 回答ユーザSIDリスト
     * @param bean ENQ_ANS_MAIN Data Binding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(List<String> subjectUserList, EnqAnsMainModel bean)
            throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        if (subjectUserList == null || subjectUserList.size() <= 0) {
            return;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" ENQ_ANS_MAIN(");
            sql.addSql("   EMN_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   EAM_STS_KBN,");
            sql.addSql("   EQM_ANS_DATE,");
            sql.addSql("   EAM_AUID,");
            sql.addSql("   EAM_ADATE,");
            sql.addSql("   EAM_EUID,");
            sql.addSql("   EAM_EDATE");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());

            for (String subjectUser : subjectUserList) {
                sql.addLongValue(bean.getEmnSid());
                sql.addIntValue(Integer.parseInt(subjectUser));
                sql.addIntValue(bean.getEamStsKbn());
                sql.addDateValue(bean.getEqmAnsDate());
                sql.addIntValue(bean.getEamAuid());
                sql.addDateValue(bean.getEamAdate());
                sql.addIntValue(bean.getEamEuid());
                sql.addDateValue(bean.getEamEdate());
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
     * <p>Update ENQ_ANS_MAIN Data Binding JavaBean
     * @param bean ENQ_ANS_MAIN Data Binding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(EnqAnsMainModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ENQ_ANS_MAIN");
            sql.addSql(" set ");
            sql.addSql("   EAM_STS_KBN=?,");
            sql.addSql("   EQM_ANS_DATE=?,");
            sql.addSql("   EAM_AUID=?,");
            sql.addSql("   EAM_ADATE=?,");
            sql.addSql("   EAM_EUID=?,");
            sql.addSql("   EAM_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   EMN_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getEamStsKbn());
            sql.addDateValue(bean.getEqmAnsDate());
            sql.addIntValue(bean.getEamAuid());
            sql.addDateValue(bean.getEamAdate());
            sql.addIntValue(bean.getEamEuid());
            sql.addDateValue(bean.getEamEdate());
            //where
            sql.addLongValue(bean.getEmnSid());
            sql.addIntValue(bean.getUsrSid());

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
     * <br>[機  能] 回答アンケート更新処理
     * <br>[解  説]
     * <br>[備  考]
     * @param bean ENQ_ANS_MAIN Data Binding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateAnsMain(EnqAnsMainModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ENQ_ANS_MAIN");
            sql.addSql(" set ");
            sql.addSql("   EAM_STS_KBN=?,");
            sql.addSql("   EQM_ANS_DATE=?,");
            sql.addSql("   EAM_EUID=?,");
            sql.addSql("   EAM_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   EMN_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and exists(");
            sql.addSql("   select 1");
            sql.addSql("     from ENQ_MAIN");
            sql.addSql("    where ENQ_MAIN.EMN_SID = ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getEamStsKbn());
            sql.addDateValue(bean.getEqmAnsDate());
            sql.addIntValue(bean.getEamEuid());
            sql.addDateValue(bean.getEamEdate());
            //where
            sql.addLongValue(bean.getEmnSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addLongValue(bean.getEmnSid());

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
     * <p>Select ENQ_ANS_MAIN All Data
     * @return List in ENQ_AMS_MAINModel
     * @throws SQLException SQL実行例外
     */
    public List<EnqAnsMainModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<EnqAnsMainModel> ret = new ArrayList<EnqAnsMainModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   EMN_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   EAM_STS_KBN,");
            sql.addSql("   EQM_ANS_DATE,");
            sql.addSql("   EAM_AUID,");
            sql.addSql("   EAM_ADATE,");
            sql.addSql("   EAM_EUID,");
            sql.addSql("   EAM_EDATE");
            sql.addSql(" from ");
            sql.addSql("   ENQ_ANS_MAIN");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getEnqAmsMainFromRs(rs));
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
     * <p>Select ENQ_ANS_MAIN
     * @param emnSid EMN_SID
     * @param usrSid USR_SID
     * @return ENQ_AMS_MAINModel
     * @throws SQLException SQL実行例外
     */
    public EnqAnsMainModel select(long emnSid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        EnqAnsMainModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   EMN_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   EAM_STS_KBN,");
            sql.addSql("   EQM_ANS_DATE,");
            sql.addSql("   EAM_AUID,");
            sql.addSql("   EAM_ADATE,");
            sql.addSql("   EAM_EUID,");
            sql.addSql("   EAM_EDATE");
            sql.addSql(" from");
            sql.addSql("   ENQ_ANS_MAIN");
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
            if (rs.next()) {
                ret = __getEnqAmsMainFromRs(rs);
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
     * <br>[機  能] 指定した添付ファイルが指定のアンケートのファイルかチェックする
     * <br>[解  説]
     * <br>[備  考]
     * @param enqSid アンケートSID
     * @param binSid バイナリSID
     * @return 1以上：参照可能、0：参照不可
     * @throws SQLException SQL実行例外
     */
    public int countUseTempFromAns(long enqSid, long binSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int count = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select count(*) CNT");
            sql.addSql("   from ENQ_ANS_MAIN");
            sql.addSql("   left join ENQ_MAIN on ENQ_ANS_MAIN.EMN_SID = ENQ_MAIN.EMN_SID");
            sql.addSql("   left join ENQ_QUE_MAIN on ENQ_ANS_MAIN.EMN_SID = ENQ_QUE_MAIN.EMN_SID");
            sql.addSql("  where ENQ_MAIN.EMN_SID = ?");
            sql.addSql("    and (ENQ_MAIN.EMN_ATTACH_ID = ? or ENQ_QUE_MAIN.EQM_ATTACH_ID = ?)");


            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(enqSid);
            sql.addStrValue(String.valueOf(binSid));
            sql.addStrValue(String.valueOf(binSid));

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
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
     * <br>[機  能] アンケート回答者が、 指定した添付ファイルを参照可能かチェックする
     * <br>[解  説]
     * <br>[備  考]
     * @param enqSid アンケートSID
     * @param binSid バイナリSID
     * @param usrSid ユーザSID
     * @return 1以上：参照可能、0：参照不可
     * @throws SQLException SQL実行例外
     */
    public int countUseTempFromAns(long enqSid, long binSid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int count = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select count(*) CNT");
            sql.addSql("   from ENQ_ANS_MAIN");
            sql.addSql("   left join ENQ_MAIN on ENQ_ANS_MAIN.EMN_SID = ENQ_MAIN.EMN_SID");
            sql.addSql("   left join ENQ_QUE_MAIN on ENQ_ANS_MAIN.EMN_SID = ENQ_QUE_MAIN.EMN_SID");
            sql.addSql("  where ENQ_MAIN.EMN_SID = ?");
            if (usrSid > -1) {
                sql.addSql("    and ENQ_ANS_MAIN.USR_SID = ?");
            }
            sql.addSql("    and (ENQ_MAIN.EMN_ATTACH_ID = ? or ENQ_QUE_MAIN.EQM_ATTACH_ID = ?)");


            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(enqSid);
            if (usrSid > -1) {
                sql.addIntValue(usrSid);
            }
            sql.addStrValue(String.valueOf(binSid));
            sql.addStrValue(String.valueOf(binSid));

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
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
     * <br>[機  能] 指定したアンケートの回答情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param emnSid アンケートSID
     * @return アンケートの回答情報
     * @throws SQLException SQL実行時例外
     */
    public List<EnqAnsMainModel> getAnswerList(long emnSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<EnqAnsMainModel> ret = new ArrayList<EnqAnsMainModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   EMN_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   EAM_STS_KBN,");
            sql.addSql("   EQM_ANS_DATE,");
            sql.addSql("   EAM_AUID,");
            sql.addSql("   EAM_ADATE,");
            sql.addSql("   EAM_EUID,");
            sql.addSql("   EAM_EDATE");
            sql.addSql(" from ");
            sql.addSql("   ENQ_ANS_MAIN");
            sql.addSql(" where ");
            sql.addSql("   EMN_SID = ?");
            sql.addLongValue(emnSid);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getEnqAmsMainFromRs(rs));
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
     * <p>Delete ENQ_ANS_MAIN
     * @param emnSid EMN_SID
     * @param usrSid USR_SID
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(long emnSid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   ENQ_ANS_MAIN");
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
     * <p>Delete ENQ_ANS_MAIN
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
            sql.addSql("   ENQ_ANS_MAIN");
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
     * <br>[機  能] アンケート対象者以外の回答情報を削除する
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
            sql.addSql("   ENQ_ANS_MAIN");
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
     * <br>[機  能] 指定したアンケートの回答ユーザをListで取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param emnSid アンケートSID
     * @return アンケートの回答情報
     * @throws SQLException SQL実行時例外
     */
    public List<String> getAnswerUserList(long emnSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<String> ret = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID");
            sql.addSql(" from ");
            sql.addSql("   ENQ_ANS_MAIN");
            sql.addSql(" where ");
            sql.addSql("   EMN_SID = ?");
            sql.addLongValue(emnSid);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getString("USR_SID"));
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
            sql.addSql("   ENQ_ANS_MAIN");
            sql.addSql("  where exists (");
            sql.addSql("    select 1");
            sql.addSql("      from ENQ_MAIN");
            sql.addSql("     where ENQ_ANS_MAIN.EMN_SID = ENQ_MAIN.EMN_SID");
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
     * <p>Create ENQ_ANS_MAIN Data Binding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created EnqAmsMainModel
     * @throws SQLException SQL実行例外
     */
    private EnqAnsMainModel __getEnqAmsMainFromRs(ResultSet rs) throws SQLException {
        EnqAnsMainModel bean = new EnqAnsMainModel();
        bean.setEmnSid(rs.getInt("EMN_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setEamStsKbn(rs.getInt("EAM_STS_KBN"));
        bean.setEqmAnsDate(UDate.getInstanceTimestamp(rs.getTimestamp("EQM_ANS_DATE")));
        bean.setEamAuid(rs.getInt("EAM_AUID"));
        bean.setEamAdate(UDate.getInstanceTimestamp(rs.getTimestamp("EAM_ADATE")));
        bean.setEamEuid(rs.getInt("EAM_EUID"));
        bean.setEamEdate(UDate.getInstanceTimestamp(rs.getTimestamp("EAM_EDATE")));
        return bean;
    }
}
