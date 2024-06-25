package jp.groupsession.v2.api.webmail.mail.edit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p> WEBメール一覧情報を更新する際に使用するDAOクラス
 *
 * @author JTS DaoGenerator version 0.5
 */
public class ApiWmlMailEditDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiWmlMailEditDao.class);

    /**
     * <p>Default Constructor
     */
    public ApiWmlMailEditDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public ApiWmlMailEditDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] webmailのアカウント情報(WAC_SIDとWAC_NAME)の一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param mailNumList メールSID一覧
     * @return List in WML_ACCOUNTModel
     * @throws SQLException SQL実行例外
     */
    public HashMap<Long, Integer> getMailDirectoryList(long[] mailNumList) throws SQLException {

        if (mailNumList == null || mailNumList.length == 0) {
            return null;
        }

        PreparedStatement pstmt = null;
        ResultSet  rs  = null;
        Connection con = getCon();

        HashMap<Long, Integer> ret = new HashMap<Long, Integer>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WDR_SID");
            sql.addSql(" from");
            sql.addSql("   WML_MAILDATA");
            sql.addSql(" where ");
            sql.addSql("   WMD_MAILNUM in (");

            for (int idx = 0; idx < mailNumList.length; idx++) {
                sql.addSql((idx > 0 ? "     ,?" : "     ?"));
                sql.addLongValue(mailNumList[idx]);
            }

            sql.addSql("   )");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Long    key   = Long.valueOf(rs.getLong("WMD_MAILNUM"));
                Integer value = Integer.valueOf(rs.getInt("WDR_SID"));
                ret.put(key, value);
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
