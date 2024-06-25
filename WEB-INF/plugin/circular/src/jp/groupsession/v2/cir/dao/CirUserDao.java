package jp.groupsession.v2.cir.dao;

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
import jp.groupsession.v2.cir.model.CirUserModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>CIR_USER Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CirUserDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CirUserDao.class);

    /**
     * <p>Default Constructor
     */
    public CirUserDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CirUserDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] 回覧板個人設定を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param bean CIR_USER Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insertCirUser(CirUserModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CIR_USER(");
            sql.addSql("   USR_SID,");
            sql.addSql("   CUR_MAX_DSP,");
            sql.addSql("   CUR_RELOAD,");
            sql.addSql("   CUR_AUID,");
            sql.addSql("   CUR_ADATE,");
            sql.addSql("   CUR_EUID,");
            sql.addSql("   CUR_EDATE");
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

            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getCurMaxDsp());
            sql.addIntValue(bean.getCurReload());
            sql.addIntValue(bean.getCurAuid());
            sql.addDateValue(bean.getCurAdate());
            sql.addIntValue(bean.getCurEuid());
            sql.addDateValue(bean.getCurEdate());

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
     * 表示件数の更新を行う
     * @param bean CIR_USER Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateDspCount(CirUserModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CIR_USER");
            sql.addSql(" set ");
            sql.addSql("   CUR_MAX_DSP = ?,");
            sql.addSql("   CUR_RELOAD = ?,");
            sql.addSql("   CUR_EUID = ?,");
            sql.addSql("   CUR_EDATE = ?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCurMaxDsp());
            sql.addIntValue(bean.getCurReload());
            sql.addIntValue(bean.getCurEuid());
            sql.addDateValue(bean.getCurEdate());
            //where
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
     * <br>[機  能] ショートメール個人設定を取得
     * <br>[解  説]
     * <br>[備  考]
     * @return List in CIR_USERModel
     * @throws SQLException SQL実行例外
     */
    public List<CirUserModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<CirUserModel> ret = new ArrayList<CirUserModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   CUR_MAX_DSP,");
            sql.addSql("   CUR_RELOAD,");
            sql.addSql("   CUR_AUID,");
            sql.addSql("   CUR_ADATE,");
            sql.addSql("   CUR_EUID,");
            sql.addSql("   CUR_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CIR_USER");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getCirUserFromRs(rs));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }
//    /**
//     * <br>[機  能] 指定されたユーザSIDリストの中から
//     *              ショートメール通知対象の受信者情報を取得
//     * <br>[解  説]
//     * <br>[備  考]
//     * @param userSid ユーザSID
//     * @return List in CIR_USERModel
//     * @throws SQLException SQL実行例外
//     */
//    public List<CirUserModel> getMailSendUser(String[] userSid) throws SQLException {
//
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        Connection con = null;
//        List<CirUserModel> ret = new ArrayList<CirUserModel>();
//        con = getCon();
//
//        if (userSid == null) {
//            return ret;
//        }
//        if (userSid.length < 1) {
//            return ret;
//        }
//
//        try {
//            //SQL文
//            SqlBuffer sql = new SqlBuffer();
//            sql.addSql(" select ");
//            sql.addSql("   USR_SID,");
//            sql.addSql("   CUR_MAX_DSP,");
//            sql.addSql("   CUR_SML_NTF,");
//            sql.addSql("   CUR_RELOAD,");
//            sql.addSql("   CUR_AUID,");
//            sql.addSql("   CUR_ADATE,");
//            sql.addSql("   CUR_EUID,");
//            sql.addSql("   CUR_EDATE,");
//            sql.addSql("   CUR_MEMO_KBN,");
//            sql.addSql("   CUR_MEMO_DAY,");
//            sql.addSql("   CUR_KOU_KBN,");
//            sql.addSql("   CUR_INIT_KBN");
//            sql.addSql(" from ");
//            sql.addSql("   CIR_USER");
//
//            sql.addSql(" where ");
//            sql.addSql("   USR_SID in (");
//
//            for (int i = 0; i < userSid.length; i++) {
//                sql.addSql("     ? ");
//                sql.addIntValue(NullDefault.getInt(userSid[i], 0));
//
//                if (i < userSid.length - 1) {
//                    sql.addSql("     , ");
//                }
//            }
//            sql.addSql("   ) ");
//            sql.addSql(" and ");
//            sql.addSql("   CUR_SML_NTF = ?");
//
//            sql.addIntValue(GSConstCircular.SMAIL_TSUUCHI);
//
//            log__.info(sql.toLogString());
//
//            pstmt = con.prepareStatement(sql.toSqlString());
//            sql.setParameter(pstmt);
//            rs = pstmt.executeQuery();
//
//            while (rs.next()) {
//                ret.add(__getCirUserFromRs(rs));
//            }
//        } catch (SQLException e) {
//            throw e;
//        } finally {
//            JDBCUtil.closeResultSet(rs);
//            JDBCUtil.closeStatement(pstmt);
//        }
//        return ret;
//    }

    /**
     * <br>[機  能] ユーザSIDから回覧板個人設定を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return CIR_USERModel
     * @throws SQLException SQL実行例外
     */
    public CirUserModel getCirUserInfo(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CirUserModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   CUR_MAX_DSP,");
            sql.addSql("   CUR_RELOAD,");
            sql.addSql("   CUR_AUID,");
            sql.addSql("   CUR_ADATE,");
            sql.addSql("   CUR_EUID,");
            sql.addSql("   CUR_EDATE");
            sql.addSql(" from");
            sql.addSql("   CIR_USER");
            sql.addSql(" where ");
            sql.addSql("   USR_SID = ?");

            sql.addIntValue(userSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = __getCirUserFromRs(rs);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }

//    /**
//     * <p>Select CIR_USER
//     * @param bean CIR_USER Model
//     * @return CIR_USERModel
//     * @throws SQLException SQL実行例外
//     */
//    public CirUserModel select(CirUserModel bean) throws SQLException {
//
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        Connection con = null;
//        CirUserModel ret = null;
//        con = getCon();
//
//        try {
//            //SQL文
//            SqlBuffer sql = new SqlBuffer();
//            sql.addSql(" select");
//            sql.addSql("   USR_SID,");
//            sql.addSql("   CUR_MAX_DSP,");
//            sql.addSql("   CUR_AUID,");
//            sql.addSql("   CUR_ADATE,");
//            sql.addSql("   CUR_EUID,");
//            sql.addSql("   CUR_EDATE,");
//            sql.addSql("   CUR_MEMO_KBN,");
//            sql.addSql("   CUR_MEMO_DAY,");
//            sql.addSql("   CUR_KOU_KBN,");
//            sql.addSql("   CUR_INIT_KBN");
//            sql.addSql(" from");
//            sql.addSql("   CIR_USER");
//            sql.addSql(" where ");
//            sql.addSql("   USR_SID=?");
//
//            pstmt = con.prepareStatement(sql.toSqlString());
//            sql.addValue(bean.getUsrSid());
//
//            log__.info(sql.toLogString());
//            sql.setParameter(pstmt);
//            rs = pstmt.executeQuery();
//            if (rs.next()) {
//                ret = __getCirUserFromRs(rs);
//            }
//        } catch (SQLException e) {
//            throw e;
//        } finally {
//            JDBCUtil.closeResultSet(rs);
//            JDBCUtil.closeStatement(pstmt);
//        }
//        return ret;
//    }

    /**
     * <br>[機  能] 回覧板個人設定を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int deleteCirUser(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CIR_USER");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            sql.addIntValue(userSid);

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
     * <p>Create CIR_USER Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CirUserModel
     * @throws SQLException SQL実行例外
     */
    private CirUserModel __getCirUserFromRs(ResultSet rs) throws SQLException {
        CirUserModel bean = new CirUserModel();
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setCurMaxDsp(rs.getInt("CUR_MAX_DSP"));
        bean.setCurReload(rs.getInt("CUR_RELOAD"));
        bean.setCurAuid(rs.getInt("CUR_AUID"));
        bean.setCurAdate(UDate.getInstanceTimestamp(rs.getTimestamp("CUR_ADATE")));
        bean.setCurEuid(rs.getInt("CUR_EUID"));
        bean.setCurEdate(UDate.getInstanceTimestamp(rs.getTimestamp("CUR_EDATE")));
        return bean;
    }
//
//    /**
//     * 初期値 設定値を登録する。
//     * @param bean CIR_USER Data Bindding JavaBean
//     * @throws SQLException SQL実行例外
//     */
//    public void updateInitSet(CirUserModel bean) throws SQLException {
//
//        PreparedStatement pstmt = null;
//        Connection con = null;
//        con = getCon();
//
//        try {
//            //SQL文
//            SqlBuffer sql = new SqlBuffer();
//            sql.addSql(" update");
//            sql.addSql("   CIR_USER");
//            sql.addSql(" set ");
//            sql.addSql("   CUR_MEMO_KBN = ?,");
//            sql.addSql("   CUR_MEMO_DAY = ?,");
//            sql.addSql("   CUR_KOU_KBN = ?,");
//            sql.addSql("   CUR_INIT_KBN = ?,");
//            sql.addSql("   CUR_EUID = ?,");
//            sql.addSql("   CUR_EDATE = ?");
//            sql.addSql(" where ");
//            sql.addSql("   USR_SID = ?");
//
//            pstmt = con.prepareStatement(sql.toSqlString());
//            sql.addIntValue(bean.getCurMemoKbn());
//            sql.addIntValue(bean.getCurMemoDay());
//            sql.addIntValue(bean.getCurKouKbn());
//            sql.addIntValue(bean.getCurInitKbn());
//            sql.addIntValue(bean.getCurEuid());
//            sql.addDateValue(bean.getCurEdate());
//            //where
//            sql.addIntValue(bean.getUsrSid());
//
//            log__.info(sql.toLogString());
//            sql.setParameter(pstmt);
//            pstmt.executeUpdate();
//        } catch (SQLException e) {
//            throw e;
//        } finally {
//            JDBCUtil.closeStatement(pstmt);
//        }
//    }
}
