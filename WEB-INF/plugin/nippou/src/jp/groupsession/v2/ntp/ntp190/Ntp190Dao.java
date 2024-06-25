package jp.groupsession.v2.ntp.ntp190;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.ntp.model.NtpContactModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 日報 顧客源泉一覧画面で使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp190Dao  extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp190Dao.class);

    /**
     *<p>Default Constructor
     * */
    public Ntp190Dao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Ntp190Dao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] 活動方法情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return 活動方法リスト
     * @throws SQLException SQL実行時例外
     */
    public List<NtpContactModel> getKthouhouList()
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<NtpContactModel> ret = new ArrayList<NtpContactModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NTP_CONTACT.NCN_SID AS NCN_SID, ");
            sql.addSql("   NTP_CONTACT.NCN_CODE AS NCN_CODE, ");
            sql.addSql("   NTP_CONTACT.NCN_NAME AS NCN_NAME, ");
            sql.addSql("   CONTACT_SORT.NCS_SORT AS NCS_SORT ");
            sql.addSql(" from ");
            sql.addSql("   NTP_CONTACT");
            sql.addSql("   left join");
            sql.addSql("     (");
            sql.addSql("      select NCN_SID, NCS_SORT");
            sql.addSql("      from NTP_CONTACT_SORT");
            sql.addSql("     ) CONTACT_SORT");
            sql.addSql("   on");
            sql.addSql("     NTP_CONTACT.NCN_SID = CONTACT_SORT.NCN_SID ");
            sql.addSql(" order by ");
            sql.addSql("   CONTACT_SORT.NCS_SORT asc ");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                NtpContactModel model = new NtpContactModel();
                model.setNcnSid(rs.getInt("NCN_SID"));
                model.setNcnCode(rs.getString("NCN_CODE"));
                model.setNcnName(rs.getString("NCN_NAME"));
                model.setNcsSort(rs.getInt("NCS_SORT"));
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
