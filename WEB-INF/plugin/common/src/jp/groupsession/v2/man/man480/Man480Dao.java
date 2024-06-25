package jp.groupsession.v2.man.man480;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.usr.GSConstUser;
/**
 *
 * <br>[機  能] Todoリストインサート画面専用DAO
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man480Dao extends AbstractDao {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man480Dao.class);
    /**
     *
     * コンストラクタ
     * @param con コネクション
     */
    public Man480Dao(Connection con) {
        super(con);
    }
    /**
     * <br>[機  能] ユーザ名が存在するユーザSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param nameMap ユーザ名マップ
     * @throws SQLException SQL実行時例外
     */
    public void setUserSidToNameMap(Map<String, Integer> nameMap)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        if (nameMap == null || nameMap.isEmpty()) {
            return;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CMN_USRM.USR_SID as USR_SID, ");
            sql.addSql("   CMN_USRM.USR_UKO_FLG as USR_UKO_FLG, ");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI, ");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI ");
            sql.addSql(" from");
            sql.addSql("   CMN_USRM, ");
            sql.addSql("   CMN_USRM_INF ");
            sql.addSql(" where ");
            sql.addSql("   CMN_USRM.USR_JKBN = ? ");
            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);
            sql.addSql(" and ");
            sql.addSql("   CMN_USRM_INF.USI_SEI || ' ' || CMN_USRM_INF.USI_MEI in (");
            int idx = 0;
            for (String name : nameMap.keySet()) {
                if (idx == 0) {
                    sql.addSql("     ?");
                } else {
                    sql.addSql("     ,?");
                }
                sql.addStrValue(name);
                idx++;
            }
            sql.addSql("   )");
            sql.addSql(" and ");
            sql.addSql("   CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID ");


            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                // 一致するユーザ名が存在する場合のみユーザSIDをセットする
                String  userName = rs.getString("USI_SEI") + " " + rs.getString("USI_MEI");
                if (nameMap.containsKey(userName)) {
                    Integer userSid  = Integer.valueOf(rs.getInt("USR_SID"));
                    nameMap.put(userName, userSid);
                }
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return;
    }
}
