package jp.groupsession.v2.ntp.ntp180;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.ntp.model.NtpKthouhouModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 日報 活動方法一覧画面で使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp180Dao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp180Dao.class);

    /**
     *<p>Default Constructor
     * */
    public Ntp180Dao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Ntp180Dao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] 活動方法情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return 活動方法リスト
     * @throws SQLException SQL実行時例外
     */
    public List<NtpKthouhouModel> getKthouhouList()
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<NtpKthouhouModel> ret = new ArrayList<NtpKthouhouModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NTP_KTHOUHOU.NKH_SID AS NKH_SID, ");
            sql.addSql("   NTP_KTHOUHOU.NKH_CODE AS NKH_CODE, ");
            sql.addSql("   NTP_KTHOUHOU.NKH_NAME AS NKH_NAME, ");
            sql.addSql("   KTHOUHOU_SORT.NHS_SORT AS NHS_SORT ");
            sql.addSql(" from ");
            sql.addSql("   NTP_KTHOUHOU");
            sql.addSql("   left join");
            sql.addSql("     (");
            sql.addSql("      select NKH_SID, NHS_SORT");
            sql.addSql("      from NTP_KTHOUHOU_SORT ");
            sql.addSql("     ) KTHOUHOU_SORT");
            sql.addSql("   on");
            sql.addSql("     NTP_KTHOUHOU.NKH_SID = KTHOUHOU_SORT.NKH_SID ");
            sql.addSql(" order by ");
            sql.addSql("   KTHOUHOU_SORT.NHS_SORT asc ");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                NtpKthouhouModel model = new NtpKthouhouModel();
                model.setNkhSid(rs.getInt("NKH_SID"));
                model.setNkhCode(rs.getString("NKH_CODE"));
                model.setNkhName(rs.getString("NKH_NAME"));
                model.setNhsSort(rs.getInt("NHS_SORT"));
                ret.add(model);
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
