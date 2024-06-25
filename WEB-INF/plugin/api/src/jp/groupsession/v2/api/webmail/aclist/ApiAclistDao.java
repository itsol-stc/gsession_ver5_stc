package jp.groupsession.v2.api.webmail.aclist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p> WEBメールのアカウント一覧を取得する際に使用するDAOクラス
 *
 * @author JTS DaoGenerator version 0.5
 */
public class ApiAclistDao extends AbstractDao {

    /** アカウント 状態区分 通常 */
    private static final int WAC_JKBN_NORMAL = 0;

    /** アカウント情報 アカウント種別 通常 */
    private static final int WAC_TYPE_NORMAL = 0;
    /** アカウント情報 アカウント種別 共通(グループ) */
    private static final int WAC_TYPE_GROUP = 1;
    /** アカウント情報 アカウント種別 共通(ユーザ) */
    private static final int WAC_TYPE_USER = 2;

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiAclistDao.class);

    /**
     * <p>Default Constructor
     */
    public ApiAclistDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public ApiAclistDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] webmailのアカウント情報(WAC_SIDとWAC_NAME)の一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return List in WML_ACCOUNTModel
     * @throws SQLException SQL実行例外
     */
    public List<ApiAclistModel> getAccountList(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ApiAclistModel> ret = new ArrayList<ApiAclistModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WML_ACCOUNT.WAC_SID as WAC_SID,");
            sql.addSql("   WML_ACCOUNT.WAC_NAME as WAC_NAME");
            sql = __setAccountSearchSql(sql, userSid);

            sql.addSql(" order by");
            sql.addSql("   ACCOUNT_SORT.WAS_SORT, WML_ACCOUNT.WAC_NAME");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlAccountFromRs(rs));
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
     * <br>[機  能] メール情報取得SQLの検索条件部分を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SqlBuffer
     * @param userSid ユーザSID
     * @return SqlBuffer
     */
    private SqlBuffer __setAccountSearchSql(SqlBuffer sql, int userSid) {
        sql.addSql(" from ");
        sql.addSql("   WML_ACCOUNT");
        sql.addSql("   left join");
        sql.addSql("     (");
        sql.addSql("       select WAC_SID, WAS_SORT");
        sql.addSql("       from WML_ACCOUNT_SORT");
        sql.addSql("       where USR_SID = ?");
        sql.addSql("     ) ACCOUNT_SORT");
        sql.addSql("   on");
        sql.addSql("     WML_ACCOUNT.WAC_SID = ACCOUNT_SORT.WAC_SID");
        sql.addSql(" where");
        sql.addSql("   WML_ACCOUNT.WAC_JKBN = ?");
        sql.addSql(" and");
        sql.addSql("   (");
        sql.addSql("      (");
        sql.addSql("         WAC_TYPE = ?");
        sql.addSql("       and");
        sql.addSql("         USR_SID = ?");
        sql.addSql("      )");
        sql.addSql("     or");
        sql.addSql("      (");
        sql.addSql("         WAC_TYPE = ?");
        sql.addSql("       and");
        sql.addSql("         exists (");
        sql.addSql("           select WAC_SID from WML_ACCOUNT_USER");
        sql.addSql("           where");
        sql.addSql("             GRP_SID in (");
        sql.addSql("               select GRP_SID from CMN_BELONGM");
        sql.addSql("               where USR_SID = ?");
        sql.addSql("             )");
        sql.addSql("           and");
        sql.addSql("             WML_ACCOUNT.WAC_SID = WML_ACCOUNT_USER.WAC_SID");
        sql.addSql("         )");
        sql.addSql("      )");
        sql.addSql("     or");
        sql.addSql("      (");
        sql.addSql("         WAC_TYPE = ?");
        sql.addSql("       and");
        sql.addSql("         exists (");
        sql.addSql("           select WAC_SID from WML_ACCOUNT_USER");
        sql.addSql("           where USR_SID = ?");
        sql.addSql("           and");
        sql.addSql("             WML_ACCOUNT.WAC_SID = WML_ACCOUNT_USER.WAC_SID");
        sql.addSql("         )");
        sql.addSql("      )");
        sql.addSql("   )");

        sql.addIntValue(userSid);
        sql.addIntValue(WAC_JKBN_NORMAL);
        sql.addIntValue(WAC_TYPE_NORMAL);
        sql.addIntValue(userSid);
        sql.addIntValue(WAC_TYPE_GROUP);
        sql.addIntValue(userSid);
        sql.addIntValue(WAC_TYPE_USER);
        sql.addIntValue(userSid);

        return sql;
    }

    /**
     * <p>Create WML_ACCOUNT Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created WmlAccountModel
     * @throws SQLException SQL実行例外
     */
    private ApiAclistModel __getWmlAccountFromRs(ResultSet rs) throws SQLException {
        ApiAclistModel bean = new ApiAclistModel();
        bean.setWacSid(rs.getInt("WAC_SID"));
        bean.setWacName(rs.getString("WAC_NAME"));

        return bean;
    }
}
