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
import jp.groupsession.v2.enq.model.EnqMainModel;

/**
 * <p>ENQ_MAIN Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class EnqMainDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(EnqMainDao.class);

    /**
     * <p>Default Constructor
     */
    public EnqMainDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public EnqMainDao(Connection con) {
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
            sql.addSql("drop table ENQ_MAIN");

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
            sql.addSql(" create table ENQ_MAIN (");
            sql.addSql("   EMN_SID bigint not null,");
            sql.addSql("   EMN_DATA_KBN integer not null,");
            sql.addSql("   ETP_SID integer,");
            sql.addSql("   EMN_TITLE varchar(100),");
            sql.addSql("   EMN_PRI_KBN integer,");
            sql.addSql("   EMN_DESC text,");
            sql.addSql("   EMN_DESC_PLAIN text,");
            sql.addSql("   EMN_ATTACH_KBN integer,");
            sql.addSql("   EMN_ATTACH_ID varchar(100),");
            sql.addSql("   EMN_ATTACH_NAME varchar(100),");
            sql.addSql("   EMN_ATTACH_POS integer,");
            sql.addSql("   EMN_OPEN_STR Date,");
            sql.addSql("   EMN_OPEN_END Date,");
            sql.addSql("   EMN_OPEN_END_KBN integer,");
            sql.addSql("   EMN_RES_END Date,");
            sql.addSql("   EMN_ANS_PUB_STR Date,");
            sql.addSql("   EMN_ANONY integer,");
            sql.addSql("   EMN_ANS_OPEN integer,");
            sql.addSql("   EMN_SEND_GRP bigint,");
            sql.addSql("   EMN_SEND_USR bigint,");
            sql.addSql("   EMN_SEND_NAME varchar(100),");
            sql.addSql("   EMN_TARGET integer,");
            sql.addSql("   EMN_QUESEC_TYPE integer not null,");
            sql.addSql("   EMN_AUID integer not null,");
            sql.addSql("   EMN_ADATE timestamp not null,");
            sql.addSql("   EMN_EUID integer not null,");
            sql.addSql("   EMN_EDATE timestamp not null,");
            sql.addSql("   primary key (EMN_SID)");
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
     * <p>Insert ENQ_MAIN Data Binding JavaBean
     * @param bean ENQ_MAIN Data Binding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(EnqMainModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" ENQ_MAIN(");
            sql.addSql("   EMN_SID,");
            sql.addSql("   EMN_DATA_KBN,");
            sql.addSql("   ETP_SID,");
            sql.addSql("   EMN_TITLE,");
            sql.addSql("   EMN_PRI_KBN,");
            sql.addSql("   EMN_DESC,");
            sql.addSql("   EMN_DESC_PLAIN,");
            sql.addSql("   EMN_ATTACH_KBN,");
            sql.addSql("   EMN_ATTACH_ID,");
            sql.addSql("   EMN_ATTACH_NAME,");
            sql.addSql("   EMN_ATTACH_POS,");
            sql.addSql("   EMN_OPEN_STR,");
            sql.addSql("   EMN_OPEN_END,");
            sql.addSql("   EMN_OPEN_END_KBN,");
            sql.addSql("   EMN_RES_END,");
            sql.addSql("   EMN_ANS_PUB_STR,");
            sql.addSql("   EMN_ANONY,");
            sql.addSql("   EMN_ANS_OPEN,");
            sql.addSql("   EMN_SEND_GRP,");
            sql.addSql("   EMN_SEND_USR,");
            sql.addSql("   EMN_SEND_NAME,");
            sql.addSql("   EMN_TARGET,");
            sql.addSql("   EMN_QUESEC_TYPE,");
            sql.addSql("   EMN_AUID,");
            sql.addSql("   EMN_ADATE,");
            sql.addSql("   EMN_EUID,");
            sql.addSql("   EMN_EDATE");
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
            sql.addIntValue(bean.getEmnDataKbn());
            sql.addIntValue(bean.getEtpSid());
            sql.addStrValue(bean.getEmnTitle());
            sql.addIntValue(bean.getEmnPriKbn());
            sql.addStrValue(bean.getEmnDesc());
            sql.addStrValue(bean.getEmnDescPlain());
            sql.addIntValue(bean.getEmnAttachKbn());
            sql.addStrValue(bean.getEmnAttachId());
            sql.addStrValue(bean.getEmnAttachName());
            sql.addIntValue(bean.getEmnAttachPos());
            sql.addDateValue(bean.getEmnOpenStr());
            sql.addDateValue(bean.getEmnOpenEnd());
            sql.addIntValue(bean.getEmnOpenEndKbn());
            sql.addDateValue(bean.getEmnResEnd());
            sql.addDateValue(bean.getEmnAnsPubStr());
            sql.addIntValue(bean.getEmnAnony());
            sql.addIntValue(bean.getEmnAnsOpen());
            sql.addLongValue(bean.getEmnSendGrp());
            sql.addLongValue(bean.getEmnSendUsr());
            sql.addStrValue(bean.getEmnSendName());
            sql.addIntValue(bean.getEmnTarget());
            sql.addIntValue(bean.getEmnQuesecType());
            sql.addIntValue(bean.getEmnAuid());
            sql.addDateValue(bean.getEmnAdate());
            sql.addIntValue(bean.getEmnEuid());
            sql.addDateValue(bean.getEmnEdate());
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
     * <p>Update ENQ_MAIN Data Binding JavaBean
     * @param bean ENQ_MAIN Data Binding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(EnqMainModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ENQ_MAIN");
            sql.addSql(" set ");
            sql.addSql("   EMN_DATA_KBN=?,");
            sql.addSql("   ETP_SID=?,");
            sql.addSql("   EMN_TITLE=?,");
            sql.addSql("   EMN_PRI_KBN=?,");
            sql.addSql("   EMN_DESC=?,");
            sql.addSql("   EMN_DESC_PLAIN=?,");
            sql.addSql("   EMN_ATTACH_KBN=?,");
            sql.addSql("   EMN_ATTACH_ID=?,");
            sql.addSql("   EMN_ATTACH_NAME=?,");
            sql.addSql("   EMN_ATTACH_POS=?,");
            sql.addSql("   EMN_OPEN_STR=?,");
            sql.addSql("   EMN_OPEN_END=?,");
            sql.addSql("   EMN_OPEN_END_KBN=?,");
            sql.addSql("   EMN_RES_END=?,");
            sql.addSql("   EMN_ANS_PUB_STR=?,");
            sql.addSql("   EMN_ANONY=?,");
            sql.addSql("   EMN_ANS_OPEN=?,");
            sql.addSql("   EMN_SEND_GRP=?,");
            sql.addSql("   EMN_SEND_USR=?,");
            sql.addSql("   EMN_SEND_NAME=?,");
            sql.addSql("   EMN_TARGET=?,");
            sql.addSql("   EMN_QUESEC_TYPE=?,");
//            sql.addSql("   EMN_AUID=?,");
//            sql.addSql("   EMN_ADATE=?,");
            sql.addSql("   EMN_EUID=?,");
            sql.addSql("   EMN_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   EMN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getEmnDataKbn());
            sql.addIntValue(bean.getEtpSid());
            sql.addStrValue(bean.getEmnTitle());
            sql.addIntValue(bean.getEmnPriKbn());
            sql.addStrValue(bean.getEmnDesc());
            sql.addStrValue(bean.getEmnDescPlain());
            sql.addIntValue(bean.getEmnAttachKbn());
            sql.addStrValue(bean.getEmnAttachId());
            sql.addStrValue(bean.getEmnAttachName());
            sql.addIntValue(bean.getEmnAttachPos());
            sql.addDateValue(bean.getEmnOpenStr());
            sql.addDateValue(bean.getEmnOpenEnd());
            sql.addIntValue(bean.getEmnOpenEndKbn());
            sql.addDateValue(bean.getEmnResEnd());
            sql.addDateValue(bean.getEmnAnsPubStr());
            sql.addIntValue(bean.getEmnAnony());
            sql.addIntValue(bean.getEmnAnsOpen());
            sql.addLongValue(bean.getEmnSendGrp());
            sql.addLongValue(bean.getEmnSendUsr());
            sql.addStrValue(bean.getEmnSendName());
            sql.addIntValue(bean.getEmnTarget());
            sql.addIntValue(bean.getEmnQuesecType());
//            sql.addIntValue(bean.getEmnAuid());
//            sql.addDateValue(bean.getEmnAdate());
            sql.addIntValue(bean.getEmnEuid());
            sql.addDateValue(bean.getEmnEdate());
            //where
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
     * <br>[機  能] 指定のアンケートが、編集可能かどうかチェックする
     * <br>[解  説]
     * <br>[備  考]
     * @param emnSid アンケートSID
     * @param usrSid ユーザSID
     * @return 0:編集不可、1:編集可能
     * @throws SQLException SQL実行例外
     */
    public int countEditEnq(long emnSid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(*) CNT");
            sql.addSql(" from");
            sql.addSql("   ENQ_MAIN");
            sql.addSql(" where");
            sql.addSql("   EMN_SID = ?");
            sql.addSql(" and");
            sql.addSql("   EMN_EUID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(emnSid);
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getInt("CNT");
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
     * <br>[機  能] 指定のアンケートが、編集可能かどうかチェックする
     * <br>[解  説]
     * <br>[備  考]
     * @param emnSid アンケートSID
     * @param dataKbn データ区分
     * @return 0:編集不可、1:編集可能
     * @throws SQLException SQL実行例外
     */
    public int countExistEnq(long emnSid, int dataKbn) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(*) CNT");
            sql.addSql(" from");
            sql.addSql("   ENQ_MAIN");
            sql.addSql(" where");
            sql.addSql("   EMN_SID = ?");
            sql.addSql(" and");
            sql.addSql("   EMN_DATA_KBN = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(emnSid);
            sql.addIntValue(dataKbn);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getInt("CNT");
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
     * <p>Select ENQ_MAIN All Data
     * @return List in ENQ_MAINModel
     * @throws SQLException SQL実行例外
     */
    public List<EnqMainModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<EnqMainModel> ret = new ArrayList<EnqMainModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   EMN_SID,");
            sql.addSql("   EMN_DATA_KBN,");
            sql.addSql("   ETP_SID,");
            sql.addSql("   EMN_TITLE,");
            sql.addSql("   EMN_PRI_KBN,");
            sql.addSql("   EMN_DESC,");
            sql.addSql("   EMN_DESC_PLAIN,");
            sql.addSql("   EMN_ATTACH_KBN,");
            sql.addSql("   EMN_ATTACH_ID,");
            sql.addSql("   EMN_ATTACH_NAME,");
            sql.addSql("   EMN_ATTACH_POS,");
            sql.addSql("   EMN_OPEN_STR,");
            sql.addSql("   EMN_OPEN_END,");
            sql.addSql("   EMN_OPEN_END_KBN,");
            sql.addSql("   EMN_RES_END,");
            sql.addSql("   EMN_ANS_PUB_STR,");
            sql.addSql("   EMN_ANONY,");
            sql.addSql("   EMN_ANS_OPEN,");
            sql.addSql("   EMN_SEND_GRP,");
            sql.addSql("   EMN_SEND_USR,");
            sql.addSql("   EMN_SEND_NAME,");
            sql.addSql("   EMN_TARGET,");
            sql.addSql("   EMN_QUESEC_TYPE,");
            sql.addSql("   EMN_AUID,");
            sql.addSql("   EMN_ADATE,");
            sql.addSql("   EMN_EUID,");
            sql.addSql("   EMN_EDATE");
            sql.addSql(" from ");
            sql.addSql("   ENQ_MAIN");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getEnqMainFromRs(rs));
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
     * <p>Select ENQ_MAIN
     * @param emnSid EMN_SID
     * @return ENQ_MAINModel
     * @throws SQLException SQL実行例外
     */
    public EnqMainModel select(long emnSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        EnqMainModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   EMN_SID,");
            sql.addSql("   EMN_DATA_KBN,");
            sql.addSql("   ETP_SID,");
            sql.addSql("   EMN_TITLE,");
            sql.addSql("   EMN_PRI_KBN,");
            sql.addSql("   EMN_DESC,");
            sql.addSql("   EMN_DESC_PLAIN,");
            sql.addSql("   EMN_ATTACH_KBN,");
            sql.addSql("   EMN_ATTACH_ID,");
            sql.addSql("   EMN_ATTACH_NAME,");
            sql.addSql("   EMN_ATTACH_POS,");
            sql.addSql("   EMN_OPEN_STR,");
            sql.addSql("   EMN_OPEN_END,");
            sql.addSql("   EMN_OPEN_END_KBN,");
            sql.addSql("   EMN_RES_END,");
            sql.addSql("   EMN_ANS_PUB_STR,");
            sql.addSql("   EMN_ANONY,");
            sql.addSql("   EMN_ANS_OPEN,");
            sql.addSql("   EMN_SEND_GRP,");
            sql.addSql("   EMN_SEND_USR,");
            sql.addSql("   EMN_SEND_NAME,");
            sql.addSql("   EMN_TARGET,");
            sql.addSql("   EMN_QUESEC_TYPE,");
            sql.addSql("   EMN_AUID,");
            sql.addSql("   EMN_ADATE,");
            sql.addSql("   EMN_EUID,");
            sql.addSql("   EMN_EDATE");
            sql.addSql(" from");
            sql.addSql("   ENQ_MAIN");
            sql.addSql(" where ");
            sql.addSql("   EMN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(emnSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getEnqMainFromRs(rs);
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
     * <p>Delete ENQ_MAIN
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
            sql.addSql("   ENQ_MAIN");
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
            sql.addSql(" delete from ");
            sql.addSql("   ENQ_MAIN");
            sql.addSql(" where EMN_OPEN_END <= cast(? as date)");
            sql.addSql(" and EMN_OPEN_END_KBN = ?");
            sql.addSql(" and EMN_DATA_KBN = ?");
            sql.addDateValue(date);
            sql.addIntValue(GSConstEnquete.EMN_OPEN_END_KBN_SPECIFIED);
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
     * <br>[機  能] 発信フォルダ手動削除対象となるアンケート基本情報のアンケートSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param date 年月
     * @return アンケートSID
     * @throws SQLException SQL実行例外
     */
    public List<Long> getDeleteSendEnqSid(UDate date) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        List<Long> ret = new ArrayList<Long>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select EMN_SID from ");
            sql.addSql("   ENQ_MAIN");
            sql.addSql(" where EMN_OPEN_END <= cast(? as date)");
            sql.addSql(" and EMN_OPEN_END_KBN = ?");
            sql.addSql(" and EMN_DATA_KBN = ?");
            sql.addDateValue(date);
            sql.addIntValue(GSConstEnquete.EMN_OPEN_END_KBN_SPECIFIED);
            sql.addIntValue(GSConstEnquete.DATA_KBN_SEND);

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getLong("EMN_SID"));
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
     * <br>[機  能] 草稿フォルダ手動削除対象となるアンケート基本情報のアンケートSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param date 年月
     * @return アンケートSID
     * @throws SQLException SQL実行例外
     */
    public List<Long> getDeleteDraftEnqSid(UDate date) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        List<Long> ret = new ArrayList<Long>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select EMN_SID from ");
            sql.addSql("   ENQ_MAIN");
            sql.addSql(" where EMN_EDATE <= ?");
            sql.addSql(" and EMN_DATA_KBN = ?");
            sql.addDateValue(date);
            sql.addIntValue(GSConstEnquete.DATA_KBN_DRAFT);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getLong("EMN_SID"));
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
     * <br>[機  能] 草稿フォルダ手動削除処理
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
            sql.addSql(" delete from ");
            sql.addSql("   ENQ_MAIN");
            sql.addSql(" where EMN_EDATE <= ?");
            sql.addSql("   and EMN_DATA_KBN = ?");

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
     * <br>[備  考] アンケート_基本情報の物理削除前に実行すること
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
            sql.addSql(" select EMN_ATTACH_ID from ENQ_MAIN");
            sql.addSql(" where EMN_OPEN_END_KBN = ?");
            sql.addSql(" and EMN_OPEN_END <= ?");
            sql.addSql(" and EMN_DATA_KBN = ?");
            sql.addSql(" and EMN_ATTACH_KBN in (?,");
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
                String binSid = rs.getString("EMN_ATTACH_ID");
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
     * <br>[備  考] アンケート_基本情報の物理削除前に実行すること
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
            sql.addSql(" select EMN_ATTACH_ID from ENQ_MAIN");
            sql.addSql(" where EMN_EDATE <= ?");
            sql.addSql(" and EMN_DATA_KBN = ?");
            sql.addSql(" and EMN_ATTACH_KBN in (?,");
            sql.addSql("          ?)");

            UDate eDate = date.cloneUDate();
            eDate.setMaxHhMmSs();
            sql.addDateValue(eDate);
            sql.addIntValue(GSConstEnquete.DATA_KBN_DRAFT);
            sql.addIntValue(GSConstEnquete.TEMP_IMAGE);
            sql.addIntValue(GSConstEnquete.TEMP_FILE);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String binSid = rs.getString("EMN_ATTACH_ID");
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
     * <br>[機  能] 指定したアンケート_基本情報に関連するバイナリー情報の論理削除を行う
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
            sql.addSql(" select EMN_ATTACH_ID from ENQ_MAIN");
            sql.addSql(" where EMN_SID = ? ");
            sql.addSql(" and");
            sql.addSql("       (EMN_ATTACH_KBN = ? ");
            sql.addSql("       or EMN_ATTACH_KBN = ?) ");

            sql.addLongValue(emnSid);
            sql.addIntValue(GSConstEnquete.EMN_ATTACH_KBN_IMAGE);
            sql.addIntValue(GSConstEnquete.EMN_ATTACH_KBN_FILE);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String binSid = rs.getString("EMN_ATTACH_ID");
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
     * <br>[機  能] 匿名フラグを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param emnSid アンケートSID
     * @return 匿名フラグ
     * @throws SQLException SQL実行時例外
     */
    public int getAnony(long emnSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int anony = GSConstEnquete.EMN_ANONNY_NON;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   EMN_ANONY");
            sql.addSql(" from");
            sql.addSql("   ENQ_MAIN");
            sql.addSql(" where ");
            sql.addSql("   EMN_SID=?");
            sql.addLongValue(emnSid);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                anony = rs.getInt("EMN_ANONY");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return anony;
    }

    /**
     * <br>[機  能] 指定したユーザが作成したアンケートのSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param emnDataKbn データ区分
     * @return アンケートSID
     * @throws SQLException SQL実行時例外
     */
    public List<Long> getEnqSidList(int userSid, int emnDataKbn) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Long> enqSidList = new ArrayList<Long>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   EMN_SID");
            sql.addSql(" from");
            sql.addSql("   ENQ_MAIN");
            sql.addSql(" where ");
            sql.addSql("   EMN_AUID=?");
            sql.addSql(" and ");
            sql.addSql("   EMN_DATA_KBN=?");
            sql.addIntValue(userSid);
            sql.addIntValue(emnDataKbn);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                enqSidList.add(rs.getLong("EMN_SID"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return enqSidList;
    }

    /**
     * <br>[機  能] 指定したアンケート基本情報の「説明」サイズ合計を取得する
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
            sql.addSql("   sum(octet_length(EMN_TITLE)) as EMN_TITLE,");
            sql.addSql("   sum(octet_length(EMN_DESC)) as EMN_DESC,");
            sql.addSql("   sum(octet_length(EMN_DESC_PLAIN)) as EMN_DESC_PLAIN,");
            sql.addSql("   sum(octet_length(EMN_SEND_NAME)) as EMN_SEND_NAME");
            sql.addSql(" from");
            sql.addSql("   ENQ_MAIN");
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
                descSize = rs.getLong("EMN_TITLE");
                descSize += rs.getLong("EMN_DESC");
                descSize += rs.getLong("EMN_DESC_PLAIN");
                descSize += rs.getLong("EMN_SEND_NAME");
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
     * <br>[機  能] 指定したアンケート基本情報の添付ファイルサイズ合計を取得する
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
            sql.addSql("   ENQ_MAIN,");
            sql.addSql("   CMN_BINF");
            sql.addSql(" where");
            sql.addSql("   (");
            sql.addSql("     EMN_ATTACH_KBN = ?");
            sql.addSql("   or");
            sql.addSql("     EMN_ATTACH_KBN = ?");
            sql.addSql("   )");
            sql.addIntValue(GSConstEnquete.EMN_ATTACH_KBN_IMAGE);
            sql.addIntValue(GSConstEnquete.EMN_ATTACH_KBN_FILE);

            sql.addSql(" and");
            sql.addSql("   ENQ_MAIN.EMN_ATTACH_ID is not null");
            sql.addSql(" and");
            sql.addSql("   cast(ENQ_MAIN.EMN_ATTACH_ID as bigint) = CMN_BINF.BIN_SID");
            sql.addSql(" and");
            sql.addSql("   ENQ_MAIN.EMN_SID in (");

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
     * <p>Create ENQ_MAIN Data Binding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created EnqMainModel
     * @throws SQLException SQL実行例外
     */
    private EnqMainModel __getEnqMainFromRs(ResultSet rs) throws SQLException {
        EnqMainModel bean = new EnqMainModel();
        bean.setEmnSid(rs.getInt("EMN_SID"));
        bean.setEmnDataKbn(rs.getInt("EMN_DATA_KBN"));
        bean.setEtpSid(rs.getInt("ETP_SID"));
        bean.setEmnTitle(rs.getString("EMN_TITLE"));
        bean.setEmnPriKbn(rs.getInt("EMN_PRI_KBN"));
        bean.setEmnDesc(rs.getString("EMN_DESC"));
        bean.setEmnDescPlain(rs.getString("EMN_DESC_PLAIN"));
        bean.setEmnAttachKbn(rs.getInt("EMN_ATTACH_KBN"));
        bean.setEmnAttachId(rs.getString("EMN_ATTACH_ID"));
        bean.setEmnAttachName(rs.getString("EMN_ATTACH_NAME"));
        bean.setEmnAttachPos(rs.getInt("EMN_ATTACH_POS"));
        bean.setEmnOpenStr(UDate.getInstanceTimestamp(rs.getTimestamp("EMN_OPEN_STR")));
        bean.setEmnOpenEnd(UDate.getInstanceTimestamp(rs.getTimestamp("EMN_OPEN_END")));
        bean.setEmnOpenEndKbn(rs.getInt("EMN_OPEN_END_KBN"));
        bean.setEmnResEnd(UDate.getInstanceTimestamp(rs.getTimestamp("EMN_RES_END")));
        bean.setEmnAnsPubStr(UDate.getInstanceTimestamp(rs.getTimestamp("EMN_ANS_PUB_STR")));
        bean.setEmnAnony(rs.getInt("EMN_ANONY"));
        bean.setEmnAnsOpen(rs.getInt("EMN_ANS_OPEN"));
        bean.setEmnSendGrp(rs.getInt("EMN_SEND_GRP"));
        bean.setEmnSendUsr(rs.getInt("EMN_SEND_USR"));
        bean.setEmnSendName(rs.getString("EMN_SEND_NAME"));
        bean.setEmnTarget(rs.getInt("EMN_TARGET"));
        bean.setEmnQuesecType(rs.getInt("EMN_QUESEC_TYPE"));
        bean.setEmnAuid(rs.getInt("EMN_AUID"));
        bean.setEmnAdate(UDate.getInstanceTimestamp(rs.getTimestamp("EMN_ADATE")));
        bean.setEmnEuid(rs.getInt("EMN_EUID"));
        bean.setEmnEdate(UDate.getInstanceTimestamp(rs.getTimestamp("EMN_EDATE")));
        return bean;
    }
}
