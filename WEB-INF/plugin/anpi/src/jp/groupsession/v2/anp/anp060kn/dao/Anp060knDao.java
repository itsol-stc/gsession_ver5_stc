package jp.groupsession.v2.anp.anp060kn.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.usr.GSConstUser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 安否確認メッセージ配信確認画面のDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp060knDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Anp060knDao.class);

    /**
     * <p>Default Constructor
     */
    public Anp060knDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Anp060knDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] 緊急連絡先が設定されているデータを取得
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param usids 指定ユーザSID
     * @return List in ANP_PRI_CONFModel
     * @throws SQLException SQL実行例外
     */
    public HashMap<String, String> selectConnect(String[] usids) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        HashMap<String, String> ret = new HashMap<String, String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   APP_MAILADR");
            sql.addSql(" from ");
            sql.addSql("   ANP_PRI_CONF");
            sql.addSql(" where ");
            sql.addSql("   APP_MAILADR is not null and APP_MAILADR <> ''");

            if (usids != null && usids.length > 0) {
                String connect = " and (";
                for (String sid : usids) {
                    sql.addSql(connect + "   USR_SID = ?");
                    sql.addIntValue(Integer.parseInt(sid));
                    connect = " or ";
                }
                sql.addSql("     ) ");
            }

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.put(rs.getString("USR_SID"), rs.getString("APP_MAILADR"));
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
     * <br>[機  能] 指定したグループに安否の未返信ユーザがいるか調べる
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param anpSid 安否SID
     * @param grpSid グループSID
     * @return int count
     * @throws SQLException SQL実行例外
     */
    public int getCntMihensinGrpUsrs(int anpSid, int grpSid) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;

        con = getCon();
        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(*) as CNT");

            sql.addSql(" from");
            sql.addSql("   CMN_USRM, ");
            sql.addSql("   CMN_USRM_INF, ");
            sql.addSql("   CMN_BELONGM, ");
            sql.addSql("   ANP_JDATA ");
            sql.addSql(" where ");
            sql.addSql("   CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID ");
            sql.addSql(" and");
            //ユーザSID < 100は除外
            sql.addSql("   CMN_USRM.USR_SID>?");
            sql.addIntValue(GSConstUser.USER_RESERV_SID);
            sql.addSql(" and ");
            sql.addSql("   CMN_USRM.USR_JKBN = ? ");
            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);
            sql.addSql(" and ");
            sql.addSql("   CMN_BELONGM.USR_SID = CMN_USRM.USR_SID ");
            sql.addSql(" and ");
            sql.addSql("   CMN_BELONGM.GRP_SID = ? ");
            sql.addIntValue(grpSid);

            sql.addSql(" and ");
            sql.addSql("   CMN_USRM_INF.USR_SID = ANP_JDATA.USR_SID ");
            sql.addSql(" and ");
            sql.addSql("   ANP_JDATA.APH_SID = ? ");
            sql.addIntValue(anpSid);
            sql.addSql(" and ");
            sql.addSql("   ANP_JDATA.APD_RDATE is null ");

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] 指定したユーザの安否が未返信か返す
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param anpSid 安否SID
     * @param userSid ユーザSID
     * @return int count
     * @throws SQLException SQL実行例外
     */
    public int getCntMihensinUsr(int anpSid, int userSid) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;

        con = getCon();
        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(*) as CNT");

            sql.addSql(" from");
            sql.addSql("   CMN_USRM, ");
            sql.addSql("   CMN_USRM_INF, ");
            sql.addSql("   CMN_BELONGM, ");
            sql.addSql("   ANP_JDATA ");
            sql.addSql(" where ");
            sql.addSql("   CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID ");
            sql.addSql(" and");
            //ユーザSID < 100は除外
            sql.addSql("   CMN_USRM.USR_SID>?");
            sql.addIntValue(GSConstUser.USER_RESERV_SID);
            sql.addSql(" and ");
            sql.addSql("   CMN_USRM.USR_JKBN = ? ");
            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);
            sql.addSql(" and ");
            sql.addSql("   CMN_BELONGM.USR_SID = CMN_USRM.USR_SID ");
            sql.addSql(" and ");
            sql.addSql("   CMN_USRM_INF.USR_SID = ? ");
            sql.addIntValue(userSid);

            sql.addSql(" and ");
            sql.addSql("   CMN_USRM_INF.USR_SID = ANP_JDATA.USR_SID ");
            sql.addSql(" and ");
            sql.addSql("   ANP_JDATA.APH_SID = ? ");
            sql.addIntValue(anpSid);
            sql.addSql(" and ");
            sql.addSql("   ANP_JDATA.APD_RDATE is null ");

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
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
}