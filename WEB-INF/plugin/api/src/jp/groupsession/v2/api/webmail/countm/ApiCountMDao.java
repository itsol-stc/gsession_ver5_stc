package jp.groupsession.v2.api.webmail.countm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConstWebmail;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p> WEBメールの未読件数を取得する際に使用するDAOクラス
 *
 * @author JTS DaoGenerator version 0.5
 */
public class ApiCountMDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiCountMDao.class);

    /**
     * <p>Default Constructor
     */
    public ApiCountMDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public ApiCountMDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] webmailの指定日時以降の受信メール件数取得
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @param date   指定日時
     * @return 受信メール件数
     * @throws SQLException SQL実行例外
     */
    public long getNewMailCount(int wacSid, UDate date) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        long ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(1) as CNT");
            sql.addSql(" from");
            sql.addSql("   WML_DIRECTORY,");
            sql.addSql("   WML_MAILDATA");
            sql.addSql(" where");
            sql.addSql("   WML_DIRECTORY.WAC_SID = ?");
            sql.addSql(" and");
            sql.addSql("   WML_DIRECTORY.WDR_TYPE = ?");
            sql.addSql(" and");
            sql.addSql("   WML_MAILDATA.WAC_SID = ?");
            sql.addSql(" and");
            sql.addSql("   WML_DIRECTORY.WDR_SID = WML_MAILDATA.WDR_SID");

            sql.addIntValue(wacSid);
            sql.addIntValue(GSConstWebmail.DIR_TYPE_RECEIVE);
            sql.addIntValue(wacSid);

            //日付 受信日 From
            if (date != null) {
                sql.addSql("    and");
                sql.addSql("      WML_MAILDATA.WMD_SDATE >= ?");
                sql.addDateValue(date);
            }

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
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
}
