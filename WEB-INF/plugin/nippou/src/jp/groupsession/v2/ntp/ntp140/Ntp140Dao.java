package jp.groupsession.v2.ntp.ntp140;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.ntp.model.NtpGyomuModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 日報 業種一覧画面で使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp140Dao extends AbstractDao {


    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp140Dao.class);

    /**
     *<p>Default Constructor
     * */
    public Ntp140Dao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Ntp140Dao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] 業務情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return 活動方法リスト
     * @throws SQLException SQL実行時例外
     */
    public List<NtpGyomuModel> getGyoumuList()
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<NtpGyomuModel> ret = new ArrayList<NtpGyomuModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NTP_GYOMU.NGY_SID AS NGY_SID, ");
            sql.addSql("   NTP_GYOMU.NGY_CODE AS NGY_CODE, ");
            sql.addSql("   NTP_GYOMU.NGY_NAME AS NGY_NAME, ");
            sql.addSql("   GYOMU_SORT.NGS_SORT AS NGS_SORT ");
            sql.addSql(" from ");
            sql.addSql("   NTP_GYOMU");
            sql.addSql("   left join");
            sql.addSql("     (");
            sql.addSql("      select NGY_SID, NGS_SORT");
            sql.addSql("      from NTP_GYOMU_SORT ");
            sql.addSql("     ) GYOMU_SORT");
            sql.addSql("   on");
            sql.addSql("     NTP_GYOMU.NGY_SID = GYOMU_SORT.NGY_SID ");
            sql.addSql(" order by ");
            sql.addSql("   GYOMU_SORT.NGS_SORT asc ");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                NtpGyomuModel model = new NtpGyomuModel();
                model.setNgySid(rs.getInt("NGY_SID"));
                model.setNgyCode(rs.getString("NGY_CODE"));
                model.setNgyName(rs.getString("NGY_NAME"));
                model.setNgsSort(rs.getInt("NGS_SORT"));
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
